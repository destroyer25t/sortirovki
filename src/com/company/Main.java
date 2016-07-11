package com.company;

import java.util.Random;
import java.util.Vector;

/*
* Программа для тестировния скорости сортировок
* */
public class Main {

    /*
     * Тестирование графов
     */
    private static void graphTest(){
        try {
            Graph graph = new Graph("C:\\Users\\Олег\\IdeaProjects\\sortirovki\\src\\com\\company\\graph.txt");
            graph.showGraphInText();
            /*
            if(graph.widthSearch(0, 3)) {
                System.out.println("Путь есть");
            }
            else {
                System.out.println("Пути нет");
            }
            */
        }
        catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

    }

    /*
    * Тестирование методов сортировки
    */
    private static void sortsTest(){
        Random randomInts = new Random();       //Объявляем объект класса Рандом

        Integer[] numbers;
        Integer[] sortedMass;

        numbers = new Integer[10];

        for (int k=0;k< numbers.length; k++) {
            numbers[k] = randomInts.nextInt(1000);     //заполнили массив рандомными числами
            System.out.print(numbers[k] + " ");       //и сразу вывели его на экран
        }

        Integer[] numbersClone = numbers.clone();


        Sorts engine= new Sorts();

        long start = System.currentTimeMillis();
        //  sortedMass = engine.bubbleSort(numbersClone);
        long endBubble = System.currentTimeMillis();

        //sortedMass =  engine.insertSort(numbers);
        //sortedMass =  engine.shakerSort(numbers);

        long start1 = System.currentTimeMillis();
        sortedMass=engine.splitSort(numbers);
        // sortedMass =  engine.choiseSort(numbers);
        long endShaker = System.currentTimeMillis();

        //  System.out.print("Время работы пузырька: " + (endBubble-start)*1000+"\n");
        // System.out.print("Время работы выбором: " + (endShaker-start1)*1000+"\n");

        System.out.print("\n");
        for (int t : sortedMass) {
            System.out.print(t + " ");
        }
    }

    /*
    Тестирование прочих функций
     */
    public static void otherTest(){
        Integer[] knownMass = {2,5,6,6,7};
        Other engine = new Other();
        try{
            int position = engine.binarySearch(knownMass, 7);
            System.out.println(position);
        }
        catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }



    }

    public static void main(String[] args) {
        //sortsTest();
        //otherTest();
        graphTest();
    }
}
