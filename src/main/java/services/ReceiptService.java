package services;

import storedata.model.Receipt;

public interface ReceiptService {
    void printToFile(Receipt receipt);
    void serializeToFile(Receipt receipt);
}
