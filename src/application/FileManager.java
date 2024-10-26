package application;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.TreeMap;

public class FileManager {
	private String loginData; // Store login data
	// File paths for data storage
	private final String BOOK_FILE_NAME = "src\\application\\files\\books.dat";
	private final String USER_FILE_NAME = "src\\application\\files\\users.dat";
	private final String USERLOGIN_FILE_NAME = "src\\application\\files\\login.dat";
	private final String BORROWED_BOOKS_FILE_NAME = "src\\application\\files\\borrowedBooks.dat";

	// Generic method to read objects from a file
	@SuppressWarnings("unchecked")
	public <T> TreeMap<String, T> readFromFile(String fileName) {
		TreeMap<String, T> dataMap = new TreeMap<>();
		try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(fileName))) {
			dataMap = (TreeMap<String, T>) reader.readObject(); // Read entire map
		} catch (FileNotFoundException e) {
			System.out.println("File not found, initializing empty list.");
		} catch (EOFException e) {
			System.out.println("Reached end of file, no data found.");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return dataMap;
	}

	// Generic method to write objects to a file
	public <T> void writeToFile(String fileName, TreeMap<String, T> dataMap) {
		try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(fileName))) {
			writer.writeObject(dataMap); // Write entire map
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Specific methods for your current implementation
	public TreeMap<String, Book> readBooks() {
		return readFromFile(BOOK_FILE_NAME);
	}

	public void writeBooks(TreeMap<String, Book> books) {
		writeToFile(BOOK_FILE_NAME, books);
	}

	public TreeMap<String, User> readUsers() {
		return readFromFile(USER_FILE_NAME);
	}

	public void writeUsers(TreeMap<String, User> users) {
		writeToFile(USER_FILE_NAME, users);
	}

	public TreeMap<String, String> readBorrowedBooks() {
		return readFromFile(BORROWED_BOOKS_FILE_NAME);
	}

	public void writeBorrowedBooks(TreeMap<String, String> borrowedBooks) {
		writeToFile(BORROWED_BOOKS_FILE_NAME, borrowedBooks);
	}

	// Reads the login data from the file and returns it as a String
	public String readLogin() {
		loginData = new String();
		try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(USERLOGIN_FILE_NAME))) {
			if (new File(USERLOGIN_FILE_NAME).length() == 0) {
				System.out.println("File is empty, initializing empty login string.");
			} else {
				loginData = (String) reader.readObject(); // Read entire map
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found, initializing empty login string.");
		} catch (EOFException e) {
			System.out.println("Reached end of file, no users found.");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return loginData;
	}

	// Writes the current login data to the file
	public void writeLogin(String currentLogin) {
		try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(USERLOGIN_FILE_NAME))) {
			writer.writeObject(currentLogin); // Write login data
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
