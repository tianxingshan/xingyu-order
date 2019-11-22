package com.kongque.controller.production.basics.process;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kongque.dto.ProductionBasicDto;
import com.kongque.entity.process.MesProcessMachine;
import com.kongque.service.production.basics.process.IProcessMachineService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

@RestController
@RequestMapping(value = "/processMachine")
public class ProcessMachineController {
	@Resource
	private IProcessMachineService service;
	
	/**
	 * 根据条件分页查询设备列表
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public Pagination<MesProcessMachine> list(MesProcessMachine machine, PageBean pageBean){
		return service.list(machine,pageBean);
	}
	
	/**
	 * 保存或更新设备信息
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public Result saveOrUpdate(@RequestBody ProductionBasicDto dto) {
		return service.saveOrUpdate(dto);
	}
	
	
	/**
	 * 删除设备信息
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public Result del(String id) {
		return service.del(id);
	}

}
