package com.kongque.entity.basics;


import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Persister;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "v_dept")
public class VDept implements Serializable {


	@Id
	@Column(name = "id")
	private String id;

	/**
	 * 商户id
	 */
	@Column(name = "tenant_id")
	private String tenantId;

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

	//@OneToMany(fetch=FetchType.LAZY)
	//@JoinColumn(name = "parent_id",insertable = false,updatable = false)
	@Transient
	private List<VDept> children;

	/**
	 * 授权标志
	 */
	@Transient
	private int flag;



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

	public List<VDept> getChildren() {
		return children;
	}

	public void setChildren(List<VDept> children) {
		this.children = children;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

}
