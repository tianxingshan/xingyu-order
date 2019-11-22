package com.kongque.entity.order;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.kongque.entity.goods.GoodsDetail;

@Entity
@Table(name = "t_order_detail")
@DynamicInsert(true)
@DynamicUpdate(true)
public class OrderDetail implements Serializable{

	private static final long serialVersionUID = 4710713406073725797L;
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "c_id")
	private String id;
	
	
	@Column(name = "c_order_id")
	private String orderId;
	
	@Column(name = "c_goods_detail_id")
	private String goodsDetailId;
	
	@Column(name = "c_goods_sn")
	private String goodsSn;
	
	@Column(name = "c_goods_code")
	private String goodsCode;
	
	@Column(name = "c_goods_name")
	private String goodsName;
	
	@Column(name = "c_goods_color_name")
	private String goodsColorName;

	@Column(name = "c_lining_name")
	private String liningName;
	
	@Column(name = "c_unit")
	private String unit;
	
	@Column(name = "c_num")
	private int num;
	
	@Column(name = "c_goods_detail_image_keys")
	private String goodsDetailImageKeys;
	
	@Column(name = "c_erp_no")
	private String erpNo;
	
	@Column(name = "c_order_detail_status")
	private String orderDetailStatus;
	
	@Column(name = "c_closed_status")
	private String closedStatus;
	
	@Column(name = "c_remark")
	private String remark;
	
	@ManyToOne
	@JoinColumn(name = "c_goods_detail_id",insertable =false, updatable =false)
	private GoodsDetail goodsDetail;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getGoodsDetailId() {
		return goodsDetailId;
	}

	public void setGoodsDetailId(String goodsDetailId) {
		this.goodsDetailId = goodsDetailId;
	}

	public String getGoodsSn() {
		return goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsColorName() {
		return goodsColorName;
	}

	public void setGoodsColorName(String goodsColorName) {
		this.goodsColorName = goodsColorName;
	}

	public String getLiningName() {
		return liningName;
	}

	public void setLiningName(String liningName) {
		this.liningName = liningName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getGoodsDetailImageKeys() {
		return goodsDetailImageKeys;
	}

	public void setGoodsDetailImageKeys(String goodsDetailImageKeys) {
		this.goodsDetailImageKeys = goodsDetailImageKeys;
	}

	public String getErpNo() {
		return erpNo;
	}

	public void setErpNo(String erpNo) {
		this.erpNo = erpNo;
	}

	public String getOrderDetailStatus() {
		return orderDetailStatus;
	}

	public void setOrderDetailStatus(String orderDetailStatus) {
		this.orderDetailStatus = orderDetailStatus;
	}

	public String getClosedStatus() {
		return closedStatus;
	}

	public void setClosedStatus(String closedStatus) {
		this.closedStatus = closedStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public GoodsDetail getGoodsDetail() {
		return goodsDetail;
	}

	public void setGoodsDetail(GoodsDetail goodsDetail) {
		this.goodsDetail = goodsDetail;
	}


}
