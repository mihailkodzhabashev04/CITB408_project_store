package exceptions;

public class InsufficientStockException extends Exception {
    public InsufficientStockException(String productName, int requested, int available) {
        super("Insufficient stock for " + productName + ": requested " + requested + ", but only " + available + " available.");
    }
}
