package algorithm;

import java.util.LinkedList;

public class Node{
    private boolean isReal = true;
    private int name = -1;
    private boolean isNode = true;
    private LinkedList<Edge> links = new LinkedList<Edge>();
    private int[] colors = null; //0=not necessary, 1=blue, 2=red, 3=green

    protected Node(boolean isReal, int newName, boolean isNode, int[] newColors){
        this.isReal = isReal;
        this.name = newName;
        this.isNode = isNode;
        this.colors = newColors;
    }

    protected boolean containsColor(int color){
        for(int i=0; i<this.colors.length; i++){
            if(this.colors[i] == color){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString(){
        return "" + this.name;
    }
    
    //getter and setter
    protected int getName(){
        return name;
    }

    protected boolean getIsReal(){
        return isReal;
    }

    protected boolean getIsNode(){
        return isNode;
    }

    protected int[] getColors(){
        return colors;
    }

    protected LinkedList<Edge> getLinks(){
        return links;
    }

    protected void setName(int newName){
        this.name = newName;
    }

    protected void setIsReal(boolean newIsReal){
        this.isReal = newIsReal;
    }

    protected void setColors(int[] newColors){
        this.colors = newColors;
    }
}
