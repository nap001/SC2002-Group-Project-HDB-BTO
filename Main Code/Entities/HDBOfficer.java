package Entities;

import java.util.List;

import Application.Application;
import Application.Project;
import Controller.ReadCSV;
import Application.OfficerApplication;

public class HDBOfficer extends User{
		private boolean isRegistrationApproved;
		private List<Project> projectsinchargeof;
		private List<OfficerApplication> officerApplications;
		
		public List<OfficerApplication> getOfficerApplications() {
			return officerApplications;
		}

		public void setOfficerApplications(List<OfficerApplication> officerApplications) {
			this.officerApplications = officerApplications;
		}
		
		public void addOfficerApplication(OfficerApplication application) {
			this.officerApplications.add(application);
		}

		public HDBOfficer(String Name, String NRIC, int age, String maritialStatus, String password, String userType) {
	        super(Name, NRIC, age, maritialStatus, password, userType);}

		public List<Project> getProjectsinchargeof() {
			return projectsinchargeof;
		}

		public void setProjectsinchargeof(List<Project> projectsinchargeof) {
			this.projectsinchargeof = projectsinchargeof;
		}
		
		public void addProject(Project project) {
			projectsinchargeof.add(project);
		}
		
		
}
