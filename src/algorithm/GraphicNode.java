package algorithm;

import java.awt.geom.Point2D;
import java.util.LinkedList;

public class GraphicNode{
    private Point2D point = new Point2D.Double();
    private boolean isReal = true;
    private int name = -1;
    private boolean isNode = true;
    private LinkedList<GraphicEdge> links = new LinkedList<GraphicEdge>();
    private int[] colors = null; //0=not necessary, 1=blue, 2=red, 3=green


    protected GraphicNode(boolean isReal, int newName, double x, double y, boolean isNode, int[] newColors){
        this.isReal = isReal;
        this.name = newName;
        this.point.setLocation(x, y);
        this.isNode = isNode;
        this.colors = newColors;
    }

    protected GraphicNode (Node node, double x, double y){
        //this.nodeToRefer = node;
        this.name = node.getName();
        this.isReal = node.getIsReal();
        this.point.setLocation(x, y);
        this.colors = node.getColors();
    }

    @Override
    public String toString(){
        return "" + this.name;
    }

    //getter and setter
    public boolean getIsReal(){
        return isReal;
    }

    public LinkedList<GraphicEdge> getLinks(){
        return links;
    }

    public int getName(){
        return name;
    }

    public double getX(){
        return point.getX();
    }

    public double getY(){
        return point.getY();
    }

    public int[] getColors(){
        return colors;
    }

    public boolean getIsNode(){
        return isNode;
    }
}
