/**
 *
 */
package com.kongque.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 岳辉
 * 2017年10月24日
 */
public class SysUtil {

	public static HttpServletRequest getRequest(){
		return (((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
	}

	public static String getTokenFromRequestHeader() {
		HttpServletRequest request = getRequest();
		if(request != null) {
			String token = request.getHeader("token");
			if(StringUtils.isNotBlank(token)) {
				return token;
			}
		}
		return null;
	}
}
