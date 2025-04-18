package Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import Boundary.Applicant;
import Boundary.HDBOfficer;
import Database.ApplicantApplicationList;
import ENUM.ApplicationStatus;
import ENUM.FlatType;
import Entity.ApplicantApplication;
import Entity.Project;
import Interface.IApplicantApplicationControl;
import Interface.IApplicantProjectControl;
import Interface.IProjectControl;

public class ApplicantApplicationControl implements IApplicantApplicationControl {
    private ApplicantApplicationList applicationList;

    public ApplicantApplicationControl(ApplicantApplicationList applicationList) {
        this.applicationList = applicationList;
    }

    @Override
    public void addApplication(ApplicantApplication application) {
        applicationList.addApplication(application);
    }

    @Override
    public boolean hasApplication(String nric, String projectName) {
        return applicationList.getAllApplications().stream()
            .anyMatch(app -> app.getNric().equals(nric) && app.getProjectName().equals(projectName));
    }
    
    @Override
    public void displayApplicationDetails(ApplicantApplication application) {
        if (application == null) {
            System.out.println("No application found.");
            return;
        }

        System.out.println("=== Applicant Application Details ===");
        System.out.println("Name: " + application.getName());
        System.out.println("NRIC: " + application.getNric());
        System.out.println("Marital Status: " + application.getMaritalStatus());
        System.out.println("Project Name: " + application.getProjectName());
        System.out.println("Flat Type: " + application.getFlatType());
        System.out.println("Application Status: " + application.getApplicationStatus());
    }

    @Override
    public void processApplication(Applicant applicant, IApplicantProjectControl projectControl) {
        Scanner scanner = new Scanner(System.in);

        // Check if existing application is still active
        ApplicantApplication existingApp = applicant.getApplication();
        if (existingApp != null && existingApp.getApplicationStatus() != ApplicationStatus.UNSUCCESSFUL) {
            System.out.println("You already have an active application or a successful one.");
            return;
        }

        // Get and display all visible projects
        List<Project> visibleProjects = projectControl.getVisibleProjects();
        if (visibleProjects.isEmpty()) {
            System.out.println("There are no visible projects available for application.");
            return;
        }

        System.out.println("Available Projects:");
        for (int i = 0; i < visibleProjects.size(); i++) {
            Project p = visibleProjects.get(i);
            System.out.printf("%d. %s (Open: %s, Close: %s)%n", 
                i + 1, p.getProjectName(), p.getApplicationOpenDate(), p.getApplicationCloseDate());
        }

        System.out.print("Enter the number of the project you want to apply for: ");
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return;
        }

        if (choice < 1 || choice > visibleProjects.size()) {
            System.out.println("Invalid project number selected.");
            return;
        }

        Project selectedProject = visibleProjects.get(choice - 1);
        LocalDate now = LocalDate.now();
        if (now.isBefore(selectedProject.getApplicationOpenDate()) || now.isAfter(selectedProject.getApplicationCloseDate())) {
            System.out.println("The application period for this project is not currently open.");
            return;
        }

     // 💡 Officer Restriction
        if (applicant instanceof HDBOfficer) {
            HDBOfficer officer = (HDBOfficer) applicant;
            Project assignedProject = officer.getAssignedProject();
            if (assignedProject != null && assignedProject.getProjectName().equals(selectedProject.getProjectName())) {
                System.out.println("Officers cannot apply as an applicant for their assigned project.");
                return;
            }
        }
        
        String projectName = selectedProject.getProjectName();

        if (hasApplication(applicant.getNRIC(), projectName)) {
            System.out.println("You have already applied for this project.");
            return;
        }

        // Select flat type
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

        String maritalStatus = applicant.getMaritalStatus().toLowerCase();
        int age = applicant.getAge();

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

        // Create and assign the application
        ApplicantApplication application = new ApplicantApplication(applicant, projectName, selectedType);
        addApplication(application);
        applicant.setApplication(application);
        System.out.println("Your application for project '" + projectName + "' has been successfully submitted.");
    }
}
