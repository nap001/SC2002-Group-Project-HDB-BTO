package Interface;

import java.util.List;
import Entity.Project;

public interface IApplicantProjectControl {
    void viewVisibleProject();
    Project getProject(String projectName);
    boolean projectExists(String projectName);
    List<Project> getVisibleProjects();
}
