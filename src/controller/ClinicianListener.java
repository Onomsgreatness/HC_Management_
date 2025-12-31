/**
 * Author: Onome Abuku <oa22aed@herts.ac.uk>
 *     ID: 21092431
 *     References: Dr. John Kanyaru, BookShop Example.
 */


package controller;

import model.EmploymentStatus;

import java.util.Date;

public interface ClinicianListener {
    void onAddClinician(String firstName, String lastName, String title, String speciality,
                        String gmcNumber, String contact, String email, String workplaceId,
                        String workplaceType, EmploymentStatus employmentStatus, Date startDate);
}
