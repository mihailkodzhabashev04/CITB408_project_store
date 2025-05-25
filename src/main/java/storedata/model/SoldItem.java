package storedata.model;

import java.io.Serializable;

public class SoldItem implements Serializable {
    private Product product;
    private int quantity;
    private double salePricePerUnit;


    public SoldItem(Product product, int quantity, double salePricePerUnit) {
        this.product = product;
        this.quantity = quantity;
        this.salePricePerUnit = salePricePerUnit;
    }


    public SoldItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getSalePricePerUnit() {
        return salePricePerUnit;
    }

    public void setSalePricePerUnit(double salePricePerUnit) {
        this.salePricePerUnit = salePricePerUnit;
    }

    public double getTotalPrice() {
        return salePricePerUnit * quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format("%s x%d @ %.2f = %.2f",
                product.getName(), quantity, salePricePerUnit, getTotalPrice());
    }
}
