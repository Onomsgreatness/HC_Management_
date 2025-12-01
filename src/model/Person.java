package model;

import java.time.LocalDate;

public abstract class Person {
    private String firstName;
    private String lastName;
    private String email;
    private  String contact;
    private LocalDate DoB;

    public Person(String firstName, String lastName, String email, String contact, LocalDate DoB){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contact = contact;
        this.DoB = DoB;
    };

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String fName){
        this.firstName = fName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public LocalDate getDoB() {
        return DoB;
    }

    public void setDoB(LocalDate doB) {
        DoB = doB;
    }

    @Override
    public String toString(){
        return  "First Name: " + firstName + " Last Name: " + lastName + "\n"
                + "Email: " + email + " Contact: " + contact + "\n"
                + "Date of Birth: " + DoB;
    }
}
