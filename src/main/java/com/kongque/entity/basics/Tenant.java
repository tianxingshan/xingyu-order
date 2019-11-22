
package com.kongque.entity.basics;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "t_tenant")
@Where(clause = "c_del=0")
public class Tenant implements Serializable {

   

    /**
	 * 
	 */
	private static final long serialVersionUID = 6826140755346610868L;

	private String id;

    /**
     * 商户名称
     */
    private String tenantName;
    
    /**
     * 商户所属系统id
     */
    private String sysId;

    /**
     * 商户状态（是否删除）
     */
    private String tenantDel;
    //商户状态   0启用  1禁用
    private String tenantStatus;

	/**
	 * 父节点ID
	 */
	private String parentId;

	private List<Tenant> children;


	private int flag;

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "c_id")
    public String getId() {

	return id;
    }

    public void setId(String id) {

	this.id = id;
    }
    @Column(name = "c_tenant_name")
	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	@Column(name = "c_del")
	public String getTenantDel() {
		return tenantDel;
	}

	public void setTenantDel(String tenantDel) {
		this.tenantDel = tenantDel;
	}
	@Column(name = "c_status")
	public String getTenantStatus() {
		return tenantStatus;
	}

	public void setTenantStatus(String tenantStatus) {
		this.tenantStatus = tenantStatus;
	}

	@Column(name = "c_sys_id")
	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	@Column(name = "c_parent_id")
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@OneToMany
	@JoinColumn(name = "c_parent_id",insertable = false,updatable = false)
	@Where(clause = "c_del=0")
	public List<Tenant> getChildren() {
		return children;
	}

	public void setChildren(List<Tenant> children) {
		this.children = children;
	}

	@Transient
	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
}
