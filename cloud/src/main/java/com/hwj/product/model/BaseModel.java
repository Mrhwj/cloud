package com.hwj.product.model;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sun.jmx.snmp.Timestamp;

public class BaseModel {
	
	public String keyStr = "86R8k53kfb52768";
	/*
	 * 类对象初始化，初始化所有属性
	 */
	public void initModelField(Object object){
		try {
			String className = object.getClass().getName();
			Class<?> clazz = Class.forName(className);
			Field[] fields = clazz.getDeclaredFields(); 
			
			for (Field f : fields) {
				String fieldTypeName = f.getType().getName();
				f.setAccessible(true);
				if (fieldTypeName == "int" 
						|| "java.lang.Integer".equals(fieldTypeName) 
						|| fieldTypeName == "double") {
					f.set(this, 0);
				}
				if(fieldTypeName == "float" 
						|| "java.lang.Float".equals(fieldTypeName)) {
					f.set(this, (float)0);
				}
				if (fieldTypeName == "java.util.Date") {
					f.set(this,new Date());
				}
				if (fieldTypeName == "java.lang.String") {
				}
				if(fieldTypeName == "java.sql.Timestamp") {
					f.set(this,new Timestamp());
				}
			}
			
		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * 自动遍历并根据类名，初始化相应的类属性
	 */
	public void initWithResultSet(Object object, ResultSet rs) {
		if (rs == null)
			return;
		try {
			boolean isRsEmpty = checkResultEmpty(rs);
			String className = object.getClass().getName();
			Class<?> clazz = Class.forName(className);
			Field[] fields = clazz.getDeclaredFields(); // 根据Class对象获得属性
														// 私有的也可以获得
			
//			Date time1 = null;
//			SimpleDateFormat formattime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			for (Field f : fields) {
				if (rs == null)
					return;
				//System.out.println(f.getName() + ":" + f.getType().getName());//打印每个属性的类型名字
				String fieldTypeName = f.getType().getName();
				f.setAccessible(true);
				if (fieldTypeName == "int" || "java.lang.Integer".equals(fieldTypeName)) {
					//System.out.println(f.getName() + ":" + f.getType().getName());// 打印每个属性的类型名字
					if (isRsEmpty) {
						f.set(this, 0);
					} else {
						if (f.getName() != null)
							f.set(this, rs.getInt(f.getName()));
						else
							f.set(this, rs.getInt(0));
					}
				}
				// add double,chenh
				if (fieldTypeName == "double" || "java.lang.Double".equals(fieldTypeName)) {
					if (isRsEmpty) {
						f.set(this, (double)0);
					} else {
						if (f.getName() != null)
							f.set(this, rs.getDouble(f.getName()));
						else
							f.set(this, rs.getDouble(0));
					}
				}
				if (fieldTypeName == "float" || "java.lang.Float".equals(fieldTypeName)) {
					if (isRsEmpty) {
						f.set(this, (float)0);
					} else {
						if (f.getName() != null)
							f.set(this, rs.getFloat(f.getName()));
						else
							f.set(this, rs.getFloat(0));
					}
				}
				//
				if (fieldTypeName == "java.lang.String") {
					if (isRsEmpty) {
						f.set(this, "");
					} else {
						if (f.getName() != null)
							f.set(this, rs.getString(f.getName()));
						else
							f.set(this, rs.getString(""));
					}
				}
				if (fieldTypeName == "java.util.Date") {
					if (isRsEmpty) {
						f.set(this, null);
					} else {
						if (f.getName() != null){
							if(rs.getDate(f.getName())!=null)
							{
								//f.set(this, rs.getDate(f.getName()));
								Date time1 = new Date(rs.getTimestamp(f.getName()).getTime());
								//System.out.println(time1);
								f.set(this, time1);
							}else{
								f.set(this, null);
							}
						}
						else
							f.set(this, null);
					}
				}
				if (fieldTypeName == "java.sql.Timestamp") {
					if (isRsEmpty) {
						f.set(this, null);
					} else {
						if (f.getName() != null){
							if(rs.getTimestamp(f.getName())!=null)
							{
								
								f.set(this, rs.getTimestamp(f.getName()));
							}else{
								f.set(this, null);
							}
						}
						else
							f.set(this, null);
					}
				}
				f.setAccessible(false);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void initWithResultSet1(Object object, ResultSet rs) {
		if (rs == null)
			return;
		try {
			boolean isRsEmpty = checkResultEmpty(rs);
			String className = object.getClass().getName();
			Class<?> clazz = Class.forName(className);
			Field[] fields = clazz.getDeclaredFields(); // 根据Class对象获得属性
														// 私有的也可以获得
			
//			Date time1 = null;
//			SimpleDateFormat formattime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			for (Field f : fields) {
				if (rs == null)
					return;
				// System.out.println(f.getName() + ":" +
				// f.getType().getName());//打印每个属性的类型名字
				String fieldTypeName = f.getType().getName();
				f.setAccessible(true);
				if (fieldTypeName == "int" || "java.lang.Integer".equals(fieldTypeName)) {
					//System.out.println(f.getName() + ":" + f.getType().getName());// 打印每个属性的类型名字
					if (isRsEmpty) {
						f.set(this, 0);
					} else {
						if (f.getName() != null)
							f.set(this, rs.getInt(f.getName()));
						else
							f.set(this, rs.getInt(0));
					}
				}
				if (fieldTypeName == "java.lang.String") {
					if (isRsEmpty) {
						f.set(this, "");
					} else {
						if (f.getName() != null)
							f.set(this, rs.getString(f.getName()));
						else
							f.set(this, rs.getString(""));
					}
				}
				if (fieldTypeName == "java.util.Date") {
					if (isRsEmpty) {
						f.set(this, null);
					} else {
						if (f.getName() != null){
							if(rs.getDate(f.getName())!=null)
							{
								f.set(this, rs.getDate(f.getName()));
								Date time1 = new Date(rs.getTimestamp(f.getName()).getTime());
								f.set(this, time1);
							}else{
								f.set(this, null);
							}
						}
						else
							f.set(this, null);
					}
				}
				f.setAccessible(false);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private boolean checkResultEmpty(ResultSet rs) {
		boolean status = false;
		/*rs.last(); // 指针移到最后一行
		if (rs.getRow() == 0) {
			status = true;
		} else {
			status = false;
		}

		rs.first(); // 复位结果集*/
		
		try {
			if(rs.getRow()==0){
				status = true;
			}
		} catch (SQLException e) {
			status = true;
		}
		return status;
	}
}
