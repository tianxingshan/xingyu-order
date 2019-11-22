package com.kongque.service.production.basics.measure.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedReader;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import com.kongque.constants.Constants;
import com.kongque.dto.CategoryMeasureRelationDto;
import com.kongque.dto.PlanOrderDetailDto;
import com.kongque.dto.PositionDto;
import com.kongque.entity.basics.BodyMeasureInfo;
import com.kongque.entity.basics.CategoryMeasureRelation;
import com.kongque.entity.measure.MeasureSize;
import com.kongque.entity.productionorder.MesOrderDetailMeasure;
import com.kongque.service.basics.ICategoryMeasureRelationService;
import com.kongque.service.production.basics.measure.IMeasurePositionService;
import com.kongque.util.*;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.kongque.dao.IDaoService;
import com.kongque.dto.MeasurePositionSizeDto;
import com.kongque.entity.measure.GoodsMeasurePositionSize;
import com.kongque.entity.measure.MeasurePosition;
import com.kongque.service.production.basics.measure.IGoodsMeasurePositionSizeService;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
@Transactional
public class GoodsMeasurePositionSizeServiceImpl implements IGoodsMeasurePositionSizeService{
	
	@Resource
	private IDaoService dao;
    @Resource
	private ICategoryMeasureRelationService categoryMeasureRelationService;
    @Resource
    private IMeasurePositionService measurePositionService;
	
	
	@Override
	public Pagination<GoodsMeasurePositionSize> list(MeasurePositionSizeDto dto,PageBean pageBean){
		String[] measureSizeIds = new String[0];
		if (null!=dto.getMeasureSizeIds() && dto.getMeasureSizeIds().length>0){
			measureSizeIds = dto.getMeasureSizeIds();
		}
		if (StringUtils.isNotBlank(dto.getMeasureSizeId())){
			measureSizeIds = new String[]{dto.getMeasureSizeId()};
		}
		List<GoodsMeasurePositionSize> resList = new ArrayList<>();
		for (String measureSizeId : measureSizeIds) {
			MeasureSize measureSize = dao.findById(MeasureSize.class,measureSizeId);
			//根据商品id和尺码id判断该尺码是否已经保存量体数据
			Criteria criteria=dao.createCriteria(GoodsMeasurePositionSize.class);
			criteria.add(Restrictions.eq("goodsId", dto.getGoodsId()));
			criteria.add(Restrictions.eq("measureSizeId", measureSizeId));
			criteria.add(Restrictions.eq("del", Constants.ENABLE_FLAG.ENABLE));
			criteria.addOrder(Order.asc("measurePositionId"));
			@SuppressWarnings("unchecked")
			List<GoodsMeasurePositionSize> list = criteria.list();
			Set<String> measurePositionIds = new HashSet<>();
			for (GoodsMeasurePositionSize goodsMeasurePositionSize : list) {
				if (goodsMeasurePositionSize!=null){
					measurePositionIds.add(goodsMeasurePositionSize.getMeasurePositionId());
				}
			}
			//获取没有保存量体数据的部位列表
			Criteria criteria1=dao.createCriteria(MeasurePosition.class);
			if (measurePositionIds.size()>0){
				criteria1.add(Restrictions.not(Restrictions.in("id", measurePositionIds)));
			}
			criteria1.add(Restrictions.eq("categoryId", dto.getCategoryId()));
			criteria1.add(Restrictions.eq("del", "0"));
			criteria.addOrder(Order.asc("measurePositionId"));
			@SuppressWarnings("unchecked")
			List<MeasurePosition> measurePositions = criteria1.list();
			for (int i = 0; i < measurePositions.size(); i++) {
				GoodsMeasurePositionSize gmps = new GoodsMeasurePositionSize();
				gmps.setMeasurePositionId(measurePositions.get(i).getId());
				gmps.setMeasurePosition(measurePositions.get(i));
				gmps.setMeasureSize(measureSize);
				list.add(gmps);
			}
			resList.addAll(list);
		}

		Pagination<GoodsMeasurePositionSize> pagination=new Pagination<>();
		pagination.setRows(resList);
		pagination.setTotal(resList.size());
		return pagination;
	}
	
	@Override
	public Result saveOrUpdate(MeasurePositionSizeDto dto){
		Criteria criteria = dao.createCriteria(GoodsMeasurePositionSize.class);
		criteria.add(Restrictions.eq("goodsId", dto.getGoodsId()));
		criteria.add(Restrictions.eq("measureSizeId", dto.getMeasureSizeId()));
		criteria.add(Restrictions.eq("del", Constants.ENABLE_FLAG.ENABLE));
		@SuppressWarnings("unchecked")
		List<GoodsMeasurePositionSize> list = criteria.list();
		if(list == null || list.size()==0){
			//新增
			if(dto.getPositionList()!=null && dto.getPositionList().size()>0){
				for (int i = 0; i < dto.getPositionList().size(); i++) {
					GoodsMeasurePositionSize gmps = new GoodsMeasurePositionSize();
					gmps.setGoodsId(dto.getGoodsId());
					gmps.setMeasureSizeId(dto.getMeasureSizeId());
					gmps.setMeasurePositionId(dto.getPositionList().get(i).getMeasurePositionId());
					gmps.setModelNetSize(dto.getPositionList().get(i).getModelNetSize());
					gmps.setModelFinishSize(dto.getPositionList().get(i).getModelFinishSize());
					gmps.setShrinkage(dto.getPositionList().get(i).getShrinkage());
					gmps.setTolerance(dto.getPositionList().get(i).getTolerance());
					gmps.setDel(Constants.ENABLE_FLAG.ENABLE);
					dao.save(gmps); 
				}
			}
		}else{
			//修改
			if(dto.getPositionList()!=null && dto.getPositionList().size()>0){
				for (PositionDto positionDto : dto.getPositionList()) {
					if (positionDto!=null){
						GoodsMeasurePositionSize bean = dao.findById(GoodsMeasurePositionSize.class,positionDto.getId());
						if (bean!=null && Constants.ENABLE_FLAG.ENABLE.equals(bean.getDel())){
							bean.setGoodsId(dto.getGoodsId());
							bean.setMeasureSizeId(dto.getMeasureSizeId());
							bean.setMeasurePositionId(positionDto.getMeasurePositionId());
							bean.setModelNetSize(positionDto.getModelNetSize());
							bean.setModelFinishSize(positionDto.getModelFinishSize());
							bean.setShrinkage(positionDto.getShrinkage());
							bean.setTolerance(positionDto.getTolerance());
							dao.update(bean);
						}
					}
				}
				/*for (int j = 0; j < list.size(); j++) {
					dao.delete(list.get(j));
				}
				for (int i = 0; i < dto.getPositionList().size(); i++) {
					GoodsMeasurePositionSize gmps = new GoodsMeasurePositionSize();
					gmps.setGoodsId(dto.getGoodsId());
					gmps.setMeasureSizeId(dto.getMeasureSizeId());
					gmps.setMeasurePositionId(dto.getPositionList().get(i).getMeasurePositionId());
					gmps.setModelNetSize(dto.getPositionList().get(i).getModelNetSize());
					gmps.setModelFinishSize(dto.getPositionList().get(i).getModelFinishSize());
					gmps.setShrinkage(dto.getPositionList().get(i).getShrinkage());
					gmps.setTolerance(dto.getPositionList().get(i).getTolerance());
					dao.save(gmps); 
				}*/
			}
		}
		return new Result(list);
	}

	@Override
	public Result notAssignList(String goodsId, String measureSizeId, String orderDetailId) {
		if (StringUtils.isBlank(goodsId) || StringUtils.isBlank(measureSizeId) || StringUtils.isBlank(orderDetailId)){
			return new Result("400","款式、尺码、订单明细id不能为空");
		}
		List<GoodsMeasurePositionSize> list = dao.queryByHql(" FROM GoodsMeasurePositionSize gmps where c_del=0 and goodsId='"+goodsId+"' and measureSizeId='"+measureSizeId+"' and measurePositionId not in (select measurePositionId from  MesOrderDetailMeasure  where  orderDetailId='"+orderDetailId+"') ");
		return new Result(list);
	}

	@Override
	public Result searchMeasurePositionSizeList(MeasurePositionSizeDto dto) {
		Criteria criteria = dao.createCriteria(GoodsMeasurePositionSize.class);
		if (StringUtils.isBlank(dto.getGoodsId())){
			return new Result(Constants.RESULT_CODE.SYS_ERROR,"请传入款式参数！");
		}
		if (null==dto.getMeasureSizeIds() || dto.getMeasureSizeIds().length==0){
			return new Result(Constants.RESULT_CODE.SYS_ERROR,"请传入尺码参数！");
		}
		if (StringUtils.isBlank(dto.getMeasurePositionId())){
			return new Result(Constants.RESULT_CODE.SYS_ERROR,"请传入部位参数！");
		}
		criteria.add(Restrictions.eq("del", Constants.ENABLE_FLAG.ENABLE))
				.add(Restrictions.eq("goodsId",dto.getGoodsId()))
				.add(Restrictions.in("measureSizeId",dto.getMeasureSizeIds()))
				.add(Restrictions.eq("measurePositionId",dto.getMeasurePositionId()))
				.addOrder(Order.asc("measurePositionId"));
		return new Result(criteria.list());
	}

	@Override
	public Result saveUpdateMeasurePositionSize(MeasurePositionSizeDto dto) {
		if(null!=dto.getPositionList()){
			for (PositionDto positionDto : dto.getPositionList()) {
				//修改
				if (StringUtils.isNotBlank(positionDto.getId())){
					GoodsMeasurePositionSize bean = dao.findById(GoodsMeasurePositionSize.class,positionDto.getId());
					if (bean!=null && Constants.ENABLE_FLAG.ENABLE.equals(bean.getDel())){
						bean.setModelNetSize(positionDto.getModelNetSize());
						bean.setModelFinishSize(positionDto.getModelFinishSize());
						bean.setShrinkage(positionDto.getShrinkage());
						bean.setTolerance(positionDto.getTolerance());
						dao.update(bean);
					}
				}else{
					//新增
					for (String measureSizeId : dto.getMeasureSizeIds()) {
						GoodsMeasurePositionSize gmps = new GoodsMeasurePositionSize();
						gmps.setGoodsId(dto.getGoodsId());
						gmps.setMeasureSizeId(measureSizeId);
						gmps.setMeasurePositionId(positionDto.getMeasurePositionId());
						gmps.setModelNetSize(positionDto.getModelNetSize());
						gmps.setModelFinishSize(positionDto.getModelFinishSize());
						gmps.setShrinkage(positionDto.getShrinkage());
						gmps.setTolerance(positionDto.getTolerance());
						gmps.setDel(Constants.ENABLE_FLAG.ENABLE);
						dao.save(gmps);
					}
				}
			}
		}
		return new Result();
	}

	@Override
	public Result newSaveUpdateMeasurePositionSize(MeasurePositionSizeDto dto) {
		if(null!=dto.getPositionList()){
			for (PositionDto positionDto : dto.getPositionList()) {
				//修改
				if (StringUtils.isNotBlank(positionDto.getId())){
					GoodsMeasurePositionSize bean = dao.findById(GoodsMeasurePositionSize.class,positionDto.getId());
					if (bean!=null && Constants.ENABLE_FLAG.ENABLE.equals(bean.getDel())){
						bean.setModelNetSize(positionDto.getModelNetSize());
						bean.setModelFinishSize(positionDto.getModelFinishSize());
						bean.setShrinkage(positionDto.getShrinkage());
						bean.setTolerance(positionDto.getTolerance());
						dao.update(bean);
					}
				}else{
					//新增
					GoodsMeasurePositionSize gmps = new GoodsMeasurePositionSize();
					gmps.setGoodsId(dto.getGoodsId());
					gmps.setMeasureSizeId(positionDto.getMeasureSizeId());
					gmps.setMeasurePositionId(positionDto.getMeasurePositionId());
					gmps.setModelNetSize(positionDto.getModelNetSize());
					gmps.setModelFinishSize(positionDto.getModelFinishSize());
					gmps.setShrinkage(positionDto.getShrinkage());
					gmps.setTolerance(positionDto.getTolerance());
					gmps.setDel(Constants.ENABLE_FLAG.ENABLE);
					dao.save(gmps);
				}
			}
		}
		return new Result();
	}

	@Override
	public Result copyMeasurePositionSize(MeasurePositionSizeDto dto) {
		//判断是否勾选需要复制的数据
		if(null!=dto.getPositionList()&&dto.getPositionList().size()>0){
			String[] sizes = new String[0];
			if (StringUtils.isBlank(dto.getGoodsId())){
				return new Result(Constants.RESULT_CODE.SYS_ERROR,"请传入款式ID！");
			}
			if (null!=dto.getMeasureSizeIds()&&dto.getMeasureSizeIds().length>0){
				sizes = dto.getMeasureSizeIds();
			}
			if (StringUtils.isNotBlank(dto.getMeasureSizeId())){
				sizes = new String[]{dto.getMeasureSizeId()};
//				return new Result(Constants.RESULT_CODE.SYS_ERROR,"请传入尺码ID！");
			}
			Map<String,String> delMap = new HashMap<>();
			//遍历尺码
			for (String sizeId : sizes) {
				for (PositionDto positionDto : dto.getPositionList()) {
					if (StringUtils.isBlank(positionDto.getMeasurePositionId())){
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	//手动回滚事务
						return new Result(Constants.RESULT_CODE.SYS_ERROR,"请传入部位ID！");
					}
					String key = sizeId+positionDto.getMeasurePositionId();
					//校验防止尺码下一个部位复制多条数据的问题
					if (null==delMap.get(key)){
						delMap.put(key,positionDto.getMeasurePositionId());
						//把当前的款尺码部位下的数据清空
						Criteria criteria = dao.createCriteria(GoodsMeasurePositionSize.class)
								.add(Restrictions.eq("del", Constants.ENABLE_FLAG.ENABLE))
								.add(Restrictions.eq("goodsId",dto.getGoodsId()))
								.add(Restrictions.eq("measureSizeId",sizeId))
								.add(Restrictions.eq("measurePositionId",positionDto.getMeasurePositionId()));
						List<GoodsMeasurePositionSize> list = criteria.list();
						if (list!=null && list.size()>0){
							for (GoodsMeasurePositionSize goodsMeasurePositionSize : list) {
								goodsMeasurePositionSize.setDel(Constants.ENABLE_FLAG.DELETE);
								dao.update(goodsMeasurePositionSize);
							}
						}
					}
					//当前的款尺码部位下新增一条复制的数据
					GoodsMeasurePositionSize gmps = new GoodsMeasurePositionSize();
					gmps.setGoodsId(dto.getGoodsId());
					gmps.setMeasureSizeId(sizeId);
					gmps.setMeasurePositionId(positionDto.getMeasurePositionId());
					gmps.setModelNetSize(positionDto.getModelNetSize());
					gmps.setModelFinishSize(positionDto.getModelFinishSize());
					gmps.setShrinkage(positionDto.getShrinkage());
					gmps.setTolerance(positionDto.getTolerance());
					gmps.setDel(Constants.ENABLE_FLAG.ENABLE);
					dao.save(gmps);
				}
			}
		}
		return new Result();
	}

	@Override
	public Result batchCopyMeasurePositionSize(MeasurePositionSizeDto dto) {
		//判断是否勾选需要复制的数据
		if(null!=dto.getPositionList()&&dto.getPositionList().size()>0){
			String[] sizes = new String[0];
			if (StringUtils.isBlank(dto.getGoodsId())){
				return new Result(Constants.RESULT_CODE.SYS_ERROR,"请传入款式ID！");
			}
			if (null!=dto.getMeasureSizeIds()&&dto.getMeasureSizeIds().length>0){
				sizes = dto.getMeasureSizeIds();
			}
			if (StringUtils.isNotBlank(dto.getMeasureSizeId())){
				sizes = new String[]{dto.getMeasureSizeId()};
			}
			Map<String,String> delMap = new HashMap<>();
			//遍历复制的数据
			for (PositionDto positionDto : dto.getPositionList()) {
				if (StringUtils.isBlank(positionDto.getMeasurePositionId())){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	//手动回滚事务
					return new Result(Constants.RESULT_CODE.SYS_ERROR,"请传入部位ID！");
				}
				String key = positionDto.getMeasureSizeId()+positionDto.getMeasurePositionId();
				//校验防止尺码下一个部位复制多条数据的问题
				if (null==delMap.get(key)){
					delMap.put(key,positionDto.getMeasurePositionId());
					//把当前的款尺码部位下的数据清空
					Criteria criteria = dao.createCriteria(GoodsMeasurePositionSize.class)
							.add(Restrictions.eq("del", Constants.ENABLE_FLAG.ENABLE))
							.add(Restrictions.eq("goodsId",dto.getGoodsId()))
							.add(Restrictions.eq("measureSizeId",positionDto.getMeasureSizeId()))
							.add(Restrictions.eq("measurePositionId",positionDto.getMeasurePositionId()));
					List<GoodsMeasurePositionSize> list = criteria.list();
					if (list!=null && list.size()>0){
						for (GoodsMeasurePositionSize goodsMeasurePositionSize : list) {
							goodsMeasurePositionSize.setDel(Constants.ENABLE_FLAG.DELETE);
							dao.update(goodsMeasurePositionSize);
						}
					}
				}
				//当前的款尺码部位下新增一条复制的数据
				GoodsMeasurePositionSize gmps = new GoodsMeasurePositionSize();
				gmps.setGoodsId(dto.getGoodsId());
				gmps.setMeasureSizeId(positionDto.getMeasureSizeId());
				gmps.setMeasurePositionId(positionDto.getMeasurePositionId());
				gmps.setModelNetSize(positionDto.getModelNetSize());
				gmps.setModelFinishSize(positionDto.getModelFinishSize());
				gmps.setShrinkage(positionDto.getShrinkage());
				gmps.setTolerance(positionDto.getTolerance());
				gmps.setDel(Constants.ENABLE_FLAG.ENABLE);
				dao.save(gmps);
			}
		}
		return new Result();
	}

	@Override
	public Result delMeasurePositionSize(MeasurePositionSizeDto dto) {
		if (null==dto.getIds() || dto.getIds().length==0){
			return new Result(Constants.RESULT_CODE.SYS_ERROR,"请传入需要删除的数据ID！");
		}
		for (String id : dto.getIds()) {
			GoodsMeasurePositionSize bean = dao.findById(GoodsMeasurePositionSize.class,id);
			if (bean!=null&&Constants.ENABLE_FLAG.ENABLE.equals(bean.getDel())){
				bean.setDel(Constants.ENABLE_FLAG.DELETE);
				dao.update(bean);
			}
		}
		return new Result();
	}

	@Override
	public List<Map<String, Object>> getGoodsSize(String goodsId) {
		StringBuilder sql = new StringBuilder();
		StringBuilder sqlSelect = new StringBuilder();
		StringBuilder sqlSum = new StringBuilder();
		List<String> resultSize = this.getSizeByGoodsId(goodsId);

		for(String size : resultSize) {
			sqlSelect.append(",CASE WHEN d.c_name='"+size+"' THEN a.c_model_net_size ELSE 0 END AS 'A"+size+"',CASE WHEN d.c_name='"+size+"' THEN a.c_model_finish_size ELSE 0 END AS 'B"+size+"'");
			sqlSum.append(",SUM(`A"+size+"`) AS 'A"+size+"',SUM(`B"+size+"`) AS 'B"+size+"'");
		}
		sql.append(" SELECT goodsCode,goodsName,positionName").append(sqlSum);
		sql.append(" FROM (");
		sql.append(" SELECT a.c_goods_id,b.c_code AS goodsCode,b.c_name AS goodsName,c.c_name AS positionName").append(sqlSelect);
		sql.append(" FROM mes_goods_measure_position_size a");
		sql.append(" JOIN t_goods b ON a.c_goods_id = b.c_id");
		sql.append(" JOIN mes_measure_position c ON a.c_measure_position_id = c.c_id");
		sql.append(" JOIN mes_measure_size d ON a.c_measure_size_id = d.c_id");
		sql.append(" WHERE a.c_del='0' AND a.c_goods_id ='"+goodsId+"'");
		sql.append(" ORDER BY a.c_goods_id,c.c_code,d.c_sort,d.c_code");
		sql.append(" ) tb GROUP BY c_goods_id,goodsCode,goodsName,positionName ");

		List resultSet = dao.queryBySql(sql.toString());

		List<Map<String, Object>> lists = new ArrayList<>();

		for (Object object : resultSet) {
			Object[] properties = (Object[])object;
			ListOrderedMap map = new ListOrderedMap();
			map.put("goodsCode", properties[0]==null?"":properties[0]);
			map.put("goodsName", properties[1]==null?"":properties[1]);
			map.put("positionName", properties[2]==null?"":properties[2]);
			int i = 3;
			for(String s:resultSize){
				map.put("A"+s,properties[i]==null?"":properties[i]);
				++i;
				map.put("B"+s,properties[i]==null?"":properties[i]);
				++i;
			}
			lists.add(map);
		}
		return lists;
	}

	/**
	 * 同款多码样板净尺寸导出
	 * @param goodsId
	 * @param request
	 * @param response
	 */
	@Override
	public void getGoodsNetSizeExportExcel(String goodsId, HttpServletRequest request, HttpServletResponse response) {
		String excelFileName = "同款多码样板净尺寸分析" ;
		OutputStream out = null;
		try {

			//转码防止乱码
			final String userAgent = request.getHeader("USER-AGENT");
			if(userAgent.toLowerCase().contains("msie")){//IE浏览器
				excelFileName = URLEncoder.encode(excelFileName,"UTF8");
			}else if(userAgent.toLowerCase().contains( "mozilla") || userAgent.toLowerCase().contains("chrom")){//google浏览器,火狐浏览器
				excelFileName = new String(excelFileName.getBytes(), "ISO8859-1");
			}else{
				excelFileName = URLEncoder.encode(excelFileName,"UTF8");//其他浏览器
			}
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.addHeader("content-Disposition", "attachment;filename="+ excelFileName +".xlsx");
			response.flushBuffer();
			String[] headers = null;
			List<String> resultSize = this.getSizeByGoodsId(goodsId);
			headers =new String[3+resultSize.size()];
			headers[0]="款号";
			headers[1]="款名";
			headers[2]="量体部位";
            Integer i = 3;
			for(String size : resultSize) {
				headers[i]=size;
				i++;
			}

			out = response.getOutputStream();
			ExportExcelUtil.export2007Excel(0,0,0,"同款多码样板净尺寸分析" , headers,this.getGoodsSizeExportData(goodsId,"1"), out, "yyyy-MM-dd");
		} catch (IOException | IllegalArgumentException  e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 同款多码样板完成尺寸导出
	 * @param goodsId
	 * @param request
	 * @param response
	 */
	@Override
	public void getGoodsFinishSizeExportExcel(String goodsId, HttpServletRequest request, HttpServletResponse response) {
		String excelFileName = "同款多码样板完成尺寸分析" ;
		OutputStream out = null;
		try {

			//转码防止乱码
			final String userAgent = request.getHeader("USER-AGENT");
			if(userAgent.toLowerCase().contains("msie")){//IE浏览器
				excelFileName = URLEncoder.encode(excelFileName,"UTF8");
			}else if(userAgent.toLowerCase().contains( "mozilla") || userAgent.toLowerCase().contains("chrom")){//google浏览器,火狐浏览器
				excelFileName = new String(excelFileName.getBytes(), "ISO8859-1");
			}else{
				excelFileName = URLEncoder.encode(excelFileName,"UTF8");//其他浏览器
			}
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.addHeader("content-Disposition", "attachment;filename="+ excelFileName +".xlsx");
			response.flushBuffer();
			String[] headers = null;
			List<String> resultSize = this.getSizeByGoodsId(goodsId);
			headers =new String[3+resultSize.size()];
			headers[0]="款号";
			headers[1]="款名";
			headers[2]="量体部位";
			Integer i = 3;
			for(String size : resultSize) {
				headers[i]=size;
				i++;
			}

			out = response.getOutputStream();
			ExportExcelUtil.export2007Excel(0,0,0,"同款多码样板完成尺寸分析" , headers,this.getGoodsSizeExportData(goodsId,"2"), out, "yyyy-MM-dd");
		} catch (IOException | IllegalArgumentException  e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Pagination<Map<String, Object>> getOrderActualGoodsSize(PlanOrderDetailDto dto,PageBean pageBean) {
        Pagination<Map<String, Object>> listPagination = new Pagination<>();
		StringBuilder sql = new StringBuilder();
		StringBuilder sqlSelect = new StringBuilder();
//		StringBuilder sqlCount = new StringBuilder("SELECT COUNT(1) ");
		if(pageBean==null||pageBean.getPage()==null||pageBean.getRows()==null){
			pageBean.setPage(1);
			pageBean.setRows(10);
		}
		StringBuilder sqlLimit = new StringBuilder(" limit "+(pageBean.getPage() - 1) * pageBean.getRows()+","+pageBean.getRows());

        sqlSelect.append(" SELECT b.c_id orderDetailId, b.c_goods_sn AS goodsSn,a.c_order_code AS orderCode,d.c_code AS goodsCode,d.c_name AS goodsName,e.c_name categoryName");
        sqlSelect.append(" ,a.c_customer_name AS customerName,j.c_name AS mesMeasureSizeName,h.c_technician_name AS technicianName,b.c_order_detail_status AS orderDetailStatus");
        sqlSelect.append(" ,date_format(h.c_technical_finished_time,'%Y-%m-%d') AS technicalFinishedTime,i.c_order_measure AS orderMeasure,c.c_color_name");
		sql.append(" FROM (((t_order a");
		sql.append(" JOIN t_order_detail b ON a.c_id = b.c_order_id )");
		sql.append(" JOIN t_goods_detail c ON b.c_goods_detail_id = c.c_id)");
		sql.append(" JOIN t_goods d ON c.c_goods_id = d.c_id)");
		sql.append(" JOIN t_category e ON d.c_category_id = e.c_id");
		sql.append(" JOIN mes_order_detail_size f ON b.c_id = f.c_order_detail_id");
		sql.append(" JOIN mes_measure_size j ON f.c_mes_measure_size_id = j.c_id");
		sql.append(" JOIN mes_order_detail_assign h ON b.c_id = h.c_order_detail_id AND IFNULL(h.c_delete_flag,'0')='0'");
		sql.append(" JOIN t_body_measure i ON a.c_id = i.c_order_id ");
		sql.append(" WHERE (IFNULL(a.c_delete_flag,'0') = '0') AND d.c_id ='"+dto.getGoodsId()+"' ");
		//类别
		if(StringUtils.isNotBlank(dto.getCategoryId())){
			sql.append(" AND e.c_id ='").append(dto.getCategoryId()).append("'");
		}
		//唯一码
		if(StringUtils.isNotBlank(dto.getGoodsSn())){
			sql.append(" AND b.c_goods_sn='").append(dto.getGoodsSn()).append("'");
		}
		//单号
		if(StringUtils.isNotBlank(dto.getOrderCode())){
			sql.append(" AND a.c_order_code LIKE '%").append(dto.getOrderCode()).append("%'");
		}
		//颜色
		if(StringUtils.isNotBlank(dto.getGoodsColorName())){
			sql.append(" AND c.c_color_name='").append(dto.getGoodsColorName()).append("'");
		}
		//尺码
		if(StringUtils.isNotBlank(dto.getMesMeasureSizeName())){
			sql.append(" AND j.c_name ='").append(dto.getMesMeasureSizeName()).append("'");
		}
		//版型师名称
		if(StringUtils.isNotBlank(dto.getTechnicianName())){
			sql.append(" AND h.c_technician_name LIKE '%").append(dto.getTechnicianName()).append("%'");
		}
        //技术完成日期
		if(StringUtils.isNotBlank(dto.getTechnicalFinishedTimeStr())){
			sql.append(" AND date_format(h.c_technical_finished_time,'%Y-%m-%d') >='").append(dto.getTechnicalFinishedTimeStr()).append("'");
		}
		if(StringUtils.isNotBlank(dto.getTechnicalFinishedTimeEnd())){
			sql.append(" AND date_format(h.c_technical_finished_time,'%Y-%m-%d')<='").append(dto.getTechnicalFinishedTimeEnd()).append("'");
		}

		List resultSet = dao.queryBySql(sqlSelect.append(sql).append(sqlLimit).toString());

//		List<BigInteger> result = dao.queryBySql(sqlCount.append(sql).toString());
//		Long total = result == null || result.isEmpty() ? 0L : result.get(0).longValue();
//		listPagination.setTotal(total);
        PageBean page = new PageBean();
        page.setPage(1);
        page.setRows(10000);
        Object obj = dao.uniqueBySql("SELECT c_category_id,c_measure_category_id FROM t_goods WHERE c_id='"+dto.getGoodsId()+"'");
        if(obj==null){
            return null;
        }
        Object[] objProperties = (Object[]) obj;
        String categoryId = objProperties[0] == null ? "" : objProperties[0].toString();
        String measureCategoryId = objProperties[1] == null ? "" : objProperties[1].toString();
		//量体数据
        CategoryMeasureRelationDto categoryMeasureRelationDto = new CategoryMeasureRelationDto();
        categoryMeasureRelationDto.setCategoryId(measureCategoryId);
        Pagination<CategoryMeasureRelation> pagination =   categoryMeasureRelationService.list(categoryMeasureRelationDto,page);
        //部位
        MeasurePosition position = new MeasurePosition();
        position.setCategoryId(categoryId);
        position.setStatus("0");
        Pagination<MeasurePosition> positionPagination = measurePositionService.list(position,page);

        //订单量体数据
        StringBuilder sqlMeasure = new StringBuilder(" SELECT CONCAT(a.c_order_detail_id,a.c_mes_measure_position_id) as id,b.c_name,a.c_model_net_size,a.c_model_finish_size " +
                " FROM mes_order_detail_measure a " +
                " JOIN mes_measure_position b ON a.c_mes_measure_position_id = b.c_id " +
                " WHERE a.c_order_detail_id in (SELECT b.c_id ");
        sqlMeasure.append(sql).append(" )");
        List list = dao.queryBySql(sqlMeasure.toString());
        Map<String,Object> objectMap = new HashMap<>();
        for(Object o :list){
            Object[] p = (Object[]) o;
            MesOrderDetailMeasure mesOrderDetailMeasure = new MesOrderDetailMeasure();
            String id = p[0] == null ? "" : p[0].toString();
            mesOrderDetailMeasure.setMeasurePositionId(p[1] == null ? "" : p[1].toString());
            mesOrderDetailMeasure.setModelNetSize(p[2] == null ? "" : p[2].toString());
            mesOrderDetailMeasure.setModelFinishSize(p[3] == null ? "" : p[3].toString());
            objectMap.put(id,mesOrderDetailMeasure);
        }


		List<Map<String, Object>> lists = new ArrayList<>();

		for (Object object : resultSet) {
			Object[] properties = (Object[]) object;
			ListOrderedMap map = new ListOrderedMap();
			String orderDetailId = properties[0].toString();
			map.put("goodsSn", properties[1] == null ? "" : properties[1]);
			map.put("orderCode", properties[2] == null ? "" : properties[2]);
			map.put("goodsCode", properties[3] == null ? "" : properties[3]);
			map.put("goodsName", properties[4] == null ? "" : properties[4]);
			map.put("goodsColorName", properties[12] == null ? "" : properties[12]);
			map.put("categoryName", properties[5] == null ? "" : properties[5]);
			map.put("customerName", properties[6] == null ? "" : properties[6]);
			map.put("mesMeasureSizeName", properties[7] == null ? "" : properties[7]);
			map.put("technicianName", properties[8] == null ? "" : properties[8]);
			map.put("orderDetailStatus", properties[9] == null ? "" : Status.getRepairStatusName(properties[9]));
			map.put("technicalFinishedTime", properties[10] == null ? "" : properties[10]);
			//量体部位赋值
            JSONObject jsonObject = JSONObject.fromObject(properties[11]);
           for(CategoryMeasureRelation categoryMeasureRelation:pagination.getRows()) {
               BodyMeasureInfo bodyMeasureInfo = categoryMeasureRelation.getBodyMeasureInfo();
               if (jsonObject.containsKey(bodyMeasureInfo.getId())) {
                   String value = jsonObject.get(bodyMeasureInfo.getId()).toString();
                   map.put("$"+bodyMeasureInfo.getName(),value);
               }else{
                   map.put("$"+bodyMeasureInfo.getName(),"0");
               }
           }

           //部位尺寸赋值
           for(MeasurePosition measurePosition:positionPagination.getRows()){
               MesOrderDetailMeasure obj1 = (MesOrderDetailMeasure)objectMap.get(orderDetailId+measurePosition.getId());
               if(obj1!=null){
                   map.put("样板尺寸:"+measurePosition.getName(),obj1.getModelNetSize());
                   map.put("完成尺寸:"+measurePosition.getName(),obj1.getModelFinishSize());
               }else{
                   map.put("样板尺寸:"+measurePosition.getName(),"0");
                   map.put("完成尺寸:"+measurePosition.getName(),"0");
               }
           }

            lists.add(map);

		}


        listPagination.setRows(lists);

		return listPagination;
	}

	@Override
	public void getOrderActualGoodsSizeExportExcel(PlanOrderDetailDto dto, HttpServletRequest request, HttpServletResponse response) {

        String excelFileName = "商品配码实际制作尺寸分析" ;
        OutputStream out = null;
        try {

            //转码防止乱码
            final String userAgent = request.getHeader("USER-AGENT");
            if(userAgent.toLowerCase().contains("msie")){//IE浏览器
                excelFileName = URLEncoder.encode(excelFileName,"UTF8");
            }else if(userAgent.toLowerCase().contains( "mozilla") || userAgent.toLowerCase().contains("chrom")){//google浏览器,火狐浏览器
                excelFileName = new String(excelFileName.getBytes(), "ISO8859-1");
            }else{
                excelFileName = URLEncoder.encode(excelFileName,"UTF8");//其他浏览器
            }
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.addHeader("content-Disposition", "attachment;filename="+ excelFileName +".xlsx");
            response.flushBuffer();
            String[] headers = null;
            //查询
            StringBuilder sql = new StringBuilder();
            StringBuilder sqlSelect = new StringBuilder();
            sqlSelect.append(" SELECT b.c_id orderDetailId, b.c_goods_sn AS goodsSn,a.c_order_code AS orderCode,d.c_code AS goodsCode,d.c_name AS goodsName,e.c_name categoryName");
            sqlSelect.append(" ,a.c_customer_name AS customerName,j.c_name AS mesMeasureSizeName,h.c_technician_name AS technicianName,b.c_order_detail_status AS orderDetailStatus");
            sqlSelect.append(" ,date_format(h.c_technical_finished_time,'%Y-%m-%d') AS technicalFinishedTime,i.c_order_measure AS orderMeasure,c.c_color_name ");
            sql.append(" FROM (((t_order a");
            sql.append(" JOIN t_order_detail b ON a.c_id = b.c_order_id )");
            sql.append(" JOIN t_goods_detail c ON b.c_goods_detail_id = c.c_id)");
            sql.append(" JOIN t_goods d ON c.c_goods_id = d.c_id)");
            sql.append(" JOIN t_category e ON d.c_category_id = e.c_id");
            sql.append(" JOIN mes_order_detail_size f ON b.c_id = f.c_order_detail_id");
            sql.append(" JOIN mes_measure_size j ON f.c_mes_measure_size_id = j.c_id");
            sql.append(" JOIN mes_order_detail_assign h ON b.c_id = h.c_order_detail_id AND IFNULL(h.c_delete_flag,'0')='0'");
            sql.append(" JOIN t_body_measure i ON a.c_id = i.c_order_id ");
            sql.append(" WHERE (IFNULL(a.c_delete_flag,'0') = '0') AND d.c_id ='"+dto.getGoodsId()+"' ");
            //类别
            if(StringUtils.isNotBlank(dto.getCategoryId())){
                sql.append(" AND e.c_id ='").append(dto.getCategoryId()).append("'");
            }
            //唯一码
            if(StringUtils.isNotBlank(dto.getGoodsSn())){
                sql.append(" AND b.c_goods_sn='").append(dto.getGoodsSn()).append("'");
            }
            //单号
            if(StringUtils.isNotBlank(dto.getOrderCode())){
                sql.append(" AND a.c_order_code LIKE '%").append(dto.getOrderCode()).append("%'");
            }
			//颜色
			if(StringUtils.isNotBlank(dto.getGoodsColorName())){
				sql.append(" AND c.c_color_name='").append(dto.getGoodsColorName()).append("'");
			}
            //尺码
            if(StringUtils.isNotBlank(dto.getMesMeasureSizeName())){
                sql.append(" AND j.c_name ='").append(dto.getMesMeasureSizeName()).append("'");
            }
            //版型师名称
            if(StringUtils.isNotBlank(dto.getTechnicianName())){
                sql.append(" AND h.c_technician_name LIKE '%").append(dto.getTechnicianName()).append("%'");
            }
            //技术完成日期
            if(StringUtils.isNotBlank(dto.getTechnicalFinishedTimeStr())){
                sql.append(" AND date_format(h.c_technical_finished_time,'%Y-%m-%d') >='").append(dto.getTechnicalFinishedTimeStr()).append("'");
            }
            if(StringUtils.isNotBlank(dto.getTechnicalFinishedTimeEnd())){
                sql.append(" AND date_format(h.c_technical_finished_time,'%Y-%m-%d')<='").append(dto.getTechnicalFinishedTimeEnd()).append("'");
            }

            List resultSet = dao.queryBySql(sqlSelect.append(sql).toString());
            PageBean pageBean = new PageBean();
            pageBean.setPage(1);
            pageBean.setRows(10000);
            Object obj = dao.uniqueBySql("SELECT c_category_id,c_measure_category_id FROM t_goods WHERE c_id='"+dto.getGoodsId()+"'");
            if(obj==null){
                return ;
            }
            Object[] objProperties = (Object[]) obj;
            String categoryId = objProperties[0] == null ? "" : objProperties[0].toString();
            String measureCategoryId = objProperties[1] == null ? "" : objProperties[1].toString();
            //量体数据
            CategoryMeasureRelationDto categoryMeasureRelationDto = new CategoryMeasureRelationDto();
            categoryMeasureRelationDto.setCategoryId(measureCategoryId);
            Pagination<CategoryMeasureRelation> pagination =   categoryMeasureRelationService.list(categoryMeasureRelationDto,pageBean);
            //部位
            MeasurePosition position = new MeasurePosition();
            position.setCategoryId(categoryId);
            position.setStatus("0");
            Pagination<MeasurePosition> positionPagination = measurePositionService.list(position,pageBean);

            //订单量体数据
            StringBuilder sqlMeasure = new StringBuilder(" SELECT CONCAT(a.c_order_detail_id,a.c_mes_measure_position_id) as id,b.c_name,a.c_model_net_size,a.c_model_finish_size " +
                    " FROM mes_order_detail_measure a " +
                    " JOIN mes_measure_position b ON a.c_mes_measure_position_id = b.c_id " +
                    " WHERE a.c_order_detail_id in (SELECT b.c_id ");
            sqlMeasure.append(sql).append(" )");
            List list = dao.queryBySql(sqlMeasure.toString());
            Map<String,Object> objectMap = new HashMap<>();
            for(Object o :list){
                Object[] p = (Object[]) o;
                MesOrderDetailMeasure mesOrderDetailMeasure = new MesOrderDetailMeasure();
                String id = p[0] == null ? "" : p[0].toString();
                mesOrderDetailMeasure.setMeasurePositionId(p[1] == null ? "" : p[1].toString());
                mesOrderDetailMeasure.setModelNetSize(p[2] == null ? "" : p[2].toString());
                mesOrderDetailMeasure.setModelFinishSize(p[3] == null ? "" : p[3].toString());
                objectMap.put(id,mesOrderDetailMeasure);
            }


            List<Map<String, Object>> lists = new ArrayList<>();

            for (Object object : resultSet) {
                Object[] properties = (Object[]) object;
                ListOrderedMap map = new ListOrderedMap();
                String orderDetailId = properties[0].toString();
                map.put("goodsSn", properties[1] == null ? "" : properties[1]);
                map.put("orderCode", properties[2] == null ? "" : properties[2]);
                map.put("goodsCode", properties[3] == null ? "" : properties[3]);
                map.put("goodsName", properties[4] == null ? "" : properties[4]);
				map.put("goodsColorName", properties[12] == null ? "" : properties[12]);
                map.put("categoryName", properties[5] == null ? "" : properties[5]);
                map.put("customerName", properties[6] == null ? "" : properties[6]);
                map.put("mesMeasureSizeName", properties[7] == null ? "" : properties[7]);
                map.put("technicianName", properties[8] == null ? "" : properties[8]);
                map.put("orderDetailStatus", properties[9] == null ? "" : Status.getRepairStatusName(properties[9]));
                map.put("technicalFinishedTime", properties[10] == null ? "" : properties[10]);
                //量体部位赋值
                JSONObject jsonObject = JSONObject.fromObject(properties[11]);
                for(CategoryMeasureRelation categoryMeasureRelation:pagination.getRows()) {
                    BodyMeasureInfo bodyMeasureInfo = categoryMeasureRelation.getBodyMeasureInfo();
                    if (jsonObject.containsKey(bodyMeasureInfo.getId())) {
                        String value = jsonObject.get(bodyMeasureInfo.getId()).toString();
                        map.put(""+bodyMeasureInfo.getName(),value);
                    }else{
                        map.put(""+bodyMeasureInfo.getName(),"0");
                    }
                }

                //部位尺寸赋值
                for(MeasurePosition measurePosition:positionPagination.getRows()){
                    MesOrderDetailMeasure obj1 = (MesOrderDetailMeasure)objectMap.get(orderDetailId+measurePosition.getId());
                    if(obj1!=null){
                        map.put("样板尺寸:"+measurePosition.getName(),obj1.getModelNetSize());
                        map.put("完成尺寸:"+measurePosition.getName(),obj1.getModelFinishSize());
                    }else{
                        map.put("样板尺寸:"+measurePosition.getName(),"0");
                        map.put("完成尺寸:"+measurePosition.getName(),"0");
                    }
                }

                lists.add(map);

            }

			Integer i = 11;
            headers = new String[i+pagination.getRows().size()+positionPagination.getRows().size()*2];
            headers[0]="唯一码";
            headers[1]="订单号";
            headers[2]="款号";
            headers[3]="款名";
			headers[4]="颜色";
            headers[5]="类别";
            headers[6]="顾客姓名";
            headers[7]="匹配尺码";
            headers[8]="版师";
            headers[9]="商品状态";
            headers[10]="技术完成日期";

            for(CategoryMeasureRelation categoryMeasureRelation:pagination.getRows()) {
                BodyMeasureInfo bodyMeasureInfo = categoryMeasureRelation.getBodyMeasureInfo();
                headers[i]=bodyMeasureInfo.getName();
                i++;
            }

            for(MeasurePosition measurePosition:positionPagination.getRows()){
                headers[i] = "样板尺寸:"+measurePosition.getName();
                ++i;
                headers[i] = "完成尺寸:"+measurePosition.getName();
                ++i;
            }


            out = response.getOutputStream();
            ExportExcelUtil.export2007Excel(0,0,0,"商品配码实际制作尺寸分析" , headers,lists, out, "yyyy-MM-dd");
        } catch (IOException | IllegalArgumentException  e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}

	/**
	 * 商品配码基本码尺寸分析
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@Override
	public Pagination<Map<String, Object>> getOrderStandardGoodsSize(PlanOrderDetailDto dto, PageBean pageBean) {
		Pagination<Map<String, Object>> listPagination = new Pagination<>();
		StringBuilder sql = new StringBuilder();
		StringBuilder sqlSelect = new StringBuilder();
		StringBuilder sqlCount = new StringBuilder("SELECT COUNT(1) ");
		if(pageBean==null||pageBean.getPage()==null||pageBean.getRows()==null){
			pageBean.setPage(1);
			pageBean.setRows(10);
		}
		StringBuilder sqlLimit = new StringBuilder(" limit "+(pageBean.getPage() - 1) * pageBean.getRows()+","+pageBean.getRows());

		sqlSelect.append(" SELECT j.c_id measureSizeId, b.c_goods_sn AS goodsSn,a.c_order_code AS orderCode,d.c_code AS goodsCode,d.c_name AS goodsName,e.c_name categoryName");
		sqlSelect.append(" ,a.c_customer_name AS customerName,j.c_name AS mesMeasureSizeName,h.c_technician_name AS technicianName,b.c_order_detail_status AS orderDetailStatus");
		sqlSelect.append(" ,date_format(h.c_technical_finished_time,'%Y-%m-%d') AS technicalFinishedTime,i.c_order_measure AS orderMeasure,c.c_color_name");
		sql.append(" FROM (((t_order a");
		sql.append(" JOIN t_order_detail b ON a.c_id = b.c_order_id )");
		sql.append(" JOIN t_goods_detail c ON b.c_goods_detail_id = c.c_id)");
		sql.append(" JOIN t_goods d ON c.c_goods_id = d.c_id)");
		sql.append(" JOIN t_category e ON d.c_category_id = e.c_id");
		sql.append(" JOIN mes_order_detail_size f ON b.c_id = f.c_order_detail_id");
		sql.append(" JOIN mes_measure_size j ON f.c_mes_measure_size_id = j.c_id");
		sql.append(" JOIN mes_order_detail_assign h ON b.c_id = h.c_order_detail_id AND IFNULL(h.c_delete_flag,'0')='0'");
		sql.append(" JOIN t_body_measure i ON a.c_id = i.c_order_id ");
		sql.append(" WHERE (IFNULL(a.c_delete_flag,'0') = '0') AND d.c_id ='"+dto.getGoodsId()+"' ");
		//类别
		if(StringUtils.isNotBlank(dto.getCategoryId())){
			sql.append(" AND e.c_id ='").append(dto.getCategoryId()).append("'");
		}
		//唯一码
		if(StringUtils.isNotBlank(dto.getGoodsSn())){
			sql.append(" AND b.c_goods_sn='").append(dto.getGoodsSn()).append("'");
		}
		//单号
		if(StringUtils.isNotBlank(dto.getOrderCode())){
			sql.append(" AND a.c_order_code LIKE '%").append(dto.getOrderCode()).append("%'");
		}
		//颜色
		if(StringUtils.isNotBlank(dto.getGoodsColorName())){
			sql.append(" AND c.c_color_name='").append(dto.getGoodsColorName()).append("'");
		}
		//尺码
		if(StringUtils.isNotBlank(dto.getMesMeasureSizeName())){
			sql.append(" AND j.c_name ='").append(dto.getMesMeasureSizeName()).append("'");
		}
		//版型师名称
		if(StringUtils.isNotBlank(dto.getTechnicianName())){
			sql.append(" AND h.c_technician_name LIKE '%").append(dto.getTechnicianName()).append("%'");
		}
		//技术完成日期
		if(StringUtils.isNotBlank(dto.getTechnicalFinishedTimeStr())){
			sql.append(" AND date_format(h.c_technical_finished_time,'%Y-%m-%d') >='").append(dto.getTechnicalFinishedTimeStr()).append("'");
		}
		if(StringUtils.isNotBlank(dto.getTechnicalFinishedTimeEnd())){
			sql.append(" AND date_format(h.c_technical_finished_time,'%Y-%m-%d')<='").append(dto.getTechnicalFinishedTimeEnd()).append("'");
		}

		List resultSet = dao.queryBySql(sqlSelect.append(sql).append(sqlLimit).toString());

		List<BigInteger> result = dao.queryBySql(sqlCount.append(sql).toString());
		Long total = result == null || result.isEmpty() ? 0L : result.get(0).longValue();
		listPagination.setTotal(total);
		PageBean page = new PageBean();
		page.setPage(1);
		page.setRows(10000);
		Object obj = dao.uniqueBySql("SELECT c_category_id,c_measure_category_id FROM t_goods WHERE c_id='"+dto.getGoodsId()+"'");
		if(obj==null){
			return null;
		}
		Object[] objProperties = (Object[]) obj;
		String categoryId = objProperties[0] == null ? "" : objProperties[0].toString();
		String measureCategoryId = objProperties[1] == null ? "" : objProperties[1].toString();
		//量体数据
		CategoryMeasureRelationDto categoryMeasureRelationDto = new CategoryMeasureRelationDto();
		categoryMeasureRelationDto.setCategoryId(measureCategoryId);
		Pagination<CategoryMeasureRelation> pagination =   categoryMeasureRelationService.list(categoryMeasureRelationDto,page);
		//部位
		MeasurePosition position = new MeasurePosition();
		position.setCategoryId(categoryId);
		position.setStatus("0");
		Pagination<MeasurePosition> positionPagination = measurePositionService.list(position,page);

		//基本码尺寸数据
		StringBuilder sqlMeasure = new StringBuilder(" SELECT CONCAT(a.c_goods_id,a.c_measure_position_id,a.c_measure_size_id) as id,b.c_name,a.c_model_net_size,a.c_model_finish_size " +
				" FROM mes_goods_measure_position_size a " +
				" JOIN mes_measure_position b ON a.c_measure_position_id = b.c_id " +
				" WHERE a.c_goods_id ='"+dto.getGoodsId()+"' ");
		List list = dao.queryBySql(sqlMeasure.toString());
		Map<String,Object> objectMap = new HashMap<>();
		for(Object o :list){
			Object[] p = (Object[]) o;
			MesOrderDetailMeasure mesOrderDetailMeasure = new MesOrderDetailMeasure();
			String id = p[0] == null ? "" : p[0].toString();
			mesOrderDetailMeasure.setMeasurePositionId(p[1] == null ? "" : p[1].toString());
			mesOrderDetailMeasure.setModelNetSize(p[2] == null ? "" : p[2].toString());
			mesOrderDetailMeasure.setModelFinishSize(p[3] == null ? "" : p[3].toString());
			objectMap.put(id,mesOrderDetailMeasure);
		}


		List<Map<String, Object>> lists = new ArrayList<>();

		for (Object object : resultSet) {
			Object[] properties = (Object[]) object;
			ListOrderedMap map = new ListOrderedMap();
			String measureSizeId = properties[0].toString();
			map.put("goodsSn", properties[1] == null ? "" : properties[1]);
			map.put("orderCode", properties[2] == null ? "" : properties[2]);
			map.put("goodsCode", properties[3] == null ? "" : properties[3]);
			map.put("goodsName", properties[4] == null ? "" : properties[4]);
			map.put("goodsColorName", properties[12] == null ? "" : properties[12]);
			map.put("categoryName", properties[5] == null ? "" : properties[5]);
			map.put("customerName", properties[6] == null ? "" : properties[6]);
			map.put("mesMeasureSizeName", properties[7] == null ? "" : properties[7]);
			map.put("technicianName", properties[8] == null ? "" : properties[8]);
			map.put("orderDetailStatus", properties[9] == null ? "" : Status.getRepairStatusName(properties[9]));
			map.put("technicalFinishedTime", properties[10] == null ? "" : properties[10]);
			//量体部位赋值
			JSONObject jsonObject = JSONObject.fromObject(properties[11]);
			for(CategoryMeasureRelation categoryMeasureRelation:pagination.getRows()) {
				BodyMeasureInfo bodyMeasureInfo = categoryMeasureRelation.getBodyMeasureInfo();
				if (jsonObject.containsKey(bodyMeasureInfo.getId())) {
					String value = jsonObject.get(bodyMeasureInfo.getId()).toString();
					map.put("$"+bodyMeasureInfo.getName(),value);
				}else{
					map.put("$"+bodyMeasureInfo.getName(),"0");
				}
			}

			//部位尺寸赋值
			for(MeasurePosition measurePosition:positionPagination.getRows()){
				MesOrderDetailMeasure obj1 = (MesOrderDetailMeasure)objectMap.get(dto.getGoodsId()+measurePosition.getId()+measureSizeId);
				if(obj1!=null){
					map.put("样板尺寸:"+measurePosition.getName(),obj1.getModelNetSize());
					map.put("完成尺寸:"+measurePosition.getName(),obj1.getModelFinishSize());
				}else{
					map.put("样板尺寸:"+measurePosition.getName(),"0");
					map.put("完成尺寸:"+measurePosition.getName(),"0");
				}
			}

			lists.add(map);

		}


		listPagination.setRows(lists);

		return listPagination;
	}

	/**
	 * 商品配码基本码尺寸导出
	 * @param dto
	 * @param request
	 * @param response
	 */
	@Override
	public void getOrderStandardGoodsSizeExportExcel(PlanOrderDetailDto dto, HttpServletRequest request, HttpServletResponse response) {
		String excelFileName = "商品配码基本码尺寸分析" ;
		OutputStream out = null;
		try {

			//转码防止乱码
			final String userAgent = request.getHeader("USER-AGENT");
			if(userAgent.toLowerCase().contains("msie")){//IE浏览器
				excelFileName = URLEncoder.encode(excelFileName,"UTF8");
			}else if(userAgent.toLowerCase().contains( "mozilla") || userAgent.toLowerCase().contains("chrom")){//google浏览器,火狐浏览器
				excelFileName = new String(excelFileName.getBytes(), "ISO8859-1");
			}else{
				excelFileName = URLEncoder.encode(excelFileName,"UTF8");//其他浏览器
			}
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.addHeader("content-Disposition", "attachment;filename="+ excelFileName +".xlsx");
			response.flushBuffer();
			String[] headers = null;
			//查询
			StringBuilder sql = new StringBuilder();
			StringBuilder sqlSelect = new StringBuilder();
			sqlSelect.append(" SELECT j.c_id measureSizeId, b.c_goods_sn AS goodsSn,a.c_order_code AS orderCode,d.c_code AS goodsCode,d.c_name AS goodsName,e.c_name categoryName");
			sqlSelect.append(" ,a.c_customer_name AS customerName,j.c_name AS mesMeasureSizeName,h.c_technician_name AS technicianName,b.c_order_detail_status AS orderDetailStatus");
			sqlSelect.append(" ,date_format(h.c_technical_finished_time,'%Y-%m-%d') AS technicalFinishedTime,i.c_order_measure AS orderMeasure,c.c_color_name");
			sql.append(" FROM (((t_order a");
			sql.append(" JOIN t_order_detail b ON a.c_id = b.c_order_id )");
			sql.append(" JOIN t_goods_detail c ON b.c_goods_detail_id = c.c_id)");
			sql.append(" JOIN t_goods d ON c.c_goods_id = d.c_id)");
			sql.append(" JOIN t_category e ON d.c_category_id = e.c_id");
			sql.append(" JOIN mes_order_detail_size f ON b.c_id = f.c_order_detail_id");
			sql.append(" JOIN mes_measure_size j ON f.c_mes_measure_size_id = j.c_id");
			sql.append(" JOIN mes_order_detail_assign h ON b.c_id = h.c_order_detail_id AND IFNULL(h.c_delete_flag,'0')='0'");
			sql.append(" JOIN t_body_measure i ON a.c_id = i.c_order_id ");
			sql.append(" WHERE (IFNULL(a.c_delete_flag,'0') = '0') AND d.c_id ='"+dto.getGoodsId()+"' ");
			//类别
			if(StringUtils.isNotBlank(dto.getCategoryId())){
				sql.append(" AND e.c_id ='").append(dto.getCategoryId()).append("'");
			}
			//唯一码
			if(StringUtils.isNotBlank(dto.getGoodsSn())){
				sql.append(" AND b.c_goods_sn='").append(dto.getGoodsSn()).append("'");
			}
			//单号
			if(StringUtils.isNotBlank(dto.getOrderCode())){
				sql.append(" AND a.c_order_code LIKE '%").append(dto.getOrderCode()).append("%'");
			}
			//颜色
			if(StringUtils.isNotBlank(dto.getGoodsColorName())){
				sql.append(" AND c.c_color_name='").append(dto.getGoodsColorName()).append("'");
			}
			//尺码
			if(StringUtils.isNotBlank(dto.getMesMeasureSizeName())){
				sql.append(" AND j.c_name ='").append(dto.getMesMeasureSizeName()).append("'");
			}
			//版型师名称
			if(StringUtils.isNotBlank(dto.getTechnicianName())){
				sql.append(" AND h.c_technician_name LIKE '%").append(dto.getTechnicianName()).append("%'");
			}
			//技术完成日期
			if(StringUtils.isNotBlank(dto.getTechnicalFinishedTimeStr())){
				sql.append(" AND date_format(h.c_technical_finished_time,'%Y-%m-%d') >='").append(dto.getTechnicalFinishedTimeStr()).append("'");
			}
			if(StringUtils.isNotBlank(dto.getTechnicalFinishedTimeEnd())){
				sql.append(" AND date_format(h.c_technical_finished_time,'%Y-%m-%d')<='").append(dto.getTechnicalFinishedTimeEnd()).append("'");
			}

			List resultSet = dao.queryBySql(sqlSelect.append(sql).toString());
			PageBean pageBean = new PageBean();
			pageBean.setPage(1);
			pageBean.setRows(10000);
			Object obj = dao.uniqueBySql("SELECT c_category_id,c_measure_category_id FROM t_goods WHERE c_id='"+dto.getGoodsId()+"'");
			if(obj==null){
				return ;
			}
			Object[] objProperties = (Object[]) obj;
			String categoryId = objProperties[0] == null ? "" : objProperties[0].toString();
			String measureCategoryId = objProperties[1] == null ? "" : objProperties[1].toString();
			//量体数据
			CategoryMeasureRelationDto categoryMeasureRelationDto = new CategoryMeasureRelationDto();
			categoryMeasureRelationDto.setCategoryId(measureCategoryId);
			Pagination<CategoryMeasureRelation> pagination =   categoryMeasureRelationService.list(categoryMeasureRelationDto,pageBean);
			//部位
			MeasurePosition position = new MeasurePosition();
			position.setCategoryId(categoryId);
			position.setStatus("0");
			Pagination<MeasurePosition> positionPagination = measurePositionService.list(position,pageBean);

			//订单量体数据
			StringBuilder sqlMeasure = new StringBuilder(" SELECT CONCAT(a.c_goods_id,a.c_measure_position_id,a.c_measure_size_id) as id,b.c_name,a.c_model_net_size,a.c_model_finish_size " +
					" FROM mes_goods_measure_position_size a " +
					" JOIN mes_measure_position b ON a.c_measure_position_id = b.c_id " +
					" WHERE a.c_goods_id ='"+dto.getGoodsId()+"' ");
			List list = dao.queryBySql(sqlMeasure.toString());
			Map<String,Object> objectMap = new HashMap<>();
			for(Object o :list){
				Object[] p = (Object[]) o;
				MesOrderDetailMeasure mesOrderDetailMeasure = new MesOrderDetailMeasure();
				String id = p[0] == null ? "" : p[0].toString();
				mesOrderDetailMeasure.setMeasurePositionId(p[1] == null ? "" : p[1].toString());
				mesOrderDetailMeasure.setModelNetSize(p[2] == null ? "" : p[2].toString());
				mesOrderDetailMeasure.setModelFinishSize(p[3] == null ? "" : p[3].toString());
				objectMap.put(id,mesOrderDetailMeasure);
			}


			List<Map<String, Object>> lists = new ArrayList<>();

			for (Object object : resultSet) {
				Object[] properties = (Object[]) object;
				ListOrderedMap map = new ListOrderedMap();
				String measureSizeId = properties[0].toString();
				map.put("goodsSn", properties[1] == null ? "" : properties[1]);
				map.put("orderCode", properties[2] == null ? "" : properties[2]);
				map.put("goodsCode", properties[3] == null ? "" : properties[3]);
				map.put("goodsName", properties[4] == null ? "" : properties[4]);
				map.put("goodsColorName", properties[12] == null ? "" : properties[12]);
				map.put("categoryName", properties[5] == null ? "" : properties[5]);
				map.put("customerName", properties[6] == null ? "" : properties[6]);
				map.put("mesMeasureSizeName", properties[7] == null ? "" : properties[7]);
				map.put("technicianName", properties[8] == null ? "" : properties[8]);
				map.put("orderDetailStatus", properties[9] == null ? "" : Status.getRepairStatusName(properties[9]));
				map.put("technicalFinishedTime", properties[10] == null ? "" : properties[10]);
				//量体部位赋值
				JSONObject jsonObject = JSONObject.fromObject(properties[11]);
				for(CategoryMeasureRelation categoryMeasureRelation:pagination.getRows()) {
					BodyMeasureInfo bodyMeasureInfo = categoryMeasureRelation.getBodyMeasureInfo();
					if (jsonObject.containsKey(bodyMeasureInfo.getId())) {
						String value = jsonObject.get(bodyMeasureInfo.getId()).toString();
						map.put(""+bodyMeasureInfo.getName(),value);
					}else{
						map.put(""+bodyMeasureInfo.getName(),"0");
					}
				}

				//部位尺寸赋值
				for(MeasurePosition measurePosition:positionPagination.getRows()){
					MesOrderDetailMeasure obj1 = (MesOrderDetailMeasure)objectMap.get(dto.getGoodsId()+measurePosition.getId()+measureSizeId);
					if(obj1!=null){
						map.put("样板尺寸:"+measurePosition.getName(),obj1.getModelNetSize());
						map.put("完成尺寸:"+measurePosition.getName(),obj1.getModelFinishSize());
					}else{
						map.put("样板尺寸:"+measurePosition.getName(),"0");
						map.put("完成尺寸:"+measurePosition.getName(),"0");
					}
				}

				lists.add(map);

			}

			Integer i = 11;
			headers = new String[i+pagination.getRows().size()+positionPagination.getRows().size()*2];
			headers[0]="唯一码";
			headers[1]="订单号";
			headers[2]="款号";
			headers[3]="款名";
			headers[4]="颜色";
			headers[5]="类别";
			headers[6]="顾客姓名";
			headers[7]="匹配尺码";
			headers[8]="版师";
			headers[9]="商品状态";
			headers[10]="技术完成日期";


			for(CategoryMeasureRelation categoryMeasureRelation:pagination.getRows()) {
				BodyMeasureInfo bodyMeasureInfo = categoryMeasureRelation.getBodyMeasureInfo();
				headers[i]=bodyMeasureInfo.getName();
				i++;
			}

			for(MeasurePosition measurePosition:positionPagination.getRows()){
				headers[i] = "样板尺寸:"+measurePosition.getName();
				++i;
				headers[i] = "完成尺寸:"+measurePosition.getName();
				++i;
			}


			out = response.getOutputStream();
			ExportExcelUtil.export2007Excel(0,0,0,"商品配码基本码尺寸分析" , headers,lists, out, "yyyy-MM-dd");
		} catch (IOException | IllegalArgumentException  e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private List<String> getSizeByGoodsId(String goodsId){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT a.c_name " +
				" FROM mes_measure_size a " +
				" JOIN t_goods b ON a.c_category_id = b.c_category_id " +
				" WHERE ifnull(a.c_del,'0')='0' AND IFNULL(a.c_status,'0') = '0' AND b.c_id ='"+goodsId+"' ORDER BY a.c_sort,a.c_name ");
		List<String> resultSize = dao.queryBySql(sql.toString());
		return resultSize;
	}

	private List<Map<String, Object>> getGoodsSizeExportData(String goodsId,String type) {
		StringBuilder sql = new StringBuilder();
		StringBuilder sqlSelect = new StringBuilder();
		StringBuilder sqlSum = new StringBuilder();
		List<String> resultSize = this.getSizeByGoodsId(goodsId);

		for(String size : resultSize) {
			if("1".equals(type)) {
				sqlSelect.append(",CASE WHEN d.c_name='" + size + "' THEN a.c_model_net_size ELSE 0 END AS '" + size + "'");
				sqlSum.append(",SUM(`" + size + "`) AS '" + size + "'");
			}else{
				sqlSelect.append(",CASE WHEN d.c_name='" + size + "' THEN a.c_model_finish_size ELSE 0 END AS '" + size + "'");
				sqlSum.append(",SUM(`" + size + "`) AS '" + size + "'");
			}
		}
		sql.append(" SELECT goodsCode,goodsName,positionName").append(sqlSum);
		sql.append(" FROM (");
		sql.append(" SELECT a.c_goods_id,b.c_code AS goodsCode,b.c_name AS goodsName,c.c_name AS positionName").append(sqlSelect);
		sql.append(" FROM mes_goods_measure_position_size a");
		sql.append(" JOIN t_goods b ON a.c_goods_id = b.c_id");
		sql.append(" JOIN mes_measure_position c ON a.c_measure_position_id = c.c_id");
		sql.append(" JOIN mes_measure_size d ON a.c_measure_size_id = d.c_id");
		sql.append(" WHERE a.c_del='0' AND a.c_goods_id ='"+goodsId+"'");
		sql.append(" ORDER BY a.c_goods_id,c.c_code,d.c_sort,d.c_code");
		sql.append(" ) tb GROUP BY c_goods_id,goodsCode,goodsName,positionName ");

		List resultSet = dao.queryBySql(sql.toString());

		List<Map<String, Object>> lists = new ArrayList<>();

		for (Object object : resultSet) {
			Object[] properties = (Object[])object;
			ListOrderedMap map = new ListOrderedMap();
			map.put("goodsCode", properties[0]==null?"":properties[0]);
			map.put("goodsName", properties[1]==null?"":properties[1]);
			map.put("positionName", properties[2]==null?"":properties[2]);
			int i = 3;
			for(String s:resultSize){
				map.put(""+s,properties[i]==null?"":properties[i]);
				++i;
			}
			lists.add(map);
		}
		return lists;
	}

}
