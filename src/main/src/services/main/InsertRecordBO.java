package main.src.services.main;

import main.src.bean.RecordBean;
import main.src.common.Semaphore;
import main.src.utils.main.db.DatabaseInsertTool;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InsertRecordBO {
	private static ArrayList<Object> recordList;
	private static volatile RecordBean recordInstance;

	static {
		recordList = new ArrayList<>();
	}

	private InsertRecordBO(){}

	public static RecordBean getRecordInstance(){
		if(recordInstance == null){
			synchronized (InsertRecordBO.class){
				if(recordInstance == null){
					recordInstance = new RecordBean(Semaphore.getCurrentUser());
				}
			}
		}
		return recordInstance;
	}

	public static void insertRecord(){
		recordList.add(recordInstance);
		DatabaseInsertTool.insert(recordList);
		recordList.clear();
	}

	public static String getCurrentTime(){
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return formatter.format(currentTime);
	}
}
