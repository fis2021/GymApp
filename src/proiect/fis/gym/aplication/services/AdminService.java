package proiect.fis.gym.aplication.services;

import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.dizitart.no2.*;
import org.dizitart.no2.objects.ObjectRepository;
import proiect.fis.gym.aplication.exceptions.*;
import proiect.fis.gym.aplication.model.Admin;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;

import static proiect.fis.gym.aplication.controllers.RegisterController.VALID_EMAIL_ADDRESS_REGEX;
import static proiect.fis.gym.aplication.controllers.RegisterController.VALID_PASSWORD_REGEX;
import static proiect.fis.gym.aplication.services.FileSystemService.getPathToFile;

public class AdminService {

    private static ObjectRepository<Admin> adminRepository;
    private static final String code = "1234";


    public static void initDatabase() {
       // String userDirectory = Paths.get("").toAbsolutePath().toString();
        //System.out.println(userDirectory);

        //String dbPath = userDirectory + "\\src\\proiect\\fis\\gym\\aplication\\databases\\" ;

        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("GymAplication.db").toFile())
                .openOrCreate("test", "test");

        adminRepository = database.getRepository(Admin.class);
    }

    public static void addUser(TextField adminCode, GridPane gridPane, PasswordField confirmPassword, String firstName, String lastName, String email, String username, String password)
            throws UsernameAlreadyExistsException, EmptyFieldsException, InvalidPasswordException, NotMatchingPasswordsException,
                    InvalidEmailException, InvalidAdminCodeException
    {
        checkUserDoesNotAlreadyExist(username);
        checkEmptyFields(gridPane);
        checkInvalidEmail(email);
        checkInvalidPasswordException(password);
        checkNotMatchingPasswords(password, confirmPassword.getText());
        checkInvalidAdminCode(adminCode.getText());
        adminRepository.insert(new Admin(firstName, lastName, email, username, encodePassword(username, password)));
    }

    private static void checkInvalidAdminCode(String adminCode) throws InvalidAdminCodeException {
        if(!adminCode.equals(code)){
            throw new InvalidAdminCodeException();
        }
    }

    private static void checkInvalidEmail(String mail) throws InvalidEmailException{
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(mail);
        if(!matcher.find()){
            throw new InvalidEmailException();
        }
    }

    private static void checkEmptyFields(GridPane gridPane) throws EmptyFieldsException{
        for ( Node node : gridPane.getChildren() ) {
            if (node instanceof TextField) {
                if(((TextField) node).getText().equals("")){
                    throw new EmptyFieldsException();
                }
            }
        }
    }

    private static void checkInvalidPasswordException(String pass1) throws InvalidPasswordException {
        //Matcher matcher = VALID_PASSWORD_REGEX.matcher(pass1);
        if(!pass1.matches(VALID_PASSWORD_REGEX)){
            throw new InvalidPasswordException();
        }
    }

    private static void checkNotMatchingPasswords(String pass1, String pass2) throws NotMatchingPasswordsException{
        if (!pass1.equals(pass2)){
            throw new NotMatchingPasswordsException();
        }
    }

    private static void checkUserDoesNotAlreadyExist(String username) throws UsernameAlreadyExistsException {
        for (Admin admin : adminRepository.find()) {
            if (username.equals(admin.getUsername()))
                throw new UsernameAlreadyExistsException(username);
        }
    }

    private static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        // This is the way a password should be encoded when checking the credentials
        return new String(hashedPassword, StandardCharsets.UTF_8)
                .replace("\"", ""); //to be able to save in JSON format
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