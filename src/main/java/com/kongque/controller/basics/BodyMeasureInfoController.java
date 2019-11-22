package com.kongque.controller.basics;

import com.kongque.entity.basics.BodyMeasureInfo;
import com.kongque.service.basics.IBodyMeasureInfoService;
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
@RequestMapping("/bodyMeasureInfo")
public class BodyMeasureInfoController {

    @Resource
    private IBodyMeasureInfoService service;

    @RequestMapping("/findNotSelectedByCategoryId")
    public Result findNotSelectedByCategoryId(String categoryId){
        return service.findNotSelectedByCategoryId(categoryId);
    }

    /**
     * 保存
     * @param dto
     * @return
     */
    @RequestMapping(value = "/saveOrUpdate",method = RequestMethod.POST )
    public Result saveOrUpdate(@RequestBody BodyMeasureInfo dto){
        return service.saveOrUpdate(dto);
    }

    /**
     * 查询列表
     * @param dto
     * @param pageBean
     * @return
     */
    @RequestMapping("/list")
    public Pagination<BodyMeasureInfo> list(BodyMeasureInfo dto, PageBean pageBean){
        return service.list(dto,pageBean);
    }
}
/**
 * @program: xingyu-order
 * @description: 量体库
 * @author: zhuxl
 * @create: 2019-07-04 15:52
 **/
