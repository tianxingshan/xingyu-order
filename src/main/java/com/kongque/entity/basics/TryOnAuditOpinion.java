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
@Table(name = "t_tryon_audit_opinion")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TryOnAuditOpinion implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 6449527137447396809L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "c_id")
	private String id;
	
	@Column(name = "c_tryon_feedback_id")
	private String tryOnFeedBackId;

	@Column(name = "c_audit_opinion")
	private String auditOpinion;
	
	@Column(name = "c_audit_people")
	private String auditPeople;
	
	
	@Column(name="c_create_time")
	private Date checkCreateTime;
	
	@Column(name="c_pass_feedback")
	private String passFeedBack ;

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


	
	
}
