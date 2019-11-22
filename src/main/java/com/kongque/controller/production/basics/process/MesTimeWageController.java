package com.kongque.controller.production.basics.process;

import com.kongque.entity.process.MesTimeWage;
import com.kongque.service.production.basics.process.IMesTimeWageService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/mesTimeWage")
public class MesTimeWageController {

    @Resource
    private IMesTimeWageService service;

    @RequestMapping("/save")
    public Result save(@RequestBody MesTimeWage dto)  {
        return service.save(dto);
    }
    @RequestMapping("/list")
    public Pagination<MesTimeWage> list(MesTimeWage dto, PageBean pageBean)  {
        return service.list(dto, pageBean);
    }
}
/**
 * @program: xingyu-order
 * @description: 计时工资
 * @author: zhuxl
 * @create: 2019-08-27 09:47
 **/
