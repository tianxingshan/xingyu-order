package com.kongque.service.production.basics.process;


import com.kongque.dto.MesWorkstationProcessRelationDto;
import com.kongque.entity.process.MesWorkstationProcessRelation;
import com.kongque.model.MesWorkstationProcessRelationModel;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

import java.util.List;

/**
 * @author Administrator
 * 车位工序关系
 */
public interface IMesWorkstationProcessRelationService {
    Result save(List<MesWorkstationProcessRelation> list);
    Result update(MesWorkstationProcessRelation dto);
    Result delete(String id);
    Pagination<MesWorkstationProcessRelation> list(MesWorkstationProcessRelation dto, PageBean pageBean);

    Pagination<MesWorkstationProcessRelationModel> getAll(MesWorkstationProcessRelationDto dto, PageBean pageBean);
}
