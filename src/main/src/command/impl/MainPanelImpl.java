package main.src.command.impl;

import main.src.command.interface_command.BasicCommand;
import main.src.command.interface_command.GetCommand;
import main.src.command.interface_command.MainPanelCommand;
import main.src.common.ConstantString;
import main.src.common.Container;
import main.src.common.ExecutePoolServ;
import main.src.common.Semaphore;
import main.src.services.main.*;
import main.src.utils.main.internet.QueryChineseByBing;
import main.src.utils.main.system.SystemTools;

import java.util.Scanner;

public class MainPanelImpl implements MainPanelCommand,BasicCommand,GetCommand {
	public MainPanelImpl(){
		clearConsole();
		help();
	}

	@Override
	public void insertWord() {
		new InsertWordsBO().getCommand();
	}

	@Override
	public void repeatWord(int n) {
		Container.clearWordList();
		Container.clearCatch();
		ExecutePoolServ.getExecutorService().execute(new QueryChineseByBing(n));
		new RepeatWordBO().getCommand();
	}

	@Override
	public void getUserInfo() {
		System.out.println();
		new QueryUserInfoBO().queryInfo();
	}

	@Override
	public void setAudioStorePath(String path) {
		if(Semaphore.setAudioPath(path)){
			System.out.println("Audio path has been set successful.");
		}
	}

	@Override
	public void executeEnToZhModel(int n) {
		Container.clearWordList();
		Container.clearCatch();
		ExecutePoolServ.getExecutorService().execute(new QueryChineseByBing(n));
		new ExplainWordsModel().getCommand();
	}

	@Override
	public void bulkInsertWord() {
		new BulkInsertBO().bulkInsert();
	}

	@Override
	public void help() {
		SystemTools.printWelcomePanel(ConstantString.TITLE,new String[]{
				ConstantString.E2C,ConstantString.MEMORY,
				ConstantString.INSERT,ConstantString.INFO,
				ConstantString.SET_PATH,ConstantString.CLEAR,
				ConstantString.QUIT,ConstantString.HELP
		});
		System.out.println();
	}

	@Override
	public void getCommand(){
		Scanner in = new Scanner(System.in);
		while(true){
			String[] cmds = in.nextLine().split(" ");
			String cmd = cmds[0];
			if(cmds.length==1){
				if("\\q".equals(cmd)){
					break;
				}else if("\\c".equals(cmd)){
					clearConsole();
				}else if("\\i".equals(cmd)){
					insertWord();
				}else if("\\help".equals(cmd)){
					help();
				}else if("\\info".equals(cmd)){
					getUserInfo();
				}else if("\\im".equals(cmd)){
					bulkInsertWord();
				} else {
					//don't do anything
				}
			}else {
				int param;
				String path;
				if("\\e2c".equals(cmd)){
					param = Integer.parseInt(cmds[1].substring(1));
					executeEnToZhModel(param);
				}else if ("\\m".equals(cmd)){
					param = Integer.parseInt(cmds[1].substring(1));
					repeatWord(param);
				}else if("\\set".equals(cmd)){
					path = cmds[1].substring(1);
					setAudioStorePath(path);
				}
			}
		}
	}
}
