package com.hwj.product.model;


import java.sql.ResultSet;
import java.util.Date;

public class Menu extends BaseModel {

	private int id;
	private Date createTime;
	private String name;//名称
	private String icon;//图标
	private Integer parentId;//父级id
	private String link;//链接
	private Integer orderId;//权重
	private String roleIds;//可见角色
	public Menu() {}
	public Menu(ResultSet rs) {
		this.initWithResultSet(this, rs);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public String getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	
	
}
