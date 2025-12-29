package test;

import model.HCModel;
import model.Patient;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class csv_reader {
    public static void main(String[] args) {
        //String path = "/Users/onomeabuku/Documents/HC_Management_/appointments.csv";
        String line = "";

        System.out.println("\n\n*******Appointments***********/n");
        try {
            FileReader fr = new FileReader("appointments.csv");
            BufferedReader br = new BufferedReader(fr);

            while((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("\n\n*******Facilities***********/n");
        try {
            FileReader fr = new FileReader("facilities.csv");
            BufferedReader br = new BufferedReader(fr);

            while((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("\n\n*******Patientss***********/n");
        try {
            FileReader fr = new FileReader("patients.csv");
            BufferedReader br = new BufferedReader(fr);

            while((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("\n\n*******Clinicians***********/n");
        try {
            FileReader fr = new FileReader("clinicians.csv");
            BufferedReader br = new BufferedReader(fr);

            while((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("\n\n*******Prescription***********/n");
        try {
            FileReader fr = new FileReader("prescriptions.csv");
            BufferedReader br = new BufferedReader(fr);

            while((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("/\n\n*******Referral***********/n");
        try {
            FileReader fr = new FileReader("referrals.csv");
            BufferedReader br = new BufferedReader(fr);

            while((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("\n\n*******Staff***********/n");
        try {
            FileReader fr = new FileReader("staff.csv");
            BufferedReader br = new BufferedReader(fr);

            while((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("\n\nPatient Object");
        //=======Create a Patient instance===========
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dob = sdf.parse("1985-03-15");
            Date regDate = sdf.parse("2020-01-15");
            Patient p1 = new Patient("P001", "John", "Smith", dob,
                    "1234567890", " M ", "07123456789", "john.smith@email.com",
                    "123 Oak Street, Birmingham", "B1 1AA", "Sarah Smith",
                    "07987654321",regDate, "S001");
            System.out.println(p1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
