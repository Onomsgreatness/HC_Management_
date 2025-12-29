package model;

import java.text.SimpleDateFormat;

public class Facility {
    private String facilityId;
    private String facilityName;
    private String facilityType;
    private String address;
    private String postCode;
    private String contact;
    private String email;
    private String openingHours;
    private String managerName;
    private int capacity;
    private String specialitiesOffered;

    public Facility(String facilityId, String facilityName, String facilityType, String address,
                    String postCode, String contact, String email, String openingHours, String managerName,
                    int capacity, String specialitiesOffered)
    {
        this.facilityId = facilityId;
        this.facilityName = facilityName;
        this.facilityType = facilityType;
        this.address = address;
        this.postCode = postCode;
        this.contact = contact;
        this.email = email;
        this.openingHours = openingHours;
        this.managerName = managerName;
        this.capacity = capacity;
        this.specialitiesOffered = specialitiesOffered;

    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getSpecialitiesOffered() {
        return specialitiesOffered;
    }

    public void setSpecialitiesOffered(String specialitiesOffered) {
        this.specialitiesOffered = specialitiesOffered;
    }

    public String toCSV() {
        return facilityId + "," + facilityName + "," + facilityType + "," + address + "," + postCode + ","
                + contact + "," + email + "," + openingHours + "," + managerName + "," + capacity + "," + specialitiesOffered;
    }

    public static Facility fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        return new Facility(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7],
                parts[8], Integer.parseInt(parts[9]), parts[10]);

    }
    @Override
    public String toString() {

        return "Facilities{" +
                "facilityId='" + facilityId + "\n" +
                ", facilityName='" + facilityName + "\n"+
                ", facilityType='" + facilityType + "\n" +
                ", address='" + address + "\n" +
                ", postCode='" + postCode + "\n" +
                ", contact=" + contact +
                ", email='" + email + "\n" +
                ", openingHours='" + openingHours + "\n" +
                ", managerName='" + managerName + "\n" +
                ", capacity=" + capacity +
                ", specialitiesOffered='" + specialitiesOffered + " }";
    }
}
