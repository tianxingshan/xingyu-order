package com.kongque.controller.production.basics.measure;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kongque.dto.ProductionBasicDto;
import com.kongque.entity.measure.MeasureSize;
import com.kongque.service.production.basics.measure.IMeasureSizeService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;


@RestController
@RequestMapping(value = "/measureSize")
public class MeasureSizeController {
	
	@Resource
	private IMeasureSizeService service;

	/**
	 * 根据条件分页查询尺码列表
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public Pagination<MeasureSize> list(MeasureSize size, PageBean pageBean){
		return service.list(size,pageBean);
	}
	
	/**
	 * 保存或更新尺码信息
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public Result saveOrUpdate(@RequestBody ProductionBasicDto dto) {
		return service.saveOrUpdate(dto);
	}
	
	
	/**
	 * 删除尺码信息
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public Result delSize(String id) {
		return service.delSize(id);
	}
}
