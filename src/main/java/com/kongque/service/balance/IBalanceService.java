package com.kongque.service.balance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kongque.dto.BalanceDto;
import com.kongque.dto.OrderRepairDto;
import com.kongque.entity.balance.Balance;
import com.kongque.model.BalanceModel;
import com.kongque.model.OrderRepairModel;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

public interface IBalanceService {
	public Pagination<BalanceModel> list(BalanceDto dto, PageBean pageBean);// 根据条件分页查询结算单

	public Result saveOrUpdate(Balance dto);// 添加或修改结算单

	public Result balanceById(String id);// 根据id查询某一结算单

	public Result delRepair(BalanceDto dto);
	
	public Result del(String id);
	// 结算单导出

	public void getExcel(String id, HttpServletRequest request, HttpServletResponse response);//// 结算单导出

	public Result checkBalance(String id, String checkInfo, String checkStatus);// 结算单审核

	public Pagination<BalanceModel> listDetail(BalanceDto dto, PageBean pageBean);// 根据条件分页查询结算单明细
	public Pagination<OrderRepairModel> listRepair(OrderRepairDto dto, PageBean pageBean);// 根据条件分页查询待结算微调单

    Pagination<BalanceModel> find(BalanceDto dto, PageBean pageBean);

    Pagination<BalanceModel> orderBalance(BalanceDto dto, PageBean pageBean);
}
