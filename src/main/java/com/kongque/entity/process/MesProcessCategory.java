package com.kongque.entity.process;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Administrator
 */
@Entity
@Table(name = "mes_process_category")
@DynamicInsert(true)
@DynamicUpdate(true)
public class MesProcessCategory implements Serializable {
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
     * 备注
     */
    @Column(name = "c_remarks")
    private String remarks;

    /**
     * 排序
     */
    @Column(name = "c_sort")
    private String sort;

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

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
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
/**
 * @program: xingyu-order
 * @description: 工序类别
 * @author: zhuxl
 * @create: 2019-07-15 15:31
 **/
