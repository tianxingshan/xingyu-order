package com.kongque.entity.basics;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_xiuyu_area")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Area implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4698128545014899439L;
	/**
	 * 
	 */
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "c_id")
	private String id;
	@Column(name = "c_area_name")
	private String areaName;// 地区名称
	@Column(name = "c_status")
	private String status;// 0:未启用 1:正常 2:停用 3:删除

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
