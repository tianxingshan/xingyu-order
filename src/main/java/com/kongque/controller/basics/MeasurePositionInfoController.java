package com.kongque.controller.basics;

import com.kongque.entity.basics.MeasurePositionInfo;
import com.kongque.service.basics.IMeasurePositionInfoService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: xingyu-order
 * @description: 量体部位库
 * @author: zhuxl
 * @create: 2019-05-31 10:34
 **/
@RestController
@RequestMapping("/measurePositionInfo")
public class MeasurePositionInfoController {

    @Resource
    private IMeasurePositionInfoService service;

    /**
     * 列表
     * @param dto
     * @param pageBean
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Pagination<MeasurePositionInfo> list(MeasurePositionInfo dto, PageBean pageBean){
       return service.list(dto,pageBean);
    }

    /**
     * 保存
     * @param dto
     * @return
     */
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    public Result saveOrUpdate(@RequestBody MeasurePositionInfo dto) {
        return service.saveOrUpdate(dto);
    }

    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Result delete(String id){
        return service.delete(id);
    }

}

