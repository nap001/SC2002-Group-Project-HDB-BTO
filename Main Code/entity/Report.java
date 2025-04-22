package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Report implements Serializable{
    private String title;
    private List<String> lines;
    private static final long serialVersionUID = 1L;  // Add serialVersionUID for version control

    public Report(String title) {
        this.title = title;
        this.lines = new ArrayList<>();
    }

    public void addLine(String line) {
        lines.add(line);
    }

    public String getTitle() {
        return title;
    }

    public List<String> getLines() {
        return lines;
    }

    public void printReport() {
        System.out.println("=== " + title + " ===");
        if (lines.isEmpty()) {
            System.out.println("No data found for the given filter.");
        } else {
            for (String line : lines) {
                System.out.println(line);
            }
        }
    }
}
