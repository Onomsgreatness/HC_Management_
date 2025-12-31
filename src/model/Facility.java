/**
 * Author: Onome Abuku <oa22aed@herts.ac.uk>
 *     ID: 21092431
 *     References: Dr. John Kanyaru, BookShop Example.
 */

package model;

import CSV.CSVHandler;

import java.util.Arrays;
import java.util.List;

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
    private String capacity;
    private String specialitiesOffered;

    public Facility(String facilityId, String facilityName, String facilityType, String address,
                    String postCode, String contact, String email, String openingHours, String managerName,
                    String capacity, String specialitiesOffered)
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

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getSpecialitiesOffered() {
        return specialitiesOffered;
    }

    public void setSpecialitiesOffered(String specialitiesOffered) {
        this.specialitiesOffered = specialitiesOffered;
    }

    public String toCSV() {
        List<String> fields = Arrays.asList(
                facilityId, facilityName, facilityType, address ,postCode ,
                contact, email,openingHours,managerName,capacity ,specialitiesOffered
        );
        return CSVHandler.toLine(fields);
    }

    public static Facility fromCSV(String csvLine) {
        try {
            List<String> parts = CSVHandler.parseLine(csvLine);
            if (parts.size() < 11) return null;
            return new Facility(parts.get(0), parts.get(1), parts.get(2), parts.get(3), parts.get(4), parts.get(5),
                    parts.get(6), parts.get(7), parts.get(8), parts.get(9), parts.get(10));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

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
