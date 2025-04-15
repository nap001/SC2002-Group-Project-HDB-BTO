package Collection;

import java.util.List;
import Application.OfficerApplication;
import Interfaces.CSVHandler;

public class OfficerApplicationList {
    private List<OfficerApplication> applications;
    private final String csvPath;
    private final CSVHandler<OfficerApplication> csvHandler;

    public OfficerApplicationList(String csvPath, CSVHandler<OfficerApplication> csvHandler) {
        this.csvPath = csvPath;
        this.csvHandler = csvHandler;
        this.applications = csvHandler.loadFromCSV(csvPath);
    }

    public List<OfficerApplication> getApplications() {
        return applications;
    }

    public void addItem(OfficerApplication app) {
        applications.add(app);
    }

    public void removeItem(OfficerApplication app) {
        applications.remove(app);
    }

    public void saveItems() {
        csvHandler.saveToCSV(applications, csvPath);
    }

    public OfficerApplication findByProjectName(String name) {
        for (OfficerApplication app : applications) {
            if (app.getProjectName().equalsIgnoreCase(name)) {
                return app;
            }
        }
        return null;
    }
}
