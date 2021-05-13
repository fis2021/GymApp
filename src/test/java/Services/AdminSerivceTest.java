package Services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import proiect.fis.gym.aplication.exceptions.*;
import proiect.fis.gym.aplication.model.Admin;
import proiect.fis.gym.aplication.model.GymManager;
import proiect.fis.gym.aplication.services.AdminService;
import proiect.fis.gym.aplication.services.FileSystemService;
import proiect.fis.gym.aplication.services.GymManagerService;
import proiect.fis.gym.aplication.services.RegisterService;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AdminSerivceTest {
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
        AdminService.initTestDatabase("AdminsTest.db");

    }

    @AfterEach
    void tearDown() {
        System.out.println("after");
        AdminService.getDatabase().close();
    }

    @Test
    @DisplayName("Database is initialized, and there are no users")
    void testDatabaseIsInitializedAndNoUserIsPersisted() throws IOException {
        assertThat(AdminService.getAllUsers()).isNotNull();
        assertThat(AdminService.getAllUsers()).isEmpty();
    }

    @Test
    void testManagerIsAddedToDatabase() throws IOException {

        try {
            AdminService.addUser("1234","meremere1@M","ana","are",
                    "ana@ana.com", "ana1ana", "meremere1@M");
        } catch (UsernameAlreadyExistsException | FieldsAreNotEmptyException | ValidPasswordException | corectEmailException | NotMatchingPasswordsException  | InvalidAdminCodeException e) {
            e.printStackTrace();
        }
        assertThat(AdminService.getAllUsers()).size().isEqualTo(1);
        Admin user1 = AdminService.getAllUsers().get(0);
        assertThat(user1).isNotNull();

        assertThat(user1.getFirstName()).isEqualTo("ana");
        assertThat(user1.getLastName()).isEqualTo("are");
        assertThat(user1.getEmail()).isEqualTo("ana@ana.com");
        assertThat(user1.getUsername()).isEqualTo("ana1ana");
        assertThat(user1.getPassword()).isEqualTo(RegisterService.encodePassword("ana1ana", "meremere1@M"));

    }

    @Test
    void testManagerCannotBeAddedTwice() {
        try {
            AdminService.addUser("1234","meremere1@M","ana","are",
                    "ana@ana.com", "ana1ana", "meremere1@M");
        } catch (UsernameAlreadyExistsException | FieldsAreNotEmptyException | ValidPasswordException | corectEmailException | NotMatchingPasswordsException | InvalidAdminCodeException e) {
            e.printStackTrace();
        }

        Exception exception = assertThrows(UsernameAlreadyExistsException.class, () -> {
            AdminService.addUser("1234","meremere1@M","ana","are",
                    "ana@ana.com", "ana1ana", "meremere1@M");
        });

        String expectedMessage = "An account with the username ana1ana already exists!";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

    }

    @Test
    void InvalidAdminCodeExceptionTest(){
        Exception exception = assertThrows(InvalidAdminCodeException.class, () -> {
            AdminService.addUser("124","meremere1@M","ana","are",
                    "ana@ana.com", "ana1ana", "meremere1@M");
        });

        String expectedMessage = "Admin code is not valid";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void validPasswordExceptionTest(){
        Exception exception = assertThrows(ValidPasswordException.class, () -> {
            AdminService.addUser("1234","meremere1@","ana","are",
                    "ana@ana.com", "ana1ana", "meremere1@");
        });

        String expectedMessage = "Please enter a valid password!\nThe password must contain:\n-at least one numeric character\n-at least one lowercase character,\n-at least one uppercase character\n-at least one special symbol among @#$%^&+=_\n-the password length must be between 8 and 20 characters";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void NotMatchingPasswordsExceptionTest(){
        Exception exception = assertThrows(NotMatchingPasswordsException.class, () -> {
            AdminService.addUser("1234","meremere1@M","ana","are",
                    "ana@ana.com", "ana1ana", "meremere1@MM");
        });

        String expectedMessage = "The passwords are not matching";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void corectEmailExceptionTest(){
        Exception exception = assertThrows(corectEmailException.class, () -> {
            AdminService.addUser("1234","meremere1@M","ana","are",
                    "ana@ana.c", "ana1ana", "meremere1@MM");
        });

        String expectedMessage = "Please enter a valid email adress!";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void taxGymTest(){
        GymManager gymManager = new GymManager();
        gymManager.setTaxed(false);

        assertThat(AdminService.taxGym(gymManager)).isEqualTo(true);
        assertThat(AdminService.taxGym(gymManager)).isEqualTo(false);
    }
}
