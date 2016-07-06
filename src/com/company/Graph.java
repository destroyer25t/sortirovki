package com.company;

import com.sun.xml.internal.fastinfoset.util.StringArray;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by dogan on 06.07.2016.
 */
public class Graph {
    private boolean isOriented;
    Vector<Node> verticles;

    public Graph(){
        verticles = new Vector<>();
    }
    //Конструкторы
    public Graph(boolean isOriented){
        this.isOriented=isOriented;
        verticles = new Vector<>();
    }

    public Graph(boolean isOriented, int capacity){
        this.isOriented=isOriented;
        verticles = new Vector<>(capacity);
    }

    public void addNewNode(Node node){
        verticles.add(node);
    }

    public void addNewNode(LinkedHashSet<Node> inputSet){
        Iterator<Node> iterator = inputSet.iterator();
        while(iterator.hasNext()){
            addNewNode(iterator.next());
        }
    }

    public void showGraphInText(){
        for(int i=0; i<verticles.size();i++){
            System.out.print("Вершина "+i+": ");
            for(Node node:verticles){
                Iterator<Edge> iterator = node.linkedNodes.iterator();
                while(iterator.hasNext()){
                    Edge temp= iterator.next();
                    System.out.print(temp.edgeEnd);
                }

            }
        }
    }

    public void graphFromFile(String pathToFile){
        if(Objects.equals(pathToFile, "")){
            throw new IllegalArgumentException("Путь к файлу указан неверно");
        }
        else{
            BufferedReader fileReader;
            try {
                List<String> lines = Files.readAllLines(Paths.get(pathToFile) ,StandardCharsets.UTF_8);
                this.verticles.setSize(Integer.parseInt(lines.get(0))); //устанавливаем количество вершин (из файла)
                fillVerticles();

                //устанавливаем ориентированность графа
                if(lines.get(1).toLowerCase().equals("true")){
                    this.isOriented=true;
                }
                else if(lines.get(1).toLowerCase().equals("false")){
                    this.isOriented=false;
                }
                else{
                    throw new IllegalArgumentException("Ошибка при чтении файла: неверно указан параметр ориентации");
                }

                //заполняем сведения о вершинах
                for(int i=2;i<lines.size();i++){
                    String infoLine = lines.get(i);
                    String[] stringArray = infoLine.split(" ");

                    int verticleIndex=Integer.parseInt(stringArray[0]);
                    int verticleNewLinkEnd = Integer.parseInt(stringArray[1]);
                    int verticleNewLinkWeight = Integer.parseInt(stringArray[2]);

                    if(stringArray.length==3){
                        verticles.elementAt(verticleIndex).addNewLink(verticleNewLinkEnd,verticleNewLinkWeight);
                    }
                    else{
                        throw new IllegalArgumentException("Вершина записана в неверном формате");
                    }
                }


            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }



        }
    }

    //Заполняем весь наш вектор пустыми вершинами
    private void fillVerticles(){
        for(int i=0;i<verticles.size();i++){
            Node node=new Node();
            verticles.add(node);
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
    LinkedHashSet<Edge> linkedNodes;

    public Node(){
        linkedNodes=new LinkedHashSet<>();
    }

    public void addNewLink(int numberOfNode, int weightOfEdge){
        Edge edge = new Edge(numberOfNode,weightOfEdge);
        linkedNodes.add(edge);
    }
}

class Edge{
    public int edgeEnd;
    public int edgeWeight;

    public Edge(int numberOfNode, int weightOfEdge){
        edgeEnd=numberOfNode;
        edgeWeight=weightOfEdge;
    }
}
