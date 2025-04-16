package Controller;

import Application.*;
import Boundaries.*;
import Collection.ProjectList;
import Entities.*;
import Enum.*;

import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectController {
    private ProjectList projectList;

    public ProjectController(ProjectList projectList) {
        this.projectList = projectList;
    }

    public List<Project> getAllProjects() {
        return projectList.getProjects();
    }

    public List<Project> getAllVisibleProjects() {
        List<Project> result = new ArrayList<>();
        for (Project project : projectList.getProjects()) {
            if (project.isVisibility()) {
                result.add(project);
            }
        }
        return result;
    }

    public List<Project> filterProjectsByRegion(String region) {
        List<Project> result = new ArrayList<>();
        for (Project project : projectList.getProjects()) {
            if (project.getRegion().equals(region) || project.isVisibility()) {
                result.add(project);
            }
        }
        return result;
    }

    public List<Project> filterProjectsByNeighbourhood(String neigh) {
        List<Project> result = new ArrayList<>();
        for (Project project : projectList.getProjects()) {
            if (project.getNeighborhood().equals(neigh) || project.isVisibility()) {
                result.add(project);
            }
        }
        return result;
    }

    public List<Project> filterOpenProjects() {
        List<Project> result = new ArrayList<>();
        Date todaydate = new Date();
        for (Project project : projectList.getProjects()) {
            if (project.getOpeningDate().before(todaydate)
                    || todaydate.before(project.getClosingDate())
                    || project.isVisibility()) {
                result.add(project);
            }
        }
        return result;
    }

    public List<Project> getProjectsByManager(HDBManager manager) {
        List<Project> result = new ArrayList<>();
        for (Project project : projectList.getProjects()) {
            if (project.getManager().equals(manager)) {
                result.add(project);
            }
        }
        return result;
    }

    public Project getProjectByProjectName(String name) {
        Project project = projectList.findByName(name);
        if (project == null) {
            System.out.println("No such project with the name '" + name + "' has been found.");
        }
        return project;
    }

    public void setProjects(List<Project> projects) {
        this.projectList.getProjects().clear();
        this.projectList.getProjects().addAll(projects);
    }

    public Project initialiseProject(Project project) {
        if (projectList.findByName(project.getProjectName()) == null) {
            projectList.addProject(project);
            return project;
        }
        return null;
    }

    public Project editProject(Project project) {
        if (project != null && projectList.findByName(project.getProjectName()) != null) {
            Scanner scanner = new Scanner(System.in);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            System.out.println("Which part of the project would you like to edit: ");
            System.out.println("1. Project Name ");
            System.out.println("2. Region");
            System.out.println("3. Neighbourhood ");
            System.out.println("4. Opening Date ");
            System.out.println("5. Closing Date ");
            System.out.println("6. Visibility ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // clear newline

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter the new Project Name: ");
                        project.setProjectName(scanner.nextLine());
                        break;
                    case 2:
                        System.out.print("Enter the new Region: ");
                        project.setRegion(scanner.nextLine());
                        break;
                    case 3:
                        System.out.print("Enter the new Neighbourhood: ");
                        project.setNeighborhood(scanner.nextLine());
                        break;
                    case 4:
                        System.out.print("Enter the new Opening Date (yyyy-mm-dd): ");
                        project.setOpeningDate(dateFormat.parse(scanner.nextLine()));
                        break;
                    case 5:
                        System.out.print("Enter the new Closing Date (yyyy-mm-dd): ");
                        project.setClosingDate(dateFormat.parse(scanner.nextLine()));
                        break;
                    case 6:
                        System.out.print("Enter the new Visibility (true or false): ");
                        project.setVisibility(Boolean.parseBoolean(scanner.nextLine()));
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            return project;
        }
        System.out.println("Project not found.");
        return null;
    }

    public boolean deleteProject(Project project) {
        if (project != null && projectList.findByName(project.getProjectName()) != null) {
            projectList.removeProject(project);
            return true;
        }
        return false;
    }
}
