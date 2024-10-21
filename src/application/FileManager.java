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
	private final String BOOK_FILE_NAME = "src\\application\\files\\books.dat";
	private final String USER_FILE_NAME = "src\\application\\files\\users.dat";

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
		// TODO Auto-generated method stub
		try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(BOOK_FILE_NAME))) {
			writer.writeObject(books2);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
		// TODO Auto-generated method stub
		try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(USER_FILE_NAME))) {
			writer.writeObject(user2);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
