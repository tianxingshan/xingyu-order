package com.kongque.service.production.basics.material;

import com.kongque.dto.ProductionBasicDto;
import com.kongque.entity.material.MaterialPosition;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

public interface IMaterialPositionService {
	
	Pagination<MaterialPosition> list(MaterialPosition position, PageBean pageBean);
	
	Result saveOrUpdate(ProductionBasicDto dto);
	
	Result del(String id);

}
