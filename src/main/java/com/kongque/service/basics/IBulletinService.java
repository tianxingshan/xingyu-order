package com.kongque.service.basics;

import java.util.List;

import com.kongque.entity.basics.Area;
import com.kongque.entity.basics.Bulletin;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

public interface IBulletinService {
	public Pagination<Bulletin> list(Bulletin dto, PageBean pageBean);// 根据条件分页查询公告

	public Result saveOrUpdate(Bulletin dto);// 添加或修改公告

	public Result delete(String id);// 删除公告

	public Result byId(String id);// 根据id查询某一公告

	public Result modifyCheckStatus(String bulletinId, String userId);//给制定用户绑定制定公告，并设为已读
	public Pagination<Bulletin> queryForUser(String userId,String status, PageBean pageBean);//根据用户id,和状态，查询公告
	public Long queryTotalForUser(String userId, String status);//根据用户id,和状态，查询公告条数
}
