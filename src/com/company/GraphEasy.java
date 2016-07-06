package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

/**
 * Created by Олег on 06.07.2016.
 */
public class GraphEasy {
    private boolean isOriented;
    private int countVerticles;
    private int[][] verticlesLinks;
    private int[][] edgesWeights;

    public GraphEasy(boolean isOriented, int count){
        this.isOriented=isOriented;
        this.countVerticles=count;
    }

    public GraphEasy(String pathToFile){

        if(Objects.equals(pathToFile, "")){
            throw new IllegalArgumentException("Путь к файлу указан неверно");
        }
        else{
            BufferedReader fileReader;

            try {
                fileReader = new BufferedReader(new FileReader(pathToFile));
                String tempString = fileReader.readLine();
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

    public boolean isOriented() {
        return isOriented;
    }

    public int getCountVerticles() {
        return countVerticles;
    }
}
