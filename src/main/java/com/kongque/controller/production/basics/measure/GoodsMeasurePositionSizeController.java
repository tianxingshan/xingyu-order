package com.kongque.controller.production.basics.measure;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kongque.dto.PlanOrderDetailDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kongque.dto.MeasurePositionSizeDto;
import com.kongque.entity.measure.GoodsMeasurePositionSize;
import com.kongque.service.production.basics.measure.IGoodsMeasurePositionSizeService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/measurePositionSize")
public class GoodsMeasurePositionSizeController {
	
	@Resource
	private IGoodsMeasurePositionSizeService service;
	
	/**
	 * 根据品类以及尺码查询量体部位列表
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public Pagination<GoodsMeasurePositionSize> list(MeasurePositionSizeDto dto,PageBean pageBean){
		return service.list(dto,pageBean);
	}
	
	
	/**
	 * 保存或更新款式部位尺码信息
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public Result saveOrUpdate(@RequestBody MeasurePositionSizeDto dto) {
		return service.saveOrUpdate(dto);
	}

	@RequestMapping(value="/notAssignList",method=RequestMethod.GET)
	public Result notAssignList(String goodsId, String measureSizeId, String orderDetailId){
		return service.notAssignList(goodsId,measureSizeId,orderDetailId);
	}

	/**
	 * 查询款式尺码部位尺寸列表
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/searchMeasurePositionSizeList",method=RequestMethod.GET)
	public Result searchMeasurePositionSizeList(MeasurePositionSizeDto dto){
		return service.searchMeasurePositionSizeList(dto);
	}

	/**
	 * 新增或修改款式尺码部位尺寸
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/saveUpdateMeasurePositionSize",method=RequestMethod.POST)
	public Result saveUpdateMeasurePositionSize(@RequestBody MeasurePositionSizeDto dto){
		return service.saveUpdateMeasurePositionSize(dto);
	}

	/**
	 * 新新增或修改款式尺码部位尺寸
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/new/saveUpdateMeasurePositionSize",method=RequestMethod.POST)
	public Result newSaveUpdateMeasurePositionSize(@RequestBody MeasurePositionSizeDto dto){
		return service.newSaveUpdateMeasurePositionSize(dto);
	}

	/**
	 * 删除款式尺码部位尺寸
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/delMeasurePositionSize",method=RequestMethod.POST)
	public Result delMeasurePositionSize(@RequestBody MeasurePositionSizeDto dto){
		return service.delMeasurePositionSize(dto);
	}
	/**
	 * 款式尺码部位尺寸复制
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/copyMeasurePositionSize",method=RequestMethod.POST)
	public Result copyMeasurePositionSize(@RequestBody MeasurePositionSizeDto dto){
		return service.copyMeasurePositionSize(dto);
	}

	/**
	 * 款式尺码部位尺寸批量复制
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/batchCopyMeasurePositionSize",method=RequestMethod.POST)
	public Result batchCopyMeasurePositionSize(@RequestBody MeasurePositionSizeDto dto){
		return service.batchCopyMeasurePositionSize(dto);
	}

	/**
	 * 同款多码分析
	 * @param goodsId
	 * @return
	 */
	@RequestMapping(value="/getGoodsSize",method=RequestMethod.GET)
	public List<Map<String, Object>> getGoodsSize(String goodsId){
		return service.getGoodsSize(goodsId);
	}


	/**
	 * 同款多码样板净尺寸导出
	 * @param goodsId
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/getGoodsNetSizeExportExcel",method=RequestMethod.GET)
	public void getGoodsNetSizeExportExcel(String goodsId, HttpServletRequest request, HttpServletResponse response){
		service.getGoodsNetSizeExportExcel(goodsId,request,response);
	}

	/**
	 * 同款多码样板完成尺寸导出
	 * @param goodsId
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/getGoodsFinishSizeExportExcel",method=RequestMethod.GET)
	public void getGoodsFinishSizeExportExcel(String goodsId, HttpServletRequest request, HttpServletResponse response){
		service.getGoodsFinishSizeExportExcel(goodsId,request,response);
	}


	/**
	 * 商品配码分析
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/getOrderGoodsSize",method=RequestMethod.GET)
	public Pagination<Map<String, Object>> getOrderGoodsSize(PlanOrderDetailDto dto,PageBean pageBean){
		Pagination<Map<String, Object>> pagination = new Pagination<>();
		List<Map<String,Object>> list = new ArrayList<>();
		Map<String,Object> map = new HashMap<>();
		map.put("Actual",service.getOrderActualGoodsSize(dto,pageBean).getRows());
		Pagination<Map<String, Object>> standard = service.getOrderStandardGoodsSize(dto,pageBean);
		map.put("Standard",standard.getRows());
		list.add(map);
		pagination.setRows(list);
		pagination.setTotal(standard.getTotal());
		return pagination;
	}



	/**
	 * 商品配码基本码尺寸导出
	 * @param dto
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/getOrderStandardGoodsSizeExportExcel",method=RequestMethod.GET)
	public void getOrderStandardGoodsSizeExportExcel(PlanOrderDetailDto dto, HttpServletRequest request, HttpServletResponse response){
		service.getOrderStandardGoodsSizeExportExcel(dto,request,response);
	}




	/**
	 * 商品配码实际尺寸导出
	 * @param dto
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/getOrderActualGoodsSizeExportExcel",method=RequestMethod.GET)
	public void getOrderActualGoodsSizeExportExcel(PlanOrderDetailDto dto, HttpServletRequest request, HttpServletResponse response){
		service.getOrderActualGoodsSizeExportExcel(dto,request,response);
	}

}
