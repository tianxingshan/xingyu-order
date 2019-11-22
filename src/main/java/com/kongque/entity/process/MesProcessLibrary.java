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
@Table(name = "mes_process_library")
@DynamicInsert(true)
@DynamicUpdate(true)
public class MesProcessLibrary implements Serializable {
    private static final long serialVersionUID = 2729462007611042110L;

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "c_id")
    private String id;

    /**
     * 工段
     */
    @Column(name = "c_mes_workshop_section_id")
    private String mesWorkshopSectionId;

    @JoinColumn(name = "c_mes_workshop_section_id",insertable = false,updatable = false)
    @ManyToOne
    private MesWorkshopSection mesWorkshopSection;
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
     * 状态 0 启用 1 禁用
     */
    @Column(name = "c_status")
    private String status;

    /**
     * 卡号
     */
    @Column(name = "c_make_card_no")
    private String makeCardNo;

    /**
     * 制卡次数
     */
    @Column(name = "c_made_count")
    private Integer madeCount;
    /**
     * 制卡日期
     */
    @Column(name = "c_made_time")
    private Date madeTime;
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

    public MesWorkshopSection getMesWorkshopSection() {
        return mesWorkshopSection;
    }

    public void setMesWorkshopSection(MesWorkshopSection mesWorkshopSection) {
        this.mesWorkshopSection = mesWorkshopSection;
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

    public Integer getMadeCount() {
        return madeCount;
    }

    public void setMadeCount(Integer madeCount) {
        this.madeCount = madeCount;
    }

    public Date getMadeTime() {
        return madeTime;
    }

    public void setMadeTime(Date madeTime) {
        this.madeTime = madeTime;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getMesWorkshopSectionId() {
        return mesWorkshopSectionId;
    }

    public void setMesWorkshopSectionId(String mesWorkshopSectionId) {
        this.mesWorkshopSectionId = mesWorkshopSectionId;
    }

    public String getMakeCardNo() {
        return makeCardNo;
    }

    public void setMakeCardNo(String makeCardNo) {
        this.makeCardNo = makeCardNo;
    }
}
/**
 * @program: xingyu-order
 * @description: 工序库
 * @author: zhuxl
 * @create: 2019-07-15 16:27
 **/
