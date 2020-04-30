package com.jf.game.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期工具类
 * 
 * @author 程伟平 创建时间：2012-02-08 14:23:30
 */
public class DateUtil {
	/**
	 * 一小时毫秒数
	 */
	public static final long MILLISECONDS_OF_HOUR = 60 * 60 * 1000L;

	/**
	 * 一天毫秒数
	 */
	public static final long MILLISECONDS_OF_DAY = 24 * 60 * 60 * 1000L;

	/**
	 * 日期格式：yyyy-MM-dd
	 */
	public static final String YYYYMMDD_DASH = "yyyy-MM-dd";
	
	/**
	 * 日期格式：MM-dd
	 */
	public static final String MMDD_DASH = "MM-dd";

	/**
	 * 日期时间格式：yyyy-MM-dd HH:mm:ss
	 */
	public static final String YYYYMMDD_DASH_HHMMSS_COLON = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 时间格式：HH:mm:ss
	 */
	public static final String HHMMSS_COLON = "HH:mm:ss";

	/**
	 * 日期格式：yyyyMMdd
	 */
	public static final String YYYYMMDD = "yyyyMMdd";

	/**
	 * 时间格式：HHmmss
	 */
	public static final String HHMMSS = "HHmmss";

	/**
	 * 日期时间格式：yyyyMMddHHmm （订单标号需要，年月日时分）
	 */
	public static final String YYYYMMDD_HHMM = "yyyyMMddHHmm";
	/**
	 * 日期时间格式：yyyyMMddHHmmss
	 */
	public static final String YYYYMMDD_HHMMSS = "yyyyMMddHHmmss";
	
	/**
	 * 日期时间格式：yyyyMMddHHmmssSSS 存在毫秒数
	 */
	public static final String YYYYMMDD_HHMMSS_SSS = "yyyyMMddHHmmssSSS";

	/**
	 * 日期格式：yyyy/MM/dd
	 */
	public static final String YYYYMMDD_SLASH = "yyyy/MM/dd";

	/**
	 * 日期格式：yyyy/MM/dd
	 */
	public static final String YYYYMMDD_SLASH_HHMMSS_COLON = "yyyy/MM/dd HH:mm:ss";

	/**
	 * 获取当前日期/时间字符串 默认日期时间格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @author 程伟平 创建时间：2012-02-23 15:30:30
	 * @return 当前日期/时间字符串
	 */
	public static String getCurrentDateTime() {
		return getCurrentDateTime(YYYYMMDD_DASH_HHMMSS_COLON);
	}

	 /**
     * 获取当前日期字符串 默认日期时间格式：yyyy-MM-dd
     * @return 当前日期
     */
    public static String getCurrentDate() {
        return getCurrentDateTime(YYYYMMDD_DASH);
    }
	/**
	 * 获取当前日期/时间字符串
	 * 
	 * @author 程伟平 创建时间：2012-02-23 15:30:30
	 * @param pattern 日期/时间格式
	 * @return 当前日期/时间字符串
	 */
	public static String getCurrentDateTime(String pattern) {
		Calendar calendar = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(calendar.getTime());
	}

	/**
	 * 获取指定日期/时间字符串 默认日期时间格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @author 程伟平 创建时间：2012-05-06 10:08:36
	 * @param field 日历字段
	 * @param offset 偏移量
	 * @return 日期/时间字符串
	 */
	public static String getDateTime(int field, int offset) {
		return getDateTime(YYYYMMDD_DASH_HHMMSS_COLON, field, offset);
	}

	/**
	 * 获取指定日期/时间字符串
	 * 
	 * @author 程伟平 创建时间：2012-05-06 10:08:36
	 * @param pattern 日期/时间格式
	 * @param field 日历字段
	 * @param offset 偏移量
	 * @return 日期/时间字符串
	 */
	public static String getDateTime(String pattern, int field, int offset) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(field, offset);
		DateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(calendar.getTime());
	}

	/**
	 * 获取指定日期/时间字符串
	 * 
	 * @author 程伟平 创建时间：2012-05-06 10:08:36
	 * @param pattern 日期/时间格式
	 * @param field 日历字段
	 * @param offset 偏移量
	 * @return 日期/时间字符串
	 */
	public static Date getDate(int field, int offset) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(field, offset);
		return calendar.getTime();
	}

	/**
	 * 获取指定日期/时间字符串 默认日期时间格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @author 程伟平 创建时间：2012-05-06 10:08:36
	 * @param field 日历字段
	 * @param offset 偏移量
	 * @param date 给定日期/时间
	 * @return 日期/时间字符串
	 */
	public static String getDateTime(int field, int offset, Date date) {
		return getDateTime(YYYYMMDD_DASH_HHMMSS_COLON, field, offset, date);
	}

	/**
	 * 获取指定日期/时间字符串
	 * 
	 * @author 程伟平 创建时间：2012-05-06 10:08:36
	 * @param pattern 日期/时间格式
	 * @param field 日历字段
	 * @param offset 偏移量
	 * @param date 给定日期/时间
	 * @return 日期/时间字符串
	 */
	public static String getDateTime(String pattern, int field, int offset, Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, offset);
		DateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(calendar.getTime());
	}

	/**
	 * 格式化日期 默认日期时间格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date Date 日期
	 * @return 日期/时间字符串
	 */
	public static String format(Date date) {
		return format(date, YYYYMMDD_DASH_HHMMSS_COLON);
	}

	/**
	 * 格式化日期
	 * 
	 * @param date Date 日期
	 * @param pattern 日期/时间格式
	 * @return 日期/时间字符串
	 */
	public static String format(Date date, String pattern) {
		String result = null;
		if (date != null) {
			DateFormat dateFormat = new SimpleDateFormat(pattern);
			result = dateFormat.format(date);
		}
		return result;
	}

	/**
	 * 解析日期 默认日期时间格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @param source 日期字符串
	 * @return 日期
	 */
	public static Date parse(String source) {
		return parse(source, YYYYMMDD_DASH_HHMMSS_COLON);
	}
	/**
	 * 当前时间转换时间戳
	 * @return 当前时间时间戳
	 */
	public static Integer getCurrentTimestamp() {
		  	Date date=null;
		  	String currentDateTime = getCurrentDateTime();
			try {
				date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(currentDateTime);
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
	        long time = date.getTime();	
	        int intValue = new Long(time/1000).intValue();
	        return intValue;
		 }

	/**
	 * 解析日期
	 * 
	 * @param source 日期字符串
	 * @param pattern 日期/时间格式
	 * @return 日期
	 */
	public static Date parse(String source, String pattern) {
		Date date = null;
		try {
			if (source != null && source.length() > 0) {
				DateFormat dateFormat = new SimpleDateFormat(pattern);
				date = dateFormat.parse(source);
			}
		} catch (ParseException e) {
			throw new RuntimeException("解析日期出错：", e);
		}

		return date;
	}

	/**
	 * 返回指定年数位移后的日期
	 */
	public static Date yearOffset(Date date, int offset) {
		return offsetDate(date, Calendar.YEAR, offset);
	}

	/**
	 * 返回指定月数位移后的日期
	 */
	public static Date monthOffset(Date date, int offset) {
		return offsetDate(date, Calendar.MONTH, offset);
	}

	/**
	 * 返回指定天数位移后的日期
	 */
	public static Date dayOffset(Date date, int offset) {
		return offsetDate(date, Calendar.DATE, offset);
	}

	/**
	 * 返回指定分钟位移后 时间
	 * 
	 */
	public static Date mintsOffset(Date date, int offset) {
		return offsetDate(date,  Calendar.MINUTE, offset);
	}
	
	/**
	 * 返回指定日期相应位移后的日期
	 * 
	 * @param date 参考日期
	 * @param field 位移单位，见 {@link Calendar}
	 * @param offset 位移数量，正数表示之后的时间，负数表示之前的时间
	 * @return 位移后的日期
	 */
	public static Date offsetDate(Date date, int field, int offset) {
		Calendar calendar = convert(date);
		calendar.add(field, offset);
		return calendar.getTime();
	}

	/**
	 * 返回当月第一天的日期
	 */
	public static Date getFirstDayOfMonth(Date date) {
		Calendar calendar = convert(date);
		calendar.set(Calendar.DATE, calendar.getMinimum(Calendar.DATE));
		return calendar.getTime();
	}

	/**
	 * 返回当月最后一天的日期
	 */
	public static Date getLastDayOfMonth(Date date) {
		Calendar calendar = convert(date);
		calendar.set(Calendar.DATE, calendar.getMaximum(Calendar.DATE));
		return calendar.getTime();
	}

	/**
	 * 得到年份列表，一直到当前年
	 */
	public static List<String> getYearList(int fromYear) {
		List<String> yearList = new ArrayList<String>();

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int thisYear = cal.get(Calendar.YEAR);

		while (fromYear <= thisYear) {
			yearList.add(String.valueOf(fromYear));
			fromYear++;
		}
		return yearList;
	}

	/**
	 * 求时间差（天）
	 */
	public static long getDays(Date date, Date now) {
		long l = now.getTime() - date.getTime();

		return l / (24 * 60 * 60 * 1000);
	}

	/**
	 * 根据时间获取时间段
	 */
	public static String getPeriod(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(date.getTime());
		int hour = c.get(Calendar.HOUR_OF_DAY);
		return hour + ":00 - " + (hour + 1) + ":00";
	}

	/**
	 * 将时间计算成 天 、 小时 、 分 、 秒 、 毫秒
	 * 
	 * @param timeMillis
	 * @return
	 */
	public static String timeMillisToString(long timeMillis) {
		StringBuffer result = new StringBuffer();
		long days = timeMillis / (1000 * 60 * 60 * 24);
		long hours = (timeMillis % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (timeMillis % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (timeMillis % (1000 * 60)) / 1000;
		long millisSeconds = timeMillis % 1000;
		result.append(days);
		result.append(" 天 ");
		result.append(hours);
		result.append(" 小时 ");
		result.append(minutes);
		result.append(" 分 ");
		result.append(seconds);
		result.append(" 秒 ");
		result.append(millisSeconds);
		result.append(" 毫秒 ");
		return result.toString();
	}

	/**
	 * 返回年份
	 * 
	 * @param date 日期
	 * @return 返回年份
	 */
	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 返回月份
	 * 
	 * @param date 日期
	 * @return 返回月份
	 */
	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 返回日份
	 * 
	 * @param date 日期
	 * @return 返回日份
	 */
	public static int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 返回小时
	 * 
	 * @param date 日期
	 * @return 返回小时
	 */
	public static int getHour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 返回分钟
	 * 
	 * @param date 日期
	 * @return 返回分钟
	 */
	public static int getMinute(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MINUTE);
	}

	/**
	 * 返回秒钟
	 * 
	 * @param date 日期
	 * @return 返回秒钟
	 */
	public static int getSecond(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.SECOND);
	}

	/**
	 * 返回毫秒
	 * 
	 * @param date 日期
	 * @return 返回毫秒
	 */
	public static long getMillis(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getTimeInMillis();
	}

	/**
	 * 日期相加
	 * 
	 * @param date Date 日期
	 * @param n 天数
	 * @return 返回相加后的日期
	 */
	public static Date addDate(Date date, int n) {
		Calendar calendar = convert(date);
		calendar.add(Calendar.DAY_OF_MONTH, n);
		return calendar.getTime();
	}

	/**
	 * 两日期相减
	 * 
	 * @param date1 日期
	 * @param date2 日期
	 * @return 天数
	 */
	public static int diffDate(Date date1, Date date2) {
		return (int) ((getMillis(date1) - getMillis(date2)) / MILLISECONDS_OF_DAY);
	}

	/**
	 * 将日期转换为日历
	 * 
	 * @author 程伟平 创建时间：2012-02-23 16:30:35
	 * @param date 日期
	 * @return 日历
	 */
	private static Calendar convert(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * 格式化 日期字符串
	 */
	public static String setFormartValue(String dateStr) {
		if (org.apache.commons.lang.StringUtils.isNotBlank(dateStr)) {
			Date date = parse(dateStr);
			return format(date);
		}
		return "";
	}
	
	/**
	 * 
	* @Title: dateTimeToTimestamp
	* @Description: 时间转时间戳 
	* @param @param dateStr
	* @param @return  
	* @return long  
	* @throws
	* @author zhangyuting
	 */
	public static long dateTimeToTimestamp(String dateStr){
			long timeStamp = 0;
		 	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	Date date;
			try {
				date = format.parse(dateStr);
				timeStamp = date.getTime()/1000;
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return timeStamp;
	      
	}
	
	
	/**
     * 得到当前月的每一天
     */
    public static List<String> getDayListOfMonth() {
        List<String> list = new ArrayList<String>();
        Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
        int year = aCalendar.get(Calendar.YEAR);//年份
        int month = aCalendar.get(Calendar.MONTH);//月份
        int day = aCalendar.getActualMaximum(Calendar.DATE);
        for (int i = 1; i <= day; i++) {
            String aDate = String.valueOf(year)+"-"+(month+1)+"-"+i;
            if (i > Integer.parseInt(getCurrentDateTime().substring(8, 10))) {
            	break;
            }
            list.add(aDate);
        }
        return list;
    }
    
    /**
     * 得到某年某月的所有天数
     */
    public static List<String> getDayListOfMonthByStr(String timeInfo) {
        List<String> list = new ArrayList<String>();
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
		try {
			if(timeInfo.length()<=10){
				timeInfo = timeInfo+" 00:00:00";
			}
			Calendar cal = Calendar.getInstance();
			  
			date = format.parse(timeInfo);
			int month = getMonth(date);
			int year = getYear(date);
			
			 //设置年份  
		     cal.set(Calendar.YEAR,year);  
		     //设置月份  
		     cal.set(Calendar.MONTH, month-1);  
		     //获取某月最大天数  
		     int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);  
		     //设置日历中月份的最大天数  
		     cal.set(Calendar.DAY_OF_MONTH, lastDay);  
		     Date lastDate = cal.getTime();  

			int day = getDay(lastDate);
			for (int i = 1; i <= day; i++) {
		            String aDate = String.valueOf(year)+"-"+month+"-"+i;
		            list.add(aDate);
		     }
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return list;
    }
    
    
	public static void main(String[] args) {
//		System.out.println(DateUtil.getCurrentDateTime());
//		System.out.println(DateUtil.getCurrentDateTime());
//		System.out.println(DateUtil.getCurrentDateTime());
//		System.out.println(DateUtil.getCurrentDateTime());
		
//		System.out.println(dateTimeToTimestamp("2011-05-09 11:49:45"));
		String ti = DateUtil.getDateTime(DateUtil.YYYYMMDD_DASH,Calendar.DATE,-1,new Date()) + " 00:00:00";
		Date start = DateUtil.parse(ti, DateUtil.YYYYMMDD_DASH_HHMMSS_COLON);
		
		System.out.println(DateUtil.getDayListOfMonthByStr("2018-02-05"));
		
	}
	public static String getLastDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(1, year);
		cal.set(2, month - 1);
		int lastDay = cal.getActualMaximum(5);
		cal.set(5, lastDay);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String lastDayOfMonth = sdf.format(cal.getTime());
		return lastDayOfMonth;
	}
	/**
	 * 比较两个时间大小
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean compare(Date date1, Date date2) {
		return getMillis(date1) - getMillis(date2) > 0 ? true : false;
	}

}
