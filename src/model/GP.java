package model;

import java.util.Date;
import java.text.SimpleDateFormat;

public class GP extends Clinician {

    public GP(String clinicianId, String firstName, String lastName, String title, String speciality,
              int gmcNumber, String contact,String email,String workplaceId,
              String workplaceType, String employmentStatus, Date startDate){
        super(clinicianId, firstName, lastName, title, speciality,
                gmcNumber, contact, email, workplaceId,
                workplaceType, employmentStatus, startDate);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
