package com.kongque.component.impl;

import org.apache.commons.lang3.StringUtils;
import org.objectweb.util.monolog.wrapper.p6spy.P6SpyLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.p6spy.engine.logging.Category;

public class LogbackLogger extends P6SpyLogger {

	@Value("env")
	private String env;

	private static final Logger logger = LoggerFactory.getLogger("p6spyLogger");

	private static final Logger logger2 = LoggerFactory.getLogger(LogbackLogger.class);

	@Override
	public void logSQL(int connectionId, String s, long l, Category category, String s1, String sql) {

		if (logger.isInfoEnabled() && StringUtils.isNotBlank(sql)) {
			logger2.info(sql);
			if (sql.startsWith("insert") || sql.startsWith("update") || sql.startsWith("delete"))
				logger.info(sql);
		}
	}

	@Override
	public void logException(Exception e) {
		if (logger.isErrorEnabled()) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void logText(String s) {
		if (logger.isInfoEnabled()) {
			logger.info(s);
		}
	}

	@Override
	public boolean isCategoryEnabled(Category category) {
		return true;
	}

}