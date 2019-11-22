package com.kongque.controller.api;


import com.kongque.component.impl.JsonMapper;
import com.kongque.dto.OrderRepairDto;
import com.kongque.dto.YunOrderDto;
import com.kongque.service.api.IApiService;
import com.kongque.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 */
@RestController
public class ApiController {

    private final static Logger log = LoggerFactory.getLogger(ApiController.class);

    @Resource
    private IApiService service;

    /**
     * 通过订单计划单号获取物料数量信息
     * @param orderPlanNo
     * @return
     */
    @RequestMapping("/mes/api/getOrderPlanMaterialsByOrderPlanNo")
    public Result getOrderPlanMaterialsByOrderPlanNo(String orderPlanNo)
    {
        return service.getOrderPlanMaterialsByOrderPlanNo(orderPlanNo);
    }

    /**
     * 通过微调计划单号或申请单号获取物料数量信息
     * @param repairPlanNo
     * @param supplementNo
     * @return
     */
    @RequestMapping("/mes/api/getRepairPlanMaterialsByRepairPlanNo")
    public Result getRepairPlanMaterialsByRepairPlanNo(String repairPlanNo, String supplementNo)
    {
        return service.getRepairPlanMaterialsByRepairPlanNo(repairPlanNo,supplementNo);
    }

    /**
     * 通过微调计划单号或申请单号和物料id获取物料数量
     * @param repairPlanNo
     * @param supplementNo
     * @param materialId
     * @return
     */
    @RequestMapping("/mes/api/getRepairPlanMaterialQuantityByRepairPlanNoAndMaterialId")
    public double getRepairPlanMaterialQuantityByRepairPlanNoAndMaterialId(String repairPlanNo, String supplementNo,String materialId)
    {
        return service.getRepairPlanMaterialQuantityByRepairPlanNoAndMaterialId(repairPlanNo,supplementNo,materialId);
    }

    /**
     * 通过计划单号和物料id获取物料数量
     * @param orderPlanNo
     * @param materialId
     * @return
     */
    @RequestMapping("/mes/api/getOrderPlanMaterialQuantityByOrderPlanNoAndMaterialId")
    public double getOrderPlanMaterialQuantityByOrderPlanNoAndMaterialId(String orderPlanNo,String materialId)
    {
        return service.getOrderPlanMaterialQuantityByOrderPlanNoAndMaterialId(orderPlanNo,materialId);
    }

    @RequestMapping("/youzan/callback")
    public String callback(HttpServletRequest request, HttpServletResponse response){
        return request.getParameter("code");
    }

    /**
     * 保存云平台订单
     * @param dto
     * @return
     */
    @RequestMapping("/yun/saveYunOrder")
    public Result saveYunOrder(@RequestBody YunOrderDto dto){
        String json = JsonMapper.toJson(dto);
        log.info("云平台订单保存接口参数"+ json);
        return service.saveYunOrder(dto);
    }

    @RequestMapping("/yun/saveYunRepairOrder")
    public Result saveYunRepairOrder(@RequestBody OrderRepairDto dto){
        String json = JsonMapper.toJson(dto);
        log.info("云平台订单保存接口参数"+ json);
        return service.saveYunRepairOrder(dto);
    }





}
/**
 * @program: xingyu-order
 * @description: 外部接口
 * @author: zhuxl
 * @create: 2018-11-15 11:07
 **/
