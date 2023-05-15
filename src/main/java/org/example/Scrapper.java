package org.example;

import java.util.ArrayList;
//UTILS
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//JSOUP
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scrapper  {
    
    //ATTRIBUTS

    private static String url = "https://prix-carburants-info.fr/";
    private static String terminalColor = "\u001B[34m";
    private static String errorColor = "\u001B[31m";

    public static void start() {
        Database.getInstance().UpdateDatabase();
            System.out.println("[SCRAPPER]: Scraping...");
            HashMap<String, String> links = Scrapper.getLinks();
            for (var entry : links.entrySet()) {
                Scrapper.getStations(entry.getValue()).forEach(
                    (station) -> 
                        Database.getInstance().AddStation(station)
                    );
                System.out.println("[SCRAPPER]:"+ entry.getKey() + " scrapped");
        }
    }

    //METHODS
    public static HashMap<String, String> getLinks(){
        HashMap<String, String> map = new HashMap<>();
        try {
            Document home = Jsoup.connect(url).get();
            Elements table = home.select("table");
            Elements rows = table.select("td");
            for (Element row : rows) {
                if (!row.select("a[href]").isEmpty()) {
                    String cityName = row.select("a[href]").text();
                    String link = row.select("a[href]").attr("href");
                    map.put(cityName, link);
                }
            }
        } catch (Exception ex) {
            System.out.println(errorColor + "[SCRAPPER] Fail to extract links from home page\n[ERROR] " + ex);
        }
        System.out.println(terminalColor + "[SCRAPPER] Home page scrapped, " + map.size() + " links founded");
        return map;
    }

    public static ArrayList<Station> getStations(String link) {
        ArrayList<Station> stations = new ArrayList<>();
        try {
            //STATION ATTRIBUTS
            ArrayList<String> prices = new ArrayList<String>();
            String brand = "";
            String address = "";
            String zipCode = "";
            String city = "";
            Document hmtl = Jsoup.connect(link).get();
            Elements tables = hmtl.select("table");
            tables.remove(0);
            for (Element table : tables) {
                Elements rows = table.select("td");
                for (Element row : rows) {
                    if (row.select("div").hasClass("row row-cols-2 mr-auto")) {
                        Elements informations =  row.children();
                        brand = informations.select("h2").select("a").text();
                        String zipCodeAndCity = informations.select("div.bindpopup").next().select("a").text();
                        address = informations.select("div.bindpopup").next().text().replace(zipCodeAndCity, "");
                        //TODO: Fix this
                        Pattern p = Pattern.compile("\\d{5}");
                        Matcher m = p.matcher(zipCodeAndCity);
                        if (m.find()) {
                            zipCode = m.group(0);
                            city = zipCodeAndCity.replace(zipCode, "");
                        }
                    }
                    if (row.select("td").hasClass("col-2 text-center px-0")) {
                        prices.add(row.text());
                    }
                    if (prices.size() == 6) {
                        Station station = new Station();
                        station.addALL(city, brand, address, zipCode, Carburant.convertArrayToObj(prices));
                        stations.add(station);
                        prices.clear();
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(errorColor + "[SCRAPPER] Fail to extract stations from " + link + "\n[ERROR] " + ex);
        }
        return stations;
    }
}
