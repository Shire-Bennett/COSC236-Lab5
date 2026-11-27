package lab5;

public class BorrowingService implements BorrowingServiceAPI {

	private static BorrowingService instance = new BorrowingService();
	private int borrowingLimit;

	private BorrowingService() {
	    borrowingLimit = 3;
	}

	public static BorrowingService getInstance() {
	    return instance;
	}

    @Override
    public BorrowingBookResult borrowBook(Member member, Book book) {
        if (member == null) {
            return new BorrowingBookResult(false, "Member is required.");
        }

        if (book == null) {
            return new BorrowingBookResult(false, "Book is required.");
        }

        if (member.getBorrowedBooks().contains(book)) {
            return new BorrowingBookResult(
                false, "This member has already borrowed this book."
            );
        }

        if (!book.getIsAvailable()) {
            return new BorrowingBookResult(
                false, "This book is currently unavailable."
            );
        }

        if (member.borrowedBooksCount() >= borrowingLimit) {
            return new BorrowingBookResult(
                false, "Borrowing limit reached: maximum 3 books."
            );
        }

        member.getBorrowedBooks().add(book);
        book.setIsAvailable(false);

        return new BorrowingBookResult(
            true,
            "Borrowed " + book.getTitle() + " for " + member.getName() + "."
        );
    }

    @Override
    public BorrowingBookResult returnBook(Member member, Book book) {
        if (member == null) {
            return new BorrowingBookResult(false, "Member is required.");
        }

        if (book == null) {
            return new BorrowingBookResult(false, "Book is required.");
        }

        if (book.getIsAvailable()) {
            return new BorrowingBookResult(
                false, "This book is already available and cannot be returned."
            );
        }

        if (!member.getBorrowedBooks().contains(book)) {
            return new BorrowingBookResult(
                false, "This member has not borrowed this book."
            );
        }

        member.getBorrowedBooks().remove(book);
        book.setIsAvailable(true);

        return new BorrowingBookResult(
            true,
            "Returned " + book.getTitle() + " for " + member.getName() + "."
        );
    }
}