package Entities;

import Application.Application.ApplicationStatus;
import Controller.ReadCSV;
import Application.Project;
import java.util.List;
import java.util.ArrayList;

public class HDBManager extends User{
	private List<Project> projectsManaged;
	
	
	public HDBManager(String Name, String NRIC, int age, String maritalStatus, String password, String userType) {
        super(Name, NRIC, age, maritalStatus, password, userType);
        projectsManaged = new ArrayList<>();
    }

    public void createProject(String projectName, String neighborhood, String region, int numUnitsType1, int sellingPriceType1,
                              int numUnitsType2, int sellingPriceType2, String openingDate, String closingDate, int officerSlot) {
        Project newProject = new Project(projectName, neighborhood, region, numUnitsType1, sellingPriceType1, numUnitsType2,
                sellingPriceType2, openingDate, closingDate, this.getName(), officerSlot, new ArrayList<>());
        projectsManaged.add(newProject);
        System.out.println("Project " + projectName + " has been created.");
    }

    public void editProject(String projectName, String newNeighborhood, String newRegion, int newNumUnitsType1, int newSellingPriceType1,
                            int newNumUnitsType2, int newSellingPriceType2, String newOpeningDate, String newClosingDate) {
        Project projectToEdit = findProject(projectName);
        if (projectToEdit != null) {
            projectToEdit.setNeighborhood(newNeighborhood);
            projectToEdit.setRegion(newRegion);
            projectToEdit.setNumUnitsType1(newNumUnitsType1);
            projectToEdit.setSellingPriceType1(newSellingPriceType1);
            projectToEdit.setNumUnitsType2(newNumUnitsType2);
            projectToEdit.setSellingPriceType2(newSellingPriceType2);
            projectToEdit.setOpeningDate(newOpeningDate);
            projectToEdit.setClosingDate(newClosingDate);
            System.out.println("Project " + projectName + " has been updated.");
        } else {
            System.out.println("Project not found.");
        }
    }

    // Delete an existing project
    public void deleteProject(String projectName) {
        Project projectToDelete = findProject(projectName);
        if (projectToDelete != null) {
            projectsManaged.remove(projectToDelete);
            System.out.println("Project " + projectName + " has been deleted.");
        } else {
            System.out.println("Project not found.");
        }
    }

    // Toggle visibility of a project
    public void toggleProjectVisibility(String projectName, boolean visibility) {
        Project project = findProject(projectName);
        if (project != null) {
            project.setVisibility(visibility);
            System.out.println("Visibility for " + projectName + " has been " + (visibility ? "turned on." : "turned off."));
        } else {
            System.out.println("Project not found.");
        }
    }

    // View all projects created by this manager
    public void viewProjects() {
        if (projectsManaged.isEmpty()) {
            System.out.println("You have not created any projects yet.");
        } else {
            for (Project project : projectsManaged) {
                System.out.println(project);
            }
        }
    }

    // Approve or reject officer registration
    public void manageOfficerRegistration(String projectName, String officerNRIC, boolean isApproved) {
        Project project = findProject(projectName);
        if (project != null) {
            if (isApproved) {
                project.addOfficer(officerNRIC);
                System.out.println("Officer " + officerNRIC + " has been approved for project " + projectName + ".");
            } else {
                System.out.println("Officer " + officerNRIC + " has been rejected for project " + projectName + ".");
            }
        } else {
            System.out.println("Project not found.");
        }
    }

    // Approve or reject applicant's application
    public void manageApplicantApplication(String projectName, String applicantNRIC, boolean isApproved) {
        Project project = findProject(projectName);
        if (project != null) {
            Application application = project.findApplicationByNRIC(applicantNRIC);
            if (application != null) {
                if (isApproved) {
                    application.setStatus(Application.Status.SUCCESSFUL);
                    System.out.println("Application for " + applicantNRIC + " has been approved for project " + projectName + ".");
                } else {
                    application.setStatus(Application.Status.UNSUCCESSFUL);
                    System.out.println("Application for " + applicantNRIC + " has been rejected for project " + projectName + ".");
                }
            } else {
                System.out.println("Applicant not found.");
            }
        } else {
            System.out.println("Project not found.");
        }
    }

    // Approve or reject applicant's withdrawal request
    public void manageApplicantWithdrawal(String projectName, String applicantNRIC, boolean isApproved) {
        Project project = findProject(projectName);
        if (project != null) {
            Application application = project.findApplicationByNRIC(applicantNRIC);
            if (application != null) {
                if (isApproved) {
                    application.setStatus(Application.Status.UNSUCCESSFUL);
                    System.out.println("Application withdrawal for " + applicantNRIC + " has been approved.");
                } else {
                    System.out.println("Application withdrawal for " + applicantNRIC + " has been rejected.");
                }
            } else {
                System.out.println("Applicant not found.");
            }
        } else {
            System.out.println("Project not found.");
        }
    }

    // Generate report of applicants with flat bookings
    public void generateReport() {
        System.out.println("Generating report of applicants with their flat booking details...");
        for (Project project : projectsManaged) {
            for (Application app : project.getApplications()) {
                if (app.getStatus() == Application.Status.BOOKED) {
                    System.out.println("Applicant: " + app.getNric() + ", Project: " + app.getProjectName() + ", Flat Type: " + app.getFlatType());
                }
            }
        }
    }

    // View all enquiries of the manager's projects
    public void viewEnquiries() {
        for (Project project : projectsManaged) {
            System.out.println("Enquiries for project " + project.getProjectName() + ":");
            for (Enquiry enquiry : project.getEnquiries()) {
                System.out.println(enquiry);
            }
        }
    }

    // Find project by name
    private Project findProject(String projectName) {
        for (Project project : projectsManaged) {
            if (project.getProjectName().equalsIgnoreCase(projectName)) {
                return project;
            }
        }
        return null;
    }

}
