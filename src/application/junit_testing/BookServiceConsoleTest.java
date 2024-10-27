package application.junit_testing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.TreeMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.Book;
import application.FileManager;

class BookServiceConsoleTest {
	private FileManager fileManager;
	private BookServiceConsole bookService;
	private TreeMap<String, Book> books;

	@BeforeEach
	void setUp() {
		fileManager = new FileManager();
		bookService = new BookServiceConsole();
	}

	@Test
	void borrowBookValidTest() {
		books = fileManager.readBooks();
		Book book = books.get("B0010");

		assertEquals("You have borrowed " + book.getBookTitle(), bookService.borrowBook(book));
	}

	// Invalid test
	@Test
	void borrowBookInValidTest() {
		books = fileManager.readBooks();
		Book book = books.get("B0001");

		// This book is already borrowed
		assertEquals("You have borrowed " + book.getBookTitle(), bookService.borrowBook(book));
	}

}
