package com.kongque.service.repair;

import java.util.List;

import com.kongque.dto.PlanOrderDetailDto;
import org.springframework.web.multipart.MultipartFile;

import com.kongque.dto.OrderRepairDto;
import com.kongque.entity.repair.OrderRepair;
import com.kongque.entity.repair.OrderRepairCheck;
import com.kongque.model.OrderRepairModel;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IOrderRepairService {
	public Pagination<OrderRepairModel> list(OrderRepairDto dto, PageBean pageBean);// 根据条件分页查询微调单

	public Result saveOrUpdate(OrderRepairDto dto, MultipartFile[] files);// 添加或修改微调单

	public Result checkRepair(OrderRepairCheck dto);// 审核微调单

	public Result RepairById(String id);// 根据id查询某一微调单

	public List<OrderRepair> getHistory(OrderRepair repair);// 查询微调历史记录

	public Result changeStatus(String id,String status);// 修改微调单状态
	
	public Result remoteOrderRepair(String q);//根据条件模糊查询微调单
	
	public Result del(String id);// 删除微调单
	
	void getAttachment(String filePath);

	/**
	 * 根据微调单查询微调计划
	 * @param dto
	 * @return
	 */
	Result findMesRepairPlan(OrderRepairDto dto);

    Result RepairNoHistoryById(String id);

    Pagination<OrderRepairModel> repairOrderRatio(OrderRepairDto dto, PageBean pageBean);

    Result repairOrderRatioExportExcel(OrderRepairDto dto, HttpServletRequest request, HttpServletResponse response);

    Pagination<OrderRepairModel> repairProgress(OrderRepairDto dto, PageBean pageBean);

	Result repairProgressExportExcel(OrderRepairDto dto, HttpServletRequest request, HttpServletResponse response);
}
