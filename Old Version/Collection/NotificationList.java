package Collection;

import java.util.List;
import Application.Notification;
import Interfaces.CSVHandler;

public class NotificationList {
    private List<Notification> notifications;
    private final String csvPath;
    private final CSVHandler<Notification> csvHandler;

    public NotificationList(String csvPath, CSVHandler<Notification> csvHandler) {
        this.csvPath = csvPath;
        this.csvHandler = csvHandler;
        this.notifications = csvHandler.loadFromCSV(csvPath);
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void addItem(Notification notification) {
        notifications.add(notification);
    }

    public void removeItem(Notification notification) {
        notifications.remove(notification);
    }

    public void saveItems() {
        csvHandler.saveToCSV(notifications, csvPath);
    }

//    public Notification findByProjectName(String name) {
//        for (Notification notification : notifications) {
//            if (notification.getProjectName().equalsIgnoreCase(name)) {
//                return notification;
//            }
//        }
//        return null;
//    }
}
