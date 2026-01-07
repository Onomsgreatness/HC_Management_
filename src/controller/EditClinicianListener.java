package controller;

import model.EmploymentStatus;
import java.util.Date;
public interface EditClinicianListener {
    void onEditClinician(
            String clinicianId, String firstName, String lastName,
            String title, String speciality, String gmcNumber,
            String contact, String email, String workplaceId,
            String workplaceType, EmploymentStatus employmentStatus, Date startDate

    );
}
