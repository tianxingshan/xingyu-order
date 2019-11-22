package com.kongque.service.production.basics.process.impl;


import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.kongque.dao.IDaoService;
import com.kongque.dto.GoodsSewProcessDto;
import com.kongque.entity.process.GoodsSewProcess;
import com.kongque.entity.process.MesProcess;
import com.kongque.entity.process.ProcessPosition;
import com.kongque.service.production.basics.process.IGoodsSewProcessService;
import com.kongque.util.MysqlOrder;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;
import com.kongque.util.StringUtils;

@Service
public class GoodsSewProcessServiceImpl implements IGoodsSewProcessService{
	
	@Resource
	private IDaoService dao;
	
	@Override
	public Pagination<GoodsSewProcess> list(GoodsSewProcess sewProcess, PageBean pageBean){
		Criteria criteria=dao.createCriteria(GoodsSewProcess.class);
		if(StringUtils.isNotBlank(sewProcess.getOptionalTechnologyId())){
			criteria.add(Restrictions.eq("optionalTechnologyId", sewProcess.getOptionalTechnologyId()));
		}
		criteria.addOrder(MysqlOrder.getOrderAsc("sort"));
		Pagination<GoodsSewProcess> pagination=new Pagination<>();
		pagination.setRows(dao.findListWithPagebeanCriteria(criteria, pageBean));
		pagination.setTotal(dao.findTotalWithCriteria(criteria));
		return pagination;
	}
	
	@Override
	public Result saveOrUpdate(GoodsSewProcessDto dto){
		Criteria criteria = dao.createCriteria(GoodsSewProcess.class);
		criteria.add(Restrictions.eq("optionalTechnologyId", dto.getOptionalTechnologyId()));
		@SuppressWarnings("unchecked")
		List<GoodsSewProcess> list = criteria.list();
		if(list==null || list.size()==0){
			if(dto.getSewProcessList()!=null && dto.getSewProcessList().size()>0){
				for (int i = 0; i < dto.getSewProcessList().size(); i++) {
					GoodsSewProcess sewProcess = new GoodsSewProcess();
					sewProcess.setOptionalTechnologyId(dto.getOptionalTechnologyId());
					sewProcess.setProcessPositionId(dto.getSewProcessList().get(i).getProcessPositionId());
					sewProcess.setProcessId(dto.getSewProcessList().get(i).getProcessId());
					sewProcess.setSort(dto.getSewProcessList().get(i).getSort());
					sewProcess.setRemark(dto.getSewProcessList().get(i).getRemark());
					dao.save(sewProcess);
				}
			}
		}else{
			for (int j = 0; j < list.size(); j++) {
				dao.delete(list.get(j));
			}
			if(dto.getSewProcessList()!=null && dto.getSewProcessList().size()>0){
				for (int i = 0; i < dto.getSewProcessList().size(); i++) {
					GoodsSewProcess sewProcess = new GoodsSewProcess();
					sewProcess.setOptionalTechnologyId(dto.getOptionalTechnologyId());
					sewProcess.setProcessPositionId(dto.getSewProcessList().get(i).getProcessPositionId());
					sewProcess.setProcessId(dto.getSewProcessList().get(i).getProcessId());
					sewProcess.setSort(dto.getSewProcessList().get(i).getSort());
					sewProcess.setRemark(dto.getSewProcessList().get(i).getRemark());
					dao.save(sewProcess);
				}
			}
		}
		return new Result(list);
	}
	
	@Override
	public Result del(String id){
		GoodsSewProcess sewProcess = dao.findById(GoodsSewProcess.class, id);
		dao.delete(sewProcess);
		return new Result("200","删除成功！");
	}
	
	@Override
	public Result getPostionInfo(String categoryId){
		Criteria criteria = dao.createCriteria(ProcessPosition.class);
		criteria.add(Restrictions.eq("categoryId", categoryId));
		@SuppressWarnings("unchecked")
		List<ProcessPosition> list = criteria.list();
		if(list!=null && list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				Criteria criteria1 = dao.createCriteria(MesProcess.class);
				criteria1.add(Restrictions.eq("processPositionId", list.get(i).getId()));
				@SuppressWarnings("unchecked")
				List<MesProcess> list1 = criteria1.list();
				if(list1!=null && list1.size()>0){
					list.get(i).setProcessList(list1);
				}
			}
		}
		return new Result(list);
	}
	
}
