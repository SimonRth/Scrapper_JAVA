package org.example;

import java.util.HashMap;

import javax.xml.crypto.Data;

public class App
{
    public static void main( String[] args ){
        Menu menu = new Menu("[Scrapper Menu]");
        menu.addItems(new Menu_Items("Update database", () -> {
            Scrapper.start();
        }));
        menu.run();
    }
}
