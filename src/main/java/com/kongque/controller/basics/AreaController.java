package com.kongque.controller.basics;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.kongque.dto.AreaDto;
import com.kongque.entity.basics.Area;
import com.kongque.service.basics.IAreaService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

@Controller
@RequestMapping("/area")
public class AreaController {
	private static Logger logger = LoggerFactory.getLogger(AreaController.class);
	@Resource
	private IAreaService service;
	/**
	 * 根据id查询地区
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/areaId", method = RequestMethod.GET)
	@ResponseBody
	public Result areaById(String id) {
		return service.areaById(id);
	}
	/**
	 * 根据条件分页查询地区列表
	 * 
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Pagination<Area> list(AreaDto dto, PageBean pageBean) {
		return service.areaList(dto, pageBean);
	}

	/**
	 * 保存或更新地区信息
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Result saveOrUpdate(@RequestBody AreaDto dto) {
		logger.info(dto.toString());
		return service.saveOrUpdate(dto);
	}

	/**
	 * 刪除地区信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	public Result delete(String id) {
		return service.delete(id);
	}
}
