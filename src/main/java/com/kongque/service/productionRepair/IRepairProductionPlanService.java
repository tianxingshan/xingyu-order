package com.kongque.service.productionRepair;

import java.util.Map;

import com.kongque.dto.MaterialsSaveDto;
import com.kongque.dto.MesRepairProductionPlanDto;
import com.kongque.dto.RepairPlanCheckDto;
import com.kongque.entity.productionRepair.MesRepairProductionPlan;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

public interface IRepairProductionPlanService {
	
	Pagination<MesRepairProductionPlan> list(MesRepairProductionPlanDto dto,PageBean pageBean);
	
	Result saveOrUpdate(MesRepairProductionPlanDto dto);
	
	Result orderRepair(String repairCode);
	
	Result orderRepairSave(String planId,String repairId);
	
	Result orderRepairDel(String planId,String repairId);
	
	Result materielSave(MaterialsSaveDto dto);
	
	Result repairOpinionSave(String repairId,String repairOpinion);
	
	Result del(String id);
	
	Result getInfromationById(String id);
	
	Result getMeterielInfromation(String repairId);
	
	Result check(RepairPlanCheckDto dto);
	
	Pagination<Map<String,Object>> repayList(MesRepairProductionPlanDto dto,PageBean pageBean);
	
	Result checkRepairStatus(String repairId,String status);
	
	Result repairFeedbackSave(String repairId,String repairFeedback);
	
	Result rejectPlan(RepairPlanCheckDto dto);

}
