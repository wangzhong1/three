package model.vo;



public class User {
	private String userName;
	private String password;
	private String chrName;
	private String role;
	
	public String getName() {
		return chrName;
	}
	
	public void setName(String chrName) {
		this.chrName = chrName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}


	
}

