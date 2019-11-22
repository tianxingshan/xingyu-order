package com.kongque.entity.plan;

import com.kongque.entity.order.Order;
import com.kongque.entity.order.OrderDetail;
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
@Table(name = "mes_order_plan_detail")
@DynamicInsert(true)
@DynamicUpdate(true)
public class OrderPlanDetail implements Serializable{
    private static final long serialVersionUID = 8177725654241152155L;
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "c_id")
    private String id;  //主键
    @Column(name = "c_order_plan_id")
    private String orderPlanId;  //计划单
    @Column(name = "c_order_id")
    private String orderId;  //订单
    @Column(name = "c_order_detail_id")
    private String orderDetailId;  //订单明细
    @Column(name = "c_delete_flag")
    private Integer deleteFlag;  //删除标识：0：正常，1：已删除
    @Column(name = "c_prod_finish_time")
    private Date prodFinishTime;  //生产完成时间
    @ManyToOne
    @JoinColumn(name = "c_order_id",insertable =false, updatable =false)
    private Order order;  //订单
    @ManyToOne
    @JoinColumn(name = "c_order_detail_id",insertable =false, updatable =false)
    private OrderDetail orderDetail;  //订单明细

    @Transient
    private String size;    //尺码
    @Transient
    private String Stylist;    //版型师

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderPlanId() {
        return orderPlanId;
    }

    public void setOrderPlanId(String orderPlanId) {
        this.orderPlanId = orderPlanId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Date getProdFinishTime() {
        return prodFinishTime;
    }

    public void setProdFinishTime(Date prodFinishTime) {
        this.prodFinishTime = prodFinishTime;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getStylist() {
        return Stylist;
    }

    public void setStylist(String stylist) {
        Stylist = stylist;
    }
}
