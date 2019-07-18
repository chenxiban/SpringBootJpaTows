package com.comment.utils;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * 注意:实体类的属性名称必须和数据库字段名称相同
 * 如果类属性与表字段不同,请在SQL语句中起别名保持一致.
 * SQL语句中没有的字段,实体类中属性取属性的默认值
 * @Description:   把JDBC的结果集转为Java实体类集合
 * @ClassName:     ObjectUtilsStatic.java 
 * @author         MS 
 */
public class ObjectUtilsStatic {
	
	private static Class<?> currentClass = null;//当前对象的描述
	private static int fieldLength = 0;//当前对象属性个数
	private static Field[] fields = null;//当前对象所有的字段
	private static String[] fieldNames = null;//当前对象所有的字段名称
	
	
	
	
	
	/**
	 * 把SQL结果集 ResultSet 转化成对象集合
	 * @param <T>
	 * @param rSet
	 * @param clazz
	 * @return
	 */
	static <T>T resultSetToObjectList(ResultSet rSet,Class<T> clazz){
		List<Object> list = new ArrayList<Object>();
		Object object = null;
		//获取当前对象的描述
		currentClass = clazz;
		//获取当前对象的所有属性
		fields = currentClass.getDeclaredFields();
		fieldLength = fields.length;
		fieldNames = new String[fieldLength];
		for(int i = 0;i<fieldLength;i++){
			fieldNames[i]= fields[i].getName();
		}		
		//遍历结果集合
		boolean hasNest = false;//默认结果集合没有数据
		try {
			hasNest = rSet.next();
		} catch (SQLException e) {
			//  TODO 此处省略记录日志
			e.printStackTrace();
		}
		while(hasNest){
			try {
				object = currentClass.newInstance();
			} catch (InstantiationException e) {
				//  TODO 此处省略记录日志
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO 此处省略记录日志
				e.printStackTrace();
			}//构造一个对象实例
			for(int i=0;i<fieldLength;i++){//给对象的每个字段设置值
				Object value = null;
				try {//根据对象的属性名称获取数据库对应字段的值
					value = rSet.getObject(fieldNames[i]);//根据对象的属性名称获取数据库对应字段的值
					fields[i].setAccessible(true);//设置对象属性允许访问，以便设置值
//					System.out.println(fields[i]+"-:"+value+"----->"+object); //输出当前正在设置的字段及其值
					fields[i].set(object, value);//给对象的该字段设置结果集合中对应的值
				} catch (SQLException e) {
					// TODO 此处省略记录日志
//					System.out.println("**********警告信息：结果结合中没有对象属性的值 -->"+fieldNames[i]);
				} catch (IllegalArgumentException e) {//值类型与参数类型不匹配
//					e.printStackTrace();//数据库无符号int查出后类型超出java int范围,所以为Long类型,会产生类型转换异常
					try {
						//处理无符号int即Long类型设置给int字段的类型转换异常
						fields[i].set(object, value instanceof Long ?Integer.parseInt(Long.toString((Long)value)):value);
					} catch (NumberFormatException e1) {
						e1.printStackTrace();
					} catch (IllegalArgumentException e1) {
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						e1.printStackTrace();
					}//给对象的该字段设置结果集合中对应的值										
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
//			System.out.println("所有字段的值 "+value);
			}
			list.add(object);
			try {
				hasNest = rSet.next();//指向结果集合中的下一行数据
//				System.out.println("--------------------------下一行数据  => "+ ( ( hasNest==true)?"还有":"没有了"));
			} catch (SQLException e) {
				//  TODO 此处省略记录日志
				e.printStackTrace();
			}
		}
		return (T) list;
	}
	
	

}
