package com.hwj.product.service;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hwj.product.model.User;
import com.hwj.product.tools.Common;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;


@Service
public class UserService extends DbConfig {

	@Override
	public void add(Object model) {
		// TODO Auto-generated method stub
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				User Model = (User)model;
				Model.setId(getTableMaxId("`user`"));
				Model.setCreateTime(new Date(System.currentTimeMillis()));
				Model.setIsDelete(1);
				Model.setPassword(Common.MD5("123", 16));
				String sql = SqlSentence.getInsertSql("`user`", User.class, Model);
				statement.execute(sql);
				con.close();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public int addID(Object model) {
		// TODO Auto-generated method stub
		int id=0;
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				User Model = (User)model;
				Model.setId(getTableMaxId("`user`"));
				Model.setCreateTime(new Date(System.currentTimeMillis()));
				Model.setIsDelete(1);
				if(Common.isNullOrEmpty(Model.getPassword()))
					Model.setPassword(Common.MD5("123", 16));
				String sql = SqlSentence.getInsertSql("`user`", User.class, Model);
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
				User Model = (User)model;
				String sql = SqlSentence.getUpdateSql("`user`", User.class, Model);
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
				String sql = String.format("update `user` set isDelete=0 where id in (%s)", ids);
				statement.executeUpdate(sql);
				con.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException("执行失败:" + e.getMessage(), e);
		}
	}
	
	public User Login(String name,String pwd) {
		User model=null;
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				String sql = String.format("select * from `user` where name='%s' and password='%s' and isDelete=1 and active=1 and roleId!=5", name,pwd);
				ResultSet rs = statement.executeQuery(sql);
				if(rs.next()) {
					model = new User(rs);
				}
				rs.close();
				con.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException("执行失败:" + e.getMessage(), e);
		}
		return model;
	}
	public User getModel(String id) {
		User model = null;
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				String sql = String.format("select * from `user` where id=%s and isDelete=1", id);
				ResultSet rs = statement.executeQuery(sql);
				if(rs.next()) {
					model = new User(rs);
				}
				rs.close();
				con.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return model;
	}
	public User getModelByPhone(String phone) {
		User model = null;
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				String sql = String.format("select * from `user` where phone='%s' and isDelete=1", phone);
				ResultSet rs = statement.executeQuery(sql);
				if(rs.next()) {
					model = new User(rs);
				}
				rs.close();
				con.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return model;
	}
	public User getModelByPhone(String phone,int companyId) {
		User model = null;
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				String sql = String.format("select * from `user` where phone='%s' and companyId='%d' and isDelete=1 and roleId=5", phone,companyId);
				ResultSet rs = statement.executeQuery(sql);
				if(rs.next()) {
					model = new User(rs);
				}
				rs.close();
				con.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return model;
	}
	public User getModelByOpenId(String openId) {
		User model = null;
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				String sql = String.format("select * from `user` where openId='%s' and isDelete=1 ", openId);
				ResultSet rs = statement.executeQuery(sql);
				if(rs.next()) {
					model = new User(rs);
				}
				rs.close();
				con.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return model;
	}
	public User phoneLogin(String phone) {
		User model = null;
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				String sql = String.format("select * from `user` where phone='%s' and isDelete=1 and active=1 ", phone);
				ResultSet rs = statement.executeQuery(sql);
				if(rs.next()) {
					model = new User(rs);
				}
				rs.close();
				con.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return model;
	}
	public User phoneLogin(String phone,String pwd) {
		User model = null;
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				String sql = String.format("select * from `user` where phone='%s' and password='%s' and isDelete=1 and active=1 and roleId!=5 ", phone,pwd);
				ResultSet rs = statement.executeQuery(sql);
				if(rs.next()) {
					model = new User(rs);
				}
				rs.close();
				con.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return model;
	}
	public List phoneLoginList(String phone) {
		List list = new ArrayList();
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				String sql = String.format("select * from `user` where phone='%s' and isDelete=1 and active=1 ", phone);
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
	public User getModelByRoleIdAndDepIdAndGovId(int roleId,String depId,int govId) {
		User model = null;
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				String sql = String.format("select * from `user` where roleId=%d and departmentId in (%s) and governmentId=%d and isDelete=1", roleId,depId,govId);
				ResultSet rs = statement.executeQuery(sql);
				if(rs.next()) {
					model = new User(rs);
				}
				rs.close();
				con.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return model;
	}
	
	public User getModelByRoleIdAndDepId(int roleId,String depId) {
		User model = null;
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				String sql = String.format("select * from `user` where roleId=%d and departmentId=%s and isDelete=1", roleId,depId);
				ResultSet rs = statement.executeQuery(sql);
				if(rs.next()) {
					model = new User(rs);
				}
				rs.close();
				con.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return model;
	}
	public List getStaffList() {
		List list = new ArrayList();
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				String sql = String.format("select * from `user` where roleId=5 and  isDelete=1 and openId!='' and openId is not null and active=1");
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
	public List getStaffList2() {
		List list = new ArrayList();
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				String sql = String.format("select *,(select count(1) from sign where userId = `user`.id and TO_DAYS(createTime) = TO_DAYS(NOW())) as sign from `user` where roleId=5 and  isDelete=1 and openId!='' and openId is not null and active=1");
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
	public List getStaffList(String companyId) {
		List list = new ArrayList();
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				String sql = String.format("select * from `user` where roleId=5 and  isDelete=1  and active=1 and companyId='%s'",companyId);
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
	public int getStaffNumber(String cond) {
		int count=0;
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				String sql = String.format("select count(1) as number from  `user`  where  isDelete=1 and roleId=5 %s",cond);
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
	public int getStaffNumberByGovernment(int governmentId) {
		int count=0;
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				String sql = String.format("select count(1) as number from  (SELECT `user`.*,`company`.governmentId  as government FROM `user` LEFT JOIN `company` on (`user`.companyId = `company`.id) where `user`.roleId=5 and `user`.isDelete=1 and `company`.isDelete=1)t  where  government=%d",governmentId);
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
	public void resetPwd(String id,String pwd) {
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				String sql=String.format("update `user` set password='%s' where id='%s' ", pwd,id);
				statement.executeUpdate(sql);
				con.close();
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public String getCompanyIds(String phone) {
		String companyIds="";
		try {
			try {
				Connection con = (Connection) DriverManager.getConnection(url, user, password);
				if (!con.isClosed()) {
					Statement statement = (Statement) con.createStatement();
					String sql = String.format("select GROUP_CONCAT(companyId) as companyIds from `user` where phone ='%s' and isDelete=1 and active=1 and roleId=4",phone);
					ResultSet rs = statement.executeQuery(sql);
					if(rs.next())
						companyIds = rs.getString(1);
					rs.close();
					con.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return companyIds;
	}
	public List getUserCompanyList(String phone) {
		List list = new ArrayList();
		try {
			try {
				Connection con = (Connection) DriverManager.getConnection(url, user, password);
				if (!con.isClosed()) {
					Statement statement = (Statement) con.createStatement();
					String sql = String.format("select `user`.id,(select name from `company` where id = `user`.companyId) as company from `user` where phone ='%s' and isDelete=1 and active=1 and roleId=4",phone);
					ResultSet rs = statement.executeQuery(sql);
					list = Common.resultSetToList(rs);
					rs.close();
					con.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	public boolean checkPhone(String phone,int userId) {
		try {
			try {
				Connection con = (Connection) DriverManager.getConnection(url, user, password);
				if (!con.isClosed()) {
					Statement statement = (Statement) con.createStatement();
					String sql = String.format("select * from `user` where phone='%s' and isDelete=1 and id!=%d",phone,userId);
					ResultSet rs = statement.executeQuery(sql);
					if(rs.next())
						return true;
					rs.close();
					con.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
	public void deleteByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				String sql = String.format("update `user` set isDelete=0 where companyId=%d and roleId=4", companyId);
				statement.executeUpdate(sql);
				con.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException("执行失败:" + e.getMessage(), e);
		}
	}
	public List getTotalStay(String cond) {
		List list = new ArrayList();
		try {
			try {
				Connection con = (Connection) DriverManager.getConnection(url, user, password);
				if (!con.isClosed()) {
					Statement statement = (Statement) con.createStatement();
					String sql = String.format("select sum(case when isStay=1 and isDriving=0 then 1 else 0 end) as stay,SUM(case when isDriving=1 and isStay=0 then 1 else 0 end) as driving,sum(case when isStay=0 and isDriving=0 then 1 else 0 end) as publish from `user` LEFT JOIN `company` on (`company`.id = `user`.companyId) where  roleId=5 and `user`.isDelete=1 %s",cond);
					//System.out.println(sql);
					ResultSet rs = statement.executeQuery(sql);
					list = Common.resultSetToList(rs);
					rs.close();
					con.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	public List getNoSignUser() {
		List list = new ArrayList();
		try {
			try {
				Connection con = (Connection) DriverManager.getConnection(url, user, password);
				if (!con.isClosed()) {
					Statement statement = (Statement) con.createStatement();
					String sql = String.format("select * from  (select id,idCard,count from `user` LEFT JOIN (select count(1) as count,userId from `sign` GROUP BY userId)b on (`user`.id = b.userId) where isDelete=1 and roleId=5)t where count is null");
					//System.out.println(sql);
					ResultSet rs = statement.executeQuery(sql);
					list = Common.resultSetToList(rs);
					rs.close();
					con.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	//员工统计
	public List getTotalStaff(String cond) {
		List list = new ArrayList();
		try {
			try {
				Connection con = (Connection) DriverManager.getConnection(url, user, password);
				if (!con.isClosed()) {
					Statement statement = (Statement) con.createStatement();
					String sql = String.format("select sum(case when isStay=1 and isDriving=0 then 1 else 0 end) as stay,SUM(case when isDriving=1 and isStay=0 then 1 else 0 end) as driving,sum(case when isStay=0 and isDriving=0 then 1 else 0 end) as publish from `user` LEFT JOIN `company` on (`company`.id = `user`.companyId) where  roleId=5 and `user`.isDelete=1  %s",cond);
					//System.out.println(sql);
					ResultSet rs = statement.executeQuery(sql);
					list = Common.resultSetToList(rs);
					rs.close();
					con.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	//单位当天签到
	public List getCompanySignStaff(String companyId,String endTime) {
		List list = new ArrayList();
		try {
			try {
				Connection con = (Connection) DriverManager.getConnection(url, user, password);
				if (!con.isClosed()) {
					Statement statement = (Statement) con.createStatement();
					String sql = String.format("select a.nameCN from (select * from `user` where roleId=5 and companyId=%s and isDelete=1)a LEFT JOIN (select * from `sign` where TO_DAYS(`sign`.createTime) = TO_DAYS('"+endTime+"'))b on (a.id=b.userId) where b.id is not null",companyId);
					System.out.println(sql);
					ResultSet rs = statement.executeQuery(sql);
					list = Common.resultSetToList(rs);
					rs.close();
					con.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	public List getCompanyNoSignStaff(String companyId,String endTime) {
		List list = new ArrayList();
		try {
			try {
				Connection con = (Connection) DriverManager.getConnection(url, user, password);
				if (!con.isClosed()) {
					Statement statement = (Statement) con.createStatement();
					String sql = String.format("select a.nameCN from (select * from `user` where roleId=5 and companyId=%s and isDelete=1)a LEFT JOIN (select * from `sign` where TO_DAYS(`sign`.createTime) = TO_DAYS('"+endTime+"'))b on (a.id=b.userId) where b.id is null",companyId);
					//System.out.println(sql);
					ResultSet rs = statement.executeQuery(sql);
					list = Common.resultSetToList(rs);
					rs.close();
					con.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	
	public List getStaySignList(String cond,String endTime) {
		List list = new ArrayList();
		try {
			try {
				Connection con = (Connection) DriverManager.getConnection(url, user, password);
				if (!con.isClosed()) {
					Statement statement = (Statement) con.createStatement();
					String sql = String.format("select DISTINCT `user`.id,`user`.companyId,`company`.governmentId,`user`.nameCN,`sign`.address from `user` LEFT JOIN (select * from `sign` where TO_DAYS(`sign`.createTime) = TO_DAYS('"+endTime+"')) as  `sign` on (`user`.id = `sign`.userId) LEFT JOIN `company` on (`company`.id = `user`.companyId)   where `user`.isDelete=1 and `user`.roleId=5 and `user`.isStay=1  %s",cond);
					//System.out.println(sql);
					ResultSet rs = statement.executeQuery(sql);
					list = Common.resultSetToList(rs);
					rs.close();
					con.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	public List getStaySignTotal(String cond,String endTime) {
		List list = new ArrayList();
		try {
			try {
				Connection con = (Connection) DriverManager.getConnection(url, user, password);
				if (!con.isClosed()) {
					Statement statement = (Statement) con.createStatement();
					String sql = "select count(1) as count,IFNULL(sum(case when address is null  then 1 else 0 end),0) as noSign,IFNULL(sum(case when  address is not null then 1 else 0 end),0) as sign,IFNULL(sum(case when  address is not null and address not like '%嘉兴%' then 1 else 0 end),0) as errSign from (select DISTINCT `user`.id,`user`.companyId,`company`.governmentId,`user`.nameCN,`sign`.address from `user` LEFT JOIN (select * from `sign` where TO_DAYS(`sign`.createTime) = TO_DAYS('"+endTime+"')) as  `sign` on (`user`.id = `sign`.userId) LEFT JOIN `company` on (`company`.id = `user`.companyId)   where `user`.isDelete=1 and `user`.roleId=5 and `user`.isStay=1 "+cond+")t ";
					//System.out.println(sql);
					ResultSet rs = statement.executeQuery(sql);
					list = Common.resultSetToList(rs);
					rs.close();
					con.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	public List getReportUserList() {
		List list = new ArrayList();
		try {
			try {
				Connection con = (Connection) DriverManager.getConnection(url, user, password);
				if (!con.isClosed()) {
					Statement statement = (Statement) con.createStatement();
					String sql = "select * from `user` where roleId!=4 and roleId!=5 and isDelete=1";
					//System.out.println(sql);
					ResultSet rs = statement.executeQuery(sql);
					list = Common.resultSetToList(rs);
					rs.close();
					con.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	public List getUser2List() {
		List list = new ArrayList();
		try {
			try {
				Connection con = (Connection) DriverManager.getConnection(url, user, password);
				if (!con.isClosed()) {
					Statement statement = (Statement) con.createStatement();
					String sql = "select * from `user_copy2` where isDelete=1";
					//System.out.println(sql);
					ResultSet rs = statement.executeQuery(sql);
					list = Common.resultSetToList(rs);
					rs.close();
					con.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	public void resetUser2(String id,String phone,String idCard) {
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				String sql=String.format("update `user_copy2` set phone='%s',idCard='%s' where id='%s' ", phone,idCard,id);
				statement.executeUpdate(sql);
				con.close();
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	public void loginOut(String openId) {
		try {
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Statement statement = (Statement) con.createStatement();
				String sql=String.format("update `user` set openId='' where openId='%s' ", openId);
				statement.executeUpdate(sql);
				con.close();
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
}
