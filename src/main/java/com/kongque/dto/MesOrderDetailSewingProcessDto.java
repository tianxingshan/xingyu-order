package com.kongque.dto;

import java.util.List;

/**
 * @program: xingyu-order
 * @description: 订单明细缝制工序
 * @author: zhuxl
 * @create: 2018-10-16 17:36
 **/

public class MesOrderDetailSewingProcessDto {
    /**
     * 订单明细
     */
    private String orderDetailId;

    private String mesGoodsOptionalTechnologyId;

    private List<SewDto> sewProcessList;

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getMesGoodsOptionalTechnologyId() {
        return mesGoodsOptionalTechnologyId;
    }

    public void setMesGoodsOptionalTechnologyId(String mesGoodsOptionalTechnologyId) {
        this.mesGoodsOptionalTechnologyId = mesGoodsOptionalTechnologyId;
    }

    public List<SewDto> getSewProcessList() {
        return sewProcessList;
    }

    public void setSewProcessList(List<SewDto> sewProcessList) {
        this.sewProcessList = sewProcessList;
    }
}
