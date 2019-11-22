package com.kongque.controller.plan;

import com.kongque.constants.Constants;
import com.kongque.controller.order.OrderController;
import com.kongque.dto.OrderPlanDto;
import com.kongque.entity.plan.OrderPlan;
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

/**
 * @author xiaxt
 * @date 2018/10/16.
 */
@Controller
public class OrderPlanAuditSendController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    //审核列表状态数组
    private static final Integer[] auditStatus = {
            Constants.PLAN_STATUS.待审核.getValByCode(),Constants.PLAN_STATUS.已审核.getValByCode(),
            Constants.PLAN_STATUS.已驳回.getValByCode()
    };
    //下达列表状态数组
    private static final Integer[] sendStatus = {
            Constants.PLAN_STATUS.已审核.getValByCode(),Constants.PLAN_STATUS.已下达.getValByCode()
    };

    @Resource
    private OrderPlanService orderPlanService;

    /**
     * 生产计划单审核、驳回、下达
     * @param dto
     * @return
     */
    @RequestMapping(value = "/orderPlan/auditOrSend", method = RequestMethod.POST)
    public @ResponseBody Result auditOrSendOrderPlan(@RequestBody OrderPlanDto dto){
        log.error("生产计划单审核或下达:"+dto.toString());
        return orderPlanService.auditOrSendOrderPlan(dto);
    }
    /**
     * 查询生产计划单审核列表
     * @param dto
     * @param pageBean
     * @return
     */
    @RequestMapping(value = "/orderPlan/page/list/audit", method = RequestMethod.GET)
    public @ResponseBody Pagination<OrderPlan> findOrderPlanAuditByPage(OrderPlanDto dto, PageBean pageBean){
        log.error("生产计划单审核列表查询:"+dto.toString());
//        return orderPlanService.findOrderPlanByPage(dto,pageBean,auditStatus);
        return orderPlanService.findOrderPlanByPage(dto,pageBean,null);
    }

    /**
     * 查询生产计划单下达列表
     * @param dto
     * @param pageBean
     * @return
     */
    @RequestMapping(value = "/orderPlan/page/list/send", method = RequestMethod.GET)
    public @ResponseBody Pagination<OrderPlan> findOrderPlanSendByPage(OrderPlanDto dto, PageBean pageBean){
        log.error("生产计划单下达列表查询:"+dto.toString());
//        return orderPlanService.findOrderPlanByPage(dto,pageBean,sendStatus);
        return orderPlanService.findOrderPlanByPage(dto,pageBean,null);
    }
}
