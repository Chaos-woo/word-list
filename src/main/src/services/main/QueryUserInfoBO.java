package main.src.services.main;

import main.src.bean.RecordBean;
import main.src.common.Semaphore;
import main.src.utils.main.db.DatabaseQueryTool;

import java.sql.ResultSet;
import java.util.ArrayList;

public class QueryUserInfoBO{
	private ArrayList<Object> userList = new ArrayList<>();
	public QueryUserInfoBO(){
		userList.add(new RecordBean(Semaphore.getCurrentUser()));
	}

	public void queryInfo(){
		ResultSet rs = DatabaseQueryTool.query(userList);
		try{
			assert rs != null;
			System.out.println("id | start_time | end_time | test_word_count");
			int i = 1;
			while (rs.next()){
				System.out.println(i+" | "
				+ rs.getString("start_time")+ " | "
				+ rs.getString("end_time")+" | "
				+ rs.getInt("test_word_count"));
				i++;
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
