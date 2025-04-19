package ENUM;

public enum FlatType {
    TWO_ROOM("2-Room"),
    THREE_ROOM("3-Room");

    private final String displayName;

    FlatType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static FlatType fromDisplayName(String name) {
        for (FlatType type : FlatType.values()) {
            if (type.getDisplayName().equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No FlatType with display name: " + name);
    }
}
