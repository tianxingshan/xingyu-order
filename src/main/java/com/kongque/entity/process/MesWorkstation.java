package com.kongque.entity.process;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @program: xingyu-order
 * @description: 车位
 * @author: zhuxl
 * @create: 2019-07-15 15:31
 **/
@Entity
@Table(name = "mes_workstation")
@DynamicInsert(true)
@DynamicUpdate(true)
public class MesWorkstation implements Serializable {
    private static final long serialVersionUID = 2729462007611042110L;

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "c_id")
    private String id;

    /**
     * 编码
     */
    @Column(name = "c_code")
    private String code;

    /**
     * 名称
     */
    @Column(name = "c_name")
    private String name;


    /**
     * 终端设备编号
     */
    @Column(name = "c_terminal_number")
    private String terminalNumber;


    /**
     * 备注
     */
    @Column(name = "c_remarks")
    private String remarks;


    /**
     * 状态 0 启用 1 禁用
     */
    @Column(name = "c_status")
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTerminalNumber() {
        return terminalNumber;
    }

    public void setTerminalNumber(String terminalNumber) {
        this.terminalNumber = terminalNumber;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}

