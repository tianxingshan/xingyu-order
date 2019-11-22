package com.kongque.controller.productionorder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kongque.dto.PlanOrderDetailDto;
import com.kongque.model.OrderPlanDetailModel;
import com.kongque.model.TwOrderModel;
import com.kongque.service.productionorder.IOrderPlanDetailService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;

@RestController
@RequestMapping("/orderPlanDetail")
public class OrderPlanDetailController {
	
	@Resource
    private IOrderPlanDetailService service;
	
	
	/**
	 * 查询生产计划明细列表
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public Pagination<OrderPlanDetailModel> list(PlanOrderDetailDto dto,PageBean pageBean){
		return service.list(dto,pageBean);
	}
	
	/**
	 * 查询台湾订单
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value="/tw/list",method=RequestMethod.GET)
	public Pagination<TwOrderModel> twList(PlanOrderDetailDto dto,PageBean pageBean){
		return service.twList(dto,pageBean);
	}

	// Excel导出
	@RequestMapping(value = "/getExcel", method = RequestMethod.GET)
	public void getExcel(PlanOrderDetailDto dto, HttpServletRequest request, HttpServletResponse response) {
		service.getExcel(dto, request, response);
	}

}
