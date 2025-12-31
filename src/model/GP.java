/**
 * Author: Onome Abuku <oa22aed@herts.ac.uk>
 *     ID: 21092431
 *     References: Dr. John Kanyaru, BookShop Example.
 */

package model;

import java.util.Date;

public class GP extends Clinician {

    public GP(String clinicianId, String firstName, String lastName, String title, String speciality,
              String gmcNumber, String contact,String email,String workplaceId,
              String workplaceType, EmploymentStatus employmentStatus, Date startDate){
        super(clinicianId, firstName, lastName, title, speciality,
                gmcNumber, contact, email, workplaceId,
                workplaceType, employmentStatus, startDate);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
