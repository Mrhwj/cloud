package com.hwj.product.tools;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;


public class Common {

	/*
	 * 日期格式�?
	 */
	public static String dateToString(Date dt) {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String cdt = formatter.format(dt);

		return cdt;
	}

	/**
	 * 日期格式�? 含格式化字符�?
	 * 
	 * @param dt
	 * @param formatStr
	 * @return
	 */
	public static String dateToString(Date dt, String formatStr) {
		SimpleDateFormat formatter;
		if (dt == null) {
			dt = new Date();
		}
		if (formatStr.isEmpty()) {
			formatStr = "yyyy-MM-dd HH:mm:ss";
		}
		formatter = new SimpleDateFormat(formatStr);
		String cdt = formatter.format(dt);

		return cdt;
	}

	/**
	 * 日期增加 固定年�?�月、日
	 * 
	 * @param dt
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Date dateAddCustom(Date dt, int year, int month, int day, int hour) {
		Calendar cl = Calendar.getInstance();
		if (dt != null) {
			cl.setTime(dt);

			if (year != 0) {
				cl.add(Calendar.YEAR, year);
			}
			if (month != 0) {
				cl.add(Calendar.MONTH, month);
			}
			if (day != 0) {
				cl.add(Calendar.DATE, day);
			}
			if (hour != 0) {
				cl.add(Calendar.HOUR, hour);
			}
			dt = cl.getTime();
		}
		return dt;

	}

	/*
	 * 判断String是否为空
	 */
	public static boolean IsNullOrEmpty(String str) {
		boolean b = false;
		if (str == null || str.equals("")) {
			b = true;
		}
		return b;
	}

	/**
	 * 判断Integer是否有�??
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNullOrEmpty(Integer value) {
		if (value == null)
			return true;
		else
			return false;
	}
	public static boolean isNullOrEmpty(Double value) {
		if (value == null)
			return true;
		else
			return false;
	}
	/**
	 * 判断是否是整�?
	 * 
	 * @author scl
	 * @date 2020�?7�?1�?
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 获取内容中的图片列表
	 * 
	 * @author scl
	 * @date 2020�?7�?1�?
	 * @param htmlStr
	 * @return
	 */
	public static List<String> GetImageList(String htmlStr, String contextPathString) {
		if (IsNullOrEmpty(htmlStr))
			return null;

		List<String> list = new ArrayList<>();
		// 匹配img标签获取src
		Pattern pattern = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
		Matcher m = pattern.matcher(htmlStr);
		while (m.find()) {
			String imgStr = m.group();
			// System.out.println(imgStr);
			Pattern srcPattern = Pattern.compile("src=\".*\"");
			Matcher match = srcPattern.matcher(imgStr);
			String imgPath = "";
			while (match.find()) {
				imgPath = match.group();
				// System.out.println(imgPath);
			}
			imgPath = imgPath.replace("src=\"", "").replace("\"", "");
			if (imgPath.indexOf(" ") > -1) {
				imgPath = imgPath.split(" ")[0];
			}
			imgPath = FilterImg(imgPath);
			if (!IsNullOrEmpty(imgPath))
				if (contextPathString != "") {
					imgPath = imgPath.replace(contextPathString, "");
				}
			list.add(imgPath);
		}
		return list;
	}

	/**
	 * 过滤
	 * 
	 * @author scl
	 * @date 2020�?7�?1�?
	 * @param imgPath
	 * @return
	 */
	public static String FilterImg(String imgPath) {
		Pattern pattern = Pattern.compile("doc.*.(?:png|jpg|bmp|gif)");
		Matcher m = pattern.matcher(imgPath);
		if (m.matches())
			return "";
		return imgPath;
	}

	/*
	 * 日期格式�?--自定�?
	 */
	public static String dateToStringFormat(Date dt, String format) {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat(format);
		String cdt = formatter.format(dt);

		return cdt;
	}

	/*
	 * 解决JSP 中文乱码的问�?
	 */
	public static String getReqParam(String param) {
		try {
			return new String(param.getBytes("ISO8859_1"));
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	/*
	 * 转MD5 加密
	 */
	public static String MD5(String sourceStr, int len) {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(sourceStr.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			if (len == 32) {
				result = buf.toString();
			}
			if (len == 16) {
				result = buf.toString().substring(8, 24);
			}
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e);
		}
		return result;
	}

	/*
	 * 加密
	 */
	public static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

	/*
	 * 解码
	 */
	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List resultSetToList(ResultSet rs) throws java.sql.SQLException {
		if (rs == null)
			return Collections.EMPTY_LIST;
		ResultSetMetaData md = rs.getMetaData(); // 得到结果�?(rs)的结构信息，比如字段数�?�字段名�?
		int columnCount = md.getColumnCount(); // 返回�? ResultSet 对象中的列数
		String tmpValue = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List list = new ArrayList();
		Map<String, String> rowData = new HashMap<String, String>();
		while (rs.next()) {
			rowData = new HashMap(columnCount);
			for (int i = 1; i <= columnCount; i++) {
				Object object = rs.getObject(i);
				if (object == null) {
					object = "";
				}
				tmpValue = object.toString();

				int columntype = md.getColumnType(i);
				if (columntype == 93) {
					// 日期类型格式�?
					Date date;
					try {
						if (object.toString() != "") {
							date = dateFormat.parse(object.toString());
							tmpValue = dateFormat.format(date);
						} else {
							tmpValue = null;
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}

				}
				// LoveTools.ConsolePrint(Integer.toString(columntype) + "###" +
				// md.getColumnName(i));
				// rowData.put(md.getColumnName(i), tmpValue);获得的是原始的列�?
				rowData.put(md.getColumnLabel(i), tmpValue);// 当前的列�?
				// rowData.put(md.getColumnName(i), rs.getObject(i).toString());
			}
			list.add(rowData);
			// System.out.println("list:" + list.toString());
		}
		return list;
	}

	/**
	 * 比较两个日期相差的天�? 不�?�虑时�?�分、秒
	 * 
	 * @param fDate �?
	 * @param oDate �?
	 * @return
	 */
	public static int daysOfTwo(Date fDate, Date oDate) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(fDate);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(oDate);
		int day1 = cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);

		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		if (year1 != year2) {// 不同�?
			int timeDistance = 0;
			for (int i = year1; i < year2; i++) {
				if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {// 闰年
					timeDistance += 366;
				} else {// 不是闰年
					timeDistance += 365;
				}
			}

			return timeDistance + (day2 - day1);
		} else {// 同年
			return day2 - day1;
		}
	}

	/**
	 * 比较日期月份�?
	 * 
	 * @author scl
	 * @date 2021�?4�?5�?
	 * @param date1
	 * @param date2
	 * @return 绝对�?
	 */
	public static int monthsOfTwo(Date date1, Date date2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);
		int year1 = c1.get(Calendar.YEAR);
		int year2 = c2.get(Calendar.YEAR);
		int month1 = c1.get(Calendar.MONTH);
		int month2 = c2.get(Calendar.MONTH);
		int day1 = c1.get(Calendar.DAY_OF_MONTH);
		int day2 = c2.get(Calendar.DAY_OF_MONTH);
		// 获取年的差�??
		int yearInterval = year1 - year2;
		// 如果 d1�? �?-�? 小于 d2�? �?-�? 那么 yearInterval-- 这样就得到了相差的年�?
		if (month1 < month2 || month1 == month2 && day1 < day2) {
			yearInterval--;
		}
		// 获取月数差�??
		int monthInterval = (month1 + 12) - month2;
		if (day1 < day2) {
			monthInterval--;
		}
		monthInterval %= 12;
		int monthsDiff = Math.abs(yearInterval * 12 + monthInterval);
		return monthsDiff;
	}

	/**
	 * 截取字符串函�?
	 * 
	 * @param Str
	 * @param Num
	 * @param LastStr
	 * @return
	 */
	public static String GetSubString(String Str, int Num, String LastStr) {
		if (Str.isEmpty()) {
			return "";
		}
		String outstr = "";
		int charlen = GetLength(Str, Num);// 根据字节数获得要截取的真正长�?
		outstr = Str;
		if (Str.length() > charlen) {
			outstr = Str.substring(0, charlen) + LastStr;
		}

		return outstr;
	}

	/**
	 * 截取字符串真正的长度
	 * 
	 * @param Str
	 * @param Num
	 * @return
	 */
	public static int GetLength(String Str, int Num) {
		int strlength = Str.length();
		int l = 0, re = 0;
		for (int i = 0; i < strlength; i++) {
			int asciiCode = Str.codePointAt(i);
			int a = asciiCode >= 0 && asciiCode <= 255 ? 1 : 2;
			if (l + a > 2 * Num) {
				break;
			} else {
				re++;
			}
			l = l + a;
		}
		return re;
	}

	/**
	 * utf-8 转码处理函数 chenxj@20170718
	 * 
	 * @param html
	 * @return
	 */
	public static String getUTF8String(String html) {
		StringBuffer sb = new StringBuffer();
		sb.append(html);
		String htmlString = "";
		String htmlUTF8 = "";
		try {
			htmlString = new String(sb.toString().getBytes(Charset.defaultCharset()));
			htmlUTF8 = URLEncoder.encode(htmlString, "UTF-8");
			// htmlUTF8 = new String(sb.toString().getBytes("gbk"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// return to String Formed
		return htmlUTF8;
	}

	/**
	 * 过滤除了p标签以外的html标签
	 * 
	 * @author scl
	 * @date 2020�?7�?1�?
	 * @param htmlStr
	 * @return
	 */
	public static String delHTMLTag(String htmlStr) {
		// 定义script的正则表达式，去除js可以防止注入
		String scriptRegex = "<script[^>]*?>[\\s\\S]*?<\\/script>";
		// 定义style的正则表达式，去除style样式，防止css代码过多时只截取到css样式代码
		String styleRegex = "<style[^>]*?>[\\s\\S]*?<\\/style>";
		// 定义HTML标签的正则表达式，去除标签，只提取文字内�?
		String htmlRegex = "(?!<(p|P|/p|/P).*?>)<.*?>";
		// 定义空格,回车,换行�?,制表�?
		String spaceRegex = "\\s*|\t|\r|\n";

		// 过滤script标签
		htmlStr = htmlStr.replaceAll(scriptRegex, "");
		// 过滤style标签
		htmlStr = htmlStr.replaceAll(styleRegex, "");
		// 过滤html标签
		htmlStr = htmlStr.replaceAll(htmlRegex, "");
		// 过滤空格�?
		htmlStr = htmlStr.replaceAll(spaceRegex, "");
		return htmlStr.trim(); // 返回文本字符�?
	}

	/**
	 * 过滤全部html标签
	 * 
	 * @param htmlStr
	 * @return
	 */
	public static String FilterHTML(String htmlStr) {
		if (htmlStr == null)
			return "";
		String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
		String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
		String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
		Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll(""); // 过滤script标签

		Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll(""); // 过滤style标签

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll(""); // 过滤html标签
		return htmlStr.trim();
	}

	/**
	 * 过滤html标签除了img标签
	 * 
	 * @param htmlStr
	 * @return
	 */
	public static String FilterHTMLWithImg(String htmlStr) {
		if (htmlStr == null)
			return "";
		String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
		String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
		String regEx_html = "<(?!img)[^>]*>"; // 定义HTML标签的正则表达式
		Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll(""); // 过滤script标签

		Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll(""); // 过滤style标签

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll(""); // 过滤html标签
		return htmlStr.trim();
	}

	/**
	 * 过滤蒙文发布中的特殊字体标记
	 * 
	 * @param htmlStr
	 * @return
	 */
	public static String FilterMongolTag(String htmlStr) {
		if (htmlStr == null)
			return "";

		Pattern pattern = Pattern.compile("<font ([f].*?)>");
		Matcher matcher = pattern.matcher(htmlStr);

		ArrayList<String> strs = new ArrayList<String>();
		while (matcher.find()) {
			strs.add(matcher.group(1));
		}
		for (String s : strs) {
			// System.out.println(s);
			htmlStr = htmlStr.replaceAll(s, "");
		}

		return htmlStr.trim();
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 */
	public static Date StrToDate(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date StrToDate(String str, String formatStr) {

		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	

	public static String TrimEnd(String newstr, String endstr) {
		String re = newstr;
		if (newstr.endsWith(endstr)) {
			re = newstr.substring(0, newstr.length() - endstr.length());
		}
		return re;
	}

	/**
	 * 计算是否在指定范围内
	 * 
	 * @param point1
	 * @param point2
	 * @param distance
	 * @return
	 */
	public static Boolean checkposition(double[] point1, double[] point2, double distance) {
		Boolean sMark = false;
		double cal_distence = GetShortDistance(point1, point2);
		if (cal_distence <= distance) {
			sMark = true;
		}
		return sMark;
	}

	// 百度地图距离计算
	static double DEF_PI = 3.14159265359; // PI
	static double DEF_2PI = 6.28318530712; // 2*PI
	static double DEF_PI180 = 0.01745329252; // PI/180.0
	static double DEF_R = 6370693.5; // radius of earth

	/**
	 * 百度地图计算距离（�?�用于近距离�?
	 * 
	 * @param point1
	 * @param point2
	 * @return
	 */
	public static double GetShortDistance(double[] point1, double[] point2) {
		double ew1, ns1, ew2, ns2;
		double dx, dy, dew;
		double distance;
		// 角度转换为弧�?
		ew1 = point1[0] * DEF_PI180;
		ns1 = point1[1] * DEF_PI180;
		ew2 = point2[0] * DEF_PI180;
		ns2 = point2[1] * DEF_PI180;
		// 经度�?
		dew = ew1 - ew2;
		// 若跨东经和西�?180 度，进行调整
		if (dew > DEF_PI)
			dew = DEF_2PI - dew;
		else if (dew < -DEF_PI)
			dew = DEF_2PI + dew;
		dx = DEF_R * Math.cos(ns1) * dew; // 东西方向长度(在纬度圈上的投影长度)
		dy = DEF_R * (ns1 - ns2); // 南北方向长度(在经度圈上的投影长度)
		// 勾股定理求斜边长
		distance = Math.sqrt(dx * dx + dy * dy);
		return distance;
	}

	/**
	 * 读取html中所有img标签的src�? 170804scl
	 * 
	 * @param htmlStr
	 * @return
	 */
	public static List<String> getImgSrc(String htmlStr) {
		String img = "";
		Pattern p_image;
		Matcher m_image;
		List<String> pics = new ArrayList<String>();
		String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
		p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
		m_image = p_image.matcher(htmlStr);
		while (m_image.find()) {
			img = img + "," + m_image.group();
			Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
			while (m.find()) {
				pics.add(m.group(1));
			}
		}
		return pics;
	}

	/**
	 * 判断String是否有�??
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNullOrEmpty(String value) {
		if (value == null || value.isEmpty())
			return true;
		else
			return false;
	}

	public static String readString(String str) {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new ByteArrayInputStream(str.getBytes(Charset.forName("utf8"))), Charset.forName("utf8")));
		String line = "";
		String result = "";
		try {
			while ((line = br.readLine()) != null) {
				result += line;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 是否是手机号�?
	 * 
	 * @param Input
	 * @return
	 */
	public static Boolean IsPhone(String Input) {
		if (Input == null || Input.isEmpty()) {
			return false;
		} else {
			Pattern p = Pattern.compile(
					"^((13[0-9]\\d{8})|(147\\d{8})|(15[0-3]\\d{8})|(15[5-9]\\d{8})|(17[6-9]\\d{8})|(18[0-9]\\d{8})|(193\\d{8}))$");
			Matcher m = p.matcher(Input);
			return m.matches();
		}
	}

	/**
	 * 是否是身份证
	 * 
	 * @param Input
	 * @return
	 */
	public static Boolean IsIDCard(String Input) {
		if (Input == null || Input.isEmpty()) {
			return false;
		} else {
			Pattern p = Pattern.compile(
					"(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$)");
			Matcher m = p.matcher(Input);
			return m.matches();
		}
	}

	/**
	 * 根据出生日期获得周岁
	 * 
	 * @param birthDay
	 * @return
	 */
	public static int getAge(Date birthDay) {
		Calendar cal = Calendar.getInstance();

		if (cal.before(birthDay)) {// 出生日期还未发生
			return 0;
		}
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthDay);

		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth)
					age--;
			} else {
				age--;
			}
		}
		return age;
	}

	public static List<Date> TimeToList(String begin, String end) {
		List<Date> lDate = new ArrayList<Date>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dBegin;
		try {
			dBegin = sdf.parse(begin);
			Date dEnd = sdf.parse(end);
			lDate.add(dBegin);
			Calendar calBegin = Calendar.getInstance();
			// 使用给定�? Date 设置�? Calendar 的时�?
			calBegin.setTime(dBegin);
			Calendar calEnd = Calendar.getInstance();
			// 使用给定�? Date 设置�? Calendar 的时�?
			calEnd.setTime(dEnd);
			// 测试此日期是否在指定日期之后
			while (dEnd.after(calBegin.getTime())) {
				// 根据日历的规则，为给定的日历字段添加或减去指定的时间�?
				calBegin.add(Calendar.DAY_OF_MONTH, 1);
				lDate.add(calBegin.getTime());
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return lDate;
	}

	public static String parseTime(String datdString) {
		// datdString = datdString.replace("GMT", "").replaceAll("\\(.*\\)",
		// "");
		// 将字符串转化为date类型，格�?2016-10-12
		SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss z", Locale.ENGLISH);
		Date dateTrans = null;
		try {
			dateTrans = format.parse(datdString);
			return new SimpleDateFormat("yyyy-MM-dd").format(dateTrans);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return datdString;
	}

	public static String parseTime2(String datdString) {
		if (datdString != null && !datdString.isEmpty()) {
			datdString = datdString.replace("GMT", "").replaceAll("\\(.*\\)", "");
			// 将字符串转化为date类型，格�?2016-10-12
			SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss z", Locale.ENGLISH);
			Date dateTrans = null;
			try {
				dateTrans = format.parse(datdString);
				return new SimpleDateFormat("yyyy-MM-dd").format(dateTrans);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
			return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
		}
		return datdString;
	}

	/*
	 * 中文转unicode编码
	 */
	public static String gbEncoding(final String gbString) {
		char[] utfBytes = gbString.toCharArray();
		String unicodeBytes = "";
		for (int i = 0; i < utfBytes.length; i++) {
			String hexB = Integer.toHexString(utfBytes[i]);
			if (hexB.length() <= 2) {
				hexB = "00" + hexB;
			}
			unicodeBytes = unicodeBytes + "\\u" + hexB;
		}
		return unicodeBytes;
	}

	/*
	 * unicode编码转中�?
	 */
	public static String decodeUnicode(final String dataStr) {
		int start = 0;
		int end = 0;
		final StringBuffer buffer = new StringBuffer();
		while (start > -1) {
			end = dataStr.indexOf("\\u", start + 2);
			String charStr = "";
			if (end == -1) {
				charStr = dataStr.substring(start + 2, dataStr.length());
			} else {
				charStr = dataStr.substring(start + 2, end);
			}
			char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串�??
			buffer.append(new Character(letter).toString());
			start = end;
		}
		return buffer.toString();
	}

	/**
	 * 日期格式字符串转换成时间�?
	 *
	 * @param dateStr 字符串日�?
	 * @param format  如：yyyy-MM-dd HH:mm:ss
	 *
	 * @return
	 */
	public static String Date2TimeStamp(String dateStr, String format) {
		try {
			return new SimpleDateFormat(format, Locale.CHINA).format(new Date(Long.parseLong(dateStr) * 1000));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 取得当前时间戳（精确到秒�?
	 *
	 * @return nowTimeStamp
	 */
	public static Long getNowTimeStamp() {
		long time = System.currentTimeMillis();
		String nowTimeStamp = String.valueOf(time / 1000);
		return Long.parseLong(nowTimeStamp);
	}

	/**
	 * 6位数字验证码
	 * 
	 * @author scl
	 * @date 2021�?1�?26�?
	 * @return
	 */
	public synchronized static String getCode() {
		StringBuffer code = new StringBuffer();
		int num;
		for (int i = 0; i < 6; i++) {
			num = (int) (Math.random() * 10);
			code.append(String.valueOf(num));
		}
		return code.toString();
	}

	/**
	 * 是否是email
	 * 
	 * @author scl
	 * @date 2021�?1�?26�?
	 * @param Input
	 * @return
	 */
	public static Boolean IsEmail(String Input) {
		if (Input == null || Input.isEmpty()) {
			return false;
		} else {
			Pattern p = Pattern.compile(
					"[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?");
			Matcher m = p.matcher(Input);
			return m.matches();
		}
	}

	/**
	 * 正则验证
	 * 
	 * @author scl
	 * @date 2021�?1�?26�?
	 * @param PatternStr 正则
	 * @param Input      输入
	 * @return
	 */
	public static Boolean isVerify(String PatternStr, String Input) {
		if (Input == null || Input.isEmpty()) {
			return false;
		} else {
			Pattern p = Pattern.compile(PatternStr);
			Matcher m = p.matcher(Input);
			return m.matches();
		}
	}

	/**
	 * 大小�?+数字 随机字符�? 不包括大写I,O小写o,l
	 * 
	 * @author scl
	 * @date 2021�?2�?26�?
	 * @param num
	 * @return
	 */
	public static String getRandomNum(int num) {
		int i;
		int count = 0;

		char[] str = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U',
				'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n', 'p', 'q', 'r',
				's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		int maxNum = str.length;
		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < num) {
			i = Math.abs(r.nextInt(maxNum));
			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}
		return pwd.toString();
	}

	/**
	 * 字符串是否是日期
	 * 
	 * @author scl
	 * @date 2021�?3�?3�?
	 * @param dateStr
	 * @param format
	 * @param args
	 * @return
	 */
	public static boolean isValidDate(String dateStr, String format, Date[] args) {
		boolean convertSuccess = true;
		SimpleDateFormat formatDateTime = new SimpleDateFormat(format);
		try {
			args[0] = formatDateTime.parse(dateStr);
			convertSuccess = true;
			return convertSuccess;
		} catch (ParseException e) {
			// 如果throw java.text.ParseException或�?�NullPointerException，就说明格式不对
			convertSuccess = false;
		}
		return convertSuccess;
	}

	public static Map<String, Object> objecttomap(Object obj) throws IllegalArgumentException, IllegalAccessException {
		Map<String, Object> map = new HashMap<String, Object>();
		Class<?> clazz = obj.getClass();
		for (Field field : clazz.getDeclaredFields()) {
			field.setAccessible(true);
			String fieldname = field.getName();
			Object value = field.get(obj);
			map.put(fieldname, value);
		}
		return map;
	}
}
