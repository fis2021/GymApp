package Services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import proiect.fis.gym.aplication.exceptions.*;
import proiect.fis.gym.aplication.model.GymManager;
import proiect.fis.gym.aplication.services.FileSystemService;
import proiect.fis.gym.aplication.services.GymManagerService;

import java.io.IOException;

import static org.testfx.assertions.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class GymManagerServiceTest {

    @BeforeAll
    static void beforeAll() {
        System.out.println("Before Class");
    }

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-GymApplication";
        //FileSystemService.initDirectory();
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        GymManagerService.initTestDatabase("ManagersTest.db");

    }

    @AfterEach
    void tearDown() {
        System.out.println("after");
        GymManagerService.getDatabase().close();
    }

    @Test
    @DisplayName("Database is initialized, and there are no users")
    void testDatabaseIsInitializedAndNoUserIsPersisted() throws IOException {
        assertThat(GymManagerService.getAllUsers()).isNotNull();
        assertThat(GymManagerService.getAllUsers()).isEmpty();
    }

    @Test
    void testManagerIsAddedToDatabase() throws IOException {

        try {
            GymManagerService.addUser("meremere1@M","ana","are","+400757806405",
                    "ana@ana.com", "tm", "gym one", "GymOne", "meremere1@M");
        } catch (UsernameAlreadyExistsException | FieldsAreNotEmptyException | ValidPasswordException | corectEmailException |
                NotMatchingPasswordsException | ValidUsernameException | validPhoneNumberException | ManagerUsernameIsNotOnShortListException e) {
            e.printStackTrace();
        }
        assertThat(GymManagerService.getAllUsers()).size().isEqualTo(1);
        GymManager user1 = GymManagerService.getAllUsers().get(0);
        assertThat(user1).isNotNull();

        assertThat(user1.getFirstName()).isEqualTo("ana");
        assertThat(user1.getLastName()).isEqualTo("are");
        assertThat(user1.getPhoneNumber()).isEqualTo("+400757806405");
        assertThat(user1.getEmail()).isEqualTo("ana@ana.com");
        assertThat(user1.getGymLocation()).isEqualTo("tm");
        assertThat(user1.getCompanyName()).isEqualTo("gym one");
        assertThat(user1.getUsername()).isEqualTo("GymOne");
        assertThat(user1.getPassword()).isEqualTo(GymManagerService.encodePassword("GymOne", "meremere1@M"));

    }

    @Test
    void testManagerCannotBeAddedTwice() {
        try {
            GymManagerService.addUser("meremere1@M","ana","are","+400757806405",
                    "ana@ana.com", "tm", "gym one", "GymOne", "meremere1@M");
        } catch (UsernameAlreadyExistsException | FieldsAreNotEmptyException | ValidPasswordException | corectEmailException |
                NotMatchingPasswordsException | ValidUsernameException | validPhoneNumberException | ManagerUsernameIsNotOnShortListException e) {
            e.printStackTrace();
        }

        Exception exception = assertThrows(UsernameAlreadyExistsException.class, () -> {
            GymManagerService.addUser("meremere1@M","ana","are","+400757806405",
                    "ana@ana.com", "tm", "gym one", "GymOne", "meremere1@M");
        });

        String expectedMessage = "An account with the username GymOne already exists!";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void checkManagersListTest(){
        String name1 = "GymOne";
        String name2 = "Iguana";
        String name3 = "SmartFit";

       assertDoesNotThrow( () ->{
           GymManagerService.checkManagersList(name1);
           GymManagerService.checkManagersList(name2);
           GymManagerService.checkManagersList(name3);
               });

       assertThrows(
                ManagerUsernameIsNotOnShortListException.class,
                () -> GymManagerService.checkManagersList("Gym One")
        );
    }

    @Test
    void ValidPasswordExceptionTest(){
        Exception exception = assertThrows(ValidPasswordException.class, () -> {
            GymManagerService.addUser("meremere","ana","are","+400757806405",
                    "ana@ana.com", "tm", "gym one", "GymOne", "meremere");
        });

        String expectedMessage = "Please enter a valid password!\nThe password must contain:\n-at least one numeric character\n-at least one lowercase character,\n-at least one uppercase character\n-at least one special symbol among @#$%^&+=_\n-the password length must be between 8 and 20 characters";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void notMatchingPasswordsTest(){
        Exception exception = assertThrows(NotMatchingPasswordsException.class, () -> {
            GymManagerService.addUser("meremere1@M","ana","are","+400757806405",
                    "ana@ana.com", "tm", "gym one", "GymOne", "meremere1@MM");
        });

        String expectedMessage = "The passwords are not matching";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void incorrectEmailExceptionTest(){
        Exception exception = assertThrows(corectEmailException.class, () -> {
            GymManagerService.addUser("meremere1@M","ana","are","+400757806405",
                    "ana@ana.", "tm", "gym one", "GymOne", "meremere1@MM");
        });

        String expectedMessage = "Please enter a valid email adress!";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void validUsernameTest(){
        Exception exception = assertThrows(ValidUsernameException.class, () -> {
            GymManagerService.addUser("meremere1@M","ana","are","+400757806405",
                    "ana@ana.com", "tm", "gym one", "Gym1", "meremere1@MM");
        });

        String expectedMessage = "The username must be at least 5 characters long.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void validPhoneNumberExceptionTest(){
        Exception exception = assertThrows(validPhoneNumberException.class, () -> {
            GymManagerService.addUser("meremere1@M","ana","are","+07574",
                    "ana@ana.com", "tm", "gym one", "GymOne", "meremere1@M");
        });

        Exception exception1 = assertThrows(validPhoneNumberException.class, () -> {
            GymManagerService.addUser("meremere1@M","ana","are","07574124132",
                    "ana@ana.com", "tm", "gym one", "SmartFit", "meremere1@M");
        });

        String expectedMessage = "Please type a valid phone number!";
        String actualMessage = exception.getMessage();
        String actualMessage1 = exception1.getMessage();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedMessage, actualMessage1);
    }
}
