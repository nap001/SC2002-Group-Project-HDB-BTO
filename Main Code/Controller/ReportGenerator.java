package Controller;

import java.util.ArrayList;
import java.util.List;

import Database.FlatBookingList;
import Entity.*;
import Interface.IReportGenerator;
import ENUM.FlatType;

public class ReportGenerator implements IReportGenerator{
	private FlatBookingList flatBookingList;
	public ReportGenerator (FlatBookingList flatBookingList)
	{
		this.flatBookingList = flatBookingList;
	}
	@Override
	public Report generateApplicantReport(Project currentlyManagedProject, String filterType, Object filterValue) {
	    List<FlatBooking> relevantBookings = new ArrayList<>();

	    for (FlatBooking booking : flatBookingList.getAllBookings()) {
	        // Filter only bookings for the current project
	        if (!booking.getProject().equals(currentlyManagedProject)) continue;

	        Applicant applicant = booking.getApplicant();

	        boolean matchesFilter = true;

	        switch (filterType.toLowerCase()) {
	            case "maritalstatus":
	                if (filterValue instanceof String status) {
	                    matchesFilter = applicant.getMaritalStatus().equalsIgnoreCase(status);
	                } else {
	                    matchesFilter = false;
	                }
	                break;

	            case "flattype":
	                if (filterValue instanceof FlatType type) {
	                    matchesFilter = booking.getFlatType().equals(type);
	                } else {
	                    matchesFilter = false;
	                }
	                break;

	            case "age":
	                if (filterValue instanceof Integer age) {
	                    matchesFilter = applicant.getAge() == age;
	                } else {
	                    matchesFilter = false;
	                }
	                break;

	            default:
	                matchesFilter = true; // If no valid filter type is provided, accept all
	                break;
	        }

	        if (matchesFilter) {
	            relevantBookings.add(booking);
	        }
	    }

	    // Create report object from filtered list
	    Report report = new Report("Applicant Flat Booking Report");

	    for (FlatBooking booking : relevantBookings) {
	        Applicant applicant = booking.getApplicant();
	        String reportLine = String.format(
	                "Applicant: %s | Age: %d | Marital Status: %s | Flat Type: %s | Project: %s",
	                applicant.getName(),
	                applicant.getAge(),
	                applicant.getMaritalStatus(),
	                booking.getFlatType(),
	                booking.getProject().getProjectName()
	        );
	        report.addLine(reportLine);
	    }

	    return report;
	}


	public void displayReport(Report generatedReport) {
		generatedReport.printReport();
	}
}
