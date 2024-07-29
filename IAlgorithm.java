package algorithm;

import java.util.LinkedList;

public interface IAlgorithm {
    //Drawing
    public LinkedList<GraphicNode> getGraphicNodeList();
    public LinkedList<GraphicEdge> getGraphicEdgeList();
    //Testing
    public int getNumberOfColors();
}
