package com.hwj.product.model;
/** 
* @author 作者 xbm: 
* @version 创建时间：2021年1月21日 下午9:01:52 
* 类说明 
*/

import java.sql.ResultSet;
import java.util.Date;

public class Company extends BaseModel {
	private int id;
	private Date createTime;
	private int isDelete;
	private String name;//企业
	private String code;//企业统一码
	private String userName;//企业联系人
	private String userPhone;//联系联系方式
	private String userIdCard;//企业联系人身份证
	private String phone;//企业联系电话
	private int governmentId;//地区id
	private int pliceGovId;//所属派出所地区
	public Company() {}
	public Company(ResultSet rs) {
		initWithResultSet(this, rs);
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getUserIdCard() {
		return userIdCard;
	}
	public void setUserIdCard(String userIdCard) {
		this.userIdCard = userIdCard;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getGovernmentId() {
		return governmentId;
	}
	public void setGovernmentId(int governmentId) {
		this.governmentId = governmentId;
	}
	public int getPliceGovId() {
		return pliceGovId;
	}
	public void setPliceGovId(int pliceGovId) {
		this.pliceGovId = pliceGovId;
	}
	
}
