package lab5;

public class BorrowingService implements BorrowingServiceAPI {

    @Override
    public boolean borrowBook(Member member, Book book) {
        if (member == null || book == null) {
            return false;
        }

        if (member.getBorrowedBooks().contains(book)
                || !book.getIsAvailable()) {
            return false;
        }

        member.getBorrowedBooks().add(book);
        book.setIsAvailable(false);
        return true;
    }

    @Override
    public boolean returnBook(Member member, Book book) {
        if (member == null || book == null) {
            return false;
        }

        if (!member.getBorrowedBooks().contains(book)) {
            return false;
        }

        member.getBorrowedBooks().remove(book);
        book.setIsAvailable(true);
        return true;
    }
}