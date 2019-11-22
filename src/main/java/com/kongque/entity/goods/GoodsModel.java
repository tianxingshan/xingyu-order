package com.kongque.entity.goods;

import com.kongque.entity.basics.Category;
import com.kongque.entity.basics.MeasureCategory;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_goods")
@DynamicInsert(true)
@DynamicUpdate(true)
public class GoodsModel implements Serializable {

	private static final long serialVersionUID = -1613764529983324223L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "c_id")
	private String id;
	
	@Column(name = "c_code")
	private String code;

	@Column(name = "c_name")
	private String name;//商品名称

	@Column(name = "c_detail")
	private String detail;
	
	@ManyToOne
	@JoinColumn(name = "c_category_id")
	private Category category;//商品品类
	
	@Column(name = "c_image_keys")
	private String imageKeys;//图片路径
	
	@Column(name = "c_status")
	private String status;//商品状态是否可用：0启用1禁用
	
	@Column(name = "c_for_order")
	private String forOrder;//是否专属款式：0：是 1：否
	
	@Column(name = "c_create_user")
	private String createUser;
	
	 @Column(name = "c_create_time")
	private Date createTime;
	 
	@Column(name = "c_update_user")
	private String updateUser;
	
	@Column(name = "c_update_time")
	private Date updateTime;
	
	@Column(name = "c_remark")
	private String remark;
	@ManyToOne
	@JoinColumn(name = "c_measure_category_id")
	private MeasureCategory measureCategory;

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

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getImageKeys() {
		return imageKeys;
	}

	public void setImageKeys(String imageKeys) {
		this.imageKeys = imageKeys;
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

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public MeasureCategory getMeasureCategory() {
		return measureCategory;
	}

	public void setMeasureCategory(MeasureCategory measureCategory) {
		this.measureCategory = measureCategory;
	}


	public String getForOrder() {
		return forOrder;
	}

	public void setForOrder(String forOrder) {
		this.forOrder = forOrder;
	}

}
