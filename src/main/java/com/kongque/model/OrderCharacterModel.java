package com.kongque.model;

/**
 * @author Administrator
 */
public class OrderCharacterModel {
    private String id;
    private String tenantId;//商户id
    private String orderCharacterName;// 订单性质
    private String orderCharacterStatus;// 0:启用 1:禁用
    private String orderCharacterMemo;//备注

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getOrderCharacterName() {
        return orderCharacterName;
    }

    public void setOrderCharacterName(String orderCharacterName) {
        this.orderCharacterName = orderCharacterName;
    }

    public String getOrderCharacterStatus() {
        return orderCharacterStatus;
    }

    public void setOrderCharacterStatus(String orderCharacterStatus) {
        this.orderCharacterStatus = orderCharacterStatus;
    }

    public String getOrderCharacterMemo() {
        return orderCharacterMemo;
    }

    public void setOrderCharacterMemo(String orderCharacterMemo) {
        this.orderCharacterMemo = orderCharacterMemo;
    }
}
/**
 * @program: xingyu-order
 * @description: 订单信息
 * @author: zhuxl
 * @create: 2019-03-27 16:17
 **/
