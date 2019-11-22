package com.kongque.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.util.Date;

/**
 * @program: xingyu-order
 * @description: 订单明细分配
 * @author: zhuxl
 * @create: 2018-10-10 15:53
 **/
public class MesOrderDetailAssignDto {

    private String id;
    //订单明细id
    private String orderDetailId;
    //版型师id
    private String technicianId;
    //版型师姓名
    private String technicianName;
    //删除标志0：正常 1：删除
    private Integer deleteFlag;
    //创建人员ID
    private String createUserId;
    //创建日期
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createTime;
    //修改人员ID
    private String updateUserId;
    //修改日期
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updateTime;

    //技术完成人员ID
    private String technicalFinishedUserId;
    //技术完成日期
    @DateTimeFormat(pattern="yyyy-MM-dd")
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

