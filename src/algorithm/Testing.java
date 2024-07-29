package algorithm;

import java.util.LinkedList;
import java.util.Random;

public class Testing {
    private LinkedList<int[]> coordinatesList = new LinkedList<int[]>();
    private LinkedList<int[]> colorsList = new LinkedList<int[]>();
    private double area = 0;
    private int numberOfNodes =10;
    private int numberOfColors = 4;
    private boolean random = true;

    private int[][] colorMatrix = {{3}, {1,2}, {2}, {3}, {2}, {3}, {1}, {2}, {2}};
    private int[][] coordinates = {{200, 418},{278, 360},{450, 235},{623, 215},{700, 360},{793, 428},{995, 523},{1186, 396},{1310, 290}};

    Testing(int newNumberOfNodes, int newNumberOfColors){
        if(newNumberOfNodes > 0 && newNumberOfColors > 0){
            numberOfNodes = newNumberOfNodes;
            numberOfColors = newNumberOfColors;
            random = true;
        }
        if(random){
            for(int i=0; i<numberOfNodes; i++){
                //quanti colori per ogni nodo
                colorsList.add(new int[new Random().nextInt(1,numberOfColors)]);
                boolean[] colorsInsered = new boolean[numberOfColors];
                for(int j=0; j<colorsList.get(i).length; j++){
                    do{
                        colorsList.get(i)[j] = new Random().nextInt(1,numberOfColors);
                    }while(colorsInsered[colorsList.get(i)[j]]);
                    colorsInsered[colorsList.get(i)[j]] = true;
                }
            }
            for(int i=0; i<numberOfNodes; i++){
                coordinatesList.add(new int[]{new Random().nextInt(i*100, (i*100)+3), new Random().nextInt(200, 500)});
            }
        } else {
            
            for(int i=0; i<coordinates.length; i++){
                coordinatesList.add(coordinates[i]);
            }
            for(int i=0; i<colorMatrix.length; i++){
                colorsList.add(colorMatrix[i]);
            }
        }

        calcolateArea();
    }

    private void calcolateArea(){
        double maxUp = coordinatesList.get(0)[1];
        double maxDown = coordinatesList.get(0)[1];
        double maxRight = coordinatesList.get(0)[0];
        double maxLeft = coordinatesList.get(0)[0];
        for(int i=0; i<coordinatesList.size(); i++){
            if(coordinatesList.get(i)[1] < maxUp){
                maxUp = coordinatesList.get(i)[1];
            } else if(coordinatesList.get(i)[1] > maxDown){
                maxDown = coordinatesList.get(i)[1];
            }
            if(coordinatesList.get(i)[0] > maxRight){
                maxRight = coordinatesList.get(i)[0];
            } else if(coordinatesList.get(i)[0] < maxLeft){
                maxLeft = coordinatesList.get(i)[0];
            }
        }
        area = (maxDown - maxUp) * (maxRight - maxLeft);
    }


    //getter and setter
    protected LinkedList<int[]> getCoordinatesList(){
        return coordinatesList;
    }

    protected LinkedList<int[]> getColorsList(){
        return colorsList;
    }

    protected int getNumberOfColors(){
        return numberOfColors;
    }

    protected int getNumberOfNodes(){
        return numberOfNodes;
    }

    protected double getArea(){
        return area;
    }
}
