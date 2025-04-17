package Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import Boundary.Applicant;
import Boundary.HDBManager;
import Boundary.HDBOfficer;
import Boundary.User;
import Database.EnquiryList;
import Entity.Enquiry;
import Entity.Project;

public class EnquiryControl {
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
    
    // Method to view all enquiries in the system (admin-level access)
    public void viewEnquiries(List<Enquiry> enquiries) {
        if (enquiries.isEmpty()) {
            System.out.println("There are no enquiries in the system.");
            return;
        }

        System.out.println("All Enquiries:");
        for (Enquiry e : enquiries) {
            System.out.printf("Project: %s | From: %s | Message: %s | Reply: %s%n",
                    e.getProjectName(), e.getSenderName(), e.getMessage(),
                    e.getReply() == null ? "Not replied" : e.getReply());
        }
    }
    
    public boolean editEnquiry(Enquiry enquiry, String newContent, Applicant applicant, ProjectControl projectControl) {
        if (enquiry == null || newContent == null || newContent.trim().isEmpty()) return false;

        enquiry.setMessage(newContent);
        enquiry.setLastEdited(LocalDateTime.now());

        // Update in applicant’s personal list
        for (Enquiry e : applicant.getEnquiries()) {
            if (e == enquiry) {
                e.setMessage(newContent);
                e.setLastEdited(LocalDateTime.now());
                break;
            }
        }

        // Update in project's enquiry list
        Project project = projectControl.getProject(applicant.getApplication().getProjectName());
        for (Enquiry e : project.getEnquiries()) {
            if (e == enquiry) {
                e.setMessage(newContent);
                e.setLastEdited(LocalDateTime.now());
                return true;
            }
        }

        return false;
    }
    
    public boolean deleteEnquiry(Enquiry enquiry, Applicant applicant, ProjectControl projectControl) {
        if (enquiry == null) return false;

        // Remove from applicant's list
        boolean removedFromApplicant = applicant.getEnquiries().removeIf(e -> e == enquiry);

        // Remove from project's list
        Project project = projectControl.getProject(applicant.getApplication().getProjectName());
        boolean removedFromProject = project.getEnquiries().removeIf(e -> e == enquiry);

        return removedFromApplicant && removedFromProject;
    }


    // Method to allow a user (manager/officer) to reply to enquiries related to their project
    public void replyToEnquiries(User user) {
        Project relevantProject = null;

        if (user instanceof HDBManager manager) {
            relevantProject = manager.getCurrentlyManagedProject();
            if (relevantProject == null) {
                System.out.println("You are not managing any project.");
                return;
            }
        } else if (user instanceof HDBOfficer officer) {
            relevantProject = officer.getAssignedProject();
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
