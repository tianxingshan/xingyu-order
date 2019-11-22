package com.kongque.service.production.basics.process.impl;

import com.kongque.dao.IDaoService;
import com.kongque.entity.process.MesProcessLibrary;
import com.kongque.entity.process.MesWorkshopSection;
import com.kongque.service.production.basics.process.IMesWorkshopSectionService;
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
public class MesWorkshopSectionService implements IMesWorkshopSectionService {

    @Resource
    private IDaoService dao;

    @Override
    public Result saveOrUpdate(MesWorkshopSection dto) {

        if (StringUtils.isBlank(dto.getStatus())){
            dto.setStatus("0");
        }
        if (StringUtils.isBlank(dto.getType())){
            return new Result("500","工段标志不能为空!");
        }

        if(StringUtils.isBlank(dto.getId())){
            dao.save(dto);
        }else {
            dao.update(dto);
        }
        return new Result();
    }

    @Override
    public Result delete(String id) {
        MesWorkshopSection entity = dao.findById(MesWorkshopSection.class,id);
        if (entity==null){
            return new Result("500","id不存在!");
        }
        List<MesProcessLibrary> libraries = dao.findListByProperty(MesProcessLibrary.class,"mesWorkshopSection.id",id);
        if (libraries.size()> 0){
            return new Result("500","已经使用,不能删除");
        }
        dao.delete(entity);
        return new Result();
    }

    @Override
    public Pagination<MesWorkshopSection> list(MesWorkshopSection dto, PageBean pageBean) {
        Pagination<MesWorkshopSection> pagination = new Pagination<>();
        Criteria criteria = dao.createCriteria(MesWorkshopSection.class);
        if(StringUtils.isNotBlank(dto.getId())){
            criteria.add(Restrictions.eq("id",dto.getId()));
        }
        if(StringUtils.isNotBlank(dto.getCode())){
            criteria.add(Restrictions.like("code",dto.getCode()));
        }
        if(StringUtils.isNotBlank(dto.getName())){
            criteria.add(Restrictions.like("name",dto.getName()));
        }
        if(StringUtils.isNotBlank(dto.getType())){
            criteria.add(Restrictions.eq("type",dto.getType()));
        }
        if(StringUtils.isNotBlank(dto.getStatus())){
            criteria.add(Restrictions.eq("status",dto.getStatus()));
        }
        if(StringUtils.isNotBlank(dto.getRemarks())){
            criteria.add(Restrictions.like("remarks",dto.getRemarks()));
        }
        criteria.addOrder(Order.asc("sort")).addOrder(Order.asc("code"));
        pagination.setRows(dao.findListWithPagebeanCriteria(criteria,pageBean));
        pagination.setTotal(dao.findTotalWithCriteria(criteria));
        return pagination;
    }
}
/**
 * @program: xingyu-order
 * @description: 工段服务
 * @author: zhuxl
 * @create: 2019-07-15 15:48
 **/
