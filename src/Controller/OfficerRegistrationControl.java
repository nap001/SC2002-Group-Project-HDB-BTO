package Controller;

import Database.OfficerRegistrationList;
import Entity.OfficerRegistration;

public class OfficerRegistrationControl {
    private OfficerRegistrationList officerRegistrationDatabase;

    public OfficerRegistrationControl(OfficerRegistrationList officerRegistrationDatabase) {
        this.officerRegistrationDatabase = officerRegistrationDatabase;
    }

    public void addRegistration(OfficerRegistration registration) {
        officerRegistrationDatabase.addRegistration(registration);
    }

    public void removeRegistration(OfficerRegistration registration) {
        officerRegistrationDatabase.removeRegistration(registration);
    }

    public OfficerRegistrationList getDatabase() {
        return officerRegistrationDatabase;
    }
}
