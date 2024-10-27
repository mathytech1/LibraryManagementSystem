//package application.junit_testing;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.never;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.TreeMap;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import application.FileManager;
//import application.LoginController;
//import application.User;
//import javafx.event.ActionEvent;
//import javafx.scene.control.Label;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextField;
//
//public class LoginControllerTest {
//
//	@Mock
//	private FileManager fileManager;
//
//	@Mock
//	private ActionEvent event;
//
//	@InjectMocks
//	private LoginController loginController;
//
//	private User testUser;
//
//	@BeforeEach
//	public void setUp() {
//		MockitoAnnotations.openMocks(this);
//
//		// Initialize UI elements that are used in userLogin()
//		loginController.errorLabel = new Label();
//		loginController.usernameTextField = new TextField();
//		loginController.passwordTextField = new PasswordField();
//
//		// Setup a test user
//		testUser = new User("testUser", "John", "Doe", "password123", "Regular");
//
//		// Initialize mockUsers to simulate data read from file
//		TreeMap<String, User> mockUsers = new TreeMap<>();
//		mockUsers.put("testUser", testUser);
//
//		// Mock FileManager behavior for reading users
//		when(fileManager.readUsers()).thenReturn(mockUsers);
//	}
//
//	@Test
//	public void testUserLogin_Success() {
//		// Given
//		loginController.usernameTextField.setText("testUser");
//		loginController.passwordTextField.setText("password123");
//
//		// When
//		loginController.userLogin(event);
//
//		// Then
//		assertEquals("", loginController.errorLabel.getText()); // No error message
//		verify(fileManager, times(1)).writeLogin("testUser"); // Login recorded
//		verify(fileManager, times(1)).readBooks(); // Books read on login
//	}
//
//	@Test
//	public void testUserLogin_InvalidCredentials() {
//		// Given
//		loginController.usernameTextField.setText("testUser");
//		loginController.passwordTextField.setText("wrongPassword");
//
//		// When
//		loginController.userLogin(event);
//
//		// Then
//		assertEquals("Incorrect Credentials!", loginController.errorLabel.getText()); // Error message displayed
//		verify(fileManager, never()).writeLogin(anyString()); // Login not recorded
//	}
//
//	@Test
//	public void testUserLogin_EmptyUsername() {
//		// Given
//		loginController.usernameTextField.setText("");
//		loginController.passwordTextField.setText("password123");
//
//		// When
//		loginController.userLogin(event);
//
//		// Then
//		assertEquals("Enter username pls!", loginController.errorLabel.getText()); // Error for empty username
//		verify(fileManager, never()).writeLogin(anyString());
//	}
//
//	@Test
//	public void testUserLogin_EmptyPassword() {
//		// Given
//		loginController.usernameTextField.setText("testUser");
//		loginController.passwordTextField.setText("");
//
//		// When
//		loginController.userLogin(event);
//
//		// Then
//		assertEquals("Enter password pls", loginController.errorLabel.getText()); // Error for empty password
//		verify(fileManager, never()).writeLogin(anyString());
//	}
//
//	@Test
//	public void testUserLogin_UserNotFound() {
//		// Given
//		loginController.usernameTextField.setText("unknownUser");
//		loginController.passwordTextField.setText("password123");
//
//		// When
//		loginController.userLogin(event);
//
//		// Then
//		assertEquals("Incorrect Credentials!", loginController.errorLabel.getText()); // Error for user not found
//		verify(fileManager, never()).writeLogin(anyString());
//	}
//}
package application;


