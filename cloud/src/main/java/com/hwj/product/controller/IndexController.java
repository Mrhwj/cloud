package com.hwj.product.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.annotation.JsonAppend.Attr;
import com.hwj.product.model.User;
import com.hwj.product.service.UserService;
import com.hwj.product.tools.Base;

import cn.hutool.json.JSONObject;

/** 
* @author 作者 xbm: 
* @version 创建时间：2021年1月26日 下午2:38:41 
* 类说明 
*/
@Controller
@RequestMapping("/manage")
public class IndexController extends BaseController {
	@Autowired
	UserService userService;

	
	@RequestMapping("welcome")
	public ModelAndView page_index()  {
		ModelAndView mav = new ModelAndView("/manage/welcome");
		try {

			String userId = Base.getCookieValueByName("user", request, 0);
			User user = userService.getModel(userId);
			
			mav.addObject("user", user);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return mav;
		
	}
	
}
