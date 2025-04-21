package Entity;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

import ENUM.FlatType;

public class Project implements Serializable{
    private String projectName;
    private String neighbourhood;
    private LocalDate applicationOpenDate;
    private LocalDate applicationCloseDate;
    private boolean visibility;
    private int officerSlots;
    private HDBManager hdbManager;
    private List<HDBOfficer> hdbOfficers;
    private List<Applicant> applicants;
    private static final long serialVersionUID = 1L;  // Add serialVersionUID for version control

    // New: Flat details include both unit count and price
    private Map<FlatType, Integer> unitCountMap;
    private Map<FlatType, Double> priceMap;

    public Project(String projectName, String neighbourhood, LocalDate applicationOpenDate,
                   LocalDate applicationCloseDate, boolean visibility, int officerSlots,
                   Map<FlatType, Integer> unitCountMap, Map<FlatType, Double> priceMap,
                   HDBManager hdbManager, List<HDBOfficer> hdbOfficers) {
        this.projectName = projectName;
        this.neighbourhood = neighbourhood;
        this.applicationOpenDate = applicationOpenDate;
        this.applicationCloseDate = applicationCloseDate;
        this.visibility = visibility;
        this.officerSlots = officerSlots;
        this.unitCountMap = unitCountMap;
        this.priceMap = priceMap;
        this.hdbManager = hdbManager;
        this.hdbOfficers = hdbOfficers != null ? hdbOfficers : new ArrayList<>();
        this.applicants = new ArrayList<>();
    }

    public void displayProjectDetails() {
        System.out.println("=== Project Details ===");
        System.out.println("Project Name: " + projectName);
        System.out.println("Neighbourhood: " + neighbourhood);
        System.out.println("Application Open Date: " + applicationOpenDate);
        System.out.println("Application Close Date: " + applicationCloseDate);
        System.out.println("Visibility: " + (visibility ? "Visible" : "Hidden"));
        System.out.println("Officer Slots: " + officerSlots);
        System.out.println("Manager: " + (hdbManager != null ? hdbManager.getName() : "None"));

        System.out.println("Available Units and Prices:");
        for (FlatType type : unitCountMap.keySet()) {
            int count = unitCountMap.get(type);
            double price = priceMap.getOrDefault(type, 0.0);
            System.out.printf("  - %s: %d units at $%.2f%n", type.getDisplayName(), count, price);
        }

        System.out.println("Assigned Officers:");
        if (hdbOfficers.isEmpty()) {
            System.out.println("  - No officers assigned.");
        } else {
            for (HDBOfficer officer : hdbOfficers) {
                System.out.println("  - " + officer.getName());
            }
        }

        System.out.println("=======================\n");
    }
    
 // === Applicant Management ===
    public void addApplicant(Applicant applicant) {
        if (applicant != null && !applicants.contains(applicant)) {
            applicants.add(applicant);
        }
    }

    public void removeApplicant(Applicant applicant) {
        applicants.remove(applicant);
    }

    // === Officer Management ===
    public void addHdbOfficer(HDBOfficer officer) {
        if (officer != null && !hdbOfficers.contains(officer)) {
            hdbOfficers.add(officer);
        }
    }

    public void removeHdbOfficer(HDBOfficer officer) {
        hdbOfficers.remove(officer);
    }


    // === Getters and Setters ===
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

    public Map<FlatType, Integer> getUnitCountMap() { return unitCountMap; }
    public void setUnitCountMap(Map<FlatType, Integer> unitCountMap) { this.unitCountMap = unitCountMap; }

    public Map<FlatType, Double> getPriceMap() { return priceMap; }
    public void setPriceMap(Map<FlatType, Double> priceMap) { this.priceMap = priceMap; }

    public HDBManager getHdbManager() { return hdbManager; }
    public void setHdbManager(HDBManager hdbManager) { this.hdbManager = hdbManager; }

    public List<HDBOfficer> getHdbOfficers() { return hdbOfficers; }
    public void setHdbOfficers(List<HDBOfficer> hdbOfficers) { this.hdbOfficers = hdbOfficers; }

    public List<Applicant> getApplicants() { return applicants; }
    public void setApplicants(List<Applicant> applicants) { this.applicants = applicants; }

}
