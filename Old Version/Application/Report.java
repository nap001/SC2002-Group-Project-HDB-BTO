package Application;

import Application.*;
import Boundaries.*;
import Controller.*;
import Entities.*;
import Enum.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

public class Report {
	private String reportTitle;
    private List<FlatBooking> bookings;
    private Map<String, Object> filters;
    private Date generationDate;

    // Constructor
    public Report(String reportTitle, Map<String, Object> filters) {
        this.reportTitle = reportTitle;
        this.filters = filters;
        this.bookings = new ArrayList<>();
        this.generationDate = new Date();
    }

    // Getters and Setters
    public String getReportTitle() {
        return reportTitle;
    }

    public List<FlatBooking> getBookings() {
        return bookings;
    }

    public Map<String, Object> getFilters() {
        return filters;
    }

    public Date getGenerationDate() {
        return generationDate;
    }

    // Helper method to add bookings
    public void addBooking(FlatBooking booking) {
        if (!bookings.contains(booking)) {
            bookings.add(booking);
        }
    }

    @Override
    public String toString() {
        return "Report [title=" + reportTitle + ", filters=" + filters + ", bookings=" + bookings.size() + "]";
    }
}