package main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import Boundary.*;
import ENUM.*;
import Interface.*;
import Serializer.ObjectLoader;
import Serializer.ObjectSaver;
import User_Interface.*;
import Controller.*;
import Entity.*;
import Database.*;

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

        
        // === Initialize Lists if Null ===
        if (projectList == null) projectList = new ProjectList();
        if (userList == null) userList = new UserList();
        if (officerApplicationList == null) officerApplicationList = new OfficerRegistrationList();
        if (enquiryList == null) enquiryList = new EnquiryList();
        if (applicantApplicationList == null) applicantApplicationList = new ApplicantApplicationList();
        if (flatBookingList == null) flatBookingList = new FlatBookingList();

        //CAN DELETE MANUAL OBJECT INITIALISING AFTER FIRST RUN//

        // === Sample Users (in place of a real DB or CSV file) 
        //FROM HERE//
        HDBManager manager1 = new HDBManager("S1234567A", "pass123", 35, "Single", "Manager John");
        HDBOfficer officer1 = new HDBOfficer("S9876543B", "pass456", 30, "Married", "Officer Jane");
        Applicant applicant1 = new Applicant("S8888888C", "pass789", 36, "Single", "Applicant Alex");

        userList.addUser(manager1);
        userList.addUser(officer1);
        userList.addUser(applicant1);

        Map<FlatType, Integer> unitCountMap = new HashMap<>();
        unitCountMap.put(FlatType.TWO_ROOM, 100);
        unitCountMap.put(FlatType.THREE_ROOM, 80);

        Map<FlatType, Double> priceMap = new HashMap<>();
        priceMap.put(FlatType.TWO_ROOM, 250000.0);
        priceMap.put(FlatType.THREE_ROOM, 320000.0);

        List<HDBOfficer> officerList = new ArrayList<>();

        Project sampleProject = new Project(
                "Tampines GreenWeave",
                "Tampines",
                LocalDate.of(2025, 5, 1),
                LocalDate.of(2025, 5, 31),
                true,
                5,
                unitCountMap,
                priceMap,
                manager1,
                officerList
        );
        manager1.setCurrentlyManagedProject(sampleProject);

        // Add to the project list
        projectList.addProject(sampleProject);

        //TILL HERE//


        // === Initialize Control Classes ===
        ProjectControl projectControl = new ProjectControl(projectList);
        ManagerApplicationControl managerApplicationControl = new ManagerApplicationControl(projectList, officerApplicationList, applicantApplicationList);
        ApplicantApplicationControl applicantApplicationControl = new ApplicantApplicationControl(applicantApplicationList);
        EnquiryControl enquiryControl = new EnquiryControl(enquiryList);
        FlatBookingControl flatBookingControl = new FlatBookingControl(projectList, applicantApplicationList, flatBookingList);
        OfficerRegistrationControl officerRegistrationControl = new OfficerRegistrationControl(officerApplicationList);
        ReportGenerator reportGenerator = new ReportGenerator(flatBookingList);
        ReceiptGenerator receiptGenerator = new ReceiptGenerator(flatBookingList);

        // === Main Menu Loop ===
        while (true) {
            System.out.println("\n=== Welcome to HDB Management System ===");
            System.out.print("Enter NRIC (or 'exit' to quit): ");
            String nric = scanner.nextLine();
            if (nric.equalsIgnoreCase("exit")) break;

            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            User user = userList.validateLogin(nric, password);
            if (user == null) {
                System.out.println("Invalid credentials. Please try again.");
                continue;
            }

            // === Launch Role-Specific UI ===
            if (user instanceof HDBManager manager) {
                ManagerUI managerUI = new ManagerUI(manager, projectControl, managerApplicationControl, enquiryControl, reportGenerator);
                boolean switchUser = managerUI.run(); // returns true if logout
                if (!switchUser) break;
            } else if (user instanceof HDBOfficer officer) {
                OfficerUI officerUI = new OfficerUI(officer, projectControl, enquiryControl, flatBookingControl, officerRegistrationControl, receiptGenerator);
                boolean switchUser = officerUI.run();
                if (!switchUser) break;
            } else if (user instanceof Applicant applicant) {
                ApplicantUI applicantUI = new ApplicantUI(applicant, projectControl, applicantApplicationControl, enquiryControl);
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


        System.out.println("Thank you for using the HDB System. Goodbye!");
        scanner.close();
    }
}
