package Controller;

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
import java.util.regex.Pattern;

public class EnquiryController {
	private Map<String, List<Enquiry>> Individualenquiries;
    private List<Enquiry> Enquiries;
    private int EnquiryID = 1;
    
    public EnquiryController() {
    	Individualenquiries = new HashMap<>();
    	Enquiries = new ArrayList<>();
    }
    
    public Enquiry createEnquiry(Applicant applicant, Project project, String enquiry) {
    	Enquiry newenquiry = new Enquiry(applicant, project, enquiry);
    	newenquiry.setEnquiryID(EnquiryID);
    	EnquiryID++;
    	
    	//If enquiry is about a project, will be added to that project's enquiries
    	if (project != null) {
    		project.addEnquiry(newenquiry);
    	}
    	//else, enquiry can be a general one too
		Enquiries.add(newenquiry);
		 if (!Individualenquiries.containsKey(applicant.getNRIC())) {
			 Individualenquiries.put(applicant.getNRIC(), new ArrayList<>());
	        }
		 Individualenquiries.get(applicant.getNRIC()).add(newenquiry);
		return newenquiry;
    }
    
    public List<Enquiry> getEnquiriesByProject(Project project) {
        if (project == null) return new ArrayList<>();
        return project.getEnquiries();
    }
    
    public List<Enquiry> getEnquiriesByApplicant(Applicant applicant) {
        if (applicant == null) return new ArrayList<>();
        return Individualenquiries.getOrDefault(applicant.getNRIC(), new ArrayList<>());
    }
    
    public List<Enquiry> getAllEnquiries(){
    	return Enquiries;
    }
    
    public List<Enquiry> getUnrespondedEnquiries(){
    	List<Enquiry> result = new ArrayList<>();
    	for (Enquiry enquiry:Enquiries) {
    		if (!enquiry.hasResponse()) {
    			result.add(enquiry);
    		}
    	}
    	return result;
    }
    
    public boolean editEnquiry(Enquiry enquiry, String newEnquiry) {
    	if (enquiry == null || !Enquiries.contains(enquiry)) {
    		System.out.println("No such enquiry is found");
    		return false;
    	}
    	if (enquiry.hasResponse()) {
    		System.out.println("Enquiry has already been responded, unable to edit.");
    		return false;
    	}
    	enquiry.setEnquiryString(newEnquiry);
    	return true;
    }
    
    public boolean deleteEnquiry(Enquiry enquiry) {
    	if (enquiry == null || !Enquiries.contains(enquiry)) {
    		System.out.println("No such enquiry is found");
    		return false;
    	}
    	if (enquiry.hasResponse()) {
    		System.out.println("Enquiry has already been responded, unable to delete.");
    		return false;
    	}
    	if (enquiry.getProject()!= null) {
    		enquiry.getProject().removeEnquiry(enquiry);
    	}
    	if (Enquiries.contains(enquiry)) {
    		Enquiries.remove(enquiry);
    	}
    	if (Individualenquiries.containsKey(enquiry.getApplicant().getNRIC())) {
    		Individualenquiries.remove(enquiry.getApplicant().getNRIC());
    	}
    	return true;
    }
    
    public boolean respondToEnquiry(Enquiry enquiry, String response, User responder) {
    	if (enquiry == null || !Enquiries.contains(enquiry)) {
    		System.out.println("No such enquiry is found, please try another enquiry.");
    		return false;
    	}
    	if (enquiry.hasResponse()) {
    		System.out.println("This enquiry has already been responded by " + enquiry.getResponder().getName() + ", please respond to another enquiry instead.");
    		return false;
    	}
    	enquiry.setResponse(response);
    	enquiry.setResponder(responder);
    	enquiry.setEnquiryDate(new Date());
    	return true;
    }
    
    
	public void setEnquiries(List<Enquiry> enquiries) {
		this.Enquiries.clear();
		this.Individualenquiries.clear();
		for (Enquiry enquiry:enquiries) {
			//Error in terms of enquiryID
			if (enquiry.getEnquiryID() > 0) {
				System.out.println("Error in loading enquiries, please look at Enquiry.csv.");
				break;
			}
			if (enquiry.getEnquiryID()>=EnquiryID) {
				EnquiryID = enquiry.getEnquiryID()+1;
			}
			this.Enquiries.add(enquiry);
			if (!Individualenquiries.containsKey(enquiry.getApplicant().getNRIC())) {
				this.Individualenquiries.put(enquiry.getApplicant().getNRIC(), new ArrayList<>());
			}
			this.Individualenquiries.get(enquiry.getApplicant().getName()).add(enquiry);
			if (enquiry.getProject() != null) {
				enquiry.getProject().addEnquiry(enquiry);
			}
			
		}
		
	}

}
