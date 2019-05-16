package main.src.db.test;

import main.src.bean.RecordBean;
import main.src.bean.UserBean;
import main.src.utils.main.db.DatabaseQueryTool;
import main.src.utils.main.system.SystemTools;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class QueryTest {
	@Test
	void wordTest(){
		ArrayList<Integer> randomList = SystemTools.randomNumberList(10,100);
		ArrayList<Object> wordIdList = new ArrayList<>(randomList);
		DatabaseQueryTool.query(wordIdList);
	}

	@Test
	void userTest(){
		UserBean u = new UserBean("root");
		ArrayList<Object> userList = new ArrayList<>();
		userList.add(u);
		DatabaseQueryTool.query(userList);
	}

	@Test
	void recordTest(){
		//"root" is current user name
		RecordBean rb = new RecordBean("root");
		ArrayList<Object> recordList = new ArrayList<>();
		recordList.add(rb);
		DatabaseQueryTool.query(recordList);
	}
}
