package model;

import java.time.LocalDate;

public class GP extends Clinician {

    public GP(String clinicianId, String title, String speciality, int gmcNumber, String workplaceId,
              String workplaceType, String firstName, String lastName, String email, String contact,
              LocalDate DoB, String employmentStatus, LocalDate startDate){
        super(clinicianId, title, speciality, gmcNumber, workplaceId, workplaceType,
                firstName, lastName, email, contact, DoB, employmentStatus, startDate);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
