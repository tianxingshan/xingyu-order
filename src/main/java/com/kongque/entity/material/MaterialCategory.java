package com.kongque.entity.material;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "mes_material_category")
@DynamicInsert(true)
@DynamicUpdate(true)
public class MaterialCategory implements Serializable {

	private static final long serialVersionUID = 2729462007611042110L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "c_id")
	private String id;
	
	@Column(name = "c_code")
	private String code;

	@Column(name = "c_name")
	private String name;
	
	@Column(name = "c_parent_id")
	private String parentId;
	
	@Column(name = "c_remark")
	private String remark;
	
	@Column(name = "c_status")
	private String status;
	
	@Column(name = "c_del")
	private String del;
	
	@ManyToOne
	@JoinColumn(name = "c_parent_id",insertable =false, updatable =false)
	private MaterialCategory parentCategory;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public MaterialCategory getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(MaterialCategory parentCategory) {
		this.parentCategory = parentCategory;
	}

	public String getDel() {
		return del;
	}

	public void setDel(String del) {
		this.del = del;
	}
	
}
