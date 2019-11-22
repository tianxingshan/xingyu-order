package com.kongque.controller.plan;

import com.kongque.common.BillStatus;
import com.kongque.controller.order.OrderController;
import com.kongque.dto.MesOrderDetailSearchDto;
import com.kongque.dto.OrderDetailSearchDto;
import com.kongque.dto.OrderPlanDetailDto;
import com.kongque.dto.OrderPlanDto;
import com.kongque.entity.plan.OrderPlan;
import com.kongque.entity.plan.OrderPlanDetail;
import com.kongque.model.MesOrderDetailModel;
import com.kongque.model.OrderDetailSearchModel;
import com.kongque.service.plan.OrderPlanService;
import com.kongque.service.productionorder.IMesOrderDetailService;
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
import java.text.ParseException;
import java.util.List;

/**
 * @author xiaxt
 * @date 2018/10/16.
 */
@Controller
public class OrderPlanMaintainController {
    private final static Logger log = LoggerFactory.getLogger(OrderController.class);

    @Resource
    private OrderPlanService orderPlanService;
    @Resource
    private IMesOrderDetailService service;

    /**
     * 查询生产计划单列表
     * @param dto
     * @param pageBean
     * @return
     */
    @RequestMapping(value = "/orderPlan/page/list", method = RequestMethod.GET)
    public @ResponseBody Pagination<OrderPlan> findOrderPlanByPage(OrderPlanDto dto, PageBean pageBean){
        log.error("生产计划单查询:"+dto.toString());
        return orderPlanService.findOrderPlanByPage(dto,pageBean,null);
    }

    /**
     * 根据ID删除生产计划单
     * @param dto
     * @return
     */
    @RequestMapping(value = "/orderPlan/deleteById", method = RequestMethod.POST)
    public @ResponseBody Result delOrderPlan(@RequestBody OrderPlanDto dto){
        log.error("生产计划单删除:"+dto.toString());
        return orderPlanService.delOrderPlan(dto);
    }

    /**
     * 根据ID删除生产计划单明细
     * @param orderPlanDetailId
     * @return
     */
    @RequestMapping(value = "/orderPlan/detail/deleteById", method = RequestMethod.GET)
    public @ResponseBody Result delOrderPlan(String orderPlanDetailId){
        log.error("生产计划单明细删除:"+orderPlanDetailId);
        return orderPlanService.delOrderPlanDetail(orderPlanDetailId);
    }

    /**
     * 根据生产计划单查询生产计划单明细
     * @param id
     * @return
     */
    @RequestMapping(value = "/orderPlan/detail/list", method = RequestMethod.GET)
    public @ResponseBody Result findOrderPlanDetails(String id){
        log.error("根据生产计划单查询生产计划单明细:"+id);
        return orderPlanService.findOrderPlanDetails(id);
    }

    /**
     * 根据生产计划单分页查询生产计划单明细
     * @param dto
     * @return
     */
    @RequestMapping(value = "/orderPlan/detail/page/list", method = RequestMethod.GET)
    public @ResponseBody Pagination<OrderPlanDetail> findOrderPlanDetailByPage(OrderPlanDetailDto dto, PageBean pageBean){
        log.error("根据生产计划单分页查询生产计划单明细:"+dto.toString());
        return orderPlanService.findOrderPlanDetailByPage(dto,pageBean);
    }

    /**
     * 新增或更新计划单
     * @param dto
     * @return
     */
    @RequestMapping(value = "/orderPlan/saveOrUpdate", method = RequestMethod.POST)
    public @ResponseBody Result saveOrUpdateOrderPlan(@RequestBody OrderPlanDto dto){
        log.error("新增或更新计划单:"+dto.toString());
        return orderPlanService.saveOrUpdateOrderPlan(dto);
    }

    /**
     * 根据ID查询计划单信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/orderPlan/findOrderPlan", method = RequestMethod.GET)
    public @ResponseBody Result findOrderPlan(String id){
        log.error("根据ID查询计划单信息:"+id);
        return orderPlanService.findOrderPlan(id);
    }

    /**
     * 查询订单明细列表
     * @param dto
     * @param pageBean
     * @return
     */
    @RequestMapping(value = "/orderPlan/orderDetail/list", method = RequestMethod.GET)
    public @ResponseBody Pagination<MesOrderDetailModel> getAllOrderDetailList(MesOrderDetailSearchDto dto , PageBean pageBean){
        log.error("计划维护订单明细分页查询"+dto.toString());
//        Pagination<MesOrderDetailModel> pagination = new Pagination<>();
        dto.setOrderDetailStatus(BillStatus.OrderDetailStatus.TECHNICALFINISHED.getValue());  //查询技术完成的订单明细
//        Long total = service.getCountAllOrderDetail(dto);
//        if(total != null){
//            pagination.setTotal(total);
//        }
//        List<MesOrderDetailModel> resultList = service.getAllOrderDetailList(dto,pageBean);
//        if(resultList != null){
//            pagination.setRows(resultList);
//        }
        return service.getAllOrderDetailList(dto,pageBean);
    }
}
