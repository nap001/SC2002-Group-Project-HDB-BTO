package CSVHandling;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Application.*;
import Entities.Applicant;
import Enum.ApplicationStatus;
import Enum.FlatType;
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

                    // Convert string status to ApplicationStatus enum
                    ApplicationStatus status;
                    try {
                        status = ApplicationStatus.valueOf(statusString);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid status: " + statusString + ". Defaulting to PENDING.");
                        status = ApplicationStatus.PENDING;
                    }

                    // Assuming Applicant and Project are objects you can create/lookup using the nric and projectName
                    Applicant applicant = new Applicant(); // You need to implement applicant retrieval or creation
                    Project project = new Project(); // You need to implement project retrieval or creation
                    FlatType flatTypeEnum = FlatType.valueOf(flatType); // assuming FlatType is an enum

                    // Create and add the application
                    Application application = new Application(applicant, project);
                    application.setFlatType(flatTypeEnum);
                    application.setApplicationStatus(status);
                    applications.add(application);
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
                // Modify according to your implementation for retrieving applicant info and project name
                String applicantNric = app.getApplicant().getNRIC(); // Assuming getNRIC method exists
                String projectName = app.getProject().getProjectName(); // Assuming getName method exists
                String flatType = app.getFlatType().name(); // Assuming FlatType is an enum
                String status = app.ApplicationStatustoString(app.getApplicationStatus()); // Convert enum to String
                
                String line = String.format("\"%s\",\"%s\",\"%s\",\"%s\"",
                        applicantNric, projectName, flatType, status);
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving applications to CSV");
            e.printStackTrace();
        }
    }
}