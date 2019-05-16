package main.src.command.interface_command;

import main.src.utils.main.system.SystemTools;

public interface BasicCommand {
	void help();
	default void quitSystem(){
		System.exit(0);
	}
	default void clearConsole(){
		SystemTools.clear();
	}

}
