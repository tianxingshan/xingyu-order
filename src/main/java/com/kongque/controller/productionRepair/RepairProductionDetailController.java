package com.kongque.controller.productionRepair;

import com.kongque.dto.RepairProductionDetailDto;
import com.kongque.entity.repair.OrderRepairCopySearch;
import com.kongque.model.RepairProductionDetailModel;
import com.kongque.service.productionRepair.RepairProductionDetailService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xiaxt
 * @date 2019/1/9.
 */
@Controller
public class RepairProductionDetailController {
    private static final Logger log = LoggerFactory.getLogger(RepairProductionDetailController.class);

    @Resource
    private RepairProductionDetailService service;

    /**
     * 分页查询微调生产明细
     * @param dto
     * @param pageBean
     * @return
     */
    @RequestMapping(value = "/repairProductionDetail/find/page", method = RequestMethod.GET)
    public @ResponseBody Pagination<RepairProductionDetailModel> findRepairProductionDetailByPage(RepairProductionDetailDto dto, PageBean pageBean){
        return service.findRepairProductionDetailByPage(dto,pageBean);
    }

    /**
     * 导出微调生产明细
     * @param dto
     * @param request
     * @param response
     */
    @RequestMapping(value = "/repairProductionDetail/export", method = RequestMethod.GET)
    public @ResponseBody void findRepairProductionDetailByPage(RepairProductionDetailDto dto, HttpServletRequest request, HttpServletResponse response){
        service.exportRepairProductionDetail(dto,request,response);
    }

    /**
     * 根据条件模糊查询微调单
     * @param q
     * @param pageBean
     * @return
     */
    @RequestMapping(value = "/repairProductionDetail/remote/repair", method = RequestMethod.GET)
    @ResponseBody
    public Pagination<OrderRepairCopySearch> remoteOrderRepair(String q, PageBean pageBean){
        return service.remoteOrderRepair(q,pageBean);
    }

}
