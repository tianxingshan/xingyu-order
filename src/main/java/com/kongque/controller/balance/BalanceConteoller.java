package com.kongque.controller.balance;

import java.text.ParseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.kongque.dto.BalanceDto;
import com.kongque.dto.OrderRepairDto;
import com.kongque.entity.balance.Balance;
import com.kongque.model.BalanceModel;
import com.kongque.model.OrderRepairModel;
import com.kongque.service.balance.IBalanceService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

@Controller
@RequestMapping("/balance")
public class BalanceConteoller {
	@Resource
	private IBalanceService service;
	private static Logger logger = LoggerFactory.getLogger(BalanceConteoller.class);

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Pagination<BalanceModel> list(BalanceDto dto, PageBean pageBean) throws ParseException {
		logger.info("根据条件分页查询结算单", dto.toString(), pageBean.toString());
		return service.list(dto, pageBean);
		// 根据条件分页查询结算单
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Result saveOrUpdate(@RequestBody Balance dto) {
		logger.info("添加或修改结算单", dto.toString());
		// 添加或修改结算单
		return service.saveOrUpdate(dto);
	}

	// 根据id删除某一结算单下的微调单
	@RequestMapping(value = "/del/repair", method = RequestMethod.POST)
	@ResponseBody
	public Result delRepair(@RequestBody BalanceDto dto) {
		return service.delRepair(dto);
	}
	
	// 根据id删除某一结算单
	@RequestMapping(value = "/del", method = RequestMethod.GET)
	@ResponseBody
	public Result del(String id) {
		return service.del(id);
	}

	// 结算单导出
	@RequestMapping(value = "/getExcel", method = RequestMethod.GET)
	@ResponseBody
	public void getExcel(String id, HttpServletRequest request, HttpServletResponse response) {
		logger.info("结算单导出", id.toString(), request.toString(), response.toString());
		service.getExcel(id, request, response);
	}

	// 结算单审核
	@RequestMapping(value = "/checkBalance", method = RequestMethod.GET)
	@ResponseBody
	public Result checkBalance(String id, String checkInfo, String checkStatus) {
		logger.info("结算单审核", id.toString(), checkInfo.toString(), checkStatus.toString());
		return service.checkBalance(id, checkInfo, checkStatus);
	}

	// 结算单详情列表查询
	@RequestMapping(value = "/listDetail", method = RequestMethod.GET)
	@ResponseBody
	public Pagination<BalanceModel> listDetail(BalanceDto dto, PageBean pageBean) {
		logger.info("结算单详情查询", dto.toString(), pageBean.toString());

		return service.listDetail(dto, pageBean);
	}

	// 根据id查询结算单详情查询
	@RequestMapping(value = "/balanceById", method = RequestMethod.GET)
	@ResponseBody
	public Result balanceById(String id) {
		logger.info("根据id结算单详情查询", id);
		return service.balanceById(id);
	}
	// 根据条件分页查询待结算微调单
	@RequestMapping(value = "/listRepair", method = RequestMethod.GET)
	@ResponseBody
	public Pagination<OrderRepairModel> listRepair(OrderRepairDto dto, PageBean pageBean) {
		return service.listRepair(dto, pageBean);
	}

	// 结算明细查询
	@RequestMapping(value = "/find", method = RequestMethod.GET)
	@ResponseBody
	public Pagination<BalanceModel> find(BalanceDto dto, PageBean pageBean) {
		return service.find(dto, pageBean);
	}


	/**
	 * 客户订单结算查询
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value = "/orderBalance", method = RequestMethod.GET)
	@ResponseBody
	public Pagination<BalanceModel> orderBalance(BalanceDto dto, PageBean pageBean) {
		return service.orderBalance(dto, pageBean);
	}

}
