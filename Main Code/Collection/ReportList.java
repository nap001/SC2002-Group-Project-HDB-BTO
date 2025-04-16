package Collection;

import java.util.List;
import Application.Report;
import Interfaces.CSVHandler;

public class ReportList {
    private List<Report> reports;
    private final String csvPath;
    private final CSVHandler<Report> csvHandler;

    public ReportList(String csvPath, CSVHandler<Report> csvHandler) {
        this.csvPath = csvPath;
        this.csvHandler = csvHandler;
        this.reports = csvHandler.loadFromCSV(csvPath);
    }

    public List<Report> getReports() {
        return reports;
    }

    public void addItem(Report report) {
        reports.add(report);
    }

    public void removeItem(Report report) {
        reports.remove(report);
    }

    public void saveItems() {
        csvHandler.saveToCSV(reports, csvPath);
    }

//    public Report findByProjectName(String name) {
//        for (Report report : reports) {
//            if (report.getProjectName().equalsIgnoreCase(name)) {
//                return report;
//            }
//        }
//        return null;
//    }
    
}
