package Controller;

import Application.*;
import Collection.EnquiryList;
import Entities.*;
import java.util.*;

public class EnquiryController {
    private final EnquiryList enquiryList;
    private int enquiryID = 1;

    public EnquiryController(EnquiryList enquiryList) {
        this.enquiryList = enquiryList;
        // Initialize enquiryID from max existing ID if needed
        for (Enquiry e : enquiryList.getEnquiry()) {
            if (e.getEnquiryID() >= enquiryID) {
                enquiryID = e.getEnquiryID() + 1;
            }
        }
    }

    public Enquiry createEnquiry(Applicant applicant, Project project, String enquiryText) {
        Enquiry enquiry = new Enquiry(applicant, project, enquiryText);
        enquiry.setEnquiryID(enquiryID++);
        if (project != null) {
            project.addEnquiry(enquiry);
        }
        enquiryList.addItem(enquiry);
        return enquiry;
    }

    public List<Enquiry> getEnquiriesByProject(Project project) {
        return project != null ? project.getEnquiries() : new ArrayList<>();
    }

    public List<Enquiry> getEnquiriesByApplicant(Applicant applicant) {
        return enquiryList.getEnquiriesByApplicant(applicant);
    }

    public List<Enquiry> getAllEnquiries() {
        return enquiryList.getEnquiry();
    }

    public List<Enquiry> getUnrespondedEnquiries() {
        return enquiryList.getUnrespondedEnquiries();
    }

    public boolean editEnquiry(Enquiry enquiry, String newEnquiry) {
        if (enquiry == null || !enquiryList.getEnquiry().contains(enquiry)) {
            System.out.println("No such enquiry is found");
            return false;
        }
        if (enquiry.hasResponse()) {
            System.out.println("Enquiry has already been responded, unable to edit.");
            return false;
        }
        enquiry.setEnquiryString(newEnquiry);
        return true;
    }

    public boolean deleteEnquiry(Enquiry enquiry) {
        if (enquiry == null || !enquiryList.getEnquiry().contains(enquiry)) {
            System.out.println("No such enquiry is found");
            return false;
        }
        if (enquiry.hasResponse()) {
            System.out.println("Enquiry has already been responded, unable to delete.");
            return false;
        }
        if (enquiry.getProject() != null) {
            enquiry.getProject().removeEnquiry(enquiry);
        }
        enquiryList.removeItem(enquiry);
        return true;
    }

    public boolean respondToEnquiry(Enquiry enquiry, String response, User responder) {
        if (enquiry == null || !enquiryList.getEnquiry().contains(enquiry)) {
            System.out.println("No such enquiry is found, please try another enquiry.");
            return false;
        }
        if (enquiry.hasResponse()) {
            System.out.println("This enquiry has already been responded by " + enquiry.getResponder().getName() + ", please respond to another enquiry instead.");
            return false;
        }
        enquiry.setResponse(response);
        enquiry.setResponder(responder);
        enquiry.setEnquiryDate(new Date());
        return true;
    }
}
