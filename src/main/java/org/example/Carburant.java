package org.example;

import java.util.ArrayList;

public class Carburant {
    String name;
    String price;
    static String[] template = {"Gazole", "sp95", "e10", "sp98", "e85", "gplc"};

    public Carburant(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public static ArrayList<Carburant> convertArrayToObj(ArrayList<String> array) {
        ArrayList<Carburant> carburants = new ArrayList<Carburant>();
        for (int i = 0; i < array.size(); i++) {
            carburants.add( new Carburant(template[i], array.get(i)));
        }
        return carburants;
    }

}