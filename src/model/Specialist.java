/**
 * Author: Onome Abuku <oa22aed@herts.ac.uk>
 *     ID: 21092431
 *     References: Dr. John Kanyaru, BookShop Example.
 */

package model;

import java.util.Date;

public class Specialist extends Clinician {

    public Specialist (String clinicianId, String title, String speciality, String gmcNumber, String workplaceId,
                       String workplaceType, String firstName, String lastName, String email, String contact,
                       EmploymentStatus employmentStatus, Date startDate){
        super(clinicianId, firstName, lastName, title, speciality,
                gmcNumber, contact, email, workplaceId,
                workplaceType, employmentStatus, startDate);
    }

    @Override
    public String toString(){
        return super.toString();
    }
}
