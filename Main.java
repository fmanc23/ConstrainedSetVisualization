import algorithm.IAlgorithm;
import algorithm.Algorithm;
import view.IView;
import view.View;
import utilities.IUtilities;
import utilities.Utilities;

public class Main {
    public static void main(String[] args) {
        createAlgorithmViewAndGUI(new int[]{0, 0});
    }

    public static void createAlgorithmViewAndGUI(int[] input){
        // Creating algorithm and view objects and Assigning utilities to view and algorithm
        IUtilities utilities = new Utilities();
        IView view = new View(utilities);
        IAlgorithm algorithm = new Algorithm(utilities, view, input);
        // Assigning algorithm to view, vice-versa and algorithm and view to utilities
        view.retrieveAlgorithm(algorithm);
        utilities.retrieveAlgorithmView(algorithm, view);
        // Start the GUI
        if(input[0] == 0){
            view.createAndShowGUI();
        }
    }
}