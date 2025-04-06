import java.util.*;
import java.util.stream.Collectors;

public class ReportGenerator {

    public Report generateApplicantReport(Project project, String filterType, Object filterValue) {
        if (project == null) {
            System.out.println("No project provided.");
            return null;
        }

        List<Applicant> applicants = project.getApplicants();  // Directly from the project

        List<Applicant> filteredApplicants = applicants.stream()
                .filter(applicant -> {
                    switch (filterType.toLowerCase()) {
                        case "maritalstatus":
                            return applicant.getMaritalStatus().equalsIgnoreCase((String) filterValue);
                        case "flattype":
                            return applicant.getFlatType().equals(filterValue);
                        case "age":
                            return applicant.getAge() == (int) filterValue;
                        default:
                            return true; // No filter applied
                    }
                })
                .collect(Collectors.toList());

        StringBuilder content = new StringBuilder();
        content.append(String.format("%-15s %-10s %-15s %-10s %-10s\n", 
                "Applicant Name", "Age", "Marital Status", "Flat Type", "Project Name"));
        content.append("----------------------------------------------------------------------------\n");

        if (filteredApplicants.isEmpty()) {
            content.append("No applicants found for the given filter.\n");
        } else {
            for (Applicant applicant : filteredApplicants) {
                content.append(String.format("%-15s %-10d %-15s %-10s %-10s\n",
                        applicant.getName(), applicant.getAge(), applicant.getMaritalStatus(),
                        applicant.getFlatType(), project.getProjectName()));
            }
        }

        Map<String, String> filter = new HashMap<>();
        filter.put(filterType, filterValue.toString());

        return new Report(filter, content.toString());
    }
}
