package com.kongque.dto;

/**
 * @author xiaxt
 * @date 2018/10/16.
 */
public class OrderPlanDetailDto {

    private String id;  //明细ID
    private String orderPlan;   //计划单
    private String order;   //订单
    private String orderDetail;   //订单明细
    private Integer orderCount;   //订单数量
    private String operationStatus; //操作状态（用于更新订单状态）
    private String userId;//当前登陆用户ID

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderPlan() {
        return orderPlan;
    }

    public void setOrderPlan(String orderPlan) {
        this.orderPlan = orderPlan;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(String orderDetail) {
        this.orderDetail = orderDetail;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public String getOperationStatus() {
        return operationStatus;
    }

    public void setOperationStatus(String operationStatus) {
        this.operationStatus = operationStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
