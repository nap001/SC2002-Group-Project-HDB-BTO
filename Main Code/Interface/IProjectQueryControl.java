package Interface;

import java.util.List;

import Boundary.HDBManager;
import Entity.Project;

public interface IProjectQueryControl{
    Project getProject(String projectName);
    Project filterProjectsByManager(HDBManager manager);
    boolean projectExists(String projectName);
    List<Project> filterProjects(String filterType, Object filterValue);
    List<Project> getVisibleProjects();
    Project getProjectByOfficerNRIC(String officerNRIC);
}
