package Boundaries;

import Application.*;
import Boundaries.*;
import Controller.*;
import Entities.*;
import Enum.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class UserInterface {
	private  ReadCSV readCSV;
    private  AuthorisationController authorisationController;
    private  ProjectController projectController;
    private  ApplicationController applicationController;
    private  EnquiryController enquiryController;
    private  ReportController reportController;
    private  OfficerRegistrationController officerRegistrationController;
    private  WithdrawalController withdrawalController;
    private  BookingController bookingController;
    private  ReceiptController receiptController;
    private  NotificationController notificationController;
    
    private ManagerInterface managerInterface;
    private OfficerInterface officerInterface;
    private ApplicantInterface applicantInterface;
    
    public UserInterface(AuthorisationController authorisationController, ProjectController projectController, 
      ApplicationController applicationController, EnquiryController enquiryController, ReportController 
      reportController, OfficerRegistrationController officerRegistrationController, WithdrawalController withdrawalController, 
      BookingController bookingController, ReceiptController receiptController, 
      NotificationController notificationController) {
    	Scanner sc = new Scanner(System.in);
    	
    	this.authorisationController = authorisationController;
    	this.projectController = projectController;
    	this.applicationController = applicationController;
    	this.enquiryController = enquiryController;
    	this.reportController = reportController;
    	this.officerRegistrationController = officerRegistrationController;
    	this.withdrawalController = withdrawalController;
    	this.bookingController = bookingController;
    	this.receiptController = receiptController;
    	this.notificationController = notificationController;
    	
    	 applicantInterface = new ApplicantInterface(sc, this.authorisationController, this.projectController, 
                this.applicationController, this.enquiryController, 
                this.reportController, this.officerRegistrationController, 
                this.withdrawalController, this.bookingController, 
                this.receiptController, this.notificationController);
            officerInterface = new ApplicantInterface(sc, this.authorisationController, this.projectController, 
                    this.applicationController, this.enquiryController, 
                    this.reportController, this.officerRegistrationController, 
                    this.withdrawalController, this.bookingController, 
                    this.receiptController, this.notificationController);
            managerInterface = new ApplicantInterface(sc, this.authorisationController, this.projectController, 
                    this.applicationController, this.enquiryController, 
                    this.reportController, this.officerRegistrationController, 
                    this.withdrawalController, this.bookingController, 
                    this.receiptController, this.notificationController);
    }
    
    public void displayLoginMenu() {
    	while (true) {
                System.out.println("=== BTO MANAGEMENT SYSTEM ===");
                System.out.println("1. Login");
                System.out.println("0. Exit");
                System.out.print("Enter your choice: ");
                
                Scanner sc = new Scanner(System.in);
                String input = sc.nextLine().trim();
                
                if (input.isEmpty()) {
                    System.out.println("Please enter a valid option.");
                    continue;
                }
                try {
		            int choice = Integer.parseInt(input);
		            
		            switch (choice) {
		                case 1:
		                    System.out.print("Enter NRIC: ");
		                    String nric = sc.nextLine();
		                    System.out.print("Enter Password: ");
		                    String password = sc.nextLine();
		                    
		                    User user = authorisationController.loginUser(nric, password);
		                    if (user != null) {
		                        displayUserMenu(user);
		                    } else {
		                        System.out.println("Invalid credentials. Please try again.");
		                        continue;
		                    }
		                    break;
		   
                    case 0:
                        System.out.println("Thank you for using BTO Management System.");
                        System.exit(0);
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        continue;
                }
            } catch (NumberFormatException e) {
                // Catch exception if input is not a valid number
                System.out.println("Invalid input. Please enter a valid number.");
            	} catch (Exception e) {
                    System.out.println("An error occurred: " + e.getMessage());
                    System.out.println("Please try again.");
                }
        }
    }
       

    public void displayUserMenu(User user) {
        if (user instanceof Applicant) {
            applicantInterface.displayApplicantMenu((Applicant) user);
        } else if (user instanceof HDBOfficer) {
            officerInterface.displayOfficerMenu((HDBOfficer) user);
        } else if (user instanceof HDBManager) {
            managerInterface.displayManagerMenu((HDBManager) user);
        }
    }
	

}

