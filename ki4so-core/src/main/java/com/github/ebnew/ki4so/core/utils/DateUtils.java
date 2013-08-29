package com.github.ebnew.ki4so.core.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.time.DateFormatUtils;

public class DateUtils {
	
	/**
	 * 指定DateFormat的key值 
	 * @see cn.bidlink.framework.web.view.json.BidMappingJacksonJsonView
	 */
	public static final String DATE_FORMAT = "DATE_FORMAT";
	
	public static final String DATE = "yyyy-MM-dd";
	public static final String DATE_HH_MM = "yyyy-MM-dd HH:mm";
	public static final String DATE_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_HH_MM_SS_Z1 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	public static final String DATE_HH_MM_SS_Z2 = "yyyy-MM-dd'T'HH:mm:ssZ";
	public static final String DATE_HH_MM_SS_Z3 = "yyyy-MM-dd'T'HH:mm:ssz";
	public static final String DATE_HH_MM_SS_Z4 = "yyyy-MM-dd'T'HH:mm:ss";
	
	public static final String DATE_HH_MM_SS_A = "MM/dd/yyyy HH:mm:ss a";
	public static final String DATE_HHMMSS = "yyyyMMddHHmmss";
	
	private static List<DateFormat> formats = new ArrayList<DateFormat>();
	
	static {
		/**alternative formats*/
		formats.add(new SimpleDateFormat(DATE_HH_MM_SS));
		formats.add(new SimpleDateFormat(DATE_HH_MM));
		formats.add(new SimpleDateFormat(DATE));
		/**ISO formats*/
		formats.add(new SimpleDateFormat(DATE_HH_MM_SS_Z1));
		formats.add(new SimpleDateFormat(DATE_HH_MM_SS_Z2));
		formats.add(new SimpleDateFormat(DATE_HH_MM_SS_Z3));
		formats.add(DateFormat.getDateTimeInstance());
		/**XPDL examples format*/
		formats.add(new SimpleDateFormat(DATE_HH_MM_SS_A, Locale.US));
		formats.add(new SimpleDateFormat(DATE_HHMMSS));
		/**Only date, no time*/
		formats.add(DateFormat.getDateInstance());
	}
	/**
	 * 字符串转化成日期
	 * @param dateString 字符串
	 * @return 日期
	 */
	public synchronized static Date parse(String dateString) {
		if (dateString == null) {
			return null;
		}
		for (DateFormat format : formats) {
			try {
				return format.parse(dateString);
			} catch (ParseException e) {
			}
		}
		return null;
	}

	public static Date parse(String str, String pattern) {
		DateFormat format = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static Date parseExp(String str, String pattern) throws Exception{
		DateFormat format = new SimpleDateFormat(pattern);
		Date date = null;
		date = format.parse(str);
		return date;
	}
	
	/**
	 * 日期类型转字符串
	 * @param date 日期
	 * @return 字符串（格式为：yyyy-MM-dd）
	 */
	public synchronized static String format(Date date) {
		SimpleDateFormat df = new SimpleDateFormat(DATE);
		return df.format(date);
	}
	
	/**
	 * 日期类型转字符串
	 * @param date 日期
	 * @param pattern 格式
	 * @return 字符串
	 */
	public static String format(Date date,String pattern){
		if(date==null) return "";
		return DateFormatUtils.format(date, pattern);
	}
	
	/**
	 * 将某个时间范围按天进行切分，未满一天的按一天算
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return 日期集合
	 */
	public static List<Date> splitByDay(Date startDate, Date endDate) {
		List<Date> dayList = new ArrayList<Date>();
		String startDateStr = DateFormatUtils.format(startDate, DATE);
		Date startDate1 = DateUtils.parse(startDateStr);

		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDate1);
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);
		Calendar tempCal = Calendar.getInstance();
		tempCal.setTime(startDate1);
		tempCal.add(Calendar.DAY_OF_MONTH, 1);

		while (tempCal.before(endCal)) {
			dayList.add(startCal.getTime());
			startCal.add(Calendar.DAY_OF_MONTH, 1);
			tempCal.add(Calendar.DAY_OF_MONTH, 1);
		}
		dayList.add(startCal.getTime());
		return dayList;
	}

	/**
	 * 判断两个时间是否在同一天内
	 * @param date1 时间
	 * @param date2 时间
	 * @return true=同一天；false=非同一天；
	 */
	public static boolean isSameDay(Date date1, Date date2) {
		String date1Str = DateFormatUtils.format(date1, DATE);
		String date2Str = DateFormatUtils.format(date2, DATE);
		if (date1Str.equals(date2Str)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断两个时间是否在同一个月内
	 * @param date1 时间
	 * @param date2 时间
	 * @return true=在同一个月内；false=不在同一个月内；
	 */
	public static boolean isSameMonth(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		if (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断两个时间是否在同一季度里
	 * @param date1 时间
	 * @param date2 时间
	 * @return true=在同一个季度内；false=不在同一个季度内；
	 */
	public static boolean isSameQuarter(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		int month1 = cal1.get(Calendar.MONTH);
		int month2 = cal2.get(Calendar.MONTH);
		if (((month1 >= Calendar.JANUARY && month1 <= Calendar.MARCH) && (month2 >= Calendar.JANUARY && month2 <= Calendar.MARCH))
				|| ((month1 >= Calendar.APRIL && month1 <= Calendar.JUNE) && (month2 >= Calendar.APRIL && month2 <= Calendar.JUNE))
				|| ((month1 >= Calendar.JULY && month1 <= Calendar.SEPTEMBER) && (month2 >= Calendar.JULY && month2 <= Calendar.SEPTEMBER))
				|| ((month1 >= Calendar.OCTOBER && month1 <= Calendar.DECEMBER) && (month2 >= Calendar.OCTOBER && month2 <= Calendar.DECEMBER))) {
			return true;
		}
		return false;
	}
	
	/**
	 * 得到两个时间的差额
	 * @param date 时间
	 * @param otherDate 时间
	 * @return 时间差额
	 */
    public static long betDate(Date date, Date otherDate) {
        return date.getTime() - otherDate.getTime();
    }

	/**
	 * 获取当前日期
	 * @return long（毫秒）
	 */
	public static long getCurrentTime() {
		return Calendar.getInstance().getTimeInMillis();
	}

	/**
	 * 获取当前日期
	 * @return Date
	 */
	public static Date getCurrentDate() {
		return new Date();
	}

	/**
	 * 获取当前日期
	 * @return Calendar
	 */
	public static Calendar getCurrentCalendar() {
		return Calendar.getInstance();
	}

	public static String calendarToString(Calendar calendar, String template) {
		String stringCalendar = template;
		stringCalendar = stringCalendar.replace("{year}",
				String.valueOf(calendar.get(Calendar.YEAR)));
		stringCalendar = stringCalendar.replace("{month}",
				String.valueOf(calendar.get(Calendar.MONTH)));
		stringCalendar = stringCalendar.replace("{date}",
				String.valueOf(calendar.get(Calendar.DATE)));
		stringCalendar = stringCalendar.replace("{hour}",
				String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)));
		stringCalendar = stringCalendar.replace("{minute}",
				String.valueOf(calendar.get(Calendar.MINUTE)));
		stringCalendar = stringCalendar.replace("{second}",
				String.valueOf(calendar.get(Calendar.SECOND)));
		stringCalendar = stringCalendar.replace("{millisecond}",
				String.valueOf(calendar.get(Calendar.MILLISECOND)));
		return stringCalendar;
	}
	
	/**
     * 比较时间差 1小时内的显示：**　分钟前，例如：25　分钟前 1小时前（１天内的）：今天　**时：**分，例如：　08：17
     * 1天前的（当前年）：*月*号　**时：**分，例如：05-09　23：58
     * 当前年之前的：年－月－日　**时：**分，例如：2009-09-26　16：33
     **/
    public static String timeCompare(Date _now, Date _date) {
    	Calendar now = Calendar.getInstance();
    	now.setTime(_now);
    	Calendar date = Calendar.getInstance();
    	date.setTime(_date);
    	
    	int nowY = now.get(Calendar.YEAR);
    	int dateY = date.get(Calendar.YEAR);
    	
    	int nowM = now.get(Calendar.MONTH);
    	int dateM = date.get(Calendar.MONTH);
    	
    	int nowD = now.get(Calendar.DAY_OF_MONTH);
    	int dateD = date.get(Calendar.DAY_OF_MONTH);
    	
    	
    	long l = now.getTimeInMillis() - date.getTimeInMillis();
        long m = nowM - dateM;
        long day = nowD - dateD;
        
        int dateHour = date.get(Calendar.HOUR_OF_DAY);
        int dateMinutes = date.get(Calendar.MINUTE);

        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long y = nowY - dateY;
        String ret = "";
        if (y > 0)//大于一年的
            ret += (dateY + 1900) + "-";
        if (day > 0 || y > 0 || m > 0) {// 大于一天的
            if (dateM + 1 < 10)
                ret += "0";
            ret += (dateM + 1) + "-";
            if (dateD < 10)
                ret += "0";
            ret += dateD + " ";
        }
        if (hour > 0 || day > 0 || y > 0 || m > 0) {// 大于一小时
            if (dateHour < 10)
                ret += "0";
            ret += dateHour + ":";
            if (dateMinutes < 10)
                ret += "0";
            ret += dateMinutes;
        }
        if (y == 0 && (day * 24 + hour) == 0 && min != 0)
            ret = min + " 分前";
        if (y == 0 && (day * 24 + hour) == 0 && min == 0)
            ret = "1  分前";
        return ret;
    }
	
    public static long getFormatedTime(long time) {
    	return getFormatedTime(new Date(time));
    }
    
    public static long getFormatedTime(Date date) {
    	if(date == null) {
    		return 0;
    	}
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    	return new Long(dateFormat.format(date)).longValue();
    }
    
    /**
     * @description  给定时间差解析成中文表示(只计算过去的某个时间和当前时间的差)
     * <p>如"2小时32分"</p>
     * @param deltaMill	时差毫秒表示
     * @return
     */
    public static String subtractParse(long deltaMillis, String format) {
    	if(deltaMillis<=0) {
    		return "";
    	} else {
    		StringBuffer res = new StringBuffer();
    		long day=deltaMillis/(24*60*60*1000);
		    long hour=(deltaMillis/(60*60*1000)-day*24);
		    long min=((deltaMillis/(60*1000))-day*24*60-hour*60);
		    long sec=(deltaMillis/1000-day*24*60*60-hour*60*60-min*60);
		    //long millis=(deltaMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-sec*1000);
		    if(day>0 && format.contains("d")) {
		    	res.append(day+"天");
		    }
		    if(hour>0 && format.contains("H")) {
		    	res.append(hour+"小时");
		    }
		    if(min>0 && format.contains("m")) {
		    	res.append(min+"分钟");
		    }
		    if(sec>0 && format.contains("s")) {
		    	res.append(sec+"秒");
		    }
		    //String res = day+"天"+hour+"小时"+min+"分"+sec+"秒"+millis+"毫秒";
		    return res.toString();
    	}
    }
    
    /**
     * @description  解析过去某个时间和当前时间的差的中文表示
     * <p>如"2小时32分"</p>
     * @param oldTime 过去的某个时间
     * @param format 格式化方式 。 如dHms表示 xx天xx小时xx分钟xx秒
     * @return
     */
    public static String subtractParse(Date oldTime, String format) {
    	Date current = getCurrentDate();
    	if(oldTime.compareTo(current) <= 0) {
    		return subtractParse(betDate(current, oldTime), format);
    	}
    	return "";
    }
    
    
    public static void main(String[] args) {
		System.out.println(subtractParse(parse("2012-12-09 21:33:33", "yyyy-MM-dd HH:mm:ss"), "dH"));
	}
    
}
