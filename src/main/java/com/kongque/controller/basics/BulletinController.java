package com.kongque.controller.basics;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kongque.entity.basics.Bulletin;
import com.kongque.service.basics.IBulletinService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

@Controller
@RequestMapping("/bulletin")
public class BulletinController {
	private static Logger logger = LoggerFactory.getLogger(BulletinController.class);
	@Resource
	private IBulletinService service;

	/**
	 * 根据id查询公告
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/bulletinById", method = RequestMethod.GET)
	@ResponseBody
	public Result bulletinById(String id) {
		return service.byId(id);
	}

	/**
	 * 根据条件分页查询公告列表
	 * 
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Pagination<Bulletin> list(Bulletin dto, PageBean pageBean) {
		return service.list(dto, pageBean);
	}

	/**
	 * z 保存或更新公告
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Result saveOrUpdate(@RequestBody Bulletin dto) {
		logger.info(dto.toString());
		return service.saveOrUpdate(dto);
	}

	/**
	 * 刪除公告
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	public Result delete(String id) {
		return service.delete(id);
	}

	@RequestMapping(value = "/modifyCheckStatus", method = RequestMethod.GET)
	@ResponseBody
	public Result modifyCheckStatus(String bulletinId, String userId) {
		// 给制定用户绑定制定公告，并设为已读
		return service.modifyCheckStatus(bulletinId, userId);
	}

	@RequestMapping(value = "/queryForUser", method = RequestMethod.GET)
	@ResponseBody
	public Pagination<Bulletin> queryForUser(String userId, String status, PageBean pageBean) {
		// 根据用户id,和状态，查询公告
		return service.queryForUser(userId, status, pageBean);
	}

	@RequestMapping(value = "/queryTotalForUser", method = RequestMethod.GET)
	@ResponseBody
	public Long queryTotalForUser(String userId, String status) {
		// 根据用户id,和状态，查询公告条数
		return service.queryTotalForUser(userId, status);
	}

}
