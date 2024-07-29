package utilities;

import algorithm.IAlgorithm;
import view.IView;
public class Utilities implements IUtilities {
    private static IAlgorithm algorithm = null;
    private static IView view = null;

    public Utilities() {
        //todo
    }

    public void retrieveAlgorithmView(IAlgorithm newAlgorithm, IView newView) {
        algorithm = newAlgorithm;
        view = newView;
    }

    public static IAlgorithm getAlgorithm() {
        return algorithm;
    }

    public static  IView getView() {
        return view;
    }

    public void writeExperiments(long time, int numberOfNodes, int numberOfColors, String ratioArea,
        int numberOfBends, String numberOfBendsPerEdges, int numberOfCrossing){
        WriteTextFile.writeExperiments(time, numberOfNodes, numberOfColors, ratioArea, numberOfBends, numberOfBendsPerEdges, numberOfCrossing);
    }
}
