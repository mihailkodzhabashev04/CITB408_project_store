package storedata.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Product implements Serializable{
    private final int id;
    private String name;
    private final double deliveryPrice;
    private ProductCategory productCategory;
    private final LocalDate expirationDate;
    private int quantity;

    public Product(int id, String name, double deliveryPrice, ProductCategory productCategory, LocalDate expirationDate, int quantity) {
        this.id = id;
        this.name = name;
        this.deliveryPrice = deliveryPrice;
        this.productCategory = productCategory;
        this.expirationDate = expirationDate;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getDeliveryPrice() {
        return deliveryPrice;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return name + " (ID: " + id + ", Qty: " + quantity + ")";
    }

}
