package com.kongque.entity.process;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.*;

@Entity
@Table(name = "mes_process")
@DynamicInsert(true)
@DynamicUpdate(true)
public class MesProcess implements Serializable {

	private static final long serialVersionUID = 2729462007611042110L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "c_id")
	private String id;
	
	@Column(name = "c_process_position_id")
	private String processPositionId;
	
	@Column(name = "c_code")
	private String code;

	@Column(name = "c_name")
	private String name;
	
	@Column(name = "c_level_id")
	private String levelId;
	
	@Column(name = "c_thread_id")
	private String threadId;
	
	@Column(name = "c_needle_range_id")
	private String needleRangeId;
	
	@Column(name = "c_machine_id")
	private String machineId;
	
	@Column(name = "c_quality")
	private String quality;
	
	@Column(name = "c_remark")
	private String remark;
	
	@Column(name = "c_status")
	private String status;

	@Column(name = "c_standard_hour")
	private String standardHour;

	@NotFound(action= NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name="c_level_id",insertable=false,updatable=false)
	private MesProcessLevel level;

	@NotFound(action= NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name="c_thread_id",insertable=false,updatable=false)
	private MesProcessThread thread;

	@NotFound(action= NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name="c_needle_range_id",insertable=false,updatable=false)
	private MesProcessNeedleRange needleRange;

	@NotFound(action= NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name="c_machine_id",insertable=false,updatable=false)
	private MesProcessMachine machine;
	

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

	public String getStandardHour() {
		return standardHour;
	}

	public void setStandardHour(String standardHour) {
		this.standardHour = standardHour;
	}

	public String getProcessPositionId() {
		return processPositionId;
	}

	public void setProcessPositionId(String processPositionId) {
		this.processPositionId = processPositionId;
	}

	public String getLevelId() {
		return levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}

	public String getThreadId() {
		return threadId;
	}

	public void setThreadId(String threadId) {
		this.threadId = threadId;
	}

	public String getNeedleRangeId() {
		return needleRangeId;
	}

	public void setNeedleRangeId(String needleRangeId) {
		this.needleRangeId = needleRangeId;
	}

	public String getMachineId() {
		return machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public MesProcessLevel getLevel() {
		return level;
	}

	public void setLevel(MesProcessLevel level) {
		this.level = level;
	}

	public MesProcessThread getThread() {
		return thread;
	}

	public void setThread(MesProcessThread thread) {
		this.thread = thread;
	}

	public MesProcessNeedleRange getNeedleRange() {
		return needleRange;
	}

	public void setNeedleRange(MesProcessNeedleRange needleRange) {
		this.needleRange = needleRange;
	}

	public MesProcessMachine getMachine() {
		return machine;
	}

	public void setMachine(MesProcessMachine machine) {
		this.machine = machine;
	}
	
}
