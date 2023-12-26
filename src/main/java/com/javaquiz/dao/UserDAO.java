package com.javaquiz.dao;

import com.javaquiz.beans.User;

import java.util.List;

public interface UserDAO {
    List<User> getAllUsers();
    User getUserById(int userId);
    boolean addUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(int userId);
}
