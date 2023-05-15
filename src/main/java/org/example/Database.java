package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {

    //ATTRIBUTS

    private static Database instance;
    public Connection cnx;
    private static String pathToDB = "jdbc:sqlite:database/sqlite_database.db";
    private static String terminalColor = "\033[0;36m";
    private static String errorColor = "\u001B[31m";

    //INITIALISATION

    private Database() {
        DatabaseConnection();
    }

    public static Database getInstance() {
        if (instance == null) {
            System.out.println(terminalColor + "[DATABASE] New database instance created");
            instance = new Database();
        }
        return instance;
    }

    private void DatabaseConnection(){
        try {
            cnx = DriverManager.getConnection(pathToDB);
            
            System.out.println(terminalColor + "[DATABASE] Database connected (PONG!)");
            
        } catch (SQLException ex) {
            System.out.println(errorColor + "[DATABASE] Error during database connection \n[ERR] " + ex.getMessage());
        }
    }

    //DESTRUCT

    public void Close() {
        try {
            if (cnx != null) {
                cnx.close();
                System.out.println(terminalColor + "[DATABASE] Database succesfully disconnected");
            }
        } catch (SQLException ex) {
            System.out.println(errorColor + "[DATABASE] Connexion doesn't exist \n[ERROR] " + ex.getMessage());
        }
    }

    @Override
    public void finalize(){
        this.Close();
    }

    //METHODS

    public void AddStation(Station station){
        try {
            String gazole = station.carburants.get(0).price;
            String sp95 = station.carburants.get(1).price;
            String e10 = station.carburants.get(2).price;
            String sp98 = station.carburants.get(3).price;
            String e85 = station.carburants.get(4).price;
            String gplc = station.carburants.get(5).price;
            PreparedStatement stmt = Database.getInstance().cnx.prepareStatement("INSERT INTO stations (city, brand, stationAddress, zipcode, gazole, sp95, e10, sp98, e85, gplc) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, station.city);
            stmt.setString(2, station.brand);
            stmt.setString(3, station.address);
            stmt.setString(4, station.zipcode);
            stmt.setString(5, gazole);
            stmt.setString(6, sp95);
            stmt.setString(7, e10);
            stmt.setString(8, sp98);
            stmt.setString(9, e85);
            stmt.setString(10, gplc);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(errorColor + "[SQL] Failed to insert into database:\n[ERROR]" + e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            System.out.println(errorColor + "[FORMAT] Failed to get price information:\n[ERROR]" + e.getMessage());
        }
    }
}
