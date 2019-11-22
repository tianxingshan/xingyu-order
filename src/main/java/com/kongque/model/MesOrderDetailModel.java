package com.kongque.model;

import java.util.Date;

/**
 * @program: xingyu-order
 * @description: 生产订单明细
 * @author: zhuxl
 * @create: 2018-10-12 10:00
 **/
public class MesOrderDetailModel {

    private String id;

    /**
     * 版型师ID
     */
    private String technicianId;
    /**
     * 版型师名字
     */
    private String technicianName;
    /**
     * 分配日期
     */
    private String assignCreateTime;

    //分配创建人员姓名
    private String assignCreateName;
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
     * 顾客代码
     */
    private String customerCode;

    /**
     * 款式id
     */
    private String goodsId;

    /**
     * 商品明细id
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
     * 款式颜色
     * @since 2017-08-23
     */
    private String goodsColor;

    //款式分类id
    private String categoryId;
    //款式分类名称
    private String getCategoryName;

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
     *订单建立日期
     */
    private String orderCreateTime;

    /**
     * 尺寸
     */
    private String mesSizeId;

    private String mesSizeName;


    /**
     * 订单详情id
     */
    private String orderDetailId;

    /**
     * 20181023添加订单ID
     * @return
     */
    private String orderId;

    /**
     * 20181022添加订单类型，该字段对应表中是否重置字段，0：普通订单，1：重置订单
     * @return
     */
    private String reset;

    private String technicalFinishedTime;
    /**
     * 绣字名
     */
    private String embroidName;

    /**
     * 分配备注
     */
    private String assignRemarks;
    /**
     * 星域审核日期
     */
    private String xingyuChekTime;
    /**
     * 结案状态
     */
    private  String closedStatus;


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

    public String getTechnicianName() {
        return technicianName;
    }

    public void setTechnicianName(String technicianName) {
        this.technicianName = technicianName;
    }

    public String getAssignCreateTime() {
        return assignCreateTime;
    }

    public void setAssignCreateTime(String assignCreateTime) {
        this.assignCreateTime = assignCreateTime;
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

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsDetailId() {
        return goodsDetailId;
    }

    public void setGoodsDetailId(String goodsDetailId) {
        this.goodsDetailId = goodsDetailId;
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

    public String getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(String orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public String getMesSizeId() {
        return mesSizeId;
    }

    public void setMesSizeId(String mesSizeId) {
        this.mesSizeId = mesSizeId;
    }

    public String getMesSizeName() {
        return mesSizeName;
    }

    public void setMesSizeName(String mesSizeName) {
        this.mesSizeName = mesSizeName;
    }

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getAssignCreateName() {
        return assignCreateName;
    }

    public void setAssignCreateName(String assignCreateName) {
        this.assignCreateName = assignCreateName;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getGetCategoryName() {
        return getCategoryName;
    }

    public void setGetCategoryName(String getCategoryName) {
        this.getCategoryName = getCategoryName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getReset() {
        return reset;
    }

    public void setReset(String reset) {
        this.reset = reset;
    }

    public String getTechnicalFinishedTime() {
        return technicalFinishedTime;
    }

    public void setTechnicalFinishedTime(String technicalFinishedTime) {
        this.technicalFinishedTime = technicalFinishedTime;
    }

    public String getEmbroidName() {
        return embroidName;
    }

    public void setEmbroidName(String embroidName) {
        this.embroidName = embroidName;
    }

    public String getAssignRemarks() {
        return assignRemarks;
    }

    public void setAssignRemarks(String assignRemarks) {
        this.assignRemarks = assignRemarks;
    }

    public String getXingyuChekTime() {
        return xingyuChekTime;
    }

    public void setXingyuChekTime(String xingyuChekTime) {
        this.xingyuChekTime = xingyuChekTime;
    }

    public String getClosedStatus() {
        return closedStatus;
    }

    public void setClosedStatus(String closedStatus) {
        this.closedStatus = closedStatus;
    }
}

