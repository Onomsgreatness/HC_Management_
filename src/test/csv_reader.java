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

//        try {
//            FileReader fr = new FileReader("appointments.csv");
//            BufferedReader br = new BufferedReader(fr);
//
//            while((line = br.readLine()) != null) {
//                System.out.println(line);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }  catch (IOException e) {
//            e.printStackTrace();
//        }

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
