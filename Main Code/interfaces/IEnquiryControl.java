package interfaces;

import entity.Applicant;
import entity.Enquiry;
import entity.User;

public interface IEnquiryControl {

    // Method to create a new enquiry
    Enquiry createEnquiry(String projectName, String senderName, String message);

    // Method to view all enquiries for a specific applicant
    void viewApplicantEnquiries(Applicant applicant);

    // Method to view all enquiries in the system (admin-level access)
    void viewAllEnquiries();

    // Method to submit an enquiry from the applicant
    void submitEnquiry(Applicant applicant, IApplicantProjectControl projectControl);

    // Method to view all enquiries related to a specific applicant
    void viewEnquiries(Applicant applicant);

    // Method to edit an enquiry created by the applicant
    void editEnquiry(Applicant applicant, IApplicantProjectControl projectControl);

    // Method to delete an enquiry created by the applicant
    void deleteEnquiry(Applicant applicant, IApplicantProjectControl projectControl);

    // Method to allow a user (manager/officer) to reply to enquiries related to their project
    void replyToEnquiries(User use, IProjectQueryControl projectControl);
}
