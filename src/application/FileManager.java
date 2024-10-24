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
	private TreeMap<String, Book> books;
	private TreeMap<String, User> users;
	private TreeMap<String, String> borrowedBooks;
	private String loginData;
	private final String BOOK_FILE_NAME = "src\\application\\files\\books.dat";
	private final String USER_FILE_NAME = "src\\application\\files\\users.dat";
	private final String USERLOGIN_FILE_NAME = "src\\application\\files\\login.dat";
	private final String BORROWED_BOOKS_FILE_NAME = "src\\application\\files\\borrowedBooks.dat";

	@SuppressWarnings("unchecked")
	public TreeMap<String, Book> readBooks() {
		books = new TreeMap<>();
		try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(BOOK_FILE_NAME))) {
			books = (TreeMap<String, Book>) reader.readObject(); // Reading the entire map
		} catch (FileNotFoundException e) {
			// If the file doesn't exist, return an empty map
			System.out.println("File not found, initializing empty book list.");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return books;
	}

	public void writeBooks(TreeMap<String, Book> books2) {
		try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(BOOK_FILE_NAME))) {
			writer.writeObject(books2);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public TreeMap<String, User> readUsers() {
		users = new TreeMap<>();
		try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(USER_FILE_NAME))) {
			if (new File(USER_FILE_NAME).length() == 0) {
				System.out.println("File is empty, initializing empty user list.");
			} else {
				users = (TreeMap<String, User>) reader.readObject(); // Reading the entire map
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found, initializing empty user list.");
		} catch (EOFException e) {
			System.out.println("Reached end of file, no users found.");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return users;
	}

	public void writeUsers(TreeMap<String, User> user2) {
		try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(USER_FILE_NAME))) {
			writer.writeObject(user2);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public TreeMap<String, String> readBorrowedBooks() {
		borrowedBooks = new TreeMap<>();
		try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(BORROWED_BOOKS_FILE_NAME))) {
			if (new File(BORROWED_BOOKS_FILE_NAME).length() == 0) {
				System.out.println("File is empty, initializing empty borrowed books map.");
			} else {
				borrowedBooks = (TreeMap<String, String>) reader.readObject(); // Reading the entire map
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found, initializing empty borrowed books map.");
		} catch (EOFException e) {
			System.out.println("Reached end of file, no users found.");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return borrowedBooks;
	}

	public void writeBorrowedBooks(TreeMap<String, String> borrowedBooks2) {
		try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(BORROWED_BOOKS_FILE_NAME))) {
			writer.writeObject(borrowedBooks2);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String readLogin() {
		loginData = new String();
		try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(USERLOGIN_FILE_NAME))) {
			if (new File(USERLOGIN_FILE_NAME).length() == 0) {
				System.out.println("File is empty, initializing empty login string.");
			} else {
				loginData = (String) reader.readObject(); // Reading the entire map
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

	public void writeLogin(String currentLogin) {
		try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(USERLOGIN_FILE_NAME))) {
			writer.writeObject(currentLogin);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
