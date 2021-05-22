package com.hwj.product.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hwj.product.model.Company;
import com.hwj.product.model.User;
import com.hwj.product.service.CompanyService;
import com.hwj.product.service.UserService;
import com.hwj.product.tools.Base;
import com.hwj.product.tools.Common;
import com.hwj.product.tools.DesUtil;

import cn.hutool.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
@RequestMapping("/manage/company")
public class CompanyController extends BaseController {
	@Autowired
	CompanyService companyService;
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public ModelAndView page_list() {
		ModelAndView mav = new ModelAndView("/manage/company/list");
		String roleId = Base.getCookieValueByName("user", request, 2);
		String userId = Base.getCookieValueByName("user", request, 0);
		User user = userService.getModel(userId);
		boolean isDepartment=false;
		if(user!=null) {
			if(user.getRoleId()==2) {
				int departmentId[] = {12,19,20,21,22,23,24,25};
				for (int i : departmentId) {
					if(i==user.getDepartmentId())
					{
						isDepartment = true;
						break;
					}
				}
			}
			else if(user.getRoleId()==3) {
				if(user.getDepartmentId()==2)
					isDepartment = true;
			}
		}
		if(roleId.equals("4"))
			mav.addObject("power", false);
		else 
			mav.addObject("power", true);
		if(roleId.equals("1")||roleId.equals("3")) {
			mav.addObject("isRegion", true);
		}else {
			mav.addObject("isRegion", false);
		}
		mav.addObject("isDepartment", isDepartment);
		return mav;
	}
	@RequestMapping(value = "/edit",method = RequestMethod.GET)
	public ModelAndView page_edit() {
		ModelAndView mav=new ModelAndView("/manage/company/edit");
		try {
			String id = request.getParameter("id");
			Company model=companyService.getModel(id);
			if (model != null ) {
				model.setPhone(DesUtil.decrypt(keyStr, model.getPhone()));
				model.setUserIdCard(DesUtil.decrypt(keyStr, model.getUserIdCard()));
				model.setCode(DesUtil.decrypt(keyStr, model.getCode()));
				model.setUserPhone(DesUtil.decrypt(keyStr, model.getUserPhone()));
			}
			mav.addObject("model", model);
		} catch (Exception e) {
			// TODO: handle exception
			mav.addObject("model", null);
		}
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
			String companyId=request.getParameter("companyId");
			String userId = Base.getCookieValueByName("user", request, 0);
			User user = userService.getModel(userId);
			String governmentId="";
			if (!Common.isNullOrEmpty(searchParams)) {
				JSONObject jsonObj = new JSONObject(searchParams);
				name = (String) jsonObj.get("name");
				governmentId = (String) jsonObj.get("governmentId");
			}
			PageIndex=Integer.parseInt(pageIndex);
			PageSize=Integer.parseInt(pageSize);
			int[] args = {0};
			String cond = "";
			if(!isNullOrEmpty(name)) {
				cond += " and name like '%"+name+"%'";
			}
			if(!isNullOrEmpty(companyId))
			{
				cond += " and id="+companyId;
			}
			if(!isNullOrEmpty(governmentId))
				cond+=" and governmentId="+governmentId;
			if(user.getRoleId()==4) {
				cond += " and id ="+user.getCompanyId();
			}
			String FieldStr="*";
			String tableName="(select `company`.*,`government`.`name` as government from `company` left join `government` on (`company`.governmentId = `government`.id))t";
			String param="1=1 and isDelete=1";
			param+=cond;
			String orderStr="";
			List list = companyService.getCustomList(PageIndex, PageSize, FieldStr, tableName, param, orderStr, args);
			for (Object object : list) {
				Map<String, Object> map = (Map<String, Object>)object;
				//String phone = DesUtil.decrypt(keyStr, map.get("phone").toString()); 
				String code =  DesUtil.decrypt(keyStr, map.get("code").toString()); 
				//String userIdCard = DesUtil.decrypt(keyStr, map.get("userIdCard").toString());
				//map.put("phone", phone);
				map.put("code", code);
				//map.put("userIdCard", userIdCard);
			}
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
			
			companyService.delete(ids);
			return ResponseModel(true, "ok", null);
		} catch (Exception e) {
		// TODO: handle exception
			return ResponseModel(false,e.getMessage(), null);
		}
	}
	
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(Company model){
		try {
			if(isNullOrEmpty(model.getName()))
				throw new Exception("名称为空");
			String[] userNames = model.getUserName().split("\\,");
			String[] userPhones = model.getUserPhone().split("\\,");
			String[] userIdCards = model.getUserIdCard().split("\\,");
			if(userNames.length!=userPhones.length && userPhones.length!=userIdCards.length)
				throw new Exception("联系人信息不正常");
			if(!isNullOrEmpty(model.getPhone())&& model.getPhone().length()!=8)
				throw new Exception("请填写8位固定电话");
			model.setPhone(DesUtil.encrypt(keyStr, model.getPhone()));
			model.setUserIdCard(DesUtil.encrypt(keyStr, model.getUserIdCard()));
			model.setUserPhone(DesUtil.encrypt(keyStr, model.getUserPhone()));
			model.setCode(DesUtil.encrypt(keyStr, model.getCode()));
			if(model.getId()==0)
			{
				int id = companyService.add2(model);
				if(id!=0 && !isNullOrEmpty(model.getUserName())) {
					for(int i=0;i<userNames.length;i++) {
						User user = new User();
						user.setName(userNames[i]);
						user.setNameCN(userNames[i]);
						user.setActive(1);
						user.setPhone(DesUtil.encrypt(keyStr, userPhones[i]));
						user.setIdCard(DesUtil.encrypt(keyStr, userIdCards[i]));
						user.setCompanyId(id);
						user.setRoleId(4);
						//user.setNativePlace(nativePlace);
						userService.add(user);
					}
				}
				
			}else
			{
				companyService.modify(model);
				userService.deleteByCompanyId(model.getId());
				if(!isNullOrEmpty(model.getUserName())) {
					for (int i = 0; i < userNames.length; i++) {
						User user = new User();
						user.setName(userNames[i]);
						user.setNameCN(userNames[i]);
						user.setActive(1);
						user.setPhone(DesUtil.encrypt(keyStr, userPhones[i]));
						user.setIdCard(DesUtil.encrypt(keyStr, userIdCards[i]));
						user.setCompanyId(model.getId());
						user.setRoleId(4);
						// user.setNativePlace(nativePlace);
						userService.add(user);
					}
				}
			}
			
			return ResponseModel(true, "ok", null);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseModel(false, e.getMessage(), null);
		}
	}
	
	@RequestMapping(value = "/companyList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getList(){
		try {
			String userId=Base.getCookieValueByName("user", request, 0);
			User user = userService.getModel(userId);
			if(user==null)
				throw new Exception("请先登录");
			List list = new ArrayList();
			if(user.getRoleId() == 4 || user.getRoleId() == 5)
			{
				list = companyService.getList(String.valueOf(user.getCompanyId()));
			}else {
				list = companyService.getList();
			}
			
			return ResponseModel(true, "ok", list);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseModel(false, e.getMessage(), null);
		}
	}
	
}
