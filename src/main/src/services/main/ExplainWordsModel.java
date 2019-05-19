package main.src.services.main;

import main.src.bean.WordBean;
import main.src.command.interface_command.BasicCommand;
import main.src.command.interface_command.GetCommand;
import main.src.command.interface_command.OperationalPanelCommand;
import main.src.common.ConstantString;
import main.src.common.Container;
import main.src.common.ExecutePoolServ;
import main.src.common.Semaphore;
import main.src.utils.main.db.DatabaseUpdateTool;
import main.src.utils.main.system.PlayAudio;
import main.src.utils.main.system.SystemTools;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExplainWordsModel implements BasicCommand ,GetCommand,OperationalPanelCommand {
	private WordBean w;
	private boolean flag = true;
	public ExplainWordsModel(){
		help();
		System.out.println();
	}

	@Override
	public void help() {
		SystemTools.printWelcomePanel(ConstantString.TITLE,new String[]{
				ConstantString.MAIN,ConstantString.RECALL,
				ConstantString.HELP,ConstantString.CLEAR,
				ConstantString.QUIT,ConstantString.NOTICE_BEFORE,
				ConstantString.NOTICE,ConstantString.NOTICE2
		});
	}

	@Override
	public void getCommand() {
		Scanner in = new Scanner(System.in);
		System.out.println("Now you can prepare for it 5 seconds...");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		getWordFromCatchList();
		System.out.println();
		while (flag) {
			String[] cmds = in.nextLine().split(" ");
			Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
			Matcher m = p.matcher(cmds[0]);
			if (m.find()) {
				if(w!=null){
					System.out.println(w.getChinese());
				}
				getWordFromCatchList();
				w.setCorrectCount(w.getCorrectCount() + 1);
			}else {
				if (cmds[0].trim().length()==0) {
					System.out.println(w.getChinese());
					getWordFromCatchList();
				} else if (cmds.length == 1) {
					if ("\\main".equals(cmds[0])) {
						backToMain();
					} else if ("\\help".equals(cmds[0])) {
						help();
					} else if ("\\c".equals(cmds[0])) {
						clearConsole();
					} else if ("\\q".equals(cmds[0])) {
						quitSystem();
					}
				} else if (cmds.length == 2) {
					if ("\\rc".equals(cmds[0])) {
						rebroadcast(Integer.parseInt(cmds[1].substring(1)));
					}
				}
			}

			System.out.println();
		}
	}

	/**
	 * word catch is a array list, when its size>0 process get a word
	 * ReadWordModel is a semaphore, witch was used judging is or not show word's chinese
	 */
	protected void getWordFromCatchList() {
		if (Container.wordCatch.size()>0){
			Semaphore.setSearchByInternetFlag(false);
			w = (WordBean) Container.wordCatch.remove(0);
			if(Semaphore.isReadWordModel()){
				SystemTools.printWordCard(w.getEnglish(),w.getChinese(),w.getPronunciation());
			}else {
				SystemTools.printWordCard(w.getEnglish()," ",w.getPronunciation());
			}
			Container.newWordList.add(w);
			ExecutePoolServ.getExecutorService().execute(new PlayAudio(w.getEnglish()));
			Semaphore.setSearchByInternetFlag(true);
		}
		if(Container.wordCatch.size()<=0){
			System.out.println();
			System.out.println("This is ending. Please use \\'\\main\' to save and back main panel.");
		}
	}

	@Override
	public void backToMain() {
		flag = false;
		if(Container.newWordList.size()<=0){
			//do not do anything
		}else if(!DatabaseUpdateTool.update(Container.newWordList)){
			System.out.println("Saving word is failed. Please save again.");
		}
	}

	/**
	 * when user want to listen to word pronunciation,
	 * it can rebroadcast "n" counts
	 * but its default counts is 3, not be much than.
	 *
	 * @param n rebroadcast counts
	 */
	@Override
	public void rebroadcast(int n) {
		int defaultValue = 3;
		n = Math.min(n,defaultValue);
		for (int i = 0; i < n; i++) {
			try {
				ExecutePoolServ.getExecutorService().execute(new PlayAudio(w.getEnglish()));
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

