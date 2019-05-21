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
import main.src.utils.main.internet.QueryNeverSelectedWords;
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
		setSemaphore(true,true,true);
		QueryChineseByBing qcb = new QueryChineseByBing(n);
		ExecutePoolServ.getExecutorService().execute(qcb);
		RepeatWordBO repeatWordBO = new RepeatWordBO();
		repeatWordBO.getCommand();
	}

	@Override
	public void getUserInfo() {
		System.out.println();
		QueryUserInfoBO queryUserInfoBO = new QueryUserInfoBO();
		queryUserInfoBO.queryInfo();
	}

	@Override
	public void setAudioStorePath(String path) {
		if(Semaphore.setAudioPath(path)){
			System.out.println("Audio path has been set successful.");
		}
	}

	@Override
	public void executeEnToZhModel(int n) {
		setSemaphore(false,true,true);
		QueryChineseByBing qcb = new QueryChineseByBing(n);
		ExecutePoolServ.getExecutorService().execute(qcb);
		ExplainWordsModel explainWordsModel = new ExplainWordsModel();
		explainWordsModel.getCommand();
	}

	@Override
	public void bulkInsertWord() {
		BulkInsertBO bulkInsertBO = new BulkInsertBO();
		bulkInsertBO.bulkInsert();
	}

	@Override
	public void neverReadWordsModel() {
		setSemaphore(false,true,true);
		QueryNeverSelectedWords qns = new QueryNeverSelectedWords();
		ExecutePoolServ.getExecutorService().execute(qns);
		ExplainWordsModel explainWordsModel = new ExplainWordsModel();
		explainWordsModel.getCommand();
	}

	@Override
	public void help() {
		SystemTools.printWelcomePanel(ConstantString.TITLE,new String[]{
				ConstantString.E2C,ConstantString.NE2C,
				ConstantString.MEMORY,ConstantString.INSERT,
				ConstantString.BULK_INSERT,ConstantString.INFO,
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
				} else if("\\ne2c".equals(cmd)){
					neverReadWordsModel();
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

	private void setSemaphore(boolean readWordModel, boolean running, boolean searchByInternetFlag){
		Container.clearWordList();
		Container.clearCatch();
		Semaphore.setReadWordModel(readWordModel);
		Semaphore.setRunning(running);
		Semaphore.setSearchByInternetFlag(searchByInternetFlag);
	}
}
