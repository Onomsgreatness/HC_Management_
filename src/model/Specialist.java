package model;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Specialist extends Clinician {

    public Specialist (String clinicianId, String title, String speciality, int gmcNumber, String workplaceId,
                       String workplaceType, String firstName, String lastName, String email, String contact,
                       String employmentStatus, Date startDate){
        super(clinicianId, firstName, lastName, title, speciality,
                gmcNumber, contact, email, workplaceId,
                workplaceType, employmentStatus, startDate);
    }

    @Override
    public String toString(){
        return super.toString();
    }
}
