package storedata.model;

import java.util.ArrayList;
import java.util.List;

public class CashRegister {
    private final int id;
    private Cashier assignedCashier;
    private final List<Receipt> receipts;

    public CashRegister(int id, Cashier assignedCashier) {
        this.id = id;
        this.assignedCashier = assignedCashier;
        this.receipts = new ArrayList<>();
    }

    public void assignCashier(Cashier cashier) {
        this.assignedCashier = cashier;
    }

    public void addReceipt(Receipt receipt) {
        receipts.add(receipt);
    }

    public List<Receipt> getReceipts() {
        return receipts;
    }

    public Cashier getAssignedCashier() {
        return assignedCashier;
    }

    public int getId() {
        return id;
    }

    public double getTotalRevenue() {
        return receipts.stream()
                .mapToDouble(Receipt::getTotalAmount)
                .sum();
    }

    @Override
    public String toString() {
        return "CashRegister{" +
                "id=" + id +
                ", cashier=" + (assignedCashier != null ? assignedCashier.getName() : "Unassigned") +
                ", receipts=" + receipts.size() +
                '}';
    }
}
