package by.training.coffeeproject.entity;

import java.util.Objects;

public class User extends Entity {

	private static final long serialVersionUID = -5202439826642095405L;

	private String login;
	private String password;
	private Role role;
	private UserInfo userInfo;
	
	public User(Integer iD, String login, String password, Role role, UserInfo userInfo) {
		super(iD);
		this.login = login;
		this.password = password;
		this.role = role;
		this.userInfo = userInfo;
	}

	public User() {
		super();
	}

	public User(Integer iD) {
		super(iD);
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(login, password, role, userInfo);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(login, other.login) && Objects.equals(password, other.password) && role == other.role
				&& Objects.equals(userInfo, other.userInfo);
	}

	@Override
	public String toString() {
		return "User [login=" + login + ", password=" + password + ", role=" + role + ", userInfo=" + userInfo + "]";
	}

	

}