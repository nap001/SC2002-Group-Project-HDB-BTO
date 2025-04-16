package Collection;

import java.util.ArrayList;
import java.util.List;

import Application.*;
import Interfaces.CSVHandler; // Assuming your interface is under CSV package

public class ApplicationList{
    private List<Application> application;
    private final String csvPath;
    private final CSVHandler<Application> csvHandler;

    public ApplicationList(String csvPath, CSVHandler<Application> csvHandler) {
        this.csvPath = csvPath;
        this.csvHandler = csvHandler;
        this.application = csvHandler.loadFromCSV(csvPath);
    }

    public List<Application> getApplication() {
        return application;
    }

    public void addItem(Application app) {
    	application.add(app);
    }

    public void removeItem(Application app) {
    	application.remove(app);
    }

    
    public void saveItems() {
        csvHandler.saveToCSV(application, csvPath);
    }

    public Application findbyName(String name) {
        for (Application app : application) {
            if (app.getApplicant().getName().equalsIgnoreCase(name)) {
                return app;
            }
        }
        return null;
    }
}