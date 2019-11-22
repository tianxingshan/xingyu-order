package com.kongque.entity.productionorder;

import com.kongque.entity.process.MesProcess;
import com.kongque.entity.process.ProcessPosition;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @program: xingyu-order
 * @description: 订单明细缝制工序
 * @author: zhuxl
 * @create: 2018-10-16 17:29
 **/
@Entity
@Table(name = "mes_order_detail_sewing_process")
@DynamicInsert(true)
@DynamicUpdate(true)
public class MesOrderDetailSewingProcess implements Serializable {

    private static final long serialVersionUID = 2729462007611042110L;

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "c_id")
    private String id;

    @Column(name = "c_order_detail_id")
    private String orderDetailId;

    @Column(name = "c_process_position_id")
    private String processPositionId;

    @Column(name = "c_process_id")
    private String processId;

    @Column(name = "c_sort")
    private String sort;

    @ManyToOne
    @JoinColumn(name = "c_process_position_id",insertable =false, updatable =false)
    private ProcessPosition processPosition;

    @ManyToOne
    @JoinColumn(name = "c_process_id",insertable =false, updatable =false)
    private MesProcess process;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProcessPositionId() {
        return processPositionId;
    }

    public void setProcessPositionId(String processPositionId) {
        this.processPositionId = processPositionId;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public ProcessPosition getProcessPosition() {
        return processPosition;
    }

    public void setProcessPosition(ProcessPosition processPosition) {
        this.processPosition = processPosition;
    }

    public MesProcess getProcess() {
        return process;
    }

    public void setProcess(MesProcess process) {
        this.process = process;
    }

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }
}

