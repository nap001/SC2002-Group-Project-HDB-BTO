package Interface;

import Entity.HDBOfficer;

public interface IProjectViewControl{
    void viewAllProject();

    void viewVisibleProject();

    void viewAssignedProject(HDBOfficer officer);
}
