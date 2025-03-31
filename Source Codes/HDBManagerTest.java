import java.time.LocalDate;
import java.util.*;

public class HDBManagerTest {
    public static void main(String[] args) {
        // Step 1: Create a ProjectDatabase
        ProjectDatabase database = new ProjectDatabase();

        // Step 2: Create multiple HDBManagers
        HDBManager manager1 = new HDBManager("Alice","Passwords", 26, "Married");
        HDBManager manager2 = new HDBManager("Gay","Passwords", 26, "Married");
        HDBOfficer officer1 = new HDBOfficer("Jeff","Passwords", 26, "Married");

        // Step 3: Define available units for projects
        Map<FlatType, Integer> units1 = new HashMap<>();
        units1.put(FlatType.TWO_ROOM, 100);
        units1.put(FlatType.THREE_ROOM, 50);

        Map<FlatType, Integer> units2 = new HashMap<>();
        units2.put(FlatType.TWO_ROOM, 80);
        units2.put(FlatType.THREE_ROOM, 40);

        // Step 4: Create projects using HDBManagers
        manager1.createProject(database, 1, "Sunrise Residency", "Jurong",
                LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 30),
                true, 2, units1);

        manager2.createProject(database, 2, "Serenity Towers", "Tampines",
                LocalDate.of(2024, 7, 1), LocalDate.of(2024, 7, 31),
                true, 3, units2);
        
        // Step 5: Display all projects in the database
        System.out.println("\n--- All Projects in Database ---");
        database.displayAllProjects();
    
        manager1.toggleProjectVisibility(database, 2, false);
        // Step 5: Display all projects in the database
        System.out.println("\n--- All Projects in Database ---");
        officer1.applyToProject(database, 1);
        database.displayAllProjects();
        manager1.approveOfficerApplication(database, 1, officer1, true);
        database.displayAllProjects();
        manager1.removeProjectFromDatabase(database, 1);
        database.displayAllProjects();
    }
}
