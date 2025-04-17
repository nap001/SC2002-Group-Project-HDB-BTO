package User_Interface;

import java.util.Scanner;

import Boundary.Applicant;
import Controller.ApplicantApplicationControl;
import Controller.EnquiryControl;
import Controller.ProjectControl;

public class ApplicantUI {
    private final Applicant applicant;
    private final ProjectControl projectControl;
    private final ApplicantApplicationControl applicationControl;
    private final EnquiryControl enquiryControl;

    public ApplicantUI(Applicant applicant, ProjectControl projectControl,
                       ApplicantApplicationControl applicationControl, EnquiryControl enquiryControl) {
        this.applicant = applicant;
        this.projectControl = projectControl;
        this.applicationControl = applicationControl;
        this.enquiryControl = enquiryControl;
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
            System.out.println("0. Logout and Switch User");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    applicant.viewProjectList(projectControl);
                    break;
                case 2:
                    applicant.createApplication(applicationControl, projectControl);
                    break;
                case 3:
                    applicant.viewApplication(applicationControl);
                    break;
                case 4:
                    applicant.submitEnquiry(enquiryControl, projectControl);
                    break;
                case 5:
                    applicant.viewEnquiry(enquiryControl);
                    break;
                case 6:
                    applicant.editEnquiry(enquiryControl, projectControl);
                    break;
                case 7:
                    applicant.deleteEnquiry(enquiryControl, projectControl);
                    break;
                case 0:
                    System.out.println("Logging out...");
                    return true;
                default:
                    System.out.println("Invalid choice.");
            }

        } while (true);
    }

}
