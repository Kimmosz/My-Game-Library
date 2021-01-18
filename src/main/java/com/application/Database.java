package com.application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    // Database credentials
    private static String jdbcURL = "jdbc:postgresql://localhost:5432/MyGameLibrary";
    private static String jdbcUsername = "postgres";
    private static String jdbcPassword = "asdf";

    public static boolean usernameExists(String username) {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            String sql = "SELECT * FROM users WHERE username='"+username+"'";
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(sql);
            if (resultset.next()) {
                return true;
            }
            connection.close();
            return false;
        } catch(SQLException e) {
            System.out.println("Error in connecting postgres");
            e.printStackTrace();
            return false;
        }
    }
    public static boolean emailExists(String email) {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            String sql = "SELECT * FROM users WHERE email='"+email+"'";
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(sql);
            if (resultset.next()) {
                return true;
            }
            connection.close();
            return false;
        } catch(SQLException e) {
            System.out.println("Error in connecting postgres");
            e.printStackTrace();
            return false;
        }
    }
    public static boolean registerUser(String username, String email, String password) {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            String sql = "INSERT INTO users (username, email, password) VALUES ('"+username+"', '"+email+"', '"+password+"')";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            connection.close();
            return true;
        } catch(SQLException e) {
            System.out.println("Error in connecting postgres");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean correctLoginCredentials(String username, String password) {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            String sql = "SELECT * FROM users WHERE username='"+username+"' AND password='"+password+"'";
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(sql);
            if (resultset.next()) {
                return true;
            }
            connection.close();
            return false;
        } catch(SQLException e) {
            System.out.println("Error in connecting postgres");
            e.printStackTrace();
            return false;
        }
    }
}
