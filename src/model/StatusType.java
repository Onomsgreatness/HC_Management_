/**
 * Author: Onome Abuku <oa22aed@herts.ac.uk>
 *     ID: 21092431
 *     References: Dr. John Kanyaru, BookShop Example.
 */

package model;

public enum StatusType {
    SCHEDULED("Scheduled"),
    CANCELLED("Cancelled");

    private final String status;

    StatusType(String status){
        this.status = status;
    }

    public String toCSV() {
        return status;
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

        throw new IllegalArgumentException("Unknown StatusType: " + value);
    }

    @Override
    public String toString() {
        return status;
    }
}
