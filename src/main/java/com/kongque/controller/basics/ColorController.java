package com.kongque.controller.basics;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kongque.dto.ColorDto;
import com.kongque.entity.basics.Color;
import com.kongque.service.basics.IColorService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

@RestController
@RequestMapping("/color")
public class ColorController {
	private static Logger logger = LoggerFactory.getLogger(ColorController.class);
	@Resource
	private IColorService service;
	
	/**
	 * 根据条件分页查询颜色列表
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public Pagination<Color> colorList(ColorDto dto, PageBean pageBean){
		return service.colorList(dto,pageBean);
	}
	
	/**
	 * 保存或更新颜色信息
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public Result saveOrUpdate(ColorDto dto,MultipartFile[] files) {
		logger.info(dto.toString());
		return service.saveOrUpdate(dto,files);
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
    @RequestMapping(value ="/delete", method = RequestMethod.DELETE)
    public Result delete(String id){
        return service.delete(id);
    }
	
}
