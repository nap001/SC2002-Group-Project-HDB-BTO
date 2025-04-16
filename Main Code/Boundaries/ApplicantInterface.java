package Boundaries;

import Application.*;
import Boundaries.*;
import Controller.*;
import Entities.*;
import Enum.*;

import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ApplicantInterface {
		
		private Scanner sc;
		
    	private  ApplicationController applicationController;
    	private  AuthorisationController authorisationController;
    	private  BookingController bookingController;
	    private  EnquiryController enquiryController;
	    private  NotificationController notificationController;
	    private  OfficerApplicationController officerApplicationController;
	    private  ProjectController projectController;
	    private  ReceiptController receiptController;
	    private  ReportController reportController;
	    private  WithdrawalController withdrawalController;

	    private UserInterface userInterface;
	    private Applicant applicant;
	    
	    public ApplicantInterface(Scanner sc, ApplicationController applicationController, AuthorisationController authorisationController,
	    							BookingController bookingController, EnquiryController enquiryController,
	    							NotificationController notificationController, OfficerApplicationController officerApplicationController,
	    							ProjectController projectController, ReceiptController receiptController,
	    							ReportController reportController,  WithdrawalController withdrawalController, UserInterface userInterface){
	    	this.sc = sc;
	    	this.applicationController = applicationController;
	        this.authorisationController = authorisationController;
	        this.bookingController = bookingController;
	        this.enquiryController = enquiryController;
	        this.notificationController = notificationController;
	        this.officerApplicationController = officerApplicationController;
	        this.projectController = projectController;
	        this.receiptController = receiptController;
	        this.reportController = reportController;
	        this.withdrawalController = withdrawalController;
	        this.userInterface = userInterface;
	    }
	    
	    public void setApplicant(Applicant applicant) {
	    	this.applicant = applicant;
	    }
	    public Applicant getApplicant() {
	    	return applicant;
	    }
	
	
	public void showApplicantMenu(Applicant applicant) {
		setCurrentApplicant(applicant);
	    System.out.println("Welcome " + applicant.getName() + " to the Applicant's menu, what would you like to do today?");
	    System.out.println("==============================================================================");
	    System.out.println("1. View list of available projects");
	    System.out.println("2. View flat application details");
	    System.out.println("3. Request to withdraw application status");
	    System.out.println("4. Submit a new enquiry");
	    System.out.println("5. View enquiry");;
	    System.out.println("6. Change Password");
	    System.out.println("7. View Notifications");
	    System.out.print("Please enter your choice: ");
	    Scanner sc= new Scanner(System.in);
	    int choice = sc.nextInt();
	 
	    switch (choice) {
	        case 1:
	        	List<Project> VisibleProjects = showListOfProjects();
                Project selectedProject = displayProjectDetails(VisibleProjects);
                applicationConfirmation(selectedProject);
                break;
	        case 2:
	        	System.out.println("Viewing your application details...");
	            viewApplicationStatus();  // Function to view application details
	            break;
	        case 3:
	        	System.out.println("Request to withdraw your application...");
	            withdrawalController.submitWithdrawal(applicant, applicant.getApplication());  // Function to request withdrawal
	            break;
	        case 4:
	        	System.out.println("Submit a new enquiry...");
	            submitEnquiry();
	            break;
	        case 5:
	        	System.out.println("Viewing all enquiries submitted by you...");
	            ViewEnquiries(enquiryController.getAllEnquiries()); 
	            break;
	        case 6:
	            System.out.println("Changing your password...");
	            changePassword();
	            break;
	        case 7:
	            System.out.println("Viewing your notifications...");
	            viewNotifications();
	            break;

	        default:
	            System.out.println("Invalid choice! Please try again.");
	            break;
	    }

	}
	
	private void viewNotifications() {
	    System.out.println("\n=== NOTIFICATIONS ===");

	    // Fetch the list of notifications
	    List<Notification> notifications = notificationController.getNotificationsForApplicant(applicant);

	    if (notifications == null || notifications.isEmpty()) {
	        System.out.println("You have no notifications at the moment.");
	    } else {
	        System.out.println("Your Notifications:");
	        System.out.println("Date       Notification Content");
	        System.out.println("------------------------------------------");

	        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	        // Loop through each notification and display its details
	        for (int i = 0; i < notifications.size(); i++) {
	            Notification notification = notifications.get(i);
	            String notificationDate = dateFormat.format(notification.getCreationDate());
	            String content = notification.getNotificationContent();

	            // Print each notification's details
	            System.out.printf("%-10s %-50s%n", notificationDate, content);
	        }
	        System.out.println("------------------------------------------");
	    }

	    // Wait for user input before returning to the main menu
	    System.out.println("\nPress Enter to return to the main menu...");
	    sc.nextLine();
	    showApplicantMenu(applicant);
	}

	private void setCurrentApplicant(Applicant applicant) {
		this.applicant = applicant;
		
	}

	public List<Project> showListOfProjects() {
		System.out.println();
		System.out.println("------ HDB Projects ------");
		List<Project> projects = projectController.getAllVisibleProjects();
		if (projects == null) {
			System.out.println("System does not have any available projects at the moment, please try again later.");
		}else {
		    System.out.println("Available Projects:");
		    System.out.println("ID      Project Name             Region                 Neighborhood           Application Period");
		    System.out.println("-----------------------------------------------------------------------------------------------");
		    
		    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		    for (int i = 0; i < projects.size(); i++) {
		        Project project = projects.get(i);
		        String openDate = dateFormat.format(project.getOpeningDate());
		        String closeDate = dateFormat.format(project.getClosingDate());
		      
		        System.out.printf("%-7d %-25s %-20s %-20s %-12s - %-12s%n", 
		        	i + 1,
		            project.getProjectName(),
		            project.getRegion(),
		            project.getNeighborhood(),
		            openDate + "-" + closeDate
		        );
		    }
		    System.out.println("-----------------------------------------------------------------------------------------------");
		}
		return projects;
	}
	
	private Project displayProjectDetails(List<Project> visibleProjects) {
        if (visibleProjects.isEmpty()) {
            return null;
        }
        
        int projectChoice = getValidIntegerInput("Enter the project number to view details (or 0 to go back): ", 0, visibleProjects.size());
        
        if (projectChoice == 0) {
            showApplicantMenu(applicant);
            return null;
        }
        
        // Get the selected project (subtract 1 because list is 0-indexed)
        Project selectedProject = visibleProjects.get(projectChoice - 1);
        
        // Display detailed project information
        System.out.println("\n------ PROJECT DETAILS ------");
        System.out.println("Project Name: " + selectedProject.getProjectName());
        System.out.println("Neighborhood: " + selectedProject.getNeighborhood());
        
        // Format dates for display
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Application Open Date: " + dateFormat.format(selectedProject.getOpeningDate()));
        System.out.println("Application Close Date: " + dateFormat.format(selectedProject.getClosingDate()));
        
        // Manager in Charge
        System.out.println("Manager in Charge: " + selectedProject.getManager().getName());
        
        // Display Flat Types and Availability
        System.out.println("\nFlat Types and Availability:");
        System.out.println("Flat Type                Total Units           Available Units");
        System.out.println("----------------------------------------------------------------------");
        Map<FlatType, Integer> flatDetails = selectedProject.getFlatDetails();
        ProjectFlatsDetails projectFlats = selectedProject.getProjectFlatDetails();

        for (FlatType flatType : flatDetails.keySet()) {
            int totalUnits = flatDetails.get(flatType);
            int availableUnits = projectFlats.getAvailableFlatCount(flatType);
            
            System.out.printf("%-25s %10d %20d%n", 
                flatType.name(), 
                totalUnits, 
                availableUnits
            );
        }
        
        List<FlatType> eligibleTypes = new ArrayList<>();

        int age = applicant.getAge();
        boolean isMarried = applicant.getMaritialStatus().equals("Married"); 
        if (age > 21 && isMarried) {
	         eligibleTypes.add(FlatType.TWO_ROOM);
	         eligibleTypes.add(FlatType.THREE_ROOM);
	     } else if (age > 35 && !isMarried) {
	         eligibleTypes.add(FlatType.TWO_ROOM);
	     } else {
	         eligibleTypes.clear();
	     }
	
	     System.out.println("\nYour Eligible Flat Types:");
	     if (eligibleTypes.isEmpty()) {
	         System.out.println("  No flat types currently available for your profile.");
	     } else {
	         for (FlatType type : eligibleTypes) {
	             System.out.println("  - " + type.name());
	         }
	     }
	     return selectedProject;
	     }
	
	private void applicationConfirmation(Project selectedProject) {
        if (selectedProject == null) {
            return;
        }
        
        System.out.println("\nOptions:");
        System.out.println("1) Apply");
        System.out.println("0) Go Back");
        
        int choice = getValidIntegerInput("Enter your choice: ", 0, 1);

        switch (choice) {
            case 1:
                Boolean applicationResult = applicationController.submitApplication(applicant, selectedProject);
                
                if (applicationResult) {
                    System.out.println("\n------ APPLICATION SUBMITTED ------");
                    System.out.println("Your application is pending.");
                    System.out.println("Project: " + selectedProject.getProjectName());
                    System.out.println("Status: Pending");
                    System.out.println("\nPress Enter to continue...");
                    sc.nextLine();
                } else {
                    System.out.println("Failed to submit application. You may already have an active application.");
                    break;
                }
            case 0:
                showApplicantMenu(applicant);
                break;
        }
    }
	
	private void viewApplicationStatus() {
	    System.out.println("\n------ APPLICATION STATUS ------");
	    
	    Application application = applicationController.getApplicationByNRIC(applicant.getNRIC());
	    if (application == null) {
	        System.out.println("You currently have no active applications.");
	        return;
	    }
	    Project project = application.getProject();
	    System.out.println("Project Name: " + project.getProjectName());
	    System.out.println("Neighborhood: " + project.getNeighborhood());
	    System.out.println("Status: " + application.ApplicationStatustoString(application.getApplicationStatus()));

	    // Display selected flat type or "Not yet chosen"
	    System.out.println("Selected Flat Type: " + (application.getFlatType() != null 
	                                             ? application.getFlatType() 
	                                             : "Not yet chosen"));
	    
	    int maxChoice = 0;
	    if (application.getApplicationStatus() == ApplicationStatus.SUCCESSFUL && application.getFlatType() == null) {
	        System.out.println("1. Select Flat Type");
	        maxChoice = 1;
	    } else if (application.getApplicationStatus() == ApplicationStatus.BOOKED) {
	        System.out.println("1. View Booking Receipt");
	        maxChoice = 1;
	    }

	    System.out.println("0. Back to Main Menu");

	    // Handle user input and execute choice
	    int choice = getValidIntegerInput("\nEnter your choice: ", 0, maxChoice);
	    if (choice == 1) {
	        if (application.getApplicationStatus() == ApplicationStatus.SUCCESSFUL && application.getFlatType() == null) {
	            selectFlatType(application, project);
	        } else if (application.getApplicationStatus() == ApplicationStatus.BOOKED) {
	            bookingController.viewBookingReceipt(applicant);
	        }
	    } else {
	        showApplicantMenu(applicant);
	    }
	}
	
	private void selectFlatType(Application application, Project project) {
	    System.out.println("\n=== SELECT FLAT TYPE ===");

	    List<FlatType> eligibleTypes = project.getEligibleFlatTypes(applicant);

	    if (eligibleTypes.isEmpty()) {
	        System.out.println("You are not eligible for any flat types in this project.");
	        return;
	    }
	    System.out.println("Available and Eligible Flat Types:");
	    System.out.println("ID      Flat Type                Available Units");
	    System.out.println("---------------------------------------------------");

	    ProjectFlatsDetails projectFlats = project.getProjectFlatDetails();
	    List<FlatType> availableTypes = new ArrayList<>();
	    for (int i = 0; i < eligibleTypes.size(); i++) {
	        FlatType flatType = eligibleTypes.get(i);
	        int availableUnits = projectFlats.getAvailableFlatCount(flatType);
	        if (availableUnits > 0) {
	            availableTypes.add(flatType);
	            System.out.printf("%-7d %-25s %20d%n", i + 1, flatType.toString(), availableUnits);
	        }
	    }
	    if (availableTypes.isEmpty()) {
	        System.out.println("No eligible flat types currently have available units.");
	        return;
	    }
	    System.out.println("\nOptions:");
	    System.out.println("1. Select a Flat Type");
	    System.out.println("0. Cancel");

	    int choice = getValidIntegerInput("\nEnter your choice: ", 0, 1);

	    if (choice == 1) {
	        int selectedFlatTypeIndex = getValidIntegerInput("Enter the ID of the flat type you want to select: ", 1, availableTypes.size());
	        FlatType selectedType = availableTypes.get(selectedFlatTypeIndex - 1);
	        application.setFlatType(selectedType);
	        System.out.println("\nFlat type " + selectedType.toString() + " has been selected.");
	        System.out.println("Your selection has been submitted and is pending officer approval.");
	        System.out.println("You will be notified once your booking is confirmed, you can check your notifications.");
	    }

        System.out.println("\nPress Enter to continue...");
        sc.nextLine();
        showApplicantMenu(applicant);
	}
	
	private void viewBookingReceipt(Applicant applicant) {
        System.out.println("\n======== BOOKING RECEIPT ========");
        
        // Check if a receipt exists in the BookingController
        if (bookingController.hasReceipt(applicant.getNRIC())) {
            // Retrieve the receipt from BookingController
            Receipt receipt = bookingController.getReceiptForApplicant(applicant.getNRIC());
            
            // Display the receipt content
            System.out.println(receipt.getReceiptContent());
        } else {
            System.out.println("No receipt available. Please contact an HDB Officer to generate your receipt.");
        }
        
        System.out.println("\nNote: To save this receipt, you can copy and paste the text above.");
        
        // Wait for user input before returning to menu
        System.out.println("\nPress Enter to return to the main menu...");
        sc.nextLine();
        
        // Return to the applicant menu
        showApplicantMenu(applicant);
    }

	public int getValidIntegerInput(String prompt, int min, int max) {
	    Scanner sc = new Scanner(System.in);
	    int input = -1;

	    while (input < min || input > max) {
	        System.out.print(prompt);
	        
	        // Check if the input is a valid integer
	        if (sc.hasNextInt()) {
	            input = sc.nextInt();
	            // If the input is valid but out of range, prompt again
	            if (input < min || input > max) {
	                System.out.println("Please enter a number between " + min + " and " + max + ".");
	            }
	        } else {
	            System.out.println("Invalid input! Please enter a valid number.");
	            sc.next(); // Consume invalid input to avoid infinite loop
	        }
	    }
	    return input;
	} 
	
	private void submitEnquiry() {
	    System.out.println("\n------ SUBMIT ENQUIRY ------");
	    System.out.println("Is this enquiry about a specific project?");
	    System.out.println("1. Yes");
	    System.out.println("2. No (General Enquiry)");
	    int projectChoice = sc.nextInt();

	    Project selectedProject = null;
	    if (projectChoice == 1) {
	        selectedProject = selectProject();
	    }
	    System.out.println("\nEnter your enquiry:");
	    String enquiryContent = sc.nextLine();
	    if (enquiryContent.trim().isEmpty()) {
	        System.out.println("Enquiry content cannot be empty. Operation cancelled.");
	    } else {
	        Enquiry enquiry = enquiryController.createEnquiry(applicant, selectedProject, enquiryContent);
	        if (enquiry != null) {
		        System.out.println("\nEnquiry submitted successfully!");
		        System.out.println("You will be notified when a response is available.");
		    } else {
		        System.out.println("\nFailed to submit enquiry. Please try again later.");
		    }
	    }
	    System.out.println("\nPress Enter to return to the main menu...");
	    sc.nextLine();
	}

	private Project selectProject() {
	    List<Project> visibleProjects = projectController.getAllVisibleProjects();
	    if (visibleProjects.isEmpty()) {
	        System.out.println("No projects available. Submitting as a general enquiry.");
	        return null;
	    } else {
	        System.out.println("\nSelect Project:");
	        for (int i = 0; i < visibleProjects.size(); i++) {
	            System.out.println((i+1) + ". " + visibleProjects.get(i).getProjectName());
	        }

	        int projectNum = getValidIntegerInput("\nEnter project number (or 0 for general enquiry): ", 0, visibleProjects.size());
	        if (projectNum > 0 && projectNum <= visibleProjects.size()) {
	            Project selectedProject = visibleProjects.get(projectNum - 1);
	            System.out.println("Selected project: " + selectedProject.getProjectName());
	            return selectedProject;
	        } else {
	            System.out.println("No project selected. Submitting as a general enquiry.");
	            return null;
	        }
	    }
	}
	
	public void ViewEnquiries(List<Enquiry> enquiries) {
	    System.out.println();
	    System.out.println("------ Enquiries ------");
	    if (enquiries == null || enquiries.isEmpty()) {
	        System.out.println("You have no enquiries at the moment.");
	    } else {
	        System.out.println("Your Enquiries:");
	        System.out.println("ID      Project Name             Enquiry Content                   Enquiry Date       Response                Response Date     Responder Name");
	        System.out.println("---------------------------------------------------------------------------------------------------------------------------");
	        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	        for (int i = 0; i < enquiries.size(); i++) {
	            Enquiry enquiry = enquiries.get(i);
	            
	            String projectName = enquiry.getProject() != null ? enquiry.getProject().getProjectName() : "General Enquiry";
	            String enquiryContent = enquiry.getEnquiryString();
	            String enquiryDate = dateFormat.format(enquiry.getEnquiryDate());
	            
	            String response = enquiry.getResponse() != null ? enquiry.getResponse() : "No response yet";
	            String responseDate = enquiry.getResponseDate() != null ? dateFormat.format(enquiry.getResponseDate()) : "N/A";
	            String responderName = enquiry.getResponder().getName() != null ? enquiry.getResponder().getName() : "N/A";
	            
	            System.out.printf("%-7d %-25s %-30s %-18s %-20s %-18s %-20s%n", 
	                i + 1,
	                projectName,
	                enquiryContent,
	                enquiryDate,
	                response,
	                responseDate,
	                responderName
	            );
	        }
	        System.out.println("---------------------------------------------------------------------------------------------------------------------------");
	     // Ask the user if they want to edit, delete, or return to the main menu
	        System.out.println("\nOptions:");
	        System.out.println("1. Edit an enquiry");
	        System.out.println("2. Delete an enquiry");
	        System.out.println("0. Return to main menu");

	        int choice = getValidIntegerInput("Enter your choice: ", 0, 2);

	        if (choice == 1) {
	            editEnquiry(enquiries);
	        } else if (choice == 2) {
	            deleteEnquiry(enquiries);
	        } else if (choice == 0) {
	        	showApplicantMenu(applicant);
	            return; 
	        } else {
	            System.out.println("Invalid choice.");
	        }
	    }
	}
	    private void editEnquiry(List<Enquiry> enquiries) {
	        System.out.print("\nEnter the ID of the enquiry you want to edit: ");
	        int enquiryId = getValidIntegerInput("", 1, enquiries.size()) - 1;

	        Enquiry enquiryToEdit = enquiries.get(enquiryId);
	        System.out.println("Editing Enquiry ID " + (enquiryId + 1) + ": " + enquiryToEdit.getReceiptContent());
	        System.out.print("Enter new content for the enquiry: ");
	        String newContent = sc.nextLine();
	        enquiryController.editEnquiry(enquiryToEdit, newContent);
	    }

	    private void deleteEnquiry(List<Enquiry> enquiries) {
	        System.out.print("\nEnter the ID of the enquiry you want to delete: ");
	        int enquiryId = getValidIntegerInput("", 1, enquiries.size()) - 1; // Get valid enquiry ID

	        Enquiry enquiryToDelete = enquiries.get(enquiryId);
	        System.out.println("Are you sure you want to delete the following enquiry?");
	        System.out.println("Project: " + enquiryToDelete.getProject().getProjectName());
	        System.out.println("Enquiry Content: " + enquiryToDelete.getReceiptContent());

	        System.out.println("1. Yes, delete enquiry");
	        System.out.println("2. No, cancel deletion");

	        int deleteChoice = getValidIntegerInput("Enter your choice: ", 1, 2);
	        if (deleteChoice == 1) {
		        enquiryController.deleteEnquiry(enquiryToDelete);
	        } else {
	            System.out.println("Enquiry deletion canceled.");
	        }
	    }
	
	private void changePassword() {
	    System.out.println("\n=== CHANGE PASSWORD ===");
	    System.out.print("Enter your current password: ");
	    String oldPassword = sc.nextLine();
	    
	    if (!applicant.getPassword().equals(oldPassword)) {
	        System.out.println("Incorrect password. Change canceled.");
	        sc.nextLine();
	        showApplicantMenu(applicant);
	        return;
	    }
	    String newPassword;
	    while (true) {
	        System.out.print("Enter new password: ");
	        newPassword = sc.nextLine();

	        if (authorisationController.validatePassword(newPassword)) {
	            break;
	        } else {
	            System.out.println("Password must be at least 8 characters. Please try again.");
	        }
	    }
	    System.out.print("Confirm password: ");
	    String confirmPassword = sc.nextLine();

	    if (!newPassword.equals(confirmPassword)) {
	        System.out.println("Passwords do not match. Password change canceled.");
	        sc.nextLine();
	        showApplicantMenu(applicant);
	        return;
	    }
	    applicant.setPassword(newPassword);
	    System.out.println("Password successfully changed.");
	}
    }
