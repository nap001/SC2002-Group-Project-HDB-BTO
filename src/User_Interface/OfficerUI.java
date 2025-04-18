package User_Interface;

import java.util.Scanner;

import Boundary.HDBOfficer;
import Controller.ApplicantApplicationControl;
import Controller.EnquiryControl;
import Controller.FlatBookingControl;
import Controller.OfficerRegistrationControl;
import Controller.ProjectControl;
import Controller.ReceiptGenerator;
import Entity.Project;
import Controller.WithdrawalControl;

//Add WithdrawalControl
public class OfficerUI extends BaseUserUI{
 private final HDBOfficer officer;
 private final ProjectControl projectControl;
 private final EnquiryControl enquiryControl;
 private final FlatBookingControl flatBookingControl;
 private final OfficerRegistrationControl officerRegistrationControl;
 private final ReceiptGenerator receiptGenerator;
 private final ApplicantApplicationControl applicantApplicationControl;
 private final WithdrawalControl withdrawalControl; // ✅ Added

 public OfficerUI(HDBOfficer officer, ProjectControl projectControl,
                  EnquiryControl enquiryControl, FlatBookingControl flatBookingControl,
                  OfficerRegistrationControl officerRegistrationControl,
                  ReceiptGenerator receiptGenerator,
                  ApplicantApplicationControl applicantApplicationControl,
                  WithdrawalControl withdrawalControl) { // ✅ Updated constructor
	 super(officer);
     this.officer = officer;
     this.projectControl = projectControl;
     this.enquiryControl = enquiryControl;
     this.flatBookingControl = flatBookingControl;
     this.officerRegistrationControl = officerRegistrationControl;
     this.receiptGenerator = receiptGenerator;
     this.applicantApplicationControl = applicantApplicationControl;
     this.withdrawalControl = withdrawalControl;
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
         System.out.println("15. Withdraw Application"); // already present
         System.out.println("16. View Withdrawal Request"); // ✅ NEW
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
             case 1 -> officer.viewAllProject(projectControl);
             case 2 -> {
                 Project assigned = officer.getAssignedProject();
                 if (assigned != null) {
                     System.out.println("Assigned Project: " + assigned.getProjectName());
                 } else {
                     System.out.println("No project assigned.");
                 }
             }
             case 3 -> registerForProject();
             case 4 -> officer.viewAllEnquiries(enquiryControl);
             case 5 -> officer.replyToEnquiries(enquiryControl);
             case 6 -> officer.approveFlatBooking(officer, flatBookingControl);
             case 7 -> officer.generateReceipts(receiptGenerator);

             // Applicant inherited methods
             case 8 -> officer.viewProjectList(projectControl);
             case 9 -> officer.createApplication(applicantApplicationControl, projectControl);
             case 10 -> officer.viewApplication(applicantApplicationControl);
             case 11 -> officer.submitEnquiry(enquiryControl, projectControl);
             case 12 -> officer.viewEnquiry(enquiryControl);
             case 13 -> officer.editEnquiry(enquiryControl, projectControl);
             case 14 -> officer.deleteEnquiry(enquiryControl, projectControl);
             case 15 -> officer.withdrawApplication(withdrawalControl); // already present
             case 16 -> officer.displayWithdrawalRequest(withdrawalControl); // ✅ NEW
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
     projectControl.viewAllProject();

     System.out.print("Enter the name of the project to register for: ");
     String projectName = scanner.nextLine();

     Project selectedProject = projectControl.getProject(projectName);
     if (selectedProject != null) {
         officer.registerforProject(selectedProject, officerRegistrationControl);
     } else {
         System.out.println("Project not found.");
     }
 }
}

