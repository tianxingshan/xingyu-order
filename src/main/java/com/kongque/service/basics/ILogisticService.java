package com.kongque.service.basics;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kongque.dto.LogisticDto;
import com.kongque.dto.OrderDetailSearchDto;
import com.kongque.dto.OrderRepairDto;
import com.kongque.dto.PlanOrderDetailDto;
import com.kongque.entity.basics.Dept;
import com.kongque.entity.basics.Logistic;
import com.kongque.entity.basics.Tenant;
import com.kongque.entity.basics.XiuyuCustomer;
import com.kongque.entity.order.Order;
import com.kongque.entity.repair.OrderRepair;
import com.kongque.model.LogisticOrderModel;
import com.kongque.model.OrderDetailSearchModel;
import com.kongque.model.OrderPlanDetailModel;
import com.kongque.model.OrderRepairModel;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

public interface ILogisticService {

	Pagination<Logistic> list(LogisticDto dto, PageBean pageBean);
	
	Result saveOrUpdate(LogisticDto dto);

	Result delete(String id);
	
	Result updateStatus(String checkUserId,String id,String checkStatus);
	
	List<LogisticOrderModel> queryLogisticWithParam(LogisticDto dto, Integer page,Integer rows);
	
	long queryLogisticCountWithParam(LogisticDto dto);
	
	Result exportExcelData(LogisticDto dto,HttpServletRequest request,HttpServletResponse response);
	
	Result rollBackCheckStatus(String checkUserId,String id,String logisticType);
	
	Result updateLogisticStatus(String logisticId,String logisticStatus);
	
	Result selectLogisticOne(String logisticId);
	
	List<OrderRepair> queryOrderRepair(String q,String logisticType);
	
	List<Dept> queryTenant(String q,String tenantId);
	
	List<XiuyuCustomer> queryXiuyuCustomer(String q);
	
	List<Order> queryOrder(String q);
	
	Result getLogistic(String id);

	List<OrderDetailSearchModel> queryDetailWithParam(OrderDetailSearchDto odsDto, PageBean pageBean);

	Long queryCountWithParam(OrderDetailSearchDto odsDto) throws ParseException;

	Pagination<OrderRepairModel> list(OrderRepairDto dto, PageBean pageBean);

    Pagination<OrderPlanDetailModel> getFinishedOrderRepair(PlanOrderDetailDto dto, PageBean pageBean);

    Result finishedOrderReparirExportExcel(PlanOrderDetailDto dto, HttpServletRequest request, HttpServletResponse response);
}
