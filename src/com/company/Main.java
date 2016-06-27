package com.company;

import java.util.Random;
import java.util.Vector;

class Sorts{

    /*
    * Сортировка пузырьком, с остановкой при отсутствии перестановок
    *
    * */

    public static <T extends Comparable<T>> T[] bubbleSort(T[] unsortedMass){
        int length = unsortedMass.length;
        boolean sorted=false;
        if (unsortedMass.length != 0) {
            for(int p=0;p<length-1;p++){
                sorted=true;
                for (int i = 0; i < length-1; i++) {
                    if (unsortedMass[i].compareTo(unsortedMass[i + 1]) > 0) {
                        sorted=false;
                        T temp = unsortedMass[i];
                        unsortedMass[i] = unsortedMass[i + 1];
                        unsortedMass[i + 1] = temp;
                    }
                }
                if(sorted) break;
            }
        }
        return unsortedMass;
    }


    /*
    Сортировка вставками
     */
    public static <T extends Comparable<T>> T[] insertSort(T[] unsortedMass){
        T[] clearMass = (T[])(new Object[unsortedMass.length]);
        for(int i=0;i<unsortedMass.length;i++){
            int k=0; //счетчик в массиве итоговом
            do{
                if(unsortedMass[i].compareTo(clearMass[k])>0){
                    clearMass[k+1]=clearMass[k];
                    clearMass[k]=unsortedMass[i];

                }
                k++;
            }
            while(k<clearMass.length);
        }

        return clearMass;
    }
}
/*
* Программа для тестировния скорости сортировок
* */
public class Main {

    public static void main(String[] args) {
        Random randomInts = new Random();       //Объявляем объект класса Рандом
        Integer[] numbers;
        Integer[] sortedMass;
        numbers = new Integer[100];

        for (int k=0;k< numbers.length; k++) {
            numbers[k] = randomInts.nextInt(100);     //заполнили массив рандомными числами
            System.out.print(numbers[k] + " ");       //и сразу вывели его на экран
        }

        System.out.print("\n");


        Sorts engine= new Sorts();

        //sortedMass = engine.bubbleSort(numbers);
        sortedMass =  engine.insertSort(numbers);

        for (int t : sortedMass) {
            System.out.print(t + " ");
        }

    }
}
