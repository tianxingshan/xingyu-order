package com.kongque.service.productionorder.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kongque.common.BillStatus;
import com.kongque.constants.Constants;
import com.kongque.util.ExportExcelUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.kongque.dao.IDaoService;
import com.kongque.dto.PlanOrderDetailDto;
import com.kongque.model.OrderPlanDetailModel;
import com.kongque.model.TwOrderModel;
import com.kongque.service.productionorder.IOrderPlanDetailService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;

@Service
public class OrderPlanDetailServiceImpl implements IOrderPlanDetailService{
	 @Resource
	 private IDaoService dao;
	 
	 
	 @Override
	 public Pagination<OrderPlanDetailModel> list(PlanOrderDetailDto dto,PageBean pageBean){
//		 	Pagination<OrderPlanDetailModel> pagination = new Pagination<>();
//		 	List<OrderPlanDetailModel> resultList = orderPlanDetailList(dto,pageBean);
//		 	Long total = countOrderPlanDetail(dto);
//		 	pagination.setRows(resultList);
//		 	pagination.setTotal(total);
			return orderPlanDetails(dto,pageBean);
	 }
	 
	 @Override
	 public Pagination<TwOrderModel> twList(PlanOrderDetailDto dto,PageBean pageBean){
		 	Pagination<TwOrderModel> pagination = new Pagination<>();
		 	List<TwOrderModel> resultList = twOrderList(dto,pageBean);
		 	Long total = countTwList(dto);
		 	pagination.setRows(resultList);
		 	pagination.setTotal(total);
			return pagination;
	 }

	@Override
	public void getExcel(PlanOrderDetailDto dto, HttpServletRequest request, HttpServletResponse response) {
		String excelFileName = "生产计划明细";
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
//			PageBean pageBean = new PageBean();
//			pageBean.setPage(1);
//			pageBean.setRows(10);
			List<OrderPlanDetailModel> orderPlanDetailModels = this.orderPlanDetails(dto,null).getRows(); //this.excelData(dto);


			String[] headers = new String[] {  "计划单号", "生产工厂", "订单号", "订单性质", "会员卡号", "会员姓名", "门店", "款号", "款式名称", "颜色",
					"配码尺寸", "唯一码", "物料编码", "制版方", "制版人","订单状态","绣字名","生产前财务审核状态","发货前财务审核状态","订单明细状态","计划单状态","生产完成日期"
			        ,"技术完成日期","订单分配日期","计划投产日期","计划完成日期","发货日期","结案状态","结案时间"};

			Set<String> excludedFieldSet = new HashSet<String>();

			excludedFieldSet.add("xingyuCheckTime");
			excludedFieldSet.add("categoryName");
			excludedFieldSet.add("IssueTime");

			out = response.getOutputStream();
			ExportExcelUtil.exportExcel(0, 0, 0, excelFileName, headers,
					ExportExcelUtil.buildCustomizedExportedModel(orderPlanDetailModels, excludedFieldSet), out,
					"yyyy-MM-dd");
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
	}

	private List<OrderPlanDetailModel> excelData(PlanOrderDetailDto dto){
		List<OrderPlanDetailModel> list = new ArrayList<>();
//		StringBuilder sql = new StringBuilder();
//
//
//		sql.append(" SELECT b.c_plan_number planNumber,b.c_prod_factory_id prodFactory,d.c_order_code orderCode,d.c_order_character orderCharacter,d.c_customer_code customerCode,d.c_customer_name customerName,d.c_shop_name shopName,c.c_goods_name goodsName,c.c_goods_color_name colorName,h.c_mes_measure_size_name sizeName,c.c_goods_sn goodsSn,e.c_materiel_code materielCode,b.publishers publishers,a.c_technician_name technicianName,d.c_status_bussiness statusBussiness,d.c_embroid_name embroidName,d.c_status_before_produce beforeProduce,d.c_status_before_send beforeSend,c.c_order_detail_status detailStatus,b.c_plan_status planStatus,i.c_prod_finish_time finishTime,a.c_technical_finished_time technicialTime,a.c_create_time createTime,b.c_prod_time prodTime,b.c_delivery_time deliveryTime,g.c_send_time sendTime,k.c_closed_status closeStatus,k.c_closed_createtime closeTime,c.c_goods_code goodsCode ");
//		sql.append(" FROM t_order_detail c  ");
//		sql.append(" JOIN t_order d ON c.c_order_id = d.c_id and d.c_delete_flag='0' ");
//		sql.append(" LEFT JOIN mes_order_detail_assign a ON c.c_id = a.c_order_detail_id and a.c_delete_flag=0  ");
//		sql.append(" LEFT JOIN mes_order_plan_detail i ON c.c_id = i.c_order_detail_id and i.c_delete_flag=0 ");
//		sql.append(" LEFT JOIN mes_order_plan b ON i.c_order_plan_id = b.c_id and b.c_delete_flag=0");
//		sql.append(" LEFT JOIN t_goods_detail e ON c.c_goods_detail_id = e.c_id");
//		sql.append(" LEFT JOIN t_logistic_order f ON c.c_id = f.c_order_detail_id ");
//		sql.append(" LEFT JOIN t_logistic g ON f.c_logistic_id= g.c_id ");
//		sql.append(" LEFT JOIN mes_order_detail_size h ON c.c_id = h.c_order_detail_id  ");
//		sql.append(" LEFT JOIN t_order_detail_closed j ON c.c_id = j.c_order_detail_id ");
//		sql.append(" LEFT JOIN t_order_closed k ON j.c_order_closed_id = k.c_id ");
//		sql.append(" WHERE ifnull(d.c_status_before_produce,'0')='2' and ifnull(d.c_status_bussiness,'') in ('3','6','7','8')  ");
//
//
//		//星域审核日期
//		if(StringUtils.isNotBlank(dto.getXingyuChekTimeStr()) && StringUtils.isNotBlank(dto.getXingyuChekTimeEnd())){
//			sql.append(" and d.c_xingyu_chek_time between '").append(dto.getXingyuChekTimeStr()).append(" 00:00:00'").append(" and '").append(dto.getXingyuChekTimeEnd()).append(" 23:59:59'");
//		}else if(StringUtils.isNotBlank(dto.getXingyuChekTimeStr()) && StringUtils.isBlank(dto.getXingyuChekTimeEnd())){
//			sql.append(" and d.c_xingyu_chek_time >= '").append(dto.getXingyuChekTimeStr()).append(" 00:00:00'");
//		}else if(StringUtils.isBlank(dto.getXingyuChekTimeStr()) && StringUtils.isNotBlank(dto.getXingyuChekTimeEnd())){
//			sql.append(" and d.c_xingyu_chek_time <= '").append(dto.getXingyuChekTimeEnd()).append(" 00:00:00'");
//		}
//		//订单分配日期
//		if(StringUtils.isNotBlank(dto.getAssignCreatetimeStr()) && StringUtils.isNotBlank(dto.getAssignCreatetimeEnd())){
//			sql.append(" and a.c_create_time between '").append(dto.getAssignCreatetimeStr()).append(" 00:00:00'").append(" and '").append(dto.getAssignCreatetimeEnd()).append(" 23:59:59'");
//		}else if(StringUtils.isNotBlank(dto.getAssignCreatetimeStr()) && StringUtils.isBlank(dto.getAssignCreatetimeEnd())){
//			sql.append(" and a.c_create_time >= '").append(dto.getAssignCreatetimeStr()).append(" 00:00:00'");
//		}else if(StringUtils.isBlank(dto.getAssignCreatetimeStr()) && StringUtils.isNotBlank(dto.getAssignCreatetimeEnd())){
//			sql.append(" and a.c_create_time <= '").append(dto.getAssignCreatetimeEnd()).append(" 00:00:00'");
//		}
//		//计划单号
//		if(StringUtils.isNotBlank(dto.getPlanNumber())){
//			sql.append(" and b.c_plan_number like '%").append(dto.getPlanNumber()).append("%'");
//		}
//		//订单单号
//		if(StringUtils.isNotBlank(dto.getOrderCode())){
//			sql.append(" and d.c_order_code like '%").append(dto.getOrderCode()).append("%'");
//		}
//		//计划单状态
//		if(StringUtils.isNotBlank(dto.getPlanStatus())){
//			sql.append(" and b.c_plan_status = '").append(dto.getPlanStatus()).append("'");
//		}
//		//技术完成日期
//		if(StringUtils.isNotBlank(dto.getTechnicalFinishedTimeStr()) && StringUtils.isNotBlank(dto.getTechnicalFinishedTimeEnd())){
//			sql.append(" and a.c_technical_finished_time between '").append(dto.getTechnicalFinishedTimeStr()).append(" 00:00:00'").append(" and '").append(dto.getTechnicalFinishedTimeEnd()).append(" 23:59:59'");
//		}else if(StringUtils.isNotBlank(dto.getTechnicalFinishedTimeStr()) && StringUtils.isBlank(dto.getTechnicalFinishedTimeEnd())){
//			sql.append(" and a.c_technical_finished_time >= '").append(dto.getTechnicalFinishedTimeStr()).append(" 00:00:00'");
//		}else if(StringUtils.isBlank(dto.getTechnicalFinishedTimeStr()) && StringUtils.isNotBlank(dto.getTechnicalFinishedTimeEnd())){
//			sql.append(" and a.c_technical_finished_time <= '").append(dto.getTechnicalFinishedTimeEnd()).append(" 00:00:00'");
//		}
//		//款号
//		if(StringUtils.isNotBlank(dto.getGoodsCode())){
//			sql.append(" and c.c_goods_code like '%").append(dto.getGoodsCode()).append("%'");
//		}
//		//顾客姓名
//		if(StringUtils.isNotBlank(dto.getCustomerName())){
//			sql.append(" and d.c_customer_name like '%").append(dto.getCustomerName()).append("%'");
//		}
//
//		//订单明细状态
//		if ( StringUtils.isNotBlank(dto.getOrderDetailStatus()) && !"全部".equals(dto.getOrderDetailStatus())){
//			//如果等于6 就是没分配的订单
//			String[] status = dto.getOrderDetailStatus().split(",");
//			String strSql="";
//			for (String s:status) {
//				if(BillStatus.OrderDetailStatus.UNASSIGN.getValue().equals(s)){
//					strSql +="or  ifnull(c.c_order_detail_status,'"+BillStatus.OrderDetailStatus.UNASSIGN.getValue()+"')='"+s+"'";
//					strSql +="  and  ifnull(c.c_closed_status,'0') in ('','0','1','4') ";
//				}else {
//					strSql +="or  ifnull(c.c_order_detail_status,'"+BillStatus.OrderDetailStatus.UNASSIGN.getValue()+"')='"+s+"' ";
//				}
//			}
//			sql.append(" and ("+strSql.substring(2)+")");
//		}
//		else {
//			sql.append(" and (ifnull(c.c_order_detail_status,'").append(BillStatus.OrderDetailStatus.UNASSIGN.getValue()).append("')='")
//					.append(BillStatus.OrderDetailStatus.UNASSIGN.getValue()).append("' and  ifnull(c.c_closed_status,'0') in ('','0','1','4')  " +
//					" or  ifnull(c.c_order_detail_status,'").append(BillStatus.OrderDetailStatus.UNASSIGN.getValue()).append("')!='"+BillStatus.OrderDetailStatus.UNASSIGN.getValue()+"'" +
//					" )");
//		}
//
//		//生产完成日期
//		if(StringUtils.isNotBlank(dto.getProdFinishTimeStr()) && StringUtils.isNotBlank(dto.getProdFinishTimeEnd())){
//			sql.append(" and i.c_prod_finish_time between '").append(dto.getProdFinishTimeStr()).append(" 00:00:00'").append(" and '").append(dto.getProdFinishTimeEnd()).append(" 23:59:59'");
//		}else if(StringUtils.isNotBlank(dto.getProdFinishTimeStr()) && StringUtils.isBlank(dto.getProdFinishTimeEnd())){
//			sql.append(" and i.c_prod_finish_time >= '").append(dto.getProdFinishTimeStr()).append(" 00:00:00'");
//		}else if(StringUtils.isBlank(dto.getProdFinishTimeStr()) && StringUtils.isNotBlank(dto.getProdFinishTimeEnd())){
//			sql.append(" and i.c_prod_finish_time <= '").append(dto.getProdFinishTimeEnd()).append(" 00:00:00'");
//		}
//		//颜色
//		if(StringUtils.isNotBlank(dto.getGoodsColorName())){
//			sql.append(" and c.c_goods_color_name like '%").append(dto.getGoodsColorName()).append("%'");
//		}
//		//唯一码
//		if(StringUtils.isNotBlank(dto.getGoodsSn())){
//			sql.append(" and c.c_goods_sn like '%").append(dto.getGoodsSn()).append("%'");
//		}
//		//订单性质
//		if(StringUtils.isNotBlank(dto.getOrderCharacter()) && !"全部".equals(dto.getOrderCharacter())){
//			sql.append(" and d.c_order_character = '").append(dto.getOrderCharacter()).append("'");
//		}
//		//计划投产日期
//		if(StringUtils.isNotBlank(dto.getProdTimeStr()) && StringUtils.isNotBlank(dto.getProdTimeEnd())){
//			sql.append(" and b.c_prod_time between '").append(dto.getProdTimeStr()).append(" 00:00:00'").append(" and '").append(dto.getProdTimeEnd()).append(" 23:59:59'");
//		}else if(StringUtils.isNotBlank(dto.getProdTimeStr()) && StringUtils.isBlank(dto.getProdTimeEnd())){
//			sql.append(" and b.c_prod_time >= '").append(dto.getProdTimeStr()).append(" 00:00:00'");
//		}else if(StringUtils.isBlank(dto.getProdTimeStr()) && StringUtils.isNotBlank(dto.getProdTimeEnd())){
//			sql.append(" and b.c_prod_time <= '").append(dto.getProdTimeEnd()).append(" 00:00:00'");
//		}
//		//匹配尺码
//		if(StringUtils.isNotBlank(dto.getMesMeasureSizeName())){
//			sql.append(" and h.c_mes_measure_size_name like '%").append(dto.getMesMeasureSizeName()).append("%'");
//		}
//		//制版人
//		if(StringUtils.isNotBlank(dto.getTechnicianName())){
//			sql.append(" and a.c_technician_name like '%").append(dto.getTechnicianName()).append("%'");
//		}
//		//结案状态
//		if(StringUtils.isNotBlank(dto.getClosedStatus())){
//			sql.append(" and c.c_closed_status = '").append(dto.getClosedStatus()).append("'");
//		}
//		//发货日期
//		if(StringUtils.isNotBlank(dto.getSendTimeStr()) && StringUtils.isNotBlank(dto.getSendTimeEnd())){
//			sql.append(" and g.c_send_time between '").append(dto.getSendTimeStr()).append(" 00:00:00'").append(" and '").append(dto.getSendTimeEnd()).append(" 23:59:59'");
//		}else if(StringUtils.isNotBlank(dto.getSendTimeStr()) && StringUtils.isBlank(dto.getSendTimeEnd())){
//			sql.append(" and g.c_send_time >= '").append(dto.getSendTimeStr()).append(" 00:00:00'");
//		}else if(StringUtils.isBlank(dto.getSendTimeStr()) && StringUtils.isNotBlank(dto.getSendTimeEnd())){
//			sql.append(" and g.c_send_time <= '").append(dto.getSendTimeEnd()).append(" 00:00:00'");
//		}
//		//生产工厂
//		if(StringUtils.isNotBlank(dto.getProdFactory())){
//			sql.append(" and b.c_prod_factory_id like '%").append(dto.getProdFactory()).append("%'");
//		}
//		//出版方
//		if(StringUtils.isNotBlank(dto.getPublishers())){
//			sql.append(" and b.publishers like '%").append(dto.getPublishers()).append("%'");
//		}
//
//		//订单状态
//		if(StringUtils.isNotBlank(dto.getStatusBussiness())&&!"全部".equals(dto.getStatusBussiness())){
//			sql.append(" and d.c_status_bussiness = '").append(dto.getStatusBussiness()).append("'");
//		}
//		//下达日期
//		if(StringUtils.isNotBlank(dto.getIssueTimeStr()) && StringUtils.isNotBlank(dto.getIssueTimeEnd())){
//			sql.append(" and b.c_send_time between '").append(dto.getIssueTimeStr()).append(" 00:00:00'").append(" and '").append(dto.getIssueTimeEnd()).append(" 23:59:59'");
//		}else if(StringUtils.isNotBlank(dto.getIssueTimeStr()) && StringUtils.isBlank(dto.getIssueTimeEnd())){
//			sql.append(" and b.c_send_time >= '").append(dto.getIssueTimeStr()).append(" 00:00:00'");
//		}else if(StringUtils.isBlank(dto.getIssueTimeStr()) && StringUtils.isNotBlank(dto.getIssueTimeEnd())){
//			sql.append(" and b.c_send_time <= '").append(dto.getIssueTimeEnd()).append(" 00:00:00'");
//		}
//
//		sql.append(" order by b.c_create_time desc ");
//		List resultSet = dao.queryBySql(sql.toString());
//		for(Object result : resultSet){
//			OrderPlanDetailModel model = new OrderPlanDetailModel();//构建返回数据模型
//			Object[] properties = (Object[])result;
//			model.setPlanNumber(properties[0]==null ? "" : properties[0].toString());
//			model.setProdFactory(properties[1]==null ? "" : properties[1].toString());
//			model.setOrderCode(properties[2]==null ? "" : properties[2].toString());
//			model.setOrderCharacter(properties[3]==null ? "" : properties[3].toString());
//			model.setCustomerCode(properties[4]==null ? "" : properties[4].toString());
//			model.setCustomerName(properties[5]==null ? "" : properties[5].toString());
//			model.setShopName(properties[6]==null ? "" : properties[6].toString());
//			model.setGoodsName(properties[7]==null ? "" : properties[7].toString());
//			model.setGoodsColorName(properties[8]==null ? "" : properties[8].toString());
//			model.setMesMeasureSizeName(properties[9]==null ? "" : properties[9].toString());
//			model.setGoodsSn(properties[10]==null ? "" : properties[10].toString());
//			model.setMaterielCode(properties[11]==null ? "" : properties[11].toString());
//			model.setPublishers(properties[12]==null ? "" : this.getPublishers(properties[12].toString()));
//			model.setTechnicianName(properties[13]==null ? "" : properties[13].toString());
//			model.setStatusBussiness(properties[14]==null ? "" : this.getStatusBussiness(properties[14].toString()));
//			model.setEmbroidName(properties[15]==null ? "" : properties[15].toString());
//			model.setStatusBeforeProduce(properties[16]==null ? "" : this.getStatusBeforeProduce(properties[16].toString()));
//			model.setStatusBeforeSend(properties[17]==null ? "" : this.getStatusBeforeSend(properties[17].toString()));
//			model.setOrderDetailStatus(properties[18]==null ? "" : this.getOrderDetailStatus(properties[18].toString()));
//			model.setPlanStatus(properties[19]==null ? "" : this.getPlanStatus(properties[19].toString()));
//			model.setProdFinishTime(properties[20]==null ? "" : properties[20].toString());
//			model.setTechnicalFinishedTime(properties[21]==null ? "" : properties[21].toString());
//			model.setAssignCreatetime(properties[22]==null ? "" : properties[22].toString());
//			model.setProdTime(properties[23]==null ? "" : properties[23].toString());
//			model.setDeliveryTime(properties[24]==null ? "" : properties[24].toString());
//			model.setSendTime(properties[25]==null ? "" : properties[25].toString());
//			model.setClosedStatus(properties[26]==null ? "" :this.getClosedStatus(properties[26].toString()));
//			model.setClosedCreatetime(properties[27]==null ? "" : properties[27].toString());
//			model.setGoodsCode(properties[28]==null ? "" : properties[28].toString());
//			list.add(model);
//		}
		return list;
	}

	private Pagination<OrderPlanDetailModel> orderPlanDetails(PlanOrderDetailDto dto,PageBean pageBean){
		Pagination<OrderPlanDetailModel> pagination = new Pagination<>();
		List<OrderPlanDetailModel> list = new ArrayList<>();
	        StringBuilder sql = new StringBuilder();
	        StringBuilder countSql = new StringBuilder();
	        StringBuilder listSql = new StringBuilder();
	        countSql.append("SELECT COUNT(*) ");
		    listSql.append(" SELECT b.c_plan_number planNumber,b.c_prod_factory_id prodFactory,d.c_order_code orderCode,d.c_order_character orderCharacter,d.c_customer_code customerCode,d.c_customer_name customerName,d.c_shop_name shopName,z.c_name goodsName,e.c_color_name colorName,h.c_mes_measure_size_name sizeName,c.c_goods_sn goodsSn,e.c_materiel_code materielCode,b.publishers publishers,a.c_technician_name technicianName,d.c_status_bussiness statusBussiness,d.c_embroid_name embroidName,d.c_status_before_produce beforeProduce,d.c_status_before_send beforeSend,c.c_order_detail_status detailStatus,b.c_plan_status planStatus,i.c_prod_finish_time finishTime,a.c_technical_finished_time technicialTime,a.c_create_time createTime,b.c_prod_time prodTime,b.c_delivery_time deliveryTime,l.c_send_time sendTime,k.c_closed_status closeStatus,k.c_closed_createtime closeTime,d.c_xingyu_chek_time xingyuCheckTime,s.c_name categoryName,z.c_code goodsCode,b.c_send_time issueTime ");
	        sql.append(" FROM t_order_detail c  ");
		    sql.append(" JOIN t_order d ON c.c_order_id = d.c_id and d.c_delete_flag='0' ");
	        sql.append(" LEFT JOIN mes_order_detail_assign a ON c.c_id = a.c_order_detail_id and a.c_delete_flag=0  ");
		    sql.append(" LEFT JOIN mes_order_plan_detail i ON c.c_id = i.c_order_detail_id and i.c_delete_flag=0 ");
	        sql.append(" LEFT JOIN mes_order_plan b ON i.c_order_plan_id = b.c_id and b.c_delete_flag=0");
	        sql.append(" LEFT JOIN t_goods_detail e ON c.c_goods_detail_id = e.c_id");
		    sql.append(" LEFT JOIN (SELECT f.c_order_detail_id,f.c_logistic_id,g.c_send_time FROM t_logistic_order f JOIN t_logistic g  WHERE f.c_logistic_id= g.c_id AND f.c_delete_flag='0' AND g.c_delete_flag='0' ) l ON c.c_id = l.c_order_detail_id ");
//	        sql.append(" LEFT JOIN t_logistic_order f ON c.c_id = f.c_order_detail_id ");
//	        sql.append(" LEFT JOIN t_logistic g ON f.c_logistic_id= g.c_id ");
	        sql.append(" LEFT JOIN mes_order_detail_size h ON c.c_id = h.c_order_detail_id  ");
	        sql.append(" LEFT JOIN t_order_detail_closed j ON c.c_id = j.c_order_detail_id ");
	        sql.append(" LEFT JOIN t_order_closed k ON j.c_order_closed_id = k.c_id ");
	        sql.append(" LEFT JOIN t_goods z ON e.c_goods_id=z.c_id ");
	        sql.append(" LEFT JOIN t_category s ON z.c_category_id = s.c_id ");
	        sql.append(" WHERE ifnull(d.c_status_before_produce,'0')='2' and ifnull(d.c_status_bussiness,'') in ('3','6','7','8')  ");

			if (StringUtils.isNotBlank(dto.getEmbroidName())){
				sql.append(" and d.c_embroid_name like '%").append(dto.getEmbroidName()).append("%' ");
			}
			//商户id
			if(StringUtils.isNotBlank(dto.getTenantId())){
				sql.append(" and d.c_tenant_id ='").append(dto.getTenantId()).append("'");
			}
	        //商品品类
	        if(StringUtils.isNotBlank(dto.getCategoryId())){
	        	sql.append(" and z.c_category_id = '").append(dto.getCategoryId()).append("'");
	        }
	        //星域审核日期
	        if(StringUtils.isNotBlank(dto.getXingyuChekTimeStr()) && StringUtils.isNotBlank(dto.getXingyuChekTimeEnd())){
	            sql.append(" and d.c_xingyu_chek_time between '").append(dto.getXingyuChekTimeStr()).append(" 00:00:00'").append(" and '").append(dto.getXingyuChekTimeEnd()).append(" 23:59:59'");
	        }else if(StringUtils.isNotBlank(dto.getXingyuChekTimeStr()) && StringUtils.isBlank(dto.getXingyuChekTimeEnd())){
	        	sql.append(" and d.c_xingyu_chek_time >= '").append(dto.getXingyuChekTimeStr()).append(" 00:00:00'");
	        }else if(StringUtils.isBlank(dto.getXingyuChekTimeStr()) && StringUtils.isNotBlank(dto.getXingyuChekTimeEnd())){
	        	sql.append(" and d.c_xingyu_chek_time <= '").append(dto.getXingyuChekTimeEnd()).append(" 00:00:00'");
	        }
	        //订单分配日期
	        if(StringUtils.isNotBlank(dto.getAssignCreatetimeStr()) && StringUtils.isNotBlank(dto.getAssignCreatetimeEnd())){
	            sql.append(" and a.c_create_time between '").append(dto.getAssignCreatetimeStr()).append(" 00:00:00'").append(" and '").append(dto.getAssignCreatetimeEnd()).append(" 23:59:59'");
	        }else if(StringUtils.isNotBlank(dto.getAssignCreatetimeStr()) && StringUtils.isBlank(dto.getAssignCreatetimeEnd())){
	        	sql.append(" and a.c_create_time >= '").append(dto.getAssignCreatetimeStr()).append(" 00:00:00'");
	        }else if(StringUtils.isBlank(dto.getAssignCreatetimeStr()) && StringUtils.isNotBlank(dto.getAssignCreatetimeEnd())){
	        	sql.append(" and a.c_create_time <= '").append(dto.getAssignCreatetimeEnd()).append(" 00:00:00'");
	        }
	        //计划单号
	        if(StringUtils.isNotBlank(dto.getPlanNumber())){
	            sql.append(" and b.c_plan_number like '%").append(dto.getPlanNumber()).append("%'");
	        }
	        //订单单号
	        if(StringUtils.isNotBlank(dto.getOrderCode())){
	            sql.append(" and d.c_order_code like '%").append(dto.getOrderCode()).append("%'");
	        }
	        //计划单状态
	        if(StringUtils.isNotBlank(dto.getPlanStatus())){
	            sql.append(" and b.c_plan_status = '").append(dto.getPlanStatus()).append("'");
	        }
	        //技术完成日期
	        if(StringUtils.isNotBlank(dto.getTechnicalFinishedTimeStr()) && StringUtils.isNotBlank(dto.getTechnicalFinishedTimeEnd())){
	            sql.append(" and a.c_technical_finished_time between '").append(dto.getTechnicalFinishedTimeStr()).append(" 00:00:00'").append(" and '").append(dto.getTechnicalFinishedTimeEnd()).append(" 23:59:59'");
	        }else if(StringUtils.isNotBlank(dto.getTechnicalFinishedTimeStr()) && StringUtils.isBlank(dto.getTechnicalFinishedTimeEnd())){
	        	sql.append(" and a.c_technical_finished_time >= '").append(dto.getTechnicalFinishedTimeStr()).append(" 00:00:00'");
	        }else if(StringUtils.isBlank(dto.getTechnicalFinishedTimeStr()) && StringUtils.isNotBlank(dto.getTechnicalFinishedTimeEnd())){
	        	sql.append(" and a.c_technical_finished_time <= '").append(dto.getTechnicalFinishedTimeEnd()).append(" 00:00:00'");
	        }
	        //款号
	        if(StringUtils.isNotBlank(dto.getGoodsCode())){
	            sql.append(" and z.c_code like '%").append(dto.getGoodsCode()).append("%'");
	        }
	        //顾客姓名
	        if(StringUtils.isNotBlank(dto.getCustomerName())){
	            sql.append(" and d.c_customer_name like '%").append(dto.getCustomerName()).append("%'");
	        }

			//订单明细状态
			if (StringUtils.isNotBlank(dto.getOrderDetailStatus()) && !"全部".equals(dto.getOrderDetailStatus())){
				//如果等于6 就是没分配的订单
				String[] status = dto.getOrderDetailStatus().split(",");
				String strSql="";
				for (String s:status) {
					if(BillStatus.OrderDetailStatus.UNASSIGN.getValue().equals(s)){
						strSql +="or  ifnull(c.c_order_detail_status,'"+BillStatus.OrderDetailStatus.UNASSIGN.getValue()+"')='"+s+"'";
						strSql +="  and  ifnull(c.c_closed_status,'0') in ('','0','1','4') ";
					}else {
						strSql +="or  ifnull(c.c_order_detail_status,'"+BillStatus.OrderDetailStatus.UNASSIGN.getValue()+"')='"+s+"' ";
					}
				}
				sql.append(" and ("+strSql.substring(2)+")");
			}else{
				sql.append(" and (ifnull(c.c_order_detail_status,'").append(BillStatus.OrderDetailStatus.UNASSIGN.getValue()).append("')='")
						.append(BillStatus.OrderDetailStatus.UNASSIGN.getValue()).append("' and  ifnull(c.c_closed_status,'0') in ('','0','1','4')  " +
						" or  ifnull(c.c_order_detail_status,'").append(BillStatus.OrderDetailStatus.UNASSIGN.getValue()).append("')!='"+BillStatus.OrderDetailStatus.UNASSIGN.getValue()+"'" +
						" )");
			}
	        //生产完成日期
	        if(StringUtils.isNotBlank(dto.getProdFinishTimeStr()) && StringUtils.isNotBlank(dto.getProdFinishTimeEnd())){
	            sql.append(" and i.c_prod_finish_time between '").append(dto.getProdFinishTimeStr()).append(" 00:00:00'").append(" and '").append(dto.getProdFinishTimeEnd()).append(" 23:59:59'");
	        }else if(StringUtils.isNotBlank(dto.getProdFinishTimeStr()) && StringUtils.isBlank(dto.getProdFinishTimeEnd())){
	        	sql.append(" and i.c_prod_finish_time >= '").append(dto.getProdFinishTimeStr()).append(" 00:00:00'");
	        }else if(StringUtils.isBlank(dto.getProdFinishTimeStr()) && StringUtils.isNotBlank(dto.getProdFinishTimeEnd())){
	        	sql.append(" and i.c_prod_finish_time <= '").append(dto.getProdFinishTimeEnd()).append(" 00:00:00'");
	        }
	        //颜色
	        if(StringUtils.isNotBlank(dto.getGoodsColorName())){
	            sql.append(" and e.c_color_name like '%").append(dto.getGoodsColorName()).append("%'");
	        }
	        //唯一码
	        if(StringUtils.isNotBlank(dto.getGoodsSn())){
	            sql.append(" and c.c_goods_sn like '%").append(dto.getGoodsSn()).append("%'");
	        }
	        //订单性质
	        if(StringUtils.isNotBlank(dto.getOrderCharacter()) && !"全部".equals(dto.getOrderCharacter())){
	            sql.append(" and d.c_order_character = '").append(dto.getOrderCharacter()).append("'");
	        }
	        //计划投产日期
	        if(StringUtils.isNotBlank(dto.getProdTimeStr()) && StringUtils.isNotBlank(dto.getProdTimeEnd())){
	            sql.append(" and b.c_prod_time between '").append(dto.getProdTimeStr()).append(" 00:00:00'").append(" and '").append(dto.getProdTimeEnd()).append(" 23:59:59'");
	        }else if(StringUtils.isNotBlank(dto.getProdTimeStr()) && StringUtils.isBlank(dto.getProdTimeEnd())){
	        	sql.append(" and b.c_prod_time >= '").append(dto.getProdTimeStr()).append(" 00:00:00'");
	        }else if(StringUtils.isBlank(dto.getProdTimeStr()) && StringUtils.isNotBlank(dto.getProdTimeEnd())){
	        	sql.append(" and b.c_prod_time <= '").append(dto.getProdTimeEnd()).append(" 00:00:00'");
	        }
	        //匹配尺码
	        if(StringUtils.isNotBlank(dto.getMesMeasureSizeName())){
	            sql.append(" and h.c_mes_measure_size_name like '%").append(dto.getMesMeasureSizeName()).append("%'");
	        }
	        //制版人
	        if(StringUtils.isNotBlank(dto.getTechnicianName())){
	            sql.append(" and a.c_technician_name like '%").append(dto.getTechnicianName()).append("%'");
	        }
	        //结案状态
	        if(StringUtils.isNotBlank(dto.getClosedStatus())){
	            sql.append(" and c.c_closed_status = '").append(dto.getClosedStatus()).append("'");
	        }
	        //发货日期
	        if(StringUtils.isNotBlank(dto.getSendTimeStr()) && StringUtils.isNotBlank(dto.getSendTimeEnd())){
	            sql.append(" and l.c_send_time between '").append(dto.getSendTimeStr()).append(" 00:00:00'").append(" and '").append(dto.getSendTimeEnd()).append(" 23:59:59'");
	        }else if(StringUtils.isNotBlank(dto.getSendTimeStr()) && StringUtils.isBlank(dto.getSendTimeEnd())){
	        	sql.append(" and l.c_send_time >= '").append(dto.getSendTimeStr()).append(" 00:00:00'");
	        }else if(StringUtils.isBlank(dto.getSendTimeStr()) && StringUtils.isNotBlank(dto.getSendTimeEnd())){
	        	sql.append(" and l.c_send_time <= '").append(dto.getSendTimeEnd()).append(" 00:00:00'");
	        }
	        //生产工厂
	        if(StringUtils.isNotBlank(dto.getProdFactory())){
	            sql.append(" and b.c_prod_factory_id like '%").append(dto.getProdFactory()).append("%'");
	        }
	        //出版方
	        if(StringUtils.isNotBlank(dto.getPublishers())){
	            sql.append(" and b.publishers like '%").append(dto.getPublishers()).append("%'");
	        }

			//订单状态
			if(StringUtils.isNotBlank(dto.getStatusBussiness())&&!"全部".equals(dto.getStatusBussiness())){
				sql.append(" and d.c_status_bussiness = '").append(dto.getStatusBussiness()).append("'");
			}
			//下达日期
			if(StringUtils.isNotBlank(dto.getIssueTimeStr()) && StringUtils.isNotBlank(dto.getIssueTimeEnd())){
				sql.append(" and b.c_send_time between '").append(dto.getIssueTimeStr()).append(" 00:00:00'").append(" and '").append(dto.getIssueTimeEnd()).append(" 23:59:59'");
			}else if(StringUtils.isNotBlank(dto.getIssueTimeStr()) && StringUtils.isBlank(dto.getIssueTimeEnd())){
				sql.append(" and b.c_send_time >= '").append(dto.getIssueTimeStr()).append(" 00:00:00'");
			}else if(StringUtils.isBlank(dto.getIssueTimeStr()) && StringUtils.isNotBlank(dto.getIssueTimeEnd())){
				sql.append(" and b.c_send_time <= '").append(dto.getIssueTimeEnd()).append(" 00:00:00'");
			}
	        sql.append(" order by b.c_create_time desc ");
			listSql.append(sql);
	        if(pageBean !=null && pageBean.getPage()!=null && pageBean.getRows()!=null){
				listSql.append(" limit "+(pageBean.getPage() - 1) * pageBean.getRows()+","+pageBean.getRows());
				List<BigInteger> result = dao.queryBySql(countSql.append(sql).toString());
				Long count = result == null || result.isEmpty() ? 0L : result.get(0).longValue();
				pagination.setTotal(count);
	        }

	        List resultSet = dao.queryBySql(listSql.toString());
	        for(Object result : resultSet){
	        	OrderPlanDetailModel model = new OrderPlanDetailModel();//构建返回数据模型
	            Object[] properties = (Object[])result;
	            model.setPlanNumber(properties[0]==null ? "" : properties[0].toString());
	            model.setProdFactory(properties[1]==null ? "" : properties[1].toString());
	            model.setOrderCode(properties[2]==null ? "" : properties[2].toString());
	            model.setOrderCharacter(properties[3]==null ? "" : properties[3].toString());
	            model.setCustomerCode(properties[4]==null ? "" : properties[4].toString());
	            model.setCustomerName(properties[5]==null ? "" : properties[5].toString());
	            model.setShopName(properties[6]==null ? "" : properties[6].toString());
	            model.setGoodsName(properties[7]==null ? "" : properties[7].toString());
	            model.setGoodsColorName(properties[8]==null ? "" : properties[8].toString());
	            model.setMesMeasureSizeName(properties[9]==null ? "" : properties[9].toString());
	            model.setGoodsSn(properties[10]==null ? "" : properties[10].toString());
	            model.setMaterielCode(properties[11]==null ? "" : properties[11].toString());
	            model.setPublishers(properties[12]==null ? "" : this.getPublishers(properties[12].toString()));
	            model.setTechnicianName(properties[13]==null ? "" : properties[13].toString());
	            model.setStatusBussiness(properties[14]==null ? "" : this.getStatusBussiness(properties[14].toString()));
	            model.setEmbroidName(properties[15]==null ? "" : properties[15].toString());
	            model.setStatusBeforeProduce(properties[16]==null ? "" : this.getStatusBeforeProduce(properties[16].toString()));
	            model.setStatusBeforeSend(properties[17]==null ? "" : this.getStatusBeforeSend(properties[17].toString()));
	            model.setOrderDetailStatus(properties[18]==null ? "" : this.getOrderDetailStatus(properties[18].toString()));
	            model.setPlanStatus(properties[19]==null ? "" : this.getPlanStatus(properties[19].toString()));
	            model.setProdFinishTime(properties[20]==null ? "" : properties[20].toString());
	            model.setTechnicalFinishedTime(properties[21]==null ? "" : properties[21].toString());
	            model.setAssignCreatetime(properties[22]==null ? "" : properties[22].toString());
	            model.setProdTime(properties[23]==null ? "" : properties[23].toString());
	            model.setDeliveryTime(properties[24]==null ? "" : properties[24].toString());
	            model.setSendTime(properties[25]==null ? "" : properties[25].toString());
	            model.setClosedStatus(properties[26]==null ? "" : this.getClosedStatus(properties[26].toString()));
	            model.setClosedCreatetime(properties[27]==null ? "" : properties[27].toString());
	            model.setXingyuCheckTime(properties[28]==null ? "" : properties[28].toString());
	            model.setCategoryName(properties[29]==null ? "" : properties[29].toString());
	            model.setGoodsCode(properties[30]==null ? "" : properties[30].toString());
				model.setIssueTime(properties[31]==null ? "" : properties[31].toString());
	            list.add(model);
	        }
		pagination.setRows(list);
		return pagination;
	    }
	 
	 private Long countOrderPlanDetail(PlanOrderDetailDto dto) {
//		 StringBuilder sql = new StringBuilder();
//		 sql.append(" SELECT count(*) ");
//		 sql.append(" FROM t_order_detail c  ");
//		 sql.append(" JOIN t_order d ON c.c_order_id = d.c_id and d.c_delete_flag='0' ");
//		 sql.append(" LEFT JOIN mes_order_detail_assign a ON c.c_id = a.c_order_detail_id and a.c_delete_flag=0  ");
//		 sql.append(" LEFT JOIN mes_order_plan_detail i ON c.c_id = i.c_order_detail_id and i.c_delete_flag=0 ");
//		 sql.append(" LEFT JOIN mes_order_plan b ON i.c_order_plan_id = b.c_id and b.c_delete_flag=0");
//		 sql.append(" LEFT JOIN t_goods_detail e ON c.c_goods_detail_id = e.c_id");
//		 sql.append(" LEFT JOIN t_logistic_order f ON c.c_id = f.c_order_detail_id ");
//		 sql.append(" LEFT JOIN t_logistic g ON f.c_logistic_id= g.c_id ");
//		 sql.append(" LEFT JOIN mes_order_detail_size h ON c.c_id = h.c_order_detail_id  ");
//		 sql.append(" LEFT JOIN t_order_detail_closed j ON c.c_id = j.c_order_detail_id ");
//		 sql.append(" LEFT JOIN t_order_closed k ON j.c_order_closed_id = k.c_id ");
//		 sql.append(" LEFT JOIN t_goods z ON e.c_goods_id=z.c_id ");
//		 sql.append(" WHERE ifnull(d.c_status_before_produce,'0')='2' and ifnull(d.c_status_bussiness,'') in ('3','6','7','8')  ");
//		 //商品品类
//		if(StringUtils.isNotBlank(dto.getCategoryId())){
//			sql.append(" and z.c_category_id = '").append(dto.getCategoryId()).append("'");
//		}
//		//星域审核日期
//		if(StringUtils.isNotBlank(dto.getXingyuChekTimeStr()) && StringUtils.isNotBlank(dto.getXingyuChekTimeEnd())){
//		    sql.append(" and d.c_xingyu_chek_time between '").append(dto.getXingyuChekTimeStr()).append(" 00:00:00'").append(" and '").append(dto.getXingyuChekTimeEnd()).append(" 23:59:59'");
//		}else if(StringUtils.isNotBlank(dto.getXingyuChekTimeStr()) && StringUtils.isBlank(dto.getXingyuChekTimeEnd())){
//			sql.append(" and d.c_xingyu_chek_time >= '").append(dto.getXingyuChekTimeStr()).append(" 00:00:00'");
//		}else if(StringUtils.isBlank(dto.getXingyuChekTimeStr()) && StringUtils.isNotBlank(dto.getXingyuChekTimeEnd())){
//			sql.append(" and d.c_xingyu_chek_time <= '").append(dto.getXingyuChekTimeEnd()).append(" 00:00:00'");
//		}
//		 //订单分配日期
//		 if(StringUtils.isNotBlank(dto.getAssignCreatetimeStr()) && StringUtils.isNotBlank(dto.getAssignCreatetimeEnd())){
//			 sql.append(" and a.c_create_time between '").append(dto.getAssignCreatetimeStr()).append(" 00:00:00'").append(" and '").append(dto.getAssignCreatetimeEnd()).append(" 23:59:59'");
//		 }else if(StringUtils.isNotBlank(dto.getAssignCreatetimeStr()) && StringUtils.isBlank(dto.getAssignCreatetimeEnd())){
//			 sql.append(" and a.c_create_time >= '").append(dto.getAssignCreatetimeStr()).append(" 00:00:00'");
//		 }else if(StringUtils.isBlank(dto.getAssignCreatetimeStr()) && StringUtils.isNotBlank(dto.getAssignCreatetimeEnd())){
//			 sql.append(" and a.c_create_time <= '").append(dto.getAssignCreatetimeEnd()).append(" 00:00:00'");
//		 }
//		 //计划单号
//		 if(StringUtils.isNotBlank(dto.getPlanNumber())){
//			 sql.append(" and b.c_plan_number like '%").append(dto.getPlanNumber()).append("%'");
//		 }
//		 //订单单号
//		 if(StringUtils.isNotBlank(dto.getOrderCode())){
//			 sql.append(" and d.c_order_code like '%").append(dto.getOrderCode()).append("%'");
//		 }
//		 //计划单状态
//		 if(StringUtils.isNotBlank(dto.getPlanStatus())){
//			 sql.append(" and b.c_plan_status = '").append(dto.getPlanStatus()).append("'");
//		 }
//		 //技术完成日期
//		 if(StringUtils.isNotBlank(dto.getTechnicalFinishedTimeStr()) && StringUtils.isNotBlank(dto.getTechnicalFinishedTimeEnd())){
//			 sql.append(" and a.c_technical_finished_time between '").append(dto.getTechnicalFinishedTimeStr()).append(" 00:00:00'").append(" and '").append(dto.getTechnicalFinishedTimeEnd()).append(" 23:59:59'");
//		 }else if(StringUtils.isNotBlank(dto.getTechnicalFinishedTimeStr()) && StringUtils.isBlank(dto.getTechnicalFinishedTimeEnd())){
//			 sql.append(" and a.c_technical_finished_time >= '").append(dto.getTechnicalFinishedTimeStr()).append(" 00:00:00'");
//		 }else if(StringUtils.isBlank(dto.getTechnicalFinishedTimeStr()) && StringUtils.isNotBlank(dto.getTechnicalFinishedTimeEnd())){
//			 sql.append(" and a.c_technical_finished_time <= '").append(dto.getTechnicalFinishedTimeEnd()).append(" 00:00:00'");
//		 }
//		 //款号
//		 if(StringUtils.isNotBlank(dto.getGoodsCode())){
//			 sql.append(" and c.c_goods_code like '%").append(dto.getGoodsCode()).append("%'");
//		 }
//		 //顾客姓名
//		 if(StringUtils.isNotBlank(dto.getCustomerName())){
//			 sql.append(" and d.c_customer_name like '%").append(dto.getCustomerName()).append("%'");
//		 }
//		 //订单明细状态
//		 if (StringUtils.isNotBlank(dto.getOrderDetailStatus()) && !"全部".equals(dto.getOrderDetailStatus())){
//			 //如果等于6 就是没分配的订单
//			 String[] status = dto.getOrderDetailStatus().split(",");
//			 String strSql="";
//			 for (String s:status) {
//				 if(BillStatus.OrderDetailStatus.UNASSIGN.getValue().equals(s)){
//					 strSql +="or  ifnull(c.c_order_detail_status,'"+BillStatus.OrderDetailStatus.UNASSIGN.getValue()+"')='"+s+"'";
//					 strSql +="  and  ifnull(c.c_closed_status,'0') in ('','0','1','4') ";
//				 }else {
//					 strSql +="or  ifnull(c.c_order_detail_status,'"+BillStatus.OrderDetailStatus.UNASSIGN.getValue()+"')='"+s+"' ";
//				 }
//			 }
//			 sql.append(" and ("+strSql.substring(2)+")");
//		 }
//		 else {
//			 sql.append(" and (ifnull(c.c_order_detail_status,'").append(BillStatus.OrderDetailStatus.UNASSIGN.getValue()).append("')='")
//					 .append(BillStatus.OrderDetailStatus.UNASSIGN.getValue()).append("' and  ifnull(c.c_closed_status,'0') in ('','0','1','4')  " +
//					 " or  ifnull(c.c_order_detail_status,'").append(BillStatus.OrderDetailStatus.UNASSIGN.getValue()).append("')!='"+BillStatus.OrderDetailStatus.UNASSIGN.getValue()+"'" +
//					 " )");
//		 }
//		 //生产完成日期
//		 if(StringUtils.isNotBlank(dto.getProdFinishTimeStr()) && StringUtils.isNotBlank(dto.getProdFinishTimeEnd())){
//			 sql.append(" and i.c_prod_finish_time between '").append(dto.getProdFinishTimeStr()).append(" 00:00:00'").append(" and '").append(dto.getProdFinishTimeEnd()).append(" 23:59:59'");
//		 }else if(StringUtils.isNotBlank(dto.getProdFinishTimeStr()) && StringUtils.isBlank(dto.getProdFinishTimeEnd())){
//			 sql.append(" and i.c_prod_finish_time >= '").append(dto.getProdFinishTimeStr()).append(" 00:00:00'");
//		 }else if(StringUtils.isBlank(dto.getProdFinishTimeStr()) && StringUtils.isNotBlank(dto.getProdFinishTimeEnd())){
//			 sql.append(" and i.c_prod_finish_time <= '").append(dto.getProdFinishTimeEnd()).append(" 00:00:00'");
//		 }
//		 //颜色
//		 if(StringUtils.isNotBlank(dto.getGoodsColorName())){
//			 sql.append(" and c.c_goods_color_name like '%").append(dto.getGoodsColorName()).append("%'");
//		 }
//		 //唯一码
//		 if(StringUtils.isNotBlank(dto.getGoodsSn())){
//			 sql.append(" and c.c_goods_sn like '%").append(dto.getGoodsSn()).append("%'");
//		 }
//		 //订单性质
//		 if(StringUtils.isNotBlank(dto.getOrderCharacter()) && !"全部".equals(dto.getOrderCharacter())){
//			 sql.append(" and d.c_order_character = '").append(dto.getOrderCharacter()).append("'");
//		 }
//		 //计划投产日期
//		 if(StringUtils.isNotBlank(dto.getProdTimeStr()) && StringUtils.isNotBlank(dto.getProdTimeEnd())){
//			 sql.append(" and b.c_prod_time between '").append(dto.getProdTimeStr()).append(" 00:00:00'").append(" and '").append(dto.getProdTimeEnd()).append(" 23:59:59'");
//		 }else if(StringUtils.isNotBlank(dto.getProdTimeStr()) && StringUtils.isBlank(dto.getProdTimeEnd())){
//			 sql.append(" and b.c_prod_time >= '").append(dto.getProdTimeStr()).append(" 00:00:00'");
//		 }else if(StringUtils.isBlank(dto.getProdTimeStr()) && StringUtils.isNotBlank(dto.getProdTimeEnd())){
//			 sql.append(" and b.c_prod_time <= '").append(dto.getProdTimeEnd()).append(" 00:00:00'");
//		 }
//		 //匹配尺码
//		 if(StringUtils.isNotBlank(dto.getMesMeasureSizeName())){
//			 sql.append(" and h.c_mes_measure_size_name like '%").append(dto.getMesMeasureSizeName()).append("%'");
//		 }
//		 //制版人
//		 if(StringUtils.isNotBlank(dto.getTechnicianName())){
//			 sql.append(" and a.c_technician_name like '%").append(dto.getTechnicianName()).append("%'");
//		 }
//		 //结案状态
//		 if(StringUtils.isNotBlank(dto.getClosedStatus())){
//			 sql.append(" and c.c_closed_status = '").append(dto.getClosedStatus()).append("'");
//		 }
//		 //发货日期
//		 if(StringUtils.isNotBlank(dto.getSendTimeStr()) && StringUtils.isNotBlank(dto.getSendTimeEnd())){
//			 sql.append(" and g.c_send_time between '").append(dto.getSendTimeStr()).append(" 00:00:00'").append(" and '").append(dto.getSendTimeEnd()).append(" 23:59:59'");
//		 }else if(StringUtils.isNotBlank(dto.getSendTimeStr()) && StringUtils.isBlank(dto.getSendTimeEnd())){
//			 sql.append(" and g.c_send_time >= '").append(dto.getSendTimeStr()).append(" 00:00:00'");
//		 }else if(StringUtils.isBlank(dto.getSendTimeStr()) && StringUtils.isNotBlank(dto.getSendTimeEnd())){
//			 sql.append(" and g.c_send_time <= '").append(dto.getSendTimeEnd()).append(" 00:00:00'");
//		 }
//		 //生产工厂
//		 if(StringUtils.isNotBlank(dto.getProdFactory())){
//			 sql.append(" and b.c_prod_factory_id like '%").append(dto.getProdFactory()).append("%'");
//		 }
//		 //出版方
//		 if(StringUtils.isNotBlank(dto.getPublishers())){
//			 sql.append(" and b.publishers like '%").append(dto.getPublishers()).append("%'");
//		 }
//
//		 //订单状态
//		 if(StringUtils.isNotBlank(dto.getStatusBussiness())&&!"全部".equals(dto.getStatusBussiness())){
//			 sql.append(" and d.c_status_bussiness = '").append(dto.getStatusBussiness()).append("'");
//		 }
//		 //下达日期
//		 if(StringUtils.isNotBlank(dto.getIssueTimeStr()) && StringUtils.isNotBlank(dto.getIssueTimeEnd())){
//			 sql.append(" and b.c_send_time between '").append(dto.getIssueTimeStr()).append(" 00:00:00'").append(" and '").append(dto.getIssueTimeEnd()).append(" 23:59:59'");
//		 }else if(StringUtils.isNotBlank(dto.getIssueTimeStr()) && StringUtils.isBlank(dto.getIssueTimeEnd())){
//			 sql.append(" and b.c_send_time >= '").append(dto.getIssueTimeStr()).append(" 00:00:00'");
//		 }else if(StringUtils.isBlank(dto.getIssueTimeStr()) && StringUtils.isNotBlank(dto.getIssueTimeEnd())){
//			 sql.append(" and b.c_send_time <= '").append(dto.getIssueTimeEnd()).append(" 00:00:00'");
//		 }
//	        List<BigInteger> result = dao.queryBySql(sql.toString());
//	        return result == null || result.isEmpty() ? 0L : result.get(0).longValue();
		 return null;
	    }
	 
	 
	 private List<TwOrderModel> twOrderList(PlanOrderDetailDto dto,PageBean pageBean){
	        List<TwOrderModel> list = new ArrayList<>();
	        StringBuilder sql = new StringBuilder();
	        sql.append(" SELECT b.c_prod_factory_id prodfactory,b.publishers publishers,c.c_erp_no erpNo,d.c_customer_name customerName,tg.c_code goodsCode,tg.c_name goodsName,e.c_color_name colorName,h.c_mes_measure_size_name sizeName,i.c_technician_name etchnicianName,c.c_goods_sn goodsSn,e.c_materiel_code materielCode,d.c_shop_name shopName,b.c_plan_number planNumber,b.c_send_time sendTime,d.c_order_code orderCode,d.c_submit_time submitTime,b.status_tw statustw,c.c_order_detail_status detailStatus,d.c_id orderId ");
		 	sql.append(" ,IF(IFNULL(mgd.garment_design_name,'')='','").append(Constants.FILE_STATUS.NOT_UPLOADED).append("','").append(Constants.FILE_STATUS.UPLOADED).append("') ");
		 	sql.append(" ,IF(IFNULL(mgd.pdf_url,'')='','").append(Constants.FILE_STATUS.NOT_UPLOADED).append("','").append(Constants.FILE_STATUS.UPLOADED).append("') ");
	        sql.append(" FROM mes_order_plan b ");
	        sql.append("  JOIN mes_order_plan_detail a ON a.c_order_plan_id = b.c_id and a.c_delete_flag=0 ");
	        sql.append("  JOIN t_order_detail c ON a.c_order_detail_id = c.c_id ");
	        sql.append("  JOIN t_order d ON c.c_order_id = d.c_id and d.c_delete_flag='0' ");
	        sql.append(" LEFT JOIN t_goods_detail e ON c.c_goods_detail_id = e.c_id");
	        sql.append(" LEFT JOIN t_goods tg ON e.c_goods_id = tg.c_id ");
	        sql.append(" LEFT JOIN mes_order_detail_size h ON a.c_order_detail_id = h.c_order_detail_id  ");
	        sql.append(" LEFT JOIN mes_order_detail_assign i ON a.c_order_detail_id = i.c_order_detail_id and i.c_delete_flag=0");
		 	sql.append(" LEFT JOIN mes_garment_design mgd ON mgd.order_detail_id = c.c_id ");
	        sql.append(" WHERE  b.c_delete_flag='0' and b.c_prod_factory_id='台湾秀妮儿' and b.c_plan_status in ('4','9') ");
	        //计划下达日期
	        if(StringUtils.isNotBlank(dto.getSendTimeStr()) && StringUtils.isNotBlank(dto.getSendTimeEnd())){
	            sql.append(" and b.c_send_time between '").append(dto.getSendTimeStr()).append(" 00:00:00'").append(" and '").append(dto.getSendTimeEnd()).append(" 23:59:59'");
	        }else if(StringUtils.isNotBlank(dto.getSendTimeStr()) && StringUtils.isBlank(dto.getSendTimeEnd())){
	        	sql.append(" and b.c_send_time >= '").append(dto.getSendTimeStr()).append(" 00:00:00'");
	        }else if(StringUtils.isBlank(dto.getSendTimeStr()) && StringUtils.isNotBlank(dto.getSendTimeEnd())){
	        	sql.append(" and b.c_send_time <= '").append(dto.getSendTimeEnd()).append(" 00:00:00'");
	        }
	        //计划单号
	        if(StringUtils.isNotBlank(dto.getPlanNumber())){
	            sql.append(" and b.c_plan_number like '%").append(dto.getPlanNumber()).append("%'");
	        }
	        //订单单号
	        if(StringUtils.isNotBlank(dto.getOrderCode())){
	            sql.append(" and d.c_order_code like '%").append(dto.getOrderCode()).append("%'");
	        }
	        //门店POS单号（ERP订单号）
	        if(StringUtils.isNotBlank(dto.getErpNo())){
	            sql.append(" and c.c_erp_no like '%").append(dto.getErpNo()).append("%'");
	        }
	        //订单提交日期
	        if(StringUtils.isNotBlank(dto.getSubmitTimeStr()) && StringUtils.isNotBlank(dto.getSubmitTimeEnd())){
	            sql.append(" and d.c_submit_time between '").append(dto.getSubmitTimeStr()).append(" 00:00:00'").append(" and '").append(dto.getSubmitTimeEnd()).append(" 23:59:59'");
	        }else if(StringUtils.isNotBlank(dto.getSubmitTimeStr()) && StringUtils.isBlank(dto.getSubmitTimeEnd())){
	        	sql.append(" and d.c_submit_time >= '").append(dto.getSubmitTimeStr()).append(" 00:00:00'");
	        }else if(StringUtils.isBlank(dto.getSubmitTimeStr()) && StringUtils.isNotBlank(dto.getSubmitTimeEnd())){
	        	sql.append(" and d.c_submit_time <= '").append(dto.getSubmitTimeEnd()).append(" 00:00:00'");
	        }
	        //款号
	        if(StringUtils.isNotBlank(dto.getGoodsCode())){
	            sql.append(" and tg.c_code like '%").append(dto.getGoodsCode()).append("%'");
	        }
	        //顾客姓名
	        if(StringUtils.isNotBlank(dto.getCustomerName())){
	            sql.append(" and d.c_customer_name like '%").append(dto.getCustomerName()).append("%'");
	        }
	        //颜色
	        if(StringUtils.isNotBlank(dto.getGoodsColorName())){
	            sql.append(" and e.c_color_name like '%").append(dto.getGoodsColorName()).append("%'");
	        }
	        //唯一码
	        if(dto.getGoodsSns()!=null && dto.getGoodsSns().length>0){
	        	String a = "";
	        	for (int i = 0; i < dto.getGoodsSns().length; i++) {
	        		if(a==null || a== ""){
	        			a = dto.getGoodsSns()[i];
	        		}else{
	        			a = a+","+dto.getGoodsSns()[i];
	        		}
				}
	            sql.append(" and c.c_goods_sn in (").append(a).append(")");
	        }
	        //匹配尺码
	        if(StringUtils.isNotBlank(dto.getMesMeasureSizeName())){
	            sql.append(" and h.c_mes_measure_size_name like '%").append(dto.getMesMeasureSizeName()).append("%'");
	        }
	        //制版人
	        if(StringUtils.isNotBlank(dto.getTechnicianName())){
	            sql.append(" and i.c_technician_name like '%").append(dto.getTechnicianName()).append("%'");
	        }

	        //出版方
	        if(StringUtils.isNotBlank(dto.getPublishers())){
				sql.append(" and b.publishers = '").append(dto.getPublishers()).append("'");
//	            sql.append(" and b.publishers like '%").append(dto.getPublishers()).append("%'");
	        }
	        sql.append(" order by b.c_create_time desc ");
	        if(pageBean.getPage()!=null && pageBean.getRows()!=null){
	            sql.append(" limit "+(pageBean.getPage() - 1) * pageBean.getRows()+","+pageBean.getRows());
	        }
	        List resultSet = dao.queryBySql(sql.toString());
	        for(Object result : resultSet){
	        	TwOrderModel model = new TwOrderModel();//构建返回数据模型
	            Object[] properties = (Object[])result;
	            model.setProdfactory(properties[0]==null ? "" : properties[0].toString());
	            model.setPublishers(properties[1]==null ? "" : properties[1].toString());
	            model.setErpNo(properties[2]==null ? "" : properties[2].toString());
	            model.setCustomerName(properties[3]==null ? "" : properties[3].toString());
	            model.setGoodsCode(properties[4]==null ? "" : properties[4].toString());
	            model.setGoodsName(properties[5]==null ? "" : properties[5].toString());
	            model.setGoodsColorName(properties[6]==null ? "" : properties[6].toString());
	            model.setSizeName(properties[7]==null ? "" : properties[7].toString());
	            model.setTechnicianName(properties[8]==null ? "" : properties[8].toString());
	            model.setGoodsSn(properties[9]==null ? "" : properties[9].toString());
	            model.setMaterielCode(properties[10]==null ? "" : properties[10].toString());
	            model.setShopName(properties[11]==null ? "" : properties[11].toString());
	            model.setPlanNum(properties[12]==null ? "" : properties[12].toString());
	            model.setSendTime(properties[13]==null ? "" : properties[13].toString());
	            model.setOrderCode(properties[14]==null ? "" : properties[14].toString());
	            model.setSubmitTime(properties[15]==null ? "" : properties[15].toString());
	            model.setTwstatus(properties[16]==null ? "" : properties[16].toString());
	            model.setDetailStatus(properties[17]==null ? "" : properties[17].toString());
	            model.setOrderId(properties[18]==null ? "" : properties[18].toString());
				model.setFileStatus(properties[19]==null ? Constants.FILE_STATUS.NOT_UPLOADED : properties[19].toString());
				model.setPdfStatus(properties[20]==null ? Constants.FILE_STATUS.NOT_UPLOADED : properties[20].toString());
	            list.add(model);
	        }
	        return list;
	    }
	 
	 private Long countTwList(PlanOrderDetailDto dto) {
	 	StringBuilder sql = new StringBuilder();
        sql.append(" SELECT count(*) ");
        sql.append(" FROM mes_order_plan b ");
        sql.append(" JOIN mes_order_plan_detail a ON a.c_order_plan_id = b.c_id and a.c_delete_flag=0 ");
        sql.append(" JOIN t_order_detail c ON a.c_order_detail_id = c.c_id ");
        sql.append(" JOIN t_order d ON c.c_order_id = d.c_id and d.c_delete_flag='0' ");
        sql.append(" LEFT JOIN t_goods_detail e ON c.c_goods_detail_id = e.c_id");
        sql.append(" LEFT JOIN t_goods tg ON e.c_goods_id = tg.c_id ");
        sql.append(" LEFT JOIN mes_order_detail_size h ON a.c_order_detail_id = h.c_order_detail_id ");
        sql.append(" LEFT JOIN mes_order_detail_assign i ON a.c_order_detail_id = i.c_order_detail_id and i.c_delete_flag=0 ");
        sql.append(" WHERE  b.c_delete_flag='0' and b.c_prod_factory_id='台湾秀妮儿' and b.c_plan_status in ('4','9') ");
        //计划下达日期
        if(StringUtils.isNotBlank(dto.getSendTimeStr()) && StringUtils.isNotBlank(dto.getSendTimeEnd())){
            sql.append(" and b.c_send_time between '").append(dto.getSendTimeStr()).append(" 00:00:00'").append(" and '").append(dto.getSendTimeEnd()).append(" 23:59:59'");
        }else if(StringUtils.isNotBlank(dto.getSendTimeStr()) && StringUtils.isBlank(dto.getSendTimeEnd())){
        	sql.append(" and b.c_send_time >= '").append(dto.getSendTimeStr()).append(" 00:00:00'");
        }else if(StringUtils.isBlank(dto.getSendTimeStr()) && StringUtils.isNotBlank(dto.getSendTimeEnd())){
        	sql.append(" and b.c_send_time <= '").append(dto.getSendTimeEnd()).append(" 00:00:00'");
        }
        //计划单号
        if(StringUtils.isNotBlank(dto.getPlanNumber())){
            sql.append(" and b.c_plan_number like '%").append(dto.getPlanNumber()).append("%'");
        }
        //订单单号
        if(StringUtils.isNotBlank(dto.getOrderCode())){
            sql.append(" and d.c_order_code like '%").append(dto.getOrderCode()).append("%'");
        }
        //门店POS单号（ERP订单号）
        if(StringUtils.isNotBlank(dto.getErpNo())){
            sql.append(" and c.c_erp_no like '%").append(dto.getErpNo()).append("%'");
        }
        //订单提交日期
        if(StringUtils.isNotBlank(dto.getSubmitTimeStr()) && StringUtils.isNotBlank(dto.getSubmitTimeEnd())){
            sql.append(" and d.c_submit_time between '").append(dto.getSubmitTimeStr()).append(" 00:00:00'").append(" and '").append(dto.getSubmitTimeEnd()).append(" 23:59:59'");
        }else if(StringUtils.isNotBlank(dto.getSubmitTimeStr()) && StringUtils.isBlank(dto.getSubmitTimeEnd())){
        	sql.append(" and d.c_submit_time >= '").append(dto.getSubmitTimeStr()).append(" 00:00:00'");
        }else if(StringUtils.isBlank(dto.getSubmitTimeStr()) && StringUtils.isNotBlank(dto.getSubmitTimeEnd())){
        	sql.append(" and d.c_submit_time <= '").append(dto.getSubmitTimeEnd()).append(" 00:00:00'");
        }
        //款号
        if(StringUtils.isNotBlank(dto.getGoodsCode())){
            sql.append(" and tg.c_code = '").append(dto.getGoodsCode()).append("'");
        }
        //顾客姓名
        if(StringUtils.isNotBlank(dto.getCustomerName())){
            sql.append(" and d.c_customer_name like '%").append(dto.getCustomerName()).append("%'");
        }
        //颜色
        if(StringUtils.isNotBlank(dto.getGoodsColorName())){
            sql.append(" and e.c_color_name = '").append(dto.getGoodsColorName()).append("'");
        }
        //唯一码
        if(dto.getGoodsSns()!=null && dto.getGoodsSns().length>0){
        	String a = "";
        	for (int i = 0; i < dto.getGoodsSns().length; i++) {
        		if(a==null || a== ""){
        			a = dto.getGoodsSns()[i];
        		}else{
        			a = a+","+dto.getGoodsSns()[i];
        		}
			}
            sql.append(" and c.c_goods_sn in (").append(a).append(")");
        }
        //匹配尺码
        if(StringUtils.isNotBlank(dto.getMesMeasureSizeName())){
            sql.append(" and h.c_mes_measure_size_name like '%").append(dto.getMesMeasureSizeName()).append("%'");
        }
        //制版人
        if(StringUtils.isNotBlank(dto.getTechnicianName())){
            sql.append(" and i.c_technician_name like '%").append(dto.getTechnicianName()).append("%'");
        }
//        //生产工厂
//        if(StringUtils.isNotBlank(dto.getProdFactory())){
//            sql.append(" and b.c_prod_factory_id = '").append(dto.getProdFactory()).append("'");
//        }
        //出版方
        if(StringUtils.isNotBlank(dto.getPublishers())){
			sql.append(" and b.publishers = '").append(dto.getPublishers()).append("'");
//            sql.append(" and b.publishers = '").append(dto.getPublishers()).append("'");
        }
        List<BigInteger> result = dao.queryBySql(sql.toString());
        return result == null || result.isEmpty() ? 0L : result.get(0).longValue();
	  }

	/**
	 * 获取出版方名称
	 * @param code
	 * @return
	 */
	private String getPublishers(String code){
	 	Integer id =0;
		  String name="";
	 	if (StringUtils.isNotBlank(code)){
			id=Integer.parseInt(code);
		}
	 	switch (id){
			case 1:
				name="青岛星域";break;
			case 2:
				name="台湾秀妮儿";break;
			default:
				name="";break;
		}

		return name;
	  }
	/**
	 * 获取订单状态名称
	 * @param code
	 * @return
	 */
	private String getStatusBussiness(String code){
		Integer id =0;
		String name="";
		if (StringUtils.isNotBlank(code)){
			id=Integer.parseInt(code);
		}
		switch (id){
			case 0:
				name="未送出";break;
			case 1:
				name="已送出";break;
			case 2:
				name="已提交星域";break;
			case 3:
				name="星域审核通过";break;
			case 4:
				name="星域驳回";break;
			case 5:
				name="秀域驳回";break;
			case 6:
				name="生产完成";break;
			case 7:
				name="已发货";break;
			case 8:
				name="已收货";break;
			default:
				name="";break;
		}

		return name;
	}
	/**
	 * 获取生产前财务审核名称
	 * @param code
	 * @return
	 */
	private String getStatusBeforeProduce(String code){
		Integer id =0;
		String name="";
		if (StringUtils.isNotBlank(code)){
			id=Integer.parseInt(code);
		}
		switch (id){
			case 0:
				name="未审核";break;
			case 1:
				name="审核不通过";break;
			case 2:
				name="审核通过";break;
			default:
				name="";break;
		}

		return name;
	}
	/**
	 * 获取发货前财务审核名称
	 * @param code
	 * @return
	 */
	private String getStatusBeforeSend(String code){
		Integer id =0;
		String name="";
		if (StringUtils.isNotBlank(code)){
			id=Integer.parseInt(code);
		}
		switch (id){
			case 0:
				name="未审核";break;
			case 1:
				name="审核不通过";break;
			case 2:
				name="审核通过";break;
			default:
				name="";break;
		}

		return name;
	}
	/**
	 * 获取订单明细名称
	 * @param code
	 * @return
	 */
	private String getOrderDetailStatus(String code){
		Integer id =0;
		String name="";
		if (StringUtils.isNotBlank(code)){
			id=Integer.parseInt(code);
		}
		switch (id){
			case 0:
				name="已排程";break;
			case 1:
				name="生产中";break;
			case 2:
				name="生产完成";break;
			case 3:
				name="已发货";break;
			case 4:
				name="已收货";break;
			case 5:
				name="已反馈";break;
			case 6:
				name="未分配";break;
			case 7:
				name="技术完成";break;
			case 8:
				name="计划维护";break;
			case 9:
				name="待发货";break;
			default:
				name=code;break;
		}

		return name;
	}

	/**
	 * 获取结案状态名称
	 * @param code
	 * @return
	 */
	private String getClosedStatus(String code){
		Integer id =0;
		String name="";
		if (StringUtils.isNotBlank(code)){
			id=Integer.parseInt(code);
		}
		switch (id){
			case 0:
				name="未申请结案";break;
			case 1:
				name="已申请未提交";break;
			case 2:
				name="已提交";break;
			case 3:
				name="审核通过";break;
			case 4:
				name="已驳回";break;
			default:
				name="";break;
		}

		return name;
	}

	/**
	 * 获取结案状态名称
	 * @param code
	 * @return
	 */
	private String getPlanStatus(String code){
		Integer id =0;
		String name="";
		if (StringUtils.isNotBlank(code)){
			id=Integer.parseInt(code);
		}
		//0：草稿，1：待审核，2：已审核，3：已驳回，4：已下达，9：生产完成
		switch (id){
			case 0:
				name="草稿";break;
			case 1:
				name="待审核";break;
			case 2:
				name="已审核";break;
			case 3:
				name="已驳回";break;
			case 4:
				name="已下达";break;
			case 9:
				name="生产完成";break;
			default:
				name="";break;
		}

		return name;
	}

}
