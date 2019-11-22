package com.kongque.entity.basics;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_dept")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Dept implements Serializable {

	private static final long serialVersionUID = 1431413025831146559L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "c_id")
	private String id;
	
	@Column(name = "c_dept_name")
	private String deptName;

	@Column(name = "c_dept_code")
	private String deptCode;
	
	@Column(name = "c_dept_type")
	private String deptType;
	
	@Column(name = "c_dept_phone")
	private String deptPhone;
	
	@Column(name = "c_parent_id")
	private String deptParentId;

	@ManyToOne
	@JoinColumn(name="c_tenant_id")
	private Tenant deptTenantId;

	@OneToMany(cascade = CascadeType.PERSIST,mappedBy="shopId")
	private List<XiuyuShopCompanyRelation> companyList;
	
	@Transient
	private List<Dept> deptList;
	
	@Transient
	private XiuyuShop shop;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
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

	public Tenant getDeptTenantId() {
		return deptTenantId;
	}

	public void setDeptTenantId(Tenant deptTenantId) {
		this.deptTenantId = deptTenantId;
	}

	public List<Dept> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<Dept> deptList) {
		this.deptList = deptList;
	}

	public String getDeptPhone() {
		return deptPhone;
	}

	public void setDeptPhone(String deptPhone) {
		this.deptPhone = deptPhone;
	}

	public List<XiuyuShopCompanyRelation> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<XiuyuShopCompanyRelation> companyList) {
		this.companyList = companyList;
	}

	public XiuyuShop getShop() {
		return shop;
	}

	public void setShop(XiuyuShop shop) {
		this.shop = shop;
	}
	
}
