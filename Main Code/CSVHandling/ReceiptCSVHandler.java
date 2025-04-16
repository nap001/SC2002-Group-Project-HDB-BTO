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
import Enum.FlatType;
import Interfaces.CSVHandler;


class ReceiptCSVHandler implements CSVHandler<Receipt> {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Adjust format if needed

	@Override
    
    public List<Receipt> loadFromCSV(String filePath) {
        List<Receipt> receipts = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	        String line;
	        br.readLine();  // Skip header
	        while ((line = br.readLine()) != null) {
	            String[] columns = line.split(",");
	            if (columns.length == 7) {  // Assuming 7 columns as mentioned
	                String applicantNric = columns[0].trim();
	                String projectName = columns[1].trim();
	                String officerNric = columns[2].trim();
	                FlatType flatType = FlatType.valueOf(columns[3].trim());  // Assuming FlatType is an enum
	                String flatId = columns[4].trim();
	                String receiptDateStr = columns[5].trim();  // Receipt Date as String
	                String receiptContent = columns[6].trim();

	                // Parse receiptDate to Date
	                Date receiptDate = null;
	                try {
	                    receiptDate = dateFormat.parse(receiptDateStr);
	                } catch (Exception e) {
	                    System.out.println("Error parsing receipt date: " + receiptDateStr);
	                    e.printStackTrace();
	                }

	                // Create Receipt object
	                Receipt receipt = new Receipt(applicantNric, projectName, officerNric, flatType, flatId, receiptDate, receiptContent);
	                receipts.add(receipt);
	            }
	        }
	    } catch (IOException e) {
	        System.out.println("Unable to load receipts, please try again. " + e.getMessage());
	    }
        return receipts;
    }

    @Override
    public void saveToCSV(List<Receipt> receipts, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("nric,project,amount,date\n");
            for (Receipt r : receipts) {
                writer.write(String.format("%s,%s,%.2f,%s\n", 	                
                		r.getApplicantNric(),
                		r.getProjectName(),
                		r.getOfficerNric(),
                		r.getFlatType().name(),  // Assuming FlatType is an enum
                		r.getFlatId(),
                		dateFormat.format(r.getReceiptDate()),  // Format receiptDate to string,
                		r.getReceiptContent()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
