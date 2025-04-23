package database;
import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import entity.HDBManager;
import entity.Project;

public class ProjectList implements Serializable{
    private final List<Project> projects; // List of projects in the database
    private static final long serialVersionUID = 1L;  // Add serialVersionUID for version control

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
    public Project getProjects(HDBManager manager) {
        for (Project project : projects) {
            if (project.getHdbManager().getName().equals(manager.getName())) {
                return project;
            }
        }
        return null;
    }
    
 // Overloaded method: Get project by name
    public Project getProjects(String projectName) {
        for (Project project : projects) {
            if (project.getProjectName().equalsIgnoreCase(projectName)) {
                return project;
            }
        }
        return null; // Return null if no project with the given name is found
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
