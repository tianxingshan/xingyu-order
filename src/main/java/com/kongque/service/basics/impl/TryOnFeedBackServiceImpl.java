package com.kongque.service.basics.impl;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.kongque.dao.IDaoService;
import com.kongque.dto.TryClothesRecordDto;
import com.kongque.dto.TryOnAuditOpinionDto;
import com.kongque.dto.TryOnFeedBackDto;
import com.kongque.entity.basics.Code;
import com.kongque.entity.basics.TryOnAuditOpinion;
import com.kongque.entity.basics.TryOnFeedBack;
import com.kongque.model.TryClothesRecordModel;
import com.kongque.model.TryOnModel;
import com.kongque.model.TryOnStyleModel;
import com.kongque.model.TryonAuditOpinionModel;
import com.kongque.service.basics.ITryOnFeedBackService;
import com.kongque.util.CodeUtil;
import com.kongque.util.DateUtil;
import com.kongque.util.FileOSSUtil;
import com.kongque.util.Result;

import net.sf.json.JSONArray;


@Service
public class TryOnFeedBackServiceImpl implements ITryOnFeedBackService{
	@Resource
	private IDaoService dao;
	//@Resource
	//private IFileOssService fileService;
	@Resource
	private FileOSSUtil fileOSSUtil;
	@Override
	public List<TryClothesRecordModel> getTryClothesRecordList(TryClothesRecordDto dto, Integer page, Integer rows) {
		List<TryClothesRecordModel> tryOnModelList = new ArrayList<>();
		StringBuilder sql = new StringBuilder("SELECT c.c_order_code AS orderCode,d.c_customer_code AS customerCode,d.c_customer_name AS customerName,b.c_goods_sn AS styleSn,q.c_name AS styleName,e.c_color_name AS color,c.c_shop_name AS shopName,c.c_city AS city,c.c_measurer_name AS measurer,g.c_send_time AS sendTime,c.c_order_character AS characte,c.c_create_time AS createTime,a.c_status AS statuss,a.c_create_time AS feedTime,a.c_id AS tryOnId,c.c_id AS orderId,e.c_id AS styleId,b.c_id AS orderDetailId,p.c_create_time AS checkTime,b.c_order_detail_status AS orderDetailStatus,c.c_embroid_name  as embroidName,a.c_goods_detail_id as goodsDetailId FROM t_tryon_feedback a LEFT JOIN (SELECT *,max(c_create_time) as bigTime FROM t_tryon_audit_opinion group by c_tryon_feedback_id) p ON p.c_tryon_feedback_id =a.c_id LEFT JOIN t_order_detail b ON a.c_order_detail_id = b.c_id LEFT JOIN t_order c ON b.c_order_id=c.c_id LEFT JOIN t_xiuyu_customer d ON 	c.c_customer_id=d.c_id LEFT JOIN t_goods_detail e ON a.c_goods_detail_id = e.c_id LEFT JOIN t_goods q ON e.c_goods_id = q.c_id LEFT JOIN t_logistic_order f ON b.c_id=f.c_order_detail_id LEFT JOIN t_logistic g ON f.c_logistic_id=g.c_id WHERE b.c_order_detail_status='3'");
		//根据试衣单号查询
		if(dto.getOrderCode() != null && !dto.getOrderCode().isEmpty()){
			sql.append(" and c.c_order_code like '%").append(dto.getOrderCode()).append("%'");
		}
		//根据店铺名称查询
		if(dto.getShopName() != null && !dto.getShopName().isEmpty()){
			sql.append(" and c.c_shop_name like '%").append(dto.getShopName()).append("%'");
		}
		//根据会员卡号查询
		if(dto.getCustomerCode() != null && !dto.getCustomerCode().isEmpty()){
			sql.append(" and d.c_customer_code like '%").append(dto.getCustomerCode()).append("%'");
		}
		//根据城市查询 
		if(dto.getCity() != null && !dto.getCity().isEmpty()){
			sql.append(" and c.c_city like '%").append(dto.getCity()).append("%'");
		}
		//根据商品名称查询
		if(dto.getStyleName() != null && !dto.getStyleName().isEmpty()){
			sql.append(" and q.c_name like '%").append(dto.getStyleName()).append("%'");
		}
		//根据会员姓名查询
		if(dto.getCustomerName() != null && !dto.getCustomerName().isEmpty()){
			sql.append(" and d.c_customer_name like '%").append(dto.getCustomerName()).append("%'");
		}
		//根据量体人查询
		if(dto.getMeasurer() != null && !dto.getMeasurer().isEmpty()){
			sql.append(" and c.c_measurer_name like '%").append(dto.getMeasurer()).append("%'");
		}
		//根据结案单申请人人查询
		if(dto.getClosedApplicant() != null && !dto.getClosedApplicant().isEmpty()){
			sql.append(" and c.c_closed_applicant like '%").append(dto.getClosedApplicant()).append("%'");
		}
		
		//根据提交时间查询
		if(dto.getSendTimeStar() != null && !dto.getSendTimeStar().isEmpty() && dto.getSendTimeEnd() != null && !dto.getSendTimeEnd().isEmpty()){
			sql.append(" and c_closed_submittime between '").append(dto.getSendTimeStar()).append(" 00:00:00'").append(" and '").append(dto.getSendTimeEnd()).append(" 23:59:59'");
		}
		if(dto.getSendTimeStar() != null && !dto.getSendTimeStar().isEmpty() && (dto.getSendTimeEnd() == null || dto.getSendTimeEnd().isEmpty())){
			sql.append(" and c_closed_submittime >= '").append(dto.getSendTimeStar()).append(" 00:00:00'");
		}
		if(dto.getSendTimeEnd() != null && !dto.getSendTimeEnd().isEmpty() && (dto.getSendTimeStar() == null || dto.getSendTimeStar().isEmpty())){
			sql.append(" and c_closed_submittime <= '").append(dto.getSendTimeEnd()).append(" 23:59:59'");
		}
		//根据颜色查询
		if(dto.getColor() != null && !dto.getColor().isEmpty()){
			sql.append(" and e.c_color_name like '%").append(dto.getColor()).append("%'");
		}
		//根据试衣单性质查询
		if(dto.getCharacte() != null && !dto.getCharacte().isEmpty() && !"全部".equals(dto.getCharacte())){
			sql.append(" and c.c_order_character = '").append(dto.getCharacte()).append("'");
		}
		//根据反馈时间查询
		if(dto.getFeedTimeStar() != null && !dto.getFeedTimeStar().isEmpty() && dto.getFeedTimeEnd() != null && !dto.getFeedTimeEnd().isEmpty()){
			sql.append(" and a.c_closed_createtime between '").append(dto.getFeedTimeStar()).append(" 00:00:00'").append(" and '").append(dto.getFeedTimeEnd()).append(" 23:59:59'");
		}
		if(dto.getFeedTimeStar() != null && !dto.getFeedTimeStar().isEmpty() && (dto.getFeedTimeEnd() == null || dto.getFeedTimeEnd().isEmpty())){
			sql.append(" and a.c_closed_createtime >= '").append(dto.getFeedTimeStar()).append(" 00:00:00'");
		}
		if(dto.getFeedTimeEnd() != null && !dto.getFeedTimeEnd().isEmpty() && (dto.getFeedTimeStar() == null || dto.getFeedTimeStar().isEmpty())){
			sql.append(" and a.c_closed_createtime <= '").append(dto.getSendTimeEnd()).append(" 23:59:59'");
		}
		
	
		
		
		//根据商品唯一码查询
		if(dto.getStyleSn() != null && !dto.getStyleSn().isEmpty()){
			sql.append(" and b.c_goods_sn like '%").append(dto.getStyleSn()).append("%'");
		}
		//根据试衣单状态查询
		/*if (!StringUtils.isBlank(dto.getBillStatus()) && (!"全部".equals(dto.getBillStatus()))) {
			sql.append(" and c.c_bill_status like '%").append(dto.getBillStatus()).append("%'");
		}*/
		//根据反馈状态查询
		if (!StringUtils.isBlank(dto.getStatuss()) && (!"全部".equals(dto.getStatuss()))) {
			if ("1".equals(dto.getStatuss())) {
				sql.append(" and a.c_status = 1"); //通过
			} else if ("0".equals(dto.getStatuss())) {
				sql.append(" and a.c_status IS NULL"); //待审核
			}else if ("2".equals(dto.getStatuss())) {
				sql.append(" and a.c_status = 2"); //驳回
			}else if ("3".equals(dto.getStatuss())) { 
				sql.append(" and a.c_status = 3"); //已提交为审核
			}else if ("4".equals(dto.getStatuss())) {
				sql.append(" and a.c_status = 4"); //未提交
			}
		}
		//根据通过内容查询
	/*	if (!StringUtils.isBlank(dto.getPassFeedback()) && (!"全部".equals(dto.getPassFeedback()))) {
			if ("1".equals(dto.getPassFeedback())) {
				sql.append(" and h.c_pass_feedback = 1"); //满意
			} else if ("2".equals(dto.getPassFeedback())) {
				sql.append(" and h.c_pass_feedback = 2"); //暂时满意
			}else if ("3".equals(dto.getPassFeedback())) {
				sql.append(" and h.c_pass_feedback = 3"); //微调
			}else if ("4".equals(dto.getPassFeedback())) {
				sql.append(" and h.c_pass_feedback = 4"); //很不满意
			}
		}*/
		//根据审核时间查询
		if(dto.getCheckTimeStar() != null && !dto.getCheckTimeStar().isEmpty() && dto.getCheckTimeEnd()!= null && !dto.getCheckTimeEnd().isEmpty()){
			sql.append(" and a.c_checktime between '").append(dto.getCheckTimeStar()).append(" 00:00:00'").append(" and '").append(dto.getCheckTimeEnd()).append(" 23:59:59'");
		}
		if(dto.getCheckTimeStar() != null && !dto.getCheckTimeStar().isEmpty() && (dto.getCheckTimeEnd() == null || dto.getCheckTimeEnd().isEmpty())){
			sql.append(" and a.c_checktime >= '").append(dto.getCheckTimeStar()).append(" 00:00:00'");
		}
		if(dto.getCheckTimeEnd() != null && !dto.getCheckTimeEnd().isEmpty() && (dto.getCheckTimeStar() == null || dto.getCheckTimeStar().isEmpty())){
			sql.append(" and a.c_checktime <= '").append(dto.getCheckTimeEnd()).append(" 23:59:59'");
		}
		sql.append(" order by c.c_create_time desc");
		sql.append(" limit "+(page - 1) * rows+","+rows);
		@SuppressWarnings("rawtypes")
		List resultSet = dao.queryBySql(sql.toString());
		for(Object result : resultSet){
			TryClothesRecordModel odsModel = new TryClothesRecordModel();//构建返回数据模型
			Object[] properties = (Object[])result;
			odsModel.setOrderCode(properties[0]==null ? "" : properties[0].toString());
			odsModel.setCustomerCode(properties[1]==null ? "" : properties[1].toString());
			odsModel.setCustomerName(properties[2]==null ? "" : properties[2].toString());
			odsModel.setStyleSn(properties[3]==null ? "" : properties[3].toString());
			odsModel.setStyleName(properties[4]==null ? "" : properties[4].toString());
			odsModel.setColor(properties[5]==null ? "" : properties[5].toString());
			odsModel.setShopName(properties[6]==null ? "" : properties[6].toString());
			odsModel.setCity(properties[7]==null ? "" : properties[7].toString());
			odsModel.setMeasurer(properties[8]==null ? "" : properties[8].toString());
			odsModel.setSendTime(properties[9]==null ? "" : properties[9].toString());
			odsModel.setCharacte(properties[10]==null ? "" : properties[10].toString());
			//odsModel.setBillStatus(properties[11]==null ? "" : properties[11].toString());
			odsModel.setCreateTime(properties[11]==null ? "" : properties[11].toString());
			odsModel.setStatuss(properties[12]==null ? "" : properties[12].toString());
			odsModel.setFeedTime(properties[13]==null ? "" : properties[13].toString());
			odsModel.setTryOnId(properties[14]==null ? "" : properties[14].toString());
			odsModel.setOrderId(properties[15]==null ? "" : properties[15].toString());
			odsModel.setStyleId(properties[16]==null ? "" : properties[16].toString()); 
			odsModel.setOrderDetailId(properties[17]==null ? "" : properties[17].toString());
			odsModel.setCheckTime(properties[18]==null ? "" : properties[18].toString());
			odsModel.setOrderDetailStatus(properties[19]==null ? "" : properties[19].toString());
			odsModel.setEmbroidName(properties[20]==null ? "" : properties[20].toString());
			odsModel.setGoodsDetailId(properties[21]==null ? "" : properties[21].toString());
			odsModel.getStyleName();
			tryOnModelList.add(odsModel);
		}
		return tryOnModelList;
	}

	@Override
	public Long getTryClothesRecordCount(TryClothesRecordDto dto) {
		StringBuilder sql = new StringBuilder("SELECT count(*) FROM t_order_detail b LEFT JOIN t_tryon_feedback a ON a.c_order_detail_id=b.c_id LEFT JOIN t_tryon_audit_opinion p ON p.c_tryon_feedback_id =a.c_id LEFT JOIN t_order c ON b.c_order_id=c.c_id LEFT JOIN t_xiuyu_customer d ON 	c.c_customer_id=d.c_id LEFT JOIN t_goods_detail e ON a.c_goods_detail_id = e.c_id LEFT JOIN t_goods q ON e.c_goods_id = q.c_id LEFT JOIN t_logistic_order f ON b.c_id=f.c_order_detail_id LEFT JOIN t_logistic g ON f.c_logistic_id=g.c_id WHERE  b.c_order_detail_status='3'");
		//根据试衣单号查询
		if(dto.getOrderCode() != null && !dto.getOrderCode().isEmpty()){
			sql.append(" and c.c_order_code like '%").append(dto.getOrderCode()).append("%'");
		}
		//根据店铺名称查询
		if(dto.getShopName() != null && !dto.getShopName().isEmpty()){
			sql.append(" and c.c_shop_name like '%").append(dto.getShopName()).append("%'");
		}
		//根据会员卡号查询
		if(dto.getCustomerCode() != null && !dto.getCustomerCode().isEmpty()){
			sql.append(" and d.c_customer_code like '%").append(dto.getCustomerCode()).append("%'");
		}
		//根据城市查询 
		if(dto.getCity() != null && !dto.getCity().isEmpty()){
			sql.append(" and c.c_city like '%").append(dto.getCity()).append("%'");
		}
		//根据商品名称查询
		if(dto.getStyleName() != null && !dto.getStyleName().isEmpty()){
			sql.append(" and q.c_name like '%").append(dto.getStyleName()).append("%'");
		}
		//根据会员姓名查询
		if(dto.getCustomerName() != null && !dto.getCustomerName().isEmpty()){
			sql.append(" and d.c_customer_name like '%").append(dto.getCustomerName()).append("%'");
		}
		//根据量体人查询
		if(dto.getMeasurer() != null && !dto.getMeasurer().isEmpty()){
			sql.append(" and c.c_measurer_name like '%").append(dto.getMeasurer()).append("%'");
		}
		//根据发货时间查询
		if(dto.getSendTimeStar() != null && !dto.getSendTimeStar().isEmpty() && dto.getSendTimeEnd() != null && !dto.getSendTimeEnd().isEmpty()){
			sql.append(" and g.c_send_time between '").append(dto.getSendTimeStar()).append(" 00:00:00'").append(" and '").append(dto.getSendTimeEnd()).append(" 23:59:59'");
		}
		if(dto.getSendTimeStar() != null && !dto.getSendTimeStar().isEmpty() && (dto.getSendTimeEnd() == null || dto.getSendTimeEnd().isEmpty())){
			sql.append(" and g.c_send_time >= '").append(dto.getSendTimeStar()).append(" 00:00:00'");
		}
		if(dto.getSendTimeEnd() != null && !dto.getSendTimeEnd().isEmpty() && (dto.getSendTimeStar() == null || dto.getSendTimeStar().isEmpty())){
			sql.append(" and g.c_send_time <= '").append(dto.getSendTimeEnd()).append(" 23:59:59'");
		}
		//根据颜色查询
		if(dto.getColor() != null && !dto.getColor().isEmpty()){
			sql.append(" and e.c_color_name like '%").append(dto.getColor()).append("%'");
		}
		//根据试衣单性质查询
		if(dto.getCharacte() != null && !dto.getCharacte().isEmpty() && !"全部".equals(dto.getCharacte())){
			sql.append(" and c.c_order_character = '").append(dto.getCharacte()).append("'");
		}
		//根据反馈时间查询
		if(dto.getFeedTimeStar() != null && !dto.getFeedTimeStar().isEmpty() && dto.getFeedTimeEnd() != null && !dto.getFeedTimeEnd().isEmpty()){
			sql.append(" and a.c_create_time between '").append(dto.getFeedTimeStar()).append(" 00:00:00'").append(" and '").append(dto.getFeedTimeEnd()).append(" 23:59:59'");
		}
		if(dto.getFeedTimeStar() != null && !dto.getFeedTimeStar().isEmpty() && (dto.getFeedTimeEnd() == null || dto.getFeedTimeEnd().isEmpty())){
			sql.append(" and a.c_create_time >= '").append(dto.getFeedTimeStar()).append(" 00:00:00'");
		}
		if(dto.getFeedTimeEnd() != null && !dto.getFeedTimeEnd().isEmpty() && (dto.getFeedTimeStar() == null || dto.getFeedTimeStar().isEmpty())){
			sql.append(" and a.c_create_time <= '").append(dto.getSendTimeEnd()).append(" 23:59:59'");
		}
		
	
		
		
		//根据商品唯一码查询
		if(dto.getStyleSn() != null && !dto.getStyleSn().isEmpty()){
			sql.append(" and b.c_goods_sn like '%").append(dto.getStyleSn()).append("%'");
		}
		//根据试衣单状态查询
		/*if (!StringUtils.isBlank(dto.getBillStatus()) && (!"全部".equals(dto.getBillStatus()))) {
			sql.append(" and c.c_bill_status like '%").append(dto.getBillStatus()).append("%'");
		}*/
		//根据反馈状态查询
		if (!StringUtils.isBlank(dto.getStatuss()) && (!"全部".equals(dto.getStatuss()))) {
			if ("1".equals(dto.getStatuss())) {
				sql.append(" and a.c_status = 1"); //通过
			} else if ("0".equals(dto.getStatuss())) {
				sql.append(" and a.c_status IS NULL"); //待审核
			}else if ("2".equals(dto.getStatuss())) {
				sql.append(" and a.c_status = 2"); //驳回
			}else if ("3".equals(dto.getStatuss())) { 
				sql.append(" and a.c_status = 3"); //已提交为审核
			}else if ("4".equals(dto.getStatuss())) {
				sql.append(" and a.c_status = 4"); //未提交
			}
		}
		//根据通过内容查询
	/*	if (!StringUtils.isBlank(dto.getPassFeedback()) && (!"全部".equals(dto.getPassFeedback()))) {
			if ("1".equals(dto.getPassFeedback())) {
				sql.append(" and h.c_pass_feedback = 1"); //满意
			} else if ("2".equals(dto.getPassFeedback())) {
				sql.append(" and h.c_pass_feedback = 2"); //暂时满意
			}else if ("3".equals(dto.getPassFeedback())) {
				sql.append(" and h.c_pass_feedback = 3"); //微调
			}else if ("4".equals(dto.getPassFeedback())) {
				sql.append(" and h.c_pass_feedback = 4"); //很不满意
			}
		}*/
		//根据审核时间查询
		if(dto.getCheckTimeStar() != null && !dto.getCheckTimeStar().isEmpty() && dto.getCheckTimeEnd()!= null && !dto.getCheckTimeEnd().isEmpty()){
			sql.append(" and a.c_checktime between '").append(dto.getCheckTimeStar()).append(" 00:00:00'").append(" and '").append(dto.getCheckTimeEnd()).append(" 23:59:59'");
		}
		if(dto.getCheckTimeStar() != null && !dto.getCheckTimeStar().isEmpty() && (dto.getCheckTimeEnd() == null || dto.getCheckTimeEnd().isEmpty())){
			sql.append(" and a.c_checktime >= '").append(dto.getCheckTimeStar()).append(" 00:00:00'");
		}
		if(dto.getCheckTimeEnd() != null && !dto.getCheckTimeEnd().isEmpty() && (dto.getCheckTimeStar() == null || dto.getCheckTimeStar().isEmpty())){
			sql.append(" and a.c_checktime <= '").append(dto.getCheckTimeEnd()).append(" 23:59:59'");
		}
		List<BigInteger> result = dao.queryBySql(sql.toString());
		return result == null || result.isEmpty() ? 0L : result.get(0).longValue();
	}


	@Override
	public Result showOrderDetail(TryOnFeedBackDto dto) {
		List<TryClothesRecordModel> tryOnModelList = new ArrayList<>();
		StringBuilder sql = new StringBuilder("	SELECT c.c_order_code AS orderCode,d.c_customer_code AS customerCode,d.c_customer_name AS customerName,b.c_goods_sn AS styleSn,q.c_name AS styleName,e.c_color_name AS color,c.c_shop_name AS shopName,c.c_city AS city,c.c_measurer_name AS measurer,g.c_send_time AS sendTime,c.c_order_character AS characte,c.c_create_time AS createTime,h.c_status AS statuss,h.c_create_time AS feedTime,h.c_size AS extendedFileName,h.c_instruction AS description,h.c_file_keys AS filePath FROM t_order_detail b LEFT JOIN t_order c ON b.c_order_id=c.c_id LEFT JOIN t_xiuyu_customer d ON c.c_customer_id=d.c_id LEFT JOIN t_logistic_order f ON b.c_id=f.c_order_detail_id LEFT JOIN t_logistic g ON f.c_logistic_id=g.c_id LEFT JOIN t_tryon_feedback h ON b.c_id=h.c_order_detail_id LEFT JOIN t_goods_detail e ON h.c_goods_detail_id = e.c_id LEFT JOIN t_goods q ON e.c_goods_id = q.c_id"
		+ " WHERE d.c_delete_flag='0' AND b.c_id = '"+dto.getOrderDetailId()+"' ");
		@SuppressWarnings("rawtypes")
		List resultSet = dao.queryBySql(sql.toString());
		for(Object result : resultSet){
			TryClothesRecordModel odsModel = new TryClothesRecordModel();//构建返回数据模型
			Object[] properties = (Object[])result;
			odsModel.setOrderCode(properties[0]==null ? "" : properties[0].toString());
			odsModel.setCustomerCode(properties[1]==null ? "" : properties[1].toString());
			odsModel.setCustomerName(properties[2]==null ? "" : properties[2].toString());
			odsModel.setStyleSn(properties[3]==null ? "" : properties[3].toString());
			odsModel.setStyleName(properties[4]==null ? "" : properties[4].toString());
			odsModel.setColor(properties[5]==null ? "" : properties[5].toString());
			odsModel.setShopName(properties[6]==null ? "" : properties[6].toString());
			odsModel.setCity(properties[7]==null ? "" : properties[7].toString());
			odsModel.setMeasurer(properties[8]==null ? "" : properties[8].toString());
			odsModel.setSendTime(properties[9]==null ? "" : properties[9].toString());
			odsModel.setCharacte(properties[10]==null ? "" : properties[10].toString());
			//odsModel.setBillStatus(properties[11]==null ? "" : properties[11].toString());
			odsModel.setCreateTime(properties[11]==null ? "" : properties[11].toString());
			odsModel.setStatuss(properties[12]==null ? "" : properties[12].toString());
			odsModel.setFeedTime(properties[13]==null ? "" : properties[13].toString());
			//odsModel.setOwner(properties[15]==null ? "" : properties[15].toString());
			odsModel.setExtendedFileName(properties[14]==null ? "" : properties[14].toString());
			odsModel.setDescription(properties[15]==null ? "" : properties[15].toString());
			odsModel.setFilePath(properties[16]==null ? "" : properties[16].toString());
			//odsModel.setFileName(properties[19]==null ? "" : properties[19].toString());
			//odsModel.setTryonDeilId(properties[20]==null ? "" : properties[20].toString());
			tryOnModelList.add(odsModel); 
		}
		return new Result(tryOnModelList);

	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result showTryOnDetail(TryOnFeedBackDto dto) {
		List<TryOnModel> tryOnModelList = new ArrayList<>();
		StringBuilder sql = new StringBuilder("SELECT t.c_tryon_number as tryOnNumber,t.c_tryon_date as tryDate,q.c_customer_name as orderCustomer,q.c_customer_code as orderCustomerCode,q.c_shop_name as orderShopName,z.c_customer_name as customerName,q.c_status_bussiness as orderStatus,q.c_order_character as orderCharacter,t.c_create_time  as tryOnCreateTime,o.c_goods_sn as goodsSn,s.c_name as goodsName,h.c_color_name as goodsColor,q.c_measurer_name as measurerName,t.c_feedback_json as feedBackJson,t.c_size as size,t.c_instruction as instruction,t.c_file_keys as fileKeys,t.c_id as tryOnId from t_tryon_feedback t  LEFT JOIN t_order_detail o ON o.c_id=t.c_order_detail_id LEFT JOIN t_order q ON q.c_id = o.c_order_id LEFT JOIN t_goods_detail h ON t.c_goods_detail_id=h.c_id LEFT JOIN t_goods s ON h.c_goods_id=s.c_id LEFT JOIN t_xiuyu_customer z ON q.c_customer_id= z.c_id");
		sql.append(" where  t.c_order_detail_id = '"+dto.getOrderDetailId()+"'").append(" and t.c_goods_detail_id = '"+dto.getGoodsDetailId()+"'");

		List resultSet = dao.queryBySql(sql.toString());
		for(Object result : resultSet){
			TryOnModel odsModel = new TryOnModel();//构建返回数据模型
			Object[] properties = (Object[])result;
			odsModel.setTryOnNumber(properties[0]==null ? "" : properties[0].toString());
			odsModel.setTryDate(properties[1]==null ? "" : properties[1].toString());
			odsModel.setOrderCustomer(properties[2]==null ? "" : properties[2].toString());
			odsModel.setOrderCustomerCode(properties[3]==null ? "" : properties[3].toString());
			odsModel.setOrderShopName(properties[4]==null ? "" : properties[4].toString());
			odsModel.setCustomerName(properties[5]==null ? "" : properties[5].toString());
			odsModel.setOrderStatus(properties[6]==null ? "" : properties[6].toString());
			odsModel.setOrderCharacter(properties[7]==null ? "" : properties[7].toString());
			odsModel.setTryOnCreateTime(properties[8]==null ? "" : properties[8].toString());
			odsModel.setGoodsSn(properties[9]==null ? "" : properties[9].toString());
			odsModel.setGoodsName(properties[10]==null ? "" : properties[10].toString());
			odsModel.setGoodsColor(properties[11]==null ? "" : properties[11].toString());
			odsModel.setMeasurerName(properties[12]==null ? "" : properties[12].toString());;
			odsModel.setFeedBackJson(properties[13]==null ? "" : properties[13].toString());
			odsModel.setSize(properties[14]==null ? "" : properties[14].toString());
			odsModel.setInstruction(properties[15]==null ? "" : properties[15].toString());
			odsModel.setFilesKeys(properties[16]==null ? "" : properties[16].toString());
			odsModel.setTryOnId(properties[17]==null ? "" : properties[17].toString());
			tryOnModelList.add(odsModel);
		}
		return new Result(tryOnModelList); 
	}

	@Override
	public Result addOrUpdateTryOnFeed(TryOnFeedBackDto dto,MultipartFile[] files) {
		if(StringUtils.isBlank(dto.getTryOnId())){
			Set<String> imageKeys = null;
			if (files!= null) {
				imageKeys = appendOrderImage(files);
			}
			TryOnFeedBack tryonFeedback = new TryOnFeedBack();
			tryonFeedback.setOrderDetailId(dto.getOrderDetailId());
			tryonFeedback.setGoodsDetailId(dto.getGoodsDetailId());
			tryonFeedback.setTryOnNumber(CodeUtil.createTryOnCode(getTryOnMaxValue()));
			tryonFeedback.setTryOnDate(new Date());
			tryonFeedback.setFeedBackJson(dto.getFeedBackJson());
			tryonFeedback.setStatus("1"); //未提交
			tryonFeedback.setCreateTime(new Date()); 
			tryonFeedback.setCreateUser(dto.getCreateUser());
			tryonFeedback.setSize(dto.getSize());
			tryonFeedback.setInstruction(dto.getInstruction());
			if (imageKeys != null) {
				tryonFeedback.setFileKeys(imageKeys.toString());;
			}
			dao.save(tryonFeedback);
			return new Result(tryonFeedback);
		}else{
			TryOnFeedBack tryonFeedback = dao.findById(TryOnFeedBack.class, dto.getTryOnId());
			String[] delFileList=dto.getImageDelKeys();
			// 删除商品图片文件信息并同步到数据库中
						if (delFileList != null && delFileList.length > 0) {
							String[] removedImgKeys = delOSSFile(tryonFeedback,delFileList);
							StringBuilder logInfos = new StringBuilder("删除以下商品图片文件执行完毕：[");
							for (String imgKey : removedImgKeys) {
								logInfos.append(imgKey).append(",");
							}
							logInfos.append("]");
						}
						Set<String> tryOnKey=null;
						// 添加商品图片文件信息并同步到数据库中
						if (files != null && files.length > 0) {
							tryOnKey = appendOrderImage(tryonFeedback,files);
							if (tryOnKey.size() < files.length) {
								StringBuilder errorInfos = new StringBuilder(
										"以下[" + (files.length - tryOnKey.size()) + "]张商品图片文件信息添加失败：[");
								for (MultipartFile imageFile : files) {
									if (!tryOnKey.contains(imageFile.getOriginalFilename()))
										errorInfos.append(imageFile.getOriginalFilename()).append(",");
								}
								errorInfos.append("]");
							}
						}
			tryonFeedback.setFeedBackJson(dto.getFeedBackJson());
			tryonFeedback.setSize(dto.getSize());
			tryonFeedback.setInstruction(dto.getInstruction());
			tryonFeedback.setUpdateTime(new Date());
			tryonFeedback.setUpdateUser(dto.getUpdateUser());
			dao.update(tryonFeedback); 
			return new Result(tryonFeedback);   
			}
	}
	/**
	 * 上传试穿图片
	 * 
	 * @param file
	 * @return
	 */
	private Set<String> appendOrderImage(TryOnFeedBack tryonFeedback,MultipartFile[] file) {
		Set<String> addedImageNames = null;
		if (file == null) {// 如果没有上传任何图片文件
			return addedImageNames;
		}
		JSONArray imgKeys = tryonFeedback.getFileKeys() == null || tryonFeedback.getFileKeys() .isEmpty() ? new JSONArray()
				: JSONArray.fromObject(tryonFeedback.getFileKeys() );// 初始化JSON数组对象
		addedImageNames = new HashSet<>();// 初始化保存失败的上传文件名称列表
		for (MultipartFile imageFile : file) {// 遍历上传的图片文件
			try {
				String newKey = getFileFolder(new Date()) + imageFile.getOriginalFilename();
				imgKeys.add(fileOSSUtil.uploadPublicReadFile(newKey, imageFile.getInputStream()));
				// 把上传文件保存到OSS系统，并把OSS系统保存文件成功后返回的该文件的key添加到JSON数组中
				addedImageNames.add(newKey);
			} catch (IOException e) {// 当读取上传图片文件输入流抛出异常时
				e.printStackTrace();
			}
		}
		if (!addedImageNames.isEmpty()) {// 如果OSS成功保存的文件不为空
			tryonFeedback.setFileKeys(imgKeys.toString());// 把保存在OSS系统中各文件对应的key所形成的JSON字符串保存到商品对应的属性中
			dao.update(tryonFeedback);// 更新数据库中的商品信息
			dao.flush();// 释放缓冲区
		}
		return addedImageNames;// 返回保存失败的上传文件名称列表
	}
	private Set<String> appendOrderImage(MultipartFile[] file) {
		Set<String> addedImageNames = null;
		if (file == null) {// 如果没有上传任何图片文件
			return addedImageNames;
		}
		addedImageNames = new HashSet<>();// 初始化保存失败的上传文件名称列表
		for (MultipartFile imageFile : file) {// 遍历上传的图片文件
			try {
				String newKey = getFileFolder(new Date()) + imageFile.getOriginalFilename();
				newKey = fileOSSUtil.uploadPublicReadFile(newKey, imageFile.getInputStream());
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
				+ DateUtil.formatDate(date, "dd") + "/";
	}
	/**
	 * 删除上传到oss的文件
	 * 
	 * @param dto
	 * @return
	 */
	public String[] delOSSFile(TryOnFeedBack tryonFeedback, String[] delFileList) {
		if (delFileList != null) {
			JSONArray imgKeys = tryonFeedback.getFileKeys() == null || tryonFeedback.getFileKeys().isEmpty() ? new JSONArray()
					: JSONArray.fromObject(tryonFeedback.getFileKeys());// 初始化JSON数组对象
			for (String imgKey : delFileList) {
				String removeImgKey = fileOSSUtil.fromUrlToKey(imgKey);
				imgKeys.remove(imgKey);
				// 在oss 删除 文件
				fileOSSUtil.deletePublicReadFile(removeImgKey);
			}
			tryonFeedback.setFileKeys(imgKeys.toString());
			dao.update(tryonFeedback);
			dao.flush();// 释放缓冲区
		}
		return delFileList;
	}
		private String getTryOnMaxValue() {
			Date date = new Date();
			Criteria criteria = dao.createCriteria(Code.class);
			criteria.add(Restrictions.between("updateTime", DateUtil.minDate(date), DateUtil.maxDate(date)));
			criteria.add(Restrictions.eq("type", "SY"));
			criteria.addOrder(org.hibernate.criterion.Order.desc("maxValue"));
			criteria.setMaxResults(1);
			Code code = (Code) criteria.uniqueResult();
			if (code == null) {
				code = new Code();
				code.setMaxValue(1);
				code.setType("SY");
				code.setUpdateTime(date);
				dao.save(code);
			} else {
				code.setMaxValue(code.getMaxValue() + 1);
			}
			return String.format("%0" + 4 + "d", code.getMaxValue());
		}
		
	@Override
	public Result submitStatus(TryOnFeedBackDto dto) {
		TryOnFeedBack tryonFeedback =null;
		if(StringUtils.isNotBlank(dto.getTryOnId())){
			tryonFeedback = dao.findById(TryOnFeedBack.class, dto.getTryOnId());
			if(StringUtils.isNotBlank(dto.getStatus())) {
				//提交只能是未提交状态改为已提交状态
				if(dto.getStatus().equals("4")) {
					tryonFeedback.setStatus("3");
				}else {
					return new Result("500", "当前状态不可提交，请刷新页面");
				}
			}
		}
			return new Result(tryonFeedback);
	}

	@Override
	public Result deleteTryOn(String tryOnId) {
		TryOnFeedBack tryonFeedback = dao.findById(TryOnFeedBack.class, tryOnId);
		dao.delete(tryonFeedback);
		return new Result(tryonFeedback);
	}

	@Override
	public Result examineTryOn(TryOnAuditOpinionDto dto) {
		TryOnAuditOpinion tryonAuditOpinion = new TryOnAuditOpinion();
		tryonAuditOpinion.setTryOnFeedBackId(dto.getTryOnFeedBackId());
		tryonAuditOpinion.setAuditOpinion(dto.getAuditOpinion());
		tryonAuditOpinion.setPassFeedBack(dto.getPassFeedBack());
		tryonAuditOpinion.setAuditPeople(dto.getAuditPeople());
		/*if("1".equals(dto.getType())){
			tryonAuditOpinion.setAuditStatus("1");
		}else if("2".equals(dto.getType())){
			tryonAuditOpinion.setAuditStatus("2");
		}*/
		tryonAuditOpinion.setCheckCreateTime(new Date());
		dao.save(tryonAuditOpinion);
		TryOnFeedBack tryonFeedback = dao.findById(TryOnFeedBack.class, dto.getTryOnFeedBackId());
		if("1".equals(dto.getType())){
			tryonFeedback.setStatus("1");
		}else if("2".equals(dto.getType())){
			tryonFeedback.setStatus("2");
		}
		dao.update(tryonFeedback);
		return new Result(tryonFeedback);
	}

	@Override
	public List<TryonAuditOpinionModel> getAuditOpinionList(TryOnAuditOpinionDto dto, Integer page, Integer rows) {
		List<TryonAuditOpinionModel> tryOnModelList = new ArrayList<>();
		StringBuilder sql = new StringBuilder("SELECT a.c_audit_opinion AS auditOpinion,a.c_audit_people AS auditPeople,a.c_create_time AS creatTime,q.c_status AS statuss FROM t_tryon_audit_opinion a lEFT JOIN t_tryon_feedback q ON a.c_tryon_feedback_id = q.c_id where a.c_tryon_feedback_id='"+dto.getTryOnFeedBackId()+"'");
		sql.append(" limit "+(page - 1) * rows+","+rows);
		@SuppressWarnings("rawtypes")
		List resultSet = dao.queryBySql(sql.toString());
		for(Object result : resultSet){
			TryonAuditOpinionModel odsModel = new TryonAuditOpinionModel();//构建返回数据模型
			Object[] properties = (Object[])result;
			odsModel.setAuditOpinion(properties[0]==null ? "" : properties[0].toString());
			odsModel.setAuditPeople(properties[1]==null ? "" : properties[1].toString());
			odsModel.setCreatTime(properties[2]==null ? "" : properties[2].toString());
			odsModel.setStatus(properties[3]==null ? "" : properties[3].toString());
			tryOnModelList.add(odsModel);
		}
		return tryOnModelList;
	}
	//查询审核意见列表
	@Override
	public Long getAuditOpinionCount(TryOnAuditOpinionDto dto) {
		StringBuilder sql = new StringBuilder("SELECT count(*) FROM t_tryon_audit_opinion a lEFT JOIN t_tryon_feedback q ON a.c_tryon_feedback_id = q.c_id where a.c_tryon_feedback_id='"+dto.getTryOnFeedBackId()+"'");
		List<BigInteger> result = dao.queryBySql(sql.toString());
		return result == null || result.isEmpty() ? 0L : result.get(0).longValue();
	}

	@Override
	public List<TryOnStyleModel> getStyleList(String orderId, Integer page, Integer rows) {
		List<TryOnStyleModel> tryOnModelList = new ArrayList<>();
		StringBuilder sql = new StringBuilder("SELECT a.c_code AS code,a.c_name AS styleName,e.c_color_name AS color,e.c_materiel_code AS materielCode,a.c_status AS statuss,a.c_create_time AS createTime,c.c_id AS orderId,d.c_id AS tryOnId,d.c_status AS tryOnStatus,b.c_id AS orderDetailId,a.c_id AS styleId FROM t_goods a LEFT JOIN t_goods_detail e ON a.c_id = e.c_goods_id LEFT JOIN t_order_detail b ON b.c_goods_detail_id = e.c_id LEFT JOIN t_order c ON b.c_order_id=c.c_id LEFT JOIN t_tryon_feedback d ON d.c_goods_detail_id = e.c_id WHERE b.c_order_id='"+orderId+"' ");
		sql.append(" limit "+(page - 1) * rows+","+rows);
		@SuppressWarnings("rawtypes")
		List resultSet = dao.queryBySql(sql.toString());
		for(Object result : resultSet){
			TryOnStyleModel odsModel = new TryOnStyleModel();//构建返回数据模型
			Object[] properties = (Object[])result;
			odsModel.setCode(properties[0]==null ? "" : properties[0].toString());
			odsModel.setStyleName(properties[1]==null ? "" : properties[1].toString());
			odsModel.setColor(properties[2]==null ? "" : properties[2].toString());
			//odsModel.setSize(properties[3]==null ? "" : properties[3].toString());
			odsModel.setMaterielCode(properties[3]==null ? "" : properties[3].toString());
			odsModel.setStatus(properties[4]==null ? "" : properties[4].toString());
			odsModel.setCreateTime(properties[5]==null ? "" : properties[5].toString());
			odsModel.setOrderId(properties[6]==null ? "" : properties[6].toString());
			odsModel.setTryOnId(properties[7]==null ? "" : properties[7].toString());
			odsModel.setStatuss(properties[8]==null ? "" : properties[8].toString());
			odsModel.setOrderDetailId(properties[9]==null ? "" : properties[9].toString());
			odsModel.setStyleId(properties[10]==null ? "" : properties[10].toString());
			tryOnModelList.add(odsModel);
		}
		return tryOnModelList;
	}

	@Override
	public Long getStyleCount(String orderId) {
		StringBuilder sql = new StringBuilder("SELECT count(*) FROM t_goods a LEFT JOIN t_goods_detail e ON a.c_id = e.c_goods_id LEFT JOIN t_order_detail b ON b.c_goods_detail_id = e.c_id LEFT JOIN t_order c ON b.c_order_id=c.c_id LEFT JOIN t_tryon_feedback d ON d.c_goods_detail_id = e.c_id WHERE b.c_order_id='"+orderId+"' ");
		List<BigInteger> result = dao.queryBySql(sql.toString());
		return result == null || result.isEmpty() ? 0L : result.get(0).longValue();
	}

	/*	@Override
	public Result saveOrUpdateAttachment(String attachmentJson, CommonsMultipartFile[] attachmentFiles) {
		return null;
	}

	@Override
	public Result deleteAttachmentDetail(String detailId) {
		return null;
	}

	@Override
	public void getAttachment(String path) {
		
	}

	@Override
	public Result updateFileSizeAndInfo(TryOnFeedBackDto dto) {
		return null;
	}*/

	
	
	
}
