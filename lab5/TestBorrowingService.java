package lab5;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestBorrowingService {

    private BorrowingServiceAPI service;
    private Member alice;
    private Member bob;
    private Book book;

    @BeforeEach
    void setUp() {
        service = new BorrowingService();
        alice = new Member("Alice");
        bob = new Member("Bob");
        book = new PaperBook("Dune");
    }

     @Test
    void borrowAndReturnAvailableBook() {
        BorrowingBookResult result = service.borrowBook(alice, book);

        assertTrue(result.isSuccess());
        assertEquals("Borrowed Dune for Alice.", result.getMessage());
        assertFalse(book.getIsAvailable());
        assertEquals(1, alice.borrowedBooksCount());
        assertTrue(alice.getBorrowedBooks().contains(book));

        result = service.returnBook(alice, book);

        assertTrue(result.isSuccess());
        assertEquals("Returned Dune for Alice.", result.getMessage());
        assertTrue(book.getIsAvailable());
        assertEquals(0, alice.borrowedBooksCount());
    }

    @Test
    void rejectBorrowingSameBookTwice() {
        assertTrue(service.borrowBook(alice, book).isSuccess());

        assertFailure(
            service.borrowBook(alice, book),
            "This member has already borrowed this book."
        );

        assertEquals(1, alice.borrowedBooksCount());
        assertFalse(book.getIsAvailable());
    }

    @Test
    void rejectBorrowingUnavailableBook() {
        assertTrue(service.borrowBook(alice, book).isSuccess());

        assertFailure(
            service.borrowBook(bob, book),
            "This book is currently unavailable."
        );

        assertEquals(1, alice.borrowedBooksCount());
        assertEquals(0, bob.borrowedBooksCount());
        assertFalse(book.getIsAvailable());
    }

    @Test
    void rejectReturningAnotherMembersBook() {
        assertTrue(service.borrowBook(alice, book).isSuccess());

        assertFailure(
            service.returnBook(bob, book),
            "This member has not borrowed this book."
        );

        assertTrue(alice.getBorrowedBooks().contains(book));
        assertEquals(0, bob.borrowedBooksCount());
        assertFalse(book.getIsAvailable());
    }

    @Test
    void rejectReturningUnborrowedBook() {
        assertFailure(
            service.returnBook(alice, book),
            "This book is already available and cannot be returned."
        );

        assertTrue(book.getIsAvailable());
        assertEquals(0, alice.borrowedBooksCount());
    }

    @Test
    void rejectReturningBookTwice() {
        assertTrue(service.borrowBook(alice, book).isSuccess());
        assertTrue(service.returnBook(alice, book).isSuccess());

        assertFailure(
            service.returnBook(alice, book),
            "This book is already available and cannot be returned."
        );

        assertTrue(book.getIsAvailable());
        assertEquals(0, alice.borrowedBooksCount());
    }

    @Test
    void rejectNullArguments() {
        assertFailure(
            service.borrowBook(null, book),
            "Member is required."
        );
        assertFailure(
            service.borrowBook(alice, null),
            "Book is required."
        );
        assertFailure(
            service.returnBook(null, book),
            "Member is required."
        );
        assertFailure(
            service.returnBook(alice, null),
            "Book is required."
        );

        assertTrue(book.getIsAvailable());
        assertEquals(0, alice.borrowedBooksCount());
    }

    @Test
    void rejectFourthBook() {
        borrowThreeBooks();

        Book fourthBook = new PaperBook("The Hobbit");

        assertFailure(
            service.borrowBook(alice, fourthBook),
            "Borrowing limit reached: maximum 3 books."
        );

        assertEquals(3, alice.borrowedBooksCount());
        assertTrue(fourthBook.getIsAvailable());
        assertFalse(alice.getBorrowedBooks().contains(fourthBook));
    }

    @Test
    void allowBorrowingAfterReturningABook() {
        borrowThreeBooks();

        assertTrue(service.returnBook(alice, book).isSuccess());

        Book fourthBook = new PaperBook("The Hobbit");
        BorrowingBookResult result =
            service.borrowBook(alice, fourthBook);

        assertTrue(result.isSuccess());
        assertEquals("Borrowed The Hobbit for Alice.", result.getMessage());
        assertEquals(3, alice.borrowedBooksCount());
        assertTrue(alice.getBorrowedBooks().contains(fourthBook));
        assertFalse(fourthBook.getIsAvailable());
        assertTrue(book.getIsAvailable());
    }

    private void borrowThreeBooks() {
        assertTrue(service.borrowBook(alice, book).isSuccess());
        assertTrue(
            service.borrowBook(alice, new EBook("1984")).isSuccess()
        );
        assertTrue(
            service.borrowBook(alice, new AudioBook("Moby Dick")).isSuccess()
        );
    }

    private void assertFailure(
            BorrowingBookResult result, String expectedMessage) {
        assertFalse(result.isSuccess());
        assertEquals(expectedMessage, result.getMessage());
    }
}