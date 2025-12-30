package CSV;

import java.io.*;
import java.util.ArrayList;

/**
 * Utility class for handling CSV file operations
 */
public class CSVHandler {

    public static void writeLines(String filename, ArrayList<String> lines) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filename));
            for (int i = 0; i < lines.size(); i++) {
                writer.write(lines.get(i));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + filename);
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<String> readLines(String filename) {
        ArrayList<String> lines = new ArrayList<>();
        BufferedReader reader = null;
        try {
            File file = new File(filename);
            if (!file.exists()) {
                System.out.println("File not found: " + filename);
                return lines;
            }

            reader = new BufferedReader(new FileReader(filename));
            String line;

            // IMPORTANT: Skip the header line
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filename);
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lines;
    }

    public static void createFileIfNotExists(String filename) {
        try {
            File file = new File(filename);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Error creating file: " + filename);
            e.printStackTrace();
        }
    }
}