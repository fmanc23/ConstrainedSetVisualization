package algorithm;

import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.LinkedList;

public class Drawing{
    private LinkedList<GraphicNode> graphicNodeList = new LinkedList<GraphicNode>(); //lista di nodi che verrà passata alla MainGUI
    private LinkedList<GraphicEdge> graphicEdgeList = new LinkedList<GraphicEdge>(); //lista di archi che verrà passata alla MainGUI
    private LinkedList<Bend> bendsList = new LinkedList<Bend>();
    private double area = 0;
    private int numberOfEdges = 0;
    private int numberOfCrossing = 0;
    //classe che dato un BookEmbedding e le coordinate dei punti, restituisca una lista di punti e la lista di archi associata
    protected Drawing(BookEmbedding bookEmbedding, LinkedList<int[]> coordinates){
        //riempio la LinkedList dei punti da passare al MainGui
        int coordinatesCount = 0;
        Iterator<Node> iteratorNode = bookEmbedding.getNodes().iterator();
        while(iteratorNode.hasNext()){
            Node nodeToCopy = iteratorNode.next();
            if(nodeToCopy.getIsReal() == true){
                //dando ai nodi reali le coordinate
                graphicNodeList.add(new GraphicNode(
                    nodeToCopy, coordinates.get(coordinatesCount)[0], coordinates.get(coordinatesCount)[1]));
                    coordinatesCount++;
            } else {
                graphicNodeList.add(null);
            }     
        }
            
        //calcolo, per solo i nodi falsi, le coordinate che devono avere
        AddDummyPoints();   

        //per ogni nodo, creo la lista di adiacenza utilizzando la linkedList links nella classe GraphicNode, il pageUp
        Iterator<Edge> iteratorUp = bookEmbedding.getPageUp().iterator();
        while(iteratorUp.hasNext()){
            Edge edgeToInsert = iteratorUp.next();
            graphicNodeList.get(edgeToInsert.getStartNode().getName()).getLinks().add(
                new GraphicEdge(
                    graphicNodeList.get(edgeToInsert.getStartNode().getName()), 
                    graphicNodeList.get(edgeToInsert.getEndNode().getName()), true, edgeToInsert.getColor()));
        }
        
        //per ogni nodo, creo la lista di adiacenza utilizzando la linkedList links nella classe GraphicNode, il pageDown
        Iterator<Edge> iteratorDown = bookEmbedding.getPageDown().iterator();
        while(iteratorDown.hasNext()){
            Edge edgeToInsert = iteratorDown.next();
            graphicNodeList.get(edgeToInsert.getStartNode().getName()).getLinks().add(
                new GraphicEdge(
                    graphicNodeList.get(edgeToInsert.getStartNode().getName()),
                    graphicNodeList.get(edgeToInsert.getEndNode().getName()), false, edgeToInsert.getColor()));       
        }
        

        //si calcola la slope di tutti gli archi che collegano nodi consecutivi, anche quelli non esistenti
        double maxSlope=0;
        double slope=0;
        for(int i=1; i<graphicNodeList.size(); i++){
            slope = (graphicNodeList.get(i-1).getY()-graphicNodeList.get(i).getY())/
                (graphicNodeList.get(i-1).getX()- graphicNodeList.get(i).getX());
            //if che serve a risolvere il problema della slope infinita
            if(slope != Double.POSITIVE_INFINITY && slope != Double.NEGATIVE_INFINITY){
                if(slope > maxSlope){
                    maxSlope = slope;
                }else if(slope < -maxSlope){
                    maxSlope = -slope;
                }
            }
        }
        System.out.println("MaxSlope: " + maxSlope);
        createBends(maxSlope);
        cutBends();

        //cancello tutti i nodi falsi dalla lista che manderò alla view
        Iterator<GraphicNode> iterator = ((LinkedList<GraphicNode>)graphicNodeList.clone()).iterator();
        while(iterator.hasNext()){
            GraphicNode nodeToCheck = iterator.next();
            if(nodeToCheck.getIsReal() == false){
                graphicNodeList.remove(nodeToCheck);
            }
        }

        //serve per disegnare gli archi sovrapposti con delle linee tratteggiate
        Iterator<GraphicEdge> iteratorGraphicEdge = graphicEdgeList.iterator();
        while(iteratorGraphicEdge.hasNext()){
            GraphicEdge edgeToCheck = iteratorGraphicEdge.next();
            for(int i=graphicEdgeList.indexOf(edgeToCheck)+1; i<graphicEdgeList.size(); i++){
                if(edgeToCheck.getStartNode().getX() == graphicEdgeList.get(i).getStartNode().getX() &&
                    edgeToCheck.getStartNode().getY() == graphicEdgeList.get(i).getStartNode().getY() &&
                    edgeToCheck.getEndNode().getX() == graphicEdgeList.get(i).getEndNode().getX() &&
                    edgeToCheck.getEndNode().getY() == graphicEdgeList.get(i).getEndNode().getY() &&
                    edgeToCheck != graphicEdgeList.get(i)){
                    edgeToCheck.setDashed(true);
                    graphicEdgeList.get(i).setDashed(true);
                }
            }
        }
        calcolateArea();
        calcolateCrossingEdges();
    }

    /*scandisce il bookembedding e controlla quali nodi sono falsi, inserisce geometricamnete
    i punti di attraversamento, serve per vedere quali nodi dati dal BE sono falsi e
    disegnarli in mezzo al predecessore e successori (ci possono essere anche piu di uno)*/
    private void AddDummyPoints(){
        for(int i=1; i<graphicNodeList.size(); i++){
            if(graphicNodeList.get(i) == null){
                /*nel caso ci fosse più nodi falsi consegutivi, si calcola, usando il precedente e il successivo della sequenza,
                tutte le coordinate di tutti i nodi della sequenza equidistanziandoli nell'intervallo*/
                int consegutivesDummyNodes = 1;
                for(int j=i+1; j<graphicNodeList.size(); j++){
                    if(graphicNodeList.get(j) == null){
                        consegutivesDummyNodes++;
                    } else {
                        break;
                    }
                }
                double xCoord = (graphicNodeList.get(i+consegutivesDummyNodes).getX() - graphicNodeList.get(i-1).getX())
                    /(consegutivesDummyNodes + 1);
                double yCoord=0;
                double yMin=0;
                if(graphicNodeList.get(i+consegutivesDummyNodes).getY() < graphicNodeList.get(i-1).getY()){
                    yCoord = (graphicNodeList.get(i+consegutivesDummyNodes).getY() - graphicNodeList.get(i-1).getY())
                        /(consegutivesDummyNodes + 1);
                    yMin = graphicNodeList.get(i-1).getY();
                } else {
                    yCoord = (graphicNodeList.get(i-1).getY() - graphicNodeList.get(i+consegutivesDummyNodes).getY())
                        /(consegutivesDummyNodes + 1);
                    yMin = graphicNodeList.get(i+consegutivesDummyNodes).getY();
                }
                for(int j=1; j<=consegutivesDummyNodes; j++){
                    graphicNodeList.set(i+j-1, (new GraphicNode(false, -1,  graphicNodeList.get(i-1).getX() + (xCoord*j),
                        yMin + (yCoord*j), true, null)));
                }
                i += consegutivesDummyNodes;
            }
        }
    }

    //metodo che aggiunge al graphicEdgeList tutti gli archi ed aggiunge i bend quando servono per unire due archi non consecutivi
    private void createBends(double slope){
        Iterator<GraphicNode> iterator = graphicNodeList.iterator();
        while(iterator.hasNext()){
            GraphicNode nodeToCheck = iterator.next();
            GraphicEdge exchange;
            //ordino gli archi dall'arco con endNode più vicino a quello più lontano
            for(int i=0; i<nodeToCheck.getLinks().size(); i++){
                for(int j=i+1; j<nodeToCheck.getLinks().size(); j++){
                    if(nodeToCheck.getLinks().get(i).getEndNode().getName() >
                        nodeToCheck.getLinks().get(j).getEndNode().getName()){
                        exchange = nodeToCheck.getLinks().get(i);
                        nodeToCheck.getLinks().set(i, nodeToCheck.getLinks().get(j));
                        nodeToCheck.getLinks().set(j, exchange);
                    }
                }
            }
            
            for(int j=0; j<nodeToCheck.getLinks().size(); j++){
                if(nodeToCheck.getLinks().get(j).getPage() == true){
                    //caso archi sopra
                    /*prendo il primo nodo vero, così, se devono essere collegati,
                    non faccio bend inutili per causa di nodi di attraversamento*/
                    int index=graphicNodeList.indexOf(nodeToCheck);
                    do{
                        index++;
                    }while(graphicNodeList.get(index).getIsReal() != true);
                    GraphicNode firstRealNode = graphicNodeList.get(index);
                    if(nodeToCheck.getLinks().get(j).getEndNode() == firstRealNode){
                        //caso archi con nodi consecutivi
                        graphicEdgeList.add(
                            new GraphicEdge(nodeToCheck, nodeToCheck.getLinks().get(j).getEndNode(), 
                                true, nodeToCheck.getLinks().get(j).getColor()));
                    } else {
                        Bend bendPoint = calculateBend(
                            nodeToCheck.getLinks().get(j).getStartNode().getX(),
                            nodeToCheck.getLinks().get(j).getStartNode().getY(),
                            nodeToCheck.getLinks().get(j).getEndNode().getX(),
                            nodeToCheck.getLinks().get(j).getEndNode().getY(), -slope - j);
                        GraphicNode fakeNode = new GraphicNode(false, -1, 
                            bendPoint.getCoordinates().getX(), bendPoint.getCoordinates().getY(), false, null);
                        graphicEdgeList.add(new GraphicEdge(nodeToCheck, fakeNode, true, nodeToCheck.getLinks().get(j).getColor()));
                        bendPoint.setFirstEdge(graphicEdgeList.getLast());
                        graphicEdgeList.add(new GraphicEdge(fakeNode, nodeToCheck.getLinks().get(j).getEndNode(),
                            true, nodeToCheck.getLinks().get(j).getColor()));
                        bendPoint.setSecondEdge(graphicEdgeList.getLast());
                        bendsList.add(bendPoint);
                    }
                } else {
                    //caso archi sotto
                    Bend bendPoint = calculateBend(
                            nodeToCheck.getLinks().get(j).getStartNode().getX(),
                            nodeToCheck.getLinks().get(j).getStartNode().getY(),
                            nodeToCheck.getLinks().get(j).getEndNode().getX(),
                            nodeToCheck.getLinks().get(j).getEndNode().getY(), slope + j);
                    GraphicNode fakeNode = new GraphicNode(false, -1, bendPoint.getCoordinates().getX(), 
                        bendPoint.getCoordinates().getY(), false, null);
                    graphicEdgeList.add(new GraphicEdge(nodeToCheck, fakeNode, false, nodeToCheck.getLinks().get(j).getColor()));
                    bendPoint.setFirstEdge(graphicEdgeList.getLast());
                    graphicEdgeList.add(new GraphicEdge(fakeNode, nodeToCheck.getLinks().get(j).getEndNode(), 
                        true, nodeToCheck.getLinks().get(j).getColor()));
                    bendPoint.setSecondEdge(graphicEdgeList.getLast());
                    bendsList.add(bendPoint);
                }
            }
        }
    }

    private Bend calculateBend(double xStartPoint, double yStartPoint, double xEndPoint, double yEndPoint, double slope){
        // Calcola l'equazione della retta passante per il primo punto con inclinazione data
        double firstLineEquation = yStartPoint - slope * xStartPoint;
        // Calcola l'equazione della retta passante per il secondo punto con inclinazione data
        double secondLineEquation = yEndPoint + slope * xEndPoint;
        // Calcola il punto di intersezione tra le due rette
        int xIntersection = (int)((secondLineEquation - firstLineEquation) / (2 * slope));
        int yIntersection = (int)(slope * xIntersection + firstLineEquation);
        return new Bend(new Point2D.Double(xIntersection, yIntersection));
    }

    //metodo che taglia archi troppo lunghi a metà della loro lunghezza
    private void cutBends(){
        for(int i=0; i<bendsList.size(); i++){
            //metodo che calcola la posizione del taglio e crea i bend di secondo livello
            Bend[] newBends = createSecondLevelBends(bendsList.get(i).getFirstEdge(), bendsList.get(i).getSecondEdge());
            if(newBends != null){
                graphicEdgeList.remove(bendsList.get(i).getFirstEdge());
                graphicEdgeList.remove(bendsList.get(i).getSecondEdge());
                GraphicNode firstFakeNode = new GraphicNode(false, -1, 
                    newBends[0].getCoordinates().getX(), newBends[0].getCoordinates().getY(), false, null);
                GraphicNode secondFakeNode = new GraphicNode(false, -1, 
                    newBends[1].getCoordinates().getX(), newBends[1].getCoordinates().getY(), false, null);
                graphicEdgeList.add(new GraphicEdge(bendsList.get(i).getFirstEdge().getStartNode(), firstFakeNode,
                    bendsList.get(i).getFirstEdge().getPage(), bendsList.get(i).getFirstEdge().getColor()));
                graphicEdgeList.add(new GraphicEdge(firstFakeNode, secondFakeNode,
                    bendsList.get(i).getFirstEdge().getPage(), bendsList.get(i).getFirstEdge().getColor()));
                graphicEdgeList.add(new GraphicEdge(secondFakeNode, bendsList.get(i).getSecondEdge().getEndNode(),
                    bendsList.get(i).getFirstEdge().getPage(), bendsList.get(i).getFirstEdge().getColor()));
            }
        }
    }

    //metodo che calcola la posizione del taglio e creta i bend di secondo livello, stando attento a non incidere sui nodi
    private Bend[] createSecondLevelBends(GraphicEdge firstEdge, GraphicEdge secondEdge){
        Bend[] newBends = new Bend[2];
        if(firstEdge.getPage() == true){
            if(firstEdge.getStartNode().getY() < secondEdge.getEndNode().getY()){
                newBends[0] = new Bend(new Point2D.Double((firstEdge.getEndNode().getX() + firstEdge.getStartNode().getX())/2,
                    (firstEdge.getEndNode().getY() + firstEdge.getStartNode().getY())/2));
                newBends[1] = new Bend(new Point2D.Double((secondEdge.getEndNode().getX() + secondEdge.getStartNode().getX())/2,
                    (firstEdge.getEndNode().getY() + firstEdge.getStartNode().getY())/2));
            } else {
                newBends[0] = new Bend(new Point2D.Double((firstEdge.getEndNode().getX() + firstEdge.getStartNode().getX())/2,
                    (secondEdge.getEndNode().getY() + secondEdge.getStartNode().getY())/2));
                newBends[1] = new Bend(new Point2D.Double((secondEdge.getEndNode().getX() + secondEdge.getStartNode().getX())/2,
                    (secondEdge.getEndNode().getY() + secondEdge.getStartNode().getY())/2));
            }
            for(int i=firstEdge.getStartNode().getName()+1; i<secondEdge.getEndNode().getName(); i++){
                if(newBends[0].getCoordinates().getY() > graphicNodeList.get(i).getY()){
                    return null;
                }
            }
        }else{
            if(firstEdge.getStartNode().getY() < secondEdge.getEndNode().getY()){
                newBends[0] = new Bend(new Point2D.Double((firstEdge.getEndNode().getX() + firstEdge.getStartNode().getX())/2,
                    (firstEdge.getEndNode().getY() + firstEdge.getStartNode().getY())/2));
                newBends[1] = new Bend(new Point2D.Double((secondEdge.getEndNode().getX() + secondEdge.getStartNode().getX())/2,
                    (firstEdge.getEndNode().getY() + firstEdge.getStartNode().getY())/2));
            } else {
                newBends[0] = new Bend(new Point2D.Double((firstEdge.getEndNode().getX() + firstEdge.getStartNode().getX())/2,
                    (secondEdge.getEndNode().getY() + secondEdge.getStartNode().getY())/2));
                newBends[1] = new Bend(new Point2D.Double((secondEdge.getEndNode().getX() + secondEdge.getStartNode().getX())/2,
                    (secondEdge.getEndNode().getY() + secondEdge.getStartNode().getY())/2));
            }
            for(int i=firstEdge.getStartNode().getName()+1; i<secondEdge.getEndNode().getName(); i++){
                if(newBends[0].getCoordinates().getY() < graphicNodeList.get(i).getY()){
                    return null;
                }
            }
        }
        return newBends;
    }

    private void calcolateArea(){
        double maxUp = graphicNodeList.get(0).getY();
        double maxDown = graphicNodeList.get(0).getY();
        double maxRight = graphicNodeList.get(0).getX();
        double maxLeft = graphicNodeList.get(0).getX();
        for(int i=0; i<graphicNodeList.size(); i++){
            if(graphicNodeList.get(i).getY() < maxUp){
                maxUp = graphicNodeList.get(i).getY();
            } else if(graphicNodeList.get(i).getY() > maxDown){
                maxDown = graphicNodeList.get(i).getY();
            }
            if(graphicNodeList.get(i).getX() > maxRight){
                maxRight = graphicNodeList.get(i).getX();
            } else if(graphicNodeList.get(i).getX() < maxLeft){
                maxLeft = graphicNodeList.get(i).getX();
            }
        }
        for(int i=0; i<bendsList.size(); i++){
            if(bendsList.get(i).getCoordinates().getY() < maxUp){
                maxUp = bendsList.get(i).getCoordinates().getY();
            } else if(bendsList.get(i).getCoordinates().getY() > maxDown){
                maxDown = bendsList.get(i).getCoordinates().getY();
            }
            if(bendsList.get(i).getCoordinates().getX() > maxRight){
                maxRight = bendsList.get(i).getCoordinates().getX();
            } else if(bendsList.get(i).getCoordinates().getX() < maxLeft){
                maxLeft = bendsList.get(i).getCoordinates().getX();
            }
        }
        double height = maxDown - maxUp;
        double width = maxRight - maxLeft;
        area = height * width;
    }

    private void calcolateCrossingEdges(){
        for(int i=0; i<graphicEdgeList.size(); i++){
            for(int j=i+1; j<graphicEdgeList.size(); j++){
                Point2D.Double A = new Point2D.Double(graphicEdgeList.get(i).getStartNode().getX(),
                    graphicEdgeList.get(i).getStartNode().getY());
                Point2D.Double B = new Point2D.Double(graphicEdgeList.get(i).getEndNode().getX(),
                    graphicEdgeList.get(i).getEndNode().getY());
                Point2D.Double C = new Point2D.Double(graphicEdgeList.get(j).getStartNode().getX(),
                    graphicEdgeList.get(j).getStartNode().getY());
                Point2D.Double D = new Point2D.Double(graphicEdgeList.get(j).getEndNode().getX(),
                    graphicEdgeList.get(j).getEndNode().getY());
                Point2D intersection = getIntersection(A,B,C,D);
                if(intersection != null){
                    if((intersection.getX() != graphicEdgeList.get(i).getStartNode().getX() && 
                        intersection.getY() != graphicEdgeList.get(i).getStartNode().getY()) && 
                        (intersection.getX() != graphicEdgeList.get(i).getEndNode().getX() && 
                        intersection.getY() != graphicEdgeList.get(i).getEndNode().getY()) &&
                        (intersection.getX() != graphicEdgeList.get(j).getStartNode().getX() && 
                        intersection.getY() != graphicEdgeList.get(j).getStartNode().getY()) &&
                        (intersection.getX() != graphicEdgeList.get(j).getEndNode().getX() && 
                        intersection.getY() != graphicEdgeList.get(j).getEndNode().getY())){
                        numberOfCrossing++;
                    }
                }
            }
        }
    }

    public static Point2D getIntersection(Point2D A, Point2D B, Point2D C, Point2D D) {
        double a1 = B.getY() - A.getY();
        double b1 = A.getX() - B.getX();
        double c1 = a1 * A.getX() + b1 * A.getY();
        double a2 = D.getY() - C.getY();
        double b2 = C.getX() - D.getX();
        double c2 = a2 * C.getX() + b2 * C.getY();
        double delta = a1 * b2 - a2 * b1;
        if (delta == 0) {
            return null;
        }
        double x = (b2 * c1 - b1 * c2) / delta;
        double y = (a1 * c2 - a2 * c1) / delta;
        if (x < Math.min(A.getX(), B.getX()) || x > Math.max(A.getX(), B.getX())) {
            return null;
        }
        if (x < Math.min(C.getX(), D.getX()) || x > Math.max(C.getX(), D.getX())) {
            return null;
        }
        if (y < Math.min(A.getY(), B.getY()) || y > Math.max(A.getY(), B.getY())) {
            return null;
        }
        if (y < Math.min(C.getY(), D.getY()) || y > Math.max(C.getY(), D.getY())) {
            return null;
        }
        return new Point2D.Double(x, y);
    }

    //getter and setter
    protected LinkedList<GraphicNode> getGraphicNodeList(){
        return graphicNodeList;
    }
    protected LinkedList<GraphicEdge> getEdgeList(){
        return graphicEdgeList;
    }
    protected double getArea(){
        return area;
    }
    protected int getNumberOfBends(){
        return bendsList.size();
    }
    protected int getNumberOfEdges(){
        return numberOfEdges;
    }
    protected int getNumberOfCrossing(){
        return numberOfCrossing;
    }

}