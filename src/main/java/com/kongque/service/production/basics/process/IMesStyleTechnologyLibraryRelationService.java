package com.kongque.service.production.basics.process;

import com.kongque.entity.process.MesCategoryProcessLibraryRelation;
import com.kongque.entity.process.MesStyleTechnologyLibraryRelation;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

import java.util.List;

/**
 * @author Administrator
 * 款式工艺库关系
 */
public interface IMesStyleTechnologyLibraryRelationService {
    Result save(List<MesStyleTechnologyLibraryRelation> list);
    Result update(MesStyleTechnologyLibraryRelation dto);
    Result delete(String id);
    Pagination<MesStyleTechnologyLibraryRelation> list(MesStyleTechnologyLibraryRelation dto, PageBean pageBean);
}
