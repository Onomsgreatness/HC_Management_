/**
 * Author: Onome Abuku <oa22aed@herts.ac.uk>
 *     ID: 21092431
 *     References: Dr. John Kanyaru, BookShop Example.
 */

package controller;

import java.util.Date;

public interface PatientListener {
    void onAddPatient(String firstName, String lastName, Date dob, String nhsNumber,
                      String gender, String contact, String email, String address,
                      String postCode, String emergencyContactName, String emergencyContact,
                      String gpSurgeryId);
}
