package main.src.bean;

import main.src.annotation.Column;
import main.src.annotation.Table;

@Table("user")
public class UserBean {
	@Column("name")
	private String name;
	@Column("password")
	private String password;

	public UserBean(String name, String password) {
		this.name = name;
		this.password = password;
	}

	public UserBean(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
