package com.kongque.model;

import java.util.List;

/**
 * @author Administrator
 */
public class TenantModel {
    private String id;
    /**
     * 商户名称
     */
    private String tenantName;

    /**
     * 商户所属系统id
     */
    private String sysId;

    /**
     * 商户状态（是否删除）
     */
    private String tenantDel;
    //商户状态   0启用  1禁用
    private String tenantStatus;

    /**
     * 父节点ID
     */
    private String parentId;
    /**
     * 订单性质
     */
    private List<OrderCharacterModel> orderCharacterModels;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    public String getTenantDel() {
        return tenantDel;
    }

    public void setTenantDel(String tenantDel) {
        this.tenantDel = tenantDel;
    }

    public String getTenantStatus() {
        return tenantStatus;
    }

    public void setTenantStatus(String tenantStatus) {
        this.tenantStatus = tenantStatus;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<OrderCharacterModel> getOrderCharacterModels() {
        return orderCharacterModels;
    }

    public void setOrderCharacterModels(List<OrderCharacterModel> orderCharacterModels) {
        this.orderCharacterModels = orderCharacterModels;
    }
}
/**
 * @program: xingyu-order
 * @description: 商户信息
 * @author: zhuxl
 * @create: 2019-03-27 16:14
 **/
