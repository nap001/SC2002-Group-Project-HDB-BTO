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
import User_Interface.*;
import Controller.*;
import Entity.*;
import Database.*;




public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // === Initialize List Classes ===
        ProjectList projectList = new ProjectList();
        OfficerRegistrationList officerApplicationList = new OfficerRegistrationList();
        EnquiryList enquiryList = new EnquiryList();
        UserList userList = new UserList(); // this can store all users
        ApplicantApplicationList applicantapplicationList = new ApplicantApplicationList();
        FlatBookingList flatbookingList = new FlatBookingList();
        
        // === Sample Users (in place of a real DB or CSV file) ===
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
        // === Initialize Control Classes ===
        ProjectControl projectControl = new ProjectControl(projectList);
        ManagerApplicationControl managerApplicationControl = new ManagerApplicationControl(projectList, officerApplicationList, applicantapplicationList);
        ApplicantApplicationControl applicantApplicationControl = new ApplicantApplicationControl(applicantapplicationList);
        EnquiryControl enquiryControl = new EnquiryControl(enquiryList);
        FlatBookingControl flatbookingControl = new FlatBookingControl(projectList,applicantapplicationList, flatbookingList);
        OfficerRegistrationControl officerregistrationControl = new OfficerRegistrationControl(officerApplicationList);

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
                ManagerUI managerUI = new ManagerUI(manager, projectControl, managerApplicationControl, enquiryControl);
                boolean switchUser = managerUI.run(); // returns true if logout
                if (!switchUser) break;
            } else if (user instanceof HDBOfficer officer) {
                OfficerUI officerUI = new OfficerUI(officer, projectControl, enquiryControl, flatbookingControl, officerregistrationControl);
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
        System.out.println("Thank you for using the HDB System. Goodbye!");
        scanner.close();

    }
}
