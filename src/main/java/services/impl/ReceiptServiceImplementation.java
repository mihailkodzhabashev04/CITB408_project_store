package services.impl;

import storedata.model.Receipt;
import services.ReceiptService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

public class ReceiptServiceImplementation implements ReceiptService {

    private final String RECEIPT_DIR = "receipts";

    public ReceiptServiceImplementation() {
        // Ensure the receipts directory exists
        File directory = new File(RECEIPT_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    @Override
    public void printToFile(Receipt receipt) {
        String fileName = RECEIPT_DIR + "/receipt_" + receipt.getSerialNumber() + ".txt";
        try (PrintWriter out = new PrintWriter(fileName)) {
            out.println(receipt);
        } catch (Exception e) {
            System.err.println("Error saving receipt to text file: " + e.getMessage());
        }
    }

    @Override
    public void serializeToFile(Receipt receipt) {
        String fileName = RECEIPT_DIR + "/receipt_" + receipt.getSerialNumber() + ".dat";
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(receipt);
        } catch (Exception e) {
            System.err.println("Error serializing receipt: " + e.getMessage());
        }
    }
}
