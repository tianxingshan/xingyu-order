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
import com.kongque.entity.process.MesProcessMachine;
import com.kongque.entity.process.MesProcess;
import com.kongque.entity.process.MesProcessNeedleRange;
import com.kongque.service.production.basics.process.IProcessNeedleRangeService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;
import com.kongque.util.StringUtils;

@Service
public class ProcessNeedleRangeServiceImpl implements IProcessNeedleRangeService{
	
	@Resource
	private IDaoService dao;

	@Override
	public Pagination<MesProcessNeedleRange> list(MesProcessNeedleRange needleRange, PageBean pageBean){
		Criteria criteria=dao.createCriteria(MesProcessNeedleRange.class);
		if(StringUtils.isNotBlank(needleRange.getCode())){
			criteria.add(Restrictions.like("code", needleRange.getCode().trim(), MatchMode.ANYWHERE));
		}
		criteria.add(Restrictions.eq("del", "0"));
		Pagination<MesProcessNeedleRange> pagination=new Pagination<>();
		pagination.setRows(dao.findListWithPagebeanCriteria(criteria, pageBean));
		pagination.setTotal(dao.findTotalWithCriteria(criteria));
		return pagination;
	}
	
	@Override
	public Result saveOrUpdate(ProductionBasicDto dto){
		MesProcessNeedleRange needleRange = new MesProcessNeedleRange();
		if(StringUtils.isNotBlank(dto.getId())){
			needleRange = dao.findById(MesProcessNeedleRange.class, dto.getId());
			if(needleRange.getCode().equals(dto.getCode())){
				BeanUtils.copyProperties(dto, needleRange);
				dao.update(needleRange);
			}else{
				Boolean b = removal(dto);
				if(b == true){
					BeanUtils.copyProperties(dto, needleRange);
					dao.update(needleRange);
				}else{
					return new Result("500","该针幅下编号已存在！");
				}
			}
		}else{
			Boolean b = removal(dto);
			if(b == true){
				BeanUtils.copyProperties(dto, needleRange);
				needleRange.setDel("0");
				dao.save(needleRange);
			}else{
				return new Result("500","该针幅下编号已存在！");
			}
		}
		return new Result(needleRange);
	}
	
	@Override
	public Result del(String id){
		Criteria criteria = dao.createCriteria(MesProcess.class);
		criteria.add(Restrictions.eq("needleRangeId", id));
		@SuppressWarnings("unchecked")
		List<MesProcess> list = criteria.list();
		if(list!=null && list.size()>0){
			return new Result("500","该针幅已经被使用，不可删除！");
		}else{
			MesProcessNeedleRange needleRange = dao.findById(MesProcessNeedleRange.class, id);
			needleRange.setDel("1");
			dao.update(needleRange);
			return new Result("200","删除成功！");
		}
	}
	
	
	public Boolean removal(ProductionBasicDto dto){
		Criteria criteria = dao.createCriteria(MesProcessNeedleRange.class);
		criteria.add(Restrictions.eq("code", dto.getCode()));
		criteria.add(Restrictions.eq("del", "0"));
		@SuppressWarnings("unchecked") 
		List<MesProcessMachine> list = criteria.list();
		if(list!=null && list.size()>0){
			return false;
		}else{
			return true;
		}
	}
}
