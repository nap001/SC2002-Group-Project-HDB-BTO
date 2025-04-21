package Controller;

import java.util.List;

import Boundary.HDBOfficer;
import Database.ProjectList;
import Entity.Project;
import Interface.IProjectViewControl;

public class ProjectViewControl implements IProjectViewControl {
    private ProjectList projectDatabase;

    public ProjectViewControl(ProjectList projectDatabase) {
        this.projectDatabase = projectDatabase;
    }

    @Override
    public void viewAllProject() {
        List<Project> projects = projectDatabase.getAllProjects();
        for (Project project : projects) {
            project.displayProjectDetails();
        }
    }

    @Override
    public void viewVisibleProject() {
        List<Project> projects = projectDatabase.getAllProjects();
        for (Project project : projects) {
            if (project.isVisibility()) {
                project.displayProjectDetails();
            }
        }
    }

    @Override
    public void viewAssignedProject(HDBOfficer officer) {
        Project project = getProjectByOfficerNRIC(officer.getNRIC());
        if (project != null) {
            project.displayProjectDetails();
        } else {
            System.out.println("You are not currently assigned to any project.");
        }
    }

    private Project getProjectByOfficerNRIC(String officerNRIC) {
        return new ProjectQueryControl(projectDatabase).getProjectByOfficerNRIC(officerNRIC);
    }
}
