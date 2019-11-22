package com.kongque.controller.basics;

import com.kongque.entity.basics.MeasureCategory;
import com.kongque.service.basics.IMeasureCategoryService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/measureCategory")
public class MeasureCategoryController {

    @Resource
    private IMeasureCategoryService server;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Pagination<MeasureCategory> list(MeasureCategory dto, PageBean pageBean){
        return server.list(dto,pageBean);
    }

    @RequestMapping(value = "/saveOrUpdate",method = RequestMethod.POST)
    public Result saveOrUpdate(@RequestBody MeasureCategory dto){
        return server.saveOrUpdate(dto);
    }

    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Result delete(String id){
        return server.delete(id);
    }
}
/**
 * @program: xingyu-order
 * @description: 量体分类
 * @author: zhuxl
 * @create: 2019-07-05 08:36
 **/
