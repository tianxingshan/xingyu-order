package com.kongque.dto;

/**
 * 颜色查询条件
 * 
 * @author yiyi
 *
 */
public class ColorDto {
	private String id;
	private String code;// 颜色编码
	private String name;// 颜色名称
	private String[] imageKeys;// 图片路径
	private String[] imageDelKeys;// 删除图片路径
	private String fileName;//图片名

	
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

	public String[] getImageKeys() {
		return imageKeys;
	}

	public void setImageKeys(String[] imageKeys) {
		this.imageKeys = imageKeys;
	}

	public String[] getImageDelKeys() {
		return imageDelKeys;
	}

	public void setImageDelKeys(String[] imageDelKeys) {
		this.imageDelKeys = imageDelKeys;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
}
