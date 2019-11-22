package com.kongque.controller.production.basics.process;

import com.kongque.entity.process.MesWorkshopSection;
import com.kongque.service.production.basics.process.IMesWorkshopSectionService;
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
@RequestMapping("/mesWorkshopSection")
public class MesWorkshopSectionController {

    @Resource
    private IMesWorkshopSectionService service;

    @RequestMapping(value = "/saveOrUpdate",method = RequestMethod.POST)
    public Result saveOrUpdate(@RequestBody MesWorkshopSection dto){
        return service.saveOrUpdate(dto);
    }

    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Result delete(String id){
        return service.delete(id);
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Pagination<MesWorkshopSection> list(MesWorkshopSection dto, PageBean pageBean){
        return service.list(dto,pageBean);
    }
}
/**
 * @program: xingyu-order
 * @description: 工段
 * @author: zhuxl
 * @create: 2019-07-15 15:36
 **/
