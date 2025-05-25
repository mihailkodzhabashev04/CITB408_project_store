package storedata.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class Receipt implements Serializable {
    private static int receiptCounter = 0;

    private final int serialNumber;
    private final Cashier cashier;
    private final LocalDateTime dateTime;
    private final List<SoldItem> items;
    private final double totalAmount;

    public Receipt(Cashier cashier, List<SoldItem> items, double totalAmount) {
        this.serialNumber = ++receiptCounter;
        this.cashier = cashier;
        this.dateTime = LocalDateTime.now();
        this.items = items;
        this.totalAmount = totalAmount;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public Cashier getCashier() {
        return cashier;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public List<SoldItem> getItems() {
        return items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Receipt #").append(serialNumber).append("\n")
                .append("Cashier: ").append(cashier.getName()).append("\n")
                .append("Date: ").append(dateTime).append("\n")
                .append("Items:\n");

        for (SoldItem item : items) {
            sb.append("- ").append(item.getProduct().getName())
                    .append(" x").append(item.getQuantity())
                    .append(" @ ").append(item.getSalePricePerUnit()).append("\n");
        }

        sb.append("Total: $").append(String.format("%.2f", totalAmount));
        return sb.toString();
    }

}
