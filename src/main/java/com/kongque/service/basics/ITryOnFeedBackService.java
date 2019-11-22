package com.kongque.service.basics;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.kongque.dto.TryClothesRecordDto;
import com.kongque.dto.TryOnAuditOpinionDto;
import com.kongque.dto.TryOnFeedBackDto;
import com.kongque.model.TryClothesRecordModel;
import com.kongque.model.TryOnStyleModel;
import com.kongque.model.TryonAuditOpinionModel;
import com.kongque.util.Result;

public interface ITryOnFeedBackService {
	
	/**
	 * 查询试穿记录列表
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	public List<TryClothesRecordModel> getTryClothesRecordList(TryClothesRecordDto dto,Integer page,Integer rows);
	
	public Long getTryClothesRecordCount(TryClothesRecordDto dto);
	
	
	/**
	 * 新增或修改试衣反馈
	 * @param dto
	 * @return
	 */
	public Result addOrUpdateTryOnFeed(TryOnFeedBackDto dto,MultipartFile[] files);
	
	/**  
	 * 删除试衣反馈
	 * @param tryOnId
	 * @return
	 */
	public Result deleteTryOn(String tryOnId);
	
	
	
	/**
	 * 根据试穿记录id获取用户基本信息
	 * @param dto
	 * @return
	 */
	public Result showOrderDetail(TryOnFeedBackDto dto);
	
	/**
	 * 根据试穿反馈id获取试穿反馈详情
	 * @param dto
	 * @return
	 */
	public Result showTryOnDetail(TryOnFeedBackDto dto);
	
	
	public Result submitStatus(TryOnFeedBackDto dto);
	
	
	/**
	 * 审核试衣反馈
	 * @param tryOnId
	 * @return
	 */
	public Result examineTryOn(TryOnAuditOpinionDto dto);
	
	/**
	 * 审核意见列表
	 * @param dto
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<TryonAuditOpinionModel> getAuditOpinionList(TryOnAuditOpinionDto dto,Integer page,Integer rows);
	
	public Long getAuditOpinionCount(TryOnAuditOpinionDto dto);
	
	/**
	 * 根据orderid获取所有款式列表
	 * @param orderId
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<TryOnStyleModel> getStyleList(String orderId,Integer page,Integer rows);
	public Long getStyleCount(String orderId);
	/**
	 * 添加图
	 * @param attachmentJson
	 * @param attachmentFiles
	 * @return
	 *//*
	public Result saveOrUpdateAttachment(String attachmentJson, CommonsMultipartFile[] attachmentFiles);
	*//**
	 * 删除图
	 * @param detailId
	 * @return
	 *//*
	public Result deleteAttachmentDetail(String detailId);
	*//**
	 * 获取文件流
	 * @param path
	 *//*
	public void getAttachment(String path);
	*//**
	 * 修改附件尺码和说明
	 * @param dto
	 * @return
	 *//*
	public Result updateFileSizeAndInfo(@RequestBody TryOnFeedBackDto dto);*/
}
