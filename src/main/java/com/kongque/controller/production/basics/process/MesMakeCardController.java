package com.kongque.controller.production.basics.process;

import com.kongque.dto.MesMakeCardDto;
import com.kongque.entity.process.MesMakeCard;
import com.kongque.model.MesMakeCardModel;
import com.kongque.service.production.basics.process.IMesMakeCardService;
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
 * @description: 制卡
 * @author: zhuxl
 * @create: 2019-07-26 11:40
 **/
@RestController
@RequestMapping("/mesMakeCard")
public class MesMakeCardController {
    @Resource
    private IMesMakeCardService service;

    /**
     * 查询
     * @param dto
     * @param pageBean
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Pagination<MesMakeCardModel> list(MesMakeCardDto dto, PageBean pageBean){
        return service.list(dto, pageBean);
    }

    /**
     * 制卡
     */
    @RequestMapping(value = "/make",method = RequestMethod.POST)
    public Result make(@RequestBody MesMakeCard dto){
        return service.make(dto);
    }

    /**
     * 取消制卡
     * @param dto
     * @return
     */
    @RequestMapping(value = "/clear",method = RequestMethod.POST)
    public Result clear(@RequestBody MesMakeCard dto){
        return service.clear(dto);
    }

}

