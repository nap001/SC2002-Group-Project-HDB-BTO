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

class EnquiryCSVHandler implements CSVHandler<Enquiry> {
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

                    Enquiry enquiry = new Enquiry(enquiryID, projectName, enquiryContent, applicantNric, response, responderNRIC, submissionDate, responseDate);
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
                        enquiry.getEnquiryID(), enquiry.getProjectName(), enquiry.getEnquiryContent(),
                        enquiry.getApplicantNric(), dateFormat.format(enquiry.getSubmissionDate()),
                        enquiry.getResponse(), enquiry.getResponderNRIC(), dateFormat.format(enquiry.getResponseDate()));
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving enquiries to CSV");
            e.printStackTrace();
        }
    }
}