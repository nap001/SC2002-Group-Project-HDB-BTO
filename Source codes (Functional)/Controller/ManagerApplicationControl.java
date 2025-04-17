package Controller;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import Boundary.HDBManager;
import Database.ApplicantApplicationList;
import Database.OfficerRegistrationList;
import Database.ProjectList;
import ENUM.ApplicationStatus;
import ENUM.FlatType;
import Entity.ApplicantApplication;
import Entity.OfficerRegistration;
import Entity.Project;

public class ManagerApplicationControl {
    private ProjectList projectDatabase;
    private OfficerRegistrationList officerRegistrationDatabase;
    private ApplicantApplicationList applicantDatabase;

    public ManagerApplicationControl(ProjectList projectDatabase, OfficerRegistrationList officerRegistrationDatabase, ApplicantApplicationList applicantDatabase) {
        this.projectDatabase = projectDatabase;
        this.officerRegistrationDatabase = officerRegistrationDatabase;
        this.applicantDatabase = applicantDatabase;
    }


    public List<OfficerRegistration> getOfficerRegistrationDatabase() {
        return officerRegistrationDatabase.getAllRegistrations();
    }

    public List<ApplicantApplication> getApplicantDatabase() {
        return applicantDatabase.getAllApplications();
    }

    public boolean manageOfficerRegistration(HDBManager manager, String projectName) {
        // Ensure the manager is managing this project
        if (!manager.isManagingProject(projectName)) {
            System.out.println("You are not authorized to approve registrations for this project.");
            return false;
        }

        // Fetch the project
        Project project = projectDatabase.getProjects(projectName);
        if (project == null) {
            System.out.println("Project not found.");
            return false;
        }

        // Filter relevant registrations for the project
        List<OfficerRegistration> matchingRegistrations = officerRegistrationDatabase.getAllRegistrations().stream()
            .filter(reg -> reg.getProject().equals(project) && reg.isHandling())
            .collect(Collectors.toList());

        if (matchingRegistrations.isEmpty()) {
            System.out.println("No officer registrations found for this project.");
            return false;
        }

        // Display the matching registrations
        System.out.println("=== Officer Registrations for Project: " + projectName + " ===");
        for (int i = 0; i < matchingRegistrations.size(); i++) {
            OfficerRegistration reg = matchingRegistrations.get(i);
            System.out.println("[" + i + "] Officer: " + reg.getOfficer().getName() +
                               " | Applicant: " + reg.isApplicant() +
                               " | Handling: " + reg.isHandling());
        }

        // Ask manager to select a registration
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of the registration to approve/reject: ");
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return false;
        }

        if (choice < 0 || choice >= matchingRegistrations.size()) {
            System.out.println("Invalid registration number.");
            return false;
        }

        OfficerRegistration selectedRegistration = matchingRegistrations.get(choice);

        // Ask for approval or rejection
        System.out.print("Approve this registration? (yes/no): ");
        String decision = scanner.nextLine().trim().toLowerCase();
        boolean approved;
        if (decision.equals("yes") || decision.equals("y")) {
            approved = true;
        } else if (decision.equals("no") || decision.equals("n")) {
            approved = false;
        } else {
            System.out.println("Invalid decision input.");
            return false;
        }

        // Apply decision
        selectedRegistration.setRegistrationStatus(approved);

        if (approved) {
            if (selectedRegistration.isHandling()) {
                project.getHdbOfficers().add(selectedRegistration.getOfficer());
                project.setOfficerSlots(project.getOfficerSlots() - 1);
                selectedRegistration.getOfficer().setAssignedProject(project);
                System.out.println("Officer registration approved and assigned to project.");
            } else {
                System.out.println("Officer registration approved (not assigned as handling).");
            }
        } else {
            System.out.println("Officer registration rejected.");
        }
        officerRegistrationDatabase.removeRegistration(selectedRegistration);

        return true;
    }
    public boolean approveApplicantApplication(HDBManager manager, ProjectControl projectControl) {
        // Fetch all applications in the database
        List<ApplicantApplication> allApplications = applicantDatabase.getAllApplications();

        // Filter applications for the project that the manager is handling
        List<ApplicantApplication> projectApplications = allApplications.stream()
                .filter(app -> app.getProjectName().equals(manager.getCurrentlyManagedProject().getProjectName()))
                .collect(Collectors.toList());

        // Check if the manager is authorized to approve applications for this project
        Project project = manager.getCurrentlyManagedProject();
        if (!manager.equals(project.getHdbManager())) {
            System.out.println("You are not authorized to approve applications for this project.");
            return false;
        }

        // Iterate through the filtered applications
        for (ApplicantApplication application : projectApplications) {
            // Check if the application is still pending
            if (application.getApplicationStatus() != ApplicationStatus.PENDING) {
                System.out.println("This application is not in a pending state.");
                continue;
            }

            // Retrieve the flat type for the application
            FlatType flatType = application.getFlatType();

            // Check the availability of units for the requested flat type
            Map<FlatType, Integer> unitCountMap = project.getUnitCountMap();
            int availableUnits = unitCountMap.getOrDefault(flatType, 0);

           
                // Approve the application if there are available units
                if (availableUnits > 0) {
                    // Approve the application
                    application.setApplicationStatus(ApplicationStatus.SUCCESSFUL);

                    // Add the applicant to the project's list of applicants
                    project.addApplicant(application.getApplicant());

                    System.out.println("Application approved for Applicant: " + application.getName());
                } else {
                    // No available units
                    application.setApplicationStatus(ApplicationStatus.UNSUCCESSFUL);
                    System.out.println("No available units for the requested flat type.");
                }
            
            // Remove the application from the applicant database (if necessary)
        }

        return true;
    }


}
