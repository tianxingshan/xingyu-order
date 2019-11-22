package com.kongque.util;

import java.util.Date;
import java.util.Random;

import org.apache.commons.lang3.*;
import org.springframework.stereotype.Component;

import com.kongque.entity.basics.Code;

@Component
public class CodeUtil {
	private static Integer styleNumber;

	@Deprecated
	public synchronized String createStyleSNtoDelete(String styleCode, Code code) {
		if (styleNumber == null) {
			styleNumber = code.getMaxValue();
		}
		Date date = new Date();
		long timestamps = DateUtil.getTimeDifference(date);
		// 在每天的23:50:50~第二天的00:00:10的20秒内，暂停下单，并于之后重置SN序号
		if (timestamps < 10 * 1000) {
			try {
				CodeUtil.class.wait(20 * 1000);
				styleNumber = 0;
				return createStyleSNtoDelete(styleCode, code);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		styleNumber += 1;
		code.setMaxValue(styleNumber);
		return DateUtil.formatDate(date, "yyMMdd") + styleCode + String.format("%0" + 6 + "d", styleNumber);
	}

	public static String createStyleSN(String styleCode, Integer maxValue, Date date) {

		return DateUtil.formatDate(date, "yyMMdd") + String.format("%0" + 6 + "d", maxValue);
	}

	/**
	 * 创建订单编号
	 * 
	 * @param number
	 * @return
	 */
	public static String createOrderCode(String number) {
		return createGeneralCode(number).insert(0, "DD").toString();
	}

	/**
	 * 创建微调编号
	 * 
	 * @param number
	 * @return
	 */
	public static String createOrderRepairCode(String number) {
		return createGeneralCode(number).insert(0, "WD").toString();
	}

	/**
	 * 创建结算单编号
	 * 
	 * @param number
	 * @return
	 */
	public static String createBalanceCode(String number) {
		return createGeneralCode(number).insert(0, "JS").toString();
	}

	/**
	 * 创建通用编码尾号
	 * 
	 * @param number
	 * @return
	 */
	private static StringBuilder createGeneralCode(String number) {
		StringBuilder sb = new StringBuilder();
		sb.append(DateUtil.formatDate(new Date(), "yyyyMMdd")).append(number);
		return sb;
	}

	/**
	 * 创建试衣编号
	 * 
	 * @param number
	 * @return
	 */
	public static String createTryOnCode(String number) {
		return createGeneralCode(number).insert(0, "SY").toString();
	}
	/**
	 * 创建结案单编号
	 * 
	 */
	public static String createClosedCode(String number) {
		return createGeneralCode(number).insert(0, "JA").toString();
	}

	/**
	 * 创建计划单编号
	 * @param number
	 * @return
	 */
	public static String createOrderPlanCode(String number) {
		return createPlanGeneralCode(number).insert(0, "PP").toString();
	}
	/**
	 * 创建计划单编码尾号
	 *
	 * @param number
	 * @return
	 */
	private static StringBuilder createPlanGeneralCode(String number) {
		StringBuilder sb = new StringBuilder();
		sb.append(DateUtil.formatDate(new Date(), "yyMMdd")).append(number);
		return sb;
	}
	
	/**
	 * 创建微调计划单编号
	 * 
	 * @param number
	 * @return
	 */
	public static String createRepairPlanCode(String number) {
		return createGeneralCode(number).insert(0, "RPP").toString();
	}
	
	/**
	 * 创建微调计划补料单编号
	 * 
	 * @param number
	 * @return
	 */
	public static String createRepairPlanSupplementCode(String number) {
		return createGeneralCode(number).insert(0, "RPPS").toString();
	}
	
	public static String createAccountOrderCode(String number) {
		return createGeneralCode(number).insert(0, "HS").toString();
	}

	/**
	 * 生成制卡编号
	 * @param prefix 前缀
	 * @param fixDigits 固定位数
	 * @return
	 */
	public static String createCardNo(String prefix,int fixDigits){
         if(org.apache.commons.lang3.StringUtils.isBlank(prefix)){
			 prefix="";
		 }
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < fixDigits; i++) {
			if (i == 0 && fixDigits > 1)
				str.append(new Random().nextInt(9) + 1);
			else
				str.append(new Random().nextInt(10));
		}
		return prefix+str.toString();
	}
}
