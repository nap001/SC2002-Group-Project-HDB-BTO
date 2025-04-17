package Boundary;

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

public class HDBManager extends User implements ProjectView, EnquiryViewReply{
    private Project currentlyManagedProject;
    
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
        List<Project> myProjects = projectControl.filterProjectsByManager(this);
        if (myProjects.isEmpty()) {
            System.out.println("You have not created any projects.");
        } else {
            System.out.println("Projects created by you:");
            for (Project project : myProjects) {
                System.out.println("- " + project.getProjectName());
            }
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
    
    // Enquiry Management//
    @Override
    public void replyToEnquiries(EnquiryControl enquiryControl) {
        enquiryControl.replyToEnquiries(this); // Uses the current HDBManager object
    }

    @Override
    public void viewAllEnquiries(EnquiryControl enquiryControl) {
        enquiryControl.viewAllEnquiries();
    }

    // Report Management//
//    public Report generateApplicantReport(ReportGenerator reportGenerator, String filterType, Object filterValue) {
//        if (currentlyManagedProject == null) {
//            System.out.println("You are not managing any project. Please select a project first.");
//            return null;
//        }
//        return reportGenerator.generateApplicantReport(currentlyManagedProject, filterType, filterValue);
//    }

    
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
