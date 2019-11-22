package com.kongque.service.production.basics.process;


import com.kongque.dto.MesStyleTechnologyLibraryRelationDto;
import com.kongque.entity.process.MesTechnologyLibrary;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

/**
 * @author Administrator
 * 工序类别
 */
public interface IMesTechnologyLibraryService {
    Result saveOrUpdate(MesTechnologyLibrary dto);
    Result delete(String id);
    Pagination<MesTechnologyLibrary> list(MesTechnologyLibrary dto, PageBean pageBean);

    Result findNotSetListByStyle(MesStyleTechnologyLibraryRelationDto dto);
}
