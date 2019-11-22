package com.kongque.dto;

import java.util.Date;


public class TryOnAuditOpinionDto {
		
	private String id;
	
	private String tryOnFeedBackId;

	private String auditOpinion;
	
	private String auditPeople;
	
	//private String auditStatus;
	
	private Date checkCreateTime;
	
	private String passFeedBack ;
	
	private String type;//1.审核通过  2.审核驳回

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTryOnFeedBackId() {
		return tryOnFeedBackId;
	}

	public void setTryOnFeedBackId(String tryOnFeedBackId) {
		this.tryOnFeedBackId = tryOnFeedBackId;
	}

	public String getAuditOpinion() {
		return auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}

	public String getAuditPeople() {
		return auditPeople;
	}

	public void setAuditPeople(String auditPeople) {
		this.auditPeople = auditPeople;
	}
	
/*	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}*/

	public Date getCheckCreateTime() {
		return checkCreateTime;
	}

	public void setCheckCreateTime(Date checkCreateTime) {
		this.checkCreateTime = checkCreateTime;
	}

	public String getPassFeedBack() {
		return passFeedBack;
	}

	public void setPassFeedBack(String passFeedBack) {
		this.passFeedBack = passFeedBack;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	
}
