package com.kongque.entity.taiwan;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "mes_garment_design")
@DynamicInsert(true)
@DynamicUpdate(true)
public class MesGarmentDesign implements Serializable{
    private static final long serialVersionUID = -8038885668190592063L;

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id")
    private String id;
    @Column(name = "order_detail_id")
    private String orderDetailId;
    @Column(name = "status")
    private Integer status;
    @Column(name = "tw_order_id")
    private String twOrderId;
    @JsonIgnore
    @Column(name = "garment_design_name")
    private String garmentDesignName;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "create_user_id")
    private String createUserId;
    @Column(name = "update_user_id")
    private String updateUserId;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
    @JsonIgnore
    @Column(name = "pdf_url")
    private String pdfUrl;
    @Column(name = "pdf_name")
    private String pdfName;
    @Column(name = "pdf_update_time")
    private Date pdfUpdateTime;
    @Column(name = "status_tw")
    private Integer statusTw;
    @Column(name = "status_pdf")
    private Integer statusPdf;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTwOrderId() {
        return twOrderId;
    }

    public void setTwOrderId(String twOrderId) {
        this.twOrderId = twOrderId;
    }

    public String getGarmentDesignName() {
        return garmentDesignName;
    }

    public void setGarmentDesignName(String garmentDesignName) {
        this.garmentDesignName = garmentDesignName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getPdfName() {
        return pdfName;
    }

    public void setPdfName(String pdfName) {
        this.pdfName = pdfName;
    }

    public Date getPdfUpdateTime() {
        return pdfUpdateTime;
    }

    public void setPdfUpdateTime(Date pdfUpdateTime) {
        this.pdfUpdateTime = pdfUpdateTime;
    }

    public Integer getStatusTw() {
        return statusTw;
    }

    public void setStatusTw(Integer statusTw) {
        this.statusTw = statusTw;
    }

    public Integer getStatusPdf() {
        return statusPdf;
    }

    public void setStatusPdf(Integer statusPdf) {
        this.statusPdf = statusPdf;
    }
}