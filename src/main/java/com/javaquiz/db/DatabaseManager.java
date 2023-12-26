package com.javaquiz.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3310/quiz?autoReconnect=true&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    private static DatabaseManager instance;
    private Connection connection;

    private DatabaseManager() {
        // Initialization of the connection can be done in the constructor
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection established!");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Exception in database connection: Connection failed!");
            e.printStackTrace();
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public ResultSet executeQuery(String query, Object... parameters) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            setParameters(preparedStatement, parameters);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int executeUpdate(String query, Object... parameters) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            setParameters(preparedStatement, parameters);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private void setParameters(PreparedStatement preparedStatement, Object... parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            preparedStatement.setObject(i + 1, parameters[i]);
        }
    }

    public static void main(String[] args) {
        DatabaseManager db = DatabaseManager.getInstance();
        try {
            ResultSet resultSet = db.executeQuery("SELECT * FROM Users");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("username"));
                System.out.println(resultSet.getString("password"));
                System.out.println(resultSet.getString("email"));
                System.out.println(resultSet.getString("isAdmin"));
            }
            int result = db.executeUpdate("INSERT INTO Users (username, password, email, isAdmin) VALUES (?, ?, ?, ?)", "test", "test", "test", false);
            if (result == 1) {
                System.out.println("User inserted!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
