package com.kongque.controller.production.basics.material;

import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kongque.dto.GoodsPositionMaterialDto;
import com.kongque.entity.material.GoodsPositionMaterial;
import com.kongque.model.GoodsDetailModel;
import com.kongque.service.production.basics.material.IGoodsPositionMaterialService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

@RestController
@RequestMapping(value = "/goodsPositionMaterial")
public class GoodsPositionMaterialController {
	
	@Resource
	private IGoodsPositionMaterialService service;
	
	
	/**
	 * 根据款式id和尺码id获取部位物料
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public Pagination<GoodsPositionMaterial> list(GoodsPositionMaterialDto dto,PageBean pageBean){
		return service.list(dto,pageBean);
	}
	
	/**
	 * 保存或更新部位物料信息
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public Result saveOrUpdate(@RequestBody GoodsPositionMaterialDto dto) {
		return service.saveOrUpdate(dto);
	}
	
	
	/**
	 * 删除物料部位物料
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public Result del(@RequestBody  GoodsPositionMaterialDto dto) {
		return service.del(dto);
	}
	
	
	/**
	 * 获取物料编号
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/getMaterialCode", method = RequestMethod.GET)
	public Result getMaterialCode(String materialCode) {
		return service.getMaterialCode(materialCode);
	}

	/**
	 * 获取物料编号和物料信息
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/getMaterial", method = RequestMethod.GET)
	public Result getMaterial(String materialCode,PageBean pageBean) {
		return service.getMaterial(materialCode,pageBean);
	}
	
	/**
	 * 根据物料编号获取物料信息
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/getMaterialInfomation", method = RequestMethod.GET)
	public Result getMaterialInfomation(String materialCode) {
		return service.getMaterialInfomation(materialCode);
	}


	/**
	 * 查询订单明细列表
	 * @param code
	 * @param categoryId
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value = "/goodsDetail/list", method = RequestMethod.GET)
	@ResponseBody
	public Pagination<GoodsDetailModel> orderDetailList(String code,String categoryId,PageBean pageBean){
		Pagination<GoodsDetailModel> pagination = new Pagination<>();		
		try {
			Long total = service.queryCountWithParam(code,categoryId);
			if(total != null){
				pagination.setTotal(total);
			}
			List<GoodsDetailModel> resultList = service.queryDetailWithParam(code,categoryId,pageBean);
			if(resultList != null){			
				pagination.setRows(resultList);			
			}			
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		return pagination;
	}

	@RequestMapping(value = "/notAssignList", method = RequestMethod.GET)
	@ResponseBody
	public Result notAssignList(String goodsDetailId, String measureSizeId, String orderDetailId){
		return service.notAssignList(goodsDetailId,measureSizeId,orderDetailId);
	}
	
	
	/**
	 * 复制物料信息
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/copy/material", method = RequestMethod.POST)
	public Result copyMaterial(@RequestBody GoodsPositionMaterialDto dto) {
		return service.copyMaterial(dto);
	}

	/**
	 * 批量复制物料信息
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/batch/copy/material", method = RequestMethod.POST)
	public Result batchCopyMaterial(@RequestBody GoodsPositionMaterialDto dto) {
		return service.batchCopyMaterial(dto);
	}

}
