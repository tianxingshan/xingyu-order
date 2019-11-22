package com.kongque.entity.productionorder;

import com.kongque.entity.measure.MeasurePosition;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @program: xingyu-order
 * @description: 订单明细量体尺寸
 * @author: zhuxl
 * @create: 2018-10-16 14:39
 **/
@Entity
@Table(name = "mes_order_detail_measure")
@DynamicInsert(true)
@DynamicUpdate(true)
public class MesOrderDetailMeasure implements Serializable {
    private static final long serialVersionUID = -77320564862532272L;

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "c_id")
    private String id;

    @Column(name = "c_order_detail_id")
    private String orderDetailId;

    @Column(name = "c_mes_measure_position_id")
    private String measurePositionId;

    @Column(name = "c_model_net_size")
    private String modelNetSize;

    @Column(name = "c_model_finish_size")
    private String modelFinishSize;

    @Column(name = "c_shrinkage")
    private String shrinkage;

    @Column(name = "c_tolerance")
    private String tolerance;

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

    @ManyToOne
    @JoinColumn(name = "c_mes_measure_position_id",insertable=false, updatable=false)
    private MeasurePosition measurePosition;

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

    public String getMeasurePositionId() {
        return measurePositionId;
    }

    public void setMeasurePositionId(String measurePositionId) {
        this.measurePositionId = measurePositionId;
    }

    public String getModelNetSize() {
        return modelNetSize;
    }

    public void setModelNetSize(String modelNetSize) {
        this.modelNetSize = modelNetSize;
    }

    public String getModelFinishSize() {
        return modelFinishSize;
    }

    public void setModelFinishSize(String modelFinishSize) {
        this.modelFinishSize = modelFinishSize;
    }

    public String getShrinkage() {
        return shrinkage;
    }

    public void setShrinkage(String shrinkage) {
        this.shrinkage = shrinkage;
    }

    public String getTolerance() {
        return tolerance;
    }

    public void setTolerance(String tolerance) {
        this.tolerance = tolerance;
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

    public MeasurePosition getMeasurePosition() {
        return measurePosition;
    }

    public void setMeasurePosition(MeasurePosition measurePosition) {
        this.measurePosition = measurePosition;
    }
}

