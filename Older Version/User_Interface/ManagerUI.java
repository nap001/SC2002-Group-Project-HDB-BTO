package User_Interface;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.Map;
import java.util.Scanner;

import Boundary.HDBManager;
import Controller.EnquiryControl;
import Controller.ManagerApplicationControl;
import Controller.ProjectControl;
import Controller.ReportGenerator;
import ENUM.FlatType;

public class ManagerUI {
    private final HDBManager manager;
    private final ProjectControl projectControl;
    private final ManagerApplicationControl applicationControl;
    private final EnquiryControl enquiryControl;
    private final ReportGenerator reportGenerator;

    public ManagerUI(HDBManager manager,
                     ProjectControl projectControl,
                     ManagerApplicationControl applicationControl,
                     EnquiryControl enquiryControl,
                     ReportGenerator reportGenerator) {
        this.manager = manager;
        this.projectControl = projectControl;
        this.applicationControl = applicationControl;
        this.enquiryControl = enquiryControl;
        this.reportGenerator = reportGenerator;
    }

    public boolean run() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== HDB Manager Menu ===");
            System.out.println("1. View All Projects");
            System.out.println("2. View My Projects");
            System.out.println("3. Create New Project");
            System.out.println("4. Remove Project");
            System.out.println("5. Edit Project");
            System.out.println("6. Toggle Project Visibility");
            System.out.println("7. View All Enquiries");
            System.out.println("8. Reply to Enquiries");
            System.out.println("9. Manage Officer Applications");
            System.out.println("10. Approve Applicant Applications");
            System.out.println("11. Generate Applicant Report");
            System.out.println("12. Display Last Generated Report");
            System.out.println("0. Logout and Switch User");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> manager.viewAllProject(projectControl);
                case 2 -> manager.viewMyProjects(projectControl);
                case 3 -> createProject(scanner);
                case 4 -> removeProject(scanner);
                case 5 -> editProject(scanner);
                case 6 -> toggleProjectVisibility(scanner);
                case 7 -> manager.viewAllEnquiries(enquiryControl);
                case 8 -> manager.replyToEnquiries(enquiryControl);
                case 9 -> manageOfficerApplications(scanner);
                case 10 -> manager.approveApplicantApplications(applicationControl, projectControl);
                case 11 -> generateReport(scanner);
                case 12 -> manager.displayGeneratedReport(reportGenerator);
                case 0 -> {
                    System.out.println("Logging out...");
                    return true;
                }
                default -> System.out.println("Invalid choice.");
            }

        } while (true);
    }

    private void editProject(Scanner scanner) {
        System.out.print("Enter project name to edit: ");
        String projectName = scanner.nextLine();

        System.out.println("""
                What do you want to edit?
                1. Project Name
                2. Neighbourhood
                3. Application Open Date
                4. Application Close Date
                5. Visibility
                6. Officer Slots
                """);
        System.out.print("Enter your choice: ");
        int editChoice = Integer.parseInt(scanner.nextLine());

        Object newValue = null;
        switch (editChoice) {
            case 1, 2 -> {
                System.out.print("Enter new value (String): ");
                newValue = scanner.nextLine();
            }
            case 3, 4 -> {
                System.out.print("Enter new date (YYYY-MM-DD): ");
                newValue = LocalDate.parse(scanner.nextLine());
            }
            case 5 -> {
                System.out.print("Enter new visibility (true/false): ");
                newValue = Boolean.parseBoolean(scanner.nextLine());
            }
            case 6 -> {
                System.out.print("Enter new officer slot count: ");
                newValue = Integer.parseInt(scanner.nextLine());
            }
            default -> {
                System.out.println("Invalid edit choice.");
                return;
            }
        }

        manager.editProject(projectControl, projectName, editChoice, newValue);
    }

    private void generateReport(Scanner scanner) {
        System.out.println("Generate report by:");
        System.out.println("1. Marital Status");
        System.out.println("2. Age");
        System.out.print("Enter your choice: ");
        int reportChoice = Integer.parseInt(scanner.nextLine());

        String filterType = null;
        Object filterValue = null;

        switch (reportChoice) {
            case 1 -> {
                filterType = "marital";
                System.out.print("Enter marital status to filter (e.g. Single, Married): ");
                filterValue = scanner.nextLine();
            }
            case 2 -> {
                filterType = "age";
                System.out.print("Enter minimum age to filter: ");
                filterValue = Integer.parseInt(scanner.nextLine());
            }
            default -> {
                System.out.println("Invalid choice.");
                return;
            }
        }

        manager.generateApplicantReport(reportGenerator, filterType, filterValue);
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
        System.out.print("Enter project name to manage officer applications: ");
        String projectName = scanner.nextLine();

        manager.manageOfficerApplication(applicationControl, projectName);
    }
}
