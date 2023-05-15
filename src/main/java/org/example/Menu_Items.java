package org.example;

public class Menu_Items {

    //ATTRIBUTES
    String description;
    Runnable action;

    //CONSTRUCTOR
    public Menu_Items(String description,Runnable action) {
        this.description = description;
        this.action = action;
    }

    //METHODS
    public void execute() {
        this.action.run();
    }
}