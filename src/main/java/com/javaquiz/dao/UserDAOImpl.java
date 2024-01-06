package com.javaquiz.dao;

import com.javaquiz.beans.User;
import com.javaquiz.db.DatabaseManager;

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
        System.out.println("Trying to get all users from the database: SELECT * FROM Users");
        try {
            ResultSet resultSet = dbManager.executeQuery("SELECT * FROM Users");
            while (resultSet.next()) {
                User user = createUserFromResultSet(resultSet);
                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Error while getting all users from the database: SELECT * FROM Users");
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public User getUserById(int userId) {
        System.out.println("Trying to get user from the database: SELECT * FROM Users WHERE id=" + userId);
        try {
            ResultSet resultSet = dbManager.executeQuery("SELECT * FROM Users WHERE id=?", userId);
            if (resultSet.next()) {
                return createUserFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Error while getting user from the database: SELECT * FROM Users WHERE id=" + userId);
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public User getUserByUsername(String username) {
        System.out.println("Trying to get user from the database: SELECT * FROM Users WHERE username=" + username);
        try {
            ResultSet resultSet = dbManager.executeQuery("SELECT * FROM Users WHERE username=?", username);
            if (resultSet.next()) {
                return createUserFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Error while getting user from the database: SELECT * FROM Users WHERE id=" + username);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addUser(User user) {
        System.out.printf("Trying to add user to the database: INSERT INTO Users (username, password, email, isAdmin) VALUES (%s, %s, %s, %s) \n",
                user.getUsername(), user.getPassword(), user.getEmail(), user.isAdmin());

        try {
            int result = dbManager.executeUpdate("INSERT INTO Users (username, password, email, isAdmin) VALUES (?, ?, ?, ?)",
                    user.getUsername(), user.getPassword(), user.getEmail(), user.isAdmin());
            return result != -1;
        } catch (Exception e) {
            System.out.printf("Error while adding user to the database: INSERT INTO Users (username, password, email, isAdmin) VALUES (%s, %s, %s, %s)\n",
                    user.getUsername(), user.getPassword(), user.getEmail(), user.isAdmin());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateUser(int userId, User user) {
        System.out.printf("Trying to update user in the database: UPDATE Users SET username=%s, password=%s, email=%s, isAdmin=%s WHERE id=%s\n",
                user.getUsername(), user.getPassword(), user.getEmail(), user.isAdmin(), userId);
        try {
            int result = dbManager.executeUpdate("UPDATE Users SET username=?, password=?, email=?, isAdmin=? WHERE id=?",
                    user.getUsername(), user.getPassword(), user.getEmail(), user.isAdmin(), userId);
            return result == 1;
        } catch (Exception e) {
            System.out.printf("Error while updating user in the database: UPDATE Users SET username=%s, password=%s, email=%s, isAdmin=%s WHERE id=%s\n",
                    user.getUsername(), user.getPassword(), user.getEmail(), user.isAdmin(), userId);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteUser(int userId) {
        System.out.println("Trying to delete user from the database: DELETE FROM Users WHERE id=" + userId);
        try {
            int result = dbManager.executeUpdate("DELETE FROM Users WHERE id=?", userId);
            return result == 1;
        } catch (Exception e) {
            System.out.println("Error while deleting user from the database: DELETE FROM Users WHERE id=" + userId);
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
//        userDAOImpl.testGetAllUsers();
//        userDAOImpl.testGetUserById(4);
        userDAOImpl.testGetUserByUsername();
//        userDAOImpl.testAddUser();
//        userDAOImpl.testUpdateUser();
//        userDAOImpl.testDeleteUser();
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
    private void testGetUserById(int userId) {
        UserDAO userDAO = new UserDAOImpl();
        User user = userDAO.getUserById(userId);
        System.out.println(user);
    }
    // Testing getUserByUsername() method
    private void testGetUserByUsername() {
        UserDAO userDAO = new UserDAOImpl();
        User user = userDAO.getUserByUsername("test");
        System.out.println(user);
    }
    // Testing addUser() method
    private void testAddUser() {
        UserDAO userDAO = new UserDAOImpl();
        User user = new User("aaa", "aaa", "aaa", false);
        boolean result = userDAO.addUser(user);
        System.out.println(result);
    }
    // Testing updateUser() method
    private void testUpdateUser() {
        UserDAO userDAO = new UserDAOImpl();
        User user = new User( "aaaa", "aaaa", "aaaa", false);
        boolean result = userDAO.updateUser(23, user);
        System.out.println(result);
    }
    // Testing deleteUser() method
    private void testDeleteUser() {
        UserDAO userDAO = new UserDAOImpl();
        boolean result = userDAO.deleteUser(4);
        System.out.println(result);
    }
}
