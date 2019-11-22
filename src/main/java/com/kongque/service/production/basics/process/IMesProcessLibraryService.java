package com.kongque.service.production.basics.process;

import com.kongque.dto.MesProcessLibraryDto;
import com.kongque.dto.MesStyleTechnologyProcessDto;
import com.kongque.entity.process.MesProcessLibrary;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

import java.util.List;

/**
 * @author Administrator
 */
public interface IMesProcessLibraryService {
    Result saveOrUpdate(MesProcessLibraryDto dto);
    Result delete(String id);
    Pagination<MesProcessLibrary> list(MesProcessLibraryDto dto, PageBean pageBean);
    Result makeCard(MesProcessLibraryDto dto);

    Result findNotSetListByCategoryId(String categoryId);

    Result findNotSetListByWorkstation(MesProcessLibraryDto dto);

}
