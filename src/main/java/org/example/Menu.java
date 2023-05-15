package org.example;

import java.util.*;

public class Menu {
    
    //ATTRIBUTES
    List<Menu_Items> items = new ArrayList<Menu_Items>();
    Boolean stop = false;
    String menuName;
    static String terminalColor = "\033[0m";
    static String errorColor = "\033[0;31m";

    //CONSTRUCTOR
    public Menu(String menuName) {
        this.menuName = menuName;
    }

    //METHODS

    public void displayMenu() {
        System.out.println(this.menuName);
        for (int i = 0; i < items.size(); i++) {
            System.out.println(terminalColor + (i+1) + ". " + items.get(i).description);
        }
        System.out.println(terminalColor + (items.size() + 1) + ". Exit");
    }

    public void addItems(Menu_Items item) {
        items.add(item);
    }
    
    public int scan(){
        boolean validOption;
        int choice = 0;
        Scanner sc = new Scanner(System.in);
        do {
            try {
                validOption = false;
                choice = sc.nextInt();
            } catch (InputMismatchException e) {
                sc.next();
                validOption = true;
                System.out.println(errorColor + "Invalid input. Please enter a valid number.");
                this.displayMenu();
            }
        } while (validOption == true);
        return choice;
    }

    public void execute(int choice) {
        if (choice == this.items.size() + 1) {
            this.stop = true;
            Database.getInstance().Close();
            return;
        }
        try {
            items.get(choice-1).execute();
        } catch (IndexOutOfBoundsException e) {
            System.out.println(errorColor + "Not a valid option. Please try again.");
        }
    }

    public void run() {
        while (!this.stop) {
            this.displayMenu();
            int choice = this.scan();
            this.execute(choice);
        }
    }
}