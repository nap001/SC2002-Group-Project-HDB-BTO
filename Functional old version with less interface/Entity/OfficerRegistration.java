package Entity;
import java.io.Serializable;

import Boundary.HDBOfficer;




public class OfficerRegistration implements Serializable{
    private HDBOfficer officer;  // This will hold the officer associated with this registration
    private boolean isApplicant;
    private boolean isHandling;
    private Project project;
    private boolean status; // Approved or not
    private static final long serialVersionUID = 1L;  // Add serialVersionUID for version control

    public OfficerRegistration(HDBOfficer officer, boolean isApplicant, boolean isHandling, Project project) {
        this.officer = officer;
        this.isApplicant = isApplicant;
        this.isHandling = isHandling;
        this.project = project;
        this.status = false; // Initially, registration status is false (not approved)
    }
    


    public HDBOfficer getOfficer() {
        return officer;
    }

    public boolean isApplicant() {
        return isApplicant;
    }

    public boolean isHandling() {
        return isHandling;
    }

    public Project getProject() {
        return project;
    }

    public boolean getStatus() {
        return status;
    }

    // Method to set registration status (approve or reject)
    public void setRegistrationStatus(boolean status) {
        this.status = status;
    }
}
