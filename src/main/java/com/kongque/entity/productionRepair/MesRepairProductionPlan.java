package com.kongque.entity.productionRepair;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "mes_repair_production_plan")
@DynamicInsert(true)
@DynamicUpdate(true)
public class MesRepairProductionPlan implements Serializable{

	private static final long serialVersionUID = 5553487573632650870L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "c_id")
	private String id;
	
	@Column(name = "c_plan_code")
	private String planCode;
	
	@Column(name = "c_plan_date")
	private Date planDate;
	
	@Column(name = "c_status")
	private String status;
	
	@Column(name = "c_finish_date")
	private Date finishDate;
	
	@Column(name = "c_num")
	private Integer num;
	
	@Column(name = "c_operator")
	private String operator;
	
	@Column(name = "c_create_time")
	private Date createTime;
	
	@Column(name = "c_confirmer")
	private String confirmer;
	
	@Column(name = "c_confirm_time")
	private Date confirmTime;
	
	@Column(name = "c_releases")
	private String releases;
	
	@Column(name = "c_release_time")
	private Date releaseTime;
	
	@Column(name = "c_del")
	private String del;
	
	@OneToMany(mappedBy = "repairPlanId")
	private List<MesRepairProductionPlanDetail> detailList;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public Date getPlanDate() {
		return planDate;
	}

	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getConfirmer() {
		return confirmer;
	}

	public void setConfirmer(String confirmer) {
		this.confirmer = confirmer;
	}

	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	public String getReleases() {
		return releases;
	}

	public void setReleases(String releases) {
		this.releases = releases;
	}

	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getDel() {
		return del;
	}

	public void setDel(String del) {
		this.del = del;
	}

	public List<MesRepairProductionPlanDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<MesRepairProductionPlanDetail> detailList) {
		this.detailList = detailList;
	}

}
