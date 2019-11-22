package com.kongque.service.basics.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.kongque.constants.Constants;
import com.kongque.dao.IDaoService;
import com.kongque.dto.OrderClosedDto;
import com.kongque.entity.basics.Code;
import com.kongque.entity.basics.OrderClosed;
import com.kongque.entity.basics.OrderDetailClosed;
import com.kongque.entity.basics.XiuyuShopDirectorRelation;
import com.kongque.entity.order.Order;
import com.kongque.entity.order.OrderCheck;
import com.kongque.entity.order.OrderDetail;
import com.kongque.entity.user.UserDeptRelation;
import com.kongque.entity.user.UserRoleRelation;
import com.kongque.model.OrderClosedModel;
import com.kongque.model.OrderModel;
import com.kongque.service.basics.IOrderClosedService;
import com.kongque.util.CodeUtil;
import com.kongque.util.DateUtil;
import com.kongque.util.ListUtils;
import com.kongque.util.PageBean;
import com.kongque.util.Result;

@Service("orderClosedService")
public class OrderClosedServiceImpl implements IOrderClosedService {

	@Resource
	private IDaoService dao;
	@Resource
	private IOrderClosedService orderClosedService;
	public List<OrderClosedModel> getOrderClosedModelList(OrderClosedDto dto, Integer page, Integer rows) {
		List<OrderClosedModel> orderClosedModel = new ArrayList<>();
		StringBuilder sql = new StringBuilder("SELECT t.c_id AS closedId,t.c_closed_applicant AS closedApplicant,t.c_closed_status AS closedStatus,t.c_closed_createtime AS closedCreateTime,t.c_closed_auditor AS closedAuditor,t.c_closed_checktime AS closedCheckTime,t.c_closed_reason AS closedReason,t.c_closed_submittime AS closedSubmitTime,b.c_erp_no AS posNo,c.c_order_code AS orderCode,c.c_customer_name AS customerName,b.c_goods_sn AS styleSn,e.c_name AS styleName,e.c_code AS styleCode,h.c_materiel_code AS styleMaterielCode,h.c_color_name AS styleColor,c.c_shop_name AS shopName,c.c_id AS orderId,e.c_id AS styleId,t.c_closed_code AS closedCode,t.c_closed_instruction AS closedInstruction,r.c_id AS orderDetailId FROM t_order_closed t LEFT JOIN t_order_detail_closed r ON t.c_id= r.c_order_closed_id LEFT JOIN t_order c ON t.c_order_id = c.c_id LEFT JOIN t_order_detail b ON r.c_order_detail_id = b.c_id   LEFT JOIN t_goods_detail h ON  b.c_goods_detail_id=h.c_id LEFT JOIN t_goods e ON h.c_goods_id=e.c_id LEFT JOIN t_user f ON t.c_closed_applicant = f.c_user_name WHERE t.c_closed_delete = '0'  ");
		//根据结案单状态查询
		if(dto.getClosedStatus()!=null&&!dto.getClosedStatus().isEmpty()){
			sql.append(" and t.c_closed_status =").append(dto.getClosedStatus());
		}
		//根据订单编号查询
		if(dto.getCode()!= null && !dto.getCode().isEmpty()){
			sql.append(" and c.c_order_code like '%").append(dto.getCode()).append("%'");
		}
		//根据erp订单号查询
		if(dto.getPosNo() != null && !dto.getPosNo().isEmpty()){
			sql.append(" and b.c_erp_no like '%").append(dto.getPosNo()).append("%'");
		}
		//根据顾客姓名查询
		if(dto.getCustomerName() != null && !dto.getCustomerName().isEmpty()){
			sql.append(" and c.c_customer_name like '%").append(dto.getCustomerName()).append("%'");
		}
		//根据商品名称
		if(dto.getStyleName() != null && !dto.getStyleName().isEmpty()){
			sql.append(" and e.c_name like '%").append(dto.getStyleName()).append("%'");
		}
		//根据商品颜色
		if(dto.getStyleColor()!= null && !dto.getStyleColor().isEmpty()){
			sql.append(" and h.c_color_name like '%").append(dto.getStyleColor()).append("%'");
		}
		//根据商品唯一码
		if(dto.getStyleSN() != null && !dto.getStyleSN().isEmpty()){
			sql.append(" and b.c_goods_sn like '%").append(dto.getStyleSN()).append("%'");
		}
//		UserRoleRelation user = dao.findUniqueByProperty(UserRoleRelation.class, "userId", dto.getUserId());
		@SuppressWarnings("unchecked")
		List<UserRoleRelation> users = dao.createCriteria(UserRoleRelation.class)
				.add(Restrictions.eq( "userId", dto.getUserId())).list();
		UserDeptRelation dept = dao.findUniqueByProperty(UserDeptRelation.class, "userId", dto.getUserId());
		for (UserRoleRelation user : users) {
			if (Constants.XIUYU_ROLE_ID.equalsIgnoreCase(user.getUserRole().getId())){
				sql.append(" and orderrepair.c_shop_id in (").append(convertShopIds(getShopList(user))).append(")");
				break;
			} else if (Constants.DIANYUAN_ROLE_ID.equalsIgnoreCase(user.getUserRole().getId())
					||Constants.JIAMENG_ROLE_ID.equalsIgnoreCase(user.getUserRole().getId())){
				sql.append(" and orderrepair.c_shop_id = '" + dept.getDeptId() + "' ");
				break;
			}
		}
		/*switch (user.getUserRole().getId()) {
		case Constants.DIANYUAN_ROLE_ID:
			// 店员
			sql.append(" and orderrepair.c_shop_id ='" + dept.getDeptId() + "' ");
			break;
		case Constants.XIUYU_ROLE_ID:
			// 秀域审核主管
			sql.append(" and orderrepair.c_shop_id in (").append(convertShopIds(getShopList(user))).append(")");
			break;
		case Constants.JIAMENG_ROLE_ID:
			// 加盟店
			sql.append(" and orderrepair.c_shop_id = '" + dept.getDeptId() + "' ");
		default:
			break;
		}*/
		
		/* switch(user.getUserRole().getRoleName()){
		    case "店员": 
			sql.append(" and f.c_shop_id = '").append(user.getShop().getId()).append("'");
			break;
		    case "店长": 
			sql.append(" and f.c_shop_id = '").append(user.getShop().getId()).append("'");
			break;
		    case "加盟店":
			sql.append(" and f.c_shop_id = '").append(user.getShop().getId()).append("'");
			break;
		    case "秀域审核主管":
			//TODO
			sql.append(" and f.c_shop_id in (").append(convertShopIds(getShopList(user))).append(")");
			break;
		    }*/
		//sql.append(" group by t.c_id");
		sql.append(" order by t.c_closed_code desc ");
		sql.append(" limit "+(page - 1) * rows+","+rows);
		@SuppressWarnings("rawtypes")
		List resultSet = dao.queryBySql(sql.toString());
		for(Object result : resultSet){
			OrderClosedModel odClosedModel = new OrderClosedModel();//构建返回数据模型
			Object[] properties = (Object[])result;
			odClosedModel.setClosedID(properties[0]==null ? "" : properties[0].toString());
			odClosedModel.setClosedApplicant(properties[1]==null ? "" : properties[1].toString());
			odClosedModel.setClosedStatus(properties[2]==null ? "" : properties[2].toString());
			odClosedModel.setClosedCreateTime(properties[3]==null ? "" : properties[3].toString());
			odClosedModel.setClosedAuditor(properties[4]==null ? "" : properties[4].toString());
			odClosedModel.setClosedCheckTime(properties[5]==null ? "" : properties[5].toString());
			odClosedModel.setClosedReason(properties[6]==null ? "" : properties[6].toString());
			odClosedModel.setClosedSubmitTime(properties[7]==null ? "" : properties[7].toString());
			odClosedModel.setPosNo(properties[8]==null ? "" : properties[8].toString());
			odClosedModel.setOrderCode(properties[9]==null ? "" : properties[9].toString());
			odClosedModel.setCustomerName(properties[10]==null ? "" : properties[10].toString());
			odClosedModel.setStyleSN(properties[11]==null ? "" : properties[11].toString());
			odClosedModel.setStyleName(properties[12]==null ? "" : properties[12].toString());
			odClosedModel.setStyleCode(properties[13]==null ? "" : properties[13].toString());
			odClosedModel.setMaterielCode(properties[14]==null ? "" : properties[14].toString());
			odClosedModel.setStyleColor(properties[15]==null ? "" : properties[15].toString());
			//odClosedModel.setStyleSize(properties[16]==null ? "" : properties[16].toString());
			//odClosedModel.setStyleUnit(properties[17]==null ? "" : properties[17].toString()); 
			//odClosedModel.setNum(properties[18]==null ? "" : properties[18].toString());
			odClosedModel.setShopName(properties[16]==null ? "" : properties[16].toString());
			odClosedModel.setOrderId(properties[17]==null ? "" : properties[17].toString());
			odClosedModel.setStyleId(properties[18]==null ? "" : properties[18].toString());
			odClosedModel.setClosedCode(properties[19]==null ? "" : properties[19].toString());
			odClosedModel.setClosedInstruction(properties[20]==null ? "" : properties[20].toString());
			odClosedModel.setOrderDetailId(properties[21]==null ? "" : properties[21].toString());
			orderClosedModel.add(odClosedModel);
		}
		return orderClosedModel;
	}

	@Override
	public Long getOrderClosedModelCount(OrderClosedDto dto) {
		StringBuilder sql = new StringBuilder("select count(*) from (SELECT count(*)  from t_order_closed t LEFT JOIN t_order_detail_closed r ON t.c_id= r.c_order_closed_id LEFT JOIN t_order c ON t.c_order_id = c.c_id LEFT JOIN t_order_detail b ON r.c_order_detail_id = b.c_id   LEFT JOIN t_goods_detail h ON  b.c_goods_detail_id=h.c_id LEFT JOIN t_goods e ON h.c_goods_id=e.c_id LEFT JOIN t_user f ON t.c_closed_applicant = f.c_user_name WHERE t.c_closed_delete = '0' ");
		//根据结案单状态查询
				if(dto.getClosedStatus()!=null&&!dto.getClosedStatus().isEmpty()){
					sql.append(" and t.c_closed_status = '").append(dto.getClosedStatus()).append("'");
				}
		//根据订单编号查询
				if(dto.getCode()!= null && !dto.getCode().isEmpty()){
					sql.append(" and c.c_order_code like '%").append(dto.getCode()).append("%'");
				}
				//根据erp订单号查询
				if(dto.getPosNo() != null && !dto.getPosNo().isEmpty()){
					sql.append(" and b.c_erp_no like '%").append(dto.getPosNo()).append("%'");
				}
				//根据顾客姓名查询
				if(dto.getCustomerName() != null && !dto.getCustomerName().isEmpty()){
					sql.append(" and c.c_customer_name like '%").append(dto.getCustomerName()).append("%'");
				}
				//根据商品名称
				if(dto.getStyleName() != null && !dto.getStyleName().isEmpty()){
					sql.append(" and e.c_name like '%").append(dto.getStyleName()).append("%'");
				}
				//根据商品颜色
				if(dto.getStyleColor()!= null && !dto.getStyleColor().isEmpty()){
					sql.append(" and h.c_color_name like '%").append(dto.getStyleColor()).append("%'");
				}
				//根据商品唯一码
				if(dto.getStyleSN() != null && !dto.getStyleSN().isEmpty()){
					sql.append(" and b.c_goods_sn like '%").append(dto.getStyleSN()).append("%'");
				}
		//User user = UserUtil.getCurrentUser();
		/* switch(user.getUserRole().getRoleName()){
		    case "店员": 
			sql.append(" and f.c_shop_id = '").append(user.getShop().getId()).append("'");
			break;
		    case "店长": 
			sql.append(" and f.c_shop_id = '").append(user.getShop().getId()).append("'");
			break;
		    case "加盟店":
			sql.append(" and f.c_shop_id = '").append(user.getShop().getId()).append("'");
			break;
		    case "秀域审核主管":
			//TODO
			sql.append(" and f.c_shop_id in (").append(convertShopIds(getShopList(user))).append(")");
			break;
		    }*/
		 sql.append(" group by t.c_id ) a");
		List<BigInteger> result = dao.queryBySql(sql.toString());
		return result == null || result.isEmpty() ? 0L : result.get(0).longValue();

	}
	private String convertShopIds(List<String> list){
	    if(ListUtils.isEmptyOrNull(list)){
		return "''";
	    }
	    StringBuilder ids = new StringBuilder();
	    for(String id : list){
		ids.append("'").append(id).append("',");
	    }
	    ids.deleteCharAt(ids.length()-1);
	    return ids.toString();
	}
	private List<String> getShopList(UserRoleRelation user) {
		Criteria criteria = dao.createCriteria(XiuyuShopDirectorRelation.class);
		criteria.add(Restrictions.eq("userId", user.getUserId()));
		criteria.setProjection(Projections.property("deptId"));
		@SuppressWarnings("unchecked")
		List<String> list = criteria.list();
		if (!ListUtils.isEmptyOrNull(list)) {
			return list;
		} else {
			return null;
		}
	}

	@Override
	public List<Order> remote(String q,String userShopId) {
		Criteria criteria = dao.createCriteria(Order.class);
//		Disjunction disjunction = Restrictions.disjunction();
		if(!StringUtils.isBlank(q)){
//			disjunction .add(Restrictions.like("code", q, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("orderCode", q, MatchMode.ANYWHERE));
			criteria.add(Restrictions.eq("shopId",userShopId));
		}
		PageBean pageBean = new PageBean();
		pageBean.setPage(1);
		pageBean.setRows(10);
		return dao.findListWithPagebeanCriteria(criteria, pageBean);
	}

	@Override
	public Result deleteClosed(String closedId) {
		  Criteria criteria = dao.createCriteria(OrderClosed.class);
		  criteria.add(Restrictions.eq("cid",closedId));
		  OrderClosed result = (OrderClosed) criteria.uniqueResult();
		  result.setDel("1");
		  dao.update(result);
	      return new Result();
			
			/*User user = UserUtil.getCurrentUser();
			Order order = dao.findById(Order.class, orderId);
			if (order.getBillStatus().equals("未送出") || order.getBillStatus().equals("星域驳回")
					|| order.getBillStatus().equals("秀域驳回")) {
				order.setDeleteFlag("1");
				returnResult(synchroDeleteOrder(order.getCode()));
				return Result.ok();
			} else {
				return Result.badRequest("当前订单状态不允许删除！");
			}*/
		
	}

	@Override
	public List<OrderClosedModel> getStyleList(OrderClosedDto dto, Integer page, Integer rows) {
		List<OrderClosedModel> orderClosedModel = new ArrayList<>();
		StringBuilder sql = new StringBuilder("SELECT b.c_erp_no AS posNo,c.c_order_code AS orderCode,b.c_goods_sn AS styleSn,e.c_name AS styleName,e.c_code AS styleCode,h.c_materiel_code AS styleMaterielCode,h.c_color_name AS styleColor,c.c_id AS orderId,e.c_id AS styleId,b.c_id AS orderDetailId,c.c_customer_name AS customerName FROM t_order_detail b  LEFT JOIN t_order c  ON b.c_order_id=c.c_id LEFT JOIN t_goods_detail h ON  b.c_goods_detail_id=h.c_id LEFT JOIN t_goods e ON h.c_goods_id=e.c_id  where 1=1 ");
		//根据订单编号查询
		if(dto.getCode()!= null && !dto.getCode().isEmpty()){
			sql.append(" and c.c_order_code like '%").append(dto.getCode()).append("%'");
		}
//		sql.append(" limit "+(page - 1) * rows+","+rows);
		@SuppressWarnings("rawtypes")
		List resultSet = dao.queryBySql(sql.toString());
		for(Object result : resultSet){
			OrderClosedModel odClosedModel = new OrderClosedModel();//构建返回数据模型
			Object[] properties = (Object[])result;
			odClosedModel.setPosNo(properties[0]==null ? "" : properties[0].toString());
			odClosedModel.setOrderCode(properties[1]==null ? "" : properties[1].toString());
			odClosedModel.setStyleSN(properties[2]==null ? "" : properties[2].toString());
			odClosedModel.setStyleName(properties[3]==null ? "" : properties[3].toString());
			odClosedModel.setStyleCode(properties[4]==null ? "" : properties[4].toString());
			odClosedModel.setMaterielCode(properties[5]==null ? "" : properties[5].toString());
			odClosedModel.setStyleColor(properties[6]==null ? "" : properties[6].toString());
			//odClosedModel.setStyleSize(properties[7]==null ? "" : properties[7].toString());
			//odClosedModel.setStyleUnit(properties[8]==null ? "" : properties[8].toString()); 
			//odClosedModel.setNum(properties[9]==null ? "" : properties[9].toString());
			odClosedModel.setOrderId(properties[7]==null ? "" : properties[7].toString());
			odClosedModel.setStyleId(properties[8]==null ? "" : properties[8].toString());
			odClosedModel.setOrderDetailId(properties[9]==null ? "" : properties[9].toString());
			odClosedModel.setCustomerName(properties[10]==null ? "" : properties[10].toString());
			orderClosedModel.add(odClosedModel);
		}
		return orderClosedModel;
	}

	@Override
	public Long getStyleListCount(OrderClosedDto dto) {
		StringBuilder sql = new StringBuilder("SELECT count(*) FROM t_order_detail b  LEFT JOIN t_order c  ON b.c_order_id=c.c_id LEFT JOIN t_goods_detail h ON  b.c_goods_detail_id=h.c_id LEFT JOIN t_goods e ON h.c_goods_id=e.c_id  where 1=1 ");
		//根据订单编号查询
		if(dto.getCode()!= null && !dto.getCode().isEmpty()){
			sql.append(" and c.c_order_code like '%").append(dto.getCode()).append("%'");
		}
		List<BigInteger> result = dao.queryBySql(sql.toString());
		return result == null || result.isEmpty() ? 0L : result.get(0).longValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result saveOrUpdate(OrderClosedDto dto) {
	    Date date = new Date();
		if (StringUtils.isBlank(dto.getCid())){
//			OrderClosed dd = dao.findUniqueByProperty(OrderClosed.class, "orderId", dto.getOrderId());
				
				OrderClosed orderClosed = new OrderClosed();
				orderClosed.setClosedCode(CodeUtil.createClosedCode(getClosedMaxValue()));
				orderClosed.setOrderId(dao.findById(Order.class, dto.getOrderId()));
				orderClosed.setClosedApplicant(dto.getClosedApplicant());
				orderClosed.setClosedReason(dto.getClosedReason());
				//orderClosed.setClosedInstruction(dto.getClosedInstruction());
				orderClosed.setDel("0");
				orderClosed.setClosedStatus(dto.getClosedStatus());
				orderClosed.setClosedCreateTime(date);
				if("2".equals(dto.getClosedStatus())){
				    orderClosed.setClosedSubmitTime(date);
				}
				dao.save(orderClosed);
				if(dto.getIds()!=null){
					for (int i = 0; i < dto.getIds().length; i++) {
						OrderDetailClosed orderDetailClosed =  new OrderDetailClosed();
						orderDetailClosed.setOrderDetailId(dao.findById(OrderDetail.class, dto.getIds()[i]));
						orderDetailClosed.setOrderClosedId(dao.findById(OrderClosed.class, orderClosed.getCid()));
						dao.save(orderDetailClosed);
						OrderDetail detail = dao.findUniqueByProperty(OrderDetail.class, "id",dto.getIds()[i] );
						detail.setClosedStatus(dto.getClosedStatus());
					}
				}
			/*else {
				dd.setClosedStatus(dto.getClosedStatus());
				dd.setClosedSubmitTime(new Date());
				dao.update(dd); 
				Criteria criteria = dao.createCriteria(OrderDetailClosed.class);
			    criteria.add(Restrictions.eq("orderClosedId",dd.getCid()));
				List<OrderDetailClosed> list = criteria.list();
				for (int i = 0; i < list.size(); i++) {
					OrderDetailClosed detail = dao.findById(OrderDetailClosed.class, list.get(i).getId());
					dao.delete(detail); 
				}
				for (int j = 0; j < dto.getIds().length; j++) {
					OrderDetailClosed orderDetailClosed =  new OrderDetailClosed();
					orderDetailClosed.setOrderDetailId(dto.getIds()[j]);
					orderDetailClosed.setOrderClosedId(dd.getCid());
					dao.save(orderDetailClosed);
					OrderDetail detail = dao.findUniqueByProperty(OrderDetail.class, "id",dto.getIds()[j] );
					detail.setClosedStatus(dto.getClosedStatus());
				}
			}*/
            return new Result(orderClosed);
        }else {
        	OrderClosed orderClosed = dao.findById(OrderClosed.class,dto.getCid());
			orderClosed.setOrderId(dao.findById(Order.class, dto.getOrderId()));
			orderClosed.setClosedApplicant(dto.getClosedApplicant());
			orderClosed.setClosedReason(dto.getClosedReason());
			if(dto.getClosedStatus().equals("2")){
			   /* orderClosed.setClosedInstruction(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss") + " - - " 
			    + dto.getUserName() +", "+ "提交结案单, "+
	    		dto.getClosedInstruction() +". \r\n");*/
			    orderClosed.setClosedSubmitTime(date);
			}else{
			   // orderClosed.setClosedInstruction(dto.getClosedInstruction());
			}
			orderClosed.setClosedStatus(dto.getClosedStatus()); 
			dao.update(orderClosed);
			Criteria criteria = dao.createCriteria(OrderDetailClosed.class);
			criteria.add(Restrictions.eq("orderClosedId.cid",orderClosed.getCid()));
			List<OrderDetailClosed> list = criteria.list();
			for (int i = 0; i < list.size(); i++) {
				OrderDetailClosed detail = dao.findById(OrderDetailClosed.class, list.get(i).getId());
				dao.delete(detail); 
			}
			if(dto.getIds()!=null){
				for (int j = 0; j < dto.getIds().length; j++) {
					OrderDetailClosed orderDetailClosed =  new OrderDetailClosed();
					orderDetailClosed.setOrderDetailId(dao.findById(OrderDetail.class, dto.getIds()[j]));
					orderDetailClosed.setOrderClosedId(dao.findById(OrderClosed.class, orderClosed.getCid()));
					dao.save(orderDetailClosed);
					OrderDetail detail = dao.findUniqueByProperty(OrderDetail.class, "id",dto.getIds()[j] );
					detail.setClosedStatus(dto.getClosedStatus());
				}
			}
			
            return new Result(orderClosed);
        }
		
	}

	@Override
	public List<OrderDetailClosed> getClosedDetaillist(String closedID) {
		Criteria criteria = dao.createCriteria(OrderDetailClosed.class);
	    criteria.add(Restrictions.eq("orderClosedId.cid",closedID));
		@SuppressWarnings("unchecked")
		List<OrderDetailClosed> list = criteria.list();
		return list;
	}
	private String getClosedMaxValue() {
		Date date = new Date();
		Criteria criteria = dao.createCriteria(Code.class);
		criteria.add(Restrictions.between("updateTime", DateUtil.minDate(date), DateUtil.maxDate(date)));
		criteria.add(Restrictions.eq("type", "JA"));
		criteria.addOrder(org.hibernate.criterion.Order.desc("maxValue"));
		criteria.setMaxResults(1);
		Code code = (Code) criteria.uniqueResult();
		if (code == null) {
			code = new Code();
			code.setMaxValue(1);
			code.setType("JA");
			code.setUpdateTime(date);
			dao.save(code);
		} else {
			code.setMaxValue(code.getMaxValue() + 1);
		}
		return String.format("%0" + 6 + "d", code.getMaxValue());
	}

	@Override
	public List<OrderClosedModel> getClosedDetailList(OrderClosedDto dto, Integer page, Integer rows) {
		List<OrderClosedModel> orderClosedModel = new ArrayList<>();
		StringBuilder sql = new StringBuilder("SELECT b.c_erp_no AS posNo,c.c_order_code AS orderCode,b.c_goods_sn AS styleSn,e.c_name AS styleName,e.c_code AS styleCode,h.c_materiel_code AS styleMaterielCode,h.c_color_name AS styleColor,c.c_id AS orderId,e.c_id AS styleId,b.c_id AS orderDetailId,c.c_customer_name AS customerName   FROM t_order_detail_closed t LEFT JOIN t_order_detail b ON t.c_order_detail_id = b.c_id LEFT JOIN t_order c  ON b.c_order_id=c.c_id LEFT JOIN t_goods_detail h ON  b.c_goods_detail_id=h.c_id LEFT JOIN t_goods e ON h.c_goods_id=e.c_id  where 1=1 ");
		
		sql.append(" AND t.c_order_closed_id='"+dto.getCid()+"'");
		sql.append(" limit "+(page - 1) * rows+","+rows);
		@SuppressWarnings("rawtypes")
		List resultSet = dao.queryBySql(sql.toString());
		for(Object result : resultSet){
			OrderClosedModel odClosedModel = new OrderClosedModel();//构建返回数据模型
			Object[] properties = (Object[])result;
			odClosedModel.setPosNo(properties[0]==null ? "" : properties[0].toString());
			odClosedModel.setOrderCode(properties[1]==null ? "" : properties[1].toString());
			odClosedModel.setStyleSN(properties[2]==null ? "" : properties[2].toString());
			odClosedModel.setStyleName(properties[3]==null ? "" : properties[3].toString());
			odClosedModel.setStyleCode(properties[4]==null ? "" : properties[4].toString());
			odClosedModel.setMaterielCode(properties[5]==null ? "" : properties[5].toString());
			odClosedModel.setStyleColor(properties[6]==null ? "" : properties[6].toString());
			//odClosedModel.setStyleSize(properties[7]==null ? "" : properties[7].toString());
		    //odClosedModel.setStyleUnit(properties[8]==null ? "" : properties[8].toString()); 
			//odClosedModel.setNum(properties[9]==null ? "" : properties[9].toString());
			odClosedModel.setOrderId(properties[7]==null ? "" : properties[7].toString());
			odClosedModel.setStyleId(properties[8]==null ? "" : properties[8].toString());
			odClosedModel.setOrderDetailId(properties[9]==null ? "" : properties[9].toString());
			odClosedModel.setCustomerName(properties[10]==null ? "" : properties[10].toString());
			orderClosedModel.add(odClosedModel);
		}
		return orderClosedModel;
	}

	@Override
	public Long getClosedDetailCount(OrderClosedDto dto) {
		StringBuilder sql = new StringBuilder("SELECT count(*)  FROM t_order_detail_closed t LEFT JOIN t_order_detail b ON t.c_order_detail_id = b.c_id LEFT JOIN t_order c  ON b.c_order_id=c.c_id LEFT JOIN t_goods_detail h ON  b.c_goods_detail_id=h.c_id LEFT JOIN t_goods e ON h.c_goods_id=e.c_id  where 1=1 ");
		sql.append(" AND t.c_order_closed_id='"+dto.getCid()+"'");
		List<BigInteger> result = dao.queryBySql(sql.toString());
		return result == null || result.isEmpty() ? 0L : result.get(0).longValue();
	}

	@Override
	public Result updateStatusAndInstruction(OrderClosedDto dto) {
		if (StringUtils.isNotBlank(dto.getCid())){
			OrderClosed dd = dao.findById(OrderClosed.class, dto.getCid());
			dd.setClosedStatus(dto.getClosedStatus());
			Date date=new Date();
			String closedInstruction = "";
			if("".equals(dd.getClosedInstruction())|| dd.getClosedInstruction()==null ){
				closedInstruction = "\n"+"";
			}else{
				closedInstruction = "\n"+dd.getClosedInstruction();
			}
			dd.setClosedInstruction(
					DateUtil.formatDate(date, "yyyy-MM-dd HH:mm:ss") + " - - "+dto.getUserName()+", " + 
							closedStatus(dto.getClosedStatus()) +", " +dto.getClosedInstruction() +  ";\r\n" + closedInstruction);
			dd.setClosedAuditor(dto.getUserName());
			dd.setClosedCheckTime(date);
			dao.update(dd);
			if(dto.getIds()!=null){
				for(int i=0;i<dto.getIds().length;i++) {
					OrderDetail detail = dao.findById(OrderDetail.class, dto.getIds()[i]);
					detail.setClosedStatus(dto.getClosedStatus());
					dao.update(detail);
				}
			}
			
		}
		 return new Result();
	}
	//结案单状态(1:未提交  2：已提交 3：星域审核通过  4：星域驳回)
	public String closedStatus(String closedStatus){
		String clo = null;
		if("0".equals(closedStatus)){
			clo = "未申请结案";
		}else if("1".equals(closedStatus)){
			clo =  "已申请未提交";
		}else if("2".equals(closedStatus)){
			clo = "已提交";
		}else if("3".equals(closedStatus)){
			clo = "审核通过";
		}else if("4".equals(closedStatus)){
			clo = "已驳回";
		}
		return clo;
	}

    /*
     * (non-Javadoc)
     * 
     * @see com.kongque.service.IOrderClosedService#orderDetailList()
     */
    @Override
    public Result orderDetailList(String orderCode) {
	List<Map<String, Object>> list = new ArrayList<>();
	StringBuilder sql = new StringBuilder();
	sql.append(
		"SELECT a.c_id AS orderDetailId,s.c_code AS styleCode, s.c_name AS styleName, h.c_color_name AS styleColor, a.c_num AS num, a.c_goods_sn AS styleSN, a.c_erp_no AS posNo FROM t_order_detail a LEFT JOIN (t_order_detail_closed b LEFT JOIN t_order_closed e ON e.c_id = b.c_order_closed_id AND e.c_closed_delete = '0') ON a.c_id = b.c_order_detail_id LEFT JOIN t_order c ON a.c_order_id = c.c_id LEFT JOIN t_goods_detail h ON a.c_goods_detail_id=h.c_id LEFT JOIN t_goods s ON h.c_goods_id=s.c_id WHERE (b.c_id IS NULL OR e.c_closed_delete = '1')  AND c.c_order_code = '")
		.append(orderCode).append("';");
	List<Object[]> resultList = dao.queryBySql(sql.toString());
	if (!resultList.isEmpty()) {
	    for (Object[] row : resultList) {
		Map<String, Object> map = new HashMap<>();
		map.put("orderDetailId", row[0]);
		map.put("styleCode", row[1]);
		map.put("styleName", row[2]);
		map.put("styleColor", row[3]);
		map.put("num", row[4]);
		map.put("styleSN", row[5]);
		map.put("posNo", row[6]);
		list.add(map);
	    }
	}
	return new Result(list);
    }

    /* (non-Javadoc)
     * @see com.kongque.service.IOrderClosedService#order(java.lang.String)
     */
    @Override
    public Result order(String orderCode) {
    	OrderModel model = new OrderModel();
    	Order order = dao.findUniqueByProperty(Order.class, "orderCode", orderCode);
    	if (order != null) {
			model.setOrder(order);
			List<OrderCheck> list = dao.findListByProperty(OrderCheck.class, "orderId", order.getId());
			if (list != null && list.size() > 0) {
				model.setOrderCheckList(list);
			}
		}

	return new Result(model);
    }

    /* (non-Javadoc)
     * @see com.kongque.service.IOrderClosedService#orderClosedList(java.lang.String)
     */
    @Override
    public Result orderClosedList(String orderClosedId) {
	List<Map<String, Object>> list = new ArrayList<>();
	StringBuilder sql = new StringBuilder();
	sql.append("SELECT b.c_id AS orderDetailId, s.c_code AS styleCode,s.c_name AS styleName,h.c_color_name AS styleColor,b.c_num AS num,b.c_goods_sn AS styleSN,b.c_erp_no AS posNo,a.c_id AS orderDetailClosedId FROM t_order_detail_closed a LEFT JOIN t_order_detail b ON a.c_order_detail_id = b.c_id LEFT JOIN t_goods_detail h ON b.c_goods_detail_id=h.c_id LEFT JOIN t_goods s ON h.c_goods_id=s.c_id LEFT JOIN t_order_closed d ON d.c_id = a.c_order_closed_id WHERE d.c_id = '")
	.append(orderClosedId)
	.append("';");
	List<Object[]> resultList = dao.queryBySql(sql.toString());
	if (!resultList.isEmpty()) {
	    for (Object[] row : resultList) {
		Map<String, Object> map = new HashMap<>();
		map.put("orderDetailId", row[0]);
		map.put("styleCode", row[1]);
		map.put("styleName", row[2]);
		map.put("styleColor", row[3]);
		map.put("num", row[4]);
		map.put("styleSN", row[5]);
		map.put("posNo", row[6]);
		map.put("orderDetailClosedId", row[7]);
		list.add(map);
	    }
	}
	return new Result(list);
    }

    /* (non-Javadoc)
     * @see com.kongque.service.IOrderClosedService#check(java.util.Map)
     */
    @SuppressWarnings("unchecked")
    @Override 
    public Result check(OrderClosedDto dto) {
	OrderClosed orderClosed = dao.findById(OrderClosed.class,dto.getCid());
	String status = dto.getClosedStatus();
	orderClosed.setClosedStatus(status);
	orderClosed.setClosedCheckTime(new Date());
	orderClosed.setClosedAuditor(dto.getUserName());
	String closedInstruction = "";
	if("".equals(orderClosed.getClosedInstruction())||  orderClosed.getClosedInstruction()==null){
		closedInstruction = "\n"+"";
	}else{
		closedInstruction = "\n"+orderClosed.getClosedInstruction();
	}
	orderClosed.setClosedInstruction(
			DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss") + " - - "
					+ dto.getUserName() + ", "
					+ ("3".equals(status) ? "审核通过, " : "星域驳回, ")
					+dto.getClosedInstruction()+". "
					+ closedInstruction);
	Criteria criteria = dao.createCriteria(OrderDetailClosed.class);
	criteria.add(Restrictions.eq("orderClosedId.cid", orderClosed.getCid()));
	List<OrderDetailClosed> list = criteria.list();
	List<String> orderDetailIdList = new ArrayList<>();
	for(OrderDetailClosed orderDetailClosed : list){
	    orderDetailIdList.add(orderDetailClosed.getOrderDetailId().getId());
	} 
	Criteria orderDetailCriteria = dao.createCriteria(OrderDetail.class);
	orderDetailCriteria.add(Restrictions.in("id", orderDetailIdList));
	List<OrderDetail> orderDetailList = orderDetailCriteria.list();
	for(OrderDetail orderDetail : orderDetailList){
	    orderDetail.setClosedStatus(orderClosed.getClosedStatus());
	}
	return new Result(orderClosed);
    }
	
}
