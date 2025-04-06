import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ProjectControl {
    private ProjectList projectDatabase;

    public ProjectControl(ProjectList projectDatabase) {
        this.projectDatabase = projectDatabase;
    }

    public void createProject(HDBManager manager, int projectID, String projectName, String neighbourhood, 
                              LocalDate applicationOpenDate, LocalDate applicationCloseDate, 
                              boolean visibility, int officerSlots, Map<FlatType, Integer> availableUnits) {
        // Check for existing project with the same ID
        if (projectDatabase.getProjects(projectID) != null) {
            System.out.println("A project with ID " + projectID + " already exists. Creation aborted.");
            return;
        }


        // Create the project with the provided information
        Project project = new Project(projectID, projectName, neighbourhood, applicationOpenDate,
                                      applicationCloseDate, visibility, officerSlots, availableUnits, manager);
        projectDatabase.addProject(project);
        System.out.println("Project created: " + project.getProjectName());

        // Automatically assign the manager to manage the project
        Project assignedProject = projectDatabase.getProjects(projectID); // Fetch the project from database
        if (assignedProject != null && isProjectActive(assignedProject)) {
            manager.setCurrentlyManagedProject(assignedProject); // Automatically manage the project
            System.out.println("You are now managing the project: " + assignedProject.getProjectName());
        } else {
            System.out.println("Failed to assign the project or the project is not active.");
        }
    }

    public void editProject(HDBManager manager, int projectID, int choice, Object newValue) {
        Project project = projectDatabase.getProjects(projectID);
        if (project == null || !project.getHdbManager().equals(manager)) {
            System.out.println("Project not found or access denied.");
            return;
        }

        switch (choice) {
            case 1 -> project.setProjectName((String) newValue);
            case 2 -> project.setNeighbourhood((String) newValue);
            case 3 -> project.setApplicationOpenDate((LocalDate) newValue);
            case 4 -> project.setApplicationCloseDate((LocalDate) newValue);
            case 5 -> project.setVisibility((boolean) newValue);
            case 6 -> project.setOfficerSlots((int) newValue);
            default -> {
                System.out.println("Invalid choice. No changes made.");
                return;
            }
        }
        System.out.println("Project updated: " + project.getProjectName());
    }

    public void removeProject(HDBManager manager, int projectID) {
        Project project = projectDatabase.getProjects(projectID);
        if (project != null && project.getHdbManager().equals(manager)) {
        	projectDatabase.removeProject(project);
            System.out.println("Project Removed: " + project.getProjectName());
        } else {
            System.out.println("Project not found or access denied.");
        }
    }

    public void toggleProjectVisibility(HDBManager manager, int projectID, boolean isVisible) {
        Project project = projectDatabase.getProjects(projectID);
        if (project != null && project.getHdbManager().equals(manager)) {
            project.setVisibility(isVisible);
            System.out.println("Project visibility updated: " + isVisible);
        } else {
            System.out.println("Project not found or access denied.");
        }
    }
    
    public void viewAllProject() {
    	List<Project> projects = projectDatabase.getAllProjects();
    	for (Project project:projects) {
    		project.displayProjectDetails();
    	}
    }

    // Helper method to check if the project is active based on its open and close dates
    private boolean isProjectActive(Project project) {
        LocalDate currentDate = LocalDate.now();
        return !currentDate.isAfter(project.getApplicationCloseDate()) && !currentDate.isBefore(project.getApplicationOpenDate());
    }

    // Fetch a project by ID from the database
    public Project getProject(int projectID) {
        return projectDatabase.getProjects(projectID); // Assume `getProjects` fetches the project by its ID
    }
    
    public List<Project> filterProjectsByManager(HDBManager manager) {
        return projectDatabase.getProjects(manager);
    }
}
