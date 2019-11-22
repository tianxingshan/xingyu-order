package com.kongque.controller.basics;

import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.kongque.dto.TryClothesRecordDto;
import com.kongque.dto.TryOnAuditOpinionDto;
import com.kongque.dto.TryOnFeedBackDto;
import com.kongque.model.TryClothesRecordModel;
import com.kongque.model.TryOnStyleModel;
import com.kongque.model.TryonAuditOpinionModel;
import com.kongque.service.basics.ITryOnFeedBackService;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

@RestController
@RequestMapping("/tryonFeedback")
public class TryOnFeedBackController {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(TryOnFeedBackController.class);
	@Resource
	private ITryOnFeedBackService service;
	
	/**
	 * 查询试衣记录列表
	 * 
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Pagination<TryClothesRecordModel> getTryClothesRecordList(TryClothesRecordDto dto,Integer page,Integer rows) {
		Pagination<TryClothesRecordModel> pagination = new Pagination<>();	
		Long total = service.getTryClothesRecordCount(dto);
		System.out.println(total);
		if(total != null){
			pagination.setTotal(total);
		}
		List<TryClothesRecordModel> resultList = service.getTryClothesRecordList(dto,page,rows);
		System.out.println(resultList.toString());
		if(resultList != null){			
			pagination.setRows(resultList);			
		}		
		return pagination;
	}
	
	
	/**
	 * 根据订单id获取用户基本信息
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/showDetail", method = RequestMethod.GET)
	public  Result showOrderDetail(TryOnFeedBackDto dto){
		return service.showOrderDetail(dto);
	}
	
	/**
	 * 根据样式id获取试穿反馈详情
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/tryOnDetail", method = RequestMethod.GET)
	public Result showTryOnDetail(TryOnFeedBackDto dto){
		return service.showTryOnDetail(dto);
	}
	
	/**
	 * 新增或修改试衣反馈
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public Result addOrUpdateTryOnFeed(TryOnFeedBackDto dto,MultipartFile[] files){
		return service.addOrUpdateTryOnFeed(dto,files); 
	}   
	/**
	 * 提交
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/submitStatus", method = RequestMethod.POST)
	public Result submitStatus(@RequestBody TryOnFeedBackDto dto){
		return service.submitStatus(dto); 
	} 
	
	/**
	 * 删除试衣反馈
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public  Result deleteTryOn(String tryOnId) {
		return service.deleteTryOn(tryOnId);
	}
	
	/**
	 * 审核试衣反馈
	 * @param tryOnId
	 * @return
	 */
	@RequestMapping(value = "/examine", method = RequestMethod.POST)
	public @ResponseBody Result examineTryOn(@RequestBody TryOnAuditOpinionDto dto) {
		return service.examineTryOn(dto);
	}
	
	/**
	 * 查询试穿审核意见列表
	 * 
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value = "/auditOpinion", method = RequestMethod.GET)
	public @ResponseBody Pagination<TryonAuditOpinionModel> getAuditOpinionList(TryOnAuditOpinionDto dto,Integer page,Integer rows) {
		Pagination<TryonAuditOpinionModel> pagination = new Pagination<>();		
		Long total = service.getAuditOpinionCount(dto);
		if(total != null){
			pagination.setTotal(total);
		}
		List<TryonAuditOpinionModel> resultList = service.getAuditOpinionList(dto,page,rows);
		if(resultList != null){			
			pagination.setRows(resultList);			
		}		
		return pagination;
	}
	
	/**
	 * 根据orderid获取所有款式列表
	 * @param orderId
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/style/list", method = RequestMethod.GET)
	public Pagination<TryOnStyleModel> getStyleList(String orderId,Integer page,Integer rows) {
		Pagination<TryOnStyleModel> pagination = new Pagination<>();		
		Long total = service.getStyleCount(orderId);
		if(total != null){
			pagination.setTotal(total);
		}
		List<TryOnStyleModel> resultList = service.getStyleList(orderId,page,rows);
		if(resultList != null){			
			pagination.setRows(resultList);			
		}		
		return pagination;
	}
	
	/**
	 *保存更新试衣单附件
	 * @param attachmentJson
	 * @param attachmentFiles
	 * @return
	 *//*
	@RequestMapping(value = "/attachment/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody Result saveOrUpdateAttachment(@RequestParam String attachmentJson,
			@RequestParam(required = false, name = "attachmentFiles") CommonsMultipartFile[] attachmentFiles) {

		return service.saveOrUpdateAttachment(attachmentJson, attachmentFiles);
	}
	
	*//**
	 * 删除
	 * @param detailId
	 * @return
	 *//*
	@RequestMapping(value = "/attachmentDetail/{detailId}", method = RequestMethod.DELETE)
	public @ResponseBody Result deleteAttachmentDetail(@PathVariable String detailId) {

		return service.deleteAttachmentDetail(detailId);
	}

	*//**
	 * 获取附件文件流
	 * 
	 * @param path
	 *//*
	@RequestMapping(value = "/file", method = RequestMethod.GET)
	public @ResponseBody void getAttachement(String path) {

		service.getAttachment(path);
	}
	
	*//**
	 * 修改附件尺码和说明
	 * @param dto
	 * @return
	 *//*
	@RequestMapping(value = "/updateFileSizeAndInfo", method = RequestMethod.POST)
	public @ResponseBody Result updateFileSizeAndInfo(TryOnFeedBackDto dto) {
		return service.updateFileSizeAndInfo(dto);
		
	}*/

}
