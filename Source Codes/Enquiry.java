
public class Enquiry {
  private int enquiryId;
	private Project project;
	
	public Enquiry(int enquiryId, Project project) {
		this.enquiryId = enquiryId;
		this.project = project;
		
	}
	public int getEnquiryId() {
		return enquiryId;
	}
	public Project getProject() {
		return project;
	}

}
