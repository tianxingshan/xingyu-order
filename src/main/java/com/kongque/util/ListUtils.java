package com.kongque.util;

import java.util.List;

public class ListUtils {
	public static <T> boolean isEmptyOrNull(List<T> list){
		if(list == null || list.isEmpty()){
			return true;
		}else{
			return false;
		}
	}
}
