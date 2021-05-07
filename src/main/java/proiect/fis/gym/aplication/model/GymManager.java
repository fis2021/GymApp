package proiect.fis.gym.aplication.model;

import org.dizitart.no2.objects.Id;

import java.util.ArrayList;
import java.util.Objects;

//probabil va genera conflicte, dar e in regula.
//se adauga un field de username, cam atata tot

public class GymManager {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String gymLocation;
    private String companyName;
    @Id
    private String username;
    private String password;
    private ArrayList<Course> courseList;
    private ArrayList<Review> reviewList;
    private String profilePicURL;
    private boolean isTaxed = false;

    public GymManager(String firstName, String lastName, String phoneNumber, String email, String gymLocation, String companyName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gymLocation = gymLocation;
        this.companyName = companyName;
        this.username = username;
        this.password = password;
        courseList = new ArrayList<Course>();
        reviewList = new ArrayList<Review>();
    }

    public GymManager(){

    }

    public boolean findCourse(Course toBeFound){
        for(Course course: courseList){
            if(toBeFound.equals(course)){
                return true;
            }
        }

        return false;
    }

    public Course getCourseFromList(Course toBeFound){
        for(Course course:courseList){
            if(course.equals(toBeFound)){
                return course;
            }
        }

        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GymManager that = (GymManager) o;
        return firstName.equals(that.firstName) &&
                lastName.equals(that.lastName) &&
                phoneNumber.equals(that.phoneNumber) &&
                email.equals(that.email) &&
                gymLocation.equals(that.gymLocation) &&
                companyName.equals(that.companyName) &&
                username.equals(that.username) &&
                password.equals(that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, phoneNumber, email, gymLocation, companyName, username, password);
    }

    public ArrayList<Course> getCourseList() {
        return courseList;
    }

    public ArrayList<Review> getReviewList(){return reviewList; }

    public void setCourseList(ArrayList<Course> courseList) {
        this.courseList = courseList;
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

    public String getGymLocation() {
        return gymLocation;
    }

    public void setGymLocation(String gymLocation) {
        this.gymLocation = gymLocation;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getProfilePicURL() {
        return profilePicURL;
    }

    public void setProfilePicURL(String profilePicURL) {
        this.profilePicURL = profilePicURL;
    }

    public boolean isTaxed() {
        return isTaxed;
    }

    public void setTaxed(boolean taxed) {
        isTaxed = taxed;
    }
}