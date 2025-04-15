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
import Entities.User;
import Enum.ApplicationStatus;
import Interfaces.CSVHandler;


class ReceiptCSVHandler implements CSVHandler<Receipt> {
    @Override
    public List<Receipt> loadFromCSV(String filePath) {
        List<Receipt> receipts = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length >= 4) {
                    String nric = columns[0];
                    String project = columns[1];
                    double amount = Double.parseDouble(columns[2]);
                    String date = columns[3];
                    receipts.add(new Receipt(nric, project, amount, date));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return receipts;
    }

    @Override
    public void saveToCSV(List<Receipt> receipts, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("nric,project,amount,date\n");
            for (Receipt r : receipts) {
                writer.write(String.format("%s,%s,%.2f,%s\n", r.getNric(), r.getProject(), r.getAmount(), r.getDate()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}