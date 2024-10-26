package application;

import java.io.Serializable;

// Represents a generic user in the system, with fields and methods common to all user types.
public class User implements Serializable {

	private static final long serialVersionUID = 1L; // Ensures compatibility during serialization
	private String firstName; // User's first name
	private String lastName; // User's last name
	private String username; // User's chosen username
	private String password; // User's password
	private String role; // Role of the user (e.g., Admin, RegularUser)

	// Constructor to initialize a new User with essential information
	public User(String username, String firstName, String lastName, String password, String role) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.role = role;
	}

	// Getter and setter for firstName
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	// Getter and setter for lastName
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	// Getter and setter for username
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	// Getter and setter for password
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// Setter for role
	public void setRole(String role) {
		this.role = role;
	}

	// Getter for role
	public String getRole() {
		return role;
	}

	// Override toString to provide a readable representation of a User instance
	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", lastName=" + lastName + ", username=" + username + ", role=" + role
				+ "]";
	}
}
