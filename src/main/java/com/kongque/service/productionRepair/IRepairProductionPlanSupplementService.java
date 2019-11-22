package com.kongque.service.productionRepair;

import com.kongque.dto.RepairSupplementDto;
import com.kongque.entity.productionRepair.MesRepairProductionPlanSupplement;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

public interface IRepairProductionPlanSupplementService {
	
	Pagination<MesRepairProductionPlanSupplement> list(RepairSupplementDto dto,PageBean pageBean);
	
	Result saveOrUpdate(RepairSupplementDto dto);
	
	Result getInfoById(String id);

	/**
	 * 根据微调计划单号查询所有的补料单物料
	 * @param repairPlanId
	 * @return
	 */
	Result getInfoByRepairPlanId(String repairPlanId);
	
	Result status(String id,String userId,String status);
	
	Result del(String id);
	
}
