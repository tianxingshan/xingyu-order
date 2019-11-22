package com.kongque.controller.basics;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.kongque.dto.OrderClosedDto;
import com.kongque.entity.basics.OrderDetailClosed;
import com.kongque.entity.order.Order;
import com.kongque.model.OrderClosedModel;
import com.kongque.service.basics.IOrderClosedService;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

@Controller
@RequestMapping(value = "/orderClosed")
public class OrderClosedController {
		
	@Resource
	private IOrderClosedService service;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody Pagination<OrderClosedModel> getTryClothesRecordList(OrderClosedDto dto,Integer page,Integer rows) {
		Pagination<OrderClosedModel> pagination = new Pagination<>();	
		Long total = service.getOrderClosedModelCount(dto);
		if(total != null){
			pagination.setTotal(total);
		}
		List<OrderClosedModel> resultList = service.getOrderClosedModelList(dto,page,rows);
		if(resultList != null){			
			pagination.setRows(resultList);			
		}		
		return pagination;
	}
	/*
	 * 得到所有的订单id
	 * userShopId   用户所在的门店id
	 */
	@RequestMapping(value = "/remote", method = RequestMethod.GET)
	public @ResponseBody List<Order> remote(String q,String userShopId) {
		return service.remote(q,userShopId);
	}
	/**
	 * 删除一条结案单
	 */
	@RequestMapping(value = "/del/{closedId}", method = RequestMethod.DELETE)
	public @ResponseBody Result deleteOrder(@PathVariable String closedId) {
		return service.deleteClosed(closedId);
	}
	@RequestMapping(value = "/stylelist", method = RequestMethod.GET)
	public @ResponseBody Pagination<OrderClosedModel> getStyleList(OrderClosedDto dto,Integer page,Integer rows) {
		Pagination<OrderClosedModel> pagination = new Pagination<>();	
		Long total = service.getStyleListCount(dto);
		if(total != null){
			pagination.setTotal(total);
		}
		List<OrderClosedModel> resultList = service.getStyleList(dto,page,rows);
		if(resultList != null){			
			pagination.setRows(resultList);			
		}		
		return pagination;
	}
	/**
	 * 保存或修改结案单
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody Result saveOrUpdate(@RequestBody OrderClosedDto dto){
		return service.saveOrUpdate(dto);
		
	}
	/**
	 * 结案单审核
	 * @param orderClosed
	 * @return
	 */
	
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public @ResponseBody Result check(@RequestBody OrderClosedDto orderClosed){
		return service.check(orderClosed);
		
	}
	/**
	 * 根据结案单id得到结案单详情
	 * @param closedId
	 * @return
	 */
	@RequestMapping(value = "/getClosedDetaillist/{closedId}", method = RequestMethod.POST)
	public @ResponseBody List<OrderDetailClosed> getClosedDetaillist(@PathVariable String closedId){
		return service.getClosedDetaillist(closedId);
	}
	/**
	 * 根据结案单id查询结案单详情表
	 * @param dto
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/checklist", method = RequestMethod.GET)
	public @ResponseBody Pagination<OrderClosedModel> getClosedDetailList(OrderClosedDto dto,Integer page,Integer rows) {
		Pagination<OrderClosedModel> pagination = new Pagination<>();	
		Long total = service.getClosedDetailCount(dto);
		if(total != null){
			pagination.setTotal(total);
		}
		List<OrderClosedModel> resultList = service.getClosedDetailList(dto,page,rows);
		if(resultList != null){			
			pagination.setRows(resultList);			
		}		
		return pagination;
	}
	
	/**
	 * 修改结案单状态和说明
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody Result updateStatusAndInstruction(@RequestBody OrderClosedDto dto){
		return service.updateStatusAndInstruction(dto);
		
	}
	/**
	 * 根据订单编号查询订单详情
	 * @param orderCode
	 * @return
	 */
	@RequestMapping(value = "/orderDetail/{orderCode}", method = RequestMethod.GET)
	public @ResponseBody Result orderDetailList(@PathVariable String orderCode){
	    return service.orderDetailList(orderCode);
	}
	/**
	 * 根据订单编号查询订单
	 * @param orderCode
	 * @return
	 */
	@RequestMapping(value = "/order/{orderCode}", method = RequestMethod.GET)
	public @ResponseBody Result order(@PathVariable String orderCode){
	    return service.order(orderCode);
	}
	/**
	 * 根据结案单查询结案单详情
	 * @param orderClosedId
	 * @return
	 */
	@RequestMapping(value = "/orderDetailClosed/{orderClosedId}", method = RequestMethod.GET)
	public @ResponseBody Result orderClosedList(@PathVariable String orderClosedId){
	    return service.orderClosedList(orderClosedId);
	}
}
