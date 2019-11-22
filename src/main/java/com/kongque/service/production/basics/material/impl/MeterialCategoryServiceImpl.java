package com.kongque.service.production.basics.material.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.kongque.dao.IDaoService;
import com.kongque.dto.ProductionBasicDto;
import com.kongque.entity.material.MaterialCategory;
import com.kongque.service.production.basics.material.IMaterialCategoryService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

@Service
public class MeterialCategoryServiceImpl implements IMaterialCategoryService{
	
	@Resource
	private IDaoService dao;
	
	
	@Override
	public Pagination<MaterialCategory> list(PageBean pageBean){
		Criteria criteria=dao.createCriteria(MaterialCategory.class);
		criteria.add(Restrictions.isNull("parentId"));
		criteria.add(Restrictions.eq("del", "0"));
		Pagination<MaterialCategory> pagination=new Pagination<>();
		pagination.setRows(dao.findListWithPagebeanCriteria(criteria, pageBean));
		pagination.setTotal(dao.findTotalWithCriteria(criteria));
		return pagination;
	}
	
	
	@Override
	public Pagination<MaterialCategory> listChildren(String id,PageBean pageBean){
		Criteria criteria=dao.createCriteria(MaterialCategory.class);
		criteria.add(Restrictions.eq("parentId", id));
		criteria.add(Restrictions.eq("del", "0"));
		Pagination<MaterialCategory> pagination=new Pagination<>();
		pagination.setRows(dao.findListWithPagebeanCriteria(criteria, pageBean));
		pagination.setTotal(dao.findTotalWithCriteria(criteria));
		return pagination;
	}
	
	
	@Override
	public Result saveOrUpdate(ProductionBasicDto dto){
		MaterialCategory materialCategory = new MaterialCategory();
		if(StringUtils.isBlank(materialCategory.getId())){
			Boolean b = removal(dto);
			if(b==true){
				BeanUtils.copyProperties(dto, materialCategory);
				materialCategory.setDel("0");
				dao.save(materialCategory);
			}else{
				return new Result("500","编码已存在");
			}
		}else{
			materialCategory = dao.findById(MaterialCategory.class, dto.getId());
			if(materialCategory.getCode().equals(dto.getCode())){
				BeanUtils.copyProperties(dto, materialCategory);
				dao.update(materialCategory);
			}else{
				Boolean b = removal(dto);
				if(b == true){
					BeanUtils.copyProperties(dto, materialCategory);
					dao.update(materialCategory);
				}else{
					return new Result("500","编码已存在！");
				}
			}
		}
		return new Result(materialCategory);
	}
	
	@Override
	public Result del(String id){
		MaterialCategory materialCategory = dao.findById(MaterialCategory.class, id);
		if(StringUtils.isNotBlank(materialCategory.getParentId())){
			materialCategory.setDel("1");
			dao.update(materialCategory);
			return new Result("200","删除成功！");
		}else{
			Criteria criteria = dao.createCriteria(MaterialCategory.class);
			criteria.add(Restrictions.eq("parentId", id));
			@SuppressWarnings("unchecked") 
			List<MaterialCategory> list = criteria.list();
			if(list!=null && list.size()>0){
				return new Result("500","无法删除，此大类下还有中类");
			}else{
				materialCategory.setDel("1");
				dao.update(materialCategory);
				return new Result("200","删除成功！");
			}
		}
	}
	
	public Boolean removal(ProductionBasicDto dto){
		Criteria criteria = dao.createCriteria(MaterialCategory.class);
		if(StringUtils.isNotBlank(dto.getParentId())){
			criteria.add(Restrictions.eq("parentId", dto.getParentId()));
			criteria.add(Restrictions.eq("code", dto.getCode()));
			criteria.add(Restrictions.eq("del", "0"));
			@SuppressWarnings("unchecked") 
			List<MaterialCategory> list = criteria.list();
			if(list!=null && list.size()>0){
				return false;
			}else{
				return true;
			}
		}else{
			criteria.add(Restrictions.eq("code", dto.getCode()));
			criteria.add(Restrictions.eq("del", "0"));
			criteria.add(Restrictions.isNull("parentId"));
			@SuppressWarnings("unchecked") 
			List<MaterialCategory> list = criteria.list();
			if(list!=null && list.size()>0){
				return false;
			}else{
				return true;
			}
		}
	}

	@Override
	public List<MaterialCategory> getListByIds(String[] Ids) {
		List<MaterialCategory> modelList = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("c_id,");
		sql.append("c_code,");
		sql.append("c_name,");
		sql.append("c_parent_id,");
		sql.append("c_remark,");
		sql.append("c_status,");
		sql.append("c_del ");
		sql.append("FROM ");
		sql.append("mes_material_category ");
		sql.append("WHERE ");
		sql.append("c_status = 0 AND c_del = 0 ");
		sql.append("IN ");
		sql.append("(");
		sql.append(String.join(",", Ids));
		sql.append(")");
		
		@SuppressWarnings("rawtypes")
		List queryBySql = dao.queryBySql(sql.toString());
		for (Object object : queryBySql) {
			Object[] res = (Object[]) object;
			MaterialCategory model = new MaterialCategory();
			model.setId(res[0] == null ? "": res[0].toString());
			model.setCode(res[1]==null ? "" : res[1].toString());
			model.setName(res[2] == null ? "" : res[2].toString());
			model.setParentId(res[3] == null ? "" : res[3].toString());
			model.setRemark(res[4] == null ? "" : res[4].toString());
			model.setStatus(res[5] == null ? "" : res[5].toString());
			model.setDel(res[6] == null ? "" : res[6].toString());
			
			modelList.add(model);
		}
		return modelList;
	}
}
