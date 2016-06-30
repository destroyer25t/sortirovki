package com.company;

import java.util.Random;
import java.util.Vector;

/*
* Программа для тестировния скорости сортировок
* */
public class Main {

    public static void main(String[] args) {
        Random randomInts = new Random();       //Объявляем объект класса Рандом
        Integer[] numbers;
        Integer[] sortedMass;
        numbers = new Integer[10000];

        for (int k=0;k< numbers.length; k++) {
            numbers[k] = randomInts.nextInt(10);     //заполнили массив рандомными числами
            //System.out.print(numbers[k] + " ");       //и сразу вывели его на экран
        }

        Integer[] numbersClone = numbers.clone();

        System.out.print("\n");


        Sorts engine= new Sorts();

        long start = System.currentTimeMillis();
        sortedMass = engine.bubbleSort(numbersClone);
        long endBubble = System.currentTimeMillis();

        //sortedMass =  engine.insertSort(numbers);

        long start1 = System.currentTimeMillis();
        sortedMass =  engine.shakerSort(numbers);
        long endShaker = System.currentTimeMillis();

        System.out.print("Время работы пузырька: " + (endBubble-start)*1000+"\n");
        System.out.print("Время работы шейкерной: " + (endShaker-start1)*1000+"\n");

        for (int t : sortedMass) {
            //System.out.print(t + " ");
        }

    }
}
