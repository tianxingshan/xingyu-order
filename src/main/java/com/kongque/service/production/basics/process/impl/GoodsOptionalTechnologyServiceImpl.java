package com.kongque.service.production.basics.process.impl;

import java.util.*;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import com.kongque.constants.Constants;
import com.kongque.entity.process.GoodsSewProcess;
import com.kongque.util.*;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.kongque.dao.IDaoService;
import com.kongque.dto.GoodsOptionalTechnologyDto;
import com.kongque.entity.process.GoodsOptionalTechnology;
import com.kongque.service.production.basics.process.IGoodsOptionalTechnologyService;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
public class GoodsOptionalTechnologyServiceImpl implements IGoodsOptionalTechnologyService{
	
	@Resource
	private IDaoService dao;
	
	@Override
	public Pagination<GoodsOptionalTechnology> list(GoodsOptionalTechnology technology, PageBean pageBean){
		Criteria criteria=dao.createCriteria(GoodsOptionalTechnology.class);
		if(StringUtils.isNotBlank(technology.getGoodsId())){
			criteria.add(Restrictions.eq("goodsId", technology.getGoodsId()));
		}
		Pagination<GoodsOptionalTechnology> pagination=new Pagination<>();
		pagination.setRows(dao.findListWithPagebeanCriteria(criteria, pageBean));
		pagination.setTotal(dao.findTotalWithCriteria(criteria));
		return pagination;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result saveOrUpdate(GoodsOptionalTechnologyDto dto){
		Criteria criteria = dao.createCriteria(GoodsOptionalTechnology.class);
		criteria.add(Restrictions.eq("goodsId", dto.getGoodsId()));
		List<GoodsOptionalTechnology> list = criteria.list();
		Map<String,GoodsOptionalTechnology> modelMap = new HashMap<>();
		Map<String,String> ids = new HashMap<>();
		//list的结果是根据款式ID筛选后的，所以got.getOptionalTechnologyId()不会重复
		for (GoodsOptionalTechnology got : list) {
			ids.put(got.getOptionalTechnologyId(),got.getId());
			modelMap.put(got.getId(),got);
		}
		Map<String,String> delIds = CollectionUtil.getANotInB(ids,dto.getOptionalTechnologyIds());
		List<String> saveIds = CollectionUtil.getANotInB(dto.getOptionalTechnologyIds(),ids);
		if (delIds!=null&&delIds.size()>0){
			Collection<String> del = delIds.values();
			//查询出
			List<GoodsSewProcess> goodsSewProcesses = dao.createCriteria(GoodsSewProcess.class)
					.add(Restrictions.in("optionalTechnologyId",del)).list();
			if (goodsSewProcesses!=null&&goodsSewProcesses.size()>0){
				return new Result(Constants.RESULT_CODE.SYS_ERROR,"您要删除的可选项工艺已配置缝制工序！");
			}
			for (String key : del) {
				dao.delete(modelMap.get(key));
			}
		}
		for (String id : saveIds) {
			GoodsOptionalTechnology got = new GoodsOptionalTechnology();
			got.setGoodsId(dto.getGoodsId());
			got.setOptionalTechnologyId(id);
			dao.save(got);
		}
		/*if(list==null || list.size()==0){
			for (int i = 0; i < dto.getOptionalTechnologyIds().length; i++) {
				GoodsOptionalTechnology got = new GoodsOptionalTechnology();
				got.setGoodsId(dto.getGoodsId());
				got.setOptionalTechnologyId(dto.getOptionalTechnologyIds()[i]);
				dao.save(got); 
			}
		}else{
			//先查当前所有的款式可选项工艺，比较出不同的数据（得到新增的和需要删除的）

			//新增的直接新增，删除的需要校验，如果已被缝制工序使用，不允许删除

			for (int j = 0; j < list.size(); j++) {
				dao.delete(list.get(j));
			}
			for (int i = 0; i < dto.getOptionalTechnologyIds().length; i++) {
				GoodsOptionalTechnology got = new GoodsOptionalTechnology();
				got.setGoodsId(dto.getGoodsId());
				got.setOptionalTechnologyId(dto.getOptionalTechnologyIds()[i]);
				dao.save(got); 
			}
		}*/
		return new Result(list);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Result del(String id){
		List<GoodsSewProcess> goodsSewProcesses = dao.createCriteria(GoodsSewProcess.class)
				.add(Restrictions.eq("optionalTechnologyId",id)).list();
		if (goodsSewProcesses!=null&&goodsSewProcesses.size()>0){
			return new Result(Constants.RESULT_CODE.SYS_ERROR,"您要删除的可选项工艺已配置缝制工序！");
		}
		GoodsOptionalTechnology technology = dao.findById(GoodsOptionalTechnology.class, id);
		dao.delete(technology);
		return new Result("200","删除成功！");
	}

}
