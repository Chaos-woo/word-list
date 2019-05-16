package main.src.services.main;

import main.src.bean.RecordBean;
import main.src.utils.main.db.DatabaseInsertTool;

import java.util.ArrayList;

public class InsertRecordBO {
	private ArrayList<Object> recordList;

	public InsertRecordBO(RecordBean rb){
		recordList = new ArrayList<>();
		recordList.add(rb);
	}

	public void insertRecord(){
		DatabaseInsertTool.insert(recordList);
	}

}
