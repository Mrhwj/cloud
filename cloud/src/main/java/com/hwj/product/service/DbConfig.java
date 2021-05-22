package com.hwj.product.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.hwj.product.model.ModelSQL;
import com.hwj.product.tools.Common;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;


abstract public class DbConfig {
	//public Connection con;
	// 驱动程序名
	public String driver = "com.mysql.jdbc.Driver";
	// URL指向要访问的数据库名
	public String url = "jdbc:mysql://192.168.0.3/dhdjy";
	// public String url = "jdbc:mysql://106.15.91.210/djy";
	// public String url = "jdbc:mysql://192.168.1.6/djy";

	// MySQL配置时的用户名
	public String user = "sa";
	// MySQL配置时的密码
	public String password = "abc123";

	public ModelSQL modelSql;
	public Properties properties;

	public DbConfig() {
		// 加载驱动程序
		try {
			Class.forName(driver);
			properties = getDbConfig();
			//properties = null;		//私有化部署
			if (properties != null) {
				url = properties.getProperty("url");
				user = properties.getProperty("user");
				password = properties.getProperty("pwd");
			}
			if (modelSql == null)
				modelSql = new ModelSQL();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 关闭连接
	 * @author scl
	 * @date 2021年4月27日
	 * @param rs
	 * @param statement
	 * @param con
	 */
	public void closeConn(ResultSet rs, Statement statement, Connection con) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}



	public int getTableMaxId(String tablename) throws SQLException {
		int MaxID = 0;
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				statement = (Statement) con.createStatement();
				String sql = String.format("select max(id) from %s", tablename);
				rs = statement.executeQuery(sql);

				rs.next();
				MaxID = rs.getInt(1);

				rs.close();
				statement.close();
				con.close();
			}
		} catch (Exception e) {
			closeConn(rs, statement, con);
			e.printStackTrace();
		}
		return MaxID + 1;
	}

	/**
	 * 查找指定表的最大指定字段值
	 * 
	 * @param tablename
	 * @param field
	 * @return
	 * @throws SQLException
	 */
	public int getTableMaxIdwithField(String tablename, String field) throws SQLException {
		int MaxID = 0;
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				statement = (Statement) con.createStatement();
				String sql = String.format("select max(%s) from %s", field, tablename);
				rs = statement.executeQuery(sql);

				rs.next();
				MaxID = rs.getInt(1);

				rs.close();
				statement.close();
				con.close();
			}
		} catch (Exception e) {
			closeConn(rs, statement, con);
			e.printStackTrace();
		}
		return MaxID + 1;
	}



	/**
	 * SQL 语句参数预初始化
	 * 
	 * @param object
	 * @param preparedStatement
	 * @param type
	 */
	protected void initPreparedStatement(Object object, PreparedStatement preparedStatement, String type) {
		try {
			String className = object.getClass().getName();
			Class<?> clazz = Class.forName(className);
			Field[] fields = clazz.getDeclaredFields();
			int fieldsLen = (type == "update") ? fields.length - 1 : fields.length;
			int i = (type == "update") ? fieldsLen : 1;

			for (Field f : fields) {
				String fieldTypeName = f.getType().getName();
				f.setAccessible(true);

				if (fieldTypeName == "createDate" && type == "update") {

				} else {
					if (i > fieldsLen)
						i = i % fieldsLen;

					if (fieldTypeName == "int" || "java.lang.Integer".equals(fieldTypeName)
							|| fieldTypeName == "double") {
						preparedStatement.setInt(i, 0);
					}
					if (fieldTypeName == "java.util.Date") {
						preparedStatement.setTimestamp(i, new java.sql.Timestamp(new Date().getTime()));
					}
					if (fieldTypeName == "java.lang.String") {
						preparedStatement.setString(i, null);
					}
					i++;
				}
			}

		} catch (ClassNotFoundException | IllegalArgumentException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected boolean isNullOrEmpty(String value) {
		if (value == null || value.isEmpty())
			return true;
		else
			return false;
	}

	protected boolean isNullOrEmpty(Integer value) {
		if (value == null)
			return true;
		else
			return false;
	}

	protected int getIntegerVal(Integer val) {
		if (val == null)
			return 0;
		else
			return val.intValue();
	}

	protected double getDoubleVal(double val) {
		DecimalFormat df = new DecimalFormat("#.00");
		String strVal = df.format(val);
		double doubleVal = Double.parseDouble(strVal);
		return doubleVal;
	}

	protected java.sql.Date getDateVal(java.util.Date date) {
		if (date == null)
			return null;
		java.sql.Date sDate = new java.sql.Date(date.getTime());
		return sDate;
	}
	
	protected java.sql.Timestamp getTimestampVal(java.util.Date date) {
		if (date == null)
			return null;
		java.sql.Timestamp ts = new java.sql.Timestamp(date.getTime());
		return ts;
	}

	/**
	 * 生成通用的插入语句
	 * 
	 * @param clazz
	 * @return
	 */
	public String getInsertSql(Class<?> clazz) {
		StringBuffer bufferFront = new StringBuffer();
		StringBuffer bufferLast = new StringBuffer();
		String tableName = clazz.getSimpleName();
		bufferFront.append("insert into " + tableName.substring(0, 1).toLowerCase()
				+ tableName.substring(1, tableName.length()) + "(");
		bufferLast.append(" values(");
		// 通过class得到所有的属性不受访问控制符空值
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			bufferFront.append(field.getName() + ",");
			bufferLast.append("?,");
		}
		bufferFront.delete(bufferFront.length() - 1, bufferFront.length());
		bufferLast.delete(bufferLast.length() - 1, bufferLast.length());
		bufferFront.append(")");
		bufferLast.append(")");
		bufferFront.append(bufferLast);

		return bufferFront.toString();
	}

	/**
	 * 生成通用的更新语句
	 * 
	 * @param clazz
	 * @return
	 */
	public String getUpdateSql(Class<?> clazz) {

		StringBuffer bufferFront = new StringBuffer();
		StringBuffer bufferLast = new StringBuffer();
		String tableName = clazz.getSimpleName();
		bufferFront.append("update " + tableName.substring(0, 1).toLowerCase()
				+ tableName.substring(1, tableName.length()) + " set ");
		bufferLast.append(" where id=?");
		// 通过class得到所有的属性不受访问控制符空值
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (field.getName().equals("id"))
				continue;
			bufferFront.append(field.getName() + "=?,");
		}
		bufferFront.delete(bufferFront.length() - 1, bufferFront.length());
		bufferFront.append(bufferLast);
		return bufferFront.toString();
	}

	/**
	 * 获取自定义结果List
	 * 
	 * @param FieldStr
	 * @param param
	 * @param orderStr
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getCustomList(String FieldStr, String tableName, String param, String orderStr) {
		List list = null;
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				statement = (Statement) con.createStatement();
				String sql = String.format("select %s from %s where %s %s", FieldStr, tableName, param, orderStr);
				rs = statement.executeQuery(sql);
				
//				System.out.println(sql);
				
				list = Common.resultSetToList(rs);

				rs.close();
				statement.close();
				con.close();
			}

		} catch (SQLException e) {
			closeConn(rs, statement, con);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 执行sql 语句
	 * @param sqlStr
	 */
	public void ExecuteUpdateSQL(String sqlStr) {
		Connection con = null;
		Statement statement = null;
		try {
			con = (Connection) DriverManager.getConnection(url, user, password);

			if (!con.isClosed()) {
				statement = (Statement) con.createStatement();
				String sql = sqlStr;
				statement.executeUpdate(sql);
				statement.close();
				con.close();
			}

		} catch (SQLException e) {
			closeConn(null, statement, con);
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 执行SQL 返回list 结果集
	 * @param sqlStr
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getCustomList(String sqlStr) {
		List list = null;
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			con = (Connection) DriverManager.getConnection(url, user, password);

			if (!con.isClosed()) {
				statement = (Statement) con.createStatement();
				String sql = sqlStr;
				rs = statement.executeQuery(sql);
				list = Common.resultSetToList(rs);

				rs.close();
				statement.close();
				con.close();
			}

		} catch (SQLException e) {
			closeConn(rs, statement, con);
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 获取自定义结果List 分页 170718scl
	 * 
	 * @param FieldStr
	 * @param param
	 * @param orderStr
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getCustomList(int pageIndex, int pageSize, String FieldStr, String tableName, String param,
			String orderStr, int[] args) {
		List list = null;
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			con = (Connection) DriverManager.getConnection(url, user, password);

			if (!con.isClosed()) {
				statement = (Statement) con.createStatement();
				int startpos = (pageIndex - 1) * pageSize;
				String sql = String.format("select %s from %s where %s %s LIMIT %d,%d", FieldStr, tableName, param,
						orderStr, startpos, pageSize);
				
				//System.out.println(sql);
				rs = statement.executeQuery(sql);
				list = Common.resultSetToList(rs);
				rs.close();

				if (args != null) {
					sql = String.format("select count(id) from %s where %s %s", tableName, param, orderStr);
					rs = statement.executeQuery(sql);
					rs.next();
					args[0] = rs.getInt(1);
					rs.close();
				}
				statement.close();
				con.close();
			}

		} catch (SQLException e) {
			closeConn(rs, statement, con);
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 获取配置文件属性
	 * 
	 * @return
	 */
	private Properties getDbConfig() {
		
		Properties prop = new Properties();
	
		try {
			InputStream inputStream = this.getClass().getResourceAsStream("DbConfig.properties");
			prop.load(inputStream);
		} catch (IOException e) {
			//e.printStackTrace();
			prop = null;
		}

		return prop;
	}
	
	/**
	 * 获取配置文件属性
	 * 
	 * @return
	 */
	public Properties getFileConfig(String fileName) {
		Properties prop = new Properties();
		InputStream inputStream = this.getClass().getResourceAsStream(fileName + ".properties");

		try {
			prop.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;
	}

	/**
	 * 批量修改某表某字段某值
	 * 
	 * @param tableName
	 * @param ids
	 * @param columnName
	 * @param value
	 */
	public Integer Amend(String tableName, String ids, String columnName, Object value) {
		Connection con = null;
		Statement statement = null;
		try {
			con = (Connection) DriverManager.getConnection(url, user, password);

			if (!con.isClosed()) {
				statement = (Statement) con.createStatement();
				String sql = String.format("Update `%s` set `%s`='%s' where id in (%s)", tableName, columnName,
						String.valueOf(value), ids);
				int re = statement.executeUpdate(sql);
				statement.close();
				con.close();
				return re;
			}
		} catch (SQLException e) {
			closeConn(null, statement, con);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取带自增列的自定义结果集
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param FieldStr
	 * @param tableName
	 * @param param
	 *            排序前条件 以and开始
	 * @param orderStr
	 * @param param2
	 *            排序后条件 以where开始，没有请为空
	 * @param args
	 *            为null不分页
	 * @return
	 * @author scl
	 * @date 2018年9月18日
	 */
	@SuppressWarnings("rawtypes")
	public List getCustomRowList(int pageIndex, int pageSize, String FieldStr, String tableName, String param,
			String orderStr, String param2, int[] args) {
		List list = null;
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			con = (Connection) DriverManager.getConnection(url, user, password);

			if (!con.isClosed()) {
				statement = (Statement) con.createStatement();
				String sql = String.format(
						"select t1.* from(select CAST((@i:=@i+1) as signed) as `row`,t.* from (select %s from %s where 1=1 %s %s)t, (SELECT @i:=0) as `row`) t1 %s",
						FieldStr, tableName, param, orderStr, param2);
				if (args != null) {
					int startpos = (pageIndex - 1) * pageSize;
					sql += String.format(" limit %s,%s", startpos, pageSize);
				}
				
				//LoveTools.ConsolePrint(sql);
				rs = statement.executeQuery(sql);
				
				list = Common.resultSetToList(rs);
				rs.close();

				if (args != null) {
					sql = String.format("select count(*) from %s where 1=1 %s", tableName, param);
					rs = statement.executeQuery(sql);
					rs.next();
					args[0] = rs.getInt(1);
					rs.close();
				}
				statement.close();
				con.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	abstract public void add(Object model);

	abstract public void modify(Object model);

	abstract public void delete(String ids);
}
