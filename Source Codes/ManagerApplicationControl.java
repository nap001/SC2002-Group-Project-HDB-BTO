import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ManagerApplicationControl {
    private ProjectList projectDatabase;

    public ManagerApplicationControl(ProjectList projectDatabase) {
        this.projectDatabase = projectDatabase;
    }

    // Method to approve an officer's registration
    public boolean approveOfficerRegistration(HDBManager manager, int projectID, OfficerRegistration officerRegistration, boolean approved) {
        // Fetch the project by projectID
        Project project = projectDatabase.getProjects(projectID);
        if (project == null) {
            System.out.println("Project not found.");
            return false;
        }

        // Check if the project in the registration matches the one we are fetching
        if (!project.equals(officerRegistration.getProject())) {
            System.out.println("Project mismatch between registration and provided project.");
            return false;
        }

        // Check if the manager is managing this project
        if (!manager.isManagingProject(projectID)) {
            System.out.println("You are not authorized to approve registrations for this project.");
            return false;
        }

        // Check if the officer is the one submitting the application and if the registration is an applicant
        if (officerRegistration.isApplicant()) {
            // Set registration status based on the approval flag
            officerRegistration.setRegistrationStatus(approved);

            if (approved) {
                // Add to the handling list if approved and officer is handling
                if (officerRegistration.isHandling()) {
                    // Directly add the officer to the project's list of assigned officers
                    project.getHdbOfficers().add(officerRegistration.getOfficer());
                    project.setOfficerSlots(project.getOfficerSlots() - 1); // Decrement the available slots
                    System.out.println("Officer registration approved and assigned to project.");
                }
            } else {
                System.out.println("Officer registration rejected.");
            }
            return true;
        }

        // If the officer is not an applicant, the registration can't be approved
        System.out.println("Officer is not an applicant.");
        return false;
    }
    // Method to approve an applicant's application
    public boolean approveApplicantApplication(HDBManager manager, List<ApplicantApplication> applicantDatabase, int applicationID, boolean approved) {
        // Iterate over the list to find the application by ID
        ApplicantApplication application = null;
        for (ApplicantApplication app : applicantDatabase) {
            if (app.getApplicationID() == applicationID) {
                application = app;
                break;
            }
        }

        if (application == null) {
            System.out.println("Application not found.");
            return false;
        }

        Project project = application.getProject();
        FlatType flatType = application.getFlatType();
        ApplicationStatus status = application.getApplicationStatus();

        // Check if the application is still pending
        if (status != ApplicantApplication.ApplicationStatus.PENDING) {
            System.out.println("This application is not in a pending state.");
            return false;
        }

        // Check if the manager is managing the project
        if (!manager.isManagingProject(project.getProjectID())) {
            System.out.println("You are not authorized to approve applications for this project.");
            return false;
        }

        // Retrieve the current availability map
        Map<FlatType, Integer> availableUnitsMap = project.getAvailableUnits();
        int availableUnits = availableUnitsMap.getOrDefault(flatType, 0);

        if (approved) {
            if (availableUnits > 0) {
                // Approve the application
                application.setApplicationStatus(ApplicantApplication.ApplicationStatus.SUCCESSFUL);

                // Add the applicant to the project if the application is approved
                project.addApplicantToProject(application.getApplicant());  // Assuming there's a method for adding applicants to the project
                
                // Update the available units map by decreasing the count for the requested flat type
                availableUnitsMap.put(flatType, availableUnits - 1);

                // Apply the updated map to the project
                project.setAvailableUnits(availableUnitsMap);

                System.out.println("Application approved for Applicant ID: " + application.getApplicantID());
            } else {
                // Not enough available units, mark as unsuccessful
                application.setApplicationStatus(ApplicantApplication.ApplicationStatus.UNSUCCESSFUL);
                System.out.println("No available units for the requested flat type.");
            }
        } else {
            // Reject the application
            application.setApplicationStatus(ApplicantApplication.ApplicationStatus.REJECTED);
            System.out.println("Application rejected for Applicant ID: " + application.getApplicantID());
        }

        // Remove the application from the database after approval/rejection
        applicantDatabase.remove(application);
        return true;
    }
}
