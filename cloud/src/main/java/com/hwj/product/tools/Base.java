package com.hwj.product.tools;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 
* @author 作者 xbm: 
* @version 创建时间：2021年1月5日 下午5:40:50 
* 类说明 
*/
public class Base {
	/*
	 * 检查登录状态
	 */
	public static boolean checkLoginStatus(String cookieName, HttpServletRequest request) {
		Cookie cookies[] = request.getCookies();
		Cookie sCookie = null;
		if (cookies == null) {
			return false;
		}
		for (int i = 0; i < cookies.length; i++) {
			sCookie = cookies[i];
			if (sCookie != null) {
				if (cookieName.equals(sCookie.getName())) {
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * 删除cookie
	 */
	public static void deleteCookie(String cookieName, HttpServletRequest request, HttpServletResponse response) {
		Cookie cookies[] = request.getCookies();
		Cookie sCookie = null;
		for (int i = 0; i < cookies.length; i++) {
			sCookie = cookies[i];
			if (sCookie != null) {
				if (cookieName.equals(sCookie.getName())) {
					sCookie.setMaxAge(0);
					sCookie.setPath("/");
					response.addCookie(sCookie);
				}
			}
		}
	}
	/**
	 * 根据名字找到cookie 的对应值
	 * 
	 * @param cookieName
	 * @param request
	 * @param index
	 * @return 平台登录Cookie
	 */
	@SuppressWarnings("deprecation")
	public static String getCookieValueByName(String cookieName, HttpServletRequest request, int index) {
		Cookie cookies[] = request.getCookies();
		Cookie sCookie = null;
		String cookieValue = "";
		try {
			for (int i = 0; i < cookies.length; i++) {
				sCookie = cookies[i];
				if (sCookie != null) {
					if (cookieName.equals(sCookie.getName())) {
						String[] sArr =Common.unescape(sCookie.getValue()).split("#");
						cookieValue = sArr[index];
						break;
					}
				}
			}
		} catch (Exception e) {
			cookieValue = "";
		}
		return cookieValue;
	}

	/**
	 * 根据名字找到cookie 的对应值
	 * 
	 * @param cookieName
	 * @param request
	 * @return
	 */
	public static String getCookieValueByName(String cookieName, HttpServletRequest request) {
		Cookie cookies[] = request.getCookies();
		Cookie sCookie = null;
		String cookieValue = "";
		try {
			for (int i = 0; i < cookies.length; i++) {
				sCookie = cookies[i];
				if (sCookie != null) {
					if (cookieName.equals(sCookie.getName())) {
						cookieValue = sCookie.getValue();
						break;
					}
				}
			}
		} catch (Exception e) {
			cookieValue = "";
		}
		return cookieValue;
	}

	/**
	 * 根据名字获取cookie
	 * 
	 * @param request
	 * @param name
	 *            cookie名字
	 * @return
	 */
	public static Cookie getCookieByName(HttpServletRequest request, String name) {
		Map<String, Cookie> cookieMap = ReadCookieMap(request);
		if (cookieMap.containsKey(name)) {
			Cookie cookie = (Cookie) cookieMap.get(name);
			return cookie;
		} else {
			return null;
		}
	}

	/**
	 * 将cookie封装到Map里面
	 * 
	 * @param request
	 * @return
	 */
	private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}

}
