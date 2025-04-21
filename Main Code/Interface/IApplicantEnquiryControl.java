package Interface;

import Entity.Applicant;
import Entity.Enquiry;

public interface IApplicantEnquiryControl {

    // Submit an enquiry from the applicant
    void submitEnquiry(Applicant applicant, IApplicantProjectControl projectControl);

    // View all enquiries for a specific applicant
    void viewApplicantEnquiries(Applicant applicant);

    // Edit an enquiry created by the applicant
    void editEnquiry(Applicant applicant, IApplicantProjectControl projectControl);

    // Delete an enquiry created by the applicant
    void deleteEnquiry(Applicant applicant, IApplicantProjectControl projectControl);
}
