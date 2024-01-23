package com.javaquiz.db;

import java.sql.*;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3333/quiz_test?autoReconnect=true&allowPublicKeyRetrieval=true&useSSL=false";
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
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
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

/*
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
*/
public int executeUpdate(String query, Object... parameters) {
    try {
        PreparedStatement preparedStatement;

        // Check if it's an insert or update
        boolean insert = query.trim().toLowerCase().startsWith("insert");
        if (insert) {
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        } else {
            preparedStatement = connection.prepareStatement(query);
        }

        setParameters(preparedStatement, parameters);
        int rowsAffected = preparedStatement.executeUpdate();

        // Check if it's an insert and return the generated ID
        if (insert && rowsAffected > 0) {
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Assuming the generated key is an integer
                } else {
                    throw new SQLException("Failed to retrieve the generated ID.");
                }
            }
        } else {
            return rowsAffected;
        }
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
