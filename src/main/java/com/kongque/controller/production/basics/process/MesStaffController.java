package com.kongque.controller.production.basics.process;

import com.kongque.entity.process.MesStaff;
import com.kongque.service.production.basics.process.IMesStaffService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: xingyu-order
 * @description: 员工
 * @author: zhuxl
 * @create: 2019-07-15 17:00
 **/
@RestController
@RequestMapping("/mesStaff")
public class MesStaffController {

    @Resource
    private IMesStaffService service;

    /**
     * 增改
     * @param dto
     * @return
     */
    @RequestMapping(value = "/saveOrUpdate",method = RequestMethod.POST)
    public Result saveOrUpdate(@RequestBody MesStaff dto){
        return service.saveOrUpdate(dto);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Result delete(String id){
        return service.delete(id);
    }

    /**
     * 查询列表
     * @param dto
     * @param pageBean
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Pagination<MesStaff> list(MesStaff dto, PageBean pageBean){
        return service.list(dto, pageBean);
    }

    /**
     * 制卡
     * @param dto
     * @return
     */
    @RequestMapping(value = "/makeCard",method = RequestMethod.POST)
    public Result makeCard(@RequestBody MesStaff dto){
        return service.makeCard(dto);
    }

}

