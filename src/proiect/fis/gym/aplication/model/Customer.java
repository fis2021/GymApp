package proiect.fis.gym.aplication.model;

import java.util.Objects;

public class Customer {
    private String firstName;
    private String lastName;
    private int age;
    private String phoneNumber;
    private String email;
    //private Passwor

    public Customer(String firstName, String lastName, int age, String phoneNumber,String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email=email;
    }

    public void setfirstName(String firstName) { this.firstName=firstName; }
    public void setlastName(String lastName) { this.lastName=lastName; }
    public void setAge(int age){this.age=age;}
    public void setPhone(String phoneNumber) { this.phoneNumber=phoneNumber; }
    public void setEmail(String email) { this.email=email; }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public int getAge() { return age; }
    public String getPhoneNumber() { return phoneNumber; }
    public String email() { return email; }


    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email= ' " + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return age == customer.age && Objects.equals(firstName, customer.firstName) && Objects.equals(lastName, customer.lastName) && Objects.equals(phoneNumber, customer.phoneNumber) && Objects.equals(email, customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age, phoneNumber, email);
    }
}
