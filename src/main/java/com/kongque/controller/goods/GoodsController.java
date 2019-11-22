package com.kongque.controller.goods;

import javax.annotation.Resource;

import com.kongque.entity.goods.GoodsDetailSelect;
import com.kongque.entity.goods.GoodsDetailTanant;
import com.kongque.model.GoodsDetailTanantModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kongque.dto.GoodsDto;
import com.kongque.entity.goods.Goods;
import com.kongque.service.goods.IGoodsService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {
	private static Logger logger = LoggerFactory.getLogger(GoodsController.class);
	@Resource
	private IGoodsService service;
	
	/**
	 * 根据条件分页查询商品列表
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public Pagination<Goods> list(GoodsDto dto, PageBean pageBean){
		return service.list(dto,pageBean);
	}

	/**
	 * 根据条件分页查询款式明细列表
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value="/detail/list",method=RequestMethod.GET)
	public Pagination<GoodsDetailSelect> listDetail(GoodsDto dto, PageBean pageBean){
		return service.listDetail(dto,pageBean);
	}
	
	/**
	 * 保存或更新商品信息
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public Result saveOrUpdate(@RequestBody GoodsDto dto) {
		return service.saveOrUpdate(dto);
	}
	
	/**
	 * 启用/禁用商品
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateStatus", method = RequestMethod.GET)
	public Result updateStatus(String id,String status){
		return service.updateStatus(id,status);
	}
	
	
	/**
	 * 上传商品明细图片
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/upload/file", method = RequestMethod.POST)
	public Result uploadFile(MultipartFile[] files,String[] imageDelKeys) {
		return service.uploadFile(files,imageDelKeys);
	}
	
	/**
	 * 获取订单附件文件流
	 * 
	 * @param path
	 */
	@RequestMapping(value = "/file", method = RequestMethod.GET)
	public @ResponseBody void getAttachement(String path) {
		service.getAttachment(path);
	}


	/**
	 * 获取所有商户通过商品明细Id
	 * @param goodsDetailId
	 * @return
	 */
	@RequestMapping(value = "/getAllTenantsByGoodsDetailId", method = RequestMethod.GET)
	public Result getAllTenantsByGoodsDetailId(String goodsDetailId){
		return service.getAllTenantsByGoodsDetailId(goodsDetailId);
	}

	/**
	 * 保存商品明细与商户对照关系
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/goodsDetailTenant/save", method = RequestMethod.POST)
	public Result saveGoodsDetailTenant(@RequestBody List<GoodsDetailTanant> dto) {
		return service.saveGoodsDetailTenant(dto);
	}

	/**
	 * 删除商品明细
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/goodsDetail/delete", method = RequestMethod.GET)
	public Result deleteGoodsDetail(String id) {
		return service.deleteGoodsDetail(id);
	}
	
}
