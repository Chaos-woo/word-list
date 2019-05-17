package main.src.utils.main.db;

import main.src.annotation.Column;
import main.src.annotation.Table;
import main.src.bean.RecordBean;
import main.src.bean.UserBean;
import main.src.bean.WordBean;
import main.src.common.Semaphore;
import main.src.db.main.DatabaseOperateDAO;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class DatabaseInsertTool {
	public static boolean insert(ArrayList<Object> list){
		StringBuilder sql = new StringBuilder("INSERT INTO ");
		//通过反射获取表名
		Class c = list.get(0).getClass();
		if(!c.isAnnotationPresent(Table.class)){
			return false;
		}
		Table table = (Table)c.getAnnotation(Table.class);
		String tableName = table.value();
		sql.append(tableName+"(");
		//获取字段名
		if("wordlist".equals(tableName)){
			sql.append("english) VALUES ").append(insertWord(list));
		}else if("user".equals(tableName)){
			sql.append(makeUpTableFields(c)).append(insertUser(list));
		}else if("record".equals(tableName)){
			sql.append(makeUpTableFields(c)).append(insertRecord(list));
		}
		int i = DatabaseOperateDAO.updateAndInsert(sql.toString());
		System.out.println(i+" rows had effected");
		return true;
	}

	private static String insertWord(ArrayList<Object> list){
		StringBuilder s = new StringBuilder();
		list.forEach(w->{
			s.append("(\'").append(((WordBean)w).getEnglish()).append("\'),");
		});
		return s.replace(s.length()-1,s.length(),";").toString();
	}

	private static String insertUser(ArrayList<Object> list){
		StringBuilder s = new StringBuilder();
		list.forEach(u->{
			UserBean user = (UserBean)u;
			s.append("(\'").append(user.getName())
					.append("\',\'").append(user.getPassword()).append("\'),");
		});
		return s.replace(s.length()-1,s.length(),";").toString();
	}

	private static String insertRecord(ArrayList<Object> list){
		StringBuilder s = new StringBuilder();
		list.forEach(r->{
			RecordBean rd = (RecordBean)r;
			s.append("(\'").append(rd.getStartTime()).append("\',\'").append(rd.getEndTime())
					.append("\',\'").append(rd.getTestWordCount()).append("\',\'")
					.append(Semaphore.getCurrentUser()).append("\'),");
		});
		return s.replace(s.length()-1,s.length(),";").toString();
	}

	private static String makeUpTableFields(Class c){
		Field[] fields = c.getDeclaredFields();
		StringBuilder s = new StringBuilder();
		for (Field f:fields){
			if(!f.isAnnotationPresent(Column.class)){
				continue;
			}
			s.append(f.getAnnotation(Column.class).value()+",");
		}
		s.replace(s.length()-1,s.length(),")").append(" VALUES ");
		return s.toString();
	}
}
