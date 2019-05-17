package main.src.db.main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnectSingleton {
	private static volatile Connection connectionInstance;
	private static final String driver = "com.mysql.cj.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/word"
			+ "?useUnicode=true&characterEncoding=utf8&useSSL=true&serverTimezone=GMT%2B8";
	private static String user;
	private static String pw ;

	static {
		try(BufferedReader br = new BufferedReader
				(new InputStreamReader(new FileInputStream("H:/JavaPro/wordlist/src/main/resource/db/dbinfo.txt")))) {
			String s = null;
			if((s=br.readLine())!=null){
				DatabaseConnectSingleton.setUser(s);
			}
			if((s=br.readLine())!=null){
				DatabaseConnectSingleton.setPw(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private DatabaseConnectSingleton(){}
	private static Connection Singleton(){
		try {
			Class.forName(driver);
			connectionInstance = DriverManager.getConnection(url,user,pw);
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


	public static void setUser(String user) {
		DatabaseConnectSingleton.user = user;
	}

	public static void setPw(String pw) {
		DatabaseConnectSingleton.pw = pw;
	}
}
