package com.javaquiz.dao;

import com.javaquiz.beans.User;
import com.javaquiz.db.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private final DatabaseManager dbManager;

    public UserDAOImpl() {
        this.dbManager = DatabaseManager.getInstance();
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try {
            ResultSet resultSet = dbManager.executeQuery("SELECT * FROM Users");
            while (resultSet.next()) {
                User user = createUserFromResultSet(resultSet);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public User getUserById(int userId) {
        try {
            ResultSet resultSet = dbManager.executeQuery("SELECT * FROM Users WHERE id=?", userId);
            if (resultSet.next()) {
                return createUserFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addUser(User user) {
        try {
            int result = dbManager.executeUpdate("INSERT INTO Users (username, password, email, isAdmin) VALUES (?, ?, ?, ?)",
                    user.getUsername(), user.getPassword(), user.getEmail(), user.isAdmin());
            return result == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateUser(User user) {
        try {
            int result = dbManager.executeUpdate("UPDATE Users SET username=?, password=?, email=?, isAdmin=? WHERE id=?",
                    user.getUsername(), user.getPassword(), user.getEmail(), user.isAdmin(), user.getId());
            return result == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteUser(int userId) {
        try {
            int result = dbManager.executeUpdate("DELETE FROM Users WHERE id=?", userId);
            return result == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private User createUserFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String username = resultSet.getString("username");
        String password = resultSet.getString("password");
        String email = resultSet.getString("email");
        boolean isAdmin = resultSet.getBoolean("isAdmin");
        return new User(id, username, password, email, isAdmin);
    }

    // Testing the UserDAOImpl class
    public static void main(String[] args) {
        UserDAOImpl userDAOImpl = new UserDAOImpl();
        userDAOImpl.testGetAllUsers();
        userDAOImpl.testGetUserById();
        userDAOImpl.testAddUser();
        userDAOImpl.testUpdateUser();
        userDAOImpl.testDeleteUser();
    }
    // Testing getAllUsers() method
    private void testGetAllUsers() {
        UserDAO userDAO = new UserDAOImpl();
        List<User> userList = userDAO.getAllUsers();
        for (User user : userList) {
            System.out.println(user);
        }
    }
    // Testing getUserById() method
    private void testGetUserById() {
        UserDAO userDAO = new UserDAOImpl();
        User user = userDAO.getUserById(1);
        System.out.println(user);
    }
    // Testing addUser() method
    private void testAddUser() {
        UserDAO userDAO = new UserDAOImpl();
        User user = new User("test", "test", "test", false);
        boolean result = userDAO.addUser(user);
        System.out.println(result);
    }
    // Testing updateUser() method
    private void testUpdateUser() {
        UserDAO userDAO = new UserDAOImpl();
        User user = new User(1, "test", "test", "test", false);
        boolean result = userDAO.updateUser(user);
        System.out.println(result);
    }
    // Testing deleteUser() method
    private void testDeleteUser() {
        UserDAO userDAO = new UserDAOImpl();
        boolean result = userDAO.deleteUser(3);
        System.out.println(result);
    }


}
