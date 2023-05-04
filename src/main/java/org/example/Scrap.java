package org.example;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class Scrap {
    public static void scrap(Document doc, List<Produit> produits){


        // get title of the page
        String title = doc.title();
        System.out.println("Title: " + title);

        // get all links
        Elements links = doc.select("a[href]");
        for (Element link : links) {
            if(link.attr("href").contains("https://gtashop.fr/produit/") && link.attr("href").length() > 30){
                if(link.text().length() > 0){
                    produits.add(new Produit(link.text(),doc.select("span[class=woocommerce-Price-amount amount]").text(),
                            doc.select("span[class=posted_in]").text(),
                            doc.select("span[class=ui-provider ws b c d e f g h i j k l m n o p q r s t u v w x y z ab ac ae af ag ah ai aj ak]").text()));


//                    produits.get(produits.size()-1).setPrice(doc.select("span[class=woocommerce-Price-amount amount]").text());
//                    produits.get(produits.size()-1).setCategorie(doc.select("span[class=posted_in]").text());
//                    produits.get(produits.size()-1).setDescription(doc.select("div[class=woocommerce-Tabs-panel woocommerce-Tabs-panel--description panel entry-content wc-tab]").text());

                    //System.out.println("\nLink : " + link.attr("href"));
                    //System.out.println("Text : " + link.text());
                }

            }
        }

    }
}
