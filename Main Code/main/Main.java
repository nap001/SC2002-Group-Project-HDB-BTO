package main;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import Boundary.*;
import Database.*;
import Serializer.ObjectLoader;
import Serializer.ObjectSaver;
import Entity.*;
import ENUM.*;
import Interface.*;
import User_Interface.*;
import Controller.*;


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


        // === Sample Users (in place of a real DB or CSV file) ===
        HDBManager manager1 = new HDBManager("S1234567A", "pass123", 35, "Single", "Manager John");
        HDBOfficer officer1 = new HDBOfficer("S9876543B", "pass456", 30, "Married", "Officer Jane");
        Applicant applicant1 = new Applicant("S8888888C", "pass789", 36, "Single", "Applicant Alex");

        userList.addUser(manager1);
        userList.addUser(officer1);
        userList.addUser(applicant1);
     // === Add More Managers ===
        HDBManager manager2 = new HDBManager("S1234567B", "pass234", 40, "Married", "Manager Lisa");
        HDBManager manager3 = new HDBManager("S1234567C", "pass345", 45, "Single", "Manager Mike");
        userList.addUser(manager2);
        userList.addUser(manager3);

        // === Add More Officers if Needed ===
        HDBOfficer officer2 = new HDBOfficer("S9876543C", "pass567", 28, "Single", "Officer Tim");
        HDBOfficer officer3 = new HDBOfficer("S9876543D", "pass678", 32, "Married", "Officer Emma");
        userList.addUser(officer2);
        userList.addUser(officer3);

        // === Define Flat Data for Reuse ===
        Map<FlatType, Integer> flatUnits1 = new HashMap<>();
        flatUnits1.put(FlatType.TWO_ROOM, 50);
        flatUnits1.put(FlatType.THREE_ROOM, 30);

        Map<FlatType, Double> flatPrices1 = new HashMap<>();
        flatPrices1.put(FlatType.TWO_ROOM, 180000.0);
        flatPrices1.put(FlatType.THREE_ROOM, 260000.0);

        // === Create Projects ===
        Project project1 = new Project(
            "Sunshine Residences",
            "Bedok",
            LocalDate.of(2025, 5, 1),
            LocalDate.of(2025, 5, 31),
            true,
            2,
            flatUnits1,
            flatPrices1,
            manager1,
            List.of(officer1)
        );

        Project project2 = new Project(
            "Skyline Vista",
            "Tampines",
            LocalDate.of(2025, 6, 1),
            LocalDate.of(2025, 6, 30),
            true,
            2,
            flatUnits1,
            flatPrices1,
            manager2,
            List.of(officer2)
        );

        Project project3 = new Project(
            "Greenwood Haven",
            "Yishun",
            LocalDate.of(2025, 7, 1),
            LocalDate.of(2025, 7, 31),
            false,
            2,
            flatUnits1,
            flatPrices1,
            manager3,
            List.of(officer3)
        );

        // === Add Projects to ProjectList ===
        projectList.addProject(project1);
        projectList.addProject(project2);
        projectList.addProject(project3);

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
