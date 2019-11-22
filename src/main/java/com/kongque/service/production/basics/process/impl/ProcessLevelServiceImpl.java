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
import com.kongque.entity.process.MesProcessLevel;
import com.kongque.service.production.basics.process.IProcessLevelService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;
import com.kongque.util.StringUtils;

@Service
public class ProcessLevelServiceImpl implements IProcessLevelService{
	
	@Resource
	private IDaoService dao;

	@Override
	public Pagination<MesProcessLevel> list(MesProcessLevel level, PageBean pageBean){
		Criteria criteria=dao.createCriteria(MesProcessLevel.class);
		if(StringUtils.isNotBlank(level.getCode())){
			criteria.add(Restrictions.like("code", level.getCode().trim(), MatchMode.ANYWHERE));
		}
		criteria.add(Restrictions.eq("del", "0"));
		Pagination<MesProcessLevel> pagination=new Pagination<>();
		pagination.setRows(dao.findListWithPagebeanCriteria(criteria, pageBean));
		pagination.setTotal(dao.findTotalWithCriteria(criteria));
		return pagination;
	}
	
	@Override
	public Result saveOrUpdate(ProductionBasicDto dto){
		MesProcessLevel level = new MesProcessLevel();
		if(StringUtils.isNotBlank(dto.getId())){
			level = dao.findById(MesProcessLevel.class, dto.getId());
			if(level.getCode().equals(dto.getCode())){
				BeanUtils.copyProperties(dto, level);
				dao.update(level);
			}else{
				Boolean b = removal(dto);
				if(b == true){
					BeanUtils.copyProperties(dto, level);
					dao.update(level);
				}else{
					return new Result("500","该等级下编号已存在！");
				}
			}
		}else{
			Boolean b = removal(dto);
			if(b == true){
				BeanUtils.copyProperties(dto, level);
				level.setDel("0");
				dao.save(level);
			}else{
				return new Result("500","该等级下编号已存在！");
			}
		}
		return new Result(level);
	}
	
	@Override
	public Result del(String id){
		Criteria criteria = dao.createCriteria(MesProcess.class);
		criteria.add(Restrictions.eq("levelId", id));
		@SuppressWarnings("unchecked")
		List<MesProcess> list = criteria.list();
		if(list!=null && list.size()>0){
			return new Result("500","该等级已经被使用，不可删除！");
		}else{
			MesProcessLevel level = dao.findById(MesProcessLevel.class, id);
			level.setDel("1");
			dao.update(level);
			return new Result("200","删除成功！");
		}
	}
	
	
	public Boolean removal(ProductionBasicDto dto){
		Criteria criteria = dao.createCriteria(MesProcessLevel.class);
		criteria.add(Restrictions.eq("code", dto.getCode()));
		criteria.add(Restrictions.eq("del", "0"));
		@SuppressWarnings("unchecked") 
		List<MesProcessLevel> list = criteria.list();
		if(list!=null && list.size()>0){
			return false;
		}else{
			return true;
		}
	}
}
