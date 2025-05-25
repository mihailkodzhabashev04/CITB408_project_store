package storedata.model;

import exceptions.*;
import services.ProductService;
import services.ReceiptService;
import services.impl.ProductServiceImplementation;
import services.impl.ReceiptServiceImplementation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Store {
    private final String name;
    private final double foodMarkupPercent;
    private final double nonFoodMarkupPercent;
    private final int nearExpirationThresholdDays;
    private final double nearExpirationDiscountPercent;

    private final Map<Integer, Product> products;
    private final Map<Integer, Cashier> cashiers;
    private final List<Receipt> receipts;

    private final ProductService productService = new ProductServiceImplementation();
    private final ReceiptService receiptService = new ReceiptServiceImplementation();

    public Store(String name, double foodMarkupPercent, double nonFoodMarkupPercent,
                 int nearExpirationThresholdDays, double nearExpirationDiscountPercent,
                 List<Receipt> receipts) {
        this.name = name;
        this.foodMarkupPercent = foodMarkupPercent;
        this.nonFoodMarkupPercent = nonFoodMarkupPercent;
        this.nearExpirationThresholdDays = nearExpirationThresholdDays;
        this.nearExpirationDiscountPercent = nearExpirationDiscountPercent;

        this.products = new HashMap<>();
        this.cashiers = new HashMap<>();
        this.receipts = receipts;
    }

    public void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    public void hireCashier(Cashier cashier) {
        cashiers.put(cashier.getId(), cashier);
    }

    public void sellProducts(List<SoldItem> soldItems, int cashierId, double customerFunds)
            throws InsufficientStockException, ExpiredProductException,
            ProductNotFoundException, InsufficientFundsException {

        Cashier cashier = cashiers.get(cashierId);
        if (cashier == null) {
            throw new IllegalArgumentException("Cashier not found with ID: " + cashierId);
        }

        double totalAmount = 0;

        for (SoldItem soldItem : soldItems) {
            int productId = soldItem.getProduct().getId();
            Product product = products.get(productId);

            if (product == null) {
                throw new ProductNotFoundException(productId);
            }

            if (productService.isExpired(product)) {
                throw new ExpiredProductException(product.getName());
            }

            if (!productService.isAvailable(product, soldItem.getQuantity())) {
                throw new InsufficientStockException(product.getName(), soldItem.getQuantity(), product.getQuantity());
            }

            double markupPercent = (product.getProductCategory() == ProductCategory.FOOD_ITEM)
                    ? foodMarkupPercent
                    : nonFoodMarkupPercent;

            double salePrice = product.getDeliveryPrice() * (1 + markupPercent / 100.0);

            if (productService.isNearExpiration(product, nearExpirationThresholdDays)) {
                salePrice *= (1 - nearExpirationDiscountPercent / 100.0);
            }

            soldItem.setSalePricePerUnit(salePrice);
            totalAmount += salePrice * soldItem.getQuantity();
        }

        if (customerFunds < totalAmount) {
            throw new InsufficientFundsException(totalAmount, customerFunds);
        }

        for (SoldItem soldItem : soldItems) {
            Product product = products.get(soldItem.getProduct().getId());
            product.setQuantity(product.getQuantity() - soldItem.getQuantity());
        }

        Receipt receipt = generateReceipt(soldItems, cashier, totalAmount);

        System.out.println(receipt);
        receiptService.printToFile(receipt);
        receiptService.serializeToFile(receipt);
    }

    private Receipt generateReceipt(List<SoldItem> soldItems, Cashier cashier, double totalAmount) {
        Receipt receipt = new Receipt(cashier, soldItems, totalAmount);
        receipts.add(receipt);
        return receipt;
    }

    public double calculateExpenses() {
        double salaryExpenses = cashiers.values().stream()
                .mapToDouble(Cashier::getMonthlySalary)
                .sum();

        double deliveryExpenses = products.values().stream()
                .mapToDouble(p -> p.getDeliveryPrice() * p.getQuantity())
                .sum();

        return salaryExpenses + deliveryExpenses;
    }

    public double calculateRevenue() {
        return receipts.stream()
                .mapToDouble(Receipt::getTotalAmount)
                .sum();
    }

    public double calculateProfit() {
        return calculateRevenue() - calculateExpenses();
    }

    public Map<Integer, Product> getProducts() {
        return products;
    }

    public Map<Integer, Cashier> getCashiers() {
        return cashiers;
    }

    public List<Receipt> getReceipts() {
        return receipts;
    }

    public String getName() {
        return name;
    }
}
