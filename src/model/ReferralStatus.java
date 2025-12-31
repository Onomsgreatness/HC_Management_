/**
 * Author: Onome Abuku <oa22aed@herts.ac.uk>
 *     ID: 21092431
 *     References: Dr. John Kanyaru, BookShop Example.
 */

package model;

public enum ReferralStatus {
    COMPLETED("Completed"),
    PENDING("Pending"),
    INPROGRESS("In Progress"),
    NEW("New");

    private final String status;

    ReferralStatus(String status){
        this.status = status;
    }

    public String toCSV() {
        return status;
    }

    public static ReferralStatus fromCSV(String value) {
        if (value == null) return null;
        String v = value.trim();

        for (ReferralStatus s : values()) {
            if (s.name().equalsIgnoreCase(v))
                return s;
            if (s.status.equalsIgnoreCase(v))
                return s;
        }

        String part = v.toUpperCase().replace("-", "").replace(" ", "");
        if (part.equals("COMPLETED"))
            return COMPLETED;
        if (part.equals("PENDING"))
            return PENDING;
        if (part.equals("INPROGRESS"))
            return INPROGRESS;
        if (part.equals("NEW"))
            return NEW;

        throw new IllegalArgumentException("Unknown ReferralStatus: " + value);
    }

    @Override
    public String toString(){
        return status;
    }
}
