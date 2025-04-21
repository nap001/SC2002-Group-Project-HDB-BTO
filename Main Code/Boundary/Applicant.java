package Boundary;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Entity.ApplicantApplication;
import Entity.Enquiry;
import Interface.IApplicantApplicationControl;
import Interface.IApplicantEnquiryControl;
import Interface.IApplicantProjectControl;
import Interface.IWithdrawalControl;

public class Applicant extends User implements Serializable{
    private ApplicantApplication application = null;
    private List<Enquiry> enquiries = new ArrayList<>(); // applicant's personal list
    private static final long serialVersionUID = 1L;  // Add serialVersionUID for version control

    public Applicant(String NRIC, String password, int age, String maritalStatus, String name) {
        super(NRIC, password, age, maritalStatus, name);
    }

    @Override
    public String getRole() {
        return "Applicant";
    }

    public void viewProjectList(IApplicantProjectControl projectControl) {
        System.out.println("=== Project List ===");
        projectControl.viewVisibleProject();
    }

    public void createApplication(IApplicantApplicationControl applicationControl, IApplicantProjectControl projectControl) {
        applicationControl.processApplication(this, projectControl);
    }
    
    public void viewApplication(IApplicantApplicationControl applicationControl) {
		applicationControl.displayApplicationDetails(this.application);
    }

    public ApplicantApplication getApplication() {
        return application;
    }

    public void setApplication(ApplicantApplication application) {
        this.application = application;
    }
    
    public void withdrawApplication(IWithdrawalControl withdrawalControl) {
        if (this.application == null) {
            System.out.println("You have not applied for any project.");
            return;
        }

        boolean success = withdrawalControl.requestWithdrawal(this, this.application);

        if (success) {
            System.out.println("Withdrawal request submitted successfully for project: " +
                this.application.getProjectName());
        } else {
            System.out.println("You already have a pending withdrawal request.");
        }
    }
    
    public void displayWithdrawalRequest(IWithdrawalControl withdrawalControl) {
    	withdrawalControl.displayWithdrawalDetails(this);
    }
    
    // Delegate enquiry-related methods to EnquiryControl class
    public void submitEnquiry(IApplicantEnquiryControl enquiryControl, IApplicantProjectControl projectControl) {
        enquiryControl.submitEnquiry(this, projectControl);
    }

    public void viewEnquiry(IApplicantEnquiryControl enquiryControl) {
        enquiryControl.viewApplicantEnquiries(this);
    }

    public void editEnquiry(IApplicantEnquiryControl enquiryControl, IApplicantProjectControl projectControl) {
        enquiryControl.editEnquiry(this, projectControl);
    }

    public void deleteEnquiry(IApplicantEnquiryControl enquiryControl, IApplicantProjectControl projectControl) {
        enquiryControl.deleteEnquiry(this, projectControl);
    }

    // === Getters and Setters ===

    public List<Enquiry> getEnquiries() {
        return enquiries;
    }

    public void setEnquiries(List<Enquiry> enquiries) {
        this.enquiries = enquiries;
    }

    public void addEnquiry(Enquiry enquiry) {
        if (enquiry != null) {
            enquiries.add(enquiry);
        }
    }

    public boolean removeEnquiry(Enquiry enquiry) {
        return enquiries.remove(enquiry);
    }
}
