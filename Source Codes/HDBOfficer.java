import java.util.List;

public class HDBOfficer extends User{
    public HDBOfficer(String NRIC, String password, int age, String maritalStatus) {
		super(NRIC, password, age, maritalStatus);
	}



    public void applyToProject(ProjectDatabase database, int projectID) {
        List<Project> projects = database.getVisibleProjects(); // Retrieve all projects from the database
        
        for (Project project : projects) {
            if (project.getProjectID() == projectID) { // Find matching project
                if (!project.getPendingOfficerApplications().contains(this)) {
                    project.applyToProject(this);
                    System.out.println("Officer " + this.NRIC + " has applied to project: " + project.getProjectName());
                } else {
                    System.out.println("Officer " + this.NRIC + " is already in the pending list for this project.");
                }
                return; // Exit the loop once the matching project is found
            }
        }
        
        // If no project with the given ID is found
        System.out.println("Project with ID " + projectID + " not found.");
    }


}
