package com.kongque.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @program: xingyu-order
 * @description: 配置尺码
 * @author: zhuxl
 * @create: 2018-10-15 13:47
 **/
public class MesOrderDetailSizeDto {
    private String id;
    //订单明细id
    private String orderDetailId;
    //尺码id
    private String mesMeasureSizeId;

    //可选项工序
    private String mesGoodsOptionalTechnologyId;
    //尺码名称
    private String mesMeasureSizeName;
    //备注
    private String remarks;
    //创建人员ID
    private String createUserId;
    //创建日期
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createTime;
    //修改人员ID
    private String updateUserId;
    //修改日期
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getMesMeasureSizeId() {
        return mesMeasureSizeId;
    }

    public void setMesMeasureSizeId(String mesMeasureSizeId) {
        this.mesMeasureSizeId = mesMeasureSizeId;
    }

    public String getMesMeasureSizeName() {
        return mesMeasureSizeName;
    }

    public void setMesMeasureSizeName(String mesMeasureSizeName) {
        this.mesMeasureSizeName = mesMeasureSizeName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getMesGoodsOptionalTechnologyId() {
        return mesGoodsOptionalTechnologyId;
    }

    public void setMesGoodsOptionalTechnologyId(String mesGoodsOptionalTechnologyId) {
        this.mesGoodsOptionalTechnologyId = mesGoodsOptionalTechnologyId;
    }
}

