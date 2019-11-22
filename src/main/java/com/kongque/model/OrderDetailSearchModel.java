package com.kongque.model;

public class OrderDetailSearchModel{

	/**
	 * 订单id
	 */
	private String orderId;
	/**
	 * 订单编号
	 */
	private String orderCode;
	/**
	 * erp 订单号
	 */
	private String erpNum;
	
	/**
	 * 商品唯一识别码
	 */
	private String goodsSN;
	
	/**
	 * 城市
	 * @since 2017-08-23
	 */
	private String city;
	
     /**
      *  所属店名
      */
    private String shopName;
	
     /**
     * 订单性质：新员首购、老员续购、标码升级、产品更换、样品、员购、xxxx
     */
    private String characteres; 
	
    /**
     * 顾客姓名
     */
    private String customerName;
    
      /**
	 * 订单归属人（绣字名）
	 * @since 2017-11-07
	 */
//	private String owner;
	
	/**
	 * 款式名（商品名称）
	 */
	private String goodsName;
	
	/**
	 * 款式颜色
	 * @since 2017-08-23
	 */
	private String goodsColor;
	
    /**
	 * 数量
	 */
	private String num;
	
	/**
	 * 订单状态
	 * @since 2017-08-23
	 */
	private String billStatus;
	/**
	 * 订单明细状态
	 */
	private String orderDetailStatus;
	
	 /**
     *建立日期
     */
	private String createTime;
	
	/**
	 * 星域审核时间
	 * @since 2017-08-29
	 */
//	private String xingyuCheckTime;

	/**
	 * 发货时间
	 */
	private String sendTime;
	
	 /**
     *  物料编码
     */
	private String materielCode;
	/**
	 * 结案状态
	 * @return
	 */
	private String closedStatus;
	/**
	 * 尺寸
	 */
	private String mesCode;
	
	/**
	 * 商品编码
	 */
	private String goodsCode;
	
	/**
	 * 订单详情id
	 */
	private String orderDetailId;
	
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getErpNum() {
		return erpNum;
	}
	public void setErpNum(String erpNum) {
		this.erpNum = erpNum;
	}
	public String getGoodsSN() {
		return goodsSN;
	}
	public void setGoodsSN(String goodsSN) {
		this.goodsSN = goodsSN;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getCharacteres() {
		return characteres;
	}
	public void setCharacteres(String characteres) {
		this.characteres = characteres;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsColor() {
		return goodsColor;
	}
	public void setGoodsColor(String goodsColor) {
		this.goodsColor = goodsColor;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getBillStatus() {
		return billStatus;
	}
	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}
	public String getOrderDetailStatus() {
		return orderDetailStatus;
	}
	public void setOrderDetailStatus(String orderDetailStatus) {
		this.orderDetailStatus = orderDetailStatus;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getMaterielCode() {
		return materielCode;
	}
	public void setMaterielCode(String materielCode) {
		this.materielCode = materielCode;
	}
	public String getClosedStatus() {
		return closedStatus;
	}
	public void setClosedStatus(String closedStatus) {
		this.closedStatus = closedStatus;
	}
	public String getMesCode() {
		return mesCode;
	}
	public void setMesCode(String mesCode) {
		this.mesCode = mesCode;
	}
	public String getGoodsCode() {
		return goodsCode;
	}
	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	public String getOrderDetailId() {
		return orderDetailId;
	}
	public void setOrderDetailId(String orderDetailId) {
		this.orderDetailId = orderDetailId;
	}
	
}
