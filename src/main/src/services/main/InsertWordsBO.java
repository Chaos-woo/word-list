package main.src.services.main;

import main.src.bean.WordBean;
import main.src.command.impl.MainPanelImpl;
import main.src.command.interface_command.BasicCommand;
import main.src.command.interface_command.GetCommand;
import main.src.command.interface_command.OperatinalPanelCommand;
import main.src.common.ConstantString;
import main.src.utils.main.db.DatabaseInsertTool;
import main.src.utils.main.system.SystemTools;

import java.util.ArrayList;
import java.util.Scanner;

public class InsertWordsBO implements BasicCommand,OperatinalPanelCommand ,GetCommand {
	private ArrayList<Object> wordList;

	public InsertWordsBO(){
		wordList = new ArrayList<>();
	}

	public void insertWord(){
		help();
		getCommand();
	}

	@Override
	public void help() {
		SystemTools.printWelcomePanel(ConstantString.TITLE,new String[]{
				ConstantString.MAIN,ConstantString.CLEAR,
				ConstantString.QUIT,ConstantString.NOTICE_BEFORE,
				ConstantString.NOTICE
		});
	}

	@Override
	public void backToMain() {
		new MainPanelImpl().getCommand();
	}

	@Override
	public void rebroadcast() {

	}

	@Override
	public void getCommand() {
		Scanner in = new Scanner(System.in);
		String s = in.nextLine();
		if("\\main".equals(s)){
			System.out.println("Please wait ...");
			DatabaseInsertTool.insert(wordList);
			backToMain();
		}else if("\\c".equals(s)){
			clearConsole();
		}else if("\\q".equals(s)){
			quitSystem();
		}else {
			wordList.add(new WordBean(s));
		}
	}
}
