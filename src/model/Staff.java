/**
 * Author: Onome Abuku <oa22aed@herts.ac.uk>
 *     ID: 21092431
 *     References: Dr. John Kanyaru, BookShop Example.
 */

package model;

import CSV.CSVHandler;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Staff extends Worker {
    private String staffId;
    private String role;
    private String department;
    private String facilityID;
    private String lineManager;
    private String accessLevel;

    public Staff(String staffId,String firstName, String lastName,
                 String role, String department, String facilityID,
                 String contact, String email,
                 EmploymentStatus employmentStatus, Date startDate, String lineManager, String accessLevel)
    {
        super(firstName, lastName, email, contact, employmentStatus, startDate);
        this.staffId = staffId;
        this.role = role;
        this.department = department;
        this.facilityID = facilityID;
        this.lineManager = lineManager;
        this.accessLevel = accessLevel;
    };

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(String facilityID) {
        this.facilityID = facilityID;
    }

    public String getLineManager() {
        return lineManager;
    }

    public void setLineManager(String lineManager) {
        this.lineManager = lineManager;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String toCSV(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        List<String> fields = Arrays.asList(
                staffId ,getFirstName() ,getLastName() , role, department,
                facilityID ,getContact() ,getEmail(),
                getEmploymentStatus() == null ? "" : getEmploymentStatus().toCSV(),
                formatDate(sdf, getStartDate()),lineManager, accessLevel
        );
        return CSVHandler.toLine(fields);
    }

    public static Staff fromCSV(String csvLine){
        try {
            List<String> parts = CSVHandler.parseLine(csvLine);
            if (parts.size() < 12) return null;

            EmploymentStatus status = EmploymentStatus.fromCSV(parts.get(8));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return new Staff(parts.get(0), parts.get(1), parts.get(2), parts.get(3), parts.get(4), parts.get(5),
                    parts.get(6), parts.get(7), status, parseDate(sdf, parts.get(9)), parts.get(10), parts.get(11));
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private static Date parseDate(SimpleDateFormat sdf, String value) throws Exception {
        if (value == null) return null;
        String v = value.trim();
        if (v.isEmpty()) return null;
        return sdf.parse(v);
    }

    private static String formatDate(SimpleDateFormat sdf, Date date) {
        return date == null ? "" : sdf.format(date);
    }

    @Override
    public String toString(){
        return super.toString() + "\n"
                + "model.Staff Id: " +  staffId + "\n"
                + "Role: " + role + "\n"
                + "Department: " + department + "\n"
                + "model.Facility Id: " + facilityID + "\n"
                + "Line Manager: " + lineManager+ "\n"
                + "Access Level: " + accessLevel;
    }
}
