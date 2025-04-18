package Boundaries;

import Application.Application;
import Application.Enquiry;
import Application.Project;
import Controller.ReadCSV;
import Entities.Applicant;
import Entities.HDBOfficer;

public class OfficerInterface {
	public void showOfficerMenu() {
	    System.out.println("Welcome " + currentUser.getName() + " to the Officer's menu, what would you like to do today?");
	    System.out.println("==============================================================================");
	    System.out.println("1. Register to join a project team");
	    System.out.println("2. Flat selection work");
	    System.out.println("3. Generate flat selection receipt");
	    System.out.println("4. View list of available projects (As applicant)");
	    System.out.println("5. Apply for a project (As applicant)");
	    System.out.println("6. View flat application details (As applicant)");
	    System.out.println("7. Request to withdraw application status");
	    System.out.println("8. Create an enquiry");
	    System.out.println("9. Edit an enquiry");
	    System.out.println("10. View enquiry");
	    System.out.println("11. Delete enquiry");
	    System.out.print("Please enter your choice: ");
	    int choice = Integer.parseInt(sc.nextLine());  // Reading integer input from user
	    
	    switch (choice) {
	        case 1:
	            showListOfProjects();
	            System.out.println("Which project would you like to register to join?");
	            String project = sc.nextLine();
	            HDBOfficer officer = (HDBOfficer) currentUser;
	            officer.registerForOfficerProject(project);
	            break;
	        case 2:
	            System.out.println("Which application would you like to select the flat for?\n Please enter the applicant NRIC: ");
	            String applicantNRIC= sc.nextLine();
	            for (Application application1: applications) {
	            	if (application1.getNric().equals(applicantNRIC)) {
	            		String NRIC = application1.getNric();
	            		String flatType = application1.getFlatType();
	            		HDBOfficer officer1 = (HDBOfficer) currentUser;
	            		officer1.flatSelection(NRIC, flatType);
	            	}	            		
	            }
	        case 3:
	            System.out.println("Which application would you like to generate the receipt for?\n Please enter the application ID: ");
	            int receiptApplicationID = Integer.parseInt(sc.nextLine());
	            generateReceipt(receiptApplicationID);
	            break;
	        case 4:
	        	System.out.println("Viewing available projects as an applicant...");
	            showListOfProjects();
	            break;
	        case 5:
	        	showListOfProjects();
	            System.out.println("Which project would you like to apply for? Please enter the project name: ");
	            String projectNameToApply = sc.nextLine();
	            for (Project project1: projects) {
	            	if (project1.getProjectName().equals(projectNameToApply)) {
	            		Applicant applicant = (Applicant) currentUser;
						applicant.applyForProject(currentUser, project1);}
	            }
	            break;
	        case 6:
	        	for (Application application:applications) {
	            	if (application.getNric().equals(currentUser.getNRIC())) {
	            		application.viewApplicationDetails(application);
	            	}else {
	            		System.out.println("No such application has been created");
	            	}
	            }
	        case 7:
	        	for (Application application:applications) {
	            	if (application.getNric().equals(currentUser.getNRIC())) {
	            		application.withdrawRequest(application);
	            		System.out.println("Application has been submitted to be withdrawn, pending approval from HDB Manager.");
	            	}else {
	            		System.out.println("No such application has been created");
	            	}
	            }
	        case 8:
	        	while (true) {
		        	System.out.println("Which project would you like to enquire about: ");
		            String projectName = sc.nextLine();
		            for (Project project: projects) {
		            	if (project.getProjectName().equals(projectName)) {
		            		System.out.println("Enter your enquiry: ");
		    	            String enquiryText = sc.nextLine();
			            	Applicant applicant = (Applicant) currentUser;
		    	            applicant.createEnquiry(project.getProjectName(), enquiryText, currentUser.getNRIC());
		    	            break;
		            	}else {
		            		continue;
		            	}
		            }
	        	}
	        case 9:
	        	System.out.println("Viewing all enquiries...");
	            for (Enquiry enquiry:enquiries) {
	            	Applicant applicant = (Applicant) currentUser;
	            	if (enquiry.getApplicantNric().equals(applicant.getNRIC())) {
	            		enquiry.toString();
	            	}
	            }
            	System.out.println("Enter the enquiry index to edit: ");
    	        int enquiryIndexing = sc.nextInt();
    	        System.out.println("Enter the new enquiry: ");
    	        String enquiryText = sc.nextLine();
    	        ReadCSV readCSV = new ReadCSV();
    	        readCSV.updateEnquiryResponse(enquiryIndexing, enquiryText);
	        case 10:
	        	System.out.println("Viewing all enquiries...");
	            for (Enquiry enquiry:enquiries) {
	            	Applicant applicant = (Applicant) currentUser;
	            	if (enquiry.getApplicantNric().equals(applicant.getNRIC())) {
	            		enquiry.toString();
	            	}
	            }
	            break;
	        case 11:
	        	System.out.println("Viewing all enquiries...");
	            for (Enquiry enquiry:enquiries) {
	            	Applicant applicant = (Applicant) currentUser;
	            	if (enquiry.getApplicantNric().equals(applicant.getNRIC())) {
	            		enquiry.toString();
	            	}
	            }
            	System.out.println("Enter the enquiry index to delete: ");
    	        int enquiryIndexi = sc.nextInt();
    	        ReadCSV readCSV2 = new ReadCSV();
    	        readCSV2.deleteEnquiry(enquiryIndexi);
	            break;

	        default:
	            System.out.println("Invalid choice! Please try again.");
	            break;
	    }
	}
}
