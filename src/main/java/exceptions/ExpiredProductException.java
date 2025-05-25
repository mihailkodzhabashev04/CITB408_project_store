package exceptions;

public class ExpiredProductException extends Exception {
    public ExpiredProductException(String productName) {
        super("Product '" + productName + "' is expired and cannot be sold.");
    }
}
