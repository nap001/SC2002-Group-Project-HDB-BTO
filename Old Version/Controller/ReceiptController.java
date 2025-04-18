package Controller;

import Application.*;
import Collection.ReceiptList;
import Interfaces.CSVHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReceiptController {
    private ReceiptList receiptList;

    public ReceiptController(String csvPath, CSVHandler<Receipt> csvHandler) {
        this.receiptList = new ReceiptList(csvPath, csvHandler);
    }

    public boolean generateReceiptByFlatBooking(FlatBooking flatBooking) {
        if (flatBooking == null) {
            return false;
        }

        Receipt receipt = new Receipt(
            flatBooking.getApplicant(),
            flatBooking.getProject(),
            flatBooking.getFlatType(),
            flatBooking.getFlatID(),
            flatBooking.getOfficer()
        );
        receipt.generateReceipt();
        receiptList.addItem(receipt);
        receiptList.saveItems();
        return true;
    }
    public boolean generateReceiptByApplication(Application application) {
        if (application == null || application.getApplicant() == null ||
            application.getProject() == null || application.getFlatType() == null ||
            application.getApplicant().getFlatBooking() == null ||
            application.getApplicant().getFlatBooking().getOfficer() == null) {
            return false;
        }

        FlatBooking flatBooking = application.getApplicant().getFlatBooking();

        Receipt receipt = new Receipt(
            application.getApplicant(),
            application.getProject(),
            application.getFlatType(),
            flatBooking.getFlatID(),
            flatBooking.getOfficer()
        );
        receipt.generateReceipt();
        receiptList.addItem(receipt);
        receiptList.saveItems();
        return true;
    }
    public static void setReceipts(List<Receipt> receipts) {
        // This is static and not linked to ReceiptList anymore.
        // Recommend removing or refactoring to be instance-based if you need it.
        // Left empty for compatibility.
    }
}
