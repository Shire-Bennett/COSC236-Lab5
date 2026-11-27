package lab5;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class TestBookFactory {

	@Test
	void factoriesCreateRightType() {
		Book paper = new PaperBookFactory().createBook("Dune");
		Book ebook = new EBookFactory().createBook("1984");
		Book audio = new AudioBookFactory().createBook("Moby Dick");

		assertTrue(paper instanceof PaperBook);
		assertTrue(ebook instanceof EBook);
		assertTrue(audio instanceof AudioBook);
		assertFalse(paper instanceof EBook);
	}

	@Test
	void createdBookHasTitleAndIsAvailable() {
		Book book = new EBookFactory().createBook("1984");
		assertEquals("1984", book.getTitle());
		assertTrue(book.getIsAvailable());
	}

	@Test
	void controllerAddsRightType() {
		LibrarianController librarian = new LibrarianController();
		librarian.addBook(new AudioBookFactory(), "Moby Dick");
		Book book = librarian.getLibrary().findBookByTitle("Moby Dick");
		assertTrue(book instanceof AudioBook);
	}

	@Test
	void unsupportedTypeAddsNothing() {
		LibrarianController librarian = new LibrarianController();
		librarian.addBook(null, "Dune");
		assertEquals(0, librarian.getLibrary().booksCount());
	}
}