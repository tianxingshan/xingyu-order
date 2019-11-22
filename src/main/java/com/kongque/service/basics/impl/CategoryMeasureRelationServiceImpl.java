package com.kongque.service.basics.impl;

import com.kongque.dao.IDaoService;
import com.kongque.dto.CategoryMeasureRelationDto;
import com.kongque.entity.basics.BodyMeasureInfo;
import com.kongque.entity.basics.CategoryMeasureRelation;
import com.kongque.service.basics.ICategoryMeasureRelationService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class CategoryMeasureRelationServiceImpl implements ICategoryMeasureRelationService {

    @Resource
    private IDaoService dao;

    @Override
    public Pagination<CategoryMeasureRelation> list(CategoryMeasureRelationDto dto, PageBean pageBean) {
        Criteria criteria = dao.createCriteria(CategoryMeasureRelation.class);
        Pagination<CategoryMeasureRelation> pagination = new Pagination<CategoryMeasureRelation>();
        if (pageBean.getPage() == null) {
            pageBean.setPage(0);
        }
        if (pageBean.getRows() == null) {
            pageBean.setRows(9999);
        }
        if (StringUtils.isNotBlank(dto.getCategoryId())){
            criteria.add(Restrictions.eq("categoryId",dto.getCategoryId()));
        }
        criteria.createCriteria("bodyMeasureInfo","bodyMeasureInfo").addOrder(Order.asc("sort"));
        pagination.setRows(dao.findListWithPagebeanCriteria(criteria, pageBean));
        pagination.setTotal(dao.findTotalWithCriteria(criteria));
        return pagination;
    }

    @Override
    public Result saveOrUpdate(CategoryMeasureRelationDto dto) {
        List<String> list = dto.getBodyMeasureInfoIds();
        List<CategoryMeasureRelation> categoryMeasureRelations = new ArrayList<>();
        if (list.size() > 0 ){
            for (String id:list) {
                CategoryMeasureRelation entity = new CategoryMeasureRelation();
                entity.setCategoryId(dto.getCategoryId());
                entity.setBodyMeasureInfo(dao.findById(BodyMeasureInfo.class,id));
                entity.setStatus("0");
                dao.save(entity);
                categoryMeasureRelations.add(entity);
            }
        }
        return new Result(categoryMeasureRelations);
    }

    @Override
    public Result delete(String id) {
        dao.delete(dao.findById(CategoryMeasureRelation.class,id));
        return new Result();
    }

    @Override
    public List<String> getMeasuresByCategoryIds(String[] categoryIds) {
        List<String> list = new ArrayList<>();
        if (categoryIds!=null && categoryIds.length>0){
            String ids="";
            for (String id:categoryIds) {
                ids +="'"+id+"',";
            }
           list = dao.queryBySql("select distinct c_body_measure_id from t_category_measure_relation where c_category_id in ("+ids.substring(0,ids.length()-1)+")");
        }
        return list;
    }


}
/**
 * @program: xingyu-order
 * @description: 品类量体关系
 * @author: zhuxl
 * @create: 2019-07-04 16:54
 **/
