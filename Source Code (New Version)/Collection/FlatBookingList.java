package Collection;

import java.util.List;
import Application.FlatBooking;
import Interfaces.CSVHandler;

public class FlatBookingList {
    private List<FlatBooking> bookings;
    private final String csvPath;
    private final CSVHandler<FlatBooking> csvHandler;

    public FlatBookingList(String csvPath, CSVHandler<FlatBooking> csvHandler) {
        this.csvPath = csvPath;
        this.csvHandler = csvHandler;
        this.bookings = csvHandler.loadFromCSV(csvPath);
    }

    public List<FlatBooking> getBookings() {
        return bookings;
    }

    public void addItem(FlatBooking booking) {
        bookings.add(booking);
    }

    public void removeItem(FlatBooking booking) {
        bookings.remove(booking);
    }

    public void saveItems() {
        csvHandler.saveToCSV(bookings, csvPath);
    }

    public FlatBooking findByProjectName(String name) {
        for (FlatBooking booking : bookings) {
            if (booking.getProjectName().equalsIgnoreCase(name)) {
                return booking;
            }
        }
        return null;
    }
}
