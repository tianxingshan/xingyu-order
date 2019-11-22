package com.kongque.controller.production.basics.process;

import com.kongque.dto.MesStyleTechnologyProcessDto;
import com.kongque.entity.process.MesCategoryProcessLibraryRelation;
import com.kongque.service.production.basics.process.IMesCategoryProcessLibraryRelationService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: xingyu-order
 * @description: 类别工序库关系
 * @author: zhuxl
 * @create: 2019-07-16 10:36
 **/
@RestController
@RequestMapping("/mesCategoryProcessLibraryRelation")
public class MesCategoryProcessLibraryRelationController {

    @Resource
    private IMesCategoryProcessLibraryRelationService service;

    /**
     * 批量保存
     * @param list
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public Result save(@RequestBody List<MesCategoryProcessLibraryRelation> list){
        return service.save(list);
    }

    /**
     * 修改
     * @param dto
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result update(@RequestBody MesCategoryProcessLibraryRelation dto){
        return service.update(dto);
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
    public Pagination<MesCategoryProcessLibraryRelation> list(MesCategoryProcessLibraryRelation dto, PageBean pageBean){
        return service.list(dto,pageBean);
    }

    @RequestMapping(value = "/findNotSetListByStyleTechnology",method = RequestMethod.GET)
    public Result findNotSetListByStyleTechnology(MesStyleTechnologyProcessDto dto){
        return service.findNotSetListByStyleTechnology(dto);
    }
}

