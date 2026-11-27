package lab5;

public class BorrowingBookResult {

    private boolean isSuccess;
    private String borrowingMessage;

    public BorrowingBookResult(boolean isSuccess, String borrowingMessage) {
        this.isSuccess = isSuccess;
        this.borrowingMessage = borrowingMessage;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getMessage() {
        return borrowingMessage;
    }

    public void setMessage(String borrowingMessage) {
        this.borrowingMessage = borrowingMessage;
    }
}