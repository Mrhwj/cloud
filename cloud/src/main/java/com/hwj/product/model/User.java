package com.hwj.product.model;


import java.sql.ResultSet;
import java.util.Date;

public class User extends BaseModel {
	private int id;
	private Date createTime;
	private int isDelete;
	private String name;//名称
	private String nameCN;
	private String password;
	private String phone;//手机号
	private String idCard;//身份证
	private int companyId;//单位id
	private int isStay;//是否留秀
	private int isDriving;//是否自驾
	private String licensePlate;//自驾车牌
	private int roleId;//角色id
	private int active;//激活
	private String nativePlace;//户籍
	private int departmentId;
	private String sex;//性别
	private String openId;//wx openId
	private int isStay2020;	//2020是否留秀
	public User() {}
	public User(ResultSet rs) {
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
	
	public String getNameCN() {
		return nameCN;
	}
	public void setNameCN(String nameCN) {
		this.nameCN = nameCN;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public int getIsStay() {
		return isStay;
	}
	public void setIsStay(int isStay) {
		this.isStay = isStay;
	}
	public int getIsDriving() {
		return isDriving;
	}
	public void setIsDriving(int isDriving) {
		this.isDriving = isDriving;
	}
	public String getLicensePlate() {
		return licensePlate;
	}
	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	
	public String getNativePlace() {
		return nativePlace;
	}
	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public int getIsStay2020() {
		return isStay2020;
	}
	public void setIsStay2020(int isStay2020) {
		this.isStay2020 = isStay2020;
	}
}
