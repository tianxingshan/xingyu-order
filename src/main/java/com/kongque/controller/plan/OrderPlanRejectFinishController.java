package com.kongque.controller.plan;

import com.kongque.constants.Constants;
import com.kongque.controller.order.OrderController;
import com.kongque.dto.OrderPlanDetailDto;
import com.kongque.dto.OrderPlanDto;
import com.kongque.service.plan.OrderPlanService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author xiaxt
 * @date 2018/10/16.
 */
@Controller
public class OrderPlanRejectFinishController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private static final Integer[] rejectStatus = {
            Constants.PLAN_STATUS.已下达.getValByCode(),Constants.PLAN_STATUS.生产完成.getValByCode(),
            Constants.PLAN_STATUS.已驳回.getValByCode()
    };

    @Resource
    private OrderPlanService orderPlanService;

    /**
     * 生产计划单回报列表查询
     * @param dto
     * @param pageBean
     * @return
     */
    @RequestMapping(value = "/orderPlan/page/list/reject", method = RequestMethod.GET)
    public @ResponseBody Pagination<Map<String,Object>> findOrderPlanAndDetailByPage(OrderPlanDto dto, PageBean pageBean){
        log.error("生产计划单回报列表查询:"+dto.toString());
        return orderPlanService.findOrderPlanAndDetailByPage(dto,pageBean,rejectStatus);
    }

    /**
     * 生产计划单生产完成或驳回
     * @param dto
     * @return
     */
    @RequestMapping(value = "/orderPlan/rejectOrFinish", method = RequestMethod.POST)
    public @ResponseBody Result rejectOrFinishOrderPlan(@RequestBody OrderPlanDto dto){
        log.error("生产计划单生产完成或驳回:"+dto.toString());
        return orderPlanService.rejectOrFinishOrderPlan(dto);
    }

    /**
     * 订单明细生产完成或驳回
     * @param dto
     * @return
     */
    @RequestMapping(value = "/orderPlan/detail/rejectOrFinish", method = RequestMethod.POST)
    public @ResponseBody Result rejectOrFinishOrderDetail(@RequestBody OrderPlanDetailDto dto){
        log.error("订单明细生产完成或驳回:"+dto.toString());
        return orderPlanService.rejectOrFinishOrderDetail(dto);
    }

    /**
     * 删除单条计划单明细
     * @param dto
     * @return
     */
    @RequestMapping(value = "/orderPlan/detail/delete", method = RequestMethod.POST)
    public @ResponseBody Result deleteOrderPlanDetail(@RequestBody OrderPlanDetailDto dto){
        log.error("删除单条计划单明细:"+dto.toString());
        return orderPlanService.deleteOrderPlanDetail(dto);
    }
}
