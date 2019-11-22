package com.kongque.dto;

import com.kongque.entity.productionorder.MesOrderDetailMaterial;

import java.util.List;

/**
 * @program: xingyu-order
 * @description: 订单明细物料清单
 * @author: zhuxl
 * @create: 2018-10-16 18:11
 **/
public class MesOrderDetailMaterialDto {

    private String orderDetailId;
    private String materialPositionId;
    private List<MesOrderDetailMaterial> positionList;

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getMaterialPositionId() {
        return materialPositionId;
    }

    public void setMaterialPositionId(String materialPositionId) {
        this.materialPositionId = materialPositionId;
    }

    public List<MesOrderDetailMaterial> getPositionList() {
        return positionList;
    }

    public void setPositionList(List<MesOrderDetailMaterial> positionList) {
        this.positionList = positionList;
    }
}

