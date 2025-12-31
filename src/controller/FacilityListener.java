/**
 * Author: Onome Abuku <oa22aed@herts.ac.uk>
 *     ID: 21092431
 *     References: Dr. John Kanyaru, BookShop Example.
 */

package controller;

public interface FacilityListener {
    void onAddFacility(String facilityName, String facilityType, String address, String postCode,
                       String contact, String email, String openingHours, String managerName,
                       String capacity, String specialitiesOffered);
}
