package proiect.fis.gym.aplication.services;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.event.ChangeInfo;
import org.dizitart.no2.event.ChangeListener;
import org.dizitart.no2.objects.ObjectRepository;
import proiect.fis.gym.aplication.exceptions.*;
import proiect.fis.gym.aplication.model.Admin;
import proiect.fis.gym.aplication.model.GymManager;

import static proiect.fis.gym.aplication.services.FileSystemService.getPathToFile;

public class GymManagerService extends RegisterService{
    private static ObjectRepository<GymManager> gymManagerRepository;

    public static void initDatabase() {

        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("Managers.db").toFile())
                .openOrCreate("Geo", "Rares");

        gymManagerRepository = database.getRepository(GymManager.class);

    }

    public static ObjectRepository<GymManager> getGymManagerRepository(){
        return gymManagerRepository;
    }

    public static void addUser( GridPane gridPane, PasswordField confirmPassword, String firstName, String lastName, String phoneNumber, String email, String gymLocation, String companyCode, String username, String password)
            throws UsernameAlreadyExistsException, FieldsAreNotEmptyException, ValidPasswordException, NotMatchingPasswordsException,
            corectEmailException, validPhoneNumberException, ValidUsernameException
    {
        checkUserDoesNotAlreadyExist(username);
        checkEmptyFields(gridPane);
        checkInvalidEmail(email);
        checkInvalidPasswordException(password);
        checkNotMatchingPasswords(password, confirmPassword.getText());
        checkPhoneNumber(phoneNumber);
        checkUsername(username);

        gymManagerRepository.insert(new GymManager(firstName, lastName, phoneNumber, email, gymLocation, companyCode, username, encodePassword(username, password)));
    }

    private static void checkUserDoesNotAlreadyExist(String username) throws UsernameAlreadyExistsException {
        for (GymManager gymManager : gymManagerRepository.find()) {
            if (username.equals(gymManager.getUsername()))
                throw new UsernameAlreadyExistsException(username);
        }
    }
}


