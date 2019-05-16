package main.src.services.main;

import main.src.command.interface_command.GetCommand;
import main.src.common.ConstantString;
import main.src.utils.main.system.SystemTools;

import java.util.Scanner;

public class InitUser implements GetCommand {
	public InitUser(){
		SystemTools.printWelcomePanel(ConstantString.TITLE,new String[]{ConstantString.REGISTER,ConstantString.LOGIN});
		System.out.println();
	}

	@Override
	public void getCommand(){
		Scanner in = new Scanner(System.in);
		String[] cmds = in.nextLine().split(" ");
		String name = cmds[1].substring(1);
		String pw = cmds[2].substring(1);
		UserRegisterAndLoginBO ural = new UserRegisterAndLoginBO(name,pw);
		if("\\r".equals(cmds[0])){
			ural.registerUser();
		}else if("\\lg".equals(cmds[0])){
			ural.loginUser();
		}
	}
}
