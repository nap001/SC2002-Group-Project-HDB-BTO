package Boundaries;

import java.util.List;

import Application.Application;
import Application.Enquiry;
import Application.OfficerApplication;
import Application.Project;
import Application.Report;
import Controller.ReadCSV;
import Entities.Applicant;

public class ManagerInterface {
	public void showManagerMenu() {
	    System.out.println("Welcome " + currentUser.getName() + " to the Manager's menu, what would you like to do today?");
	    System.out.println("==============================================================================");
	    System.out.println("1. Create a new BTO project");
	    System.out.println("2. Edit an existing BTO project");
	    System.out.println("3. Delete a BTO project");
	    System.out.println("4. Toggle project visibility");
	    System.out.println("5. View all projects");
	    System.out.println("6. Approve/Reject HDB Officer Registrations");
	    System.out.println("7. Approve/Reject Applicant Applications");
	    System.out.println("8. Approve/Reject Applicant Withdrawals");
	    System.out.println("9. Generate a report");
	    System.out.println("10. View all enquiries");
	    System.out.print("Please enter your choice: ");
	    int choice = Integer.parseInt(sc.nextLine());
	    
	    switch (choice) {
	        case 1:
	            // Get the details of the new BTO
	            System.out.println("Creating a new BTO project...");
	            System.out.print("Enter Project Name: ");
	            String projectName = sc.nextLine();
	            System.out.print("Enter Neighborhood: ");
	            String neighborhood = sc.nextLine();
	            System.out.print("Enter Region: ");
	            String region = sc.nextLine();
	            System.out.print("Enter Number of 2-Room Flats: ");
	            int numUnitsType1 = Integer.parseInt(sc.nextLine());
	            System.out.print("Enter Selling Price for 2-Room Flats: ");
	            int sellingPriceType1 = Integer.parseInt(sc.nextLine());
	            System.out.print("Enter Number of 3-Room Flats: ");
	            int numUnitsType2 = Integer.parseInt(sc.nextLine());
	            System.out.print("Enter Selling Price for 3-Room Flats: ");
	            int sellingPriceType2 = Integer.parseInt(sc.nextLine());
	            System.out.print("Enter Application Opening Date (YYYY-MM-DD): ");
	            String openingDate = sc.nextLine();
	            System.out.print("Enter Application Closing Date (YYYY-MM-DD): ");
	            String closingDate = sc.nextLine();
	            System.out.print("Enter Your Name As Manager: ");
	            String manager = sc.nextLine();
	            System.out.print("Enter Available Officer Slots: ");
	            int officerSlot = Integer.parseInt(sc.nextLine());
	            System.out.println("Enter The Officer Registered For This Project, (Seperated by \",\": ");
	            String officersString = sc.nextLine();
	            List<String> officerList = List.of(officersString.split(","));
	            System.out.print("Enter The Visibility Of This Project (true/false): ");
	            String visbilityString = sc.nextLine();
	            boolean visbility = Boolean.parseBoolean(visbilityString); 
	           
	            //Update into CSV
	            Project project = new Project(projectName, neighborhood, region, numUnitsType1, sellingPriceType1, numUnitsType2, sellingPriceType2, openingDate, closingDate, manager, officerSlot, officerList, visbility);
	            projects.add(project);
	            break;
	            
	        case 2:
	            // Edit an existing BTO project
	            System.out.println("Editing an existing BTO project...");
	            System.out.println("Select a project to edit:");
	            ReadCSV readCSV1 = new ReadCSV();
	            List<Project> projects1 = readCSV1.loadProjectsFromCSV();
	            for (Project project1:projects1) {
	            	project1.toString();}
	            System.out.print("Enter the name of the project you want to edit: ");
	            String projectNameToEdit = sc.nextLine();
	            Project projectToEdit = ((Project) projects1).findProjectByName(projectNameToEdit);
	            if (project != null) {
	                System.out.print("Enter new Neighborhood (Current: " + project.getNeighborhood() + "): ");
	                String newNeighborhood = sc.nextLine();
	                System.out.print("Enter new Region (Current: " + project.getRegion() + "): ");
	                String newRegion = sc.nextLine();
	                System.out.print("Enter new Number of 2-Room Flats (Current: " + project.getNumUnitsType1() + "): ");
	                int newNumUnitsType1 = Integer.parseInt(sc.nextLine());
	                System.out.print("Enter new Selling Price for 2-Room Flats (Current: " + project.getSellingPriceType1() + "): ");
	                int newSellingPriceType1 = Integer.parseInt(sc.nextLine());
	                System.out.print("Enter new Number of 3-Room Flats (Current: " + project.getNumUnitsType2() + "): ");
	                int newNumUnitsType2 = Integer.parseInt(sc.nextLine());
	                System.out.print("Enter new Selling Price for 3-Room Flats (Current: " + project.getSellingPriceType2() + "): ");
	                int newSellingPriceType2 = Integer.parseInt(sc.nextLine());
	                System.out.print("Enter new Application Opening Date (Current: " + project.getOpeningDate() + "): ");
	                String newOpeningDate = sc.nextLine();
	                System.out.print("Enter new Application Closing Date (Current: " + project.getClosingDate() + "): ");
	                String newClosingDate = sc.nextLine();
	                System.out.print("Enter Your Name As Manager: ");
		            String newmanager = sc.nextLine();
		            System.out.print("Enter Available Officer Slots: ");
		            int newofficerSlot = Integer.parseInt(sc.nextLine());
		            System.out.println("Enter The Officer Registered For This Project, (Seperated by \",\": )");
		            String newofficersString = sc.nextLine();
		            List<String> newofficerList = List.of(officersString.split(","));
		            System.out.println("Enter The Visibility Of This Project (true/false): ");
		            boolean visibility = sc.nextLine() != null;
	                projects1.remove(projectToEdit);
	                readCSV1.addProjectToCSV(projectNameToEdit, newNeighborhood, newRegion, newNumUnitsType1, newSellingPriceType1, newNumUnitsType2, newSellingPriceType2, newOpeningDate, newClosingDate, newmanager, newofficerSlot, newofficerList, visibility);
	            } else {
	                System.out.println("Project not found.");
	            }
	            break;
	        
	        case 3:
	            // Delete a BTO project
	            System.out.println("Deleting a BTO project...");
	            ReadCSV readCSV11 = new ReadCSV();
	            List<Project> projects2 = readCSV11.loadProjectsFromCSV();
	            for (Project project1:projects2) {
	            	project1.toString();}
	            System.out.print("Enter the name of the project you want to delete: ");
	            String projectNameToDelete = sc.nextLine();
	            Project projectToDelete = ((Project) projects2).findProjectByName(projectNameToDelete);
	            if (projectToDelete != null) {
	                projects2.remove(projectToDelete);
	                System.out.println("Project has been deleted.");
	            } else {
	                System.out.println("Project not found.");
	            }
	            break;
	        
	        case 4:
	            // Toggle project visibility
	            System.out.println("Toggling project visibility...");
	            List<Project> projects21 = readCSV11.loadProjectsFromCSV();
	            for (Project project1:projects21) {
	            	project1.toString();}
	            System.out.print("Enter the name of the project to toggle visibility: ");
	            String projectNameToToggle = sc.nextLine();
	            Project projectToToggle = ((Project) projects21).findProjectByName(projectNameToToggle);
	            if (projectToToggle != null) {
	                System.out.print("Enter visibility status (true for visible, false for hidden): ");
	                boolean visibility = Boolean.parseBoolean(sc.nextLine());
	                projectToToggle.setVisibility(visibility);
	            } else {
	                System.out.println("Project not found.");
	            }
	            break;
	        
	        case 5:
	            System.out.println("Viewing all projects...");
	            List<Project> projects11 = readCSV11.loadProjectsFromCSV();
	            for (Project project1:projects11) {
	            	if (project1.getVisibility()) {
	            		project1.toString();}}
	            break;
	        
	        case 6:
	        	System.out.println("Viewing all Officer Applications...");
		        List<OfficerApplication> OApp = readCSV11.loadOfficerApplicationsFromCSV();
		        for (OfficerApplication app :OApp) {
		            app.toString();}
		        System.out.println("Which Application would you like to handle, please enter the Officer's Name");
		        String OfficerName = sc.nextLine();
		        for (OfficerApplication app :OApp) {
		            if (app.getOfficerName().equals(OfficerName)) {
		            	System.out.println("1. Approve HDB Officer Registration");
		 	            System.out.println("2. Reject HDB Officer Registration");
		 	            System.out.print("Choose action: ");
		 	            int officerChoice = Integer.parseInt(sc.nextLine());
		 	            if (officerChoice == 1) {
		 	                app.setApplicationStatus(Status.SUCCESSFUL);
		 	            } else if (officerChoice == 2) {
		 	            	app.setApplicationStatus(Status.UNSUCCESSFUL);
		 	            } else {
		 	                System.out.println("Invalid choice.");
		 	            }
		 	            break;
		            }
		            } 
	           
	        
	        case 7:
	            // Approve/Reject Applicant Applications
	        	for (Application application:applications) {
	        		application.toString();}
	        	System.out.println("Which Application would you like to handle, please enter the Applicant's NRIC: ");
		        String ApplicantNRIC = sc.nextLine();
		        for (Application application:applications) {
	        		if (application.getNric().equals(ApplicantNRIC)){
	        			System.out.println("1. Approve Applicant Application");
	    	            System.out.println("2. Reject Applicant Application");
	    	            System.out.print("Choose action: ");
	    	            int appChoice = Integer.parseInt(sc.nextLine());
	    	            if (appChoice == 1) {
	    	                application.setApplicationStatus(ApplicationStatus.SUCCESSFUL);
	    	            } else if (appChoice == 2) {
	    	            	application.setApplicationStatus(ApplicationStatus.UNSUCCESSFUL);
	    	            } else {
	    	                System.out.println("Invalid choice.");
	    	            }
	    	            break;
	        		}
	        	}
	            
	        
	        case 8:
	            // Approve/Reject Applicant Withdrawals
	        	for (Application application:applications) {
	        		if (application.getApplicationStatus().equals(ApplicationStatus.WITHDRAWNPENDING)){
	        			application.toString();
	        		}
	        	}
	        	System.out.println("Which Application would you like to delete, please enter the Applicant's NRIC: ");
		        String Applicantnric = sc.nextLine();
		        for (Application application:applications) {
	        		if (application.getNric().equals(Applicantnric)){
			            System.out.println("1. Approve Applicant Withdrawal");
			            System.out.println("2. Reject Applicant Withdrawal");
			            System.out.print("Choose action: ");
			            int withdrawalChoice = Integer.parseInt(sc.nextLine());
			            if (withdrawalChoice == 1) {
			            	application.setApplicationStatus(ApplicationStatus.WITHDRAWNSUCCESSFUL);
			            	System.out.println("Application for " + application.getNric() + " has been successfully withdrawn.");
			            	break;
			            } else if (withdrawalChoice == 2) {
			            	application.setApplicationStatus(ApplicationStatus.WITHDRAWNUNSUCCESSFUL);
			            	System.out.println("Application for " + application.getNric() + " has been rejected.");
			            	break;
			            } else {
			                System.out.println("Invalid choice.");
			            }
	        		}
		        }
	            break;
	        
	        case 9:
	            System.out.println("Generating a report of applicants with flat bookings...");
	            System.out.println("Please select the type of report: ");
	            System.out.println("1. General Report ");
	            System.out.println("2. Report based on Marital Status");
	            System.out.println("3. Report based on Flat Type");
	            System.out.println("4. Report based on Age ");
	           
	            Report report = new Report(applications);
	            int reportchoice = sc.nextInt();  // Change this value based on the user's selection

	            switch (reportchoice) {
	                case 1:
	                    report.generateReport();  // Generate the general report
	                    break;
	                case 2:
	                	System.out.println("Please select the Marital Status: ");
	    	            System.out.println("1. Single \n2. Married");
	    	            int reportMSInt = sc.nextInt();
	    	            switch (reportMSInt) {
	    	            case 1:
	    	            	 report.filterByMaritalStatus("Single");
	    	            	 break;
	    	            case 2:
	    	            	report.filterByMaritalStatus("Married"); 
	    	            	break;
	                    default:
	                    	System.out.println("Invalid choice.");
	    	            }
	                case 3:
	                	System.out.println("Please select the Flat Type: ");
	    	            System.out.println("1. 2-Room \n2. 3-Room");
	    	            int reportFlatTypeInt = sc.nextInt();
	    	            switch (reportFlatTypeInt) {
	    	            case 1:
	    	            	 report.filterByFlatType("2-Room");
	    	            	 break;
	    	            case 2:
	    	            	report.filterByFlatType("3-Room"); 
	    	            	break;
	                    default:
	                    	System.out.println("Invalid choice.");
	                case 4:
	                	System.out.println("Please enter the upper limit: ");
	                	 int upperlimit = sc.nextInt();
	    	            System.out.println("Please enter the lower limit: ");
	    	            int lowerlimit = sc.nextInt();
	                    report.filterByAgeGroup(lowerlimit, upperlimit);  // Filter by age group (example: "30-39")
	                    break;
	                default:
	                    System.out.println("Invalid selection.");
	            }
	        
	        case 10:
	        	System.out.println("Viewing all enquiries...");
	            for (Enquiry enquiry:enquiries) {
	            	Applicant applicant = (Applicant) currentUser;
	            	if (enquiry.getApplicantNric().equals(applicant.getNRIC())) {
	            		enquiry.toString();
	            	}
	            }
	    
	        default:
	            System.out.println("Invalid choice! Please try again.");
	            break;
	    }
	}
}
