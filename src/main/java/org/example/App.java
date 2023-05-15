package org.example;

import java.util.HashMap;

public class App
{
    public static void main( String[] args ){
        Database db = Database.getInstance();
        HashMap<String, String> links = Scrapper.getLinks();
        for (var entry : links.entrySet()) {
            // Scrapper.getStations(entry.getValue())
            Scrapper.getStations(entry.getValue()).forEach(
                (station) -> 
                    db.AddStation(station)
                );
            System.out.println("[SCRAPPER]:"+ entry.getKey() + " scrapped");
        }
        db.Close();
    }
}
