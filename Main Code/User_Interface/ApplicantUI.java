package User_Interface;

import java.util.Scanner;

import Boundary.Applicant;
import Interface.IApplicantApplicationControl;
import Interface.IEnquiryControl;
import Interface.IProjectControl;
import Interface.IWithdrawalControl;

public class ApplicantUI extends BaseUserUI {
    private final Applicant applicant;
    private final IProjectControl projectControl;
    private final IApplicantApplicationControl applicationControl;
    private final IEnquiryControl enquiryControl;
    private final IWithdrawalControl withdrawalControl;

    public ApplicantUI(Applicant applicant,
                       IProjectControl projectControl,
                       IApplicantApplicationControl applicationControl,
                       IEnquiryControl enquiryControl,
                       IWithdrawalControl withdrawalControl) {
        super(applicant);
        this.applicant = applicant;
        this.projectControl = projectControl;
        this.applicationControl = applicationControl;
        this.enquiryControl = enquiryControl;
        this.withdrawalControl = withdrawalControl;
    }

    public boolean run() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== Applicant Menu ===");
            System.out.println("1. View Project List");
            System.out.println("2. Apply for Flat");
            System.out.println("3. View My Application");
            System.out.println("4. Submit Enquiry");
            System.out.println("5. View My Enquiries");
            System.out.println("6. Edit Enquiry");
            System.out.println("7. Delete Enquiry");
            System.out.println("8. Withdraw Application");
            System.out.println("9. View Withdrawal Request");
            System.out.println("0. Logout and Switch User");
            System.out.println("-1. Change Password");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> applicant.viewProjectList(projectControl);
                case 2 -> applicant.createApplication(applicationControl, projectControl);
                case 3 -> applicant.viewApplication(applicationControl);
                case 4 -> applicant.submitEnquiry(enquiryControl, projectControl);
                case 5 -> applicant.viewEnquiry(enquiryControl);
                case 6 -> applicant.editEnquiry(enquiryControl, projectControl);
                case 7 -> applicant.deleteEnquiry(enquiryControl, projectControl);
                case 8 -> applicant.withdrawApplication(withdrawalControl);
                case 9 -> applicant.displayWithdrawalRequest(withdrawalControl);
                case 0 -> {
                    System.out.println("Logging out...");
                    return true;
                }
                case -1 -> changePassword();
                default -> System.out.println("Invalid choice.");
            }

        } while (true);
    }
}
