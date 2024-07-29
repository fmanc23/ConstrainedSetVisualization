package utilities;

import view.IView;
import algorithm.IAlgorithm;
public interface IUtilities {
    public void retrieveAlgorithmView(IAlgorithm algorithm, IView view);
    public void writeExperiments(long time, int numberOfNodes, int numberOfColors,
        String ratioArea, int numberOfBends, String numberOfBendsPerEdges, int numberOfCrossing);
}




