package com.kongque.entity.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_role_menu_relation")
public class RoleMenuRelation implements Serializable{

	private static final long serialVersionUID = 8705817216893089985L;
	
	/**
	 * 主键
	 */
	@Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "c_id")
	private String id;
	/**
	 * 角色
	 */
	@Column(name = "c_role_id")
	private String roleId;
	
	/**
	 * 权限
	 */
	@ManyToOne
	@JoinColumn(name = "c_menu_id")
	private Menu menu;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	
}
