package main.src.db.main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseOperateDAO {
	private static Connection c = null;

	public static ResultSet query(String sql) {
		ResultSet rs = null;
		c = DatabaseConnectSingleton.getConnectionInstance();
		try {
			Statement stmt = null;
			try {
				stmt = c.createStatement();
				rs = stmt.executeQuery(sql);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				stmt.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public static int updateAndInsert(String sql) {
		c = DatabaseConnectSingleton.getConnectionInstance();
		int rows = 0;
		try {
			Statement stmt = null;
			try {
				stmt = c.createStatement();
				rows = stmt.executeUpdate(sql);
				c.commit();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				stmt.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rows;
	}

	public static int getCountOfWords(){
		String sql = "SELECT english FROM wordlist";
		ResultSet rs = null;
		int count = 0;
		c = DatabaseConnectSingleton.getConnectionInstance();
		try {
			Statement stmt = null;
			try {
				stmt = c.createStatement();
				rs = stmt.executeQuery(sql);
				while (rs.next()){
					count++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				stmt.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
}
