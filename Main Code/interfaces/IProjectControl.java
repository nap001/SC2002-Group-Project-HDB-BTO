//package Interface;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Map;
//import ENUM.FlatType;
//import Entity.Project;
//import Boundary.HDBManager;
//import Boundary.HDBOfficer;
//
//public interface IProjectControl {
//    void createProject(HDBManager manager, String projectName, String neighbourhood,
//                       LocalDate applicationOpenDate, LocalDate applicationCloseDate,
//                       boolean visibility, int officerSlots,
//                       Map<FlatType, Integer> availableUnits,
//                       Map<FlatType, Integer> priceInput);
//
//    void editProject(HDBManager manager, String projectName, int choice, Object newValue);
//    void removeProject(HDBManager manager, String projectName);
//    void toggleProjectVisibility(HDBManager manager, String projectName, boolean isVisible);
//    void viewAllProject();
//    Project getProject(String projectName);
//    boolean projectExists(String projectName);
//    void viewVisibleProject();
//    Project filterProjectsByManager(HDBManager manager);
//    List<Project> getVisibleProjects();
//	Project getProjectByOfficerNRIC(String nric);
//
//	void viewAssignedProject(HDBOfficer hdbOfficer);
//
//	List<Project> filterProjects(String filterType, Object filterValue);
//}
package interfaces;


