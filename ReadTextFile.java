package utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.swing.JOptionPane;

public class ReadTextFile {

    private static final String ATT = "Attenzione";

    protected static List<String> readExperimentsFile(){
        // Getting the path of the level file
        Path hsPath = Paths.get(new Assets().getExperimentsFile());

        // Creating the String List
        List<String> hsLines = null;

        // Trying to read the file
        try {
            hsLines = Files.readAllLines(hsPath);
        }
        // Catching the exceptions
        catch(IOException ioe) {
            ioe.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore nella lettura del file Experiments",
                                        ATT, JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // Returning lines
        return hsLines;
    }
}