package com.kongque.dto;

import com.kongque.entity.order.OrderAttachment;

import java.util.List;

/**
 * @author Administrator
 */
public class YunOrderDto {

    /**
     * 商户名称
     */
    private String tenantName;
    /**
     * 云平台单号
     */
    private String orderCode;
    /**
     * 会员编码
     */
    private String customerCode;
    /**
     * 会员姓名
     */
    private String customerName;
    /**
     * 刺绣
     */
    private String embroidName;
    /**
     * 会员生日
     */
    private String customerBirthday;
    /**
     * 会员身高
     */
    private String customerHeight;
    /**
     * 会员体重
     */
    private String customerWeight;
    /**
     * 会员职业
     */
    private String customerProfessional;
    /**
     * 会员电话
     */
    private String customerPhone;
    /**
     * 城市
     */
    private String city;
    /**
     * 门店编码
     */
    private String shopCode;
    /**
     * 门店名称
     */
    private String shopName;
    /**
     * 量体人姓名
     */
    private String measurerName;
    /**
     * 量体人电话
     */
    private String measurerPhone;
    /**
     * 收货地址
     */
    private String receivingAddress;
    /**
     * 创建日期
     */
    private String createTime;
    /**
     * 创建人员
     */
    private String createUser;
    /**
     * 审核日期
     */
    private String auditTime;
    /**
     * 审核人员
     */
    private String auditUser;
    /**
     * 备注
     */
    private String remark;

    /**
     * 试衣说明
     */
    private String tryonOpinion;
    /**
     * 订单明细
     */
    private List<YunOrderDetailDto> orderDetail;
    /**
     * 量体信息
     */
    private String orderBodyMeasure;
    /**
     * 身体语言
     */
    private String orderBodyLanguage;
    /**
     * 订单工艺
     */
    private String orderProcess;

    /**
     * 量体图片
     */
    private List<OrderAttachment> orderAttachment;

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmbroidName() {
        return embroidName;
    }

    public void setEmbroidName(String embroidName) {
        this.embroidName = embroidName;
    }

    public String getCustomerBirthday() {
        return customerBirthday;
    }

    public void setCustomerBirthday(String customerBirthday) {
        this.customerBirthday = customerBirthday;
    }

    public String getCustomerHeight() {
        return customerHeight;
    }

    public void setCustomerHeight(String customerHeight) {
        this.customerHeight = customerHeight;
    }

    public String getCustomerWeight() {
        return customerWeight;
    }

    public void setCustomerWeight(String customerWeight) {
        this.customerWeight = customerWeight;
    }

    public String getCustomerProfessional() {
        return customerProfessional;
    }

    public void setCustomerProfessional(String customerProfessional) {
        this.customerProfessional = customerProfessional;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getMeasurerName() {
        return measurerName;
    }

    public void setMeasurerName(String measurerName) {
        this.measurerName = measurerName;
    }

    public String getMeasurerPhone() {
        return measurerPhone;
    }

    public void setMeasurerPhone(String measurerPhone) {
        this.measurerPhone = measurerPhone;
    }

    public String getReceivingAddress() {
        return receivingAddress;
    }

    public void setReceivingAddress(String receivingAddress) {
        this.receivingAddress = receivingAddress;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }

    public String getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<YunOrderDetailDto> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<YunOrderDetailDto> orderDetail) {
        this.orderDetail = orderDetail;
    }

    public String getOrderBodyMeasure() {
        return orderBodyMeasure;
    }

    public void setOrderBodyMeasure(String orderBodyMeasure) {
        this.orderBodyMeasure = orderBodyMeasure;
    }

    public String getOrderBodyLanguage() {
        return orderBodyLanguage;
    }

    public void setOrderBodyLanguage(String orderBodyLanguage) {
        this.orderBodyLanguage = orderBodyLanguage;
    }

    public String getOrderProcess() {
        return orderProcess;
    }

    public void setOrderProcess(String orderProcess) {
        this.orderProcess = orderProcess;
    }

    public List<OrderAttachment> getOrderAttachment() {
        return orderAttachment;
    }

    public void setOrderAttachment(List<OrderAttachment> orderAttachment) {
        this.orderAttachment = orderAttachment;
    }

    public String getTryonOpinion() {
        return tryonOpinion;
    }

    public void setTryonOpinion(String tryonOpinion) {
        this.tryonOpinion = tryonOpinion;
    }

}
/**
 * @program: xingyu-order
 * @description: 云订单
 * @author: zhuxl
 * @create: 2019-09-23 16:57
 **/
