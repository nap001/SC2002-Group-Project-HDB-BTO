package User_Interface;

import java.util.Scanner;

import Boundary.HDBOfficer;
import Entity.Project;
import Interface.IApplicantApplicationControl;
import Interface.IApplicantEnquiryControl;
import Interface.IApplicantProjectControl;
import Interface.IEnquiryControl;
import Interface.IFlatBookingControl;
import Interface.IOfficerRegistrationControl;
import Interface.IProjectControl;
import Interface.IReceiptGenerator;
import Interface.IWithdrawalControl;

public class OfficerUI extends BaseUserUI {
    private final HDBOfficer officer;
    private final IProjectControl IProjectControl;
    private final IEnquiryControl IEnquiryControl; // Used for officer functions
    private final IApplicantEnquiryControl IApplicantEnquiryControl; // Used for applicant functions
    private final IFlatBookingControl IFlatBookingControl;
    private final IOfficerRegistrationControl IOfficerRegistrationControl;
    private final IReceiptGenerator IReceiptGenerator;
    private final IApplicantApplicationControl IApplicantApplicationControl;
    private final IWithdrawalControl IWithdrawalControl;
    private final IApplicantProjectControl IApplicantProjectControl;

    public OfficerUI(HDBOfficer officer,
                     IProjectControl IProjectControl,
                     IApplicantProjectControl IApplicantProjectControl,
                     IEnquiryControl IEnquiryControl,
                     IApplicantEnquiryControl IApplicantEnquiryControl,
                     IFlatBookingControl IFlatBookingControl,
                     IOfficerRegistrationControl IOfficerRegistrationControl,
                     IReceiptGenerator IReceiptGenerator,
                     IApplicantApplicationControl IApplicantApplicationControl,
                     IWithdrawalControl IWithdrawalControl) {
        super(officer);
        this.officer = officer;
        this.IProjectControl = IProjectControl;
        this.IApplicantProjectControl = IApplicantProjectControl;
        this.IEnquiryControl = IEnquiryControl;
        this.IApplicantEnquiryControl = IApplicantEnquiryControl;
        this.IFlatBookingControl = IFlatBookingControl;
        this.IOfficerRegistrationControl = IOfficerRegistrationControl;
        this.IReceiptGenerator = IReceiptGenerator;
        this.IApplicantApplicationControl = IApplicantApplicationControl;
        this.IWithdrawalControl = IWithdrawalControl;
    }

    public boolean run() {
        int choice;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("\n=== Officer Menu ===");
            System.out.println("1. View All Projects (Officer View)");
            System.out.println("2. View Assigned Project");
            System.out.println("3. Register for a Project");
            System.out.println("4. View All Enquiries (Officer View)");
            System.out.println("5. Reply to Enquiries");
            System.out.println("6. Approve BTO Bookings");
            System.out.println("7. Generate Receipt for Flat Bookings");

            System.out.println("--- Applicant Functionalities ---");
            System.out.println("8. View Project List");
            System.out.println("9. Create Application");
            System.out.println("10. View My Application");
            System.out.println("11. Submit Enquiry");
            System.out.println("12. View My Enquiries");
            System.out.println("13. Edit Enquiry");
            System.out.println("14. Delete Enquiry");
            System.out.println("15. Withdraw Application");
            System.out.println("16. View Withdrawal Request");
            System.out.println("0. Logout and Switch User");
            System.out.println("-1. Change Password");
            System.out.print("Enter your choice: ");

            while (!scanner.hasNextInt()) {
                System.out.print("Please enter a number: ");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> officer.viewAllProject(IProjectControl);
                case 2 -> {
                    Project assigned = officer.getAssignedProject();
                    if (assigned != null) {
                        System.out.println("Assigned Project: " + assigned.getProjectName());
                    } else {
                        System.out.println("No project assigned.");
                    }
                }
                case 3 -> registerForProject();
                case 4 -> officer.viewAllEnquiries(IEnquiryControl); // Officer-only method
                case 5 -> officer.replyToEnquiries(IEnquiryControl, IProjectControl); // Officer-only method
                case 6 -> officer.approveFlatBooking(officer, IFlatBookingControl);
                case 7 -> officer.generateReceipts(IReceiptGenerator);

                // Applicant functionality (use IApplicantEnquiryControl)
                case 8 -> officer.viewProjectList(IApplicantProjectControl);
                case 9 -> officer.createApplication(IApplicantApplicationControl, IApplicantProjectControl);
                case 10 -> officer.viewApplication(IApplicantApplicationControl);
                case 11 -> officer.submitEnquiry(IApplicantEnquiryControl, IApplicantProjectControl);
                case 12 -> officer.viewEnquiry(IApplicantEnquiryControl);
                case 13 -> officer.editEnquiry(IApplicantEnquiryControl, IApplicantProjectControl);
                case 14 -> officer.deleteEnquiry(IApplicantEnquiryControl, IApplicantProjectControl);
                case 15 -> officer.withdrawApplication(IWithdrawalControl);
                case 16 -> officer.displayWithdrawalRequest(IWithdrawalControl);
                case 0 -> {
                    System.out.println("Logging out...");
                    return true;
                }
                case -1 -> changePassword();
                default -> System.out.println("Invalid choice. Please try again.");
            }

        } while (true);
    }

    private void registerForProject() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== Register for a Project ===");
        IProjectControl.viewAllProject();

        System.out.print("Enter the name of the project to register for: ");
        String projectName = scanner.nextLine();

        Project selectedProject = IProjectControl.getProject(projectName);
        if (selectedProject != null) {
            officer.registerforProject(selectedProject, IOfficerRegistrationControl);
        } else {
            System.out.println("Project not found.");
        }
    }
}
