package Boundary;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Controller.ApplicantApplicationControl;
import Controller.EnquiryControl;
import Controller.ProjectControl;
import ENUM.ApplicationStatus;
import ENUM.FlatType;
import Entity.ApplicantApplication;
import Entity.Enquiry;
import Entity.Project;

public class Applicant extends User {
	ApplicantApplication application = null;
	private List<Enquiry> enquiries = new ArrayList<>(); // applicant's personal list

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
        // No restriction on flat type for married applicants 21+
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

    public ApplicantApplication getApplication() {
	return application;
}

public void setApplication(ApplicantApplication application) {
	this.application = application;
}

	public void viewApplication(ApplicantApplicationControl applicantapplicationcontrol) {
		applicantapplicationcontrol.displayApplicationDetails(this.application);
    }
//
//    public void requestWithdrawal() {
//        if (appliedProject == null) {
//            System.out.println("No project to be withdrawn");
//            return;
//        }
//        System.out.println("Withdrawing Application: " + appliedProject.getProjectName());
//        appliedProject = null;
//        applicationStatus = ApplicationStatus.NONE;
//    }

    public void submitEnquiry(EnquiryControl enquiryControl, ProjectControl projectControl) {
    	Scanner scanner = new Scanner(System.in);

    	// Ask user for project name
    	System.out.print("Enter the project name to submit enquiry: ");
    	String projectName = scanner.nextLine().trim();

    	// Check if the project exists
    	if (!projectControl.projectExists(projectName)) {
    	    System.out.println("The project '" + projectName + "' does not exist. Cannot submit enquiry.");
    	    return;
    	}

    	// Prompt for enquiry content
    	System.out.println("Enter the enquiry content: ");
    	String enquiryContent = scanner.nextLine();

    	if (enquiryContent == null || enquiryContent.trim().isEmpty()) {
    	    System.out.println("Cannot submit an empty enquiry!");
    	    return;
    	}

    	// Get sender name
    	String senderName = getName();

    	// Create and add enquiry
    	Enquiry newEnquiry = enquiryControl.createEnquiry(projectName, senderName, enquiryContent);
    	Project affectedProject = projectControl.getProject(projectName);
    	affectedProject.addEnquiry(newEnquiry);

    	// Local copy
    	Enquiry localCopy = new Enquiry(projectName, senderName, enquiryContent);
    	enquiries.add(localCopy);

    	System.out.println("Enquiry submitted successfully.");

    }

    public void viewEnquiry(EnquiryControl enquirycontrol) {
        enquirycontrol.viewEnquiries(enquiries);
    }

    public void editEnquiry(EnquiryControl enquiryControl, ProjectControl projectControl) {
        if (enquiries.isEmpty()) {
            System.out.println("You have no enquiries to edit.");
            return;
        }

        System.out.println("=== Your Enquiries ===");
        for (int i = 0; i < enquiries.size(); i++) {
            Enquiry e = enquiries.get(i);
            System.out.println((i + 1) + ". [ID: " + e.getProjectName() + "] " + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of the enquiry you want to edit: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (choice < 1 || choice > enquiries.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Enquiry enquiryToEdit = enquiries.get(choice - 1);
        System.out.print("Enter new content: ");
        String newContent = scanner.nextLine();

        boolean success = enquiryControl.editEnquiry(enquiryToEdit, newContent, this, projectControl);
        if (success) {
            System.out.println("Enquiry updated successfully.");
        } else {
            System.out.println("Enquiry update failed.");
        }
    }


    public void deleteEnquiry(EnquiryControl enquiryControl, ProjectControl projectControl) {
        if (enquiries.isEmpty()) {
            System.out.println("You have no enquiries to delete.");
            return;
        }

        System.out.println("=== Your Enquiries ===");
        for (int i = 0; i < enquiries.size(); i++) {
            Enquiry e = enquiries.get(i);
            System.out.println((i + 1) + ". [ID: " + e.getProjectName() + "] " + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of the enquiry you want to delete: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (choice < 1 || choice > enquiries.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Enquiry enquiryToDelete = enquiries.get(choice - 1);

        boolean success = enquiryControl.deleteEnquiry(enquiryToDelete, this, projectControl);
        if (success) {
            System.out.println("Enquiry deleted successfully.");
        } else {
            System.out.println("Enquiry deletion failed.");
        }
    }


    // === ✅ Getters and Setters ===


    public List<Enquiry> getEnquiries() {
        return enquiries;
    }

    public void setEnquiries(List<Enquiry> enquiries) {
        this.enquiries = enquiries;
    }

    // === ✅ Utility methods for Enquiries ===

    public void addEnquiry(Enquiry enquiry) {
        if (enquiry != null) {
            enquiries.add(enquiry);
        }
    }

    public boolean removeEnquiry(Enquiry enquiry) {
        return enquiries.remove(enquiry);
    }

}
