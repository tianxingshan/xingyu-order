package com.kongque.service.productionRepair.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.kongque.entity.productionRepair.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.kongque.dao.IDaoService;
import com.kongque.dto.MaterialsSaveDto;
import com.kongque.dto.MesRepairProductionPlanDto;
import com.kongque.dto.RepairPlanCheckDto;
import com.kongque.entity.basics.Code;
import com.kongque.entity.order.OrderDetail;
import com.kongque.entity.repair.OrderRepair;
import com.kongque.entity.repair.OrderRepairCheck;
import com.kongque.model.MesRepairProductionPlanModel;
import com.kongque.model.OrderRepairModel;
import com.kongque.service.productionRepair.IRepairProductionPlanService;
import com.kongque.util.CodeUtil;
import com.kongque.util.DateUtil;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
public class RepairProductionPlanServiceImpl implements IRepairProductionPlanService{

	@Resource
	private IDaoService dao;
	
	@Override
	public Pagination<MesRepairProductionPlan> list(MesRepairProductionPlanDto dto,PageBean pageBean){
		Criteria criteria=dao.createCriteria(MesRepairProductionPlanCopy.class);
		if(StringUtils.isNotBlank(dto.getPlanCode())){
			criteria.add(Restrictions.like("planCode", dto.getPlanCode().trim(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(dto.getStatus())){
			criteria.add(Restrictions.eq("status", dto.getStatus()));
		}
		if(dto.getPlanDateStr()!=null && dto.getPlanDateEnd()!=null){
			criteria.add(Restrictions.between("planDate", dto.getPlanDateStr(), dto.getPlanDateEnd()));
		}else if(dto.getPlanDateStr()!=null && dto.getPlanDateEnd()==null){
			criteria.add(Restrictions.ge("planDate", dto.getPlanDateStr()));
		}else if(dto.getPlanDateStr()==null && dto.getPlanDateEnd()!=null){
			criteria.add(Restrictions.le("planDate", dto.getPlanDateEnd()));
		}
		criteria.add(Restrictions.eq("del", "0"));
		criteria.addOrder(org.hibernate.criterion.Order.desc("planCode"));
		Pagination<MesRepairProductionPlan> pagination=new Pagination<>();
		pagination.setRows(dao.findListWithPagebeanCriteria(criteria, pageBean));
		pagination.setTotal(dao.findTotalWithCriteria(criteria));
		return pagination;
	}
	
	
	@Override
	public Result saveOrUpdate(MesRepairProductionPlanDto dto){
		if(StringUtils.isBlank(dto.getId())){
			MesRepairProductionPlan plan = new MesRepairProductionPlan();
			BeanUtils.copyProperties(dto, plan);
			plan.setPlanCode(CodeUtil.createRepairPlanCode(getOrderMaxValue()));
			plan.setStatus("0");
			plan.setDel("0");
			plan.setCreateTime(new Date());
			dao.save(plan);
			return new Result(plan);
		}else{
			MesRepairProductionPlan plan = new MesRepairProductionPlan();
			BeanUtils.copyProperties(dto, plan);
			plan.setNum(Integer.parseInt(dto.getNum()));
			dao.update(plan);
			return new Result(plan);
		}
	}
	
	@Override
	public Result orderRepair(String repairCode){
		OrderRepair orderRepair = dao.findUniqueByProperty(OrderRepair.class, "orderRepairCode", repairCode);
		if(orderRepair!=null){
			Criteria criteria=dao.createCriteria(MesRepairProductionPlanDetail.class);
			criteria.add(Restrictions.eq("orderRepairId", orderRepair.getId()));
			@SuppressWarnings("unchecked")
			List<MesRepairProductionPlanDetail> list = criteria.list();
			if(list!=null && list.size()>0){
				return new Result("500","该微调单已经下达计划");
			}
			Criteria criteria1 = dao.createCriteria(OrderRepair.class);
			criteria1.add(Restrictions.eq("orderRepairCode", repairCode));
			criteria1.add(Restrictions.eq("orderRepairStatus", "8"));
			@SuppressWarnings("unchecked")
			List<OrderRepair> list1 = criteria1.list();
			if(list1!=null && list1.size()>0){
				return new Result(list1);
			}else{
				return new Result("500","该微调单不符合下达计划条件");
			}
		}else{
			return new Result("500","查询不到此微调单");
		}
		
	}
	
	@Override
	public Result orderRepairSave(String planId,String repairId){
		MesRepairProductionPlanDetail detail = new MesRepairProductionPlanDetail();
		detail.setRepairPlanId(planId);
		detail.setOrderRepairId(repairId);
		dao.save(detail);
		MesRepairProductionPlan plan = dao.findById(MesRepairProductionPlan.class, planId);
		Integer num = plan.getNum();
		if(num!=null){
			num = num+1;
		}else{
			num = 1;
		}
		plan.setNum(num);
		dao.update(plan);
		OrderRepair o = dao.findById(OrderRepair.class, repairId);
		o.setOrderRepairStatus("4");
		dao.update(o); 
		Criteria criteria = dao.createCriteria(MesRepairProductionPlanDetail.class);
		criteria.add(Restrictions.eq("repairPlanId", planId));
		@SuppressWarnings("unchecked")
		List<MesRepairProductionPlanDetail> list = criteria.list();
		List<OrderRepairModel> orderRepairList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			OrderRepairModel models = new OrderRepairModel();
			OrderRepair repair = dao.findById(OrderRepair.class, list.get(i).getOrderRepairId());
			BeanUtils.copyProperties(repair,models);
			orderRepairList.add(models);
		}
		return new Result(orderRepairList);
	}
	
	@Override
	public Result orderRepairDel(String planId,String repairId){
		Criteria criteria = dao.createCriteria(MesRepairProductionPlanDetail.class);
		criteria.add(Restrictions.eq("repairPlanId", planId));
		criteria.add(Restrictions.eq("orderRepairId", repairId));
		@SuppressWarnings("unchecked")
		List<MesRepairProductionPlanDetail> list = criteria.list();
		if(list!=null){
			MesRepairProductionPlanDetail detail = list.get(0);
			MesRepairProductionPlan plan = dao.findById(MesRepairProductionPlan.class, planId);
			Integer num = plan.getNum();
			plan.setNum(num-1);
			dao.update(plan); 
			OrderRepair o = dao.findById(OrderRepair.class, repairId);
			o.setOrderRepairStatus("8");
			dao.update(o);
			dao.delete(detail);
			return new Result("200","删除成功");
		}else{
			return new Result("500","微调单不存在");
		}
	}
	
	@Override
	public Result materielSave(MaterialsSaveDto dto){
		Criteria criteria = dao.createCriteria(MesRepairProductionPlanMateriel.class);
		criteria.add(Restrictions.eq("orderRepairId", dto.getOrderRepairId()));
		@SuppressWarnings("unchecked")
		List<MesRepairProductionPlanMateriel> list = criteria.list();
		if(list!=null){
			for (int j = 0; j < list.size(); j++) {
				dao.delete(list.get(j));
			}
		}
		for (int i = 0; i < dto.getMaterielList().size(); i++) {
			MesRepairProductionPlanMateriel materiel = new MesRepairProductionPlanMateriel();
			materiel.setOrderRepairId(dto.getOrderRepairId());
			materiel.setMaterielId(dto.getMaterielList().get(i).getId());
			materiel.setMaterielCode(dto.getMaterielList().get(i).getMaterialCode());
			materiel.setMaterielName(dto.getMaterielList().get(i).getMaterialName());
			materiel.setNum(dto.getMaterielList().get(i).getNum());
			materiel.setPosition(dto.getMaterielList().get(i).getPosition());
			materiel.setSpecifications(dto.getMaterielList().get(i).getSpecifications());
			materiel.setUnit(dto.getMaterielList().get(i).getUnit());
			materiel.setTexture(dto.getMaterielList().get(i).getTexture());
			materiel.setColor(dto.getMaterielList().get(i).getColor());
			materiel.setColorCode(dto.getMaterielList().get(i).getColorCode()); 
			materiel.setColorValue(dto.getMaterielList().get(i).getColorValue());
			dao.save(materiel);
		}
		return new Result(list);
	}
	
	@Override
	public Result repairOpinionSave(String repairId,String repairOpinion){
		MesRepairProductionPlanDetail detail = dao.findUniqueByProperty(MesRepairProductionPlanDetail.class, "orderRepairId", repairId);
		detail.setRepairOpinion(repairOpinion);
		dao.update(detail);
		return new Result(detail);
	}
	
	@Override
	public Result del(String id){
		MesRepairProductionPlan plan = dao.findById(MesRepairProductionPlan.class, id);
		Criteria criteria = dao.createCriteria(MesRepairProductionPlanDetail.class);
		criteria.add(Restrictions.eq("repairPlanId", plan.getId()));
		@SuppressWarnings("unchecked")
		List<MesRepairProductionPlanDetail> list = criteria.list();
		if(list!=null && list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				OrderRepair o = dao.findById(OrderRepair.class, list.get(i).getOrderRepairId());
				o.setOrderRepairStatus("8");
				dao.update(o);
				Criteria criteria1 = dao.createCriteria(MesRepairProductionPlanMateriel.class);
				criteria1.add(Restrictions.eq("orderRepairId", plan.getId()));
				@SuppressWarnings("unchecked")
				List<MesRepairProductionPlanMateriel> list1 = criteria1.list();
				if(list1!=null && list1.size()>0){
					for (int j = 0; j < list1.size(); j++) {
						dao.delete(list1.get(j));
					}
				}
				dao.delete(list.get(i)); 
			}
		}
		plan.setDel("1");
		dao.update(plan);
		return new Result("200","删除成功！");
	}
	
	@Override
	public Result getInfromationById(String id){
		MesRepairProductionPlanModel model = new MesRepairProductionPlanModel();
		MesRepairProductionPlan plan = dao.findById(MesRepairProductionPlan.class, id);
		BeanUtils.copyProperties(plan,model);
		Criteria criteria = dao.createCriteria(MesRepairProductionPlanDetail.class);
		criteria.add(Restrictions.eq("repairPlanId", plan.getId()));
		@SuppressWarnings("unchecked")
		List<MesRepairProductionPlanDetail> list = criteria.list();
		List<OrderRepairModel> orderRepairList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			OrderRepairModel models = new OrderRepairModel();
			OrderRepair repair = dao.findById(OrderRepair.class, list.get(i).getOrderRepairId());
			BeanUtils.copyProperties(repair,models);
			OrderDetail detail = dao.findById(OrderDetail.class, repair.getOrderDetailId());
			if(detail!=null){
				models.setGoodsSn(detail.getGoodsSn());
			}
			models.setRepairOpinion(list.get(i).getRepairOpinion());
			models.setRepairFeedback(list.get(i).getRepairFeedback());
			Criteria criteria1 = dao.createCriteria(OrderRepairCheck.class);
			criteria1.add(Restrictions.eq("orderRepairId", list.get(i).getOrderRepairId()));
			@SuppressWarnings("unchecked")
			List<OrderRepairCheck> list1 = criteria1.list();
			if(list1!=null){
				models.setCheckList(list1);
			}
			orderRepairList.add(models);
		}
		model.setOrderRepairList(orderRepairList);
		return new Result(model);
	}
	
	@Override
	public Result getMeterielInfromation(String repairId){
		Criteria criteria = dao.createCriteria(MesRepairProductionPlanMateriel.class);
		criteria.add(Restrictions.eq("orderRepairId", repairId));
		@SuppressWarnings("unchecked")
		List<MesRepairProductionPlanMateriel> list = criteria.list();
		if(list!=null && list.size()>0){
			return new Result(list);
		}else{
			return new Result("500","该微调单下没有物料信息");
		}
	}
	
	@Override
	public Result check(RepairPlanCheckDto dto){
		MesRepairProductionPlan plan = dao.findById(MesRepairProductionPlan.class, dto.getPlanId());
		if(StringUtils.isNotBlank(dto.getPlanStatus())){
			plan.setStatus(dto.getPlanStatus());
			if("4".equals(dto.getPlanStatus())){
				Criteria criteria = dao.createCriteria(MesRepairProductionPlanDetail.class);
				criteria.add(Restrictions.eq("repairPlanId", dto.getPlanId()));
				@SuppressWarnings("unchecked")
				List<MesRepairProductionPlanDetail> list = criteria.list();
				if(list!=null && list.size()>0){
					for (int i = 0; i < list.size(); i++) {
						OrderRepair o = dao.findById(OrderRepair.class, list.get(i).getOrderRepairId());
						o.setOrderRepairStatus("5");
						dao.update(o);
					}
					
				}
				plan.setReleases(dto.getUserName());
				plan.setReleaseTime(new Date());
			}
			dao.update(plan);
		}
		if(StringUtils.isNotBlank(dto.getCheckStatus())){
			MesRepairProductionPlanCheck check = new MesRepairProductionPlanCheck();
			check.setRepairPlanId(dto.getPlanId());
			check.setStatus(dto.getCheckStatus());
			check.setRemark(dto.getRemark());
			check.setCheckUserId(dto.getUserId());
			check.setCheckUserName(dto.getUserName());
			check.setCheckTime(new Date());
			dao.save(check);
			if("0".equals(dto.getCheckStatus())){
				plan.setStatus("2");
			}
			if("1".equals(dto.getCheckStatus())){
				plan.setStatus("3");
			}
			plan.setConfirmer(dto.getUserName());
			plan.setConfirmTime(new Date());
			dao.update(plan);
		}
		return new Result("200","操作成功！");
	}
	
	@Override
	public Pagination<Map<String,Object>> repayList(MesRepairProductionPlanDto dto,PageBean pageBean){
		Pagination<Map<String, Object>> pagination=new Pagination<>();
		List<Map<String,Object>> list = new ArrayList<>();
		Pagination<MesRepairProductionPlanCopy> planPagination =  findRepairPlanByPage(dto, pageBean);
		if (planPagination.getRows()!=null && planPagination.getRows().size()>0){
			for(MesRepairProductionPlanCopy plan : planPagination.getRows()){
				 Map<String,Object> map = new LinkedHashMap<>();
	             if (plan.getDetailList()!=null){
	            	 map.put("repairPlan",plan);
	                 map.put("orderPlanDetails",plan.getDetailList());
	             }
	             list.add(map);
			}
		}
		pagination.setTotal(planPagination.getTotal());
        pagination.setRows(list);
        return pagination;
	}
	
	
	private Pagination<MesRepairProductionPlanCopy> findRepairPlanByPage(MesRepairProductionPlanDto dto,PageBean pageBean){
		boolean isCreateLL = false;
		Criteria criteria=dao.createCriteria(MesRepairProductionPlanCopy.class);
		if(StringUtils.isNotBlank(dto.getPlanCode())){
			criteria.add(Restrictions.like("planCode", dto.getPlanCode().trim(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(dto.getStatus())){
			criteria.add(Restrictions.eq("status", dto.getStatus()));
		}
		if(StringUtils.isNotBlank(dto.getRepairStatus())){
			criteria.createCriteria("detailList", "ll",JoinType.LEFT_OUTER_JOIN).createCriteria("repair", "o",JoinType.LEFT_OUTER_JOIN);
			isCreateLL=true;
			criteria.add(Restrictions.eq("o.orderRepairStatus", dto.getRepairStatus()));
		}
		if(StringUtils.isNotBlank(dto.getRepairOrderCode())){
			if (!isCreateLL){
				criteria.createCriteria("detailList", "ll",JoinType.LEFT_OUTER_JOIN).createCriteria("repair", "o",JoinType.LEFT_OUTER_JOIN);
			}
			criteria.add(Restrictions.eq("o.orderRepairCode", dto.getRepairOrderCode()));
		}
		if(dto.getPlanDateStr()!=null && dto.getPlanDateEnd()!=null){
			criteria.add(Restrictions.between("planDate", dto.getPlanDateStr(), dto.getPlanDateEnd()));
		}else if(dto.getPlanDateStr()!=null && dto.getPlanDateEnd()==null){
			criteria.add(Restrictions.ge("planDate", dto.getPlanDateStr()));
		}else if(dto.getPlanDateStr()==null && dto.getPlanDateEnd()!=null){
			criteria.add(Restrictions.le("planDate", dto.getPlanDateEnd()));
		}
		criteria.add(Restrictions.eq("del", "0"));
		criteria.addOrder(Order.desc("planDate"));
		List<MesRepairProductionPlanCopy> list = dao.findListWithPagebeanCriteria(criteria, pageBean);
		for (int k = 0; k < list.size(); k++) {
			if(list.get(k).getDetailList()!=null){
				for (int s = 0; s < list.get(k).getDetailList().size(); s++) {
					OrderRepair or = list.get(k).getDetailList().get(s).getRepair();
					if(StringUtils.isNotBlank(or.getOrderDetailId())){
						OrderDetail od = dao.findById(OrderDetail.class, or.getOrderDetailId());
						or.setGoodsSn(od.getGoodsSn());
					}
				}
			}
		}
//		if(StringUtils.isNotBlank(dto.getRepairStatus())){
//			for (int i = 0; i < list.size() ; i++) {
//				for (int j = list.get(i).getDetailList().size() - 1; j >=0; j--) {
//					if(!dto.getRepairStatus().equals(list.get(i).getDetailList().get(j).getRepair().getOrderRepairStatus())){
//						list.get(i).getDetailList().remove(j);
//					}
//				}
//			}
//		}
//		if(StringUtils.isNotBlank(dto.getRepairOrderCode())){
//			for (int i = 0; i < list.size() ; i++) {
//				for (int j = list.get(i).getDetailList().size() - 1; j >= 0; j--) {
//					if(!dto.getRepairOrderCode().equals(list.get(i).getDetailList().get(j).getRepair().getOrderRepairCode())){
//						list.get(i).getDetailList().remove(j);
//					}
//				}
//			}
//		}

		Pagination<MesRepairProductionPlanCopy> pagination=new Pagination<>();
		pagination.setRows(list);
		pagination.setTotal(dao.findTotalWithCriteria(criteria));
		return pagination;
	}
	
	@Override
	public Result checkRepairStatus(String repairId,String status){
		OrderRepair o = dao.findById(OrderRepair.class, repairId);
		if("6".equals(status)){
			o.setOrderRepairStatus(status);
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = formatter.format(date);
			o.setFinishDate(dateString);
			dao.update(o);
			//判断计划单下的微调单状态是否都是生产完成
			MesRepairProductionPlanDetail detail = dao.findUniqueByProperty(MesRepairProductionPlanDetail.class, "orderRepairId", repairId);
			Criteria criteria = dao.createCriteria(MesRepairProductionPlanDetail.class);
			criteria.add(Restrictions.eq("repairPlanId", detail.getRepairPlanId()));
			@SuppressWarnings("unchecked")
			List<MesRepairProductionPlanDetail> list = criteria.list();
			Boolean b = true;
			for (int i = 0; i < list.size(); i++) {
				OrderRepair op = dao.findById(OrderRepair.class, list.get(i).getOrderRepairId());
				if(!"6".equals(op.getOrderRepairStatus()) && !"7".equals(op.getOrderRepairStatus())){
					b = false;
					break;
				}
			}
//			Boolean b = null;
//			for (int i = 0; i < list.size(); i++) {
//				OrderRepair op = dao.findById(OrderRepair.class, list.get(i).getOrderRepairId());
//				if("6".equals(op.getOrderRepairStatus())){
//					b = true;
//				}else{
//					b = false;
//				}
//			}
			//如果微调状态全部为生产完成，则把计划单的状态改为生产完成
			if(b==true){
				MesRepairProductionPlan plan = dao.findById(MesRepairProductionPlan.class, detail.getRepairPlanId());
				plan.setStatus("5");
				dao.update(plan);
			}
		}else if("5".equals(status)){
			o.setOrderRepairStatus(status);
			o.setFinishDate("");
			dao.update(o);
			MesRepairProductionPlanDetail detail = dao.findUniqueByProperty(MesRepairProductionPlanDetail.class, "orderRepairId", repairId);
			MesRepairProductionPlan plan = dao.findById(MesRepairProductionPlan.class, detail.getRepairPlanId());
			plan.setStatus("4");
			dao.update(plan);
		}
		return new Result(o);
	}
	
	@Override
	public Result repairFeedbackSave(String repairId,String repairFeedback){
		MesRepairProductionPlanDetail detail = dao.findUniqueByProperty(MesRepairProductionPlanDetail.class, "orderRepairId", repairId);
		detail.setRepairFeedback(repairFeedback);
		dao.update(detail);
		return new Result(detail);
	}
	
	@Override
	public Result rejectPlan(RepairPlanCheckDto dto){
		String orderRepairStatus;
		MesRepairProductionPlan plan = dao.findById(MesRepairProductionPlan.class, dto.getPlanId());
		plan.setStatus(dto.getPlanStatus());
		MesRepairProductionPlanCheck check = new MesRepairProductionPlanCheck();
		check.setRepairPlanId(dto.getPlanId());
		check.setStatus(dto.getCheckStatus());
		check.setRemark(dto.getRemark());
		check.setCheckUserName(dto.getUserName());
		check.setCheckTime(new Date());
		dao.save(check);
		Criteria criteria = dao.createCriteria(MesRepairProductionPlanDetail.class);
		criteria.add(Restrictions.eq("repairPlanId", dto.getPlanId()));
		@SuppressWarnings("unchecked")
		List<MesRepairProductionPlanDetail> list = criteria.list();
		for (int i = 0; i < list.size(); i++) {
			OrderRepair o = dao.findById(OrderRepair.class, list.get(i).getOrderRepairId());
            orderRepairStatus = o.getOrderRepairStatus();
            if (StringUtils.isNotBlank(orderRepairStatus)){
            	if (Integer.parseInt(orderRepairStatus) > 5){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return new Result("500","微调单:"+o.getOrderRepairCode()+" 的状态不允许驳回！");
				}
			}

			o.setOrderRepairStatus("4");
			dao.update(o); 
		}
		return new Result("200","操作成功！");
		
	}
	
	private String getOrderMaxValue() {
		Date date = new Date();
		Criteria criteria = dao.createCriteria(Code.class);
		criteria.add(Restrictions.between("updateTime", DateUtil.minDate(date), DateUtil.maxDate(date)));
		criteria.add(Restrictions.eq("type", "RPP"));
		criteria.addOrder(org.hibernate.criterion.Order.desc("maxValue"));
		criteria.setMaxResults(1);
		Code code = (Code) criteria.uniqueResult();
		if (code == null) {
			code = new Code();
			code.setMaxValue(1);
			code.setType("RPP");
			code.setUpdateTime(date);
			dao.save(code);
		} else {
			code.setMaxValue(code.getMaxValue() + 1);
		}
		return String.format("%0" + 6 + "d", code.getMaxValue());
	}
}
