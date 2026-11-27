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
        assertTrue(service.borrowBook(alice, book));

        assertFalse(book.getIsAvailable());
        assertEquals(1, alice.borrowedBooksCount());
        assertTrue(alice.getBorrowedBooks().contains(book));

        assertTrue(service.returnBook(alice, book));

        assertTrue(book.getIsAvailable());
        assertEquals(0, alice.borrowedBooksCount());
    }

    @Test
    void rejectBorrowingSameBookTwice() {
        assertTrue(service.borrowBook(alice, book));
        assertFalse(service.borrowBook(alice, book));

        assertEquals(1, alice.borrowedBooksCount());
        assertFalse(book.getIsAvailable());
    }

    @Test
    void rejectBorrowingUnavailableBook() {
        assertTrue(service.borrowBook(alice, book));
        assertFalse(service.borrowBook(bob, book));

        assertEquals(1, alice.borrowedBooksCount());
        assertEquals(0, bob.borrowedBooksCount());
        assertFalse(book.getIsAvailable());
    }

    @Test
    void rejectReturningAnotherMembersBook() {
        assertTrue(service.borrowBook(alice, book));
        assertFalse(service.returnBook(bob, book));

        assertTrue(alice.getBorrowedBooks().contains(book));
        assertEquals(0, bob.borrowedBooksCount());
        assertFalse(book.getIsAvailable());
    }

    @Test
    void rejectReturningUnborrowedBook() {
        assertFalse(service.returnBook(alice, book));

        assertTrue(book.getIsAvailable());
        assertEquals(0, alice.borrowedBooksCount());
    }

    @Test
    void rejectReturningBookTwice() {
        assertTrue(service.borrowBook(alice, book));
        assertTrue(service.returnBook(alice, book));
        assertFalse(service.returnBook(alice, book));

        assertTrue(book.getIsAvailable());
        assertEquals(0, alice.borrowedBooksCount());
    }

    @Test
    void rejectNullArguments() {
        assertFalse(service.borrowBook(null, book));
        assertFalse(service.borrowBook(alice, null));
        assertFalse(service.returnBook(null, book));
        assertFalse(service.returnBook(alice, null));

        assertTrue(book.getIsAvailable());
        assertEquals(0, alice.borrowedBooksCount());
    }
}