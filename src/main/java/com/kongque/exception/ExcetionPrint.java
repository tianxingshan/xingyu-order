package com.kongque.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

/**
 * 
 * 岳辉 2017年10月12日
 */
@Component
@Order(-1)
public class ExcetionPrint extends DefaultHandlerExceptionResolver {

	Logger log = LoggerFactory.getLogger(ExcetionPrint.class);

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		log.error("系统错误uri" + request.getRequestURI(), ex);
		return super.doResolveException(request, response, handler, ex);
	}
}
