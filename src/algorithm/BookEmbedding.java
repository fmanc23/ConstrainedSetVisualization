package algorithm;

import java.util.Iterator;
import java.util.LinkedList;

public class BookEmbedding{

    /*
        La Classe BookEmbedding rappresenta la struttura BookEmbedding dove:
            -l'ordine dei nodi è rappresentata da una LinkedList di nodi;
            -gli archi sopra e gli archi sotto sono raggruppati da due LinkedList
    */
    private LinkedList<Node> nodesList = new LinkedList<Node>();
    private LinkedList<Edge> pageUp = new LinkedList<Edge>();
    private LinkedList<Edge> pageDown = new LinkedList<Edge>();
    protected BookEmbedding(LinkedList<int[]> coordinatesList, LinkedList<int[]> colorsList) {
        //ordinamento per coordinata x
        int exchange;
        for(int i=0; i<coordinatesList.size(); i++){
            for(int j=i; j<coordinatesList.size(); j++){
                if(coordinatesList.get(i)[0] > coordinatesList.get(j)[0]){
                    exchange = coordinatesList.get(i)[0];
                    coordinatesList.get(i)[0] = coordinatesList.get(j)[0];
                    coordinatesList.get(j)[0] = exchange;
                }
            }
        }
        //inserisco tutti i nodi nel BE
        for(int i=0; i<coordinatesList.size(); i++){
            if(i==0){
                insertNodeInBEPred(new Node(true, i, true, colorsList.get(i)), null);
            } else {
                insertNodeInBEPred(new Node(true, i, true, colorsList.get(i)), nodesList.getLast());
            }
        }
        //inserisco gli archi
        edgeColors();
        /*metodo che ottimizza il BE, eliminando archi doppi, archi che hanno stesso nodo di partenza e arrivo
        e archi che possono tranquillamente andare nel pageDown*/
        optimization();

        for(int i=nodesList.size()-1; i>0; i--){
            if(nodesList.getLast().getIsReal() == false){
                nodesList.removeLast();
            } else {
                break;
            }
        }
    }

    //inserisco gli archi degli altri colori
    private void edgeColors(){
        LinkedList<LinkedList<Node>> colorsNodeList = new LinkedList<LinkedList<Node>>();
        Boolean newColor = true;
        LinkedList<Integer> colorsInsered = new LinkedList<Integer>();
        /*iterator che scandisce i nodi, controlla quali sono i nuovi colori,
        se ce ne sono aggiunge una nuova lista nella LinkedList di LinkedList di Nodi*/
        Iterator<Node> iteratorNodes = ((LinkedList<Node>)nodesList.clone()).iterator();
        //while che controlla il numero di colori
        while(iteratorNodes.hasNext()){
            Node nodeToCheck = iteratorNodes.next();
            //for che scandisce l'array di colori del nodeToCheck
            for(int i=0; i<nodeToCheck.getColors().length; i++){
                //for che scandisce la lista dei colori gia inseriti
                for(int j=0; j<colorsInsered.size(); j++){
                    if(colorsInsered.get(j) == nodeToCheck.getColors()[i]){
                        newColor = false;
                    }
                }
                if(newColor){
                    colorsInsered.add(nodeToCheck.getColors()[i]);
                    colorsNodeList.add(new LinkedList<Node>());
                }
                newColor = true;
            }
        }
        //inserisco nelle linkedList di Node tutti i nodi che hanno stesso colore
        for(int i=0; i< colorsNodeList.size(); i++){
            int color = colorsInsered.get(i);
            for(int j=0; j< nodesList.size(); j++){
                if(nodesList.get(j).containsColor(color)){
                    colorsNodeList.get(i).add(nodesList.get(j));
                }
            }
        }
        //inserisco gli archi scandendo tutte le LinkedList di colori create prima
        for(int i=0; i< colorsNodeList.size(); i++){
            LinkedList<Node> colorList = colorsNodeList.get(i);
            //scandisco la linkedlist i-esima che contiene tutti i nodi dello stesso colore
            for(int j=0; j< colorsNodeList.get(i).size()-1; j++){
                int cases = 0;
                int crossingIndex = 0;
                LinkedList<Node> nodesInside = new LinkedList<Node>();
                //guardo quali sono i possibili nodi che possono dare fastidio e li salvo nella linkedlist nodesInside
                for(int k=nodesList.indexOf(colorsNodeList.get(i).get(j)); k<=nodesList.indexOf(colorsNodeList.get(i).get(j+1)); k++){
                    nodesInside.add(nodesList.get(k));
                }
                if(!nodesInside.isEmpty()){
                    //for che scandisce tutti i nodi che possono dare fastidio
                    for(int k=nodesList.indexOf(colorsNodeList.get(i).get(j))+1; k<nodesList.indexOf(colorsNodeList.get(i).get(j+1)); k++){
                        if(!nodesList.get(k).getLinks().isEmpty()){
                            if(control(nodesList.get(k), true, nodesInside)){
                                cases = 1;
                            } else if(cases == 1 && control(nodesList.get(k), false, nodesInside)){
                                cases = 2;
                                crossingIndex = k;
                            }
                        }
                    }
                }
                if(cases == 0){
                    insertEdgeInBE(new Edge(colorList.get(j), colorList.get(j+1), true, null, colorsInsered.get(i)));
                }else if(cases == 1){
                    insertEdgeInBE(new Edge(colorList.get(j), colorList.get(j+1), false, null, colorsInsered.get(i)));
                }else if(cases == 2){
                    aroundTheNode(crossingIndex, colorsInsered, colorList, i, j);
                }
            }
        }
    }

    private void aroundTheNode(int crossingIndex, LinkedList<Integer> colorsInsered, LinkedList<Node> colorList, int i, int j){
        Node firstNode = new Node(false, -1, false, new int[]{colorsInsered.get(i)});
        insertNodeInBESucc(firstNode, nodesList.get(crossingIndex));
        insertEdgeInBE(new Edge(colorList.get(j), firstNode, false, null, colorsInsered.get(i)));
        insertEdgeInBE(new Edge(firstNode, colorList.get(j+1), true, null, colorsInsered.get(i)));
    }

    //metodo che controlla se ci sono degli archi che possono dare fastidio
    private boolean control(Node node, boolean page, LinkedList<Node> nodesInside){
        for(int i=0; i< node.getLinks().size(); i++){
            if(node.getLinks().get(i).getPage() == page && !(nodesInside.contains(node.getLinks().get(i).getEndNode())
                && nodesInside.contains(node.getLinks().get(i).getStartNode()))){
                return true;
            }
        }
        return false;
    }

    /*metodo che ottimizza il BE, eliminando archi doppi, archi che hanno stesso nodo di partenza e arrivo
    e archi che possono tranquillamente andare nel pageDown*/
    private void optimization(){
        //elimino gli archi che hanno stesso nodo di partenza e arrivo e gli archi doppi nel pageUp
        Iterator<Edge> iteratorPageUp = ((LinkedList<Edge>)pageUp.clone()).iterator();
        while(iteratorPageUp.hasNext()){
            Edge edgeToControl = iteratorPageUp.next();
            if(edgeToControl.getStartNode() == edgeToControl.getEndNode()){
                pageUp.remove(edgeToControl);
            }
            Iterator<Edge> iteratorPageUp2 = ((LinkedList<Edge>)pageUp.clone()).iterator();
            while(iteratorPageUp2.hasNext()){
                Edge edgePossiblyDuplicate = iteratorPageUp2.next();
                if(edgeToControl.getStartNode() == edgePossiblyDuplicate.getStartNode() &&
                    edgeToControl.getEndNode() == edgePossiblyDuplicate.getEndNode() &&
                    edgeToControl.getColor() == edgePossiblyDuplicate.getColor()){
                }
            }
        }
            
        //elimino gli archi che hanno stesso nodo di partenza e arrivo e gli archi doppi nel pageDown
        Iterator<Edge> iteratorPageDown = ((LinkedList<Edge>)pageDown.clone()).iterator();
        while(iteratorPageDown.hasNext()){
            Edge edgeToControl = iteratorPageDown.next();
            if(edgeToControl.getStartNode() == edgeToControl.getEndNode()){
                pageDown.remove(edgeToControl);
            }
            Iterator<Edge> iteratorPageDown2 = ((LinkedList<Edge>)pageDown.clone()).iterator();
            while(iteratorPageDown2.hasNext()){
            Edge edgePossiblyDuplicate = iteratorPageDown2.next();
                if(edgeToControl.getStartNode() == edgePossiblyDuplicate.getStartNode() &&
                    edgeToControl.getEndNode() == edgePossiblyDuplicate.getEndNode() &&
                    edgeToControl != edgePossiblyDuplicate && 
                    edgeToControl.getColor() == edgePossiblyDuplicate.getColor()){
                    pageDown.remove(edgePossiblyDuplicate);
                }
            }
        }
    }

    /*inserisce il nodo dopo predecessorNode, setta se è un nodo reale o fittizio, se non è il primo nodo da aggiungere, 
        aggiorna i nomi dei nodi successivi (cioè le posizioni dei nodi successivi)*/
    protected void insertNodeInBEPred(Node node, Node predecessorNode){
        int index= nodesList.indexOf(predecessorNode)+1;
        nodesList.add(index, node);
        for(int i=index; i< nodesList.size(); i++){
            if(nodesList.get(i).getName() != index){
                nodesList.get(i).setName(index);
                index++;
            }
        }
    }
    protected void insertNodeInBESucc(Node node, Node successorNode){
        int index= nodesList.indexOf(successorNode);
        nodesList.add(index, node);
        for(int i=index; i< nodesList.size(); i++){
            if(nodesList.get(i).getName() != index){
                nodesList.get(i).setName(index);
                index++;
            }
        }
    }

    /*se page è true, l'arco è inserito nella pagina alta, se page è false, l'arco è inserito nella pagina bassa*/
    protected void insertEdgeInBE(Edge edge){
        if(edge.getPage() == true){
            pageUp.add(edge);
        } else {
            pageDown.add(edge);
        }
    }

    public String toString(){
        String string = "";
        for(int i=0; i< nodesList.size(); i++){
            string += "  " + nodesList.get(i).getName();
        }
        string += "\n PageUp: \n";
        for(int i=0; i< pageUp.size(); i++){
            string += "  " + pageUp.get(i).toString();
        }
        string += "\n PageDown: \n";
        for(int i=0; i< pageDown.size(); i++){
            string += "  " + pageDown.get(i).toString();
        }
        return string;
    }

    //getter and setter
    //restituisce tutti i nodi, sia veri che falsi
    protected LinkedList<Node> getNodes(){
        return nodesList;
    }

    protected LinkedList<Edge> getPageUp(){
        return pageUp;
    }
    
    protected LinkedList<Edge> getPageDown(){
        return pageDown;
    }
}
