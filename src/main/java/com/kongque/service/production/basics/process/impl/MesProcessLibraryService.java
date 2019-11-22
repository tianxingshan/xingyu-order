package com.kongque.service.production.basics.process.impl;

import com.kongque.dao.IDaoService;
import com.kongque.dto.MesProcessLibraryDto;
import com.kongque.dto.MesStyleTechnologyProcessDto;
import com.kongque.entity.process.MesProcessLibrary;
import com.kongque.entity.process.MesWorkshopSection;
import com.kongque.service.production.basics.process.IMesProcessLibraryService;
import com.kongque.util.CodeUtil;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class MesProcessLibraryService implements IMesProcessLibraryService {
    @Resource
    private IDaoService dao;

    @Override
    public Result saveOrUpdate(MesProcessLibraryDto dto) {
        MesProcessLibrary entity = new MesProcessLibrary();
        if (StringUtils.isBlank(dto.getMesWorkshopSectionId())){
            return new Result("500","工段不能为空!");
        }
        if(StringUtils.isBlank(dto.getStatus())){
            dto.setStatus("0");
        }
        BeanUtils.copyProperties(dto,entity);
        MesWorkshopSection mesWorkshopSection = dao.findById(MesWorkshopSection.class,dto.getMesWorkshopSectionId());
        if(mesWorkshopSection == null){
            return new Result("500","工段不存在!");
        }
//        entity.setMesWorkshopSection(mesWorkshopSection);
        if(StringUtils.isNotBlank(dto.getId())){
            dao.update(entity);
        }else{
            dao.save(entity);
        }

        return new Result(entity);
    }

    @Override
    public Result delete(String id) {
        MesProcessLibrary entity = dao.findById(MesProcessLibrary.class,id);
        if(entity==null){
            return new Result("500","不存在!");
        }
        dao.delete(entity);
        return new Result();
    }

    @Override
    public Pagination<MesProcessLibrary> list(MesProcessLibraryDto dto, PageBean pageBean) {
        Pagination<MesProcessLibrary> pagination = new Pagination<>();
        Criteria criteria = dao.createCriteria(MesProcessLibrary.class);
        if(StringUtils.isNotBlank(dto.getMesWorkshopSectionId())) {
            criteria.add(Restrictions.eq("mesWorkshopSectionId",dto.getMesWorkshopSectionId()));
//            criteria.createCriteria("mesWorkshopSection", "mesWorkshopSection").add(Restrictions.eq("id",dto.getMesWorkshopSectionId()));
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
    public Result makeCard(MesProcessLibraryDto dto) {
        if(StringUtils.isBlank(dto.getId())){
            return new Result("500","请选择要制卡的工序!");
        }
        MesProcessLibrary entity = dao.findById(MesProcessLibrary.class,dto.getId());
        if(entity==null){
            return new Result("500","已经删除或者不存在!");
        }
        entity.setMadeCount(dto.getMadeCount());
//        if(dto.getMadeTime()==null){
        entity.setMadeTime( new Date());
//        }
        if(StringUtils.isBlank(entity.getMakeCardNo())){
            entity.setMakeCardNo(CodeUtil.createCardNo("10",6));
        }

        dao.update(entity);
        return new Result(entity);
    }

    @Override
    public Result findNotSetListByCategoryId(String categoryId) {
        List<MesProcessLibrary> list = dao.queryByHql("FROM MesProcessLibrary WHERE status='0' and id NOT IN (SELECT mesProcessLibraryId FROM MesCategoryProcessLibraryRelation WHERE mesProcessCategoryId='"+categoryId+"')");
        return new Result(list);
    }

    @Override
    public Result findNotSetListByWorkstation(MesProcessLibraryDto dto) {
        if(StringUtils.isBlank(dto.getMesWorkstationId())){
            return new Result("500","车位id不能为空!");
        }
        String hql="FROM MesProcessLibrary WHERE status='0' AND id NOT IN (SELECT mesProcessLibraryId FROM MesWorkstationProcessRelation WHERE mesWorkstationId='"+dto.getMesWorkstationId()+"') ";
        if(StringUtils.isNotBlank(dto.getCode())){
            hql+=" AND code like '%"+dto.getCode()+"%'";
        }
        if(StringUtils.isNotBlank(dto.getName())){
            hql+=" AND name like '%"+dto.getName()+"%'";
        }
        List<MesProcessLibrary> libraries = dao.queryByHql(hql);
        return new Result(libraries);
    }


}
/**
 * @program: xingyu-order
 * @description: 工序库服务
 * @author: zhuxl
 * @create: 2019-07-15 17:06
 **/
