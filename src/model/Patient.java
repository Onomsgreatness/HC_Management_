package model;

import java.time.LocalDate;

public class Patient extends Person{
    private String patientId;
    private int nhsNumber;
    private String gender;
    private String address;
    private String postCode;
    private String emergencyContactName;
    private int emergencyContact;
    private LocalDate registrationDate;
    private GP gpSurgery;

    public Patient(String firstName, String lastName, String email, String contact, LocalDate DoB, String patientId,
                   int nhsNumber, String gender, String address, String postCode,
                   String emergencyContactName, int emergencyContact, LocalDate registrationDate, GP gpSurgery){
        super(firstName, lastName, email, contact, DoB);
        this.patientId = patientId;
        this.nhsNumber = nhsNumber;
        this.gender = gender;
        this.address = address;
        this.postCode = postCode;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContact = emergencyContact;
        this.registrationDate = registrationDate;
        this.gpSurgery = gpSurgery;

    };

    public String getPatientId(){
        return patientId;
    }

    public void setPatientId(String id){
        this.patientId = id;
    }

    public int getNhsNumber() {
        return nhsNumber;
    }

    public void setNhsNumber(int nhsNumber) {
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

    public int getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(int emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public GP getGpSurgery() {
        return gpSurgery;
    }

    public void setGpSurgery(GP gpSurgery) {
        this.gpSurgery = gpSurgery;
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
                + "GP_Surgery Id: " + gpSurgery + "}";

    }
}
