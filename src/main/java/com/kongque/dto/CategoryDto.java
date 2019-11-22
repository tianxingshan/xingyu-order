package com.kongque.dto;

/**
 * 颜色查询条件
 * 
 * @author yiyi
 *
 */
public class CategoryDto {
	private String id;
	private String code;// 颜色编码
	private String name;// 颜色名称

	
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

}
