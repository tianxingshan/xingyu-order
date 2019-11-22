package com.kongque.service.basics.impl;

import java.io.Console;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import com.kongque.component.impl.JsonMapper;
import com.kongque.constants.Constants;
import com.kongque.dto.PlanOrderDetailDto;
import com.kongque.model.*;
import com.kongque.util.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.kongque.dao.IDaoService;
import com.kongque.dto.LogisticDto;
import com.kongque.dto.OrderDetailSearchDto;
import com.kongque.dto.OrderRepairDto;
import com.kongque.entity.basics.Dept;
import com.kongque.entity.basics.Logistic;
import com.kongque.entity.basics.LogisticOrder;
import com.kongque.entity.basics.Tenant;
import com.kongque.entity.basics.XiuyuCustomer;
import com.kongque.entity.order.Order;
import com.kongque.entity.order.OrderDetail;
import com.kongque.entity.repair.OrderRepair;
import com.kongque.service.basics.ILogisticService;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
@Transactional
public class LogisticServiceImpl implements ILogisticService{
	
	@Resource
	IDaoService dao;
	@Override
	public Pagination<Logistic> list(LogisticDto dto, PageBean pageBean) {
		Criteria criteria = dao.createCriteria(Logistic.class);
		if(!StringUtils.isBlank(dto.getLogisticType())){
			criteria.add(Restrictions.eq("logisticType", dto.getLogisticType()));
		}
		if (!StringUtils.isBlank(dto.getExpressCompany())) {
			criteria.add(Restrictions.like("expressCompany", dto.getExpressCompany(), MatchMode.ANYWHERE));
		}
		if (!StringUtils.isBlank(dto.getExpressNumber())) {
			criteria.add(Restrictions.like("expressNumber", dto.getExpressNumber(), MatchMode.ANYWHERE));
		}
		
		if(!StringUtils.isBlank(dto.getShopName())){//向本次数据库查询中添加发货门店查询条件[2017-06-21]
			criteria.add(Restrictions.like("shopName", dto.getShopName(),MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotBlank(dto.getCheckStatus())){
			criteria.add(Restrictions.eq("checkStatus", dto.getCheckStatus()));
		}
		if (null!=dto.getStartCreateTime()){
			criteria.add(Restrictions.ge("createTime",DateUtil.minDate(dto.getStartCreateTime())));
		}
		if (null!=dto.getEndCreateTime()){
			criteria.add(Restrictions.le("createTime",DateUtil.maxDate(dto.getEndCreateTime())));
		}
		criteria.add(Restrictions.eq("deleteFlag","0"));
		if(dto.getLogisticType()!=null){
			switch(dto.getLogisticType()){
			case "0":
				if (!StringUtils.isBlank(dto.getOrderCode())) {
					criteria.createCriteria("list", "ll")
					.add(Restrictions.like("ll.orderCode", dto.getOrderCode(), MatchMode.ANYWHERE));
				}
				if (!StringUtils.isBlank(dto.getOrderRepairCode())) {
						criteria.createCriteria("list", "ll").createCriteria("orderRepair", "o")
								.add(Restrictions.like("o.orderRepairCode", dto.getOrderRepairCode(), MatchMode.ANYWHERE));
				}
//				criteria.addOrder(org.hibernate.criterion.Order.desc("sendTime"));
				break;
			case "1":
				if (!StringUtils.isBlank(dto.getOrderRepairCode())) {
					criteria.createCriteria("list", "ll").createCriteria("orderRepair", "o")
							.add(Restrictions.like("o.orderRepairCode", dto.getOrderRepairCode(), MatchMode.ANYWHERE));
				}
//				criteria.addOrder(org.hibernate.criterion.Order.desc("deliveryTime"));
				break;
			}
		}
		if(dto.getLogisticType()!=null){
			switch(dto.getLogisticType()){
			case "1": 
					  if (dto.getStartTime() != null && dto.getEndTime() != null) {
							criteria.add(Restrictions.between("deliveryTime", DateUtil.minDate(dto.getStartTime()),
									DateUtil.maxDate(dto.getEndTime())));
						} else if (dto.getStartTime() != null) {
							criteria.add(Restrictions.between("deliveryTime", DateUtil.minDate(dto.getStartTime()),
									DateUtil.maxDate(dto.getStartTime())));
						} else if (dto.getEndTime() != null) {
							criteria.add(Restrictions.between("deliveryTime", DateUtil.minDate(dto.getEndTime()),
									DateUtil.maxDate(dto.getEndTime())));
						}
				break;
			case "0": 
				if (dto.getStartTime() != null && dto.getEndTime() != null) {
					criteria.add(Restrictions.between("sendTime", DateUtil.minDate(dto.getStartTime()),
							DateUtil.maxDate(dto.getEndTime())));
				} else if (dto.getStartTime() != null) {
					criteria.add(Restrictions.between("sendTime", DateUtil.minDate(dto.getStartTime()),
							DateUtil.maxDate(dto.getStartTime())));
				} else if (dto.getEndTime() != null) {
					criteria.add(Restrictions.between("sendTime", DateUtil.minDate(dto.getEndTime()),
							DateUtil.maxDate(dto.getEndTime())));
				}
				break;
			}
		}
		criteria.addOrder(org.hibernate.criterion.Order.desc("createTime"));
		Pagination<Logistic> pagination = new Pagination<>();
		pagination.setRows(dao.findListWithPagebeanCriteria(criteria, pageBean));
		pagination.setTotal(dao.findTotalWithCriteria(criteria));
		return pagination;
	}

	@Override
	public Result saveOrUpdate(LogisticDto dto) {
		if (StringUtils.isBlank(dto.getShopId())){
			return new Result("500","店铺代码不能为空!");
		}
		if(StringUtils.isBlank(dto.getId())){ 
			Logistic logistic=new Logistic();
			logistic.setCheckStatus("0");
			logistic.setDeleteFlag("0");
			//新增加物流单录入时间，注释掉发货单发货时间
			logistic.setCreateTime(new Date());
			if(dto.getLogisticType() != null) {
				logistic.setLogisticType(dto.getLogisticType());
				/*if(dto.getLogisticType().equals("0")){
					logistic.setSendTime(new Date());
				}else*/
				if(dto.getLogisticType().equals("1")){
					logistic.setDeliveryTime(new Date());
				}
			}
			logistic.setSender(dto.getSender());
			logistic.setSenderAddress(dto.getSenderAddress());
			logistic.setSenderPhone(dto.getSenderPhone());
			logistic.setReceiver(dto.getReceiver());
			logistic.setReceiverAddress(dto.getReceiverAddress());
			logistic.setReceiverPhone(dto.getReceiverPhone());
			logistic.setExpressCompany(dto.getExpressCompany());
			logistic.setExpressNumber(dto.getExpressNumber());
			logistic.setExpressPrice(dto.getExpressPrice());
			logistic.setSettlementType(dto.getSettlementType());
			logistic.setNote(dto.getNote());
			logistic.setShopId(dto.getShopId());
			logistic.setShopName(dto.getShopName());
			logistic.setTenantId(dto.getTenantId());
			logistic.setOrderType(dto.getOrderType());
			logistic.setLogisticStatus("1");
			dao.save(logistic);
			if(dto.getOrderList()!=null){
				if(dto.getOrderList().getOrderRepairId().length>0){
					for(int i=0;i<dto.getOrderList().getOrderRepairId().length;i++){
						if(dto.getLogisticType().equals("0")){
							LogisticOrder logisticOrder=new LogisticOrder();
							logisticOrder.setLogisticId(logistic.getId());
							logisticOrder.setOrderRepairId(dto.getOrderList().getOrderRepairId()[i]);
							OrderRepair orderRepair =dao.findById(OrderRepair.class,dto.getOrderList().getOrderRepairId()[i] );
							logisticOrder.setOrderCode(orderRepair.getOrderRepairCode());
							dao.save(logisticOrder);
//							orderRepair.setOrderRepairStatus("9");
							orderRepair.setOrderRepairStatus("10");//新增发货单微调单状态为待发货20190107
							dao.update(orderRepair);
						}else{
							LogisticOrder logisticOrder=new LogisticOrder();
							logisticOrder.setLogisticId(logistic.getId());
							logisticOrder.setOrderRepairId(dto.getOrderList().getOrderRepairId()[i]);
							OrderRepair orderRepair =dao.findById(OrderRepair.class,dto.getOrderList().getOrderRepairId()[i] );
							logisticOrder.setOrderCode(orderRepair.getOrderRepairCode());
							dao.save(logisticOrder);
//							orderRepair.setOrderRepairStatus("10");
							orderRepair.setOrderRepairStatus("9");//新增收货单微调单状态为待收货20190107
							dao.update(orderRepair);
						}
					}
				}
				if(dto.getOrderList().getOrderDetailId().length>0){
					for(int i=0;i<dto.getOrderList().getOrderDetailId().length;i++){
						LogisticOrder logisticOrder=new LogisticOrder();
						logisticOrder.setLogisticId(logistic.getId());
						logisticOrder.setOrderDetailId(dto.getOrderList().getOrderDetailId()[i]);
						OrderDetail orderDetail =dao.findById(OrderDetail.class, dto.getOrderList().getOrderDetailId()[i]);
						Order order =dao.findById(Order.class, orderDetail.getOrderId());
						logisticOrder.setOrderCode(order.getOrderCode());
						dao.save(logisticOrder);
						orderDetail.setOrderDetailStatus("9");
						dao.update(orderDetail);
					}
					
				}		
			}
			return new Result(logistic);
			}else{
				Logistic logistic=dao.findById(Logistic.class, dto.getId());
				if(dto.getSendTime()!=null){
					logistic.setSendTime(dto.getSendTime());
				}
				if(dto.getDeliveryTime()!=null){
					logistic.setDeliveryTime(dto.getDeliveryTime());
				}
				logistic.setSender(dto.getSender());
				logistic.setSenderAddress(dto.getSenderAddress());
				logistic.setSenderPhone(dto.getSenderPhone());
				logistic.setReceiver(dto.getReceiver());
				logistic.setReceiverAddress(dto.getReceiverAddress());
				logistic.setReceiverPhone(dto.getReceiverPhone());
				logistic.setExpressCompany(dto.getExpressCompany());
				logistic.setExpressNumber(dto.getExpressNumber());
				logistic.setExpressPrice(dto.getExpressPrice());
				logistic.setSettlementType(dto.getSettlementType());
				logistic.setNote(dto.getNote());
				logistic.setShopId(dto.getShopId());
				logistic.setShopName(dto.getShopName());
				logistic.setTenantId(dto.getTenantId());
				logistic.setOrderType(dto.getOrderType());
				dao.update(logistic);
				Criteria criteria = dao.createCriteria(LogisticOrder.class);
				criteria.add(Restrictions.eq("logisticId", logistic.getId()));
				@SuppressWarnings("unchecked")
				List<LogisticOrder> listt = criteria.list();
				if(!ListUtils.isEmptyOrNull(listt)) {
					updateOrderstatus(logistic.getLogisticType(), listt);
				}
				for (LogisticOrder logisticOrder2 : listt) {
					dao.delete(logisticOrder2);
				}
				if(dto.getOrderList()!=null){
					if(dto.getOrderList().getOrderRepairId()!=null && dto.getOrderList().getOrderRepairId().length>0){
						for(int i=0;i<dto.getOrderList().getOrderRepairId().length;i++){
							if(dto.getLogisticType().equals("0")){
								LogisticOrder logisticOrder=new LogisticOrder();
								logisticOrder.setLogisticId(logistic.getId());
								logisticOrder.setOrderRepairId(dto.getOrderList().getOrderRepairId()[i]);
								OrderRepair orderRepair =dao.findById(OrderRepair.class,dto.getOrderList().getOrderRepairId()[i] );
								logisticOrder.setOrderCode(orderRepair.getOrderRepairCode());
								dao.save(logisticOrder);
//								orderRepair.setOrderRepairStatus("9");
								orderRepair.setOrderRepairStatus("10"); //20190107修改微调单状态，10为待发货
								dao.update(orderRepair);
							}else{
								LogisticOrder logisticOrder=new LogisticOrder();
								logisticOrder.setLogisticId(logistic.getId());
								logisticOrder.setOrderRepairId(dto.getOrderList().getOrderRepairId()[i]);
								OrderRepair orderRepair =dao.findById(OrderRepair.class,dto.getOrderList().getOrderRepairId()[i] );
								logisticOrder.setOrderCode(orderRepair.getOrderRepairCode());
								dao.save(logisticOrder);
//								orderRepair.setOrderRepairStatus("10");
								orderRepair.setOrderRepairStatus("9");//20190107修改微调单状态，9为待收货
								dao.update(orderRepair);
							}
						}
					}
					if(dto.getOrderList().getOrderDetailId()!=null && dto.getOrderList().getOrderDetailId().length>0){
						for(int i=0;i<dto.getOrderList().getOrderDetailId().length;i++){
							LogisticOrder logisticOrder=new LogisticOrder();
							logisticOrder.setLogisticId(logistic.getId());
							logisticOrder.setOrderDetailId(dto.getOrderList().getOrderDetailId()[i]);
							OrderDetail orderDetail =dao.findById(OrderDetail.class, dto.getOrderList().getOrderDetailId()[i]);
							Order order =dao.findById(Order.class, orderDetail.getOrderId());
							logisticOrder.setOrderCode(order.getOrderCode());
							dao.save(logisticOrder);
							orderDetail.setOrderDetailStatus("9");
							dao.update(orderDetail);
						}
						
					}
			}
			return new Result(logistic);
		}
	}
	//删除物流明细前，将订单状态恢复到录入物流单之前的状态
	private void updateOrderstatus(String logisticType,List<LogisticOrder> listt) {
		for(LogisticOrder logisticOrder : listt) {
			switch (logisticType) {
			//发货
			case "0":
				if(logisticOrder.getOrderRepair() != null) {
					//更新微调单状态为生产完成
					logisticOrder.getOrderRepair().setOrderRepairStatus("6");	
				}else if(logisticOrder.getOrderDetail() != null) {
					//更新订单明细状态为生产完成
					logisticOrder.getOrderDetail().setOrderDetailStatus("2");
				}
				break;
			//收货
			case "1":
				if(logisticOrder.getOrderRepair() != null) {
					//更新微调单状态为星域审核通过
					logisticOrder.getOrderRepair().setOrderRepairStatus("2");
				}
				break;
			}
		}
	}

	public String orderDetailStatus(String orderDetailStatus){
		String orderDetailStatuss=null;
		if(orderDetailStatus.equals("0")){
			orderDetailStatuss="已排程";
		}else if(orderDetailStatus.equals("1")){
			orderDetailStatuss="生产中";
		}else if(orderDetailStatus.equals("2")){
			orderDetailStatuss="生产完成";
		}
		else if(orderDetailStatus.equals("3")){
			orderDetailStatuss="已发货";
		}
		else if(orderDetailStatus.equals("4")){
			orderDetailStatuss="已收货";
		}else if(orderDetailStatus.equals("5")){
			orderDetailStatuss="已反馈";
		}
		return orderDetailStatuss;
	}
	
	public String orderRepairStatus(String orderRepairStatus){
		String orderRepairStatuss=null;
		if(orderRepairStatus.equals("0")){
			orderRepairStatuss="未送出";
		}else if(orderRepairStatus.equals("1")){
			orderRepairStatuss="已送出";
		}else if(orderRepairStatus.equals("2")){
			orderRepairStatuss="星域审核通过";
		}else if(orderRepairStatus.equals("3")){
			orderRepairStatuss="星域驳回";
		}else if(orderRepairStatus.equals("4")){
			orderRepairStatuss="已排程";
		}else if(orderRepairStatus.equals("5")){
			orderRepairStatuss="生产中";
		}else if(orderRepairStatus.equals("6")){
			orderRepairStatuss="生产完成";
		}else if(orderRepairStatus.equals("7")){
			orderRepairStatuss="已发货";
		}else if(orderRepairStatus.equals("8")){
			orderRepairStatuss="已收货";
		}
		return orderRepairStatuss;
	}
	@Override
	public Result delete(String id) {
		/*Logistic logistic=dao.findById(Logistic.class,id);
		logistic.setDeleteFlag("1");
		dao.update(logistic);*/
		Logistic logistic =null;
		if (StringUtils.isNotBlank(id)) {
			logistic = dao.findById(Logistic.class,id);
			if (logistic != null){
				List<LogisticOrder> logisticOrders = dao.findListByProperty(LogisticOrder.class,"logisticId",logistic.getId());
				switch(logistic.getLogisticType()) {
				case "0":
					if (logisticOrders.size() > 0) {
						for (LogisticOrder logisticOrder:logisticOrders){//修改发货单状态前，对发货单中所包含的明细信息状态进行校验[2017-07-27]
							if (logisticOrder.getOrderRepair()!= null){
								 OrderRepair orderRepair = dao.findById(OrderRepair.class, logisticOrder.getOrderRepair().getId());
								 if(!orderRepair.getOrderRepairStatus().equals("7")&& !orderRepair.getOrderRepairStatus().equals("6")&& !orderRepair.getOrderRepairStatus().equals("9")){//如果物流单所包含的微调单中有不是"已收货"状态的微调单则该物流单不能作废[2017-07-27]							 
			                        return new Result("500","包含["+orderRepairStatus(orderRepair.getOrderRepairStatus())+"]状态的微调单的发货单不能作废！");
								 }
							}
							if (logisticOrder.getOrderDetail() != null){
								   OrderDetail orderDetail = dao.findById(OrderDetail.class, logisticOrder.getOrderDetail().getId());
								 if(!orderDetail.getOrderDetailStatus().equals("3") &&!orderDetail.getOrderDetailStatus().equals("2") &&!orderDetail.getOrderDetailStatus().equals("9")){//如果物流单所包含的微调单中有不是"已收货"状态的微调单则该物流单不能作废[2017-07-27]							 
				                        return new Result("500","包含["+orderDetailStatus(orderDetail.getOrderDetailStatus())+"]状态的订单明细信息的发货单不能作废！");
								}
							}
						}
						for (LogisticOrder logisticOrder: logisticOrders){
	                        if (logisticOrder.getOrderRepair()!= null){
	                            OrderRepair orderRepair = dao.findById(OrderRepair.class, logisticOrder.getOrderRepair().getId());
	                            orderRepair.setOrderRepairStatus("6");
	                            dao.update(orderRepair);
	                            //记录作废发货物流单时微调单的状态变化
	                     /*       HistoryRecord historyRecord =HistoryRecordUtil.historyRecord(orderRepair.getId(), "OrderRepair", new Date(),user.getUserName()+"作废发货物流单时修改微调单:"+orderRepair.getRepairCode(),JSON.toJSONString(orderRepair), user.getId());
	                			dao.save(historyRecord);*/
	                        }
	                        if (logisticOrder.getOrderDetail() != null){
	                            OrderDetail orderDetail = dao.findById(OrderDetail.class, logisticOrder.getOrderDetail().getId());
	                            orderDetail.setOrderDetailStatus("2");
	                            dao.update(orderDetail);
	                            //记录作废发货物流单时订单的状态变化
	                      /*      HistoryRecord historyRecord =HistoryRecordUtil.historyRecord(orderDetail.getId(), "OrderDetail", new Date(),user.getUserName()+"作废发货物流单时修改订单:"+orderDetail.getOrderId(),JSON.toJSONString(orderDetail), user.getId());
	                			dao.save(historyRecord);*/
	                        }
	                    }
						logistic.setDeleteFlag("1");
						dao.update(logistic);
						//记录作废发货物流单
					/*	 HistoryRecord historyRecord =HistoryRecordUtil.historyRecord(logistic.getId(), "Logistic", new Date(),user.getUserName()+"作废发货物流单:"+logistic.getExpressNumber(),JSON.toJSONString(logistic), user.getId());
						dao.save(historyRecord);*/
					} else {
						logistic.setDeleteFlag("1");
						dao.update(logistic);
						//记录作废发货物流单
			/*			 HistoryRecord historyRecord =HistoryRecordUtil.historyRecord(logistic.getId(), "Logistic", new Date(),user.getUserName()+"作废发货物流单:"+logistic.getExpressNumber(),JSON.toJSONString(logistic), user.getId());
						dao.save(historyRecord);*/
					};
					break;
				case "1":
					if (logisticOrders.size() > 0) {
						for (LogisticOrder logisticOrder:logisticOrders){//修改收货单状态前，对收货单中所包含的明细信息状态进行校验[2017-07-27]
							 OrderRepair orderRepair = dao.findById(OrderRepair.class, logisticOrder.getOrderRepair().getId());
							 if(!orderRepair.getOrderRepairStatus().equals("4")&& !orderRepair.getOrderRepairStatus().equals("6")){//如果物流单所包含的微调单中有不是"已收货"状态的微调单则该物流单不能作废[2017-07-27]							 
		                        return new Result("500","包含["+orderRepairStatus(orderRepair.getOrderRepairStatus())+"]状态的微调单[微调单号："+orderRepair.getOrderRepairCode()+"]的收货单不能作废！");
							 }
						}
						Boolean checkStatus = true;
						for (LogisticOrder logisticOrder:logisticOrders){
	                        if (logisticOrder.getOrderRepair() != null){
	                        	OrderRepair orderRepair = dao.findById(OrderRepair.class, logisticOrder.getOrderRepair().getId());
	                            if ("2".equals(orderRepair.getOrderRepairStatus()) || "8".equals(orderRepair.getOrderRepairStatus())){
									orderRepair.setOrderRepairStatus("2");
									dao.update(orderRepair);
									//记录作废收货物流单时微调单的状态变化
						/*			HistoryRecord historyRecord =HistoryRecordUtil.historyRecord(orderRepair.getId(), "OrderRepair", new Date(),user.getUserName()+"作废收货物流单时修改微调单:"+orderRepair.getRepairCode(),JSON.toJSONString(orderRepair), user.getId());
									dao.save(historyRecord);*/
								}else {
									checkStatus = false;
									break;
								}
	                        }
	                    }
	                    if (checkStatus == false){
							return new Result("500","微调单状态不是星域审核通过或者已收货状态");
						}
						logistic.setDeleteFlag("1");
						dao.update(logistic);
						//记录作废收货物流单
					/*	 HistoryRecord historyRecord =HistoryRecordUtil.historyRecord(logistic.getId(), "Logistic", new Date(),user.getUserName()+"作废收货物流单:"+logistic.getExpressNumber(),JSON.toJSONString(logistic), user.getId());
	         			dao.save(historyRecord);*/
//	         			return new Result(logistic);
					}else {
						logistic.setDeleteFlag("1");
						dao.update(logistic);
						//记录作废收货物流单
	/*					 HistoryRecord historyRecord =HistoryRecordUtil.historyRecord(logistic.getId(), "Logistic", new Date(),user.getUserName()+"作废收货物流单:"+logistic.getExpressNumber(),JSON.toJSONString(logistic), user.getId());
	        			dao.save(historyRecord);*/
//						return new Result(logistic);
					};
					break;
				}
				
			}else {
				return new Result("500","不存在该物流单");
			}
		}else {
			return new Result("500","物流单号不能为空");
		}
		return new Result(logistic);   
	}

	@SuppressWarnings("unused")
	@Override

	public Result updateStatus(String checkUserId,String id, String checkStatus) {
		Logistic logistic=dao.findById(Logistic.class, id);
		StringBuilder excludedLogistic = new StringBuilder();
		Integer _0 = 0;
		boolean canCheckStatus = true;
		for(LogisticOrder logisticOrder : logistic.getList()){
			OrderRepair orderRepair=null;
			if(logisticOrder.getOrderRepair()!=null){
				if(logisticOrder.getOrderRepair().getId()!=null){
					orderRepair = dao.findById(OrderRepair.class, logisticOrder.getOrderRepair().getId());
				}
			}
			if(orderRepair != null){
				dao.refresh(orderRepair);
				if(StringUtils.isNotBlank(logistic.getLogisticType())){//判断物流单的类型属性是否为空[2017-07-27]
					switch(logistic.getLogisticType()){//判断物流单的类型是收货还是发货[2017-07-27]
				//修改微调单明细之前，对微调单明细的状态进行检验，如果要修改的微调单明细状态不是“星域审核通过”时，则不能审核该微调单所属物流单更不能改变微调单状态[2017-07-27]
//						case "0":if(!orderRepair.getOrderRepairStatus().equals("9")){
						case "0":if(!orderRepair.getOrderRepairStatus().equals("10")){//20190107修改待发货状态为10
							excludedLogistic.append(logistic.getExpressNumber()).append(",");//把当前物流单单号添加到不可审核物流单单号列表中[2017-07-28]
							canCheckStatus = false;//把审核标识变量设置为不可审核[2017-07-28]	
							return new Result("500","对应的微调单状态不是待发货，不能确认审核");
						}break;
				//修改微调单明细之前，对微调单明细的状态进行检验，如果要修改的微调单明细状态不是“星域审核通过”时，则不能审核该微调单所属物流单更不能改变微调单状态[2017-07-27]
//						case "1":if(!orderRepair.getOrderRepairStatus().equals("10")){
						case "1":if(!orderRepair.getOrderRepairStatus().equals("9")){//20190107修改待收货状态为9
							excludedLogistic.append(logistic.getExpressNumber()).append(",");//把当前物流单单号添加到不可审核物流单单号列表中[2017-07-28]
							canCheckStatus = false;//把审核标识变量设置为不可审核[2017-07-28]	
							return new Result("500","对应的微调单状态不是待收货，不能确认审核");
						}break;
					}						
				}else{//如果物流单的类型属性为空则不予审核
					canCheckStatus = false;
				}
			}else{
				//判断订单明细状态
				OrderDetail orderDetail=null;
				if(logisticOrder.getOrderDetail()!=null){
					if(logisticOrder.getOrderDetail().getId()!=null){
						orderDetail = dao.findById(OrderDetail.class, logisticOrder.getOrderDetail().getId());
					}
				}
					dao.refresh(orderDetail);
					//修改订单明细之前，对订单明细的状态进行检验，只有当要修改的订调单明细状态是“生产完成”时，才能对该订单明细所属物流单进行审核并改变订单明细状态[2017-07-27]
					if(!orderDetail.getOrderDetailStatus().equals("9")){
						excludedLogistic.append(logistic.getExpressNumber()).append(",");//把当前物流单单号添加到不可审核物流单单号列表中[2017-07-28]
						canCheckStatus = false;//把审核标识变量设置为不可审核[2017-07-28]
					}
			}
			if(!canCheckStatus) break;
		}
		if(canCheckStatus) {
			logistic=dao.findById(Logistic.class,id);
			logistic.setCheckStatus(checkStatus);
			logistic.setCheckUserId(checkUserId);
			logistic.setCheckTime(new Date());
			//如果是发货单，更新发货时间
			if ("0".equals(logistic.getLogisticType())){
				logistic.setSendTime(new Date());
			}
			dao.update(logistic);
			Result result1 = updateLogisticOrderStatus(logistic);
			if(!result1.getReturnCode().equals("200")){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  //手动回滚事务
				return result1;
			}
		}
		Result result = new Result();//初始化返回数据模型
		String message="所选物流单全部审核成功！";//初始化返回信息
		if(excludedLogistic.length() > 0){//如果所选物流单中有不符合审核要求的物流单被排除
			result.setReturnCode("300");//返回结果模型中的状态码设为300
			message = "由于包含的微调单或订单的状态不符合要求，不能进行审核的物流单单号有：["+excludedLogistic.deleteCharAt(excludedLogistic.length()-1)+"]";
		}
		result.setReturnMsg(message);//向返回结果数据模型中设置返回信息
		return result;
	}
	private Result updateLogisticOrderStatus(Logistic logistic) {
		for(LogisticOrder logisticOrder : logistic.getList()){
			OrderRepair orderRepair = null;
			if(logisticOrder.getOrderRepair()!=null){
				if(logisticOrder.getOrderRepair().getId()!=null){
					orderRepair =dao.findById(OrderRepair.class, logisticOrder.getOrderRepair().getId());
				}
			}
			if(orderRepair != null){
				dao.refresh(orderRepair);
				if(StringUtils.isNotBlank(logistic.getLogisticType())){//判断物流单的类型属性是否为空[2017-07-25]
					switch(logistic.getLogisticType()){//判断物流单的类型是收货还是发货[2017-07-25]
					case "0":orderRepair.setOrderRepairStatus("7"); break;//如果物流单类型是发货，则把对应的微调单状态改为已发货[2017-07-25]
					case "1":orderRepair.setOrderRepairStatus("8");break;//如果物流单类型是收货，则把对应的微调单状态改为已收货[2017-07-25]
					}
				}
				dao.update(orderRepair);
			}else{
				if(logisticOrder.getOrderDetail().getId()!=null){
					OrderDetail orderDetail = dao.findById(OrderDetail.class, logisticOrder.getOrderDetail().getId());
					dao.refresh(orderDetail);
					orderDetail.setOrderDetailStatus("3");
					Order order = dao.findById(Order.class,orderDetail.getOrderId());
					if(StringUtils.isNotBlank(order.getOutFlag())&&order.getOutFlag().equals("1")){
						Map<String, String> header = new HashMap<String, String>();
						header.put("FACTORY-CODE","XINGYU");
						String json="{\"orderCode\":\""+order.getOrderCode()+"\",\"name\":\""+logistic.getExpressCompany()+"\",\"code\":\""+logistic.getExpressNumber()+"\"}";
						Result result = JsonMapper.toObject(HttpClientUtils.doPostJson(Constants.YUN.KONGQUE_YUN_ORDER_LOGISTICS,header, json), Result.class);
						if(!"200".equals(result.getReturnCode())){
							return result;
						}
					}
				}
			}
		}
		return  new Result();
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<LogisticOrderModel> queryLogisticWithParam(LogisticDto dto, Integer page,Integer rows) {
		List<LogisticOrderModel> list=new ArrayList<LogisticOrderModel>();
		StringBuilder sql = null;
		if(dto.getVourType()!=null){
		if(dto.getVourType().equals("0")){
			 sql = new StringBuilder("SELECT l.c_shop_name AS shopName,z.c_customer_name AS customerName,r.c_order_repair_code AS orderRepairCode,q.c_order_code AS orderCode,r.c_is_extract AS shopConsume,l.c_logistic_type AS logisticType,o.c_goods_sn AS orderDetailGoodsSN,o.c_goods_code AS orderDetailGoodsCode,o.c_goods_name AS orderDetailGoodsName,o.c_goods_color_name AS orderDetailGoodsColorName,o.c_num AS orderDetailGoodsNum FROM t_logistic l  LEFT JOIN t_logistic_order t ON l.c_id=t.c_logistic_id LEFT JOIN t_order_detail o ON o.c_id=t.c_order_detail_id LEFT JOIN t_order_repair r ON r.c_id=t.c_order_repair_id LEFT JOIN t_order q ON q.c_id = o.c_order_id LEFT JOIN t_goods_detail h ON o.c_goods_detail_id=h.c_id LEFT JOIN t_goods s ON h.c_goods_id=s.c_id LEFT JOIN t_xiuyu_customer z ON q.c_customer_id= z.c_id WHERE l.c_delete_flag='0' and l.c_id = ");

		}else if(dto.getVourType().equals("1")){
			sql = new StringBuilder("SELECT l.c_shop_name AS shopName,r.c_order_repair_code AS orderRepairCode,q.c_order_code AS orderCode,r.c_is_extract AS shopConsume,r.c_customer_name AS repairCustomerName,l.c_logistic_type AS logisticType,r.c_goods_code AS orderRepairGoodsCode,r.c_goods_name AS orderRepairGoodsName,r.c_goods_color AS orderRepairGoodsColor,r.c_num AS orderRepairGoodsNum,r.c_solution as solution FROM t_logistic l  LEFT JOIN t_logistic_order t ON l.c_id=t.c_logistic_id LEFT JOIN t_order_detail o ON o.c_id=t.c_order_detail_id LEFT JOIN t_order_repair r ON r.c_id=t.c_order_repair_id LEFT JOIN t_order q ON q.c_id = o.c_order_id LEFT JOIN t_goods_detail h ON o.c_goods_detail_id=h.c_id LEFT JOIN t_goods s ON h.c_goods_id=s.c_id LEFT JOIN t_xiuyu_customer z ON q.c_customer_id= z.c_id WHERE l.c_delete_flag='0' and l.c_id = ");

		}
		}else{
			sql = new StringBuilder("SELECT l.c_shop_name AS shopName,r.c_order_repair_code AS orderRepairCode,q.c_order_code AS orderCode,r.c_is_extract AS shopConsume,r.c_customer_name AS repairCustomerName,l.c_logistic_type AS logisticType,r.c_goods_code AS orderRepairGoodsCode,r.c_goods_name AS orderRepairGoodsName,r.c_goods_color AS orderRepairGoodsColor,r.c_num AS orderRepairGoodsNum,r.c_solution AS solution,q.c_customer_name AS customerName,o.c_goods_sn AS orderDetailGoodsSN,o.c_goods_code AS orderDetailGoodsCode,o.c_goods_name AS orderDetailGoodsName,o.c_goods_color_name AS orderDetailGoodsColorName,o.c_num AS orderDetailGoodsNum,q.c_city as orderCity,r.c_city as orderRepairCity,q.c_reset as orderReset,q.c_order_character  as orderCharacter,l.c_send_time as sendTime,l.c_delivery_time as deliveryTime,l.c_express_company as expressCompany,l.c_express_number as expressNumber,l.c_sender as sender,l.c_receiver as receiver,l.c_express_price as expressPrice,l.c_settlement_type as settlementType FROM t_logistic_order t  LEFT JOIN t_logistic l ON l.c_id=t.c_logistic_id LEFT JOIN t_order_detail o ON o.c_id=t.c_order_detail_id LEFT JOIN t_order_repair r ON r.c_id=t.c_order_repair_id LEFT JOIN t_order q ON q.c_id = o.c_order_id LEFT JOIN t_goods_detail h ON o.c_goods_detail_id=h.c_id LEFT JOIN t_goods s ON h.c_goods_id=s.c_id LEFT JOIN t_xiuyu_customer z ON q.c_customer_id= z.c_id WHERE l.c_delete_flag='0'  and l.c_id =  ");
		}
		sql.append("'"+dto.getId()+"'");
		if(dto.getLogisticType()!=null) {
			switch(dto.getLogisticType()){
			case "1": sql.append(" AND l.c_logistic_type = '1' ");break;
			case "0": sql.append(" AND l.c_logistic_type = '0' ");break;
			}
		}
		if(dto.getOrderCode()!=null&&!dto.getOrderCode().isEmpty()){
			sql.append(" and q.c_order_code like '%").append(dto.getOrderCode()).append("%' ");
			
		}
		if(dto.getCustomerName()!=null&&!dto.getCustomerName().isEmpty()){
			sql.append(" and z.c_customer_name like '%").append(dto.getCustomerName()).append("%' ");
		}
		if(dto.getOrderRepairCode()!=null && !dto.getOrderRepairCode().isEmpty()) {
			sql.append(" and r.c_order_repair_code like '%").append(dto.getOrderRepairCode()).append("%' ");
		}
		if(dto.getShopName()!=null && !dto.getShopName().isEmpty()) {
			sql.append(" and l.c_shop_name like '%").append(dto.getShopName()).append("%' ");
		}
	/*	if(dto.getExpressCompany()!=null && !dto.getExpressCompany().isEmpty()) {
			sql.append(" and l.c_express_company like '%").append(dto.getExpressCompany()).append("%' ");
		}
		if(dto.getExpressNumber() != null && !dto.getExpressNumber().isEmpty()){//添加物流单号限定条件
			sql.append(" and l.c_express_number like '%").append(dto.getExpressNumber()).append("%'");
		}
		if(dto.getStartTime() != null  && dto.getEndTime() != null ){//添加同时包含起始发货日期和截止发货日期的限定条件
			
			switch(dto.getLogisticType()){
			case "1": sql.append(" and l.c_delivery_time between '").append(dto.getStartTime()).append(" 00:00:00'").append(" and '").append(dto.getEndTime()).append(" 23:59:59'");break;
			case "0": sql.append(" and l.c_send_time between '").append(dto.getStartTime()).append(" 00:00:00'").append(" and '").append(dto.getEndTime()).append(" 23:59:59'");break;
			}			
		}
		else if(dto.getStartTime() != null  && (dto.getEndTime() == null )){//添加只包含起始发货日期而不包含截止发货日期的限定条件
			
			switch(dto.getLogisticType()){
			case "1": sql.append(" and l.c_delivery_time >= '").append(dto.getStartTime()).append(" 00:00:00'");break;
			case "0": sql.append(" and l.c_send_time >= '").append(dto.getStartTime()).append(" 00:00:00'");break;
			}
			
		}
		else if ((dto.getStartTime() == null && dto.getEndTime() != null)) {//添加只包含截止发货日期而不包含起始发货日期的限定条件
			
			switch(dto.getLogisticType()){
			case "1": sql.append(" and l.c_delivery_time"
					+ " <= '").append(dto.getEndTime()).append(" 23:59:59'");break;
			case "0": sql.append(" and l.c_send_time <= '").append(dto.getEndTime()).append(" 23:59:59'");break;
			}			
		}*/
		if(page != null && rows != null){
			sql.append(" limit "+(page - 1) * rows+","+rows);
		}
		List resultSet = dao.queryBySql(sql.toString());
		if(dto.getVourType()!=null){
			if(dto.getVourType().equals("0")){
				for(Object result : resultSet){
					LogisticOrderModel rsModel = new LogisticOrderModel();
					Object[] properties = (Object[])result;
					rsModel.setShopName(properties[0]==null ? "" : properties[0].toString());
					rsModel.setCustomerName(properties[1]==null ? "" : properties[1].toString());
					rsModel.setOrderRepairCode(properties[2]==null ? "" : properties[2].toString());
					rsModel.setOrderCode(properties[3]==null ? "" : properties[3].toString());
					rsModel.setShopConsume(properties[4]==null ? "" : properties[4].toString());
					rsModel.setLogisticType(properties[5]==null ? "" : properties[5].toString());
					rsModel.setOrderDetailGoodsSN(properties[6]==null ? "" : properties[6].toString());
					rsModel.setOrderDetailGoodsCode(properties[7]==null ? "" : properties[7].toString());
					rsModel.setOrderDetailGoodsName(properties[8]==null ? "" : properties[8].toString());
					rsModel.setOrderDetailGoodsColorName(properties[9]==null ? "" : properties[9].toString());
					rsModel.setOrderDetailNum(properties[10]==null ? "" : properties[10].toString());
					list.add(rsModel);
				}
			}else if(dto.getVourType().equals("1")){
				for(Object result : resultSet){
					LogisticOrderModel rsModel = new LogisticOrderModel();
					Object[] properties = (Object[])result;
					rsModel.setShopName(properties[0]==null ? "" : properties[0].toString());
					rsModel.setOrderRepairCode(properties[1]==null ? "" : properties[1].toString());
					rsModel.setOrderCode(properties[2]==null ? "" : properties[2].toString());
					rsModel.setShopConsume(properties[3]==null ? "" : properties[3].toString());
					rsModel.setRepairCustomerName(properties[4]==null ? "" : properties[4].toString());
					rsModel.setLogisticType(properties[5]==null ? "" : properties[5].toString());
					rsModel.setOrderRepairGoodsCode(properties[6]==null ? "" : properties[6].toString());
					rsModel.setOrderRepairGoodsName(properties[7]==null ? "" : properties[7].toString());
					rsModel.setOrderRepairGoodsColor(properties[8]==null ? "" : properties[8].toString());
					rsModel.setOrderRepairGoodsNum(properties[9]==null ? "" : properties[9].toString());
					rsModel.setSolution(properties[10]==null ? "" : properties[10].toString());
					list.add(rsModel);
				}
			}
		}else{
			for(Object result : resultSet){
				LogisticOrderModel rsModel = new LogisticOrderModel();
				Object[] properties = (Object[])result;
				rsModel.setShopName(properties[0]==null ? "" : properties[0].toString());
				rsModel.setOrderRepairCode(properties[1]==null ? "" : properties[1].toString());
				rsModel.setOrderCode(properties[2]==null ? "" : properties[2].toString());
				rsModel.setShopConsume(properties[3]==null ? "" : properties[3].toString());
				rsModel.setRepairCustomerName(properties[4]==null ? "" : properties[4].toString());
				rsModel.setLogisticType(properties[5]==null ? "" : properties[5].toString());
				rsModel.setOrderRepairGoodsCode(properties[6]==null ? "" : properties[6].toString());
				rsModel.setOrderRepairGoodsName(properties[7]==null ? "" : properties[7].toString());
				rsModel.setOrderRepairGoodsColor(properties[8]==null ? "" : properties[8].toString());
				rsModel.setOrderRepairGoodsNum(properties[9]==null ? "" : properties[9].toString());
				rsModel.setSolution(properties[10]==null ? "" : properties[10].toString());
				rsModel.setCustomerName(properties[11]==null ? "" : properties[11].toString());
				rsModel.setOrderDetailGoodsSN(properties[12]==null ? "" : properties[12].toString());
				rsModel.setOrderDetailGoodsCode(properties[13]==null ? "" : properties[13].toString());
				rsModel.setOrderDetailGoodsName(properties[14]==null ? "" : properties[14].toString());
				rsModel.setOrderDetailGoodsColorName(properties[15]==null ? "" : properties[15].toString());
				rsModel.setOrderDetailNum(properties[16]==null ? "" : properties[16].toString());
				rsModel.setOrderCity(properties[17]==null ? "" : properties[17].toString());
				rsModel.setOrderRepairCity(properties[18]==null ? "" : properties[18].toString());
				rsModel.setOrderReset(properties[19]==null ? "" : properties[19].toString());
				rsModel.setOrderCharacter(properties[20]==null ? "" : properties[20].toString());
				rsModel.setSendTime(properties[21]==null ? "" : properties[21].toString());
				rsModel.setDeliveryTime(properties[22]==null ? "" : properties[22].toString());
				rsModel.setExpressCompany(properties[23]==null ? "" : properties[23].toString());
				rsModel.setExpressNumber(properties[24]==null ? "" : properties[24].toString());
				rsModel.setSender(properties[25]==null ? "" : properties[25].toString());
				rsModel.setReceiver(properties[26]==null ? "" : properties[26].toString());
				rsModel.setExpressPrice(properties[27]==null ? "" : properties[27].toString());
				rsModel.setSettlementType(properties[28]==null ? "" : properties[28].toString());
				list.add(rsModel);
			}
		}	
		return list;
	}
	@Override
	public long queryLogisticCountWithParam(LogisticDto dto) {
		StringBuilder sql = new StringBuilder("SELECT count(*) FROM t_logistic_order t LEFT JOIN t_logistic l ON l.c_id=t.c_logistic_id LEFT JOIN t_order_detail o ON o.c_id=t.c_order_detail_id LEFT JOIN t_order_repair r ON r.c_id=t.c_order_repair_id LEFT JOIN t_order q ON q.c_id = o.c_order_id LEFT JOIN t_goods_detail h ON o.c_goods_detail_id=h.c_id LEFT JOIN t_goods s ON h.c_goods_id=s.c_id LEFT JOIN t_xiuyu_customer z ON q.c_customer_id= z.c_id WHERE l.c_delete_flag='0' and l.c_id = ");
		sql.append("'"+dto.getId()+"'");
		if(dto.getLogisticType()!=null) {
			switch(dto.getLogisticType()){
			case "1": sql.append(" AND l.c_logistic_type = '1' ");break;
			case "0": sql.append(" AND l.c_logistic_type = '0' ");break;
			}
		}
		if(dto.getOrderCode()!=null&&!dto.getOrderCode().isEmpty()){
			sql.append(" and q.c_order_code like '%").append(dto.getOrderCode()).append("%' ");
			
		}
		if(dto.getCustomerName()!=null&&!dto.getCustomerName().isEmpty()){
			sql.append(" and z.c_customer_name like '%").append(dto.getCustomerName()).append("%' ");
		}
		if(dto.getOrderRepairCode()!=null && !dto.getOrderRepairCode().isEmpty()) {
			sql.append(" and r.c_order_repair_code like '%").append(dto.getOrderRepairCode()).append("%' ");
		}
		if(dto.getShopName()!=null && !dto.getShopName().isEmpty()) {
			sql.append(" and l.c_shop_name like '%").append(dto.getShopName()).append("%' ");
		}
		/*if(dto.getExpressCompany()!=null && !dto.getExpressCompany().isEmpty()) {
			sql.append(" and l.c_express_company like '%").append(dto.getExpressCompany()).append("%' ");
		}
		if(dto.getExpressNumber() != null && !dto.getExpressNumber().isEmpty()){//添加物流单号限定条件
			sql.append(" and l.c_express_number like '%").append(dto.getExpressNumber()).append("%'");
		}
		if(dto.getStartTime() != null  && dto.getEndTime() != null ){//添加同时包含起始发货日期和截止发货日期的限定条件
			switch(dto.getLogisticType()){
			case "1": sql.append(" and l.c_delivery_time between '").append(dto.getStartTime()).append(" 00:00:00'").append(" and '").append(dto.getEndTime()).append(" 23:59:59'");break;
			case "0": sql.append(" and l.c_send_time between '").append(dto.getStartTime()).append(" 00:00:00'").append(" and '").append(dto.getEndTime()).append(" 23:59:59'");break;
			}			
		}
		else if(dto.getStartTime() != null  && (dto.getEndTime() == null )){//添加只包含起始发货日期而不包含截止发货日期的限定条件
			switch(dto.getLogisticType()){
			case "1": sql.append(" and l.c_delivery_time >= '").append(dto.getStartTime()).append(" 00:00:00'");break;
			case "0": sql.append(" and l.c_send_time >= '").append(dto.getStartTime()).append(" 00:00:00'");break;
			}
			
		}
		else if ((dto.getStartTime() == null && dto.getEndTime() != null)) {//添加只包含截止发货日期而不包含起始发货日期的限定条件
			switch(dto.getLogisticType()){
			case "1": sql.append(" and l.c_delivery_time"
					+ " <= '").append(dto.getEndTime()).append(" 23:59:59'");break;
			case "0": sql.append(" and l.c_send_time <= '").append(dto.getEndTime()).append(" 23:59:59'");break;
			}			
		}*/
		List<BigInteger> result = dao.queryBySql(sql.toString());	
		return result == null || result.isEmpty() ? 0L : result.get(0).longValue();
	}
	
	public List<LogisticExportModel> queryLogisticWithParam1(LogisticDto  dto) {
		List<LogisticExportModel> list=new ArrayList<LogisticExportModel>();
		StringBuilder sql = new StringBuilder("SELECT l.c_express_number as expressNumber,l.c_express_company as expressCompany,l.c_send_time as sendTime,l.c_delivery_time as deliveryTime,r.c_city AS orderRepairCity,l.c_shop_name AS shopName,q.c_customer_name as customerName,q.c_order_code AS orderCode,r.c_order_repair_code AS orderRepairCode,r.c_goods_name  as orderRepairGoodsName,r.c_num  as orderRepairNum,l.c_express_price as expressPrice,l.c_settlement_type as settlementType,r.c_is_extract AS shopConsume,o.c_num as Num,s.c_name as goodsName,r.c_repair_reason as repqirContext,q.c_city as orderCity,q.c_customer_name as orderCustomerName,q.c_order_character as orderCharacter,r.c_order_character as orderRepairCharacter,o.c_goods_sn as goodsSn,h.c_color_name as goodsColor,t.c_order_repair_id  as logisticOrderRepaorId,t.c_order_detail_id as logisticOrderDetailId FROM t_logistic l LEFT JOIN t_logistic_order t ON l.c_id=t.c_logistic_id LEFT JOIN t_order_detail o ON o.c_id=t.c_order_detail_id LEFT JOIN t_order_repair r ON r.c_id=t.c_order_repair_id LEFT JOIN t_order q ON q.c_id = o.c_order_id LEFT JOIN t_goods_detail h ON o.c_goods_detail_id=h.c_id  LEFT JOIN t_goods s ON h.c_goods_id=s.c_id LEFT JOIN t_xiuyu_customer z ON q.c_customer_id= z.c_id WHERE l.c_delete_flag='0' ");
		if(dto.getLogisticType()!=null) {
			switch(dto.getLogisticType()){
			case "1": sql.append(" AND l.c_logistic_type = '1' ");break;
			case "0": sql.append(" AND l.c_logistic_type = '0' ");
			if(dto.getVoucherType().equals("1")){
				sql.append(" AND  t.c_order_repair_id is not null");
			}
			else{
				sql.append(" AND t.c_order_detail_id is not null");
			}
			break;
			}
		}
		if(dto.getOrderCode()!=null&&!dto.getOrderCode().isEmpty()){
			sql.append(" and q.c_order_code like '%").append(dto.getOrderCode()).append("%' ");
			
		}
		if(dto.getCustomerName()!=null&&!dto.getCustomerName().isEmpty()){
			sql.append(" and q.c_customer_name like '%").append(dto.getCustomerName()).append("%' ");
		}
		if(dto.getOrderRepairCode()!=null && !dto.getOrderRepairCode().isEmpty()) {
			sql.append(" and r.c_order_repair_code like '%").append(dto.getOrderRepairCode()).append("%' ");
		}
		if(dto.getShopName()!=null && !dto.getShopName().isEmpty()) {
			sql.append(" and l.c_shop_name like '%").append(dto.getShopName()).append("%' ");
		}
		if(dto.getExpressCompany()!=null && !dto.getExpressCompany().isEmpty()) {
			sql.append(" and l.c_express_company like '%").append(dto.getExpressCompany()).append("%' ");
		}
		if(dto.getExpressNumber() != null && !dto.getExpressNumber().isEmpty()){//添加物流单号限定条件
			sql.append(" and l.c_express_number like '%").append(dto.getExpressNumber()).append("%'");
		}
		if(dto.getStartTime() != null  && dto.getEndTime() != null ){//添加同时包含起始发货日期和截止发货日期的限定条件
			switch(dto.getLogisticType()){
			case "1": sql.append(" and l.c_delivery_time between '").append(dto.getStartTime()).append(" 00:00:00'").append(" and '").append(dto.getEndTime()).append(" 23:59:59'");break;
			case "0": sql.append(" and l.c_send_time between '").append(dto.getStartTime()).append(" 00:00:00'").append(" and '").append(dto.getEndTime()).append(" 23:59:59'");break;
			}			
		}
		else if(dto.getStartTime() != null  && (dto.getEndTime() == null )){//添加只包含起始发货日期而不包含截止发货日期的限定条件
			switch(dto.getLogisticType()){
			case "1": sql.append(" and l.c_delivery_time >= '").append(dto.getStartTime()).append(" 00:00:00'");break;
			case "0": sql.append(" and l.c_send_time >= '").append(dto.getStartTime()).append(" 00:00:00'");break;
			}
			
		}
		else if ((dto.getStartTime() == null && dto.getEndTime() != null)) {//添加只包含截止发货日期而不包含起始发货日期的限定条件
			switch(dto.getLogisticType()){
			case "1": sql.append(" and l.c_delivery_time"
					+ " <= '").append(dto.getEndTime()).append(" 23:59:59'");break;
			case "0": sql.append(" and l.c_send_time <= '").append(dto.getEndTime()).append(" 23:59:59'");break;
			}
		}
	
		List resultSet = dao.queryBySql(sql.toString());
			for(Object result : resultSet){
				LogisticExportModel rsModel = new LogisticExportModel();
				Object[] properties = (Object[])result;
				rsModel.setExpressNumber(properties[0]==null ? "" : properties[0].toString());
				rsModel.setExpressCompany(properties[1]==null ? "" : properties[1].toString());
				rsModel.setSendTime(properties[2]==null ? "" : properties[2].toString());
				rsModel.setDeliveryTime(properties[3]==null ? "" : properties[3].toString());
				rsModel.setShopName(properties[4]==null ? "" : properties[4].toString());
				rsModel.setCustomerName(properties[5]==null ? "" : properties[5].toString());
				rsModel.setOrderCode(properties[6]==null ? "" : properties[6].toString());
				rsModel.setOrderRepairCode(properties[7]==null ? "" : properties[7].toString());
				rsModel.setOrderRepairGoodsName(properties[8]==null ? "" : properties[8].toString());
				rsModel.setOrderRepairNum(properties[9]==null ? "" : properties[9].toString());
				rsModel.setExpressPrice(properties[10]==null ? "" : properties[10].toString());
				rsModel.setSettlementType(properties[11]==null ? "" : properties[11].toString());
				rsModel.setShopConsume(properties[12]==null ? "" : properties[12].toString());
				
				rsModel.setNum(properties[13]==null ? "" : properties[13].toString());
				rsModel.setGoodsName(properties[14]==null ? "" : properties[14].toString());
				rsModel.setRepairContext(properties[15]==null ? "" : properties[15].toString());
				rsModel.setOrderRepairCity(properties[16]==null ? "" : properties[16].toString());
				rsModel.setOrderCity(properties[17]==null ? "" : properties[17].toString());
				rsModel.setOrderCustomerName(properties[18]==null ? "" : properties[18].toString());
				rsModel.setOrderCharacter(properties[19]==null ? "" : properties[19].toString());
				rsModel.setOrderRepairCharacter(properties[20]==null ? "" : properties[20].toString());
				rsModel.setGoodsSn(properties[21]==null ? "" : properties[21].toString());
				rsModel.setGoodsColor(properties[22]==null ? "" : properties[22].toString());
				rsModel.setLogisticOrderRepaorId(properties[23]==null ? "" : properties[23].toString());
				rsModel.setLogisticOrderDetailId(properties[24]==null ? "" : properties[24].toString());
				list.add(rsModel);
			}
			
		return list;
	}

	@Override
	public Result exportExcelData(LogisticDto dto, HttpServletRequest request, HttpServletResponse response) {
		
		String excelFileName = "1".equals(dto.getLogisticType()) ? "收货明细查询结果" : dto.getVoucherType() == "1" ? "微调单发货明细查询结果" : "订单发货明细查询结果";
		OutputStream out = null;
		try {
			
			//转码防止乱码
			final String userAgent = request.getHeader("USER-AGENT");
			if(userAgent.toLowerCase().contains("msie")){//IE浏览器  
		    	excelFileName = URLEncoder.encode(excelFileName,"UTF8");  
		    }else if(userAgent.toLowerCase().contains( "mozilla") || userAgent.toLowerCase().contains("chrom")){//google浏览器,火狐浏览器  
		    	excelFileName = new String(excelFileName.getBytes(), "ISO8859-1");  
		    }else{  
		    	excelFileName = URLEncoder.encode(excelFileName,"UTF8");//其他浏览器  
		    } 			
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.addHeader("content-Disposition", "attachment;filename="+ excelFileName +".xls");
			response.flushBuffer();
			List<LogisticExportModel> resultList = queryLogisticWithParam1(dto);
			String[] headers = null;
			Set<String> excludedFieldSet = new HashSet<String>();
			excludedFieldSet.add("serialVersionUID");
			switch(dto.getLogisticType()){
				case "1":
					headers =new String[]{"物流单号","物流公司","收货日期","发货门店","顾客姓名","微调单号","商品名称","数量","物流费","结算类型","顾客提取状态","微调内容"};	
					excludedFieldSet.add("sendTime");//不导出发货日期
					excludedFieldSet.add("orderRepairCity");
					excludedFieldSet.add("orderCode");//不导出订单单号
					excludedFieldSet.add("Num");//不导出微调单中的订单性质
					excludedFieldSet.add("goodsName");//不导出订单城市
					excludedFieldSet.add("orderCity");
					excludedFieldSet.add("orderCustomerName");
					excludedFieldSet.add("orderRepairCharacter");
					excludedFieldSet.add("goodsSn");
					excludedFieldSet.add("goodsColor");
					excludedFieldSet.add("orderCharacter");
					excludedFieldSet.add("logisticOrderRepaorId");
					excludedFieldSet.add("logisticOrderDetailId");
					break;
				case "0": 
					switch(dto.getVoucherType()){
					case "1"://微调单物流信息导出
						headers =new String[]{"物流单号","物流公司","发货日期","收货日期","城市","收货门店","顾客姓名","微调单号","商品名称","数量","物流费","结算类型","顾客提取状态","微调内容","订单性质"};
						excludedFieldSet.add("orderCode");//不导出订单单号
						excludedFieldSet.add("Num");
						excludedFieldSet.add("goodsName");
						excludedFieldSet.add("orderCity");//不导出订单城市
						excludedFieldSet.add("orderCustomerName");
						excludedFieldSet.add("logisticOrderRepaorId");
						excludedFieldSet.add("logisticOrderDetailId");
						excludedFieldSet.add("goodsColor");
						excludedFieldSet.add("goodsSn");
						excludedFieldSet.add("orderRepairCharacter");
						break;
					case "2"://订单物流信息导出
						headers =new String[]{"物流单号","物流公司","发货日期","城市","收货门店","顾客姓名","订单单号","商品名称","数量","物流费","结算类型","订单性质"};
						excludedFieldSet.add("deliveryTime");//不导出收货日期
						excludedFieldSet.add("orderRepairCode");//不导出微调单号
						excludedFieldSet.add("shopConsume");//不导出顾客提取状态
						excludedFieldSet.add("orderCity");//不导出订单城市
						excludedFieldSet.add("goodsColor");
						excludedFieldSet.add("goodsSn");
						excludedFieldSet.add("goodsName");
						excludedFieldSet.add("orderCustomerName");
						excludedFieldSet.add("Num");
						excludedFieldSet.add("repairContext");
						excludedFieldSet.add("orderRepairCharacter");
						excludedFieldSet.add("logisticOrderRepaorId");
						excludedFieldSet.add("logisticOrderDetailId");
						break;
					}					
					break;
			}			
			 out = response.getOutputStream();  
			ExportExcelUtil.exportExcel(0,0,0,dto.getLogisticType().equals("1") ? "收货明细" : "发货明细", headers,ExportExcelUtil.buildCustomizedExportedModel(resultList, excludedFieldSet) , out, "yyyy-MM-dd");			
		} catch (IOException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (out != null) out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new Result();
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public Result rollBackCheckStatus(String checkUserId,String id, String logisticType) {
		Criteria criteria = dao.createCriteria(Logistic.class);
		criteria.add(Restrictions.eq("id",id));
		List<Logistic> list = criteria.list();
		Boolean checkStatus = true;
		String logistictype = null;
		if(logisticType!=null) {
			if(logisticType.equals("0")){
				logistictype="发货";
			}else if(logisticType .equals("1")){
				logistictype="收货";
			}
		}
		switch(logistictype) {
		case"收货":
			for (Logistic logistic:list){
				Boolean status =rollBackOrderRepairStatus(logistic);
				 if (status == false){
				 	checkStatus = false;
					return new Result("500","该收货单中微调单的状态不是已收货，不能取消审核");
				 }
				logistic.setCheckStatus("0");
				logistic.setCheckTime(new Date());
				logistic.setCheckUserId(checkUserId);
				//记录取消审核物流单
		/*		 HistoryRecord historyRecord =HistoryRecordUtil.historyRecord(logistic.getId(), "Logistic", new Date(),user.getUserName()+"取消审核收货物流单:"+logistic.getExpressNumber(),JSON.toJSONString(logistic), user.getId());
				 dao.save(historyRecord);*/
				 			}
			break;
		case"发货":
			for (Logistic logistic: list){
				logistic.setCheckStatus("0");
				logistic.setCheckTime(new Date());
				logistic.setCheckUserId(checkUserId);
				//更新发货时间为空
				logistic.setSendTime(null);
				//记录取消审核物流单
	/*			 HistoryRecord historyRecord =HistoryRecordUtil.historyRecord(logistic.getId(), "Logistic", new Date(),user.getUserName()+"取消审核发货物流单:"+logistic.getExpressNumber(),JSON.toJSONString(logistic), user.getId());
				 dao.save(historyRecord);*/
				rollBackDeliveryStatus(logistic);
			}
			break;
		}
		return new Result();
	}
	 //回滚收货单微调单的状态为星域审核通过
    private Boolean rollBackOrderRepairStatus(Logistic logistic){
        List<String> orderRepairIdList = new ArrayList<String>();
        //如果物流单信息中包含微调单信息
        if (logistic.getList()!=null){
        	Boolean checkStatus = true;
            for (LogisticOrder logistic1:logistic.getList()){
				if (logistic1.getOrderRepair() != null) {
					OrderRepair orderRepair=dao.findById(OrderRepair.class, logistic1.getOrderRepair().getId());
					if (!"8".equals(orderRepair.getOrderRepairStatus())){
						checkStatus = false;
						break;
					}
					orderRepairIdList.add(orderRepair.getId());
				}
			}
			if (checkStatus == false){
            	return checkStatus;
			}
            Criteria criteria = dao.createCriteria(OrderRepair.class);
            criteria.add(Restrictions.in("id",orderRepairIdList));
            @SuppressWarnings("unchecked")
            List<OrderRepair> orderRepairList = criteria.list();
            for (OrderRepair orderRepair :orderRepairList){
                orderRepair.setOrderRepairStatus("9");
                //记录收货物流单取消审核时微调单的状态变化
            /*    HistoryRecord historyRecord =HistoryRecordUtil.historyRecord(orderRepair.getId(), "OrderRepair", new Date(),user.getUserName()+"取消审核物流单中的微调单:"+orderRepair.getRepairCode(),JSON.toJSONString(orderRepair), user.getId());
    			dao.save(historyRecord);*/
            }
            return checkStatus;
        }
        return true;
    }
	 //回滚发货单中订单以及微调单的状态为生产完成状态
    private void rollBackDeliveryStatus(Logistic logistic){
        List<String> orderRepairIdList = new ArrayList<String>();
        List<String> orderDetialIdList = new ArrayList<String>();
       
        if (logistic.getList()!=null){
			for (LogisticOrder logisticOrder:logistic.getList()){
            	if (logisticOrder != null){
            		if(logisticOrder.getOrderRepair()!=null){
            			if(logisticOrder.getOrderRepair().getId()!=null){
                			OrderRepair orderRepair	=dao.findById(OrderRepair.class, logisticOrder.getOrderRepair().getId());
                   		 if (orderRepair!= null) {
                   			 if(StringUtils.isNotBlank(orderRepair.getId())){
                   				 orderRepairIdList.add(orderRepair.getId());
                   			 }
                            }
                		}
            		}
            		if(logisticOrder.getOrderDetail()!=null){
            			if(logisticOrder.getOrderDetail().getId()!=null){
                			OrderDetail orderDetail	=dao.findById(OrderDetail.class, logisticOrder.getOrderDetail().getId());
                            if (orderDetail!= null) {
                           	 if(StringUtils.isNotBlank(orderDetail.getId())){
                                orderDetialIdList.add(orderDetail.getId());
                           	 }
                            }
            			}
            		}
            		
            	} 
            }
			if (orderRepairIdList.size() > 0) {
				Criteria criteria1 = dao.createCriteria(OrderRepair.class);
				criteria1.add(Restrictions.in("id",orderRepairIdList));
				@SuppressWarnings("unchecked")
				List<OrderRepair> orderRepairList = criteria1.list();
				for (OrderRepair orderRepair : orderRepairList){
                    orderRepair.setOrderRepairStatus("10");
                }
			}
			if (orderDetialIdList.size() > 0){
				Criteria criteria2 = dao.createCriteria(OrderDetail.class);
				criteria2.add(Restrictions.in("id",orderDetialIdList));
				@SuppressWarnings("unchecked")
				List<OrderDetail> orderDetailList = criteria2.list();
				for (OrderDetail orderDetail:orderDetailList){
					orderDetail.setOrderDetailStatus("9");
				}
			}
			}
		}

	@Override
	public Result updateLogisticStatus(String logisticId, String logisticStatus) {
		Logistic logistic = dao.findById(Logistic.class, logisticId);
		logistic.setLogisticStatus(logisticStatus);
		logistic.setDeliveryTime(new Date());
		return new Result(logistic);
	}

	@Override
	public Result selectLogisticOne(String logisticId) {
		Logistic logistic = dao.findById(Logistic.class, logisticId);
		return new Result(logistic);
	}

	@Override
	public List<OrderRepair> queryOrderRepair(String q,String logisticType) {
		Criteria criteria = dao.createCriteria(OrderRepair.class);
		Disjunction disjunction = Restrictions.disjunction();
		if(!StringUtils.isBlank(q)){
			disjunction .add(Restrictions.like("orderRepairCode", q, MatchMode.ANYWHERE));
		}
		if(!StringUtils.isBlank(logisticType)){
			switch (logisticType) {
			case "1":
				criteria.add(Restrictions.eq("orderRepairStatus", "2"));
				break;
			case "0":
				criteria.add(Restrictions.eq("orderRepairStatus", "6"));
				break;
			default:
				break;
			}
		}
		criteria.add(disjunction);
		PageBean pageBean = new PageBean();
		pageBean.setPage(0);
		pageBean.setRows(30);
		return dao.findListWithPagebeanCriteria(criteria, pageBean);
	}

	@Override
	public List<Dept> queryTenant(String q,String tenantId) {
		Criteria criteria = dao.createCriteria(Dept.class);
		Tenant tenant = dao.findById(Tenant.class, tenantId);
		criteria.add(Restrictions.eq("deptTenantId", tenant));
		@SuppressWarnings("unchecked")
		List<Dept> list = criteria.list();
		if(q!=null && !"".equals(q)){
			for( int i=0;i<list.size();i++){
				criteria.add(Restrictions.like("deptName", q,MatchMode.ANYWHERE));
				criteria.add(Restrictions.eq("deptType", "1"));
			}
		}
		/*Disjunction disjunction = Restrictions.disjunction();
		if(!StringUtils.isBlank(q)){
			disjunction .add(Restrictions.eq("deptType","1")).add(Restrictions.like("deptName", q, MatchMode.ANYWHERE));
		}
		criteria.add(disjunction);*/
		PageBean pageBean = new PageBean();
		pageBean.setPage(0);
		pageBean.setRows(30);
		return dao.findListWithPagebeanCriteria(criteria, pageBean);
	}

	@Override
	public List<XiuyuCustomer> queryXiuyuCustomer(String q) {
		Criteria criteria = dao.createCriteria(XiuyuCustomer.class);
		Disjunction disjunction = Restrictions.disjunction();
		if(!StringUtils.isBlank(q)){
			disjunction .add(Restrictions.like("customerName", q, MatchMode.ANYWHERE));
		}
		criteria.add(disjunction);
		PageBean pageBean = new PageBean();
		pageBean.setPage(0);
		pageBean.setRows(30);
		return dao.findListWithPagebeanCriteria(criteria, pageBean);
	}

	@Override
	public List<Order> queryOrder(String q) {
		Criteria criteria = dao.createCriteria(Order.class);
		criteria.add(Restrictions.eq("deleteFlag", "0"));
		Disjunction disjunction = Restrictions.disjunction();
		if(!StringUtils.isBlank(q)){
			disjunction .add(Restrictions.like("orderCode", q, MatchMode.ANYWHERE));
		}
		criteria.add(disjunction);
		PageBean pageBean = new PageBean();
		pageBean.setPage(0);
		pageBean.setRows(30);
		return dao.findListWithPagebeanCriteria(criteria, pageBean);
	}
	
	
	@Override
	public Result getLogistic(String id){
		LogisticModel model = new LogisticModel();
		Logistic a = dao.findById(Logistic.class, id);
		try {
			BeanUtils.copyProperties(model, a);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		Criteria c = dao.createCriteria(LogisticOrder.class);
		c.add(Restrictions.eq("logisticId", id));
		List<LogisticOrder> list = c.list();
		List<LogisticOrderDetailModel> modelList = new ArrayList<>();
		//0定制订单  1微调单
		
		for (int i = 0; i < list.size(); i++) {
			LogisticOrderDetailModel modell = new LogisticOrderDetailModel();
			OrderDetailModel fsd= new OrderDetailModel();
			OrderRepairModel fsdo= new OrderRepairModel();
			OrderDetail o = null;
			Order oreder =null;
			OrderRepair oo = null;
			if(list.get(i).getOrderDetailId()!=null){
				 o = dao.findById(OrderDetail.class, list.get(i).getOrderDetailId());
			 oreder = dao.findById(Order.class, o.getOrderId());
			}   
			if(list.get(i).getOrderRepairId()!=null){
				oo = dao.findById(OrderRepair.class, list.get(i).getOrderRepairId());
			}
			try {
				if(o!=null){
					BeanUtils.copyProperties(fsd, o);
					if(oreder!=null){
						if(oreder.getCustomerName()!=null){
							fsd.setCustomerName(oreder.getCustomerName());
						}
						if(oreder.getShopName()!=null){
							fsd.setShopName(oreder.getShopName());
						}
						if(oreder.getShopId()!=null){
							fsd.setShopId(oreder.getShopId());
						}
						if(oreder.getOrderCode()!=null){
							fsd.setOrderCode(oreder.getOrderCode());
						}
					}
					modell.setOrderDetailModel(fsd);
				}
				if(oo!=null){
					BeanUtils.copyProperties(fsdo, oo);
					if(oo.getOrderDetailId()!=null){
						OrderDetail orderDetail = dao.findById(OrderDetail.class,oo.getOrderDetailId());
						if(orderDetail!=null && StringUtils.isNotBlank(orderDetail.getGoodsSn())){
							fsdo.setGoodsSn(orderDetail.getGoodsSn());
						}
					}
					modell.setOrderRepairModel(fsdo);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			
			modelList.add(modell);
		}
		model.setDetailList(modelList); 
		return new Result(model);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public	List<OrderDetailSearchModel> queryDetailWithParam(OrderDetailSearchDto odsDto,PageBean pageBean){
		List<OrderDetailSearchModel> odsModelList = new ArrayList<OrderDetailSearchModel>();
		StringBuilder sql = new StringBuilder("SELECT a.c_id as orderId,a.c_order_code,b.c_erp_no,b.c_goods_sn,a.c_city,a.c_shop_name,a.c_order_character,a.c_customer_name,b.c_goods_name,b.c_goods_color_name,b.c_num,a.c_status_bussiness,b.c_order_detail_status,a.c_create_time,c.c_materiel_code,b.c_closed_status,d.c_code,b.c_id FROM t_order_detail b LEFT JOIN t_order a ON a.c_id=b.c_order_id LEFT JOIN t_goods_detail c ON c.c_id=b.c_goods_detail_id LEFT JOIN t_goods d ON d.c_id=c.c_goods_id  where a.c_delete_flag='0'");
		if(odsDto.getOrderDetailStatus() != null && !odsDto.getOrderDetailStatus().isEmpty()){
			sql.append(" and b.c_order_detail_status = '").append(odsDto.getOrderDetailStatus()).append("'");
		}
		if(odsDto.getOrderCode() != null && !odsDto.getOrderCode().isEmpty()){
			sql.append(" and a.c_order_code like '%").append(odsDto.getOrderCode()).append("%'");
		}
		if(odsDto.getCustomerName() != null && !odsDto.getCustomerName().isEmpty()){
			sql.append(" and a.c_customer_name like '%").append(odsDto.getCustomerName()).append("%'");
		}
		if(odsDto.getShopName() != null && !odsDto.getShopName().isEmpty()){
			sql.append(" and a.c_shop_name like '%").append(odsDto.getShopName()).append("%'");
		}
		if(StringUtils.isNotBlank(odsDto.getGoodsSn())){
			sql.append(" and b.c_goods_sn like '%").append(odsDto.getGoodsSn()).append("%'");
		}
		if(odsDto.getIds() != null && odsDto.getIds().length>0){
			for(int i=0;i<odsDto.getIds().length;i++){
				sql.append(" and b.c_id != '").append(odsDto.getIds()[i]).append("'");
			}
		}
		if(pageBean.getPage()!=null && pageBean.getRows()!=null){
			sql.append(" limit "+(pageBean.getPage() - 1) * pageBean.getRows()+","+pageBean.getRows());
		}
		List resultSet = dao.queryBySql(sql.toString());
		for(Object result : resultSet){
			OrderDetailSearchModel odsModel = new OrderDetailSearchModel();//构建返回数据模型
			Object[] properties = (Object[])result;
			odsModel.setOrderId(properties[0]==null ? "" : properties[0].toString());
			odsModel.setOrderCode(properties[1]==null ? "" : properties[1].toString());
			odsModel.setErpNum(properties[2]==null ? "" : properties[2].toString());
			odsModel.setGoodsSN(properties[3]==null ? "" : properties[3].toString());
			odsModel.setCity(properties[4]==null ? "" : properties[4].toString());
			odsModel.setShopName(properties[5]==null ? "" : properties[5].toString());
			odsModel.setCharacteres(properties[6]==null ? "" : properties[6].toString());
			odsModel.setCustomerName(properties[7]==null ? "" : properties[7].toString());
			odsModel.setGoodsName(properties[8]==null ? "" : properties[8].toString());
			odsModel.setGoodsColor(properties[9]==null ? "" : properties[9].toString());
			odsModel.setNum(properties[10]==null ? "" : properties[10].toString());
			odsModel.setBillStatus(properties[11]==null ? "" : properties[11].toString());
			odsModel.setOrderDetailStatus(properties[12]==null ? "" : properties[12].toString());
			odsModel.setCreateTime(properties[13]==null ? "" : properties[13].toString());
			odsModel.setMaterielCode(properties[14]==null ? "" : properties[14].toString());
			odsModel.setClosedStatus(properties[15]==null ? "" : changeClosedStatus(properties[15].toString()));
			odsModel.setGoodsCode(properties[16]==null ? "" : properties[16].toString());
			odsModel.setOrderDetailId(properties[17]==null ? "" : properties[17].toString());
			odsModelList.add(odsModel); 
		}
		return 	odsModelList;
	}
	
	@Override
	public Long queryCountWithParam(OrderDetailSearchDto odsDto) throws ParseException{
		StringBuilder sql = new StringBuilder("SELECT count(*) FROM t_order_detail b LEFT JOIN t_order a ON b.c_order_id=a.c_id LEFT JOIN t_goods_detail c ON c.c_id=b.c_goods_detail_id LEFT JOIN t_goods d ON d.c_id=c.c_goods_id LEFT JOIN t_category g on d.c_category_id = g.c_id  where a.c_delete_flag='0'");
		if(odsDto.getOrderCode() != null && !odsDto.getOrderCode().isEmpty()){
			sql.append(" and a.c_order_code like '%").append(odsDto.getOrderCode()).append("%'");
		}
		if(odsDto.getCustomerName() != null && !odsDto.getCustomerName().isEmpty()){
			sql.append(" and a.c_customer_name like '%").append(odsDto.getCustomerName()).append("%'");
		}
		if(odsDto.getShopName() != null && !odsDto.getShopName().isEmpty()){
			sql.append(" and a.c_shop_name like '%").append(odsDto.getShopName()).append("%'");
		}
		if(StringUtils.isNotBlank(odsDto.getGoodsSn())){ 
			sql.append(" and b.c_goods_sn like '%").append(odsDto.getGoodsSn()).append("%'");
		}
		if(odsDto.getOrderDetailStatus() != null && !odsDto.getOrderDetailStatus().isEmpty()){
			sql.append(" and b.c_order_detail_status = '").append(odsDto.getOrderDetailStatus()).append("'");
		}
		if(odsDto.getIds() != null && odsDto.getIds().length>0){
			for(int i=0;i<odsDto.getIds().length;i++){
				sql.append(" and b.c_id != '").append(odsDto.getIds()[i]).append("'");
			}
		}
		List<BigInteger> result = dao.queryBySql(sql.toString());
		return result == null || result.isEmpty() ? 0L : result.get(0).longValue();
	}
	public String changeOrderStatus(String a){
		String value= "";
		if("0".equals(a)){
			value = "未送出";
		}else if("1".equals(a)){
			value = "已送出";
		}else if("2".equals(a)){
			value = "已提交星域";
		}else if("3".equals(a)){
			value = "星域审核通过";
		}else if("4".equals(a)){
			value = "星域驳回";
		}else if("5".equals(a)){
			value = "秀域驳回";
		}else if("6".equals(a)){
			value = "生产完成";
		}else if("7".equals(a)){
			value = "已发货";
		}else if("8".equals(a)){
			value = "已收货";
		}
		return value;
	}
	
	public String changeOrderDetailStatus(String a){
		String value= "";
		if("0".equals(a)){
			value = "已排程";
		}else if("1".equals(a)){
			value = "生产中";
		}else if("2".equals(a)){
			value = "生产完成";
		}else if("3".equals(a)){
			value = "已发货";
		}else if("4".equals(a)){
			value = "已收货";
		}else if("5".equals(a)){
			value = "已反馈";
		}
		return value;
	}
	
	public String changeClosedStatus(String a){
		String value= "";
		if("1".equals(a)){
			value = "未提交";
		}else if("2".equals(a)){
			value = "已提交";
		}else if("3".equals(a)){
			value = "星域审核通过";
		}else if("4".equals(a)){
			value = "星域驳回";
		}
		return value;
	}
	@Override
	public Pagination<OrderRepairModel> list(OrderRepairDto dto, PageBean pageBean) {
		Pagination<OrderRepairModel> pagination = new Pagination<OrderRepairModel>();
		if (pageBean.getPage() == null) {
			pageBean.setPage(1);
		}
		if (pageBean.getRows() == null) {
			pageBean.setRows(9999);
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select "
				+ "orderrepair.c_id,orderrepair.c_order_repair_code,orderrepair.c_customer_name,orderrepair.c_order_character, "
				+ "orderrepair.c_customer_code, " + "orderrepair.c_city, " + "orderrepair.c_shop_name, "
				+ "orderrepair.c_goods_name, " + "orderrepair.c_order_repair_status,orderrepair.c_repair_person, "
				+ "orderrepair.c_is_extract,orderrepair.c_check_time,"
				+ "orderrepair.c_goods_code, " + "orderrepair.c_goods_color, " + "orderrepair.c_num, "
				+ "orderrepair.c_solution, " + "orderdetail.c_goods_sn "
				+ ",orderrepair.c_extended_file_name ,orderrepair.c_description   from t_order_repair orderrepair "
//				+ "left join  (select max(c_check_time) c_check_time,c_order_repair_id from t_order_repair_check  group by  c_order_repair_id ) repaircheck on orderrepair.c_id=repaircheck.c_order_repair_id  "
				+ "left join   t_order_detail  orderdetail on orderrepair.c_order_detail_id = orderdetail.c_id where orderrepair.c_del ='0' ");
		if (StringUtils.isNotBlank(dto.getOrderRepairCode())) {
			sql.append(" and orderrepair.c_order_repair_code ='" + dto.getOrderRepairCode() + "' ");
		}
		if (StringUtils.isNotBlank(dto.getCustomerInfo())) {
			sql.append(" and ((orderrepair.c_customer_name like '%" + dto.getCustomerInfo()
					+ "%')  or  (orderrepair.c_customer_code like '%" + dto.getCustomerInfo() + "%')) ");
		}
		
		if (StringUtils.isNotBlank(dto.getShopName())) {
			sql.append(" and orderrepair.c_shop_name like '%" + dto.getShopName() + "%' ");
		}
		
		if (StringUtils.isNotBlank(dto.getOrderRepairStatus())) {
			sql.append(" and orderrepair.c_order_repair_status ='" + dto.getOrderRepairStatus() + "' ");
		}
		if(dto.getIds() != null && dto.getIds().length>0){
			for(int i=0;i<dto.getIds().length;i++){
				sql.append(" and orderrepair.c_id != '").append(dto.getIds()[i]).append("'");
			}
		}
		int total = dao.queryBySql(sql.toString()).size();
		pagination.setTotal(total);
		if (pageBean.getPage() != null && pageBean.getRows() != null) {
			sql.append("  limit " + (pageBean.getPage() - 1) * pageBean.getRows() + "," + pageBean.getRows());
		}
		List<OrderRepairModel> lrbm = new ArrayList<OrderRepairModel>();
		List resultSet = dao.queryBySql(sql.toString());
		if (resultSet != null && resultSet.size() > 0) {
			for (Object result : resultSet) {
				OrderRepairModel rbm = new OrderRepairModel();
				Object[] properties = (Object[]) result;
				rbm.setId(properties[0] == null ? "" : properties[0].toString());
				rbm.setOrderRepairCode(properties[1] == null ? "" : properties[1].toString());
				rbm.setCustomerName(properties[2] == null ? "" : properties[2].toString());
				rbm.setOrderCharacter(properties[3] == null ? "" : properties[3].toString());
				rbm.setCustomerCode(properties[4] == null ? "" : properties[4].toString());
				rbm.setCity(properties[5] == null ? "" : properties[5].toString());
				rbm.setShopName(properties[6] == null ? "" : properties[6].toString());
				rbm.setGoodsName(properties[7] == null ? "" : properties[7].toString());
				rbm.setOrderRepairStatus(properties[8] == null ? "" : properties[8].toString());
				rbm.setRepairPerson(properties[9] == null ? "" : properties[9].toString());
				rbm.setIsExtract(properties[10] == null ? "" : properties[10].toString());
				rbm.setCheckTime(properties[11] == null ? "" : properties[11].toString());
				rbm.setGoodsCode(properties[12] == null ? "" : properties[12].toString());
				rbm.setGoodsColor(properties[13] == null ? "" : properties[13].toString());
				rbm.setNum(properties[14] == null ? "" : properties[14].toString());
				rbm.setSolution(properties[15] == null ? "" : properties[15].toString());
				rbm.setGoodsSn(properties[16] == null ? "" : properties[16].toString());
				rbm.setExtendedFileName(properties[17] == null ? "" : properties[17].toString());
				rbm.setDescription(properties[18] == null ? "" : properties[18].toString());
				lrbm.add(rbm);
			}
		}
		pagination.setRows(lrbm);

		return pagination;
	}

	@Override
	public Pagination<OrderPlanDetailModel> getFinishedOrderRepair(PlanOrderDetailDto dto, PageBean pageBean) {
		Pagination<OrderPlanDetailModel> pagination = new Pagination<OrderPlanDetailModel>();


		Double total = 0.00;
		BigDecimal rate ;
		Double quantity =0.00;
		Double repariQuantity =0.00;

		StringBuilder sqlFrom        = new StringBuilder();
		StringBuilder sqlFromFlag    = new StringBuilder();
		StringBuilder sqlFromNotFlag = new StringBuilder();
		StringBuilder sqlFrom2       = new StringBuilder();
		StringBuilder sqlCount       = new StringBuilder();
		StringBuilder sqlWhere       = new StringBuilder();
		StringBuilder sqlSelect      = new StringBuilder();
		StringBuilder sqlLimit       = new StringBuilder();

		StringBuilder sqlBXSSelect   = new StringBuilder();
		StringBuilder sqlBXSGroup    = new StringBuilder();

		StringBuilder sqlKSFLSelect   = new StringBuilder();
		StringBuilder sqlKSFLGroup    = new StringBuilder();

		StringBuilder sqlSCGCSelect   = new StringBuilder();
		StringBuilder sqlSCGCGroup    = new StringBuilder();

		StringBuilder sqlCBFSelect   = new StringBuilder();
		StringBuilder sqlCBFGroup    = new StringBuilder();

		StringBuilder sqlFHSelect = new StringBuilder();
		StringBuilder sqlGroup   = new StringBuilder();

		sqlFHSelect.append(" SELECT g.c_name,count( b.c_order_detail_id) as fhsl,count( k.c_id) as sl ");
		sqlGroup.append(" GROUP BY g.c_name");

		sqlBXSSelect.append(" SELECT j.c_technician_name,count( k.c_id) as sl ");
		sqlBXSGroup.append(" GROUP BY j.c_technician_name");

		sqlKSFLSelect.append(" SELECT g.c_name,count( k.c_id) as sl ");
		sqlKSFLGroup.append(" GROUP BY g.c_name");
		sqlSCGCSelect.append(" SELECT `i`.`c_prod_factory_id`,count( k.c_id) as sl ");
		sqlSCGCGroup.append(" GROUP BY `i`.`c_prod_factory_id` ");

		sqlCBFSelect.append(" SELECT (CASE WHEN (`i`.`publishers` = '1') THEN '青岛星域' WHEN (`i`.`publishers` = '2') THEN '台湾秀妮儿' ELSE '其它' END) AS `publishers`,count( k.c_id) as sl ");
		sqlCBFGroup.append(" GROUP BY `i`.`publishers` ");

		if (pageBean.getPage() != null &&pageBean.getRows() != null) {
			sqlLimit.append(" LIMIT ").append(pageBean.getPage()).append(",").append(pageBean.getRows());
		}



		sqlCount.append("select count(1) ");
		sqlSelect.append(" SELECT `a`.`c_send_time` AS `c_send_time`,`d`.`c_order_code` AS `c_order_code`,`c`.`c_goods_sn` AS `c_goods_sn`," +
				" `d`.`c_customer_name` AS `c_customer_name`,`f`.`c_code` AS `c_goods_code`,`f`.`c_name` AS `c_goods_name`,`g`.`c_name` AS `category_name`," +
				" `d`.`c_order_character` AS `c_order_character`,`d`.`c_shop_name` AS `c_shop_name`,`i`.`c_prod_factory_id` AS `c_prod_factory_id`," +
				" (CASE WHEN (`i`.`publishers` = '1') THEN '青岛星域' WHEN (`i`.`publishers` = '2') THEN '台湾秀妮儿' ELSE '其它' END) AS `publishers`,`j`.`c_technician_name` AS `c_technician_name`," +
				" `k`.`c_order_repair_code` AS `c_order_repair_code`,`k`.`c_check_time` AS `c_check_time`,IFNULL(TO_DAYS(`k`.`c_check_time`) - TO_DAYS(`a`.`c_send_time`),0) AS `days`,`l`.`c_repair_feedback`" );
		sqlFrom.append(" FROM (((((((((((`t_logistic` `a`" +
				" JOIN `t_logistic_order` `b` ON (`a`.`c_id` = `b`.`c_logistic_id`))" +
				" JOIN `t_order_detail` `c` ON (`b`.`c_order_detail_id` = `c`.`c_id`))" +
				" JOIN `t_order` `d` ON (`c`.`c_order_id` = `d`.`c_id`))" +
				" JOIN `t_goods_detail` `e` ON (`c`.`c_goods_detail_id` = `e`.`c_id`))" +
				" JOIN `t_goods` `f` ON (`e`.`c_goods_id` = `f`.`c_id`))" +
				" JOIN `t_category` `g` ON (`f`.`c_category_id` = `g`.`c_id`))" +
				" JOIN `mes_order_plan_detail` `h` ON ((`c`.`c_id` = `h`.`c_order_detail_id`) AND (`h`.`c_delete_flag` = 0)))" +
				" JOIN `mes_order_plan` `i` ON ((`h`.`c_order_plan_id` = `i`.`c_id`) AND (`i`.`c_delete_flag` = 0)))" +
				" JOIN `mes_order_detail_assign` `j` ON ((`c`.`c_id` = `j`.`c_order_detail_id`) AND (`j`.`c_delete_flag` = 0)))" );

		sqlFromFlag.append("  JOIN `t_order_repair` `k` ON ((`b`.`c_order_detail_id` = `k`.`c_order_detail_id`) AND (`k`.`c_del` = '0') AND (k.c_order_repair_status not in ('1','0','3'))))");
		sqlFromFlag.append(" LEFT JOIN `mes_repair_production_plan_detail` `l` ON (`k`.`c_id` = `l`.`c_order_repair_id`))");
		sqlFromNotFlag.append("  LEFT JOIN `t_order_repair` `k` ON ((`b`.`c_order_detail_id` = `k`.`c_order_detail_id`) AND (`k`.`c_del` = '0') AND (k.c_order_repair_status not in ('1','0','3'))))");
		sqlFromNotFlag.append("  LEFT JOIN `mes_repair_production_plan_detail` `l` ON (`k`.`c_id` = `l`.`c_order_repair_id`))");
		if (StringUtils.isNotBlank(dto.getFlag())){
			sqlFrom2.append(sqlFromFlag);
		} else {
			sqlFrom2.append(sqlFromNotFlag);
		}

		sqlWhere.append(" WHERE (`a`.`c_logistic_type` = '0') AND (`a`.`c_delete_flag` = '0') AND (`b`.`c_delete_flag` = '0') AND (`d`.`c_delete_flag` = '0')" );
		if (StringUtils.isNotBlank(dto.getSendTimeStr())){
			sqlWhere.append(" AND (`a`.`c_send_time` >= '").append(dto.getSendTimeStr()).append("') ");
		}
		if (StringUtils.isNotBlank(dto.getSendTimeEnd())){
			sqlWhere.append(" AND (`a`.`c_send_time` <= '").append(dto.getSendTimeEnd()).append(" 23:59:59') ");
		}

		if (StringUtils.isNotBlank(dto.getProdFactory())){
			sqlWhere.append(" AND (`i`.`c_prod_factory_id` = '").append(dto.getProdFactory()).append("')");
		}

		if (StringUtils.isNotBlank(dto.getPublishers())){
			sqlWhere.append(" AND (`i`.`publishers`='").append(dto.getPublishers()).append("')");
		}
		if (StringUtils.isNotBlank(dto.getTechnicianId())){
			sqlWhere.append(" AND (`j`.`c_technician_id`='").append(dto.getTechnicianId()).append("')");
		}

		if (dto.getDays()!=null){
			sqlWhere.append(" AND (IFNULL(TO_DAYS(`k`.`c_check_time`) - TO_DAYS(`a`.`c_send_time`),0)>=").append(dto.getDays()).append(")");
		}

		//导出不执行该语句
        if (pageBean.getPage() != null &&pageBean.getRows() != null && pageBean.getPage()==1) {
			Map<String, Object> mp = new HashMap<String, Object>();

//			List<Map<String, Object>> orderList = new ArrayList<>();
//			Double fhsl = Double.parseDouble(dao.uniqueBySql(sqlFHSelect.append(sqlFrom).append(sqlFrom2).append(sqlWhere).toString()).toString());
//			Double wtsl = Double.parseDouble(dao.uniqueBySql(sqlWTSelect.append(sqlFrom).append(sqlFromFlag).append(sqlWhere).toString()).toString());
//			if (fhsl != 0) {
//				rate = new BigDecimal((wtsl / fhsl) * 100).setScale(2, BigDecimal.ROUND_HALF_UP);
//			} else {
//				rate = BigDecimal.valueOf(0);
//			}
//			Map<String, Object> m = new HashMap<>();
//			m.put("orderQuantity",fhsl);
//			m.put("repairquantity",wtsl);
//			m.put("rate",rate+"%");
//			orderList.add(m);

//			mp.put("order",orderList);

			List resultFH = dao.queryBySql(sqlFHSelect.append(sqlFrom).append(sqlFrom2).append(sqlWhere).append(sqlGroup).toString());

			List<Map<String, Object>> FHlist = new ArrayList<Map<String, Object>>();
			if (resultFH != null && resultFH.size() > 0) {
				for (Object result : resultFH) {
					Object[] properties = (Object[]) result;
					Map<String, Object> map = new HashMap<>();
					map.put("categoryName", properties[0] == null ? "其它" : properties[0]);
					quantity = Double.parseDouble(properties[1] == null ? "0" : properties[1].toString());
					repariQuantity = Double.parseDouble(properties[2] == null ? "0" : properties[2].toString());
					if (quantity != 0 ){
						rate = new BigDecimal((repariQuantity/quantity) * 100).setScale(2, BigDecimal.ROUND_HALF_UP);
					}else{
						rate = BigDecimal.valueOf(0);
					}
					map.put("orderQuantity", properties[1] == null ? "0" : properties[1]);
					map.put("repairQuantity", properties[2] == null ? "0" : properties[2]);
					map.put("rate", rate + "%");
					FHlist.add(map);
				}
			}

			mp.put("order",FHlist);
			//版型师
			List resultBXS = dao.queryBySql(sqlBXSSelect.append(sqlFrom).append(sqlFromFlag).append(sqlWhere).append(sqlBXSGroup).toString());
			List<Map<String, Object>> BXSlist = new ArrayList<Map<String, Object>>();
			total = 0.00;
			if (resultBXS != null && resultBXS.size() > 0) {
				for (Object result : resultBXS) {
					Object[] properties = (Object[]) result;
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("technicianName", properties[0] == null ? "其它" : properties[0]);
					quantity = Double.parseDouble(properties[1] == null ? "0" : properties[1].toString());
					map.put("quantity", properties[1] == null ? "0" : properties[1]);
					total += quantity;
					BXSlist.add(map);
				}
				for (Map<String, Object> map : BXSlist) {
					quantity = Double.parseDouble(map.get("quantity").toString());
					if (total != 0) {
						rate = new BigDecimal((quantity / total) * 100).setScale(2, BigDecimal.ROUND_HALF_UP);
					} else {
						rate = BigDecimal.valueOf(0);
					}
					map.put("rate", rate + "%");
				}
			}

			mp.put("technician",BXSlist);
			//款式分类

			List resultKSFL = dao.queryBySql(sqlKSFLSelect.append(sqlFrom).append(sqlFromFlag).append(sqlWhere).append(sqlKSFLGroup).toString());
			List<Map<String, Object>> KSFLlist = new ArrayList<Map<String, Object>>();
			total = 0.00;
			if (resultKSFL != null && resultKSFL.size() > 0) {
				for (Object result : resultKSFL) {
					Object[] properties = (Object[]) result;
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("categoryName", properties[0] == null ? "其它" : properties[0]);
					quantity = Double.parseDouble(properties[1] == null ? "0" : properties[1].toString());
					map.put("quantity", properties[1] == null ? "0" : properties[1]);
					total += quantity;
					KSFLlist.add(map);
				}
				for (Map<String, Object> map : KSFLlist) {
					quantity = Double.parseDouble(map.get("quantity").toString());
					if (total != 0) {
						rate = new BigDecimal((quantity / total) * 100).setScale(2, BigDecimal.ROUND_HALF_UP);
					} else {
						rate = BigDecimal.valueOf(0);
					}
					map.put("rate", rate + "%");
				}
			}
            mp.put("category",KSFLlist);
			//生产工厂
			List resultSCGC = dao.queryBySql(sqlSCGCSelect.append(sqlFrom).append(sqlFromFlag).append(sqlWhere).append(sqlSCGCGroup).toString());
			List<Map<String, Object>> SCGClist = new ArrayList<Map<String, Object>>();
			total = 0.00;
			if (resultSCGC != null && resultSCGC.size() > 0) {
				for (Object result : resultSCGC) {
					Object[] properties = (Object[]) result;
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("prodFactory", properties[0] == null ? "其它" : properties[0]);
					quantity = Double.parseDouble(properties[1] == null ? "0" : properties[1].toString());
					map.put("quantity", properties[1] == null ? "0" : properties[1]);
					total += quantity;
					SCGClist.add(map);
				}
				for (Map<String, Object> map : SCGClist) {
					quantity = Double.parseDouble(map.get("quantity").toString());
					if (total != 0) {
						rate = new BigDecimal((quantity / total) * 100).setScale(2, BigDecimal.ROUND_HALF_UP);
					} else {
						rate = BigDecimal.valueOf(0);
					}
					map.put("rate", rate + "%");
				}
			}
			mp.put("prodFactory",SCGClist);
			//出版方
			List resultCBF = dao.queryBySql(sqlCBFSelect.append(sqlFrom).append(sqlFromFlag).append(sqlWhere).append(sqlCBFGroup).toString());
			List<Map<String, Object>> CBFlist = new ArrayList<Map<String, Object>>();
			total = 0.00;
			if (resultCBF != null && resultCBF.size() > 0) {
				for (Object result : resultCBF) {
					Object[] properties = (Object[]) result;
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("publishers", properties[0] == null ? "其它" : properties[0]);
					quantity = Double.parseDouble(properties[1] == null ? "0" : properties[1].toString());
					map.put("quantity", properties[1] == null ? "0" : properties[1]);
					total += quantity;
					CBFlist.add(map);
				}
				for (Map<String, Object> map : CBFlist) {
					quantity = Double.parseDouble(map.get("quantity").toString());
					if (total != 0) {
						rate = new BigDecimal((quantity / total) * 100).setScale(2, BigDecimal.ROUND_HALF_UP);
					} else {
						rate = BigDecimal.valueOf(0);
					}
					map.put("rate", rate + "%");
				}
			}

			mp.put("publishers",CBFlist);

			pagination.setMap(mp);

		}



		List resultSet = dao.queryBySql(sqlSelect.append(sqlFrom).append(sqlFrom2).append(sqlWhere).append(sqlLimit).toString());
		List<OrderPlanDetailModel> list = new ArrayList<>();
		if (resultSet != null && resultSet.size() > 0) {
			for (Object result : resultSet) {
				OrderPlanDetailModel model = new OrderPlanDetailModel();
				Object[] properties = (Object[]) result;
				model.setSendTime(properties[0] == null ? "" : properties[0].toString());
				model.setOrderCode(properties[1] == null ? "" : properties[1].toString());
				model.setGoodsSn(properties[2] == null ? "" : properties[2].toString());
				model.setCustomerName(properties[3] == null ? "" : properties[3].toString());
				model.setGoodsCode(properties[4] == null ? "" : properties[4].toString());
				model.setGoodsName(properties[5] == null ? "" : properties[5].toString());
				model.setCategoryName(properties[6] == null ? "" : properties[6].toString());
				model.setOrderCharacter(properties[7] == null ? "" : properties[7].toString());
				model.setShopName(properties[8] == null ? "" : properties[8].toString());
				model.setProdFactory(properties[9] == null ? "" : properties[9].toString());
				model.setPublishers(properties[10] == null ? "" : properties[10].toString());
				model.setTechnicianName(properties[11] == null ? "" : properties[11].toString());
				model.setOrderRepairCode(properties[12] == null ? "" : properties[12].toString());
				model.setOrderRepairCheckTime(properties[13] == null ? "" : properties[13].toString());
				model.setDays(properties[14] == null ? "0" : properties[14].toString());
				model.setOrderRepairFeedback(properties[15] == null ? "" : properties[15].toString());
				list.add(model);
			}
		}

		pagination.setRows(list);

		if (pageBean.getPage() != null && pageBean.getRows() != null) {
			Long count = Long.parseLong(dao.uniqueBySql(sqlCount.append(sqlFrom).append(sqlFrom2).append(sqlWhere).toString()).toString());
			pagination.setTotal(count);
		}

		return pagination;
	}

	@Override
	public Result finishedOrderReparirExportExcel(PlanOrderDetailDto dto, HttpServletRequest request, HttpServletResponse response) {
		String excelFileName = "已发货订单微调" ;
		OutputStream out = null;
		try {

			//转码防止乱码
			final String userAgent = request.getHeader("USER-AGENT");
			if(userAgent.toLowerCase().contains("msie")){//IE浏览器
				excelFileName = URLEncoder.encode(excelFileName,"UTF8");
			}else if(userAgent.toLowerCase().contains( "mozilla") || userAgent.toLowerCase().contains("chrom")){//google浏览器,火狐浏览器
				excelFileName = new String(excelFileName.getBytes(), "ISO8859-1");
			}else{
				excelFileName = URLEncoder.encode(excelFileName,"UTF8");//其他浏览器
			}
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.addHeader("content-Disposition", "attachment;filename="+ excelFileName +".xls");
			response.flushBuffer();
			String[] headers = null;
			String[] fields = new String[] {"sendTime","OrderCode","goodsSn","customerName","goodsCode","goodsName","categoryName","orderCharacter","shopName","prodFactory","publishers","technicianName","orderRepairCode","orderRepairCheckTime","days","orderRepairFeedback"};


			headers =new String[]{"订单发货时间","订单号","唯一码","会员名","款号","款名","类别","订单性质","订单门店","订单生产工厂","出版方","订单版师","微调单号","微调审核时间","时长","实调内容"};

			out = response.getOutputStream();
			ExportExcelUtil.exportExcel(0,0,0,"已发货订单微调" , headers,ExportExcelUtil.buildExportedModel(this.getFinishedOrderRepair(dto,new PageBean()).getRows(), fields) , out, "yyyy-MM-dd");
		} catch (IOException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new Result();
	}

}
