package com.kongque.dto;

/**
 * 颜色查询条件
 * 
 * @author yiyi
 *
 */
public class AreaDto {
	private String id;
	private String areaName;// 地区名称
	private String status;// 0:未启用 1:正常 2:停用 3:删除

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	

}
