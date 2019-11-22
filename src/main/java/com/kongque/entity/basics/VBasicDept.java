package com.kongque.entity.basics;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Administrator
 */
@Entity
@Table(name = "v_dept")
public class VBasicDept implements Serializable {
    @Id
    @Column(name = "id")
    private String id;

    /**
     * 商户
     */
    @Column(name = "tenant_id")
    private String tenantId;
    @Column(name = "tenant_name")
    private String tenantName;

    /**
     * 公司id
     */
    @Column(name = "company_id")
    private String companyId;

    /**
     * 店铺代码
     */
    @Column(name = "code")
    private String code;

    /**
     * 名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 类型 0  商户 1 分公司  2 店铺
     */
    @Column(name = "type")
    private String type;

    @Column(name = "parent_id")
    private String parentId;


    ///////

    @Column(name = "code",insertable = false,updatable = false)
    private String deptCode;
    @Column(name = "name",insertable = false,updatable = false)
    private String deptName;
    @Column(name = "type",insertable = false,updatable = false)
    private String deptType;
    @Column(name = "parent_id",insertable = false,updatable = false)
    private String deptParentId;
    @Column(name = "c_city")
    private String city;
    @Column(name = "c_address")
    private String address;
    @Column(name = "c_latest_address")
    private String latestSddress;
    @Column(name = "c_contact")
    private String contact;
    @Column(name = "c_dept_phone")
    private String deptPhone;


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

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptType() {
        return deptType;
    }

    public void setDeptType(String deptType) {
        this.deptType = deptType;
    }

    public String getDeptParentId() {
        return deptParentId;
    }

    public void setDeptParentId(String deptParentId) {
        this.deptParentId = deptParentId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatestSddress() {
        return latestSddress;
    }

    public void setLatestSddress(String latestSddress) {
        this.latestSddress = latestSddress;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDeptPhone() {
        return deptPhone;
    }

    public void setDeptPhone(String deptPhone) {
        this.deptPhone = deptPhone;
    }
}
/**
 * @program: xingyu-order
 * @description: 基本部门试图
 * @author: zhuxl
 * @create: 2019-04-08 14:12
 **/
