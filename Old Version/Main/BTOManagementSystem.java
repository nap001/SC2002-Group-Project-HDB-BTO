package Main;

import Application.Application;
import Application.Enquiry;
import Application.FlatBooking;
import Application.Notifications;
import Application.Project;
import Application.Receipt;
import Application.OfficerApplication;
import Application.OfficerApplication.Status;
import Boundaries.ProjectApplication;
import Boundaries.UserInterface;
import Controller.ApplicationController;
import Controller.AuthorisationController;
import Controller.BookingController;
import Controller.EnquiryController;
import Controller.NotificationController;
import Controller.OfficerRegistrationController;
import Controller.ProjectController;
import Controller.ReadCSV;
import Controller.ReceiptController;
import Controller.ReportController;
import Controller.WithdrawalController;
import Entities.Applicant;
import Entities.HDBManager;
import Entities.HDBOfficer;
import Entities.User;
import Application.Report;
import Application.Application.ApplicationStatus;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class BTOManagementSystem{
	
    
    Scanner sc = new Scanner(System.in);
	
	public BTOManagementSystem() {
		private static ReadCSV readCSV;
	    private static AuthorisationController authorisationController;
	    private static ProjectController projectController;
	    private static ApplicationController applicationController;
	    private static EnquiryController enquiryController;
	    private static ReportController reportController;
	    private static OfficerRegistrationController officerRegistrationController;
	    private static WithdrawalController withdrawalController;
	    private static BookingController bookingController;
	    private static ReceiptController receiptController;
	    private static NotificationController notificationController;
		
	    public static void initialise() {
			authorisationController = new AuthorisationController();
	        projectController = new ProjectController();
	        applicationController = new ApplicationController();
	        enquiryController = new EnquiryController();
	        officerRegistrationController = new OfficerRegistrationController();
	        reportController = new ReportController();
	        withdrawalController = new WithdrawalController();
	        bookingController = new BookingController();
	        receiptController = new ReceiptController();
	        notificationController = new NotificationController();
	        
	        readCSV = new ReadCSV();
	        retrieveData();
	        
	        UserInterface userinterface = new UserInterface(authorisationController, projectController, 	
	        		applicationController, enquiryController, reportController, 
	        		officerRegistrationController, withdrawalController, bookingController, 
	        		receiptController, notificationController);
	        
		}
		
		public static void retrieveData() {
			try {
				List<User> users = readCSV.loadUsersFromCSV();
				List<Project> projects = readCSV.loadProjectsFromCSV();
		        List<Application> applications = readCSV.loadApplicationsFromCSV();
		        List<Enquiry> enquiries = readCSV.loadEnquiriesFromCSV();
		        List<OfficerApplication> officerapplications = readCSV.loadOfficerApplicationsFromCSV();
		        List<FlatBooking> flatbookings = readCSV.loadFlatBookingsFromCSV();
		        List<Notifications> notifications = readCSV.loadNotificationsFromCSV();
		        List<Receipt> receipts = readCSV.loadReceiptsFromCSV();
		        
		        initialiseAuthorisationController(users);
		        initialiseApplicationController(applications);
		        initialiseEnquiryController(enquiries);
		        initialiseOfficerApplicationController(officerapplications);
		        initialiseFlatBookingController(flatbookings);
		        initialiseNotificationsController(notifications);
		        initialiseReceiptsController(receipts);
		        
		        setUp(users, projects, applications, enquiries, officerapplications, flatbookings, notifications, receipts);
		        
			}catch (Exception e) {
	            System.err.println("Error during load data: " + e.getMessage());
	            e.printStackTrace();
	        }
		}
		
		public static void setUp(List<User> users, List<Project> projects, List<Application> applications ,List<Enquiry> enquiries, List<OfficerApplication> officerapplications, List<FlatBooking> flatbookings, List<Notifications> notifications, List<Receipt> receipts) {
			for (User user : users) {
	            for (Application app : applications) {
	                if (app.getApplicant().getNric().equals(applicant.getNRIC())) {
	                    applicant.setAppliedProject(app);
	                    break;
	                }
	            }
	        }
			
			for (Applicant applicant : applicants) {
	            for (FlatBooking booking : bookings) {
	                if (booking.getApplicant().getNric().equals(applicant.getNric())) {
	                    applicant.setBookedFlat(booking);
	                    break;
	                }
	            }
	        }
			
			for (Project project : projects) {
	            for (Enquiry enquiry : enquiries) {
	                if (enquiry.getProject() != null && 
	                    enquiry.getProject().getProjectName().equals(project.getProjectName())) {
	                    project.addEnquiry(enquiry);
	                }
	            }
	        }
			
			for (Project project : projects) {
	            project.initialiseProjectFlats();
	        }
		}
		
		// Update the initialization method for BookingController
	    private static void initialiseFlatBookingController(List<FlatBooking> bookings, List<Receipt> receipts) {
	        bookingController.setBookings(bookings);
	        bookingController.setReceipts(receipts);
	    }

	    // Initialization methods for controllers
	    private static void initialiseAuthorisationController(List<User> users) {
	        for (User user : users) {
	            authorisationController.addUser(user);
	        }
	    }

	    private static void initialiseProjectController(List<Project> projects) {
	        projectController.setProjects(projects);
	    }

	    private static void initialiseApplicationController(List<ProjectApplication> applications) {
	        applicationController.setApplications(applications);
	    }

	    private static void initialiseEnquiryController(List<Enquiry> enquiries) {
	        enquiryController.setEnquiries(enquiries);
	    }
	    
	    private static void initialiseOfficerApplicationController(List<OfficerApplication> registrations) {
	        officerRegistrationController.setRegistrations(registrations);
	    }
	    
	    private static void initialiseNotificationsController(List<Notification> notifications) {
	        NotificationController.setNotifications(notifications);
	    }
	    
	    private static void initialiseReceiptsController(List<Receipt> receipts) {
	        ReceiptController.setReceipts(receipts);
	    }
	
	    public void displayLoginMenu() {
	        System.out.println("\n===== BTO MANAGEMENT SYSTEM LOGIN =====");
	        System.out.println("1. Login");
		    System.out.println("2. Exit");
		    System.out.print("Enter your choice: ");
		    int choice = sc.nextInt();
		    
		    switch (choice) {
		    	case 1:
		    		login();
		    		break;
		        case 2:
		            System.out.println("Exiting system. Goodbye!");
		            System.exit(0);
		            break;
		        default:
		            System.out.println("Invalid choice. Please try again.");
		    }
		}
	

	
	
	
	
	

	


	}
	

