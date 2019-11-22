package com.kongque.service.production.basics.material;

import java.util.List;

import com.kongque.dto.ProductionBasicDto;
import com.kongque.entity.material.MaterialCategory;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

public interface IMaterialCategoryService {
	
	Pagination<MaterialCategory> list(PageBean pageBean);
	
	Pagination<MaterialCategory> listChildren(String id,PageBean pageBean);
	
	Result saveOrUpdate(ProductionBasicDto dto);
	
	Result del(String id);
	
	List<MaterialCategory> getListByIds(String[] Ids);
}
