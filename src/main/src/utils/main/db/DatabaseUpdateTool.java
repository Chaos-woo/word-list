package main.src.utils.main.db;

import main.src.bean.WordBean;
import main.src.common.Semaphore;
import main.src.db.main.DatabaseOperateDAO;

import java.util.ArrayList;

public class DatabaseUpdateTool {
	public static boolean update(ArrayList<WordBean> list){
		boolean flag = false;
		StringBuilder sql = new StringBuilder("UPDATE wordlist ");
		StringBuilder setPart1 = new StringBuilder("SET chinese = CASE id ");
//		StringBuilder setPart2 = new StringBuilder("SET pronunciation = CASE id ");
		StringBuilder setPart3 = new StringBuilder("SET sound = CASE id ");
		StringBuilder setPart4 = new StringBuilder("SET selected_count = CASE id ");
		StringBuilder setPart5 = new StringBuilder("SET correct_count = CASE id ");
		StringBuilder endPart = new StringBuilder("WHERE id IN(");


		list.forEach(i->{
			int key = i.getId();
			setPart1.append("WHEN "+ key +" THEN \'"+i.getChinese()+"\' ");
//			setPart2.append("WHEN "+ key +" THEN \'"+p+"\' ");
			setPart3.append("WHEN "+ key +" THEN \'"+i.getSound()+"\' ");
			setPart4.append("WHEN "+ key +" THEN "+i.getSelectedCount()+" ");
			setPart5.append("WHEN "+ key +" THEN "+i.getCorrectCount()+" ");
			endPart.append(key+",");
		});
		StringBuilder end;
		end = new StringBuilder(endPart.substring(0,endPart.length()-1));
		end.append(");");

		String e = "END ";
		String part1 = sql+setPart1.toString()+e+end;
//		String part2 = sql+setPart2.toString()+e+end;
		String part3 = sql+setPart3.toString()+e+end;
		int i = DatabaseOperateDAO.updateAndInsert(part1);
//		DatabaseOperateDAO.updateAndInsert(part2);
		DatabaseOperateDAO.updateAndInsert(part3);

		String part4 = sql+setPart4.toString()+e+end;
		String part5 = sql+setPart5.toString()+e+end;
		DatabaseOperateDAO.updateAndInsert(part4);
		DatabaseOperateDAO.updateAndInsert(part5);

		System.out.println(i+" rows had effected.");
		if(i>0){
			flag = true;
		}
		return flag;
	}
}
