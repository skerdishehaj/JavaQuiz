package com.javaquiz.dao;

import com.javaquiz.beans.User;

import java.util.List;

public interface UserDAO {
    List<User> getAllUsers();
    User getUserById(int userId);
    User getUserByUsername(String username);
    boolean addUser(User user);
    boolean updateUser(int userId, User user);
    boolean deleteUser(int userId);

    boolean emailExists(int userId, String email);
    boolean usernameExists(int userId, String username);
}
