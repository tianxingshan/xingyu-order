package com.kongque.service.production.basics.process;

import com.kongque.entity.process.MesWorkshopSection;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

/**
 * @author Administrator
 */
public interface IMesWorkshopSectionService {
    Result saveOrUpdate(MesWorkshopSection dto);
    Result delete(String id);
    Pagination<MesWorkshopSection> list(MesWorkshopSection dto, PageBean pageBean);
}
