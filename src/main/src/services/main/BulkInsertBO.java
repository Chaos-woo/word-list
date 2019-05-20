package main.src.services.main;

import main.src.bean.WordBean;
import main.src.command.interface_command.BasicCommand;
import main.src.command.interface_command.OperationalPanelCommand;
import main.src.common.ConstantString;
import main.src.utils.main.db.DatabaseInsertTool;
import main.src.utils.main.system.SystemTools;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class BulkInsertBO implements BasicCommand ,OperationalPanelCommand {
	private ArrayList<Object> list;
	private boolean flag = true;

	public BulkInsertBO(){
		list = new ArrayList<>();
		help();
	}

	@Override
	public void help() {
		SystemTools.printWelcomePanel("Insert words panel",new String[]{
				ConstantString.MAIN,ConstantString.CLEAR,
				ConstantString.QUIT,ConstantString.NOTICE_BEFORE,
				ConstantString.NOTICE
		});
		System.out.println();
	}

	public void bulkInsert(){
		Scanner in = new Scanner(System.in);
		System.out.println("Please input word file's path");
		while (flag){
			String path = in.nextLine();
			File file = new File(path);
			if(!file.exists()){
				System.out.println("file isn't exist...");
				continue;
			}
			try(BufferedReader br = new BufferedReader(new FileReader(file))){
				String s = "";
				while ((s=br.readLine())!=null){
					WordBean w = new WordBean(s);
					list.add(w);
				}
			}catch (Exception e){
				e.printStackTrace();
			}
			if(DatabaseInsertTool.insert(list)){
				backToMain();
			}else {
				System.out.println("Insert failed. Please try again.");
				backToMain();
			}
		}

	}

	@Override
	public void backToMain() {
		flag = false;
	}

	@Override
	public void rebroadcast(int n) {

	}
}
