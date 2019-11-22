package com.kongque.model;

import java.util.Date;

/**
 * @author xiaxt
 * @date 2018/11/12.
 */
public class TechProcessTWModel {
    private String orderDetailId;   //订单明细ID
    private String planNo;  //计划单号
    private String orderNo; //订单号
    private String posNo;   //erp单号
    private String customerName;    //
    private String ownerName;
    private String shopName;
    private String shopCode;
    private String styleSN;
    private String styleName;
    private String color;
    private String materialCode;
    private String productionSize;
    private String trySize;
    private String tryRecord;
    private String garmentDesignName;
    private Integer garmentStatus;
    private String remark;
    private String orderMark;
    private String quantity;
    private String pdfName;
    private String pdfUpdateTime;

    private String closedStatus;
    private Date closedTime;

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPosNo() {
        return posNo;
    }

    public void setPosNo(String posNo) {
        this.posNo = posNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getStyleSN() {
        return styleSN;
    }

    public void setStyleSN(String styleSN) {
        this.styleSN = styleSN;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getProductionSize() {
        return productionSize;
    }

    public void setProductionSize(String productionSize) {
        this.productionSize = productionSize;
    }

    public String getTrySize() {
        return trySize;
    }

    public void setTrySize(String trySize) {
        this.trySize = trySize;
    }

    public String getTryRecord() {
        return tryRecord;
    }

    public void setTryRecord(String tryRecord) {
        this.tryRecord = tryRecord;
    }

    public String getGarmentDesignName() {
        return garmentDesignName;
    }

    public void setGarmentDesignName(String garmentDesignName) {
        this.garmentDesignName = garmentDesignName;
    }

    public Integer getGarmentStatus() {
        return garmentStatus;
    }

    public void setGarmentStatus(Integer garmentStatus) {
        this.garmentStatus = garmentStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOrderMark() {
        return orderMark;
    }

    public void setOrderMark(String orderMark) {
        this.orderMark = orderMark;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPdfName() {
        return pdfName;
    }

    public void setPdfName(String pdfName) {
        this.pdfName = pdfName;
    }

    public String getPdfUpdateTime() {
        return pdfUpdateTime;
    }

    public void setPdfUpdateTime(String pdfUpdateTime) {
        this.pdfUpdateTime = pdfUpdateTime;
    }

    public String getClosedStatus() {
        return closedStatus;
    }

    public void setClosedStatus(String closedStatus) {
        this.closedStatus = closedStatus;
    }

    public Date getClosedTime() {
        return closedTime;
    }

    public void setClosedTime(Date closedTime) {
        this.closedTime = closedTime;
    }
}
