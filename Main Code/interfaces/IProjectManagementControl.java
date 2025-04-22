package interfaces;

import java.time.LocalDate;
import java.util.Map;

import ENUM.FlatType;
import entity.HDBManager;

public interface IProjectManagementControl {
    void createProject(HDBManager manager, String projectName, String neighbourhood,
                       LocalDate applicationOpenDate, LocalDate applicationCloseDate,
                       boolean visibility, int officerSlots,
                       Map<FlatType, Integer> availableUnits,
                       Map<FlatType, Integer> priceInput);

    void editProject(HDBManager manager, String projectName, int choice, Object newValue);

    void removeProject(HDBManager manager, String projectName);

    void toggleProjectVisibility(HDBManager manager, String projectName, boolean isVisible);
}
