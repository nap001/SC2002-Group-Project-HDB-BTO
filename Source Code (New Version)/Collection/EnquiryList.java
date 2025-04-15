package Collection;

import java.util.ArrayList;
import java.util.List;

import Application.*;
import Interfaces.CSVHandler; // Assuming your interface is under CSV package

public class EnquiryList{
    private List<Enquiry> enquiries;
    private final String csvPath;
    private final CSVHandler<Enquiry> csvHandler;

    public EnquiryList(String csvPath, CSVHandler<Enquiry> csvHandler) {
        this.csvPath = csvPath;
        this.csvHandler = csvHandler;
        this.enquiries = csvHandler.loadFromCSV(csvPath);
    }

    public List<Enquiry> getEnquiry() {
        return enquiries;
    }

    public void addItem(Enquiry enquiry) {
        enquiries.add(enquiry);
    }

    public void removeItem(Enquiry enquiry) {
        enquiries.remove(enquiry);
    }

    
    public void saveItems() {
        csvHandler.saveToCSV(enquiries, csvPath);
    }

    public Enquiry findByProjectName(String name) {
        for (Enquiry enquiry : enquiries) {
            if (enquiry.getProjectName().equalsIgnoreCase(name)) {
                return enquiry;
            }
        }
        return null;
    }
}