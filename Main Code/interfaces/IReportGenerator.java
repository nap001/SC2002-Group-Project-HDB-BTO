package interfaces;

import entity.Project;
import entity.Report;

public interface IReportGenerator {
    Report generateApplicantReport(Project currentlyManagedProject, String filterType, Object filterValue);
    void displayReport(Report generatedReport);
}
