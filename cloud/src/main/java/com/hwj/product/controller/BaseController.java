package com.hwj.product.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

public class BaseController {
	public Properties properties;
	
	public int PageIndex = 1;
	public int PageSize = 10;
	public String deleteUids = "";
	

	
	/*
	@ModelAttribute
	public final void init(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String URI = request.getRequestURI();
		if(URI.contains("/manage/")){
			if(Base.checkLoginStatus("redPlat", request)){
				String memberId = Base.getCookieValueByName("redPlat", request,0);
				//String sessionId = request.getRequestedSessionId();
				String sessionId = Base.getCookieValueByName("JSESSIONID", request);
				String localIp = getIP(request);
				if(memberId!=null&&!memberId.isEmpty()){
					LoginMember lm = loginMemberService.GetLoginMemberById(memberId);
					if(lm != null){
						if(!sessionId.equals(lm.getSessionId())){
							if(!checkIP(localIp, lm.getLoginIp())){
								Base.deleteCookie("redPlat", request, response);
								response.sendRedirect(request.getContextPath() + "/err/out.do"); // 跳到登录页面
							}
						}
					}
				}
			}else{
				response.sendRedirect(request.getContextPath() + "/welcome/login.do"); // 跳到登录页面
			}
			/*if (!Base.checkLoginStatus("redPlat", request)) {
				System.out.println(request.getContextPath() + "/welcome/login.do");
				response.sendRedirect(request.getContextPath() + "/welcome/login.do"); // 跳到登录页面
			}*/
		//}
	//}*/

	/**
	 * 获取redPlatcookie
	 * 
	 * @param redPlatVal
	 * @return
	 */
	public String[] getRedPlatArr(String redPlatVal) {
		if (isNullOrEmpty(redPlatVal))
			return null;
		String[] valArr = redPlatVal.split("#");
		return valArr;
	}

	/**
	 * 判断String是否有值
	 * 
	 * @param value
	 * @return
	 */
	public boolean isNullOrEmpty(String value) {
		if (value == null || value.isEmpty())
			return true;
		else
			return false;
	}

	/**
	 * 判断Integer是否有值
	 * 
	 * @param value
	 * @return
	 */
	public boolean isNullOrEmpty(Integer value) {
		if (value == null)
			return true;
		else
			return false;
	}
	/**
	 * 判断Double是否有值
	 * @param value
	 * @return
	 */
	public boolean isNullOrEmpty(Double value){
		if (value == null)
			return true;
		else
			return false;
	}
	
	/**
	 * 判断Float是否有值
	 * @param value
	 * @return
	 */
	public boolean isNullOrEmpty(Float value){
		if (value == null)
			return true;
		else
			return false;
	}
	
	private String getIpConfig() {
		Properties prop = new Properties();
		InputStream inputStream = this.getClass().getResourceAsStream("ipconfig.properties");
		try {
			prop.load(inputStream);
			String ip = prop.getProperty("ip");
			inputStream.close();
			return ip;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public boolean checkIP(String ip1,String ip2){
		String setIps = getIpConfig();
		String[] ips = setIps.split(";");
		for(String ip : ips){
			if(ip1.equals(ip)){
				return true;
			}
		}
		String[] strs1 = ip1.split("\\.");
		String[] strs2 = ip2.split("\\.");
		String ipWeb1 = strs1[0]+"."+strs1[1]+"."+strs1[2];
		String ipWeb2 = strs2[0]+"."+strs2[1]+"."+strs2[2];
		if(ipWeb1.equals(ipWeb2)){
			return true;
		}
		return false;
	}
	
	private String getIP(HttpServletRequest request) {
		String ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
															// = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}
	
	public Map<String, Object> ResponseModel(boolean b,String message,Object data){
		Map<String, Object> map = new HashMap<>();
		map.put("status", b);
		map.put("msg",message);
		map.put("data", data);
		return map;
	}
	public Map<String, Object> ResponseModel(boolean b,String message,int count,Object data){
		Map<String, Object> map = new HashMap<>();
		map.put("status", b);
		map.put("msg",message);
		map.put("count",count);
		map.put("data", data);
		return map;
	}
	
	/**
	 * 获取配置文件属性
	 * @return
	 */
	public Properties getFileConfig(String fileName) {
		Properties prop = new Properties();
		InputStream inputStream = this.getClass().getResourceAsStream(fileName + ".properties");

		try {
			prop.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;
	}
}
