package com.kongque.controller.basics;

import javax.annotation.Resource;

import com.kongque.entity.basics.OrderCharacterTenantRelation;
import com.kongque.model.TenantModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.kongque.dto.OrderCharacterDto;
import com.kongque.entity.basics.OrderCharacter;
import com.kongque.service.basics.IOrderCharacterService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

@Controller
@RequestMapping("/orderCharacter")
public class OrderCharacterController {
	private static Logger logger = LoggerFactory.getLogger(OrderCharacterController.class);
	@Resource
	private IOrderCharacterService service;

	/**
	 * 根据条件分页查询地区列表
	 * 
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Pagination<OrderCharacter> list(OrderCharacterDto dto, PageBean pageBean) {
		return service.orderCharacterList(dto, pageBean);
	}

	
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Result saveOrUpdate(@RequestBody OrderCharacterDto dto) {
		logger.info(dto.toString());
		return service.saveOrUpdate(dto);
	}

	
	@RequestMapping(value = "/updateStatus", method = RequestMethod.GET)
	@ResponseBody
	public Result delete(String orderCharacterId, String orderCharacterStatus) {
		return service.updateStatus(orderCharacterId,orderCharacterStatus);
	}

	/**
	 * 按照商户分类显示订单性质
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/listByTenant", method = RequestMethod.GET)
	@ResponseBody
	public Result listByTenant(OrderCharacterDto dto) {
		return service.listByTenant(dto);
	}

	/**
	 * 通过订单性质id保存商户id
	 * @return
	 */
	@RequestMapping(value ="/saveTenantList/byOrderCharacterId", method = RequestMethod.POST)
	@ResponseBody
	public Result saveTenantListbyOrderCharacterId(@RequestBody OrderCharacterDto dto){
          return service.saveTenantListbyOrderCharacterId(dto);
	}

	/**
	 * 通过id更新订单性质跟商户的对照关系状态
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping(value ="/orderCharacterTenantRelation/updateStatus", method = RequestMethod.GET)
	@ResponseBody
	public Result orderCharacterTenantRelationUpdateStatus(String id,String status){
          return service.orderCharacterTenantRelationUpdateStatus(id,status);
	}

	/**
	 * 查询订单性质跟商户对照关系列表
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value = "/orderCharacterTenantRelation/list", method = RequestMethod.GET)
	@ResponseBody
	public Pagination<OrderCharacterTenantRelation> orderCharacterTenantRelationlList(OrderCharacterDto dto, PageBean pageBean) {
		return service.orderCharacterTenantRelationlList(dto, pageBean);
	}

	/**
	 * 通过Id删除对照关系
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/orderCharacterTenantRelation/deleteById", method = RequestMethod.GET)
	@ResponseBody
	public Result orderCharacterTenantRelationlDeleteById(String id) {
		return service.orderCharacterTenantRelationlDeleteById(id);
	}

}
