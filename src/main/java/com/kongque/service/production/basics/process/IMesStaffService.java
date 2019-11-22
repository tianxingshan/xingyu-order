package com.kongque.service.production.basics.process;


import com.kongque.entity.process.MesStaff;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

/**
 * @author Administrator
 * @员工
 */
public interface IMesStaffService {
    Result saveOrUpdate(MesStaff dto);
    Result delete(String id);
    Pagination<MesStaff> list(MesStaff dto, PageBean pageBean);

    Result makeCard(MesStaff dto);
}
