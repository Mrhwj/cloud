package com.hwj.product.model;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang3.reflect.FieldUtils;

public class ModelSQL {
    private Object target;

    private String idName;

    private Object idValue;

    private SqlType currentType;

    public enum SqlType {
        INSERT, UPDATE, DELETE
    }

    public ModelSQL(){}
    
    public ModelSQL(SqlType sqlType, Object target) {
        this.target = target;
        switch (sqlType) {
        case INSERT:
            currentType = SqlType.INSERT;
            createInsert(target);
            break;
        case UPDATE:
            currentType = SqlType.UPDATE;
            createUpdate(target);
            break;
        case DELETE:
            currentType = SqlType.DELETE;
            createDelete(target);
            break;
        }
    }

    public ModelSQL(Class<?> target) {
        String tableName = getTableNameForClass(target);
        getFields(target);

        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("DELETE FROM ").append(tableName).append(" WHERE ");
        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers())) {
                ID id = field.getAnnotation(ID.class);
                if (null != id) {
                    sqlBuffer.append(field.getName()).append("=?");
                }
            }
        }
        this.sqlBuffer = sqlBuffer.toString();
    }

    /**
     * 创建删除
     */
    public String createDelete(Object target) {
    	this.target = target;
    	this.currentType = SqlType.DELETE;
    	
    	this.param.clear();
    	this.fields.clear();
    	
        String tableName = getTableName();
        getFields(target.getClass());
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("DELETE FROM ").append(tableName).append(" WHERE ");
        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers())) {
                ID id = field.getAnnotation(ID.class);
                if (null != id) {
                    sqlBuffer.append(field.getName()).append(" = ? ");
                    param.add(readField(field));
                }
            }
        }
        //System.err.println("delete:\t"+sqlBuffer.toString());
        this.sqlBuffer = sqlBuffer.toString();
        
        return this.sqlBuffer;
    }

    protected Object readField(Field field) {
        try {
            return FieldUtils.readField(field, target, true);
        } catch (Exception e) {
            throw new RuntimeException(currentType.name(), e);
        }
    }

    /**
     * 创建更新语句
     */
    public String createUpdate(Object target) {
    	this.target = target;
    	this.currentType = SqlType.UPDATE;
    	
    	this.param.clear();
    	this.fields.clear();
    	
        String tableName = getTableName();
        getFields(target.getClass());
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("UPDATE ").append(tableName).append(" SET ");

        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers())) {
                ID id = field.getAnnotation(ID.class);
                if (id == null) {
                	if(field.getName()!="createDate" 
                			&& !"createtime".equals(field.getName().toLowerCase())){
                		String fieldName = field.getName();
                		if("RIGHT".equals(fieldName.toUpperCase()))
                    		fieldName = "`" + fieldName + "`";
                		sqlBuffer.append(fieldName).append("=? , ");
                		param.add(readField(field));
                	}
                } else {
                    idName = field.getName();
                    idValue = readField(field);
                }
            }
        }
        sqlBuffer.replace(sqlBuffer.length()-2, sqlBuffer.length()-1, " ");
        if (idName == null) {
            throw new RuntimeException("not found of " + target.getClass() + "'s ID");
        }
        sqlBuffer.append(" WHERE ").append(idName).append("=?");
        param.add(idValue);
        //System.err.println("update:\t"+sqlBuffer.toString());
        this.sqlBuffer = sqlBuffer.toString();
        return this.getSqlBuffer();
    }

    /**
     * 根据注解获取表名
     */
    private String getTableName() {
        String tableName = null;
        Class<?> clazz = target.getClass();
        tableName = getTableNameForClass(clazz);
        return tableName;
    }

    private String getTableNameForClass(Class<?> clazz) {
        String tableName;
        Table table = clazz.getAnnotation(Table.class);
        if (null != table) {
            tableName = table.name();
            if ("".equalsIgnoreCase(tableName)) {
                tableName = clazz.getSimpleName();
            }
        } else {
            tableName = clazz.getSimpleName();
        }
        return tableName;
    }

    /**
     * 创建插入语句
     */
    public String createInsert(Object target) {
    	this.target = target;
    	this.currentType = SqlType.INSERT;
    	
    	this.param.clear();
    	this.fields.clear();
    	
        String tableName = getTableName();
        getFields(target.getClass());
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("INSERT INTO ").append(tableName).append("(");

        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers())) {
//              ID id = field.getAnnotation(ID.class);
//              if (id == null) {
            	String fieldName = field.getName();
            	if("RIGHT".equals(fieldName.toUpperCase()))
            		fieldName = "`" + fieldName + "`";
                sqlBuffer.append(fieldName).append(",");
                param.add(readField(field));
//              }
            }
        }
        int length = sqlBuffer.length();
        sqlBuffer.delete(length - 1, length).append(")values(");
        int size = param.size();
        for (int x = 0; x < size; x++) {
            if (x != 0) {
                sqlBuffer.append(",");
            }
            sqlBuffer.append("?");
        }
        sqlBuffer.append(")");
    	//System.err.println("insert:\t"+sqlBuffer.toString());
        this.sqlBuffer = sqlBuffer.toString();
        // 返回
        return this.sqlBuffer;
    }

    private List<Object> param = new Vector<Object>();

    private String sqlBuffer;

    public List<Object> getParam() {
        return param;
    }

    public String getSqlBuffer() {
        return sqlBuffer;
    }

    public String getIdName() {
        return idName;
    }

    public Object getIdValue() {
        return idValue;
    }

    List<Field> fields = new Vector<Field>();

    protected void getFields(Class<?> clazz) {
        if (Object.class.equals(clazz)) {
            return;
        }
        Field[] fieldArray = clazz.getDeclaredFields();
        for (Field filed : fieldArray) {
            fields.add(filed);
        }
        getFields(clazz.getSuperclass());
    }

    //创建注解,标识该model的table名
    @java.lang.annotation.Target(value = { java.lang.annotation.ElementType.TYPE })
    @java.lang.annotation.Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
    public @interface Table {
        String name() default "";
    }

    //创建注解,标识该model的id字段
    @java.lang.annotation.Target(value = { java.lang.annotation.ElementType.FIELD })
    @java.lang.annotation.Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
    public @interface ID {
    }
}