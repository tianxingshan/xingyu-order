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
@Table(name = "mes_material_position")
@DynamicInsert(true)
@DynamicUpdate(true)
public class MaterialPosition implements Serializable {

	private static final long serialVersionUID = 2729462007611042110L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "c_id")
	private String id;
	
	@Column(name = "c_category_id")
	private String categoryId;
	
	@Column(name = "c_code")
	private String code;

	@Column(name = "c_name")
	private String name;
	
	@Column(name = "c_meterial_category_id")
	private String meterialCategoryId;
	
	@Column(name = "c_remark")
	private String remark;
	
	@Column(name = "c_status")
	private String status;
	
	@Column(name = "c_del")
	private String del;
	
	@ManyToOne
	@JoinColumn(name = "c_meterial_category_id",insertable =false, updatable =false)
	private MaterialCategory category;

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

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getMeterialCategoryId() {
		return meterialCategoryId;
	}

	public void setMeterialCategoryId(String meterialCategoryId) {
		this.meterialCategoryId = meterialCategoryId;
	}

	public MaterialCategory getCategory() {
		return category;
	}

	public void setCategory(MaterialCategory category) {
		this.category = category;
	}

	public String getDel() {
		return del;
	}

	public void setDel(String del) {
		this.del = del;
	}
	
}
