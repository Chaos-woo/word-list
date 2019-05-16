package main.src.db.test;

import main.src.bean.WordBean;
import main.src.utils.main.db.DatabaseInsertTool;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class InsertTest {
	@Test
	void test(){
		ArrayList<Object> list = new ArrayList<>();
		WordBean w1 = new WordBean("word");
		WordBean w2 = new WordBean("hello");
		list.add(w1);
		list.add(w2);
		DatabaseInsertTool.insert(list);
	}
}
