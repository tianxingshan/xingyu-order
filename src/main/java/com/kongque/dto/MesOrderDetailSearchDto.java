package com.kongque.dto;

import java.util.Date;

/**
 * @program: xingyu-order
 * @description: 生产订单明细
 * @author: zhuxl
 * @create: 2018-10-12 10:24
 **/
public class MesOrderDetailSearchDto {

    private String id;

    /**
     * 查询类型 空为全部、1 未分配、2 分配
     */
    private String searchType;

    /**
     * 版型师ID
     */
    private String technicianId;

    /**
     * 分配开始日期
     */
    private String assignTimeBegin;
    /**
     * 分配截止日期
     */
    private String assignTimeEnd;
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
     *  所属店铺id
     */
    private String shopId;

    private String shopName;

    /**
     * 订单性质：新员首购、老员续购、标码升级、产品更换、样品、员购、xxxx
     */
    private String characteres;

    /**
     * 顾客Id
     */
    private String customerId;

    /**
     * 顾客姓名
     */
    private String customerName;

    /**
     * 商品明细Id
     */
    private String goodsDetailId;

    /**
     * 商品编码
     */
    private String goodsCode;

    /**
     * 款式名（商品名称）
     */
    private String goodsName;


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
     *订单建立开始日期
     */
    private String orderCreateTimeBegin;

    /**
     *订单建立截止日期
     */
    private String orderCreateTimeEnd;

    //订单审核起始日期
    private String orderAuditTimeBegin;

   //订单截止日期
    private String orderAuditTimeEnd;

    /**
     * 尺寸
     */
    private String mesCode;

    /**
     * 分类
     */
    private String categoryId;


    //订单技术完成起始日期
    private String orderTechnicalFinishedTimeBegin;

    //订单技术完成截止日期
    private String orderTechnicalFinishedTimeEnd;
    
    private String customerQ;//会员名称及code

    private String embroidName; //绣字名

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(String technicianId) {
        this.technicianId = technicianId;
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

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
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



    public String getMesCode() {
        return mesCode;
    }

    public void setMesCode(String mesCode) {
        this.mesCode = mesCode;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getGoodsDetailId() {
        return goodsDetailId;
    }

    public void setGoodsDetailId(String goodsDetailId) {
        this.goodsDetailId = goodsDetailId;
    }

    public String getAssignTimeBegin() {
        return assignTimeBegin;
    }

    public void setAssignTimeBegin(String assignTimeBegin) {
        this.assignTimeBegin = assignTimeBegin;
    }

    public String getAssignTimeEnd() {
        return assignTimeEnd;
    }

    public void setAssignTimeEnd(String assignTimeEnd) {
        this.assignTimeEnd = assignTimeEnd;
    }

    public String getOrderCreateTimeBegin() {
        return orderCreateTimeBegin;
    }

    public void setOrderCreateTimeBegin(String orderCreateTimeBegin) {
        this.orderCreateTimeBegin = orderCreateTimeBegin;
    }

    public String getOrderCreateTimeEnd() {
        return orderCreateTimeEnd;
    }

    public void setOrderCreateTimeEnd(String orderCreateTimeEnd) {
        this.orderCreateTimeEnd = orderCreateTimeEnd;
    }

    public String getOrderAuditTimeBegin() {
        return orderAuditTimeBegin;
    }

    public void setOrderAuditTimeBegin(String orderAuditTimeBegin) {
        this.orderAuditTimeBegin = orderAuditTimeBegin;
    }

    public String getOrderAuditTimeEnd() {
        return orderAuditTimeEnd;
    }

    public void setOrderAuditTimeEnd(String orderAuditTimeEnd) {
        this.orderAuditTimeEnd = orderAuditTimeEnd;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getOrderTechnicalFinishedTimeBegin() {
        return orderTechnicalFinishedTimeBegin;
    }

    public void setOrderTechnicalFinishedTimeBegin(String orderTechnicalFinishedTimeBegin) {
        this.orderTechnicalFinishedTimeBegin = orderTechnicalFinishedTimeBegin;
    }

    public String getOrderTechnicalFinishedTimeEnd() {
        return orderTechnicalFinishedTimeEnd;
    }

    public void setOrderTechnicalFinishedTimeEnd(String orderTechnicalFinishedTimeEnd) {
        this.orderTechnicalFinishedTimeEnd = orderTechnicalFinishedTimeEnd;
    }

	public String getCustomerQ() {
		return customerQ;
	}

	public void setCustomerQ(String customerQ) {
		this.customerQ = customerQ;
	}

    public String getEmbroidName() {
        return embroidName;
    }

    public void setEmbroidName(String embroidName) {
        this.embroidName = embroidName;
    }
}

