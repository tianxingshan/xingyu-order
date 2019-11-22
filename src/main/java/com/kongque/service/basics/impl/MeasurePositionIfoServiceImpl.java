package com.kongque.service.basics.impl;

import com.kongque.dao.IDaoService;
import com.kongque.entity.basics.MeasurePositionInfo;
import com.kongque.service.basics.IMeasurePositionInfoService;
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
 * @program: xingyu-order
 * @description: 量体部位库实现
 * @author: zhuxl
 * @create: 2019-05-31 10:45
 **/
@Service
public class MeasurePositionIfoServiceImpl implements IMeasurePositionInfoService {

    @Resource
    private IDaoService dao;

    @Override
    public Pagination<MeasurePositionInfo> list(MeasurePositionInfo dto, PageBean pageBean) {

        Pagination<MeasurePositionInfo> pagination = new Pagination<>();

        Criteria criteria = dao.createCriteria(MeasurePositionInfo.class);

        if(StringUtils.isNotBlank(dto.getId())){
            criteria.add(Restrictions.eq("id",dto.getId()));
        }
        if (StringUtils.isNotBlank(dto.getCode())){
            criteria.add(Restrictions.like("code",dto.getCode()));
        }
        if(StringUtils.isNotBlank(dto.getName())){
            criteria.add(Restrictions.like("name",dto.getName()));
        }
        if(StringUtils.isNotBlank(dto.getStatus())){
            criteria.add(Restrictions.eq("status",dto.getStatus()));
        }
        criteria.addOrder(Order.asc("code"));
        pagination.setRows(dao.findListWithPagebeanCriteria(criteria,pageBean));
        pagination.setTotal(dao.findTotalWithCriteria(criteria));
        return pagination;
    }

    @Override
    public Result saveOrUpdate(MeasurePositionInfo dto) {


        if(StringUtils.isBlank(dto.getCode())||StringUtils.isBlank(dto.getName())){
            return new Result("500","代码或名称不能为空!");
        }

        if(StringUtils.isNotBlank(dto.getId())){
            dao.update(dto);
        }else {
            List<MeasurePositionInfo> list = dao.findListByProperty(MeasurePositionInfo.class,"code",dto.getCode());
            if(list.size()>0){
                return new Result("500","代码已经存在,不能重复定义!");
            }
            dao.save(dto);
        }
        return new Result();
    }

    @Override
    public Result delete(String id) {
        Result result = new Result();
        if(StringUtils.isBlank(id)){
            result.setReturnCode("500");
            result.setReturnMsg("id不能为空!");
            return result;
        }
        MeasurePositionInfo entity = dao.findById(MeasurePositionInfo.class,id);
        if(entity==null){
            result.setReturnCode("500");
            result.setReturnMsg("id不存在!");
            return result;
        }
        dao.delete(entity);
        return result;
    }

}

