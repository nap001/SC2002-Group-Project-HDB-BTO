package serializer;

import database.*;
import entity.*;

import java.util.ArrayList;
import java.util.List;

public class ReferenceNormalizer {

    public static void normalize(ProjectList projectList, UserList userList,
                                 OfficerRegistrationList officerList,
                                 FlatBookingList flatBookingList,
                                 ApplicantApplicationList applicantApplicationList,
                                 EnquiryList enquiryList,
                                 WithdrawalList withdrawalList) {

        // === ProjectList Normalization ===
        for (Project project : projectList.getAllProjects()) {
            // Manager
            User manager = userList.getUserByNric(project.getHdbManager().getNRIC());
            if (manager instanceof HDBManager trueManager) {
                project.setHdbManager(trueManager);
            }

            // Officers
            List<HDBOfficer> resolvedOfficers = new ArrayList<>();
            for (HDBOfficer officer : project.getHdbOfficers()) {
                User u = userList.getUserByNric(officer.getNRIC());
                if (u instanceof HDBOfficer trueOfficer) {
                    resolvedOfficers.add(trueOfficer);
                }
            }
            project.setHdbOfficers(resolvedOfficers);

            // Applicants
            List<Applicant> resolvedApplicants = new ArrayList<>();
            for (Applicant applicant : project.getApplicants()) {
                User u = userList.getUserByNric(applicant.getNRIC());
                if (u instanceof Applicant trueApplicant) {
                    resolvedApplicants.add(trueApplicant);
                }
            }
            project.setApplicants(resolvedApplicants);
        }

     // Normalize references in FlatBookingList
        for (FlatBooking booking : flatBookingList.getAllBookings()) {
            User u = userList.getUserByNric(booking.getApplicant().getNRIC());
            if (u instanceof Applicant trueApplicant) {
                booking.setApplicant(trueApplicant);
            }

            Project project = projectList.getProjects(booking.getProject().getProjectName());
            if (project != null) {
                booking.setProject(project);
            }
        }
     // Normalize references in OfficerRegistrationList
        for (OfficerRegistration registration : officerList.getAllRegistrations()) {
            // Resolve officer by NRIC
            User u = userList.getUserByNric(registration.getOfficer().getNRIC());
            if (u instanceof HDBOfficer trueOfficer) {
                registration.setOfficer(trueOfficer);
            }

            // Resolve project by project name
            Project p = projectList.getProjects(registration.getProject().getProjectName());
            if (p != null) {
                registration.setProject(p);
            }
        }

     // Normalize references in ApplicantApplicationList
        for (ApplicantApplication application : applicantApplicationList.getAllApplications()) {
            // Resolve Applicant reference from UserList
            User u = userList.getUserByNric(application.getApplicant().getNRIC());
            if (u instanceof Applicant trueApplicant) {
                application.setApplicant(trueApplicant);
            }

            // Normalize projectName: verify if it corresponds to an existing project
            Project p = projectList.getProjects(application.getProjectName());
            if (p != null) {
                // Re-set project name in case of normalization (e.g., capitalization or name correction)
                application.setProjectName(p.getProjectName());
            } else {
                System.out.println("Warning: Project not found for application with project name: " + application.getProjectName());
            }
        }


}
}
