package Boundary;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import Controller.EnquiryControl;
import Controller.ManagerApplicationControl;
import Controller.ProjectControl;
import Controller.ReportGenerator;
import ENUM.FlatType;
import Entity.Enquiry;
import Entity.Project;
import Entity.Report;
import Interface.EnquiryViewReply;
import Interface.ProjectView;

public class HDBManager extends User implements ProjectView, EnquiryViewReply, Serializable{
    private Project currentlyManagedProject;
    private Report generatedReport;
    private static final long serialVersionUID = 1L;  // Add serialVersionUID for version control

    public Report getGeneratedReport() {
		return generatedReport;
	}

	public void setGeneratedReport(Report generatedReport) {
		this.generatedReport = generatedReport;
	}

	@Override
    public String getRole() {
        return "Manager";
    }

    public HDBManager(String NRIC, String password, int age, String maritalStatus, String name) {
        super(NRIC, password, age, maritalStatus, name);
        this.currentlyManagedProject = null;
    }
   //Project Management//
    public void createProject(ProjectControl projectControl, String projectName, String neighbourhood,
            LocalDate applicationOpenDate, LocalDate applicationCloseDate,
            boolean visibility, int officerSlots,
            Map<FlatType, Integer> unitCountMap,
            Map<FlatType, Integer> priceInput) {
        if (currentlyManagedProject != null) {
            System.out.println("Cannot create a new project. You are already managing a project.");
            return;
        }
        projectControl.createProject(
            this, projectName, neighbourhood, applicationOpenDate,
            applicationCloseDate, visibility, officerSlots, unitCountMap, priceInput);
    }

    public void removeProject(ProjectControl projectControl, String projectName) {
        if (isManagingProject(projectName)) {
            projectControl.removeProject(this, projectName);
            currentlyManagedProject = null;
        } else {
            System.out.println("You are not managing this project.");
        }
    }
    
    public void editProject(ProjectControl projectControl, String projectName, int choice, Object newValue) {
        projectControl.editProject(this, projectName, choice, newValue);
    }
    
    public void toggleProjectVisibility(ProjectControl projectControl, String projectName, boolean isVisible) {
        if (isManagingProject(projectName)) {
            projectControl.toggleProjectVisibility(this, projectName, isVisible);
        } else {
            System.out.println("You are not managing this project.");
        }
    }

    @Override
    public void viewAllProject(ProjectControl projectControl) {
        projectControl.viewAllProject();
    }
    
    public void viewMyProjects(ProjectControl projectControl) {
        if (this.getCurrentlyManagedProject() == null) {
            System.out.println("You have not created any projects.");
        } else {
        	this.getCurrentlyManagedProject().displayProjectDetails();
            }
        }
    
    
    // Officer and Applicant Applications management//

    public boolean manageOfficerApplication(ManagerApplicationControl applicationManagement, String projectName) {
        if (isManagingProject(projectName)) {
            return applicationManagement.manageOfficerRegistration(this, projectName);
        }
        System.out.println("You are not managing this project.");
        return false;
    }
    
    public void approveApplicantApplications(ManagerApplicationControl managerApplicationControl, ProjectControl projectControl) {
        boolean success = managerApplicationControl.approveApplicantApplication(this, projectControl);

        if (success) {
            System.out.println("Application approvals processed successfully.");
        } else {
            System.out.println("Application approval process failed.");
        }
    }
    
    public void approveApplicantWithdrawals(ManagerApplicationControl managerApplicationControl) {
        boolean success = managerApplicationControl.approveWithdrawals(this);

        if (success) {
            System.out.println("Withdrawal approvals processed successfully.");
        } else {
            System.out.println("Withdrawal approval process failed.");
        }
    }
    
    // Enquiry Management//
    @Override
    public void replyToEnquiries(EnquiryControl enquiryControl) {
        enquiryControl.replyToEnquiries(this); // Uses the current HDBManager object
    }

    @Override
    public void viewAllEnquiries(EnquiryControl enquiryControl) {
        enquiryControl.viewAllEnquiries();
    }

    //Report Management//
    public void generateApplicantReport(ReportGenerator reportGenerator, String filterType, Object filterValue) {
        if (currentlyManagedProject == null) {
            System.out.println("You are not managing any project. Please select a project first.");
            return;
        }
        this.setGeneratedReport(reportGenerator.generateApplicantReport(currentlyManagedProject, filterType, filterValue));
    }
    public void displayGeneratedReport(ReportGenerator reportGenerator) {
    	reportGenerator.displayReport(generatedReport);
    }

    
    //Owning Project checkings//
    public boolean isManagingProject(String projectName) {
        return currentlyManagedProject != null && currentlyManagedProject.getProjectName().equals(projectName);
    }

    public boolean isProjectActive(Project project) {
        LocalDate currentDate = LocalDate.now();
        return !currentDate.isAfter(project.getApplicationCloseDate())
                && !currentDate.isBefore(project.getApplicationOpenDate());
    }

    public Project getCurrentlyManagedProject() {
        return currentlyManagedProject;
    }

    public void setCurrentlyManagedProject(Project project) {
        this.currentlyManagedProject = project;
    }

    public boolean stopManagingProject() {
        if (currentlyManagedProject != null) {
            System.out.println("You are no longer managing the project: " + currentlyManagedProject.getProjectName());
            currentlyManagedProject = null;
            return true;
        }
        System.out.println("You are not managing any project.");
        return false;
    }
}
