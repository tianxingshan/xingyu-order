package com.kongque.model;

import java.util.List;

import com.kongque.entity.productionRepair.MesRepairProductionPlanSupplement;
import com.kongque.entity.productionRepair.MesRepairProductionPlanSupplementMateriel;

public class RepairSupplementModel {
	
	private MesRepairProductionPlanSupplement repairPlanSupplement;
	
	private List<MesRepairProductionPlanSupplementMateriel> MaterielList;

	public MesRepairProductionPlanSupplement getRepairPlanSupplement() {
		return repairPlanSupplement;
	}

	public void setRepairPlanSupplement(MesRepairProductionPlanSupplement repairPlanSupplement) {
		this.repairPlanSupplement = repairPlanSupplement;
	}

	public List<MesRepairProductionPlanSupplementMateriel> getMaterielList() {
		return MaterielList;
	}

	public void setMaterielList(List<MesRepairProductionPlanSupplementMateriel> materielList) {
		MaterielList = materielList;
	}
	
}
