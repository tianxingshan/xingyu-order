package com.kongque.service.production.basics.material;

import java.text.ParseException;
import java.util.List;

import com.kongque.dto.GoodsPositionMaterialDto;
import com.kongque.entity.material.GoodsPositionMaterial;
import com.kongque.model.GoodsDetailModel;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

public interface IGoodsPositionMaterialService {
	
	Pagination<GoodsPositionMaterial> list(GoodsPositionMaterialDto dto,PageBean pageBean);
	
	Result saveOrUpdate(GoodsPositionMaterialDto dto);
	
	Result del(GoodsPositionMaterialDto dto);
	
	Result getMaterialCode(String materialCode);

	Result getMaterial(String materialCode, PageBean pageBean);
	
	Result getMaterialInfomation(String materialCode);
	
	Long queryCountWithParam(String code,String categoryId) throws ParseException;
	
	List<GoodsDetailModel> queryDetailWithParam(String code,String categoryId,PageBean pageBean);

	Result notAssignList(String goodsId, String measureSizeId, String orderDetailId);
	
	Result copyMaterial(GoodsPositionMaterialDto dto);

	Result batchCopyMaterial(GoodsPositionMaterialDto dto);
	
}
