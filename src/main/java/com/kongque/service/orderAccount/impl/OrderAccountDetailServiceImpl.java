package com.kongque.service.orderAccount.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kongque.constants.Constants;
import com.kongque.dao.IDaoService;
import com.kongque.dto.OrderAccountDetailDto;
import com.kongque.entity.material.MaterialCategory;
import com.kongque.entity.order.OrderAccountDetail;
import com.kongque.entity.productionorder.MesOrderDetailMaterial;
import com.kongque.model.OrderAccountMesModel;
import com.kongque.service.orderAccount.IOrderAccountDetailService;
import com.kongque.service.production.basics.material.IMaterialCategoryService;
import com.kongque.service.productionorder.IMesOrderDetailService;
import com.kongque.util.DateUtil;
import com.kongque.util.HttpClientUtils;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;
import com.kongque.util.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class OrderAccountDetailServiceImpl implements IOrderAccountDetailService {
	
	@Autowired
	private IDaoService dao;
	
	@Autowired
	private IMesOrderDetailService mesOrderDetailService;
	
	@Autowired
	private IMaterialCategoryService materialCategoryService;
	

	@Override
	public Pagination<OrderAccountDetail> orderAccountDetailList(PageBean pageBean, OrderAccountDetailDto dto) {
		
		Pagination<OrderAccountDetail> pagination = new Pagination<>();
		Criteria criteria = dao.createCriteria(OrderAccountDetail.class);
		if(StringUtils.isNotBlank(dto.getOrderAccountId())) {
			criteria.add(Restrictions.eq("orderAccountId", dto.getOrderAccountId()));
		}
		List<OrderAccountDetail> findListWithPagebeanCriteria = dao.findListWithPagebeanCriteria(criteria, pageBean);
		Long total = dao.findTotalWithCriteria(criteria);
		
		pagination.setTotal(total);
		pagination.setRows(findListWithPagebeanCriteria);
		
		return pagination;
	}

	
	@Override
		
	public Result accountOrderByMonth(OrderAccountDetailDto dto) {
		Result result = new Result();
		List<OrderAccountDetail> orderAccountDetailList  = new ArrayList<>();
		List<String> orderDetailIdsList = new ArrayList<>();
		String accountMonth = dto.getAccountMonth();
		Integer year = Integer.parseInt(accountMonth.substring(0, 4));
		Integer month = Integer.parseInt(accountMonth.substring(5, 7));
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("ooda.c_order_code,");
		sql.append("ooda.c_order_character,");
		sql.append("ooda.c_xingyu_chek_time,");
		sql.append("ooda.c_id,");
		sql.append("ooda.c_goods_sn,");
		sql.append("ooda.c_goods_code,");
		sql.append("ooda.c_goods_name,");
		sql.append("ooda.c_goods_color_name,");
		sql.append("ooda.c_num,");
		sql.append("s.c_mes_measure_size_id ");
		sql.append("FROM ");
		sql.append("(");
		sql.append("SELECT ");
		sql.append("o.c_order_code,");
		sql.append("o.c_order_character,");
		sql.append("o.c_xingyu_chek_time,");
		sql.append("oda.c_id,");
		sql.append("oda.c_goods_sn,");
		sql.append("oda.c_goods_code,");
		sql.append("oda.c_goods_name,");
		sql.append("oda.c_goods_color_name,");
		sql.append("oda.c_num  ");
		sql.append("FROM ");
		sql.append("(");
		sql.append("SELECT ");
		sql.append("od.c_id,");
		sql.append("od.c_order_id,");
		sql.append("od.c_goods_sn,");
		sql.append("od.c_goods_code,");
		sql.append("od.c_goods_name,");
		sql.append("od.c_goods_color_name,");
		sql.append("od.c_num ");
		sql.append("FROM ");
		sql.append("(");
		sql.append("SELECT ");
		sql.append("b.c_order_detail_id,");
		sql.append("b.c_id ");
		sql.append("FROM ");
		sql.append("( SELECT a.c_id FROM mes_order_plan a WHERE  a.c_delete_flag = 0  ");
		if(StringUtils.isNotBlank(accountMonth)) {
			sql.append("and a.c_send_time LIKE ").append("'%"+ dto.getAccountMonth() +"%'");
		}
		sql.append(" ) c ");
		sql.append("LEFT JOIN mes_order_plan_detail b ON c.c_id = b.c_order_plan_id ");
		sql.append("WHERE ");
		sql.append("b.c_delete_flag = 0");
		sql.append(") d ");
		sql.append("LEFT JOIN t_order_detail od ON od.c_id = d.c_order_detail_id ");
		sql.append(") oda ");
		sql.append("LEFT JOIN t_order o ON o.c_id = oda.c_order_id ");
		sql.append("WHERE ");
		sql.append("o.c_delete_flag = 0  ");
		sql.append(") ooda ");
		sql.append("LEFT JOIN mes_order_detail_size s ON ooda.c_id = s.c_order_detail_id");

		@SuppressWarnings("rawtypes")
		List queryBySql = dao.queryBySql(sql.toString());
		for (Object object : queryBySql) {
			OrderAccountDetail accountDetail = new OrderAccountDetail(dto.getOrderAccountId());
			Object[] properties = (Object[])object;
			accountDetail.setOrderCode(properties[0]==null ? "" : properties[0].toString());
			accountDetail.setOrderCharacter(properties[1]==null ? "" : properties[1].toString());
			accountDetail.setOrderTime(properties[2]==null ? null: DateUtil.str2Date(properties[2].toString(), "yyyy-MM-dd HH:mm:ss"));
			accountDetail.setOrderDetailId(properties[3]==null ? "" : properties[3].toString());
			accountDetail.setGoodsSn(properties[4]==null ? "" : properties[4].toString());
			accountDetail.setGoodsCode(properties[5] == null ? "" : properties[5].toString());
			accountDetail.setGoodsName(properties[6]==null ? "" : properties[6].toString());
			accountDetail.setGoodsColorName(properties[7]==null ? "" : properties[7].toString());
			accountDetail.setNum(properties[8] == null ? 0 : Integer.parseInt(properties[8].toString()));
			accountDetail.setMesMeasureSizeId(properties[9]==null ? "":properties[9].toString());
	
			String orderDetailId = properties[3]==null ? "" : properties[3].toString();
			orderDetailIdsList.add(orderDetailId);
			orderAccountDetailList.add(accountDetail);
		} 
		if(orderAccountDetailList.size() == 0) {
//			result.setReturnCode("200");
//			result.setReturnMsg("本月没有要核算的订单");
			return result;
		}
		
		//获取所有物料
		String[] orderDetailIds ;
		if(orderDetailIdsList.size() > 0) {
			orderDetailIds = new String[orderDetailIdsList.size()];
		}else {
			orderDetailIds = new String[0];
		}
		String[] orderDetailIdArray = orderDetailIdsList.toArray(orderDetailIds);
		List<MesOrderDetailMaterial> listByOrderDateilList= mesOrderDetailService.getListByOrderDateilIds(orderDetailIdArray);
		//整合所有物料Id
		Set<String> materiaIdSet = new HashSet<>();
		String[] materialIds;
		for (MesOrderDetailMaterial mesOrderDetailMaterialModel : listByOrderDateilList) {
			materiaIdSet.add(mesOrderDetailMaterialModel.getMaterialId());
		}
		if(materiaIdSet.size() > 0) {
			materialIds = new String[materiaIdSet.size()];
		}else {
			materialIds = new String[0];
		}
		String[] materiaArray = materiaIdSet.toArray(materialIds);
		String materiaPost = HttpClientUtils.doPost(Constants.PRODUCTION.KONGQUE_PRODUCTION_MATERIA_DETAIL, year,month, materiaArray);
		
		//核算价格
		if(!JSONObject.fromObject(materiaPost).get("returnCode").equals("200")) {
			result.setReturnCode("500");
			result.setReturnMsg("调用仓库系统化接口失败");
			return result;
		}
		Object returnData = JSONObject.fromObject(materiaPost).get("returnData");
		
		//返回null表明,所需物料(全部)单价还没统计(仓库系统)
		if(returnData == null) {
			result.setReturnCode("500");
			result.setReturnMsg("核算失败,所需物料单价获取不到,仓库系统物料单价还未统计");
			return result;
		}
		//返回[],表明所需物料(全部)单价,从仓库系统中匹配不到.(仓库物料单价核算完成,但没有所需物料)
		JSONArray jsonArray = JSONArray.fromObject(returnData);
		if(jsonArray.size() == 0) {
			result.setReturnCode("500");
			result.setReturnMsg("核算失败,所有要核算物料的单价获取不到,仓库已核算的物料中不存在要核算的物料");
			return result;
		}
		//(当核算物料匹配不到仓库返回物料单价时,不予统计,此次核算失败)
		/*Set<String> materiaIdSetCopy = new HashSet<>();//复制物料id集合,移除仓库那边能获取到的物料id,统计出获取不到的id
		Set<String> getmateriaIdYesSet = new HashSet<>();
		materiaIdSetCopy.addAll(materiaIdSet);
		for (String materiaId : materiaIdSetCopy) {
			for (Object object : jsonArray) {
				JSONObject json = (JSONObject) object;
				if(materiaId.equals(json.get("materialId").toString())) {
					getmateriaIdYesSet.add(materiaId);
				}
			}
		}
		boolean removeAll = materiaIdSetCopy.removeAll(getmateriaIdYesSet);//移除从从库系统能够获取到的物料Id,剩下的就是仓库那边获取不到的物料id
		if(removeAll && materiaIdSetCopy.size() > 0) {
			result.setReturnCode("500");
			result.setReturnMsg("核算失败,要核算的物料单价仓库获取不到,仓库已核算的物料中不存在要核算的物料,返回数据,就是不存在的物料id" );
			result.setReturnData(materiaIdSetCopy);
			return result;
		}*/
		
		// 遍历核算单明细列表,并核算成本.
		for (OrderAccountDetail orderAccountDetail : orderAccountDetailList) {
			Integer goodNum = orderAccountDetail.getNum();
			BigDecimal goodNumBD = new BigDecimal(goodNum);
			String orderDetailId = orderAccountDetail.getOrderDetailId();
			BigDecimal sumPrice = new BigDecimal("0.00");//成本初始化为0(当单价没算的时候默认)
			
			for (MesOrderDetailMaterial mesOrderDetailMaterial : listByOrderDateilList) {//每一个商品匹配所需物料
				if (orderDetailId.equals(mesOrderDetailMaterial.getOrderDetailId())) {
					String materialId = mesOrderDetailMaterial.getMaterialId();
					String materiaNum = mesOrderDetailMaterial.getNum();
					BigDecimal materiaNumBD;
					if (StringUtils.isNotBlank(materiaNum)) {
						materiaNumBD = new BigDecimal(materiaNum);
					} else {
						materiaNumBD = new BigDecimal("0.00");
					}
					for (Object resp : jsonArray) {//匹配物料单价,算出物料成本(物料数量 * 单价)
						JSONObject jsonResp = (JSONObject) resp;
						if(materialId.equals(jsonResp.get("materialId").toString())) {
							String price = jsonResp.get("price").toString();
							BigDecimal priceBD;
							if(StringUtils.isBlank(price)) {
								priceBD = new BigDecimal("0.00");
							}else {
								priceBD = new BigDecimal(price);
							}
						
							BigDecimal multiply = priceBD.multiply(materiaNumBD);
							multiply = multiply.setScale(2, BigDecimal.ROUND_HALF_UP);
							sumPrice = sumPrice.add(multiply);
						}
					}
				}
			}//每件商品结束
			orderAccountDetail.setAccountCostPrice(goodNumBD.multiply(sumPrice));
		}
		dao.saveAllEntity(orderAccountDetailList);
		System.out.println("该月核算订单数 : " + orderAccountDetailList.size());
		return result;
	}

	@Override
	public Result materiaDetail(OrderAccountDetailDto dto) {
		BigDecimal totalPrice = new BigDecimal("0");
		
		Result result = new Result();
		List<OrderAccountMesModel> modelList = new ArrayList<>(); 
		String orderDetailId = dto.getOrderDetailId();
		//获取所需物料
		@SuppressWarnings("unchecked")
		List<MesOrderDetailMaterial> materialList = (List<MesOrderDetailMaterial>) mesOrderDetailService.getAssignMaterialByOrderDetailId(orderDetailId).getReturnData();
		if(materialList.size() == 0) {
			result.setReturnMsg("该订单没有查到所需物料");
			return result;
		}
		//获取物料id数组(请求库存工程参数)
		Set<String> materiaIdSet = new HashSet<>();
		if(materialList != null && materialList.size() >0) {
			for (MesOrderDetailMaterial mesOrderDetailMaterial : materialList) {
				materiaIdSet.add(mesOrderDetailMaterial.getMaterialId());
			}
		}
		String[] materiaIdArrayLenth = new String[materiaIdSet.size()];
		String[] materiaIdArray = materiaIdSet.toArray(materiaIdArrayLenth);
		//获取统计的时间(请求库存工程參數)
		String accountMonth = dto.getAccountMonth();
		int year = Integer.parseInt(accountMonth.substring(0, 4));
		int month = Integer.parseInt(accountMonth.substring(5, 7));
		@SuppressWarnings("static-access")
		String materiaPost = HttpClientUtils.doPost(Constants.PRODUCTION.KONGQUE_PRODUCTION_MATERIA_DETAIL,year,month,materiaIdArray);
		if(!JSONObject.fromObject(materiaPost).get("returnCode").equals("200")) {
			result.setReturnCode("500");
			result.setReturnMsg("调用仓库系统化接口失败");
			return result;
		}
		JSONArray jsonArray = JSONArray.fromObject(JSONObject.fromObject(materiaPost).get("returnData"));
		//获取面料分类集合
		Set<String> materiaCategoryIdSet = new HashSet<>();
		for (MesOrderDetailMaterial mesOrderDetailMaterial : materialList) {
			materiaCategoryIdSet.add(mesOrderDetailMaterial.getMaterialCategoryId());
		}
		Set<String> newIdSet = new HashSet<>();
		for (String string : materiaCategoryIdSet) {
				string = "'"+string+"'";
				newIdSet.add(string);
		}
		String[] newIdsArrayLength = new String[newIdSet.size()];
		String[] idsarray = newIdSet.toArray(newIdsArrayLength);
		List<MaterialCategory> materialCategoryList = materialCategoryService.getListByIds(idsarray);
		
		for (MesOrderDetailMaterial mesOrderDetailMaterial : materialList) {
			OrderAccountMesModel model = new OrderAccountMesModel();
			
			String materiaId = mesOrderDetailMaterial.getMaterialId();
			String code = mesOrderDetailMaterial.getCode();
			String name = mesOrderDetailMaterial.getName();
			String inventoryUnit = mesOrderDetailMaterial.getInventoryUnit();
			String spec = mesOrderDetailMaterial.getSpec();
			String materialClassName = "";
			String quality = mesOrderDetailMaterial.getQuality();
			String color = mesOrderDetailMaterial.getColor();
			String num = mesOrderDetailMaterial.getNum();
			String materialCategoryId = mesOrderDetailMaterial.getMaterialCategoryId();
			String colorValue = "";
			String price = "";
			BigDecimal sumPrice = new BigDecimal("0.00");
			
			MaterialCategory category = new MaterialCategory();
			for (MaterialCategory materialCategory : materialCategoryList) {
				if(materialCategoryId.equals(materialCategory.getId())) {
					category = materialCategory;
				}
			}
			for (Object resp : jsonArray) {
				JSONObject json = (JSONObject) resp;
				if(materiaId.equals(json.get("materialId").toString())) {
					String materialFirstClass = json.getString("materialFirstClass").toString();
					String materialSecondClass = json.getString("materialSecondClass").toString();
					String materialThirdClass = json.get("materialClass").toString();
					materialClassName = materialFirstClass+"-"+materialSecondClass+"-"+materialThirdClass;
					price = json.get("price").toString();
					/*System.out.println("单价 : " + price);
					System.out.println("数量 :" +num);*/
					colorValue = json.get("colorValue").toString();
					BigDecimal priceBD;
					if(StringUtils.isBlank(price)) {
						priceBD = new BigDecimal("0");
					}else {
						priceBD = new BigDecimal(price);
					}
					BigDecimal multiply = new BigDecimal(num).multiply(priceBD);
					multiply = multiply.setScale(2, BigDecimal.ROUND_HALF_UP);
					sumPrice = multiply;
//					System.out.println("每个物料价格 : " + sumPrice);
				}
			}
			//组装返回参数
			model.setColor(color);
			model.setColorValue(colorValue);
			model.setCount(num);
			model.setMateriaClasslName(materialClassName);
			model.setMateriaId(materiaId);
			model.setMateriaName(name);
			model.setQuality(quality);
			model.setSpec(spec);
			model.setSumPrice(sumPrice);
			model.setUnit(inventoryUnit);
			model.setUnitPrice(price);
			model.setMesMaterialCategory(category);
			model.setMateriaCode(code);
			
			totalPrice = totalPrice.add(sumPrice);
			
			modelList.add(model);
		}
		System.out.println("每件商品价格:"+totalPrice);
		result.setReturnData(modelList);
		return result;
	}

	@Override
	public int deleteByOrderAccountId(OrderAccountDetailDto dto) {
		String sql = "DELETE FROM t_order_account_detail WHERE c_order_account_id = ";
		sql = sql + "'"+dto.getOrderAccountId()+"'";
		int deleteBySql = dao.deleteBySql(sql);
		return deleteBySql;
	}
	
}
