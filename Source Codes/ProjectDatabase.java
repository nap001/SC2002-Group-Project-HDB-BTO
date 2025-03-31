import java.util.List;
import java.util.ArrayList;

public class ProjectDatabase {
    private List<Project> projects; // List of projects in the database

    // Constructor to initialize the list
    public ProjectDatabase() {
        this.projects = new ArrayList<>();
    }

    // Add a project to the database
    public void addProject(Project project) {
        projects.add(project);
    }

    // Remove a project from the database (can only be called by HDBManager)
    public void removeProject(Project project) {
            projects.remove(project);
    }
    
    // Display details of all projects in the database
    public void displayAllProjects() {
        if (projects.isEmpty()) {
            System.out.println("No projects available.");
            return;
        }
        
        System.out.println("List of Projects:");
        for (Project project : projects) {
            project.displayProjectDetails();
            System.out.println("--------------------------");
        }
    }
    
    public List<Project> getProjectsByManager(HDBManager manager) {
        List<Project> managerProjects = new ArrayList<>();
        for (Project project : projects) {
            if (project.getHdbManager().equals(manager)) {
                managerProjects.add(project);
            }
        }
        return managerProjects;
    }
    // Method to return only visible projects
    public List<Project> getVisibleProjects() {
        List<Project> visibleProjects = new ArrayList<>();
        
        for (Project project : projects) {
            if (project.isVisibility() == true) { // Check visibility
                visibleProjects.add(project);
            }
        }
        
        return visibleProjects;
    }

    // Other methods to query or interact with the projects...
}
