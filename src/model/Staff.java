package model;

import java.text.SimpleDateFormat;
import java.util.Date;

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
                 String employmentStatus, Date startDate, String lineManager, String accessLevel)
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

    public  getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(Facility facilityID) {
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

    @Override
    public String toString(){
        return super.toString() + "\n"
                + "Staff Id: " +  staffId + "\n"
                + "Role: " + role + "\n"
                + "Department: " + department + "\n"
                + "Facility Id: " + facilityID + "\n"
                + "Line Manager: " + lineManager+ "\n"
                + "Access Level: " + accessLevel;
    }
}
