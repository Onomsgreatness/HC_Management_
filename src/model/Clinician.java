package model;

import java.util.Date;
import java.text.SimpleDateFormat;


public class Clinician extends Worker {
    private String clinicianId;
    private String title;
    private String speciality;
    private int gmcNumber;
    private String workplaceId;
    private String workplaceType;

    public Clinician(String clinicianId, String firstName, String lastName, String title, String speciality,
                     int gmcNumber, String contact,String email,String workplaceId,
                     String workplaceType, String employmentStatus, Date startDate){
        super(firstName, lastName, email, contact, employmentStatus, startDate);
        this.clinicianId = clinicianId;
        this.title = title;
        this.speciality = speciality;
        this.gmcNumber = gmcNumber;
        this.workplaceId = workplaceId;
        this.workplaceType = workplaceType;
    }

    public String getClinicianId() {
        return clinicianId;
    }
    public void setClinicianId(String id){
        this.clinicianId = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public int getGmcNumber() {
        return gmcNumber;
    }

    public void setGmcNumber(int gmcNumber) {
        this.gmcNumber = gmcNumber;
    }

    public String getWorkplaceId() {
        return workplaceId;
    }

    public void setWorkplaceId(String workplaceId) {
        this.workplaceId = workplaceId;
    }

    public String getWorkplaceType() {
        return workplaceType;
    }

    public void setWorkplaceType(String workplaceType) {
        this.workplaceType = workplaceType;
    }

    @Override
    public String toString(){
        return super.toString() + "Clinicians {" + " Clinician Id: " + clinicianId + "\n"
                + "Title: " + title + "\n"
                + "Speciality: " + speciality + "\n"
                + "Gmc Number: " + gmcNumber + "\n"
                + "Work place id: " + workplaceId + "\n"
                + "Work place type: " + workplaceType;
     }
}
