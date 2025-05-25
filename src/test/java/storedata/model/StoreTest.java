package storedata.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.impl.ProductServiceImplementation;
import services.impl.ReceiptServiceImplementation;
import exceptions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class StoreTest {

    private Store store;

    @BeforeEach
    public void setup() {
        store = new Store(
                "Test Store",
                20.0,
                30.0,
                5,
                15.0,
                new ArrayList<>()
        );

        Product milk = new Product(1, "Milk", 3.0, ProductCategory.FOOD_ITEM, LocalDate.now().plusDays(2), 10);
        Product soap = new Product(2, "Soap", 0.5, ProductCategory.NON_FOOD_ITEM, LocalDate.now().plusDays(30), 20);
        store.addProduct(milk);
        store.addProduct(soap);

        Cashier cashier = new Cashier(1, "Kasierka Kasierova", 1000.0);
        store.hireCashier(cashier);
    }

    @Test
    public void testProfitCalculationAfterSale() throws Exception {
        Product milk = store.getProducts().get(1);
        Product soap = store.getProducts().get(2);

        SoldItem item1 = new SoldItem(milk, 2);
        SoldItem item2 = new SoldItem(soap, 1);

        store.sellProducts(Arrays.asList(item1, item2), 1, 50.0);

        double revenue = store.calculateRevenue();
        double expenses = store.calculateExpenses();
        double profit = store.calculateProfit();

        assertTrue(revenue > 0);
        assertTrue(expenses > 0);
        assertEquals(profit, revenue - expenses, 0.01);
    }

    @Test
    public void testInsufficientStockThrowsException() {
        Product milk = store.getProducts().get(1);  // Only 10 available
        SoldItem item = new SoldItem(milk, 20);     // Requesting more

        Exception exception = assertThrows(
                InsufficientStockException.class,
                () -> store.sellProducts(Arrays.asList(item), 1, 100.0)
        );

        assertTrue(exception.getMessage().contains("Insufficient stock for"));
    }

    @Test
    public void testExpiredProductThrowsException() {
        Product expired = new Product(3, "Old Cheese", 2.0, ProductCategory.FOOD_ITEM, LocalDate.now().minusDays(1), 5);
        store.addProduct(expired);

        SoldItem item = new SoldItem(expired, 1);

        Exception exception = assertThrows(
                ExpiredProductException.class,
                () -> store.sellProducts(Arrays.asList(item), 1, 50.0)
        );

        assertTrue(exception.getMessage().contains("is expired"));
    }

    @Test
    public void testProductNotFoundThrowsException() {
        Product ghostProduct = new Product(999, "Ghost", 1.0, ProductCategory.NON_FOOD_ITEM, LocalDate.now().plusDays(10), 1);
        SoldItem item = new SoldItem(ghostProduct, 1);

        Exception exception = assertThrows(
                ProductNotFoundException.class,
                () -> store.sellProducts(Arrays.asList(item), 1, 10.0)
        );

        assertTrue(exception.getMessage().contains("Product not found"));
    }

    @Test
    public void testInsufficientFundsThrowsException() {
        Product milk = store.getProducts().get(1);
        SoldItem item = new SoldItem(milk, 2);  // Enough quantity, but we give less money

        Exception exception = assertThrows(
                InsufficientFundsException.class,
                () -> store.sellProducts(Arrays.asList(item), 1, 0.5)  // Not enough money
        );

        assertTrue(exception.getMessage().contains("Insufficient funds"));
    }

    @Test
    public void testNearExpirationDiscountIsApplied() throws Exception {
        Product nearExpiry = new Product(4, "Yogurt", 10.0, ProductCategory.FOOD_ITEM, LocalDate.now().plusDays(1), 10);
        store.addProduct(nearExpiry);

        SoldItem item = new SoldItem(nearExpiry, 1);
        store.sellProducts(Arrays.asList(item), 1, 100.0);

        double expectedPrice = 10.0 * (1 + 0.20) * (1 - 0.15);  // markup + discount
        assertEquals(expectedPrice, item.getSalePricePerUnit(), 0.01);
    }


}
