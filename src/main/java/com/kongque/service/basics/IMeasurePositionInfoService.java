package com.kongque.service.basics;

import com.kongque.entity.basics.MeasurePositionInfo;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

/**
 * @author Administrator
 */
public interface IMeasurePositionInfoService {

    Pagination<MeasurePositionInfo> list(MeasurePositionInfo dto, PageBean pageBean);

    Result saveOrUpdate(MeasurePositionInfo dto);

    Result delete(String id);
}
