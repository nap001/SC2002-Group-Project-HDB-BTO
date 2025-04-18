package Interface;

import Entity.Project;
import Entity.Report;

public interface IReportGenerator {
    Report generateApplicantReport(Project currentlyManagedProject, String filterType, Object filterValue);
    void displayReport(Report generatedReport);
}
