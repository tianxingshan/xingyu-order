package com.kongque.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: xingyu-order
 * @description: 部门
 * @author: zhuxl
 * @create: 2019-03-21 09:26
 **/
public class DeptModel {
    private String id;
    private String tenantId;
    private String companyId;
    private String code;
    private String name;
    private List<DeptModel> children = new ArrayList<>();


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

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DeptModel> getChildren() {
        return children;
    }

    public void setChildren(List<DeptModel> children) {
        this.children = children;
    }
}

