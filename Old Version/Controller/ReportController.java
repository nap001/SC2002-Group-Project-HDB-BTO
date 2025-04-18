package Controller;
import java.util.Scanner; // Add this import at the top of your file

import Application.*;
import Boundaries.*;
import Controller.*;
import Entities.*;
import Enum.*;
import Collection.*;
import Interfaces.*;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class ReportController {
    private ProjectList projectList;
    private ReportList reportList;

    public ReportController(ProjectList projectList, ReportList reportList) {
        this.projectList = projectList;
        this.reportList = reportList;
    }

    public Map<String, Object> collectFilterCriteriaFromUser(HDBManager currentManager) {
        // Get filter criteria from the user
        System.out.println("Select Filter Criteria:");
        System.out.println("1. All Applicants");
        System.out.println("2. Married Applicants");
        System.out.println("3. Single Applicants");
        System.out.println("4. Applicants by Flat Type");
        System.out.println("5. Applicants by Project");
        System.out.println("6. Applicants by Age Range");

        int filterChoice = getIntegerInput("Enter your choice: ", 1, 6);
        Map<String, String> selectedFilters = new HashMap<>();

        switch (filterChoice) {
            case 1:
                System.out.println("Generating report that includes: All applicants.");
                break;
            case 2:
                selectedFilters.put("maritalStatus", "Married");
                System.out.println("Generating report that includes: married applicants.");
                break;
            case 3:
                selectedFilters.put("maritalStatus", "Single");
                System.out.println("Generating report for single applicants.");
                break;
            case 4:
                addFlatTypeFilter(selectedFilters);
                break;
            case 5:
                addProjectFilter(selectedFilters, currentManager);
                break;
            case 6:
                addAgeRangeFilter(selectedFilters);
                break;
            default:
                return null;
        }

        return selectedFilters;
    }

    // Method to create and generate the report based on filters and projects
    public Report generateReport(String reportTitle, Map<String, Object> filters) {
        Report newReport = new Report(reportTitle, filters);
        collectBookings(newReport, filters);
        return newReport;
    }

    // Method to collect bookings based on the filters
    private void collectBookings(Report newReport, Map<String, Object> filters) {
        Set<String> processedApplicantNrics = new HashSet<>();

        for (Project project : projectList.getProjects()) {
            if (filters.containsKey("project") && !filters.get("project").equals(project)) {
                continue;
            }

            for (Application application : project.getApplications()) {
                if (application.getApplicationStatus() != ApplicationStatus.BOOKED) continue;

                Applicant applicant = application.getApplicant();
                if (processedApplicantNrics.contains(applicant.getNRIC())) continue;

                if (filters.containsKey("maritalStatus") && !filters.get("maritalStatus").equals(applicant.getMaritialStatus())) continue;
                if (filters.containsKey("minAge") && applicant.getAge() < (int) filters.get("minAge")) continue;
                if (filters.containsKey("maxAge") && applicant.getAge() > (int) filters.get("maxAge")) continue;
                if (filters.containsKey("flatType") && !filters.get("flatType").equals(application.getFlatType())) continue;

                FlatBooking existingBooking = applicant.getFlatBooking();
                if (existingBooking != null) {
                    newReport.addBooking(existingBooking);
                } else {
                    FlatBooking booking = new FlatBooking(applicant, project, application.getFlatType(), 0);
                    newReport.addBooking(booking);
                }
                processedApplicantNrics.add(applicant.getNRIC());
            }
        }
    }

    // Method to get a formatted report
    public String getFormattedReport(Report newReport) {
        if (newReport == null) return "Invalid report.";

        StringBuilder formattedReport = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        formattedReport.append("=== " + newReport.getReportTitle() + " ===\n");
        formattedReport.append("Generated On: " + dateFormat.format(newReport.getGenerationDate()) + "\n\n");

        formattedReport.append("Filter Criteria:\n");
        for (Map.Entry<String, Object> entry : newReport.getFilters().entrySet()) {
            formattedReport.append("- " + entry.getKey() + ": " + entry.getValue() + "\n");
        }

        formattedReport.append("\nBooking Information:\n");
        List<FlatBooking> bookings = newReport.getBookings();
        if (bookings.isEmpty()) {
            formattedReport.append("No bookings found matching the criteria.");
        } else {
            formattedReport.append(String.format("%-15s %-25s %-15s %-15s %-15s\n",
                    "Applicant", "Project", "Flat Type", "Status", "Booking Date"));
            formattedReport.append("------------------------------------------------------------------------------\n");
            for (FlatBooking booking : bookings) {
                formattedReport.append(String.format("%-15s %-25s %-15s %-15s %-15s\n",
                        booking.getApplicant().getName(),
                        booking.getProject().getProjectName(),
                        booking.getFlatType().toString(),
                        booking.getBookingStatus(),
                        dateFormat.format(booking.getBookingDate())
                ));
            }
            formattedReport.append("\nTotal Bookings: " + bookings.size());
        }

        return formattedReport.toString();
    }

    public boolean exportReport(Report newReport, String filename) {
        if (newReport == null) return false;
        System.out.println("Exporting report to " + filename);
        return true;
    }

    // Method to handle flat type filter
    private void addFlatTypeFilter(Map<String, Object> selectedFilters) {
        System.out.println("Select Flat Type:");
        System.out.println("1. 2-Room");
        System.out.println("2. 3-Room");
        int flatTypeChoice = getIntegerInput("Enter your choice: ", 1, 2);
        selectedFilters.put("flatType", (flatTypeChoice == 1) ? FlatType.TWO_ROOM : FlatType.THREE_ROOM);
        System.out.println("Generating report for " + (flatTypeChoice == 1 ? "2-Room" : "3-Room") + " flat applications.");
    }

    // Method to handle project filter
    private void addProjectFilter(Map<String, Object> selectedFilters, HDBManager currentManager) {
        ProjectController PC = new ProjectController(projectList);
        List<Project> availableProjects = projectList.getProjects();
        List<Project> managerProjects = projectList.getProjectsByManager(currentManager);

        if (managerProjects.isEmpty()) {
            System.out.println("You don't have any projects. Generating report for all projects.");
        } else {
            System.out.println("Your Projects:");
            for (int i = 0; i < managerProjects.size(); i++) {
                Project project = managerProjects.get(i);
                System.out.println((i + 1) + ". " + project.getProjectName());
            }

            int projectChoice = getIntegerInput("Select a project (0 for all): ", 0, managerProjects.size());

            if (projectChoice > 0) {
                Project selectedProject = managerProjects.get(projectChoice - 1);
                selectedFilters.put("project", selectedProject);
                System.out.println("Generating report for project: " + selectedProject.getProjectName());
            } else {
                System.out.println("Generating report for all projects.");
            }
        }
    }

 // Method to handle age range filter
    private void addAgeRangeFilter(Map<String, String> selectedFilters) {
        int minAge = getIntegerInput("Enter minimum age: ", 0, Integer.MAX_VALUE); // Get minimum age from user input
        int maxAge = getIntegerInput("Enter maximum age: ", minAge, Integer.MAX_VALUE); // Get maximum age from user input, ensuring it's greater than or equal to minAge
        selectedFilters.put("minAge", minAge);
        selectedFilters.put("maxAge", maxAge);
        System.out.println("Generating report for applicants aged " + minAge + " to " + maxAge);
    }


    public static int getIntegerInput(String prompt, int min, int max) {
        int input = -1; // Initializing with a value that is outside the valid range
        Scanner sc = new Scanner(System.in); // Initialize the scanner

        while (true) {
            System.out.print(prompt); // Show the prompt message to the user
            try {
                input = Integer.parseInt(sc.nextLine()); // Read user input and try to parse it to an integer

                // Check if the input is within the valid range
                if (input >= min && input <= max) {
                    break; // If valid, exit the loop
                } else {
                    System.out.println("Input must be between " + min + " and " + max + ". Please try again.");
                }
            } catch (NumberFormatException e) {
                // If the input is not an integer, display an error message and prompt the user again
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }

        return input; // Return the valid integer input
    }
}
