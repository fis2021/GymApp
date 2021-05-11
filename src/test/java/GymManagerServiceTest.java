import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import proiect.fis.gym.aplication.controllers.RegisterGymManagerController;
import proiect.fis.gym.aplication.exceptions.*;
import proiect.fis.gym.aplication.model.GymManager;
import proiect.fis.gym.aplication.services.FileSystemService;
import proiect.fis.gym.aplication.services.GymManagerService;

import java.io.IOException;

import static org.testfx.assertions.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class GymManagerServiceTest {

    private boolean canDelete;

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
}
