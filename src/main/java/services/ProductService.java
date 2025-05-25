package services;

import storedata.model.Product;

public interface ProductService {
    boolean isAvailable(Product product, int requestedQuantity);
    boolean isExpired(Product product);
    boolean isNearExpiration(Product product, int thresholdDays);
}
