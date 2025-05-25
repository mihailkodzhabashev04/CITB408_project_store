package services.impl;

import storedata.model.Product;
import services.ProductService;

import java.time.LocalDate;

public class ProductServiceImplementation implements ProductService {

    @Override
    public boolean isAvailable(Product product, int requestedQuantity) {
        return product.getQuantity() >= requestedQuantity;
    }

    @Override
    public boolean isExpired(Product product) {
        return product.getExpirationDate().isBefore(LocalDate.now());
    }

    @Override
    public boolean isNearExpiration(Product product, int thresholdDays) {
        long daysUntilExpiration = java.time.temporal.ChronoUnit.DAYS.between(
                LocalDate.now(), product.getExpirationDate());
        return daysUntilExpiration <= thresholdDays && daysUntilExpiration > 0;
    }
}
