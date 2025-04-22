package boundary;

import java.util.Scanner;

import entity.Applicant;
import interfaces.IApplicantApplicationControl;
import interfaces.IApplicantEnquiryControl;
import interfaces.IApplicantProjectControl;
import interfaces.IWithdrawalControl;

public class ApplicantUI extends BaseUserUI {
    private final Applicant applicant;
    private final IApplicantProjectControl iProjectControl;
    private final IApplicantApplicationControl iApplicationControl;
    private final IApplicantEnquiryControl iEnquiryControl;
    private final IWithdrawalControl iWithdrawalControl;

    public ApplicantUI(Applicant applicant,
                       IApplicantProjectControl iProjectControl,
                       IApplicantApplicationControl iApplicationControl,
                       IApplicantEnquiryControl iEnquiryControl,
                       IWithdrawalControl iWithdrawalControl) {
        super(applicant);
        this.applicant = applicant;
        this.iProjectControl = iProjectControl;
        this.iApplicationControl = iApplicationControl;
        this.iEnquiryControl = iEnquiryControl;
        this.iWithdrawalControl = iWithdrawalControl;
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
                case 1 -> applicant.viewProjectList(iProjectControl);
                case 2 -> applicant.createApplication(iApplicationControl, iProjectControl);
                case 3 -> applicant.viewApplication(iApplicationControl);
                case 4 -> applicant.submitEnquiry(iEnquiryControl, iProjectControl);
                case 5 -> applicant.viewEnquiry(iEnquiryControl);
                case 6 -> applicant.editEnquiry(iEnquiryControl, iProjectControl);
                case 7 -> applicant.deleteEnquiry(iEnquiryControl, iProjectControl);
                case 8 -> applicant.withdrawApplication(iWithdrawalControl);
                case 9 -> applicant.displayWithdrawalRequest(iWithdrawalControl);
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
