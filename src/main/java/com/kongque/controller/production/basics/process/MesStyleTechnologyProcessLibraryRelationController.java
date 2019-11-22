package com.kongque.controller.production.basics.process;

import com.kongque.dto.MesStyleTechnologyProcessLibraryRelationDto;
import com.kongque.entity.process.MesStyleTechnologyLibraryRelation;
import com.kongque.entity.process.MesStyleTechnologyProcessLibraryRelation;
import com.kongque.service.production.basics.process.IMesStyleTechnologyLibraryRelationService;
import com.kongque.service.production.basics.process.IMesStyleTechnologyProcessLibraryRelationService;
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
 * @description: 款式工艺工序库关系
 * @author: zhuxl
 * @create: 2019-07-16 10:36
 **/
@RestController
@RequestMapping("/mesStyleTechnologyProcessLibraryRelation")
public class MesStyleTechnologyProcessLibraryRelationController {

    @Resource
    private IMesStyleTechnologyProcessLibraryRelationService service;

    /**
     * 批量保存
     * @param list
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public Result save(@RequestBody List<MesStyleTechnologyProcessLibraryRelation> list){
        return service.save(list);
    }

    /**
     * 修改
     * @param dto
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result update(@RequestBody MesStyleTechnologyProcessLibraryRelation dto){
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
    public Pagination<MesStyleTechnologyProcessLibraryRelation> list(MesStyleTechnologyProcessLibraryRelationDto dto, PageBean pageBean){
        return service.list(dto,pageBean);
    }
}

