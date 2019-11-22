package com.kongque.controller.productionorder;

import com.kongque.dto.*;
import com.kongque.model.MesOrderDetailModel;
import com.kongque.service.productionorder.IMesOrderDetailService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: xingyu-order
 * @description: 订单明细分配
 * @author: zhuxl
 * @create: 2018-10-10 15:45
 **/
@Controller
@RequestMapping("/mesOrderDetail")
public class MesOrderDetailController {

    @Resource
    private IMesOrderDetailService service;

    /**
     * 订单明细分配版型师
     * @param dtos
     * @return
     */
    @RequestMapping(value = "/assign", method = RequestMethod.POST)
    @ResponseBody
    public Result assign(@RequestBody List<MesOrderDetailAssignDto> dtos){
        return service.assign(dtos);
    }

    /**
     * 取消订单明细分配版型师
     * @param id
     * @return
     */
    @RequestMapping(value = "/unAssign", method = RequestMethod.GET)
    @ResponseBody
    public Result unAssign(String id) {
        return service.unAssign(id);
    }

    /**
     * 查询所有订单明细信息
     * @param dto
     * @param pageBean
     * @return
     */
    @RequestMapping(value = "/getAllOrderDetailList", method = RequestMethod.GET)
    @ResponseBody
    public Pagination<MesOrderDetailModel> getAllOrderDetailList(MesOrderDetailSearchDto dto , PageBean pageBean){
//        Pagination<MesOrderDetailModel> pagination = new Pagination<>();
//                    Long total = service.getCountAllOrderDetail(dto);
//            if(total != null){
//                pagination.setTotal(total);
//            }
//        List<MesOrderDetailModel> resultList = service.getAllOrderDetailList(dto,pageBean);
//        if(resultList != null){
//            pagination.setRows(resultList);
//        }
        return service.getAllOrderDetailList(dto,pageBean);
    }

    /**
     * 获取已分配订单明细
     * @param dto
     * @param pageBean
     * @return
     */
    @RequestMapping(value = "/getAssignOrderDetailList", method = RequestMethod.GET)
    @ResponseBody
    public Pagination<MesOrderDetailModel> getAssignOrderDetailList(MesOrderDetailSearchDto dto , PageBean pageBean){
        Pagination<MesOrderDetailModel> pagination = new Pagination<>();
        Long total = service.getAssignCountAllOrderDetail(dto);
        if(total != null){
            pagination.setTotal(total);
        }
        List<MesOrderDetailModel> resultList = service.getAssignAllOrderDetailList(dto,pageBean);
        if(resultList != null){
            pagination.setRows(resultList);
        }
        return pagination;
    }

    /**
     * 获取已分配订单明细
     * @param dto
     * @param pageBean
     * @return
     */
    @RequestMapping(value = "/getNotAssignOrderDetailList", method = RequestMethod.GET)
    @ResponseBody
    public Pagination<MesOrderDetailModel> getNotAssignOrderDetailList(MesOrderDetailSearchDto dto , PageBean pageBean){
        Pagination<MesOrderDetailModel> pagination = new Pagination<>();
        Long total = service.getNotAssignCountAllOrderDetail(dto);
        if(total != null){
            pagination.setTotal(total);
        }
        List<MesOrderDetailModel> resultList = service.getNotAssignAllOrderDetailList(dto,pageBean);
        if(resultList != null){
            pagination.setRows(resultList);
        }
        return pagination;
    }

    /**
     * 分配尺码
     * @param dto
     * @return
     */
    @RequestMapping(value = "/assignMesOrderDetailSize", method = RequestMethod.POST)
    @ResponseBody
    public Result assignMesOrderDetailSize(@RequestBody MesOrderDetailSizeDto dto){
        return service.assignMesOrderDetailSize(dto);
    }



    /**
     * 分配量体尺寸
     * @param dto
     * @return
     */
    @RequestMapping(value = "/assignMeasure", method = RequestMethod.POST)
    @ResponseBody
    public Result assignMeasure(@RequestBody MesOrderDetailMeasureDto dto){
        return service.assignMeasure(dto);
    }


    /**
     * 取消分配量体尺寸
     * @param id
     * @return
     */
    @RequestMapping(value = "/unAssignMeasure", method = RequestMethod.GET)
    @ResponseBody
    public Result unAssignMeasure(String id){
        return service.unAssignMeasure(id);
    }

    /**
     * 查询分配量体通过订单明细id
     * @param orderDetailId
     * @return
     */
    @RequestMapping(value = "/getAssignMeasureByOrderDetailId", method = RequestMethod.GET)
    @ResponseBody
    public Result getAssignMeasureByOrderDetailId(String orderDetailId){
        return service.getAssignMeasureByOrderDetailId(orderDetailId);
    }

    /**
     * 分配工序
     * @param dto
     * @return
     */
    @RequestMapping(value = "/assignSewingProcess", method = RequestMethod.POST)
    @ResponseBody
    public Result assignSewingProcess(@RequestBody MesOrderDetailSewingProcessDto dto){
        return service.assignSewingProcess(dto);
    }

    /**
     * 取消分配工序
     * @param id
     * @return
     */
    @RequestMapping(value = "/unAssignSewingProcess", method = RequestMethod.GET)
    @ResponseBody
    public Result unAssignSewingProcess(String id){
        return service.unAssignSewingProcess(id);
    }

    /**
     * 获取分配工序通过订单明细id
     * @param orderDetailId
     * @return
     */
    @RequestMapping(value = "/getAssignSewingProcessByOrderDetailId", method = RequestMethod.GET)
    @ResponseBody
    public Result getAssignSewingProcessByOrderDetailId(String orderDetailId){
        return service.getAssignSewingProcessByOrderDetailId(orderDetailId);
    }

    /**
     * 分配物料
     * @param dto
     * @return
     */
    @RequestMapping(value = "/assignMaterial", method = RequestMethod.POST)
    @ResponseBody
    public Result assignMaterial(@RequestBody MesOrderDetailMaterialDto dto){
        return service.assignMaterial(dto);
    }

    /**
     * 取消分配物料
     * @param id
     *
     * @return
     */
    @RequestMapping(value = "/unAssignMaterial", method = RequestMethod.GET)
    @ResponseBody
    public Result unAssignMaterial(String id){
        return service.unAssignMaterial(id);
    }

    /**
     * 获取分配物料通过订单明细id
     * @param orderDetailId
     * @return
     */
    @RequestMapping(value = "/getAssignMaterialByOrderDetailId", method = RequestMethod.GET)
    @ResponseBody
    public Result getAssignMaterialByOrderDetailId(String orderDetailId){
        return service.getAssignMaterialByOrderDetailId(orderDetailId);
    }

    /**
     * 技术完成
     * @param id
     * @param userId
     * @return
     */
    @RequestMapping(value = "/technicalFinished", method = RequestMethod.GET)
    @ResponseBody
    public Result technicalFinished(String id,String userId){
        return service.technicalFinished(id,userId);
    }

    /**
     * 取消技术完成
     * @param id
     * @return
     */
    @RequestMapping(value = "/unTechnicalFinished", method = RequestMethod.GET)
    @ResponseBody
    public Result unTechnicalFinished(String id){
        return service.unTechnicalFinished(id);
    }

    @RequestMapping(value = "/getSizeByOrderDetailId", method = RequestMethod.GET)
    @ResponseBody
    public Result getSizeByOrderDetailId(String orderDetailId){
        return service.getSizeByOrderDetailId(orderDetailId);
    }


    /**
     * 根据登录用户Id查询打印列表
     * @param dto
     * @return
     */
    @RequestMapping(value = "/getMesOrderDetailPrintLog", method = RequestMethod.GET)
    @ResponseBody
    public Result getMesOrderDetailPrintLog(MesOrderDetailPrintLogDto dto){
        return service.getMesOrderDetailPrintLog(dto);
    }

    /**
     * 保存打印列表
     * @param dtos
     * @return
     */
    @RequestMapping(value = "/saveMesOrderDetailPrintLog", method = RequestMethod.POST)
    @ResponseBody
    public Result saveMesOrderDetailPrintLog(@RequestBody List<MesOrderDetailPrintLogDto> dtos){
        return service.saveMesOrderDetailPrintLog(dtos);
    }

    /**
     * 根据登录用户id删除打印列表
     * @param createUserId
     * @return
     */
    @RequestMapping(value = "/delAllMesOrderDetailPrintLogByCreateUserId", method = RequestMethod.GET)
    @ResponseBody
    public Result delAllMesOrderDetailPrintLogByCreateUserId(String createUserId){
        return service.delAllMesOrderDetailPrintLogByCreateUserId(createUserId);
    }

    /**
     * 根据id删除打印
     * @param id
     * @return
     */
    @RequestMapping(value = "/delMesOrderDetailPrintLogById", method = RequestMethod.GET)
    @ResponseBody
    public Result delMesOrderDetailPrintLogById(String id){
        return service.delMesOrderDetailPrintLogById(id);
    }


}

