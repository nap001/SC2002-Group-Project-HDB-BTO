package interfaces;

import entity.HDBOfficer;
import entity.OfficerRegistration;
import entity.Project;

public interface IOfficerRegistrationControl {
    void addRegistration(OfficerRegistration registration);
    void removeRegistration(OfficerRegistration registration);
    boolean registerOfficerForProject(HDBOfficer officer, Project project);
}
