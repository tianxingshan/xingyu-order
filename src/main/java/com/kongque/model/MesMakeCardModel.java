package com.kongque.model;

import java.util.Date;

/**
 * @program: xingyu-order
 * @description: 制卡
 * @author: zhuxl
 * @create: 2019-07-26 10:38
 **/
public class MesMakeCardModel {
    /**
     * id
     */
    private String id ;
    /**
     * 订单明细id
     */
    private String orderDetailId;
    /**
     * 卡号
     */
    private String cardCode;

    /**
     * 商品唯一码
     */
    private String goodSN;
    /**
     * 订单计划单号
     */
    private String orderPlanCode;

    /**
     * 计划下达日期
     */
    private String planSendTime;
    /**
     * 订单计划状态
     */
    private String orderPlanStatus;

    /**
     * 单号
     */
    private String orderCode;

    /**
     * 订单明细状态
     */
    private String orderDetailStatus;
    /**
     * 顾客姓名
     */
    private String customerName;

    /**
     * 绣字名
     */
    private String embroidName;
    /**
     * 店铺名称
     */
    private String shopName;
    /**
     * 款式
     */
    private String goodId;
    private String goodCode;
    private String goodName;
    /**
     * 颜色名称
     */
    private String goodColorName;
    /**
     * 尺码
     */
    private String goodSize;

    /**
     * 版型师
     */
    private String technicianName;
    /**
     * 卡状态
     */
    private String cardStatus;
    /**
     * 操作日期
     */
    private String createTime;

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

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getGoodSN() {
        return goodSN;
    }

    public void setGoodSN(String goodSN) {
        this.goodSN = goodSN;
    }

    public String getOrderPlanCode() {
        return orderPlanCode;
    }

    public void setOrderPlanCode(String orderPlanCode) {
        this.orderPlanCode = orderPlanCode;
    }

    public String getPlanSendTime() {
        return planSendTime;
    }

    public void setPlanSendTime(String planSendTime) {
        this.planSendTime = planSendTime;
    }

    public String getOrderPlanStatus() {
        return orderPlanStatus;
    }

    public void setOrderPlanStatus(String orderPlanStatus) {
        this.orderPlanStatus = orderPlanStatus;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderDetailStatus() {
        return orderDetailStatus;
    }

    public void setOrderDetailStatus(String orderDetailStatus) {
        this.orderDetailStatus = orderDetailStatus;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmbroidName() {
        return embroidName;
    }

    public void setEmbroidName(String embroidName) {
        this.embroidName = embroidName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public String getGoodCode() {
        return goodCode;
    }

    public void setGoodCode(String goodCode) {
        this.goodCode = goodCode;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getGoodColorName() {
        return goodColorName;
    }

    public void setGoodColorName(String goodColorName) {
        this.goodColorName = goodColorName;
    }

    public String getGoodSize() {
        return goodSize;
    }

    public void setGoodSize(String goodSize) {
        this.goodSize = goodSize;
    }

    public String getTechnicianName() {
        return technicianName;
    }

    public void setTechnicianName(String technicianName) {
        this.technicianName = technicianName;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}

