package Boundary;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Controller.ApplicantApplicationControl;
import Controller.EnquiryControl;
import Controller.ProjectControl;
import ENUM.FlatType;
import Entity.ApplicantApplication;
import Entity.Enquiry;
import Entity.Project;

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
        Scanner scanner = new Scanner(System.in);

        // Prompt for project name
        System.out.print("Enter the project name you want to apply for: ");
        String projectName = scanner.nextLine();

        // Check if project exists
        if (!projectControl.projectExists(projectName)) {
            System.out.println("The project '" + projectName + "' does not exist.");
            return;
        }

        // Check for existing application
        if (applicationControl.hasApplication(getNRIC(), projectName)) {
            System.out.println("You have already applied for this project.");
            return;
        }

        System.out.println("Select flat type: ");
        for (FlatType type : FlatType.values()) {
            System.out.println("- " + type.name());
        }

        System.out.print("Enter flat type: ");
        String flatTypeInput = scanner.nextLine().toUpperCase();
        FlatType selectedType;

        try {
            selectedType = FlatType.valueOf(flatTypeInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid flat type.");
            return;
        }

        // === Validate user conditions ===
        String maritalStatus = this.getMaritalStatus().toLowerCase(); // assume getMaritalStatus returns "Single" or "Married"
        int age = this.getAge(); // assume User class has getAge()

        boolean isSingle = maritalStatus.equals("single");
        boolean isMarried = maritalStatus.equals("married");

        if (isSingle) {
            if (age < 35) {
                System.out.println("Singles must be at least 35 years old to apply.");
                return;
            }
            if (selectedType != FlatType.TWO_ROOM) {
                System.out.println("Singles aged 35 and above can only apply for 2-Room flats.");
                return;
            }
        } else if (isMarried) {
            if (age < 21) {
                System.out.println("Married applicants must be at least 21 years old to apply.");
                return;
            }
        } else {
            System.out.println("Invalid marital status.");
            return;
        }

        // Flat type selection is valid
        System.out.println("You have selected: " + selectedType);

        // Create and add application
        ApplicantApplication application = new ApplicantApplication(this, projectName, selectedType);
        applicationControl.addApplication(application);
        this.setApplication(application);
        System.out.println("Your application for project '" + projectName + "' has been successfully submitted.");
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
