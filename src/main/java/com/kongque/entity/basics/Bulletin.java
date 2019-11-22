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

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "t_bulletin")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Bulletin implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -73233482613454603L;
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "c_id")
	private String id;
	/**
	 * 商户Id
	 */
	@Column(name = "c_tenant_id")
	private String tenantId;
	@Column(name = "c_title")
	private String title;// 公告标题
	@Column(name = "c_content")
	private String content;// 公告内容
	@Column(name = "c_status")
	private String status;// 公告状态:0：编辑,1：发布
	@Column(name = "c_releaser")
	private String releaser;// 发布人
	@Column(name = "c_release_time")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date releaseTime;// 发布时间
	@Column(name = "c_delete_flag")
	private String deleteFlag;// 删除标识1表示删除
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReleaser() {
		return releaser;
	}
	public void setReleaser(String releaser) {
		this.releaser = releaser;
	}
	public Date getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
}
