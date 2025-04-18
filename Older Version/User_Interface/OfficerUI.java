package User_Interface;

import java.util.Scanner;

import Boundary.HDBOfficer;
import Controller.EnquiryControl;
import Controller.FlatBookingControl;
import Controller.OfficerRegistrationControl;
import Controller.ProjectControl;
import Controller.ReceiptGenerator;
import Entity.Project;

public class OfficerUI {
    private final HDBOfficer officer;
    private final ProjectControl projectControl;
    private final EnquiryControl enquiryControl;
    private final FlatBookingControl flatBookingControl;
    private final OfficerRegistrationControl officerRegistrationControl;
    private final ReceiptGenerator receiptGenerator;

    public OfficerUI(HDBOfficer officer, ProjectControl projectControl,
                     EnquiryControl enquiryControl, FlatBookingControl flatBookingControl,
                     OfficerRegistrationControl officerRegistrationControl,
                     ReceiptGenerator receiptGenerator) {
        this.officer = officer;
        this.projectControl = projectControl;
        this.enquiryControl = enquiryControl;
        this.flatBookingControl = flatBookingControl;
        this.officerRegistrationControl = officerRegistrationControl;
        this.receiptGenerator = receiptGenerator;
    }

    public boolean run() {
        int choice;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("\n=== Officer Menu ===");
            System.out.println("1. View All Projects");
            System.out.println("2. View Assigned Project");
            System.out.println("3. Register for a Project");
            System.out.println("4. View All Enquiries");
            System.out.println("5. Reply to Enquiries");
            System.out.println("6. Approve BTO Bookings");
            System.out.println("7. Generate Receipt for Flat Bookings");
            System.out.println("0. Logout and Switch User");
            System.out.print("Enter your choice: ");

            while (!scanner.hasNextInt()) {
                System.out.print("Please enter a number: ");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    officer.viewAllProject(projectControl);
                    break;
                case 2:
                    Project assigned = officer.getAssignedProject();
                    if (assigned != null) {
                        System.out.println("Assigned Project: " + assigned.getProjectName());
                    } else {
                        System.out.println("No project assigned.");
                    }
                    break;
                case 3:
                    registerForProject();
                    break;
                case 4:
                    officer.viewAllEnquiries(enquiryControl);
                    break;
                case 5:
                    officer.replyToEnquiries(enquiryControl);
                    break;
                case 6:
                    officer.approveFlatBooking(officer, flatBookingControl);
                    break;
                case 7:
                    officer.generateReceipts(receiptGenerator);
                    break;
                case 0:
                    System.out.println("Logging out...");
                    return true;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (true);
    }

    private void registerForProject() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== Register for a Project ===");
        projectControl.viewAllProject();

        System.out.print("Enter the name of the project to register for: ");
        String projectName = scanner.nextLine();

        Project selectedProject = projectControl.getProject(projectName);
        if (selectedProject != null) {
            System.out.print("Register as Applicant? (true/false): ");
            boolean isApplicant = Boolean.parseBoolean(scanner.nextLine());

            System.out.print("Register as Handling Officer? (true/false): ");
            boolean isHandling = Boolean.parseBoolean(scanner.nextLine());

            officer.registerforProject(selectedProject, isApplicant, isHandling, officerRegistrationControl);
        } else {
            System.out.println("Project not found.");
        }
    }
}
