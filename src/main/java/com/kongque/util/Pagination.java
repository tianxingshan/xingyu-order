package com.kongque.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pagination<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4227035018135320444L;
	/**
	 * 状态码
	 */
	private String returnCode = "200";
	/**
	 * 提示信息
	 */
	private String returnMsg = "操作成功！";
	/**
	 * 数据列表
	 */
	private List<T> rows;

	/**
	 * 总数
	 */
	private long total;

	/**
	 * 其它
	 */
	private Map<String, Object> map = new HashMap<>();

	public Pagination() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
}
