package com.hwj.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.hwj.product.model.ResponseModel;
import com.hwj.product.service.ProductDetailService;

@Controller
@RequestMapping(value="manage/detail")
public class ProductDetailController extends BaseController{
	
	@Autowired
	ProductDetailService detailService;

	@RequestMapping(value="listPage",method=RequestMethod.GET)
	public ModelAndView ListPage(){
		ModelAndView mv = new ModelAndView("manage/productDetail/list");
		return mv;
	}
	
	@RequestMapping(value="list",method=RequestMethod.POST)
	public @ResponseBody ResponseModel getList(){
		ResponseModel rm = null;
		try {
			String pageIndex = request.getParameter("pageIndex");
			String pageSize = request.getParameter("pageSize");
			String name = request.getParameter("name");
			
			int[] args = {0};
			String cond = "";
			if(!isNullOrEmpty(name)){
				cond += " and a.name like '%"+name+"%'";
			}
			
			List list = detailService.GetList(Integer.parseInt(pageIndex), Integer.parseInt(pageSize), args, cond);
			
			rm = new ResponseModel(true, "获取成功", list, args[0]);
		} catch (Exception e) {
			e.printStackTrace();
			rm = new ResponseModel(false, "获取失败"+e.getMessage(), null, 0);
		}
		return rm;
	}
}
