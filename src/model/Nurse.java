/**
 * Author: Onome Abuku <oa22aed@herts.ac.uk>
 *     ID: 21092431
 *     References: Dr. John Kanyaru, BookShop Example.
 */

package model;

import java.util.Date;

public class Nurse extends Clinician {
    private String qualification;

    public Nurse(String clinicianId, String title, String speciality, String gmcNumber, String workplaceId,
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

    public void setQualification(String qualification){
        this.qualification = qualification;
    }

    @Override
    public String toString(){
        return super.toString()  + "\n"
                + "Qualification: " + qualification;
    }
}
