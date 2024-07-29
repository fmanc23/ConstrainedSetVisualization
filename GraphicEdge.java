package algorithm;

public class GraphicEdge{
    private GraphicNode startNode;
    private GraphicNode endNode;
    private boolean page;
    private int[] name = new int[2];
    private int color = 0;
    private boolean dashed = false;

    protected GraphicEdge(GraphicNode newStartNode, GraphicNode newEndNode, boolean newPage, int color) {
        this.startNode = newStartNode;
        this.endNode = newEndNode;
        this.name[0] = newStartNode.getName();
        this.name[1] = newEndNode.getName();
        this.page = newPage;
        this.color = color;
    }

    @Override
    public String toString(){
        return "Edge{"+ startNode.getName() + ";" + endNode.getName() + "} {( " + getStartNode().getX() + ", "
            + getStartNode().getY() + ")( " + getEndNode().getX() + ", " + getEndNode().getY() + ")};\n";
    }


    //getter and setter
    public GraphicNode getStartNode(){
        return startNode;
    }

    public GraphicNode getEndNode(){
        return endNode;
    }

    public boolean getPage(){
        return page;
    }

    public int getColor(){
        return color;
    }

    public boolean getDashed(){
        return dashed;
    }

    public void setDashed(boolean newDashed){
        this.dashed = newDashed;
    }
}
