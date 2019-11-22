package com.kongque.service.production.basics.material.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.kongque.dao.IDaoService;
import com.kongque.dto.ProductionBasicDto;
import com.kongque.entity.material.MaterialPosition;
import com.kongque.service.production.basics.material.IMaterialPositionService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;
import com.kongque.util.StringUtils;

@Service
public class MaterialPositionServiceImpl implements IMaterialPositionService{

	@Resource
	private IDaoService dao;
	
	@Override
	public Pagination<MaterialPosition> list(MaterialPosition position, PageBean pageBean){
		Criteria criteria=dao.createCriteria(MaterialPosition.class);
		if(StringUtils.isNotBlank(position.getCategoryId())){
			criteria.add(Restrictions.eq("categoryId", position.getCategoryId()));
		}
		criteria.add(Restrictions.eq("del", "0"));
		criteria.addOrder(Order.asc("code"));
		Pagination<MaterialPosition> pagination=new Pagination<>();
		pagination.setRows(dao.findListWithPagebeanCriteria(criteria, pageBean));
		pagination.setTotal(dao.findTotalWithCriteria(criteria));
		return pagination;
	}
	
	@Override
	public Result saveOrUpdate(ProductionBasicDto dto){
		MaterialPosition position = new MaterialPosition();
		if(StringUtils.isNotBlank(dto.getId())){
			position = dao.findById(MaterialPosition.class, dto.getId());
			if(position.getCode().equals(dto.getCode())){
				BeanUtils.copyProperties(dto, position);
				dao.update(position);
			}else{
				Boolean b = removal(dto);
				if(b == true){
					BeanUtils.copyProperties(dto, position);
					dao.update(position);
				}else{
					return new Result("500","该品类下物料部位编号已存在！");
				}
			}
		}else{
			Boolean b = removal(dto);
			if(b == true){
				BeanUtils.copyProperties(dto, position);
				position.setDel("0");
				dao.save(position);
			}else{
				return new Result("500","该品类下物料部位编号已存在！");
			}
		}
		return new Result(position);
	}
	
	@Override
	public Result del(String id){
		MaterialPosition position = dao.findById(MaterialPosition.class, id);
		position.setDel("1");
		dao.update(position);
		return new Result("200","删除成功！");
	}
	
	
	public Boolean removal(ProductionBasicDto dto){
		Criteria criteria = dao.createCriteria(MaterialPosition.class);
		criteria.add(Restrictions.eq("categoryId", dto.getCategoryId()));
		criteria.add(Restrictions.eq("code", dto.getCode()));
		criteria.add(Restrictions.eq("del", "0"));
		@SuppressWarnings("unchecked") 
		List<MaterialPosition> list = criteria.list();
		if(list!=null && list.size()>0){
			return false;
		}else{
			return true;
		}
	}
}
