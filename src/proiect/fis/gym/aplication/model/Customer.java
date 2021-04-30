package proiect.fis.gym.aplication.model;
import java.lang.reflect.Array;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.dizitart.no2.objects.Id;

import javax.validation.constraints.Past;
import java.util.ArrayList;
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
    private LocalDate[] date=new LocalDate[3];
    private String[] gym=new String[3];
    private final int k=3;
    private ArrayList<Course> Smartfitcourses;
    private ArrayList<Course> Iguanacourses;
    private ArrayList<Course> GymOnecourses;

    public void addSmartfitCourse(Course c){
        Smartfitcourses.add(c);
    }
    public void addGymOneCourse(Course c){GymOnecourses.add(c);}
    public void addIguanaCourse(Course c){Iguanacourses.add(c);}

    public ArrayList<Course> getArraySmart(){return Smartfitcourses;}
    public ArrayList<Course> getArrayOne(){return GymOnecourses;}
    public ArrayList<Course> getArrayIguana(){return Iguanacourses;}

    public ArrayList<Course> getSmartfitCourse(){
        return Smartfitcourses;
    }
    public ArrayList<Course> getGymOneCourse(){
        return GymOnecourses;
    }
    public ArrayList<Course> getIguanaCourse(){
        return Iguanacourses;
    }

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
            date[i]=null;
        }
        Smartfitcourses = new ArrayList<Course>();
        GymOnecourses=new ArrayList<Course>();
        Iguanacourses=new ArrayList<Course>();
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

   public LocalDate[] getDate(){
        return date;
   }

    public void setDate(int i,LocalDate date){
        this.date[i]=date;
    }

    public void setDate2(LocalDate[] date){this.date=date;}

    public LocalDate getDate(int i,LocalDate[] date){
        return date[i];
    }

    public LocalDate[] getDate2(){
       return date;
   }

    public String getGym(int i,String[] gym) {
        return gym[i];
    }

   public String[] getGym2() {
       return gym;
    }

    public void setGym(int i, String gym) {
        this.gym[i] = gym;
    }

    public int getK() {
        return k;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return k == customer.k && Objects.equals(username, customer.username) && Objects.equals(password, customer.password) && Objects.equals(role, customer.role) && Objects.equals(firstName, customer.firstName) && Objects.equals(lastName, customer.lastName) && Objects.equals(phoneNumber, customer.phoneNumber) && Objects.equals(email, customer.email) && Arrays.equals(date, customer.date) && Arrays.equals(gym, customer.gym);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(username, password, role, firstName, lastName, phoneNumber, email, k);
        result = 31 * result + Arrays.hashCode(date);
        result = 31 * result + Arrays.hashCode(gym);
        return result;
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
                ", date=" + Arrays.toString(date) +
                ", gym=" + Arrays.toString(gym) +
                ", k=" + k +
                '}';
    }
}
