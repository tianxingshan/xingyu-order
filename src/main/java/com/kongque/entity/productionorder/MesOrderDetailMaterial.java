package com.kongque.entity.productionorder;

import com.kongque.entity.material.MaterialCategory;
import com.kongque.entity.material.MaterialPosition;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @program: xingyu-order
 * @description: 订单明细物料清单
 * @author: zhuxl
 * @create: 2018-10-16 18:08
 **/
@Entity
@Table(name = "mes_order_detail_material")
@DynamicInsert(true)
@DynamicUpdate(true)
public class MesOrderDetailMaterial implements Serializable{

    private static final long serialVersionUID = 2729462007611042110L;

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "c_id")
    private String id;

    @Column(name = "c_order_detail_id")
    private String orderDetailId;

    @Column(name = "c_material_position_id")
    private String materialPositionId;

    @Column(name = "c_material_id")
    private String materialId;

    @Column(name = "c_code")
    private String code;

    @Column(name = "c_name")
    private String name;

    @Column(name = "c_inventory_unit")
    private String inventoryUnit;

    @Column(name = "c_material_category_id")
    private String materialCategoryId;

    @Column(name = "c_spec")
    private String spec;

    @Column(name = "c_quality")
    private String quality;

    @Column(name = "c_num")
    private String num;

    @Column(name = "c_color")
    private String color;

    @Column(name = "c_color_code")
    private String colorCode;

    @Column(name = "c_material_name")
    private String materialName;


    @ManyToOne
    @JoinColumn(name="c_material_position_id",insertable=false,updatable=false)
    private MaterialPosition materialPosition;

    @ManyToOne
    @JoinColumn(name="c_material_category_id",insertable=false,updatable=false)
    private MaterialCategory materialCategory;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getMaterialPositionId() {
        return materialPositionId;
    }

    public void setMaterialPositionId(String materialPositionId) {
        this.materialPositionId = materialPositionId;
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

    public String getInventoryUnit() {
        return inventoryUnit;
    }

    public void setInventoryUnit(String inventoryUnit) {
        this.inventoryUnit = inventoryUnit;
    }

    public String getMaterialCategoryId() {
        return materialCategoryId;
    }

    public void setMaterialCategoryId(String materialCategoryId) {
        this.materialCategoryId = materialCategoryId;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public MaterialPosition getMaterialPosition() {
        return materialPosition;
    }

    public void setMaterialPosition(MaterialPosition materialPosition) {
        this.materialPosition = materialPosition;
    }

    public MaterialCategory getMaterialCategory() {
        return materialCategory;
    }

    public void setMaterialCategory(MaterialCategory materialCategory) {
        this.materialCategory = materialCategory;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }
}
