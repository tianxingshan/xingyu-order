package com.kongque.entity.productionRepair;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.kongque.model.MaterialsModel;

@Entity
@Table(name = "mes_repair_production_plan_supplement_materiel")
@DynamicInsert(true)
@DynamicUpdate(true)
public class MesRepairProductionPlanSupplementMateriel implements Serializable{
	
	private static final long serialVersionUID = 4102852146272468167L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "c_id")
	private String id;
	
	@Column(name = "c_supplement_id")
	private String supplementId;
	
	@Column(name = "c_material_id")
	private String materialId;
	
	@Column(name = "c_quantity")
	private String quantity;
	
	@Transient
	private MaterialsModel material;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSupplementId() {
		return supplementId;
	}

	public void setSupplementId(String supplementId) {
		this.supplementId = supplementId;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public MaterialsModel getMaterial() {
		return material;
	}

	public void setMaterial(MaterialsModel material) {
		this.material = material;
	}
	
}
