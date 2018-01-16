package cn.edu.njtech.manage.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * <Date工具类>
 * <功能详细描述>
 *
 * @author sunhuan
 * @version [版本号, 2017/8/15 8:20]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DateUtil {

	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

	public static final String DATE_PATTERN = "MM/dd/yyyy";

	public static final String DATA_TIME_PATTERN = "yyyyMMddHHmmss";

	public static final String TIME_PATTERN = DATE_PATTERN + " HH:MM a";

	public static final String DATE_PATTERN_SHOW = "yyyy年MM月dd日";

	public static final String DEFAULT_DATE_FORMAT_PATTERN_SHORT = "yyyy-MM-dd";

	public static final String DEFAULT_DATE_FORMAT_PATTERN_MIN = "yyyy-MM-dd HH:mm";

	public static final String DEFAULT_DATE_FORMAT_PATTERN_FULL = "yyyy-MM-dd HH:mm:ss";

	//精确到毫秒
	public static final String DEFAULT_DATE_FORMAT_PATTERN_MILLION = "yyyy-MM-dd HH:mm:ss:SSS";

	public static final String DEFAULT_DATE_FORMAT_MM_DD_HH_MM = "MM-dd HH:mm";

	public static String getDatePattern() {
		return DATE_PATTERN;
	}

	/**
	 * 返回日期的String格式
	 *
	 * @param aDate Date
	 * @return String
	 */
	public static String date2Str(Date aDate) {
		SimpleDateFormat df;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(DATE_PATTERN);
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}

	/**
	 * 根据格式返回日期的String格式
	 *
	 * @param aDate   date
	 * @param pattern pattern
	 * @return string
	 */
	public static String date2Str(Date aDate, String pattern) {
		SimpleDateFormat df;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(pattern);
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}

	/**
	 * 字符串转为date格式
	 *
	 * @param aMask   aMask
	 * @param strDate strDate
	 * @return date
	 * @throws ParseException 格式异常
	 */
	public static Date str2Date(String aMask, String strDate)
			throws ParseException {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask);

		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			logger.error("str2Date error:" + pe);
			return null;
		}
		return (date);
	}

	/**
	 * 返回date时间的string格式
	 *
	 * @param theTime theTime
	 * @return String
	 */
	public static String getTimeNow(Date theTime) {
		return getDateTime(TIME_PATTERN, theTime);
	}

	/**
	 * 返回当前日期
	 *
	 * @return Calendar
	 * @throws ParseException 格式异常
	 */
	public static Calendar getToday()
			throws ParseException {
		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat(DATE_PATTERN);
		String todayAsString = df.format(today);
		Calendar cal = new GregorianCalendar();
		cal.setTime(convertStringToDate(todayAsString));
		return cal;
	}

	/**
	 * 返回日期时间的string格式
	 *
	 * @param aMask aMask
	 * @param aDate aDate
	 * @return String
	 */
	private static String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";
		if (aDate == null) {
			System.out.print("aDate is null!");
		} else {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}

	/**
	 * date转换为string
	 *
	 * @param aDate aDate
	 * @return string
	 */
	public static String convertDateToString(Date aDate) {
		return getDateTime(DATE_PATTERN, aDate);
	}

	/**
	 * date转换为string
	 *
	 * @param strDate strDate
	 * @return Date
	 * @throws ParseException 格式异常
	 */
	private static Date convertStringToDate(String strDate)
			throws ParseException {
		Date aDate = null;

		try {
			aDate = str2Date(DATE_PATTERN, strDate);
		} catch (ParseException pe) {
			logger.error("convertStringToDate error:" + pe);
			return null;

		}
		return aDate;
	}

	/**
	 * 获取当前时间的string格式
	 *
	 * @return string
	 */
	public static String getNowDateTime() {
		String strReturn = null;
		Date now = new Date();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			strReturn = sdf.format(now);
		} catch (Exception e) {
			logger.error("getNowDateTime error:" + e);
			strReturn = "";
		}
		return strReturn;
	}

	/**
	 * 获取当前时间戳
	 *
	 * @return timestamp
	 */
	public static Timestamp getNowTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 返回星期
	 *
	 * @param strWeek strWeek
	 * @return int
	 */
	public static int getWeekNum(String strWeek) {
		int returnValue = 0;
		if (StringUtils.isEmpty(strWeek)) {
			return returnValue;
		}
		if ("Mon".equals(strWeek)) {
			returnValue = 1;
		} else if ("Tue".equals(strWeek)) {
			returnValue = 2;
		} else if ("Wed".equals(strWeek)) {
			returnValue = 3;
		} else if ("Thu".equals(strWeek)) {
			returnValue = 4;
		} else if ("Fri".equals(strWeek)) {
			returnValue = 5;
		} else if ("Sat".equals(strWeek)) {
			returnValue = 6;
		} else if ("Sun".equals(strWeek)) {
			returnValue = 0;
		}
		return returnValue;
	}

	/**
	 * 得到格式化后的指定日期
	 *
	 * @param strScheme 格式模式字符串
	 * @param date      指定的日期值
	 * @return 格式化后的指定日期，如果有异常产生，返回空串""
	 */
	public static final String getDateTime(Date date, String strScheme) {
		String strReturn = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(strScheme);
			strReturn = sdf.format(date);
		} catch (Exception e) {
			logger.error("getDateTime error:" + e);
			strReturn = "";
		}
		return strReturn;
	}

	/**
	 * 获取日期
	 *
	 * @param timeType 时间类型，譬如：Calendar.DAY_OF_YEAR
	 * @param timenum  时间数字，譬如：-1 昨天，0 今天，1 明天
	 * @return 日期
	 */
	public static Date getDateFromNow(int timeType, int timenum) {
		Calendar cld = Calendar.getInstance();
		cld.set(timeType, cld.get(timeType) + timenum);
		return cld.getTime();
	}

	/**
	 * 获取日期
	 *
	 * @param timeType     时间类型，譬如：Calendar.DAY_OF_YEAR
	 * @param timenum      时间数字，譬如：-1 昨天，0 今天，1 明天
	 * @param formatString 时间格式，譬如："yyyy-MM-dd HH:mm:ss"
	 * @return 字符串
	 */
	public static String getDateFromNow(int timeType, int timenum, String formatString) {
		if ((formatString == null) || ("".equals(formatString))) {
			formatString = "yyyy-MM-dd HH:mm:ss";
		}
		Calendar cld = Calendar.getInstance();
		Date date = null;
		DateFormat df = new SimpleDateFormat(formatString);
		cld.set(timeType, cld.get(timeType) + timenum);
		date = cld.getTime();
		return df.format(date);
	}

	/**
	 * 获取当前日期的字符串
	 *
	 * @param formatString 时间格式，譬如："yyyy-MM-dd HH:mm:ss"
	 * @return 字符串
	 */
	public static String getDateNow(String formatString) {
		if ((formatString == null) || ("".equals(formatString))) {
			formatString = "yyyy-MM-dd HH:mm:ss";
		}
		Calendar cld = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat(formatString);
		return df.format(cld.getTime());
	}

	/**
	 * dateDiffType
	 */
	public enum DateDiffType {
		SECONDS(1000), MINUTES(1000 * 60), HOUR(1000 * 60 * 60), DAY(1000 * 60 * 60 * 24);

		private long type;

		private DateDiffType(long type) {
			this.type = type;
		}

		public long value() {
			return this.type;
		}
	}

	/**
	 * 返回时间段差值
	 *
	 * @param startTime  start
	 * @param endTime    end
	 * @param formatDiff diff
	 * @return long
	 */
	public static long getDateDiff(String startTime, String endTime, DateDiffType formatDiff) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date d1 = null;
		Date d2 = null;
		try {
			d1 = format.parse(startTime);
			d2 = format.parse(endTime);
			long diff = d1.getTime() - d2.getTime();
			return diff / formatDiff.value();

		} catch (Exception e) {
			logger.error("getDateDiff error:" + e);
			return 0;
		}
	}

	/**
	 * 返回时间段差值
	 *
	 * @param startTime  start
	 * @param endTime    end
	 * @param formatDiff diff
	 * @return long
	 */
	public static long getDateDiff(Date startTime, Date endTime, DateDiffType formatDiff) {
		try {
			long diff = startTime.getTime() - endTime.getTime();
			return diff / formatDiff.value();

		} catch (Exception e) {
			logger.error("getDateDiff error:" + e);
			return 0;
		}
	}

	/**
	 * 将字符串转为时间戳
	 *
	 * @param userTime time
	 * @return string
	 */
	public static String dateToTimestamp(String userTime) {
		String re_time = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date d;
		try {
			d = sdf.parse(userTime);
			long l = d.getTime();
			String str = String.valueOf(l);
			re_time = str.substring(0, 10);
		} catch (ParseException e) {
			return null;
		}
		return re_time;
	}

	/**
	 * 返回
	 *
	 * @param sDate1 date
	 * @return date
	 */
	public static Date getLastDayOfMonth(Date sDate1) {
		Calendar cDay1 = Calendar.getInstance();
		cDay1.setTime(sDate1);
		cDay1.add(Calendar.MONTH, 1);
		return cDay1.getTime();
	}

	/**
	 * 功能描述: <br>
	 * 根据指定格式对当前日期进行格式化
	 * (传入：String，返回：String)
	 *
	 * @param date   传入日期类型日期
	 * @param format 需要转化的格式
	 * @return String 转换后的字符串格式日期
	 */
	public static String parseDate(Date date, String format) {
		SimpleDateFormat dateformat = new SimpleDateFormat(format);
		return dateformat.format(date);
	}

	/**
	 * 功能描述: <br>
	 * 根据指定格式对当前日期进行格式化
	 * (传入：String，返回：Date)
	 *
	 * @param datestr     传入字符串类型日期
	 * @param dateFormate 需要转化的格式
	 * @return Date 转换后的日期类型日期
	 */
	public static Date parseDate(String datestr, String dateFormate) {
		SimpleDateFormat df = new SimpleDateFormat(dateFormate);
		Date date = null;
		try {
			date = df.parse(datestr);
		} catch (ParseException e) {
			logger.error("parseDate error:" + e);
			return null;
		}
		return date;
	}

	/**
	 * 判断当前时间是否在时间段内
	 *
	 * @param start
	 * @param end
	 * @param betweenDate
	 * @return
	 */
	public static int compareDate(Date start, Date end, Date betweenDate) {
		if (betweenDate.getTime() < start.getTime()) {
			return -1;
		} else if (betweenDate.getTime() > end.getTime()) {
			return 1;
		} else {
			return 0;
		}
	}

	// 判断当前时间是否在时间段内
	public static Boolean compareDate(String compBeginTime, String compEndTime, String compedBeginTime,
									  String compedEndTime) {
		try {
			Date compBegin = str2Date(DEFAULT_DATE_FORMAT_PATTERN_MIN, compBeginTime);
			Date compEnd = str2Date(DEFAULT_DATE_FORMAT_PATTERN_MIN, compEndTime);
			Date compedBegin = str2Date(DEFAULT_DATE_FORMAT_PATTERN_MIN, compedBeginTime);
			Date compedEnd = str2Date(DEFAULT_DATE_FORMAT_PATTERN_MIN, compedEndTime);
			if (null != compEnd && null != compedEnd && null != compBegin && null != compedBegin) {
				if (compedEnd.compareTo(compBegin) <= 0 || compEnd.compareTo(compedBegin) <= 0) {
					return false;
				} else {
					return true;
				}
			} else {
				return false;
			}
		} catch (ParseException e) {
			logger.error("compareDate error:" + e);
			return false;
		}
	}

	/**
	 * utcTime转换成本地时间格式
	 *
	 * @param utcTime         utcTime
	 * @param utcTimePatten   utcTimePatten
	 * @param localTimePatten localTimePatten
	 * @return string
	 */
	public static String utc2Local(String utcTime, String utcTimePatten, String localTimePatten) {
		SimpleDateFormat utcFormater = new SimpleDateFormat(utcTimePatten);
		utcFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date gpsUTCDate = null;
		String localTime = "";
		try {
			gpsUTCDate = utcFormater.parse(utcTime);
			SimpleDateFormat localFormater = new SimpleDateFormat(localTimePatten);
			localFormater.setTimeZone(TimeZone.getDefault());
			localTime = localFormater.format(gpsUTCDate.getTime());
		} catch (Exception e) {
			logger.error("utc2Local error:" + e);
			return null;
		}
		return localTime;
	}

	/**
	 * 得到UTC时间，类型为字符串，格式为"yyyyMMddHHmmss"<br />
	 * 如果获取失败，返回null
	 *
	 * @return
	 */
	public static String getUTCTimeStr() {
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		StringBuilder utcTimeBuffer = new StringBuilder();
		// 1、取得本地时间：
		Calendar cal = Calendar.getInstance();
		// 2、取得时间偏移量：
		int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
		// 3、取得夏令时差：
		int dstOffset = cal.get(Calendar.DST_OFFSET);
		// 4、从本地时间里扣除这些差量，即可以取得UTC时间：
		cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		utcTimeBuffer.append(year).append(month).append(day);
		utcTimeBuffer.append(hour).append(minute).append(second);
		try {
			format.parse(utcTimeBuffer.toString());
			return utcTimeBuffer.toString();
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取UTC时间
	 *
	 * @return UTC时间
	 * @author duhongwei
	 * @see [类、类#方法、类#成员]
	 */
	public static Date getUTCTime() {
		//1、取得本地时间：
		Calendar cal = Calendar.getInstance();

		//2、取得时间偏移量：
		int zoneOffset = cal.get(Calendar.ZONE_OFFSET);

		//3、取得夏令时差：
		int dstOffset = cal.get(Calendar.DST_OFFSET);

		//4、从本地时间里扣除这些差量，即可以取得UTC时间：
		cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));

		return new Date(cal.getTimeInMillis());
	}

	/**
	 * 校验时间格式 （yyyy-MM-dd HH:mm:ss）
	 *
	 * @param strDateTime
	 * @return
	 */
	public static int checkDateFormatAndValite(String strDateTime) {
		SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_FORMAT_PATTERN_FULL);

		String str = "";
		try {
			Date ndate = format.parse(strDateTime);
			str = format.format(ndate);
		} catch (ParseException e) {
			return -1;
		}
		if (str.equals(strDateTime)) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * 判断当前时间是否在两个时间之间(区间都不包含)
	 *
	 * @param startTime 开始时间 （格式yyyyMMdd24HHmmss）
	 * @param endTime   结束时间（格式yyyyMMdd24HHmmss）
	 * @return
	 */
	public static boolean isBetweenTwoTimes(String startTime, String endTime) {
		// 当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		long nowTime = Long.parseLong(sdf.format(new Date()));
		if (StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)) {
			return false;
		}
		if (Long.parseLong(startTime) < nowTime && nowTime < Long.parseLong(endTime)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断当前时间是否在制定时间之前
	 *
	 * @param oneTime 结束时间（yyyyMMddHHmmss）
	 * @return
	 */
	public static boolean isBeforeOneTime(String oneTime) {
		// 当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		long nowTime = Long.parseLong(sdf.format(new Date()));
		if (StringUtils.isEmpty(oneTime)) {
			return false;
		}
		if (nowTime < Long.parseLong(oneTime)) {
			return true;
		}
		return false;
	}

	/**
	 * Long 类型时间戳转 Date
	 *
	 * @param timeStamp 时间戳 Long 类型
	 * @return
	 */
	public static Date getDateFromLong(Long timeStamp) {
		SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_FORMAT_PATTERN_FULL);
		String temp = format.format(timeStamp);
		try {
			Date date = format.parse(temp);
			return date;
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * Long 类型时间戳转 Date
	 *
	 * @param timeStamp  时间戳 Long 类型
	 * @param patternStr 格式Str
	 * @return
	 */
	public static Date getDateFromLong(Long timeStamp, String patternStr) {
		SimpleDateFormat format = new SimpleDateFormat(patternStr);
		String temp = format.format(timeStamp);
		try {
			Date date = format.parse(temp);
			return date;
		} catch (ParseException e) {
			return null;
		}
	}
}
