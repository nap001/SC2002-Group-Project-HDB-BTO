package User_Interface;

import java.util.Scanner;

import Boundary.Applicant;
import Controller.ApplicantApplicationControl;
import Controller.EnquiryControl;
import Controller.ProjectControl;
import Controller.WithdrawalControl;

//Add WithdrawalControl to the constructor and field
public class ApplicantUI extends BaseUserUI{
 private final Applicant applicant;
 private final ProjectControl projectControl;
 private final ApplicantApplicationControl applicationControl;
 private final EnquiryControl enquiryControl;
 private final WithdrawalControl withdrawalControl; // ✅ Added

 public ApplicantUI(Applicant applicant, ProjectControl projectControl,
                    ApplicantApplicationControl applicationControl, EnquiryControl enquiryControl,
                    WithdrawalControl withdrawalControl) { // ✅ Constructor updated
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
      // Inside the do-while loop in ApplicantUI.run()
         System.out.println("8. Withdraw Application"); // already present
         System.out.println("9. View Withdrawal Request"); // ✅ NEW
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
          // Inside the switch block
             case 8 -> applicant.withdrawApplication(withdrawalControl); // already present
             case 9 -> applicant.displayWithdrawalRequest(withdrawalControl); // ✅ NEW
             
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
