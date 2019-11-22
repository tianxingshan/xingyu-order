package com.kongque.service.balance.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kongque.util.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.kongque.constants.Constants;
import com.kongque.dao.IDaoService;
import com.kongque.dto.BalanceDto;
import com.kongque.dto.OrderRepairDto;
import com.kongque.entity.balance.Balance;
import com.kongque.entity.balance.BalanceRepairRelation;
import com.kongque.entity.basics.Code;
import com.kongque.entity.repair.OrderRepair;
import com.kongque.model.BalanceModel;
import com.kongque.model.OrderRepairModel;
import com.kongque.service.balance.IBalanceService;

@Service
public class BalanceServiceImpl implements IBalanceService {
	@Resource
	private IDaoService dao;

	// 根据条件分页查询结算单列表
	@Override
	public Pagination<BalanceModel> list(BalanceDto dto, PageBean pageBean) {
		if (pageBean.getPage() == null) {
			pageBean.setPage(1);
		}
		if (pageBean.getRows() == null) {
			pageBean.setRows(9999);
		}
		List<BalanceModel> lrbm = new ArrayList<>();
		Pagination<BalanceModel> pagination = new Pagination<>();
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT balance.c_id,balance.c_balance_numeber,balance.c_city,balance.c_create_time,balance.c_company_id ,balance.c_balance_status,company.c_dept_name,balance.c_count,balance.c_settlement_amount,t.c_tenant_name,balance.c_payer,balance.c_payment_days   "
						+ "from t_balance balance "
						+ "left join t_dept company on balance.c_company_id=company.c_id and company.c_dept_type='2' "
						+" left join t_tenant t on balance.c_tenant_id = t.c_id "
						+" where 1=1 ");
		/**
		 * 城市
		 */
		if (dto.getCity() != null && !dto.getCity().isEmpty()) {
			sql.append("  and balance.c_city like '%").append(dto.getCity()).append("%'");
		}
		/**
		 * 分公司查询
		 */
		if (dto.getCompanyId() != null && !dto.getCompanyId().isEmpty()) {
			sql.append(" and balance.c_company_id =  '").append(dto.getCompanyId()).append("' ");
		}
		/**
		 * 商户
		 */
		if(StringUtils.isNotBlank(dto.getTenantId())){
			sql.append(" and balance.c_tenant_id =  '").append(dto.getTenantId()).append("' ");
		}

		/**
		 * 付款方
		 */
		if(StringUtils.isNotBlank(dto.getPayer())){
			sql.append(" and balance.c_payer like  '%").append(dto.getPayer()).append("%' ");
		}

		/**
		 * 账期
		 */
		if(StringUtils.isNotBlank(dto.getPaymentDays())){
			sql.append(" and balance.c_payment_days =  '").append(dto.getPaymentDays()).append("' ");
		}

		/**
		 * 结算时间
		 */
		if (dto.getBalanceStartTime() != null && !dto.getBalanceStartTime().isEmpty() && dto.getBalanceEndTime() != null
				&& !dto.getBalanceEndTime().isEmpty()) {// 添加同时包含起始订单日期和截止订单日期的限定条件
			sql.append(" and balance.c_create_time between '").append(dto.getBalanceStartTime()).append(" 00:00:00'")
					.append(" and '").append(dto.getBalanceEndTime()).append(" 23:59:59'");
		} else if (dto.getBalanceStartTime() != null && !dto.getBalanceStartTime().isEmpty()
				&& (dto.getBalanceEndTime() == null || dto.getBalanceEndTime().isEmpty())) {// 添加只包含起始订单日期而不包含截止订单日期的限定条件
			sql.append(" and balance.c_create_time between '").append(dto.getBalanceStartTime()).append(" 00:00:00'")
					.append(" and '").append(dto.getBalanceStartTime()).append(" 23:59:59'");
		} else if ((dto.getBalanceStartTime() == null || dto.getBalanceStartTime().isEmpty())
				&& dto.getBalanceEndTime() != null && !dto.getBalanceEndTime().isEmpty()) {// 添加只包含截止订单日期而不包含起始订单日期的限定条件
			sql.append(" and balance.c_create_time  between '").append(dto.getBalanceEndTime()).append(" 00:00:00'")
					.append(" and '").append(dto.getBalanceEndTime()).append(" 23:59:59'");
		}
		/**
		 * 结算单号
		 */
		if (dto.getBalanceCode() != null && !dto.getBalanceCode().isEmpty()) {
			sql.append(" and balance.c_balance_numeber like '%").append(dto.getBalanceCode()).append("%'");
		}
		/**
		 * 结算单id
		 */
		if (dto.getBalanceId() != null && !dto.getBalanceId().isEmpty()) {

			sql.append(" and balance.c_id = '").append(dto.getBalanceId()).append("'");
		}
		/**
		 * 结算凭证结算状态
		 */
		if (dto.getBalanceStatus() != null && !dto.getBalanceStatus().isEmpty()) {
			sql.append(" and balance.c_balance_status = '").append(dto.getBalanceStatus()).append("'");
		}
			sql.append(" order by balance.c_balance_numeber desc ");
		int total = dao.queryBySql(sql.toString()).size();
		pagination.setTotal(total);
		if (pageBean.getPage() != null && pageBean.getRows() != null) {
			sql.append(" limit " + (pageBean.getPage() - 1) * pageBean.getRows() + "," + pageBean.getRows());
		}
		List resultSet = dao.queryBySql(sql.toString());
		if (resultSet != null && resultSet.size() > 0) {
			for (Object result : resultSet) {
				BalanceModel rbm = new BalanceModel();
				Object[] properties = (Object[]) result;
				rbm.setBalanceId(properties[0] == null ? "" : properties[0].toString());
				rbm.setBalanceNumeber(properties[1] == null ? "" : properties[1].toString());
				rbm.setCity(properties[2] == null ? "" : properties[2].toString());
				rbm.setBalanceTime(formatDate(properties[3] == null ? "" : properties[3].toString()));
				rbm.setCompanyId(properties[4] == null ? "" : properties[4].toString());
				rbm.setBalanceStatus(properties[5] == null ? "" : properties[5].toString());
				rbm.setUnitName(properties[6] == null ? "" : properties[6].toString());
				rbm.setNum(properties[7] == null ? "" : properties[7].toString());
				rbm.setAmount(properties[8] == null ? "" : properties[8].toString());
				rbm.setTenantName(properties[9] == null ? "" : properties[9].toString());
				rbm.setPayer(properties[10] == null ? "" : properties[10].toString());
				rbm.setPaymentDays(properties[11] == null ? "" : properties[11].toString());
				lrbm.add(rbm);
			}
		}
		pagination.setRows(lrbm);

		return pagination;

	}

	// 新增或修改结算单
	@Override
	public Result saveOrUpdate(Balance dto) {
		if (StringUtils.isBlank(dto.getId())) {
			Balance balance = new Balance();
			BeanUtils.copyProperties(dto, balance);
			balance.setBalanceNumeber(CodeUtil.createBalanceCode(getBalanceMaxValue()));
			balance.setCreateTime(new Date());
			dao.save(balance);
			saveBalance(balance, dto);
		} else {
//			Balance balance = dao.findById(Balance.class, dto.getId());
//			if (balance != null) {
			Balance balance = new Balance();
			BeanUtils.copyProperties(dto, balance);
			balance.setUpdateTime(new Date());
			if (StringUtils.isNotBlank(dto.getBalanceStatus()) && "3".equals(dto.getBalanceStatus())) {
				balance.setBalanceTime(new Date());
			}
			updateBalance(balance, dto);
			dao.update(balance);
//			} else {
//				return new Result(Constants.RESULT_CODE.SYS_ERROR, "没有此结算单！");
//			}
		}
		return new Result("200", "操作成功！");
	}

	// 新增结算单_orderRapair
	public void saveBalance(Balance balance, Balance dto) {
		if (dto.getBalanceRepairRelations() != null && dto.getBalanceRepairRelations().size() > 0) {
			for (int i = 0; i < dto.getBalanceRepairRelations().size(); i++) {
				BalanceRepairRelation repairRelation = new BalanceRepairRelation();
				repairRelation.setBalanceId(balance.getId());
				repairRelation.setLineOrderCode(dto.getBalanceRepairRelations().get(i).getLineOrderCode());
				repairRelation.setRepairId(dto.getBalanceRepairRelations().get(i).getRepairId());
				repairRelation.setMatterCode(dto.getBalanceRepairRelations().get(i).getMatterCode());
				repairRelation.setUnitPrice(dto.getBalanceRepairRelations().get(i).getUnitPrice());
				repairRelation.setAmount(dto.getBalanceRepairRelations().get(i).getAmount());
				dao.save(repairRelation);
				OrderRepair repair = dao.findById(OrderRepair.class, dto.getBalanceRepairRelations().get(i).getRepairId());
				repair.setFinanceCheckStatus("1");
				dao.update(repair);
			}
		}

	}

	// 修改结算单-orderRapair
	public void updateBalance(Balance balance, Balance dto) {
		if (dto.getBalanceRepairRelations() != null && dto.getBalanceRepairRelations().size() > 0) {
			Criteria criteria = dao.createCriteria(BalanceRepairRelation.class);
			criteria.add(Restrictions.eq("balanceId", balance.getId()));
			@SuppressWarnings("unchecked")
			List<BalanceRepairRelation> list = criteria.list();
			if (list != null) {
				for (BalanceRepairRelation repairRelation : list) {
					dao.delete(repairRelation);
				}
			}
			for (int i = 0; i < dto.getBalanceRepairRelations().size(); i++) {
				BalanceRepairRelation repairRelation = new BalanceRepairRelation();
				repairRelation.setBalanceId(balance.getId());
				repairRelation.setLineOrderCode(dto.getBalanceRepairRelations().get(i).getLineOrderCode());
				repairRelation.setRepairId(dto.getBalanceRepairRelations().get(i).getRepairId());
				repairRelation.setMatterCode(dto.getBalanceRepairRelations().get(i).getMatterCode());
				repairRelation.setUnitPrice(dto.getBalanceRepairRelations().get(i).getUnitPrice());
				repairRelation.setAmount(dto.getBalanceRepairRelations().get(i).getAmount());
				dao.save(repairRelation);
				OrderRepair repair = dao.findById(OrderRepair.class, dto.getBalanceRepairRelations().get(i).getRepairId());
				repair.setFinanceCheckStatus("1");
				dao.update(repair);
			}
		}

	}

	//删除orderRapair
	@Override
	public Result delRepair(BalanceDto dto) {
		for (int i = 0; i < dto.getOrderRepairIds().length; i++) {
			BalanceRepairRelation relation = dao.findUniqueByProperty(BalanceRepairRelation.class, "repairId", dto.getOrderRepairIds()[i]);
			if(relation!=null){
				dao.delete(relation);
			}
		}
		return new Result("200","删除成功！");
	}
	
	@Override
	public Result del(String id){
		Balance balance = dao.findById(Balance.class, id);
		if (balance != null) {
			Criteria criteria = dao.createCriteria(BalanceRepairRelation.class);
			criteria.add(Restrictions.eq("balanceId", balance.getId()));
			@SuppressWarnings("unchecked")
			List<BalanceRepairRelation> list = criteria.list();
			if (list != null) {
				for (BalanceRepairRelation repairRelation : list) {
					dao.delete(repairRelation);
				}
				updateOrderRepairFinanceStatus(list);
			}
			dao.delete(balance);
			return new Result("200","删除成功！");
		} else {
			return new Result(Constants.RESULT_CODE.SYS_ERROR, "没有此结算单！");
		}
	}

	private void updateOrderRepairFinanceStatus(List<BalanceRepairRelation> list) {
		Criteria criteria = dao.createCriteria(OrderRepair.class);
		List<String> ids = new ArrayList<>();
		for(BalanceRepairRelation balanceRepairRelation : list) {
			ids.add(balanceRepairRelation.getRepairId());
		}
		criteria.add(Restrictions.in("id", ids));
		List<OrderRepair> orderRepairList = criteria.list();
		for(OrderRepair orderRepair : orderRepairList) {
			orderRepair.setFinanceCheckStatus("0");
		}
	}

	// 获取结算单号
	public String getBalanceMaxValue() {
		Date date = new Date();
		Criteria criteria = dao.createCriteria(Code.class);
		criteria.add(Restrictions.between("updateTime", DateUtil.minDate(date), DateUtil.maxDate(date)));
		criteria.add(Restrictions.eq("type", "JS"));
		criteria.addOrder(org.hibernate.criterion.Order.desc("maxValue"));
		criteria.setMaxResults(1);
		Code code = (Code) criteria.uniqueResult();
		if (code == null) {
			code = new Code();
			code.setMaxValue(1);
			code.setType("JS");
			code.setUpdateTime(date);
			dao.save(code);
		} else {
			code.setMaxValue(code.getMaxValue() + 1);
		}
		return String.format("%0" + 6 + "d", code.getMaxValue());
	}

	// 审核结算单
	@Override
	public Result checkBalance(String id, String checkInfo, String checkStatus) {
		Balance balance = dao.findById(Balance.class, id);
		if (balance != null) {
			// checkStatus: 2：已确认 3：已结算
			/**
			 * 确认：2 和审核：3后台校验
			 */
			if (StringUtils.isNotBlank(checkStatus) && Integer.parseInt(checkStatus) >= 1
					&& Integer.parseInt(checkStatus) <= 3) {
				// 结算确认校验
				if ("2".equals(checkStatus)) {
					// 结算确认只能确认待确认状态
					String dataBalanceStatus = balance.getBalanceStatus();
					if (!"1".equals(dataBalanceStatus)) {
						return new Result(Constants.RESULT_CODE.SYS_ERROR, "当前状态不能确认");
					}
					balance.setBalanceTime(null);
				}
				if ("3".equals(checkStatus)) {
					// 提交校验
					// 结算提交只能提交已确认状态
					String dataBalanceStatus = balance.getBalanceStatus();

					if (!"2".equals(dataBalanceStatus)) {
						return new Result(Constants.RESULT_CODE.SYS_ERROR, "当前状态不能提交");
					}
					balance.setBalanceTime(new Date());
				}
				// 设置结算状态
				balance.setBalanceStatus(checkStatus);
				//修改微调单结算状态
				if("2".equals(checkStatus)){
					Criteria criteria = dao.createCriteria(BalanceRepairRelation.class);
					criteria.add(Restrictions.eq("balanceId", balance.getId()));
					@SuppressWarnings("unchecked")
					List<BalanceRepairRelation> list = criteria.list();
					if(list!=null){
						for (int i = 0; i < list.size(); i++) {
							OrderRepair or = dao.findById(OrderRepair.class, list.get(i).getRepairId());
							or.setFinanceCheckStatus("2");
							dao.update(or);
						}
					}
				}else if("3".equals(checkStatus)){
					Criteria criteria = dao.createCriteria(BalanceRepairRelation.class);
					criteria.add(Restrictions.eq("balanceId", balance.getId()));
					@SuppressWarnings("unchecked")
					List<BalanceRepairRelation> list = criteria.list();
					if(list!=null){
						for (int i = 0; i < list.size(); i++) {
							OrderRepair or = dao.findById(OrderRepair.class, list.get(i).getRepairId());
							or.setFinanceCheckStatus("3");
							dao.update(or);
						}
					}
				}
			} else {
				return new Result(Constants.RESULT_CODE.SYS_ERROR, "当前状态不能提交");
			}
			if (checkInfo != null && !checkInfo.isEmpty()) {
				balance.setCheckInfo(checkInfo);
			}
			balance.setUpdateTime(new Date());
			return new Result(balance);
		} else {
			return new Result(Constants.RESULT_CODE.SYS_ERROR, "没有此结算单！");
		}
	}

	// 打印Excel
	@Override
	public void getExcel(String id, HttpServletRequest request, HttpServletResponse response) {
		String excelFileName = "结算单明细";
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
			Balance balance = dao.findById(Balance.class, id);
			Pagination<BalanceModel> pagination = findListByBalanceidForExcel(id);
			List<BalanceModel> repairBalanceModels = pagination.getRows();

			/**
			 * 结算明细
			 */
			String[] headers = new String[] { "序号", "城市", "门店", "顾客姓名", "实调内容", "款式名", "件数", "颜色", "微调单号", "孔雀订单号",
					"单价", "金额", "发货时间", "收货时间", "发货物流单号","订单性质","商品唯一码","原生产工厂" };
			/**
			 * 结算凭证
			 */
			/*
			 * String[] headers2 = new String[] { "收款方", "付款方", "付款方式", "货币类型", "结算金额",
			 * "当前定金总额", "本次扣除定金", "本次应收金额", "单价", "数量", "结算时间", "账期", "业务类型", "结算单号",
			 * "结算状态", "微调单来源", "税务相关信息", "备注" };
			 */
			Set<String> excludedFieldSet = new HashSet<String>();

			excludedFieldSet.add("unitName");
			excludedFieldSet.add("styleCode");
			excludedFieldSet.add("balanceTime");
			excludedFieldSet.add("createTime");
			excludedFieldSet.add("remark");

			excludedFieldSet.add("repairStatus");
			excludedFieldSet.add("checkStatus");
			excludedFieldSet.add("repairId");
			excludedFieldSet.add("logisticdeleteflag");

			excludedFieldSet.add("shopId");
			excludedFieldSet.add("companyId");

			excludedFieldSet.add("balanceNumeber");
			excludedFieldSet.add("balanceId");
			excludedFieldSet.add("balanceStatus");
			excludedFieldSet.add("lineOrderCode");
			excludedFieldSet.add("matterCode");
			excludedFieldSet.add("orderRepairCode");
//			excludedFieldSet.add("orderCharacter");
			excludedFieldSet.add("goodsName");
			excludedFieldSet.add("goodsCode");
			excludedFieldSet.add("goodsColor");
			excludedFieldSet.add("orderRepairStatus");
			excludedFieldSet.add("companyName");
			excludedFieldSet.add("tenantId");
			excludedFieldSet.add("tenantName");
			excludedFieldSet.add("payer");
			excludedFieldSet.add("paymentDays");

			excludedFieldSet.add("repairAuditTime");
			excludedFieldSet.add("categoryName");
			excludedFieldSet.add("frequency");
			excludedFieldSet.add("publishers");
			excludedFieldSet.add("orderSendTime");
			excludedFieldSet.add("days");


			out = response.getOutputStream();
			ExportExcelUtil.exportExcel(0, 0, 0, "结算单明细", headers,
					ExportExcelUtil.buildCustomizedExportedModel(repairBalanceModels, excludedFieldSet), out,
					"yyyy-MM-dd");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
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

	// 查询打印Excel数据
	public Pagination<BalanceModel> findListByBalanceidForExcel(String id) throws ParseException {

		Pagination<BalanceModel> pagination = new Pagination<BalanceModel>();
		if (StringUtils.isNotBlank(id)) {
			StringBuilder sql = new StringBuilder();
			List<BalanceModel> lrbm = new ArrayList<>();
			sql.append(
					"SELECT t_order_repair.c_city city , " +
							"t_order_repair.c_shop_name shopname , " +
							"t_order_repair.c_customer_name customername ," +
							"t_order_repair.c_trimming_json repairproject ," +
							"t_order_repair.c_goods_name goodsname ," +
							"t_order_repair.c_num number ," +
							"t_order_repair.c_goods_color color ," +
							"t_order_repair.c_order_repair_code repaircode ," +
							"t_order_repair.c_order_code ordercode ," +
							"t_balance_repair_relation.c_unit_price unitprice ," +
							"t_balance_repair_relation.c_amount amount ," +
							"logistics.send_time sendtime ," +
							"logistics.deliverytime expresstime ," +
							"logistics.expressnumber expressnumber," +
							"mrppd.c_repair_feedback ," +
							"tod.c_goods_sn," +
							"mop.c_prod_factory_id,t_order_repair.c_order_character   "
							+ "FROM t_balance  left join t_balance_repair_relation   on t_balance.c_id=t_balance_repair_relation.c_balance_id   "
							+ "left join t_order_repair  on t_order_repair.c_id=t_balance_repair_relation.c_repair_id   "
					        + "left join mes_repair_production_plan_detail mrppd on t_order_repair.c_id=mrppd.c_order_repair_id "
							+ "left join (select orderrepair.c_id, MAX(CASE logistic.c_logistic_type WHEN '0' THEN logistic.c_send_time ELSE null END )  'send_time',MAX(CASE logistic.c_logistic_type WHEN '1' THEN logistic.c_delivery_time ELSE null END ) 'deliverytime' ,MAX(CASE logistic.c_logistic_type WHEN '0' THEN logistic.c_express_number ELSE null END ) 'expressnumber'  "
							+ "From t_order_repair orderrepair   "
							+ "left join t_logistic_order logisticorder on orderrepair.c_id=logisticorder.c_order_repair_id   "
							+ "left join t_logistic logistic on logisticorder.c_logistic_id=logistic.c_id  "
							+ "where 1=1 and logistic.c_delete_flag=0   group by orderrepair.c_id  ) "
							+ "logistics on t_order_repair.c_id=logistics.c_id  " + "" +
							"  LEFT JOIN t_order_detail tod ON t_order_repair.c_order_detail_id= tod.c_id " +
							"  LEFT JOIN mes_order_plan_detail mopd ON tod.c_id = mopd.c_order_detail_id " +
							"  LEFT JOIN mes_order_plan mop ON mopd.c_order_plan_id = mop.c_id "+
							"where 1=1 ");// 得到导出Excel的数据
			if (id != null && !id.isEmpty()) {
				sql.append(" and t_balance.c_id='").append(id).append("' ");
			}
			sql.append(
					" order by convert(t_order_repair.c_shop_name using gbk),convert(t_order_repair.c_customer_name using gbk)");

			List resultSet = dao.queryBySql(sql.toString());
			Integer num = 0;// 设置序号
			if (resultSet != null && resultSet.size() > 0) {
				for (Object result : resultSet) {// 便利查询出来的数据
					num++;// 自增
					BalanceModel rbm = new BalanceModel();
					Object[] properties = (Object[]) result;
					rbm.setNum(num.toString());
					rbm.setCity(properties[0] == null ? "" : properties[0].toString());
					rbm.setShopName(properties[1] == null ? "" : properties[1].toString());
					rbm.setCustomerName(properties[2] == null ? "" : properties[2].toString());
//					rbm.setRepairProject(replaceStr(properties[3] == null ? "" : properties[3].toString()));
					//rbm.setRepairProject(properties[3] == null ? "" : properties[3].toString());
					rbm.setRepairProject(properties[14] == null ? "" : properties[14].toString());
					rbm.setStyleName(properties[4] == null ? "" : properties[4].toString());
					rbm.setNumber(properties[5] == null ? "" : properties[5].toString());
					rbm.setColor(properties[6] == null ? "" : properties[6].toString());
					rbm.setRepairCode(properties[7] == null ? "" : properties[7].toString());
					rbm.setOrderCode(properties[8] == null ? "" : properties[8].toString());
					rbm.setUnitPrice(properties[9] == null ? "" : properties[9].toString());
					rbm.setAmount(properties[10] == null ? "" : properties[10].toString());
					rbm.setSendTime(formatDate(properties[11] == null ? "" : properties[11].toString()));
					rbm.setExpressTime(formatDate(properties[12] == null ? "" : properties[12].toString()));
					rbm.setExpressNumber(properties[13] == null ? "" : properties[13].toString());
					rbm.setGoodsSn(properties[15] == null ? "" : properties[15].toString());
					rbm.setProdFactory(properties[16] == null ? "" : properties[16].toString());
					rbm.setOrderCharacter(properties[17] == null ? "" : properties[17].toString());
					lrbm.add(rbm);

				}

			}
			pagination.setRows(lrbm);
			pagination.setTotal(lrbm.size());
			return pagination;
		}

		return pagination;
	}

	/*
	 * 微调结算单明细
	 */
	@Override
	public Pagination<BalanceModel> listDetail(BalanceDto dto, PageBean pageBean) {
		if (pageBean.getPage() == null) {
			pageBean.setPage(1);
		}
		if (pageBean.getRows() == null) {
			pageBean.setRows(9999);
		}
		List<BalanceModel> lrbm = new ArrayList<>();
		Pagination<BalanceModel> pagination = new Pagination<>();
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT distinct balance.c_id,orderrepair.c_id orderrepairid,	orderrepair.c_city,	orderrepair.c_shop_name,company.c_dept_name,orderrepair.c_customer_name,orderrepair.c_trimming_json,orderrepair.c_goods_name, "
						+ "orderrepair.c_goods_code,orderrepair.c_goods_color,orderrepair.c_num,orderrepair.c_order_repair_code,balance.c_balance_numeber,balance.c_balance_time,balance.c_balance_status,orderrepair.c_order_repair_status, "
						+ "balancerepair.c_line_order_code,balancerepair.c_matter_code,balancerepair.c_unit_price,balancerepair.c_amount,logistics.c_send_time,orderrepair.c_order_code, orderrepair.c_character ,tod.c_goods_sn,mop.c_prod_factory_id,tenant.c_tenant_name "
						+ "from t_balance  balance  "
						+ "left join t_balance_repair_relation   balancerepair on  balance.c_id=balancerepair.c_balance_id  "
						+ "left join t_order_repair  orderrepair on balancerepair.c_repair_id=orderrepair.c_id "
						+" left join t_dept company on balance.c_company_id = company.c_id and company.c_dept_type='2' "
						+" left join t_tenant tenant on balance.c_tenant_id = tenant.c_id  "
						+ "left join  (select logistic.c_send_time,logistic.c_id,logisticorder.c_order_repair_id,logistic.c_logistic_type  from t_logistic_order logisticorder left join t_logistic logistic on logisticorder.c_logistic_id=logistic.c_id where logistic.c_logistic_type='0' and  logistic.c_delete_flag=0) logistics on logistics.c_order_repair_id=orderrepair.c_id  "+
				        "  LEFT JOIN t_order_detail tod ON orderrepair.c_order_detail_id= tod.c_id " +
						"  LEFT JOIN mes_order_plan_detail mopd ON tod.c_id = mopd.c_order_detail_id " +
						"  LEFT JOIN mes_order_plan mop ON mopd.c_order_plan_id = mop.c_id "
						+ "where  1=1 ");

		/**
		 * 城市分公司查询
		 */
		if (dto.getCompanyId() != null && !dto.getCompanyId().isEmpty()) {
			sql.append(" and balance.c_company_id ='").append(dto.getCompanyId()).append("'");
		}
		/**
		 * 城市
		 */
		if (dto.getCity() != null && !dto.getCity().isEmpty()) {
			sql.append("  and orderrepair.c_city like '%").append(dto.getCity()).append("%'");
		}
		/**
		 * 发货起止时间
		 */
		if (dto.getStartTime() != null && !dto.getStartTime().isEmpty() && dto.getEndTime() != null
				&& !dto.getEndTime().isEmpty()) {// 添加同时包含起始订单日期和截止订单日期的限定条件
			sql.append(" and logistics.c_send_time between '").append(dto.getStartTime()).append(" 00:00:00'")
					.append(" and '").append(dto.getEndTime()).append(" 23:59:59'");
		} else if (dto.getStartTime() != null && !dto.getStartTime().isEmpty()
				&& (dto.getEndTime() == null || dto.getEndTime().isEmpty())) {// 添加只包含起始订单日期而不包含截止订单日期的限定条件
			sql.append(" and  logistics.c_send_time like '%").append(dto.getStartTime()).append(" %'");
		} else if ((dto.getStartTime() == null || dto.getStartTime().isEmpty()) && dto.getEndTime() != null
				&& !dto.getEndTime().isEmpty()) {// 添加只包含截止订单日期而不包含起始订单日期的限定条件
			sql.append(" and  logistics.c_send_time like '%").append(dto.getEndTime()).append(" %'");
		}
		/**
		 * 结算时间
		 */
		if (dto.getBalanceStartTime() != null && !dto.getBalanceStartTime().isEmpty() && dto.getBalanceEndTime() != null
				&& !dto.getBalanceEndTime().isEmpty()) {// 添加同时包含起始订单日期和截止订单日期的限定条件
			sql.append(" and balance.c_balance_time between '").append(dto.getBalanceStartTime()).append(" 00:00:00'")
					.append(" and '").append(dto.getBalanceEndTime()).append(" 23:59:59'");
		} else if (dto.getBalanceStartTime() != null && !dto.getBalanceStartTime().isEmpty()
				&& (dto.getBalanceEndTime() == null || dto.getBalanceEndTime().isEmpty())) {// 添加只包含起始订单日期而不包含截止订单日期的限定条件
			sql.append(" and balance.c_balance_time between '").append(dto.getBalanceStartTime()).append(" 00:00:00'")
					.append(" and '").append(dto.getBalanceStartTime()).append(" 23:59:59'");
		} else if ((dto.getBalanceStartTime() == null || dto.getBalanceStartTime().isEmpty())
				&& dto.getBalanceEndTime() != null && !dto.getBalanceEndTime().isEmpty()) {// 添加只包含截止订单日期而不包含起始订单日期的限定条件
			sql.append(" and balance.c_balance_time  between '").append(dto.getBalanceEndTime()).append(" 00:00:00'")
					.append(" and '").append(dto.getBalanceEndTime()).append(" 23:59:59'");
		}
		/**
		 * 门店
		 */
		if (dto.getShopId() != null && !dto.getShopId().isEmpty()) {
			sql.append(" and  orderrepair.c_shop_id = '").append(dto.getShopId()).append("'");
		}
		/**
		 * 微调项目
		 * 
		 * if (dto.getRepairProject() != null && !dto.getRepairProject().isEmpty()) {
		 * sql.append( " and repairproject like '%"
		 * ).append(dto.getRepairProject()).append("%'"); }
		 */
		/**
		 * 结算单id
		 */
		if (dto.getBalanceId() != null && !dto.getBalanceId().isEmpty()) {

			sql.append(" and balance.c_id = '").append(dto.getBalanceId()).append("'");
		}
		/**
		 * 微调单号
		 */
		if (dto.getRepairCode() != null && !dto.getRepairCode().isEmpty()) {
			sql.append(" and orderrepair.c_order_repair_code like '%").append(dto.getRepairCode()).append("%'");
		}
		/**
		 * 顾客姓名
		 */
		if (dto.getCustomerName() != null && !dto.getCustomerName().isEmpty()) {
			sql.append(" and orderrepair.c_customer_name like '%").append(dto.getCustomerName()).append("%'");
		}
		/**
		 * 结算单号
		 */
		if (dto.getBalanceCode() != null && !dto.getBalanceCode().isEmpty()) {
			sql.append(" and balance.c_balance_numeber like '%").append(dto.getBalanceCode()).append("%'");
		}
		/**
		 * 孔雀订单号
		 */
		if (dto.getOrderCode() != null && !dto.getOrderCode().isEmpty()) {
			sql.append(" and orderrepair.c_order_code like '%").append(dto.getOrderCode()).append("%'");
		}
		/**
		 * 物料编码
		 */
		if (dto.getMatterCode() != null && !dto.getMatterCode().isEmpty()) {
			sql.append(" and balancerepair.c_matter_code like '%").append(dto.getMatterCode()).append("%'");
		}
		/**
		 * 线下订单号
		 */
		if (dto.getLineOrderCode() != null && !dto.getLineOrderCode().isEmpty()) {
			sql.append(" and balancerepair.c_line_order_code like '%").append(dto.getLineOrderCode()).append("%'");
		}
		/**
		 * 微调单财务状态
		 */
		if (dto.getRepairCheckStatus() != null && !dto.getRepairCheckStatus().isEmpty()) {
			sql.append(" and orderrepair.c_order_repair_status = '").append(dto.getRepairStatus()).append("'");
		}
		/**
		 * 结算凭证结算状态
		 */
		if (dto.getBalanceStatus() != null && !dto.getBalanceStatus().isEmpty()) {
			sql.append(" and balance.c_balance_status = '").append(dto.getBalanceStatus()).append("'");
		}
		int total = dao.queryBySql(sql.toString()).size();
		pagination.setTotal(total);
		if (pageBean.getPage() != null && pageBean.getRows() != null) {
			sql.append(" limit " + (pageBean.getPage() - 1) * pageBean.getRows() + "," + pageBean.getRows());
		}
		List resultSet = dao.queryBySql(sql.toString());
		if (resultSet != null && resultSet.size() > 0) {
			for (Object result : resultSet) {
				BalanceModel rbm = new BalanceModel();
				Object[] properties = (Object[]) result;
				rbm.setBalanceId(properties[0] == null ? "" : properties[0].toString());
				rbm.setRepairId(properties[1] == null ? "" : properties[1].toString());
				rbm.setCity(properties[2] == null ? "" : properties[2].toString());
				rbm.setShopName(properties[3] == null ? "" : properties[3].toString());
				rbm.setCompanyName(properties[4] == null ? "" : properties[4].toString());
				rbm.setCustomerName(properties[5] == null ? "" : properties[5].toString());
//				rbm.setRepairProject(replaceStr(properties[6] == null ? "" : properties[6].toString()));
				//rbm.setRepairProject(properties[6] == null ? "" : properties[6].toString());
				rbm.setRepairProject("");
				rbm.setGoodsName(properties[7] == null ? "" : properties[7].toString());
				rbm.setGoodsCode(properties[8] == null ? "" : properties[8].toString());
				rbm.setColor(properties[9] == null ? "" : properties[9].toString());
				rbm.setNum(properties[10] == null ? "" : properties[10].toString());
				rbm.setOrderRepairCode(properties[11] == null ? "" : properties[11].toString());
				rbm.setBalanceNumeber(properties[12] == null ? "" : properties[12].toString());
				rbm.setBalanceTime(formatDate(properties[13] == null ? "" : properties[13].toString()));
				rbm.setBalanceStatus(properties[14] == null ? "" : properties[14].toString());
				rbm.setOrderRepairStatus(properties[15] == null ? "" : properties[15].toString());
				rbm.setLineOrderCode(properties[16] == null ? "" : properties[16].toString());
				rbm.setMatterCode(properties[17] == null ? "" : properties[17].toString());
				rbm.setUnitPrice(properties[18] == null ? "" : properties[18].toString());
				rbm.setAmount(properties[19] == null ? "" : properties[19].toString());
				rbm.setSendTime(formatDate(properties[20] == null ? "" : properties[20].toString()));
				rbm.setOrderCode(properties[21] == null ? "" : properties[21].toString());
				rbm.setOrderCharacter(properties[22] == null ? "" : properties[22].toString());
				rbm.setGoodsSn(properties[23] == null ? "" : properties[23].toString());
				rbm.setProdFactory(properties[24] == null ? "" : properties[24].toString());
				rbm.setTenantName(properties[25] == null ? "" : properties[25].toString());
				lrbm.add(rbm);

			}
		}
		pagination.setRows(lrbm);
		return pagination;

	}

	@Override
	public Result balanceById(String id) {
		if (StringUtils.isNotBlank(id)) {
			PageBean pageBean = new PageBean();
			Map<String, Object> map = new HashMap<String, Object>();
			Balance balance = dao.findById(Balance.class, id);
			BalanceDto dto = new BalanceDto();
			dto.setBalanceId(id);
			Pagination<BalanceModel> pagination = listDetail(dto, pageBean);
			if (balance != null) {
				map.put("balance", balance);
				if (pagination != null) {
					map.put("listDetail", pagination.getRows());
				}
				return new Result(map);
			} else {
				return new Result(Constants.RESULT_CODE.SYS_ERROR, "没有此结算单！");
			}

		}
		return new Result(Constants.RESULT_CODE.SYS_ERROR, "没有此结算单！");
	}


	@Override
	public Pagination<OrderRepairModel> listRepair(OrderRepairDto dto, PageBean pageBean) {
		Pagination<OrderRepairModel> pagination = new Pagination<OrderRepairModel>();
		if (pageBean.getPage() == null) {
			pageBean.setPage(1);
		}
		if (pageBean.getRows() == null) {
			pageBean.setRows(9999);
		}
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT DISTINCT a.c_id,a.c_city,c.company_name,a.c_shop_name,"
				+"a.c_order_repair_code,a.c_customer_name, a.c_goods_code,a.c_goods_name,"
				+"a.c_goods_color,a.c_num,f.c_send_time,b.c_goods_sn,a.c_order_repair_status, "
				+"a.c_order_code, a.c_character,c.tenant_name "
				+"FROM t_order_repair  a  "
				+" left join t_order_detail b on a.c_order_detail_id = b.c_id "
				+" join ( "
				+" select shop.c_id shop_id,shop.c_dept_code as shop_code,shop.c_dept_name as shop_name,company.c_id company_id,company.c_dept_code as company_code,company.c_dept_name as company_name,shop.c_tenant_id,t.c_tenant_name tenant_name "
				+" from t_dept shop "
				+" left join t_dept company on shop.c_parent_id = company.c_id and company.c_dept_type='2' " +
				" join t_tenant t on shop.c_tenant_id = t.c_id "
				+" where shop.c_dept_type='1' ) c on a.c_shop_id = c.shop_id "
				+" left join t_logistic_order e on a.c_id = e.c_order_repair_id left join t_logistic f on e.c_logistic_id=f.c_id  "
				+" where a.c_order_repair_status='7' and f.c_delete_flag='0' and a.c_del='0' and e.c_delete_flag='0' and f.c_send_time is not null and a.c_finance_check_status='0'");
		if (StringUtils.isNotBlank(dto.getOrderRepairCode())) {
			sql.append(" and a.c_order_repair_code ='" + dto.getOrderRepairCode() + "' ");
		}
		if (StringUtils.isNotBlank(dto.getCustomerInfo())) {
			sql.append(" and ((a.c_customer_name like '%" + dto.getCustomerInfo()
					+ "%')  or  (a.c_customer_code like '%" + dto.getCustomerInfo() + "%')) ");
		}
		if (StringUtils.isNotBlank(dto.getCity())) {
			sql.append(" and a.c_city ='" + dto.getCity() + "' ");
		}
		if (StringUtils.isNotBlank(dto.getShopId())) {
			sql.append(" and a.c_shop_id ='" + dto.getShopId() + "' ");
		}
		if (StringUtils.isNotBlank(dto.getCompanyId())) {
			sql.append(" and c.company_id ='" + dto.getCompanyId() + "' ");
		}
		if (StringUtils.isNotBlank(dto.getTenantId())) {
			sql.append(" and c.c_tenant_id ='" + dto.getTenantId() + "' ");
		}
		// 发货时间
		if (dto.getSendStartTime() != null && !dto.getSendStartTime().isEmpty() && dto.getSendEndTime() != null
				&& !dto.getSendEndTime().isEmpty()) {// 添加同时包含起始订单日期和截止订单日期的限定条件
			sql.append(" and f.c_send_time between '").append(dto.getSendStartTime()).append(" 00:00:00'")
					.append(" and '").append(dto.getSendEndTime()).append(" 23:59:59'");
		} else if (dto.getSendStartTime() != null && !dto.getSendStartTime().isEmpty()
				&& (dto.getSendEndTime() == null || dto.getSendEndTime().isEmpty())) {// 添加只包含起始订单日期而不包含截止订单日期的限定条件
			sql.append(" and f.c_send_time between '").append(dto.getSendStartTime()).append(" 00:00:00'")
					.append(" and '").append(dto.getSendStartTime()).append(" 23:59:59'");
		} else if ((dto.getSendStartTime() == null || dto.getSendStartTime().isEmpty()) && dto.getSendEndTime() != null
				&& !dto.getSendEndTime().isEmpty()) {// 添加只包含截止订单日期而不包含起始订单日期的限定条件
			sql.append(" and f.c_send_time  between '").append(dto.getSendEndTime()).append(" 00:00:00'")
					.append(" and '").append(dto.getSendEndTime()).append(" 23:59:59'");
		}
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
				rbm.setCity(properties[1] == null ? "" : properties[1].toString());
				rbm.setCompanyName(properties[2] == null ? "" : properties[2].toString());
				rbm.setShopName(properties[3] == null ? "" : properties[3].toString());
				rbm.setOrderRepairCode(properties[4] == null ? "" : properties[4].toString());
				rbm.setCustomerName(properties[5] == null ? "" : properties[5].toString());
				rbm.setGoodsCode(properties[6] == null ? "" : properties[6].toString());
				rbm.setGoodsName(properties[7] == null ? "" : properties[7].toString());
				rbm.setGoodsColor(properties[8] == null ? "" : properties[8].toString());
				rbm.setNum(properties[9] == null ? "" : properties[9].toString());
				rbm.setSendTime(properties[10] == null ? "" : properties[10].toString());
				rbm.setGoodsSn(properties[11] == null ? "" : properties[11].toString());
				rbm.setOrderRepairStatus(properties[12] == null ? "" : properties[12].toString());
				rbm.setOrderCode(properties[13] == null ? "" : properties[13].toString());
				rbm.setOrderCharacter(properties[14] == null ? "" : properties[14].toString());
				rbm.setTenantName(properties[15] == null ? "" : properties[15].toString());
				lrbm.add(rbm);
			}
		}
		//判断该微调单是否已经结算，如果已结算则删掉该微调单
//		for (int i = 0; i < lrbm.size(); i++) {
//			BalanceRepairRelation relation = dao.findUniqueByProperty(BalanceRepairRelation.class, "repairId", lrbm.get(i).getId());
//			if(relation!=null){
//				lrbm.remove(lrbm.get(i));
//			}
//		}
		pagination.setRows(lrbm);
		return pagination;
	}

	@Override
	public Pagination<BalanceModel> find(BalanceDto dto, PageBean pageBean) {
		Pagination<BalanceModel> pagination = new Pagination<BalanceModel>();
		StringBuilder sqlSelect = new StringBuilder();
		StringBuilder sqlCount = new StringBuilder();
		StringBuilder sqlFrom  = new StringBuilder();
		StringBuilder sqlWhere = new StringBuilder();
		StringBuilder sqlLimit = new StringBuilder();
		sqlCount.append(" SELECT COUNT(1) ");
		sqlSelect.append(" SELECT tenant.c_tenant_name,company.c_dept_name company_name,shop.c_dept_name shop_name,balance.c_payment_days,balance.c_balance_numeber,balance.c_balance_status" +
				"  ,balance.c_balance_time,balanceRepair.c_amount,orepair.c_order_repair_code,orepair.c_check_time,repairSend.c_send_time repari_send_time,orepair.c_customer_name" +
				"  ,orepair.c_goods_code,orepair.c_goods_name,orepair.c_goods_color,category.c_name category_name,orepair.c_order_repair_status,orepair.c_frequency,torder.c_order_code" +
				"  ,orderDetail.c_goods_sn,orepair.c_order_character,plan.c_prod_factory_id,plan.publishers,orderSend.c_send_time order_send_time ,IFNULL(TO_DAYS(orepair.c_check_time) - TO_DAYS(orderSend.c_send_time),0) AS days,orepair.c_num,balanceRepair.c_unit_price ");
		sqlFrom.append(" FROM (((((((((((((t_balance balance" +
				"  JOIN t_balance_repair_relation balanceRepair ON (balance.c_id=balanceRepair.c_balance_id))" +
				"  LEFT JOIN t_tenant tenant ON (balance.c_tenant_id = tenant.c_id))" +
				"  LEFT JOIN t_dept company ON (balance.c_company_id = company.c_id))" +
				"  LEFT JOIN t_order_repair orepair ON (balanceRepair.c_repair_id = orepair.c_id))" +
				"  LEFT JOIN (SELECT logistic.c_send_time,logisticOrder.c_order_repair_id FROM t_logistic logistic,t_logistic_order logisticOrder WHERE logistic.c_id = logisticOrder.c_logistic_id AND logisticOrder.c_delete_flag='0' AND logistic.c_delete_flag='0' AND logistic.c_logistic_type='0') repairSend ON (orepair.c_id = repairSend.c_order_repair_id))" +
				"  LEFT JOIN t_dept shop ON (orepair.c_shop_id = shop.c_id))" +
				"  LEFT JOIN t_goods goods ON (orepair.c_goods_code = goods.c_code))" +
				"  LEFT JOIN t_category category ON (goods.c_category_id = category.c_id))" +
				"  LEFT JOIN t_order_detail orderDetail ON (orepair.c_order_detail_id = orderDetail.c_id))" +
				"  LEFT JOIN t_order torder ON (orderDetail.c_order_id = torder.c_id))" +
				"  LEFT JOIN mes_order_plan_detail planDetail ON (orderDetail.c_id = planDetail.c_order_detail_id AND planDetail.c_delete_flag=0))" +
				"  LEFT JOIN mes_order_plan plan ON (planDetail.c_order_plan_id = plan.c_id AND plan.c_delete_flag=0))" +
				"  LEFT JOIN (SELECT logistic.c_send_time,logisticOrder.c_order_detail_id FROM t_logistic logistic,t_logistic_order logisticOrder WHERE logistic.c_id = logisticOrder.c_logistic_id AND logisticOrder.c_delete_flag='0' AND logistic.c_delete_flag='0' AND logistic.c_logistic_type='0') orderSend ON (orepair.c_order_detail_id = orderSend.c_order_detail_id))");

		sqlWhere.append(" WHERE 1=1");
		//商户
		if (StringUtils.isNotBlank(dto.getTenantId())){
			sqlWhere.append(" AND balance.c_tenant_id='").append(dto.getTenantId()).append("'");
		}
		//公司
		if(StringUtils.isNotBlank(dto.getCompanyId())){
			sqlWhere.append(" AND balance.c_company_id='").append(dto.getCompanyId()).append("'");
		}

		//门店
		if (StringUtils.isNotBlank(dto.getShopId())){
			sqlWhere.append(" AND orepair.c_shop_id ='").append(dto.getShopId()).append("'");
		}
		//账期
		if (StringUtils.isNotBlank(dto.getPaymentDays())){
			sqlWhere.append(" AND balance.c_payment_days='").append(dto.getPaymentDays()).append("'");
		}

		//客户姓名
		if(StringUtils.isNotBlank(dto.getCustomerName())){
			sqlWhere.append(" AND orepair.c_customer_name LIKE '%").append(dto.getCustomerName()).append("%'");
		}
		//结算单号
		if(StringUtils.isNotBlank(dto.getBalanceCode())){
			sqlWhere.append(" AND balance.c_balance_numeber LIKE '%").append(dto.getBalanceCode()).append("%'");
		}
		//状态
		if(dto.getBalanceStatuses()!=null&&dto.getBalanceStatuses().length>0){
			String statuses="";
			for(int i=0;i<dto.getBalanceStatuses().length;i++){
				statuses+="'"+dto.getBalanceStatuses()[i]+"'";
				if (i != dto.getBalanceStatuses().length - 1){
					statuses+=",";
				}
			}
			sqlWhere.append(" AND balance.c_balance_status in (").append(statuses).append(")");
		}
		//订单性质
		if(StringUtils.isNotBlank(dto.getOrderCharacteres())){
			sqlWhere.append(" AND  orepair.c_order_character='").append(dto.getOrderCharacteres()).append("'");
		}

		//结算日期
		if (StringUtils.isNotBlank(dto.getBalanceStartTime())){
			sqlWhere.append(" AND balance.c_balance_time >='").append(dto.getBalanceStartTime()).append(" 00:00:00'");
		}
		if(StringUtils.isNotBlank(dto.getBalanceEndTime())){
			sqlWhere.append(" AND balance.c_balance_time <='").append(dto.getBalanceEndTime()).append(" 23:59:59'");
		}

		//微调发货日期
		if(StringUtils.isNotBlank(dto.getRepairSendStartTime())){
			sqlWhere.append(" AND repairSend.c_send_time >='").append(dto.getRepairSendStartTime()).append(" 00:00:00'");
		}
		if(StringUtils.isNotBlank(dto.getRepairSendEndTime())){
			sqlWhere.append(" AND repairSend.c_send_time <='").append(dto.getRepairSendEndTime()).append(" 23:59:59'");
		}

		//时长
		if(StringUtils.isNotBlank(dto.getStartDays())){
			sqlWhere.append(" AND IFNULL(TO_DAYS(orepair.c_check_time) - TO_DAYS(orderSend.c_send_time),0) >=").append(dto.getStartDays());
		}
		if(StringUtils.isNotBlank(dto.getEndDays())){
			sqlWhere.append(" AND IFNULL(TO_DAYS(orepair.c_check_time) - TO_DAYS(orderSend.c_send_time),0) <=").append(dto.getEndDays());
		}
		//结算金额
		if(StringUtils.isNotBlank(dto.getSettlementAmountStart())){
			sqlWhere.append(" AND balanceRepair.c_amount >=").append(dto.getSettlementAmountStart());
		}
		if(StringUtils.isNotBlank(dto.getSettlementAmountEnd())){
			sqlWhere.append(" AND balanceRepair.c_amount<=").append(dto.getSettlementAmountEnd());
		}
		if(StringUtils.isNotBlank(dto.getGoodsSn())){
			sqlWhere.append(" AND orderDetail.c_goods_sn='").append(dto.getGoodsSn()).append("'");
		}
		if (pageBean.getPage() != null &&pageBean.getRows() != null) {
			sqlLimit.append(" LIMIT ").append(pageBean.getPage()).append(",").append(pageBean.getRows());
		}

		List resultSet = dao.queryBySql(sqlSelect.append(sqlFrom).append(sqlWhere).append(sqlLimit).toString());
		List<BalanceModel> list = new ArrayList<>();
		if (resultSet != null && resultSet.size() > 0) {
			for (Object result : resultSet) {
				BalanceModel model = new BalanceModel();
				Object[] properties = (Object[]) result;
				model.setTenantName(properties[0] == null ? "" : properties[0].toString());
				model.setCompanyName(properties[1] == null ? "" : properties[1].toString());
				model.setShopName(properties[2] == null ? "" : properties[2].toString());
				model.setPaymentDays(properties[3] == null ? "" : properties[3].toString());
				model.setBalanceNumeber(properties[4] == null ? "" : properties[4].toString());
				model.setBalanceStatus(properties[5] == null ? "" : Status.getBalanceStatusName(properties[5]));
				model.setBalanceTime(properties[6] == null ? "" : properties[6].toString());
				model.setAmount(properties[7] == null ? "0" : properties[7].toString());
				model.setRepairCode(properties[8] == null ? "" : properties[8].toString());
				model.setRepairAuditTime(properties[9] == null ? "" : properties[9].toString());
				model.setSendTime(properties[10] == null ? "" : properties[10].toString());
				model.setCustomerName(properties[11] == null ? "" : properties[11].toString());
				model.setGoodsCode(properties[12] == null ? "" : properties[12].toString());
				model.setGoodsName(properties[13] == null ? "" : properties[13].toString());
				model.setGoodsColor(properties[14] == null ? "0" : properties[14].toString());
				model.setCategoryName(properties[15] == null ? "" : properties[15].toString());
				model.setRepairStatus(properties[16] == null ? "" : Status.getRepairStatusName(properties[16]));
				model.setFrequency(properties[17] == null ? "" : properties[17].toString());
				model.setOrderCode(properties[18] == null ? "" : properties[18].toString());
				model.setGoodsSn(properties[19] == null ? "" : properties[19].toString());
				model.setOrderCharacter(properties[20] == null ? "" : properties[20].toString());
				model.setProdFactory(properties[21] == null ? "" : properties[21].toString());
				model.setPublishers(properties[22] == null ? "" : Status.getPublishersName(properties[22]));
				model.setOrderSendTime(properties[23] == null ? "" : properties[23].toString());
				model.setDays(properties[24] == null ? "" : properties[24].toString());
				model.setNum(properties[25] == null ? "0" : properties[25].toString());
				model.setUnitPrice(properties[26] == null ? "0" : properties[26].toString());
				list.add(model);
			}
		}

		pagination.setRows(list);

		if (pageBean.getPage() != null && pageBean.getRows() != null) {
			Long count = Long.parseLong(dao.uniqueBySql(sqlCount.append(sqlFrom).append(sqlWhere).toString()).toString());
			pagination.setTotal(count);
		}
		return pagination;
	}

	/**
	 * 客户订单结算查询
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@Override
	public Pagination<BalanceModel> orderBalance(BalanceDto dto, PageBean pageBean) {
		Pagination<BalanceModel> pagination = new Pagination<BalanceModel>();
		StringBuilder sqlSelect = new StringBuilder();
		StringBuilder sqlCount = new StringBuilder();
		StringBuilder sqlFrom  = new StringBuilder();
		StringBuilder sqlWhere = new StringBuilder();
		StringBuilder sqlGroup = new StringBuilder();
		String tenantIds="";
		sqlSelect.append("SELECT c.c_tenant_name AS tenant_name,f.c_name AS category_name,e.c_code AS goods_code,e.c_name AS goods_name,d.c_color_name AS goods_color_name,g.c_balance_price AS balance_price " +
				" ,SUM(b.c_num) AS num,SUM(b.c_num*IFNULL(g.c_balance_price,0)) AS balance_amt ");
		sqlFrom.append("FROM t_order a " +
				"JOIN t_order_detail b ON a.c_id = b.c_order_id " +
				"JOIN t_tenant c ON a.c_tenant_id = c.c_id " +
				"JOIN t_goods_detail d ON b.c_goods_detail_id = d.c_id " +
				"JOIN t_goods e ON d.c_goods_id = e.c_id " +
				"JOIN t_category f ON e.c_category_id = f.c_id " +
				"JOIN t_goods_detail_tanant_relation g ON b.c_goods_detail_id = g.c_goods_detail_id AND a.c_tenant_id = g.c_tenant_id  " +
				"JOIN t_logistic_order h ON b.c_id = h.c_order_detail_id AND h.c_delete_flag='0' " +
				"JOIN t_logistic i ON a.c_tenant_id = i.c_tenant_id AND h.c_logistic_id = i.c_id AND i.c_delete_flag='0' AND i.c_logistic_type='0' ");
		sqlWhere.append(" WHERE a.c_delete_flag ='0' AND b.c_order_detail_status='3' ");
		//商户
		if(StringUtils.isNotBlank(dto.getTenantId())){
           for(String id :dto.getTenantId().split(",")){
           	tenantIds +="'"+id+"',";
		   }
		   sqlWhere.append(" AND a.c_tenant_id in (").append(tenantIds.substring(0,tenantIds.length() - 1)).append(")");
		}

		// 发货时间
		if (StringUtils.isNotBlank(dto.getSendStartTime()) && StringUtils.isNotBlank(dto.getSendEndTime())) {
			sqlWhere.append(" AND i.c_send_time between '").append(dto.getSendStartTime()).append(" 00:00:00'")
					.append(" AND '").append(dto.getSendEndTime()).append(" 23:59:59'");
		} else if (StringUtils.isNotBlank(dto.getSendStartTime()) && StringUtils.isBlank(dto.getSendEndTime())) {
			sqlWhere.append(" and i.c_send_time between '").append(dto.getSendStartTime()).append(" 00:00:00'")
					.append(" AND '").append(dto.getSendStartTime()).append(" 23:59:59'");
		} else if (StringUtils.isBlank(dto.getSendStartTime()) && StringUtils.isNotBlank(dto.getSendEndTime())) {
			sqlWhere.append(" AND i.c_send_time  between '").append(dto.getSendEndTime()).append(" 00:00:00'")
					.append(" and '").append(dto.getSendEndTime()).append(" 23:59:59'");
		}
		/**
		 * 类别
		 */
		if(StringUtils.isNotBlank(dto.getCategoryId())){
			sqlWhere.append(" AND f.c_id='").append(dto.getCategoryId()).append("'");
		}

		sqlGroup.append(" GROUP BY c.c_tenant_name ,f.c_name ,e.c_code ,e.c_name ,d.c_color_name ,g.c_balance_price ");


		List resultSet = dao.queryBySql(sqlSelect.append(sqlFrom).append(sqlWhere).append(sqlGroup).toString());
		List<BalanceModel> list = new ArrayList<>();
		if (resultSet != null && resultSet.size() > 0) {
			for (Object result : resultSet) {
				BalanceModel model = new BalanceModel();
				Object[] properties = (Object[]) result;
				model.setTenantName(properties[0] == null ? "" : properties[0].toString());
				model.setCategoryName(properties[1] == null ? "" : properties[1].toString());
				model.setGoodsCode(properties[2] == null ? "" : properties[2].toString());
				model.setGoodsName(properties[3] == null ? "" : properties[3].toString());
				model.setGoodsColor(properties[4] == null ? "" : properties[4].toString());
				model.setUnitPrice(properties[5] == null ? "0" : properties[5].toString());
				model.setNum(properties[6] == null ? "0" : properties[6].toString());
				model.setAmount(properties[7] == null ? "0" : properties[7].toString());
				list.add(model);
			}
		}

		pagination.setRows(list);

//		if (pageBean.getPage() != null && pageBean.getRows() != null) {
//			Long count = Long.parseLong(dao.uniqueBySql(sqlCount.append(sqlFrom).append(sqlWhere).toString()).toString());
//			pagination.setTotal(count);
//		}
		return pagination;
	}

	private String formatDate(String time) {
		if(time == null || time == ""){
			return "";
		}else{
			String newTime = null;
			if (StringUtils.isNotBlank(time)) {
				newTime = time.substring(0, 19);
			}
			return newTime;
		}
	}

}
