package com.kongque.service.plan;

import com.kongque.dto.OrderPlanDetailDto;
import com.kongque.dto.OrderPlanDto;
import com.kongque.entity.plan.OrderPlan;
import com.kongque.entity.plan.OrderPlanDetail;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

import java.util.Map;

/**
 * @author xiaxt
 * @date 2018/10/16.
 */
public interface OrderPlanService {
    /**
     * 新增或更新计划单
     * @param dto
     */
    Result saveOrUpdateOrderPlan(OrderPlanDto dto);

    /**
     * 删除计划单
     * @param dto
     */
    Result delOrderPlan(OrderPlanDto dto);

    /**
     * 删除计划单明细
     * @param id
     */
    Result delOrderPlanDetail(String id);

    /**
     * 删除计划单明细
     * @param dto
     */
    Result delOrderPlanDetail(OrderPlanDetailDto dto);

    /**
     * 分页查询计划单
     * @param dto
     * @param pageBean
     * @return
     */
    Pagination<OrderPlan> findOrderPlanByPage(OrderPlanDto dto, PageBean pageBean, Integer[] planStatus);

    /**
     * 分页查询计划单明细
     * @param dto
     * @param pageBean
     * @return
     */
    Pagination<OrderPlanDetail> findOrderPlanDetailByPage(OrderPlanDetailDto dto, PageBean pageBean);

    /**
     * 分页查询计划单及其包含明细信息
     * @param dto
     * @param pageBean
     * @return
     */
    Pagination<Map<String,Object>> findOrderPlanAndDetailByPage(OrderPlanDto dto, PageBean pageBean, Integer[] planStatus);

    /**
     * 根据计划单ID查询计划单信息
     * @param id
     * @return
     */
    Result findOrderPlan(String id);

    /**
     * 根据计划单ID查询计划单明细
     * @param id
     * @return
     */
    Result findOrderPlanDetails(String id);

    /**
     * 计划单审核或下达
     * @param dto
     */
    Result auditOrSendOrderPlan(OrderPlanDto dto);

    /**
     * 计划驳回或完成
     * @param dto
     */
    Result rejectOrFinishOrderPlan(OrderPlanDto dto);

    /**
     * 订单明细完成或撤销
     * @param dto
     */
    Result rejectOrFinishOrderDetail(OrderPlanDetailDto dto);

    /**
     * 计划单明细删除
     * @param dto
     */
    Result deleteOrderPlanDetail(OrderPlanDetailDto dto);
}
