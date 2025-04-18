package Controller;

import java.util.ArrayList;
import java.util.List;

import Boundary.Applicant;
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

    public Report generateApplicantReport(Project currentlyManagedProject, String filterType, Object filterValue) {
        List<FlatBooking> relevantBookings = new ArrayList<>();

        for (FlatBooking booking : flatBookingList.getAllBookings()) {
            // Filter only bookings for the current project
            if (!booking.getProject().equals(currentlyManagedProject)) continue;

            Applicant applicant = booking.getApplicant();

            // Apply filter
            if ("maritalStatus".equalsIgnoreCase(filterType)) {
                if (!applicant.getMaritalStatus().equalsIgnoreCase((String) filterValue)) {
                    continue;
                }
            } else if ("flatType".equalsIgnoreCase(filterType)) {
                if (booking.getFlatType() != (FlatType) filterValue) {
                    continue;
                }
            } else if ("age".equalsIgnoreCase(filterType)) {
                if (applicant.getAge() != (int) filterValue) {
                    continue;
                }
            }

            relevantBookings.add(booking);
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
