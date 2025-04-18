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

class NotificationCSVHandler implements CSVHandler<Notification> {
    @Override
    public List<Notification> loadFromCSV(String filePath) {
        List<Notification> notifications = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	        String line;
	        br.readLine(); // Skip the header
	        while ((line = br.readLine()) != null) {
	            String[] columns = line.split(",");
	            if (columns.length == 2) {  // NRIC of User and Notification
	                String nric = columns[0].trim();
	                String notification = columns[1].trim();
	                
	                notifications.add(new Notification(nric, notification));
	            }
	        }
	    } catch (IOException e) {
	        System.out.println("Unable to load notifications, please try again. " + e.getMessage());
	    }
	    return notifications;
	}

    @Override
    public void saveToCSV(List<Notification> notifications, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("recipient,message,date\n");
            for (Notification n : notifications) {
                writer.write(String.format("%s,%s,%s\n", 	                
                		n.getNric(),
    	                n.getMessage()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
