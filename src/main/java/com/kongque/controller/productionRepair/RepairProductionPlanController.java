package com.kongque.controller.productionRepair;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kongque.dto.MaterialsSaveDto;
import com.kongque.dto.MesRepairProductionPlanDto;
import com.kongque.dto.RepairPlanCheckDto;
import com.kongque.entity.productionRepair.MesRepairProductionPlan;
import com.kongque.service.productionRepair.IRepairProductionPlanService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

@RestController
@RequestMapping(value = "/repairProductionPlan")
public class RepairProductionPlanController {
	@Resource
	private IRepairProductionPlanService service;
	
	/**
	 * 查询微调单生产计划列表
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public Pagination<MesRepairProductionPlan> list(MesRepairProductionPlanDto dto,PageBean pageBean){
		return service.list(dto,pageBean);
	}
	
	/**
	 * 保存或修改微调单生产计划单
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public Result saveOrUpdate(@RequestBody MesRepairProductionPlanDto dto) {
		return service.saveOrUpdate(dto);
	}
	
	/**
	 * 根据微调单号查询可添加生产计划的微调单
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value="/orderRepair",method=RequestMethod.GET)
	public Result orderRepair(String repairCode){
		return service.orderRepair(repairCode);
	}
	
	/**
	 * 新增微调单
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value="/orderRepair/save",method=RequestMethod.GET)
	public Result orderRepairSave(String planId,String repairId){
		return service.orderRepairSave(planId,repairId);
	}
	
	/**
	 * 删除微调单
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value="/orderRepair/del",method=RequestMethod.GET)
	public Result orderRepairDel(String planId,String repairId){
		return service.orderRepairDel(planId,repairId);
	}
	
	/**
	 * 新增或修改物料单
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value="/materiel/saveOrUpdate",method=RequestMethod.POST)
	public Result materielSave(@RequestBody MaterialsSaveDto dto){
		return service.materielSave(dto);
	}
	
	/**
	 * 保存或修改微调建议
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value="/repairOpinion/saveOrUpdate",method=RequestMethod.GET)
	public Result repairOpinionSave(String repairId,String repairOpinion){
		return service.repairOpinionSave(repairId,repairOpinion);
	}
	
	/**
	 * 根据id删除微调单生产计划
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public Result del(String id) {
		return service.del(id);
	}
	
	/**
	 * 根据id查询微调单生产计划详情
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/getInfromationById", method = RequestMethod.GET)
	public Result getInfromationById(String id) {
		return service.getInfromationById(id);
	}
	
	
	/**
	 * 根据微调单id查询物料信息
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/getMeterielInfromation", method = RequestMethod.GET)
	public Result getMeterielInfromation(String repairId) {
		return service.getMeterielInfromation(repairId);
	}
	
	
	/**
	 * 修改微调单生产计划单状态
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	public Result check(RepairPlanCheckDto dto) {
		return service.check(dto);
	}
	
	/**
	 * 查询微调单生产计划回报单列表列表
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value="/repay/list",method=RequestMethod.GET)
	public Pagination<Map<String,Object>> repayList(MesRepairProductionPlanDto dto,PageBean pageBean){
		return service.repayList(dto,pageBean);
	}
	
	/**
	 * 修改微调单状态(生产完成或撤销完成)
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/check/repairStatus", method = RequestMethod.GET)
	public Result checkRepairStatus(String repairId,String status) {
		return service.checkRepairStatus(repairId,status);
	}
	
	/**
	 * 保存或修改实调内容
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value="/repairFeedback/saveOrUpdate",method=RequestMethod.GET)
	public Result repairFeedbackSave(String repairId,String repairFeedback){
		return service.repairFeedbackSave(repairId,repairFeedback);
	}
	
	/**
	 * 驳回生产计划单
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/reject/plan", method = RequestMethod.GET)
	public Result rejectPlan(RepairPlanCheckDto dto) {
		return service.rejectPlan(dto);
	}
	
}
