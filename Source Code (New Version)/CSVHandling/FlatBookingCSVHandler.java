package CSVHandling;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Application.*;
import Enum.ApplicationStatus;
import Interfaces.CSVHandler;


class FlatBookingCSVHandler implements CSVHandler<FlatBooking> {
    @Override
    public List<FlatBooking> loadFromCSV(String filePath) {
        List<FlatBooking> bookings = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length >= 4) {
                    String applicant = columns[0];
                    String project = columns[1];
                    String flatType = columns[2];
                    String date = columns[3];
                    bookings.add(new FlatBooking(applicant, project, flatType, date));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public void saveToCSV(List<FlatBooking> bookings, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("applicant,project,flatType,date\n");
            for (FlatBooking b : bookings) {
                writer.write(String.format("%s,%s,%s,%s\n", b.getApplicant(), b.getProject(), b.getFlatType(), b.getDate()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}