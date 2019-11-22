package com.kongque.service.production.basics.process;


import com.kongque.dto.MesStyleTechnologyProcessLibraryRelationDto;
import com.kongque.entity.process.MesStyleTechnologyProcessLibraryRelation;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

import java.util.List;

/**
 * @author Administrator
 * 款式工艺工序库关系
 */
public interface IMesStyleTechnologyProcessLibraryRelationService {
    Result save(List<MesStyleTechnologyProcessLibraryRelation> list);
    Result update(MesStyleTechnologyProcessLibraryRelation dto);
    Result delete(String id);
    Pagination<MesStyleTechnologyProcessLibraryRelation> list(MesStyleTechnologyProcessLibraryRelationDto dto, PageBean pageBean);
}
