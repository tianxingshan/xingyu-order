package com.kongque.service.order.impl;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kongque.component.impl.JsonMapper;
import com.kongque.model.*;
import com.kongque.util.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kongque.constants.Constants;
import com.kongque.dao.IDaoService;
import com.kongque.dto.OrderCheckDto;
import com.kongque.dto.OrderDetailSearchDto;
import com.kongque.dto.OrderDto;
import com.kongque.entity.basics.Code;
import com.kongque.entity.basics.SizeCode;
import com.kongque.entity.basics.XiuyuShopDirectorRelation;
import com.kongque.entity.goods.Goods;
import com.kongque.entity.goods.GoodsDetail;
import com.kongque.entity.order.BodyLanguage;
import com.kongque.entity.order.BodyMeasure;
import com.kongque.entity.order.Order;
import com.kongque.entity.order.OrderAttachment;
import com.kongque.entity.order.OrderCheck;
import com.kongque.entity.order.OrderDetail;
import com.kongque.entity.user.UserDeptRelation;
import com.kongque.entity.user.UserRoleRelation;
import com.kongque.service.order.IOrderService;

import net.sf.json.JSONArray;

@Service
public class OrderServiceImpl implements IOrderService {

	@Resource
	private IDaoService dao;

	@Resource
	private FileOSSUtil fileOSSUtil;

	@Override
	public Pagination<Order> orderList(OrderDto dto, PageBean pageBean) {
		Pagination<Order> pagination = new Pagination<Order>();
		Criteria criteria = dao.createCriteria(Order.class);
//		@SuppressWarnings("unchecked")
//		List<UserRoleRelation> users = dao.createCriteria(UserRoleRelation.class)
//				.add(Restrictions.eq( "userId", dto.getUserId())).list();
//		UserDeptRelation dept = dao.findUniqueByProperty(UserDeptRelation.class, "userId", dto.getUserId());
//		for (UserRoleRelation user : users) {
//			if (Constants.XIUYU_ROLE_ID.equalsIgnoreCase(user.getUserRole().getId())){
//				// 秀域审核主管
//				List<String> list = getShopList(user);
//				if (ListUtils.isEmptyOrNull(list)) {
//					return pagination;
//				}
//				criteria.add(Restrictions.in("shopId", list));
//				break;
//			} else if (Constants.DIANYUAN_ROLE_ID.equalsIgnoreCase(user.getUserRole().getId())
//					||Constants.JIAMENG_ROLE_ID.equalsIgnoreCase(user.getUserRole().getId())){
//				criteria.add(Restrictions.eq("shopId", dept.getDeptId()));
//				break;
//			}
//		}

		if("1".equals(dto.getOrderType())){
			 criteria.add(Restrictions.sqlRestriction(" this_.c_status_bussiness in (0,1,2,3,4,5)"));
		}
		if(StringUtils.isNotBlank(dto.getOrderCode())){
			criteria.add(Restrictions.like("orderCode", dto.getOrderCode(),MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(dto.getCustomerCode())){
			criteria.add(Restrictions.like("customerCode", dto.getCustomerCode(),MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(dto.getCustomerName())){
			criteria.add(Restrictions.like("customerName", dto.getCustomerName(),MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(dto.getStatusBeforeProduce())){
			criteria.add(Restrictions.eq("statusBeforeProduce", dto.getStatusBeforeProduce()));
		}
		if(StringUtils.isNotBlank(dto.getStatusBeforeSend())){
			criteria.add(Restrictions.eq("statusBeforeSend", dto.getStatusBeforeSend()));
		}
		if(StringUtils.isNotBlank(dto.getCity())){
			criteria.add(Restrictions.like("city", dto.getCity(),MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(dto.getShopName())){
			criteria.add(Restrictions.like("shopName", dto.getShopName(),MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(dto.getOrderCharacter())){
			criteria.add(Restrictions.like("orderCharacter", dto.getOrderCharacter(),MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(dto.getStatusBussiness())){
			criteria.add(Restrictions.like("statusBussiness", dto.getStatusBussiness(),MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(dto.getReset())){
			criteria.add(Restrictions.like("reset", dto.getReset(),MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(dto.getCreateUserId())){
			criteria.add(Restrictions.eq("createUserId", dto.getCreateUserId()));
		}
		if(StringUtils.isNotBlank(dto.getCreateUserName())){
//			criteria.add(Restrictions.eq("CreateUser.userName", dto.getCreateUserName()));
			criteria.createCriteria("CreateUser", "o").add(Restrictions.like("o.userName", dto.getCreateUserName(), MatchMode.ANYWHERE));
			
		}
		if (dto.getCrdateTimeBegin() != null && dto.getCrdateTimeEnd() != null) {
			criteria.add(Restrictions.ge("createTime", dto.getCrdateTimeBegin()));
			criteria.add(Restrictions.lt("createTime", DateUtil.maxDate(dto.getCrdateTimeEnd())));
//			criteria.add(Restrictions.between("createTime", DateUtil.minDate(dto.getCrdateTimeBegin()),
//					DateUtil.maxDate(dto.getCrdateTimeEnd())));
		} else if (dto.getCrdateTimeBegin() != null) {
			criteria.add(Restrictions.ge("createTime", dto.getCrdateTimeBegin()));
//			criteria.add(Restrictions.between("createTime", DateUtil.minDate(dto.getCrdateTimeBegin()),
//					DateUtil.maxDate(dto.getCrdateTimeBegin())));
		} else if (dto.getCrdateTimeEnd() != null) {
			criteria.add(Restrictions.le("createTime", dto.getCrdateTimeEnd()));
//			criteria.add(Restrictions.between("createTime", DateUtil.minDate(dto.getCrdateTimeEnd()),
//					DateUtil.maxDate(dto.getCrdateTimeEnd())));
		}
		if (dto.getXiuyuCheckTimeStr() != null && dto.getXiuyuCheckTimeEnd() != null) {
			criteria.add(Restrictions.ge("xiuyuChekTime", dto.getXiuyuCheckTimeStr()));
			criteria.add(Restrictions.lt("xiuyuChekTime", DateUtil.maxDate(dto.getXiuyuCheckTimeEnd())));
		} else if (dto.getXiuyuCheckTimeStr() != null) {
			criteria.add(Restrictions.ge("xiuyuChekTime", dto.getXiuyuCheckTimeStr()));
		} else if (dto.getXiuyuCheckTimeEnd() != null) {
			criteria.add(Restrictions.le("xiuyuChekTime", dto.getXiuyuCheckTimeEnd()));
		}
		if (dto.getXingyuCheckTimeStr() != null && dto.getXingyuCheckTimeEnd() != null) {
			criteria.add(Restrictions.ge("xiuyuChekTime", dto.getXingyuCheckTimeStr()));
			criteria.add(Restrictions.lt("xiuyuChekTime", DateUtil.maxDate(dto.getXingyuCheckTimeEnd())));
		} else if (dto.getXingyuCheckTimeStr() != null) {
			criteria.add(Restrictions.ge("xiuyuChekTime", dto.getXingyuCheckTimeStr()));
		} else if (dto.getXingyuCheckTimeEnd() != null) {
			criteria.add(Restrictions.le("xiuyuChekTime", dto.getXingyuCheckTimeEnd()));
		}
		if (dto.getSubmitTimeStr() != null && dto.getSubmitTimeEnd() != null) {
			criteria.add(Restrictions.ge("submitTime", dto.getSubmitTimeStr()));
			criteria.add(Restrictions.lt("submitTime", DateUtil.maxDate(dto.getSubmitTimeEnd())));
		} else if (dto.getSubmitTimeStr() != null) {
			criteria.add(Restrictions.ge("submitTime", dto.getSubmitTimeStr()));
		} else if (dto.getSubmitTimeEnd() != null) {
			criteria.add(Restrictions.le("submitTime", dto.getSubmitTimeEnd()));
		}
		criteria.add(Restrictions.eq("deleteFlag", "0"));
		criteria.addOrder(org.hibernate.criterion.Order.desc("createTime"));
		List<Order> list = dao.findListWithPagebeanCriteria(criteria, pageBean);
		for (int i = 0; i < list.size(); i++) {
			List<OrderDetail> detailList = list.get(i).getOrderDetailList();
			for (int j = 0; j < detailList.size(); j++) {
				GoodsDetail goodsDetail = detailList.get(j).getGoodsDetail(); 
				String goodsId = goodsDetail.getGoodsId();
				Goods goods = dao.findById(Goods.class, goodsId);
				goodsDetail.setDetail(goods.getDetail());
				goodsDetail.setCategoryName(goods.getCategory().getName());
			}
			
		}
		pagination.setRows(list);
		pagination.setTotal(dao.findTotalWithCriteria(criteria));
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
	
	@Override
	public Result orderDetail(String id) {
		OrderModel model = new OrderModel();
		Order order = dao.findById(Order.class, id);
		if (order != null) {
			if(order.getOrderDetailList()!=null && order.getOrderDetailList().size()>0){
				for (int i = 0; i < order.getOrderDetailList().size(); i++) {
					OrderDetail orderDetail = order.getOrderDetailList().get(i);
					GoodsDetail goodsDetail = dao.findById(GoodsDetail.class, orderDetail.getGoodsDetailId());
					Goods goods = dao.findById(Goods.class, goodsDetail.getGoodsId());
					String categoryName = goods.getCategory().getName();
					//orderDetail.setCategoryName(categoryName);
					orderDetail.setGoodsName(goods.getName());
				}
			}
			model.setOrder(order);
		}
		BodyMeasure bodyMeasure = dao.findUniqueByProperty(BodyMeasure.class, "orderId", id);
		if (bodyMeasure != null) {
			model.setBodyMeasure(bodyMeasure);
		}
		BodyLanguage bodyLanguage = dao.findUniqueByProperty(BodyLanguage.class, "orderId", id);
		if (bodyLanguage != null) {
			model.setBodyLanguage(bodyLanguage);
		}
		Criteria criteria2 = dao.createCriteria(OrderAttachment.class);
		criteria2.add(Restrictions.eq("orderId", id));
		@SuppressWarnings("unchecked")
		List<OrderAttachment> list2 = criteria2.list();
		if (list2 != null && list2.size() > 0) {
			model.setOrderAttachmentList(list2);
		}
		Criteria criteria = dao.createCriteria(OrderCheck.class);
		criteria.add(Restrictions.eq("orderId", id));
		@SuppressWarnings("unchecked")
		List<OrderCheck> list = criteria.list();
		if (list != null && list.size() > 0) {
			model.setOrderCheckList(list);
		}
		return new Result(model);
	}

	@Override
	public Result saveOrUpdate(OrderDto dto, MultipartFile[] files) {
		if (StringUtils.isBlank(dto.getId())) {
			Order order = new Order();
			BeanUtils.copyProperties(order, dto);
			order.setOrderCode(CodeUtil.createOrderCode(getOrderMaxValue()));
			order.setCreateTime(new Date());
			dao.save(order);
			saveOrder(order, dto, files);
		} else {
			Order order = new Order();
			BeanUtils.copyProperties(order, dto);
			order.setUpdateTime(new Date());
			dao.update(order);
			updateOrder(order, dto, files);
		}
		return new Result("200", "操作成功！");
	}

	public void saveOrder(Order order, OrderDto dto, MultipartFile[] files) {
		if (dto.getGoodsDetailList() != null && dto.getGoodsDetailList().size() > 0) {
			for (int i = 0; i < dto.getGoodsDetailList().size(); i++) {
				OrderDetail orderDetail = new OrderDetail();
				orderDetail.setOrderId(order.getId());
				orderDetail.setGoodsDetailId(dto.getGoodsDetailList().get(i).getGoodsDetailId());
				orderDetail.setGoodsCode(dto.getGoodsDetailList().get(i).getGoodsCode());
				orderDetail.setGoodsColorName(dto.getGoodsDetailList().get(i).getGoodsColorName());
				orderDetail.setGoodsDetailImageKeys(dto.getGoodsDetailList().get(i).getGoodsDetailImageKeys());
				orderDetail.setGoodsSn(getSnValue());
				orderDetail.setErpNo(dto.getGoodsDetailList().get(i).getErpNo());
				orderDetail.setNum(dto.getGoodsDetailList().get(i).getNum());
				orderDetail.setUnit(dto.getGoodsDetailList().get(i).getUnit());
				orderDetail.setOrderDetailStatus(dto.getGoodsDetailList().get(i).getOrderDetailStatus());
				orderDetail.setClosedStatus(dto.getGoodsDetailList().get(i).getClosedStatus());
				orderDetail.setRemark(dto.getGoodsDetailList().get(i).getRemark());
				dao.save(orderDetail);
			}
		}
		/*if (files != null) {
			Set<String> imageKeys = appendOrderImage(order,files);
			OrderAttachment orderAttachment = new OrderAttachment();
			orderAttachment.setOrderId(order.getId());
			orderAttachment.setOssKey(imageKeys.toString());
			orderAttachment.setFileName(dto.getFileName());
			dao.save(orderAttachment);
		}*/
		if (dto.getFileName()!=null&&files!=null){
			String[] fileNames = dto.getFileName().split(",");
			if (files.length==fileNames.length)
				for (int i=0;i<files.length;i++) {
					MultipartFile file = files[i];
					String fileName = fileNames[i];
					String key = appendOrderImage(order,file);
					if (key!=null){
						OrderAttachment orderAttachment = new OrderAttachment();
						orderAttachment.setOrderId(order.getId());
						orderAttachment.setOssKey(key);
						orderAttachment.setFileName(fileName);
						dao.save(orderAttachment);
					}
				}
		}
		if (dto.getOrderLanguage() != null) {
			BodyLanguage bodyLanguage = new BodyLanguage();
			bodyLanguage.setOrderId(order.getId());
			bodyLanguage.setCustomerId(dto.getCustomerId());
			bodyLanguage.setOrderLanguage(dto.getOrderLanguage());
			dao.save(bodyLanguage);

		}
		if (dto.getOrderMeasure() != null) {
			BodyMeasure bodyMeasure = new BodyMeasure();
			bodyMeasure.setOrderId(order.getId());
			bodyMeasure.setCustomerId(dto.getCustomerId());
			bodyMeasure.setOrderMeasure(dto.getOrderMeasure());
			dao.save(bodyMeasure);
		}
	}

	public void updateOrder(Order order, OrderDto dto, MultipartFile[] files) {
		if (dto.getGoodsDetailList() != null && dto.getGoodsDetailList().size() > 0) {
			Criteria criteria = dao.createCriteria(OrderDetail.class);
			criteria.add(Restrictions.eq("orderId", order.getId()));
			@SuppressWarnings("unchecked")
			List<OrderDetail> list = criteria.list();
			for (OrderDetail orderDetail : list) {
				dao.delete(orderDetail);
			}
			for (int i = 0; i < dto.getGoodsDetailList().size(); i++) {
				OrderDetail orderDetail = new OrderDetail();
				orderDetail.setOrderId(order.getId());
				orderDetail.setGoodsDetailId(dto.getGoodsDetailList().get(i).getGoodsDetailId());
				orderDetail.setGoodsCode(dto.getGoodsDetailList().get(i).getGoodsCode());
				orderDetail.setGoodsColorName(dto.getGoodsDetailList().get(i).getGoodsColorName());
				orderDetail.setGoodsDetailImageKeys(dto.getGoodsDetailList().get(i).getGoodsDetailImageKeys());
				orderDetail.setGoodsSn(getSnValue());
				orderDetail.setErpNo(dto.getGoodsDetailList().get(i).getErpNo());
				orderDetail.setNum(dto.getGoodsDetailList().get(i).getNum());
				orderDetail.setUnit(dto.getGoodsDetailList().get(i).getUnit());
				orderDetail.setOrderDetailStatus(dto.getGoodsDetailList().get(i).getOrderDetailStatus());
				orderDetail.setClosedStatus(dto.getGoodsDetailList().get(i).getClosedStatus());
				orderDetail.setRemark(dto.getGoodsDetailList().get(i).getRemark());
				dao.save(orderDetail);
			}
		}
		String[] delFileList = dto.getImageDelKeys();
		if (delFileList != null && delFileList.length > 0) {
			List<OrderAttachment> orderAttachments = dao.findListByProperty(OrderAttachment.class, "orderId", order.getId());
			delOSSFile(orderAttachments, delFileList);
		}

		if (dto.getFileName()!=null&&files!=null){
			String[] fileNames = dto.getFileName().split(",");
			if (files.length==fileNames.length)
			for (int i=0;i<files.length;i++) {
				MultipartFile file = files[i];
				String fileName = fileNames[i];
				String key = appendOrderImage(order,file);
				if (key!=null){
					OrderAttachment orderAttachment = new OrderAttachment();
					orderAttachment.setOrderId(order.getId());
					orderAttachment.setOssKey(key);
					orderAttachment.setFileName(fileName);
					dao.save(orderAttachment);
				}
			}
		}

		if (dto.getOrderLanguage() != null) {
			BodyLanguage bodyLanguage = dao.findUniqueByProperty(BodyLanguage.class, "orderId", order.getId());
			bodyLanguage.setOrderLanguage(dto.getOrderLanguage());

			dao.update(bodyLanguage);
		}
		if (dto.getOrderMeasure() != null) {
			BodyMeasure bodyMeasure = dao.findUniqueByProperty(BodyMeasure.class, "orderId", order.getId());
			bodyMeasure.setOrderMeasure(dto.getOrderMeasure());
			bodyMeasure.setOrderMeasureTemplate(dto.getOrderMeasureTemplate());
			dao.update(bodyMeasure);
		}
	}

	@Override
	public Result checkOrUpdate(OrderCheckDto dto) {
		Order order = dao.findById(Order.class, dto.getOrderId());

		if ("2".equals(dto.getCheckType())) {
			order.setStatusBeforeProduce(dto.getCheckStatus());
			order.setBusinessCheckerName(dto.getCheckerName());
			order.setBusinessCheckerTime(new Date());
		}
		if ("3".equals(dto.getCheckType())) {
			order.setStatusBeforeSend(dto.getCheckStatus());
			order.setBusinessCheckerName(dto.getCheckerName());
			order.setBusinessCheckerTime(new Date());
		}
		if("1".equals(dto.getCheckType())){
			if("4".equals(dto.getStatus()) && !checkOrderDetailStatus(order)) {
				Result result = new Result();
				result.setReturnCode("400");
				result.setReturnMsg("存在生产中的订单明细，不允许驳回！");
				return result;
			}
			//如果是外部订单并且是秀域审核通过的,驳回的话直接删除
			if("1".equals(dto.getCheckStatus())&&"1".equals(order.getOutFlag())){

				Map<String, String> header = new HashMap<String, String>();
				header.put("FACTORY-CODE","XINGYU");
				String json="{\"orderCode\":\""+order.getOrderCode()+"\",\"note\":\""+dto.getCheckInstruction()+"\",\"handleName\":\""+dto.getCheckerName()+"\",\"handelTime\":\""+DateUtil.stringOfDateTime(new Date())+"\"}";

				Result result = JsonMapper.toObject(HttpClientUtils.doPostJson(Constants.YUN.KONGQUE_YUN_ORDER_REJECT,header, json), Result.class);

				if(!result.getReturnCode().equals("200")){
					return result;
				}
				dao.deleteBySql("delete from t_order where c_id='"+order.getId()+"';");
				dao.deleteBySql("delete from t_order_detail where c_order_id='"+order.getId()+"';");
				dao.deleteBySql("delete from t_body_measure where c_order_id='"+order.getId()+"';");
				dao.deleteBySql("delete from t_body_language where c_order_id='"+order.getId()+"';");
				dao.deleteBySql("delete from t_order_attachment where c_order_id='"+order.getId()+"';");
				return new Result();
			}
			if (StringUtils.isNotBlank(dto.getStatus())) {
				order.setStatusBussiness(dto.getStatus());
			}
			if("1".equals(dto.getStatus())){
				order.setSubmitTime(new Date()); 
			}
			if("3".equals(dto.getStatus()) || "4".equals(dto.getStatus())){  
				order.setBusinessCheckerName(dto.getCheckerName());
				order.setXingyuChekTime(new Date());
			}
			if("5".equals(dto.getStatus()) || "2".equals(dto.getStatus())){
				order.setBusinessCheckerName(dto.getCheckerName());
				order.setXiuyuChekTime(new Date());
			}
			if("6".equals(dto.getStatus()) || "7".equals(dto.getStatus()) || "8".equals(dto.getStatus())){
				order.setBusinessCheckerName(dto.getCheckerName());
			}
		}
		dao.update(order);
		OrderCheck orderCheck = new OrderCheck();
		orderCheck.setOrderId(dto.getOrderId());
		orderCheck.setCheckType(dto.getCheckType());
		orderCheck.setCheckerName(dto.getCheckerName());
		orderCheck.setCheckStatus(dto.getCheckStatus());
		orderCheck.setCheckInstruction(dto.getCheckInstruction());
		orderCheck.setCheckTime(new Date());
		dao.save(orderCheck);
		return new Result(order);
	}
	private boolean checkOrderDetailStatus(Order order) {
		for(OrderDetail orderDetail : order.getOrderDetailList()) {
			if(!"6".equals(orderDetail.getOrderDetailStatus())) {
				return false;
			}
		}
		return true;
	}

	private String appendOrderImage(Order order,MultipartFile file) {
		if (file == null) {// 如果没有上传任何图片文件
			return null;
		}
		try {
			String newKey = "xingyu/order/"+ getFileFolder(new Date())+"/"+order.getOrderCode()+"/" +UUID.randomUUID().toString().replace("-", "") + "." + file.getOriginalFilename();
			return fileOSSUtil.uploadPrivateFile(newKey, file.getInputStream());
			// 把上传文件保存到OSS系统，并把OSS系统保存文件成功后返回的该文件的key添加到set集合中
		} catch (IOException e) {// 当读取上传图片文件输入流抛出异常时
			e.printStackTrace();
		}
		return null;
	}
	private Set<String> appendOrderImage(Order order,MultipartFile[] file) {
		Set<String> addedImageNames = null;
		if (file == null) {// 如果没有上传任何图片文件
			return addedImageNames;
		}
		addedImageNames = new HashSet<>();// 初始化保存失败的上传文件名称列表
		for (MultipartFile imageFile : file) {// 遍历上传的图片文件
			try {
				String newKey = "xingyu/order/"+ getFileFolder(new Date())+"/"+order.getOrderCode()+"/" +UUID.randomUUID().toString().replace("-", "") + "." + imageFile.getOriginalFilename();
				newKey = fileOSSUtil.uploadPrivateFile(newKey, imageFile.getInputStream());
				String keys = "\"" + newKey + "\"";
				// 把上传文件保存到OSS系统，并把OSS系统保存文件成功后返回的该文件的key添加到JSON数组中
				addedImageNames.add(keys);
			} catch (IOException e) {// 当读取上传图片文件输入流抛出异常时
				e.printStackTrace();
			}
		}
		return addedImageNames;// 返回保存失败的上传文件名称列表
	}

	private String getFileFolder(Date date) {
		return DateUtil.formatDate(date, "yyyy") + "/" + DateUtil.formatDate(date, "MM") + "/"
				+ DateUtil.formatDate(date, "dd");
	}
	
	private String getSnValue() {
		Date day = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = df.format(day);
		int a = (int) (Math.random() * (9999 - 1000 + 1)) + 1000;
		String sn = date + String.valueOf(a);
		return sn;
	}

	private String getOrderMaxValue() {
		Date date = new Date();
		Criteria criteria = dao.createCriteria(Code.class);
		criteria.add(Restrictions.between("updateTime", DateUtil.minDate(date), DateUtil.maxDate(date)));
		criteria.add(Restrictions.eq("type", "DD"));
		criteria.addOrder(org.hibernate.criterion.Order.desc("maxValue"));
		criteria.setMaxResults(1);
		Code code = (Code) criteria.uniqueResult();
		if (code == null) {
			code = new Code();
			code.setMaxValue(1);
			code.setType("DD");
			code.setUpdateTime(date);
			dao.save(code);
		} else {
			code.setMaxValue(code.getMaxValue() + 1);
		}
		return String.format("%0" + 6 + "d", code.getMaxValue());
	}

	/**
	 * 删除上传到oss的文件
	 * @param orderAttachments
	 * @param delFileList
	 * @return
	 */
	public String[] delOSSFile(List<OrderAttachment> orderAttachments, String[] delFileList) {
		if (delFileList != null) {
			for (String imgKey : delFileList) {
				/*String removeImgKey = fileOSSUtil.fromUrlToKey(imgKey);
				// 在oss 删除 文件
				fileOSSUtil.deletePrivateFile(removeImgKey);*/
				for (OrderAttachment orderAttachment : orderAttachments) {
					if (orderAttachment.getOssKey()!=null&&orderAttachment.getOssKey().contains(imgKey.trim())){
						dao.delete(orderAttachment);
					}
				}
			}
			dao.flush();// 释放缓冲区
		}
		return delFileList;
	}

	/**
	 * 删除上传到oss的文件
	 * @param orderAttachment
	 * @param delFileList
	 * @return
	 */
	public String[] delOSSFile(OrderAttachment orderAttachment, String[] delFileList) {
		if (delFileList != null) {
			JSONArray imgKeys = orderAttachment.getOssKey() == null || orderAttachment.getOssKey().isEmpty()
					? new JSONArray() : JSONArray.fromObject(orderAttachment.getOssKey());// 初始化JSON数组对象
			for (String imgKey : delFileList) {
				String removeImgKey = fileOSSUtil.fromUrlToKey(imgKey);
				imgKeys.remove(imgKey);
				// 在oss 删除 文件
				fileOSSUtil.deletePrivateFile(removeImgKey);
			}
			orderAttachment.setOssKey(imgKeys.toString());
			dao.update(orderAttachment);
			dao.flush();// 释放缓冲区
		}
		return delFileList;
	}
	
	@Override
	public void exportRepairBalExcel(OrderDetailSearchDto dto, HttpServletRequest request, HttpServletResponse response){
		List<ExportOrderDetailSearchModel> odsModelList = queryOrderDetailWithParam(dto);
		String excelFileName = "订单明细";
		OutputStream out = null;
		try {
			final String userAgent = request.getHeader("USER-AGENT");
			if (userAgent.toLowerCase().contains("msie")) {// IE浏览器
				excelFileName = URLEncoder.encode(excelFileName, "UTF8");
			} else if (userAgent.toLowerCase().contains("mozilla") || userAgent.toLowerCase().contains("chrom")) {// google浏览器,火狐浏览器
				excelFileName = new String(excelFileName.getBytes(), "ISO8859-1");
			} else {
				excelFileName = URLEncoder.encode(excelFileName, "UTF8");// 其他浏览器
			}
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.addHeader("content-Disposition", "attachment;filename=" + excelFileName + ".xls");
			response.flushBuffer();
			out = response.getOutputStream();
			Set<String> excludedFieldSet = new HashSet<String>();
			String[] headers = new String[] {"订单编号", "ERP订单号","唯一码","城市", "门店", "性质", "会员名称", "绣字名","商品名称", "颜色", "数量" ,"订单状态" ,"订单明细状态","生产前财务审核状态","发货前财务审核状态","建立时间","星域审核时间","工厂发货日期", "物料编码", "结案状态","尺码"};
			excludedFieldSet.add("orderId");
			
			
			ExportExcelUtil.exportExcel(headers, ExportExcelUtil.buildCustomizedExportedModel(odsModelList, excludedFieldSet), out,"yyyy-MM-dd HH:mm:ss");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} finally {
				try {
					if (out != null)
						out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		
		
		/*List<OrderDetailSearchModel> odsModelList = queryOrderDetailWithParam(dto);
		String excelFileName = "订单明细";
		OutputStream out = null;
		try {
			final String userAgent = request.getHeader("USER-AGENT");
			if (userAgent.toLowerCase().contains("msie")) {// IE浏览器
				excelFileName = URLEncoder.encode(excelFileName, "UTF8");
			} else if (userAgent.toLowerCase().contains("mozilla") || userAgent.toLowerCase().contains("chrom")) {// google浏览器,火狐浏览器
				excelFileName = new String(excelFileName.getBytes(), "ISO8859-1");
			} else {
				excelFileName = URLEncoder.encode(excelFileName, "UTF8");// 其他浏览器
			}
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.addHeader("content-Disposition", "attachment;filename=" + excelFileName + ".xls");
			response.flushBuffer();
			out = response.getOutputStream();
			Set<String> excludedFieldSet = new HashSet<String>();
			String[] headers = new String[] {"订单编号", "ERP订单号","商品唯一码","城市", "店铺名称", "性质", "顾客名称", "商品名称", "颜色", "数量" ,"订单状态" ,"订单明细状态","建立时间" ,"工厂发货日期", "物料编码", "结案状态","尺码" };
			excludedFieldSet.add("orderId");
			ExportExcelUtil.exportExcel(headers, ExportExcelUtil.buildCustomizedExportedModel(odsModelList, excludedFieldSet), out,"yyyy-MM-dd HH:mm:ss");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}  catch (IllegalAccessException e) {
				e.printStackTrace();
			} finally {
				try {
					if (out != null)
						out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}*/
	}
	
	public	List<ExportOrderDetailSearchModel> queryOrderDetailWithParam(OrderDetailSearchDto odsDto){
		
		List<ExportOrderDetailSearchModel> odsModelList = new ArrayList<>();
		StringBuilder sql = new StringBuilder("select a.c_id,a.c_order_code as orderCode,b.c_erp_no as erpNo,b.c_goods_sn as goodsSn,a.c_city as city,a.c_shop_name as shopName,a.c_order_character as orderCharacter,a.c_customer_name as customerName,a.c_embroid_name as embroidName,d.c_name as goodsName,c.c_color_name as goodsColor,b.c_num as num,a.c_status_bussiness as orderStatus,b.c_order_detail_status as orderDetailStutas,a.c_status_before_produce as beforePriduce,a.c_status_before_send as beforeSend,a.c_create_time as createTime,a.c_xiuyu_chek_time as xingyuCheckTime,f.c_send_time as sendTime,c.c_materiel_code as materielCode,b.c_closed_status as closedStatus " +
				"from t_order_detail b " +
				"left join t_order a on a.c_id=b.c_order_id " +
				"left join t_goods_detail c on c.c_id=b.c_goods_detail_id " +
				"left join t_goods d on d.c_id=c.c_goods_id " +
				"left join t_logistic_order e on e.c_order_detail_id=b.c_id " +
				"left join t_logistic f on f.c_id=e.c_logistic_id " +
				"where a.c_delete_flag='0'");
		if(odsDto.getOrderCode() != null && !odsDto.getOrderCode().isEmpty()){
			sql.append(" and a.c_order_code like '%").append(odsDto.getOrderCode()).append("%'");
		}
		if(odsDto.getCustomerName() != null && !odsDto.getCustomerName().isEmpty()){
			sql.append(" and a.c_customer_name like '%").append(odsDto.getCustomerName()).append("%'");
		}
		if(odsDto.getCity() != null && !odsDto.getCity().isEmpty()){
			sql.append(" and a.c_city like '%").append(odsDto.getCity()).append("%'");
		}
		if(odsDto.getShopName() != null && !odsDto.getShopName().isEmpty()){
			sql.append(" and a.c_shop_name like '%").append(odsDto.getShopName()).append("%'");
		}
		if(odsDto.getOrderCharacter() != null && !odsDto.getOrderCharacter().isEmpty()){
			sql.append(" and a.c_order_character = '").append(odsDto.getOrderCharacter()).append("'");
		}
		if(odsDto.getReset() != null && !odsDto.getReset().isEmpty()){
			sql.append(" and a.c_reset = '").append(odsDto.getReset()).append("'");
		}
		if(odsDto.getStatusBussiness() != null && !odsDto.getStatusBussiness().isEmpty()){
			sql.append(" and a.c_status_bussiness = '").append(odsDto.getStatusBussiness()).append("'");
		}
		if(odsDto.getStatusBeforeProduce() != null && !odsDto.getStatusBeforeProduce().isEmpty()){
			sql.append(" and a.c_status_before_produce = '").append(odsDto.getStatusBeforeProduce()).append("'");
		}
		if(odsDto.getStatusBeforeSend() != null && !odsDto.getStatusBeforeSend().isEmpty()){
			sql.append(" and a.c_status_before_send = '").append(odsDto.getStatusBeforeSend()).append("'");
		}
		if(odsDto.getOrderDetailStatus() != null && !odsDto.getOrderDetailStatus().isEmpty()){
			sql.append(" and b.c_order_detail_status = '").append(odsDto.getOrderDetailStatus()).append("'");
		}
		if(odsDto.getGoodsName() != null && !odsDto.getGoodsName().isEmpty()){
			sql.append(" and d.c_name like '%").append(odsDto.getGoodsName()).append("%'");
		}
		if(odsDto.getGoodsColorName() != null && !odsDto.getGoodsColorName().isEmpty()){
			sql.append(" and c.c_color_name = '").append(odsDto.getGoodsColorName()).append("'");
		}
		if(odsDto.getCategoryId() != null && !odsDto.getCategoryId().isEmpty()){
			sql.append(" and d.c_category_id = '").append(odsDto.getCategoryId()).append("'");
		}
		if(odsDto.getErpNo() != null && !odsDto.getErpNo().isEmpty()){
			sql.append(" and b.c_erp_no like '%").append(odsDto.getErpNo()).append("%'");
		}
		if(odsDto.getGoodsSn() != null && !odsDto.getGoodsSn().isEmpty()){
			sql.append(" and b.c_goods_sn like '%").append(odsDto.getGoodsSn()).append("%'");
		}
		//添加订单创建日期限定条件
		if(odsDto.getCreateTimeStart() != null && !odsDto.getCreateTimeStart().isEmpty() && odsDto.getCreateTimeEnd() != null && !odsDto.getCreateTimeEnd().isEmpty()){//添加同时包含起始订单日期和截止订单日期的限定条件
			sql.append(" and a.c_create_time between '").append(odsDto.getCreateTimeStart()).append(" 00:00:00'").append(" and '").append(odsDto.getCreateTimeEnd()).append(" 23:59:59'");
		}
		else if(odsDto.getCreateTimeStart() != null && !odsDto.getCreateTimeStart().isEmpty() && (odsDto.getCreateTimeEnd() == null || odsDto.getCreateTimeEnd().isEmpty())){//添加只包含起始订单日期而不包含截止订单日期的限定条件
			sql.append(" and a.c_create_time >= '").append(odsDto.getCreateTimeStart()).append(" 00:00:00'");
		}
		else if ((odsDto.getCreateTimeStart() == null || odsDto.getCreateTimeStart().isEmpty()) && odsDto.getCreateTimeEnd() != null && !odsDto.getCreateTimeEnd().isEmpty()) {//添加只包含截止订单日期而不包含起始订单日期的限定条件
			sql.append(" and a.c_create_time <= '").append(odsDto.getCreateTimeEnd()).append(" 23:59:59'");
		}
		//添加物流单发货日期限定条件
		if(odsDto.getSendTimeStart() != null && !odsDto.getSendTimeStart().isEmpty() && odsDto.getSendTimeEnd() != null && !odsDto.getSendTimeEnd().isEmpty()){//添加同时包含起始订单日期和截止订单日期的限定条件
			sql.append(" and f.c_send_time between '").append(odsDto.getSendTimeStart()).append(" 00:00:00'").append(" and '").append(odsDto.getSendTimeEnd()).append(" 23:59:59'");
		}
		else if(odsDto.getSendTimeStart() != null && !odsDto.getSendTimeStart().isEmpty() && (odsDto.getSendTimeEnd() == null || odsDto.getSendTimeEnd().isEmpty())){//添加只包含起始订单日期而不包含截止订单日期的限定条件
			sql.append(" and f.c_send_time >= '").append(odsDto.getSendTimeStart()).append(" 00:00:00'");
		}
		else if ((odsDto.getSendTimeStart() == null || odsDto.getSendTimeStart().isEmpty()) && odsDto.getSendTimeEnd() != null && !odsDto.getSendTimeEnd().isEmpty()) {//添加只包含截止订单日期而不包含起始订单日期的限定条件
			sql.append(" and f.c_send_time <= '").append(odsDto.getSendTimeEnd()).append(" 23:59:59'");
		}
		sql.append(" order by a.c_create_time desc");
		List resultSet = dao.queryBySql(sql.toString());		
		for(Object result : resultSet){
			ExportOrderDetailSearchModel odsModel = new ExportOrderDetailSearchModel();//构建返回数据模型
			Object[] properties = (Object[])result;
			odsModel.setOrderId(properties[0]==null ? "" : properties[0].toString());
			odsModel.setOrderCode(properties[1]==null ? "" : properties[1].toString());
			odsModel.setErpNum(properties[2]==null ? "" : properties[2].toString());
			odsModel.setGoodsSN(properties[3]==null ? "" : properties[3].toString());
			odsModel.setCity(properties[4]==null ? "" : properties[4].toString());
			odsModel.setShopName(properties[5]==null ? "" : properties[5].toString());
			odsModel.setCharacteres(properties[6]==null ? "" : properties[6].toString());
			odsModel.setCustomerName(properties[7]==null ? "" : properties[7].toString());
			odsModel.setEmbroidName(properties[8]==null ? "" : properties[8].toString());
			odsModel.setGoodsName(properties[9]==null ? "" : properties[9].toString());
			odsModel.setGoodsColor(properties[10]==null ? "" : properties[10].toString());
			odsModel.setNum(properties[11]==null ? "" : properties[11].toString());
			odsModel.setBillStatus(properties[12]==null ? "" : changeOrderStatus(properties[12].toString()));
			odsModel.setOrderDetailStatus(properties[13]==null ? "" : changeOrderDetailStatus(properties[13].toString()));
			odsModel.setBeforePriduce(properties[14]==null ? "" : changeStatus(properties[14].toString()));
			odsModel.setBeforeSend(properties[15]==null ? "" :changeStatus(properties[15].toString()));
			odsModel.setCreateTime(properties[16]==null ? "" : properties[16].toString().substring(0,10));
			odsModel.setXingyuCheckTime(properties[17]==null ? "" : properties[17].toString().substring(0,10));
			odsModel.setSendTime(properties[18]==null ? "" : properties[18].toString().substring(0,10));
			odsModel.setMaterielCode(properties[19]==null ? "" : properties[19].toString());
			odsModel.setClosedStatus(properties[20]==null ? "" : changeClosedStatus(properties[20].toString()));
			odsModelList.add(odsModel);
		}
		return 	odsModelList;


		/*List<OrderDetailSearchModel> odsModelList = new ArrayList<>();
		StringBuilder sql = new StringBuilder("select a.c_id,a.c_order_code as orderCode,b.c_erp_no as erpNo,b.c_goods_sn as goodsSn,a.c_city as city,a.c_shop_name as shopName,a.c_order_character as orderCharacter,a.c_customer_name as customerName,b.c_goods_name as goodsName,b.c_goods_color_name as goodsColor,b.c_num as num,a.c_status_bussiness as orderStatus,b.c_order_detail_status as orderDetailStutas,a.c_create_time as createTime,f.c_send_time as sendTime,c.c_materiel_code as materielCode,b.c_closed_status as closedStatus from t_order_detail b left join t_order a on a.c_id=b.c_order_id left join t_goods_detail c on c.c_id=b.c_goods_detail_id left join t_goods d on d.c_id=c.c_goods_id left join t_logistic_order e on e.c_order_detail_id=b.c_id left join t_logistic f on f.c_id=e.c_logistic_id where a.c_delete_flag='0'");
		if(odsDto.getOrderCode() != null && !odsDto.getOrderCode().isEmpty()){
			sql.append(" and a.c_order_code like '%").append(odsDto.getOrderCode()).append("%'");
		}
		if(odsDto.getCustomerName() != null && !odsDto.getCustomerName().isEmpty()){
			sql.append(" and a.c_customer_name like '%").append(odsDto.getCustomerName()).append("%'");
		}
		if(odsDto.getCity() != null && !odsDto.getCity().isEmpty()){
			sql.append(" and a.c_city like '%").append(odsDto.getCity()).append("%'");
		}
		if(odsDto.getShopName() != null && !odsDto.getShopName().isEmpty()){
			sql.append(" and a.c_shop_name like '%").append(odsDto.getShopName()).append("%'");
		}
		if(odsDto.getOrderCharacter() != null && !odsDto.getOrderCharacter().isEmpty()){
			sql.append(" and a.c_order_character = '").append(odsDto.getOrderCharacter()).append("'");
		}
		if(odsDto.getReset() != null && !odsDto.getReset().isEmpty()){
			sql.append(" and a.c_reset = '").append(odsDto.getReset()).append("'");
		}
		if(odsDto.getStatusBussiness() != null && !odsDto.getStatusBussiness().isEmpty()){
			sql.append(" and a.c_status_bussiness = '").append(odsDto.getStatusBussiness()).append("'");
		}
		if(odsDto.getStatusBeforeProduce() != null && !odsDto.getStatusBeforeProduce().isEmpty()){
			sql.append(" and a.c_status_before_produce = '").append(odsDto.getStatusBeforeProduce()).append("'");
		}
		if(odsDto.getStatusBeforeSend() != null && !odsDto.getStatusBeforeSend().isEmpty()){
			sql.append(" and a.c_status_before_send = '").append(odsDto.getStatusBeforeSend()).append("'");
		}
		if(odsDto.getOrderDetailStatus() != null && !odsDto.getOrderDetailStatus().isEmpty()){
			sql.append(" and b.c_order_detail_status = '").append(odsDto.getOrderDetailStatus()).append("'");
		}
		if(odsDto.getGoodsName() != null && !odsDto.getGoodsName().isEmpty()){
			sql.append(" and b.c_goods_name like '%").append(odsDto.getGoodsName()).append("%'");
		}
		if(odsDto.getGoodsColorName() != null && !odsDto.getGoodsColorName().isEmpty()){
			sql.append(" and b.c_goods_color_name = '").append(odsDto.getGoodsColorName()).append("'");
		}
		if(odsDto.getCategoryId() != null && !odsDto.getCategoryId().isEmpty()){
			sql.append(" and d.c_category_id = '").append(odsDto.getCategoryId()).append("'");
		}
		if(odsDto.getErpNo() != null && !odsDto.getErpNo().isEmpty()){
			sql.append(" and b.c_erp_no like '%").append(odsDto.getErpNo()).append("%'");
		}
		if(odsDto.getGoodsSn() != null && !odsDto.getGoodsSn().isEmpty()){
			sql.append(" and b.c_goods_sn like '%").append(odsDto.getGoodsSn()).append("%'");
		}
		//添加订单创建日期限定条件
		if(odsDto.getCreateTimeStart() != null && !odsDto.getCreateTimeStart().isEmpty() && odsDto.getCreateTimeEnd() != null && !odsDto.getCreateTimeEnd().isEmpty()){//添加同时包含起始订单日期和截止订单日期的限定条件
			sql.append(" and a.c_create_time between '").append(odsDto.getCreateTimeStart()).append(" 00:00:00'").append(" and '").append(odsDto.getCreateTimeEnd()).append(" 23:59:59'");
		}
		else if(odsDto.getCreateTimeStart() != null && !odsDto.getCreateTimeStart().isEmpty() && (odsDto.getCreateTimeEnd() == null || odsDto.getCreateTimeEnd().isEmpty())){//添加只包含起始订单日期而不包含截止订单日期的限定条件
			sql.append(" and a.c_create_time >= '").append(odsDto.getCreateTimeStart()).append(" 00:00:00'");
		}
		else if ((odsDto.getCreateTimeStart() == null || odsDto.getCreateTimeStart().isEmpty()) && odsDto.getCreateTimeEnd() != null && !odsDto.getCreateTimeEnd().isEmpty()) {//添加只包含截止订单日期而不包含起始订单日期的限定条件
			sql.append(" and a.c_create_time <= '").append(odsDto.getCreateTimeEnd()).append(" 23:59:59'");
		}
		//添加物流单发货日期限定条件
		if(odsDto.getSendTimeStart() != null && !odsDto.getSendTimeStart().isEmpty() && odsDto.getSendTimeEnd() != null && !odsDto.getSendTimeEnd().isEmpty()){//添加同时包含起始订单日期和截止订单日期的限定条件
			sql.append(" and f.c_send_time between '").append(odsDto.getSendTimeStart()).append(" 00:00:00'").append(" and '").append(odsDto.getSendTimeEnd()).append(" 23:59:59'");
		}
		else if(odsDto.getSendTimeStart() != null && !odsDto.getSendTimeStart().isEmpty() && (odsDto.getSendTimeEnd() == null || odsDto.getSendTimeEnd().isEmpty())){//添加只包含起始订单日期而不包含截止订单日期的限定条件
			sql.append(" and f.c_send_time >= '").append(odsDto.getSendTimeStart()).append(" 00:00:00'");
		}
		else if ((odsDto.getSendTimeStart() == null || odsDto.getSendTimeStart().isEmpty()) && odsDto.getSendTimeEnd() != null && !odsDto.getSendTimeEnd().isEmpty()) {//添加只包含截止订单日期而不包含起始订单日期的限定条件
			sql.append(" and f.c_send_time <= '").append(odsDto.getSendTimeEnd()).append(" 23:59:59'");
		}
		sql.append(" order by a.c_create_time desc");
		List resultSet = dao.queryBySql(sql.toString());		
		for(Object result : resultSet){
			OrderDetailSearchModel odsModel = new OrderDetailSearchModel();//构建返回数据模型
			Object[] properties = (Object[])result;
			odsModel.setOrderId(properties[0]==null ? "" : properties[0].toString());
			odsModel.setOrderCode(properties[1]==null ? "" : properties[1].toString());
			odsModel.setErpNum(properties[2]==null ? "" : properties[2].toString());
			odsModel.setGoodsSN(properties[3]==null ? "" : properties[3].toString());
			odsModel.setCity(properties[4]==null ? "" : properties[4].toString());
			odsModel.setShopName(properties[5]==null ? "" : properties[5].toString());
			odsModel.setCharacteres(properties[6]==null ? "" : properties[6].toString());
			odsModel.setCustomerName(properties[7]==null ? "" : properties[7].toString());
			odsModel.setGoodsName(properties[8]==null ? "" : properties[8].toString());
			odsModel.setGoodsColor(properties[9]==null ? "" : properties[9].toString());
			odsModel.setNum(properties[10]==null ? "" : properties[10].toString());
			odsModel.setBillStatus(properties[11]==null ? "" : changeOrderStatus(properties[11].toString()));
			odsModel.setOrderDetailStatus(properties[12]==null ? "" : changeOrderDetailStatus(properties[12].toString()));
			odsModel.setCreateTime(properties[13]==null ? "" : properties[13].toString().substring(0,10));
			odsModel.setSendTime(properties[14]==null ? "" : properties[14].toString().substring(0,10));
			odsModel.setMaterielCode(properties[15]==null ? "" : properties[15].toString());
			odsModel.setClosedStatus(properties[16]==null ? "" : changeClosedStatus(properties[16].toString()));
			odsModelList.add(odsModel);
		}
		return 	odsModelList;*/
	}
	
	
	public String changeStatus(String a) {
		String value = "";
		if("0".equals(a)) {
			value = "未审核";
		}else if("1".equals(a)) {
			value = "审核不通过";
		}else if ("2".equals(a)) {
			value = "审核通过";
		}
		return value;
	}
	
	
	
	@Override
	public	List<OrderDetailSearchModel> queryDetailWithParam(OrderDetailSearchDto odsDto,PageBean pageBean){
		List<OrderDetailSearchModel> odsModelList = new ArrayList<>();
		StringBuilder sql = new StringBuilder("SELECT a.c_id as orderId,a.c_order_code,b.c_erp_no,b.c_goods_sn,a.c_city,a.c_shop_name,a.c_order_character,a.c_customer_name,d.c_name,c.c_color_name,b.c_num,a.c_status_bussiness,b.c_order_detail_status,a.c_create_time,f.c_send_time,c.c_materiel_code,b.c_closed_status,d.c_code,b.c_id " +
				"FROM t_order_detail b " +
				"LEFT JOIN t_order a ON a.c_id=b.c_order_id " +
				"LEFT JOIN t_goods_detail c ON c.c_id=b.c_goods_detail_id " +
				"LEFT JOIN t_goods d ON d.c_id=c.c_goods_id " +
				"LEFT JOIN t_logistic_order e ON e.c_order_detail_id=b.c_id " +
				"LEFT JOIN t_logistic f ON f.c_id=e.c_logistic_id " +
				"where a.c_delete_flag='0' and f.c_delete_flag = '0'");
		if(odsDto.getOrderCode() != null && !odsDto.getOrderCode().isEmpty()){
			sql.append(" and a.c_order_code like '%").append(odsDto.getOrderCode()).append("%'");
		}
		if(odsDto.getCustomerName() != null && !odsDto.getCustomerName().isEmpty()){
			sql.append(" and a.c_customer_name like '%").append(odsDto.getCustomerName()).append("%'");
		}
		if(odsDto.getCity() != null && !odsDto.getCity().isEmpty()){
			sql.append(" and a.c_city like '%").append(odsDto.getCity()).append("%'");
		}
		if(odsDto.getShopName() != null && !odsDto.getShopName().isEmpty()){
			sql.append(" and a.c_shop_name like '%").append(odsDto.getShopName()).append("%'");
		}
		if(odsDto.getOrderCharacter() != null && !odsDto.getOrderCharacter().isEmpty()){
			sql.append(" and a.c_order_character = '").append(odsDto.getOrderCharacter()).append("'");
		}
		if(odsDto.getReset() != null && !odsDto.getReset().isEmpty()){
			sql.append(" and a.c_reset = '").append(odsDto.getReset()).append("'");
		}
		if(odsDto.getStatusBussiness() != null && !odsDto.getStatusBussiness().isEmpty()){
			sql.append(" and a.c_status_bussiness = '").append(odsDto.getStatusBussiness()).append("'");
		}
		if(odsDto.getStatusBeforeProduce() != null && !odsDto.getStatusBeforeProduce().isEmpty()){
			sql.append(" and a.c_status_before_produce = '").append(odsDto.getStatusBeforeProduce()).append("'");
		}
		if(odsDto.getStatusBeforeSend() != null && !odsDto.getStatusBeforeSend().isEmpty()){
			sql.append(" and a.c_status_before_send = '").append(odsDto.getStatusBeforeSend()).append("'");
		}
		if(odsDto.getOrderDetailStatus() != null && !odsDto.getOrderDetailStatus().isEmpty()){
			sql.append(" and b.c_order_detail_status = '").append(odsDto.getOrderDetailStatus()).append("'");
		}
		if(odsDto.getGoodsName() != null && !odsDto.getGoodsName().isEmpty()){
			sql.append(" and d.c_name like '%").append(odsDto.getGoodsName()).append("%'");
		}
		if(odsDto.getGoodsColorName() != null && !odsDto.getGoodsColorName().isEmpty()){
			sql.append(" and c.c_color_name = '").append(odsDto.getGoodsColorName()).append("'");
		}
		if(odsDto.getCategoryId() != null && !odsDto.getCategoryId().isEmpty()){
			sql.append(" and d.c_category_id = '").append(odsDto.getCategoryId()).append("'");
		}
		if(odsDto.getErpNo() != null && !odsDto.getErpNo().isEmpty()){
			sql.append(" and b.c_erp_no like '%").append(odsDto.getErpNo()).append("%'");
		}
		if(odsDto.getGoodsSn() != null && !odsDto.getGoodsSn().isEmpty()){
			sql.append(" and b.c_goods_sn like '%").append(odsDto.getGoodsSn()).append("%'");
		}
		//添加订单创建日期限定条件
		if(odsDto.getCreateTimeStart() != null && !odsDto.getCreateTimeStart().isEmpty() && odsDto.getCreateTimeEnd() != null && !odsDto.getCreateTimeEnd().isEmpty()){//添加同时包含起始订单日期和截止订单日期的限定条件
			sql.append(" and a.c_create_time between '").append(odsDto.getCreateTimeStart()).append(" 00:00:00'").append(" and '").append(odsDto.getCreateTimeEnd()).append(" 23:59:59'");
		}
		else if(odsDto.getCreateTimeStart() != null && !odsDto.getCreateTimeStart().isEmpty() && (odsDto.getCreateTimeEnd() == null || odsDto.getCreateTimeEnd().isEmpty())){//添加只包含起始订单日期而不包含截止订单日期的限定条件
			sql.append(" and a.c_create_time >= '").append(odsDto.getCreateTimeStart()).append(" 00:00:00'");
		}
		else if ((odsDto.getCreateTimeStart() == null || odsDto.getCreateTimeStart().isEmpty()) && odsDto.getCreateTimeEnd() != null && !odsDto.getCreateTimeEnd().isEmpty()) {//添加只包含截止订单日期而不包含起始订单日期的限定条件
			sql.append(" and a.c_create_time <= '").append(odsDto.getCreateTimeEnd()).append(" 23:59:59'");
		}
		//添加物流单发货日期限定条件
		if(odsDto.getSendTimeStart() != null && !odsDto.getSendTimeStart().isEmpty() && odsDto.getSendTimeEnd() != null && !odsDto.getSendTimeEnd().isEmpty()){//添加同时包含起始订单日期和截止订单日期的限定条件
			sql.append(" and f.c_send_time between '").append(odsDto.getSendTimeStart()).append(" 00:00:00'").append(" and '").append(odsDto.getSendTimeEnd()).append(" 23:59:59'");
		}
		else if(odsDto.getSendTimeStart() != null && !odsDto.getSendTimeStart().isEmpty() && (odsDto.getSendTimeEnd() == null || odsDto.getSendTimeEnd().isEmpty())){//添加只包含起始订单日期而不包含截止订单日期的限定条件
			sql.append(" and f.c_send_time >= '").append(odsDto.getSendTimeStart()).append(" 00:00:00'");
		}
		else if ((odsDto.getSendTimeStart() == null || odsDto.getSendTimeStart().isEmpty()) && odsDto.getSendTimeEnd() != null && !odsDto.getSendTimeEnd().isEmpty()) {//添加只包含截止订单日期而不包含起始订单日期的限定条件
			sql.append(" and f.c_send_time <= '").append(odsDto.getSendTimeEnd()).append(" 23:59:59'");
		}
			sql.append(" order by a.c_create_time desc ");
		if(pageBean.getPage()!=null && pageBean.getRows()!=null){
			sql.append(" limit "+(pageBean.getPage() - 1) * pageBean.getRows()+","+pageBean.getRows());
		}
		List resultSet = dao.queryBySql(sql.toString());		
		for(Object result : resultSet){
			OrderDetailSearchModel odsModel = new OrderDetailSearchModel();//构建返回数据模型
			Object[] properties = (Object[])result;
			odsModel.setOrderId(properties[0]==null ? "" : properties[0].toString());
			odsModel.setOrderCode(properties[1]==null ? "" : properties[1].toString());
			odsModel.setErpNum(properties[2]==null ? "" : properties[2].toString());
			odsModel.setGoodsSN(properties[3]==null ? "" : properties[3].toString());
			odsModel.setCity(properties[4]==null ? "" : properties[4].toString());
			odsModel.setShopName(properties[5]==null ? "" : properties[5].toString());
			odsModel.setCharacteres(properties[6]==null ? "" : properties[6].toString());
			odsModel.setCustomerName(properties[7]==null ? "" : properties[7].toString());
			odsModel.setGoodsName(properties[8]==null ? "" : properties[8].toString());
			odsModel.setGoodsColor(properties[9]==null ? "" : properties[9].toString());
			odsModel.setNum(properties[10]==null ? "" : properties[10].toString());
			odsModel.setBillStatus(properties[11]==null ? "" : changeOrderStatus(properties[11].toString()));
			odsModel.setOrderDetailStatus(properties[12]==null ? "" : changeOrderDetailStatus(properties[12].toString()));
			odsModel.setCreateTime(properties[13]==null ? "" : properties[13].toString());
			odsModel.setSendTime(properties[14]==null ? "" : properties[14].toString());
			odsModel.setMaterielCode(properties[15]==null ? "" : properties[15].toString());
			odsModel.setClosedStatus(properties[16]==null ? "" : changeClosedStatus(properties[16].toString()));
			odsModel.setGoodsCode(properties[17]==null ? "" : properties[17].toString());
			odsModel.setOrderDetailId(properties[18]==null ? "" : properties[18].toString());
			odsModelList.add(odsModel); 
		}
		return 	odsModelList;
	}
	
	@Override
	public Long queryCountWithParam(OrderDetailSearchDto odsDto) throws ParseException{
		StringBuilder sql = new StringBuilder("SELECT count(*) " +
				"FROM t_order_detail b " +
				"LEFT JOIN t_order a ON b.c_order_id=a.c_id " +
				"LEFT JOIN t_goods_detail c ON c.c_id=b.c_goods_detail_id " +
				"LEFT JOIN t_goods d ON d.c_id=c.c_goods_id " +
				"LEFT JOIN t_category g on d.c_category_id = g.c_id " +
				"LEFT JOIN t_logistic_order e ON e.c_order_detail_id=b.c_id " +
				"LEFT JOIN t_logistic f ON f.c_id=e.c_logistic_id " +
				"where a.c_delete_flag='0' and f.c_delete_flag = '0'");
		if(odsDto.getOrderCode() != null && !odsDto.getOrderCode().isEmpty()){
			sql.append(" and a.c_order_code like '%").append(odsDto.getOrderCode()).append("%'");
		}
		if(odsDto.getCustomerName() != null && !odsDto.getCustomerName().isEmpty()){
			sql.append(" and a.c_customer_name like '%").append(odsDto.getCustomerName()).append("%'");
		}
		if(odsDto.getCity() != null && !odsDto.getCity().isEmpty()){
			sql.append(" and a.c_city like '%").append(odsDto.getCity()).append("%'");
		}
		if(odsDto.getShopName() != null && !odsDto.getShopName().isEmpty()){
			sql.append(" and a.c_shop_name like '%").append(odsDto.getShopName()).append("%'");
		}
		if(odsDto.getOrderCharacter() != null && !odsDto.getOrderCharacter().isEmpty()){
			sql.append(" and a.c_order_character = '").append(odsDto.getOrderCharacter()).append("'");
		}
		if(odsDto.getReset() != null && !odsDto.getReset().isEmpty()){
			sql.append(" and a.c_reset = '").append(odsDto.getReset()).append("'");
		}
		if(odsDto.getStatusBussiness() != null && !odsDto.getStatusBussiness().isEmpty()){
			sql.append(" and a.c_status_bussiness = '").append(odsDto.getStatusBussiness()).append("'");
		}
		if(odsDto.getStatusBeforeProduce() != null && !odsDto.getStatusBeforeProduce().isEmpty()){
			sql.append(" and a.c_status_before_produce = '").append(odsDto.getStatusBeforeProduce()).append("'");
		}
		if(odsDto.getStatusBeforeSend() != null && !odsDto.getStatusBeforeSend().isEmpty()){
			sql.append(" and a.c_status_before_send = '").append(odsDto.getStatusBeforeSend()).append("'");
		}
		if(odsDto.getOrderDetailStatus() != null && !odsDto.getOrderDetailStatus().isEmpty()){
			sql.append(" and b.c_order_detail_status = '").append(odsDto.getOrderDetailStatus()).append("'");
		}
		if(odsDto.getGoodsName() != null && !odsDto.getGoodsName().isEmpty()){
			sql.append(" and d.c_name like '%").append(odsDto.getGoodsName()).append("%'");
		}
		if(odsDto.getGoodsColorName() != null && !odsDto.getGoodsColorName().isEmpty()){
			sql.append(" and c.c_color_name = '").append(odsDto.getGoodsColorName()).append("'");
		}
		if(odsDto.getCategoryId() != null && !odsDto.getCategoryId().isEmpty()){
			sql.append(" and d.c_category_id = '").append(odsDto.getCategoryId()).append("'");
		}
		if(odsDto.getErpNo() != null && !odsDto.getErpNo().isEmpty()){
			sql.append(" and b.c_erp_no like '%").append(odsDto.getErpNo()).append("%'");
		}
		if(odsDto.getGoodsSn() != null && !odsDto.getGoodsSn().isEmpty()){
			sql.append(" and b.c_goods_sn like '%").append(odsDto.getGoodsSn()).append("%'");
		}
		//添加订单创建日期限定条件
		if(odsDto.getCreateTimeStart() != null && !odsDto.getCreateTimeStart().isEmpty() && odsDto.getCreateTimeEnd() != null && !odsDto.getCreateTimeEnd().isEmpty()){//添加同时包含起始订单日期和截止订单日期的限定条件
			sql.append(" and a.c_create_time between '").append(odsDto.getCreateTimeStart()).append(" 00:00:00'").append(" and '").append(odsDto.getCreateTimeEnd()).append(" 23:59:59'");
		}
		else if(odsDto.getCreateTimeStart() != null && !odsDto.getCreateTimeStart().isEmpty() && (odsDto.getCreateTimeEnd() == null || odsDto.getCreateTimeEnd().isEmpty())){//添加只包含起始订单日期而不包含截止订单日期的限定条件
			sql.append(" and a.c_create_time >= '").append(odsDto.getCreateTimeStart()).append(" 00:00:00'");
		}
		else if ((odsDto.getCreateTimeStart() == null || odsDto.getCreateTimeStart().isEmpty()) && odsDto.getCreateTimeEnd() != null && !odsDto.getCreateTimeEnd().isEmpty()) {//添加只包含截止订单日期而不包含起始订单日期的限定条件
			sql.append(" and a.c_create_time <= '").append(odsDto.getCreateTimeEnd()).append(" 23:59:59'");
		}
		//添加物流单发货日期限定条件
		if(odsDto.getSendTimeStart() != null && !odsDto.getSendTimeStart().isEmpty() && odsDto.getSendTimeEnd() != null && !odsDto.getSendTimeEnd().isEmpty()){//添加同时包含起始订单日期和截止订单日期的限定条件
			sql.append(" and f.c_send_time between '").append(odsDto.getSendTimeStart()).append(" 00:00:00'").append(" and '").append(odsDto.getSendTimeEnd()).append(" 23:59:59'");
		}
		else if(odsDto.getSendTimeStart() != null && !odsDto.getSendTimeStart().isEmpty() && (odsDto.getSendTimeEnd() == null || odsDto.getSendTimeEnd().isEmpty())){//添加只包含起始订单日期而不包含截止订单日期的限定条件
			sql.append(" and f.c_send_time >= '").append(odsDto.getSendTimeStart()).append(" 00:00:00'");
		}
		else if ((odsDto.getSendTimeStart() == null || odsDto.getSendTimeStart().isEmpty()) && odsDto.getSendTimeEnd() != null && !odsDto.getSendTimeEnd().isEmpty()) {//添加只包含截止订单日期而不包含起始订单日期的限定条件
			sql.append(" and f.c_send_time <= '").append(odsDto.getSendTimeEnd()).append(" 23:59:59'");
		}
		List<BigInteger> result = dao.queryBySql(sql.toString());
		return result == null || result.isEmpty() ? 0L : result.get(0).longValue();
	}
	
	
	@Override
	public Pagination<OrderFinishedLabel> getOrderFinishedLabel(OrderFinishedLabel dto, PageBean pageBean) {
		Pagination<OrderFinishedLabel> pagination = new Pagination<>();
		StringBuilder sql = new StringBuilder(" FROM t_order a LEFT " +
				"JOIN t_order_detail b ON a.c_id=b.c_order_id " +
				"LEFT JOIN t_goods_detail c ON c.c_id=b.c_goods_detail_id " +
				"LEFT JOIN t_goods d ON d.c_id=c.c_goods_id " +
				"LEFT JOIN mes_order_plan_detail e ON b.c_id = e.c_order_detail_id and e.c_delete_flag =0 " +
				"LEFT JOIN mes_order_plan f ON e.c_order_plan_id = f.c_id " +
				"WHERE a.c_delete_flag='0' AND f.c_delete_flag='0' ");
		if (StringUtils.isNotBlank(dto.getCode())) {
			sql.append(" and a.c_order_code like '%").append(dto.getCode()).append("%'");
		}
		if (StringUtils.isNotBlank(dto.getPlanNo())) {
			sql.append(" and f.c_plan_number like '%").append(dto.getPlanNo()).append("%'");
		}
		if (StringUtils.isNotBlank(dto.getCustomerName())) {
			sql.append(" and a.c_customer_name like '%").append(dto.getCustomerName()).append("%'");
		}
		if (StringUtils.isNotBlank(dto.getStyleSN())) {
			sql.append(" and b.c_goods_sn like '%").append(dto.getStyleSN()).append("%'");
		}
		if (StringUtils.isNotBlank(dto.getStyleCode())) {
			sql.append(" and d.c_code like '%").append(dto.getStyleCode()).append("%'");
		}
		if (StringUtils.isNotBlank(dto.getStyleName())) {
			sql.append(" and d.c_name like '%").append(dto.getStyleName()).append("%'");
		}
		if (StringUtils.isNotBlank(dto.getColor())) {
			sql.append(" and c.c_color_name like '%").append(dto.getColor()).append("%'");
		}
		if (StringUtils.isNotBlank(dto.getShopName())) {
			sql.append(" and a.c_shop_name like '%").append(dto.getShopName()).append("%'");
		}
		sql.append(" order by a.c_order_code desc ");
		List<BigInteger> result = dao.queryBySql(" select count( b.c_goods_sn) " + sql.toString());
		Long total = ((result == null || result.isEmpty()) ? 0L : result.get(0).longValue());
		if (total != null) {
			pagination.setTotal(total);
		}
		List<OrderFinishedLabel> returnList = new ArrayList<OrderFinishedLabel>();
		sql.append(" limit " + (pageBean.getPage() - 1) * pageBean.getRows() + "," + pageBean.getRows());
		List resultSet = dao.queryBySql("SELECT  b.c_goods_sn,a.c_order_code,a.c_customer_name,d.c_code,d.c_name,c.c_color_name,a.c_shop_name,a.c_embroid_name,a.c_order_character,f.c_plan_number "+sql.toString());
		for (Object item : resultSet) {
			OrderFinishedLabel ofl = new OrderFinishedLabel();// 构建返回数据模型
			Object[] properties = (Object[]) item;
			ofl.setCode(properties[1] == null ? "" : properties[1].toString());
			ofl.setCustomerName(properties[2] == null ? "" : properties[2].toString());
			ofl.setStyleSN(properties[0] == null ? "" : properties[0].toString());
			ofl.setStyleCode(properties[3] == null ? "" : properties[3].toString());
			ofl.setStyleName(properties[4] == null ? "" : properties[4].toString());
			ofl.setColor(properties[5] == null ? "" : properties[5].toString());
			ofl.setShopName(properties[6] == null ? "" : properties[6].toString());
			ofl.setEmbroidName(properties[7] == null ? "" : properties[7].toString());
			ofl.setOrderCharacter(properties[8] == null ? "" : properties[8].toString());
			ofl.setPlanNo(properties[9] == null ? "" : properties[9].toString());
			returnList.add(ofl);
		}
		pagination.setRows(returnList);
		return pagination;
	}
	
	@Override
	public Result remoteOrder(String q,PageBean pageBean){
		Criteria criteria = dao.createCriteria(Order.class);
		if(StringUtils.isNotBlank(q)){
			criteria.add(Restrictions.like("orderCode", q ,MatchMode.ANYWHERE));
		}
		List<Order> list = dao.findListWithPagebeanCriteria(criteria, pageBean);
		return new Result(list);
	}
	
	
	public String changeOrderStatus(String a){
		String value= "";
		if("0".equals(a)){
			value = "未送出";
		}else if("1".equals(a)){
			value = "已送出";
		}else if("2".equals(a)){
			value = "已提交星域";
		}else if("3".equals(a)){
			value = "星域审核通过";
		}else if("4".equals(a)){
			value = "星域驳回";
		}else if("5".equals(a)){
			value = "秀域驳回";
		}else if("6".equals(a)){
			value = "生产完成";
		}else if("7".equals(a)){
			value = "已发货";
		}else if("8".equals(a)){
			value = "已收货";
		}
		return value;
	}
	
	public String changeOrderDetailStatus(String a){
		String value= "";
		if("0".equals(a)){
			value = "已排程";
		}else if("1".equals(a)){
			value = "生产中";
		}else if("2".equals(a)){
			value = "生产完成";
		}else if("3".equals(a)){
			value = "已发货";
		}else if("4".equals(a)){
			value = "已收货";
		}else if("5".equals(a)){
			value = "已反馈";
		}
		return value;
	}
	
	public String changeClosedStatus(String a){
		String value= "";
		if("1".equals(a)){
			value = "未提交";
		}else if("2".equals(a)){
			value = "已提交";
		}else if("3".equals(a)){
			value = "星域审核通过";
		}else if("4".equals(a)){
			value = "星域驳回";
		}
		return value;
	}
	
	@Override
	public Result findSizeCode(String number){
		Criteria criteria = dao.createCriteria(SizeCode.class);
		criteria.add(Restrictions.le("minNumber", Double.parseDouble(number)));
		criteria.add(Restrictions.gt("maxNumber", Double.parseDouble(number)));
		@SuppressWarnings("unchecked")
		List<SizeCode> list = criteria.list();
		if(list!=null && !list.isEmpty()){
			String code = list.get(0).getSizeCode();
			return new Result(code);
		}else{
			return new Result("500","未找到CUP适用规格，请重新填写");
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

	/**
	 * 通过Id查询订单明细
	 * @param id
	 * @return
	 */
	@Override
	public Result orderDetailById(String id) {
		OrderModel model = new OrderModel();
//		OrderDetail orderDetail = dao.findById(OrderDetail.class,id);
//		if (orderDetail ==null){
//			return new Result();
//		}

		Criteria criteriaOrder = dao.createCriteria(Order.class);
		criteriaOrder.createCriteria("orderDetailList","list", JoinType.INNER_JOIN).add(Restrictions.eq("list.id",id));
		Order order = (Order) criteriaOrder.uniqueResult();

		if (order==null){
			return new Result();
		}

//		Order order = dao.findById(Order.class,orderDetail.getOrderId());
//
		model.setOrder(order);
//		List<OrderDetail> orderDetails = new ArrayList<>();
//		orderDetails.add(orderDetail);
//		model.setOrderDetailList(orderDetails);
		BodyMeasure bodyMeasure = dao.findUniqueByProperty(BodyMeasure.class, "orderId", order.getId());
		if (bodyMeasure != null) {
			model.setBodyMeasure(bodyMeasure);
		}
		BodyLanguage bodyLanguage = dao.findUniqueByProperty(BodyLanguage.class, "orderId", order.getId());
		if (bodyLanguage != null) {
			model.setBodyLanguage(bodyLanguage);
		}
		Criteria criteria2 = dao.createCriteria(OrderAttachment.class);
		criteria2.add(Restrictions.eq("orderId", order.getId()));
		@SuppressWarnings("unchecked")
		List<OrderAttachment> list2 = criteria2.list();
		if (list2 != null && list2.size() > 0) {
			model.setOrderAttachmentList(list2);
		}
		Criteria criteria = dao.createCriteria(OrderCheck.class);
		criteria.add(Restrictions.eq("orderId", order.getId()));
		@SuppressWarnings("unchecked")
		List<OrderCheck> list = criteria.list();
		if (list != null && list.size() > 0) {
			model.setOrderCheckList(list);
		}
		return new Result(model);
	}

	@Override
	public Pagination<OrderDetailModel> orderProgress(OrderDetailSearchDto dto, PageBean pageBean) {

		Pagination<OrderDetailModel> pagination = new Pagination<OrderDetailModel>();

		StringBuilder sql = new StringBuilder();
		String sqlWhere   = "",where="";
		String factory="全部";
		String tenantName="''";

		if (StringUtils.isNotBlank(dto.getProdFactory())&&!dto.getProdFactory().equals("全部")){
			factory = dto.getProdFactory();
			sqlWhere=" AND plan.c_prod_factory_id='"+dto.getProdFactory()+"'";
		}

		if (dto.getTenantIds()!=null&&dto.getTenantIds().length>0){
			for (String id:dto.getTenantIds()) {
				where +="'"+id+"',";
			}
			where =" AND tenant.c_id in ("+where.substring(0,where.length()-1)+")";
		}
		if(dto.getFlag().equals("Y")){
			tenantName=" tenant.c_tenant_name ";
		}

		sql.append(" SELECT "+tenantName+" AS tenant_name,category.c_name AS category_name ,orderDetail.c_goods_color_name,SUM(submit_num) AS submit_num,SUM(audit_num) AS audit_num,SUM(business_checker_num) AS business_checker_num,SUM(assign_num) AS assign_num,SUM(technical_finish_num) AS technical_finish_num,SUM(issued_num) AS issued_num,SUM(finish_num) AS finish_num,SUM(send_num) AS send_num" +
				" FROM (" +
				" SELECT orderDetail.c_id AS order_detail_id,1 AS submit_num,0 AS audit_num,0 AS business_checker_num,0 AS assign_num,0 AS technical_finish_num,0 AS issued_num,0 AS finish_num,0 AS send_num" +
				" FROM t_order torder" +
				" JOIN t_order_detail orderDetail ON torder.c_id = orderDetail.c_order_id" +
				" WHERE torder.c_delete_flag='0' AND torder.c_submit_time>='"+dto.getCreateTimeStart()+" 00:00:00' AND  torder.c_submit_time<='"+dto.getCreateTimeEnd()+" 23:59:59'" +
				" UNION ALL" +
				" SELECT orderDetail.c_id AS order_detail_id,0 AS submit_num,1 AS audit_num,0 AS business_checker_num,0 AS assign_num,0 AS technical_finish_num,0 AS issued_num,0 AS finish_num,0 AS send_num" +
				" FROM t_order torder" +
				" JOIN t_order_detail orderDetail ON torder.c_id = orderDetail.c_order_id" +
				" WHERE torder.c_delete_flag='0' AND torder.c_xingyu_chek_time>='"+dto.getCreateTimeStart()+" 00:00:00' AND  torder.c_xingyu_chek_time<='"+dto.getCreateTimeEnd()+" 23:59:59'" +
				" UNION ALL" +
				" SELECT orderDetail.c_id AS order_detail_id,0 AS submit_num,0 AS audit_num,1 AS business_checker_num,0 AS assign_num,0 AS technical_finish_num,0 AS issued_num,0 AS finish_num,0 AS send_num" +
				" FROM t_order torder" +
				" JOIN t_order_detail orderDetail ON torder.c_id = orderDetail.c_order_id" +
				" WHERE torder.c_delete_flag='0' AND torder.c_business_checker_time>='"+dto.getCreateTimeStart()+" 00:00:00' AND  torder.c_business_checker_time<='"+dto.getCreateTimeEnd()+" 23:59:59'" +
				" UNION ALL" +
				" SELECT assign.c_order_detail_id,0 AS submit_num,0 AS audit_num,0 AS business_checker_num,1 AS assign_num,0 AS technical_finish_num,0 AS issued_num,0 AS finish_num,0 AS send_num" +
				" FROM mes_order_detail_assign assign" +
				" WHERE assign.c_delete_flag=0 AND assign.c_create_time >='"+dto.getCreateTimeStart()+" 00:00:00' AND assign.c_create_time<='"+dto.getCreateTimeEnd()+" 23:59:59'" +
				" UNION ALL" +
				" SELECT assign.c_order_detail_id,0 AS submit_num,0 AS audit_num,0 AS business_checker_num,0 AS assign_num,1 AS technical_finish_num,0 AS issued_num,0 AS finish_num,0 AS send_num" +
				" FROM mes_order_detail_assign assign" +
				" WHERE assign.c_delete_flag=0 AND assign.c_technical_finished_time >='"+dto.getCreateTimeStart()+" 00:00:00' AND assign.c_technical_finished_time<='"+dto.getCreateTimeEnd()+" 23:59:59'" +
				" UNION ALL" +
				" SELECT logisticOrder.c_order_detail_id order_detail_id,0 AS submit_num,0 AS audit_num,0 AS business_checker_num,0 AS assign_num,0 AS technical_finish_num,0 AS issued_num,0 AS finish_num,1 AS send_num" +
				" FROM t_logistic logistic" +
				" JOIN t_logistic_order logisticOrder ON logistic.c_id = logisticOrder.c_logistic_id AND IFNULL(logisticOrder.c_order_detail_id,'') <>''" +
				" JOIN mes_order_plan_detail planDetail ON logisticOrder.c_order_detail_id = planDetail.c_order_detail_id AND planDetail.c_delete_flag=0 " +
				" JOIN mes_order_plan plan ON  plan.c_id = planDetail.c_order_plan_id AND planDetail.c_delete_flag=0" +
				" WHERE logistic.c_logistic_type='0' AND logistic.c_delete_flag='0' AND logisticOrder.c_delete_flag='0'  AND" +
				" logistic.c_send_time >='"+dto.getCreateTimeStart()+" 00:00:00' AND logistic.c_send_time <='"+dto.getCreateTimeEnd()+" 23:59:59'" +sqlWhere+
				" UNION ALL" +
				" SELECT planDetail.c_order_detail_id AS order_detail_id,0 AS submit_num,0 AS audit_num,0 AS business_checker_num,0 AS assign_num,0 AS technical_finish_num,1 AS issued_num,0 AS finish_num,0 AS send_num" +
				" FROM mes_order_plan plan" +
				" JOIN mes_order_plan_detail planDetail ON plan.c_id = planDetail.c_order_plan_id AND planDetail.c_delete_flag=0" +
				" WHERE plan.c_delete_flag=0 AND plan.c_send_time >='"+dto.getCreateTimeStart()+" 00:00:00' AND plan.c_send_time<='"+dto.getCreateTimeEnd()+" 23:59:59'" +sqlWhere+
				" UNION ALL" +
				" SELECT planDetail.c_order_detail_id AS order_detail_id,0 AS submit_num,0 AS audit_num,0 AS business_checker_num,0 AS assign_num,0 AS technical_finish_num,0 AS issued_num,1 AS finish_num,0 AS send_num" +
				" FROM mes_order_plan plan" +
				" JOIN mes_order_plan_detail planDetail ON plan.c_id = planDetail.c_order_plan_id AND planDetail.c_delete_flag=0" +
				" WHERE plan.c_delete_flag=0 AND planDetail.c_prod_finish_time >='"+dto.getCreateTimeStart()+" 00:00:00' AND planDetail.c_prod_finish_time<='"+dto.getCreateTimeEnd()+" 23:59:59'" +sqlWhere+
				" ) tb" +
				" JOIN t_order_detail orderDetail ON tb.order_detail_id = orderDetail.c_id" +
				" JOIN t_order torder ON orderDetail.c_order_id = torder.c_id AND torder.c_delete_flag='0'" +
				" JOIN t_tenant tenant ON torder.c_tenant_id = tenant.c_id" +
				" JOIN t_goods goods ON orderDetail.c_goods_code = goods.c_code" +
				" JOIN t_category category ON goods.c_category_id = category.c_id" +
				" WHERE 1=1 " +where+
				" GROUP BY "+tenantName+",category.c_name ,orderDetail.c_goods_color_name ");

		List resultSet = dao.queryBySql(sql.toString());
		List<OrderDetailModel> list = new ArrayList<>();
		if (resultSet != null && resultSet.size() > 0) {
			for (Object result : resultSet) {
				OrderDetailModel model = new OrderDetailModel();
				Object[] properties = (Object[]) result;
				model.setTenantName(properties[0] == null ? "" : properties[0].toString());
				model.setCategoryName(properties[1] == null ? "" : properties[1].toString());
				model.setGoodsColor(properties[2] == null ? "" : properties[2].toString());
				model.setSubmitNum(properties[3] == null ? "0" : properties[3].toString());
				model.setAuditNum(properties[4] == null ? "0" : properties[4].toString());
				model.setBusinessCheckerNum(properties[5] == null ? "0" : properties[5].toString());
				model.setAssignNum(properties[6] == null ? "0" : properties[6].toString());
				model.setTechnicalFinishNum(properties[7] == null ? "0" : properties[7].toString());
				model.setIssuedNum(properties[8] == null ? "0" : properties[8].toString());
				model.setFinishNum(properties[9] == null ? "0" : properties[9].toString());
				model.setSendNum(properties[10] == null ? "0" : properties[10].toString());
				model.setProdFactory(factory);
				list.add(model);
			}
		}

		pagination.setRows(list);
		pagination.setTotal(list.size());
		return pagination;
	}

	@Override
	public Result orderProgressExportExcel(OrderDetailSearchDto dto, HttpServletRequest request, HttpServletResponse response) {
		String excelFileName = "订单进度" ;
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
			String[] fields = new String[] {"tenantName","categoryName","goodsColor","submitNum","auditNum","businessCheckerNum","assignNum","technicalFinishNum","prodFactory","issuedNum","finishNum","sendNum"};


			headers =new String[]{"客户","产品类别","颜色","店铺提交数量","星域审核数量","财务审核数量","分配订单数量","技术完成数量","生产工厂","计划下达数量","生产完成数量","发货数量"};

			out = response.getOutputStream();
			ExportExcelUtil.exportExcel(0,0,0,"订单进度" , headers,ExportExcelUtil.buildExportedModel(this.orderProgress(dto,new PageBean()).getRows(), fields) , out, "yyyy-MM-dd");
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
