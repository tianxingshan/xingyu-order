package com.kongque.service.customer;

import com.kongque.dto.XiuyuCustomerDto;
import com.kongque.entity.basics.XiuyuCustomer;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

public interface ICustomerService {
	
	public Pagination<XiuyuCustomer> customerList(XiuyuCustomerDto dto,PageBean pageBean);
	
	public Result saveOrUpdateCustomer(XiuyuCustomer customer);
	
	public Result delCustomer(String id);
	
	public Result remoteCustomer(String q,PageBean pageBean);

}
