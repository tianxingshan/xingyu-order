package com.kongque.service.basics.impl;

import com.kongque.dao.IDaoService;
import com.kongque.entity.basics.BodyMeasureInfo;
import com.kongque.service.basics.IBodyMeasureInfoService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class BodyMeasureInfoServiceImpl implements IBodyMeasureInfoService {

    @Resource
    private IDaoService dao;

    @Override
    public Result findNotSelectedByCategoryId(String categoryId) {
        Result result = new Result();
        List<BodyMeasureInfo> list = dao.queryByHql(" FROM BodyMeasureInfo WHERE ifnull(status,'0') = '0' and id NOT IN (SELECT bodyMeasureInfo.id FROM CategoryMeasureRelation WHERE categoryId='"+categoryId+"') order by sort asc ");
        result.setRows(list);
        return result;
    }

    @Override
    public Result saveOrUpdate(BodyMeasureInfo dto) {
        if (StringUtils.isNotBlank(dto.getId())){
            dao.update(dto);
        }else {
            dao.save(dto);
        }

        return new Result();
    }

    @Override
    public Pagination<BodyMeasureInfo> list(BodyMeasureInfo dto, PageBean pageBean) {
        Pagination<BodyMeasureInfo> pagination = new Pagination<>();
        Criteria criteria = dao.createCriteria(BodyMeasureInfo.class);
        if (StringUtils.isNotBlank(dto.getCode())){
            criteria.add(Restrictions.like("code",dto.getCode()));
        }
        if(StringUtils.isNotBlank(dto.getName())){
            criteria.add(Restrictions.like("name",dto.getName()));
        }
        if(StringUtils.isNotBlank(dto.getId())){
            criteria.add(Restrictions.eq("id",dto.getId()));
        }
        if(StringUtils.isNotBlank(dto.getStatus())){
            criteria.add(Restrictions.eq("status",dto.getStatus()));
        }
        criteria.addOrder(Order.asc("sort"));
        pagination.setRows(dao.findListWithPagebeanCriteria(criteria,pageBean));
        pagination.setTotal(dao.findTotalWithCriteria(criteria));
        return pagination;
    }
}
/**
 * @program: xingyu-order
 * @description: 量体库
 * @author: zhuxl
 * @create: 2019-07-04 16:01
 **/
