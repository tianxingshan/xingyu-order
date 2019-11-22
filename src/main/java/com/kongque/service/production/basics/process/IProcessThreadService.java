package com.kongque.service.production.basics.process;

import com.kongque.dto.ProductionBasicDto;
import com.kongque.entity.process.MesProcessThread;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

public interface IProcessThreadService {
	
	Pagination<MesProcessThread> list(MesProcessThread thread, PageBean pageBean);
	
	Result saveOrUpdate(ProductionBasicDto dto);
	
	Result del(String id);

}
