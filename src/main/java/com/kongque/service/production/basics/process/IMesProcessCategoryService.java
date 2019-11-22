package com.kongque.service.production.basics.process;

import com.kongque.entity.process.MesProcessCategory;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

/**
 * @author Administrator
 * 工序类别
 */
public interface IMesProcessCategoryService {
    Result saveOrUpdate(MesProcessCategory dto);
    Result delete(String id);
    Pagination<MesProcessCategory> list(MesProcessCategory dto, PageBean pageBean);
}
