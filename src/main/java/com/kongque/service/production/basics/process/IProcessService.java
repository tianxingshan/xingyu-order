package com.kongque.service.production.basics.process;

import com.kongque.entity.process.MesProcess;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

public interface IProcessService {
	
	Pagination<MesProcess> list(MesProcess process, PageBean pageBean);
	
	Result saveOrUpdate(MesProcess process);
	
	Result del(String id);

}
