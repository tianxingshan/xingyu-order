package com.kongque.entity.process;

import com.kongque.entity.goods.Goods;
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
@Table(name = "mes_workstation_process_relation")
@DynamicInsert(true)
@DynamicUpdate(true)
public class MesWorkstationProcessRelation implements Serializable {
    private static final long serialVersionUID = 2729462007611042110L;

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "c_id")
    private String id;

    /**
     * 车位
     */
    @Column(name = "c_mes_workstation_id")
    private String mesWorkstationId;

    @JoinColumn(name = "c_mes_workstation_id",insertable = false,updatable = false)
    @ManyToOne
    private MesWorkstation mesWorkstation;

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
    @Column(name = "c_create_time")
    private Date createTime;


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

    public String getMesWorkstationId() {
        return mesWorkstationId;
    }

    public void setMesWorkstationId(String mesWorkstationId) {
        this.mesWorkstationId = mesWorkstationId;
    }

    public MesWorkstation getMesWorkstation() {
        return mesWorkstation;
    }

    public void setMesWorkstation(MesWorkstation mesWorkstation) {
        this.mesWorkstation = mesWorkstation;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
/**
 * @program: xingyu-order
 * @description: 款式工艺库关系
 * @author: zhuxl
 * @create: 2019-07-15 16:27
 **/
