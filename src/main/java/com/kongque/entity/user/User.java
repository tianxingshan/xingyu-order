
package com.kongque.entity.user;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
@Table(name = "t_user")
@DynamicInsert(true)
@DynamicUpdate(true)
public class User implements Serializable {

    private static final long serialVersionUID = 112518804944201060L;

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "c_id")
    private String id;
    
    /**
     * 员工编号
     */
    @Column(name = "c_user_code")
    private String userCode;

    /**
     * 真实姓名
     */
    @Column(name = "c_actual_name")
    private String actualName;

    /**
     * 用户名
     */
    @Column(name = "c_user_name")
    private String userName;

    /**
     * 密码md5 32位
     */
    @Column(name = "c_password")
    private String password;
    
    /**
     * 手机号
     */
    @Column(name = "c_phone_no")
    private String phoneNo;

    /**
     * 邮箱
     */
    @Column(name = "c_email")
    private String email;

    /**
     * 用户状态
     */
    @Column(name = "c_status")
    private String status;
    
    /**
     * 创建者
     */
    @Column(name = "c_create_user")
    private String createUser;
    
    /**
     * 操作时间
     */
    @Column(name = "c_create_time")
    private Date createTime;
    
    /**
     * 更新者
     */
    @Column(name = "c_update_user")
    private String updateUser;
    
    /**
     * 更新时间
     */
    @Column(name = "c_update_time")
    private Date updateTime;

    /**
     * 备注
     */
    @Column(name = "c_remark")
    private String remark;
	/**
	 * 自动授权
	 */
	@Column(name = "c_default_authority_flag")
    private int defaultAuthorityFlag;
    
    @OneToMany(mappedBy = "userId",cascade = CascadeType.PERSIST)
	private List<UserRoleRelation> userRoleRelationList;
    
    @OneToMany(mappedBy = "userId",cascade = CascadeType.PERSIST)
	private List<UserSysRelation> userSysRelationList;
    
    @OneToMany(mappedBy = "userId",cascade = CascadeType.PERSIST)
	private List<UserDeptRelation> userDeptRelationList;
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getActualName() {
		return actualName;
	}

	public void setActualName(String actualName) {
		this.actualName = actualName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<UserRoleRelation> getUserRoleRelationList() {
		return userRoleRelationList;
	}

	public void setUserRoleRelationList(List<UserRoleRelation> userRoleRelationList) {
		this.userRoleRelationList = userRoleRelationList;
	}

	public List<UserSysRelation> getUserSysRelationList() {
		return userSysRelationList;
	}

	public void setUserSysRelationList(List<UserSysRelation> userSysRelationList) {
		this.userSysRelationList = userSysRelationList;
	}

	public List<UserDeptRelation> getUserDeptRelationList() {
		return userDeptRelationList;
	}

	public void setUserDeptRelationList(List<UserDeptRelation> userDeptRelationList) {
		this.userDeptRelationList = userDeptRelationList;
	}

	public int getDefaultAuthorityFlag() {
		return defaultAuthorityFlag;
	}

	public void setDefaultAuthorityFlag(int defaultAuthorityFlag) {
		this.defaultAuthorityFlag = defaultAuthorityFlag;
	}
}
