package com.kongque.service.api;

import com.kongque.dto.OrderRepairDto;
import com.kongque.dto.YunOrderDto;
import com.kongque.util.Result;

/**
 * @author Administrator
 */
public interface IApiService {

    Result getOrderPlanMaterialsByOrderPlanNo(String orderPlanNo);

    Result getRepairPlanMaterialsByRepairPlanNo(String repairPlanNo,String supplementNo);

    double getOrderPlanMaterialQuantityByOrderPlanNoAndMaterialId(String orderPlanNo,String materialId);
    double getRepairPlanMaterialQuantityByRepairPlanNoAndMaterialId(String repairPlanNo, String supplementNo,String materialId);
    /**
     * 保存云平台订单
     * @param dto
     * @return
     */
    Result saveYunOrder(YunOrderDto dto);

    Result saveYunRepairOrder(OrderRepairDto dto);


}
