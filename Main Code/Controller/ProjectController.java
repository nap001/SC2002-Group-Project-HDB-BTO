package Controller;

import Application.*;
import Boundaries.*;
import Controller.*;
import Entities.*;
import Enum.*;

import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class ProjectController {
	private Map<String, Project> projects;
	
	public ProjectController() {
		projects = new HashMap<>();
	}
	
	 public List<Project> getAllProjects() {
		 List<Project> result = new ArrayList<>();
		    projects.values().forEach(result::add);
		    return result;
	 }
	 
	 public List<Project> getAllVisibleProjects() {
		 List<Project> result = new ArrayList<>();
		 for (Project project:projects.values()) {
			 if (project.isVisibility()) {
				 result.add(project);
			 }
		 }
		 return result;
	 }
	 
	 public List<Project> filterProjectsByRegion(String region) {
		 List<Project> result = new ArrayList<>();
		 for (Project project:projects.values()) {
			 if (project.getRegion().equals(region) || project.isVisibility()){
				 result.add(project);
			 }
		 }
		 return result;
	 }
	 
	 public List<Project> filterProjectsByNeighbourhood(String neigh) {
		 List<Project> result = new ArrayList<>();
		 for (Project project:projects.values()) {
			 if (project.getNeighborhood().equals(neigh) || project.isVisibility()){
				 result.add(project);
			 }
		 }
		 return result;
	 }
	 
	 public List<Project> filterOpenProjects() {
		 List<Project> result = new ArrayList<>();
		 Date todaydate = new Date();
		 for (Project project:projects.values()) {
			 if (project.getOpeningDate().before(todaydate) || todaydate.before(project.getClosingDate()) || project.isVisibility()){
				 result.add(project);
			 }
		 }
		 return result;
	 }
	 
	 public List<Project> getProjectsByManager(HDBManager manager){
		 List<Project> result = new ArrayList<>();
		 for (Project project:projects.values()) {
			 if (project.getManager().equals(manager)){
				 result.add(project);
			 }
		 }
		 return result;
	 }
	 
	 public Project getProjectByProjectName(String Name) {
		 for (Project project:projects.values()) {
			 if (project.getProjectName().equals(Name)) {
				 return project;
			 }
		 }
		 System.out.println("No such project with the name \'" + Name + "\' has been found, please enter another project name.");
		 return null;
	 }
	 
	 public void setProjects(List<Project> projectList) {
	     this.projects.clear();
	     
	     for (Project project : projectList) {
	         this.projects.put(project.getProjectName(), project);
	     }
	 }
	 
	 public Project initialiseProject(Project project) {
		 if (!projects.containsKey(project.getProjectName())){
			 projects.put(project.getProjectName(), project);
			 return project;
		 }
		 return null;
	 }
	 
	 public Project editProject(Project project) {
		 if (project != null && projects.containsKey(project.getProjectName())) {
			 System.out.println("Which part of the project would you like to edit: ");
			 System.out.println("1. Project Name ");
			 System.out.println("2. Region");
			 System.out.println("3. Neighbourhood ");
			 System.out.println("4. Opening Date ");
			 System.out.println("5. Closing Date ");
			 System.out.println("6. Visibility ");
			 Scanner scanner = new Scanner(System.in);
		        int choice = scanner.nextInt();
		        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		        switch (choice) {
		            case 1:
		                System.out.println("Enter the new Project Name: ");
		                String newName = scanner.next();
		                project.setProjectName(newName);
		                break;

		            case 2:
		                System.out.println("Enter the new Region: ");
		                String newRegion = scanner.next();
		                project.setRegion(newRegion);
		                break;

		            case 3:
		                System.out.println("Enter the new Neighbourhood: ");
		                String newNeighbourhood = scanner.next();
		                project.setNeighborhood(newNeighbourhood);
		                break;

		            case 4:
		                System.out.println("Enter the new Opening Date (yyyy-mm-dd): ");
		                String newOpeningDateStr = scanner.next();
		                try {
		                    Date newOpeningDate = dateFormat.parse(newOpeningDateStr);
		                    project.setOpeningDate(newOpeningDate);
		                } catch (Exception e) {
		                    System.out.println("Invalid date format. Please use yyyy-mm-dd.");
		                }
		                break;

		            case 5:
		                System.out.println("Enter the new Closing Date (yyyy-mm-dd): ");
		                String newClosingDateStr = scanner.next();
		                try {
		                    Date newClosingDate = dateFormat.parse(newClosingDateStr);
		                    project.setClosingDate(newClosingDate);
		                } catch (Exception e) {
		                    System.out.println("Invalid date format. Please use yyyy-mm-dd.");
		                }
		                break;

		            case 6:
		                System.out.println("Enter the new Visibility (true or false): ");
		                boolean newVisibility = scanner.nextBoolean();
		                project.setVisibility(newVisibility); // Updating the visibility
		                break;

		            default:
		                System.out.println("Invalid choice! Please select a valid option.");
		                break;
		        }
		        return project;
		    } else {
		        System.out.println("Project not found!");
		        return null;
		 }
	 }
	 
	 public boolean deleteProject(Project project) {
	     if (project != null && projects.containsKey(project.getProjectName())) {
	         projects.remove(project.getProjectName());
	         return true;
	     }
	     
	     return false;
	 }
}
