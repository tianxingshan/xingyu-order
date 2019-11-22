package com.kongque.util;

import java.util.Arrays;
import java.util.Random;

public class StringUtils extends org.apache.commons.lang3.StringUtils {
	public static String[] addStringToStringArray(String[] array,String addStr) {
		String[] newArray = array;
		if(isNotBlank(addStr)) {
			newArray = Arrays.copyOf(array, array.length+1);
			newArray[newArray.length-1] = addStr;
		}
		return newArray;
	}
	public static String isNull(String s){
		if (org.apache.commons.lang3.StringUtils.isBlank(s)){
			return "";
		}
		return s;
	}

	/**
	 * 得到固定长度的字符串
	 * @param digit 长度
	 * @return
	 */
	public static String getNum(int digit) {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < digit; i++) {
			if (i == 0 && digit > 1)
				str.append(new Random().nextInt(9) + 1);
			else
				str.append(new Random().nextInt(10));
		}
		return str.toString();
	}
}
