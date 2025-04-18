package Controller;

import Application.*;
import Collection.*;
import Entities.Applicant;
import Enum.*;

import java.util.*;

public class BookingController {
    private FlatBookingList flatBookingList;
    private ReceiptController receiptController;
    private Map<String, Receipt> receipts;

    public BookingController(FlatBookingList flatBookingList) {
        this.flatBookingList = flatBookingList;
        this.receiptController = new ReceiptController();
        this.receipts = new HashMap<>();
    }

    public boolean createBooking(FlatBooking flatBooking) {
        if (flatBooking == null || hasBooking(flatBooking.getApplicant().getNRIC())) {
            return false;
        }

        flatBooking.setBookingStatus(ApplicationStatus.SUCCESSFUL);
        flatBooking.getApplicant().setConfirmedFlatType(flatBooking.getFlatType());

        if (flatBooking.getApplicant().getApplication() != null &&
            flatBooking.getApplicant().getApplication().getApplicationStatus() == ApplicationStatus.SUCCESSFUL) {
            flatBooking.getApplicant().getApplication().setApplicationStatus(ApplicationStatus.BOOKED);
        }

        flatBookingList.addItem(flatBooking);
        return true;
    }

    public boolean rejectBookingRequest(Application application, String message) {
        if (application == null) return false;

        FlatBooking flatBooking = getBookingByApplication(application);
        if (flatBooking == null) {
            System.out.println("No such booking request.");
            return false;
        }

        flatBooking.setBookingStatus(ApplicationStatus.UNSUCCESSFUL);
        flatBooking.setMessage(message);
        return true;
    }

    public boolean isBookingRequestApproved(Application application) {
        FlatBooking booking = getBookingByApplication(application);
        return booking != null && booking.getBookingStatus() == ApplicationStatus.SUCCESSFUL;
    }

    public FlatBooking getBookingByApplication(Application application) {
        if (application == null) return null;

        for (FlatBooking booking : flatBookingList.getBookings()) {
            if (booking.getApplicant().getNRIC().equals(application.getApplicant().getNRIC())) {
                return booking;
            }
        }
        return null;
    }

    public List<FlatBooking> getBookingByApplicationStatus(ApplicationStatus status) {
        List<FlatBooking> result = new ArrayList<>();
        for (FlatBooking booking : flatBookingList.getBookings()) {
            if (booking.getBookingStatus() == status) {
                result.add(booking);
            }
        }

        if (result.isEmpty()) {
            System.out.println("No bookings found with application status: " + status);
        }
        return result;
    }

    public Receipt createReceipt(FlatBooking flatBooking) {
        if (flatBooking == null || flatBooking.getOfficer() == null) {
            System.out.println("Booking not complete, receipt generation failed.");
            return null;
        }

        String nric = flatBooking.getApplicant().getNRIC();
        if (receipts.containsKey(nric)) {
            return receipts.get(nric);
        }

        Receipt receipt = new Receipt(flatBooking.getApplicant(), flatBooking.getProject(),
                                      flatBooking.getFlatType(), flatBooking.getFlatID(), flatBooking.getOfficer());
        boolean content = receiptController.generateReceiptByFlatBooking(flatBooking);
        receipt.setReceiptContent(content);
        receipts.put(nric, receipt);

        return receipt;
    }

    public boolean hasReceipt(Applicant applicant) {
        return receipts.containsKey(applicant.getNRIC());
    }

    public boolean hasReceipt(String NRIC) {
        return receipts.containsKey(NRIC);
    }

    public Receipt getReceiptByApplicant(String NRIC) {
        return receipts.get(NRIC);
    }

    public void setReceipts(List<Receipt> receiptList) {
        receipts.clear();
        for (Receipt receipt : receiptList) {
            receipts.put(receipt.getApplicant().getNRIC(), receipt);
        }
    }

    public List<FlatBooking> getAllFlatBookings() {
        return flatBookingList.getBookings();
    }

    public List<Receipt> getAllReceipts() {
        return new ArrayList<>(receipts.values());
    }

    private boolean hasBooking(String nric) {
        for (FlatBooking booking : flatBookingList.getBookings()) {
            if (booking.getApplicant().getNRIC().equals(nric)) {
                return true;
            }
        }
        return false;
    }
}
