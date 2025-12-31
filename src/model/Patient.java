/**
 * Author: Onome Abuku <oa22aed@herts.ac.uk>
 *     ID: 21092431
 *     References: Dr. John Kanyaru, BookShop Example.
 */

package model;

import CSV.CSVHandler;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Patient extends Person{
    private String patientId;
    private Date DoB;
    private String nhsNumber;
    private String gender;
    private String address;
    private String postCode;
    private String emergencyContactName;
    private String emergencyContact;
    private Date registrationDate;
    private String gpSurgeryId;

    public Patient(String patientId, String firstName, String lastName, Date doB, String nhsNumber, String gender,
                   String contact, String email, String address, String postCode,
                   String emergencyContactName, String emergencyContact, Date registrationDate, String gpSurgeryId) {
        super(firstName, lastName, email, contact);
        this.patientId = patientId;
        DoB = doB;
        this.nhsNumber = nhsNumber;
        this.gender = gender;
        this.address = address;
        this.postCode = postCode;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContact = emergencyContact;
        this.registrationDate = registrationDate;
        this.gpSurgeryId = gpSurgeryId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public Date getDoB() {
        return DoB;
    }

    public void setDoB(Date doB) {
        DoB = doB;
    }

    public String getNhsNumber() {
        return nhsNumber;
    }

    public void setNhsNumber(String nhsNumber) {
        this.nhsNumber = nhsNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getGpSurgeryId() {
        return gpSurgeryId;
    }

    public void setGpSurgeryId(String gpSurgeryId) {
        this.gpSurgeryId = gpSurgeryId;
    }

    public String toCSV() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        List<String> fields = Arrays.asList(
                patientId ,getFirstName(), getLastName() ,sdf.format(DoB), nhsNumber,
                gender, getContact() ,getEmail() ,address ,postCode ,emergencyContactName,
                emergencyContact ,sdf.format(registrationDate) ,gpSurgeryId
        );
        return CSVHandler.toLine(fields);
    }

    public static Patient fromCSV(String csvLine) {
        try{
            List<String> parts = CSVHandler.parseLine(csvLine);
            if (parts.size() < 14) return null;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(parts.get(3));
            Date date1 = sdf.parse(parts.get(12));
            return new Patient(
                    parts.get(0), parts.get(1), parts.get(2), date,
                    parts.get(4), parts.get(5), parts.get(6), parts.get(7),
                    parts.get(8), parts.get(9), parts.get(10), parts.get(11),
                    date1, parts.get(13)
            );
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return  "model.Patient {"
                + "model.Patient Id: " + patientId + "\n"
                + super.toString() + "\n"
                + "NHS Number: " + nhsNumber + "\n"
                + "Gender: " + gender + "\n"
                + "Address: " + address + "\n"
                + "Post Code: " + postCode + "\n"
                + "Emergency Contact Name : Emergency Number: " + emergencyContactName + " : " + emergencyContact + "\n"
                + "Registration Date: " + sdf.format(registrationDate) + "\n"
                + "GP_Surgery Id: " + gpSurgeryId + "}";

    }
}
