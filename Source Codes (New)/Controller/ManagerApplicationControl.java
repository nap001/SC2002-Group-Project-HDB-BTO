package Controller;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import Boundary.Applicant;
import Boundary.HDBManager;
import Database.ApplicantApplicationList;
import Database.FlatBookingList;
import Database.OfficerRegistrationList;
import Database.ProjectList;
import Database.WithdrawalList;
import ENUM.ApplicationStatus;
import ENUM.FlatType;
import Entity.ApplicantApplication;
import Entity.FlatBooking;
import Entity.OfficerRegistration;
import Entity.Project;
import Entity.Withdrawal;

public class ManagerApplicationControl {
    private ProjectList projectDatabase;
    private OfficerRegistrationList officerRegistrationDatabase;
    private ApplicantApplicationList applicantDatabase;
    private WithdrawalList withdrawalDatabase;
    private FlatBookingList flatBookingDatabase;

    public ManagerApplicationControl(ProjectList projectDatabase,
                                     OfficerRegistrationList officerRegistrationDatabase,
                                     ApplicantApplicationList applicantDatabase,
                                     WithdrawalList withdrawalDatabase,
                                     FlatBookingList flatBookingDatabase) {
        this.projectDatabase = projectDatabase;
        this.officerRegistrationDatabase = officerRegistrationDatabase;
        this.applicantDatabase = applicantDatabase;
        this.withdrawalDatabase = withdrawalDatabase;
        this.flatBookingDatabase = flatBookingDatabase;
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
        }

        return true;
    }

    public boolean approveWithdrawals(HDBManager manager) {
        Project project = manager.getCurrentlyManagedProject();
        if (project == null || !manager.equals(project.getHdbManager())) {
            System.out.println("You are not authorized to approve withdrawals for this project.");
            return false;
        }

        List<Withdrawal> pendingWithdrawals = withdrawalDatabase.getAllWithdrawals().stream()
                .filter(w -> w.getApplication() != null &&
                             w.getApplication().getProjectName().equals(project.getProjectName()) &&
                             w.getStatus() == ApplicationStatus.WITHDRAWNPENDING)
                .collect(Collectors.toList());

        if (pendingWithdrawals.isEmpty()) {
            System.out.println("No pending withdrawals found for this project.");
            return false;
        }

        for (Withdrawal withdrawal : pendingWithdrawals) {
            ApplicantApplication app = withdrawal.getApplication();
            Applicant applicant = withdrawal.getApplicant();


            // Remove flat booking if exists
            FlatBooking booking = flatBookingDatabase.getBookingByApplicant(applicant);
            if (booking != null) {
                // Increment back flat count
                FlatType flatType = app.getFlatType();
                Map<FlatType, Integer> unitMap = project.getUnitCountMap();
                unitMap.put(flatType, unitMap.getOrDefault(flatType, 0) + 1);
                flatBookingDatabase.removeBooking(booking);
            }

            // Remove application and nullify from applicant
            applicantDatabase.removeApplication(app);
            applicant.setApplication(null);

            // Update withdrawal status
            withdrawal.setStatus(ApplicationStatus.SUCCESSFUL);

            System.out.println("Withdrawal approved for applicant: " + applicant.getName());
        }

        return true;
    }


}
