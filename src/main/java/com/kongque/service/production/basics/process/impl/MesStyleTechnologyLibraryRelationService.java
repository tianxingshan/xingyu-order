package com.kongque.service.production.basics.process.impl;

import com.kongque.dao.IDaoService;
import com.kongque.entity.process.MesCategoryProcessLibraryRelation;
import com.kongque.entity.process.MesStyleTechnologyLibraryRelation;
import com.kongque.service.production.basics.process.IMesCategoryProcessLibraryRelationService;
import com.kongque.service.production.basics.process.IMesStyleTechnologyLibraryRelationService;
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
 * @description: 款式工艺库关系
 * @author: zhuxl
 * @create: 2019-07-15 15:48
 **/
@Service
public class MesStyleTechnologyLibraryRelationService implements IMesStyleTechnologyLibraryRelationService {

    @Resource
    private IDaoService dao;

    @Override
    public Result save(List<MesStyleTechnologyLibraryRelation> list) {
        dao.saveAllEntity(list);
        return new Result();
    }

    @Override
    public Result update(MesStyleTechnologyLibraryRelation dto) {
        MesStyleTechnologyLibraryRelation entity;
        if (StringUtils.isBlank(dto.getId())){
            return new Result("500","ID不能为空!");
        }else{
            entity = dao.findById(MesStyleTechnologyLibraryRelation.class,dto.getId());
            if(entity==null){
                return new Result("500","已经删除或不存在,不能修改!");
            }
        }
        BeanUtils.copyProperties(dto,entity);
        dao.update(entity);
        return new Result();
    }


    @Override
    public Result delete(String id) {
        MesStyleTechnologyLibraryRelation entity = dao.findById(MesStyleTechnologyLibraryRelation.class,id);
        if (entity==null){
            return new Result("500","id不存在!");
        }
        dao.delete(entity);
        return new Result();
    }

    @Override
    public Pagination<MesStyleTechnologyLibraryRelation> list(MesStyleTechnologyLibraryRelation dto, PageBean pageBean) {
        Pagination<MesStyleTechnologyLibraryRelation> pagination = new Pagination<>();
        Criteria criteria = dao.createCriteria(MesStyleTechnologyLibraryRelation.class);
        if(StringUtils.isNotBlank(dto.getId())){
            criteria.add(Restrictions.eq("id",dto.getId()));
        }
        if(StringUtils.isNotBlank(dto.getGoodsId())){
            criteria.add(Restrictions.eq("goodsId",dto.getGoodsId()));
        }
        if(StringUtils.isNotBlank(dto.getMesTechnologyLibraryId())){
            criteria.add(Restrictions.eq("mesTechnologyLibraryId",dto.getMesTechnologyLibraryId()));
        }

        pagination.setRows(dao.findListWithPagebeanCriteria(criteria,pageBean));
        pagination.setTotal(dao.findTotalWithCriteria(criteria));
        return pagination;
    }
}

