package com.kongque.controller.basics;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kongque.dto.CategoryDto;
import com.kongque.entity.basics.Category;
import com.kongque.service.basics.ICategoryService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

@RestController
@RequestMapping("/category")
public class CategoryController {
	private static Logger logger = LoggerFactory.getLogger(CategoryController.class);
	@Resource
	private ICategoryService service;
	
	/**
	 * 根据条件分页查询品类列表
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public Pagination<Category> list(CategoryDto dto, PageBean pageBean){
		return service.list(dto,pageBean);
	}
	
	/**
	 * 保存或更新品类信息
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public Result saveOrUpdate(@RequestBody CategoryDto dto) {
		logger.info(dto.toString());
		return service.saveOrUpdate(dto);
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
    @RequestMapping(value ="/delete", method = RequestMethod.POST)
    public Result delete(String id){
        return service.delete(id);
    }
	
}
