package main.src.command.interface_command;

public interface MainPanelCommand {
	void insertWord();
	void repeatWord(int n);
	void getUserInfo();
	void setAudioStorePath(String path);
	void executeEnToZhModel(int n);
	void bulkInsertWord();
}
