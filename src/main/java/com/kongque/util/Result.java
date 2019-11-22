package com.kongque.util;

import org.springframework.http.HttpStatus;

public class Result {

	private String returnCode = "200";

	private String returnMsg = "操作成功";

	private Object returnData;
	
	private Object rows;

	public Result() {

	}

	public Result(Object data) {
		this.returnData = data;
	}

	public Result(String returnCode, String returnMsg) {
		this.returnCode = returnCode;
		this.returnMsg = returnMsg;
	}

	public Result(String returnCode, String returnMsg, Object returnData) {
		this.returnCode = returnCode;
		this.returnMsg = returnMsg;
		this.returnData = returnData;
	}
	
	public Result(String returnCode, String returnMsg, Object returnData, Object rows) {
		this.returnCode = returnCode;
		this.returnMsg = returnMsg;
		this.returnData = returnData;
		this.rows = rows;
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

	public Object getReturnData() {
		return returnData;
	}

	public void setReturnData(Object returnData) {
		this.returnData = returnData;
	}

	public Object getRows() {
		return rows;
	}

	public void setRows(Object rows) {
		this.rows = rows;
	}

	public static boolean isOK(Result result){
		if (result!=null && String.valueOf(HttpStatus.OK.value()).equals(result.getReturnCode())){
			return true;
		}
		return false;
	}

}
