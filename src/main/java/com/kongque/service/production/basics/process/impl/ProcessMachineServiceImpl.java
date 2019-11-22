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
import com.kongque.service.production.basics.process.IProcessMachineService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;
import com.kongque.util.StringUtils;

@Service
public class ProcessMachineServiceImpl implements IProcessMachineService{
	
	@Resource
	private IDaoService dao;

	@Override
	public Pagination<MesProcessMachine> list(MesProcessMachine machine, PageBean pageBean){
		Criteria criteria=dao.createCriteria(MesProcessMachine.class);
		if(StringUtils.isNotBlank(machine.getCode())){
			criteria.add(Restrictions.like("code", machine.getCode().trim(), MatchMode.ANYWHERE));
		}
		criteria.add(Restrictions.eq("del", "0"));
		Pagination<MesProcessMachine> pagination=new Pagination<>();
		pagination.setRows(dao.findListWithPagebeanCriteria(criteria, pageBean));
		pagination.setTotal(dao.findTotalWithCriteria(criteria));
		return pagination;
	}
	
	@Override
	public Result saveOrUpdate(ProductionBasicDto dto){
		MesProcessMachine machine = new MesProcessMachine();
		if(StringUtils.isNotBlank(dto.getId())){
			machine = dao.findById(MesProcessMachine.class, dto.getId());
			if(machine.getCode().equals(dto.getCode())){
				BeanUtils.copyProperties(dto, machine);
				dao.update(machine);
			}else{
				Boolean b = removal(dto);
				if(b == true){
					BeanUtils.copyProperties(dto, machine);
					dao.update(machine);
				}else{
					return new Result("500","该设备下编号已存在！");
				}
			}
		}else{
			Boolean b = removal(dto);
			if(b == true){
				BeanUtils.copyProperties(dto, machine);
				machine.setDel("0");
				dao.save(machine);
			}else{
				return new Result("500","该设备下编号已存在！");
			}
		}
		return new Result(machine);
	}
	
	@Override
	public Result del(String id){
		Criteria criteria = dao.createCriteria(MesProcess.class);
		criteria.add(Restrictions.eq("machineId", id));
		@SuppressWarnings("unchecked")
		List<MesProcess> list = criteria.list();
		if(list!=null && list.size()>0){
			return new Result("500","该设备已经被使用，不可删除！");
		}else{
			MesProcessMachine machine = dao.findById(MesProcessMachine.class, id);
			machine.setDel("1");
			dao.update(machine);
			return new Result("200","删除成功！");
		}
	}
	
	
	public Boolean removal(ProductionBasicDto dto){
		Criteria criteria = dao.createCriteria(MesProcessMachine.class);
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
