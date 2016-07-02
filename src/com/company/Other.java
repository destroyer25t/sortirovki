package com.company;

/**
 * Created by Олег on 02.07.2016.
 */
public class Other {
    public static <T extends Comparable<T>> int binarySearch(T[] inputMass, T value, int start, int finish){
        int position=0;
        if(start==finish){
            if(inputMass[finish].compareTo(value)==0){
                position=finish;
            }
            else{
                throw new IllegalArgumentException("Данное значение отсутствует в массиве");
            }

        }
        else{
            int halfIndex=(finish+start)/2;

            if (inputMass[halfIndex].compareTo(value)==0) {
                position = halfIndex;
            }
            else if(inputMass[halfIndex].compareTo(value)>0){
                position = binarySearch(inputMass,value,start,halfIndex-1);
            }
            else{
                position = binarySearch(inputMass,value,halfIndex+1,finish);
            }
        }
        return position;
    }

    public static <T extends Comparable<T>> int binarySearch(T[] inputMass, T value){
            return binarySearch(inputMass, value, 0, inputMass.length - 1);
    }
}
