package com.kongque.service.production.basics.process.impl;

import com.kongque.dao.IDaoService;
import com.kongque.entity.process.MesStaff;
import com.kongque.service.production.basics.process.IMesStaffService;
import com.kongque.util.CodeUtil;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @program: xingyu-order
 * @description: 员工
 * @author: zhuxl
 * @create: 2019-07-15 15:48
 **/
@Service
public class MesStaffService implements IMesStaffService {

    @Resource
    private IDaoService dao;

    @Override
    public Result saveOrUpdate(MesStaff dto) {

        if (StringUtils.isBlank(dto.getStatus())){
            dto.setStatus("0");
        }

        if(StringUtils.isBlank(dto.getId())){
            dao.save(dto);
        }else {
            dao.update(dto);
        }
        return new Result(dto);
    }

    @Override
    public Result delete(String id) {
        MesStaff entity = dao.findById(MesStaff.class,id);
        if (entity==null){
            return new Result("500","id不存在!");
        }
//        List<MesStaff> list = dao.findListByProperty(MesStaff.class,"mesTeamId",id);
//        if (list.size()> 0){
//            return new Result("500","已经使用,不能删除");
//        }
        dao.delete(entity);
        return new Result();
    }

    @Override
    public Pagination<MesStaff> list(MesStaff dto, PageBean pageBean) {
        Pagination<MesStaff> pagination = new Pagination<>();
        Criteria criteria = dao.createCriteria(MesStaff.class);
        if(StringUtils.isNotBlank(dto.getId())){
            criteria.add(Restrictions.eq("id",dto.getId()));
        }
        if(StringUtils.isNotBlank(dto.getCode())){
            criteria.add(Restrictions.like("code",dto.getCode()));
        }
        if(StringUtils.isNotBlank(dto.getName())){
            criteria.add(Restrictions.like("name",dto.getName()));
        }

        if(StringUtils.isNotBlank(dto.getMesTeamId())){
            criteria.add(Restrictions.eq("mesTeamId",dto.getMesTeamId()));
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

    @Override
    public Result makeCard(MesStaff dto) {
        if(StringUtils.isBlank(dto.getId())){
            return new Result("500","该员工不存在,不能制卡!");
        }
        if(dto.getMakeCardTime()==null){
            dto.setMakeCardTime(new Date());
        }
        MesStaff entity = dao.findById(MesStaff.class,dto.getId());
        entity.setMakeCardNumber(dto.getMakeCardNumber());
        entity.setMakeCardCreater(dto.getMakeCardCreater());
        entity.setMakeCardTime(dto.getMakeCardTime());
        if(StringUtils.isBlank(entity.getMakeCardNo())){
            entity.setMakeCardNo(CodeUtil.createCardNo("0",5));
        }
        dao.update(entity);
        return new Result(entity);
    }
}

