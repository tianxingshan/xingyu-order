package com.kongque.service.basics;

import com.kongque.entity.basics.BodyMeasureInfo;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

/**
 * @author Administrator
 */
public interface IBodyMeasureInfoService {
    Result findNotSelectedByCategoryId(String areaId);

    Result saveOrUpdate(BodyMeasureInfo dto);

    Pagination<BodyMeasureInfo> list(BodyMeasureInfo dto, PageBean pageBean);
}
