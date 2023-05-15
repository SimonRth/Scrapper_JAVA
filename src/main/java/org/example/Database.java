package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

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
    public void SelectCity(String city){
        try {
            PreparedStatement stmt = Database.getInstance().cnx.prepareStatement("SELECT * FROM stations WHERE city = ?");
            stmt.setString(1, city);
            stmt.executeQuery();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(errorColor + "[SQL] Failed to select from database:\n[ERROR]" + e.getMessage());
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

    public void UpdateDatabase() {
        String sql_init = readSQLFile("./ressources/init_database.sql");
        String sql_drop = readSQLFile("./ressources/drop_database.sql");
        try {
            Database.getInstance();
            if (cnx != null) {
                Statement stmt = cnx.createStatement();
                stmt.execute(sql_drop);
                stmt.execute(sql_init);
            }
        } catch (Exception e) {
            System.out.println(errorColor + "[DATABASE] Error during database update \n[ERR] " + e.getMessage());
        }
    }

    private String readSQLFile(String SQLFile) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(SQLFile)));
            return content;
        } catch (IOException e) {
            System.out.println(errorColor + "[DATABASE] Error during database connection \n[ERR] " + e.getMessage());
        }
        return null;
    }

    //TODO

    public void AddStation(Station station){
        try {
            PreparedStatement stmt = Database.getInstance().cnx.prepareStatement("INSERT INTO stations (city, brand, stationAddress, zipcode, gazole, sp95, e10, sp98, e85, gplc) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, station.city);
            stmt.setString(2, station.brand);
            stmt.setString(3, station.address);
            stmt.setString(4, station.zipcode);
            stmt.setString(5, station.carburants.get(0).price);
            stmt.setString(6, station.carburants.get(1).price);
            stmt.setString(7, station.carburants.get(2).price);
            stmt.setString(8, station.carburants.get(3).price);
            stmt.setString(9, station.carburants.get(4).price);
            stmt.setString(10, station.carburants.get(5).price);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(errorColor + "[SQL] Failed to insert into database:\n[ERROR]" + e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            System.out.println(errorColor + "[FORMAT] Failed to get price information:\n[ERROR]" + e.getMessage());
        }
    }
}
