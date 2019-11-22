package com.kongque.service.production.basics.process.impl;

import com.kongque.dao.IDaoService;
import com.kongque.dto.MesStyleTechnologyProcessLibraryRelationDto;
import com.kongque.entity.process.MesStyleTechnologyProcessLibraryRelation;
import com.kongque.service.production.basics.process.IMesStyleTechnologyProcessLibraryRelationService;
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
 * @description: 款式工艺工序库关系
 * @author: zhuxl
 * @create: 2019-07-17 11:42
 **/
@Service
public class MesStyleTechnologyProcessLibraryRelationService implements IMesStyleTechnologyProcessLibraryRelationService {

    @Resource
    private IDaoService dao;

    @Override
    public Result save(List<MesStyleTechnologyProcessLibraryRelation> list) {
        //dao.saveAllEntity(list);
        for (MesStyleTechnologyProcessLibraryRelation entity:list) {
            int count = Integer.parseInt(dao.uniqueBySql("select count(1) from mes_style_technology_process_library_relation where c_goods_id='"+entity.getGoodsId()+"' and c_mes_technology_library_id='"+entity.getMesTechnologyLibraryId()+"' and c_mes_process_library_id ='"+entity.getMesProcessLibraryId()+"'").toString());
            if(count == 0 ){
                dao.save(entity);
            }
        }
        return new Result(list);
    }

    @Override
    public Result update(MesStyleTechnologyProcessLibraryRelation dto) {
        MesStyleTechnologyProcessLibraryRelation entity;
        if (StringUtils.isBlank(dto.getId())){
            return new Result("500","ID不能为空!");
        }else{
            entity = dao.findById(MesStyleTechnologyProcessLibraryRelation.class,dto.getId());
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
        MesStyleTechnologyProcessLibraryRelation entity = dao.findById(MesStyleTechnologyProcessLibraryRelation.class,id);
        if (entity==null){
            return new Result("500","id不存在!");
        }
        dao.delete(entity);
        return new Result();
    }

    @Override
    public Pagination<MesStyleTechnologyProcessLibraryRelation> list(MesStyleTechnologyProcessLibraryRelationDto dto, PageBean pageBean) {
        Pagination<MesStyleTechnologyProcessLibraryRelation> pagination = new Pagination<>();
        Criteria criteria = dao.createCriteria(MesStyleTechnologyProcessLibraryRelation.class);
//        Criteria criteriaNot = dao.createCriteria(MesStyleTechnologyProcessLibraryRelation.class);
        Criteria process = criteria.createCriteria("mesProcessLibrary","mesProcessLibrary");
        Criteria technology = criteria.createCriteria("mesTechnologyLibrary","mesTechnologyLibrary");
        if(StringUtils.isNotBlank(dto.getId())){
            criteria.add(Restrictions.eq("id",dto.getId()));
        }
        if(StringUtils.isNotBlank(dto.getGoodsId())){
            criteria.add(Restrictions.eq("goodsId",dto.getGoodsId()));
        }
        if(StringUtils.isNotBlank(dto.getMesTechnologyLibraryId())){
            criteria.add(Restrictions.eq("mesTechnologyLibraryId",dto.getMesTechnologyLibraryId()));
        }
        if(StringUtils.isNotBlank(dto.getMesProcessLibraryCode())){
            process.add(Restrictions.like("code",dto.getMesProcessLibraryCode()));
        }
        if(StringUtils.isNotBlank(dto.getMesProcessLibraryName())){
            process.add(Restrictions.like("name",dto.getMesProcessLibraryName()));
        }

        if(StringUtils.isNotBlank(dto.getMesTechnologyLibraryCode())){
            technology.add(Restrictions.like("code",dto.getMesTechnologyLibraryCode()));
        }
        if(StringUtils.isNotBlank(dto.getMesTechnologyLibraryName())){
            technology.add(Restrictions.like("name",dto.getMesTechnologyLibraryName()));
        }

        if(StringUtils.isNotBlank(dto.getMesProcessLibraryId())){
            criteria.add(Restrictions.eq("mesProcessLibraryId",dto.getMesProcessLibraryId()));
        }

//        if(StringUtils.isNotBlank(dto.getNotExistFlag())&&dto.getNotExistFlag().equals("Y")){
//            //criteriaNot.add(Restrictions.eq("goodsId",dto.getGoodsId())).add(Restrictions.eq("mesTechnologyLibraryId",dto.getTechnologyId()));
//            List<String> processIds = dao.queryByHql("SELECT mesProcessLibraryId FROM MesStyleTechnologyProcessLibraryRelation WHERE goodsId='"+dto.getGoodsId()+"' AND mesTechnologyLibraryId='"+dto.getTechnologyId()+"' ");
//            criteria.add(Restrictions.not(Restrictions.in("mesProcessLibraryId",processIds)));
//        }


        pagination.setRows(dao.findListWithPagebeanCriteria(criteria,pageBean));
        pagination.setTotal(dao.findTotalWithCriteria(criteria));
        return pagination;
    }
}

