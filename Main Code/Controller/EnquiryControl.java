package Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import Database.EnquiryList;
import Entity.Applicant;
import Entity.Enquiry;
import Entity.HDBManager;
import Entity.HDBOfficer;
import Entity.Project;
import Entity.User;
import Interface.IApplicantEnquiryControl;
import Interface.IApplicantProjectControl;
import Interface.IEnquiryControl;
import Interface.IProjectQueryControl;

public class EnquiryControl implements IEnquiryControl, IApplicantEnquiryControl {
    private EnquiryList enquiryList;

    // Constructor to initialize with an EnquiryList
    public EnquiryControl(EnquiryList enquiryList) {
        this.enquiryList = enquiryList;
    }

    // Method to create a new enquiry
    public Enquiry createEnquiry(String projectName, String senderName, String message) {
        Enquiry newEnquiry = new Enquiry(projectName, senderName, message);
        enquiryList.addEnquiry(newEnquiry);
        System.out.println("Your enquiry has been submitted successfully.");
        return newEnquiry;
    }
    
	public void viewApplicantEnquiries(Applicant applicant) {
		// TODO Auto-generated method stub
        List<Enquiry> allEnquiries = enquiryList.getAllEnquiries();
        if (allEnquiries.isEmpty()) {
            System.out.println("There are no enquiries in the system.");
            return;
        }

        System.out.println("All Enquiries:");
        for (Enquiry e : allEnquiries) {
        	
        if (e.getSenderName() == applicant.getName()) {
            System.out.printf("Project: %s | From: %s | Message: %s | Reply: %s%n",
                    e.getProjectName(), e.getSenderName(), e.getMessage(),
                    e.getReply() == null ? "Not replied" : e.getReply());
        	}
        }
	}
    
    // Method to view all enquiries in the system (admin-level access)
    public void viewAllEnquiries() {
        List<Enquiry> allEnquiries = enquiryList.getAllEnquiries();
        if (allEnquiries.isEmpty()) {
            System.out.println("There are no enquiries in the system.");
            return;
        }

        System.out.println("All Enquiries:");
        for (Enquiry e : allEnquiries) {
            System.out.printf("Project: %s | From: %s | Message: %s | Reply: %s%n",
                    e.getProjectName(), e.getSenderName(), e.getMessage(),
                    e.getReply() == null ? "Not replied" : e.getReply());
        }
    }
    // Method to submit an enquiry from the applicant
    public void submitEnquiry(Applicant applicant, IApplicantProjectControl projectControl) {
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

        // Create and add enquiry
        Enquiry newEnquiry = createEnquiry(projectName, applicant.getName(), enquiryContent);

        System.out.println("Enquiry submitted successfully.");
    }

    // Method to view all enquiries in the system
    public void viewEnquiries(Applicant applicant) {
        List<Enquiry> applicantEnquiries = applicant.getEnquiries();

        if (applicantEnquiries.isEmpty()) {
            System.out.println("There are no enquiries in the system.");
            return;
        }

        System.out.println("All Enquiries:");
        for (Enquiry e : applicantEnquiries) {
            System.out.printf("Project: %s | From: %s | Message: %s | Reply: %s%n",
                    e.getProjectName(), e.getSenderName(), e.getMessage(),
                    e.getReply() == null ? "Not replied" : e.getReply());
        }
    }
    public void editEnquiry(Applicant applicant, IApplicantProjectControl projectControl) {
        // Get all enquiries and filter based on applicant's name
        List<Enquiry> applicantEnquiries = enquiryList.getAllEnquiries().stream()
            .filter(e -> e.getSenderName().equals(applicant.getName()))  // Only enquiries belonging to the applicant
            .collect(Collectors.toList());

        if (applicantEnquiries.isEmpty()) {
            System.out.println("You have no enquiries to edit.");
            return;
        }

        System.out.println("=== Your Enquiries ===");
        for (int i = 0; i < applicantEnquiries.size(); i++) {
            Enquiry e = applicantEnquiries.get(i);
            System.out.println((i + 1) + ". [ID: " + e.getProjectName() + "] " + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of the enquiry you want to edit: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (choice < 1 || choice > applicantEnquiries.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Enquiry enquiryToEdit = applicantEnquiries.get(choice - 1);
        System.out.print("Enter new content: ");
        String newContent = scanner.nextLine();

        enquiryToEdit.setMessage(newContent);
        enquiryToEdit.setLastEdited(LocalDateTime.now());

        System.out.println("Enquiry updated successfully.");
    }

    public void deleteEnquiry(Applicant applicant, IApplicantProjectControl projectControl) {
        // Get all enquiries and filter based on applicant's name
        List<Enquiry> applicantEnquiries = enquiryList.getAllEnquiries().stream()
            .filter(e -> e.getSenderName().equals(applicant.getName()))  // Only enquiries belonging to the applicant
            .collect(Collectors.toList());

        if (applicantEnquiries.isEmpty()) {
            System.out.println("You have no enquiries to delete.");
            return;
        }

        System.out.println("=== Your Enquiries ===");
        for (int i = 0; i < applicantEnquiries.size(); i++) {
            Enquiry e = applicantEnquiries.get(i);
            System.out.println((i + 1) + ". [ID: " + e.getProjectName() + "] " + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of the enquiry you want to delete: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (choice < 1 || choice > applicantEnquiries.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Enquiry enquiryToDelete = applicantEnquiries.get(choice - 1);
       

        boolean removed =  enquiryList.removeEnquiry(enquiryToDelete);;
        if (removed) {
            System.out.println("Enquiry deleted successfully.");
        } else {
            System.out.println("Enquiry deletion failed.");
        }
    }

    // Method to allow a user (manager/officer) to reply to enquiries related to their project
    public void replyToEnquiries(User user, IProjectQueryControl projectControl) {
        Project relevantProject = null;

        if (user instanceof HDBManager manager) {
            relevantProject = manager.getCurrentlyManagedProject(projectControl);
            if (relevantProject == null) {
                System.out.println("You are not managing any project.");
                return;
            }
        } else if (user instanceof HDBOfficer officer) {
            relevantProject = officer.getAssignedProject(projectControl);
            if (relevantProject == null) {
                System.out.println("You are not assigned to any project.");
                return;
            }
        } else {
            System.out.println("Unauthorized user type.");
            return;
        }

        String projectName = relevantProject.getProjectName();
        List<Enquiry> relevantEnquiries = enquiryList.getAllEnquiries().stream()
                .filter(e -> e.getProjectName().equals(projectName))
                .collect(Collectors.toList());

        if (relevantEnquiries.isEmpty()) {
            System.out.println("There are no enquiries for your project.");
            return;
        }

        System.out.println("Enquiries for your project:");
        for (int i = 0; i < relevantEnquiries.size(); i++) {
            Enquiry e = relevantEnquiries.get(i);
            System.out.printf("%d. From: %s | Message: %s | Reply: %s%n",
                    i + 1, e.getSenderName(), e.getMessage(),
                    e.getReply() == null ? "Not replied" : e.getReply());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of the enquiry to reply to (0 to cancel): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (choice < 1 || choice > relevantEnquiries.size()) {
            System.out.println("Cancelled or invalid choice.");
            return;
        }

        Enquiry selected = relevantEnquiries.get(choice - 1);
        System.out.print("Enter your reply: ");
        String reply = scanner.nextLine();
        selected.setReply(reply);
        System.out.println("Reply sent successfully.");
    }


}

