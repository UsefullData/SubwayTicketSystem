package exceptions;

public class InsufficientFundsException extends Exception {
    public InsufficientFundsException() {
        super("Insufficient funds for the transaction.");
    }

    public InsufficientFundsException(String message) {
        super(message);
    }
}
