package exceptions;

public class InsufficientFundsException extends Exception {
  public InsufficientFundsException(double totalCost, double availableFunds) {
    super(String.format("Insufficient funds: Needed $%.2f, but only $%.2f available.", totalCost, availableFunds));
  }
}
