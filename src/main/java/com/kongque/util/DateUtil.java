package com.kongque.util;

import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.FastDateFormat;
import org.apache.commons.lang3.time.DateUtils;

public class DateUtil {
	public static final String DEFAULT_MAX_PATTERN = "yyyy-MM-dd 23:59:59";
	public static final String DEFAULT_MIN_PATTERN = "yyyy-MM-dd 00:00:00";
	/**
	 * 按指定格式格式化日期
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date, String pattern){
		if (date==null){
			return null;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}


	public static Date minDate(Date date)  {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		try {
		    return simpleDateFormat.parse(formatDate(date, "yyyyMMdd"));
		} catch (ParseException e) {
		    throw new RuntimeException(e);
		}
	}

	public static Date maxDate(Date date) {
		return DateUtils.addDays(minDate(date), 1);
	}

	/**
	 * 获取当前时间距当日最后一刻相差的毫秒数
	 * @param date
	 * @return
	 */
	public static long getTimeDifference(Date date) {
		
		return maxDate(date).getTime() - date.getTime();
	}
	public static String stringOfDateTime(Date date) {
		Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(date);
	}

	public static Date objToDate(Object o){
		if (null==o) return null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(o.toString());
		} catch (Exception e) {
			return null;
		}
	}
	public static String fastDateFormat(Object o){
		return FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(o);
	}
	public static String fastDateFormat(Object o, String pattern){
		return FastDateFormat.getInstance(pattern).format(o);
	}
	
	public static Date str2Date(String strDate,String format) {
		 SimpleDateFormat formatter = new SimpleDateFormat(format);
		 ParsePosition pos = new ParsePosition(0);
		 Date strtodate = formatter.parse(strDate, pos);
		 return strtodate;
		
	}
	
	 /**获取指定时间的下一个月
     * @param dateStr
     * @return
     */
    public static String getPreMonth(String dateStr) {
        String lastMonth = "";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM");
        int year = Integer.parseInt(dateStr.substring(0, 4));
        String monthsString = dateStr.substring(4, 6);
        int month;
        if ("0".equals(monthsString.substring(0, 1))) {
            month = Integer.parseInt(monthsString.substring(1, 2));
        } else {
            month = Integer.parseInt(monthsString.substring(0, 2));
        }
        
        cal.set(year,month,Calendar.DATE);
        lastMonth = dft.format(cal.getTime());
        return lastMonth;
    }
    
    public static Long str2Long(String strDate,String format) {
		 SimpleDateFormat formatter = new SimpleDateFormat(format);
		 ParsePosition pos = new ParsePosition(0);
		 Date strtodate = formatter.parse(strDate, pos);
		 return strtodate.getTime();
		
	}

}
