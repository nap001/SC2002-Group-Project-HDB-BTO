package Database;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Entity.Enquiry;

public class EnquiryList {
    private List<Enquiry> enquiryList = new ArrayList<>();

    // Add a new enquiry
    public void addEnquiry(Enquiry enquiry) {
        enquiryList.add(enquiry);
    }

    // Get all enquiries
    public List<Enquiry> getAllEnquiries() {
        return enquiryList;
    }


    // Filter enquiries by project name
    public List<Enquiry> getEnquiriesByProject(String projectName) {
        return enquiryList.stream()
                .filter(e -> e.getProjectName().equalsIgnoreCase(projectName))
                .collect(Collectors.toList());
    }

    // Clear all enquiries (if needed, e.g., for testing)
    public void clearAll() {
        enquiryList.clear();
    }

    // Optional: Get number of enquiries
    public int getEnquiryCount() {
        return enquiryList.size();
    }
}
