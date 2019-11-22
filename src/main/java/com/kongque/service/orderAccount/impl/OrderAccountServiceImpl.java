package com.kongque.service.orderAccount.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kongque.constants.Constants;
import com.kongque.dao.IDaoService;
import com.kongque.dto.OrderAccountDetailDto;
import com.kongque.dto.OrderAccountDto;
import com.kongque.entity.basics.Code;
import com.kongque.entity.order.OrderAccount;
import com.kongque.service.orderAccount.IOrderAccountDetailService;
import com.kongque.service.orderAccount.IOrderAccountService;
import com.kongque.util.CodeUtil;
import com.kongque.util.DateUtil;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;
import com.kongque.util.StringUtils;

/**
 * @author sws
 *
 */
@Service
public class OrderAccountServiceImpl implements IOrderAccountService {
	
	@Resource
	private IDaoService dao;
	
	@Autowired
	private IOrderAccountDetailService orderAccountDetailService;

	@Override
	public Pagination<OrderAccount> listPage(PageBean pageBean, OrderAccountDto dto) {
		Pagination<OrderAccount> pagination = new Pagination<>();
		Criteria criteria = dao.createCriteria(OrderAccount.class);
		
		List<OrderAccount> findListWithPagebeanCriteria = dao.findListWithPagebeanCriteria(criteria, pageBean);
		Long total = dao.findTotalWithCriteria(criteria);
		
		pagination.setRows(findListWithPagebeanCriteria);
		pagination.setTotal(total);
		
		return pagination;
	}

	@Override
	public Result saveOrUpdate(OrderAccountDto dto) {
		Date date = new Date();
		String accountCode = CodeUtil.createAccountOrderCode(getOrderAccountMaxValue());
		String accountMonth = dto.getAccountMonth();
		
		String formatDate = DateUtil.formatDate(new Date(), "yyyy-MM");
		if(accountMonth.trim().equals(formatDate)) {
			Result result = new Result();
			result.setReturnCode("500");
			result.setReturnMsg("核算失败,当月不予核算");
			return result;
		}
		
		List<OrderAccount> list = this.list(dto);
		if(list.size() == 0) {//第一次核算 -- 添加
			OrderAccount orderAccount = new OrderAccount();
			BeanUtils.copyProperties(dto, orderAccount);
			orderAccount.setAccountNumber(accountCode);
			orderAccount.setCreateTime(date);
			orderAccount.setCurrency(Constants.RMB);
			orderAccount.setUpdateTime(date);
			dao.save(orderAccount);
			// 开始核算订单+并入库明细表
			OrderAccountDetailDto detailDto = new OrderAccountDetailDto();
			detailDto.setOrderAccountId(orderAccount.getId());
			detailDto.setAccountMonth(accountMonth);
			Result accountOrderByMonth = orderAccountDetailService.accountOrderByMonth(detailDto);
			if(!accountOrderByMonth.getReturnCode().equals("200")) {//若核算失败,删除核算单主表
				dao.delete(orderAccount);
			}
			return accountOrderByMonth;
		}else {//非第一次核算 --重新核算  改: 只核算一次,后边不再核算,直接返回
			/*OrderAccount orderAccount = list.get(0);
			orderAccount.setUpdateTime(new Date());
			orderAccount.setAccountNumber(accountCode);
			dao.update(orderAccount);
			//核算明细删除,再重新核算
			OrderAccount orderAccount2 = list.get(0);
			OrderAccountDetailDto detailDto = new OrderAccountDetailDto();
			detailDto.setOrderAccountId(orderAccount2.getId());
			detailDto.setAccountMonth(accountMonth);
			orderAccountDetailService.deleteByOrderAccountId(detailDto);
			
			Result accountOrderByMonth = orderAccountDetailService.accountOrderByMonth(detailDto);*/
			
			Result result = new Result();
			result.setReturnMsg("每月只核算一次,该月已核算过,无需再次核算");
			return result;
		}
	}

	@Override
	public List<OrderAccount> list(OrderAccountDto dto) {
		Criteria criteria = dao.createCriteria(OrderAccount.class);
		if(StringUtils.isNotBlank(dto.getAccountMonth())) {
			criteria.add(Restrictions.eq("accountMonth", dto.getAccountMonth()));
		}
		@SuppressWarnings("unchecked")
		List<OrderAccount> list = criteria.list();
		return list;
	}
	
	// 获取核算单号
	public String getOrderAccountMaxValue() {
		Date date = new Date();
		Criteria criteria = dao.createCriteria(Code.class);
		criteria.add(Restrictions.between("updateTime", DateUtil.minDate(date), DateUtil.maxDate(date)));
		criteria.add(Restrictions.eq("type", "HS"));
		criteria.addOrder(org.hibernate.criterion.Order.desc("maxValue"));
		criteria.setMaxResults(1);
		Code code = (Code) criteria.uniqueResult();
		if (code == null) {
			code = new Code();
			code.setMaxValue(1);
			code.setType("HS");
			code.setUpdateTime(date);
			dao.save(code);
		} else {
			code.setMaxValue(code.getMaxValue() + 1);
		}
		return String.format("%0" + 6 + "d", code.getMaxValue());
	}

	@Override
	public Result getOrderAccountMonth() {
		Result result = new Result();
		List<Integer> allMonth = new ArrayList<>();
		
		Criteria criteria = dao.createCriteria(OrderAccount.class);
		List<OrderAccount> list = criteria.list();
		
		if(list!=null && list.size() > 0) {
			for (OrderAccount orderAccount : list) {
				String accountMonth = orderAccount.getAccountMonth();
				String replaceAll = accountMonth.replaceAll("-", "");
				allMonth.add(Integer.parseInt(replaceAll));
			}
			Integer maxMonth = Collections.max(allMonth);
			String accountMonth = DateUtil.getPreMonth(String.valueOf(maxMonth));
			result.setReturnData(accountMonth);
		}else {
			String accountMonth = "2018-10";
			result.setReturnData(accountMonth);
		}
		return result;
	}
}
