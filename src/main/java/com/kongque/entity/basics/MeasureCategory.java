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
@Table(name = "t_measure_category")
@DynamicInsert(true)
@DynamicUpdate(true)
public class MeasureCategory implements Serializable {
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
    private String status;// 0:未启用 1:正常 2:停用 3:删除

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
}
/**
 * @program: xingyu-order
 * @description: 量体分类
 * @author: zhuxl
 * @create: 2019-07-05 08:34
 **/
