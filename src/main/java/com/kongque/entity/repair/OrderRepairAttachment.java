package com.kongque.entity.repair;

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
@Table(name = "t_order_repair_attachment")
@DynamicInsert(true)
@DynamicUpdate(true)
public class OrderRepairAttachment implements Serializable {

	private static final long serialVersionUID = 8766330544788520178L;
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "c_id")
	private String id;
	@Column(name = "c_order_repair_id")
	private String orderRepairId;
	@Column(name = "c_oss_key")
	private String ossKey;
	@Column(name = "c_file_name")
	private String fileName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderRepairId() {
		return orderRepairId;
	}

	public void setOrderRepairId(String orderRepairId) {
		this.orderRepairId = orderRepairId;
	}

	public String getOssKey() {
		return ossKey;
	}

	public void setOssKey(String ossKey) {
		this.ossKey = ossKey;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}