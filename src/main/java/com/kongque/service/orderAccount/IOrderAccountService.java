package com.kongque.service.orderAccount;

import java.util.List;

import com.kongque.dto.OrderAccountDto;
import com.kongque.entity.order.OrderAccount;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

/**
 * @author sws
 *
 */
public interface IOrderAccountService {
	
	
	/**订单核算分页列表
	 * @param pageBean
	 * @param dto
	 * @return
	 */
	Pagination<OrderAccount> listPage(PageBean pageBean,OrderAccountDto dto);

	/**生成订单核算数据
	 * @param dto
	 * @return
	 */
	Result saveOrUpdate(OrderAccountDto dto);
	
	List<OrderAccount> list(OrderAccountDto dto);
	
	/**获取订单核算要核算的月份(已统计完月份的下一个月,若数据库是空,默认2018-10)
	 * @return
	 */
	Result getOrderAccountMonth();
	
	
	
}
