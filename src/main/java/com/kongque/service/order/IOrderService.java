package com.kongque.service.order;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kongque.model.OrderDetailModel;
import org.springframework.web.multipart.MultipartFile;

import com.kongque.dto.OrderCheckDto;
import com.kongque.dto.OrderDetailSearchDto;
import com.kongque.dto.OrderDto;
import com.kongque.entity.order.Order;
import com.kongque.model.OrderDetailSearchModel;
import com.kongque.model.OrderFinishedLabel;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

public interface IOrderService {
	
	Pagination<Order> orderList(OrderDto dto,PageBean pageBean);
	
	Result orderDetail(String id);
	
	Result saveOrUpdate(OrderDto dto,MultipartFile[] files);
	
	Result checkOrUpdate(OrderCheckDto dto);
	
	List<OrderDetailSearchModel> queryDetailWithParam(OrderDetailSearchDto dto,PageBean pageBean);
	
	Long queryCountWithParam(OrderDetailSearchDto dto) throws ParseException;
	
	void exportRepairBalExcel(OrderDetailSearchDto dto, HttpServletRequest request, HttpServletResponse response);
	
	Pagination<OrderFinishedLabel> getOrderFinishedLabel(OrderFinishedLabel dto, PageBean pageBean);
	
    Result remoteOrder(String q,PageBean pageBean);
    
    Result findSizeCode(String number);
    
    void getAttachment(String filePath);

    Result orderDetailById(String id);

    Pagination<OrderDetailModel> orderProgress(OrderDetailSearchDto dto, PageBean pageBean);

	Result orderProgressExportExcel(OrderDetailSearchDto dto, HttpServletRequest request, HttpServletResponse response);
}
