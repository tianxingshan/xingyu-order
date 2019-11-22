package com.kongque.entity.user;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "t_menu")
public class Menu implements Serializable{

	private static final long serialVersionUID = -7624504235325644227L;
	
	/**
	 * 主键
	 */
	@Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "c_id")
	private String id;
	/**
	 * 系统id
	 */
	@Column(name = "c_sys_id")
	private String sysId;
	/**
	 * 节点名称
	 */
	@Column(name = "c_name")
	private String name;
	/**
	 * 网页链接地址
	 */
	@Column(name = "c_file")
	private String file;
	/**
	 * 展示顺序
	 */
	@Column(name = "c_order")
	private Integer order;
	
	/**
	 * 父节点id
	 */
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "c_parent_id",insertable=false, updatable=false)
	private Menu parent;
	/**
	 * 子节点列表
	 */
	@OneToMany(mappedBy = "parent")
	@OrderBy("order asc")
	private List<Menu> children;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public Menu getParent() {
		return parent;
	}
	public void setParent(Menu parent) {
		this.parent = parent;
	}
	public List<Menu> getChildren() {
		return children;
	}
	public void setChildren(List<Menu> children) {
		this.children = children;
	}
	public String getSysId() {
		return sysId;
	}
	public void setSysId(String sysId) {
		this.sysId = sysId;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
}
