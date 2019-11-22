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
@Table(name = "t_category_measure_relation")
@DynamicInsert(true)
@DynamicUpdate(true)
public class CategoryMeasureRelation implements Serializable {
    private static final long serialVersionUID = -4698128545014899439L;

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "c_id")
    private String id;

    @Column(name = "c_category_id")
    private String categoryId;
    @ManyToOne
    @JoinColumn(name = "c_body_measure_id")
    private BodyMeasureInfo bodyMeasureInfo;

    @Column(name = "c_status")
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public BodyMeasureInfo getBodyMeasureInfo() {
        return bodyMeasureInfo;
    }

    public void setBodyMeasureInfo(BodyMeasureInfo bodyMeasureInfo) {
        this.bodyMeasureInfo = bodyMeasureInfo;
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
 * @description: 品类量体对照库
 * @author: zhuxl
 * @create: 2019-07-04 14:51
 **/
