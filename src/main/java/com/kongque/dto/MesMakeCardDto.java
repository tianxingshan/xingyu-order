package com.kongque.dto;

/**
 * @program: xingyu-order
 * @description: 制卡
 * @author: zhuxl
 * @create: 2019-07-26 10:38
 **/
public class MesMakeCardDto {
    /**
     * id
     */
    private String id ;
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
     * 订单计划状态
     */
    private String orderPlanStatus;

    /**
     * 单号
     */
    private String orderCode;
    /**
     * 顾客姓名
     */
    private String customerName;
    /**
     * 款式
     */
    private String goodId;
    private String goodCode;
    private String goodName;

    /**
     * 版型师
     */
    private String technicianName;
    /**
     * 卡状态
     */
    private String cardStatus;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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
}

