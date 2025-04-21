//package Controller;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import Boundary.HDBManager;
//import Boundary.HDBOfficer;
//import Database.ProjectList;
//import ENUM.FlatType;
//import Entity.Project;
//import Interface.IApplicantProjectControl;
//import Interface.IProjectControl;
//
//public class ProjectControl implements IProjectControl, IApplicantProjectControl{
//    private ProjectList projectDatabase;
//
//    public ProjectControl(ProjectList projectDatabase) {
//        this.projectDatabase = projectDatabase;
//    }
//    public void createProject(HDBManager manager, String projectName, String neighbourhood,
//            LocalDate applicationOpenDate, LocalDate applicationCloseDate,
//            boolean visibility, int officerSlots,
//            Map<FlatType, Integer> availableUnits,
//            Map<FlatType, Integer> priceInput) {
//		
//		if (projectDatabase.getProjects(projectName) != null) {
//		System.out.println("A project with name " + projectName + " already exists. Creation aborted.");
//		return;
//		}
//		
//		// Convert Integer price input to Double for the constructor
//		Map<FlatType, Double> priceMap = new HashMap<>();
//		for (Map.Entry<FlatType, Integer> entry : priceInput.entrySet()) {
//		priceMap.put(entry.getKey(), entry.getValue().doubleValue());
//		}
//		
//		Project project = new Project(
//		projectName,
//		neighbourhood,
//		applicationOpenDate,
//		applicationCloseDate,
//		visibility,
//		officerSlots,
//		availableUnits,
//		priceMap,
//		manager,
//		null
//		);
//		
//		projectDatabase.addProject(project);
//		System.out.println("Project created: " + project.getProjectName());
//		
//		Project assignedProject = projectDatabase.getProjects(projectName);
//		if (assignedProject != null && isProjectActive(assignedProject)) {
//		System.out.println("You are now managing the project: " + assignedProject.getProjectName());
//		} else {
//		System.out.println("Failed to assign the project or the project is not active.");
//		}
//}
//
//
//    public void editProject(HDBManager manager, String projectName, int choice, Object newValue) {
//        Project project = projectDatabase.getProjects(projectName);
//        if (project == null || !project.getHdbManager().equals(manager)) {
//            System.out.println("Project not found or access denied.");
//            return;
//        }
//
//        switch (choice) {
//            case 1 -> project.setProjectName((String) newValue);
//            case 2 -> project.setNeighbourhood((String) newValue);
//            case 3 -> project.setApplicationOpenDate((LocalDate) newValue);
//            case 4 -> project.setApplicationCloseDate((LocalDate) newValue);
//            case 5 -> project.setVisibility((boolean) newValue);
//            case 6 -> project.setOfficerSlots((int) newValue);
//            default -> {
//                System.out.println("Invalid choice. No changes made.");
//                return;
//            }
//        }
//        System.out.println("Project updated: " + project.getProjectName());
//    }
//
//    public void removeProject(HDBManager manager, String projectName) {
//        Project project = projectDatabase.getProjects(projectName);
//        if (project != null && project.getHdbManager().equals(manager)) {
//            projectDatabase.removeProject(project);
//            System.out.println("Project Removed: " + project.getProjectName());
//        } else {
//            System.out.println("Project not found or access denied.");
//        }
//    }
//
//    public void toggleProjectVisibility(HDBManager manager, String projectName, boolean isVisible) {
//        Project project = projectDatabase.getProjects(projectName);
//        if (project != null && project.getHdbManager().equals(manager)) {
//            project.setVisibility(isVisible);
//            System.out.println("Project visibility updated: " + isVisible);
//        } else {
//            System.out.println("Project not found or access denied.");
//        }
//    }
//
//    @Override
//    public List<Project> filterProjects(String filterType, Object filterValue) {
//        List<Project> filtered = new ArrayList<>();
//
//        for (Project project : projectDatabase.getAllProjects()) {
//            switch (filterType.toLowerCase()) {
//                case "neighbourhood":
//                    if (project.getNeighbourhood().equalsIgnoreCase((String) filterValue)) {
//                        filtered.add(project);
//                    }
//                    break;
//                case "visibility":
//                    if (project.isVisibility() == (Boolean) filterValue) {
//                        filtered.add(project);
//                    }
//                    break;
//                case "manager":
//                    if (project.getHdbManager() != null &&
//                        project.getHdbManager().equals(filterValue)) {
//                        filtered.add(project);
//                    }
//                    break;
//                // Add more filters as needed
//            }
//        }
//
//        return filtered;
//    }
//
//    public void viewAllProject() {
//        List<Project> projects = projectDatabase.getAllProjects();
//        for (Project project : projects) {
//            project.displayProjectDetails();
//        }
//    }
//    @Override
//    public void viewAssignedProject(HDBOfficer officer) {
//        Project project = getProjectByOfficerNRIC(officer.getNRIC());
//        if (project != null) {
//            project.displayProjectDetails(); 
//        } else {
//            System.out.println("You are not currently assigned to any project.");
//        }
//    }
//
//
//    // Helper method to check if the project is active based on its open and close dates
//    private boolean isProjectActive(Project project) {
//        LocalDate currentDate = LocalDate.now();
//        return !currentDate.isAfter(project.getApplicationCloseDate()) && !currentDate.isBefore(project.getApplicationOpenDate());
//    }
//
//    // Fetch a project by name from the database
//    public Project getProject(String projectName) {
//        return projectDatabase.getProjects(projectName);
//    }
//
//    public Project filterProjectsByManager(HDBManager manager) {
//        return projectDatabase.getProjects(manager);
//    }
//
//    public boolean projectExists(String projectName) {
//        return projectDatabase.getAllProjects().stream()
//                .anyMatch(p -> p.getProjectName().equalsIgnoreCase(projectName));
//    }
//	public void viewVisibleProject() {
//        List<Project> projects = projectDatabase.getAllProjects();
//        for (Project project : projects) {
//        	if (project.isVisibility())
//            project.displayProjectDetails();
//        }		
//	}
//	public List<Project> getVisibleProjects() {
//	    return projectDatabase.getVisibleProjects();
//	}
//	
//	@Override
//	public Project getProjectByOfficerNRIC(String officerNRIC) {
//	    for (Project project : projectDatabase.getAllProjects()) {
//	        for (HDBOfficer officer : project.getHdbOfficers()) {
//	            if (officer.getNRIC().equals(officerNRIC)) {
//	                return project;
//	            }
//	        }
//	    }
//	    return null;
//	}
//
//
//}
