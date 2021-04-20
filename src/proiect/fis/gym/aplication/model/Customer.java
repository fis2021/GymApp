package proiect.fis.gym.aplication.model;

import org.dizitart.no2.objects.Id;

import java.util.Arrays;
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
    private String[] subscriptionExpirationDay= new String[3];
    private String[] subscriptionExpirationMonth= new String [3];
    private String[] subscriptionExpirationYear= new String[3];
    private String[] gym=new String[3];
    private final int k=3;

    public Customer(){}

    public Customer(String username, String password, String role, String firstName, String lastName, String phoneNumber, String email) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        for(int i=0;i<k;i++) {
            gym[i] = "";
            subscriptionExpirationDay[i] = "";
            subscriptionExpirationMonth[i] = "";
            subscriptionExpirationYear[i] = "";
        }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String[] getSubscriptionExpirationDay() {
        return subscriptionExpirationDay;
    }

    public void setSubscriptionExpirationDay(int i,String subscriptionExpirationDay) {
        this.subscriptionExpirationDay[i] = subscriptionExpirationDay;
    }

    public String[] getSubscriptionExpirationMonth() {
        return subscriptionExpirationMonth;
    }

    public void setSubscriptionExpirationMonth(int i,String subscriptionExpirationMonth) {
        this.subscriptionExpirationMonth[i] = subscriptionExpirationMonth;
    }

    public String[] getSubscriptionExpirationYear() {
        return subscriptionExpirationYear;
    }

    public void setSubscriptionExpirationYear(int i,String subscriptionExpirationYear) {
        this.subscriptionExpirationYear[i] = subscriptionExpirationYear;
    }

    public String[] getGym() {
        return gym;
    }

    public void setGym(String[] gym) {
        this.gym = gym;
    }

    public int getK() {
        return k;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", subscriptionExpirationDay=" + Arrays.toString(subscriptionExpirationDay) +
                ", subscriptionExpirationMonth=" + Arrays.toString(subscriptionExpirationMonth) +
                ", subscriptionExpirationYear=" + Arrays.toString(subscriptionExpirationYear) +
                ", gym=" + Arrays.toString(gym) +
                ", k=" + k +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return k == customer.k && Objects.equals(username, customer.username) && Objects.equals(password, customer.password) && Objects.equals(role, customer.role) && Objects.equals(firstName, customer.firstName) && Objects.equals(lastName, customer.lastName) && Objects.equals(phoneNumber, customer.phoneNumber) && Objects.equals(email, customer.email) && Arrays.equals(subscriptionExpirationDay, customer.subscriptionExpirationDay) && Arrays.equals(subscriptionExpirationMonth, customer.subscriptionExpirationMonth) && Arrays.equals(subscriptionExpirationYear, customer.subscriptionExpirationYear) && Arrays.equals(gym, customer.gym);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(username, password, role, firstName, lastName, phoneNumber, email, k);
        result = 31 * result + Arrays.hashCode(subscriptionExpirationDay);
        result = 31 * result + Arrays.hashCode(subscriptionExpirationMonth);
        result = 31 * result + Arrays.hashCode(subscriptionExpirationYear);
        result = 31 * result + Arrays.hashCode(gym);
        return result;
    }
}
