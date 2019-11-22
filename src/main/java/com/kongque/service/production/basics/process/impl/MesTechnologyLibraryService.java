package com.kongque.service.production.basics.process.impl;

import com.kongque.dao.IDaoService;
import com.kongque.dto.MesStyleTechnologyLibraryRelationDto;
import com.kongque.entity.process.MesStyleTechnologyLibraryRelation;
import com.kongque.entity.process.MesTechnologyLibrary;
import com.kongque.service.production.basics.process.IMesTechnologyLibraryService;
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
 * @author Administrator
 */
@Service
public class MesTechnologyLibraryService implements IMesTechnologyLibraryService {

    @Resource
    private IDaoService dao;

    @Override
    public Result saveOrUpdate(MesTechnologyLibrary dto) {

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
        MesTechnologyLibrary entity = dao.findById(MesTechnologyLibrary.class,id);
        if (entity==null){
            return new Result("500","id不存在!");
        }
        List<MesStyleTechnologyLibraryRelation> libraries = dao.findListByProperty(MesStyleTechnologyLibraryRelation.class,"mesTechnologyLibraryId",id);
        if (libraries.size()> 0){
            return new Result("500","已经使用,不能删除");
        }
        dao.delete(entity);
        return new Result();
    }

    @Override
    public Pagination<MesTechnologyLibrary> list(MesTechnologyLibrary dto, PageBean pageBean) {
        Pagination<MesTechnologyLibrary> pagination = new Pagination<>();
        Criteria criteria = dao.createCriteria(MesTechnologyLibrary.class);
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

    @Override
    public Result findNotSetListByStyle(MesStyleTechnologyLibraryRelationDto dto) {
        if(StringUtils.isBlank(dto.getGoodsId())){
            return new Result("500","款式id不能为空");
        }
        String hql="FROM MesTechnologyLibrary WHERE status='0' AND id NOT IN (SELECT mesTechnologyLibraryId FROM MesStyleTechnologyLibraryRelation WHERE goodsId='"+dto.getGoodsId()+"') ";
        if(StringUtils.isNotBlank(dto.getTechnologyCode())){
            hql+=" AND code like '%"+dto.getTechnologyCode()+"%'";
        }
        if (StringUtils.isNotBlank(dto.getTechnologyName())){
            hql+=" AND name like '%"+dto.getTechnologyName()+"%'";
        }
        List<MesTechnologyLibrary> list = dao.queryByHql(hql);
        return new Result(list);
    }
}
/**
 * @program: xingyu-order
 * @description: 工艺库
 * @author: zhuxl
 * @create: 2019-07-15 15:48
 **/
