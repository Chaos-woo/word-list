package main.src.utils.test.system;

import main.src.common.ConstantString;
import main.src.utils.main.system.SystemTools;
import org.junit.jupiter.api.Test;

public class SystemToolsTest {
	@Test
	void printMainPanelTest(){
		SystemTools.printWelcomePanel(ConstantString.TITLE,new String[]{ConstantString.HELP,ConstantString.NOTICE});
	}

	@Test
	void printWordCardTest(){
		SystemTools.printWordCard("word","我的单词","[]");
	}
}
