package com.kongque.service.production.basics.process;


import com.kongque.entity.process.MesTeam;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

/**
 * @author Administrator
 * @班组
 */
public interface IMesTeamService {
    Result saveOrUpdate(MesTeam dto);
    Result delete(String id);
    Pagination<MesTeam> list(MesTeam dto, PageBean pageBean);

}
