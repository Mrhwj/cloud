package com.hwj.product.tools;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;


//import com.zjcz.product.jar.LoveTools;

public class Common {
	private static String BASIC_FILE_PATH = "/files/";

	/*
	 * 日期格式化
	 */
	public static String dateToString(Date dt) {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String cdt = formatter.format(dt);

		return cdt;
	}

	/*
	 * 日期格式化--自定义
	 */
	public static String dateToStringFormat(Date dt, String format) {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat(format);
		String cdt = formatter.format(dt);

		return cdt;
	}

	/*
	 * 解决JSP 中文乱码的问题
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
		ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
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
					// 日期类型格式话
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
				// System.out.println(md.getColumnLabel(i));
				// LoveTools.ConsolePrint(Integer.toString(columntype) + "###" +
				// md.getColumnName(i));
				// rowData.put(md.getColumnName(i), tmpValue);获得的是原始的列名
				rowData.put(md.getColumnLabel(i), tmpValue);// 当前的列名
				// rowData.put(md.getColumnName(i), rs.getObject(i).toString());
			}
			list.add(rowData);
			// System.out.println("list:" + list.toString());
		}
		return list;
	}

	/**
	 * 比较两个日期相差的天数 不考虑时、分、秒
	 * 
	 * @param fDate
	 *            先
	 * @param oDate
	 *            后
	 * @return
	 */
	public static int daysOfTwo(Date fDate, Date oDate) {
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.setTime(fDate);
		int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
		aCalendar.setTime(oDate);
		int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
		return day2 - day1;
	}

	/**
	 * 截取字符串函数
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
		int charlen = GetLength(Str, Num);// 根据字节数获得要截取的真正长度
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
	 * 过滤html标签
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
	 * 过滤html标签
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
	 * 百度地图计算距离（适用于近距离）
	 * 
	 * @param point1
	 * @param point2
	 * @return
	 */
	public static double GetShortDistance(double[] point1, double[] point2) {
		double ew1, ns1, ew2, ns2;
		double dx, dy, dew;
		double distance;
		// 角度转换为弧度
		ew1 = point1[0] * DEF_PI180;
		ns1 = point1[1] * DEF_PI180;
		ew2 = point2[0] * DEF_PI180;
		ns2 = point2[1] * DEF_PI180;
		// 经度差
		dew = ew1 - ew2;
		// 若跨东经和西经180 度，进行调整
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
	 * 读取html中所有img标签的src值 170804scl
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

	public static String ResetUuids(String uuids) {
		String[] ids = uuids.split(",");
		String newId = "";
		String flag = "";
		for (String s : ids) {
			newId += flag + "'" + s + "'";
			flag = ",";
		}
		return newId;
	}

	/**
	 * 判断String是否有值
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

	/**
	 * 判断是否为整数
	 * 
	 * @author scl
	 * @date 2020年10月13日
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
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
	 * 是否是手机号码
	 * 
	 * @param Input
	 * @return
	 */
	public static Boolean IsPhone(String Input) {
		if (Input == null || Input.isEmpty()) {
			return false;
		} else {
			//添加新的号码段
			Pattern p = Pattern.compile(
					"^((13[0-9]\\d{8})|(147\\d{8})|(15[0-3]\\d{8})|(15[5-9]\\d{8})|(17[0-9]\\d{8})|(18[0-9]\\d{8})|(19[0-9]\\d{8}))$");
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
			// 使用给定的 Date 设置此 Calendar 的时间
			calBegin.setTime(dBegin);
			Calendar calEnd = Calendar.getInstance();
			// 使用给定的 Date 设置此 Calendar 的时间
			calEnd.setTime(dEnd);
			// 测试此日期是否在指定日期之后
			while (dEnd.after(calBegin.getTime())) {
				// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
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
		// 将字符串转化为date类型，格式2016-10-12
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
			// 将字符串转化为date类型，格式2016-10-12
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

	/**
	 * 输出所有的request 参数到控制台 by chenxj@20180923
	 * 
	 * @param request
	 */
	@SuppressWarnings("rawtypes")
	public static void printRequestparam(HttpServletRequest request) {
		Enumeration enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String paraName = (String) enu.nextElement();
			System.out.println(paraName + ": " + request.getParameter(paraName));
		}
	}

	/**
	 * 通过身份证号码获取出生日期、性别、年龄
	 * 
	 * @param certificateNo
	 * @return 返回的出生日期格式：1990-01-01 性别格式：F-女，M-男
	 */
	public static Map<String, String> getBirAgeSex(String certificateNo) {
		String birthday = "";
		String age = "";
		String sexCode = "";

		int year = Calendar.getInstance().get(Calendar.YEAR);
		char[] number = certificateNo.toCharArray();
		boolean flag = true;
		if (number.length == 15) {
			for (int x = 0; x < number.length; x++) {
				if (!flag)
					return new HashMap<String, String>();
				flag = Character.isDigit(number[x]);
			}
		} else if (number.length == 18) {
			for (int x = 0; x < number.length - 1; x++) {
				if (!flag)
					return new HashMap<String, String>();
				flag = Character.isDigit(number[x]);
			}
		}
		if (flag && certificateNo.length() == 15) {
			birthday = "19" + certificateNo.substring(6, 8) + "-" + certificateNo.substring(8, 10) + "-"
					+ certificateNo.substring(10, 12);
			sexCode = Integer.parseInt(certificateNo.substring(certificateNo.length() - 3, certificateNo.length()))
					% 2 == 0 ? "F" : "M";
			age = (year - Integer.parseInt("19" + certificateNo.substring(6, 8))) + "";
		} else if (flag && certificateNo.length() == 18) {
			birthday = certificateNo.substring(6, 10) + "-" + certificateNo.substring(10, 12) + "-"
					+ certificateNo.substring(12, 14);
			sexCode = Integer.parseInt(certificateNo.substring(certificateNo.length() - 4, certificateNo.length() - 1))
					% 2 == 0 ? "F" : "M";
			age = (year - Integer.parseInt(certificateNo.substring(6, 10))) + "";
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("birthday", birthday);
		map.put("age", age);
		map.put("sexCode", sexCode);
		return map;
	}

	/*
	 * public static String SavePostFiles(MultipartHttpServletRequest
	 * multipartRequest, String folderName) throws IOException { String
	 * filePathStr = ""; // 保存文件 List<MultipartFile> postFiles =
	 * multipartRequest.getFiles("file");
	 * 
	 * for (MultipartFile multipartFile : postFiles) { String path =
	 * multipartRequest.getSession().getServletContext().getRealPath(
	 * BASIC_FILE_PATH); String suffix = multipartFile.getOriginalFilename()
	 * .substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
	 * String fileSaveName = path + folderName + "/" + dateToStringFormat(new
	 * Date(), "yyyyMMddHHmmss") + "." + suffix; File folder = new File(path +
	 * folderName); if (!folder.exists()) { folder.mkdirs(); }
	 * Files.write(Paths.get(fileSaveName), multipartFile.getBytes());
	 * filePathStr = (filePathStr == "") ? fileSaveName.replace(path,
	 * BASIC_FILE_PATH) : filePathStr + "|" + fileSaveName.replace(path,
	 * BASIC_FILE_PATH); } return filePathStr; }
	 */

	/**
	 * 新的文件保存 by chenxj @20201115
	 * 
	 * @param files
	 * @param folderName
	 * @return
	 * @throws IOException
	 */
	public static String SavePostFiles(MultipartFile[] files, String folderName) throws IOException {
		String filePathStr = "";
		// 保存文件

		for (MultipartFile multipartFile : files) {
			String suffix = multipartFile.getOriginalFilename()
					.substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
			String fileSaveName = folderName + "/" + dateToStringFormat(new Date(), "yyyyMMddHHmmss") + "." + suffix;

			File folder = new File(folderName);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			if (!folder.canWrite()) {
				throw new IOException("上传目录没有写权限");
			}
			Files.write(Paths.get(fileSaveName), multipartFile.getBytes());

			// System.out.println("fileSaveName:" + fileSaveName);
			// System.out.println("filenameArr:" +
			// fileSaveName.replace(BASIC_FILE_PATH,"#"));

			String[] filenameArr = fileSaveName.replace("\\", "/").replace(BASIC_FILE_PATH, "#").split("#");
			filePathStr = (filePathStr == "") ? BASIC_FILE_PATH + filenameArr[1]
					: filePathStr + "|" + BASIC_FILE_PATH + filenameArr[1];
		}
		return filePathStr;
	}
	
	/**
	 * 新的文件保存 
	 * 
	 * @param files
	 * @param folderName
	 * @return
	 * @throws IOException
	 */
	public static String SavePostFiles(MultipartFile[] files, String folderName,String basePath) throws IOException {
		String filePathStr = "";
		// 保存文件

		for (MultipartFile multipartFile : files) {
			String suffix = multipartFile.getOriginalFilename()
					.substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
			String fileSaveName = folderName + "/" + dateToStringFormat(new Date(), "yyyyMMddHHmmss") + "." + suffix;

			File folder = new File(folderName);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			if (!folder.canWrite()) {
				throw new IOException("上传目录没有写权限");
			}
			Files.write(Paths.get(fileSaveName), multipartFile.getBytes());

			// System.out.println("fileSaveName:" + fileSaveName);
			// System.out.println("filenameArr:" +
			// fileSaveName.replace(BASIC_FILE_PATH,"#"));

			String[] filenameArr = fileSaveName.replace("\\", "/").replace(basePath, "#").split("#");
			filePathStr = (filePathStr == "") ? basePath + filenameArr[1]
					: filePathStr + "|" + basePath + filenameArr[1];
		}
		return filePathStr;
	}

	/**
	 * map 转JSON 字符串 by chenxj@20201008
	 * 
	 * @param map
	 * @return
	 */
	public static String getJSONStringFromMap(Map<String, String> map) {
		String retJSON = "";
		for (String key : map.keySet()) {
			retJSON = (retJSON == "") ? key + ":'" + map.get(key) + "'"
					: retJSON + "," + key + ":'" + map.get(key) + "'";
		}
		return "{" + retJSON + "}";
	}

	/**
	 * 获取季度
	 * 
	 * @author scl
	 * @date 2020年11月9日
	 * @param c
	 * @return
	 */
	public static int getQuarter(Calendar c) {
		int m = c.get(Calendar.MONTH) + 1;
		int q = m % 3 == 0 ? m / 3 : (m / 3 + 1);
		return q;
	}

	/**
	 * 当前月最后一天
	 * 
	 * @author scl
	 * @date 2020年11月16日
	 * @return
	 */
	public static Date getMonthEndTime() {
		Calendar cal = Calendar.getInstance();
		Date now = null;
		try {
			int lastDay = cal.getActualMaximum(Calendar.DATE);
			// 设置日历中月份的最大天数
			cal.set(Calendar.DAY_OF_MONTH, lastDay);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			now = cal.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/****
	 * 当前季度最后一天
	 * 
	 * @author scl
	 * @date 2020年11月16日
	 * @return
	 */
	public static Date getCurrentQuarterEndTime() {
		Calendar c = Calendar.getInstance();
		int currentMonth = c.get(Calendar.MONTH) + 1;
		Date now = null;
		try {
			if (currentMonth >= 1 && currentMonth <= 3) {
				c.set(Calendar.MONTH, 2);
				c.set(Calendar.DATE, 31);
			} else if (currentMonth >= 4 && currentMonth <= 6) {
				c.set(Calendar.MONTH, 5);
				c.set(Calendar.DATE, 30);
			} else if (currentMonth >= 7 && currentMonth <= 9) {
				c.set(Calendar.MONTH, 8);
				c.set(Calendar.DATE, 30);
			} else if (currentMonth >= 10 && currentMonth <= 12) {
				c.set(Calendar.MONTH, 11);
				c.set(Calendar.DATE, 31);
			}
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			now = c.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}
	
	/**
	 * 转map
	 * @param rs
	 * @return
	 * @throws SQLException
	 * @author 作者 xbm: 
	 * @version 创建时间：2021年1月5日 下午6:38:17 
	 */
	public  Map<String, String> ResulToMap(ResultSet rs)  
            throws SQLException {  
        Map<String, String> hm = new HashMap<String, String>();  
        ResultSetMetaData rsmd = rs.getMetaData();  
        int count = rsmd.getColumnCount(); 
        if(rs.next()) {
            for (int i = 1; i <= count; i++) {  
                String key = rsmd.getColumnName(i);  
                String value = rs.getString(i);  
                hm.put(key, value);  
            }  
            return hm;  
        }
        return null;
    }
}
