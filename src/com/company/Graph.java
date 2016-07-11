package com.company;

import com.sun.xml.internal.bind.v2.runtime.IllegalAnnotationException;
import com.sun.xml.internal.fastinfoset.util.StringArray;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.UUID;

public class Graph {
    private boolean isOriented;
    private boolean isWeighted;
    private boolean allowNegativeWeight;

    private HashMap<String, Node> vertexes;

    //Конструкторы
    public Graph() {
        vertexes = new HashMap<>();
    }

    public Graph(String path) {
        vertexes = new HashMap<>();
        graphFromFile(path);
    }

    public Graph(boolean allowNegativeWeight, boolean isOriented, int capacity) {
        this.allowNegativeWeight = allowNegativeWeight;
        this.isOriented = isOriented;
        vertexes = new HashMap<>(capacity);

    }

    //Общие методы
    public void addNewNode(Node node) {
        String uuid = UUID.randomUUID().toString();
        uuid = node.getNodeIndex();
        vertexes.put(uuid, node);
    }

    public void addNewNode(Node node, String index) {
        try {
            if (!vertexes.containsKey(index)) {
                vertexes.put(index, node);
            } else {
                throw new IllegalArgumentException("WARNING! Вершина с таким ключом уже включена в состав графа. Ключ будет изменен.");
            }
        } finally {
            String uuid = UUID.randomUUID().toString();
            node.setNodeIndex(node.getNodeIndex() + " " + uuid);
            vertexes.put(uuid, node);
        }
    }

    public void addNewNode(Node node, String index, LinkedHashSet<Edge> edges) {
        edges = node.getLinkedNodes();
        addNewNode(node, index);
    }


    public void makeLink(String keyFrom, String keyTo, float weight) throws IllegalArgumentException {
        if (allowNegativeWeight == false && weight <= 0) {
            throw new IllegalArgumentException("Ребра с отрицательным весом были запрещены при создании графа.");
        }

        if (vertexes.containsKey(keyFrom) && vertexes.containsKey(keyTo)) {
            Node nodeFrom = vertexes.get(keyFrom);
            nodeFrom.addNewLink(keyTo, weight);
        } else {
            throw new IllegalArgumentException("Ключ одной из вершин не существует.");
        }
    }

    public void showGraphInText() {
        for(Map.Entry<String, Node> entry: vertexes.entrySet()){
            Node node = entry.getValue();
            System.out.print("Вершина "+ node.getNodeIndex()+": ");
            LinkedHashSet<Edge> edges = node.getLinkedNodes();
            for(Edge edge:edges){
                System.out.print(edge.edgeEnd+"("+edge.edgeWeight+")");
                System.out.print(" ");
            }
            System.out.println();

        }
    }

    public void showInfoAboutNode(Node node){

    }


    //Служебные методы
    private void graphFromFile(String pathToFile) {
        if (Objects.equals(pathToFile, "")) {
            throw new IllegalArgumentException("Путь к файлу указан неверно");
        } else {
            BufferedReader fileReader;
            try {
                List<String> lines = Files.readAllLines(Paths.get(pathToFile), StandardCharsets.UTF_8);

                //считывание первых двух строк - ориентированность и можно ли использовать отриц.веса
                if (lines.get(0).toLowerCase().equals("true")) {
                    this.isOriented = true;
                } else if (lines.get(0).toLowerCase().equals("false")) {
                    this.isOriented = false;
                } else {
                    throw new IllegalArgumentException("Ошибка при чтении файла: неверно указан параметр ориентации");
                }

                if (lines.get(1).toLowerCase().equals("true")) {
                    this.allowNegativeWeight=true;
                } else if (lines.get(1).toLowerCase().equals("false")) {
                    this.allowNegativeWeight=false;
                } else {
                    throw new IllegalArgumentException("Ошибка при чтении файла: неверно указан параметр разрешающий использование ребер с отрицательыми весами.");
                }

                for(int i=createVertexesInMap(lines);i<lines.size();i++){                    //заполняем Map вершинами без содержания, после начинаем цикл по оставшимся вершинам
                    String[] elementsOfString = lines.get(i).split("->");
                    if(elementsOfString.length==3){
                        String keyFrom=elementsOfString[0];
                        String keyTo=elementsOfString[1];
                        float weight = Float.valueOf(elementsOfString[2]);
                            Node node = vertexes.get(keyFrom);
                            node.addNewLink(keyTo,weight);          //добавили новую связь


                        if(!this.isOriented){
                            Node nodeRevert = vertexes.get(keyTo);
                            nodeRevert.addNewLink(keyFrom,weight);
                        }

                    }else{
                        throw new IllegalArgumentException("Неверная запись связи в строке "+ i+". Проверьте файл с данными.");
                    }
                }

            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }


    /*
    * @param lines Массив строк полученных из файла
    * @return stringCounter Строка с которой начинается перечисление связей
    *
    * */
    private int createVertexesInMap(List<String> lines){
        int stringCounter=2;
        String temp[];
        boolean isEnd=false;
        do{
            temp=lines.get(stringCounter).split(",");
            char symbol;
            for(String string:temp){                  //каждую загоняем в vertexes - map
                string=string.trim();
                symbol=string.charAt(string.length()-1);
                if(symbol==';'){
                    String cuttedString=string.substring(0,string.length()-1);
                    Node node = new Node(cuttedString);
                    vertexes.put(cuttedString, node);
                    isEnd=true;
                }
                else{
                    Node node = new Node(string);
                    vertexes.put(string, node);
                }

            }
            stringCounter++;
        }while(isEnd!=true);
        return stringCounter;

    }

    public void djkstraAlgo() {

    }

    public void uorshallAlgo() {

    }

    public void depthSearch() {

    }

    public boolean widthSearch(int from, int to) {
        /*
        boolean[] marked = new boolean[this.verticles.size()];

        ArrayDeque<Node> outputQueue = new ArrayDeque<>();
        outputQueue.add(verticles.get(from));
        while (outputQueue.size() != 0) {
            Node node = outputQueue.remove();
            if (node.equals(verticles.get(to))) {
                return true;
            } else {
                for (Edge temp : node.linkedNodes) {
                    outputQueue.addLast(verticles.get(temp.edgeEnd));
                }
            }
        }*/
        return false;


    }

    public void primAlgo() {

    }

}


class Node {
    private String nodeIndex;
    private LinkedHashSet<Edge> linkedNodes;

    public Node() {
        linkedNodes = new LinkedHashSet<>();
    }

    public Node(String nodeIndex){
        this.nodeIndex=nodeIndex;
        linkedNodes = new LinkedHashSet<>();
    }

    public String getNodeIndex() {
        return nodeIndex;
    }

    public void setNodeIndex(String nodeIndex) {
        this.nodeIndex = nodeIndex;
    }

    public LinkedHashSet<Edge> getLinkedNodes() {
        return linkedNodes;
    }

    public void addNewLink(String keyOfNodeTo, float weightOfEdge) {
        Edge edge = new Edge(keyOfNodeTo, weightOfEdge);
        linkedNodes.add(edge);
    }

}

class Edge {
    public String edgeEnd;
    public float edgeWeight;

    public Edge(String to, float weightOfEdge) {
        edgeEnd = to;
        edgeWeight = weightOfEdge;
    }
}
