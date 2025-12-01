package model;

import java.time.LocalDate;

public class Nurse extends Clinician {
    private String qualification;

    public Nurse(String clinicianId, String title, String speciality, int gmcNumber, String workplaceId,
                 String workplaceType, String firstName, String lastName, String email, String contact,
                 LocalDate DoB, String employmentStatus, LocalDate startDate, String qualification){
        super(clinicianId, title, speciality, gmcNumber, workplaceId, workplaceType ,
                firstName, lastName, email, contact, DoB, employmentStatus, startDate);
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
