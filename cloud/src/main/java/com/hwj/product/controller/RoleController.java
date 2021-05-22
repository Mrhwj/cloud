package com.hwj.product.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hwj.product.model.Role;
import com.hwj.product.service.RoleService;
import com.hwj.product.tools.Base;
import com.hwj.product.tools.Common;

import cn.hutool.json.JSONObject;

/** 
* @author 作者 xbm: 
* @version 创建时间：2021年1月21日 下午8:43:38 
* 类说明 
*/
@Controller
@RequestMapping("/manage/role")
public class RoleController extends BaseController {
	@Autowired
	RoleService roleService;
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public ModelAndView page_list() {
		return new ModelAndView("/manage/role/list");
	}
	@RequestMapping(value = "/edit",method = RequestMethod.GET)
	public ModelAndView page_edit() {
		String id = request.getParameter("id");
		Role model=roleService.getModel(id);
		
		ModelAndView mav=new ModelAndView("/manage/role/edit");
		mav.addObject("model", model);
		return mav;
	}
	
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> list(){
		try {
			String pageIndex = request.getParameter("pageIndex");
			String pageSize = request.getParameter("pageSize");
			String searchParams = request.getParameter("searchParams");
			String name="";
			if (!Common.isNullOrEmpty(searchParams)) {
				JSONObject jsonObj = new JSONObject(searchParams);
				name = (String) jsonObj.get("name");
				
			}
			PageIndex=Integer.parseInt(pageIndex);
			PageSize=Integer.parseInt(pageSize);
			int[] args = {0};
			String cond = "";
			if(!isNullOrEmpty(name)) {
				cond += " and name like '%"+name+"%'";
			}
			String FieldStr="*";
			String tableName="`role`";
			String param="1=1 and isDelete=1";
			param+=cond;
			String orderStr="";
			List list = roleService.getCustomList(PageIndex, PageSize, FieldStr, tableName, param, orderStr, args);
			return ResponseModel(true, "ok", args[0] ,list);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseModel(false, e.getMessage(), null);
		}
	}
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> del(){
		try {
			String ids=request.getParameter("ids");
			if (ids == "" || ids == null)
				throw new Exception("ids为空");
			
			roleService.delete(ids);
			return ResponseModel(true, "ok", null);
		} catch (Exception e) {
		// TODO: handle exception
			return ResponseModel(false,e.getMessage(), null);
		}
	}
	
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(Role model){
		try {
			if(isNullOrEmpty(model.getName()))
				throw new Exception("角色名称为空");
			
			if(model.getId()==0)
			{
				roleService.add(model);
			}else
			{
				roleService.modify(model);
			}
			
			return ResponseModel(true, "ok", null);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseModel(false, e.getMessage(), null);
		}
	}
	
	@RequestMapping(value = "/roleList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getList(){
		try {
			String roleId=Base.getCookieValueByName("user", request, 2);
			List list = new ArrayList();
			if(roleId.equals("1"))
				list = roleService.getList();
			else
				list = roleService.getList(roleId);
			
			return ResponseModel(true, "ok", list);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseModel(false, e.getMessage(), null);
		}
	}
}
