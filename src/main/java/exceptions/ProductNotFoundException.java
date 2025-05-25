package exceptions;

public class ProductNotFoundException extends Exception {
  public ProductNotFoundException(int productId) {
    super("Product not found with ID: " + productId);
  }
}
