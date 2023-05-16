package org.example;

import java.util.ArrayList;

public class Station {
    public String city;
    public String brand;
    public String address;
    public String zipcode;
    public ArrayList<Carburant> carburants = new ArrayList<Carburant>();

    public Station(String city, String brand, String address, String zipcode, ArrayList<Carburant> carburants) {
        this.city = city;
        this.brand = brand;
        this.address = address;
        this.zipcode = zipcode;
        this.carburants = carburants;
    }

    @Override
    public String toString() {
        return "Address: " + address + "\nBrand: " + brand + "\nCity: " + city + "\nZipcode: " + zipcode + "\nCarburants: " + carburants + "\n";
    }
}