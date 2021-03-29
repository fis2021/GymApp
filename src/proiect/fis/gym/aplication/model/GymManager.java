package proiect.fis.gym.aplication.model;

import java.util.Objects;

public class GymManager {
    private String firstName;
    private String lastName;
    private int age;
    private String gymLocation;
    private String companyName;

    public GymManager(String firstName, String lastName, int age, String gymLocation, String companyName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gymLocation = gymLocation;
        this.companyName = companyName;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public int getAge() { return age; }
    public String getGymLocation() { return gymLocation; }
    public String getCompanyName() { return companyName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setAge(int age) { this.age = age; }
    public void setGymLocation(String gymLocation) { this.gymLocation = gymLocation; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    @Override
    public String toString() {
        return "GymManager{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", gymLocation='" + gymLocation + '\'' +
                ", companyName='" + companyName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GymManager that = (GymManager) o;
        return age == that.age && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(gymLocation, that.gymLocation) && Objects.equals(companyName, that.companyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age, gymLocation, companyName);
    }
}
