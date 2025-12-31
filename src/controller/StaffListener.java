/**
 * Author: Onome Abuku <oa22aed@herts.ac.uk>
 *     ID: 21092431
 *     References: Dr. John Kanyaru, BookShop Example.
 */

package controller;

import model.EmploymentStatus;

import java.util.Date;

public interface StaffListener {
    void onAddStaff(String firstName, String lastName, String role, String department,
                    String facilityId, String contact, String email, EmploymentStatus employmentStatus,
                    Date startDate, String lineManager, String accessLevel);
}
