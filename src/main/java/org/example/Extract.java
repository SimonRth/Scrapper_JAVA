package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Extract {

    public static void ExtractProduits() throws IOException {
        List<Produit> produits = new ArrayList<Produit>();
        Document doc;
        doc = Jsoup.connect("https://gtashop.fr/products/").get();
        Scrap.scrap(doc, produits);
        doc = Jsoup.connect("https://gtashop.fr/products/page/2/").get();
        Scrap.scrap(doc, produits);
        Produit.PrintProduit(produits);
    }
}
