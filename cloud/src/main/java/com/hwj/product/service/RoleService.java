package com.hwj.product.service;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hwj.product.model.Role;
import com.hwj.product.tools.Common;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

/** 
* @author 作者 xbm: 
* @version 创建时间：2021年1月21日 下午8:41:33 
* 类说明 
*/
@Service
public class RoleService extends DbConfig {

	@Override
	public void add(Object model) {
		// TODO Auto-generated method stub
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				Role Model = (Role)model;
				Model.setId(getTableMaxId("`role`"));
				Model.setCreateTime(new Date(System.currentTimeMillis()));
				Model.setIsDelete(1);
				String sql = SqlSentence.getInsertSql("`role`", Role.class, Model);
				statement.execute(sql);
				con.close();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void modify(Object model) {
		// TODO Auto-generated method stub
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				Role Model = (Role)model;
				String sql = SqlSentence.getUpdateSql("`role`", Role.class, Model);
				statement.execute(sql);
				con.close();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void delete(String ids) {
		// TODO Auto-generated method stub
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				String sql = String.format("update `role` set isDelete=0 where id in (%s)", ids);
				statement.executeUpdate(sql);
				con.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException("执行失败:" + e.getMessage(), e);
		}
	}
	public List getList(String roleId){
		List list=new ArrayList();
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				String sql = String.format("SELECT * FROM `role` where isDelete=1 and find_in_set(%s,roleIds)",roleId);
				ResultSet rs = statement.executeQuery(sql);
				list = Common.resultSetToList(rs);
				rs.close();
				con.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException("执行失败:" + e.getMessage(), e);
		}
		return list;
	}
	public List getList(){
		List list=new ArrayList();
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				String sql = String.format("SELECT * FROM `role` where isDelete=1 ");
				ResultSet rs = statement.executeQuery(sql);
				list = Common.resultSetToList(rs);
				rs.close();
				con.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException("执行失败:" + e.getMessage(), e);
		}
		return list;
	}
	public Role getModel(String id) {
		Role model= null;
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				String sql = String.format("SELECT * FROM `role` where isDelete=1 and id=%s ",id);
				ResultSet rs = statement.executeQuery(sql);
				if(rs.next())
					model = new Role(rs);
				rs.close();
				con.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return model;
	}

}
