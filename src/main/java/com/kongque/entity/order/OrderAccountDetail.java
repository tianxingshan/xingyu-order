package com.kongque.entity.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author sws
 *
 */
@Entity
@Table(name = "t_order_account_detail")
@DynamicInsert(true)
@DynamicUpdate(true)
public class OrderAccountDetail implements Serializable {

	private static final long serialVersionUID = -7544530263981623648L;
	
	public OrderAccountDetail() {
		super();
	}
	
	public OrderAccountDetail(String id) {
		this.orderAccountId = id;
	}
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "c_id")
	private String id;
	
	@Column(name = "c_order_account_id")
	private String orderAccountId;
	
	@Column(name = "c_order_code")
	private String orderCode;
	
	@Column(name = "c_goods_code")
	private String goodsCode;
	
	@Column(name = "c_goods_name")
	private String goodsName;
	
	@Column(name = "c_goods_color_name")
	private String goodsColorName;
	
	@Column(name = "c_mes_measure_size_id")
	private String mesMeasureSizeId;
	
	@Column(name = "c_order_time")
	private Date orderTime; 
	
	@Column(name = "c_order_character")
	private String orderCharacter;
	
	@Column(name = "c_goods_sn")
	private String goodsSn;

	@Column(name = "c_num")
	private Integer num; 
	
	@Column(name = "c_account_cost_price")
	private BigDecimal accountCostPrice;
	
	@Column(name = "c_order_detail_id")
	private String orderDetailId;

	public String getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(String orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderAccountId() {
		return orderAccountId;
	}

	public void setOrderAccountId(String orderAccountId) {
		this.orderAccountId = orderAccountId;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
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

	public String getMesMeasureSizeId() {
		return mesMeasureSizeId;
	}

	public void setMesMeasureSizeId(String mesMeasureSizeId) {
		this.mesMeasureSizeId = mesMeasureSizeId;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public String getOrderCharacter() {
		return orderCharacter;
	}

	public void setOrderCharacter(String orderCharacter) {
		this.orderCharacter = orderCharacter;
	}

	public String getGoodsSn() {
		return goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public BigDecimal getAccountCostPrice() {
		return accountCostPrice;
	}

	public void setAccountCostPrice(BigDecimal accountCostPrice) {
		this.accountCostPrice = accountCostPrice;
	}
	
}
