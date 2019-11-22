package com.kongque.entity.productionorder;

import com.kongque.entity.order.OrderDetail;
import com.kongque.entity.user.User;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Administrator
 */
@Entity
@Table(name = "mes_order_detail_print_log")
@DynamicInsert(true)
@DynamicUpdate(true)
public class MesOrderDetailPrintLog implements Serializable {

    private static final long serialVersionUID = -1613764529983324223L;

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "c_id")
    private String id;

    //订单明细id

    @Column(name = "c_order_detail_id")
    private String orderDetailId;

    //操作人员id
    @Column(name = "c_create_user_id")
    private String createUserId;

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

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }
}
/**
 * @program: xingyu-order
 * @description: 技术工艺订单明细批量打印
 * @author: zhuxl
 * @create: 2018-11-26 17:35
 **/
