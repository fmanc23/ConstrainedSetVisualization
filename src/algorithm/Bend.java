package algorithm;

import java.awt.geom.Point2D;

public class Bend {
    private Point2D coordinates;
    private GraphicEdge firstEdge;
    private GraphicEdge secondEdge;

    protected Bend(Point2D newCoordinates){
        coordinates = newCoordinates;
    }

    //getter and setter

    protected void setFirstEdge(GraphicEdge newFirstEdge){
        this.firstEdge = newFirstEdge;
    }

    protected void setSecondEdge(GraphicEdge newSecondEdge){
        this.secondEdge = newSecondEdge;
    }

    protected Point2D getCoordinates(){
        return coordinates;
    }

    protected GraphicEdge getFirstEdge(){
        return firstEdge;
    }

    protected GraphicEdge getSecondEdge(){
        return secondEdge;
    }
}
