package com.kongque.service.production.basics.process;

import com.kongque.dto.GoodsOptionalTechnologyDto;
import com.kongque.entity.process.GoodsOptionalTechnology;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

public interface IGoodsOptionalTechnologyService {
	
	Pagination<GoodsOptionalTechnology> list(GoodsOptionalTechnology technology, PageBean pageBean);
	
	Result saveOrUpdate(GoodsOptionalTechnologyDto dto);
	
	Result del(String id);

}
