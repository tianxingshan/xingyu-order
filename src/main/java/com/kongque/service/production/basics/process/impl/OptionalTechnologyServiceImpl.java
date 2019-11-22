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
import com.kongque.entity.process.GoodsOptionalTechnology;
import com.kongque.entity.process.OptionalTechnology;
import com.kongque.service.production.basics.process.IOptionalTechnologyService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;
import com.kongque.util.StringUtils;

@Service
public class OptionalTechnologyServiceImpl implements IOptionalTechnologyService{
	
	@Resource
	private IDaoService dao;
	
	@Override
	public Pagination<OptionalTechnology> list(OptionalTechnology technology, PageBean pageBean){
		Criteria criteria=dao.createCriteria(OptionalTechnology.class);
		if(StringUtils.isNotBlank(technology.getCode())){
			criteria.add(Restrictions.like("code", technology.getCode().trim(), MatchMode.ANYWHERE));
		}
		criteria.add(Restrictions.eq("del", "0"));
		Pagination<OptionalTechnology> pagination=new Pagination<>();
		pagination.setRows(dao.findListWithPagebeanCriteria(criteria, pageBean));
		pagination.setTotal(dao.findTotalWithCriteria(criteria));
		return pagination;
	}
	
	@Override
	public Result saveOrUpdate(ProductionBasicDto dto){
		OptionalTechnology technology = new OptionalTechnology();
		if(StringUtils.isNotBlank(dto.getId())){
			technology = dao.findById(OptionalTechnology.class, dto.getId());
			if(technology.getCode().equals(dto.getCode())){
				BeanUtils.copyProperties(dto, technology);
				dao.update(technology);
			}else{
				Boolean b = removal(dto);
				if(b == true){
					BeanUtils.copyProperties(dto, technology);
					dao.update(technology);
				}else{
					return new Result("500","该可选项工艺下编号已存在！");
				}
			}
		}else{
			Boolean b = removal(dto);
			if(b == true){
				BeanUtils.copyProperties(dto, technology);
				technology.setDel("0");
				dao.save(technology);
			}else{
				return new Result("500","该可选项工艺下编号已存在！");
			}
		}
		return new Result(technology);
	}
	
	@Override
	public Result del(String id){
		Criteria criteria = dao.createCriteria(GoodsOptionalTechnology.class);
		criteria.add(Restrictions.eq("optionalTechnologyId", id));
		@SuppressWarnings("unchecked")
		List<GoodsOptionalTechnology> list = criteria.list();
		if(list!=null && list.size()>0){
			return new Result("500","该可选项工艺已经被使用，不可删除！");
		}else{
			OptionalTechnology technology = dao.findById(OptionalTechnology.class, id);
			technology.setDel("1");
			dao.update(technology);
			return new Result("200","删除成功！");
		}
	}
	
	
	public Boolean removal(ProductionBasicDto dto){
		Criteria criteria = dao.createCriteria(OptionalTechnology.class);
		criteria.add(Restrictions.eq("code", dto.getCode()));
		criteria.add(Restrictions.eq("del", "0"));
		@SuppressWarnings("unchecked") 
		List<OptionalTechnology> list = criteria.list();
		if(list!=null && list.size()>0){
			return false;
		}else{
			return true;
		}
	}

}
