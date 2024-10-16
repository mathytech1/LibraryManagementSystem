package application;

import java.io.Serializable;

// Common functionality for Admin and RegularUser.
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private String firstName;
	private String lastName;
	private String userID;
	private String username;
	private String password;
	private String role;
//	private static int idNumber = 1000;

	public User(String username, String firstName, String lastName, String password, String role) {
		// idNumber++;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		// this.userID = "S" + idNumber;
		this.password = password;
		this.role = role;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserID() {
		return userID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void login(String username, String password) {

	}

	public void logout() {

	}

	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", lastName=" + lastName + ", username=" + username + ", role=" + role
				+ "]";
	}

	// abstract public void viewDashboard();

}
