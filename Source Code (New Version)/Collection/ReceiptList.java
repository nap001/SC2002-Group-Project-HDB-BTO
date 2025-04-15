package Collection;

import java.util.List;
import Application.Receipt;
import Interfaces.CSVHandler;

public class ReceiptList {
    private List<Receipt> receipts;
    private final String csvPath;
    private final CSVHandler<Receipt> csvHandler;

    public ReceiptList(String csvPath, CSVHandler<Receipt> csvHandler) {
        this.csvPath = csvPath;
        this.csvHandler = csvHandler;
        this.receipts = csvHandler.loadFromCSV(csvPath);
    }

    public List<Receipt> getReceipts() {
        return receipts;
    }

    public void addItem(Receipt receipt) {
        receipts.add(receipt);
    }

    public void removeItem(Receipt receipt) {
        receipts.remove(receipt);
    }

    public void saveItems() {
        csvHandler.saveToCSV(receipts, csvPath);
    }

    public Receipt findByProjectName(String name) {
        for (Receipt receipt : receipts) {
            if (receipt.getProjectName().equalsIgnoreCase(name)) {
                return receipt;
            }
        }
        return null;
    }
}
