package main.src.command.test;

import main.src.command.impl.MainPanelImpl;
import org.junit.jupiter.api.Test;

public class MainPanelTest {
	@Test
	void test(){
		new MainPanelImpl().getCommand();
	}

}
