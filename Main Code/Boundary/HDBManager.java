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
import Interface.IEnquiryControl;
import Interface.IManagerApplicationControl;
import Interface.IProjectControl;
import Interface.IReportGenerator;
import Interface.ProjectView;

public class HDBManager extends User implements ProjectView, EnquiryViewReply, Serializable {
    private Report generatedReport;
    private static final long serialVersionUID = 1L;

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
    }

    // Project Management
    public void createProject(IProjectControl projectControl, String projectName, String neighbourhood,
                              LocalDate applicationOpenDate, LocalDate applicationCloseDate,
                              boolean visibility, int officerSlots,
                              Map<FlatType, Integer> unitCountMap,
                              Map<FlatType, Integer> priceInput) {
        if (getCurrentlyManagedProject(projectControl) != null) {
            System.out.println("Cannot create a new project. You are already managing a project.");
            return;
        }
        projectControl.createProject(this, projectName, neighbourhood, applicationOpenDate,
                applicationCloseDate, visibility, officerSlots, unitCountMap, priceInput);
    }

    public void removeProject(IProjectControl projectControl, String projectName) {
        if (isManagingProject(projectControl, projectName)) {
            projectControl.removeProject(this, projectName);
        } else {
            System.out.println("You are not managing this project.");
        }
    }

    public void editProject(IProjectControl projectControl, String projectName, int choice, Object newValue) {
        projectControl.editProject(this, projectName, choice, newValue);
    }

    
    public void toggleProjectVisibility(IProjectControl projectControl, String projectName, boolean isVisible) {
        Project project = getCurrentlyManagedProject(projectControl);
        if (project == null) {
            System.out.println("You are not managing any project.");
            return;
        }

        if (!project.getProjectName().equals(projectName)) {
            System.out.println("You are not managing the specified project.");
            return;
        }

        projectControl.toggleProjectVisibility(this, projectName, isVisible);
    }


    @Override
    public void viewAllProject(IProjectControl projectControl) {
        projectControl.viewAllProject();
    }

    public void viewMyProjects(IProjectControl projectControl) {
        Project managedProject = getCurrentlyManagedProject(projectControl);
        if (managedProject == null) {
            System.out.println("You have not created any projects.");
        } else {
            managedProject.displayProjectDetails();
        }
    }

    // Officer and Applicant Applications management
    public boolean manageOfficerApplication(IManagerApplicationControl applicationControl, IProjectControl projectControl, String projectName) {
        if (isManagingProject(projectControl, projectName)) {
            return applicationControl.manageOfficerRegistration(this, projectName, projectControl);
        }
        System.out.println("You are not managing this project.");
        return false;
    }

    public void approveApplicantApplications(IManagerApplicationControl applicationControl, IProjectControl projectControl) {
        if (getCurrentlyManagedProject(projectControl) == null) {
            System.out.println("You are not managing any project.");
            return;
        }

        boolean success = applicationControl.approveApplicantApplication(this, projectControl);
        if (success) {
            System.out.println("Application approvals processed successfully.");
        } else {
            System.out.println("Application approval process failed.");
        }
    }

    public void approveApplicantWithdrawals(IManagerApplicationControl applicationControl, IProjectControl projectControl) {
        if (getCurrentlyManagedProject(projectControl) == null) {
            System.out.println("You are not managing any project.");
            return;
        }

        boolean success = applicationControl.approveWithdrawals(this, projectControl);
        if (success) {
            System.out.println("Withdrawal approvals processed successfully.");
        } else {
            System.out.println("Withdrawal approval process failed.");
        }
    }

    // Enquiry Management
    @Override
    public void replyToEnquiries(IEnquiryControl enquiryControl,  IProjectControl projectControl) {
        enquiryControl.replyToEnquiries(this, projectControl);
    }

    @Override
    public void viewAllEnquiries(IEnquiryControl enquiryControl) {
        enquiryControl.viewAllEnquiries();
    }

    // Report Management
    public void generateApplicantReport(IReportGenerator reportGenerator, IProjectControl projectControl, String filterType, Object filterValue) {
        Project project = getCurrentlyManagedProject(projectControl);
        if (project == null) {
            System.out.println("You are not managing any project. Please select a project first.");
            return;
        }
        this.setGeneratedReport(reportGenerator.generateApplicantReport(project, filterType, filterValue));
    }

    public void displayGeneratedReport(IReportGenerator reportGenerator) {
        reportGenerator.displayReport(generatedReport);
    }

    // Owning Project checkings
    public boolean isManagingProject(IProjectControl projectControl, String projectName) {
        Project project = getCurrentlyManagedProject(projectControl);
        return project != null && project.getProjectName().equals(projectName);
    }

    public boolean isProjectActive(IProjectControl projectControl) {
        Project project = getCurrentlyManagedProject(projectControl);
        if (project == null) return false;

        LocalDate currentDate = LocalDate.now();
        return !currentDate.isAfter(project.getApplicationCloseDate())
                && !currentDate.isBefore(project.getApplicationOpenDate());
    }

    public Project getCurrentlyManagedProject(IProjectControl projectControl) {
        return projectControl.filterProjectsByManager(this);
    }

    public boolean stopManagingProject(IProjectControl projectControl) {
        Project project = getCurrentlyManagedProject(projectControl);
        if (project != null) {
            System.out.println("You are no longer managing the project: " + project.getProjectName());
            return true;
        }
        System.out.println("You are not managing any project.");
        return false;
    }


}
