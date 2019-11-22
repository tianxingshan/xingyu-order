package com.kongque.service.production.basics.measure.impl;

import java.util.List;

import javax.annotation.Resource;

import com.kongque.constants.Constants;
import com.kongque.entity.measure.GoodsMeasurePositionSize;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.kongque.dao.IDaoService;
import com.kongque.dto.ProductionBasicDto;
import com.kongque.entity.measure.MeasureSize;
import com.kongque.service.production.basics.measure.IMeasureSizeService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;
import com.kongque.util.StringUtils;

@Service
public class MeasureSizeServiceImpl implements IMeasureSizeService{
	
	@Resource
	private IDaoService dao;
	
	@Override
	public Pagination<MeasureSize> list(MeasureSize size, PageBean pageBean){
		Criteria criteria=dao.createCriteria(MeasureSize.class);
		if(StringUtils.isNotBlank(size.getCategoryId())){
			criteria.add(Restrictions.eq("categoryId", size.getCategoryId()));
		}
		if(StringUtils.isNotBlank(size.getCode())){
			criteria.add(Restrictions.like("code", size.getCode().trim(), MatchMode.ANYWHERE));
		}
		criteria.add(Restrictions.eq("del", "0"));
		criteria.addOrder(Order.asc("sort"));
		Pagination<MeasureSize> pagination=new Pagination<>();
		pagination.setRows(dao.findListWithPagebeanCriteria(criteria, pageBean));
		pagination.setTotal(dao.findTotalWithCriteria(criteria));
		return pagination;
	}
	
	@Override
	public Result saveOrUpdate(ProductionBasicDto dto){
		MeasureSize size = new MeasureSize();
		if(StringUtils.isNotBlank(dto.getId())){
			size = dao.findById(MeasureSize.class, dto.getId());
			if(size.getCode().equals(dto.getCode())){
				BeanUtils.copyProperties(dto, size);
				dao.update(size);
			}else{
				Boolean b = removal(dto);
				if(b == false){
					BeanUtils.copyProperties(dto, size);
					dao.update(size);
				}else{
					return new Result("500","该品类下尺码编号已存在！");
				}
			}
		}else{
			Boolean b = removal(dto);
			if(b == false){
				BeanUtils.copyProperties(dto, size);
				size.setDel("0");
				dao.save(size);
			}else{
				return new Result("500","该品类下尺码编号已存在！");
			}
		}
		return new Result(size);
	}
	
	@Override
	public Result delSize(String id){
		Criteria criteria = dao.createCriteria(GoodsMeasurePositionSize.class)
				.add(Restrictions.eq("measureSizeId",id))
				.add(Restrictions.eq("del", Constants.ENABLE_FLAG.ENABLE));
		List list = criteria.list();
        if (null!=list && list.size()>0){
			return new Result(Constants.RESULT_CODE.SYS_ERROR,"该尺码已被使用！");
		}
		MeasureSize size = dao.findById(MeasureSize.class, id);
		size.setDel("1");
		dao.update(size);
		return new Result("200","删除成功！");
	}
	
	
	public Boolean removal(ProductionBasicDto dto){
		Criteria criteria = dao.createCriteria(MeasureSize.class);
		criteria.add(Restrictions.eq("categoryId", dto.getCategoryId()));
		criteria.add(Restrictions.eq("code", dto.getCode()));
		criteria.add(Restrictions.eq("del", "0"));
		@SuppressWarnings("unchecked") 
		List<MeasureSize> list = criteria.list();
		Boolean b = false;
		if(list!=null && list.size()>0){
			b = true;
		}
		return b;
	}

}
