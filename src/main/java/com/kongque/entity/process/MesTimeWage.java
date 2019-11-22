package com.kongque.entity.process;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 */

@Entity
@Table(name = "mes_time_wage")
@DynamicInsert(true)
@DynamicUpdate(true)
public class MesTimeWage implements Serializable {
    private static final long serialVersionUID = 2729462007611042110L;

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "c_id")
    private String id;

    /**
     * 开始日期
     */
    @Column(name = "c_start_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    /**
     * 截止日期
     */
    @Column(name = "c_end_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    /**
     * 单价
     */
    @Column(name = "c_price")
    private Double price;

    /**
     * 操作日期
     */
    @Column(name = "c_create_time")
    private Date createTime;
    /**
     * 操作人员
     */
    @Column(name = "c_creater")
    private String creater;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }
}
/**
 * @program: xingyu-order
 * @description: 计时工资单价设置
 * @author: zhuxl
 * @create: 2019-08-27 09:37
 **/
