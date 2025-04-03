import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class ProjectList {
    private final List<Project> projects; // List of projects in the database

    // Constructor to initialize the list
    public ProjectList() {
        this.projects = new ArrayList<>();
    }

    // Add a project to the database
    public void addProject(Project project) {
        if (project != null) {
            projects.add(project);
        }
    }

    // Remove a project from the database
    public boolean removeProject(Project project) {
        return projects.remove(project);
    }

    // Return all projects (read-only)
    public List<Project> getAllProjects() {
        return Collections.unmodifiableList(projects);
    }

    // Overloaded method: Get projects by manager
    public List<Project> getProjects(HDBManager manager) {
        List<Project> managerProjects = new ArrayList<>();
        for (Project project : projects) {
            if (project.getHdbManager().equals(manager)) {
                managerProjects.add(project);
            }
        }
        return Collections.unmodifiableList(managerProjects);
    }

    // Overloaded method: Get project by ID
    public Project getProjects(int projectID) {
        for (Project project : projects) {
            if (project.getProjectID() == projectID) {
                return project;
            }
        }
        return null; // Return null if no project is found
    }
    // Return only visible projects
    public List<Project> getVisibleProjects() {
        List<Project> visibleProjects = new ArrayList<>();
        for (Project project : projects) {
            if (project.isVisibility()) { // Simplified boolean check
                visibleProjects.add(project);
            }
        }
        return Collections.unmodifiableList(visibleProjects);
    }
}
