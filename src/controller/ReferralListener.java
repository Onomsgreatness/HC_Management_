/**
 * Author: Onome Abuku <oa22aed@herts.ac.uk>
 *     ID: 21092431
 *     References: Dr. John Kanyaru, BookShop Example.
 */

package controller;

import java.util.Date;

public interface ReferralListener {
    void onAddReferral(String patientId, String referringClinicianId, String referredToClinicianId,
                       String referringFacilityId, String referringToFacilityId, Date referralDate,
                       String urgencyLevel, String referralReason, String clinicalSummary,
                       String requestedInvestigation, String appointmentId, String notes);
}
