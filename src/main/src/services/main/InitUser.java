package main.src.services.main;

import main.src.command.impl.MainPanelImpl;
import main.src.command.interface_command.BasicCommand;
import main.src.command.interface_command.GetCommand;
import main.src.common.ConstantString;
import main.src.common.Semaphore;
import main.src.utils.main.system.SystemTools;

import java.util.Scanner;

public class InitUser implements GetCommand,BasicCommand {
	public InitUser() {
		help();
		System.out.println();
	}

	@Override
	public void getCommand() {
		Scanner in = new Scanner(System.in);
		UserRegisterAndLoginBO ural;
		while (true) {
			String[] cmds = in.nextLine().split(" ");
			if (cmds.length < 3)
				continue;
			String name = cmds[1].substring(1);
			String pw = cmds[2].substring(1);
			ural = new UserRegisterAndLoginBO(name, pw);
			if ("\\r".equals(cmds[0])) {
				if (ural.registerUser()) {
					clearConsole();
					help();
					System.out.println();
				} else {
					clearConsole();
					help();
					System.out.println("user register is failed.");
				}
			} else if ("\\lg".equals(cmds[0])) {
				if(ural.loginUser()){
					Semaphore.setCurrentUser(name);
					break;
				}else {
					System.out.println("user name or password is error.");
				}
			}
		}
		new MainPanelImpl().getCommand();
	}


	@Override
	public void help() {
		SystemTools.printWelcomePanel(ConstantString.TITLE,new String[]{ConstantString.REGISTER,ConstantString.LOGIN});
	}
}
