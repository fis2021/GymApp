package proiect.fis.gym.aplication.model;

import org.dizitart.no2.objects.Id;
import java.util.Objects;

public class Customer {
    @Id
    private String username;
    private String password;
    private String role;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String subscriptionExpirationDay;
    private String subscriptionExpirationMonth;
    private String subscriptionExpirationYear;
    private String gym;

    public Customer(String username, String password, String role, String firstName, String lastName, String phoneNumber, String email) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        gym ="";
        subscriptionExpirationDay="";
        subscriptionExpirationMonth="";
        subscriptionExpirationYear="";

    }

    public String getSubscriptionExpirationDay() {
        return subscriptionExpirationDay;
    }

    public String getSubscriptionExpirationMonth() {
        return subscriptionExpirationMonth;
    }

    public String getSubscriptionExpirationYear() {
        return subscriptionExpirationYear;
    }

    public void setSubscriptionExpirationDay(String subscriptionExpirationDay) {
        this.subscriptionExpirationDay = subscriptionExpirationDay;
    }

    public void setSubscriptionExpirationMonth(String subscriptionExpirationMonth) {
        this.subscriptionExpirationMonth = subscriptionExpirationMonth;
    }

    public void setSubscriptionExpirationYear(String subscriptionExpirationYear) {
        this.subscriptionExpirationYear = subscriptionExpirationYear;
    }

    public void setGym(String gym) {
        this.gym = gym;
    }

    public Customer(){
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(username, customer.username) && Objects.equals(password, customer.password) && Objects.equals(role, customer.role) && Objects.equals(firstName, customer.firstName) && Objects.equals(lastName, customer.lastName) && Objects.equals(phoneNumber, customer.phoneNumber) && Objects.equals(email, customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, role, firstName, lastName, phoneNumber, email);
    }
}
