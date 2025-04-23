package main;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import boundary.*;
import controller.*;
import database.*;
import entity.*;
import interfaces.*;
import serializer.ObjectLoader;
import serializer.ObjectSaver;
import serializer.ReferenceNormalizer;
import ENUM.*;


public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // === Load Lists from File ===
        ProjectList projectList = (ProjectList) ObjectLoader.loadObject("projects.ser");
        UserList userList = (UserList) ObjectLoader.loadObject("users.ser");
        OfficerRegistrationList officerApplicationList = (OfficerRegistrationList) ObjectLoader.loadObject("officerApplications.ser");
        EnquiryList enquiryList = (EnquiryList) ObjectLoader.loadObject("enquiries.ser");
        ApplicantApplicationList applicantApplicationList = (ApplicantApplicationList) ObjectLoader.loadObject("applicantApplications.ser");
        FlatBookingList flatBookingList = (FlatBookingList) ObjectLoader.loadObject("flatBookings.ser");
        WithdrawalList withdrawalList = (WithdrawalList) ObjectLoader.loadObject("withdrawals.ser");
        
        // === Initialize Lists if Null ===
        if (projectList == null) projectList = new ProjectList();
        if (userList == null) userList = new UserList();
        if (officerApplicationList == null) officerApplicationList = new OfficerRegistrationList();
        if (enquiryList == null) enquiryList = new EnquiryList();
        if (applicantApplicationList == null) applicantApplicationList = new ApplicantApplicationList();
        if (flatBookingList == null) flatBookingList = new FlatBookingList();
        if (withdrawalList == null) withdrawalList = new WithdrawalList();

     // === Normalize All Cross-References ===
        ReferenceNormalizer.normalize(projectList,  userList,
        		officerApplicationList,
                 flatBookingList,
                 applicantApplicationList,
                 enquiryList,
                 withdrawalList);


        // === Initialize Control Classes ===
        ProjectManagementControl projectManagementControl = new ProjectManagementControl(projectList);
        ProjectQueryControl projectQueryControl = new ProjectQueryControl(projectList);
        ProjectViewControl projectViewControl = new ProjectViewControl(projectList);
        ApplicantProjectControl applicantProjectControl = new ApplicantProjectControl(projectQueryControl,projectViewControl);
        ManagerApplicationControl managerApplicationControl = new ManagerApplicationControl(projectList, officerApplicationList, applicantApplicationList, withdrawalList, flatBookingList);
        ApplicantApplicationControl applicantApplicationControl = new ApplicantApplicationControl(applicantApplicationList);
        EnquiryControl enquiryControl = new EnquiryControl(enquiryList);
        FlatBookingControl flatBookingControl = new FlatBookingControl(projectList, applicantApplicationList, flatBookingList);
        OfficerRegistrationControl officerRegistrationControl = new OfficerRegistrationControl(officerApplicationList);
        ReportGenerator reportGenerator = new ReportGenerator(flatBookingList);
        ReceiptGenerator receiptGenerator = new ReceiptGenerator(flatBookingList);
        WithdrawalControl withdrawalControl = new WithdrawalControl(withdrawalList);

        // === Main Menu Loop ===
        while (true) {
            System.out.println("\n=== Welcome to HDB Management System ===");
            System.out.print("Enter NRIC (or 'exit' to quit): ");
            String nric = scanner.nextLine();
            if (nric.equalsIgnoreCase("exit")) break;

            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            UserAuthenticator authenticator = userList;  // DIP in action
            User user = authenticator.validateLogin(nric, password);
            
            if (user == null) {
                System.out.println("Invalid credentials. Please try again.");
                continue;
            }

            // === Launch Role-Specific UI ===
            if (user instanceof HDBManager manager) {
                ManagerUI managerUI = new ManagerUI(manager, projectManagementControl,projectQueryControl,projectViewControl , managerApplicationControl, enquiryControl, reportGenerator);
                boolean switchUser = managerUI.run(); // returns true if logout
                if (!switchUser) break;
            } else if (user instanceof HDBOfficer officer) {
                OfficerUI officerUI = new OfficerUI(officer,  projectManagementControl,projectQueryControl,projectViewControl,applicantProjectControl, enquiryControl, enquiryControl, flatBookingControl, officerRegistrationControl, receiptGenerator, applicantApplicationControl, withdrawalControl);
                boolean switchUser = officerUI.run();
                if (!switchUser) break;
            } else if (user instanceof Applicant applicant) {
                ApplicantUI applicantUI = new ApplicantUI(applicant, applicantProjectControl, applicantApplicationControl, enquiryControl, withdrawalControl);
                boolean switchUser = applicantUI.run();
                if (!switchUser) break;
            } else {
                System.out.println("Unknown user type.");
            }
        }

        // Save the objects before the program ends
        ObjectSaver.saveObjects(projectList, "projects.ser");
        ObjectSaver.saveObjects(userList, "users.ser");
        ObjectSaver.saveObjects(officerApplicationList, "officerApplications.ser");
        ObjectSaver.saveObjects(enquiryList, "enquiries.ser");
        ObjectSaver.saveObjects(applicantApplicationList, "applicantApplications.ser");
        ObjectSaver.saveObjects(flatBookingList, "flatBookings.ser");
        ObjectSaver.saveObjects(withdrawalList, "withdrawals.ser");

        System.out.println("Thank you for using the HDB System. Goodbye!");
        scanner.close();
    }
}
