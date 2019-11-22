package com.kongque.service.production.basics.process.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.kongque.dao.IDaoService;
import com.kongque.dto.ProductionBasicDto;
import com.kongque.entity.process.GoodsSewProcess;
import com.kongque.entity.process.MesProcess;
import com.kongque.entity.process.ProcessPosition;
import com.kongque.entity.productionorder.MesOrderDetailSewingProcess;
import com.kongque.service.production.basics.process.IProcessPositionService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;
import com.kongque.util.StringUtils;

@Service
public class ProcessPositionServiceImpl implements IProcessPositionService{
	
	@Resource
	private IDaoService dao;
	
	@Override
	public Pagination<ProcessPosition> list(ProcessPosition position, PageBean pageBean){
		Criteria criteria=dao.createCriteria(ProcessPosition.class);
		if(StringUtils.isNotBlank(position.getCategoryId())){
			criteria.add(Restrictions.eq("categoryId", position.getCategoryId()));
		}
		if(StringUtils.isNotBlank(position.getCode())){
			criteria.add(Restrictions.like("code", position.getCode().trim(), MatchMode.ANYWHERE));
		}
		criteria.add(Restrictions.eq("del", "0"));
		Pagination<ProcessPosition> pagination=new Pagination<>();
		pagination.setRows(dao.findListWithPagebeanCriteria(criteria, pageBean));
		pagination.setTotal(dao.findTotalWithCriteria(criteria));
		return pagination;
	}
	
	@Override
	public Result saveOrUpdate(ProductionBasicDto dto){
		ProcessPosition position = new ProcessPosition();
		if(StringUtils.isNotBlank(dto.getId())){
			position = dao.findById(ProcessPosition.class, dto.getId());
			if(position.getCode().equals(dto.getCode())){
				BeanUtils.copyProperties(dto, position);
				dao.update(position);
			}else{
				Boolean b = removal(dto);
				if(b == true){
					BeanUtils.copyProperties(dto, position);
					dao.update(position);
				}else{
					return new Result("500","该部位下尺码编号已存在！");
				}
			}
		}else{
			Boolean b = removal(dto);
			if(b == true){
				BeanUtils.copyProperties(dto, position);
				position.setDel("0");
				dao.save(position);
			}else{
				return new Result("500","该部位下尺码编号已存在！");
			}
		}
		return new Result(position);
	}
	
	@Override
	public Result del(String id){
		Criteria criteria = dao.createCriteria(MesProcess.class);
		criteria.add(Restrictions.eq("processPositionId", id));
		@SuppressWarnings("unchecked")
		List<MesProcess> list = criteria.list();
		Criteria criteria1 = dao.createCriteria(GoodsSewProcess.class);
		criteria1.add(Restrictions.eq("processPositionId", id));
		@SuppressWarnings("unchecked")
		List<GoodsSewProcess> list1 = criteria1.list();
		Criteria criteria2 = dao.createCriteria(MesOrderDetailSewingProcess.class);
		criteria2.add(Restrictions.eq("processPositionId", id));
		@SuppressWarnings("unchecked")
		List<MesOrderDetailSewingProcess> list2 = criteria2.list();
		if(list!=null && list.size()>0 && list1!=null && list1.size()>0 && list2!=null && list2.size()>0){
			return new Result("500","该工序部位已经被使用，不可删除！");
		}else{
			ProcessPosition position = dao.findById(ProcessPosition.class, id);
			position.setDel("1");
			dao.update(position);
			return new Result("200","删除成功！");
		}
	}
	
	
	public Boolean removal(ProductionBasicDto dto){
		Criteria criteria = dao.createCriteria(ProcessPosition.class);
		criteria.add(Restrictions.eq("categoryId", dto.getCategoryId()));
		criteria.add(Restrictions.eq("code", dto.getCode()));
		criteria.add(Restrictions.eq("del", "0"));
		@SuppressWarnings("unchecked") 
		List<ProcessPosition> list = criteria.list();
		if(list!=null && list.size()>0){
			return false;
		}else{
			return true;
		}
	}

}
