package com.kongque.service.production.basics.process.impl;

import com.kongque.dao.IDaoService;
import com.kongque.dto.MesWorkstationProcessRelationDto;
import com.kongque.entity.process.MesStyleTechnologyLibraryRelation;
import com.kongque.entity.process.MesWorkstationProcessRelation;
import com.kongque.model.MesWorkstationProcessRelationModel;
import com.kongque.service.production.basics.process.IMesStyleTechnologyLibraryRelationService;
import com.kongque.service.production.basics.process.IMesWorkstationProcessRelationService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: xingyu-order
 * @description: 车位工序关系
 * @author: zhuxl
 * @create: 2019-07-15 15:48
 **/
@Service
public class MesWorkstationProcessRelationService implements IMesWorkstationProcessRelationService {

    @Resource
    private IDaoService dao;

    @Override
    public Result save(List<MesWorkstationProcessRelation> list) {
        for (MesWorkstationProcessRelation entity:list) {
            entity.setCreateTime(new Date());
        }
        dao.saveAllEntity(list);
        return new Result(list);
    }

    @Override
    public Result update(MesWorkstationProcessRelation dto) {
        MesWorkstationProcessRelation entity;
        if (StringUtils.isBlank(dto.getId())){
            return new Result("500","ID不能为空!");
        }else{
            entity = dao.findById(MesWorkstationProcessRelation.class,dto.getId());
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
        MesWorkstationProcessRelation entity = dao.findById(MesWorkstationProcessRelation.class,id);
        if (entity==null){
            return new Result("500","id不存在!");
        }
        dao.delete(entity);
        return new Result();
    }

    @Override
    public Pagination<MesWorkstationProcessRelation> list(MesWorkstationProcessRelation dto, PageBean pageBean) {
        Pagination<MesWorkstationProcessRelation> pagination = new Pagination<>();
        Criteria criteria = dao.createCriteria(MesWorkstationProcessRelation.class);
        if(StringUtils.isNotBlank(dto.getId())){
            criteria.add(Restrictions.eq("id",dto.getId()));
        }
        if(StringUtils.isNotBlank(dto.getMesWorkstationId())){
            criteria.add(Restrictions.eq("mesWorkstationId",dto.getMesWorkstationId()));
        }
        if(StringUtils.isNotBlank(dto.getMesProcessLibraryId())){
            criteria.add(Restrictions.eq("mesProcessLibraryId",dto.getMesProcessLibraryId()));
        }

        pagination.setRows(dao.findListWithPagebeanCriteria(criteria,pageBean));
        pagination.setTotal(dao.findTotalWithCriteria(criteria));
        return pagination;
    }

    @Override
    public Pagination<MesWorkstationProcessRelationModel> getAll(MesWorkstationProcessRelationDto dto, PageBean pageBean) {

        List<MesWorkstationProcessRelationModel> list = new ArrayList<>();
        StringBuilder sqlSelect = new StringBuilder(" SELECT mws.c_code AS workshop_section_code,mws.c_name AS workshop_section_name,mpl.c_code AS process_code,mpl.c_name AS process_name,mw.c_terminal_number terminal_number,mwpr.c_create_time,case when ifnull(mw.c_id,0)= 0 then 0 else 1 end bind_flag," +
                " mw.c_code workstation_code,mw.c_name workstation_name " );
        StringBuilder sqlCount = new StringBuilder("SELECT COUNT(1)");
        StringBuilder sqlLimit = new StringBuilder();
        StringBuilder sql = new StringBuilder(" FROM mes_process_library mpl" +
                " JOIN mes_workshop_section mws ON mpl.c_mes_workshop_section_id = mws.c_id " );
//        if(StringUtils.isNotBlank(dto.getBindFlag())&&dto.getBindFlag().equals("Y")){
//            sql.append(" JOIN mes_workstation_process_relation mwpr ON mpl.c_id = mwpr.c_mes_process_library_id");
//            sql.append(" JOIN mes_workstation mw ON mwpr.c_mes_workstation_id=mw.c_id");
//        }else {
            sql.append(" LEFT JOIN mes_workstation_process_relation mwpr ON mpl.c_id = mwpr.c_mes_process_library_id");
            sql.append(" LEFT JOIN mes_workstation mw ON mwpr.c_mes_workstation_id=mw.c_id");
//        }
        sql.append(" WHERE 1= 1 ");


        if(StringUtils.isNotBlank(dto.getBindFlag())){
            sql.append(" AND case when ifnull(mw.c_id,0)= 0 then 0 else 1 end =").append(dto.getBindFlag());
        }

        /**
         * 工段
         */
        if(StringUtils.isNotBlank(dto.getWorkshopSectionId())){
            sql.append(" AND mws.c_id='").append(dto.getWorkshopSectionId()).append("'");
        }
        if(StringUtils.isNotBlank(dto.getWorkshopSectionCode())){
            sql.append(" AND mws.c_code like '%").append(dto.getWorkshopSectionCode()).append("%'");
        }
        if(StringUtils.isNotBlank(dto.getWorkshopSectionName())){
            sql.append(" AND mws.c_name like '%").append(dto.getWorkshopSectionName()).append("%'");
        }
        /**
         * 车位
         */
        if(StringUtils.isNotBlank(dto.getWorkstationCode())){
            sql.append(" AND mw.c_code like '%").append(dto.getWorkstationCode()).append("%'");
        }

        if(StringUtils.isNotBlank(dto.getTerminalNumber())){
            sql.append(" AND mw.c_terminal_number like '%").append(dto.getTerminalNumber()).append("%'");
        }
        /**
         * 工序
         */
        if(StringUtils.isNotBlank(dto.getProcessCode())){
            sql.append(" AND mpl.c_code like '%").append(dto.getProcessCode()).append("%'");
        }
        if(StringUtils.isNotBlank(dto.getProcessName())){
            sql.append(" AND mpl.c_name like '%").append(dto.getProcessName()).append("%'");
        }
        if(pageBean.getPage()!=null && pageBean.getRows()!=null){
            sqlLimit.append(" limit "+(pageBean.getPage() - 1) * pageBean.getRows()+","+pageBean.getRows());
        }
        List resultSet = dao.queryBySql(sqlSelect.append(sql).append(sqlLimit).toString());
        for(Object result : resultSet){
            MesWorkstationProcessRelationModel model = new MesWorkstationProcessRelationModel();//构建返回数据模型
            Object[] properties = (Object[])result;
            model.setWorkshopSectionCode(properties[0]==null ? "" : properties[0].toString());
            model.setWorkshopSectionName(properties[1]==null ? "" : properties[1].toString());
            model.setProcessCode(properties[2]==null ? "" : properties[2].toString());
            model.setProcessName(properties[3]==null ? "" : properties[3].toString());
            model.setTerminalNumber(properties[4]==null ? "" : properties[4].toString());
            model.setCreateTime(properties[5]==null ? "" : properties[5].toString());
            model.setBindFlag(properties[6]==null ? "" : properties[6].toString());
            model.setWorkstationCode(properties[7]==null ? "" : properties[7].toString());
            model.setWorkstationName(properties[8]==null ? "" : properties[8].toString());
            list.add(model);
        }
        List<BigInteger> result = dao.queryBySql(sqlCount.append(sql).toString());
        Long count = result.isEmpty() ? 0L : result.get(0).longValue();
        Pagination<MesWorkstationProcessRelationModel> pagination = new Pagination<>();
        pagination.setTotal(count);
        pagination.setRows(list);
        return pagination;
    }
}

