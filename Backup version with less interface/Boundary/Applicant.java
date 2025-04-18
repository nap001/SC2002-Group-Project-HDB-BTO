package Boundary;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Controller.ApplicantApplicationControl;
import Controller.EnquiryControl;
import Controller.ProjectControl;
import Controller.WithdrawalControl;
import ENUM.FlatType;
import Entity.ApplicantApplication;
import Entity.Enquiry;
import Entity.Project;
import Entity.Withdrawal;

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

    public void viewProjectList(ProjectControl projectControl) {
        System.out.println("=== Project List ===");
        projectControl.viewVisibleProject();
    }

    public void createApplication(ApplicantApplicationControl applicationControl, ProjectControl projectControl) {
        applicationControl.processApplication(this, projectControl);
    }
    
    public void viewApplication(ApplicantApplicationControl applicantapplicationcontrol) {
		applicantapplicationcontrol.displayApplicationDetails(this.application);
    }

    public ApplicantApplication getApplication() {
        return application;
    }

    public void setApplication(ApplicantApplication application) {
        this.application = application;
    }
    
    public void withdrawApplication(WithdrawalControl withdrawalControl) {
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
    
    public void displayWithdrawalRequest(WithdrawalControl withdrawalControl) {
    	withdrawalControl.displayWithdrawalDetails(this);
    }
    
    // Delegate enquiry-related methods to EnquiryControl class
    public void submitEnquiry(EnquiryControl enquiryControl, ProjectControl projectControl) {
        enquiryControl.submitEnquiry(this, projectControl);
    }

    public void viewEnquiry(EnquiryControl enquiryControl) {
        enquiryControl.viewApplicantEnquiries(this);
    }

    public void editEnquiry(EnquiryControl enquiryControl, ProjectControl projectControl) {
        enquiryControl.editEnquiry(this, projectControl);
    }

    public void deleteEnquiry(EnquiryControl enquiryControl, ProjectControl projectControl) {
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
