package com.hwj.product.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hwj.product.model.User;
import com.hwj.product.service.RoleService;
import com.hwj.product.service.UserService;
import com.hwj.product.tools.Base;
import com.hwj.product.tools.Common;
import com.hwj.product.tools.DesUtil;
import com.hwj.product.tools.NativePlace;

import cn.hutool.core.util.IdcardUtil;
import cn.hutool.json.JSONObject;

@Controller
@RequestMapping("/manage/user")
public class UserController extends BaseController {
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;
	
	String PW_PATTERN = "^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\W]+$)[a-zA-Z0-9\\W]{8,}$";
	
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public ModelAndView page_list() {
		return new ModelAndView("/manage/user/list");
	}
	@RequestMapping(value = "/edit",method = RequestMethod.GET)
	public ModelAndView page_edit() {
		ModelAndView mav=new ModelAndView("/manage/user/edit");
		try {
			String id = request.getParameter("id");
			User model = userService.getModel(id);
			if (model != null) {
				model.setPhone(DesUtil.decrypt(keyStr, model.getPhone()));
				model.setIdCard(DesUtil.decrypt(keyStr, model.getIdCard()));
			}
			mav.addObject("model", model);

		} catch (Exception e) {
			// TODO: handle exception
			mav.addObject("model", null);
		}
		return mav;
	}
	@RequestMapping(value = "/staff",method = RequestMethod.GET)
	public ModelAndView page_staff() {
		String companyId=request.getParameter("companyId");
		ModelAndView mav = new ModelAndView("/manage/company/staff");
		mav.addObject("companyId", companyId);
		return mav;
	}
	@RequestMapping(value = "/department",method = RequestMethod.GET)
	public ModelAndView page_department() {
		String roleId = Base.getCookieValueByName("user", request, 2);
		ModelAndView mav = new ModelAndView("/manage/user/department");
		mav.addObject("roleId", roleId);
		return mav;
	}
	@RequestMapping(value = "/departmentEdit",method = RequestMethod.GET)
	public ModelAndView page_departmentEdit() {
		ModelAndView mav=new ModelAndView("/manage/user/departmentEdit");
		try {
			String id = request.getParameter("id");
			String roleId = request.getParameter("roleId");
			User model = userService.getModel(id);
			if (model != null) {
				model.setPhone(DesUtil.decrypt(keyStr, model.getPhone()));
				model.setIdCard(DesUtil.decrypt(keyStr, model.getIdCard()));
			}
			mav.addObject("model", model);
			mav.addObject("roleId", roleId);
		} catch (Exception e) {
			// TODO: handle exception
			mav.addObject("model", null);
		}
		return mav;
	}
	@RequestMapping(value = "/staffEdit",method = RequestMethod.GET)
	public ModelAndView page_staffEdit() {
		ModelAndView mav=new ModelAndView("/manage/company/staffEdit");
		try {
			String id = request.getParameter("id");
			String companyId = request.getParameter("companyId");
			User model = userService.getModel(id);
			if (model != null ) {
				model.setPhone(DesUtil.decrypt(keyStr, model.getPhone()));
				model.setIdCard(DesUtil.decrypt(keyStr, model.getIdCard()));
			}
			mav.addObject("model", model);
			mav.addObject("companyId", companyId);
		} catch (Exception e) {
			// TODO: handle exception
			mav.addObject("model", null);
		}
		return mav;
	}
	@RequestMapping(value = "/resetPassword",method = RequestMethod.GET)
	public ModelAndView page_resetPassword() {
		return new ModelAndView("/manage/resetPassword");
	}
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> list(){
		try {
			String pageIndex = request.getParameter("pageIndex");
			String pageSize = request.getParameter("pageSize");
			String searchParams = request.getParameter("searchParams");
			String roleId ="";
			User user = userService.getModel(Base.getCookieValueByName("user", request, 0));
			if(user!=null) {
				roleId = String.valueOf(user.getRoleId());
			}
			String name="";
			String phone="";
			String role="";
			String department="";
			if (!Common.isNullOrEmpty(searchParams)) {
				JSONObject jsonObj = new JSONObject(searchParams);
				name = (String) jsonObj.get("name");
				phone = (String) jsonObj.get("phone");
				role = (String) jsonObj.get("role");
				department = (String) jsonObj.get("department");
			}
			PageIndex=Integer.parseInt(pageIndex);
			PageSize=Integer.parseInt(pageSize);
			int[] args = {0};
			String cond = "";
			if(!isNullOrEmpty(name)) {
				cond += " and name like '%"+name+"%'";
			}
			if(!isNullOrEmpty(roleId)) {
				if(roleId.equals("4") || roleId.equals("5"))
					cond+=" and companyId="+user.getCompanyId();
			}
			if(!isNullOrEmpty(phone)) {
				cond+=" and phone = '"+DesUtil.encrypt(keyStr, phone)+"'";
			}
			if(!isNullOrEmpty(role)) {
				cond+=" and roleId="+role;
			}
			if(!isNullOrEmpty(department))
			{
				cond+=" and (department like '%"+department+"%' or company like '%"+department+"%')";
			}
			String FieldStr="*";
			String tableName="(SELECT `user`.id,`user`.`name`,`user`.sex,`role`.`name` as role,`company`.`name` as company,`user`.active,`user`.isDelete,`user`.roleId,`user`.phone,`user`.companyId  FROM `user` LEFT JOIN `role` on (`user`.roleId = `role`.id) LEFT JOIN `company` on (`company`.id = `user`.companyId))t";
			String param="1=1 and isDelete=1";
			param+=cond;
			String orderStr=" order by roleId";
			List list = userService.getCustomList(PageIndex, PageSize, FieldStr, tableName, param, orderStr, args);
			for (Object object : list) {
				Map<String, Object> map = (Map<String, Object>)object;
				phone = DesUtil.decrypt(keyStr, map.get("phone").toString());
				map.put("phone", phone.replaceAll("(\\w{3})\\w*(\\w{4})", "$1****$2"));
			}
			return ResponseModel(true, "ok", args[0] ,list);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseModel(false, e.getMessage(), null);
		}
	}
	
	@RequestMapping(value = "/listTable",method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> listTable(){
		try {
			String pageIndex = request.getParameter("pageIndex");
			String pageSize = request.getParameter("pageSize");
			String name = request.getParameter("name");
			String roleId ="";
			User user = userService.getModel(Base.getCookieValueByName("user", request, 0));
			if(user!=null) {
				roleId = String.valueOf(user.getRoleId());
			}
			String phone="";
			String role="";
			String department="";
			
			PageIndex=Integer.parseInt(pageIndex);
			PageSize=Integer.parseInt(pageSize);
			int[] args = {0};
			String cond = "";
			if(!isNullOrEmpty(name)) {
				cond += " and name like '%"+name+"%'";
			}
			if(!isNullOrEmpty(roleId)) {
				if(roleId.equals("4") || roleId.equals("5"))
					cond+=" and companyId="+user.getCompanyId();
			}
			if(!isNullOrEmpty(phone)) {
				cond+=" and phone = '"+DesUtil.encrypt(keyStr, phone)+"'";
			}
			if(!isNullOrEmpty(role)) {
				cond+=" and roleId="+role;
			}
			if(!isNullOrEmpty(department))
			{
				cond+=" and (department like '%"+department+"%' or company like '%"+department+"%')";
			}
			String FieldStr="*";
			String tableName="(SELECT `user`.id,`user`.`name`,`user`.sex,`user`.idcard,`role`.`name` as role,`company`.`name` as company,`user`.active,`user`.isDelete,`user`.roleId,`user`.phone,`user`.departmentId,`user`.companyId,`department`.`name` as department  FROM `user` LEFT JOIN `role` on (`user`.roleId = `role`.id) LEFT JOIN `company` on (`company`.id = `user`.companyId) left join `department` on (`department`.id=`user`.departmentId))t";
			String param="1=1 and isDelete=1";
			param+=cond;
			String orderStr=" order by roleId";
			List list = userService.getCustomList(PageIndex, PageSize, FieldStr, tableName, param, orderStr, args);
			for (Object object : list) {
				Map<String, Object> map = (Map<String, Object>)object;
				phone = DesUtil.decrypt(keyStr, map.get("phone").toString());
				String idcard = DesUtil.decrypt(keyStr, map.get("idcard").toString());
				map.put("phone", phone);
				map.put("idcard", idcard);
			}
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("code", 0);
			result.put("msg", "");
			result.put("count", args[0]);
			result.put("data", list);
			
			return result;
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
			
			userService.delete(ids);
			return ResponseModel(true, "ok", null);
		} catch (Exception e) {
		// TODO: handle exception
			return ResponseModel(false,e.getMessage(), null);
		}
	}
	
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(User model){
		try {
			if(isNullOrEmpty(model.getName()))
				throw new Exception("姓名为空");
			if(model.getRoleId()==5) {
				if(userService.checkPhone(DesUtil.encrypt(keyStr, model.getPhone()), model.getId()))
					throw new Exception("该手机号已存在");
				if(!isNullOrEmpty( model.getIdCard())&&model.getIdCard().length()>4&&model.getIdCard().substring(0, 4).equals("3304")&&model.getRoleId()==5)
					throw new Exception("该用户非外来人员");
			}
			if(Common.IsIDCard(model.getIdCard())){
				if (Integer.parseInt(model.getIdCard().substring(16).substring(0, 1)) % 2 == 0) {// 判断性别
			           model.setSex("女"); 
			        } else {
			        	 model.setSex("男"); 
			        }
					if(isNullOrEmpty(model.getNativePlace()))
						model.setNativePlace(NativePlace.getNativePlace(Integer.parseInt(model.getIdCard().substring(0, 6))));
			}
			else {
				if(!model.getIdCard().matches("^[A-Z]{1,2}[0-9]{6}\\(?[0-9A]\\)?$"))
				{
					if(!model.getIdCard().matches("^[1|5|7][0-9]{6}\\(?[0-9A-Z]\\)?$")) {
						if(!model.getIdCard().matches("^[a-zA-Z][0-9]{9}"))
							throw new Exception("身份证不正确");
					}
						
				}
					
			} 
			if(model.getPhone().length()!=11)
				throw new Exception("手机号不正确");
			String phone = model.getPhone();
			User oldUser= userService.getModel(String.valueOf(model.getId()));
			if(oldUser!=null) {
				model.setOpenId(oldUser.getOpenId());
			}
			
			model.setPhone(DesUtil.encrypt(keyStr, model.getPhone()));
			model.setIdCard(DesUtil.encrypt(keyStr, model.getIdCard()));
			if(model.getId()==0)
			{
				if(!isNullOrEmpty(phone) && phone.length()>4)
					model.setPassword(phone.substring(phone.length()-4,phone.length()));
				userService.add(model);
			}else
			{
				userService.modify(model);
			}
			
			
			return ResponseModel(true, "ok", null);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseModel(false, e.getMessage(), null);
		}
	}
	
	@RequestMapping(value = "/staff",method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> staff(){
		try {
			String pageIndex = request.getParameter("pageIndex");
			String pageSize = request.getParameter("pageSize");
			String searchParams = request.getParameter("searchParams");
			String companyId = request.getParameter("companyId");
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
			if(!isNullOrEmpty(companyId)) {
				cond += " and companyId="+companyId;
			}	
			String FieldStr="*";
			String tableName="`user`";
			String param="1=1 and isDelete=1 and roleId=5";
			param+=cond;
			String orderStr="";
			List list = userService.getCustomList(PageIndex, PageSize, FieldStr, tableName, param, orderStr, args);
			for (Object object : list) {
				Map<String, Object> map = (Map<String, Object>)object;
				String phone = DesUtil.decrypt(keyStr, map.get("phone").toString());
				map.put("phone", phone.replaceAll("(\\w{3})\\w*(\\w{4})", "$1****$2"));
			}
			return ResponseModel(true, "ok", args[0] ,list);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseModel(false, e.getMessage(), null);
		}
	}
	
	@RequestMapping(value =  "resetPassword" ,method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> resetPassword(){
		try {
			String oldPwd=request.getParameter("oldPwd");
			String newPwd1=request.getParameter("newPwd1");
			String newPwd2=request.getParameter("newPwd2");
			if(Common.isNullOrEmpty(oldPwd))
				throw new Exception("原始密码为空");
			if(Common.isNullOrEmpty(newPwd1))
				throw new Exception("新密码为空");
			if(!newPwd1.equals(newPwd2))
				throw new Exception("两次输入新密码不同");
			if(!newPwd1.matches(PW_PATTERN))
				throw new Exception("密码必须是包含大写字母、小写字母、数字、特殊符号（不是字母，数字，下划线，汉字的字符）的8位以上组合");
			
			User user=userService.getModel(Base.getCookieValueByName("user", request, 0));
			if(user.getPassword().equals(Common.MD5(newPwd1, 16)))
				throw new Exception("新密码跟原始密码相同");
			userService.resetPwd(Base.getCookieValueByName("user", request, 0), Common.MD5(newPwd1, 16));
			return ResponseModel(true, "ok", null);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseModel(false, "err,"+e.getMessage(), null);
		}
	}
	@RequestMapping(value =  "resetPwd" ,method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> resetPwd(){
		try {
			String id = request.getParameter("id");
			String pwd="123";
			userService.resetPwd(id,Common.MD5(pwd, 16));
			return ResponseModel(true, "ok", null);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseModel(false, "err,"+e.getMessage(), null);
		}
	}
	@RequestMapping(value =  "userCompany" )
	@ResponseBody
	public Map<String, Object> userCompany(){
		try {
			String userId = Base.getCookieValueByName("user", request, 0);
			User user = userService.getModel(userId);
			if(user==null)
				throw new Exception("请先登录");
			List list = new ArrayList();
			if(user.getRoleId()==4)
				list = userService.getUserCompanyList(user.getPhone());
			return ResponseModel(true, "ok", list);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseModel(false, "err,"+e.getMessage(), null);
		}
	}
	@RequestMapping("/export")
	@ResponseBody
	public Map<String, Object> export(){
		try {
			String companyId = request.getParameter("companyId");
			List list = userService.getStaffList(companyId);
			HSSFWorkbook book = null;
			book = new HSSFWorkbook();// excell2003
			
			Sheet sheet = book.createSheet("企业员工列表");
			CellStyle style = createTitleStyle(book);
			Row row = sheet.createRow(0);
			Cell cell = row.createCell(0);
			cell.setCellValue("姓名");
			cell = row.createCell(1);
			cell.setCellValue("性别");
			cell = row.createCell(2);
			cell.setCellValue("员工个人微信绑定的手机号码");
			cell = row.createCell(3);
			cell.setCellValue("身份证号码");
			cell = row.createCell(4);
			cell.setCellValue("是否留秀");
			cell = row.createCell(5);
			cell.setCellValue("是否自驾");	
			cell = row.createCell(6);
			cell.setCellValue("自驾车牌号码");
			int i=1;
			for (Object object : list) {
				Map<String, Object> map = (Map<String, Object>)object;
				row = sheet.createRow(i);
			    cell = row.createCell(0);
				cell.setCellValue(map.get("nameCN").toString());
				cell = row.createCell(1);
				cell.setCellValue(map.get("sex").toString());
				cell = row.createCell(2);
				cell.setCellValue(DesUtil.decrypt(keyStr, map.get("phone").toString()) );
				cell = row.createCell(3);
				cell.setCellValue(DesUtil.decrypt(keyStr, map.get("idCard").toString()));
				cell = row.createCell(4);
				cell.setCellValue(map.get("isStay").toString().equals("1")?"是":"否");
				cell = row.createCell(5);
				cell.setCellValue(map.get("isDriving").toString().equals("1")?"是":"否");	
				cell = row.createCell(6);
				cell.setCellValue(map.get("licensePlate").toString());
				i++;
			}
			String rootPath= request.getSession().getServletContext().getRealPath("/DailyFiles/");
			String url="excel/";
			File file = new File(rootPath+url);
	        if(!file.exists())
	        	file.mkdirs();
	        String name="企业员工"+String.valueOf(new Date().getTime())+".xls";
	        FileOutputStream out = new FileOutputStream(new File(rootPath+url+name));
	        book.write(out);
	        out.close();
			return ResponseModel(true, "ok", "DailyFiles/"+url+name);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseModel(false, "err,"+e.getMessage(), null);
		}
	}
	public static CellStyle createTitleStyle(Workbook book) {
        CellStyle cellStyle = book.createCellStyle();
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框  
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        
        cellStyle.setWrapText(true);
        Font font = book.createFont();
        font.setFontHeightInPoints((short) 12); // 字体大小
        font.setFontName("宋体");
         // 粗体
        cellStyle.setFont(font);
        return cellStyle;
    }
	
	@RequestMapping(value = "/department",method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> department(){
		try {
			String pageIndex = request.getParameter("pageIndex");
			String pageSize = request.getParameter("pageSize");
			String searchParams = request.getParameter("searchParams");
			String companyId = request.getParameter("companyId");
			String name="";
			String userId = Base.getCookieValueByName("user", request, 0);
			User user = userService.getModel(userId);
			
			if(user==null)
				throw new Exception("请先登录");
			else if(user.getRoleId()==2) {
				int[] departmentId = {12,19,20,21,22,23,24,25};
				boolean isDepartment=false;
				for (int i : departmentId) {
					if(i==user.getDepartmentId())
					{
						isDepartment=true;
						break;
					}
				}
				if(!isDepartment)
					throw new Exception("无权限");
			}else if(user.getRoleId()==3) {
				if(user.getDepartmentId()!=2)
					throw new Exception("无权限");
			}
			else {
				throw new Exception("无权限");
			}
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
			if(user.getRoleId()==3)
			{
				cond+= " and roleId="+user.getRoleId();
			}
			String FieldStr="*";
			String tableName="(select `user`.*,`department`.governmentId,`department`.`name` as department from `user` INNER JOIN `department` on (`user`.departmentId = `department`.id) where `user`.isDelete=1 and `user`.roleId=2 or `user`.roleId=3 )t";
			String param="1=1";
			param+=cond;
			String orderStr="";
			List list = userService.getCustomList(PageIndex, PageSize, FieldStr, tableName, param, orderStr, args);
			for (Object object : list) {
				Map<String, Object> map = (Map<String, Object>)object;
				String phone = DesUtil.decrypt(keyStr, map.get("phone").toString());
				map.put("phone", phone.replaceAll("(\\w{3})\\w*(\\w{4})", "$1****$2"));
			}
			return ResponseModel(true, "ok", args[0] ,list);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseModel(false, e.getMessage(), null);
		}
	}
	@RequestMapping(value = "/applyList",method = RequestMethod.GET)
	public ModelAndView page_applyList() {
		String userId = request.getParameter("userId");
		ModelAndView mav = new ModelAndView("/manage/user/applyList");
		mav.addObject("userId", userId);
		return mav;
	}
	@RequestMapping(value = "/applyList",method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> applyList(){
		try {
			String pageIndex = request.getParameter("pageIndex");
			String pageSize = request.getParameter("pageSize");
			String searchParams = request.getParameter("searchParams");
			String userId =request.getParameter("userId");
			if(isNullOrEmpty(userId)) {
				throw new Exception("缺少用户id");
			}
			if (!Common.isNullOrEmpty(searchParams)) {
				JSONObject jsonObj = new JSONObject(searchParams);
				
			}
			PageIndex=Integer.parseInt(pageIndex);
			PageSize=Integer.parseInt(pageSize);
			int[] args = {0};
			String cond = "";
			cond+=" and userId="+userId;
			String FieldStr="*";
			String tableName="(SELECT a.*,b.`name` FROM `applycollect` as a inner JOIN eventtype as b on (a.typeId=b.id))t";
			String param="1=1";
			param+=cond;
			String orderStr="";
			List list = userService.getCustomList(PageIndex, PageSize, FieldStr, tableName, param, orderStr, args);
			
			return ResponseModel(true, "ok", args[0] ,list);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseModel(false, e.getMessage(), null);
		}
	}
}
