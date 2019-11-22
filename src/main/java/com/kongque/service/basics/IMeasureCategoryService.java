package com.kongque.service.basics;

import com.kongque.entity.basics.MeasureCategory;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

/**
 * @author Administrator
 */
public interface IMeasureCategoryService {
    Pagination<MeasureCategory> list(MeasureCategory dto, PageBean pageBean);
    Result saveOrUpdate(MeasureCategory dto);
    Result delete(String id);
}
