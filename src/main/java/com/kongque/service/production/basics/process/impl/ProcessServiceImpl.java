package com.kongque.service.production.basics.process.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.kongque.dao.IDaoService;
import com.kongque.entity.process.GoodsSewProcess;
import com.kongque.entity.process.MesProcess;
import com.kongque.entity.productionorder.MesOrderDetailSewingProcess;
import com.kongque.service.production.basics.process.IProcessService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;
import com.kongque.util.StringUtils;

@Service
public class ProcessServiceImpl implements IProcessService{
	
	@Resource
	private IDaoService dao;
	
	@Override
	public Pagination<MesProcess> list(MesProcess process, PageBean pageBean){
		Criteria criteria=dao.createCriteria(MesProcess.class);
		if(StringUtils.isNotBlank(process.getProcessPositionId())){
			criteria.add(Restrictions.eq("processPositionId", process.getProcessPositionId()));
		}
		if(StringUtils.isNotBlank(process.getCode())){
			criteria.add(Restrictions.like("code", process.getCode().trim(), MatchMode.ANYWHERE));
		}
		Pagination<MesProcess> pagination=new Pagination<>();
		pagination.setRows(dao.findListWithPagebeanCriteria(criteria, pageBean));
		pagination.setTotal(dao.findTotalWithCriteria(criteria));
		return pagination;
	}
	
	@Override
	public Result saveOrUpdate(MesProcess process){
		if(StringUtils.isNotBlank(process.getId())){
			dao.update(process);
		}else{
			Boolean b = removal(process);
			if(b == true){
				dao.save(process);
			}else{
				return new Result("500","该工序下编号已存在！");
			}
		}
		return new Result(process);
	}
	
	@Override
	public Result del(String id){
		Criteria criteria = dao.createCriteria(MesOrderDetailSewingProcess.class);
		criteria.add(Restrictions.eq("processId", id));
		@SuppressWarnings("unchecked")
		List<MesOrderDetailSewingProcess> list = criteria.list();
		Criteria criteria1 = dao.createCriteria(GoodsSewProcess.class);
		criteria1.add(Restrictions.eq("processId", id));
		@SuppressWarnings("unchecked")
		List<GoodsSewProcess> list1 = criteria1.list();
		if(list!=null && list.size()>0 && list1!=null && list1.size()>0){
			return new Result("500","该工序已经被使用，不可删除！");
		}else{
			MesProcess process = dao.findById(MesProcess.class, id);
			dao.delete(process);
			return new Result("200","删除成功！");
		}
	}
	
	
	public Boolean removal(MesProcess process){
		Criteria criteria = dao.createCriteria(MesProcess.class);
		criteria.add(Restrictions.eq("processPositionId", process.getProcessPositionId()));
		criteria.add(Restrictions.eq("code", process.getCode()));
		@SuppressWarnings("unchecked") 
		List<MesProcess> list = criteria.list();
		if(list!=null && list.size()>0){
			return false;
		}else{
			return true;
		}
	}
}
