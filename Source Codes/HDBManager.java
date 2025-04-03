import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HDBManager extends User {
    private ProjectService projectService;
    private ApplicationService applicationService;
    private Project currentlyManagedProject; // Field to track the currently managed project

    public HDBManager(String NRIC, String password, int age, String maritalStatus, 
                      ProjectService projectService, ApplicationService applicationService) {
        super(NRIC, password, age, maritalStatus);
        this.projectService = projectService;
        this.applicationService = applicationService;
        this.currentlyManagedProject = null; // Initially no project is being managed
    }

    // Delegate project management to ProjectService
    public void createProject(int projectID, String projectName, String neighbourhood, 
                              LocalDate applicationOpenDate, LocalDate applicationCloseDate, 
                              boolean visibility, int officerSlots, Map<FlatType, Integer> availableUnits) {
        // Check if the HDBManager is already managing a project
        if (currentlyManagedProject != null) {
            System.out.println("Cannot create a new project. You are already managing a project.");
            return;
        }

        // Create the project through ProjectService
        projectService.createProject(this, projectID, projectName, neighbourhood, applicationOpenDate, 
                                     applicationCloseDate, visibility, officerSlots, availableUnits);
    }

    public void editProject(int projectID, int choice, Object newValue) {
        if (isManagingProject(projectID)) {
            projectService.editProject(this, projectID, choice, newValue);
        } else {
            System.out.println("You are not managing this project.");
        }
    }

    public void removeProject(int projectID) {
        if (isManagingProject(projectID)) {
            projectService.removeProject(this, projectID);
            currentlyManagedProject = null; // No longer managing any project
        } else {
            System.out.println("You are not managing this project.");
        }
    }

    public void toggleProjectVisibility(int projectID, boolean isVisible) {
        if (isManagingProject(projectID)) {
            projectService.toggleProjectVisibility(this, projectID, isVisible);
        } else {
            System.out.println("You are not managing this project.");
        }
    }

    // Delegate application approval to ApplicationService
    public boolean approveOfficerApplication(int projectID, OfficerRegistration officerRegistration, boolean approved) {
        if (isManagingProject(projectID)) {
            return applicationService.approveOfficerRegistration(this, projectID, officerRegistration, approved);
        }
        System.out.println("You are not managing this project.");
        return false;
    }

    // Method to approve an applicant's application
    public boolean approveApplicantApplication(int projectID, ApplicantApplication application) {
        if (isManagingProject(projectID)) {
            return applicationService.approveApplicantApplication(this, application);
        }
        System.out.println("You are not managing this project.");
        return false;
    }

    // Method to check if the HDBManager is managing the given project
    public boolean isManagingProject(int projectID) {
        return currentlyManagedProject != null && currentlyManagedProject.getProjectID() == projectID;
    }

    // Method to check if a project is active (between open and close dates)
    public boolean isProjectActive(Project project) {
        LocalDate currentDate = LocalDate.now();
        return !currentDate.isAfter(project.getApplicationCloseDate()) && !currentDate.isBefore(project.getApplicationOpenDate());
    }

    // Getter and Setter for currentlyManagedProject
    public Project getCurrentlyManagedProject() {
        return currentlyManagedProject;
    }
    // Setter for currentlyManagedProject
    public void setCurrentlyManagedProject(Project project) {
        this.currentlyManagedProject = project;
    }
    
    // Method to generate a report of applicants with filters
    public Report generateApplicantReport(String filterType, Object filterValue) {
        if (currentlyManagedProject == null) {
            System.out.println("You are not managing any project. Please select a project first.");
            return null;
        }

        // Retrieve the list of applicants for the currently managed project
        List<Applicant> applicants = applicationService.getApplicantsForProject(currentlyManagedProject.getProjectID());

        // Apply filters based on the given filterType
        List<Applicant> filteredApplicants = applicants.stream()
                .filter(applicant -> {
                    switch (filterType.toLowerCase()) {
                        case "maritalstatus":
                            return applicant.getMaritalStatus().equalsIgnoreCase((String) filterValue);
                        case "flattype":
                            return applicant.getFlatType().equals(filterValue);
                        case "age":
                            return applicant.getAge() == (int) filterValue;
                        default:
                            return true; // No filter applied
                    }
                })
                .collect(Collectors.toList());

        // Build the report content
        StringBuilder reportContent = new StringBuilder();
        reportContent.append(String.format("%-15s %-10s %-15s %-10s %-10s\n", 
                "Applicant Name", "Age", "Marital Status", "Flat Type", "Project Name"));
        reportContent.append("----------------------------------------------------------------------------\n");

        if (filteredApplicants.isEmpty()) {
            reportContent.append("No applicants found for the given filter.\n");
        } else {
            for (Applicant applicant : filteredApplicants) {
                reportContent.append(String.format("%-15s %-10d %-15s %-10s %-10s\n",
                        applicant.getName(), applicant.getAge(), applicant.getMaritalStatus(),
                        applicant.getFlatType(), currentlyManagedProject.getProjectName()));
            }
        }

        // Store filter criteria in a map
        Map<String, String> filter = new HashMap<>();
        filter.put(filterType, filterValue.toString());

        // Return the Report object
        return new Report(filter, reportContent.toString());
    }

    // Method to stop managing the current project
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
