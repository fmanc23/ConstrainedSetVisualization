package algorithm;

public class Edge{
    private Edge edgeToRefer;
    private Node startNode;
    private Node endNode;
    private boolean page;
    private int[] name = new int[2];
    private int color = 0;
    private boolean dashed = false;

    protected Edge(Node newStartNode, Node newEndNode, boolean newPage, Edge newEdgeToRefer, int newColor){
        this.startNode = newStartNode;
        this.endNode = newEndNode;
        this.name[0] = newStartNode.getName();
        this.name[1] = newEndNode.getName();
        this.page = newPage;
        this.edgeToRefer = newEdgeToRefer;
        this.color = newColor;
        newStartNode.getLinks().add(this);
        newEndNode.getLinks().add(this);
    }

    public String toString(){
        if(edgeToRefer == null){
            return "Edge{"+ name[0] + ";" + name[1] + "};\n";
        } else {
            return "Edge{"+ name[0] + ";" + name[1] + "}; EdgeToRefer: " + edgeToRefer;
        }
    }

    //getter and setter
    protected Node getStartNode(){
        return this.startNode;
    }

    protected Node getEndNode(){
        return this.endNode;
    }

    protected int[] getName(){
        return this.name;
    }

    protected boolean getPage(){
        return this.page;
    }

    protected Edge getEdgeToRefer(){
        return this.edgeToRefer;
    }

    protected int getColor(){
        return this.color;
    }

    protected boolean getDashed(){
        return this.dashed;
    }

    protected void setEndNode(Node newEndNode){
        this.endNode = newEndNode;
    }

    protected void setPage(boolean newPage){
        this.page = newPage;
    }
    
    protected void setEdgeToRefer(Edge newEdgeToRefer){
        this.edgeToRefer = newEdgeToRefer;
    }

    protected void setDashed(Boolean newDashed){
        this.dashed = newDashed;
    }

}
