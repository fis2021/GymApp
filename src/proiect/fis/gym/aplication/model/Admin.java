package proiect.fis.gym.aplication.model;

import java.util.Objects;

public class Admin {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    //cod pt register ca admin
    //private static final String adminCode = "1234";

    public Admin(){}

    public Admin(String firstName, String lastName, String email, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return firstName.equals(admin.firstName) &&
                lastName.equals(admin.lastName) &&
                username.equals(admin.username) &&
                password.equals(admin.password) &&
                email.equals(admin.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, username, password, email);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}