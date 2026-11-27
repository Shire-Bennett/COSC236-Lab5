package lab5;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestOtherBookTypes {

    private LibrarianController librarian;
    private Library library;

    @BeforeEach
    void setUp() {
        librarian = new LibrarianController();
        library = librarian.getLibrary();
        librarian.addMember("Alice");
    }

    @Test
    void eBookCanBeAddedBorrowedReturnedAndRemoved() {
        librarian.addEBook("Dune");

        Book book = library.findBookByTitle("Dune");
        Member member = library.findMemberByName("Alice");

        assertInstanceOf(EBook.class, book);
        assertEquals(1, library.booksCount());
        assertTrue(book.getIsAvailable());

        librarian.borrowBookByMember("Dune", "Alice");

        assertFalse(book.getIsAvailable());
        assertEquals(1, member.borrowedBooksCount());
        assertTrue(member.getBorrowedBooks().contains(book));

        librarian.returnBookByMember("Dune", "Alice");

        assertTrue(book.getIsAvailable());
        assertEquals(0, member.borrowedBooksCount());

        librarian.removeBook("Dune");

        assertEquals(0, library.booksCount());
        assertNull(library.findBookByTitle("Dune"));
    }

    @Test
    void audioBookCanBeAddedBorrowedReturnedAndRemoved() {
        librarian.addAudioBook("1984");

        Book book = library.findBookByTitle("1984");
        Member member = library.findMemberByName("Alice");

        assertInstanceOf(AudioBook.class, book);
        assertEquals(1, library.booksCount());
        assertTrue(book.getIsAvailable());

        librarian.borrowBookByMember("1984", "Alice");

        assertFalse(book.getIsAvailable());
        assertEquals(1, member.borrowedBooksCount());
        assertTrue(member.getBorrowedBooks().contains(book));

        librarian.returnBookByMember("1984", "Alice");

        assertTrue(book.getIsAvailable());
        assertEquals(0, member.borrowedBooksCount());

        librarian.removeBook("1984");

        assertEquals(0, library.booksCount());
        assertNull(library.findBookByTitle("1984"));
    }
}