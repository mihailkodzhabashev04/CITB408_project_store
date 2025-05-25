package org.example;

import exceptions.*;
import storedata.model.*;
import services.ProductService;
import services.ReceiptService;
import services.impl.ProductServiceImplementation;
import services.impl.ReceiptServiceImplementation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ProductService productService = new ProductServiceImplementation();
        ReceiptService receiptService = new ReceiptServiceImplementation();

        List<Receipt> receiptList = new ArrayList<>();

        Store store = new Store(
                "Mihail's market",
                20.0,
                30.0,
                5,
                15.0,
                receiptList
        );


        Product milk = new Product(1, "Milk", 2.00, ProductCategory.FOOD_ITEM, LocalDate.now().plusDays(3), 20);
        Product soap = new Product(2, "rice", 1.00, ProductCategory.NON_FOOD_ITEM, LocalDate.now().plusDays(100), 50);
        store.addProduct(milk);
        store.addProduct(soap);

        Cashier cashier = new Cashier(101, "Kasier Kasierov", 1200.0);
        store.hireCashier(cashier);


        SoldItem item1 = new SoldItem(milk, 2);
        SoldItem item2 = new SoldItem(soap, 3);
        List<SoldItem> cart = Arrays.asList(item1, item2);

        double customerFunds = 20.00;

        try {
            store.sellProducts(cart, cashier.getId(), customerFunds);
        } catch (ProductNotFoundException | ExpiredProductException |
                 InsufficientStockException | InsufficientFundsException e) {
            System.err.println("Sale failed: " + e.getMessage());
        }

        // Summary
        System.out.println("\n=== Store Summary ===");
        System.out.println("Expenses: " + store.calculateExpenses());
        System.out.println("Revenue: " + store.calculateRevenue());
        System.out.println("Profit: " + store.calculateProfit());
    }
}
