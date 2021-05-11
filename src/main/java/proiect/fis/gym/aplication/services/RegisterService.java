package proiect.fis.gym.aplication.services;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import proiect.fis.gym.aplication.controllers.RegisterController;
import proiect.fis.gym.aplication.exceptions.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterService {

    protected static void checkInvalidEmail(String mail) throws corectEmailException {
        Matcher matcher = RegisterController.VALID_EMAIL_ADDRESS_REGEX.matcher(mail);
        if(!matcher.find()){
            throw new corectEmailException();
        }
    }

    protected static void checkEmptyFields(GridPane gridPane) throws FieldsAreNotEmptyException {
        for ( Node node : gridPane.getChildren() ) {
            if (node instanceof TextField) {
                if(((TextField) node).getText().equals("")){
                    throw new FieldsAreNotEmptyException();
                }
            }
        }
    }

    protected static void checkPhoneNumber(String phoneNumber) throws validPhoneNumberException{
        String regex= "^\\+(?:[0-9] ?){6,14}[0-9]$";
        Pattern pat = Pattern.compile(regex);
        if( !(pat.matcher(phoneNumber).matches()) )
            throw new validPhoneNumberException();
    }

    protected static void checkUsername(String username) throws ValidUsernameException{
        if(username.length()<5)
            throw new ValidUsernameException();
    }

    protected static void checkInvalidPasswordException(String pass1) throws ValidPasswordException {
        //Matcher matcher = VALID_PASSWORD_REGEX.matcher(pass1);
        if(!pass1.matches(RegisterController.VALID_PASSWORD_REGEX)){
            throw new ValidPasswordException();
        }
    }

    protected static void checkNotMatchingPasswords(String pass1, String pass2) throws NotMatchingPasswordsException {
        if (!pass1.equals(pass2)){
            throw new NotMatchingPasswordsException();
        }
    }

    public static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        // This is the way a password should be encoded when checking the credentials
        return new String(hashedPassword, StandardCharsets.UTF_8)
                .replace("\"", ""); //to be able to save in JSON format
    }

    protected static MessageDigest getMessageDigest() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 does not exist!");
        }
        return md;
    }
}