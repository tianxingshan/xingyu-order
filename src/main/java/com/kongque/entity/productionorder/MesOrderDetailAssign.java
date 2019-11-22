package com.kongque.entity.productionorder;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @program: xingyu-order
 * @description: 订单明细分配
 * @author: zhuxl
 * @create: 2018-10-10 15:24
 **/
@Entity
@Table(name = "mes_order_detail_assign")
@DynamicInsert(true)
@DynamicUpdate(true)
public class MesOrderDetailAssign implements Serializable {

    private static final long serialVersionUID = -1613764529983324223L;

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "c_id")
    private String id;

    //订单明细id
    @Column(name = "c_order_detail_id")
    private String orderDetailId;

    //版型师id
    @Column(name = "c_technician_id")
    private String technicianId;

    //版型师姓名
    @Column(name = "c_technician_name")
    private String technicianName;

    //删除标志0：正常 1：删除
    @Column(name = "c_delete_flag")
    private Integer deleteFlag;

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

    //技术完成人员ID
    @Column(name = "c_technical_finished_user_id")
    private String technicalFinishedUserId;
    //技术完成日期

    @Column(name = "c_technical_finished_time")
    private Date technicalFinishedTime;


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

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
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

    public String getTechnicalFinishedUserId() {
        return technicalFinishedUserId;
    }

    public void setTechnicalFinishedUserId(String technicalFinishedUserId) {
        this.technicalFinishedUserId = technicalFinishedUserId;
    }

    public Date getTechnicalFinishedTime() {
        return technicalFinishedTime;
    }

    public void setTechnicalFinishedTime(Date technicalFinishedTime) {
        this.technicalFinishedTime = technicalFinishedTime;
    }
}

