package com.kongque.entity.plan;

import com.kongque.entity.user.User;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaxt
 * @date 2018/10/15.
 */
@Entity
@Table(name = "mes_order_plan")
@DynamicInsert(true)
@DynamicUpdate(true)
public class OrderPlan implements Serializable{
    private static final long serialVersionUID = 2974944252375760542L;
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "c_id")
    private String id;  //主键
    @Column(name = "c_plan_number")
    private String planNumber;  //计划单号
    @Column(name = "c_plan_status")
    private Integer planStatus;  //计划单状态：0：草稿，1：待审核，2：已审核，3：已驳回，4：已下达，9：生产完成
    @Column(name = "c_prod_factory_id")
    private String prodFactory;   //生产工厂
    @Column(name = "c_prod_time")
    private Date prodTime;  //投产日期
    @Column(name = "c_delivery_time")
    private Date deliveryTime;  //计划交期
    @Column(name = "c_total_count")
    private Integer totalCount;  //计划单总数量
    @Column(name = "c_create_user_id")
    private String createUserId;  //创建人
    @Column(name = "c_create_time")
    private Date createTime;  //创建时间
    @Column(name = "c_audit_user_id")
    private String auditUserId;  //审核人
    @Column(name = "c_audit_time")
    private Date auditTime;  //审核时间
    @Column(name = "c_send_user_id")
    private String sendUserId;  //下达人
    @Column(name = "c_send_time")
    private Date sendTime;  //下达时间
    @Column(name = "c_update_user_id")
    private String updateUserId;  //修改人
    @Column(name = "c_update_time")
    private Date updateTime;  //修改时间
    @Column(name = "c_remark")
    private String remark;  //备注
    @Column(name = "c_delete_flag")
    private Integer deleteFlag;  //删除标识：0：正常，1：已删除
    @ManyToOne
    @JoinColumn(name = "c_create_user_id",insertable =false, updatable =false)
    private User createUser;  //创建人
    @ManyToOne
    @JoinColumn(name = "c_audit_user_id",insertable =false, updatable =false)
    private User auditUser;  //审核人
    @ManyToOne
    @JoinColumn(name = "c_send_user_id",insertable =false, updatable =false)
    private User sendUser;  //下达人
    @ManyToOne
    @JoinColumn(name = "c_update_user_id",insertable =false, updatable =false)
    private User updateUser;  //修改人
    @Column(name = "status_tw")
    private Integer statusTW;  //台湾状态
    @Column(name = "tw_post_time")
    private Date postTimeTW;  //台湾同步日期
    @Column(name = "publishers")
    private String publishers;  //出版方

    /*@OneToMany(mappedBy = "orderPlanId")
    private List<OrderPlanDetail> orderPlanDetails;*/

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

    public Integer getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(Integer planStatus) {
        this.planStatus = planStatus;
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

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
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

    public String getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(String auditUserId) {
        this.auditUserId = auditUserId;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    public User getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(User auditUser) {
        this.auditUser = auditUser;
    }

    public User getSendUser() {
        return sendUser;
    }

    public void setSendUser(User sendUser) {
        this.sendUser = sendUser;
    }

    public User getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(User updateUser) {
        this.updateUser = updateUser;
    }

    public Integer getStatusTW() {
        return statusTW;
    }

    public void setStatusTW(Integer statusTW) {
        this.statusTW = statusTW;
    }

    public Date getPostTimeTW() {
        return postTimeTW;
    }

    public void setPostTimeTW(Date postTimeTW) {
        this.postTimeTW = postTimeTW;
    }

    public String getPublishers() {
        return publishers;
    }

    public void setPublishers(String publishers) {
        this.publishers = publishers;
    }
/*public List<OrderPlanDetail> getOrderPlanDetails() {
        return orderPlanDetails;
    }

    public void setOrderPlanDetails(List<OrderPlanDetail> orderPlanDetails) {
        this.orderPlanDetails = orderPlanDetails;
    }*/
}
