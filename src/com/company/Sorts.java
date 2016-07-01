package com.company;

/**
 * Created by Олег on 30.06.2016.
 */
public class Sorts {
    /*
    * Сортировка пузырьком, с остановкой при отсутствии перестановок. Тест для Github
    * Здесь и далее используется T extends Comparable<T>. Comparable - для возможности использовать CompareTo
    * */
    public static <T extends Comparable<T>> T[] bubbleSort(T[] unsortedMass) {
        int length = unsortedMass.length;
        boolean sorted = false;

       // int count=0;
        if (unsortedMass.length != 0) {
            for (int p = 0; p < length - 1; p++) {
                sorted = true;
                for (int i = 0; i < length - 1; i++) {
                    if (unsortedMass[i].compareTo(unsortedMass[i + 1]) > 0) {
                     //   count++;
                        sorted = false;
                        T temp = unsortedMass[i];
                        unsortedMass[i] = unsortedMass[i + 1];
                        unsortedMass[i + 1] = temp;
                    }
                }
                if (sorted) break;
            }
        }
      //  System.out.print("BubbleSort: "+count+"\n");
        return unsortedMass;
    }


    /*
    Сортировка вставками
     */
    public static <T extends Comparable<T>> T[] insertSort(T[] unsortedMass) {
        for (int i = 1; i < unsortedMass.length; i++) {
            for(int j=i;j>0&&unsortedMass[j-1].compareTo(unsortedMass[j])>0;j--){
                T temp=unsortedMass[j-1];
                unsortedMass[j-1]=unsortedMass[j];
                unsortedMass[j]=temp;
            }
        }
        return unsortedMass;
    }

    /*
    *
    *  Шейкерная сортировка
    *
    * */
    public static <T extends Comparable<T>> T[] shakerSort(T[] unsortedMass) {

        int start=0;        //новое начало массива
        int finish=unsortedMass.length-1;         //новый конец массива
       // int count=0;

        while(start<=finish){
            for(int i=start;i<finish;i++){
                if(unsortedMass[i].compareTo(unsortedMass[i+1])>0){
            //        count++;
                    T temp = unsortedMass[i + 1];
                    unsortedMass[i + 1] = unsortedMass[i];
                    unsortedMass[i] = temp;
                }
            }
            finish--;

            for(int i=finish;i>start;i--){
                if(unsortedMass[i].compareTo(unsortedMass[i-1])<0){
                  //  count++;
                    T temp = unsortedMass[i - 1];
                    unsortedMass[i - 1] = unsortedMass[i];
                    unsortedMass[i] = temp;
                }
            }
            start++;
        }
       // System.out.print("ShakerSort: "+count+"\n");

        return unsortedMass;
    }

    /*
    *
    *  Сортировка Шелла
    *
    * */
    public static <T extends Comparable<T>> T[] shellSort(T[] unsortedMass) {
        return unsortedMass;
    }

    /*
    *
    *  Сортировка выбором
    *
    * */
    public static <T extends Comparable<T>> T[] choiseSort(T[] unsortedMass) {
        int length=unsortedMass.length;
        for(int i=0;i<length;i++){
            int minIndex=i;
            for(int j=i;j<length;j++){
                if(unsortedMass[minIndex].compareTo(unsortedMass[j])>0){
                    minIndex=j;
                }
            }
            if(minIndex!=i){
                T temp=unsortedMass[i];
                unsortedMass[i]=unsortedMass[minIndex];
                unsortedMass[minIndex]=temp;
            }

        }
        return unsortedMass;
    }

    /*
    *
    *  Сортировка слиянием
    *
    * */
    public static <T extends Comparable<T>> T[] splitSort(T[] unsortedMass) {
        int indexFirstElem=0;
        int indexLastElem=unsortedMass.length-1;
        int half=unsortedMass.length/2;

        return splitSort(unsortedMass,indexFirstElem,indexLastElem);
    }

    public static <T extends Comparable<T>> T[] splitSort(T[] unsortedMass, int indexFirstElem, int indexLastElem) {
        if(unsortedMass.length!=2){
            int half=unsortedMass.length/2;
            splitSort(unsortedMass, indexFirstElem, half-1);
            splitSort(unsortedMass, half, indexLastElem);
        }
        else{
            T temp=unsortedMass[indexFirstElem];
            unsortedMass[indexFirstElem]=unsortedMass[indexLastElem];
            unsortedMass[indexLastElem]=temp;
        }
        return unsortedMass;
    }

    /*
    *
    *  Сортировка быстрая
    *
    * */
    public static <T extends Comparable<T>> T[] quickSort(T[] unsortedMass) {
        return unsortedMass;
    }
}

