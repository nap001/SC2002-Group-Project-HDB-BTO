package interfaces;

import java.util.List;

import entity.Project;

public interface IApplicantProjectControl {
    void viewVisibleProject();
    Project getProject(String projectName);
    boolean projectExists(String projectName);
    List<Project> getVisibleProjects();
}
