package com.kongque.controller.production.basics.process;

import com.kongque.entity.process.MesProcessCategory;
import com.kongque.service.production.basics.process.IMesProcessCategoryService;
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
@RequestMapping("/mesProcessCategory")
public class MesProcessCategoryController {

    @Resource
    private IMesProcessCategoryService service;

    @RequestMapping(value = "/saveOrUpdate",method = RequestMethod.POST)
    public Result saveOrUpdate(@RequestBody MesProcessCategory dto){
        return service.saveOrUpdate(dto);
    }

    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Result delete(String id){
        return service.delete(id);
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Pagination<MesProcessCategory> list(MesProcessCategory dto, PageBean pageBean){
        return service.list(dto,pageBean);
    }
}
/**
 * @program: xingyu-order
 * @description: 工序类别
 * @author: zhuxl
 * @create: 2019-07-15 15:36
 **/
