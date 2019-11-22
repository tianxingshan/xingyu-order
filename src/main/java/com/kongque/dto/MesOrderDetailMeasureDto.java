package com.kongque.dto;

import java.util.List;

/**
 * @program: xingyu-order
 * @description: 订单明细量体尺寸
 * @author: zhuxl
 * @create: 2018-10-16 16:06
 **/
public class MesOrderDetailMeasureDto {

    /**
     * 订单明细id
     */
    private String orderDetailId;
    /**
     * 部位尺寸信息
     */
    private List<PositionDto> positionList;

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public List<PositionDto> getPositionList() {
        return positionList;
    }

    public void setPositionList(List<PositionDto> positionList) {
        this.positionList = positionList;
    }
}

