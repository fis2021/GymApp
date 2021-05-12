package proiect.fis.gym.aplication.services;

import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;

import proiect.fis.gym.aplication.model.Admin;

import proiect.fis.gym.aplication.controllers.CommonFunctionality;

import proiect.fis.gym.aplication.model.GymManager;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import proiect.fis.gym.aplication.exceptions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GymManagerService extends RegisterService{
    private static Nitrite database;
    private static ObjectRepository<GymManager> gymManagerRepository;
    private static final ArrayList<String> usernameList = new ArrayList<String>(Arrays.asList("GymOne", "SmartFit", "Iguana"));

    public static void initDatabase() {

        database = Nitrite.builder()
                .filePath(FileSystemService.getPathToFile("Managers.db").toFile())
                .openOrCreate("Geo", "Rares");

        gymManagerRepository = database.getRepository(GymManager.class);

    }


    public static List<GymManager> getAllUsers() {
        return gymManagerRepository.find().toList();

    public static void initTestDatabase(String dbName){
        database = Nitrite.builder()
                .filePath(FileSystemService.getPathToTestFile(dbName).toFile())
                .openOrCreate("Geo", "Rares");

        gymManagerRepository = database.getRepository(GymManager.class);

    }

    public static ObjectRepository<GymManager> getGymManagerRepository(){
        return gymManagerRepository;
    }

    public static void addUser( String confirmPassword, String firstName, String lastName, String phoneNumber, String email, String gymLocation, String companyCode, String username, String password)
            throws UsernameAlreadyExistsException, FieldsAreNotEmptyException, ValidPasswordException, NotMatchingPasswordsException,
            corectEmailException, validPhoneNumberException, ValidUsernameException, ManagerUsernameIsNotOnShortListException {
        checkUserDoesNotAlreadyExist(username);
        checkInvalidEmail(email);
        checkManagersList(username);
        checkInvalidPasswordException(password);
        checkNotMatchingPasswords(password, confirmPassword);
        checkPhoneNumber(phoneNumber);
        checkUsername(username);
        gymManagerRepository.insert(new GymManager(firstName, lastName, phoneNumber, email, gymLocation, companyCode, username, encodePassword(username, password)));
    }

    public static void checkManagersList(String typedUsername) throws ManagerUsernameIsNotOnShortListException{
        int exists = 0;
        for(String username: usernameList){
            if(username.equals(typedUsername)){
                exists = 1;
                break;
            }
        }

        if(exists == 0){
            throw new ManagerUsernameIsNotOnShortListException();
        }
    }

    public static void checkUserDoesNotAlreadyExist(String username) throws UsernameAlreadyExistsException {
        for (GymManager gymManager : gymManagerRepository.find()) {
            System.out.println(gymManager.getUsername());
            if (username.equals(gymManager.getUsername())) {
                throw new UsernameAlreadyExistsException(username);
            }
        }

    }

    public static List<GymManager> getAllUsers() {
        return gymManagerRepository.find().toList();
    }

    public static Nitrite getDatabase() {
        return database;
    }
}