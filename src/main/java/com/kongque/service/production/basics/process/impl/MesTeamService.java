package com.kongque.service.production.basics.process.impl;

import com.kongque.dao.IDaoService;
import com.kongque.entity.process.MesStaff;
import com.kongque.entity.process.MesTeam;
import com.kongque.service.production.basics.process.IMesTeamService;
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
 * @description: 班组
 * @author: zhuxl
 * @create: 2019-07-15 15:48
 **/
@Service
public class MesTeamService implements IMesTeamService {

    @Resource
    private IDaoService dao;

    @Override
    public Result saveOrUpdate(MesTeam dto) {

        if (StringUtils.isBlank(dto.getStatus())){
            dto.setStatus("0");
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
        MesTeam entity = dao.findById(MesTeam.class,id);
        if (entity==null){
            return new Result("500","id不存在!");
        }
        List<MesStaff> list = dao.findListByProperty(MesStaff.class,"mesTeamId",id);
        if (list.size()> 0){
            return new Result("500","已经使用,不能删除");
        }
        dao.delete(entity);
        return new Result();
    }

    @Override
    public Pagination<MesTeam> list(MesTeam dto, PageBean pageBean) {
        Pagination<MesTeam> pagination = new Pagination<>();
        Criteria criteria = dao.createCriteria(MesTeam.class);
        if(StringUtils.isNotBlank(dto.getId())){
            criteria.add(Restrictions.eq("id",dto.getId()));
        }
        if(StringUtils.isNotBlank(dto.getCode())){
            criteria.add(Restrictions.like("code",dto.getCode()));
        }
        if(StringUtils.isNotBlank(dto.getName())){
            criteria.add(Restrictions.like("name",dto.getName()));
        }


        if(StringUtils.isNotBlank(dto.getStatus())){
            criteria.add(Restrictions.eq("status",dto.getStatus()));
        }
        if(StringUtils.isNotBlank(dto.getRemarks())){
            criteria.add(Restrictions.like("remarks",dto.getRemarks()));
        }
        pagination.setRows(dao.findListWithPagebeanCriteria(criteria,pageBean));
        pagination.setTotal(dao.findTotalWithCriteria(criteria));
        return pagination;
    }
}

