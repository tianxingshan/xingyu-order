package com.kongque.service.production.basics.material.impl;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.*;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.kongque.component.impl.JsonMapper;
import com.kongque.constants.Constants;
import com.kongque.dao.IDaoService;
import com.kongque.dto.GoodsPositionMaterialDto;
import com.kongque.entity.material.GoodsPositionMaterial;
import com.kongque.model.GoodsDetailModel;
import com.kongque.service.production.basics.material.IGoodsPositionMaterialService;
import com.kongque.util.HttpClientUtils;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

@Transactional
@Service
public class GoodsPositionMaterialServiceImpl implements IGoodsPositionMaterialService{

	@Resource
	private IDaoService dao;
	
	@Override
	public Pagination<GoodsPositionMaterial> list(GoodsPositionMaterialDto dto,PageBean pageBean){
		Pagination<GoodsPositionMaterial> pagination=new Pagination<>();
		Criteria criteria=dao.createCriteria(GoodsPositionMaterial.class);

		if(StringUtils.isBlank(dto.getGoodsDetailId())){
			pagination.setTotal(0);
			return pagination;
		}
		criteria.add(Restrictions.eq("goodsDetailId", dto.getGoodsDetailId()));
		if(StringUtils.isNotBlank(dto.getMeasureSizeId())){
			criteria.add(Restrictions.eq("measureSizeId", dto.getMeasureSizeId()));
		}
		if(dto.getMeasureSizeIds()!=null && dto.getMeasureSizeIds().length>0){
			criteria.add(Restrictions.in("measureSizeId", dto.getMeasureSizeIds()));
		}
		if(StringUtils.isNotBlank(dto.getMaterialPositionId())){
			criteria.add(Restrictions.eq("materialPositionId", dto.getMaterialPositionId()));
		}
		criteria.createAlias("materialPosition","materialPosition");
		criteria.addOrder(Order.asc("materialPosition.code"));
		List<GoodsPositionMaterial> list = dao.findListWithPagebeanCriteria(criteria, pageBean);
		Set<String> set = new HashSet<>();
		for (GoodsPositionMaterial goodsPositionMaterial: list) {
			set.add(goodsPositionMaterial.getMaterialId());
		}
		set.add("");
		Map<String,String> map = new HashMap<>();
		map.put("ids",set.toString());
		map.put("page","1");
		map.put("rows",String.valueOf(Integer.MAX_VALUE));
		Result result = JsonMapper.toObject(HttpClientUtils.doGet(Constants.PRODUCTION.KONGQUE_PRODUCTION_INFOMATION,
				map), Result.class);
		// 如果数据正常返回
		if (Constants.RESULT_CODE.SUCCESS.equals(result.getReturnCode())) {
			List<Map<String, Object>> lists = (List<Map<String, Object>>)result.getRows();
			for (GoodsPositionMaterial goodsPositionMaterial: list) {
				for (Map<String, Object> resMap : lists) {
					if (goodsPositionMaterial.getMaterialId().equals(resMap.get("id"))){
						goodsPositionMaterial.setStatus(String.valueOf(resMap.get("status")));
					}
				}
			}
		}else{
			pagination.setReturnMsg(result.getReturnMsg());
			pagination.setReturnCode(result.getReturnCode());
			return pagination;
		}
		pagination.setRows(list);
		pagination.setTotal(dao.findTotalWithCriteria(criteria));
		return pagination;
	}
	
	@Override
	public Result saveOrUpdate(GoodsPositionMaterialDto dto){
		for (int i = 0; i < dto.getPositionList().size(); i++) {
			if (StringUtils.isNotBlank(dto.getPositionList().get(i).getId())){
				GoodsPositionMaterial gpm = dao.findById(GoodsPositionMaterial.class,dto.getPositionList().get(i).getId());
				if (null!=gpm){
					gpm.setCode(dto.getPositionList().get(i).getCode());
					gpm.setName(dto.getPositionList().get(i).getName());
					gpm.setInventoryUnit(dto.getPositionList().get(i).getInventoryUnit());
					gpm.setSpec(dto.getPositionList().get(i).getSpec());
					gpm.setQuality(dto.getPositionList().get(i).getQuality());
					gpm.setNum(dto.getPositionList().get(i).getNum());
					gpm.setColor(dto.getPositionList().get(i).getColor());
					gpm.setColorCode(dto.getPositionList().get(i).getColorCode());
					gpm.setMaterialName(dto.getPositionList().get(i).getMaterialName());
					dao.saveOrUpdate(gpm);
				}
			}else {
				for (int j = 0; j < dto.getMeasureSizeIds().length; j++) {
					GoodsPositionMaterial gpm = new GoodsPositionMaterial();
					gpm.setGoodsDetailId(dto.getGoodsDetailId());
					gpm.setMeasureSizeId(dto.getMeasureSizeIds()[j]);
					gpm.setMaterialPositionId(dto.getMaterialPositionId());
					gpm.setMaterialId(dto.getPositionList().get(i).getMaterialId());
					gpm.setCode(dto.getPositionList().get(i).getCode());
					gpm.setName(dto.getPositionList().get(i).getName());
					gpm.setInventoryUnit(dto.getPositionList().get(i).getInventoryUnit());
					gpm.setMaterialCategoryId(dto.getPositionList().get(i).getMaterialCategoryId());
					gpm.setSpec(dto.getPositionList().get(i).getSpec());
					gpm.setQuality(dto.getPositionList().get(i).getQuality());
					gpm.setNum(dto.getPositionList().get(i).getNum());
					gpm.setColor(dto.getPositionList().get(i).getColor());
					gpm.setColorCode(dto.getPositionList().get(i).getColorCode());
					gpm.setMaterialName(dto.getPositionList().get(i).getMaterialName());
					dao.save(gpm);
				}
			}
		}
		Criteria criteria=dao.createCriteria(GoodsPositionMaterial.class);
		criteria.add(Restrictions.eq("goodsDetailId", dto.getGoodsDetailId()));
		criteria.add(Restrictions.in("measureSizeId", dto.getMeasureSizeIds()));
		criteria.add(Restrictions.eq("materialPositionId", dto.getMaterialPositionId()));
		return new Result(criteria.list());
	}

	@Override
	public Result del(GoodsPositionMaterialDto dto){
		String[] ids = dto.getIds();
		if (null==dto.getIds()){
			return new Result();
		}
		for (int i = 0; i < ids.length; i++) {
			GoodsPositionMaterial positionMaterial = dao.findById(GoodsPositionMaterial.class, ids[i]);
			dao.delete(positionMaterial);
		}
		return new Result("200","删除成功");
	}
	
	
	@Override
	public Result getMaterialCode(String materialCode){
		Map<String, String> map = new HashMap<String, String>();
		map.put("materialCode", materialCode);
		Result result = JsonMapper.toObject(HttpClientUtils.doGet(Constants.PRODUCTION.KONGQUE_PRODUCTION_LIST,
				map), Result.class);
		// 如果数据正常返回
		if (Constants.RESULT_CODE.SUCCESS.equals(result.getReturnCode())) {
			return result;
		}else{
			return new Result(result.getReturnCode(), result.getReturnMsg());
		}
	}

	@Override
	public Result getMaterial(String materialCode, PageBean pageBean){
		Map<String, String> map = new HashMap<String, String>();
		map.put("materialCode", materialCode);
		map.put("page", pageBean.getPage()==null?"1":pageBean.getPage().toString());
		map.put("rows", pageBean.getRows()==null?"10":pageBean.getRows().toString());
		Result result = JsonMapper.toObject(HttpClientUtils.doGet(Constants.PRODUCTION.KONGQUE_PRODUCTION_INFOMATION,
				map), Result.class);
		// 如果数据正常返回
		if (Constants.RESULT_CODE.SUCCESS.equals(result.getReturnCode())) {
			return result;
		}else{
			return new Result(result.getReturnCode(), result.getReturnMsg());
		}
	}
	
	@Override
	public Result getMaterialInfomation(String materialCode){
		Map<String, String> map = new HashMap<String, String>();
		map.put("materialCode", materialCode);
		Result result = JsonMapper.toObject(HttpClientUtils.doGet(Constants.PRODUCTION.KONGQUE_PRODUCTION_INFOMATION,
				map), Result.class);
		// 如果数据正常返回
		if (Constants.RESULT_CODE.SUCCESS.equals(result.getReturnCode())) {
			return result;
		}else{
			return new Result(result.getReturnCode(), result.getReturnMsg());
		}
	}
	
	@Override
	public List<GoodsDetailModel> queryDetailWithParam(String code,String categoryId,PageBean pageBean){
		List<GoodsDetailModel> odsModelList = new ArrayList<>();
		StringBuilder sql = new StringBuilder("select a.c_goods_id as goodsId,a.c_id as goodsDetailId,b.c_code as goodsCode,b.c_name as goodsName,a.c_color_name as goodsColorName,c.c_id as categoryId,c.c_name as categoryName from t_goods_detail a left join t_goods b on a.c_goods_id=b.c_id left join t_category c on b.c_category_id=c.c_id where 1=1 ");
		if(StringUtils.isNotBlank(code)){
			sql.append(" and (b.c_code like '%").append(code).append("%'").append(" or b.c_name like '%").append(code).append("%') ");
		}
		if(StringUtils.isNotBlank(categoryId)){
			sql.append(" and c.c_id = '").append(categoryId).append("'");
		}
		if(pageBean.getPage()!=null && pageBean.getRows()!=null){
			sql.append(" limit "+(pageBean.getPage() - 1) * pageBean.getRows()+","+pageBean.getRows());
		}
		List<Object> resultSet = dao.queryBySql(sql.toString());		
		for(Object result : resultSet){
			GoodsDetailModel odsModel = new GoodsDetailModel();//构建返回数据模型
			Object[] properties = (Object[])result;
			odsModel.setGoodsId(properties[0]==null ? "" : properties[0].toString());
			odsModel.setGoodsDetailId(properties[1]==null ? "" : properties[1].toString());
			odsModel.setGoodsCode(properties[2]==null ? "" : properties[2].toString());
			odsModel.setGoodsName(properties[3]==null ? "" : properties[3].toString());
			odsModel.setGoodsColor(properties[4]==null ? "" : properties[4].toString());
			odsModel.setCategotyId(properties[5]==null ? "" : properties[5].toString());
			odsModel.setCategotyName(properties[6]==null ? "" : properties[6].toString());
			odsModelList.add(odsModel); 
		}
		return 	odsModelList;
	}

	@Override
	public Result notAssignList(String goodsDetailId, String measureSizeId, String orderDetailId) {
		if (StringUtils.isBlank(goodsDetailId) || StringUtils.isBlank(measureSizeId) || StringUtils.isBlank(orderDetailId)){
			return new Result("400","商品、尺码、订单明细id不能为空");
		}
		List<GoodsPositionMaterial> list = dao.queryByHql("from GoodsPositionMaterial gpm where goodsDetailId='"+goodsDetailId+"' and measureSizeId='"+measureSizeId+"' and not exists (select 1 from MesOrderDetailMaterial modm where orderDetailId='"+orderDetailId+"' and gpm.materialPositionId=modm.materialPositionId and gpm.code=modm.code ) ");
		return new Result(list);
	}

	@Override
	public Long queryCountWithParam(String code,String categoryId) throws ParseException{
		StringBuilder sql = new StringBuilder("SELECT count(*) FROM t_goods_detail a left join t_goods b on a.c_goods_id=b.c_id left join t_category c on b.c_category_id=c.c_id where 1=1 ");
		if(StringUtils.isNotBlank(code)){
			sql.append(" and (b.c_code like '%").append(code).append("%'").append(" or b.c_name like '%").append(code).append("%') ");
		}
		if(StringUtils.isNotBlank(categoryId)){
			sql.append(" and c.c_id = '").append(categoryId).append("'");
		}
		List<BigInteger> result = dao.queryBySql(sql.toString());
		return result == null || result.isEmpty() ? 0L : result.get(0).longValue();
	}
	
	@Override
	public Result copyMaterial(GoodsPositionMaterialDto dto){
		String[] sizes = new String[]{""};
		Criteria criteria=dao.createCriteria(GoodsPositionMaterial.class);
		if (StringUtils.isBlank(dto.getGoodsDetailId())){
			return new Result();
		}
		criteria.add(Restrictions.eq("goodsDetailId", dto.getGoodsDetailId()));
		if (null!=dto.getMeasureSizeIds()&&dto.getMeasureSizeIds().length>0){
			sizes = dto.getMeasureSizeIds();
		}
		//如果传入measureSizeId，会覆盖measureSizeIds数组
		if (StringUtils.isNotBlank(dto.getMeasureSizeId())){
			sizes = new String[]{dto.getMeasureSizeId()};
		}
		criteria.add(Restrictions.in("measureSizeId", dto.getMeasureSizeIds()));
		//获取款式部位物料
		@SuppressWarnings("unchecked")
		List<GoodsPositionMaterial> list = criteria.list();
		Map<String,String> keyMap = new HashMap<>();
		if(list!=null && list.size()>0){
			for (int k = 0; k < list.size(); k++) {
//				dao.delete(list.get(k));
				GoodsPositionMaterial bean = list.get(k);
				String key = bean.getMeasureSizeId()+bean.getMaterialPositionId()+bean.getMaterialId();
				if (StringUtils.isBlank(keyMap.get(key))){
					keyMap.put(key,bean.getMaterialId());
				}
			}
		}
		//遍历尺码数组
		for (String sizeId : sizes) {
			//遍历复制的款式部位物料
			for (int i = 0; i < dto.getPositionList().size(); i++) {
				GoodsPositionMaterial goodsPositionMaterial = dto.getPositionList().get(i);
				//如果没有该物料，新增该物料
				String key = sizeId+goodsPositionMaterial.getMaterialPositionId()+goodsPositionMaterial.getMaterialId();
				if (keyMap.get(key)==null){
					GoodsPositionMaterial gpm = new GoodsPositionMaterial();
					gpm.setGoodsDetailId(dto.getGoodsDetailId());
					gpm.setMeasureSizeId(sizeId);
					gpm.setMaterialPositionId(dto.getPositionList().get(i).getMaterialPositionId());
					gpm.setCode(dto.getPositionList().get(i).getCode());
					gpm.setName(dto.getPositionList().get(i).getName());
					gpm.setInventoryUnit(dto.getPositionList().get(i).getInventoryUnit());
					gpm.setMaterialCategoryId(dto.getPositionList().get(i).getMaterialCategoryId());
					gpm.setSpec(dto.getPositionList().get(i).getSpec());
					gpm.setQuality(dto.getPositionList().get(i).getQuality());
					gpm.setNum(dto.getPositionList().get(i).getNum());
					gpm.setColor(dto.getPositionList().get(i).getColor());
					gpm.setColorCode(dto.getPositionList().get(i).getColorCode());
					gpm.setMaterialId(dto.getPositionList().get(i).getMaterialId());
					gpm.setMaterialName(dto.getPositionList().get(i).getMaterialName());
					dao.save(gpm);
				}
			}
		}
		return new Result(list);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result batchCopyMaterial(GoodsPositionMaterialDto dto) {
		String[] sizes = new String[]{""};
		Criteria criteria=dao.createCriteria(GoodsPositionMaterial.class);
		criteria.add(Restrictions.eq("goodsDetailId", dto.getGoodsDetailId()));
		if (null!=dto.getMeasureSizeIds()&&dto.getMeasureSizeIds().length>0){
			sizes = dto.getMeasureSizeIds();
		}
		criteria.add(Restrictions.in("measureSizeId", sizes));
		//获取款式部位物料
		List<GoodsPositionMaterial> list = criteria.list();
		Map<String,String> keyMap = new HashMap<>();
		for (GoodsPositionMaterial bean : list) {
			if (null!=bean){
				String key = bean.getMeasureSizeId()+bean.getMaterialPositionId()+bean.getMaterialId();
				if (StringUtils.isBlank(keyMap.get(key))){
					//该款式下已存在的尺码部位物料扔进map
					keyMap.put(key,key);
				}
			}

		}
		//遍历复制的款式部位物料
		for (int i = 0; i < dto.getPositionList().size(); i++) {
			GoodsPositionMaterial goodsPositionMaterial = dto.getPositionList().get(i);
			String key = goodsPositionMaterial.getMeasureSizeId()+goodsPositionMaterial.getMaterialPositionId()+goodsPositionMaterial.getMaterialId();
			//如果该款式尺码部位下没有该物料，新增该物料
			if (StringUtils.isBlank(keyMap.get(key))){
				GoodsPositionMaterial gpm = new GoodsPositionMaterial();
				gpm.setGoodsDetailId(dto.getGoodsDetailId());
				gpm.setMeasureSizeId(goodsPositionMaterial.getMeasureSizeId());
				gpm.setMaterialPositionId(goodsPositionMaterial.getMaterialPositionId());
				gpm.setCode(goodsPositionMaterial.getCode());
				gpm.setName(goodsPositionMaterial.getName());
				gpm.setInventoryUnit(goodsPositionMaterial.getInventoryUnit());
				gpm.setMaterialCategoryId(goodsPositionMaterial.getMaterialCategoryId());
				gpm.setSpec(goodsPositionMaterial.getSpec());
				gpm.setQuality(goodsPositionMaterial.getQuality());
				gpm.setNum(goodsPositionMaterial.getNum());
				gpm.setColor(goodsPositionMaterial.getColor());
				gpm.setColorCode(goodsPositionMaterial.getColorCode());
				gpm.setMaterialId(goodsPositionMaterial.getMaterialId());
				gpm.setMaterialName(goodsPositionMaterial.getMaterialName());
				dao.save(gpm);
			}
		}
		return new Result(list);
	}
}
