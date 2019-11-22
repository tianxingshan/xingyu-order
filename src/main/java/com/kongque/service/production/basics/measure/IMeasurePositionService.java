package com.kongque.service.production.basics.measure;

import com.kongque.dto.ProductionBasicDto;
import com.kongque.entity.measure.MeasurePosition;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

public interface IMeasurePositionService {
	
	Pagination<MeasurePosition> list(MeasurePosition position, PageBean pageBean);
	
	Result saveOrUpdate(ProductionBasicDto dto);
	
	Result delPosition(String id);

}
