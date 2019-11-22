package com.kongque.service.basics.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import com.kongque.constants.Constants;
import com.kongque.dao.IDaoService;
import com.kongque.dto.AreaDto;
import com.kongque.entity.basics.Area;
import com.kongque.service.basics.IAreaService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

@Service("area")
public class AreaServiceImpl implements IAreaService {
	@Resource
	IDaoService dao;

	// 根据id查询某一地区
	@Override
	public Result areaById(String areaId) {
		Area area = dao.findById(Area.class, areaId);
		if (area != null) {
			return new Result(area);
		} else {
			return new Result(Constants.RESULT_CODE.SYS_ERROR, "没有此地区！");
		}

	}

	// 根据条件分页查询地区
	@Override
	public Pagination<Area> areaList(AreaDto dto, PageBean pageBean) {
		if (pageBean.getPage() == null) {
			pageBean.setPage(0);
		}
		if (pageBean.getRows() == null) {
			pageBean.setRows(9999);
		}
		Pagination<Area> pagination = new Pagination<Area>();
		Criteria criteria = dao.createCriteria(Area.class);
		if (StringUtils.isNotBlank(dto.getId())) {
			criteria.add(Restrictions.eq("id", dto.getId()));
		}
		if (StringUtils.isNotBlank(dto.getAreaName())) {
			criteria.add(Restrictions.like("areaName", dto.getAreaName(), MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotBlank(dto.getStatus())) {
			criteria.add(Restrictions.eq("status", dto.getStatus()));
		}else{
			criteria.add(Restrictions.ne("status", "3"));
		}
		
		pagination.setRows(dao.findListWithPagebeanCriteria(criteria, pageBean));
		pagination.setTotal(dao.findTotalWithCriteria(criteria));
		return pagination;
	}

	// 添加或修改地区
	@Override
	public Result saveOrUpdate(AreaDto dto) {
		if (StringUtils.isBlank(dto.getId())) {
			if (StringUtils.isNotBlank(dto.getAreaName())) {

				Criteria criteria = dao.createCriteria(Area.class);
				criteria.add(Restrictions.eq("areaName", dto.getAreaName()));
				List<Area> areas = (List<Area>) criteria.list();
				if (areas != null && areas.size() > 0) {
					return new Result(Constants.RESULT_CODE.SYS_ERROR, "不能重复添加此地区！");
				} else {
					Area area = new Area();
					area.setAreaName(dto.getAreaName());
					area.setStatus(dto.getStatus());
					dao.save(area);
					return new Result(area);
				}
			} else {
				return new Result(Constants.RESULT_CODE.SYS_ERROR, "添加失败！");
			}
		} else {
			Area area = dao.findById(Area.class, dto.getId());
			if (area == null) {
				return new Result(Constants.RESULT_CODE.SYS_ERROR, "没有此地区！");
			}
			if (StringUtils.isNotBlank(dto.getAreaName())) {
				area.setAreaName(dto.getAreaName());
			}
			if (StringUtils.isNotBlank(dto.getStatus())) {
				area.setStatus(dto.getStatus());
			}
			dao.update(area);
			return new Result(area);
		}
	}
	// 删除地区
	@Override
	public Result delete(String areaId) {
		Area area = dao.findById(Area.class, areaId);
		if (area != null) {
			area.setStatus("3");
			dao.update(area);
			return new Result(area);
		} else {
			return new Result(Constants.RESULT_CODE.SYS_ERROR, "没有此地区！");
		}
	}

}
