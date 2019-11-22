package com.kongque.entity.productionorder;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @program: xingyu-order
 * @description: 分配尺码
 * @author: zhuxl
 * @create: 2018-10-15 11:45
 **/
@Entity
@Table(name = "mes_order_detail_size")
@DynamicInsert(true)
@DynamicUpdate(true)
public class MesOrderDetailSize implements Serializable {
    private static final long serialVersionUID = -1613764529983324223L;

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "c_id")
    private String id;

    //订单明细id
    @Column(name = "c_order_detail_id")
    private String orderDetailId;

    //尺码id
    @Column(name = "c_mes_measure_size_id")
    private String mesMeasureSizeId;

    //尺码名称
    @Column(name = "c_mes_measure_size_name")
    private String mesMeasureSizeName;

    //款式可选项工艺id
    @Column(name = "c_mes_goods_optional_technology_id")
    private String mesGoodsOptionalTechnologyId;

    //备注
    @Column(name = "c_remarks")
    private String remarks;

    //创建人员ID
    @Column(name = "c_create_user_id")
    private String createUserId;

    //创建日期
    @Column(name = "c_create_time")
    private Date createTime;

    //修改人员ID
    @Column(name = "c_update_user_id")
    private String updateUserId;

    //修改日期
    @Column(name = "c_update_time")
    private Date updateTime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public String getMesGoodsOptionalTechnologyId() {
        return mesGoodsOptionalTechnologyId;
    }

    public void setMesGoodsOptionalTechnologyId(String mesGoodsOptionalTechnologyId) {
        this.mesGoodsOptionalTechnologyId = mesGoodsOptionalTechnologyId;
    }

    public String getMesMeasureSizeId() {
        return mesMeasureSizeId;
    }

    public void setMesMeasureSizeId(String mesMeasureSizeId) {
        this.mesMeasureSizeId = mesMeasureSizeId;
    }

    public String getMesMeasureSizeName() {
        return mesMeasureSizeName;
    }

    public void setMesMeasureSizeName(String mesMeasureSizeName) {
        this.mesMeasureSizeName = mesMeasureSizeName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}

