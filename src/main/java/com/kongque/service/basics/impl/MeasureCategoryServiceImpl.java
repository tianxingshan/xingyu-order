package com.kongque.service.basics.impl;

import com.kongque.dao.IDaoService;
import com.kongque.entity.basics.CategoryMeasureRelation;
import com.kongque.entity.basics.MeasureCategory;
import com.kongque.entity.goods.Goods;
import com.kongque.service.basics.IMeasureCategoryService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: xingyu-order
 * @description: 量体分类
 * @author: zhuxl
 * @create: 2019-07-05 08:47
 **/
@Service
public class MeasureCategoryServiceImpl implements IMeasureCategoryService {

    @Resource
    private IDaoService dao;
    @Override
    public Pagination<MeasureCategory> list(MeasureCategory dto, PageBean pageBean) {
        Pagination<MeasureCategory> pagination = new Pagination<>();
        Criteria criteria = dao.createCriteria(MeasureCategory.class);
        if(StringUtils.isNotBlank(dto.getCode())){
            criteria.add(Restrictions.like("code",dto.getCode()));
        }
        if(StringUtils.isNotBlank(dto.getName())){
            criteria.add(Restrictions.like("name",dto.getName()));
        }
        if(StringUtils.isNotBlank(dto.getStatus())){
            criteria.add(Restrictions.eq("status",dto.getStatus()));
        }
        pagination.setRows(dao.findListWithPagebeanCriteria(criteria, pageBean));
        pagination.setTotal(dao.findTotalWithCriteria(criteria));
        return pagination;
    }

    @Override
    public Result saveOrUpdate(MeasureCategory dto) {
        Result result = new Result();
        if(StringUtils.isBlank(dto.getStatus())){
            dto.setStatus("0");
        }
        if(StringUtils.isNotBlank(dto.getId())){
            dao.update(dto);
        }else{
            dao.save(dto);
        }
        result.setRows(dto);
        return result;
    }

    @Override
    public Result delete(String id) {
        List<Goods> list = dao.findListByProperty(Goods.class,"measureCategory.id",id);
        List<CategoryMeasureRelation> list1 = dao.findListByProperty(CategoryMeasureRelation.class,"categoryId",id);
        if (list.size()> 0 || list1.size() > 0){
            return new Result("500","已经使用,不能删除");
        }


        dao.delete(dao.findById(MeasureCategory.class, id));
        return new Result();
    }
}

