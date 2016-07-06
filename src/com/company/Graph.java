package com.company;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Created by dogan on 06.07.2016.
 */
public class Graph {
    int[][] adjacenceMatrix;

}

public class Node{
    int
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
