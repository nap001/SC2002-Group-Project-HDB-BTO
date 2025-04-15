package CSVHandling;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Application.*;
import Enum.ApplicationStatus;
import Interfaces.CSVHandler;

class ApplicationCSVHandler implements CSVHandler<Application> {
    @Override
    public List<Application> loadFromCSV(String filePath) {
        List<Application> applications = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length == 4) {
                    String nric = columns[0];
                    String projectName = columns[1];
                    String flatType = columns[2];
                    String statusString = columns[3].trim();

                    ApplicationStatus status;
                    try {
                        status = ApplicationStatus.valueOf(statusString);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid status: " + statusString + ". Defaulting to PENDING.");
                        status = ApplicationStatus.PENDING;
                    }

                    applications.add(new Application());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return applications;
    }

    @Override
    public void saveToCSV(List<Application> applications, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("nric,projectName,flatType,status\n");
            for (Application app : applications) {
                String line = String.format("\"%s\",\"%s\",\"%s\",\"%s\"",
                        app.getNric(), app.getProjectName(), app.getFlatType(), app.getApplicationStatus());
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving applications to CSV");
            e.printStackTrace();
        }
    }
}