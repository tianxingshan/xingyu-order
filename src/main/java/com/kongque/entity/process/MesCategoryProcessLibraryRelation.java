package com.kongque.entity.process;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 */
@Entity
@Table(name = "mes_category_process_library_relation")
@DynamicInsert(true)
@DynamicUpdate(true)
public class MesCategoryProcessLibraryRelation implements Serializable {
    private static final long serialVersionUID = 2729462007611042110L;

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "c_id")
    private String id;

    /**
     * 工序类别
     */
    @Column(name = "c_mes_process_category_id")
    private String mesProcessCategoryId;

    @JoinColumn(name = "c_mes_process_category_id",insertable = false,updatable = false)
    @ManyToOne
    private MesProcessCategory mesProcessCategory;

    /**
     * 工序库
     */
    @Column(name = "c_mes_process_library_id")
    private String mesProcessLibraryId;
    @JoinColumn(name = "c_mes_process_library_id",insertable = false,updatable = false)
    @ManyToOne
    private MesProcessLibrary mesProcessLibrary;
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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getMesProcessCategoryId() {
        return mesProcessCategoryId;
    }

    public void setMesProcessCategoryId(String mesProcessCategoryId) {
        this.mesProcessCategoryId = mesProcessCategoryId;
    }

    public MesProcessCategory getMesProcessCategory() {
        return mesProcessCategory;
    }

    public void setMesProcessCategory(MesProcessCategory mesProcessCategory) {
        this.mesProcessCategory = mesProcessCategory;
    }

    public String getMesProcessLibraryId() {
        return mesProcessLibraryId;
    }

    public void setMesProcessLibraryId(String mesProcessLibraryId) {
        this.mesProcessLibraryId = mesProcessLibraryId;
    }

    public MesProcessLibrary getMesProcessLibrary() {
        return mesProcessLibrary;
    }

    public void setMesProcessLibrary(MesProcessLibrary mesProcessLibrary) {
        this.mesProcessLibrary = mesProcessLibrary;
    }
}
/**
 * @program: xingyu-order
 * @description: 类别工序库关系
 * @author: zhuxl
 * @create: 2019-07-15 16:27
 **/
