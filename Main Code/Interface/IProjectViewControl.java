package Interface;

import Boundary.HDBOfficer;

public interface IProjectViewControl{
    void viewAllProject();

    void viewVisibleProject();

    void viewAssignedProject(HDBOfficer officer);
}
