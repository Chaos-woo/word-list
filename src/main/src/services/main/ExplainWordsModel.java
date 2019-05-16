package main.src.services.main;

import main.src.bean.WordBean;
import main.src.command.impl.MainPanelImpl;
import main.src.command.interface_command.BasicCommand;
import main.src.command.interface_command.GetCommand;
import main.src.command.interface_command.OperatinalPanelCommand;
import main.src.common.ConstantString;
import main.src.common.Container;
import main.src.common.ExecutePoolServ;
import main.src.common.Semaphore;
import main.src.db.main.DatabaseOperateDAO;
import main.src.utils.main.db.DatabaseQueryTool;
import main.src.utils.main.db.DatabaseUpdateTool;
import main.src.utils.main.system.PlayAudio;
import main.src.utils.main.system.SystemTools;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

public class ExplainWordsModel implements BasicCommand ,GetCommand,Runnable,OperatinalPanelCommand {
	private WordBean w;
	public ExplainWordsModel(int n){
		ArrayList<Integer> randomList = SystemTools.randomNumberList(n,DatabaseOperateDAO.getCountOfWords());
		ArrayList<Object> list = new ArrayList<>(randomList);
		ResultSet rs = DatabaseQueryTool.query(list);
		try{
			assert rs != null;
			while (rs.next()){
				Container.oldWordList.add(new WordBean(
						rs.getInt("id"),rs.getString("english"),
						rs.getString("chinese"),rs.getString("pronunciation"),
						rs.getString("sound"),rs.getInt("selected_count"),
								rs.getInt("correct_count")));
			}
			Semaphore.setSearchByInternetFlag(true);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void help() {
		SystemTools.printWelcomePanel(ConstantString.TITLE,new String[]{
				ConstantString.MAIN,ConstantString.RECALL,
				ConstantString.HELP,ConstantString.CLEAR,
				ConstantString.QUIT,ConstantString.NOTICE_BEFORE,
				ConstantString.NOTICE
		});
	}

	@Override
	public void run() {
		while (Semaphore.isRunning()){
			while (Container.wordCatch.size()>0){
				Semaphore.setSearchByInternetFlag(false);
				w = (WordBean) Container.wordCatch.remove(0);
				SystemTools.printWordCard(w.getEnglish(),w.getChinese(),w.getPronunciation());
				System.out.println();
				getCommand();
			}
		}
	}

	@Override
	public void getCommand() {
		Scanner in = new Scanner(System.in);
		String[] cmds = in.nextLine().split(" ");
		if(cmds[0].trim().length()==0){
			Container.newWordList.add(w);
			return;
		}else if(cmds.length==1){
			if("\\main".equals(cmds[0])){
				backToMain();
			}else if("\\help".equals(cmds[0])){
				help();
			}else if("\\c".equals(cmds[0])){
				clearConsole();
			}else if("\\q".equals(cmds[0])){
				quitSystem();
			}
		}else if(cmds.length==2) {
			if("\\rc".equals(cmds[0])){
				for(int i=0;i<Integer.parseInt(cmds[1]);i++){
					try{
						ExecutePoolServ.getExecutorService().execute(new PlayAudio(w.getEnglish()));
						Thread.sleep(1000);
					}catch (Exception e){
						e.printStackTrace();
					}
				}
			}
		}else {
			w.setCorrectCount(w.getCorrectCount()+1);
			Container.newWordList.add(w);
		}
	}

	@Override
	public void backToMain() {
		Semaphore.setRunning(false);
		DatabaseUpdateTool.update(Container.newWordList);
		Container.clearWordList();
		Container.clearCatch();
		new MainPanelImpl().getCommand();
	}

	@Override
	public void rebroadcast() {

	}
}
