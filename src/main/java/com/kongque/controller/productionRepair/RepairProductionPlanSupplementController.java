package com.kongque.controller.productionRepair;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kongque.dto.RepairSupplementDto;
import com.kongque.entity.productionRepair.MesRepairProductionPlanSupplement;
import com.kongque.service.productionRepair.IRepairProductionPlanSupplementService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;


@RestController
@RequestMapping(value = "/repairSupplement")
public class RepairProductionPlanSupplementController {
	
	@Resource
	private IRepairProductionPlanSupplementService service;
	
	/**
	 * 查询微调单生产计划补料单列表
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public Pagination<MesRepairProductionPlanSupplement> list(RepairSupplementDto dto,PageBean pageBean){
		return service.list(dto,pageBean);
	}
	
	
	/**
	 * 保存或修改微调单生产计划补料单
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public Result saveOrUpdate(@RequestBody RepairSupplementDto dto) {
		return service.saveOrUpdate(dto);
	}


	/**
	 * 根据id查询微调单生产计划补料单详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/getInfoById",method=RequestMethod.GET)
	public Result getInfoById(String id){
		return service.getInfoById(id);
	}

	/**
	 * 根据微调计划单id查询所有微调单生产计划补料单详情
	 * @param repairPlanId
	 * @return
	 */
	@RequestMapping(value="/getInfoByRepairPlanId",method=RequestMethod.GET)
	public Result getInfoByRepairPlanId(String repairPlanId){
		return service.getInfoByRepairPlanId(repairPlanId);
	}

	/**
	 * 提交微调单生产计划补料单
	 * @param id
	 * @param userId
	 * @param status
	 * @return
	 */
	@RequestMapping(value="/status",method=RequestMethod.GET)
	public Result status(String id,String userId,String status){
		return service.status(id,userId,status);
	}

	/**
	 * 根据id删除微调单生产计划补料单
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/del",method=RequestMethod.GET)
	public Result del(String id){
		return service.del(id);
	}
	
}
