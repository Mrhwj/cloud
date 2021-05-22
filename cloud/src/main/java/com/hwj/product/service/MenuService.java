package com.hwj.product.service;

import java.util.List;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.hwj.product.model.Menu;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;


@Service
public class MenuService extends DbConfig {

	@Override
	public void add(Object model) {
		// TODO Auto-generated method stub
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				Menu Model = (Menu)model;
				Model.setId(getTableMaxId("`menu`"));
				Model.setCreateTime(new Date(System.currentTimeMillis()));
				String sql = SqlSentence.getInsertSql("`menu`", Menu.class, Model);
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
				Menu Model = (Menu)model;
				String sql = SqlSentence.getUpdateSql("`menu`", Menu.class, Model);
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
				String sql = String.format("delete from `menu` where id in (%s)", ids);
				statement.executeUpdate(sql);
				
				con.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException("执行失败:" + e.getMessage(), e);
		}
	}
	public List getListByRole(String roleId){
		List<Menu> list=new ArrayList<Menu>();
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				String sql = String.format("SELECT * FROM `menu` where FIND_IN_SET('%s',roleIds)>0 order by orderId", roleId);
				ResultSet rs = statement.executeQuery(sql);
				while(rs.next()) {
					Menu model=new Menu(rs);
					list.add(model);
				}
				rs.close();
				con.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException("执行失败:" + e.getMessage(), e);
		}
		return list;
	}
	
	public Menu getModel(String id) {
		Menu model=null;
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				String sql = String.format("SELECT * FROM `menu` where id=%s", id);
				ResultSet rs = statement.executeQuery(sql);
				if(rs.next()) {
					model=new Menu(rs);
				}
				rs.close();
				con.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException("执行失败:" + e.getMessage(), e);
		}
		return model;
	}
	public boolean checkChild(String id) {
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				String sql = String.format("SELECT * FROM `menu` where parentId=%s", id);
				ResultSet rs = statement.executeQuery(sql);
				if(rs.next()) {
					return true;
				}
				rs.close();
				con.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException("执行失败:" + e.getMessage(), e);
		}
		return false;
	}
	
}
