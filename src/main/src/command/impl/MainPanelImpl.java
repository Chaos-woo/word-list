package main.src.command.impl;

import main.src.command.interface_command.BasicCommand;
import main.src.command.interface_command.GetCommand;
import main.src.command.interface_command.MainPanelCommand;
import main.src.common.ConstantString;
import main.src.common.ExecutePoolServ;
import main.src.common.Semaphore;
import main.src.services.main.ExplainWordsModel;
import main.src.services.main.InsertWordsBO;
import main.src.services.main.QueryUserInfoBO;
import main.src.utils.main.internet.QurryChineseByBing;
import main.src.utils.main.system.SystemTools;

import java.util.Scanner;

public class MainPanelImpl implements MainPanelCommand,BasicCommand,GetCommand {
	public MainPanelImpl(){
		clearConsole();
		help();
	}

	@Override
	public void insertWord() {
		clearConsole();
		new InsertWordsBO().insertWord();
	}

	@Override
	public void repeatWord(int n) {
		clearConsole();
	}

	@Override
	public void getUserInfo() {
		clearConsole();
		new QueryUserInfoBO().queryInfo();
	}

	@Override
	public void setAudioStorePath(String path) {
		Semaphore.setAudioPath(path);
	}

	@Override
	public void executeEnToZhModel(int n) {
		clearConsole();
		ExecutePoolServ.getExecutorService().execute(new ExplainWordsModel(n));
		ExecutePoolServ.getExecutorService().execute(new QurryChineseByBing());
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
		while(true){
			Scanner in = new Scanner(System.in);
			String[] cmds = in.nextLine().split(" ");
			String cmd = cmds[0];
			if(cmds.length==1){
				if("\\q".equals(cmd)){
					quitSystem();
				}else if("\\c".equals(cmd)){
					clearConsole();
				}else if("\\i".equals(cmd)){
					insertWord();
				}else if("\\help".equals(cmd)){
					help();
				}else if("\\info".equals(cmd)){
					getUserInfo();
				}else {
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
