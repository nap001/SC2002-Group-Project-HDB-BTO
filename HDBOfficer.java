
public class HDBOfficer extends Applicant {
    private String registeredProjectId; 
    private String registrationStatus;  

    public HDBOfficer(String nric, String name, int age, String maritalStatus) {
        super(nric, name, age, maritalStatus);
        this.registeredProjectId = null;
        this.registrationStatus = null;
    }

    // Register to handle a BTO project
    public boolean registerAsOfficer(String projectId, boolean isEligible, boolean alreadyRegisteredOther) {
        if (this.hasAppliedToProject(projectId)) return false; 
        if (alreadyRegisteredOther) return false; 
        this.registeredProjectId = projectId;
        this.registrationStatus = "Pending";
        return true;
    }

    public void setRegistrationStatus(String status) {
        this.registrationStatus = status;
    }

    public String getRegistrationStatus() {
        return this.registrationStatus;
    }

    public String getRegisteredProjectId() {
        return this.registeredProjectId;
    }

    // Reply to enquiry
    public void replyToEnquiry(Enquiry enquiry, String reply) {
        if (enquiry.getProjectId().equals(this.registeredProjectId)) {
            enquiry.setReply(reply);
        }
    }

    // Flat booking on behalf of successful applicant
    public boolean bookFlat(Applicant applicant, Project project, String flatType) {
        if (!project.getProjectId().equals(this.registeredProjectId)) return false;
        if (!applicant.getApplicationStatus().equals("Successful")) return false;
        if (!project.bookFlat(flatType)) return false; 
        applicant.setApplicationStatus("Booked");
        applicant.setBookedFlatType(flatType);
        return true;
    }

    // Generate booking receipt
    public String generateReceipt(Applicant applicant, Project project) {
        if (!applicant.getApplicationStatus().equals("Booked")) return "No booking found.";
        return "----- Booking Receipt -----\n"
             + "Name: " + applicant.getName() + "\n"
             + "NRIC: " + applicant.getNRIC() + "\n"
             + "Age: " + applicant.getAge() + "\n"
             + "Marital Status: " + applicant.getMaritalStatus() + "\n"
             + "Project: " + project.getName() + "\n"
             + "Flat Type: " + applicant.getBookedFlatType() + "\n";
    }
}
