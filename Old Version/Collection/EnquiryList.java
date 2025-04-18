package Collection;

import Application.*;
import Entities.Applicant;
import Interfaces.CSVHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EnquiryList {
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
            if (enquiry.getProject().getProjectName().equalsIgnoreCase(name)) {
                return enquiry;
            }
        }
        return null;
    }

    public List<Enquiry> getEnquiriesByApplicant(Applicant applicant) {
        if (applicant == null) return new ArrayList<>();
        return enquiries.stream()
                .filter(e -> e.getApplicant().getNRIC().equals(applicant.getNRIC()))
                .collect(Collectors.toList());
    }

    public List<Enquiry> getUnrespondedEnquiries() {
        return enquiries.stream()
                .filter(e -> !e.hasResponse())
                .collect(Collectors.toList());
    }
}
