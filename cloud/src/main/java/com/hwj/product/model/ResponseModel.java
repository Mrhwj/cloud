package com.hwj.product.model;

public class ResponseModel {

	private boolean status;
	private String msg;
	private Object data;
	private int count;
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	public ResponseModel(boolean status,String msg,Object data,int count) {
		this.status = status;
		this.msg = msg;
		this.data = data;
		this.count = count;
	}
}
