package Entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import ENUM.FlatType;
import Interface.IEnquiryControl;
import Interface.IManagerApplicationControl;
import Interface.IProjectManagementControl;
import Interface.IProjectQueryControl;
import Interface.IProjectViewControl;
import Interface.IReportGenerator;

public class HDBManager extends User implements Serializable {
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
    public void createProject(IProjectManagementControl projectControl, IProjectQueryControl projectQueryControl, String projectName, String neighbourhood,
                              LocalDate applicationOpenDate, LocalDate applicationCloseDate,
                              boolean visibility, int officerSlots,
                              Map<FlatType, Integer> unitCountMap,
                              Map<FlatType, Integer> priceInput) {
        if (getCurrentlyManagedProject(projectQueryControl) != null) {
            System.out.println("Cannot create a new project. You are already managing a project.");
            return;
        }
        projectControl.createProject(this, projectName, neighbourhood, applicationOpenDate,
                applicationCloseDate, visibility, officerSlots, unitCountMap, priceInput);
    }

    public void removeProject(IProjectManagementControl projectControl,IProjectQueryControl projectQueryControl, String projectName) {
        if (isManagingProject(projectQueryControl, projectName)) {
            projectControl.removeProject(this, projectName);
        } else {
            System.out.println("You are not managing this project.");
        }
    }

    public void editProject(IProjectManagementControl projectControl, String projectName, int choice, Object newValue) {
        projectControl.editProject(this, projectName, choice, newValue);
    }

    public void toggleProjectVisibility(IProjectManagementControl projectControl, IProjectQueryControl projectQuery, String projectName, boolean isVisible) {
        Project project = getCurrentlyManagedProject(projectQuery);
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

    
    public void viewAllProject(IProjectViewControl projectControl) {
        projectControl.viewAllProject();
    }

    public void viewMyProjects(IProjectQueryControl projectControl) {
        Project managedProject = getCurrentlyManagedProject(projectControl);
        if (managedProject == null) {
            System.out.println("You have not created any projects.");
        } else {
            managedProject.displayProjectDetails();
        }
    }

    // Officer and Applicant Applications management
    public boolean manageOfficerApplication(IManagerApplicationControl applicationControl, IProjectQueryControl projectControl, String projectName) {
        if (isManagingProject(projectControl, projectName)) {
            return applicationControl.manageOfficerRegistration(this, projectName, projectControl);
        }
        System.out.println("You are not managing this project.");
        return false;
    }

    public void approveApplicantApplications(IManagerApplicationControl applicationControl, IProjectQueryControl projectControl) {
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

    public void approveApplicantWithdrawals(IManagerApplicationControl applicationControl, IProjectQueryControl projectControl) {
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

    public void filterAllProjects(IProjectQueryControl projectControl, String filterType, Object filterValue) {
        List<Project> filteredProjects = projectControl.filterProjects(filterType, filterValue);

        if (filteredProjects == null || filteredProjects.isEmpty()) {
            System.out.println("No projects found for the given filter.");
        } else {
            System.out.println("Filtered Projects:");
            for (Project project : filteredProjects) {
                project.displayProjectDetails();
            }
        }
    }

    // Enquiry Management
    
    public void replyToEnquiries(IEnquiryControl enquiryControl, IProjectQueryControl projectControl) {
        enquiryControl.replyToEnquiries(this, projectControl);
    }

    
    public void viewAllEnquiries(IEnquiryControl enquiryControl) {
        enquiryControl.viewAllEnquiries();
    }

    // Report Management
    public void generateApplicantReport(IReportGenerator reportGenerator, IProjectQueryControl projectControl, String filterType, Object filterValue) {
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
    public boolean isManagingProject(IProjectQueryControl projectControl, String projectName) {
        Project project = getCurrentlyManagedProject(projectControl);
        return project != null && project.getProjectName().equals(projectName);
    }

    public boolean isProjectActive(IProjectQueryControl projectControl) {
        Project project = getCurrentlyManagedProject(projectControl);
        if (project == null) return false;

        LocalDate currentDate = LocalDate.now();
        return !currentDate.isAfter(project.getApplicationCloseDate())
                && !currentDate.isBefore(project.getApplicationOpenDate());
    }

    public Project getCurrentlyManagedProject(IProjectQueryControl projectControl) {
        return projectControl.filterProjectsByManager(this);
    }

    public boolean stopManagingProject(IProjectQueryControl projectControl) {
        Project project = getCurrentlyManagedProject(projectControl);
        if (project != null) {
            System.out.println("You are no longer managing the project: " + project.getProjectName());
            return true;
        }
        System.out.println("You are not managing any project.");
        return false;
    }
}
