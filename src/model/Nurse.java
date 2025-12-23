package model;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Nurse extends Clinician {
    private String qualification;

    public Nurse(String clinicianId, String title, String speciality, int gmcNumber, String workplaceId,
                 String workplaceType, String firstName, String lastName, String email, String contact,
                 EmploymentStatus employmentStatus, Date startDate, String qualification){
        super(clinicianId, firstName, lastName, title, speciality,
                gmcNumber, contact, email, workplaceId,
                workplaceType, employmentStatus, startDate);
        this.qualification = qualification;
    }

    public String getQualification(){
        return qualification;
    }

    public void setQualification(){
        this.qualification = qualification;
    }

    @Override
    public String toString(){
        return super.toString()  + "\n"
                + "Qualification: " + qualification;
    }
}
