package main.src.db.main;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnectSingleton {
	private static volatile Connection connectionInstance;
	private DatabaseConnectSingleton(){}
	private static Connection Singleton(){
		try {
			Class.forName("org.sqlite.JDBC");
			connectionInstance = DriverManager.getConnection("jdbc:sqlite:H:/JavaPro/wordlist/src/main/resource/db/word.db");
			connectionInstance.setAutoCommit(false);
		}catch (Exception e){
			e.printStackTrace();
		}
		return connectionInstance;
	}

	public static Connection getConnectionInstance() {
		if(connectionInstance == null){
			synchronized (DatabaseConnectSingleton.class){
				if(connectionInstance == null){
					connectionInstance = DatabaseConnectSingleton.Singleton();
				}
			}
		}
		return connectionInstance;
	}
}
