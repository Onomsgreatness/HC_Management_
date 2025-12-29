package model;

public enum EmploymentStatus {
    FULLTIME("Full-Time"),
    PARTTIME("Part-Time");

    private final String status;

    EmploymentStatus (String status) { this.status = status; }

    public String getStatus(){
        return status;
    }

    public static EmploymentStatus fromCSV(String value) {
        if (value == null) return null;
        String v = value.trim();

        for (EmploymentStatus s : values()) {
            if (s.name().equalsIgnoreCase(v))
                return s;
            if (s.status.equalsIgnoreCase(v))
                return s;
        }

        String part = v.toUpperCase().replace("-", "").replace(" ", "");
        if (part.equals("FULLTIME"))
            return FULLTIME;
        if (part.equals("PARTTIME"))
            return PARTTIME;

        throw new IllegalArgumentException("Unknown EmploymentStatus: " + value);
    }

    @Override
    public String toString(){
        return "Employment Status: " + status;
    }

}
