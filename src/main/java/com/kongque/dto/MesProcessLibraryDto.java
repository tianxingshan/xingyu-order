package com.kongque.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author Administrator
 */
public class MesProcessLibraryDto {
    /**
     * id
     */
    private String id;
    /**
     * 编码
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 排序
     */
    private String sort;
    /**
     * 状态 0 启用 1 禁用
     */
    private String status;
    /**
     * 工段id
     */
    private String mesWorkshopSectionId;
    /**
     * 制卡次数
     */
    private Integer madeCount;
    /**
     * 制卡日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date madeTime;
    /**
     * 工位Id
     */
    private String mesWorkstationId;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMesWorkshopSectionId() {
        return mesWorkshopSectionId;
    }

    public void setMesWorkshopSectionId(String mesWorkshopSectionId) {
        this.mesWorkshopSectionId = mesWorkshopSectionId;
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

    public String getMesWorkstationId() {
        return mesWorkstationId;
    }

    public void setMesWorkstationId(String mesWorkstationId) {
        this.mesWorkstationId = mesWorkstationId;
    }
}
/**
 * @program: xingyu-order
 * @description: 工序库
 * @author: zhuxl
 * @create: 2019-07-15 16:49
 **/
