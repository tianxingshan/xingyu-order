package com.kongque.service.basics;

import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import com.kongque.dto.OrderClosedDto;
import com.kongque.entity.basics.OrderDetailClosed;
import com.kongque.entity.order.Order;
import com.kongque.model.OrderClosedModel;
import com.kongque.util.Result;


public interface IOrderClosedService {
	/*分页获取结案单列表*/
	public List<OrderClosedModel> getOrderClosedModelList(OrderClosedDto dto,Integer page,Integer rows);
	
	public Long getOrderClosedModelCount(OrderClosedDto dto);
	
	//userShopId    用户所在的门店id
	public List<Order> remote(String q,String userShopId);
	
	public Result deleteClosed(String closedId);
	
	/**
	 * 根据订单编号查询款式
	 */
	public List<OrderClosedModel> getStyleList(OrderClosedDto dto,Integer page,Integer rows);
	
	public Long getStyleListCount(OrderClosedDto dto);
	/**
	 * 根据结案单id查询款式
	 */
	public List<OrderClosedModel> getClosedDetailList(OrderClosedDto dto,Integer page,Integer rows);
	
	public Long getClosedDetailCount(OrderClosedDto dto);
	
	//保存或修改结案单
	public Result saveOrUpdate(OrderClosedDto orderClosed);
	
	//查询结案单详情表
	public List<OrderDetailClosed> getClosedDetaillist(String closedID);
	//结案单审核通过和驳回
	public Result updateStatusAndInstruction(OrderClosedDto orderClosed);

	/**
	 * 根据订单号查询未结案的订单明细
	 * @return
	 */
	public Result orderDetailList(String orderCode);

	/**
	 * 根据订单号查询订单信息
	 * @param orderCode
	 * @return
	 */
	public Result order(String orderCode);

	/**
	 * 根据结案单id查询结案明细
	 * @param orderClosedId
	 * @return
	 */
	public Result orderClosedList(String orderClosedId);

	/**
	 * 结案单审核
	 * userName   登录用户的用户名
	 * @param map
	 * @return
	 */
	public Result check(OrderClosedDto dto);

}
