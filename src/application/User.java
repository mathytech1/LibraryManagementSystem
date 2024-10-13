package application;

// Common functionality for Admin and RegularUser.
abstract public class User {
	private String firstName;
	private String lastName;
	private String userID;
	private String username;
	private String password;
	private String role;

	public User(String firstName, String lastName, String userID, String username, String password, String role) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.userID = userID;
		this.username = username;
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

	abstract public void viewDashboard();

}
