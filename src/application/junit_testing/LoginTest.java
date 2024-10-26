package application.junit_testing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.BookController;
import application.BookService;
import application.FileManager;
import application.LoginController;

class LoginTest {
	FileManager fileManager;
	LoginController loginController;
	BookService bookService;
	BookController bookController;

	@BeforeEach
	void setUp() {
		fileManager = new FileManager();
		loginController = new LoginController();
		bookService = new BookService();
		bookController = new BookController();
	}

	@Test
	void loginTest() {

	}

}
