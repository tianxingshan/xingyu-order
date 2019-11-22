package com.kongque.service.production.basics.measure.impl;

import java.util.List;

import javax.annotation.Resource;

import com.kongque.constants.Constants;
import com.kongque.entity.measure.GoodsMeasurePositionSize;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.kongque.dao.IDaoService;
import com.kongque.dto.ProductionBasicDto;
import com.kongque.entity.measure.MeasurePosition;
import com.kongque.service.production.basics.measure.IMeasurePositionService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

@Service
public class MeasurePositionServiceImpl implements IMeasurePositionService{
	
	@Resource
	private IDaoService dao;
	
	@Override
	public Pagination<MeasurePosition> list(MeasurePosition position, PageBean pageBean){
		Criteria criteria=dao.createCriteria(MeasurePosition.class);
		if(StringUtils.isNotBlank(position.getCategoryId())){
			criteria.add(Restrictions.eq("categoryId", position.getCategoryId()));
		}
		if(StringUtils.isNotBlank(position.getCode())){
			criteria.add(Restrictions.like("code", position.getCode().trim(), MatchMode.ANYWHERE));
		}

		if(StringUtils.isNotBlank(position.getStatus())){
			criteria.add(Restrictions.eq("status",position.getStatus()));
		}
		criteria.add(Restrictions.eq("del", "0"));
		criteria.addOrder(Order.asc("code"));
		Pagination<MeasurePosition> pagination=new Pagination<>();
		pagination.setRows(dao.findListWithPagebeanCriteria(criteria, pageBean));
		pagination.setTotal(dao.findTotalWithCriteria(criteria));
		return pagination;
	}
	
	@Override
	public Result saveOrUpdate(ProductionBasicDto dto){
		MeasurePosition position = new MeasurePosition();
		if(StringUtils.isBlank(dto.getStatus())){
			dto.setStatus("0");
		}
		if(StringUtils.isNotBlank(dto.getId())){
			position = dao.findById(MeasurePosition.class, dto.getId());
			if(position.getCode().equals(dto.getCode())){
				BeanUtils.copyProperties(dto, position);
				dao.update(position);
			}else{
				Boolean b = removal(dto);
				if(b == false){
					BeanUtils.copyProperties(dto, position);
					dao.update(position);
				}else{
					return new Result("500","该品类下量体部位编号已存在！");
				}
			}
		}else{
			Boolean b = removal(dto);
			if(b == false){
				BeanUtils.copyProperties(dto, position);
				position.setDel("0");
				dao.save(position);
			}else{
				return new Result("500","该品类下量体部位编号已存在！");
			}
		}
		return new Result(position);
	}
	
	@Override
	public Result delPosition(String id){
		Criteria criteria = dao.createCriteria(GoodsMeasurePositionSize.class)
				.add(Restrictions.eq("measurePositionId",id))
				.add(Restrictions.eq("del", Constants.ENABLE_FLAG.ENABLE));
		List list = criteria.list();
		if (null!=list && list.size()>0){
			return new Result(Constants.RESULT_CODE.SYS_ERROR,"该部位已被使用！");
		}
		MeasurePosition position = dao.findById(MeasurePosition.class, id);
		position.setDel("1");
		dao.update(position);
		return new Result("200","删除成功！");
	}
	
	
	public Boolean removal(ProductionBasicDto dto){
		Criteria criteria = dao.createCriteria(MeasurePosition.class);
		criteria.add(Restrictions.eq("categoryId", dto.getCategoryId()));
		criteria.add(Restrictions.eq("code", dto.getCode()));
		criteria.add(Restrictions.eq("del", "0"));
		@SuppressWarnings("unchecked") 
		List<MeasurePosition> list = criteria.list();
		Boolean b = false;
		if(list!=null && list.size()>0){
			b = true;
		}
		return b;
	}

}
