import java.time.LocalDate;
import java.util.List;

public class HDBManager extends User {
    private Project currentlyManagedProject; // Field to track the currently managed project

    public HDBManager(String NRIC, String password, int age, String maritalStatus) {
        super(NRIC, password, age, maritalStatus);
        this.currentlyManagedProject = null; // Initially no project is being managed
    }

    // Create Project
    public void createProject(ProjectControl projectManagement, int projectID, String projectName,
                              String neighbourhood, LocalDate applicationOpenDate, LocalDate applicationCloseDate,
                              boolean visibility, int officerSlots, 
                              java.util.Map<FlatType, Integer> availableUnits) {
        if (currentlyManagedProject != null) {
            System.out.println("Cannot create a new project. You are already managing a project.");
            return;
        }
        projectManagement.createProject(this, projectID, projectName, neighbourhood, applicationOpenDate,
                applicationCloseDate, visibility, officerSlots, availableUnits);
    }

    public void editProject(ProjectControl projectManagement, int projectID, int choice, Object newValue) {
        if (isManagingProject(projectID)) {
            projectManagement.editProject(this, projectID, choice, newValue);
        } else {
            System.out.println("You are not managing this project.");
        }
    }

    public void removeProject(ProjectControl projectManagement, int projectID) {
        if (isManagingProject(projectID)) {
            projectManagement.removeProject(this, projectID);
            currentlyManagedProject = null;
        } else {
            System.out.println("You are not managing this project.");
        }
    }

    public void toggleProjectVisibility(ProjectControl projectManagement, int projectID, boolean isVisible) {
        if (isManagingProject(projectID)) {
            projectManagement.toggleProjectVisibility(this, projectID, isVisible);
        } else {
            System.out.println("You are not managing this project.");
        }
    }

    public void viewAllProject(ProjectControl projectManagement) {
        projectManagement.viewAllProject();
    }

    public void viewMyProjects(ProjectControl projectManagement) {
        List<Project> myProjects = projectManagement.filterProjectsByManager(this);
        if (myProjects.isEmpty()) {
            System.out.println("You have not created any projects.");
        } else {
            System.out.println("Projects created by you:");
            for (Project project : myProjects) {
                System.out.println("- " + project.getProjectName() + " (ID: " + project.getProjectID() + ")");
            }
        }
    }

    public boolean approveOfficerApplication(ManagerApplicationControl applicationManagement, int projectID,
                                             OfficerRegistration officerRegistration, boolean approved) {
        if (isManagingProject(projectID)) {
            return applicationManagement.approveOfficerRegistration(this, projectID, officerRegistration, approved);
        }
        System.out.println("You are not managing this project.");
        return false;
    }

    // Check if the manager is managing a project
    public boolean isManagingProject(int projectID) {
        return currentlyManagedProject != null && currentlyManagedProject.getProjectID() == projectID;
    }

    // Check if project is active
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

    public void replyToEnquiries(List<Enquiry> enquiryList, EnquiryControl enquiryControl) {
        enquiryControl.handleEnquiries(this, enquiryList);
    }

    public Report generateApplicantReport(ReportGenerator reportGenerator, String filterType, Object filterValue) {
        if (currentlyManagedProject == null) {
            System.out.println("You are not managing any project. Please select a project first.");
            return null;
        }
        return reportGenerator.generateApplicantReport(currentlyManagedProject, filterType, filterValue);
    }
}
