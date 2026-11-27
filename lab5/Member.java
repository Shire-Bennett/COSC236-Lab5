package lab5;

import java.util.ArrayList;
import java.util.Iterator;

public class Member {

	private String name;
	private ArrayList<Book> borrowedBooks; // Book class dependency

	public Member(String name) {
		this.name = name;
		this.borrowedBooks = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public ArrayList<Book> getBorrowedBooks() {
		return borrowedBooks;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return "Member: " + name;
	}

	public void borrowBook(Book book) {
		BorrowingServiceAPI borrowingService = new BorrowingService();
		boolean success = borrowingService.borrowBook(this, book);

		if (success) {
			System.out.println("Book borrowed successfully.");
		} else {
			System.out.println("Could not borrow book.");
		}
	}

	public void returnBook(Book book) {
		BorrowingServiceAPI borrowingService = new BorrowingService();
		boolean success = borrowingService.returnBook(this, book);

		if (success) {
			System.out.println("Book returned successfully.");
		} else {
			System.out.println("Could not return book.");
		}
	}

	public void listBorrowedBooks() {
		for (Book book : borrowedBooks)
			System.out.println(book); // book.toString()
	}

	public int borrowedBooksCount() {
		return borrowedBooks.size();
	}

	public void returnAllBooks() {
		for (Book book : new ArrayList<>(borrowedBooks)) {
			returnBook(book);
		}
	}
}
