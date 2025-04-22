package entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Enquiry implements Serializable{
    private String projectName;
    private String senderName;
    private String message;
    private String reply;
    private LocalDateTime timestamp; // ✅ New field
    private static final long serialVersionUID = 1L;  // Add serialVersionUID for version control

    // Constructor (sets timestamp at creation)
    public Enquiry(String projectName, String senderName, String message) {
        this.projectName = projectName;
        this.senderName = senderName;
        this.message = message;
        this.reply = null;
        this.timestamp = LocalDateTime.now(); // ✅ Auto-set timestamp
    }

    // Overloaded constructor (useful for loading from CSV etc.)
    public Enquiry(String projectName, String senderName, String message, String reply, LocalDateTime timestamp) {
        this.projectName = projectName;
        this.senderName = senderName;
        this.message = message;
        this.reply = reply;
        this.timestamp = timestamp;
    }

    // === Getters ===
    public String getProjectName() { return projectName; }
    public String getSenderName() { return senderName; }
    public String getMessage() { return message; }
    public String getReply() { return reply; }
    public LocalDateTime getTimestamp() { return timestamp; } // ✅ Getter for timestamp

    // === Setters ===
    public void setReply(String reply) {
        this.reply = reply;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Optional: toString for displaying enquiries
    @Override
    public String toString() {
        return "[" + timestamp + "] " + senderName + ": " + message + (reply != null ? " (Reply: " + reply + ")" : "");
    }

    // Recommended: equals and hashCode based on timestamp
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Enquiry)) return false;
        Enquiry other = (Enquiry) obj;
        return projectName.equals(other.projectName)
            && senderName.equals(other.senderName)
            && timestamp.equals(other.timestamp);
    }

    @Override
    public int hashCode() {
        return timestamp.hashCode();
    }

	public void setLastEdited(LocalDateTime now) {
		timestamp = now;
		
	}
}
