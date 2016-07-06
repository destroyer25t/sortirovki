package com.company;

import java.util.*;

/**
 * Created by dogan on 06.07.2016.
 */
public class Graph {
    private boolean isOriented;
    Vector<Node> graphVector;
    Vector<Vector<Node>> lengthEdgesVector = new Vector<>();

    //Конструкторы
    public Graph(boolean isOriented){
        this.isOriented=isOriented;
        graphVector = new Vector<>();
    }

    public Graph(boolean isOriented, int capacity){
        this.isOriented=isOriented;
        graphVector = new Vector<>(capacity);
    }

    public void addNewNode(Node node){
        graphVector.add(node);
    }

    public void addNewNode(LinkedHashSet<Node> inputSet){
        Iterator<Node> iterator = inputSet.iterator();
        while(iterator.hasNext()){
            addNewNode(iterator.next());
        }
    }

    public void showGraphInText(){
        for(int i=0; i<graphVector.size();i++){
            System.out.print();
        }
    }

    public void djkstraAlgo(){

    }

    public void uorshallAlgo(){

    }

    public void depthSearch(){

    }

    public void widthSearch(){

    }

    public void primAlgo(){

    }

}


 class Node{
    int weight;
    LinkedHashSet<Integer> linkedNodes;

    public Node(){
        weight=0;
        linkedNodes=new LinkedHashSet<>();
    }

    public Node(int inputWeight){
        weight=inputWeight;
        linkedNodes=new LinkedHashSet<>();
    }

    public void addNewLink(int numberOfNode){
        linkedNodes.add(numberOfNode);
    }
}
