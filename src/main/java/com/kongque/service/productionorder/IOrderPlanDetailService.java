package com.kongque.service.productionorder;

import com.kongque.dto.PlanOrderDetailDto;
import com.kongque.model.OrderPlanDetailModel;
import com.kongque.model.TwOrderModel;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IOrderPlanDetailService {
	
	Pagination<OrderPlanDetailModel> list(PlanOrderDetailDto dto,PageBean pageBean);
	
	Pagination<TwOrderModel> twList(PlanOrderDetailDto dto,PageBean pageBean);

	void getExcel(PlanOrderDetailDto dto, HttpServletRequest request, HttpServletResponse response);

}
