package com.kongque.service.orderAccount;

import java.util.List;

import com.kongque.dto.OrderAccountDetailDto;
import com.kongque.entity.order.OrderAccountDetail;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

/**
 * @author sws
 *
 */
public interface IOrderAccountDetailService {

	/**订单核算明细列表
	 * @param pageBean
	 * @param dto (核算单id 和 pageBean)
	 * @return
	 */
	Pagination<OrderAccountDetail> orderAccountDetailList(PageBean pageBean,OrderAccountDetailDto dto); 
	
	/**按照月份核算订单并添加入库(添加到明细表)
	 * @param dto (订单主键 c_id + 月份accountMonth)
	 * @return
	 */
	Result accountOrderByMonth(OrderAccountDetailDto dto);
	
	/**重新核算订单
	 * @param orderAccountId
	 * @return
	 */
	/*List<OrderAccountDetail> againAccountOrder(String orderAccountId);*/
	
	/**核算订单详情之物料详情
	 * @param dto (orderDetailId,accountMonth)
	 * @return
	 */
	Result materiaDetail(OrderAccountDetailDto dto);
	
	/**物理删除核算明细
	 * @param dto (orderAccountId)
	 */
	int deleteByOrderAccountId(OrderAccountDetailDto dto);
}
