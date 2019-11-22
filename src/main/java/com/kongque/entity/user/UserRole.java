package com.kongque.entity.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.kongque.entity.basics.Tenant;

/**
 * 用户角色
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_role")
public class UserRole implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4895128293380786441L;
	/**
	 * 主键
	 */
	@Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "c_id")
	private String id;
	/**
	 * 角色名称
	 */
	@Column(name = "c_role_name")
	private String roleName;
	/**
	 * 商户id
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="c_tenant_id")
	private Tenant tenant;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Tenant getTenant() {
		return tenant;
	}
	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}
}
