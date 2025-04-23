package controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import ENUM.FlatType;
import database.ProjectList;
import entity.HDBManager;
import entity.Project;
import interfaces.IProjectManagementControl;

public class ProjectManagementControl implements IProjectManagementControl {
    private ProjectList projectDatabase;

    public ProjectManagementControl(ProjectList projectDatabase) {
        this.projectDatabase = projectDatabase;
    }

    @Override
    public void createProject(HDBManager manager, String projectName, String neighbourhood,
            LocalDate applicationOpenDate, LocalDate applicationCloseDate,
            boolean visibility, int officerSlots,
            Map<FlatType, Integer> availableUnits,
            Map<FlatType, Integer> priceInput) {

        if (projectDatabase.getProjects(projectName) != null) {
            System.out.println("A project with name " + projectName + " already exists. Creation aborted.");
            return;
        }

        Map<FlatType, Double> priceMap = new HashMap<>();
        for (Map.Entry<FlatType, Integer> entry : priceInput.entrySet()) {
            priceMap.put(entry.getKey(), entry.getValue().doubleValue());
        }

        Project project = new Project(
            projectName,
            neighbourhood,
            applicationOpenDate,
            applicationCloseDate,
            visibility,
            officerSlots,
            availableUnits,
            priceMap,
            manager,
            null
        );

        projectDatabase.addProject(project);
        System.out.println("Project created: " + project.getProjectName());

        Project assignedProject = projectDatabase.getProjects(projectName);
        if (assignedProject != null && isProjectActive(assignedProject)) {
            System.out.println("You are now managing the project: " + assignedProject.getProjectName());
        } else {
            System.out.println("Failed to assign the project or the project is not active.");
        }
    }

    @Override
    public void editProject(HDBManager manager, String projectName, int choice, Object newValue) {
        Project project = projectDatabase.getProjects(projectName);
        if (project == null || !project.getHdbManager().getNRIC().equals(manager.getNRIC())) {
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

    @Override
    public void removeProject(HDBManager manager, String projectName) {
        Project project = projectDatabase.getProjects(projectName);
        if (project != null && project.getHdbManager().getNRIC().equals(manager.getNRIC())) {
            projectDatabase.removeProject(project);
            System.out.println("Project Removed: " + project.getProjectName());
        } else {
            System.out.println("Project not found or access denied.");
        }
    }

    @Override
    public void toggleProjectVisibility(HDBManager manager, String projectName, boolean isVisible) {
        Project project = projectDatabase.getProjects(projectName);
        if (project != null && project.getHdbManager().getNRIC().equals(manager.getNRIC())) {
            project.setVisibility(isVisible);
            System.out.println("Project visibility updated: " + isVisible);
        } else {
            System.out.println("Project not found or access denied.");
        }
    }

    private boolean isProjectActive(Project project) {
        LocalDate currentDate = LocalDate.now();
        return !currentDate.isAfter(project.getApplicationCloseDate()) && !currentDate.isBefore(project.getApplicationOpenDate());
    }
}
