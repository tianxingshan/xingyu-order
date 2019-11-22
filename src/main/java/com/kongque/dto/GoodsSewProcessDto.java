package com.kongque.dto;

import java.util.List;

public class GoodsSewProcessDto {
	
	private String optionalTechnologyId;
	
	private List<SewDto> sewProcessList;
	
	public String getOptionalTechnologyId() {
		return optionalTechnologyId;
	}

	public void setOptionalTechnologyId(String optionalTechnologyId) {
		this.optionalTechnologyId = optionalTechnologyId;
	}

	public List<SewDto> getSewProcessList() {
		return sewProcessList;
	}

	public void setSewProcessList(List<SewDto> sewProcessList) {
		this.sewProcessList = sewProcessList;
	}

}
