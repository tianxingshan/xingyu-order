package com.kongque.service.basics.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.kongque.entity.user.User;
import com.kongque.entity.user.UserDeptRelation;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.kongque.constants.Constants;
import com.kongque.dao.IDaoService;
import com.kongque.entity.basics.Bulletin;
import com.kongque.entity.basics.BulletinUser;
import com.kongque.service.basics.IBulletinService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;
import com.kongque.util.StringUtils;

@Service
public class BulletinServiceImpl implements IBulletinService {
	@Resource
	IDaoService dao;

	// 公告查詢
	@Override
	public Pagination<Bulletin> list(Bulletin dto, PageBean pageBean) {
		Criteria criteria = dao.createCriteria(Bulletin.class);
		Pagination<Bulletin> pagination = new Pagination<Bulletin>();
		if (pageBean.getPage() == null) {
			pageBean.setPage(0);
		}
		if (pageBean.getRows() == null) {
			pageBean.setRows(9999);
		}
		if (StringUtils.isNotBlank(dto.getId())) {
			criteria.add(Restrictions.eq("id", dto.getId()));
		}
		if (StringUtils.isNotBlank(dto.getTitle())) {
			criteria.add(Restrictions.like("title", dto.getTitle(), MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotBlank(dto.getStatus())) {
			criteria.add(Restrictions.eq("status", dto.getStatus()));
		}
		if (StringUtils.isNotBlank(dto.getDeleteFlag())) {
			criteria.add(Restrictions.eq("deleteFlag", dto.getDeleteFlag()));
		} else {
			criteria.add(Restrictions.ne("deleteFlag", "1"));
		}

		if (StringUtils.isNotBlank(dto.getReleaser())) {
			criteria.add(Restrictions.eq("releaser", dto.getReleaser()));
		}
		pagination.setRows(dao.findListWithPagebeanCriteria(criteria, pageBean));
		pagination.setTotal(dao.findTotalWithCriteria(criteria));
		return pagination;
	}

	// 新增或修改公告
	@Override
	public Result saveOrUpdate(Bulletin dto) {

		/**
		 * 通过用户名给商户Id赋值
		 */
		if(StringUtils.isNotBlank(dto.getReleaser())){
			User user = dao.findUniqueByProperty(User.class,"userName",dto.getReleaser());
			UserDeptRelation userDeptRelation = dao.findUniqueByProperty(UserDeptRelation.class,"userId",user.getId());
			dto.setTenantId(userDeptRelation.getDept().getTenantId());
		}

		if (StringUtils.isBlank(dto.getId())) {

			Bulletin bulletin = new Bulletin();
			BeanUtils.copyProperties(dto, bulletin);
			if (StringUtils.isNotBlank(dto.getStatus())) {
				if ("1".equals(dto.getStatus())) {
					bulletin.setReleaseTime(new Date());
				}
			}
			dao.save(bulletin);
			return new Result(bulletin);

		} else {
			Bulletin bulletin = dao.findById(Bulletin.class, dto.getId());
			if (bulletin == null) {
				return new Result(Constants.RESULT_CODE.SYS_ERROR, "没有此公告！");
			}
			BeanUtils.copyProperties(dto, bulletin);
			dao.update(bulletin);
			return new Result(bulletin);
		}
	}

	// 刪除公告
	@Override
	public Result delete(String id) {

		Bulletin bulletin = dao.findById(Bulletin.class, id);
		if (bulletin != null) {
			bulletin.setDeleteFlag("1");
			dao.update(bulletin);
			List<BulletinUser> userBulletins = dao.findListByProperty(BulletinUser.class, "bulletin.id",
					bulletin.getId());
			if (userBulletins != null) {
				for (BulletinUser bulletinUser : userBulletins) {
					dao.delete(bulletinUser);
				}
			}

			return new Result(bulletin);

		} else {
			return new Result(Constants.RESULT_CODE.SYS_ERROR, "没有此公告！");
		}
	}

	// 根据id查询公告
	@Override
	public Result byId(String id) {
		Bulletin bulletin = dao.findById(Bulletin.class, id);
		if (bulletin != null) {
			return new Result(bulletin);
		} else {
			return new Result(Constants.RESULT_CODE.SYS_ERROR, "没有此公告！");
		}
	}

	// 给制定用户绑定制指定公告并设置状态已读
	@Override
	public Result modifyCheckStatus(String bulletinId, String userId) {
		Bulletin bulletin = new Bulletin();
		bulletin.setId(bulletinId);
		BulletinUser bulletinUser = new BulletinUser();
		bulletinUser.setUserId(userId);
		bulletinUser.setBulletin(bulletin);
		bulletinUser.setStatus("已读");
		bulletinUser.setVisible("1");
		dao.save(bulletinUser);
		return new Result();

	}

	/**
	 * 为参数userId指定的用户查询对该用户来说，具有status参数指定的状态的公告信息记录：
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Pagination<Bulletin> queryForUser(String userId, String status, PageBean pageBean) {
		Pagination<Bulletin> pagination = new Pagination<>();
		List<Bulletin> bulletins = null;
		if (status != null && status.equals("已读")) {
			Criteria criteria = dao.createCriteria(BulletinUser.class);
			// 为bulletin属性创建别名bulletin，
			// 以便按照bulletin属性对象的releaseTime属性进行排序
			criteria.createAlias("bulletin", "bulletin");
			criteria.add(Restrictions.eq("userId", userId));
			criteria.add(Restrictions.eq("visible", "1"));
			criteria.addOrder(Order.desc("bulletin.releaseTime"));

			List<BulletinUser> userBulletins = dao.findListWithPagebeanCriteria(criteria, pageBean);
			Long total = dao.findTotalWithCriteria(criteria);
			bulletins = new ArrayList<>();
			if (userBulletins != null) {
				for (BulletinUser userBulletin : userBulletins) {
					bulletins.add(userBulletin.getBulletin());
				}
			}
			pagination.setRows(bulletins);
			pagination.setTotal(total);
		} else if (status != null && status.equals("未读")) {
			Criteria criteria = dao.createCriteria(BulletinUser.class);
			// 为bulletin属性创建别名bulletin，
			// 以便按照bulletin属性对象的releaseTime属性进行排序
			criteria.createAlias("bulletin", "bulletin");
			criteria.add(Restrictions.eq("userId", userId));
			criteria.add(Restrictions.eq("visible", "1"));
			criteria.setProjection(Property.forName("bulletin.id"));// 为查询结果投影，只保留关联公告信息的ID数据字段
			List<String> bulletinIds = criteria.list();
			criteria = dao.createCriteria(Bulletin.class);
			criteria.add(Restrictions.eq("deleteFlag", "0"));
			if (bulletinIds != null && !bulletinIds.isEmpty()) {
				criteria.add(Restrictions.not(Restrictions.in("id", bulletinIds)));
				criteria.addOrder(Order.desc("releaseTime"));
			}

			bulletins = dao.findListWithPagebeanCriteria(criteria, pageBean);
			Long total = dao.findTotalWithCriteria(criteria);
			pagination.setRows(bulletins);
			pagination.setTotal(total);
		} else {
			Criteria criteria = dao.createCriteria(Bulletin.class);
			criteria.add(Restrictions.eq("deleteFlag", "0"));
			criteria.addOrder(Order.desc("releaseTime"));
			bulletins = dao.findListWithPagebeanCriteria(criteria, pageBean);
			Long total = dao.findTotalWithCriteria(criteria);
			pagination.setRows(bulletins);
			pagination.setTotal(total);
		}
		return pagination;
	}

	/**
	 * 为参数userId指定的用户查询对该用户来说，具有status参数指定的状态的公告信息记录条数：
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Long queryTotalForUser(String userId, String status) {
		Long total = -1L;
		if (status != null && status.equals("已读")) {
			Criteria criteria = dao.createCriteria(BulletinUser.class);
			criteria.createAlias("bulletin", "bulletin");
			criteria.add(Restrictions.eq("userId", userId));
			criteria.add(Restrictions.eq("visible", "1"));
			criteria.addOrder(Order.desc("bulletin.releaseTime"));
			total = dao.findTotalWithCriteria(criteria);
		} else if (status != null && status.equals("未读")) {
			Criteria criteria = dao.createCriteria(BulletinUser.class);
			criteria.createAlias("bulletin", "bulletin");
			criteria.add(Restrictions.eq("userId", userId));
			criteria.add(Restrictions.eq("visible", "1"));
			criteria.addOrder(Order.desc("bulletin.releaseTime"));
			criteria.setProjection(Property.forName("bulletin.id"));
			List<String> bulletinIds = criteria.list();
			criteria = dao.createCriteria(Bulletin.class);
			criteria.add(Restrictions.eq("deleteFlag", "0"));
			if (bulletinIds != null && !bulletinIds.isEmpty()) {
				criteria.add(Restrictions.not(Restrictions.in("id", bulletinIds)));
			}
			total = dao.findTotalWithCriteria(criteria);
		} else {
			Criteria criteria = dao.createCriteria(Bulletin.class);
			criteria.add(Restrictions.eq("deleteFlag", "0"));
			total = dao.findTotalWithCriteria(criteria);
		}
		return total;
	}

}
