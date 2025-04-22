package interfaces;

import entity.HDBOfficer;

public interface IProjectViewControl{
    void viewAllProject();

    void viewVisibleProject();

    void viewAssignedProject(HDBOfficer officer);
}
