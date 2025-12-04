
import model.Appointment;
import view.PatientGUI;

public class Main {
    public static void main(String[] args){

        System.out.println("Test");

        PatientGUI view = new PatientGUI();
        Appointment a1 = new Appointment("01", "02", "03", "04", "2025-12-04", "12:00", 120, "Scheduled",
                "Scheduled", "help", "help", "2025-12-03", "2025-12-03");
        System.out.println(a1.toString());

        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
       // LocalDate dob = LocalDate.parse("01/08/2002", formatter);
        //LocalDate startDate = LocalDate.parse("04/03/2020",  formatter);

    }

}
