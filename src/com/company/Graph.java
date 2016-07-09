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

                String[] vertexesString = lines.get(2).split(",");  //получаем третью строку со списком всех вершин

                for(String string:vertexesString){                  //каждую загоняем в vertexes - map
                    Node node = new Node(string);
                    vertexes.put(string, node);
                }

                for(int i=3;i<lines.size();i++){                    //оставшиеся строки, которые говорят о связях между вершинами обрабатываем
                    String[] elementsOfString = lines.get(i).split("->");
                    if(elementsOfString.length==3){

                        String keyFrom=elementsOfString[0];
                        String keyTo=elementsOfString[1];
                        float weight = Float.valueOf(elementsOfString[2]);
                        Node node = vertexes.get(keyFrom);
                        node.addNewLink(keyTo,weight);          //добавили новую связь
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
        }
        return false;

*/
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
