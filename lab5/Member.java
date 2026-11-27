package lab5;

import java.util.ArrayList;
import java.util.Iterator;

public class Member {

	private String name;
	private ArrayList<Book> borrowedBooks; // Book class dependency
	private BorrowingService borrowingService;

	public Member(String name, BorrowingService service) {
		this.name = name;
		this.borrowedBooks = new ArrayList<>();
		this.borrowingService = service;
	}

	public String getName() {
		return name;
	}

	public ArrayList<Book> getBorrowedBooks() {
		return borrowedBooks;
	}

	public BorrowingService getBorrowingService() {
		return borrowingService;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return "Member: " + name;
	}

	public void borrowBook(Book book) {


		BorrowingBookResult result =
				borrowingService.borrowBook(this, book);

		System.out.println(
				"Success: " + result.isSuccess() + ": " + result.getMessage()
				);
	}

	public void returnBook(Book book) {

		BorrowingBookResult result =
				borrowingService.returnBook(this, book);

		System.out.println(
				"Success: " + result.isSuccess() + ": " + result.getMessage()
				);
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
