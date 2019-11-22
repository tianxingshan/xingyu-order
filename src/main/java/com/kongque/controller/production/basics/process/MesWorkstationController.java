package com.kongque.controller.production.basics.process;

import com.kongque.dto.MesProcessLibraryDto;
import com.kongque.entity.process.MesProcessLibrary;
import com.kongque.entity.process.MesWorkstation;
import com.kongque.service.production.basics.process.IMesProcessLibraryService;
import com.kongque.service.production.basics.process.IMesWorkstationService;
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
@RequestMapping("/mesWorkstation")
public class MesWorkstationController {

    @Resource
    private IMesWorkstationService service;

    @RequestMapping(value = "/saveOrUpdate",method = RequestMethod.POST)
    public Result saveOrUpdate(@RequestBody MesWorkstation dto){
        return service.saveOrUpdate(dto);
    }
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Result delete(String id){
        return service.delete(id);
    }
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Pagination<MesWorkstation> list(MesWorkstation dto, PageBean pageBean){
        return service.list(dto, pageBean);
    }

}
/**
 * @program: xingyu-order
 * @description: 车位
 * @author: zhuxl
 * @create: 2019-07-15 17:00
 **/
