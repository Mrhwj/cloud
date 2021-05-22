package com.hwj.product.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hwj.product.model.User;
import com.hwj.product.service.UserService;
import com.hwj.product.tools.Base;
import com.hwj.product.tools.CaptchaUtil;
import com.hwj.product.tools.Common;
import com.hwj.product.tools.DesUtil;



@Controller
public class CommonController extends BaseController {
	
	@Autowired
	UserService userService;

	@RequestMapping(value = "/manage/loginPage", method = RequestMethod.GET)
	public String page_loginPage() {
		return "redirect:manage/login.do";
	}
	
	@RequestMapping(value = "/manage/login", method = RequestMethod.GET)
	public ModelAndView page_login() {
		if(Base.checkLoginStatus("user", request)) {
	    	ModelAndView mv=new ModelAndView("/manage/index");
	    	mv.addObject("userId", Base.getCookieValueByName("user", request, 0));
	    	mv.addObject("name", Base.getCookieValueByName("user", request, 1));
	    	return mv;
		}else
		{
			return new ModelAndView("/manage/login");
		}
	}
	
	@RequestMapping(value = "/manage/login", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login(HttpServletResponse response) {
		try {
			String name = request.getParameter("name");
			String pwd = request.getParameter("pwd");
			String code = request.getParameter("code");
			String sessionCode = request.getSession().getAttribute("code").toString();
			if (isNullOrEmpty(name))
				throw new Exception("用户名为空");
			else if (isNullOrEmpty(pwd))
				throw new Exception("密码为空");
			else if (isNullOrEmpty(code))
				throw new Exception("验证码为空");
			else if (!code.equalsIgnoreCase(sessionCode))
				throw new Exception("验证码不正确");
			else {
				User user = userService.Login(name, Common.MD5(pwd, 16));
				if (user == null)
					user =  userService.phoneLogin(DesUtil.encrypt(keyStr,name), Common.MD5(pwd, 16));
				if (user == null)
					throw new Exception("用户名或密码不正确");
					
				String value = user.getId()+"#"+user.getName()+"#"+user.getRoleId()+"#"+user.getDepartmentId();
				Cookie cookie = new Cookie("user", Common.escape(value));// 设置cookie值
				
				cookie.setPath("/");
				response.addCookie(cookie);
				
			}
			return ResponseModel(true, "ok", null);
		} catch (Exception e) {
			return ResponseModel(false, "err," + e.getMessage(), null);
		}

	}
	
	@RequestMapping(value = "/manage/index", method = RequestMethod.GET)
	public ModelAndView page_index() {
	    if(Base.checkLoginStatus("user", request)) {
	    	ModelAndView mv=new ModelAndView("/manage/index");
	    	mv.addObject("userId", Base.getCookieValueByName("user", request, 0));
	    	mv.addObject("name", Base.getCookieValueByName("user", request, 1));
	    	return mv;
		}else
		{
			ModelAndView mv=new ModelAndView("/manage/login");
			return mv;
		}
	}
	
	@RequestMapping(value = "/manage/loginOut", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loginOut(HttpServletResponse response) {
		try {
			Base.deleteCookie("user", request, response);
		
			return ResponseModel(true, "ok", null);
		} catch (Exception e) {
		// TODO: handle exception
			return ResponseModel(false, "err," + e.getMessage(), null);
		}
	}
	
	@RequestMapping(value = "manage/captcha", method = RequestMethod.GET)
	@ResponseBody
	public void captcha(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CaptchaUtil.outputCaptcha(request, response);
	}
	
}
