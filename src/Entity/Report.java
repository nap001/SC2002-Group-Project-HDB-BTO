package Entity;
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

    public void print() {
        System.out.println("Filter Criteria: " + filter);
        System.out.println("Report:\n" + content);
    }
}
