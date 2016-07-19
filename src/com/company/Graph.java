package com.company;

import com.sun.xml.internal.bind.v2.runtime.IllegalAnnotationException;
import com.sun.xml.internal.fastinfoset.sax.SystemIdResolver;
import com.sun.xml.internal.fastinfoset.util.StringArray;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.UUID;

import static java.lang.Float.POSITIVE_INFINITY;

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
    /*
    * Добавляет новую вершину в граф
    * @param node Готовая вершина
    * */
    public void addNewNode(Node node) {
        String uuid = UUID.randomUUID().toString();
        uuid = node.getNodeIndex();
        vertexes.put(uuid, node);
    }

    /*
    * Добавляет новую вершину в граф с указанным ключом
    * @param node Готовая вершина
    * @param index Ключ вершины
    * */
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

    /*
    * Добавляет новую вершину в граф вместе со связями и ключом
    * @param node Готовая вершина
    * @param index Ключ вершины
    * @param edges Набор связей между данной вершиной и другими
    * */
    public void addNewNode(Node node, String index, LinkedHashSet<Edge> edges) {
        edges = node.getLinkedNodes();
        addNewNode(node, index);
    }


    /*
    * Создает связь между двумя существующими вершинами с указанным весом
    * @param keyFrom Вершина из которой строится связь
    * @param keyTo Вершина в которую строится связь
    * @param weight Вес связи
    * */
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


    /*
    * Выводит в консоль вершины и связи между ними
    * */
    public void showGraphInText() {
        for (Map.Entry<String, Node> entry : vertexes.entrySet()) {
            showInfoAboutNode(entry.getValue());
        }
    }

    public void showInfoAboutNode(Node node) {
        System.out.print("Вершина " + node.getNodeIndex() + ": ");
        LinkedHashSet<Edge> edges = node.getLinkedNodes();
        for (Edge edge : edges) {
            System.out.print(edge.edgeEnd + "(" + edge.edgeWeight + ")");
            System.out.print(" ");
        }
        System.out.println();
    }

    public void showInfoAboutNodeWithWeight(Node node) {
        LinkedList<String> fullPathKeys = new LinkedList<>();

        if (node.keyOfPreviousNode != null) {
            showInfoAboutNodeWithWeight(vertexes.get(node.keyOfPreviousNode), fullPathKeys);
        }
        fullPathKeys.addLast(node.getNodeIndex());
        Iterator<String> iter = fullPathKeys.iterator();

        while (iter.hasNext()) {
            System.out.print("->" + iter.next());
        }

        System.out.print(" Длина пути: " + node.currentWeight);
        System.out.println();
    }

    private LinkedList<String> showInfoAboutNodeWithWeight(Node node, LinkedList<String> list) {
        if (node.keyOfPreviousNode != null) {
            list.add(node.getNodeIndex());
            showInfoAboutNodeWithWeight(vertexes.get(node.keyOfPreviousNode), list);
        }
        return list;
    }


    //Служебные методы
    /*
    * Заполняет vertexes в классе из файла
    * @param pathToFile Массив строк полученных из файла
    *
    * */
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
                    this.allowNegativeWeight = true;
                } else if (lines.get(1).toLowerCase().equals("false")) {
                    this.allowNegativeWeight = false;
                } else {
                    throw new IllegalArgumentException("Ошибка при чтении файла: неверно указан параметр разрешающий использование ребер с отрицательыми весами.");
                }

                for (int i = createVertexesInMap(lines); i < lines.size(); i++) {                    //заполняем Map вершинами без содержания, после начинаем цикл по оставшимся вершинам
                    String[] elementsOfString = lines.get(i).split("->");
                    if (elementsOfString.length == 3) {
                        String keyFrom = elementsOfString[0];
                        String keyTo = elementsOfString[1];
                        float weight = Float.valueOf(elementsOfString[2]);

                        Node node = vertexes.get(keyFrom);
                        if (node.equals(null) || !vertexes.containsKey(keyTo)) {
                            throw new IllegalArgumentException("В строке " + (i + 1) + " указан неверный ключ. Проверьте файл с данными.");
                        }

                        node.addNewLink(keyTo, weight);          //добавили новую связь


                        if (!this.isOriented) {
                            Node nodeRevert = vertexes.get(keyTo);
                            nodeRevert.addNewLink(keyFrom, weight);
                        }

                    } else {
                        throw new IllegalArgumentException("Неверная запись связи в строке " + i + ". Проверьте файл с данными.");
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
    * Заполняем vertexes вершинами
    * @param lines Массив строк полученных из файла
    * @return stringCounter Строка с которой начинается перечисление связей
    *
    * */
    private int createVertexesInMap(List<String> lines) {
        int stringCounter = 2;
        String temp[];
        boolean isEnd = false;
        do {
            temp = lines.get(stringCounter).split(",");
            char symbol;
            for (String string : temp) {                  //каждую загоняем в vertexes - map
                string = string.trim();
                symbol = string.charAt(string.length() - 1);
                if (symbol == ';') {
                    String cuttedString = string.substring(0, string.length() - 1);
                    Node node = new Node(cuttedString);
                    vertexes.put(cuttedString, node);
                    isEnd = true;
                } else {
                    Node node = new Node(string);
                    vertexes.put(string, node);
                }

            }
            stringCounter++;
        } while (isEnd != true);
        return stringCounter;

    }


    public void uorshallAlgo() {

    }

    public void depthSearch() {

    }

    /*
    * Проверка существования пути и его построение
    * @param from Вершина из которой ищется путь
    * @param to Вершина в которую ищется путь
    * */
    public boolean widthSearch(String from, String to) {

        HashSet<String> marked = new HashSet<>(vertexes.size());

        ArrayDeque<Node> outputQueue = new ArrayDeque<>();
        outputQueue.add(vertexes.get(from));
        while (outputQueue.size() != 0) {
            Node node = outputQueue.remove();
            marked.add(node.getNodeIndex());
            if (node.equals(vertexes.get(to))) {
                return true;
            } else {
                for (Edge temp : node.getLinkedNodes()) {
                    Node nodeTemp = vertexes.get(temp.edgeEnd);
                    if (!marked.contains(nodeTemp.getNodeIndex())) {
                        outputQueue.addLast(nodeTemp);
                    }

                }
            }
        }
        return false;


    }

    public void primAlgo(String keyFrom, int queueMethod) {
        if(this.isOriented){
            throw new IllegalArgumentException("Данный алгоритм работает только с неориентированными графамии");
        }
        NodeDjkstraComparator comparator = new NodeDjkstraComparator();
        Node node=vertexes.get(keyFrom);
        node.currentWeight=0;

        if(queueMethod==0){
            ArrayList<Node> priorityList= new ArrayList<>(vertexes.values());
            while(!priorityList.isEmpty()){
                node=Collections.min(priorityList,comparator);
                priorityList.remove(node);
                for(Edge edge:node.getLinkedNodes()){
                    Node nodeTemp=vertexes.get(edge.edgeEnd);
                    if(edge.edgeWeight<nodeTemp.currentWeight){
                        nodeTemp.currentWeight=edge.edgeWeight;
                    }
                }
            }

        }
        else if (queueMethod==1){
            PriorityQueue<Node> priorityQueue = new PriorityQueue<>(vertexes.size(),comparator);     //очередь с приоритетами, должна быть быстрее аррайлиста
            for(Node vertexesNode:vertexes.values()){
                priorityQueue.add(vertexesNode);
            }
            while(!priorityQueue.isEmpty()){
                node=priorityQueue.poll();
                for(Edge edge:node.getLinkedNodes()){
                    Node nodeTemp=vertexes.get(edge.edgeEnd);
                    if(edge.edgeWeight<nodeTemp.currentWeight){
                        nodeTemp.currentWeight=edge.edgeWeight;
                    }
                }
            }

        }
        else{
            throw new IllegalArgumentException("Второй аргумент должен быть 0 для использования ArrayList и 1 для использования PriorityQueue");
        }
        for(Node node1:vertexes.values()){
            System.out.print(node1.getNodeIndex()+" "+node1.currentWeight+"\n");

        }
    }


    /**
     * @param keyFrom Ключ вершины из которой при помощи алгоритма Дейкстры будут построены кратчайшие расстояния до остальных вершин
     */
    public void djkstraAlgo(String keyFrom) {
        if (this.allowNegativeWeight) {
            throw new IllegalArgumentException("Данный алгоритм работает с положительными связями.");
        }
        Node startNode = vertexes.get(keyFrom);
        startNode.currentWeight = 0;              //для стартовой вершины, в соответствии с алгоритмом задаем вес 0. У остальных она равна бесконечности.

        //для возможности извлечения минимальных элементов, создаем ArrayList из значений vertexes. Здесь лежат только необработанные вершины
        ArrayList<Node> mainDjkstraList = new ArrayList<>(vertexes.values());

        // После заполнения сортируем список.
        NodeDjkstraComparator comp = new NodeDjkstraComparator();


        while (mainDjkstraList.size() != 0) {
            Node node = Collections.min(mainDjkstraList, comp);
            mainDjkstraList.remove(node);   //удаляем обработанную вершину

            for (Edge edge : node.getLinkedNodes()) {
                Node nodeEnd = vertexes.get(edge.edgeEnd);
                float minimalPath = node.currentWeight + edge.edgeWeight;
                //для вершины находим такую вершину, из которой путь до нашей минимален
                if (nodeEnd.currentWeight >= minimalPath) {
                    nodeEnd.currentWeight = minimalPath;
                    nodeEnd.keyOfPreviousNode = node.getNodeIndex();
                }
            }
        }

        System.out.println("Минимальные пути из вершины " + startNode.getNodeIndex() + ":");
        for (Map.Entry<String, Node> entry : vertexes.entrySet()) {
            System.out.print(startNode.getNodeIndex());
            showInfoAboutNodeWithWeight(entry.getValue());
        }

    }

}


class Node {
    private String nodeIndex;
    private LinkedHashSet<Edge> linkedNodes;

    //для алгоритма дейкстры
    boolean isUsed = false;
    float currentWeight = POSITIVE_INFINITY;
    String keyOfPreviousNode;

    public Node() {
        linkedNodes = new LinkedHashSet<>();
    }

    public Node(Node obj) {
        nodeIndex = obj.getNodeIndex();
        linkedNodes = obj.getLinkedNodes();
    }

    public Node(String nodeIndex) {
        this.nodeIndex = nodeIndex;
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

    public void resetIsUsedAndCurrentWeight() {
        isUsed = false;
        currentWeight = POSITIVE_INFINITY;
    }

}


class NodeDjkstraComparator implements Comparator<Node> {
    @Override
    public int compare(Node o1, Node o2) {
        int result;
        result = Float.compare(o1.currentWeight, o2.currentWeight);            //у нас есть POSITIVE_INFINITY, поэтому воспользуемся правильным методов сравнения
        return result;
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
