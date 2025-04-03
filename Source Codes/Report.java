import java.util.Map;

public class Report {
    private Map<String, String> filter;
    private String content;

    public Report(Map<String, String> filter, String content) {
        this.filter = filter;
        this.content = content;
    }

    public Map<String, String> getFilter() {
        return filter;
    }

    public String getContent() {
        return content;
    }

    public void displayReport() {
        System.out.println("===== Applicant Report =====");
        System.out.println("Filters Applied: " + filter);
        System.out.println(content);
    }
}
