import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class HDBManager extends User {
    public HDBManager(String NRIC, String password, int age, String maritalStatus) {
        super(NRIC, password, age, maritalStatus);
    }

    public void createProject(ProjectDatabase database, int projectID, String projectName, 
                              String neighbourhood, LocalDate applicationOpenDate, 
                              LocalDate applicationCloseDate, boolean visibility, 
                              int officerSlots, Map<FlatType, Integer> availableUnits) {
        Project project = new Project(projectID, projectName, neighbourhood, applicationOpenDate, 
                                      applicationCloseDate, visibility, officerSlots, availableUnits, this);
        database.addProject(project);
        System.out.println("Project created: " + project.getProjectName());
    }

    public void editProject(ProjectDatabase database, int projectID) {
        List<Project> projects = database.getProjectsByManager(this);
        
        // Find the project based on projectID
        Project projectToEdit = null;
        for (Project project : projects) {
            if (project.getProjectID() == projectID) {
                projectToEdit = project;
                break;
            }
        }
        
        if (projectToEdit == null) {
            System.out.println("Project not found or access denied.");
            return;
        }
        
        // Prompt the user for which attribute they want to edit
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which attribute would you like to edit?");
        System.out.println("1. Project Name");
        System.out.println("2. Neighbourhood");
        System.out.println("3. Application Open Date");
        System.out.println("4. Application Close Date");
        System.out.println("5. Visibility");
        System.out.println("6. Officer Slots");
        System.out.println("7. Available Units");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline
        
        // Apply changes based on user input
        switch (choice) {
            case 1:
                System.out.println("Enter new Project Name:");
                String newProjectName = scanner.nextLine();
                projectToEdit.setProjectName(newProjectName);
                break;
            case 2:
                System.out.println("Enter new Neighbourhood:");
                String newNeighbourhood = scanner.nextLine();
                projectToEdit.setNeighbourhood(newNeighbourhood);
                break;
            case 3:
                System.out.println("Enter new Application Open Date (yyyy-mm-dd):");
                String openDateString = scanner.nextLine();
                LocalDate newOpenDate = LocalDate.parse(openDateString);
                projectToEdit.setApplicationOpenDate(newOpenDate);
                break;
            case 4:
                System.out.println("Enter new Application Close Date (yyyy-mm-dd):");
                String closeDateString = scanner.nextLine();
                LocalDate newCloseDate = LocalDate.parse(closeDateString);
                projectToEdit.setApplicationCloseDate(newCloseDate);
                break;
            case 5:
                System.out.println("Enter new Visibility (true/false):");
                boolean newVisibility = scanner.nextBoolean();
                projectToEdit.setVisibility(newVisibility);
                break;
            case 6:
                System.out.println("Enter new Officer Slots:");
                int newOfficerSlots = scanner.nextInt();
                projectToEdit.setOfficerSlots(newOfficerSlots);
                break;
            case 7:
                System.out.println("Enter new Available Units (as a map of FlatType to Integer):");
                Map<FlatType, Integer> newAvailableUnits = new HashMap<>();
                // Example code for adding units (you would need to iterate over available types and units)
                // Assuming the FlatType is an enum and you have a method to input them
                System.out.println("Enter FlatType and available unit number (e.g., 2BHK 5, 3BHK 10):");
                scanner.nextLine(); // Consume newline
                String unitsString = scanner.nextLine();
                // Parse and update the map accordingly
                break;
            default:
                System.out.println("Invalid choice. No changes made.");
                return;
        }
        

        System.out.println("Project updated: " + projectToEdit.getProjectName());
    }

    public void removeProjectFromDatabase(ProjectDatabase database, int projectID) {
        List<Project> projects = database.getProjectsByManager(this);
        for (Project project : projects) {
            if (project.getProjectID() == projectID) {
                database.removeProject(project);;
                System.out.println("Project Removed: " + project.getProjectName());
                return;
            }
        }
    }

    public void toggleProjectVisibility(ProjectDatabase database, int projectID, boolean isVisible) {
        List<Project> projects = database.getProjectsByManager(this);
        
        for (Project project : projects) {
            if (project.getProjectID() == projectID) {
                project.setVisibility(isVisible);
                System.out.println("Project visibility updated: " + isVisible);
                return;
            }
        }
        
        System.out.println("Project not found or access denied.");
    }

    public boolean approveOfficerApplication(ProjectDatabase database, int projectID, HDBOfficer officer, boolean approved) {
        List<Project> projects = database.getProjectsByManager(this); // Get all projects managed by this manager
        
        for (Project project : projects) {
            if (project.getProjectID() == projectID) {
                if (project.getHdbManager().equals(this)) { // Ensure the current manager is the one managing the project
                    
                    // Check pending officer applications
                    Iterator<HDBOfficer> iterator = project.getPendingOfficerApplications().iterator();
                    
                    while (iterator.hasNext()) {
                        HDBOfficer pendingOfficer = iterator.next();
                        
                        // If the officer is the one we are processing
                        if (pendingOfficer.equals(officer)) {
                            if (approved) {
                                // If approved, add the officer to the hdbOfficers list
                                project.approveOfficerApplication(officer);
                            }
                            // Whether approved or not, remove from pending applications
                            iterator.remove();
                            return true; // Success, officer is processed
                        }
                    }
                }
            }
        }
        
        return false; // Project not found or access denied
    }

}
