package Controller;

import java.util.ArrayList;
import java.util.List;

import Boundary.HDBManager;
import Boundary.HDBOfficer;
import Database.ProjectList;
import Entity.Project;
import Interface.IProjectQueryControl;

public class ProjectQueryControl implements IProjectQueryControl {
    private ProjectList projectDatabase;

    public ProjectQueryControl(ProjectList projectDatabase) {
        this.projectDatabase = projectDatabase;
    }

    @Override
    public Project getProject(String projectName) {
        return projectDatabase.getProjects(projectName);
    }

    @Override
    public Project filterProjectsByManager(HDBManager manager) {
        return projectDatabase.getProjects(manager);
    }

    @Override
    public boolean projectExists(String projectName) {
        return projectDatabase.getAllProjects().stream()
                .anyMatch(p -> p.getProjectName().equalsIgnoreCase(projectName));
    }

    @Override
    public List<Project> filterProjects(String filterType, Object filterValue) {
        List<Project> filtered = new ArrayList<>();
        for (Project project : projectDatabase.getAllProjects()) {
            switch (filterType.toLowerCase()) {
                case "neighbourhood":
                    if (project.getNeighbourhood().equalsIgnoreCase((String) filterValue)) {
                        filtered.add(project);
                    }
                    break;
                case "visibility":
                    if (project.isVisibility() == (Boolean) filterValue) {
                        filtered.add(project);
                    }
                    break;
                case "manager":
                    if (project.getHdbManager().equals(filterValue)) {
                        filtered.add(project);
                    }
                    break;
            }
        }
        return filtered;
    }

    @Override
    public List<Project> getVisibleProjects() {
        return projectDatabase.getVisibleProjects();
    }

    @Override
    public Project getProjectByOfficerNRIC(String officerNRIC) {
        for (Project project : projectDatabase.getAllProjects()) {
            for (HDBOfficer officer : project.getHdbOfficers()) {
                if (officer.getNRIC().equals(officerNRIC)) {
                    return project;
                }
            }
        }
        return null;
    }
}
