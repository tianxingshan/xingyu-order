package com.kongque.service.productionorder;

import java.util.List;

import com.kongque.dto.MesOrderDetailAssignDto;
import com.kongque.dto.MesOrderDetailMaterialDto;
import com.kongque.dto.MesOrderDetailMeasureDto;
import com.kongque.dto.MesOrderDetailPrintLogDto;
import com.kongque.dto.MesOrderDetailSearchDto;
import com.kongque.dto.MesOrderDetailSewingProcessDto;
import com.kongque.dto.MesOrderDetailSizeDto;
import com.kongque.entity.productionorder.MesOrderDetailMaterial;
import com.kongque.model.MesOrderDetailModel;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

/**
 * @program: xingyu-order
 * @description: 订单明细服务接口
 * @author: zhuxl
 * @create: 2018-10-10 16:01
 **/

public interface IMesOrderDetailService {

    //分配
    Result assign(List<MesOrderDetailAssignDto> mesOrderDetailAssignDtos);
    //取消分配
    Result unAssign(String id);


    //订单明细查询
    Pagination<MesOrderDetailModel> getAllOrderDetailList(MesOrderDetailSearchDto dto , PageBean pageBean);

    //订单明细条数
    Long getCountAllOrderDetail(MesOrderDetailSearchDto dto );


    //已分配订单明细查询
    List<MesOrderDetailModel> getAssignAllOrderDetailList(MesOrderDetailSearchDto dto , PageBean pageBean);

    //已分配订单明细条数
    Long getAssignCountAllOrderDetail(MesOrderDetailSearchDto dto );

    //未分配订单明细查询
    List<MesOrderDetailModel> getNotAssignAllOrderDetailList(MesOrderDetailSearchDto dto , PageBean pageBean);

    //未分配订单明细条数
    Long getNotAssignCountAllOrderDetail(MesOrderDetailSearchDto dto );

    /**
     * 分配尺码
     * @param dto
     * @return
     */
    Result assignMesOrderDetailSize(MesOrderDetailSizeDto dto);

    /**
     * 按照订单明细id查询分配尺码
     * @param orderDetailId
     * @return
     */
    Result getSizeByOrderDetailId(String orderDetailId);

    /**
     * 分配量体尺寸
     * @param dto
     * @return
     */
    Result assignMeasure(MesOrderDetailMeasureDto dto);

    /**
     * 取消分配量体尺寸
     * @param id
     * @return
     */
    Result unAssignMeasure(String id);

    /**
     * 获取分配部位尺寸通过订单明细id
     * @param orderDetailId
     * @return
     */
    Result getAssignMeasureByOrderDetailId(String orderDetailId);

    /**
     * 分配工序
     * @param dto
     * @return
     */
    Result assignSewingProcess(MesOrderDetailSewingProcessDto dto);

    /**
     * 取消分配工序
     * @param id
     * @return
     */
    Result unAssignSewingProcess(String id);

    /**
     * 获取分配工序通过订单明细id
     * @param orderDetailId
     * @return
     */
    Result getAssignSewingProcessByOrderDetailId(String orderDetailId);

    /**
     * 分配物料
     * @param dto
     * @return
     */
    Result assignMaterial(MesOrderDetailMaterialDto dto);

    /**
     * 取消分配物料
     * @param id
     * @return
     */
    Result unAssignMaterial(String id);

    /**
     * 获取分配物料通过订单明细id
     * @param orderDetailId
     * @return
     */
    Result getAssignMaterialByOrderDetailId(String orderDetailId);

    /**
     * 技术完成
     * @param id
     * @return
     */
    Result technicalFinished(String id,String userId);

    /**
     * 取消技术完成
     * @param id
     * @return
     */
    Result unTechnicalFinished(String id);

    /**
     * 添加打印
     * @param dtos
     * @return
     */
    Result saveMesOrderDetailPrintLog(List<MesOrderDetailPrintLogDto> dtos);

    Result getMesOrderDetailPrintLog(MesOrderDetailPrintLogDto dto);

    Result delAllMesOrderDetailPrintLogByCreateUserId(String createUserId);

    Result delMesOrderDetailPrintLogById(String id);
    
    
    /**根据订单明细Id数组获取list
     * @param String[]
     * @return
     */
    List<MesOrderDetailMaterial> getListByOrderDateilIds(String[] orderDetailIds);
}

