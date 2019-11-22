package com.kongque.service.production.basics.process;

import com.kongque.dto.GoodsSewProcessDto;
import com.kongque.entity.process.GoodsSewProcess;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

public interface IGoodsSewProcessService {
	
	Pagination<GoodsSewProcess> list(GoodsSewProcess sewProcess, PageBean pageBean);
	
	Result saveOrUpdate(GoodsSewProcessDto dto);
	
	Result del(String id);
	
	Result getPostionInfo(String categoryId);

}
