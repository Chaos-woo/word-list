package main.src.services.main;

import main.src.common.Semaphore;

import java.util.Scanner;

public class RepeatWordBO extends ExplainWordsModel {
	private boolean flag = true;
	public RepeatWordBO(){
		super();
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
			if (cmds[0].trim().length()==0) {
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
			System.out.println();
		}
	}

	@Override
	protected void getWordFromCatchList() {
		super.getWordFromCatchList();
	}

	@Override
	public void backToMain() {
		flag = false;
	}
}
