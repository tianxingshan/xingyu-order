package com.kongque.entity.basics;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_tryon_feedback")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TryOnFeedBack implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -42259501083948504L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "c_id")
	private String id;
	
	@Column(name = "c_order_detail_id")
	private String orderDetailId;

	@Column(name = "c_tryon_number")
	private String tryOnNumber;
	
	@Column(name = "c_tryon_date")
	private Date tryOnDate;
	
	
	@Column(name = "c_feedback_json")
	private String feedBackJson;
	
	
	@Column(name="c_size")
	private String size;
	
	
	@Column(name = "c_instruction")
	private String instruction;
	
	
	@Column(name="c_file_keys")
	private String fileKeys;
	
	@Column(name = "c_create_user")
	private String createUser;
	
	
	@Column(name="c_create_time")
	private Date createTime;
	
	@Column(name = "c_update_user")
	private String updateUser;
	
	
	@Column(name="c_update_time")
	private Date updateTime;
	
	@Column(name="c_goods_detail_id")
	private String goodsDetailId;
	
	@Column(name="c_status")
	private String status;
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getOrderDetailId() {
		return orderDetailId;
	}


	public void setOrderDetailId(String orderDetailId) {
		this.orderDetailId = orderDetailId;
	}


	public String getTryOnNumber() {
		return tryOnNumber;
	}


	public void setTryOnNumber(String tryOnNumber) {
		this.tryOnNumber = tryOnNumber;
	}


	public Date getTryOnDate() {
		return tryOnDate;
	}


	public void setTryOnDate(Date tryOnDate) {
		this.tryOnDate = tryOnDate;
	}


	public String getFeedBackJson() {
		return feedBackJson;
	}


	public void setFeedBackJson(String feedBackJson) {
		this.feedBackJson = feedBackJson;
	}


	public String getSize() {
		return size;
	}


	public void setSize(String size) {
		this.size = size;
	}


	public String getInstruction() {
		return instruction;
	}


	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}


	public String getFileKeys() {
		return fileKeys;
	}


	public void setFileKeys(String fileKeys) {
		this.fileKeys = fileKeys;
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


	public String getGoodsDetailId() {
		return goodsDetailId;
	}


	public void setGoodsDetailId(String goodsDetailId) {
		this.goodsDetailId = goodsDetailId;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
