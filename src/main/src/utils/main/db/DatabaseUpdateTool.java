package main.src.utils.main.db;

import main.src.bean.WordBean;
import main.src.common.Semaphore;
import main.src.db.main.DatabaseOperateDAO;

import java.util.ArrayList;

public class DatabaseUpdateTool {
	public static void update(ArrayList<WordBean> list){
		StringBuilder sql = new StringBuilder("UPDATE wordlist ");
		StringBuilder setPart1 = new StringBuilder("SET chinese = CASE english ");
		StringBuilder setPart2 = new StringBuilder("SET pronunciation = CASE english ");
		StringBuilder setPart3 = new StringBuilder("SET sound = CASE english ");
		StringBuilder setPart4 = new StringBuilder("SET selected_count = CASE english ");
		StringBuilder setPart5 = new StringBuilder("SET correct_count = CASE english ");
		StringBuilder endPart = new StringBuilder("WHERE english IN (");
		list.forEach(i->{
			String key = i.getEnglish();
			setPart1.append("WHEN "+ key +" THEN \'"+i.getChinese()+"\' ");
			setPart2.append("WHEN "+ key +" THEN \'"+i.getPronunciation()+"\' ");
			setPart3.append("WHEN "+ key +" THEN \'"+i.getSound()+"\' ");
			if(!Semaphore.isReadWordModel()){
				setPart4.append("WHEN "+ key +" THEN \'"+i.getSelectedCount()+"\' ");
				setPart5.append("WHEN "+ key +" THEN \'"+i.getCorrectCount()+"\' ");
			}
			endPart.append(key+",");
		});
		String end = endPart.subSequence(0,endPart.length()-1).toString();
		sql.append(setPart1).append(setPart2).append(setPart3);
		if(!Semaphore.isReadWordModel()){
			sql.append(setPart4).append(setPart5);
		}
		sql.append(end).append(");");
		System.out.println(DatabaseOperateDAO.updateAndInsert(sql.toString())+"rows had effected.");
	}
}
