package Collection;

import java.util.ArrayList;
import java.util.List;

import Application.*;
import Interfaces.CSVHandler; // Assuming your interface is under CSV package

public class ProjectList {
    private List<Project> projects;
    private final String csvPath;
    private final CSVHandler<Project> csvHandler;

    public ProjectList(String csvPath, CSVHandler<Project> csvHandler) {
        this.csvPath = csvPath;
        this.csvHandler = csvHandler;
        this.projects = csvHandler.loadFromCSV(csvPath);
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void addProject(Project project) {
        projects.add(project);
    }

    public void removeProject(Project project) {
        projects.remove(project);
    }

    public void saveProjects() {
        csvHandler.saveToCSV(projects, csvPath);
    }

    public Project findByName(String name) {
        for (Project project : projects) {
            if (project.getProjectName().equalsIgnoreCase(name)) {
                return project;
            }
        }
        return null;
    }
}
