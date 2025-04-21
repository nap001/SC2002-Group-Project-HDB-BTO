package Controller;

import java.util.List;

import Entity.Project;
import Interface.IApplicantProjectControl;
import Interface.IProjectQueryControl;
import Interface.IProjectViewControl;

public class ApplicantProjectControl implements IApplicantProjectControl {
    private IProjectQueryControl queryControl;
    private IProjectViewControl viewControl;

    public ApplicantProjectControl(IProjectQueryControl queryControl, IProjectViewControl viewControl) {
        this.queryControl = queryControl;
        this.viewControl = viewControl;
    }

    @Override
    public void viewVisibleProject() {
        viewControl.viewVisibleProject(); // delegate viewing
    }

    @Override
    public Project getProject(String projectName) {
        return queryControl.getProject(projectName);
    }

    @Override
    public boolean projectExists(String projectName) {
        return queryControl.projectExists(projectName);
    }

    @Override
    public List<Project> getVisibleProjects() {
        return queryControl.getVisibleProjects();
    }
}
