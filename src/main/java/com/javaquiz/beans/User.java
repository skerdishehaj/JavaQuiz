package com.javaquiz.beans;

public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private boolean isAdmin;

    // Konstruktor, getter dhe setter metoda
    public User() {
        super();
    }

    public User(int id, String username, String password, String email, boolean isAdmin) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.isAdmin = isAdmin;
    }

    public User(String username, String password, String email, boolean isAdmin) {
        super();
        this.username = username;
        this.password = password;
        this.email = email;
        this.isAdmin = isAdmin;
    }
    public User(String username, String password, String email) {
        super();
        this.username = username;
        this.password = password;
        this.email = email;
        this.isAdmin = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", username='" + username + '\'' +
               ", password='" + password + '\'' +
               ", email='" + email + '\'' +
               ", isAdmin=" + isAdmin +
               '}';
    }

    // Testimi i l;asës User
    public static void main(String[] args) {
        User user = new User(1, "admin", "admin", "admin", true);
        System.out.println(user);
    }
}