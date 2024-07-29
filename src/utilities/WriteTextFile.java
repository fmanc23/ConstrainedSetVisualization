package utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

public class WriteTextFile {

    private static final String ATT = "Attenzione";

	protected static void writeExperiments(long time, int numberOfNodes, int numberOfColors,
        String ratioArea, int numberOfBends, String numberOfBendsPerEdges, int numberOfCrossing) {

        BufferedWriter writer = null;
        try{
            // Creating a file object
            File hsFile = new File(new Assets().getExperimentsFile());
            // Using a BufferedReader to append text to file
            writer = new BufferedWriter(new FileWriter(hsFile, true));
            writer.append(numberOfNodes + " " + numberOfColors 
                + " " + time + " " + ratioArea + " " + numberOfBends + " " + numberOfBendsPerEdges+ " " + numberOfCrossing + "\n");
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore nell'apertura del file Experiments",
                                        ATT, JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } catch(IOException ioe) {
            ioe.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore nella scrittura del file Experiments",
                                        ATT, JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } finally {
            try {
                writer.close();
            } catch (NullPointerException npe) {
                npe.printStackTrace();
                JOptionPane.showMessageDialog(null, "Errore nella chiusura del BufferedWriter - oggetto nullo",
                                        ATT, JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            } catch(IOException ioe) {
                ioe.printStackTrace();
                JOptionPane.showMessageDialog(null, "Errore nella chiusura del BufferedWriter - IOException",
                                        ATT, JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        }
    }
}
