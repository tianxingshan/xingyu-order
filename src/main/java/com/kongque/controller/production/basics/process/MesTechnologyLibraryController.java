package com.kongque.controller.production.basics.process;

import com.kongque.dto.MesStyleTechnologyLibraryRelationDto;
import com.kongque.entity.process.MesProcessCategory;
import com.kongque.entity.process.MesTechnologyLibrary;
import com.kongque.service.production.basics.process.IMesProcessCategoryService;
import com.kongque.service.production.basics.process.IMesTechnologyLibraryService;
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
@RequestMapping("/mesTechnologyLibrary")
public class MesTechnologyLibraryController {

    @Resource
    private IMesTechnologyLibraryService service;

    @RequestMapping(value = "/saveOrUpdate",method = RequestMethod.POST)
    public Result saveOrUpdate(@RequestBody MesTechnologyLibrary dto){
        return service.saveOrUpdate(dto);
    }

    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Result delete(String id){
        return service.delete(id);
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Pagination<MesTechnologyLibrary> list(MesTechnologyLibrary dto, PageBean pageBean){
        return service.list(dto,pageBean);
    }

    @RequestMapping(value = "/findNotSetListByStyle",method = RequestMethod.GET)
    public Result findNotSetListByStyle(MesStyleTechnologyLibraryRelationDto dto){
        return service.findNotSetListByStyle(dto);
    }
}
/**
 * @program: xingyu-order
 * @description: 工艺库
 * @author: zhuxl
 * @create: 2019-07-17 10:34
 **/
