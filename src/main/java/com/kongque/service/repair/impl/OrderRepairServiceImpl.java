package com.kongque.service.repair.impl;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kongque.entity.productionRepair.MesRepairProductionPlan;
import com.kongque.entity.productionRepair.MesRepairProductionPlanDetail;
import com.kongque.util.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kongque.constants.Constants;
import com.kongque.dao.IDaoService;
import com.kongque.dto.OrderRepairDto;
import com.kongque.entity.basics.Code;
import com.kongque.entity.basics.XiuyuShopDirectorRelation;
import com.kongque.entity.goods.Goods;
import com.kongque.entity.goods.GoodsDetail;
import com.kongque.entity.order.OrderDetail;
import com.kongque.entity.repair.OrderRepair;
import com.kongque.entity.repair.OrderRepairAttachment;
import com.kongque.entity.repair.OrderRepairCheck;
import com.kongque.entity.user.UserDeptRelation;
import com.kongque.entity.user.UserRoleRelation;
import com.kongque.model.OrderRepairModel;
import com.kongque.service.repair.IOrderRepairService;

import net.sf.json.JSONArray;

@Service
public class OrderRepairServiceImpl implements IOrderRepairService {
	@Resource
	private IDaoService dao;
	@Resource
	private FileOSSUtil fileOSSUtil;

	/**
	 * 根据条件分页查询微调单
	 * 
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@Override
	public Pagination<OrderRepairModel> list(OrderRepairDto dto, PageBean pageBean) {
		Pagination<OrderRepairModel> pagination = new Pagination<OrderRepairModel>();
		if (pageBean.getPage() == null) {
			pageBean.setPage(1);
		}
		if (pageBean.getRows() == null) {
			pageBean.setRows(9999);
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select 	orderrepair.c_id,orderrepair.c_order_repair_code,"
				+ "orderrepair.c_customer_name,orderrepair.c_order_character, 	"
				+ "orderrepair.c_customer_code,  orderrepair.c_city,  orderrepair.c_shop_name, 	"
				+ "tg.c_name goods_name,  orderrepair.c_order_repair_status,orderrepair.c_repair_person, 	"
				+ "orderrepair.c_is_extract,orderrepair.c_check_time,"
				+ "max(case logistic.c_logistic_type when '0' then logistic.c_express_number else null end) 'send',	"
				+ "	max(case logistic.c_logistic_type when '1' then logistic.c_express_number else null end) 'reseve',	"
				+ "orderrepair.c_goods_code,  orderrepair.c_goods_color,  orderrepair.c_num, 		"
				+ "orderrepair.c_solution,  orderdetail.c_goods_sn 		,"
				+ "orderrepair.c_extended_file_name ,orderrepair.c_description "
				+ "from t_order_repair orderrepair 	" +
				" LEFT JOIN t_goods_detail tgd ON orderrepair.c_goods_detail_id = tgd.c_id " +
				" LEFT JOIN t_goods tg ON tgd.c_goods_id = tg.c_id "
				+ "left join t_logistic_order logisticorder on orderrepair.c_id=logisticorder.c_order_repair_id   		"
				+ "left join t_logistic logistic on logisticorder.c_logistic_id=logistic.c_id    		"
				+ "left join t_order_detail  orderdetail on orderrepair.c_order_detail_id = orderdetail.c_id where orderrepair.c_del = '0' ");

//		@SuppressWarnings("unchecked")
//		List<UserRoleRelation> users = dao.createCriteria(UserRoleRelation.class)
//				.add(Restrictions.eq( "userId", dto.getUserId())).list();
//		UserDeptRelation dept = dao.findUniqueByProperty(UserDeptRelation.class, "userId", dto.getUserId());
//		for (UserRoleRelation user : users) {
//			if (Constants.XIUYU_ROLE_ID.equalsIgnoreCase(user.getUserRole().getId())){
//				sql.append(" and orderrepair.c_shop_id in (").append(convertShopIds(getShopList(user))).append(")");
//				break;
//			} else if (Constants.DIANYUAN_ROLE_ID.equalsIgnoreCase(user.getUserRole().getId())
//					||Constants.JIAMENG_ROLE_ID.equalsIgnoreCase(user.getUserRole().getId())){
//				sql.append(" and orderrepair.c_shop_id = '" + dept.getDeptId() + "' ");
//				break;
//			}
//		}

		if (StringUtils.isNotBlank(dto.getOrderRepairCode())) {
			sql.append(" and orderrepair.c_order_repair_code ='" + dto.getOrderRepairCode() + "' ");
		}
		if (StringUtils.isNotBlank(dto.getCustomerInfo())) {
			sql.append(" and ((orderrepair.c_customer_name like '%" + dto.getCustomerInfo()
					+ "%')  or  (orderrepair.c_customer_code like '%" + dto.getCustomerInfo() + "%')) ");
		}
		if (StringUtils.isNotBlank(dto.getCity())) {
			sql.append(" and orderrepair.c_city  like '%" + dto.getCity() + "%' ");
		}
		if (StringUtils.isNotBlank(dto.getShopId())) {
			sql.append(" and orderrepair.c_shop_id ='" + dto.getShopId() + "' ");
		}
		if (StringUtils.isNotBlank(dto.getShopName())) {
			sql.append(" and orderrepair.c_shop_name like '%" + dto.getShopName() + "%' ");
		}
		if (StringUtils.isNotBlank(dto.getGoodsName())) {
			sql.append(" and tg.c_name  like '%" + dto.getGoodsName() + "%' ");
		}
		if (StringUtils.isNotBlank(dto.getOrderCharacter())) {
			sql.append(" and orderrepair.c_order_character ='" + dto.getOrderCharacter() + "' ");
		}
		if (StringUtils.isNotBlank(dto.getOrderRepairStatus())) {
			sql.append(" and orderrepair.c_order_repair_status ='" + dto.getOrderRepairStatus() + "' ");
		}
		if (StringUtils.isNotBlank(dto.getIsExtract())) {
			sql.append(" and orderrepair.c_is_extract ='" + dto.getIsExtract() + "' ");
		}
		if (StringUtils.isNotBlank(dto.getRepairPerson())) {
			sql.append(" and orderrepair.c_repair_person  like '%" + dto.getRepairPerson() + "%' ");
		}
		if (StringUtils.isNotBlank(dto.getExpressNumber())) {
			sql.append(" and logistic.c_express_number ='" + dto.getExpressNumber() + "' ");
		}
		// 审核时间
		if (dto.getStartCheckTime() != null && !dto.getStartCheckTime().isEmpty() && dto.getEndCheckTime() != null
				&& !dto.getEndCheckTime().isEmpty()) {// 添加同时包含起始订单日期和截止订单日期的限定条件
			sql.append(" and orderrepair.c_check_time between '").append(dto.getStartCheckTime()).append(" 00:00:00'")
					.append(" and '").append(dto.getEndCheckTime()).append(" 23:59:59'");
		} else if (dto.getStartCheckTime() != null && !dto.getStartCheckTime().isEmpty()
				&& (dto.getEndCheckTime() == null || dto.getEndCheckTime().isEmpty())) {// 添加只包含起始订单日期而不包含截止订单日期的限定条件
			sql.append(" and orderrepair.c_check_time between '").append(dto.getStartCheckTime()).append(" 00:00:00'")
					.append(" and '").append(dto.getStartCheckTime()).append(" 23:59:59'");
		} else if ((dto.getStartCheckTime() == null || dto.getStartCheckTime().isEmpty())
				&& dto.getEndCheckTime() != null && !dto.getEndCheckTime().isEmpty()) {// 添加只包含截止订单日期而不包含起始订单日期的限定条件
			sql.append(" and orderrepair.c_check_time  between '").append(dto.getEndCheckTime()).append(" 00:00:00'")
					.append(" and '").append(dto.getEndCheckTime()).append(" 23:59:59'");
		}
		sql.append("  group by orderrepair.c_id order by orderrepair.c_order_repair_code desc ");
		int total = dao.queryBySql(sql.toString()).size();
		pagination.setTotal(total);
		if (pageBean.getPage() != null && pageBean.getRows() != null) {
			sql.append("  limit " + (pageBean.getPage() - 1) * pageBean.getRows() + "," + pageBean.getRows());
		}
		List<OrderRepairModel> lrbm = new ArrayList<>();
		List resultSet = dao.queryBySql(sql.toString());
		if (resultSet != null && resultSet.size() > 0) {
			for (Object result : resultSet) {
				OrderRepairModel rbm = new OrderRepairModel();
				Object[] properties = (Object[]) result;
				rbm.setId(properties[0] == null ? "" : properties[0].toString());
				rbm.setOrderRepairCode(properties[1] == null ? "" : properties[1].toString());
				rbm.setCustomerName(properties[2] == null ? "" : properties[2].toString());
				rbm.setOrderCharacter(properties[3] == null ? "" : properties[3].toString());
				rbm.setCustomerCode(properties[4] == null ? "" : properties[4].toString());
				rbm.setCity(properties[5] == null ? "" : properties[5].toString());
				rbm.setShopName(properties[6] == null ? "" : properties[6].toString());
				rbm.setGoodsName(properties[7] == null ? "" : properties[7].toString());
				rbm.setOrderRepairStatus(properties[8] == null ? "" : properties[8].toString());
				rbm.setRepairPerson(properties[9] == null ? "" : properties[9].toString());
				rbm.setIsExtract(properties[10] == null ? "" : properties[10].toString());
				rbm.setCheckTime(properties[11] == null ? "" : properties[11].toString());
				rbm.setSendExpressNumber(properties[12] == null ? "" : properties[12].toString());
				rbm.setReceiveExpressNumber(properties[13] == null ? "" : properties[13].toString());
				rbm.setGoodsCode(properties[14] == null ? "" : properties[14].toString());
				rbm.setGoodsColor(properties[15] == null ? "" : properties[15].toString());
				rbm.setNum(properties[16] == null ? "" : properties[16].toString());
				rbm.setSolution(properties[17] == null ? "" : properties[17].toString());
				rbm.setGoodsSn(properties[18] == null ? "" : properties[18].toString());
				rbm.setExtendedFileName(properties[19] == null ? "" : properties[19].toString());
				rbm.setDescription(properties[20] == null ? "" : properties[20].toString());

				lrbm.add(rbm);
			}
		}
		pagination.setRows(lrbm);

		return pagination;
	}
	
	private List<String> getShopList(UserRoleRelation user) {
		Criteria criteria = dao.createCriteria(XiuyuShopDirectorRelation.class);
		criteria.add(Restrictions.eq("userId", user.getUserId()));
		criteria.setProjection(Projections.property("deptId"));
		@SuppressWarnings("unchecked")
		List<String> list = criteria.list();
		if (!ListUtils.isEmptyOrNull(list)) {
			return list;
		} else {
			return null;
		}
	}
	
	private String convertShopIds(List<String> list){
	    if(ListUtils.isEmptyOrNull(list)){
		return "''";
	    }
	    StringBuilder ids = new StringBuilder();
	    for(String id : list){
		ids.append("'").append(id).append("',");
	    }
	    ids.deleteCharAt(ids.length()-1);
	    return ids.toString();
	}

	/**
	 * 增加或修改微调单
	 * 
	 * @param dto
	 * @param files
	 * @return
	 */
	@Override
	public Result saveOrUpdate(OrderRepairDto dto, MultipartFile[] files) {

		if(StringUtils.isBlank(dto.getOrderCharacter())){
			return new Result("500","订单性质不能为空!");
		}

		if (StringUtils.isBlank(dto.getId())) {
			OrderRepair repair = new OrderRepair();
			BeanUtils.copyProperties(dto, repair);
			String wdid = CodeUtil.createOrderRepairCode(getOrderRepairMaxValue());
			repair.setOrderRepairCode(wdid);
			// 图片
			saveOrderRepairImg(repair, files);
			return new Result(repair);
		} else {
			OrderRepair repair = new OrderRepair();
			BeanUtils.copyProperties(dto, repair);
			dao.update(repair);
			updateOrderRepairImg(repair, dto, files);
			return new Result(repair);
		}
	}

	// 新增结算单图片
	private void saveOrderRepairImg(OrderRepair repair, MultipartFile[] files) {
		if (files != null) {
			String key = null;
			for (MultipartFile multipartFile : files) {
				OrderRepairAttachment attachment = new OrderRepairAttachment();
				attachment.setOrderRepairId(repair.getId());
				key = appendOrderImage(repair,multipartFile);
				if (key != null) {
					attachment.setOssKey(key);
				}
				attachment.setFileName(multipartFile.getOriginalFilename());
				dao.save(attachment);

			}
		}
	}

	// 修改结算单图片
	private void updateOrderRepairImg(OrderRepair repair, OrderRepairDto dto, MultipartFile[] files) {
		String imageDelKeys = dto.getImageDelKeys();
		JSONArray delkeys = null;
		if (StringUtils.isNotBlank(imageDelKeys)) {
			delkeys = JSONArray.fromObject(imageDelKeys);
		}

		StringBuffer str = new StringBuffer();
		// 查询出此微调单对应的OrderRepairAttachment
		Criteria criteria = dao.createCriteria(OrderRepairAttachment.class);
		List<OrderRepairAttachment> attachments = criteria.add(Restrictions.eq("orderRepairId", repair.getId())).list();
		// 判断要删的图片路径OssKey数组是否为空，不为空删掉OssKey属性等于要删的图片路径OssKey
		if (delkeys != null && delkeys.size() > 0) {
			for (int i = 0; i < delkeys.size(); i++) {
				for (OrderRepairAttachment orderRepairAttachment : attachments) {
					if (delkeys.get(i).toString().equals(orderRepairAttachment.getOssKey())) {
						dao.delete(orderRepairAttachment);
					}
				}
			}
		}
		// 如果有新增，添加
		saveOrderRepairImg(repair, files);
		// Oss删除图片
		if (delkeys != null && delkeys.size() > 0) {
			for (int i = 0; i < delkeys.size(); i++) {
				delOSSFile(delkeys.get(i).toString());
			}

		}

	}

	/**
	 * 根据id查询微调单
	 * 
	 * @param id
	 * 
	 * @return
	 */
	@Override
	public Result RepairById(String id) {
		OrderRepair repair = dao.findById(OrderRepair.class, id);
		Map<String, Object> map = new HashMap<String, Object>();
		if (repair != null) {
			OrderDetail detail = dao.findById(OrderDetail.class, repair.getOrderDetailId());
			if(detail!=null){
				GoodsDetail g = dao.findById(GoodsDetail.class, detail.getGoodsDetailId());
				if(g!=null){
					Goods goods = dao.findById(Goods.class, g.getGoodsId());
					repair.setCategoryId(goods.getCategory().getId());
					repair.setCategoryName(goods.getCategory().getName());
					repair.setGoodsSn(detail.getGoodsSn());
				}
				map.put("repair", repair);
			}else{
				GoodsDetail g = dao.findById(GoodsDetail.class, repair.getGoodsDetailId());
				if(g!=null){
					Goods goods = dao.findById(Goods.class, g.getGoodsId());
					repair.setCategoryId(goods.getCategory().getId());
					repair.setCategoryName(goods.getCategory().getName());
				}
				map.put("repair", repair);
			}
			List<OrderRepair> list = getHistory(repair);
			if (list != null) {
				map.put("history", list);
			}
			return new Result(map);
		} else {
			return new Result(Constants.RESULT_CODE.SYS_ERROR, "没有此微调单！");
		}
	}

	private String getOrderRepairMaxValue() {
		Date date = new Date();
		Criteria criteria = dao.createCriteria(Code.class);
		criteria.add(Restrictions.between("updateTime", DateUtil.minDate(date), DateUtil.maxDate(date)));
		criteria.add(Restrictions.eq("type", "WD"));
		criteria.addOrder(org.hibernate.criterion.Order.desc("maxValue"));
		criteria.setMaxResults(1);
		Code code = (Code) criteria.uniqueResult();
		if (code == null) {
			code = new Code();
			code.setMaxValue(1);
			code.setType("WD");
			code.setUpdateTime(date);
			dao.save(code);
		} else {
			code.setMaxValue(code.getMaxValue() + 1);
		}
		return String.format("%0" + 6 + "d", code.getMaxValue());
	}

	private String appendOrderImage(OrderRepair repair,MultipartFile file) {
		String key = null;
		if (file == null) {// 如果没有上传任何图片文件
			return key;
		}
		try {
			String newKey = "xingyu/repair/"+getFileFolder(new Date())+"/"+repair.getOrderRepairCode()+"/" +UUID.randomUUID().toString().replace("-", "") + "." + file.getOriginalFilename();
			key = fileOSSUtil.uploadPrivateFile(newKey, file.getInputStream());
			// 把上传文件保存到OSS系统
		} catch (IOException e) {// 当读取上传图片文件输入流抛出异常时
			e.printStackTrace();
		}
		return key;// 返回上传文件路径
	}

	private String getFileFolder(Date date) {
		return DateUtil.formatDate(date, "yyyy") + "/" + DateUtil.formatDate(date, "MM") + "/"
				+ DateUtil.formatDate(date, "dd") ;
	}

	/**
	 * 删除上传到oss的文件
	 * 
	 * @param delKey
	 * @return
	 */
	public void delOSSFile(String delKey) {
		if (delKey != null) {
			String removeImgKey = fileOSSUtil.fromUrlToKey(delKey);
			// 在oss 删除 文件
			fileOSSUtil.deletePrivateFile(removeImgKey);
		}

	}

	/**
	 * 审核微调单
	 * 
	 * @param dto
	 * 
	 * @return
	 */
	@Override
	public Result checkRepair(OrderRepairCheck dto) {

		// OrderRepair orderRepair = dao.findById(OrderRepair.class,
		// dto.getOrderRepairId());
		// OrderRepairCheck check = orderRepair.getChecks().get(0);

		// 对于被审核的微调单，如果状态不是"已送出"则不能被把其状态修改为"星域审核通过"[2017-07-27]
		// if (StringUtils.isNotBlank(dto.getCheckStatus())
		// && StringUtils.isNotBlank(orderRepair.getOrderRepairStatus())) {
		// if (dto.getCheckStatus().equals("2") &&
		// !orderRepair.getOrderRepairStatus().equals("已送出")) {
		// return new Result(Constants.RESULT_CODE.SYS_ERROR,
		// "不能审核[" + orderRepair.getOrderRepairStatus() + "]状态的微调单！");
		// } else if ((dto.getCheckStatus().equals("1") &&
		// !orderRepair.getOrderRepairStatus().equals("已送出"))
		// && (dto.getCheckStatus().equals("1") &&
		// !orderRepair.getOrderRepairStatus().equals("星域审核通过"))) {
		// // 对于被驳回的微调单，如果状态不是"已送出"也不是"星域审核通过"则不能被把其状态修改为"星域驳回"[2017-07-27]
		// return new Result(Constants.RESULT_CODE.SYS_ERROR,
		// "不能驳回[" + orderRepair.getOrderRepairStatus() + "]状态的微调单！");
		// } else {
		//
		// if (StringUtils.isNotBlank(dto.getCheckStatus()))
		// check.setCheckStatus(dto.getCheckStatus());
		// if (StringUtils.isNotBlank(dto.getCheckerName()))
		// check.setCheckerName(dto.getCheckerName());
		// if (StringUtils.isNotBlank(dto.getCheckInstruction()))
		// check.setCheckInstruction(dto.getCheckInstruction());
		// Date date = new Date();
		// check.setCheckTime(date);
		// dao.update(check);
		// if (dto.getCheckStatus().equals("2")) {
		// orderRepair.setOrderCharacter("星域审核通过");
		// } else {
		// orderRepair.setOrderCharacter("星域审核未通过");
		// }
		// dao.update(orderRepair);
		// return new Result(orderRepair);
		// }
		//
		// } else {
		// return new Result(Constants.RESULT_CODE.SYS_ERROR, "无效参数！");
		// }
		OrderRepairCheck check = new OrderRepairCheck();

		if (StringUtils.isNotBlank(dto.getCheckerName())) {
			check.setCheckerName(dto.getCheckerName());
		}
		if (StringUtils.isNotBlank(dto.getCheckStatus())) {
			check.setCheckStatus(dto.getCheckStatus());
		}
		if (StringUtils.isNotBlank(dto.getOrderRepairId())) {
			check.setOrderRepairId(dto.getOrderRepairId());
		}
		check.setCheckTime(new Date());

		if (StringUtils.isNotBlank(dto.getCheckInstruction())) {
			check.setCheckInstruction(dto.getCheckInstruction());
		}

		switch (dto.getCheckStatus()) {
		case "1":
			changeStatus(dto.getOrderRepairId(), "3");

			break;

		case "2":
			changeStatus(dto.getOrderRepairId(), "2");
			break;
		}
		OrderRepair orderRepair = dao.findById(OrderRepair.class, dto.getOrderRepairId());
		if (null!=orderRepair){
			orderRepair.setCheckTime(new Date());
			dao.update(orderRepair);
		}
		dao.save(check);

		return new Result(check);

	}

	// 查询微调历史记录
	@Override
	public List<OrderRepair> getHistory(OrderRepair repair) {

		Criteria criteria = dao.createCriteria(OrderRepair.class);
		if (StringUtils.isNotBlank(repair.getOrderRepairCode())) {
			criteria.add(Restrictions.eq("goodsDetailId", repair.getGoodsDetailId()));
			return criteria.list();
		}
		/*if (StringUtils.isNotBlank(repair.getEcOrderCode())) {
			criteria.add(Restrictions.eq("ecOrderCode", repair.getEcOrderCode()));
		}*/
		return new ArrayList<>();
	}
	// // 修改微调单状态

	@Override
	public Result changeStatus(String id, String status) {
		OrderRepair orderRepair = dao.findById(OrderRepair.class, id);
		if (orderRepair != null) {
			switch (status) {
			case "0":
				orderRepair.setOrderRepairStatus("0");
				break;

			case "1":
				orderRepair.setOrderRepairStatus("1");
				break;

			case "2":
				orderRepair.setOrderRepairStatus("2");
				break;

			case "3":
				orderRepair.setOrderRepairStatus("3");
				break;
			case "4":
				orderRepair.setOrderRepairStatus("4");
				break;
			case "5":
				orderRepair.setOrderRepairStatus("5");
				break;
			case "6":
				orderRepair.setOrderRepairStatus("6");
				break;
			case "7":
				orderRepair.setOrderRepairStatus("7");
				break;
			case "8":
				orderRepair.setOrderRepairStatus("8");
				break;
			}
			dao.update(orderRepair);
		}

		return new Result(orderRepair);
	}

	@Override
	public Result remoteOrderRepair(String q) {
		Criteria criteria = dao.createCriteria(OrderRepair.class);
		if (StringUtils.isNotBlank(q)) {
			criteria.add(Restrictions.like("orderRepairCode", q, MatchMode.ANYWHERE));
		}
		@SuppressWarnings("unchecked")
		List<OrderRepair> list = criteria.list();
		return new Result(list);
	}

	@Override
	public Result del(String id) {

		if (StringUtils.isNotBlank(id)) {
			OrderRepair repair = dao.findById(OrderRepair.class, id);
//			List<OrderRepairAttachment> attachments = repair.getAttachments();
//			if (attachments != null && attachments.size() > 0) {
//				for (OrderRepairAttachment orderRepairAttachment : attachments) {
//					dao.delete(orderRepairAttachment);
//				}
//			}
//			List<OrderRepairCheck> checks = repair.getChecks();
//			if (checks != null && checks.size() > 0) {
//				for (OrderRepairCheck orderRepairCheck : checks) {
//					dao.delete(orderRepairCheck);
//				}
//			}
			repair.setDel("1");
			dao.update(repair);
			return new Result(repair);

		} else {
			return new Result(Constants.RESULT_CODE.SYS_ERROR, "没有此微调单！");
		}
	}
	
	public void getAttachment(String filePath) {
		OutputStream outputStream = null;
		try {
			if (StringUtils.isNotBlank(filePath)) {
				File file = new File(filePath);
				HttpServletResponse response = HttpUtil.getHttpServletResponse();
				response.reset();
				response.setContentType("application/octet-stream; charset=utf-8");
				response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
				outputStream = response.getOutputStream();
				outputStream.write(fileOSSUtil.getPrivateObject(filePath));
				outputStream.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
			}
		}
	}

	@Override
	public Result findMesRepairPlan(OrderRepairDto dto) {
		Criteria criteria = dao.createCriteria(MesRepairProductionPlanDetail.class);
		Criteria repairCriteria = criteria.createAlias("repair","repair");
		repairCriteria.add(Restrictions.eq("repair.orderRepairCode",dto.getOrderRepairCode()));
		repairCriteria.add(Restrictions.eq("repair.del", Constants.ENABLE_FLAG.ENABLE));
		List<MesRepairProductionPlanDetail> list = criteria.list();
		if (null==list||list.size()==0){
			return new Result();
		}
		MesRepairProductionPlan mesRepairProductionPlan = dao.findById(MesRepairProductionPlan.class,list.get(0).getRepairPlanId());
		return new Result(mesRepairProductionPlan);
	}

	@Override
	public Result RepairNoHistoryById(String id) {
		OrderRepair repair = dao.findById(OrderRepair.class, id);
		Map<String, Object> map = new HashMap<String, Object>();
		if (repair != null) {
			OrderDetail detail = dao.findById(OrderDetail.class, repair.getOrderDetailId());
			if(detail!=null){
				GoodsDetail g = dao.findById(GoodsDetail.class, detail.getGoodsDetailId());
				if(g!=null){
					Goods goods = dao.findById(Goods.class, g.getGoodsId());
					repair.setCategoryId(goods.getCategory().getId());
					repair.setCategoryName(goods.getCategory().getName());
					repair.setGoodsSn(detail.getGoodsSn());
					repair.setGoodsName(goods.getName());
					repair.setGoodsCode(goods.getCode());
				}
				map.put("repair", repair);
			}else{
				GoodsDetail g = dao.findById(GoodsDetail.class, repair.getGoodsDetailId());
				if(g!=null){
					Goods goods = dao.findById(Goods.class, g.getGoodsId());
					repair.setCategoryId(goods.getCategory().getId());
					repair.setCategoryName(goods.getCategory().getName());
					repair.setGoodsName(goods.getName());
					repair.setGoodsCode(goods.getCode());
				}
				map.put("repair", repair);
			}
			return new Result(map);
		} else {
			return new Result(Constants.RESULT_CODE.SYS_ERROR, "没有此微调单！");
		}
	}

	@Override
	public Pagination<OrderRepairModel> repairOrderRatio(OrderRepairDto dto, PageBean pageBean) {

		Double total = 0.00;
		BigDecimal rate ;
		Double quantity =0.00;

		Pagination<OrderRepairModel> pagination = new Pagination<OrderRepairModel>();

		StringBuilder sqlSelect = new StringBuilder();
		StringBuilder sqlSelectCount  = new StringBuilder();
		StringBuilder sqlFrom   = new StringBuilder();
		StringBuilder sqlWhere  = new StringBuilder();
		StringBuilder sqlLimit  = new StringBuilder();

		StringBuilder sqlBXSSelect   = new StringBuilder();
		StringBuilder sqlBXSGroup    = new StringBuilder();

		StringBuilder sqlKSFLSelect   = new StringBuilder();
		StringBuilder sqlKSFLGroup    = new StringBuilder();

		StringBuilder sqlSCGCSelect   = new StringBuilder();
		StringBuilder sqlSCGCGroup    = new StringBuilder();

		StringBuilder sqlCBFSelect   = new StringBuilder();
		StringBuilder sqlCBFGroup    = new StringBuilder();

		sqlBXSSelect.append(" SELECT j.c_technician_name,count( a.c_id) as sl ");
		sqlBXSGroup.append(" GROUP BY j.c_technician_name");

		sqlKSFLSelect.append(" SELECT d.c_name,count( a.c_id) as sl ");
		sqlKSFLGroup.append(" GROUP BY d.c_name");

		sqlSCGCSelect.append(" SELECT f.c_prod_factory_id,count( a.c_id) as sl ");
		sqlSCGCGroup.append(" GROUP BY f.c_prod_factory_id ");

		sqlCBFSelect.append(" SELECT f.publishers,count( a.c_id) as sl ");
		sqlCBFGroup.append(" GROUP BY f.publishers ");

		sqlSelectCount.append("SELECT COUNT(a.c_id) AS sl");

		sqlSelect.append(" SELECT a.c_order_repair_code,a.c_customer_name,c.c_code AS goods_code,c.c_name AS goods_name,b.c_color_name,d.c_name AS category_name,a.c_shop_name" +
				"  ,a.c_order_repair_status,CASE WHEN ifnull(a.c_order_detail_id,'')='' THEN 'EC订单'  ELSE '孔雀订单' END AS order_type,a.c_order_character " +
				"  ,f.c_prod_factory_id,f.publishers,j.c_technician_name,a.c_frequency,IFNULL(TO_DAYS(a.c_check_time) - TO_DAYS(i.c_send_time),0) AS days,g.c_repair_feedback,i.c_send_time,a.c_check_time,m.c_delivery_time ");
		sqlFrom.append(" FROM (t_order_repair a" +
				"  LEFT JOIN t_goods_detail b ON (a.c_goods_detail_id = b.c_id))" +
				"  LEFT JOIN t_goods c ON (b.c_goods_id = c.c_id)" +
				"  LEFT JOIN t_category d ON (c.c_category_id = d.c_id)" +
				"  LEFT JOIN mes_order_plan_detail e ON (a.c_order_detail_id = e.c_order_detail_id AND e.c_delete_flag='0')" +
				"  LEFT JOIN mes_order_plan f ON (e.c_order_plan_id = f.c_id AND f.c_delete_flag='0')" +
				"  LEFT JOIN mes_order_detail_assign j ON (a.c_order_detail_id = j.c_order_detail_id AND j.c_delete_flag=0)" +
				"  LEFT JOIN (SELECT h.c_send_time,l.c_order_repair_id,l.c_order_detail_id FROM t_logistic h,t_logistic_order l WHERE h.c_id = l.c_logistic_id AND l.c_delete_flag='0' AND h.c_delete_flag='0' AND h.c_logistic_type='0') i ON (a.c_order_detail_id = i.c_order_detail_id)" +
				"  LEFT JOIN mes_repair_production_plan_detail g ON (a.c_id = g.c_order_repair_id)" +
				"  LEFT JOIN (SELECT t_logistic.c_delivery_time,t_logistic_order.c_order_repair_id FROM t_logistic ,t_logistic_order  WHERE t_logistic.c_id = t_logistic_order.c_logistic_id AND t_logistic_order.c_delete_flag='0' AND t_logistic.c_delete_flag='0' AND t_logistic.c_logistic_type='1') m ON (a.c_id = m.c_order_repair_id)" +
				" WHERE a.c_del='0' ");

		//微调收货日期
		if (StringUtils.isNotBlank(dto.getDeliveryStartTime())){
			sqlWhere.append(" AND m.c_delivery_time>='").append(dto.getDeliveryStartTime()).append("'");
		}
		if (StringUtils.isNotBlank(dto.getDeliveryEndTime())){
			sqlWhere.append(" AND m.c_delivery_time <='").append(dto.getDeliveryEndTime()).append(" 23:59:59' ");
		}
		//微调审核日期
		if (StringUtils.isNotBlank(dto.getStartCheckTime())){
			sqlWhere.append(" AND a.c_check_time >='").append(dto.getStartCheckTime()).append("'");
		}
		if (StringUtils.isNotBlank(dto.getEndCheckTime())){
			sqlWhere.append(" AND a.c_check_time <='").append(dto.getEndCheckTime()).append(" 23:59:59'");
		}

		//微调单号
		if (StringUtils.isNotBlank(dto.getOrderRepairCode())){
			sqlWhere.append(" AND a.c_order_repair_code like '%").append(dto.getOrderRepairCode()).append("%'");
		}
		//微调单状态
		if (StringUtils.isNotBlank(dto.getOrderRepairStatus())){
			sqlWhere.append(" AND a.c_order_repair_status='").append(dto.getOrderRepairStatus()).append("'");
		}

		//原生产工厂
		if (StringUtils.isNotBlank(dto.getProdFactory())){
			sqlWhere.append(" AND f.c_prod_factory_id='").append(dto.getProdFactory()).append("'");
		}

		//版型师
		if (StringUtils.isNotBlank(dto.getTechnicianId())){
			sqlWhere.append(" AND j.c_technician_id='").append(dto.getTechnicianId()).append("'");
		}

		//出版方
		if (StringUtils.isNotBlank(dto.getPublishers())){
			sqlWhere.append(" AND f.publishers='").append(dto.getPublishers()).append("'");
		}

		//类别
		if (StringUtils.isNotBlank(dto.getCategoryId())){
			sqlWhere.append(" AND d.c_id='").append(dto.getCategoryId()).append("'");
		}

		if (pageBean.getPage() != null &&pageBean.getRows() != null) {
			sqlLimit.append(" LIMIT ").append(pageBean.getPage()).append(",").append(pageBean.getRows());
		}

		//导出不执行该语句
		if (pageBean.getPage() != null &&pageBean.getRows() != null && pageBean.getPage()==1) {
			Map<String, Object> mp = new HashMap<>();

			//版型师
			List resultBXS = dao.queryBySql(sqlBXSSelect.append(sqlFrom).append(sqlWhere).append(sqlBXSGroup).toString());
			List<Map<String, Object>> BXSlist = new ArrayList<>();
			total = 0.00;
			if (resultBXS != null && resultBXS.size() > 0) {
				for (Object result : resultBXS) {
					Object[] properties = (Object[]) result;
					Map<String, Object> map = new HashMap<>();
					map.put("technicianName", properties[0] == null ? "其它" : properties[0]);
					quantity = Double.parseDouble(properties[1] == null ? "0" : properties[1].toString());
					map.put("quantity", properties[1] == null ? "0" : properties[1]);
					total += quantity;
					BXSlist.add(map);
				}
				for (Map<String, Object> map : BXSlist) {
					quantity = Double.parseDouble(map.get("quantity").toString());
					if (total != 0) {
						rate = new BigDecimal((quantity / total) * 100).setScale(2, BigDecimal.ROUND_HALF_UP);
					} else {
						rate = BigDecimal.valueOf(0);
					}
					map.put("rate", rate + "%");
				}
			}

			mp.put("technician",BXSlist);
			//款式分类

			List resultKSFL = dao.queryBySql(sqlKSFLSelect.append(sqlFrom).append(sqlWhere).append(sqlKSFLGroup).toString());
			List<Map<String, Object>> KSFLlist = new ArrayList<>();
			total = 0.00;
			if (resultKSFL != null && resultKSFL.size() > 0) {
				for (Object result : resultKSFL) {
					Object[] properties = (Object[]) result;
					Map<String, Object> map = new HashMap<>();
					map.put("categoryName", properties[0] == null ? "其它" : properties[0]);
					quantity = Double.parseDouble(properties[1] == null ? "0" : properties[1].toString());
					map.put("quantity", properties[1] == null ? "0" : properties[1]);
					total += quantity;
					KSFLlist.add(map);
				}
				for (Map<String, Object> map : KSFLlist) {
					quantity = Double.parseDouble(map.get("quantity").toString());
					if (total != 0) {
						rate = new BigDecimal((quantity / total) * 100).setScale(2, BigDecimal.ROUND_HALF_UP);
					} else {
						rate = BigDecimal.valueOf(0);
					}
					map.put("rate", rate + "%");
				}
			}
			mp.put("category",KSFLlist);
			//生产工厂
			List resultSCGC = dao.queryBySql(sqlSCGCSelect.append(sqlFrom).append(sqlWhere).append(sqlSCGCGroup).toString());
			List<Map<String, Object>> SCGClist = new ArrayList<>();
			total = 0.00;
			if (resultSCGC != null && resultSCGC.size() > 0) {
				for (Object result : resultSCGC) {
					Object[] properties = (Object[]) result;
					Map<String, Object> map = new HashMap<>();
					map.put("prodFactory", properties[0] == null ? "其它" : properties[0]);
					quantity = Double.parseDouble(properties[1] == null ? "0" : properties[1].toString());
					map.put("quantity", properties[1] == null ? "0" : properties[1]);
					total += quantity;
					SCGClist.add(map);
				}
				for (Map<String, Object> map : SCGClist) {
					quantity = Double.parseDouble(map.get("quantity").toString());
					if (total != 0) {
						rate = new BigDecimal((quantity / total) * 100).setScale(2, BigDecimal.ROUND_HALF_UP);
					} else {
						rate = BigDecimal.valueOf(0);
					}
					map.put("rate", rate + "%");
				}
			}
			mp.put("prodFactory",SCGClist);
			//出版方
			List resultCBF = dao.queryBySql(sqlCBFSelect.append(sqlFrom).append(sqlWhere).append(sqlCBFGroup).toString());
			List<Map<String, Object>> CBFlist = new ArrayList<>();
			total = 0.00;
			if (resultCBF != null && resultCBF.size() > 0) {
				for (Object result : resultCBF) {
					Object[] properties = (Object[]) result;
					Map<String, Object> map = new HashMap<>();
					map.put("publishers", properties[0] == null ? "其它" : Status.getPublishersName(properties[0]));
					quantity = Double.parseDouble(properties[1] == null ? "0" : properties[1].toString());
					map.put("quantity", properties[1] == null ? "0" : properties[1]);
					total += quantity;
					CBFlist.add(map);
				}
				for (Map<String, Object> map : CBFlist) {
					quantity = Double.parseDouble(map.get("quantity").toString());
					if (total != 0) {
						rate = new BigDecimal((quantity / total) * 100).setScale(2, BigDecimal.ROUND_HALF_UP);
					} else {
						rate = BigDecimal.valueOf(0);
					}
					map.put("rate", rate + "%");
				}
			}

			mp.put("publishers",CBFlist);

			pagination.setMap(mp);
		}

		List resultSet = dao.queryBySql(sqlSelect.append(sqlFrom).append(sqlWhere).append(sqlLimit).toString());
		List<OrderRepairModel> list = new ArrayList<>();
		if (resultSet != null && resultSet.size() > 0) {
			for (Object result : resultSet) {
				OrderRepairModel model = new OrderRepairModel();
				Object[] properties = (Object[]) result;
				model.setOrderRepairCode(properties[0] == null ? "" : properties[0].toString());
				model.setCustomerName(properties[1] == null ? "" : properties[1].toString());
				model.setGoodsCode(properties[2] == null ? "" : properties[2].toString());
				model.setGoodsName(properties[3] == null ? "" : properties[3].toString());
				model.setGoodsColor(properties[4] == null ? "" : properties[4].toString());
				model.setCategoryName(properties[5] == null ? "" : properties[5].toString());
				model.setShopName(properties[6] == null ? "" : properties[6].toString());
				model.setOrderRepairStatus(Status.getRepairStatusName(properties[7]));
				model.setOrderType(properties[8] == null ? "" : properties[8].toString());
				model.setOrderCharacter(properties[9] == null ? "" : properties[9].toString());
				model.setProdFactory(properties[10] == null ? "" : properties[10].toString());
				model.setPublishers(Status.getPublishersName(properties[11]));
				model.setTechnicianName(properties[12] == null ? "" : properties[12].toString());
				model.setFrequency(properties[13] == null ? "" : properties[13].toString());
				model.setDays(properties[14] == null ? "0" : properties[14].toString());
				model.setRepairFeedback(properties[15] == null ? "" : properties[15].toString());
				model.setSendTime(properties[16] == null ? "" : properties[16].toString());
				model.setCheckTime(properties[17] == null ? "" : properties[17].toString());
				model.setDeliveryTime(properties[18] == null ? "" : properties[18].toString());
				list.add(model);
			}
		}

		pagination.setRows(list);

		if (pageBean.getPage() != null && pageBean.getRows() != null) {
			Long count = Long.parseLong(dao.uniqueBySql(sqlSelectCount.append(sqlFrom).append(sqlWhere).toString()).toString());
			pagination.setTotal(count);
		}



		return pagination;
	}

	@Override
	public Result repairOrderRatioExportExcel(OrderRepairDto dto, HttpServletRequest request, HttpServletResponse response) {
		String excelFileName = "微调订单占比" ;
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
			response.addHeader("content-Disposition", "attachment;filename="+ excelFileName +".xls");
			response.flushBuffer();
			String[] headers = null;
			String[] fields = new String[] {"orderRepairCode","customerName","goodsCode","goodsName","goodsColor","categoryName","shopName","orderRepairStatus","orderType","orderCharacter","prodFactory","publishers","technicianName","frequency","days","repairFeedback","sendTime","deliveryTime","checkTime"};


			headers =new String[]{"微调单号","会员名","款号","款名","颜色","类别","店铺","微调单状态","微调单类型","原订单性质","原订单生产工厂","原订单出版方","原订单版型师","第几次微调","时长","实调内容","订单发货日期","微调收货日期","微调审核时间"};

			out = response.getOutputStream();
			ExportExcelUtil.exportExcel(0,0,0,"微调订单占比" , headers,ExportExcelUtil.buildExportedModel(this.repairOrderRatio(dto,new PageBean()).getRows(), fields) , out, "yyyy-MM-dd");
		} catch (IOException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new Result();
	}

	@Override
	public Pagination<OrderRepairModel> repairProgress(OrderRepairDto dto, PageBean pageBean) {
		Pagination<OrderRepairModel> pagination = new Pagination<OrderRepairModel>();

		String sqlwhere ="";
		String tenantName="''";
		StringBuilder sqlSelect = new StringBuilder();
		StringBuilder sqlFrom   = new StringBuilder();


		if (dto.getTenantIds()!=null&&dto.getTenantIds().length>0){
			for (String id:dto.getTenantIds()) {
				sqlwhere +="'"+id+"',";
			}
			sqlwhere =" AND tenant.c_id in ("+sqlwhere.substring(0,sqlwhere.length()-1)+")";
		}
		if(dto.getFlag().equals("Y")){
			tenantName=" tenant.c_tenant_name ";
		}

		sqlSelect.append(" SELECT "+tenantName+" AS tenant_name,category.c_name AS category_name ,orepair.c_goods_color,SUM(tb.audit_num) AS audit_num,SUM(tb.received_num) AS received_num,SUM(tb.issued_num) AS issued_num,SUM(tb.finish_num) AS finish_num,SUM(tb.send_num) AS send_num" );
		sqlFrom.append(" FROM (" +
				"  SELECT orepair.c_id AS order_repair_id,orepair.c_num AS audit_num,0 AS received_num,0 AS issued_num,0 AS finish_num,0 AS send_num" +
				"  FROM t_order_repair orepair" +
				"  WHERE orepair.c_del='0' AND orepair.c_check_time >= '"+dto.getDateStart()+"' AND orepair.c_check_time <='"+dto.getDateEnd()+" 23:59:59'" +
				"  UNION ALL" +
				"  SELECT logisticOrder.c_order_repair_id order_repair_id,0 AS audit_num,1 AS received_num,0 AS issued_num,0 AS finish_num,0 AS send_num" +
				"  FROM t_logistic logistic" +
				"  JOIN t_logistic_order logisticOrder ON logistic.c_id = logisticOrder.c_logistic_id AND IFNULL(logisticOrder.c_order_repair_id,'') <>''" +
				"  WHERE logistic.c_logistic_type='1' AND logistic.c_delete_flag='0' AND logisticOrder.c_delete_flag='0' AND" +
				"  logistic.c_delivery_time >='"+dto.getDateStart()+"' AND logistic.c_delivery_time <='"+dto.getDateEnd()+" 23:59:59'" +
				"  UNION ALL" +
				"  SELECT plan_detail.c_order_repair_id,0 AS audit_num,0 AS received_num,1 AS issued_num,0 AS finish_num,0 AS send_num" +
				"  FROM mes_repair_production_plan plan" +
				"  JOIN mes_repair_production_plan_detail plan_detail ON plan.c_id = plan_detail.c_repair_plan_id " +
				"  WHERE plan.c_del='0' AND " +
				"  plan.c_release_time>='"+dto.getDateStart()+"' AND plan.c_release_time <='"+dto.getDateEnd()+" 23:59:59'" +
				"  UNION ALL" +
				"  SELECT orepair.c_id AS order_repair_id,0 AS audit_num,0 AS received_num,0 AS issued_num,orepair.c_num AS finish_num,0 AS send_num" +
				"  FROM t_order_repair orepair" +
				"  WHERE orepair.c_del='0' AND orepair.c_finish_date >= '"+dto.getDateStart()+"' AND orepair.c_finish_date <='"+dto.getDateEnd()+" 23:59:59'" +
				"  UNION ALL" +
				"  SELECT logisticOrder.c_order_repair_id order_repair_id,0 AS audit_num,0 AS received_num,0 AS issued_num,0 AS finish_num,1 AS send_num" +
				"  FROM t_logistic logistic" +
				"  JOIN t_logistic_order logisticOrder ON logistic.c_id = logisticOrder.c_logistic_id AND IFNULL(logisticOrder.c_order_repair_id,'') <>''" +
				"  WHERE logistic.c_logistic_type='0' AND logistic.c_delete_flag='0' AND logisticOrder.c_delete_flag='0' AND" +
				"  logistic.c_send_time >='"+dto.getDateStart()+"' AND logistic.c_send_time <='"+dto.getDateEnd()+" 23:59:59'" +
				"  ) tb" +
				"  JOIN t_order_repair orepair ON tb.order_repair_id = orepair.c_id" +
				"  JOIN t_tenant tenant ON orepair.c_tenant_id = tenant.c_id" +
				"  JOIN t_goods goods ON orepair.c_goods_code = goods.c_code" +
				"  JOIN t_category category ON goods.c_category_id = category.c_id" +
				"  WHERE 1=1" +sqlwhere+
				"  GROUP BY "+tenantName+",category.c_name ,orepair.c_goods_color ");

		List resultSet = dao.queryBySql(sqlSelect.append(sqlFrom).toString());
		List<OrderRepairModel> list = new ArrayList<>();
		if (resultSet != null && resultSet.size() > 0) {
			for (Object result : resultSet) {
				OrderRepairModel model = new OrderRepairModel();
				Object[] properties = (Object[]) result;
				model.setTenantName(properties[0] == null ? "" : properties[0].toString());
				model.setCategoryName(properties[1] == null ? "" : properties[1].toString());
				model.setGoodsColor(properties[2] == null ? "" : properties[2].toString());
				model.setAuditNum(properties[3] == null ? "0" : properties[3].toString());
				model.setReceivedNum(properties[4] == null ? "0" : properties[4].toString());
				model.setIssuedNum(properties[5] == null ? "0" : properties[5].toString());
				model.setFinishNum(properties[6] == null ? "0" : properties[6].toString());
				model.setSendNum(properties[7] == null ? "0" : properties[7].toString());
				list.add(model);
			}
		}

		pagination.setRows(list);
		pagination.setTotal(list.size());
		return pagination;
	}

	@Override
	public Result repairProgressExportExcel(OrderRepairDto dto, HttpServletRequest request, HttpServletResponse response) {
		String excelFileName = "微调进度" ;
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
			response.addHeader("content-Disposition", "attachment;filename="+ excelFileName +".xls");
			response.flushBuffer();
			String[] headers = null;
			String[] fields = new String[] {"tenantName","categoryName","goodsColor","auditNum","receivedNum","issuedNum","finishNum","sendNum"};


			headers =new String[]{"客户","产品类别","颜色","星域审核数量","收货数量","计划下达数量","生产完成数量","发货数量"};

			out = response.getOutputStream();
			ExportExcelUtil.exportExcel(0,0,0,"微调进度" , headers,ExportExcelUtil.buildExportedModel(this.repairProgress(dto,new PageBean()).getRows(), fields) , out, "yyyy-MM-dd");
		} catch (IOException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new Result();
	}


}
