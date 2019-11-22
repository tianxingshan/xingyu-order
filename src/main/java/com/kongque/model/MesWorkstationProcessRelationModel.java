package com.kongque.model;

import java.util.Date;

/**
 * @author Administrator
 */
public class MesWorkstationProcessRelationModel {
    /**
     * 工段id
     */
    private String workshopSectionId;
    /**
     * 工段编码
     */
    private String workshopSectionCode;
    /**
     * 工段名称
     */
    private String workshopSectionName;
    /**
     * 车位编码
     */
    private String workstationCode;
    /**
     * 车位名称
     */
    private String workstationName;
    /**
     * 终端编号
     */
    private String terminalNumber;
    /**
     * 工序编码
     */
    private String processCode;
    /**
     * 工序名称
     */
    private String processName;
    /**
     *
     */
    private String createTime;
    /**
     * 绑定标志 0 未绑定 1 绑定
     */
    private String bindFlag;

    public String getWorkshopSectionId() {
        return workshopSectionId;
    }

    public void setWorkshopSectionId(String workshopSectionId) {
        this.workshopSectionId = workshopSectionId;
    }

    public String getWorkshopSectionCode() {
        return workshopSectionCode;
    }

    public void setWorkshopSectionCode(String workshopSectionCode) {
        this.workshopSectionCode = workshopSectionCode;
    }

    public String getWorkshopSectionName() {
        return workshopSectionName;
    }

    public void setWorkshopSectionName(String workshopSectionName) {
        this.workshopSectionName = workshopSectionName;
    }

    public String getWorkstationCode() {
        return workstationCode;
    }

    public void setWorkstationCode(String workstationCode) {
        this.workstationCode = workstationCode;
    }

    public String getWorkstationName() {
        return workstationName;
    }

    public void setWorkstationName(String workstationName) {
        this.workstationName = workstationName;
    }

    public String getTerminalNumber() {
        return terminalNumber;
    }

    public void setTerminalNumber(String terminalNumber) {
        this.terminalNumber = terminalNumber;
    }

    public String getProcessCode() {
        return processCode;
    }

    public void setProcessCode(String processCode) {
        this.processCode = processCode;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getBindFlag() {
        return bindFlag;
    }

    public void setBindFlag(String bindFlag) {
        this.bindFlag = bindFlag;
    }
}
/**
 * @program: xingyu-order
 * @description: 车位工序
 * @author: zhuxl
 * @create: 2019-07-19 14:54
 **/
