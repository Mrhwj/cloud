package com.hwj.product.service;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hwj.product.model.Menu;
import com.hwj.product.model.ProductDetail;
import com.hwj.product.model.User;
import com.hwj.product.tools.Common;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

@Service
public class ProductDetailService extends DbConfig{

	@Override
	public void add(Object model) {
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				ProductDetail Model = (ProductDetail)model;
				Model.setId(getTableMaxId("`productDetail`"));
				Model.setCreateDate(new Date(System.currentTimeMillis()));
				String sql = SqlSentence.getInsertSql("`productDetail`", ProductDetail.class, Model);
				statement.execute(sql);
				con.close();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void modify(Object model) {
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				ProductDetail Model = (ProductDetail)model;
				String sql = SqlSentence.getUpdateSql("`productDetail`", ProductDetail.class, Model);
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
		
	}
	
	public ProductDetail getModel(String id) {
		ProductDetail model = null;
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				String sql = String.format("select * from productDetail where id=%s", id);
				ResultSet rs = statement.executeQuery(sql);
				if(rs.next()) {
					model = new ProductDetail(rs);
				}
				rs.close();
				con.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return model;
	}
	
	public List GetList(int pageIndex,int pageSize,int[] args,String cond) {
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();

				int start = (pageIndex-1)*pageSize;
				String sql = String.format("select a.*,b.nameCN as userName from productDetail a left join `user` b on a.userId=b.id where 1=1 %s order by a.createDate desc limit %d,%s",cond,start,pageSize);
				ResultSet rs = statement.executeQuery(sql);
				List list = Common.resultSetToList(rs);
				sql = String.format("select count(a.id) from productDetail a where 1=1 %s",cond);
				rs = statement.executeQuery(sql);
				if(rs.next()) {
					args[0] = rs.getInt(1);
				}
				rs.close();
				con.close();
				return list;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	

}
