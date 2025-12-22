package test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class csv_reader {
    public static void main(String[] args) {
        //String path = "/Users/onomeabuku/Documents/HC_Management_/appointments.csv";
        String line = "";

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
    }
}
