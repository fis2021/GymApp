package proiect.fis.gym.aplication.services;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import proiect.fis.gym.aplication.controllers.RegisterController;
import proiect.fis.gym.aplication.model.Admin;
import proiect.fis.gym.aplication.model.GymManager;
import org.dizitart.no2.*;
import org.dizitart.no2.objects.ObjectRepository;
import proiect.fis.gym.aplication.exceptions.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Matcher;

public class AdminService {
    private static Nitrite database;
    private static ObjectRepository<Admin> adminRepository;
    private static final String code = "1234";


    public static void initDatabase() {

        database = Nitrite.builder()
                .filePath(FileSystemService.getPathToFile("Admins.db").toFile())
                .openOrCreate("Geo", "Rares");

        adminRepository = database.getRepository(Admin.class);
    }

    public static void initTestDatabase(String dbName){
        database = Nitrite.builder()
                .filePath(FileSystemService.getPathToTestFile(dbName).toFile())
                .openOrCreate("Geo", "Rares");

        adminRepository = database.getRepository(Admin.class);
    }

    public static ObjectRepository<Admin> getAdminRepository(){
        return adminRepository;
    }

    public static void closeDatabase(){
        adminRepository.close();
    }

    public static void addUser(String adminCode, String confirmPassword, String firstName, String lastName, String email, String username, String password)
            throws UsernameAlreadyExistsException, FieldsAreNotEmptyException, ValidPasswordException, NotMatchingPasswordsException,
            corectEmailException, InvalidAdminCodeException
    {
        checkUserDoesNotAlreadyExist(username);
        checkInvalidEmail(email);
        checkInvalidPasswordException(password);
        checkNotMatchingPasswords(password, confirmPassword);
        checkInvalidAdminCode(adminCode);
        adminRepository.insert(new Admin(firstName, lastName, email, username, encodePassword(username, password)));
    }

    private static void checkInvalidAdminCode(String adminCode) throws InvalidAdminCodeException {
        if(!adminCode.equals(code)){
            throw new InvalidAdminCodeException();
        }
    }

    private static void checkInvalidEmail(String mail) throws corectEmailException{
        Matcher matcher = RegisterController.VALID_EMAIL_ADDRESS_REGEX.matcher(mail);
        if(!matcher.find()){
            throw new corectEmailException();
        }
    }

    private static void checkEmptyFields(GridPane gridPane) throws FieldsAreNotEmptyException{
        for ( Node node : gridPane.getChildren() ) {
            if (node instanceof TextField) {
                if(((TextField) node).getText().equals("")){
                    throw new FieldsAreNotEmptyException();
                }
            }
        }
    }

    private static void checkInvalidPasswordException(String pass1) throws ValidPasswordException {
        //Matcher matcher = VALID_PASSWORD_REGEX.matcher(pass1);
        if(!pass1.matches(RegisterController.VALID_PASSWORD_REGEX)){
            throw new ValidPasswordException();
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

    public static boolean taxGym(GymManager manager, Label warningLabel) {
        if (!manager.isTaxed()) {
            manager.setTaxed(true);
            warningLabel.setVisible(false);
            return true;
        }
        else{
            warningLabel.setVisible(true);
            return false;
        }
    }

    public static List<Admin> getAllUsers() {
        return adminRepository.find().toList();
    }

    public static Nitrite getDatabase() {
        return database;
    }
}