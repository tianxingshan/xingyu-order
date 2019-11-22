package com.kongque.entity.process;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.jboss.logging.annotations.FormatWith;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @program: xingyu-order
 * @description: 员工
 * @author: zhuxl
 * @create: 2019-07-23 14:27
 **/
@Entity
@Table(name = "mes_staff")
@DynamicInsert(true)
@DynamicUpdate(true)
public class MesStaff implements Serializable {

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
     * 班组
     */
    @Column(name = "c_mes_team_id")
    private String mesTeamId;

    @ManyToOne
    @JoinColumn(name = "c_mes_team_id",insertable = false,updatable = false)
    private MesTeam mesTeam;

    /**
     * 性别
     */
    @Column(name = "c_sex")
    private String sex;

    /**
     * 生日
     */
    @Column(name = "c_birthday")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthday;

    /**
     * 制卡卡号
     */
    @Column(name = "c_make_card_no")
    private String makeCardNo;

    /**
     * 制卡次数
     */
    @Column(name = "c_make_card_number")
    private Integer makeCardNumber;

    /**
     * 制卡日期
     */
    @Column(name = "c_make_card_time")
    private Date makeCardTime;

    /**
     * 制卡人员
     */
    @Column(name = "c_make_card_creater")
    private String makeCardCreater;

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

    public String getMesTeamId() {
        return mesTeamId;
    }

    public void setMesTeamId(String mesTeamId) {
        this.mesTeamId = mesTeamId;
    }

    public MesTeam getMesTeam() {
        return mesTeam;
    }

    public void setMesTeam(MesTeam mesTeam) {
        this.mesTeam = mesTeam;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getMakeCardNumber() {
        return makeCardNumber;
    }

    public void setMakeCardNumber(Integer makeCardNumber) {
        this.makeCardNumber = makeCardNumber;
    }

    public Date getMakeCardTime() {
        return makeCardTime;
    }

    public void setMakeCardTime(Date makeCardTime) {
        this.makeCardTime = makeCardTime;
    }

    public String getMakeCardCreater() {
        return makeCardCreater;
    }

    public void setMakeCardCreater(String makeCardCreater) {
        this.makeCardCreater = makeCardCreater;
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

    public String getMakeCardNo() {
        return makeCardNo;
    }

    public void setMakeCardNo(String makeCardNo) {
        this.makeCardNo = makeCardNo;
    }
}

