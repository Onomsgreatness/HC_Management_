package model;

public enum StatusType {
    SCHEDULED("Scheduled"),
    CANCELLED("Cancelled");

    private String status;

    StatusType(String status){
        this.status = status;
    }

    public static StatusType fromCSV(String value) {
        if (value == null) return null;
        String v = value.trim();

        for (StatusType s : values()) {
            if (s.name().equalsIgnoreCase(v))
                return s;
            if (s.status.equalsIgnoreCase(v))
                return s;
        }

        String part = v.toUpperCase().replace("-", "").replace(" ", "");
        if (part.equals("SCHEDULED"))
            return SCHEDULED;
        if (part.equals("CANCELLED"))
            return CANCELLED;

        throw new IllegalArgumentException("Unknown EmploymentStatus: " + value);
    }
    @Override
    public String toString() {
        return "status: " + status;
    }
}
