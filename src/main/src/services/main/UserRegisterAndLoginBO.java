package main.src.services.main;

import main.src.bean.UserBean;
import main.src.command.impl.MainPanelImpl;
import main.src.command.interface_command.UserCommand;
import main.src.common.ConstantString;
import main.src.common.Semaphore;
import main.src.utils.main.db.DatabaseInsertTool;
import main.src.utils.main.db.DatabaseQueryTool;
import main.src.utils.main.system.SystemTools;

import java.sql.ResultSet;
import java.util.ArrayList;

public class UserRegisterAndLoginBO implements UserCommand {
	private String name;
	private String pw;

	public UserRegisterAndLoginBO(String name, String pw) {
		this.name = name;
		this.pw = pw;
	}

	@Override
	public void registerUser() {
		ArrayList<Object> userList = new ArrayList<>();
		userList.add(new UserBean(name,pw));
		DatabaseInsertTool.insert(userList);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		SystemTools.clear();
		new InitUser().getCommand();
	}

	@Override
	public void loginUser(){
		ArrayList<Object> userList = new ArrayList<>();
		userList.add(new UserBean(name));
		ResultSet rs = DatabaseQueryTool.query(userList);
		try{
			assert rs != null;
			while (rs.next()){
				if(pw.equals(rs.getString("password"))){
					//to main panel
					SystemTools.printWelcomePanel(ConstantString.TITLE,new String[]{ConstantString.HELP});
					Semaphore.setCurrentUser(name);
					break;
				}else {
					new InitUser().getCommand();
					System.out.println("user name or password is error.");
				}
			}
			new MainPanelImpl().getCommand();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
