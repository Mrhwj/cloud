package com.hwj.product.service;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hwj.product.model.Company;
import com.hwj.product.tools.Common;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

@Service
public class CompanyService extends DbConfig {

	@Override
	public void add(Object model) {
		// TODO Auto-generated method stub
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				Company Model = (Company)model;
				Model.setId(getTableMaxId("`company`"));
				Model.setCreateTime(new Date(System.currentTimeMillis()));
				Model.setIsDelete(1);
				String sql = SqlSentence.getInsertSql("`company`", Company.class, Model);
				statement.execute(sql);
				con.close();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public int add2(Object model) {
		// TODO Auto-generated method stub
		int id= 0;
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				Company Model = (Company)model;
				Model.setId(getTableMaxId("`company`"));
				Model.setCreateTime(new Date(System.currentTimeMillis()));
				Model.setIsDelete(1);
				String sql = SqlSentence.getInsertSql("`company`", Company.class, Model);
				statement.execute(sql);
				con.close();
				id=Model.getId();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return id;
	}
	@Override
	public void modify(Object model) {
		// TODO Auto-generated method stub
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				Company Model = (Company)model;
				String sql = SqlSentence.getUpdateSql("`company`", Company.class, Model);
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
				String sql = String.format("update `company` set isDelete=0 where id in (%s)", ids);
				statement.executeUpdate(sql);
				con.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException("执行失败:" + e.getMessage(), e);
		}
	}
	
	public Company getModel(String id) {
		Company model = null;
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				String sql = String.format("select * from  `company`  where id =%s and isDelete=1", id);
				ResultSet rs = statement.executeQuery(sql);
				if(rs.next())
					model = new Company(rs);
				rs.close();
				con.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return model;
	}
	public Company getModelByName(String name) {
		Company model = null;
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				String sql = String.format("select * from  `company`  where name ='%s' and isDelete=1", name);
				ResultSet rs = statement.executeQuery(sql);
				if(rs.next())
					model = new Company(rs);
				rs.close();
				con.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return model;
	}
	public Company getModelByCode(String code) {
		Company model = null;
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				String sql = String.format("select * from  `company`  where code ='%s' and isDelete=1", code);
				ResultSet rs = statement.executeQuery(sql);
				if(rs.next())
					model = new Company(rs);
				rs.close();
				con.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return model;
	}
	public List getList() {
		List list = new ArrayList();
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				String sql = String.format("select * from  `company`  where  isDelete=1 ");
				ResultSet rs = statement.executeQuery(sql);
				list = Common.resultSetToList(rs);
				rs.close();
				con.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	public List getList(String id) {
		List list = new ArrayList();
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				String sql = String.format("select * from  `company`  where  isDelete=1 and id=%s",id);
				ResultSet rs = statement.executeQuery(sql);
				list = Common.resultSetToList(rs);
				rs.close();
				con.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	public int getCompanyNumber(String cond) {
		int count=0;
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				String sql = String.format("select count(1) as number from  `company`  where  isDelete=1 %s",cond);
				ResultSet rs = statement.executeQuery(sql);
				if(rs.next())
					count = rs.getInt(1);
				rs.close();
				con.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return count;
	}
	public List getCompanyList(String cond) {
		List list = new ArrayList();
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				String sql = String.format("select * from  `company`  where  isDelete=1 %s",cond);
				ResultSet rs = statement.executeQuery(sql);
				list = Common.resultSetToList(rs);
				rs.close();
				con.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	
	public List getListByGovernmentId(int pageIndex,int pageSize,int[] args,String governmentId,String cond,int departmentId) {
		List list = null;
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				int start = (pageIndex-1)*pageSize;
				//String sql = String.format("select * from  `company`  where  isDelete=1 and governmentId=%s %s order by createTime desc limit %d,%d",governmentId,cond,start,pageSize);
				/*String sql = "select c.*,"+
						"(select (select sum(grantPrice) from applyCollect where approveId=a.id) as grantPrice from approveevent a where a.companyId=c.id and (a.dealDepId="+departmentId+" or find_in_set("+departmentId+",a.flow)) and a.applyType=1) as rentPrice,"+
						"(select (select sum(grantPrice) from applyCollect where approveId=a.id) as grantPrice from approveevent a where a.companyId=c.id and (a.dealDepId="+departmentId+" or find_in_set("+departmentId+",a.flow)) and a.applyType=2) as relativePrice,"+
						"(select (select sum(grantPrice) from applyCollect where approveId=a.id) as grantPrice from approveevent a where a.companyId=c.id and (a.dealDepId="+departmentId+" or find_in_set("+departmentId+",a.flow)) and a.applyType=3) as activityPrice,"+
						"(select (select sum(grantPrice) from applyCollect where approveId=a.id) as grantPrice from approveevent a where a.companyId=c.id and (a.dealDepId="+departmentId+" or find_in_set("+departmentId+",a.flow)) and a.applyType=4) as spendPrice,"+
						"(select (select sum(grantPrice) from applyCollect where approveId=a.id) as grantPrice from approveevent a where a.companyId=c.id and (a.dealDepId="+departmentId+" or find_in_set("+departmentId+",a.flow)) and a.applyType=5) as childPrice,"+
						"(select (select sum(grantPrice) from applyCollect where approveId=a.id) as grantPrice from approveevent a where a.companyId=c.id and (a.dealDepId="+departmentId+" or find_in_set("+departmentId+",a.flow)) and a.applyType=6) as drivePrice,"+
						"(select (select sum(grantPrice) from applyCollect where approveId=a.id) as grantPrice from approveevent a where a.companyId=c.id and (a.dealDepId="+departmentId+" or find_in_set("+departmentId+",a.flow)) and a.applyType=7) as trafficPrice,"+
						"(select (select sum(grantPrice) from applyCollect where approveId=a.id) as grantPrice from approveevent a where a.companyId=c.id and (a.dealDepId="+departmentId+" or find_in_set("+departmentId+",a.flow)) and a.applyType=8) as newpeoplePrice,"+
						"(select (select sum(grantPrice) from applyCollect where approveId=a.id) as grantPrice from approveevent a where a.companyId=c.id and (a.dealDepId="+departmentId+" or find_in_set("+departmentId+",a.flow)) and a.applyType=9) as newpeoplechildPrice"+
								
						" from company c where  c.isDelete=1 and c.governmentId="+governmentId+ cond + " order by createTime desc limit "+start+","+pageSize;*/
				
				String sql = "select c.*,"+
						"(select sum(grantPrice) from (select a.companyId,(select sum(grantPrice) from applyCollect where approveId=a.id) as grantPrice from approveevent a where (a.dealDepId="+departmentId+" or find_in_set("+departmentId+",a.flow)) and a.applyType=1) t where t.companyId=c.id group by t.companyId) as rentPrice,"+
						"(select sum(grantPrice) from (select a.companyId,(select sum(grantPrice) from applyCollect where approveId=a.id) as grantPrice from approveevent a where (a.dealDepId="+departmentId+" or find_in_set("+departmentId+",a.flow)) and a.applyType=2) t where t.companyId=c.id group by t.companyId) as relativePrice,"+
						"(select sum(grantPrice) from (select a.companyId,(select sum(grantPrice) from applyCollect where approveId=a.id) as grantPrice from approveevent a where (a.dealDepId="+departmentId+" or find_in_set("+departmentId+",a.flow)) and a.applyType=3) t where t.companyId=c.id group by t.companyId) as activityPrice,"+
						"(select sum(grantPrice) from (select a.companyId,(select sum(grantPrice) from applyCollect where approveId=a.id) as grantPrice from approveevent a where (a.dealDepId="+departmentId+" or find_in_set("+departmentId+",a.flow)) and a.applyType=4) t where t.companyId=c.id group by t.companyId) as spendPrice,"+
						"(select sum(grantPrice) from (select a.companyId,(select sum(grantPrice) from applyCollect where approveId=a.id) as grantPrice from approveevent a where (a.dealDepId="+departmentId+" or find_in_set("+departmentId+",a.flow)) and a.applyType=5) t where t.companyId=c.id group by t.companyId) as childPrice,"+
						"(select sum(grantPrice) from (select a.companyId,(select sum(grantPrice) from applyCollect where approveId=a.id) as grantPrice from approveevent a where (a.dealDepId="+departmentId+" or find_in_set("+departmentId+",a.flow)) and a.applyType=6) t where t.companyId=c.id group by t.companyId) as drivePrice,"+
						"(select sum(grantPrice) from (select a.companyId,(select sum(grantPrice) from applyCollect where approveId=a.id) as grantPrice from approveevent a where (a.dealDepId="+departmentId+" or find_in_set("+departmentId+",a.flow)) and a.applyType=7) t where t.companyId=c.id group by t.companyId) as trafficPrice,"+
						"(select sum(grantPrice) from (select a.companyId,(select sum(grantPrice) from applyCollect where approveId=a.id) as grantPrice from approveevent a where (a.dealDepId="+departmentId+" or find_in_set("+departmentId+",a.flow)) and a.applyType=8) t where t.companyId=c.id group by t.companyId) as newpeoplePrice,"+
						"(select sum(grantPrice) from (select a.companyId,(select sum(grantPrice) from applyCollect where approveId=a.id) as grantPrice from approveevent a where (a.dealDepId="+departmentId+" or find_in_set("+departmentId+",a.flow)) and a.applyType=9) t where t.companyId=c.id group by t.companyId) as newpeoplechildPrice,"+
						"(select count(t.id) as applyNum from (select h.id,h.companyId from applycollect h left join approveevent i on h.approveId=i.id where (i.dealDepId="+departmentId+" or find_in_set("+departmentId+",i.flow))) t where t.companyId=c.id) as applyNum" +
						" from company c where  c.isDelete=1 and c.governmentId="+governmentId+ cond + " order by applyNum desc, createTime desc limit "+start+","+pageSize;
				
				ResultSet rs = statement.executeQuery(sql);
				list = Common.resultSetToList(rs);
				
				sql = String.format("select count(id) from  `company`  where  isDelete=1 and governmentId=%s %s",governmentId,cond);
				rs = statement.executeQuery(sql);
				if(rs.next()) {
					args[0] = rs.getInt(1);
				}
				
				rs.close();
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return list;
	}
	
	public List GetGovCompanyTotal(int governmentId,int departmentId) {
		List list = new ArrayList();
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				//String sql = String.format("select * from company where  isDelete=1 and governmentId=%s",governmentId);
				/*String sql = "select c.id,c.name,"+
				"(select (select sum(grantPrice) from applyCollect where approveId=a.id) as grantPrice from approveevent a where a.companyId=c.id and (a.dealDepId="+departmentId+" or find_in_set("+departmentId+",a.flow)) and a.applyType=1) as rentPrice,"+
				"(select (select sum(grantPrice) from applyCollect where approveId=a.id) as grantPrice from approveevent a where a.companyId=c.id and (a.dealDepId="+departmentId+" or find_in_set("+departmentId+",a.flow)) and a.applyType=2) as relativePrice,"+
				"(select (select sum(grantPrice) from applyCollect where approveId=a.id) as grantPrice from approveevent a where a.companyId=c.id and (a.dealDepId="+departmentId+" or find_in_set("+departmentId+",a.flow)) and a.applyType=3) as activityPrice,"+
				"(select (select sum(grantPrice) from applyCollect where approveId=a.id) as grantPrice from approveevent a where a.companyId=c.id and (a.dealDepId="+departmentId+" or find_in_set("+departmentId+",a.flow)) and a.applyType=4) as spendPrice,"+
				"(select (select sum(grantPrice) from applyCollect where approveId=a.id) as grantPrice from approveevent a where a.companyId=c.id and (a.dealDepId="+departmentId+" or find_in_set("+departmentId+",a.flow)) and a.applyType=5) as childPrice,"+
				"(select (select sum(grantPrice) from applyCollect where approveId=a.id) as grantPrice from approveevent a where a.companyId=c.id and (a.dealDepId="+departmentId+" or find_in_set("+departmentId+",a.flow)) and a.applyType=6) as drivePrice,"+
				"(select (select sum(grantPrice) from applyCollect where approveId=a.id) as grantPrice from approveevent a where a.companyId=c.id and (a.dealDepId="+departmentId+" or find_in_set("+departmentId+",a.flow)) and a.applyType=7) as trafficPrice,"+
				"(select (select sum(grantPrice) from applyCollect where approveId=a.id) as grantPrice from approveevent a where a.companyId=c.id and (a.dealDepId="+departmentId+" or find_in_set("+departmentId+",a.flow)) and a.applyType=8) as newpeoplePrice,"+
				"(select (select sum(grantPrice) from applyCollect where approveId=a.id) as grantPrice from approveevent a where a.companyId=c.id and (a.dealDepId="+departmentId+" or find_in_set("+departmentId+",a.flow)) and a.applyType=9) as newpeoplechildPrice"+
						
				" from company c where  c.isDelete=1 and c.governmentId="+governmentId +" order by c.createTime desc";*/
				String sql = "select c.*,"+
						"(select sum(grantPrice) from (select a.companyId,(select sum(grantPrice) from applyCollect where approveId=a.id) as grantPrice from approveevent a where (a.dealDepId="+departmentId+" or find_in_set("+departmentId+",a.flow)) and a.applyType=1) t where t.companyId=c.id group by t.companyId) as rentPrice,"+
						"(select sum(grantPrice) from (select a.companyId,(select sum(grantPrice) from applyCollect where approveId=a.id) as grantPrice from approveevent a where (a.dealDepId="+departmentId+" or find_in_set("+departmentId+",a.flow)) and a.applyType=2) t where t.companyId=c.id group by t.companyId) as relativePrice,"+
						"(select sum(grantPrice) from (select a.companyId,(select sum(grantPrice) from applyCollect where approveId=a.id) as grantPrice from approveevent a where (a.dealDepId="+departmentId+" or find_in_set("+departmentId+",a.flow)) and a.applyType=3) t where t.companyId=c.id group by t.companyId) as activityPrice,"+
						"(select sum(grantPrice) from (select a.companyId,(select sum(grantPrice) from applyCollect where approveId=a.id) as grantPrice from approveevent a where (a.dealDepId="+departmentId+" or find_in_set("+departmentId+",a.flow)) and a.applyType=4) t where t.companyId=c.id group by t.companyId) as spendPrice,"+
						"(select sum(grantPrice) from (select a.companyId,(select sum(grantPrice) from applyCollect where approveId=a.id) as grantPrice from approveevent a where (a.dealDepId="+departmentId+" or find_in_set("+departmentId+",a.flow)) and a.applyType=5) t where t.companyId=c.id group by t.companyId) as childPrice,"+
						"(select sum(grantPrice) from (select a.companyId,(select sum(grantPrice) from applyCollect where approveId=a.id) as grantPrice from approveevent a where (a.dealDepId="+departmentId+" or find_in_set("+departmentId+",a.flow)) and a.applyType=6) t where t.companyId=c.id group by t.companyId) as drivePrice,"+
						"(select sum(grantPrice) from (select a.companyId,(select sum(grantPrice) from applyCollect where approveId=a.id) as grantPrice from approveevent a where (a.dealDepId="+departmentId+" or find_in_set("+departmentId+",a.flow)) and a.applyType=7) t where t.companyId=c.id group by t.companyId) as trafficPrice,"+
						"(select sum(grantPrice) from (select a.companyId,(select sum(grantPrice) from applyCollect where approveId=a.id) as grantPrice from approveevent a where (a.dealDepId="+departmentId+" or find_in_set("+departmentId+",a.flow)) and a.applyType=8) t where t.companyId=c.id group by t.companyId) as newpeoplePrice,"+
						"(select sum(grantPrice) from (select a.companyId,(select sum(grantPrice) from applyCollect where approveId=a.id) as grantPrice from approveevent a where (a.dealDepId="+departmentId+" or find_in_set("+departmentId+",a.flow)) and a.applyType=9) t where t.companyId=c.id group by t.companyId) as newpeoplechildPrice"+
	
						" from company c where  c.isDelete=1 and c.governmentId="+governmentId + " order by c.createTime desc ";
				ResultSet rs = statement.executeQuery(sql);
				list = Common.resultSetToList(rs);
				rs.close();
				con.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
}
