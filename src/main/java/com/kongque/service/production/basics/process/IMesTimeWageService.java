package com.kongque.service.production.basics.process;

import com.kongque.entity.process.MesTimeWage;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

import java.text.ParseException;
import java.util.List;

/**
 * @author Administrator
 */
public interface IMesTimeWageService {
    Result save(MesTimeWage dto) ;
    Pagination<MesTimeWage> list(MesTimeWage dto, PageBean pageBean) ;
}
