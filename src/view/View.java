package view;

import algorithm.IAlgorithm;
import utilities.IUtilities;

import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager;

public class View implements IView{
    private static IAlgorithm algorithm = null;
    private static IUtilities utilities = null;

    public View(IUtilities newUtilities) {
        setUtilities(newUtilities);
    };

    //Algorithm
    public void retrieveAlgorithm(IAlgorithm newAlgorithm){
        setAlgorithm(newAlgorithm);
    }
    private static void setAlgorithm(IAlgorithm newAlgorithm) {
        algorithm = newAlgorithm;
    }
    private static void setUtilities(IUtilities newUtilities) {
        utilities = newUtilities;
    }
    public static IAlgorithm getAlgorithm() {
        return algorithm;
    }
    public static IUtilities getUtilities() {
        return utilities;
    }

    // Starting the GUI
    public void createAndShowGUI() {
        // Calling the invokeLater to put the GUI related stuff in the Event Dispatch Thread
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Changing the look and feel of the application
                try {
                    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                } catch (ClassNotFoundException cnfe) {
                    cnfe.printStackTrace();
                } catch (InstantiationException ie) {
                    ie.printStackTrace();
                } catch (IllegalAccessException iae) {
                    iae.printStackTrace();
                } catch (UnsupportedLookAndFeelException ulfe) {
                    ulfe.printStackTrace();
                } catch (ClassCastException cce) {
                    cce.printStackTrace();
                } catch (NullPointerException npe) {
                    npe.printStackTrace();
                }

                // Starting the GUI
                new GeneralGUI();
            }
        });
    }
}
