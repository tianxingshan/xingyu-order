package com.kongque.service.production.basics.measure;

import com.kongque.dto.ProductionBasicDto;
import com.kongque.entity.measure.MeasureSize;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

public interface IMeasureSizeService {
	
	Pagination<MeasureSize> list(MeasureSize size, PageBean pageBean);
	
	Result saveOrUpdate(ProductionBasicDto dto);
	
	Result delSize(String id);

}
