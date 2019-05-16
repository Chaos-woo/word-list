package main.src.utils.test.system;

import main.src.utils.main.system.SystemTools;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class RandomNumberListTest {
	@Test
	void randomNumberListTest(){
		ArrayList<Integer> list = SystemTools.randomNumberList(10,100);
		list.forEach(n-> System.out.print(n+","));
	}
}
