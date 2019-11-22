package com.kongque.dto;

import java.util.List;

public class MaterialsSaveDto {
	
	private String orderRepairId;
	
	private List<MaterialsDto> materielList;

	public String getOrderRepairId() {
		return orderRepairId;
	}

	public void setOrderRepairId(String orderRepairId) {
		this.orderRepairId = orderRepairId;
	}

	public List<MaterialsDto> getMaterielList() {
		return materielList;
	}

	public void setMaterielList(List<MaterialsDto> materielList) {
		this.materielList = materielList;
	}
	
}
