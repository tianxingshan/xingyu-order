package com.kongque.controller.production.basics.process;

import com.kongque.dto.MesProcessLibraryDto;
import com.kongque.dto.MesStyleTechnologyProcessDto;
import com.kongque.entity.process.MesProcessLibrary;
import com.kongque.service.production.basics.process.IMesProcessLibraryService;
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
@RequestMapping("/mesProcessLibrary")
public class MesProcessLibraryController {

    @Resource
    private IMesProcessLibraryService service;

    @RequestMapping(value = "/saveOrUpdate",method = RequestMethod.POST)
    public Result saveOrUpdate(@RequestBody MesProcessLibraryDto dto){
        return service.saveOrUpdate(dto);
    }
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Result delete(String id){
        return service.delete(id);
    }
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Pagination<MesProcessLibrary> list(MesProcessLibraryDto dto, PageBean pageBean){
        return service.list(dto, pageBean);
    }

    @RequestMapping(value = "/makeCard",method = RequestMethod.POST)
    public Result makeCard(@RequestBody MesProcessLibraryDto dto){
        return service.makeCard(dto);
    }

    @RequestMapping(value = "/findNotSetListByCategoryId",method = RequestMethod.GET)
    public Result findNotSetListByCategoryId(String categoryId){
          return service.findNotSetListByCategoryId(categoryId);
    }

    @RequestMapping(value = "/findNotSetListByWorkstation",method = RequestMethod.GET)
    public Result findNotSetListByWorkstation(MesProcessLibraryDto dto){
        return service.findNotSetListByWorkstation(dto);
    }


}
/**
 * @program: xingyu-order
 * @description: 工序库
 * @author: zhuxl
 * @create: 2019-07-15 17:00
 **/
