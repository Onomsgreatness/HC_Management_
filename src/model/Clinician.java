/**
 * Author: Onome Abuku <oa22aed@herts.ac.uk>
 *     ID: 21092431
 *     References: Dr. John Kanyaru, BookShop Example.
 */

package model;

import CSV.CSVHandler;

import java.util.Arrays;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;


public class
Clinician extends Worker {
    private String clinicianId;
    private String title;
    private String speciality;
    private String gmcNumber;
    private String workplaceId;
    private String workplaceType;

    public Clinician(String clinicianId, String firstName, String lastName, String title, String speciality,
                     String gmcNumber, String contact,String email,String workplaceId,
                     String workplaceType, EmploymentStatus employmentStatus, Date startDate){
        super(firstName, lastName, email, contact, employmentStatus, startDate);
        this.clinicianId = clinicianId;
        this.title = title;
        this.speciality = speciality;
        this.gmcNumber = gmcNumber;
        this.workplaceId = workplaceId;
        this.workplaceType = workplaceType;
    }

    public String getClinicianId() {
        return clinicianId;
    }
    public void setClinicianId(String id){
        this.clinicianId = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getGmcNumber() {
        return gmcNumber;
    }

    public void setGmcNumber(String gmcNumber) {
        this.gmcNumber = gmcNumber;
    }

    public String getWorkplaceId() {
        return workplaceId;
    }

    public void setWorkplaceId(String workplaceId) {
        this.workplaceId = workplaceId;
    }

    public String getWorkplaceType() {
        return workplaceType;
    }

    public void setWorkplaceType(String workplaceType) {
        this.workplaceType = workplaceType;
    }

    public String toCSV() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        List<String> fields = Arrays.asList(
                clinicianId, getFirstName(), getLastName(), title , speciality ,
                gmcNumber, getContact(), getEmail(), workplaceId, workplaceType ,
                getEmploymentStatus() == null ? "" : getEmploymentStatus().toCSV() ,sdf.format(getStartDate())
        );
        return CSVHandler.toLine(fields);
    }

    public static Clinician fromCSV(String csvLine){
        try{
            List<String> parts = CSVHandler.parseLine(csvLine);
            if (parts.size() < 12) return null;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            EmploymentStatus status = EmploymentStatus.fromCSV(parts.get(10));
            return new Clinician(parts.get(0), parts.get(1), parts.get(2), parts.get(3), parts.get(4),
                    parts.get(5), parts.get(6), parts.get(7),
                    parts.get(8), parts.get(9), status , sdf.parse(parts.get(11)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString(){
        return super.toString() + "Clinicians {" + " model.Clinician Id: " + clinicianId + "\n"
                + "Title: " + title + "\n"
                + "Speciality: " + speciality + "\n"
                + "Gmc Number: " + gmcNumber + "\n"
                + "Work place id: " + workplaceId + "\n"
                + "Work place type: " + workplaceType + "\n"
                + "}";
    }
}
