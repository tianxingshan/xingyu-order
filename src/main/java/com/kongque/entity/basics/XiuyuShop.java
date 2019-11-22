package com.kongque.entity.basics;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_xiuyu_shop")
@DynamicInsert(true)
@DynamicUpdate(true)
public class XiuyuShop implements Serializable{

	private static final long serialVersionUID = -8724545618818549425L;
	
	@Id
	@Column(name = "c_id")
	private String id;
	
	@Column(name = "c_city")
	private String city;

	@Column(name = "c_address")
	private String address;
	
	@Column(name = "c_contact")
	private String contact;
	
	@Column(name = "c_note")
	private String note;
	
	@Column(name = "c_create_time")
	private Date createTime;
	
	@Column(name = "c_operator_id")
	private String operatorId;
	
	@Column(name = "c_operator_name")
	private String operatorName;
	
	@Column(name = "c_latest_address")
	private String latestSddress;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getLatestSddress() {
		return latestSddress;
	}

	public void setLatestSddress(String latestSddress) {
		this.latestSddress = latestSddress;
	}

}
