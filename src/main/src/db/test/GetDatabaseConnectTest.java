package main.src.db.test;

import main.src.db.main.DatabaseConnectSingleton;
import org.junit.jupiter.api.Test;

public class GetDatabaseConnectTest {
	@Test
	void test(){
		DatabaseConnectSingleton.getConnectionInstance();
	}
}
