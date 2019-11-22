package com.kongque.entity.process;

import com.kongque.entity.goods.Goods;
import com.kongque.entity.goods.GoodsModel;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Administrator
 */
@Entity
@Table(name = "mes_style_technology_process_library_relation")
@DynamicInsert(true)
@DynamicUpdate(true)
public class MesStyleTechnologyProcessLibraryRelation implements Serializable {
    private static final long serialVersionUID = 2729462007611042110L;

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "c_id")
    private String id;

    /**
     * 款式id
     */
    @Column(name = "c_goods_id")
    private String goodsId;

    @JoinColumn(name = "c_goods_id",insertable = false,updatable = false)
    @ManyToOne
    private GoodsModel goods;

    /**
     * 工艺库
     */
    @Column(name = "c_mes_technology_library_id")
    private String mesTechnologyLibraryId;
    @JoinColumn(name = "c_mes_technology_library_id",insertable = false,updatable = false)
    @ManyToOne
    private MesTechnologyLibrary mesTechnologyLibrary;

    /**
     * 工序库
     */
    @Column(name = "c_mes_process_library_id")
    private String mesProcessLibraryId;
    @JoinColumn(name = "c_mes_process_library_id",insertable = false,updatable = false)
    @ManyToOne
    private MesProcessLibrary mesProcessLibrary;

    /**
     * 工序等级
     */
    @Column(name = "c_mes_process_level_id")
    private String mesProcessLevelId;

    @ManyToOne
    @JoinColumn(name = "c_mes_process_level_id",insertable = false,updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private MesProcessLevel mesProcessLevel;

    /**
     * 线
     */
    @Column(name = "c_mes_process_thread_id")
    private String mesProcessThreadId;

    @ManyToOne
    @JoinColumn(name = "c_mes_process_thread_id",insertable = false,updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private MesProcessThread mesProcessThread;

    /**
     * 机器
     */
    @Column(name = "c_mes_process_machine_id")
    private String mesProcessMachineId;

    @ManyToOne
    @JoinColumn(name = "c_mes_process_machine_id",insertable = false,updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private MesProcessMachine mesProcessMachine;

    /**
     * 针幅
     */
    @Column(name = "c_mes_process_needle_range_id")
    private String mesProcessNeedleRangeId;
    @ManyToOne
    @JoinColumn(name = "c_mes_process_needle_range_id",insertable = false,updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private MesProcessNeedleRange mesProcessNeedleRange;

    /**
     * 基准秒数
     */
    @Column(name = "c_base_seconds")
    private Integer baseSeconds;

    /**
     * 基准单价
     */
    @Column(name = "c_base_price")
    private Double basePrice;

    /**
     * 难度系数
     */
    @Column(name = "c_difficulty_degree")
    private Integer difficultyDegree;

    /**
     * 工价
     */
    @Column(name = "c_labour_cost")
    private Double labourCost;


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

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public GoodsModel getGoods() {
        return goods;
    }

    public void setGoods(GoodsModel goods) {
        this.goods = goods;
    }

    public String getMesTechnologyLibraryId() {
        return mesTechnologyLibraryId;
    }

    public void setMesTechnologyLibraryId(String mesTechnologyLibraryId) {
        this.mesTechnologyLibraryId = mesTechnologyLibraryId;
    }

    public MesTechnologyLibrary getMesTechnologyLibrary() {
        return mesTechnologyLibrary;
    }

    public void setMesTechnologyLibrary(MesTechnologyLibrary mesTechnologyLibrary) {
        this.mesTechnologyLibrary = mesTechnologyLibrary;
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

    public String getMesProcessLevelId() {
        return mesProcessLevelId;
    }

    public void setMesProcessLevelId(String mesProcessLevelId) {
        this.mesProcessLevelId = mesProcessLevelId;
    }

    public MesProcessLevel getMesProcessLevel() {
        return mesProcessLevel;
    }

    public void setMesProcessLevel(MesProcessLevel mesProcessLevel) {
        this.mesProcessLevel = mesProcessLevel;
    }

    public String getMesProcessThreadId() {
        return mesProcessThreadId;
    }

    public void setMesProcessThreadId(String mesProcessThreadId) {
        this.mesProcessThreadId = mesProcessThreadId;
    }

    public MesProcessThread getMesProcessThread() {
        return mesProcessThread;
    }

    public void setMesProcessThread(MesProcessThread mesProcessThread) {
        this.mesProcessThread = mesProcessThread;
    }

    public String getMesProcessMachineId() {
        return mesProcessMachineId;
    }

    public void setMesProcessMachineId(String mesProcessMachineId) {
        this.mesProcessMachineId = mesProcessMachineId;
    }

    public MesProcessMachine getMesProcessMachine() {
        return mesProcessMachine;
    }

    public void setMesProcessMachine(MesProcessMachine mesProcessMachine) {
        this.mesProcessMachine = mesProcessMachine;
    }

    public String getMesProcessNeedleRangeId() {
        return mesProcessNeedleRangeId;
    }

    public void setMesProcessNeedleRangeId(String mesProcessNeedleRangeId) {
        this.mesProcessNeedleRangeId = mesProcessNeedleRangeId;
    }

    public MesProcessNeedleRange getMesProcessNeedleRange() {
        return mesProcessNeedleRange;
    }

    public void setMesProcessNeedleRange(MesProcessNeedleRange mesProcessNeedleRange) {
        this.mesProcessNeedleRange = mesProcessNeedleRange;
    }

    public Integer getBaseSeconds() {
        return baseSeconds;
    }

    public void setBaseSeconds(Integer baseSeconds) {
        this.baseSeconds = baseSeconds;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public Integer getDifficultyDegree() {
        return difficultyDegree;
    }

    public void setDifficultyDegree(Integer difficultyDegree) {
        this.difficultyDegree = difficultyDegree;
    }

    public Double getLabourCost() {
        return labourCost;
    }

    public void setLabourCost(Double labourCost) {
        this.labourCost = labourCost;
    }
}
/**
 * @program: xingyu-order
 * @description: 款式工艺工序库关系
 * @author: zhuxl
 * @create: 2019-07-15 16:27
 **/
