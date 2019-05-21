package main.src.utils.main.db;

import main.src.annotation.Column;
import main.src.annotation.Table;
import main.src.db.main.DatabaseOperateDAO;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DatabaseQueryTool {
	public static ResultSet query(ArrayList<Object> list){
		StringBuilder sql = new StringBuilder("SELECT * FROM ");
		Class c = list.get(0).getClass();
		if(c.equals(Integer.class)){
			sql.append("wordlist").append(" WHERE 1=1 AND ");
			sql.append("id IN (");
			list.forEach(n->sql.append(n.toString()).append(","));
			sql.replace(sql.length()-1,sql.length(),")").append(";");
			return DatabaseOperateDAO.query(sql.toString());
		}else if(!c.isAnnotationPresent(Table.class)){
			return null;
		}
		Table table = (Table)c.getAnnotation(Table.class);
		String tableName = table.value();
		sql.append(tableName).append(" WHERE 1=1");
		Object filter = list.get(0);
		sql.append(queryUserOrRecord(c,filter));
		return DatabaseOperateDAO.query(sql.toString());
	}

	private static String queryUserOrRecord(Class c,Object filter){
		StringBuilder s = new StringBuilder();
		Field[] fields = c.getDeclaredFields();
		for(Field f:fields){
			if(!f.isAnnotationPresent(Column.class)){
				continue;
			}
			String columnName = f.getAnnotation(Column.class).value();
			String fieldName = f.getName();
			String getMethodName = "get"+fieldName.substring(0,1).toUpperCase()
					+ fieldName.substring(1);
			Object fieldValue = null;
			try {
				Method getMethod = c.getMethod(getMethodName);
				fieldValue = getMethod.invoke(filter);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(fieldValue==null ||
					(fieldValue instanceof Integer && (Integer)fieldValue==0)){
				continue;
			}
			s.append(" AND ").append(columnName);
			if(fieldValue instanceof String){
				s.append("=\'").append(fieldValue).append("\'").append(";");
			}
		}
		return s.toString();
	}

	public static ResultSet queryNeverSelectedWord(){
		String sql = "SELECT * FROM wordlist WHERE selected_count<2";
		return DatabaseOperateDAO.query(sql);
	}
}
