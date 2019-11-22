package com.kongque.controller.account;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kongque.dto.OrderAccountDto;
import com.kongque.entity.order.OrderAccount;
import com.kongque.service.orderAccount.IOrderAccountService;
import com.kongque.util.HttpClientUtils;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/orderAccount")
public class OrderAccountController {
	
	private final static Logger log = LoggerFactory.getLogger(OrderAccountController.class);
	
	@Autowired
	private IOrderAccountService orderAccountService;
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ResponseBody
	public Pagination<OrderAccount> list(OrderAccountDto dto,PageBean pageBean){
		log.error("订单核算列表查询"+dto.toString());
		Pagination<OrderAccount> list = orderAccountService.listPage(pageBean, dto);
		return list;
	}
	
	/**核算订单(核算的时候,先检查是否核算过,以前核算过则更新,否则添加)
	 * @param dto 
	 * @return
	 */
	@RequestMapping(value="/saveOrUpdate",method=RequestMethod.POST)
	@ResponseBody
	public Result saveOrUpdate(@RequestBody OrderAccountDto dto){
		log.error("核算指定月份订单 : "+dto.toString());
		Result saveOrUpdate = orderAccountService.saveOrUpdate(dto);
		return saveOrUpdate;
		
	}
	
	@RequestMapping(value="/getAccountMonth",method=RequestMethod.GET)
	@ResponseBody
	public Result getAccountMonth(){
		log.error("获取订单要核算的月份,不需要参数 : ");
		
		return orderAccountService.getOrderAccountMonth();
		 
	}
	
	
	/* 返回空数组和null是不同情况
	//测试第三方返回数据null
	@RequestMapping(value="/a",method=RequestMethod.GET)
	public void a(){
		String[] arr = {"1"};
		
		String doPost = HttpClientUtils.doPost("http://192.168.0.51:8080/xingyu-order/orderAccount/test", 1, 1, arr);
		System.out.println(" 返回数据 :" + doPost);
		Object object = JSONObject.fromObject(doPost).get("returnData");
		System.out.println("data没返回时 : " + object);
		JSONArray fromObject = JSONArray.fromObject(object);
		System.out.println(fromObject.toString());
		for (Object object2 : fromObject) {
			System.out.println("json数组数null的时候 : " + object2);
		}
	}
	
	@RequestMapping(value = "/test",method = RequestMethod.POST)
	@ResponseBody
	public Result test(Integer year,Integer month,String[] ids) {
		Result result = new Result();
		List<String> list = new ArrayList<>();
//		list.add("dsfdg");
//		result.setReturnData(list);
		return result;
	}*/

}
