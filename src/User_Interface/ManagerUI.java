package User_Interface;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.Map;
import java.util.Scanner;

import Boundary.HDBManager;
import Controller.EnquiryControl;
import Controller.ManagerApplicationControl;
import Controller.ProjectControl;
import ENUM.FlatType;

public class ManagerUI {
    private final HDBManager manager;
    private final ProjectControl projectControl;
    private final ManagerApplicationControl applicationControl;
    private final EnquiryControl enquiryControl;

    public ManagerUI(HDBManager manager,
                     ProjectControl projectControl,
                     ManagerApplicationControl applicationControl,
                     EnquiryControl enquiryControl) {
        this.manager = manager;
        this.projectControl = projectControl;
        this.applicationControl = applicationControl;
        this.enquiryControl = enquiryControl;
    }

    // Run the manager UI menu loop. Returns true if user wants to logout and switch users.
    public boolean run() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== HDB Manager Menu ===");
            System.out.println("1. View All Projects");
            System.out.println("2. View My Projects");
            System.out.println("3. Create New Project");
            System.out.println("4. Remove Project");
            System.out.println("5. Toggle Project Visibility");
            System.out.println("6. Manage Officer Applications");
            System.out.println("7. View All Enquiries");
            System.out.println("8. Reply to Enquiries");
            System.out.println("0. Logout and Switch User");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    manager.viewAllProject(projectControl);
                    break;
                case 2:
                    manager.viewMyProjects(projectControl);
                    break;
                case 3:
                    createProject(scanner);
                    break;
                case 4:
                    removeProject(scanner);
                    break;
                case 5:
                    toggleProjectVisibility(scanner);
                    break;
                case 6:
                    manageOfficerApplications(scanner);
                    break;
                case 7:
                    manager.viewAllEnquiries(enquiryControl);
                    break;
                case 8:
                    manager.replyToEnquiries(enquiryControl);
                    break;
                case 0:
                    System.out.println("Logging out...");
                    return true;
                default:
                    System.out.println("Invalid choice.");
            }

        } while (true);
    }

    private void createProject(Scanner scanner) {
        System.out.print("Enter project name: ");
        String projectName = scanner.nextLine();

        System.out.print("Enter neighbourhood: ");
        String neighbourhood = scanner.nextLine();

        System.out.print("Enter application open date (YYYY-MM-DD): ");
        LocalDate openDate = LocalDate.parse(scanner.nextLine());

        System.out.print("Enter application close date (YYYY-MM-DD): ");
        LocalDate closeDate = LocalDate.parse(scanner.nextLine());

        System.out.print("Should the project be visible? (true/false): ");
        boolean visible = Boolean.parseBoolean(scanner.nextLine());

        System.out.print("Enter number of officer slots: ");
        int officerSlots = Integer.parseInt(scanner.nextLine());

        Map<FlatType, Integer> unitCountMap = new EnumMap<>(FlatType.class);
        Map<FlatType, Integer> priceMap = new EnumMap<>(FlatType.class);

        for (FlatType flatType : FlatType.values()) {
            System.out.print("Enter number of units for " + flatType + ": ");
            unitCountMap.put(flatType, Integer.parseInt(scanner.nextLine()));
            System.out.print("Enter price for " + flatType + ": ");
            priceMap.put(flatType, Integer.parseInt(scanner.nextLine()));
        }

        manager.createProject(projectControl, projectName, neighbourhood, openDate,
                closeDate, visible, officerSlots, unitCountMap, priceMap);
    }

    private void removeProject(Scanner scanner) {
        System.out.print("Enter project name to remove: ");
        String projectName = scanner.nextLine();
        manager.removeProject(projectControl, projectName);
    }

    private void toggleProjectVisibility(Scanner scanner) {
        System.out.print("Enter project name to change visibility: ");
        String projectName = scanner.nextLine();

        System.out.print("Enter new visibility (true/false): ");
        boolean isVisible = Boolean.parseBoolean(scanner.nextLine());

        manager.toggleProjectVisibility(projectControl, projectName, isVisible);
    }

    private void manageOfficerApplications(Scanner scanner) {
        System.out.print("Enter project name to manage applications: ");
        String projectName = scanner.nextLine();


        manager.manageOfficerApplication(applicationControl, projectName);
    }
}
