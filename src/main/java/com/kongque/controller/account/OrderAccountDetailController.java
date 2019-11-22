package com.kongque.controller.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kongque.dto.OrderAccountDetailDto;
import com.kongque.dto.OrderAccountDto;
import com.kongque.entity.order.OrderAccount;
import com.kongque.entity.order.OrderAccountDetail;
import com.kongque.service.orderAccount.IOrderAccountDetailService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

@Controller
@RequestMapping(value = "/orderAccountDetail")
public class OrderAccountDetailController {
	
	private final static Logger log = LoggerFactory.getLogger(OrderAccountDetailController.class);

	@Autowired
	private IOrderAccountDetailService orderAccountDetailService;
	
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ResponseBody
	public Pagination<OrderAccountDetail> list(OrderAccountDetailDto dto,PageBean pageBean){
		log.error("订单核算详情列表查询"+dto.toString());
		Pagination<OrderAccountDetail> orderAccountDetailList = orderAccountDetailService.orderAccountDetailList(pageBean, dto);
		return orderAccountDetailList;
	}
	
	@RequestMapping(value="/materiaDetail",method=RequestMethod.POST)
	@ResponseBody
	public Result materiaDetail(@RequestBody OrderAccountDetailDto dto){
		log.error("订单详情之物料详情 : "+dto.toString());
		Result materiaDetail = orderAccountDetailService.materiaDetail(dto);
		return materiaDetail;
	}
}
