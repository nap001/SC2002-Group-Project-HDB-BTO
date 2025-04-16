package Application;

import Application.*;
import Boundaries.*;
import Controller.*;
import Entities.*;
import Enum.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Project {
    private String projectName;
    private String neighborhood;
    private String region;
    private HashMap<FlatType, Integer> flatDetails;
    private Date openingDate;
    private Date closingDate;
    private HDBManager manager;
    private int availableOfficerSlot;
    private int officerSlot;
    private List<String> officers;
    private List <Enquiry> enquiries;
    private List <OfficerApplication> officerapplications;
    private List <Application> applications;
    private boolean visibility;
    
    private ProjectFlatsDetails projectFlatDetails;
    
    Scanner sc = new Scanner(System.in);

    // Constructor
    public Project() {
    	flatDetails = new HashMap<>();
    	enquiries = new ArrayList<>();
        applications = new ArrayList<>();
        officerapplications = new ArrayList<>();
        visibility = false;
        projectFlatDetails = new ProjectFlatsDetails(this);
    }
    
    public Project(String projectName, String neighbourhood, String region, HDBManager manager) {
    	this();
    	this.projectName = projectName;
    	this.neighborhood = neighbourhood;
    	this.region = region;
    	this.manager = manager;
    }

    // Getters and setters
    
    
    public void initialiseProjectFlatsDetails() {
    	projectFlatDetails.initialiseFlatDetails(flatDetails);
    }

    public void updateFlatTypeCount(FlatType flatType, int newCount) {
        if (newCount < 0) {
            System.out.println("Number of " + flatType + " cannot be negative value!");
        }else {
        	flatDetails.put(flatType, newCount);
        	
        	Map<FlatType, Integer> newProjectFlatDetails = new HashMap<>(flatDetails);
        	projectFlatDetails = new ProjectFlatsDetails(this);
        	projectFlatDetails.initialiseFlatDetails(newProjectFlatDetails); 
        }
    } 
    
	
    public String getDetails() {
        StringBuilder details = new StringBuilder();
        details.append("Project Name: ").append(projectName).append("\n");
        details.append("Region: ").append(region).append("\n");
        details.append("Neighborhood: ").append(neighborhood).append("\n");
        details.append("Application Period: ").append(openingDate).append(" to ").append(closingDate).append("\n");
        details.append("Available Flat Types:\n");

        flatDetails.forEach((flatType, units) -> 
            details.append("- ").append(flatType).append(": ").append(units).append(" units\n")
        );
        
        return details.toString();
    }
    
    public void addEnquiry(Enquiry enquiry) {
        if (!enquiries.contains(enquiry)) {
            enquiries.add(enquiry);
        }
    }
    
    public void removeEnquiry(Enquiry enquiry) {
    	if (enquiries.contains(enquiry)) {
            enquiries.remove(enquiry);
        }
    }
    
    public void submitApplication(Application application) {
        if (!applications.contains(application)) {
            applications.add(application);
        }
    }
    
    public void addOfficerApplication(OfficerApplication application) {
        if (!officerapplications.contains(application)) {
        	officerapplications.add(application);
        }
    }
    
    public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public HashMap<FlatType, Integer> getFlatDetails() {
		return flatDetails;
	}

	public void setFlatDetails(HashMap<FlatType, Integer> flatDetails) {
		this.flatDetails = flatDetails;
	}

	public Date getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(Date openingDate) {
		this.openingDate = openingDate;
	}

	public Date getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(Date closingDate) {
		this.closingDate = closingDate;
	}

	public HDBManager getManager() {
		return manager;
	}

	public void setManager(HDBManager manager) {
		this.manager = manager;
	}

	public int getAvailableOfficerSlot() {
		return availableOfficerSlot;
	}

	public void setAvailableOfficerSlot(int availableOfficerSlot) {
		this.availableOfficerSlot = availableOfficerSlot;
	}
	
	public void reduceAvailableOfficerCount() {
		availableOfficerSlot -= 1;
	}

	public int getOfficerSlot() {
		return officerSlot;
	}

	public void setOfficerSlot(int officerSlot) {
		this.officerSlot = officerSlot;
	}

	public List<String> getOfficers() {
		return officers;
	}

	public void setOfficers(List<String> officers) {
		this.officers = officers;
	}
	
	public void addOfficers(String name) {
		this.officers.add(name);
	}

	public List<Enquiry> getEnquiries() {
		return enquiries;
	}

	public void setEnquiries(List<Enquiry> enquiries) {
		this.enquiries = enquiries;
	}

	public List<OfficerApplication> getOfficerapplications() {
		return officerapplications;
	}

	public void setOfficerapplications(List<OfficerApplication> officerapplications) {
		this.officerapplications = officerapplications;
	}

	public List<Application> getApplications() {
		return applications;
	}
	
	public int getAvailableFlatType(FlatType flatType) {
		return this.flatDetails.getOrDefault(flatType, 0);
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}
	
	public void addApplication(Application application) {
		applications.add(application);
	}

	public boolean isVisibility() {
		return visibility;
	}

	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}

	public ProjectFlatsDetails getProjectFlatDetails() {
		return projectFlatDetails;
	}

	public void setProjectFlatDetails(ProjectFlatsDetails projectFlatDetails) {
		this.projectFlatDetails = projectFlatDetails;
	}


	
}

