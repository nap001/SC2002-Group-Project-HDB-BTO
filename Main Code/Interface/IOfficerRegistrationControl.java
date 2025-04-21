package Interface;

import Entity.HDBOfficer;
import Entity.OfficerRegistration;
import Entity.Project;

public interface IOfficerRegistrationControl {
    void addRegistration(OfficerRegistration registration);
    void removeRegistration(OfficerRegistration registration);
    boolean registerOfficerForProject(HDBOfficer officer, Project project);
}
