package com.kongque.service.goods;


import com.kongque.entity.goods.GoodsDetailSelect;
import com.kongque.entity.goods.GoodsDetailTanant;
import com.kongque.model.GoodsDetailTanantModel;
import org.springframework.web.multipart.MultipartFile;

import com.kongque.dto.GoodsDto;
import com.kongque.entity.goods.Goods;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

import java.util.List;

public interface IGoodsService {

	Pagination<Goods> list(GoodsDto dto, PageBean pageBean);

	Pagination<GoodsDetailSelect> listDetail(GoodsDto dto, PageBean pageBean);

	Result saveOrUpdate(GoodsDto dto);
	
	Result updateStatus(String id,String status);
	
	Result uploadFile(MultipartFile[] files,String[] imageDelKeys);
	
	void getAttachment(String filePath);

    Result getAllTenantsByGoodsDetailId(String goodsDetailId);

	Result saveGoodsDetailTenant(List<GoodsDetailTanant> dto);

    Result deleteGoodsDetail(String id);
}
