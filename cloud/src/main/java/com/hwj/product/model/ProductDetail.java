package com.hwj.product.model;

import java.sql.ResultSet;
import java.util.Date;

public class ProductDetail extends BaseModel{
	private int id;
	private String name;		//产品名称
	private int num;			//数量 卷
	private double len;			//卷/米
	private double width;		//门幅（宽度,米）
	private String color;		//颜色
	private String spec;		//规格 每立方的重量 40-240 克
	private Date productTime;	//生产时间
	private Date createDate;	//录入时间
	private int userId;			//人员id
	private String memo;		//备注
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public double getLen() {
		return len;
	}
	public void setLen(double len) {
		this.len = len;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public Date getProductTime() {
		return productTime;
	}
	public void setProductTime(Date productTime) {
		this.productTime = productTime;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public ProductDetail(){}
	
	public ProductDetail(ResultSet rs){
		this.initWithResultSet(this, rs);
	}
}
