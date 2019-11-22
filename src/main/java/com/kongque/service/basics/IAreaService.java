package com.kongque.service.basics;
import com.kongque.dto.AreaDto;
import com.kongque.entity.basics.Area;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

public interface IAreaService {
	public Pagination<Area> areaList(AreaDto dto, PageBean pageBean);//根据条件分页查询地区
	public Result saveOrUpdate(AreaDto dto);//添加或修改地区
	public Result delete(String areaId);//删除地区
	public Result areaById(String areaId);//根据id查询某一地区
}
