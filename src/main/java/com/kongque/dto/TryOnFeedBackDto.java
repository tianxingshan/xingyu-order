package com.kongque.dto;

import java.util.Date;

/**
 * 商户查询条件
 * 
 * @author wangwj
 *
 */
public class TryOnFeedBackDto {
	//试穿反馈id
		private String tryOnId;
		
		//订单id
		private String orderId;
		
		//订单详情id
		private String orderDetailId;
		
		//款式id
		private String goodsId;
		
		//反馈单号
		private String tryonNumber;
		
		//反馈日期
		private String tryonDate;
		//试衣效果
		private String feedBackJson;
		
		private String size;
		
		private String instruction;
	
		private String createUser;
		
		
		private Date createTime;
		
		private String updateUser;
		
		
		private Date updateTime;

		private String goodsDetailId;

		private String status;
		
		private String[] fileKeys; 
		
		private String[] imageDelKeys;// 删除图片路径
		public String getTryOnId() {
			return tryOnId;
		}


		public void setTryOnId(String tryOnId) {
			this.tryOnId = tryOnId;
		}


		public String getOrderId() {
			return orderId;
		}


		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}


		public String getOrderDetailId() {
			return orderDetailId;
		}


		public void setOrderDetailId(String orderDetailId) {
			this.orderDetailId = orderDetailId;
		}


		public String getGoodsId() {
			return goodsId;
		}


		public void setGoodsId(String goodsId) {
			this.goodsId = goodsId;
		}


		public String getTryonNumber() {
			return tryonNumber;
		}


		public void setTryonNumber(String tryonNumber) {
			this.tryonNumber = tryonNumber;
		}


		public String getTryonDate() {
			return tryonDate;
		}


		public void setTryonDate(String tryonDate) {
			this.tryonDate = tryonDate;
		}


		public String getFeedBackJson() {
			return feedBackJson;
		}


		public void setFeedBackJson(String feedBackJson) {
			this.feedBackJson = feedBackJson;
		}


		public String getSize() {
			return size;
		}


		public void setSize(String size) {
			this.size = size;
		}


		public String getInstruction() {
			return instruction;
		}


		public void setInstruction(String instruction) {
			this.instruction = instruction;
		}


		public String getCreateUser() {
			return createUser;
		}


		public void setCreateUser(String createUser) {
			this.createUser = createUser;
		}


		public Date getCreateTime() {
			return createTime;
		}


		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}


		public String getUpdateUser() {
			return updateUser;
		}


		public void setUpdateUser(String updateUser) {
			this.updateUser = updateUser;
		}


		public Date getUpdateTime() {
			return updateTime;
		}


		public void setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
		}


		public String getGoodsDetailId() {
			return goodsDetailId;
		}


		public void setGoodsDetailId(String goodsDetailId) {
			this.goodsDetailId = goodsDetailId;
		}


		public String getStatus() {
			return status;
		}


		public void setStatus(String status) {
			this.status = status;
		}


		public String[] getFileKeys() {
			return fileKeys;
		}


		public void setFileKeys(String[] fileKeys) {
			this.fileKeys = fileKeys;
		}


		public String[] getImageDelKeys() {
			return imageDelKeys;
		}


		public void setImageDelKeys(String[] imageDelKeys) {
			this.imageDelKeys = imageDelKeys;
		}

		
		
	

	
}
