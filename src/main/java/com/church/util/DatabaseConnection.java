package com.church.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection connect() {
        try {
            // Force load the driver class
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:church.db";
            return DriverManager.getConnection(url);
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.err.println("Failed to connect to database");
            throw new RuntimeException(e);
        }
    }


}
