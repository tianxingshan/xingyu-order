package com.kongque.entity.process;

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
@Table(name = "mes_goods_sew_process")
@DynamicInsert(true)
@DynamicUpdate(true)
public class GoodsSewProcess implements Serializable {

	private static final long serialVersionUID = 2729462007611042110L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "c_id")
	private String id;
	
	@Column(name = "c_optional_technology_id")
	private String optionalTechnologyId;

	@Column(name = "c_process_position_id")
	private String processPositionId;
	
	@Column(name = "c_process_id")
	private String processId;
	
	@Column(name = "c_sort")
	private String sort;
	
	@Column(name = "c_remark")
	private String remark;
	
	@ManyToOne
	@JoinColumn(name = "c_process_position_id",insertable =false, updatable =false)
	private ProcessPosition processPosition;
	
	@ManyToOne
	@JoinColumn(name = "c_process_id",insertable =false, updatable =false)
	private MesProcess process;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOptionalTechnologyId() {
		return optionalTechnologyId;
	}

	public void setOptionalTechnologyId(String optionalTechnologyId) {
		this.optionalTechnologyId = optionalTechnologyId;
	}

	public String getProcessPositionId() {
		return processPositionId;
	}

	public void setProcessPositionId(String processPositionId) {
		this.processPositionId = processPositionId;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public ProcessPosition getProcessPosition() {
		return processPosition;
	}

	public void setProcessPosition(ProcessPosition processPosition) {
		this.processPosition = processPosition;
	}

	public MesProcess getProcess() {
		return process;
	}

	public void setProcess(MesProcess process) {
		this.process = process;
	}
	
}
