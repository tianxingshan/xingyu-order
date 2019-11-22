package com.kongque.entity.basics;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Administrator
 */
@Entity
@Table(name = "t_body_measure_info")
@DynamicInsert(true)
@DynamicUpdate(true)
public class BodyMeasureInfo implements Serializable {
    private static final long serialVersionUID = -4698128545014899439L;

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "c_id")
    private String id;
    @Column(name = "c_code")
    private String code;
    @Column(name = "c_name")
    private String name;// 名称
    @Column(name = "c_status")
    private String status;// 0:正常 1:停用
    /**
     * 排序
     */
    @Column(name = "c_sort")
    private String sort;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
/**
 * @program: xingyu-order
 * @description: 量体数据库
 * @author: zhuxl
 * @create: 2019-07-04 13:45
 **/
