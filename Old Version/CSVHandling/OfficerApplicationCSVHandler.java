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

class OfficerApplicationCSVHandler implements CSVHandler<OfficerApplication> {
    @Override
    public List<OfficerApplication> loadFromCSV(String filePath) {
        List<OfficerApplication> officerApplications = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	        String line;
	        br.readLine(); // Skip the header line
	        
	        while ((line = br.readLine()) != null) {
	            String[] columns = line.split(",");
	            if (columns.length == 4) {  // Assuming 4 columns: Officer Name, Officer NRIC, Project Name, Application Status
	                String officerNRIC = columns[0].trim();
	                String projectName = columns[2].trim();
	                String statusString = columns[3].trim();
                    ApplicationStatus status = null;
                    try {
                        status = ApplicationStatus.valueOf(statusString);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid status value: " + statusString + " for project: " + projectName + ". Defaulting to PENDING.");
                        status = ApplicationStatus.PENDING;
                    }

	                OfficerApplication officerApplication = new OfficerApplication (officerNRIC, projectName, status); // Example, modify if needed
	                officerApplications.add(officerApplication); 
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return officerApplications;
	}

    @Override
    public void saveToCSV(List<OfficerApplication> officerApplications, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Officer NRIC\tProject Name\tStatus\n");
            for (OfficerApplication app : officerApplications) {
                writer.write(String.format("%s\t%s\t%s\n", app.getOfficerNRIC(), app.getProjectName(), app.getApplicationStatus().name()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
