package com.kongque.entity.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.kongque.entity.basics.VBasicDept;
import com.kongque.entity.basics.VDept;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.kongque.entity.basics.Dept;

@Entity
@Table(name = "t_user_dept_relation")
@DynamicInsert(true)
@DynamicUpdate(true)
public class UserDeptRelation implements Serializable{
	
	private static final long serialVersionUID = 1968045090323725327L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "c_id")
	private String id;
	
	@Column(name = "c_user_id")
	private String userId;
	
	@Column(name = "c_dept_id")
	private String deptId;
	
	@OneToOne
	@JoinColumn(name = "c_dept_id", insertable = false, updatable = false)
	private VBasicDept dept;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public VBasicDept getDept() {
		return dept;
	}

	public void setDept(VBasicDept dept) {
		this.dept = dept;
	}
}
