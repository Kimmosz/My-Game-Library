package com.application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;

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

    public static boolean addNewGame(String title, String console, String genre, String type) {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            String sql = "INSERT INTO games (title, console, genre, type, username) VALUES ('"+title+"', '"+console+"', '"+genre+"', '"+type+"', '"+SessionAttributes.getLoggedUser()+"')";
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

    public static List<Game> getGames() {
        List<Game> gamesList = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            String sql = "SELECT * FROM games WHERE username='"+SessionAttributes.getLoggedUser()+"'";
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(sql);
            while (resultset.next()) {
                gamesList.add(new Game(resultset.getInt("id"), resultset.getString("title"), resultset.getString("console"), resultset.getString("genre"), resultset.getString("type")));
            }
            connection.close();
            return gamesList;
        } catch(SQLException e) {
            System.out.println("Error in connecting postgres");
            e.printStackTrace();
            return gamesList;
        }
    }

    public static void loadGame(TextField title, ComboBox<String> console, ComboBox<String> genre, RadioButtonGroup<String> type) {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            String sql = "SELECT * FROM games WHERE username='"+SessionAttributes.getLoggedUser()+"' AND id='"+SessionAttributes.getSelectedGame()+"'";
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(sql);
            if (resultset.next()) {
                title.setValue(resultset.getString("title"));
                console.setValue(resultset.getString("console"));
                genre.setValue(resultset.getString("genre"));
                type.setValue(resultset.getString("type"));
            } 
            connection.close();
        } catch(SQLException e) {
            System.out.println("Error in connecting postgres");
            e.printStackTrace();
        }  
    }

    public static boolean editGame(String title, String console, String genre, String type) {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            String sql = "UPDATE games SET title='"+title+"', console='"+console+"', genre='"+genre+"', type='"+type+"' WHERE username='"+SessionAttributes.getLoggedUser()+"' AND id='"+SessionAttributes.getSelectedGame()+"'";
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

    public static boolean deleteGame() {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            String sql = "DELETE FROM games WHERE username='"+SessionAttributes.getLoggedUser()+"' AND id='"+SessionAttributes.getSelectedGame()+"'";
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
}
