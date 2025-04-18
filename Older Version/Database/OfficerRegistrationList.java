package Database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Entity.OfficerRegistration;
import Boundary.HDBOfficer;
import Entity.Project;

public class OfficerRegistrationList implements Serializable{

    private List<OfficerRegistration> registrations;
    private static final long serialVersionUID = 1L;  // Add serialVersionUID for version control

    public OfficerRegistrationList() {
        registrations = new ArrayList<>();
    }

    // Add a new registration
    public void addRegistration(OfficerRegistration registration) {
        if (registration != null) {
            registrations.add(registration);
        }
    }

    // Remove a registration
    public boolean removeRegistration(OfficerRegistration registration) {
        return registrations.remove(registration);
    }

    // Get all registrations
    public List<OfficerRegistration> getAllRegistrations() {
        return new ArrayList<>(registrations);
    }

    // Get registrations by officer
    public List<OfficerRegistration> getRegistrationsByOfficer(HDBOfficer officer) {
        List<OfficerRegistration> result = new ArrayList<>();
        for (OfficerRegistration reg : registrations) {
            if (reg.getOfficer().equals(officer)) {
                result.add(reg);
            }
        }
        return result;
    }

    // Get registrations by project
    public List<OfficerRegistration> getRegistrationsByProject(Project project) {
        List<OfficerRegistration> result = new ArrayList<>();
        for (OfficerRegistration reg : registrations) {
            if (reg.getProject().equals(project)) {
                result.add(reg);
            }
        }
        return result;
    }

    // Get approved registrations
    public List<OfficerRegistration> getApprovedRegistrations() {
        List<OfficerRegistration> approved = new ArrayList<>();
        for (OfficerRegistration reg : registrations) {
            if (reg.getStatus()) {
                approved.add(reg);
            }
        }
        return approved;
    }

    // Get pending registrations
    public List<OfficerRegistration> getPendingRegistrations() {
        List<OfficerRegistration> pending = new ArrayList<>();
        for (OfficerRegistration reg : registrations) {
            if (!reg.getStatus()) {
                pending.add(reg);
            }
        }
        return pending;
    }
}
