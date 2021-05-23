package com.hwj.product.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hwj.product.model.Menu;
import com.hwj.product.service.MenuService;
import com.hwj.product.tools.Base;


@Controller
@RequestMapping("/manage/menu")
public class MenuController extends BaseController {
	@Autowired
	MenuService menuService;
	
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public ModelAndView page_list() {
		return new ModelAndView("/manage/menu/list");
	}
	@RequestMapping(value = "/edit",method = RequestMethod.GET)
	public ModelAndView page_edit() {
		String id = request.getParameter("id");
		String parentId=request.getParameter("parentId");
		Menu model=menuService.getModel(id);
		
		ModelAndView mav=new ModelAndView("/manage/menu/edit");
		mav.addObject("model", model);
		mav.addObject("parentId", parentId);
		return mav;
	}
	
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> list(){
		try {
			
			String FieldStr = "id,name,icon,parentId,link,orderId";
			String tableName = "`menu`";
			String param = "1=1 ";
			
			String orderStr = "order by orderId";
			int[] args = { 0 };
			List list = menuService.getCustomList(1, 1000, FieldStr,
					tableName, param, orderStr, args);
			
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
			String id=request.getParameter("id");
			if (id == "" || id == null)
				throw new Exception("id为空");
			if(menuService.checkChild(id))
				throw new Exception("请先删除子级菜单");
			menuService.delete(id);
			return ResponseModel(true, "ok", null);
		} catch (Exception e) {
		// TODO: handle exception
			return ResponseModel(false,e.getMessage(), null);
		}
	}
	
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(Menu menu){
		try {
			if(isNullOrEmpty(menu.getName()))
				throw new Exception("菜单名称为空");
			
			if(menu.getId()==0)
			{
				menuService.add(menu);
			}else
			{
				menuService.modify(menu);
			}
			
			return ResponseModel(true, "ok", null);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseModel(false, e.getMessage(), null);
		}
	}
	
	@RequestMapping(value = "/menu", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> menu() {
		try {
			
			Map<String, Object> map = new HashMap<>();
			
			Map<String, Object> map1 = new HashMap<>();
			map1.put("clearUrl", "api/clear.json");
			
			Map<String, Object> map2 = new HashMap<>();
			map2.put("title", "首页");
			map2.put("icon", "fa fa-home");
			map2.put("href", "welcome.do");
			
			Map<String, Object> map3 = new HashMap<>();
			map3.put("title", "生产管理平台");
			map3.put("image", "");
			map3.put("href", "");
			
			String roleId=Base.getCookieValueByName("user", request, 2);
			List<Menu> list = menuService.getListByRole(roleId);
			List menuList=recursionMenu(list);
			map.put("clearInfo", map1);
			map.put("homeInfo", map2);
			map.put("logoInfo", map3);
			map.put("menuInfo", menuList);
			
			
			return map;
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseModel(false,  e.getMessage(), null);
		}
	}
	public List recursionMenu(List<Menu> list){
		List treeList = new ArrayList();
		// 循环查出的list，找到根节点（最大的父节点）的子节点
	    for(Menu menu : list){
	    	Map< String, Object> map=new HashMap<String, Object>();
	    	
	        // 我们这里最大的根节点ID是-1，所以首先找pid为-1的子，然后调用我们的递归算法
		    if(menu.getParentId()==0){
		    	//treeList
		    	map.put("title", menu.getName());
		    	map.put("href", menu.getLink());
		    	map.put("icon", menu.getIcon());
		    	map.put("target", "_self");
		    	map.put("child",addChildMenu(menu.getId(),list));
		    	treeList.add(map);
		    }
		}
		return treeList;
	}
	public List addChildMenu(int parentId, List<Menu> list){
		List<Object> childList = new ArrayList<>();
	    // 为每一个父节点增加子树（List形式，没有则为空的list）
	 	for (Menu childMenu : list){
	        //如果子节点的pid等于父节点的ID，则说明是父子关系
	 		Map< String, Object> childMap=new HashMap<String, Object>();
		    if(childMenu.getParentId()==parentId){
			    // 是父子关系，则将其放入子list字面
		    	childMap.put("title", childMenu.getName());
		    	childMap.put("href", childMenu.getLink());
		    	childMap.put("icon", childMenu.getIcon());
		    	childMap.put("target", "_self");
		    	List child=addChildMenu(childMenu.getId(),list);
		    	if(child.size()>0)
		    		childMap.put("child", child);
	            // 继续调用递归算法，将当前作为父节点，继续找他的子节点，反复执行。
			    
			    childList.add(childMap);
		    }
		}
	    // 当遍历完成，返回调用的父节点的所有子节点
		return childList;
	}
}
