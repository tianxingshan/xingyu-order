package com.kongque.controller.order;

import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kongque.model.OrderDetailModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kongque.dto.OrderCheckDto;
import com.kongque.dto.OrderDetailSearchDto;
import com.kongque.dto.OrderDto;
import com.kongque.entity.order.Order;
import com.kongque.model.OrderDetailSearchModel;
import com.kongque.model.OrderFinishedLabel;
import com.kongque.service.order.IOrderService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

@Controller
@RequestMapping(value="/order")
public class OrderController {
	
	private final static Logger log = LoggerFactory.getLogger(OrderController.class);
	@Resource
	private IOrderService service;
	
	/**
	 * 查询订单列表
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Pagination<Order> orderList(OrderDto dto,PageBean pageBean){
		log.error("订单查询查询"+dto.toString());
		return service.orderList(dto,pageBean);
	}
	
	/**
	 * 根据订单id获取订单全部信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getDetail/byId", method = RequestMethod.GET)
	@ResponseBody
	public Result orderDetail(String id){
		return service.orderDetail(id);
	}
	
	/**
	 * 新增或修改订单
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Result saveOrUpdate(OrderDto dto,MultipartFile[] files){
		return service.saveOrUpdate(dto,files);
	}
	
	
	/**
	 * 修改订单状态及财务审核
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Result checkOrder(@RequestBody OrderCheckDto dto){
		return service.checkOrUpdate(dto);
	}
	
	/**
	 * 查询订单明细列表
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value = "/detail/list", method = RequestMethod.GET)
	@ResponseBody
	public Pagination<OrderDetailSearchModel> orderDetailList(OrderDetailSearchDto dto,PageBean pageBean){
		log.error("订单明细查询查询"+dto.toString());
		Pagination<OrderDetailSearchModel> pagination = new Pagination<>();		
		try {
			Long total = service.queryCountWithParam(dto);
			if(total != null){
				pagination.setTotal(total);
			}
			List<OrderDetailSearchModel> resultList = service.queryDetailWithParam(dto,pageBean);
			if(resultList != null){			
				pagination.setRows(resultList);			
			}			
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		return pagination;
	}
	
	
	/**
	 * 导出订单明细excel
	 * @param odsDto
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/exportExcel",method = RequestMethod.GET)
	public void exportRepairBalExcel(OrderDetailSearchDto dto, HttpServletRequest request, HttpServletResponse response){
		service.exportRepairBalExcel(dto, request, response);
	}
	
	
	/**
	 * 查询成品标签列表
	 *
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value = "/listFinishedLabel", method = RequestMethod.GET)
	public @ResponseBody Pagination<OrderFinishedLabel> getOrderFinishedLabel(OrderFinishedLabel dto,PageBean pageBean) {
		return service.getOrderFinishedLabel(dto,pageBean);
	}
	
	
	/**
	 * 根据条件模糊查询订单列表
	 * @param q
	 * @return
	 */
	@RequestMapping(value = "/remote", method = RequestMethod.GET)
	@ResponseBody
	public Result remoteOrder(String q,PageBean pageBean){
		return service.remoteOrder(q,pageBean);
	}
	
	/**
	 * 根据bra值查找尺码
	 * @param sizeCode
	 * @return
	 */
	@RequestMapping(value = "/findSizeCode",method = RequestMethod.GET)
    @ResponseBody
	public Result findSizeCode(String number){
		return service.findSizeCode(number);
	}
	
	/**
	 * 获取订单附件文件流
	 * 
	 * @param path
	 */
	@RequestMapping(value = "/file", method = RequestMethod.GET)
	public @ResponseBody void getAttachement(String path) {
		service.getAttachment(path);
	}

	@RequestMapping(value = "/orderDetailById", method = RequestMethod.GET)
	public @ResponseBody Result orderDetailById(String id){
		return service.orderDetailById(id);
	}

	/**
	 * 订单进度查询
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value = "/orderProgress", method = RequestMethod.GET)
	@ResponseBody
	public Pagination<OrderDetailModel> orderProgress(OrderDetailSearchDto dto, PageBean pageBean) {
		return service.orderProgress(dto, pageBean);
	}

	@RequestMapping(value="/orderProgressExportExcel",method=RequestMethod.GET)
	public  Result orderProgressExportExcel(OrderDetailSearchDto dto, HttpServletRequest request, HttpServletResponse response){
		return service.orderProgressExportExcel(dto,request,response);
	}
	
	
}
