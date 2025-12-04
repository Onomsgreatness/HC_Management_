package model;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Patient extends Person{
    private String patientId;
    private String nhsNumber;
    private String gender;
    private String address;
    private String postCode;
    private String emergencyContactName;
    private String emergencyContact;
    private LocalDate registrationDate;
    private String gpSurgeryId;

    public Patient(String patientId, String firstName, String lastName,  LocalDate DoB,
                   String nhsNumber, String gender, String contact, String email,String address, String postCode,
                   String emergencyContactName, String emergencyContact, LocalDate registrationDate, String gpSurgeryId){
        super(firstName, lastName, email, contact, DoB);
        this.patientId = patientId;
        this.nhsNumber = nhsNumber;
        this.gender = gender;
        this.address = address;
        this.postCode = postCode;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContact = emergencyContact;
        this.registrationDate = registrationDate;
        this.gpSurgeryId = gpSurgeryId;

    };

    public String getPatientId(){
        return patientId;
    }

    public void setPatientId(String id){
        this.patientId = id;
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

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getGpSurgeryId() {
        return gpSurgeryId;
    }

    public void setGpSurgeryId(String gpSurgeryId) {
        this.gpSurgeryId = gpSurgeryId;
    }

    public String toCSV() {
        return patientId + "," + getFirstName() + "," + getLastName() + "," + getDoB() + "," + nhsNumber
                + "," + gender + "," + getContact() + ","  + getEmail() + "," + address + ","
                + postCode + "," + emergencyContactName + "," + emergencyContact
                + registrationDate + "," + gpSurgeryId;
    }

    public static Patient fromCSV(String csvLine) {
        try{
            String[] parts = csvLine.split(",");
            return new Patient(parts[0], parts[1], parts[2],LocalDate.parse(parts[3]),
                    parts[4], parts[5], parts[6], parts[7],
                    parts[8], parts[9],parts[10], parts[11],
                    LocalDate.parse(parts[12]), parts[13]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public String toString() {
        return super.toString() + "Patient {"
                + "Patient Id: " + patientId + "\n"
                + "NHS Number: " + nhsNumber + "\n"
                + "Gender: " + gender + "\n"
                + "Address: " + address + "\n"
                + "Post Code: " + postCode + "\n"
                + "Emergency Contact Name : Emergency Number: " + emergencyContactName + " : " + emergencyContact + "\n"
                + "Registration Date: " + registrationDate + "\n"
                + "GP_Surgery Id: " + gpSurgeryId + "}";

    }
}
