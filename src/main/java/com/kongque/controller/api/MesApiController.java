package com.kongque.controller.api;

import com.kongque.model.ARTModel;
import com.kongque.service.api.IMesApiService;
import com.kongque.util.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/mesApi")
public class MesApiController {
    @Resource
    private IMesApiService service;

    /**
     * 查询员工信息
     * @param cardNo
     * @return
     */
    @RequestMapping(value = "/getStaff/byCardNo",method = RequestMethod.GET)
    public ARTModel getStaffByCardNo(String cardNo){
        return service.getStaffByCardNo(cardNo);
    }

    /**
     * 添加工序
     * @param model
     * @return
     */
    @RequestMapping(value = "/insertProcess",method = RequestMethod.GET)
    public Result insertProcess(ARTModel model){
        return service.insertProcess(model);
    }

    /**
     * 删除工序
     * @param model
     * @return
     */
    @RequestMapping(value = "/deleteProcess",method = RequestMethod.GET)
    public Result deleteProcess(ARTModel model){
        return service.deleteProcess(model);
    }

    /**
     * 扫描物料卡
     * @param model
     * @return
     */
    @RequestMapping(value = "/scan",method = RequestMethod.GET)
    public Result scan(ARTModel model){
        return service.scan(model);
    }


}
/**
 * @program: xingyu-order
 * @description: 用于MES接口调用
 * @author: zhuxl
 * @create: 2019-08-07 09:12
 **/
