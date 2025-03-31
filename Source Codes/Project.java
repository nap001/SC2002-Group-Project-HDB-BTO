import java.time.LocalDate;
import java.util.*;

public class Project {
    private int projectID;
    private String projectName;
    private String neighbourhood;
    private LocalDate applicationOpenDate;
    private LocalDate applicationCloseDate;
    private boolean visibility;
    private int officerSlots;
    private Map<FlatType, Integer> availableUnits;
    private HDBManager hdbManager;
    private List<Enquiry> enquiries;
    private List<Applicant> applicants;
    private List<HDBOfficer> hdbOfficers;
    private List<HDBOfficer> pendingOfficerApplications; // New list for pending applications

    public Project(int projectID, String projectName, String neighbourhood, LocalDate applicationOpenDate,
                   LocalDate applicationCloseDate, boolean visibility, int officerSlots,
                   Map<FlatType, Integer> availableUnits, HDBManager hdbManager) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.neighbourhood = neighbourhood;
        this.applicationOpenDate = applicationOpenDate;
        this.applicationCloseDate = applicationCloseDate;
        this.visibility = visibility;
        this.officerSlots = officerSlots;
        this.availableUnits = availableUnits;
        this.hdbManager = hdbManager;
        this.hdbOfficers = new ArrayList<>();
        this.pendingOfficerApplications = new ArrayList<>();
    }

    // HDB Officer applies to a project
    public boolean applyToProject(HDBOfficer officer) {
        if (!pendingOfficerApplications.contains(officer)) {
            pendingOfficerApplications.add(officer);
            return true; // Successfully applied
        }
        return false; // Officer has already applied
    }

    // HDB Manager approves an officer
    public boolean approveOfficerApplication(HDBOfficer officer) {
        if (pendingOfficerApplications.contains(officer) && hdbOfficers.size() < officerSlots) {
            hdbOfficers.add(officer);
            this.officerSlots = officerSlots-1;
            pendingOfficerApplications.remove(officer);
            return true; // Successfully approved and assigned
        }
        return false; // Approval failed (slots full or officer not found)
    }
    
    
    // Get list of pending officer applications
    public List<HDBOfficer> getPendingOfficerApplications() {
        return new ArrayList<>(pendingOfficerApplications);
    }

    // Remaining available units
    public int getRemainingUnits(FlatType flatType) {
        return availableUnits.getOrDefault(flatType, 0);
    }
   
    
    public void displayProjectDetails() {
        System.out.println("=== Project Details ===");
        System.out.println("Project ID: " + projectID);
        System.out.println("Project Name: " + projectName);
        System.out.println("Neighbourhood: " + neighbourhood);
        System.out.println("Application Open Date: " + applicationOpenDate);
        System.out.println("Application Close Date: " + applicationCloseDate);
        System.out.println("Visibility: " + (visibility ? "Visible" : "Hidden"));
        System.out.println("Officer Slots: " + officerSlots);
        
        // Display available units
        System.out.println("Available Units:");
        for (Map.Entry<FlatType, Integer> entry : availableUnits.entrySet()) {
            System.out.println("  - " + entry.getKey().getDisplayName() + ": " + entry.getValue() + " units");
        }
        
        // Display approved HDB officers
        System.out.println("\nAssigned HDB Officers:");
        if (hdbOfficers.isEmpty()) {
            System.out.println("  - No officers assigned yet.");
        } else {
            for (HDBOfficer officer : hdbOfficers) {
                System.out.println("  - " + officer.getNRIC()); // Assuming HDBOfficer has a getNRIC() method
            }
        }
        
        // Display pending HDB officers
        System.out.println("\nPending HDB Officer Applications:");
        if (pendingOfficerApplications.isEmpty()) {
            System.out.println("  - No pending officer applications.");
        } else {
            for (HDBOfficer officer : pendingOfficerApplications) {
                System.out.println("  - " + officer.getNRIC()); // Assuming HDBOfficer has a getNRIC() method
            }
        }

        System.out.println("=======================\n");
    }
   


    // Getters and Setters
    public int getProjectID() { return projectID; }
    public void setProjectID(int projectID) { this.projectID = projectID; }
    public String getProjectName() { return projectName; }
    public void setProjectName(String projectName) { this.projectName = projectName; }
    public String getNeighbourhood() { return neighbourhood; }
    public void setNeighbourhood(String neighbourhood) { this.neighbourhood = neighbourhood; }
    public LocalDate getApplicationOpenDate() { return applicationOpenDate; }
    public void setApplicationOpenDate(LocalDate applicationOpenDate) { this.applicationOpenDate = applicationOpenDate; }
    public LocalDate getApplicationCloseDate() { return applicationCloseDate; }
    public void setApplicationCloseDate(LocalDate applicationCloseDate) { this.applicationCloseDate = applicationCloseDate; }
    public boolean isVisibility() { return visibility; }
    public void setVisibility(boolean visibility) { this.visibility = visibility; }
    public int getOfficerSlots() { return officerSlots; }
    public void setOfficerSlots(int officerSlots) { this.officerSlots = officerSlots; }
    public Map<FlatType, Integer> getAvailableUnits() { return availableUnits; }
    public void setAvailableUnits(Map<FlatType, Integer> availableUnits) { this.availableUnits = availableUnits; }
    public HDBManager getHdbManager() { return hdbManager; }
    public void setHdbManager(HDBManager hdbManager) { this.hdbManager = hdbManager; }
    public List<Enquiry> getEnquiries() { return enquiries; }
    public void setEnquiries(List<Enquiry> enquiries) { this.enquiries = enquiries; }
    public List<Applicant> getApplicants() { return applicants; }
    public void setApplicants(List<Applicant> applicants) { this.applicants = applicants; }
    public List<HDBOfficer> getHdbOfficers() { return hdbOfficers; }
    public void setHdbOfficers(List<HDBOfficer> hdbOfficers) { this.hdbOfficers = hdbOfficers; }
}
