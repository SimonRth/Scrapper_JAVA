package org.example;

import java.util.List;

public class Produit {
    static String name;
    static String price;
    static String categorie;
    static String description;

    public Produit(String name, String price, String categorie, String description) {
        Produit.name = name;
        Produit.price = price;
        Produit.categorie = categorie;
        Produit.description = description;
    }


//    public void setPrice(String price){
//        Produit.price = price;
//    }
//    public void setCategorie(String description){
//        Produit.categorie = description;
//    }
//    public void setDescription(String description){
//        Produit.description = description;
//    }
    public static void PrintProduit(List<Produit> produits){
        for( int i =0 ; i < produits.size(); i++){
            System.out.println(produits.get(i).name);
            System.out.println(produits.get(i).price);
            System.out.println(produits.get(i).categorie);
            System.out.println(produits.get(i).description);
            System.out.println("--------------------");
        }
    }

}
