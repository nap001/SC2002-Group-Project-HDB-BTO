package CSVHandling;

import Application.*;
import Enum.ApplicationStatus;
import Enum.FlatType;
import Interfaces.CSVHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class FlatBookingCSVHandler implements CSVHandler<FlatBooking> {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Adjust format if needed

    @Override
    public List<FlatBooking> loadFromCSV(String filePath) {
        List<FlatBooking> bookings = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();  // Skip header
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length == 7) {  // Assuming 7 columns as mentioned
                    String nric = columns[0].trim();
                    String projectName = columns[1].trim();
                    FlatType flatType = FlatType.valueOf(columns[2].trim());  // Assuming FlatType is an enum
                    String flatId = columns[3].trim();
                    String bookingDateStr = columns[4].trim();  // Booking Date as String
                    ApplicationStatus bookingStatus = ApplicationStatus.valueOf(columns[5].trim());  // Assuming ApplicationStatus is an enum
                    String rejectionReason = columns[6].trim();

                    // Parse bookingDate to Date
                    Date bookingDate = null;
                    try {
                        bookingDate = dateFormat.parse(bookingDateStr);
                    } catch (Exception e) {
                        System.out.println("Error parsing booking date: " + bookingDateStr);
                        e.printStackTrace();
                    }

                    // Create FlatBooking object using constructor
                    FlatBooking booking = new FlatBooking(nric, projectName, flatType, flatId, bookingDate, bookingStatus, rejectionReason);
                    bookings.add(booking);
                }
            }
        } catch (IOException e) {
            System.out.println("Unable to load flat bookings, please try again. " + e.getMessage());
        }
        return bookings;
    }

    @Override
    public void saveToCSV(List<FlatBooking> bookings, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write the header
            writer.write("nric,projectName,flatType,flatId,bookingDate,bookingStatus,rejectionReason\n");

            // Write each booking's details to the CSV
            for (FlatBooking b : bookings) {
                writer.write(String.format("%s,%s,%s,%s,%s,%s,%s\n",
                        b.getApplicant().getNRIC(),
                        b.getProject().getProjectName(),
                        b.getFlatType().name(),  // Assuming FlatType is an enum
                        b.getFlatID(),
                        dateFormat.format(b.getBookingDate()),
                        b.getBookingStatus().name(),  // Assuming ApplicationStatus is an enum
                        b.getMessage() != null ? b.getMessage() : ""));  // Assuming rejectionReason is stored in 'message'
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
