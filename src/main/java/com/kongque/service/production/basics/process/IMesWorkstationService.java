package com.kongque.service.production.basics.process;


import com.kongque.entity.process.MesWorkstation;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

/**
 * @author Administrator
 * 车位
 */
public interface IMesWorkstationService {
    Result saveOrUpdate(MesWorkstation dto);
    Result delete(String id);
    Pagination<MesWorkstation> list(MesWorkstation dto, PageBean pageBean);
}
