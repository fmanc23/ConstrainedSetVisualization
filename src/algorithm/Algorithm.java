package algorithm;

import utilities.IUtilities;
import view.IView;

import java.util.LinkedList;


public class Algorithm implements IAlgorithm{
    
    private static IView view = null;
    private static IUtilities utilities = null;
    private long totalTime =0;
    private long startTime =0;
    private long stopTime =0;

    private static Drawing drawing;
    private static Testing testing;
    private static BookEmbedding bookEmbedding;

    public Algorithm(IUtilities utilities, IView view, int[] input) {
        setView(view);
        setUtilities(utilities);
        testing = new Testing(input[0], input[1]);
        startTime = System.nanoTime(); //avvio il timer
        bookEmbedding = new BookEmbedding(testing.getCoordinatesList(), testing.getColorsList());
        drawing = new Drawing(bookEmbedding, testing.getCoordinatesList());
        stopTime = System.nanoTime(); //fermo il timer
        totalTime = totalTime + (stopTime - startTime)/1000000;
        Double numberOfBendsPerEdges = (double) drawing.getNumberOfBends()/
            (bookEmbedding.getPageDown().size() + bookEmbedding.getPageUp().size());
        //stampo tutte le informazioni
        System.out.println("Total time Used for calculate " + testing.getNumberOfNodes() + " of nodes and with "
            + (testing.getNumberOfColors()-1) + " colors: "+ totalTime + " millisec");
        System.out.println("Ratio of node area to end area: " + drawing.getArea()/testing.getArea());
        System.out.println("Number of bends: " + drawing.getNumberOfBends());
        System.out.println("Number of bends per edge: " + numberOfBendsPerEdges);
        System.out.println("Number of crossing edges: " + drawing.getNumberOfCrossing());
        Double ratioArea = drawing.getArea()/testing.getArea();
        String ratioAreaString = ratioArea.toString();
        String[] dividedRatiAreaString = ratioAreaString.split("\\.");
        dividedRatiAreaString[0] = dividedRatiAreaString[0] + ",";
        ratioAreaString = dividedRatiAreaString[0] + dividedRatiAreaString[1];
        String numberOfBendsPerEdgeString = numberOfBendsPerEdges.toString();
        String[] dividedNumbertOfBendPerEdgesStrings = numberOfBendsPerEdgeString.split("\\.");
        dividedNumbertOfBendPerEdgesStrings[0] = dividedNumbertOfBendPerEdgesStrings[0] + ",";
        numberOfBendsPerEdgeString = dividedNumbertOfBendPerEdgesStrings[0] + dividedNumbertOfBendPerEdgesStrings[1];
        Algorithm.getUtilities().writeExperiments(totalTime, testing.getNumberOfNodes(), (testing.getNumberOfColors()-1),
            ratioAreaString, drawing.getNumberOfBends(), numberOfBendsPerEdgeString, drawing.getNumberOfCrossing());
    }

    public static void setView(IView newView) {
        view = newView;
    }

    public static IView getView() {
        return view;
    }

    public static void setUtilities(IUtilities newUtilities) {
        utilities = newUtilities;
    }

    public static IUtilities getUtilities() {
        return utilities;
    }

    //Drawing
    public LinkedList<GraphicNode> getGraphicNodeList(){
        return drawing.getGraphicNodeList();
    }

    public LinkedList<GraphicEdge> getGraphicEdgeList(){
        return drawing.getEdgeList();
    }

    //Testing
    public int getNumberOfColors(){
        return testing.getNumberOfColors();
    }
}
