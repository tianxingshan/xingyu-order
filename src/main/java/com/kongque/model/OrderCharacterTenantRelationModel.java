package com.kongque.model;

import java.util.Date;

/**
 * @author Administrator
 */
public class OrderCharacterTenantRelationModel {
    private String id;
    private String tenantId;//商户id
    private String tenantName;//商户名称
    private String orderCharacterId;//订单性质id
    private String orderCharacterName;// 订单性质
    private String Status;// 0:启用 1:禁用
    private Date createTime;//创建日期

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

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getOrderCharacterId() {
        return orderCharacterId;
    }

    public void setOrderCharacterId(String orderCharacterId) {
        this.orderCharacterId = orderCharacterId;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
/**
 * @program: xingyu-order
 * @description: 订单性质商户对照信息
 * @author: zhuxl
 * @create: 2019-03-27 16:17
 **/
