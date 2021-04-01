package proiect.fis.gym.aplication.services;

import javafx.fxml.FXML;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.event.ChangeInfo;
import org.dizitart.no2.event.ChangeListener;
import org.dizitart.no2.event.ChangeType;
import org.dizitart.no2.objects.ObjectRepository;
import proiect.fis.gym.aplication.exceptions.*;
import proiect.fis.gym.aplication.model.Customer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static proiect.fis.gym.aplication.services.FileSystemService.getPathToFile;

public class CustomerService {

    private static ObjectRepository<Customer> customerRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("GymAplication.db").toFile())
                .openOrCreate("Geo", "Rares");

        customerRepository = database.getRepository(Customer.class);

    }

    public static ObjectRepository<Customer> getCustomerRepository(){
        return customerRepository;
    }


    public static void addUser(String username, String password, String role, String firstName, String lastName, String phoneNumber, String email) throws UsernameAlreadyExistsException, corectEmailException, FieldsAreNotEmptyException, ValidPasswordException, ValidUsernameException, validPhoneNumberException {
        checkFieldsAreNotEmpty(username,password,role,firstName,lastName,phoneNumber,email);
        checkUserDoesNotAlreadyExist(username);
        checkUsername(username);
        checkPassword(password);
        checkEmailIsValid(email);
        checkPhoneNumber(phoneNumber);
        customerRepository.insert(new Customer(username, encodePassword(username, password), role, firstName, lastName, phoneNumber, email));
    }

    private static void checkPhoneNumber(String phoneNumber) throws validPhoneNumberException{
        String regex= "^\\+(?:[0-9] ?){6,14}[0-9]$";
        Pattern pat = Pattern.compile(regex);
        if( !(pat.matcher(phoneNumber).matches()) )
            throw new validPhoneNumberException();
    }

    private static void checkUsername(String username) throws ValidUsernameException{
        if(username.length()<5)
            throw new ValidUsernameException();
    }

    private static void checkPassword(String password) throws ValidPasswordException{
        String regex= "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=_])(?=\\S+$).{8,20}$";
        Pattern pat = Pattern.compile(regex);
        if( !(pat.matcher(password).matches()) )
            throw new ValidPasswordException();
    }

    private static void checkFieldsAreNotEmpty(String username, String password, String role, String firstName, String lastName, String phoneNumber, String email) throws FieldsAreNotEmptyException{
        if(username.isEmpty() || password.isEmpty() || role.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty() || email.isEmpty()){
            throw new FieldsAreNotEmptyException();
        }
    }

    private static void checkEmailIsValid(String email) throws corectEmailException {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            throw new corectEmailException();
        if( !(pat.matcher(email).matches()) )
            throw new corectEmailException();
    }

    private static void checkUserDoesNotAlreadyExist(String username) throws UsernameAlreadyExistsException {
        for (Customer customer : customerRepository.find()) {
            if (Objects.equals(username, customer.getUsername()))
                throw new UsernameAlreadyExistsException(username);
        }
    }

    private static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));


        return new String(hashedPassword, StandardCharsets.UTF_8).replace("\"", "");
    }

    private static MessageDigest getMessageDigest() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 does not exist!");
        }
        return md;
    }

}
