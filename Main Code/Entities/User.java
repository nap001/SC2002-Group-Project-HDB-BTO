package Entities;
import java.util.List;
import java.util.Scanner;

import Application.Application;
import Application.Project;
import Controller.ReadCSV;

public class User {
	protected String Name;
	protected String NRIC;
	protected int age;
	protected String maritialStatus;
	protected String password;
	protected String userType;
	private List<Project> ProjectList;
	private List<User> users;
	Scanner sc = new Scanner(System.in);
	
	//Constructors
	public User() {}
	public User(String Name, String NRIC, int age, String maritialStatus,String password, String userType) {
		this.Name = Name;
		this.NRIC = NRIC;
		this.age = age;
		this.maritialStatus = maritialStatus;
		this.password = password;
		this.userType = userType;
	}
	
	//Setters and Getters
	public String getName() {return this.Name;}
	public String getNRIC(){return this.NRIC;}
	public int getAge() {return this.age;}
	public String getMaritialStatus() {return this.maritialStatus;}
	public String getpassword() {return this.password;}
	public String getuserType() {return this.userType;}
	public String getPassword() {return password;}
	public String getUserType() {return userType;}
	

	
	
	public void registerForProject(String projectName) {
	    boolean projectFound = false;
	    // Loop through the list of projects to find the project by name
	    for (List<Project> projectList : projectsList) {
	        for (Project project : projectList) {
	            if (project.getProjectName().equals(projectName)) {
	                // Register the officer for this project
	                projectFound = true;
	                System.out.println("You have successfully registered for the project: " + projectName);
	                // Add registration logic here if needed
	                break;
	            }
	        }
	        if (projectFound) break;
	    }
	    if (!projectFound) {
	        System.out.println("Project not found: " + projectName);
	    }
	}

	public void flatSelection(int applicationID) {
	    // Loop through the applications to find the specific application
	    Application app = findApplicationByID(applicationID);
	    if (app != null) {
	        // Check if the application status is successful before allowing flat selection
	        if (app.getStatus() == Application.Status.SUCCESSFUL) {
	            System.out.println("Select a flat type (2-Room or 3-Room): ");
	            String flatType = sc.nextLine();
	            if (flatType.equals("2-Room") || flatType.equals("3-Room")) {
	                // Update the application status to "Booked"
	                app.bookFlat(flatType);
	                System.out.println("Flat selection for application " + applicationID + " is completed.");
	            } else {
	                System.out.println("Invalid flat type selected.");
	            }
	        } else {
	            System.out.println("The application is not successful, flat selection cannot proceed.");
	        }
	    } else {
	        System.out.println("Application with ID " + applicationID + " not found.");
	    }
	}
	
	public void generateReceipt(int applicationID) {
	    Application app = findApplicationByID(applicationID);
	    if (app != null && app.getStatus() == Application.Status.BOOKED) {
	        System.out.println("Generating receipt for application ID: " + applicationID);
	        // Logic to generate receipt (for example, output flat type, price, and applicant details)
	        System.out.println("Receipt: " + app.getFlatType() + " flat at " + app.getProject().getProjectName());
	    } else {
	        System.out.println("Application with ID " + applicationID + " is not yet booked.");
	    }
	}
	
	public void viewAvailableProjectsAsApplicant() {
	    // Loop through all projects and display only those that are visible
	    boolean foundAvailableProject = false;
	    for (List<Project> projectList : projectsList) {
	        for (Project project : projectList) {
	            if (project.isVisible()) { // Assuming there's a method isVisible() in Project
	                System.out.println("Project: " + project.getProjectName());
	                foundAvailableProject = true;
	            }
	        }
	    }
	    if (!foundAvailableProject) {
	        System.out.println("No available projects at the moment.");
	    }
	}
	
	public void applyForProject(String projectName) {
	    boolean projectFound = false;
	    for (List<Project> projectList : projectsList) {
	        for (Project project : projectList) {
	            if (project.getProjectName().equals(projectName)) {
	                // Check if the project is visible
	                if (project.isVisible()) {
	                    Application newApplication = new Application(currentUser.getNRIC(), project, "2-Room", true); // Example flatType "2-Room"
	                    project.addApplication(newApplication);
	                    System.out.println("You have successfully applied for the project: " + projectName);
	                } else {
	                    System.out.println("The project is not currently visible for application.");
	                }
	                projectFound = true;
	                break;
	            }
	        }
	        if (projectFound) break;
	    }
	    if (!projectFound) {
	        System.out.println("Project " + projectName + " not found.");
	    }
	}
	
	public void createEnquiry(String projectName, String enquiryText) {
	    boolean projectFound = false;
	    for (List<Project> projectList : projectsList) {
	        for (Project project : projectList) {
	            if (project.getProjectName().equals(projectName)) {
	                // Create an enquiry and add it to the project
	                Enquiry enquiry = new Enquiry(enquiryText, currentUser.getNRIC());
	                project.addEnquiry(enquiry);
	                System.out.println("Enquiry added successfully for project: " + projectName);
	                projectFound = true;
	                break;
	            }
	        }
	        if (projectFound) break;
	    }
	    if (!projectFound) {
	        System.out.println("Project " + projectName + " not found.");
	    }
	}
	
	public void viewEnquiries() {
	    // Loop through the projects and show enquiries related to the current user
	    boolean foundEnquiries = false;
	    for (List<Project> projectList : projectsList) {
	        for (Project project : projectList) {
	            for (Enquiry enquiry : project.getEnquiries()) {
	                if (enquiry.getApplicantNric().equals(currentUser.getNRIC())) {
	                    System.out.println(enquiry);
	                    foundEnquiries = true;
	                }
	            }
	        }
	    }
	    if (!foundEnquiries) {
	        System.out.println("You have not made any enquiries yet.");
	    }
	}
	
	public void deleteEnquiry(int enquiryID) {
	    boolean enquiryFound = false;
	    for (List<Project> projectList : projectsList) {
	        for (Project project : projectList) {
	            for (Enquiry enquiry : project.getEnquiries()) {
	                if (enquiry.getEnquiryIndex() == enquiryID && enquiry.getApplicantNric().equals(currentUser.getNRIC())) {
	                    project.getEnquiries().remove(enquiry);  // Remove the enquiry
	                    System.out.println("Enquiry deleted successfully.");
	                    enquiryFound = true;
	                    break;
	                }
	            }
	        }
	        if (enquiryFound) break;
	    }
	    if (!enquiryFound) {
	        System.out.println("Enquiry with ID " + enquiryID + " not found.");
	    }
	}
	
    public Application findApplicationByNRIC(String nric) {
        // Iterate through the list of applications
    	ReadCSV readCSV = new ReadCSV();
    	List<Application> applications = readCSV.loadApplicationsFromCSV();
 
        for (Application application : applications) {
            // Check if the NRIC matches the applicant's NRIC in the application
            if (application.getNric().equals(nric)) {
                return application;  // Return the application if found
            }
        }
        return null;
    }
    
    public Project findProjectByName(String projectName) {
    	ReadCSV readCSV = new ReadCSV();
    	List<Project> projects = readCSV.loadProjectsFromCSV();
        for (Project project : projects) {
            if (project.getProjectName().equalsIgnoreCase(projectName)) {  // Check if project name matches
                return project;  // Return the project if found
            }
        }
        return null;  // Return null if no project is found with the given name
    }

}
