package database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import entity.Enquiry;

public class EnquiryList implements Serializable{
    private List<Enquiry> enquiryList = new ArrayList<>();
    private static final long serialVersionUID = 1L;  // Add serialVersionUID for version control

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
    // Remove an enquiry from the list
    public boolean removeEnquiry(Enquiry enquiry) {
        return enquiryList.remove(enquiry); // Returns true if the enquiry was removed, false otherwise
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
