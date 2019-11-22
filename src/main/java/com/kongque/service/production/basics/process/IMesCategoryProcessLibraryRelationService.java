package com.kongque.service.production.basics.process;

import com.kongque.dto.MesStyleTechnologyProcessDto;
import com.kongque.entity.process.MesCategoryProcessLibraryRelation;
import com.kongque.entity.process.MesProcessCategory;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

import java.util.List;

/**
 * @author Administrator
 * 类别工序库关系
 */
public interface IMesCategoryProcessLibraryRelationService {
    Result save(List<MesCategoryProcessLibraryRelation> list);
    Result update(MesCategoryProcessLibraryRelation dto);
    Result delete(String id);
    Pagination<MesCategoryProcessLibraryRelation> list(MesCategoryProcessLibraryRelation dto, PageBean pageBean);

    Result findNotSetListByStyleTechnology(MesStyleTechnologyProcessDto dto);
}
