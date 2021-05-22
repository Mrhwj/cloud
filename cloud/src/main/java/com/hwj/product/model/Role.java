package com.hwj.product.model;

import java.sql.ResultSet;
import java.util.Date;

/** 
* @author 作者 xbm: 
* @version 创建时间：2021年1月21日 下午8:40:04 
* 类说明 
*/
public class Role extends BaseModel {
	private int id;
	private Date createTime;
	private int isDelete;
	private String name;
	private String memo;
	private String roleIds;//可见角色
	public Role() {}
	public Role(ResultSet rs) {
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
	public int getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	
}
