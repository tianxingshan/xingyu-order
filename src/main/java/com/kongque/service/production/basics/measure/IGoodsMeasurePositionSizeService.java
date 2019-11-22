package com.kongque.service.production.basics.measure;

import com.kongque.dto.MeasurePositionSizeDto;
import com.kongque.dto.PlanOrderDetailDto;
import com.kongque.entity.measure.GoodsMeasurePositionSize;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface IGoodsMeasurePositionSizeService {
	
	Pagination<GoodsMeasurePositionSize> list(MeasurePositionSizeDto dto,PageBean pageBean);
	
	Result saveOrUpdate(MeasurePositionSizeDto dto);

	Result notAssignList(String goodsId,String measureSizeId ,String orderDetailId);

	/**
	 * 查询款式尺码部位尺寸列表
	 * @param dto
	 * @return
	 */
	Result searchMeasurePositionSizeList(MeasurePositionSizeDto dto);

	/**
	 * 新增或修改款式尺码部位尺寸
	 * @param dto
	 * @return
	 */
	Result saveUpdateMeasurePositionSize(MeasurePositionSizeDto dto);

	/**
	 * 新新增或修改款式尺码部位尺寸
	 * @param dto
	 * @return
	 */
	Result newSaveUpdateMeasurePositionSize(MeasurePositionSizeDto dto);

	/**
	 * 款式尺码部位尺寸复制
	 * @param dto
	 * @return
	 */
	Result copyMeasurePositionSize(MeasurePositionSizeDto dto);

	/**
	 * （新）款式尺码部位尺寸批量复制
	 * @param dto
	 * @return
	 */
	Result batchCopyMeasurePositionSize(MeasurePositionSizeDto dto);

	/**
	 * 删除款式尺码部位尺寸
	 * @param dto
	 * @return
	 */
	Result delMeasurePositionSize(MeasurePositionSizeDto dto);

	List<Map<String, Object>> getGoodsSize(String goodsId);


	/**
	 *商品配码实际制作尺寸分析
	 * @param dto
	 * @return
	 */
	Pagination<Map<String,Object>> getOrderActualGoodsSize(PlanOrderDetailDto dto,PageBean pageBean);

	/**
	 * 商品配码实际制作尺寸分析导出
	 * @param dto
	 * @param request
	 * @param response
	 */
	void getOrderActualGoodsSizeExportExcel(PlanOrderDetailDto dto, HttpServletRequest request, HttpServletResponse response);

	Pagination<Map<String,Object>> getOrderStandardGoodsSize(PlanOrderDetailDto dto, PageBean pageBean);

	void getOrderStandardGoodsSizeExportExcel(PlanOrderDetailDto dto, HttpServletRequest request, HttpServletResponse response);

	/**
	 * 同款多码样板净尺寸导出
	 * @param goodsId
	 * @param request
	 * @param response
	 */
	void getGoodsNetSizeExportExcel(String goodsId, HttpServletRequest request, HttpServletResponse response);

	/**
	 * 同款多码样板完成尺寸导出
	 * @param goodsId
	 * @param request
	 * @param response
	 */
	void getGoodsFinishSizeExportExcel(String goodsId, HttpServletRequest request, HttpServletResponse response);
}
