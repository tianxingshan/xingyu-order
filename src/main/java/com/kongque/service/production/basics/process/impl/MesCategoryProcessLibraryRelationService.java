package com.kongque.service.production.basics.process.impl;

import com.kongque.dao.IDaoService;
import com.kongque.dto.MesStyleTechnologyProcessDto;
import com.kongque.entity.process.MesCategoryProcessLibraryRelation;
import com.kongque.entity.process.MesProcessLibrary;
import com.kongque.service.production.basics.process.IMesCategoryProcessLibraryRelationService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: xingyu-order
 * @description: 类别工序库关系
 * @author: zhuxl
 * @create: 2019-07-15 15:48
 **/
@Service
public class MesCategoryProcessLibraryRelationService implements IMesCategoryProcessLibraryRelationService {

    @Resource
    private IDaoService dao;

    @Override
    public Result save(List<MesCategoryProcessLibraryRelation> list) {
        dao.saveAllEntity(list);
        return new Result(list);
    }

    @Override
    public Result update(MesCategoryProcessLibraryRelation dto) {
        MesCategoryProcessLibraryRelation entity;
        if (StringUtils.isBlank(dto.getId())){
            return new Result("500","ID不能为空!");
        }else {
            entity = dao.findById(MesCategoryProcessLibraryRelation.class,dto.getId());
            if (entity==null){
                return new Result("500","已经删除或不存在,不能修改!");
            }
        }
        BeanUtils.copyProperties(dto,entity);
        dao.update(entity);
        return new Result();
    }


    @Override
    public Result delete(String id) {
        MesCategoryProcessLibraryRelation entity = dao.findById(MesCategoryProcessLibraryRelation.class,id);
        if (entity==null){
            return new Result("500","id不存在!");
        }
        dao.delete(entity);
        return new Result();
    }

    @Override
    public Pagination<MesCategoryProcessLibraryRelation> list(MesCategoryProcessLibraryRelation dto, PageBean pageBean) {
        Pagination<MesCategoryProcessLibraryRelation> pagination = new Pagination<>();
        Criteria criteria = dao.createCriteria(MesCategoryProcessLibraryRelation.class);
        if(StringUtils.isNotBlank(dto.getId())){
            criteria.add(Restrictions.eq("id",dto.getId()));
        }
        if(StringUtils.isNotBlank(dto.getMesProcessCategoryId())){
            criteria.add(Restrictions.eq("mesProcessCategoryId",dto.getMesProcessCategoryId()));
        }
        if(StringUtils.isNotBlank(dto.getMesProcessLibraryId())){
            criteria.add(Restrictions.eq("mesProcessLibraryId",dto.getMesProcessLibraryId()));
        }

        pagination.setRows(dao.findListWithPagebeanCriteria(criteria,pageBean));
        pagination.setTotal(dao.findTotalWithCriteria(criteria));
        return pagination;
    }

    @Override
    public Result findNotSetListByStyleTechnology(MesStyleTechnologyProcessDto dto) {
        if (StringUtils.isBlank(dto.getGoodsId())){
            return  new Result("500","款式Id不能为空!");
        }
        if(StringUtils.isBlank(dto.getTechnologyId())){
            return new Result("500","工艺id不能为空!");
        }

        String hql="From MesCategoryProcessLibraryRelation WHERE  mesProcessLibraryId NOT IN (SELECT mesProcessLibraryId FROM MesStyleTechnologyProcessLibraryRelation WHERE goodsId='"+dto.getGoodsId()+"' AND mesTechnologyLibraryId = '"+dto.getTechnologyId()+"' )";
        if(StringUtils.isNotBlank(dto.getProcessCode())){
            hql +=" AND mesProcessLibrary.code like '%"+dto.getProcessCode()+"%'";
        }

        if(StringUtils.isNotBlank(dto.getProcessName())){
            hql +=" AND mesProcessLibrary.name like '%"+dto.getProcessName()+"%'";
        }
        if(StringUtils.isNotBlank(dto.getProcessCategoryId())){
            hql+=" AND mesProcessCategoryId = '"+dto.getProcessCategoryId()+"'";
        }
        List<MesCategoryProcessLibraryRelation> list = dao.queryByHql(hql);
        return new Result(list);
    }
}

