package com.kongque.service.basics;

import com.kongque.dto.CategoryMeasureRelationDto;
import com.kongque.entity.basics.CategoryMeasureRelation;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

import java.util.List;

/**
 * @author Administrator
 */
public interface ICategoryMeasureRelationService {
    Pagination<CategoryMeasureRelation> list(CategoryMeasureRelationDto dto, PageBean pageBean);
    Result saveOrUpdate(CategoryMeasureRelationDto dto);
    Result delete (String id);
    List<String> getMeasuresByCategoryIds(String[] categoryIds);
}
