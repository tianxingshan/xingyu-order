package com.kongque.controller.production.basics.measure;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kongque.dto.ProductionBasicDto;
import com.kongque.entity.measure.MeasurePosition;
import com.kongque.service.production.basics.measure.IMeasurePositionService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

@RestController
@RequestMapping(value = "/measurePosition")
public class MeasurePositionController {
	
	@Resource
	private IMeasurePositionService service;
	
	/**
	 * 根据条件分页查询量体部位列表
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public Pagination<MeasurePosition> list(MeasurePosition position, PageBean pageBean){
		return service.list(position,pageBean);
	}
	
	/**
	 * 保存或更新量体部位信息
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public Result saveOrUpdate(@RequestBody ProductionBasicDto dto) {
		return service.saveOrUpdate(dto);
	}
	
	
	/**
	 * 删除量体部位信息
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public Result delPosition(String id) {
		return service.delPosition(id);
	}
}
