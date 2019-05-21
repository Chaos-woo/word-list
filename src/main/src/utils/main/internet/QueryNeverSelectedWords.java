package main.src.utils.main.internet;

import main.src.bean.WordBean;
import main.src.common.Container;
import main.src.db.main.DatabaseOperateDAO;
import main.src.utils.main.db.DatabaseQueryTool;

import java.sql.ResultSet;

public class QueryNeverSelectedWords extends QueryChineseByBing {

	public QueryNeverSelectedWords() {
		super();
		ResultSet rs = DatabaseQueryTool.queryNeverSelectedWord();
		try{
			assert rs != null;
			while (rs.next()){
				Container.oldWordList.add(new WordBean(
						rs.getInt("id"),rs.getString("english"),
						rs.getString("chinese"),rs.getString("pronunciation"),
						rs.getString("sound"),rs.getInt("selected_count"),
						rs.getInt("correct_count")));
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
