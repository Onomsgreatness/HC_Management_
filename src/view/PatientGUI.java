package view;

import java.awt.*;
import javax.swing.*;

public class PatientGUI extends JFrame{
    private JTextField patientId = new JTextField();

    private JButton updateButton = new JButton("Update Patient");

    public PatientGUI(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //add form panel to the top
        add(BorderLayout.CENTER, createPatientFormPanel());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(updateButton);
        //
        setSize(400, 600);
        setVisible(true);
    }

    public JPanel createPatientFormPanel(){
        JPanel setPatientPanel = new JPanel(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(2, 5));
        panel.add(new JLabel("Patient ID: "));
        panel.add(patientId);
        return panel;
    }
}
