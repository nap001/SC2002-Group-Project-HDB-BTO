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
import Interfaces.CSVHandler;

public class EnquiryCSVHandler implements CSVHandler<Enquiry> {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public List<Enquiry> loadFromCSV(String filePath) {
        List<Enquiry> enquiries = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length >= 8) {
                    int enquiryID = Integer.parseInt(columns[0]);
                    String projectName = columns[1];
                    String enquiryContent = columns[2];
                    String applicantNric = columns[3];
                    Date submissionDate = dateFormat.parse(columns[4]);
                    String response = columns[5];
                    String responderNRIC = columns[6];
                    Date responseDate = dateFormat.parse(columns[7]);

                    // Retrieve or create User objects for applicant and responder
                    User applicant = new User(); // Assuming you can retrieve the User based on NRIC
                    applicant.setNric(applicantNric); // Set the NRIC to search for the user

                    User responder = new User(); // Similar to applicant
                    responder.setNric(responderNRIC); // Set the NRIC for responder

                    // Retrieve the project based on the project name
                    Project project = new Project();
                    project.setProjectName(projectName); // Assuming you are storing projects by name

                    Enquiry enquiry = new Enquiry(applicant, project, enquiryContent);
                    enquiry.setEnquiryID(enquiryID);
                    enquiry.setEnquiryDate(submissionDate);
                    enquiry.setResponse(response);
                    enquiry.setResponseDate(responseDate);
                    enquiry.setResponder(responder);

                    enquiries.add(enquiry);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return enquiries;
    }

    @Override
    public void saveToCSV(List<Enquiry> enquiries, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("enquiryID,projectName,enquiryContent,applicantNric,submissionDate,response,responderNRIC,responseDate\n");
            for (Enquiry enquiry : enquiries) {
                String line = String.format("\"%d\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"",
                        enquiry.getEnquiryID(),
                        enquiry.getProject().getProjectName(),
                        enquiry.getEnquiryString(),
                        enquiry.getApplicant().getNRIC(),
                        dateFormat.format(enquiry.getEnquiryDate()),
                        enquiry.getResponse(),
                        enquiry.getResponder() != null ? enquiry.getResponder().getNRIC() : "",
                        dateFormat.format(enquiry.getResponseDate()));
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving enquiries to CSV");
            e.printStackTrace();
        }
    }
}
