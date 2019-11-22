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
import com.kongque.entity.process.MesProcess;
import com.kongque.entity.process.MesProcessThread;
import com.kongque.service.production.basics.process.IProcessThreadService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;
import com.kongque.util.StringUtils;

@Service
public class ProcessThreadServiceImpl implements IProcessThreadService{
	
	@Resource
	private IDaoService dao;

	@Override
	public Pagination<MesProcessThread> list(MesProcessThread thread, PageBean pageBean){
		Criteria criteria=dao.createCriteria(MesProcessThread.class);
		if(StringUtils.isNotBlank(thread.getCode())){
			criteria.add(Restrictions.like("code", thread.getCode().trim(), MatchMode.ANYWHERE));
		}
		criteria.add(Restrictions.eq("del", "0"));
		Pagination<MesProcessThread> pagination=new Pagination<>();
		pagination.setRows(dao.findListWithPagebeanCriteria(criteria, pageBean));
		pagination.setTotal(dao.findTotalWithCriteria(criteria));
		return pagination;
	}
	
	@Override
	public Result saveOrUpdate(ProductionBasicDto dto){
		MesProcessThread thread = new MesProcessThread();
		if(StringUtils.isNotBlank(dto.getId())){
			thread = dao.findById(MesProcessThread.class, dto.getId());
			if(thread.getCode().equals(dto.getCode())){
				BeanUtils.copyProperties(dto, thread);
				dao.update(thread);
			}else{
				Boolean b = removal(dto);
				if(b == true){
					BeanUtils.copyProperties(dto, thread);
					dao.update(thread);
				}else{
					return new Result("500","该线下编号已存在！");
				}
			}
			dao.update(thread);
		}else{
			Boolean b = removal(dto);
			if(b == true){
				BeanUtils.copyProperties(dto, thread);
				thread.setDel("0");
				dao.save(thread);
			}else{
				return new Result("500","该线下编号已存在！");
			}
		}
		return new Result(thread);
	}
	
	@Override
	public Result del(String id){
		Criteria criteria = dao.createCriteria(MesProcess.class);
		criteria.add(Restrictions.eq("threadId", id));
		@SuppressWarnings("unchecked")
		List<MesProcess> list = criteria.list();
		if(list!=null && list.size()>0){
			return new Result("500","该线已经被使用，不可删除！");
		}else{
			MesProcessThread thread = dao.findById(MesProcessThread.class, id);
			thread.setDel("1");
			dao.update(thread);
			return new Result("200","删除成功！");
		}
		
	}
	
	
	public Boolean removal(ProductionBasicDto dto){
		Criteria criteria = dao.createCriteria(MesProcessThread.class);
		criteria.add(Restrictions.eq("code", dto.getCode()));
		criteria.add(Restrictions.eq("del", "0"));
		@SuppressWarnings("unchecked") 
		List<MesProcessThread> list = criteria.list();
		if(list!=null && list.size()>0){
			return false;
		}else{
			return true;
		}
	}
}
