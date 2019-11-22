package com.kongque.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author xiaxt
 * @date 2018/10/16.
 */
public class OrderPlanDto {

    private String id;  //主键
    private String planNumber;  //计划单号
    private String planStatus;  //计划单状态：0：草稿，1：待审核，2：已审核，3：已驳回，4：已下达，9：生产完成
    private String[] planStatusArray;  //计划单状态数组
    private String prodFactory;   //生产工厂
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date prodTime;  //投产日期
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date deliveryTime;  //计划交期
    private String totalCount;  //计划单总数量
    private String user;  //当前登陆用户ID
    private String remark;  //备注
    private List<OrderPlanDetailDto> orderPlanDetails; //计划单下的明细列表

    /**
     * 以下字段为补充查询条件
     */
    private String orderNumber; //订单号
    private String goods;   //款式
    private String vip; //会员
    private String orderDetailStatus; //订单明细状态
    private String publishers;  //出版方
    private String singleNo;    //唯一码
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date prodTimeBegin;  //开始投产日期
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date prodTimeEnd;  //结束投产日期
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date deliveryTimeBegin;  //开始计划日期
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date deliveryTimeEnd;  //结束计划日期
    private String size;    //尺码
    private String stylist;    //版型师
    
    private String customerQ;//会员名称及code

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlanNumber() {
        return planNumber;
    }

    public void setPlanNumber(String planNumber) {
        this.planNumber = planNumber;
    }

    public String getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }

    public String[] getPlanStatusArray() {
        return planStatusArray;
    }

    public void setPlanStatusArray(String[] planStatusArray) {
        this.planStatusArray = planStatusArray;
    }

    public String getProdFactory() {
        return prodFactory;
    }

    public void setProdFactory(String prodFactory) {
        this.prodFactory = prodFactory;
    }

    public Date getProdTime() {
        return prodTime;
    }

    public void setProdTime(Date prodTime) {
        this.prodTime = prodTime;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<OrderPlanDetailDto> getOrderPlanDetails() {
        return orderPlanDetails;
    }

    public void setOrderPlanDetails(List<OrderPlanDetailDto> orderPlanDetails) {
        this.orderPlanDetails = orderPlanDetails;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getOrderDetailStatus() {
        return orderDetailStatus;
    }

    public void setOrderDetailStatus(String orderDetailStatus) {
        this.orderDetailStatus = orderDetailStatus;
    }

    public String getSingleNo() {
        return singleNo;
    }

    public void setSingleNo(String singleNo) {
        this.singleNo = singleNo;
    }

    public String getPublishers() {
        return publishers;
    }

    public void setPublishers(String publishers) {
        this.publishers = publishers;
    }

    public Date getProdTimeBegin() {
        return prodTimeBegin;
    }

    public void setProdTimeBegin(Date prodTimeBegin) {
        this.prodTimeBegin = prodTimeBegin;
    }

    public Date getProdTimeEnd() {
        return prodTimeEnd;
    }

    public void setProdTimeEnd(Date prodTimeEnd) {
        this.prodTimeEnd = prodTimeEnd;
    }

    public Date getDeliveryTimeBegin() {
        return deliveryTimeBegin;
    }

    public void setDeliveryTimeBegin(Date deliveryTimeBegin) {
        this.deliveryTimeBegin = deliveryTimeBegin;
    }

    public Date getDeliveryTimeEnd() {
        return deliveryTimeEnd;
    }

    public void setDeliveryTimeEnd(Date deliveryTimeEnd) {
        this.deliveryTimeEnd = deliveryTimeEnd;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getStylist() {
        return stylist;
    }

    public void setStylist(String stylist) {
        this.stylist = stylist;
    }

	public String getCustomerQ() {
		return customerQ;
	}

	public void setCustomerQ(String customerQ) {
		this.customerQ = customerQ;
	}
    
}
