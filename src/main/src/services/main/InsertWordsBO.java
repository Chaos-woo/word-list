package main.src.services.main;

import main.src.bean.WordBean;
import main.src.command.interface_command.BasicCommand;
import main.src.command.interface_command.GetCommand;
import main.src.command.interface_command.OperationalPanelCommand;
import main.src.common.ConstantString;
import main.src.utils.main.db.DatabaseInsertTool;
import main.src.utils.main.system.SystemTools;

import java.util.ArrayList;
import java.util.Scanner;

public class InsertWordsBO implements BasicCommand,OperationalPanelCommand,GetCommand {
	private ArrayList<Object> wordList;
	private static boolean flag = true;

	public InsertWordsBO(){
		wordList = new ArrayList<>();
	}

	@Override
	public void help() {
		SystemTools.printWelcomePanel("Insert words panel",new String[]{
				ConstantString.MAIN,ConstantString.CLEAR,
				ConstantString.QUIT,ConstantString.NOTICE_BEFORE,
				ConstantString.NOTICE
		});
	}

	@Override
	public void backToMain() {
		flag = false;
	}

	@Override
	public void rebroadcast(int n) {

	}

	@Override
	public void getCommand() {
		help();
		System.out.println();
		Scanner in = new Scanner(System.in);
		while (flag){
			String s = in.nextLine();
			if(s.contains("\\")){
				if("\\main".equals(s)){
					if(wordList.size()<=0){
						backToMain();
					}else {
						if(DatabaseInsertTool.insert(wordList)){
							System.out.println("Please wait ...");
							backToMain();
						}else {
							System.out.println("Saving is failed. Please save again");
						}
					}
				}else if("\\c".equals(s)){
					clearConsole();
				}else if("\\q".equals(s)){
					quitSystem();
				}
			} else {
				wordList.add(new WordBean(s));
			}
		}

	}
}
