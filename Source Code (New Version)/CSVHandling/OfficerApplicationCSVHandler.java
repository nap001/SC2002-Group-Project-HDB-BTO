package CSVHandling;
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
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] columns = line.split("\t");
                if (columns.length == 3) {
                    String officerNRIC = columns[0].trim();
                    String projectName = columns[1].trim();
                    ApplicationStatus status = ApplicationStatus.valueOf(columns[2].trim());
                    officerApplications.add(new OfficerApplication(officerNRIC, projectName, status));
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
                writer.write(String.format("%s\t%s\t%s\n", app.getOfficerNRIC(), app.getProjectName(), app.getApplicationStatus()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}